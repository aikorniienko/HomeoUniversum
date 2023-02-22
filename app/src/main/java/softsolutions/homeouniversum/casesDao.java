package softsolutions.homeouniversum;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import io.reactivex.rxjava3.core.Single;



@Dao
interface casesDao {
    String getCaseById = "SELECT * FROM cases WHERE _id = :caseID";

    @Query(getCaseById)
    Single<cases> getCaseById(String caseID);

    String getALLCases = "SELECT * FROM cases ORDER BY cases_surname ASC";

    @Query(getALLCases)
    Single<List<cases>> getAllCases();

    String getCasesQuantity = "SELECT COUNT() FROM cases";

    @Query(getCasesQuantity)
    Single<Integer> getCasesQuantity();

    String deleteCaseById = "DELETE FROM cases WHERE _id= :caseID";

    @Query(deleteCaseById)
    Single<Integer> deleteCaseById(String caseID);

    String deleteAllCases = "DELETE FROM cases";

    @Query(deleteAllCases)
    Single<Integer> deleteAllCases();

    String getMaxCaseId = "SELECT MAX(_id) FROM cases";

    @Query(getMaxCaseId)
    Single<Integer> getMaxCaseId();


    String existCaseById = "SELECT _id FROM cases WHERE EXISTS (SELECT * FROM cases WHERE _id= :caseID)";

    @Query(existCaseById)
    Single<Boolean> existCaseById(Integer caseID);

    String insertCase = "INSERT INTO cases (surname) VALUES (:caseIns)";

    @Query(insertCase)
    Single<Long> insertCase(String caseIns);
    //@Insert
    //void insert(cases cases);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Single<Long> insert(cases cases);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public Single<List<Long>> insertAll(List<cases> cases);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public Single<Integer> update(cases cases);

    @Delete
    void delete(cases cases);
}