package com.test.app.Database;

import android.app.Application;

import com.test.app.DAO.AssessmentDAO;
import com.test.app.DAO.CourseDAO;
import com.test.app.DAO.TermDAO;
import com.test.app.Entities.AssessmentEntity;
import com.test.app.Entities.CourseEntity;
import com.test.app.Entities.TermEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private TermDAO termDAO;
    private CourseDAO courseDAO;
    private AssessmentDAO assessmentDAO;

    private List<TermEntity>allTerms;
    private List<CourseEntity>allCourses;
    private List<AssessmentEntity>allAssessments;
    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        termDAO = db.termDAO();
        courseDAO = db.courseDAO();
        assessmentDAO = db.assessmentDAO();
    }

    public List<TermEntity> getAllTerms(){
        databaseExecutor.execute(()->{
            allTerms = termDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allTerms;
    }

    public void insertTerm(TermEntity termEntity){
        databaseExecutor.execute(()->{
            termDAO.insert(termEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateTerm(TermEntity termEntity){
        databaseExecutor.execute(()->{
            termDAO.update(termEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteTerm(TermEntity termEntity){
        databaseExecutor.execute(()->{
            termDAO.delete(termEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllTerms(){
        databaseExecutor.execute(()->{
            termDAO.deleteAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<CourseEntity> getAllCourses(){
        databaseExecutor.execute(()->{
            allCourses = courseDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allCourses;
    }

    public void insertCourse(CourseEntity courseEntity){
        databaseExecutor.execute(()->{
            courseDAO.insert(courseEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateCourse(CourseEntity courseEntity){
        databaseExecutor.execute(()->{
            courseDAO.update(courseEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(CourseEntity courseEntity){
        databaseExecutor.execute(()->{
            courseDAO.delete(courseEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllCourses(){
        databaseExecutor.execute(()->{
            courseDAO.deleteAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<AssessmentEntity> getAllAssessments(){
        databaseExecutor.execute(()->{
            allAssessments = assessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allAssessments;
    }

    public void insertAssessment(AssessmentEntity assessmentEntity){
        databaseExecutor.execute(()->{
            assessmentDAO.insert(assessmentEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateAssessment(AssessmentEntity assessmentEntity){
        databaseExecutor.execute(()->{
            assessmentDAO.update(assessmentEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAssessment(AssessmentEntity assessmentEntity){
        databaseExecutor.execute(()->{
            assessmentDAO.delete(assessmentEntity);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllAssessments(){
        databaseExecutor.execute(()->{
            assessmentDAO.deleteAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
