package com.zybooks.bradleyprieskornschedulerapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.bradleyprieskornschedulerapp.Entities.TermEntity;
import com.zybooks.bradleyprieskornschedulerapp.R;
import com.zybooks.bradleyprieskornschedulerapp.controllers.CourseActivity;

import java.util.List;

public class TermRecyclerAdapter extends RecyclerView.Adapter<TermRecyclerAdapter.TermViewHolder> {

    List<TermEntity> termEntities;
    Context context;

    public TermRecyclerAdapter(Context con, List<TermEntity> termEntityList){
        context = con;
        termEntities = termEntityList;

    }

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        holder.termTitleView.setText(termEntities.get(position).getTermTitle());
    }

    @Override
    public int getItemCount() {
        if(termEntities==null){
            return -1;
        }
        else{return termEntities.size();}
    }

    public class TermViewHolder extends RecyclerView.ViewHolder{

        TextView termTitleView;

        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            termTitleView = itemView.findViewById(R.id.termTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAbsoluteAdapterPosition();
                    Intent intent = new Intent(context, CourseActivity.class);
                    intent.putExtra("currentTerm", pos);
                    context.startActivity(intent);
                }
            });
        }
    }
}
