package com.example.tt.rasp;

/**
 * Created by tt on 20.09.17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Lesson> lessons;

    DataAdapter(Context context, List<Lesson> lessons) {
        this.lessons = lessons;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.subjectView.setText(lesson.getSubject());
        holder.timeView.setText(lesson.getTime());
        holder.classroomView.setText(lesson.getClassroom());
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView subjectView;
        final TextView timeView, classroomView;
        ViewHolder(View view){
            super(view);
            subjectView = (TextView) view.findViewById(R.id.subject);
            timeView = (TextView) view.findViewById(R.id.time);
            classroomView = (TextView) view.findViewById(R.id.classroom);
        }
    }
}
