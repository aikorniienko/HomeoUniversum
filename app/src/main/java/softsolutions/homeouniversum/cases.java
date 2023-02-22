package softsolutions.homeouniversum;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class cases {
    @PrimaryKey(autoGenerate = true)
    int _id;
    String cases_name;
    String cases_surname;
    String cases_birth_date;
    String cases_ocupation;
    int cases_sex;
    int cases_married;
    String cases_phone;
    String cases_mail;
    String cases_address;

}
