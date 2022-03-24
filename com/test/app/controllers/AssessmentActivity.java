package com.test.app.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.test.app.Database.Repository;
import com.test.app.Entities.AssessmentEntity;
import com.test.app.Entities.CourseEntity;
import com.test.app.Entities.TermEntity;
import com.test.app.MyReceiver;
import com.test.app.R;
import com.test.app.adapters.AssessmentRecyclerAdapter;
import com.test.app.adapters.CourseRecyclerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentActivity extends AppCompatActivity {

    private FloatingActionButton assessmentFloatingActionButton;
    private Button editCourseButton, shareNoteButton, notifyButton;
    private RecyclerView assessmentRecyclerView;
    private TextView currentCourseTextView;
    CourseEntity currentCourse;
    private Repository repository;
    private List<CourseEntity> allCourses;
    private List<AssessmentEntity> allAssessments, courseAssessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);


        androidx.appcompat.widget.Toolbar appBar = findViewById(R.id.assessmentAppBar);
        appBar.setTitle(R.string.assessmentLabel);
        setSupportActionBar(appBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        courseAssessments = new ArrayList<>();

        repository = new Repository(getApplication());
        allCourses = repository.getAllCourses();
        for(CourseEntity course: allCourses) {
            if (course.getCourseName().equals(getIntent().getStringExtra("currentCourseName"))) {
                currentCourse = course;
            }
        }
        currentCourseTextView = findViewById(R.id.currentCourseLabel);
        currentCourseTextView.setText(currentCourse.getCourseName());
        editCourseButton = findViewById(R.id.editCourseButton);
        shareNoteButton = findViewById(R.id.shareNoteButton);
        notifyButton = findViewById(R.id.notifyButton);
        allAssessments = repository.getAllAssessments();
        if (allAssessments.size() != 0) {
            courseAssessments = new ArrayList<>();
            for(AssessmentEntity a:allAssessments){
                if(a.getCourseId()==currentCourse.getCourseId()){
                    courseAssessments.add(a);
                }
            }
        }
        assessmentRecyclerView = findViewById(R.id.assessmentRecyclerView);
        AssessmentRecyclerAdapter assessmentRecyclerAdapter = new AssessmentRecyclerAdapter(this, courseAssessments);
        assessmentRecyclerView.setAdapter(assessmentRecyclerAdapter);
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        editCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentActivity.this, CourseEditActivity.class);
                intent.putExtra("currentCourseName", currentCourse.getCourseName());
                startActivity(intent);
            }
        });

        shareNoteButton.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, currentCourse.getNote());
            shareIntent.putExtra(Intent.EXTRA_TITLE, R.string.shareNoteButton);
            shareIntent.setType("text/plain");
            Intent chooserIntent = Intent.createChooser(shareIntent, null);
            startActivity(chooserIntent);
        });

        String notificationMessage = currentCourse.toString();
        notifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDateString = currentCourse.getStartDate();
                String endDateString = currentCourse.getEndDate();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                Date startDate = null;
                Date endDate = null;
                try {
                    startDate = simpleDateFormat.parse(startDateString);
                    endDate = simpleDateFormat.parse(endDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long startDateMills = startDate.getTime();
                Long endDateMills = endDate.getTime();

                Intent intent = new Intent(AssessmentActivity.this, MyReceiver.class);
                intent.putExtra("message", notificationMessage);
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentActivity.this, ++MainActivity.numAlert, intent, 0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, startDateMills, sender);

                Intent intent2 = new Intent(AssessmentActivity.this, MyReceiver.class);
                intent.putExtra("message", notificationMessage);
                PendingIntent sender2 = PendingIntent.getBroadcast(AssessmentActivity.this, ++MainActivity.numAlert, intent2, 0);
                AlarmManager alarmManager2 = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager2.set(AlarmManager.RTC_WAKEUP, endDateMills, sender2);
            }
        });

        assessmentFloatingActionButton = findViewById(R.id.assessmentFloatingActionButton);
        assessmentFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentActivity.this, AssessmentAddActivity.class);
                intent.putExtra("currentCourse", currentCourse.getCourseId());
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.assessment_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteCourseButton:
                repository.deleteCourse(currentCourse);
                finish();
            case R.id.refreshButton:
                allAssessments = repository.getAllAssessments();
                if(allAssessments.size()!=0){
                    courseAssessments = new ArrayList<>();
                    for(AssessmentEntity a:allAssessments){
                        if(a.getCourseId()==currentCourse.getCourseId()){
                            courseAssessments.add(a);
                        }
                    }
                    AssessmentRecyclerAdapter assessmentRecyclerAdapter = new AssessmentRecyclerAdapter(AssessmentActivity.this, courseAssessments);
                    assessmentRecyclerView.setAdapter(assessmentRecyclerAdapter);
                    assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));}
                else{
                    courseAssessments.clear();
                    assessmentRecyclerView.getAdapter().notifyDataSetChanged();
                }

        } return super.onOptionsItemSelected(item);
    }
}