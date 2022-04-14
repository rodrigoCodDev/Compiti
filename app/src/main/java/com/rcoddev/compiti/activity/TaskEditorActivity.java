package com.rcoddev.compiti.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.rcoddev.compiti.R;
import com.rcoddev.compiti.dao.TaskDAO;
import com.rcoddev.compiti.databinding.ActivityMainBinding;
import com.rcoddev.compiti.databinding.ActivityTaskEditorBinding;
import com.rcoddev.compiti.model.Task;

import java.util.Date;

public class TaskEditorActivity extends AppCompatActivity {

    private ActivityTaskEditorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTaskEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View view = binding.getRoot();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_task_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.check_task:
                String name = binding.textInputName.getText().toString();
                String annotation = binding.textInputName.getText().toString();

                Task myTask = new Task();
                myTask.setName(name);
                myTask.setAnnotation(annotation);
                myTask.setDate(new Date());

                TaskDAO taskDAO = new TaskDAO(binding.getRoot().getContext());
                taskDAO.create(myTask);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}