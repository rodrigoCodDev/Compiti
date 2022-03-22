package com.rcoddev.compiti.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rcoddev.compiti.R;
import com.rcoddev.compiti.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_task_list, parent, false);

        return new TaskViewHolder(taskItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.name.setText(task.getName());
        holder.date.setText(task.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( view.getContext(), "Click", Toast.LENGTH_LONG).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText( view.getContext(), "Long Click", Toast.LENGTH_LONG).show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView date;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textTaskName);
            date = itemView.findViewById(R.id.textTaskDate);
        }
    }
}
