package com.rcoddev.compiti.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rcoddev.compiti.R;
import com.rcoddev.compiti.db.TaskDao;
import com.rcoddev.compiti.model.Task;
import com.rcoddev.compiti.model.User;
import com.rcoddev.compiti.ui.editor.TaskEditorActivity;
import com.rcoddev.compiti.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private TaskDao taskDao;
    private Map<String, Task> tasksHashMap;
    private DatabaseReference dbReference;

    public TaskAdapter(TaskDao taskDao) {
        this.taskDao = taskDao;
        tasksHashMap = new HashMap<>();
        dbReference = taskDao.getDbReference();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_task_list, parent, false);

        return new TaskViewHolder(taskItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasksHashMap.values().stream().collect(Collectors.toList()).get(position);
        Date date = task.getDate();

        holder.name.setText(task.getName());
        holder.date.setText(DateUtils.formatDate(date));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( view.getContext(), "Edit", Toast.LENGTH_LONG).show();

                Intent intent = new Intent( view.getContext(), TaskEditorActivity.class);
                intent.putExtra("currentTask", task);

                view.getContext().startActivity( intent );
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder( view.getContext() );

                dialog.setTitle("Confirm deletion");
                dialog.setMessage("Do you want to delete the task \"" + task.getName() + "\" ?");

                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskDao.deleteTask(task);
                        reloadList();
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
        return tasksHashMap.size();
    }

    public void reloadList() {
        tasksHashMap.clear();

        dbReference.get().addOnCompleteListener(dataSnapshotTask -> {
            for (DataSnapshot child : dataSnapshotTask.getResult().getChildren()) {
                Task task = child.getValue(Task.class);
                tasksHashMap.put(task.getName(), task);
            }

            notifyDataSetChanged();
        });
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
