package com.test.app.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.test.app.Database.Repository;
import com.test.app.Entities.CourseEntity;
import com.test.app.Entities.TermEntity;
import com.test.app.R;
import com.test.app.adapters.CourseRecyclerAdapter;
import com.test.app.adapters.TermRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TermActivity extends AppCompatActivity {

    private FloatingActionButton termFloatingActionButton;
    private List<TermEntity> allTerms;
    private RecyclerView termRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        androidx.appcompat.widget.Toolbar appBar = findViewById(R.id.termAppBar);
        appBar.setTitle(R.string.termLabel);
        setSupportActionBar(appBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        termFloatingActionButton = findViewById(R.id.termFloatingActionButton);
        termFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermActivity.this, TermAddActivity.class);
                startActivity(intent);
            }
        });

        Repository repository = new Repository(getApplication());
        allTerms = repository.getAllTerms();
        termRecyclerView = findViewById(R.id.termRecyclerView);
        TermRecyclerAdapter termRecyclerAdapter = new TermRecyclerAdapter(this, allTerms);
        termRecyclerView.setAdapter(termRecyclerAdapter);
        termRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.term_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.refreshButton:
                Repository repository = new Repository(getApplication());
                allTerms = repository.getAllTerms();
                if(allTerms.size()!=0){
                    TermRecyclerAdapter termRecyclerAdapter = new TermRecyclerAdapter(this, allTerms);
                    termRecyclerView.setAdapter(termRecyclerAdapter);
                    termRecyclerView.setLayoutManager(new LinearLayoutManager(this));}
                else{
                    termRecyclerView.getAdapter().notifyDataSetChanged();
                }


        } return super.onOptionsItemSelected(item);
    }
}