package softsolutions.homeouniversum;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class cases_symptoms {
    @PrimaryKey(autoGenerate = true)
    int _id;
    int cases_symptoms_id;
    String cases_symptoms_date;
    @ColumnInfo(name = "cases_id", defaultValue ="0")
    int cases_id;
    @ColumnInfo(name = "cases_symptoms_rep_id", defaultValue ="0")
    int cases_symptoms_rep_id;
    @ColumnInfo(name = "cases_symptoms_selected", defaultValue ="0")
    int cases_symptoms_selected;
    @ColumnInfo(name = "cases_symptoms_range", defaultValue ="1")
    int cases_symptoms_range;
}
