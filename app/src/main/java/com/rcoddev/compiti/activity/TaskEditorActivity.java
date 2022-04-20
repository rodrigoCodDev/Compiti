package com.rcoddev.compiti.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.rcoddev.compiti.R;
import com.rcoddev.compiti.dao.TaskDAO;
import com.rcoddev.compiti.databinding.ActivityMainBinding;
import com.rcoddev.compiti.databinding.ActivityTaskEditorBinding;
import com.rcoddev.compiti.model.Task;

import java.util.Date;
import java.util.List;

public class TaskEditorActivity extends AppCompatActivity {

    private ActivityTaskEditorBinding binding;
    private Task currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTaskEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View view = binding.getRoot();

        currentTask = (Task) getIntent().getSerializableExtra("selectedTask");

        if ( currentTask != null ){
            binding.textInputName.setText( currentTask.getName() );
            binding.textInputAnnotation.setText( currentTask.getAnnotation() );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_task_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check_task:
                TaskDAO taskDAO = new TaskDAO(binding.getRoot().getContext());

                String name = binding.textInputName.getText().toString();
                String annotation = binding.textInputAnnotation.getText().toString();

                Task myTask = new Task();
                myTask.setName(name);
                myTask.setAnnotation(annotation);
                myTask.setDate(new Date());

                if (currentTask != null) {
                    myTask.setId(currentTask.getId());
                    taskDAO.update(myTask);

                } else {
                    taskDAO.create(myTask);

                    List<Task> tasks = taskDAO.read();
                    currentTask = tasks.get(tasks.size() - 1);
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}