package com.rcoddev.compiti.ui.editor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.rcoddev.compiti.R;
import com.rcoddev.compiti.db.local.TaskDao;
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
                TaskDao taskDao = new TaskDao(binding.getRoot().getContext());

                String name = binding.textInputName.getText().toString();
                String annotation = binding.textInputAnnotation.getText().toString();

                Task myTask = new Task();
                myTask.setName(name);
                myTask.setAnnotation(annotation);
                myTask.setDate(new Date());

                if (currentTask != null) {
                    myTask.setId(currentTask.getId());
                    taskDao.update(myTask);

                } else {
                    taskDao.create(myTask);

                    List<Task> tasks = taskDao.read();
                    currentTask = tasks.get(tasks.size() - 1);
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}