package com.zybooks.bradleyprieskornschedulerapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.zybooks.bradleyprieskornschedulerapp.Entities.AssessmentEntity;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(AssessmentEntity term);

    @Update
    void update(AssessmentEntity term);

    @Delete
    void delete(AssessmentEntity term);

    @Query("DELETE FROM assessment_table")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessment_table ORDER BY assessmentId ASC")
    List<AssessmentEntity> getAllAssessments();
}
