package softsolutions.homeouniversum;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SymptomsListActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES = "app_settings";
    public static final String APP_PREFERENCES_COUNTER = "symptomCounter";
    public static final String APP_PREFERENCES_IDCASE = "currentIdCase";
    public static final String APP_PREFERENCES_SURNAMECASE = "currentCaseSurname";
    public static final String APP_PREFERENCES_DATECASE = "currentCaseDate";
    public static final String APP_PREFERENCES_SELECTEDALL = "selectedAll";
    SharedPreferences mSettings;
    AppRecycleAdapter recyclerViewAdapterSympList;
    ImageButton casesButton, startButton, selectButton, menuButton;
    private String currentIdCase;
    private String currentCaseSurname;
    private String currentCaseDate;
    db db= AppData.getInstance().getDatabase();
    private AppBarConfiguration appBarConfiguration;
    private final ArrayList<String> symptomsListDescription =new ArrayList<>();
    private final ArrayList<Integer> symptomsListSelected =new ArrayList<>();
    RecyclerView symptomsRecyclerView;
    AlertDialog exitDialog, caseDialog;
    Context listContext;
    String stringDate;
    Calendar appCalendar;
    DateFormat df;
    int currentSymptomCounter;
    Dialog dialog;

    boolean selectedAll;
    private final View.OnClickListener onItemClickListenerSympList = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            if(symptomsListSelected.get(position) ==0){
            symptomsListSelected.set(position,1);}
            else{
                symptomsListSelected.set(position,0);
            }
            buildSymptomsList();
            // viewHolder.getItemId();
            // viewHolder.getItemViewType();
            // viewHolder.itemView;
            TextView tv = (TextView) view.findViewById(R.id.section);
        /*  SharedPreferences.Editor editor = mSettings.edit();
            editor.putInt(APP_PREFERENCES_SPLASH_C, splashCounter+1);
            editor.apply();*/

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listContext = SymptomsListActivity.this;
        setContentView(R.layout.activity_symptoms_list);
        appCalendar = Calendar.getInstance();
        df = DateFormat.getDateInstance(DateFormat.SHORT);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        currentIdCase = mSettings.getString(APP_PREFERENCES_IDCASE, "0");
        currentCaseSurname = mSettings.getString(APP_PREFERENCES_SURNAMECASE, getResources().getString(R.string.no_nameCase));
        currentCaseDate = mSettings.getString(APP_PREFERENCES_DATECASE, String.valueOf(appCalendar.getTimeInMillis()));
        currentSymptomCounter = mSettings.getInt(APP_PREFERENCES_COUNTER, 0);
        selectedAll = mSettings.getBoolean(APP_PREFERENCES_SELECTEDALL, false);
        dialog = new Dialog(listContext);
        stringDate=df.format(Long.parseLong(currentCaseDate));
        symptomsRecyclerView = findViewById(R.id.symptoms_list);
        selectButton = findViewById(R.id.select_button);
        selectButton.setOnClickListener(view -> {
            AllSelectedUnselected();
        });

        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(view -> {
            startRepertoresation();
        });


        casesButton = findViewById(R.id.cases_button);
        casesButton.setOnClickListener(view -> {
            makeCaseAlertDialog();
        });
        menuButton = findViewById(R.id.symptoms_menu_button);
        menuButton.setOnClickListener(view -> {
            makeCaseAlertDialog();
        });
            createSymptomsList();
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
            void buildSymptomsList(){
                if(symptomsListDescription.size()>0){
                    recyclerViewAdapterSympList = new AppRecycleAdapter(symptomsListDescription, symptomsListSelected);
                    symptomsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    symptomsRecyclerView.setAdapter(null);
                    symptomsRecyclerView.setAdapter(recyclerViewAdapterSympList);
                    recyclerViewAdapterSympList.setOnItemClickListener(onItemClickListenerSympList);
                }
                else {
                    //close activity
                }
            }
            void createSymptomsList(){
                db.cases_symptomsDao().getSympCasesDate(currentIdCase, currentCaseDate)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSingleObserver<List<cases_symptoms>>() {
                            @Override
                            public void onSuccess(List<cases_symptoms> CasesSymptomsList) {
                                if(CasesSymptomsList.size() > 0) {
                                   createFullSymptomsList(CasesSymptomsList);
                                }

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("getSympCasesDateERR===", e.toString());
                            }
                        });
            }
    void createFullSymptomsList(List<cases_symptoms>CasesSymptomsList){
        int[] symptomsId = new int[CasesSymptomsList.size()];
        for(int i= 0;i < symptomsId.length;i++ ){
            symptomsId[i] = CasesSymptomsList.get(i).cases_symptoms_id;
            symptomsListSelected.add(CasesSymptomsList.get(i).cases_symptoms_selected);
        }
        db.full_repertoryDao().getSympListByListId(symptomsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<full_repertory>>() {
                    @Override
                    public void onSuccess(@NonNull List<full_repertory> symptoms) {

                        if(symptoms.size() > 0){
                            Log.i("symptoms===", ""+symptoms.size());
                            createSymptomsDescription(symptoms);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("getSListByListIdERR===", e.toString());
                    }
                });
    }

    void createSymptomsDescription(List<full_repertory> symptoms){
        StringBuilder strBuilder = new StringBuilder();
        int i = 1;
        for (full_repertory symp:symptoms){
            strBuilder.append(i);
            strBuilder.append(".");
            if(symp.section_0!=null)strBuilder.append(symp.section_0);
            if(symp.section_1!=null && !symp.section_1.equals("...")){
                strBuilder.append(", ");
                strBuilder.append(symp.section_1);
            }
            if(symp.section_2!=null && !symp.section_2.equals("...") && symp.section_2.length() >0){
                strBuilder.append(", ");
                strBuilder.append(symp.section_2);
            }
            if(symp.section_3!=null && !symp.section_3.equals("...")&& symp.section_3.length() >0){
                strBuilder.append(", ");
                strBuilder.append(symp.section_3);
            }
            if(symp.section_4!=null && !symp.section_4.equals("...")&& symp.section_4.length() >0){
                strBuilder.append(", ");
                strBuilder.append(symp.section_4);
            }
            if(symp.section_5!=null && !symp.section_5.equals("...")&& symp.section_5.length() >0){
                    strBuilder.append(", ");
                    strBuilder.append(symp.section_5);
                }
            if(symp.section_6!=null && !symp.section_6.equals("...")&& symp.section_6.length() >0){
                    strBuilder.append(", ");
                    strBuilder.append(symp.section_6);
                }
                if(symp.section_7!=null && !symp.section_7.equals("...")&& symp.section_7.length() >0){
                    strBuilder.append(", ");
                    strBuilder.append(symp.section_7);
                }
                    symptomsListDescription.add(strBuilder.toString());

        }
        buildSymptomsList();
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
                            editor.apply();
                            currentSymptomCounter = symptomsCount;

                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("checkEistSymptomERR===", e.toString());
                    }
                });
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
    void AllSelectedUnselected(){
        if(selectedAll){
            selectedAll = false;
            selectButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_unselect_circle_48, null));
            Collections.fill(symptomsListSelected, 1);
        }
        else {
            selectedAll = true;
            selectButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_select_circle_48, null));
            Collections.fill(symptomsListSelected, 0);

        }
        buildSymptomsList();
    }
    void startRepertoresation(){

    }

}
