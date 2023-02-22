package softsolutions.homeouniversum;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity{
    public static final String APP_PREFERENCES = "app_settings";
    public static final String APP_PREFERENCES_COUNTER = "symptomCounter";
    public static final String APP_PREFERENCES_IDCASE = "currentIdCase";
    public static final String APP_PREFERENCES_SURNAMECASE = "currentCaseSurname";
    public static final String APP_PREFERENCES_DATECASE = "currentCaseDate";
    SharedPreferences mSettings;
    private String currentIdCase;
    private String currentCaseSurname;
    private String currentCaseDate;
    db db= AppData.getInstance().getDatabase();
    private AppBarConfiguration appBarConfiguration;
    private final ArrayList<String> topList =new ArrayList<>();
    private ArrayList<String> bottomList = new ArrayList<>();
    private ArrayList<String> filterStrings =new ArrayList<>();
    private final ArrayList<Integer> selectedTop = new ArrayList<>();
    RecyclerView topSection, bottomSection;
    EditText searchLocal;
    AppRecycleAdapter recyclerViewAdapterBottom, recyclerViewAdapterTop;
    TextView AllRemedies;
    ImageButton casesButton, addSymptomButton, runToSympListButton;
    AlertDialog exitDialog, caseDialog;
    Context mainContext;
    String stringDate;
    Calendar appCalendar;
    DateFormat df;
    int currentSymptomCounter;
    Dialog dialog;

    //---------click Listeners for Top and Bottom REsyclerViews-------------
    private final View.OnClickListener onItemClickListenerBottom = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            // int position = viewHolder.getAdapterPosition();

            // viewHolder.getItemId();
            // viewHolder.getItemViewType();
            // viewHolder.itemView;
            TextView tv = (TextView) view.findViewById(R.id.section);
        /*  SharedPreferences.Editor editor = mSettings.edit();
            editor.putInt(APP_PREFERENCES_SPLASH_C, splashCounter+1);
            editor.apply();*/
            topList.add(tv.getText().toString());
            selectedTop.add(1);
            buildTopList();
            getSectionsFromDB(topList.size(), topList, null);
        }
    };
    private final View.OnClickListener onItemClickListenerTop = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            TextView tv = (TextView) view.findViewById(R.id.section);
            String clickItem = tv.getText().toString();
            // viewHolder.getItemId();
            // viewHolder.getItemViewType();
            // viewHolder.itemView;

            do
            {
                topList.remove(topList.size()-1);
                selectedTop.remove(topList.size()-1);
            }
            while((topList.size())>position);
            if(searchLocal != null) searchLocal.setText("");
            Log.i("TopListCLICK===", clickItem);
            if(searchLocal.getVisibility()!=View.VISIBLE)searchLocal.setVisibility(View.VISIBLE);
            addSymptomButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_baseline_add_circle_white_48, null));
            addSymptomButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            buildTopList();
            getSectionsFromDB(topList.size(), topList, clickItem);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainContext = MainActivity.this;
        setContentView(R.layout.activity_main);
        appCalendar = Calendar.getInstance();
        df = DateFormat.getDateInstance(DateFormat.SHORT);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        currentIdCase = mSettings.getString(APP_PREFERENCES_IDCASE, "0");
        currentCaseSurname = mSettings.getString(APP_PREFERENCES_SURNAMECASE, getResources().getString(R.string.no_nameCase));
        currentCaseDate = mSettings.getString(APP_PREFERENCES_DATECASE, String.valueOf(appCalendar.getTimeInMillis()));
        currentSymptomCounter = mSettings.getInt(APP_PREFERENCES_COUNTER, 0);
        dialog = new Dialog(mainContext);
        stringDate=df.format(Long.parseLong(currentCaseDate));
        topSection = findViewById(R.id.top_section);
        bottomSection = findViewById(R.id.bottom_section);
        addSymptomButton = findViewById(R.id.select_button);
        runToSympListButton = findViewById(R.id.start_button);
        checkExistSymptomsList();
        casesButton = findViewById(R.id.cases_button);
        casesButton.setOnClickListener(view -> {
            makeCaseAlertDialog();
        });
        searchLocal = findViewById(R.id.search_local);
        AllRemedies =findViewById(R.id.all_remedies);
        //-------------------add filter to search local------------
        searchLocal.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }


        });
        // ------------------start of layout building--------------
        if (bottomList.size() == 0 && topList.size() == 0) {
            getSectionsFromDB(topList.size(), topList, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    void buildTopList() {
        if(topList.size()>0){
         recyclerViewAdapterTop = new AppRecycleAdapter(topList, selectedTop);
        topSection.setLayoutManager(new LinearLayoutManager(this));
        topSection.setAdapter(recyclerViewAdapterTop);
        recyclerViewAdapterTop.setOnItemClickListener(onItemClickListenerTop);
        }
        else {
            topSection.setAdapter(null);
        }


    }

    void buildBottomList(List<String> strings, String itemText, ArrayList<Integer> selectedBottom){
        if(searchLocal!=null) searchLocal.setText("");
        filterStrings = (ArrayList<String>) strings;
        if (bottomSection != null) {

            recyclerViewAdapterBottom = new AppRecycleAdapter(strings,selectedBottom);
            bottomSection.setLayoutManager(new LinearLayoutManager(this));
            bottomSection.setAdapter(recyclerViewAdapterBottom);
            if(itemText!=null){
                int position=0;
                for(int i=0; i<strings.size();i++){
                    if(itemText.equals(strings.get(i))){
                        position=i;
                        break;
                    }
                }
                //Log.i("ScrollToPosition====",""+position);
                int finalPosition = position;
                bottomSection.post(() -> {
                    // Call smooth scroll
                    bottomSection.scrollToPosition(finalPosition);
                });
                //lvBottom.smoothScrollToPosition(position);
            }
            recyclerViewAdapterBottom.setOnItemClickListener(onItemClickListenerBottom);
        }
    }

    void filter(String filterText) {
        List<String> filteredStrings = new ArrayList<>();
        for (String item : filterStrings) {
            // Log.i("item===",item);
            if (item.toLowerCase().contains(filterText)) {
                filteredStrings.add(item);
            }
        }
        if(recyclerViewAdapterBottom!=null)recyclerViewAdapterBottom.filterList(filteredStrings);
    }

    void getSectionsFromDB(int section_numb, ArrayList<String> sections, String clickItem) {
        switch (section_numb){
            case 0:
                db.full_repertoryDao().getDistinctSection0()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSingleObserver<List<String>>() {
                            @Override
                            public void onSuccess(@NonNull List<String> strings) {
                                bottomList = (ArrayList<String>) strings;
                                ArrayList<Integer> selectedBottom = new ArrayList<>();
                                for (String item: bottomList) {
                                    selectedBottom.add(0);
                                }
                                buildBottomList(bottomList, clickItem, selectedBottom);
                                AllRemedies.setText("");
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("getDistinct_0_ERR===", e.toString());
                            }
                        });
                break;
            case 1:
                Log.i("sections.get(0)===", sections.get(0));
                db.full_repertoryDao().getDistinctSection1(sections.get(0))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSingleObserver<List<String>>() {
                            @Override
                            public void onSuccess(@NonNull List<String> strings) {
                                bottomList = (ArrayList<String>) strings;
                                ArrayList<Integer> selectedBottom = new ArrayList<>();
                                for (String item: bottomList) {
                                    selectedBottom.add(0);
                                }
                                buildBottomList(bottomList, clickItem, selectedBottom);
                                AllRemedies.setText("");
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("getDistinct_1_ERR===", e.toString());
                            }
                        });
                break;
            case 2:
                Log.i("sections.get(01)===", sections.get(0)+" "+ sections.get(1));
                db.full_repertoryDao().getDistinctSection2(sections.get(0), sections.get(1))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSingleObserver<List<String>>() {
                            @Override
                            public void onSuccess(@NonNull List<String> strings) {
                                if(strings.size()==1)
                                {
                                    db.full_repertoryDao().getSingleSymptomSection01(sections.get(0),sections.get(1))
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new DisposableSingleObserver<full_repertory>() {
                                                @Override
                                                public void onSuccess(@NonNull full_repertory symptom) {
                                                    startFullSymp(symptom);
                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {
                                                    Log.e("getSymptom011===", e.toString());
                                                }
                                            });
                                }
                                else {
                                    bottomList = (ArrayList<String>) strings;
                                    ArrayList<Integer> selectedBottom = new ArrayList<>();
                                    for (String item: bottomList) {
                                        selectedBottom.add(0);
                                    }
                                    buildBottomList(bottomList, clickItem, selectedBottom);
                                    AllRemedies.setText("");
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("getDistinct_2_ERR===", e.toString());
                            }
                        });
                break;
            case 3:
                Log.i("sections.get(03)===", sections.get(0)+"|"+ sections.get(1)+"|" +sections.get(2));
                db.full_repertoryDao().getDistinctSection3(sections.get(0), sections.get(1), sections.get(2))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSingleObserver<List<String>>() {
                            @Override
                            public void onSuccess(@NonNull List<String> strings) {
                                if(strings.size()==1)
                                {
                                    db.full_repertoryDao().getSingleSymptomSection02(sections.get(0),sections.get(1),sections.get(2))
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new DisposableSingleObserver<full_repertory>() {
                                                @Override
                                                public void onSuccess(@NonNull full_repertory symptom) {
                                                    startFullSymp(symptom);
                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {
                                                    Log.e("getSymptom012===", e.toString());
                                                }
                                            });
                                }
                                else {
                                    bottomList = (ArrayList<String>) strings;
                                    ArrayList<Integer> selectedBottom = new ArrayList<>();
                                    for (String item: bottomList) {
                                        selectedBottom.add(0);
                                    }
                                    buildBottomList(bottomList, clickItem, selectedBottom);
                                    AllRemedies.setText("");
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("getDistinct_3_ERR===", e.toString());
                            }
                        });
                break;
            case 4:
                db.full_repertoryDao().getDistinctSection4(sections.get(0), sections.get(1), sections.get(2), sections.get(3))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSingleObserver<List<String>>() {
                            @Override
                            public void onSuccess(@NonNull List<String> strings) {
                                if(strings.size()==1)
                                {
                                    db.full_repertoryDao().getSingleSymptomSection03(sections.get(0),sections.get(1), sections.get(2), sections.get(3))
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new DisposableSingleObserver<full_repertory>() {
                                                @Override
                                                public void onSuccess(@NonNull full_repertory symptom) {
                                                    startFullSymp(symptom);
                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {
                                                    Log.e("getSymptom0123===", e.toString());
                                                }
                                            });
                                }
                                else {
                                    bottomList = (ArrayList<String>) strings;
                                    ArrayList<Integer> selectedBottom = new ArrayList<>();
                                    for (String item: bottomList) {
                                        selectedBottom.add(0);
                                    }
                                    buildBottomList(bottomList, clickItem, selectedBottom);
                                    AllRemedies.setText("");
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("getDistinct_4_ERR===", e.toString());
                            }
                        });
                break;
            case 5:
                db.full_repertoryDao().getDistinctSection5(sections.get(0), sections.get(1), sections.get(2), sections.get(3), sections.get(4))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSingleObserver<List<String>>() {
                            @Override
                            public void onSuccess(@NonNull List<String> strings) {
                                if(strings.size()==1)
                                {
                                    db.full_repertoryDao().getSingleSymptomSection04(sections.get(0),sections.get(1), sections.get(2), sections.get(3), sections.get(4))
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new DisposableSingleObserver<full_repertory>() {
                                                @Override
                                                public void onSuccess(@NonNull full_repertory symptom) {
                                                    startFullSymp(symptom);
                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {
                                                    Log.e("getSymptom01234===", e.toString());
                                                }
                                            });
                                }
                                else {
                                    bottomList = (ArrayList<String>) strings;
                                    ArrayList<Integer> selectedBottom = new ArrayList<>();
                                    for (String item: bottomList) {
                                        selectedBottom.add(0);
                                    }
                                    buildBottomList(bottomList, clickItem, selectedBottom);
                                    AllRemedies.setText("");
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("getDistinct_5_ERR===", e.toString());
                            }
                        });
                break;
            case 6:
                db.full_repertoryDao().getDistinctSection6(sections.get(0), sections.get(1), sections.get(2), sections.get(3), sections.get(4), sections.get(5))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSingleObserver<List<String>>() {
                            @Override
                            public void onSuccess(@NonNull List<String> strings) {
                                if(strings.size()==1)
                                {
                                    db.full_repertoryDao().getSingleSymptomSection05(sections.get(0),sections.get(1), sections.get(2), sections.get(3), sections.get(4), sections.get(5))
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new DisposableSingleObserver<full_repertory>() {
                                                @Override
                                                public void onSuccess(@NonNull full_repertory symptom) {
                                                    startFullSymp(symptom);
                                                }

                                                @Override
                                                public void onError(@NonNull Throwable e) {
                                                    Log.e("getSymptom012345===", e.toString());
                                                }
                                            });
                                }
                                else {
                                    bottomList = (ArrayList<String>) strings;
                                    ArrayList<Integer> selectedBottom = new ArrayList<>();
                                    for (String item: bottomList) {
                                        selectedBottom.add(0);
                                    }
                                    buildBottomList(bottomList, clickItem, selectedBottom);
                                    AllRemedies.setText("");
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("getDistinct_6_ERR===", e.toString());
                            }
                        });
                break;
            case 7:
                db.full_repertoryDao().getDistinctSection7(sections.get(0), sections.get(1), sections.get(2), sections.get(3), sections.get(4), sections.get(5), sections.get(6))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSingleObserver<List<String>>() {
                            @Override
                            public void onSuccess(@NonNull List<String> strings) {
                                db.full_repertoryDao().getSingleSymptomSection06(sections.get(0),sections.get(1), sections.get(2), sections.get(3), sections.get(4), sections.get(5), sections.get(6))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new DisposableSingleObserver<full_repertory>() {
                                            @Override
                                            public void onSuccess(@NonNull full_repertory symptom) {
                                                startFullSymp(symptom);
                                            }

                                            @Override
                                            public void onError(@NonNull Throwable e) {
                                                Log.e("getSymptom012345===", e.toString());
                                            }
                                        });
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("getDistinct_7_ERR===", e.toString());
                            }
                        });
                break;
            default:

        }
    }
    void startFullSymp(full_repertory symptom){
        Log.i("Ful symptom===", "START");
        bottomSection.setAdapter(null);
        searchLocal.setVisibility(View.GONE);
        String prp4 = symptom.prp4 != null ? "<font color=#ed0400>"+symptom.prp4+"</font>" : "";
        String prp3 = symptom.prp3 != null ? ", <font color=#1d6b00>"+symptom.prp3+"</font>" : "";
        String prp2 = symptom.prp2 != null ? ", <font color=#005591>"+symptom.prp2+"</font>" : "";
        String prp1 = symptom.prp1 != null ? ", <font color=#000000>"+symptom.prp1+"</font>" : "";
        AllRemedies.setText(Html.fromHtml("" + prp4 + prp3 + prp2 + prp1));
        checSymptomAllredyExit(symptom);
    }

    void makeExitAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainContext);
        builder.setTitle(getResources().getString(R.string.TitleDialogExit))  // заголовок
                .setMessage(getResources().getString(R.string.MessageDialogExit)) // сообщение
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getResources().getString(R.string.ButtonOk), (dialog, arg1) -> {
                    //  getDataFromBD.databaseClose();
                    ActivityCompat.finishAffinity(MainActivity.this);
                    System.exit(0);
                })
                .setNegativeButton(getResources().getString(R.string.ButtonNo), (dialog, arg1) -> {

                })
                .setCancelable(true)
                .setOnCancelListener(dialog -> {

                });
        exitDialog = builder.create();
    }
    void makeCaseAlertDialog() {
        dialog.setContentView(androidx.drawerlayout.R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView caseInfo = dialog.findViewById(R.id.header_text);
        TextView symptomInfo = dialog.findViewById(R.id.string1_text);
        TextView dateInfo = dialog.findViewById(R.id.string2_text);
        ImageView closeDialog = dialog.findViewById(R.id.imageDialogClose);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button casesButton = dialog.findViewById(R.id.button_ok);
        casesButton.setText(getResources().getString(R.string.CaseButtonString));
        casesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        caseInfo.setText(getResources().getString(R.string.CaseString,currentCaseSurname));
        symptomInfo.setText(getResources().getString(R.string.SelectedSymptomString, currentSymptomCounter));
        dateInfo.setText(getResources().getString(R.string.SelectedDate,stringDate ));
       dialog.show();
    }

    void checSymptomAllredyExit(full_repertory symptom){
        db.cases_symptomsDao().checkSymptomExist(currentIdCase, currentCaseDate,symptom._id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer symptomCounter) {
                        if(symptomCounter ==0){
                            addSymptomButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_baseline_add_circle_48, null));
                            addSymptomButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                addSymptomForCase(symptom);
                                }
                            });
                        }
                        else {
                            addSymptomButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_baseline_add_circle_white_48, null));
                            addSymptomButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("checkEistSymptomERR===", e.toString());
                    }
                });
    }
    void addSymptomForCase(full_repertory symptom){
        db.cases_symptomsDao().addSymptom(currentIdCase, currentCaseDate,symptom._id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Long>() {
                    @Override
                    public void onSuccess(Long insert) {
                        Log.i("Insert===", ""+insert);
                        checkExistSymptomsList();
                            addSymptomButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.ic_baseline_add_circle_white_48, null));
                            addSymptomButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("checkEistSymptomERR===", e.toString());
                    }
                });
    }

    void checkExistSymptomsList(){
        db.cases_symptomsDao().getSympCounttByCaseIdDate(currentIdCase, currentCaseDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer symptomsCount) {
                        Log.i("SymptomCount===", ""+symptomsCount);
                        if(symptomsCount > 0) {
                            //------------------write symptom counter in references
                            SharedPreferences.Editor editor = mSettings.edit();
                            editor.putInt(APP_PREFERENCES_COUNTER,symptomsCount);
                            editor.putString(APP_PREFERENCES_DATECASE,currentCaseDate);
                            editor.apply();
                            currentSymptomCounter = symptomsCount;
                            runToSympListButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_forward_48, null));
                            runToSympListButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                //---------to SymptomsListActivity
                                    Intent  startSymptomsListActivity= new Intent(MainActivity.this, SymptomsListActivity.class);
                                    startSymptomsListActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(startSymptomsListActivity);
                                }
                            });
                        }else {
                            currentSymptomCounter = 0;
                            runToSympListButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_forward_white_48, null));
                            runToSympListButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("checkEistSymptomERR===", e.toString());
                    }
                });
    }

}