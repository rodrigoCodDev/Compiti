package com.rcoddev.compiti.ui.editor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rcoddev.compiti.R;
import com.rcoddev.compiti.databinding.ActivityTaskEditorBinding;
import com.rcoddev.compiti.db.TaskDao;
import com.rcoddev.compiti.model.Task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskEditorActivity extends AppCompatActivity {

    private Task currentTask;
    private TaskDao taskDao;

    private TextInputEditText textInputName;
    private TextInputEditText textInputAnnotation;

    private DatabaseReference dbReference;
    private FirebaseAuth user = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_editor);

        textInputName = findViewById(R.id.textInputName);
        textInputAnnotation = findViewById(R.id.textInputAnnotation);

        taskDao = new TaskDao(FirebaseAuth.getInstance().getUid());
        dbReference = taskDao.getDbReference();

        this.currentTask = (Task) getIntent().getSerializableExtra("currentTask");

        if (currentTask != null) {
            textInputName.setText(currentTask.getName());
            textInputAnnotation.setText(currentTask.getAnnotation());
        }
    }

    public void saveTask(View view) {
        String name = textInputName.getText().toString();
        String annotation = textInputAnnotation.getText().toString();

        Task myTask = new Task();

        myTask.setName(name);
        myTask.setAnnotation(annotation);
        myTask.setDate(new Date());

        try {
            if (currentTask != null) {
                taskDao.deleteTask(currentTask);
            }

            if (!isUnique(myTask)) {
                return;
            }

            taskDao.insertTask(myTask);

        } catch (Exception e) {
            onDestroy();
        }
    }

    private boolean isUnique(Task task) {
        Map<String, Task> tasksHashMap = new HashMap<>();

        dbReference.get().addOnCompleteListener(dataSnapshotTask -> {
            for (DataSnapshot child : dataSnapshotTask.getResult().getChildren()) {
                Task childTask = child.getValue(Task.class);
                tasksHashMap.put(task.getName(), childTask);
            }
        });

        List<Task> result = tasksHashMap.values()
                .stream()
                .filter(x -> x.getName().equals(task.getName()))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            return true;
        }

        return false;
    }
}