package com.zybooks.bradleyprieskornschedulerapp.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zybooks.bradleyprieskornschedulerapp.Database.Repository;
import com.zybooks.bradleyprieskornschedulerapp.Entities.AssessmentEntity;
import com.zybooks.bradleyprieskornschedulerapp.Entities.CourseEntity;
import com.zybooks.bradleyprieskornschedulerapp.Entities.TermEntity;
import com.zybooks.bradleyprieskornschedulerapp.R;

public class MainActivity extends AppCompatActivity {

    public static int numAlert;


    private Button enterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Repository repository = new Repository(getApplication());

        enterButton = findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent termIntent = new Intent(MainActivity.this, TermActivity.class);
                startActivity(termIntent);
            }
        });


    }
}