package softsolutions.homeouniversum;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


public class AppData extends MultiDexApplication {

    public static AppData instance;
    //private databaseApp database;
    private  db database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(
                        getApplicationContext(), db.class, "db").createFromAsset("db.db")
                // .addMigrations(MIGRATION_4_5)
                .build();
        //Log.i("VERSION====",""+database.getOpenHelper().getReadableDatabase().getVersion());
    }
    public static AppData getInstance() {
        return instance;
    }

    //public databaseApp getDatabase() {  return database;    }
    public db getDatabase() {  return database;    }

    public static final Migration MIGRATION_4_5= new Migration(4, 5) {
        @Override
        public void migrate(@NonNull final SupportSQLiteDatabase database) {}
    };


    public static final Migration MIGRATION_2_3= new Migration(2, 3) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("BEGIN TRANSACTION");
            database.execSQL("CREATE TABLE IF NOT EXISTS materia " +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL DEFAULT (0)," +
                    " name TEXT ," +
                    " shortname TEXT ," +
                    " hist TEXT," +
                    " alt TEXT," +
                    " deskr TEXT ," +
                    " mind TEXT ," +
                    " head TEXT ," +
                    " eye TEXT ," +
                    " ear TEXT ," +
                    " nose TEXT ," +
                    " face TEXT ," +
                    " mouth TEXT ," +
                    " throat TEXT ," +
                    " stomach TEXT ," +
                    " abdomen TEXT ," +
                    " rectum TEXT ," +
                    " stool TEXT ," +
                    " urinary TEXT ," +
                    " male TEXT ," +
                    " female TEXT ," +
                    " respiration TEXT ," +
                    " heart TEXT ," +
                    " chest TEXT ," +
                    " back TEXT ," +
                    " limbs TEXT ," +
                    " sleep TEXT ," +
                    " fever TEXT ," +
                    " skin TEXT ," +
                    " generality TEXT ," +
                    " modality TEXT ," +
                    " relations TEXT ," +
                    " dose TEXT ," +
                    " mark_del INTEGER NOT NULL DEFAULT (0)," +
                    " mark_edit INTEGER NOT NULL DEFAULT (0)," +
                    " mark_shared INTEGER NOT NULL DEFAULT (0)," +
                    " author_id INTEGER NOT NULL DEFAULT (0)," +
                    " last_change TEXT," +
                    " tried INTEGER NOT NULL DEFAULT (0)" +
                    ")");
            database.execSQL("COMMIT");
        }
    };
    public static final Migration MIGRATION_10_11= new Migration(10, 11) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE work_prep RENAME TO sqlitestudio_temp_table");
            database.execSQL("CREATE TABLE work_prep (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " short TEXT ," +
                    " full_en TEXT," +
                    " full_ru TEXT," +
                    " freq INTEGER DEFAULT (0)," +
                    " rate INTEGER DEFAULT (0)" +
                    ")");
            database.execSQL("INSERT INTO work_prep (_id, short, full_en, full_ru,freq,rate)" +
                    " SELECT _id,short, full_en, full_ru, freq, rate FROM sqlitestudio_temp_table");
            database.execSQL("DROP TABLE sqlitestudio_temp_table");
            Log.i("MIGRATE====","DROP TABLE sqlitestudio_temp_table");
        }
    };

}