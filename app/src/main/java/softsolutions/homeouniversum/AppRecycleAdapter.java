package softsolutions.homeouniversum;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;


public class AppRecycleAdapter extends RecyclerView.Adapter<AppRecycleAdapter.MyViewHolder> {
    private List<String> mItemList;
    private final List<Spanned> mlistOfRemedies=null;
    private final List<Integer> mflagSelectedSymp;
    private View.OnClickListener mOnItemClickListener;
  //  private final String bottom;

    public AppRecycleAdapter(List<String> ItemList, List<Integer> selected) {
        this.mItemList = ItemList;
        this.mflagSelectedSymp=selected;
   //     this.bottom = bottom;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);

        /*if(xl_text.equals("Yes"))
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.symptom_list_item, parent, false);
        else
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.symptom_list_item, parent, false);*/
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (mItemList != null) {
            Context cont = holder.itemView.getContext();
            if (mflagSelectedSymp != null) {
                int color = 0;
                int colorText = 0;
                if (mflagSelectedSymp.get(position) == 1)
                {
                    color = ContextCompat.getColor(cont, R.color.teal_700);
                    colorText = Color.WHITE;
                }
                else {
                    color = ContextCompat.getColor(cont, R.color.background_grey);
                    colorText = Color.BLACK;
                }
                holder.cardView.setCardBackgroundColor(color);
                holder.description.setTextColor(colorText);
                holder.description.setText(mItemList.get(position));
            }
            if (mlistOfRemedies != null) {
                holder.description.setText(mlistOfRemedies.get(position));
            }

        }
    }

    @Override
    public int getItemCount() {

        if(mItemList!=null) return mItemList.size();
        else {
            if (mlistOfRemedies != null) return mlistOfRemedies.size();
            else
                return 0;
        }

    }

    //TODO: Step 2 of 4: Assign itemClickListener to your local View.OnClickListener variable
    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            /*title = itemView.findViewById(R.id.title);*/
            description = itemView.findViewById(R.id.section);
            cardView = itemView.findViewById(R.id.cardView);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
    public void filterList(List<String> filteredStrings) {
        //mTestItemList.clear();
        mItemList=filteredStrings;
        this.notifyDataSetChanged();
    }
}


