package com.test.app.controllers;

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

import com.test.app.Database.Repository;
import com.test.app.Entities.CourseEntity;
import com.test.app.R;

import java.text.SimpleDateFormat;

public class CourseAddActivity extends AppCompatActivity {

    private Button newCourseSaveButton;
    private EditText courseTitleEditText, courseStartDateEditText, courseEndDateEditText,
    instructorNameEditText, instructorPhoneEditText, instructorEmailEditText, courseNoteEditText;
    private TextView seekBarLabel;
    private SeekBar courseProgressSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);


        androidx.appcompat.widget.Toolbar appBar = findViewById(R.id.courseAddAppBar);
        appBar.setTitle(R.string.courseAddLabel);
        setSupportActionBar(appBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        newCourseSaveButton = findViewById(R.id.newCourseSaveButton);
        courseStartDateEditText = (EditText) findViewById(R.id.editTextNewCourseStart);
        courseEndDateEditText = (EditText) findViewById(R.id.editTextAssessmentEnd);
        courseTitleEditText = (EditText) findViewById(R.id.editTextAssessmentTitle);
        instructorNameEditText = (EditText) findViewById(R.id.instructorNameEditText);
        instructorPhoneEditText = (EditText) findViewById(R.id.instructorPhoneEditText);
        instructorEmailEditText = (EditText) findViewById(R.id.instructorEmailEditText);
        courseNoteEditText = (EditText) findViewById(R.id.courseNoteEdit);
        seekBarLabel = findViewById(R.id.seekBarLabel);
        courseProgressSeekBar = findViewById(R.id.progressSeekBar);

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

        newCourseSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseTitle = courseTitleEditText.getText().toString();
                String courseStartDate = courseStartDateEditText.getText().toString();
                String courseEndDate = courseEndDateEditText.getText().toString();
                String instructorName = instructorNameEditText.getText().toString();
                String instructorPhone = instructorPhoneEditText.getText().toString();
                String instructorEmail = instructorEmailEditText.getText().toString();
                String courseNote = courseNoteEditText.getText().toString();
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
                    Repository repository = new Repository(getApplication());
                    repository.insertCourse(new CourseEntity(courseTitle, courseStartDate, courseEndDate, status, instructorName,
                            instructorPhone, instructorEmail, termId, courseNote));
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(CourseAddActivity.this, "Invalid date format, please use MM/DD/YYYY", Toast.LENGTH_LONG).show();
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