package com.test.app.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.app.Database.Repository;
import com.test.app.Entities.AssessmentEntity;
import com.test.app.Entities.CourseEntity;
import com.test.app.Entities.TermEntity;
import com.test.app.R;

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