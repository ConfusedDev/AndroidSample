package com.zybooks.bradleyprieskornschedulerapp.controllers;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.bradleyprieskornschedulerapp.Database.Repository;
import com.zybooks.bradleyprieskornschedulerapp.Entities.CourseEntity;
import com.zybooks.bradleyprieskornschedulerapp.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class CourseEditActivity extends AppCompatActivity {

    private Button editCourseSaveButton;
    private EditText courseTitleEditText, courseStartDateEditText, courseEndDateEditText,
    instructorNameEditText, instructorPhoneEditText, instructorEmailEditText, courseNoteEditText;
    private TextView seekBarLabel;
    private SeekBar courseProgressSeekBar;
    private CourseEntity courseToEdit;
    private Repository repository;
    private List<CourseEntity> allCourses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);


        androidx.appcompat.widget.Toolbar appBar = findViewById(R.id.courseEditAppBar);
        appBar.setTitle(R.string.courseEditLabel);
        setSupportActionBar(appBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        repository = new Repository(getApplication());
        allCourses = repository.getAllCourses();
        for(CourseEntity c: allCourses){
            if(c.getCourseName().equals(getIntent().getStringExtra("currentCourseName")))
                courseToEdit = c;
        }

        editCourseSaveButton = findViewById(R.id.editCourseSaveButton);
        courseStartDateEditText = (EditText) findViewById(R.id.editTextCourseStart);
        courseEndDateEditText = (EditText) findViewById(R.id.editTextCourseEnd);
        courseTitleEditText = (EditText) findViewById(R.id.editTextEditCourseTitle);
        instructorNameEditText = (EditText) findViewById(R.id.instructorNameEdit);
        instructorPhoneEditText = (EditText) findViewById(R.id.instructorPhoneEdit);
        instructorEmailEditText = (EditText) findViewById(R.id.instructorEmailEdit);
        courseNoteEditText = (EditText) findViewById(R.id.courseNoteEdit2);
        seekBarLabel = findViewById(R.id.seekBarLabel);
        courseProgressSeekBar = findViewById(R.id.progressSeekBar);

        courseStartDateEditText.setText(courseToEdit.getStartDate());
        courseEndDateEditText.setText(courseToEdit.getEndDate());
        courseTitleEditText.setText(courseToEdit.getCourseName());
        instructorNameEditText.setText(courseToEdit.getCourseInstructorName());
        instructorPhoneEditText.setText(courseToEdit.getCourseInstructorPhone());
        instructorEmailEditText.setText(courseToEdit.getCourseInstructorEmail());
        courseNoteEditText.setText(courseToEdit.getNote());
        if(courseToEdit.getStatus().equals("Planned"))courseProgressSeekBar.setProgress(0);
        else if (courseToEdit.getStatus().equals("Dropped"))courseProgressSeekBar.setProgress(1);
        else if (courseToEdit.getStatus().equals("In Progress"))courseProgressSeekBar.setProgress(2);
        else courseProgressSeekBar.setProgress(3);

        courseProgressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String newText;
                if(progress == 1)newText = "Dropped";
                else if (progress == 2)newText = "In Progress";
                else if(progress == 3)newText = "Completed";
                else newText = "Planned";
                seekBarLabel.setText(newText);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(seekBar.getProgress()==0){
                    seekBarLabel.setText("Planned");
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        editCourseSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseTitle = courseTitleEditText.getText().toString();
                String courseStartDate = courseStartDateEditText.getText().toString();
                String courseEndDate = courseEndDateEditText.getText().toString();
                String instructorName = instructorNameEditText.getText().toString();
                String instructorPhone = instructorPhoneEditText.getText().toString();
                String instructorEmail = instructorEmailEditText.getText().toString();
                String note = courseNoteEditText.getText().toString();
                int termId = getIntent().getIntExtra("selectedTerm", -1);
                Log.d("TERM ID", String.valueOf(termId));
                String status;
                if(courseProgressSeekBar.getProgress() == 1)status = "Dropped";
                else if (courseProgressSeekBar.getProgress() == 2)status = "In Progress";
                else if(courseProgressSeekBar.getProgress() == 3)status = "Completed";
                else status = "Planned";

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                try{
                    simpleDateFormat.parse(courseStartDate);
                    simpleDateFormat.parse(courseEndDate);
                    courseToEdit.setCourseName(courseTitle);
                    courseToEdit.setStartDate(courseStartDate);
                    courseToEdit.setEndDate(courseEndDate);
                    courseToEdit.setCourseInstructorName(instructorName);
                    courseToEdit.setCourseInstructorPhone(instructorPhone);
                    courseToEdit.setCourseInstructorEmail(instructorEmail);
                    courseToEdit.setStatus(status);
                    courseToEdit.setNote(note);
                    repository.updateCourse(courseToEdit);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(CourseEditActivity.this, "Invalid date format, please use MM/DD/YYYY", Toast.LENGTH_LONG).show();
                }

            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        } return super.onOptionsItemSelected(item);
    }
}