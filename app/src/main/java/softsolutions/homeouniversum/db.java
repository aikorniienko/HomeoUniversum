package softsolutions.homeouniversum;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        version =1,
        entities = {full_repertory.class, cases_symptoms.class},
        /*autoMigrations={
              @AutoMigration(
                       from = 26,
                       to = 27

        },    */
        exportSchema = false)
public  abstract class db extends RoomDatabase {
    private static db INSTANCE;
    public abstract full_repertoryDao full_repertoryDao();
    public abstract cases_symptomsDao cases_symptomsDao();

}