package com.rcoddev.compiti.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.rcoddev.compiti.R;
import com.rcoddev.compiti.model.TaskSql;
import com.rcoddev.compiti.ui.editor.TaskEditorActivity;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<TaskSql> taskSqlList;

    public TaskAdapter(List<TaskSql> taskSqlList) {
        this.taskSqlList = taskSqlList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_task_list, parent, false);

        return new TaskViewHolder(taskItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskSql taskSql = taskSqlList.get(position);

        holder.name.setText(taskSql.getName());
        holder.date.setText(taskSql.getFormattedDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( view.getContext(), "Edit", Toast.LENGTH_LONG).show();

                Intent intent = new Intent( view.getContext(), TaskEditorActivity.class);
                intent.putExtra("idSelectedTask", taskSql.getId());

                view.getContext().startActivity( intent );
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder( view.getContext() );

                dialog.setTitle("Confirm deletion");
                dialog.setMessage("Do you want to delete the task \"" + taskSql.getName() + "\" ?");

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskSql.delete();

                        taskSqlList = TaskSql.listAll(TaskSql.class);
                        notifyDataSetChanged();
                    }
                });

                dialog.setNegativeButton("No", null);

                dialog.create();
                dialog.show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskSqlList.size();
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
