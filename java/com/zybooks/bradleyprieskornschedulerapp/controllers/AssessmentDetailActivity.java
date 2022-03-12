package com.zybooks.bradleyprieskornschedulerapp.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zybooks.bradleyprieskornschedulerapp.Database.Repository;
import com.zybooks.bradleyprieskornschedulerapp.Entities.AssessmentEntity;
import com.zybooks.bradleyprieskornschedulerapp.MyReceiver;
import com.zybooks.bradleyprieskornschedulerapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetailActivity extends AppCompatActivity {

    private Button assessmentSaveButton, notifyButton2;
    private EditText assessmentTitleEditText2, assessmentEndEditText2;
    private RadioButton objectiveRadio2, performanceRadio2;
    private RadioGroup buttonGroup2;
    private AssessmentEntity currentAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        androidx.appcompat.widget.Toolbar appBar = findViewById(R.id.assessmentDetailAppBar);
        appBar.setTitle(R.string.assessmentDetailLabel);
        setSupportActionBar(appBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        assessmentSaveButton = findViewById(R.id.assessmentSaveButton);
        notifyButton2 = findViewById(R.id.notifyButton2);
        assessmentTitleEditText2 = findViewById(R.id.editTextAssessmentTitle2);
        assessmentEndEditText2 = findViewById(R.id.editTextAssessmentEnd2);
        objectiveRadio2 = findViewById(R.id.objectiveRadio);
        performanceRadio2 = findViewById(R.id.performanceRadio);

        String currentAssessmentName = getIntent().getStringExtra("currentAssessmentName");
        Repository repository = new Repository(getApplication());
        for(AssessmentEntity a : repository.getAllAssessments()){
            if(a.getAssessmentName().equals(currentAssessmentName)){
                currentAssessment = a;
            }
        }

        assessmentTitleEditText2.setText(currentAssessment.getAssessmentName());
        assessmentEndEditText2.setText(currentAssessment.getEndDate());

        String notificationString2 = currentAssessment.getAssessmentName()+" is due today!";
        notifyButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String assessmentDueDate = currentAssessment.getEndDate();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                Date dueDate = null;
                try {
                    dueDate = simpleDateFormat.parse(assessmentDueDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long dueDateMills = dueDate.getTime();

                Intent intent = new Intent(AssessmentDetailActivity.this, MyReceiver.class);
                intent.putExtra("message", notificationString2);
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetailActivity.this, ++MainActivity.numAlert, intent, 0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, dueDateMills, sender);
            }
        });

        assessmentSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAssessment.setAssessmentName(assessmentTitleEditText2.getText().toString());
                currentAssessment.setEndDate(assessmentEndEditText2.getText().toString());
                if(objectiveRadio2.isActivated()){
                    currentAssessment.setAssessmentType("Objective");
                } else currentAssessment.setAssessmentType("Performance");
                repository.updateAssessment(currentAssessment);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.assessment_details_activity_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteAssessmentButton:
                Repository repository = new Repository(getApplication());
                repository.deleteAssessment(currentAssessment);
                finish();
        } return super.onOptionsItemSelected(item);
    }
}