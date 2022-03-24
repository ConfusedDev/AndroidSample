package com.test.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.app.Entities.AssessmentEntity;
import com.test.app.Entities.CourseEntity;
import com.test.app.R;
import com.test.app.controllers.AssessmentActivity;
import com.test.app.controllers.AssessmentDetailActivity;

import java.util.List;

public class AssessmentRecyclerAdapter extends RecyclerView.Adapter<AssessmentRecyclerAdapter.AssessmentViewHolder> {

    List<AssessmentEntity> assessmentEntities;
    Context context;

    public AssessmentRecyclerAdapter(Context con, List<AssessmentEntity> assessmentEntityList){
        context = con;
        assessmentEntities = assessmentEntityList;

    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        holder.assessmentTitleView.setText(assessmentEntities.get(position).getAssessmentName());

    }

    @Override
    public int getItemCount() {
        if(assessmentEntities==null){
            return -1;
        }
        else{return assessmentEntities.size();}
    }

    public class AssessmentViewHolder extends RecyclerView.ViewHolder{

        TextView assessmentTitleView;

        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assessmentTitleView = itemView.findViewById(R.id.assessmentTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String currentAssessmentName = assessmentTitleView.getText().toString();
                    Intent intent = new Intent(context, AssessmentDetailActivity.class);
                    intent.putExtra("currentAssessmentName", currentAssessmentName);
                    context.startActivity(intent);
                }
            });
        }
    }
}
