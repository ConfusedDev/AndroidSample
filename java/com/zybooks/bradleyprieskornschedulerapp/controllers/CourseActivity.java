package com.zybooks.bradleyprieskornschedulerapp.controllers;

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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zybooks.bradleyprieskornschedulerapp.Database.Repository;
import com.zybooks.bradleyprieskornschedulerapp.Entities.CourseEntity;
import com.zybooks.bradleyprieskornschedulerapp.Entities.TermEntity;
import com.zybooks.bradleyprieskornschedulerapp.R;
import com.zybooks.bradleyprieskornschedulerapp.adapters.CourseRecyclerAdapter;
import com.zybooks.bradleyprieskornschedulerapp.adapters.TermRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CourseActivity extends AppCompatActivity {

    private EditText termTitleEditText;
    private EditText termStartEditText;
    private EditText termEndEditText;
    private Button termEditSave;
    private Repository repository = new Repository(getApplication());
    private TermEntity selectedTerm;
    private FloatingActionButton courseFloatingActionButton;
    private List<CourseEntity> allCourses, termCourses;
    private RecyclerView courseRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        termCourses = new ArrayList<>();
        allCourses = repository.getAllCourses();

        androidx.appcompat.widget.Toolbar appBar = findViewById(R.id.courseAppBar);
        appBar.setTitle(R.string.courseLabel);
        setSupportActionBar(appBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        termTitleEditText = findViewById(R.id.editTextTermTitleCourseActivity);
        termStartEditText = findViewById(R.id.editTextTermStart);
        termEndEditText = findViewById(R.id.editTextTermEnd);
        termEditSave = findViewById(R.id.editTermSaveButton);

        if(getIntent().getIntExtra("currentTerm", -1)!= -1){
            int currentTerm = getIntent().getIntExtra("currentTerm", -1);
            List<TermEntity> allTerms = repository.getAllTerms();
            selectedTerm = allTerms.get(currentTerm);
            termTitleEditText.setText(selectedTerm.getTermTitle());
            termStartEditText.setText(selectedTerm.getStartDate());
            termEndEditText.setText(selectedTerm.getEndDate());
        }


        Repository repository = new Repository(getApplication());
        allCourses = repository.getAllCourses();
        if(allCourses.size()!=0){
        termCourses = new ArrayList<>();
        for(CourseEntity c:allCourses){
            if(c.getCourseTerm()==selectedTerm.getTermId()){
                termCourses.add(c);
            }
        }}
        courseRecyclerView = findViewById(R.id.courseRecyclerView);
        CourseRecyclerAdapter courseRecyclerAdapter = new CourseRecyclerAdapter(this, termCourses);
        courseRecyclerView.setAdapter(courseRecyclerAdapter);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        courseFloatingActionButton = findViewById(R.id.courseFloatingActionButton);
        courseFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseActivity.this, CourseAddActivity.class);
                Log.d("TERM ID", String.valueOf(selectedTerm.getTermId()));
                intent.putExtra("selectedTerm", selectedTerm.getTermId());
                startActivity(intent);
            }
        });

        termEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTerm.setTermTitle(termTitleEditText.getText().toString());
                selectedTerm.setStartDate(termStartEditText.getText().toString());
                selectedTerm.setEndDate(termEndEditText.getText().toString());
                repository.updateTerm(selectedTerm);
                Intent intent = new Intent(CourseActivity.this, TermActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.course_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteTermButton:
                List<CourseEntity> allCourses = repository.getAllCourses();
                boolean test = false;
                for(CourseEntity courseEntity:allCourses){
                    if(courseEntity.getCourseTerm()== selectedTerm.getTermId()){
                        test=true;
                    }
                }
                if(!test){
                    repository.deleteTerm(repository.getAllTerms().get(getIntent().getIntExtra("currentTerm", -1)));
                    Intent intent = new Intent(this, TermActivity.class);
                    startActivity(intent);
                }
                else{Toast.makeText(this, "Remove Courses From Term First", Toast.LENGTH_LONG).show();}
                return true;
            case R.id.refreshButton:
                allCourses = repository.getAllCourses();
                if(allCourses.size()!=0){
                    termCourses = new ArrayList<>();
                    for(CourseEntity c:allCourses){
                        if(c.getCourseTerm()==selectedTerm.getTermId()){
                            termCourses.add(c);
                        }
                    }
                    CourseRecyclerAdapter courseRecyclerAdapter = new CourseRecyclerAdapter(this, termCourses);
                    courseRecyclerView.setAdapter(courseRecyclerAdapter);
                    courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));}
                else{
                    termCourses.clear();
                    courseRecyclerView.getAdapter().notifyDataSetChanged();
                }

        } return super.onOptionsItemSelected(item);
    }
}