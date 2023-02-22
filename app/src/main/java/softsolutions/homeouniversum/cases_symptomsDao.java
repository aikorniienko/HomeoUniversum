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
public interface cases_symptomsDao {
    String  getSympCounttByCaseIdDate="SELECT COUNT() FROM cases_symptoms WHERE (cases_id=:caseID AND cases_symptoms_date=:date)";
    @Query( getSympCounttByCaseIdDate)
    Single<Integer> getSympCounttByCaseIdDate(String caseID, String date);

    String  deleteSympCaseIdDate="DELETE FROM cases_symptoms WHERE (cases_id=:caseID AND cases_symptoms_date=:date)";
    @Query(deleteSympCaseIdDate)
    Single<Integer> deleteSympCaseIdDate(String caseID, String date);

    String  deleteAllSympCases="DELETE FROM cases_symptoms";
    @Query(deleteAllSympCases)
    Single<Integer> deleteAllSympCases();


    String checkSymptomExist="SELECT COUNT() FROM cases_symptoms WHERE (cases_id=:caseID AND cases_symptoms_date=:date AND cases_symptoms_id= :sympID)";
    @Query(checkSymptomExist)
    Single <Integer> checkSymptomExist(String caseID, String date, int sympID);

   String checkSymExistCaseDate="SELECT cases_symptoms_id FROM cases_symptoms WHERE(cases_id=:caseID AND cases_symptoms_date=:date)";
    @Query(checkSymExistCaseDate)
    Single <List<Integer>> checkSymExistCaseDate(String caseID, String date);

    String  getDatesForCaseId="SELECT DISTINCT cases_symptoms_date FROM cases_symptoms WHERE (cases_id=:caseID) ORDER BY cases_symptoms_date DESC";
    @Query(getDatesForCaseId)
    Single<String[]> getDatesForCaseId(String caseID);

    String getAllSymptoms="SELECT * FROM cases_symptoms";
    @Query(getAllSymptoms)
    Single <List<cases_symptoms>> getAllSymptoms();


    String  getSympCasesDate="SELECT * FROM cases_symptoms WHERE (cases_id=:caseID AND cases_symptoms_date=:date)";
    @Query(getSympCasesDate)
    Single<List<cases_symptoms>> getSympCasesDate(String caseID, String date);

    String  getSelectedSymp="SELECT * FROM cases_symptoms WHERE (cases_id=:caseID AND cases_symptoms_date=:date AND cases_symptoms_selected=1)";
    @Query(getSelectedSymp)
    Single<List<cases_symptoms>> getSelectedSymp(String caseID, String date);


    String  deleteOneSymptomId="DELETE FROM cases_symptoms WHERE (cases_id=:caseID AND cases_symptoms_date=:date AND cases_symptoms_id= :deleteSympId)";
    @Query(deleteOneSymptomId)
    Single<Integer> deleteOneSymptomId(String caseID, String date,String deleteSympId);

    String  deleteListSymptomsId="DELETE FROM cases_symptoms WHERE (cases_id=:caseID AND cases_symptoms_date=:date AND cases_symptoms_id IN (:deleteSympId))";
    @Query(deleteListSymptomsId)
    Single<Integer> deleteListSymptomsId(String caseID, String date,Integer[] deleteSympId);

    String  deleteSymptomsCaseId="DELETE FROM cases_symptoms WHERE (cases_id=:caseID)";
    @Query(deleteSymptomsCaseId)
    Single<Integer> deleteSymptomsCaseId(String caseID);

    String  unselectAllSymptomsCaseDate="UPDATE cases_symptoms SET cases_symptoms_selected=0 WHERE (cases_id=:caseID AND cases_symptoms_date=:date)";
    @Query(unselectAllSymptomsCaseDate)
    Single<Integer> unselectAllSymptomsCaseDate(String caseID, String date);

    String  setSelectedSymptomsCaseDateIdList="UPDATE cases_symptoms SET cases_symptoms_selected=1 WHERE (cases_id=:caseID AND cases_symptoms_date=:date AND cases_symptoms_id IN (:sympID))";
    @Query(setSelectedSymptomsCaseDateIdList)
    Single<Integer> setSelectedSymptomsCaseDateIdList(String caseID, String date, Integer [] sympID);


    String  saveAnonymSymptoms="UPDATE cases_symptoms SET cases_id=:caseID, cases_symptoms_date=:dateSave WHERE (cases_id=0)";
    @Query(saveAnonymSymptoms)
    Single<Integer> saveAnonymSymptoms(String caseID, String dateSave);

    String  setSymptomRange="UPDATE cases_symptoms SET cases_symptoms_range=:range WHERE (cases_id=:caseID AND cases_symptoms_date=:dateSave AND cases_symptoms_id =:sympId)";
    @Query(setSymptomRange)
    Single<Integer> setSymptomRange(String caseID, String dateSave, String sympId, int range);

    String  addSymptom="INSERT INTO cases_symptoms (cases_id, cases_symptoms_id, cases_symptoms_date,  cases_symptoms_selected, cases_symptoms_range) VALUES (:caseID,:sympID,:date,0,1)";
    @Query(addSymptom)
    Single<Long> addSymptom(String caseID, String date, int sympID);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public Single<List<Long>> insertAll(List<cases_symptoms> insertCase);

    @Update
    void update(cases_symptoms cases_symptoms);

    @Delete
    void delete(cases_symptoms casesSymp);

}
