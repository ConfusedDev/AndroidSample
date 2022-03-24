package com.test.app.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.app.Database.Repository;
import com.test.app.Entities.TermEntity;
import com.test.app.R;

import java.text.SimpleDateFormat;

public class TermAddActivity extends AppCompatActivity {

    private Button newTermSaveButton;
    private EditText termTitleEditText, termStartDateEditText, termEndDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_add);


        androidx.appcompat.widget.Toolbar appBar = findViewById(R.id.courseAddAppBar);
        appBar.setTitle(R.string.termAddLabel);
        setSupportActionBar(appBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        newTermSaveButton = findViewById(R.id.newCourseSaveButton);
        termStartDateEditText = (EditText) findViewById(R.id.editTextNewCourseStart);
        termEndDateEditText = (EditText) findViewById(R.id.editTextAssessmentEnd);
        termTitleEditText = (EditText) findViewById(R.id.editTextAssessmentTitle);


        newTermSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String termTitle = termTitleEditText.getText().toString();
                String termStartDate = termStartDateEditText.getText().toString();
                String termEndDate = termEndDateEditText.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                try{
                    simpleDateFormat.parse(termStartDate);
                    simpleDateFormat.parse(termEndDate);
                    Repository repository = new Repository(getApplication());
                    repository.insertTerm(new TermEntity(termTitle, termStartDate, termEndDate));
                    Intent intent = new Intent(TermAddActivity.this, TermActivity.class);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(TermAddActivity.this, "Invalid date format, please use MM/DD/YYYY", Toast.LENGTH_LONG).show();
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