package softsolutions.homeouniversum;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Single;


@Dao
public interface full_repertoryDao {

    String getDistinctSection0="SELECT DISTINCT section_0 FROM full_repertory";
    @Query(getDistinctSection0)
    Single<List<String>> getDistinctSection0();

    String getDistinctSection1="SELECT DISTINCT section_1 FROM full_repertory WHERE section_0=:section0";
    @Query(getDistinctSection1)
    Single<List<String>>  getDistinctSection1(String  section0);

    String getDistinctSection2="SELECT DISTINCT section_2 FROM full_repertory WHERE (section_0=:section0 AND section_1=:section1)";
    @Query(getDistinctSection2)
    Single<List<String>>  getDistinctSection2 (String section0, String section1);

    String getDistinctSection3="SELECT DISTINCT section_3 FROM full_repertory WHERE (section_0=:section0 AND section_1=:section1 " +
            "AND section_2=:section2)";
    @Query(getDistinctSection3)
    Single<List<String>> getDistinctSection3 (String section0, String section1, String section2);

    String getDistinctSection4="SELECT DISTINCT section_4 FROM full_repertory WHERE (section_0=:section0 AND section_1=:section1" +
            " AND section_2=:section2 AND section_3=:section3)";
    @Query(getDistinctSection4)
    Single<List<String>> getDistinctSection4 (String section0, String section1, String section2, String section3);

    String getDistinctSection5="SELECT DISTINCT section_5 FROM full_repertory WHERE (section_0=:section0 AND section_1=:section1" +
            " AND section_2=:section2 AND section_3=:section3 AND section_4=:section4)";
    @Query(getDistinctSection5)
    Single<List<String>> getDistinctSection5 (String section0, String section1, String section2, String section3, String section4);

    String getDistinctSection6="SELECT DISTINCT section_6 FROM full_repertory WHERE (section_0=:section0 AND section_1=:section1" +
            " AND section_2=:section2 AND section_3=:section3 AND section_4=:section4 AND section_5=:section5)";
    @Query(getDistinctSection6)
    Single<List<String>> getDistinctSection6 (String section0, String section1, String section2, String section3, String section4, String section5);

    String getDistinctSection7="SELECT DISTINCT section_7 FROM full_repertory WHERE (section_0=:section0 AND section_1=:section1" +
            " AND section_2=:section2 AND section_3=:section3 AND section_4=:section4 AND section_5=:section5" +
            " AND section_6=:section6)";
    @Query(getDistinctSection7)
    Single<List<String>> getDistinctSection7 (String section0, String section1, String section2, String section3, String section4, String section5, String section6);
    //---------------------------getSingleSymptom---------------------------------------------------------------
    String getSingleSymptomSection01="SELECT * FROM full_repertory WHERE (section_0=:section0 AND section_1=:section1)";
    @Query(getSingleSymptomSection01)
    Single<full_repertory> getSingleSymptomSection01 (String section0, String section1);

    String getSingleSymptomSection02="SELECT * FROM full_repertory WHERE (section_0=:section0 AND section_1=:section1 AND section_2=:section2)";
    @Query(getSingleSymptomSection02)
    Single<full_repertory> getSingleSymptomSection02 (String section0, String section1, String section2);

    String getSingleSymptomSection03="SELECT DISTINCT * FROM full_repertory WHERE (section_0=:section0 AND section_1=:section1" +
            " AND section_2=:section2 AND section_3=:section3)";
    @Query(getSingleSymptomSection03)
    Single<full_repertory> getSingleSymptomSection03 (String section0, String section1, String section2, String section3);

    String getSingleSymptomSection04="SELECT DISTINCT * FROM full_repertory WHERE (section_0=:section0 AND section_1=:section1 AND section_2=:section2 " +
            "AND section_3=:section3 AND section_4=:section4)";
    @Query(getSingleSymptomSection04)
    Single<full_repertory> getSingleSymptomSection04 (String section0, String section1, String section2, String section3, String section4);

    String getSingleSymptomSection05="SELECT DISTINCT * FROM full_repertory WHERE (section_0=:section0 AND section_1=:section1 AND section_2=:section2 " +
            "AND section_3=:section3 AND section_4=:section4 AND section_5=:section5)";
    @Query(getSingleSymptomSection05)
    Single<full_repertory> getSingleSymptomSection05 (String section0, String section1, String section2, String section3, String section4, String section5);

    String getSingleSymptomSection06="SELECT DISTINCT * FROM full_repertory WHERE (section_0=:section0 AND section_1=:section1 AND section_2=:section2 " +
            "AND section_3=:section3 AND section_4=:section4 AND section_5=:section5 AND section_6=:section6)";
    @Query(getSingleSymptomSection06)
    Single<full_repertory> getSingleSymptomSection06 (String section0, String section1, String section2, String section3, String section4, String section5, String section6);

    String getSingleSymptomSection07="SELECT DISTINCT * FROM full_repertory WHERE (section_0=:section0 AND section_1=:section1 AND section_2=:section2 " +
            "AND section_3=:section3 AND section_4=:section4 AND section_5=:section5 AND section_6=:section6 AND section_7=:section7)";
    @Query(getSingleSymptomSection07)
    Single<full_repertory> getSingleSymptomSection07 (String section0, String section1, String section2, String section3, String section4, String section5, String section6, String section7);

    String getSingleSymptomById="SELECT * FROM full_repertory WHERE _id=:sympID";
    @Query(getSingleSymptomById)
    Single<full_repertory>  getSingleSymptomById (int sympID);

    String getSympListByListId="SELECT * FROM full_repertory WHERE _id IN (:ArraySympId)";
    @Query(getSympListByListId)
    Single <List<full_repertory>>  getSympListByListId (int [] ArraySympId);
//-------------------Search by words-------------------------------------------
    String searchByOneWord="SELECT * FROM full_repertory WHERE (section_0 LIKE '%'||:word||'%' OR section_1 LIKE '%'||:word||'%' " +
            "OR section_2 LIKE '%'||:word||'%' OR section_3 LIKE '%'||:word||'%' OR section_4 LIKE '%'||:word||'%' OR section_5 LIKE '%'||:word||'%') ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(searchByOneWord)
    Single<List<full_repertory>>  searchByOneWord (String word);

    String searchByTwoWord="SELECT * FROM full_repertory WHERE ((section_0 LIKE '%'||:word1||'%' OR section_1 LIKE '%'||:word1||'%' " +
            "OR section_2 LIKE '%'||:word1||'%' OR section_3 LIKE '%'||:word1||'%'  OR section_4 LIKE '%'||:word1||'%' OR section_5 LIKE '%'||:word1||'%') AND" +
            "(section_0 LIKE '%'||:word2||'%' OR section_1 LIKE '%'||:word2||'%' OR section_2 LIKE '%'||:word2||'%' " +
            "OR section_3 LIKE '%'||:word2||'%'  OR section_4 LIKE '%'||:word2||'%' OR section_5 LIKE '%'||:word2||'%')) ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(searchByTwoWord)
    Single<List<full_repertory>>  searchByTwoWord (String word1, String word2);

    String searchByThreeWord="SELECT * FROM full_repertory WHERE ((section_0 LIKE '%'||:word1||'%' OR section_1 LIKE '%'||:word1||'%' " +
            "OR section_2 LIKE '%'||:word1||'%' OR section_3 LIKE '%'||:word1||'%'  OR section_4 LIKE '%'||:word1||'%' OR section_5 LIKE '%'||:word1||'%') AND" +
            "(section_0 LIKE '%'||:word2||'%' OR section_1 LIKE '%'||:word2||'%' OR section_2 LIKE '%'||:word2||'%' " +
            "OR section_3 LIKE '%'||:word2||'%'  OR section_4 LIKE '%'||:word2||'%' OR section_5 LIKE '%'||:word2||'%') AND " +
            "(section_0 LIKE '%'||:word3||'%' OR section_1 LIKE '%'||:word3||'%' OR section_2 LIKE '%'||:word3||'%' " +
            "OR section_3 LIKE '%'||:word3||'%'  OR section_4 LIKE '%'||:word3||'%' OR section_5 LIKE '%'||:word3||'%')) ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(searchByThreeWord)
    Single<List<full_repertory>>  searchByThreeWord (String word1, String word2, String word3);
//-------------select Symptoms by remedies-------------------------------------------------------------
    String getSympWithRemedy1000="SELECT * FROM full_repertory WHERE ( prp1 LIKE :remedy1 OR prp1 LIKE '%, '||:remedy1||',%' OR prp1 LIKE '%, '||:remedy1 OR prp1 LIKE :remedy1||',%') ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(getSympWithRemedy1000)
    Single<List<full_repertory>>  getSympWithRemedy100(String remedy1);

    String getSympWithRemedy0200="SELECT * FROM full_repertory WHERE (prp2 LIKE :remedy2  OR prp2 LIKE '% ,'||:remedy2||',%' OR prp2 LIKE '%, '||:remedy2 OR prp2 LIKE :remedy2||',%') ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(getSympWithRemedy0200)
    Single<List<full_repertory>>  getSympWithRemedy0200(String remedy2);

    String getSympWithRemedy0030="SELECT * FROM full_repertory WHERE (prp3 LIKE :remedy3 OR prp3 LIKE '%, '||:remedy3||',%' OR prp3 LIKE '%,'||:remedy3 OR prp3 LIKE :remedy3||' ,%') ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(getSympWithRemedy0030)
    Single<List<full_repertory>>  getSympWithRemedy0030(String remedy3);

    String getSympWithRemedy0004="SELECT * FROM full_repertory WHERE (prp4 LIKE :remedy4 OR prp4 LIKE '%, '||:remedy4||',%' OR prp4 LIKE '%,'||:remedy4 OR prp4 LIKE :remedy4||' ,%') ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(getSympWithRemedy0004)
    Single<List<full_repertory>>  getSympWithRemedy0004(String remedy4);

    String getSympWithRemedy1200="SELECT * FROM full_repertory WHERE (" +
            "prp2 LIKE :remedy2  OR prp2 LIKE '% ,'||:remedy2||',%' OR prp2 LIKE '%, '||:remedy2 OR prp2 LIKE :remedy2||',%'" +
            "OR prp1 LIKE :remedy1 OR prp1 LIKE '%, '||:remedy1||',%' OR prp1 LIKE '%, '||:remedy1 OR prp1 LIKE :remedy1||',%') ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(getSympWithRemedy1200)
    Single<List<full_repertory>>  getSympWithRemedy1200(String remedy2, String remedy1);

    String getSympWithRemedy1230="SELECT * FROM full_repertory WHERE (" +
            "prp3 LIKE :remedy3  OR prp3 LIKE '% ,'||:remedy3||',%' OR prp3 LIKE '%, '||:remedy3 OR prp3 LIKE :remedy3||',%'"+
            "OR prp2 LIKE :remedy2  OR prp2 LIKE '% ,'||:remedy2||',%' OR prp2 LIKE '%, '||:remedy2 OR prp2 LIKE :remedy2||',%'" +
            "OR prp1 LIKE :remedy1 OR prp1 LIKE '%, '||:remedy1||',%' OR prp1 LIKE '%, '||:remedy1 OR prp1 LIKE :remedy1||',%') ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(getSympWithRemedy1230)
    Single<List<full_repertory>> getSympWithRemedy1230(String remedy3,String remedy2, String remedy1);

    String getSympWithRemedy0234="SELECT * FROM full_repertory WHERE (" +
            "prp4 LIKE :remedy4 OR prp4 LIKE '%, '||:remedy4||',%' OR prp4 LIKE '%, '||:remedy4 OR prp4 LIKE :remedy4||',%'" +
            "OR prp3 LIKE :remedy3 OR prp3 LIKE '%, '||:remedy3||',%' OR prp3 LIKE '%, '||:remedy3 OR prp3 LIKE :remedy3||',%'" +
            "OR prp2 LIKE :remedy2  OR prp2 LIKE '% ,'||:remedy2||',%' OR prp2 LIKE '%, '||:remedy2 OR prp2 LIKE :remedy2||',%') ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(getSympWithRemedy0234)
    Single<List<full_repertory>> getSympWithRemedy0234(String remedy4, String remedy3, String remedy2);

    String getSympWithRemedy0230="SELECT * FROM full_repertory WHERE (" +
            "prp3 LIKE :remedy3 OR prp3 LIKE '%, '||:remedy3||',%' OR prp3 LIKE '%, '||:remedy3 OR prp3 LIKE :remedy3||',%'" +
            "OR prp2 LIKE :remedy2  OR prp2 LIKE '% ,'||:remedy2||',%' OR prp2 LIKE '%, '||:remedy2 OR prp2 LIKE :remedy2||',%') ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(getSympWithRemedy0230)
    Single<List<full_repertory>> getSympWithRemedy0230(String remedy3, String remedy2);

    String getSympWithRemedy0204="SELECT * FROM full_repertory WHERE (" +
            "prp4 LIKE :remedy4 OR prp4 LIKE '%, '||:remedy4||',%' OR prp4 LIKE '%, '||:remedy4 OR prp4 LIKE :remedy4||',%'" +
            "OR prp2 LIKE :remedy2  OR prp2 LIKE '% ,'||:remedy2||',%' OR prp2 LIKE '%, '||:remedy2 OR prp2 LIKE :remedy2||',%'" +
            ") ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(getSympWithRemedy0204)
    Single<List<full_repertory>>  getSympWithRemedy0204(String remedy4, String remedy2);

    String getSympWithRemedy1004="SELECT * FROM full_repertory WHERE (" +
            "prp4 LIKE :remedy4 OR prp4 LIKE '%, '||:remedy4||',%' OR prp4 LIKE '%, '||:remedy4 OR prp4 LIKE :remedy4||',%'" +
            "OR prp1 LIKE :remedy1 OR prp1 LIKE '%, '||:remedy1||',%' OR prp1 LIKE '%, '||:remedy1 OR prp1 LIKE :remedy1||',%') ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(getSympWithRemedy1004)
    Single<List<full_repertory>>  getSympWithRemedy1004(String remedy4, String remedy1);

    String getSympWithRemedy1030="SELECT * FROM full_repertory WHERE (" +
            "prp3 LIKE :remedy3  OR prp3 LIKE '% ,'||:remedy3||',%' OR prp3 LIKE '%, '||:remedy3 OR prp3 LIKE :remedy3||',%'"+
            "OR prp1 LIKE :remedy1 OR prp1 LIKE '%, '||:remedy1||',%' OR prp1 LIKE '%, '||:remedy1 OR prp1 LIKE :remedy1||',%') ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(getSympWithRemedy1030)
    Single<List<full_repertory>>  getSympWithRemedy1030(String remedy3, String remedy1);

    String getSympWithRemedy1034="SELECT * FROM full_repertory WHERE (" +
            "prp4 LIKE :remedy4 OR prp4 LIKE '%, '||:remedy4||',%' OR prp4 LIKE '%, '||:remedy4 OR prp4 LIKE :remedy4||',%'" +
            "OR prp3 LIKE :remedy3 OR prp3 LIKE '%, '||:remedy3||',%' OR prp3 LIKE '%, '||:remedy3 OR prp3 LIKE :remedy3||',%'" +
            "OR prp1 LIKE :remedy1 OR prp1 LIKE '%, '||:remedy1||',%' OR prp1 LIKE '%, '||:remedy1 OR prp1 LIKE :remedy1||',%') ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(getSympWithRemedy1034)
    Single<List<full_repertory>> getSympWithRemedy1034(String remedy4, String remedy3, String remedy1);

    String getSympWithRemedy1204="SELECT * FROM full_repertory WHERE (" +
            "prp4 LIKE :remedy4 OR prp4 LIKE '%, '||:remedy4||',%' OR prp4 LIKE '%, '||:remedy4 OR prp4 LIKE :remedy4||',%'" +
            "OR prp2 LIKE :remedy2 OR prp3 LIKE '%, '||:remedy2||',%' OR prp3 LIKE '%, '||:remedy2 OR prp3 LIKE :remedy2||',%'" +
            "OR prp1 LIKE :remedy1 OR prp1 LIKE '%, '||:remedy1||',%' OR prp1 LIKE '%, '||:remedy1 OR prp1 LIKE :remedy1||',%') ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(getSympWithRemedy1204)
    Single<List<full_repertory>> getSympWithRemedy1204(String remedy4, String remedy2, String remedy1);


    String getSympWithRemedy1234="SELECT * FROM full_repertory WHERE (" +
            "prp4 LIKE :remedy4 OR prp4 LIKE '%, '||:remedy4||',%' OR prp4 LIKE '%, '||:remedy4 OR prp4 LIKE :remedy4||',%'" +
            "OR prp3 LIKE :remedy3  OR prp3 LIKE '% ,'||:remedy3||',%' OR prp3 LIKE '%, '||:remedy3 OR prp3 LIKE :remedy3||',%'"+
            "OR prp2 LIKE :remedy2  OR prp2 LIKE '% ,'||:remedy2||',%' OR prp2 LIKE '%, '||:remedy2 OR prp2 LIKE :remedy2||',%'" +
            "OR prp1 LIKE :remedy1 OR prp1 LIKE '%, '||:remedy1||',%' OR prp1 LIKE '%, '||:remedy1 OR prp1 LIKE :remedy1||',%') ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(getSympWithRemedy1234)
    Single<List<full_repertory>>  getSympWithRemedy1234(String remedy4, String remedy3, String remedy2, String remedy1);


    String getSymptomsContainsRemedy="SELECT * FROM full_repertory WHERE ((" +
            "prp3 LIKE :remedy3 OR prp3 LIKE '%, '||:remedy3||',%' OR prp3 LIKE '%, '||:remedy3 OR prp3 LIKE :remedy3||',%'" +
            "OR prp2 LIKE :remedy2  OR prp2 LIKE '%, '||:remedy2||',%' OR prp2 LIKE '%, '||:remedy2 OR prp2 LIKE :remedy2||',%'" +
            "OR prp1 LIKE :remedy1 OR prp1 LIKE '%, '||:remedy1||',%' OR prp1 LIKE '%, '||:remedy1 OR prp1 LIKE :remedy1||',%')"+
            "AND _id IN(:id)) ORDER BY section_0, section_1, section_2, section_3, section_5, section_6 ASC";
    @Query(getSymptomsContainsRemedy)
    Single<List<full_repertory>>  getSymptomsContainsRemedy(String remedy3,String remedy2, String remedy1, Integer[]id);

    String deleteSympByPram="DELETE FROM full_repertory WHERE section_0=:section0 AND " +
            "section_1=:section1 AND section_2=:section2 AND section_3=:section3";
    @Query(deleteSympByPram)
    Single<Integer> deleteSympByPram(String section0, String section1, String section2, String section3);

    String deleteSympByListId="DELETE FROM full_repertory WHERE _id IN (:idList)";
    @Query(deleteSympByListId)
    Single<Integer> deleteSympByListId(List<String> idList);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    Single<Integer> update(List<full_repertory> symps);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Single<List<Long>> insert(List<full_repertory> symps);
    @Delete
    public int delete(List<full_repertory> symps);
}

