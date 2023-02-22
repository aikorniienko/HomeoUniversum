package softsolutions.homeouniversum;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class cases_notes {
    @PrimaryKey(autoGenerate = true)
    int _id;
    int cases_id;
    String cases_notes_date;
    String cases_notes_text;

}
