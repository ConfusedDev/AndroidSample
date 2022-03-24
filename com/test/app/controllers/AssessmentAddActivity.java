package com.test.app.controllers;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.app.Database.Repository;
import com.test.app.Entities.AssessmentEntity;
import com.test.app.R;

import java.text.SimpleDateFormat;

public class AssessmentAddActivity extends AppCompatActivity {

    private Button newAssessmentSaveButton;
    private EditText assessmentTitleEditText, assessmentEndEditText;
    private RadioButton objectiveRadio, performanceRadio;
    private RadioGroup buttonGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_add);


        androidx.appcompat.widget.Toolbar appBar = findViewById(R.id.assessmentAddAppBar);
        appBar.setTitle(R.string.assessmentAddLabel);
        setSupportActionBar(appBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        assessmentTitleEditText = findViewById(R.id.editTextAssessmentTitle);
        newAssessmentSaveButton = findViewById(R.id.newAssessmentSaveButton);
        assessmentEndEditText = (EditText) findViewById(R.id.editTextAssessmentEnd);
        buttonGroup = findViewById(R.id.radioGroup);
        objectiveRadio = findViewById(R.id.objectiveRadio);
        performanceRadio = findViewById(R.id.performanceRadio);


        newAssessmentSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String assessmentTitle = assessmentTitleEditText.getText().toString();
                String assessmentEndDate = assessmentEndEditText.getText().toString();
                String assessmentType;
                if(objectiveRadio.isActivated()){
                    assessmentType = "Objective";
                } else assessmentType = "Performance";
                int courseId = getIntent().getIntExtra("currentCourse", -1);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                try{
                    simpleDateFormat.parse(assessmentEndDate);
                    Repository repository = new Repository(getApplication());
                    repository.insertAssessment(new AssessmentEntity(assessmentTitle, assessmentType, assessmentEndDate, courseId));
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(AssessmentAddActivity.this, "Invalid date format, please use MM/DD/YYYY", Toast.LENGTH_LONG).show();
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