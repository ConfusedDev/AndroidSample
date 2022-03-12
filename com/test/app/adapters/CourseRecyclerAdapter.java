package com.zybooks.bradleyprieskornschedulerapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.bradleyprieskornschedulerapp.Database.Repository;
import com.zybooks.bradleyprieskornschedulerapp.Entities.CourseEntity;
import com.zybooks.bradleyprieskornschedulerapp.R;
import com.zybooks.bradleyprieskornschedulerapp.controllers.AssessmentActivity;
import com.zybooks.bradleyprieskornschedulerapp.controllers.CourseActivity;

import java.util.List;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.CourseViewHolder> {

    List<CourseEntity> courseEntities;
    Context context;

    public CourseRecyclerAdapter(Context con, List<CourseEntity> courseEntityList){
        context = con;
        courseEntities = courseEntityList;

    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.courseTitleView.setText(courseEntities.get(position).getCourseName());

    }

    @Override
    public int getItemCount() {
        if(courseEntities==null){
            return -1;
        }
        else{return courseEntities.size();}
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder{

        TextView courseTitleView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTitleView = itemView.findViewById(R.id.courseTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String currentCourseName = courseTitleView.getText().toString();
                    Intent intent = new Intent(context, AssessmentActivity.class);
                    intent.putExtra("currentCourseName", currentCourseName);
                    context.startActivity(intent);
                }
            });
        }
    }
}
