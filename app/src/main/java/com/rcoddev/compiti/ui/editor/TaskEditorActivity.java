package com.rcoddev.compiti.ui.editor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.rcoddev.compiti.R;
import com.rcoddev.compiti.databinding.ActivityTaskEditorBinding;
import com.rcoddev.compiti.model.TaskSql;

import java.util.Date;

public class TaskEditorActivity extends AppCompatActivity {

    private ActivityTaskEditorBinding binding;
    private TaskSql currentTaskSql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTaskEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View view = binding.getRoot();

        Long idSelectedTask = (Long) getIntent().getSerializableExtra("idSelectedTask");

        if ( idSelectedTask != null ){
            currentTaskSql = TaskSql.findById(TaskSql.class, idSelectedTask);

            binding.textInputName.setText( currentTaskSql.getName() );
            binding.textInputAnnotation.setText( currentTaskSql.getAnnotation() );
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
                String name = binding.textInputName.getText().toString();
                String annotation = binding.textInputAnnotation.getText().toString();

                TaskSql myTaskSql = new TaskSql();

                if (currentTaskSql != null) {
                    myTaskSql = currentTaskSql;
                }

                myTaskSql.setName(name);
                myTaskSql.setAnnotation(annotation);
                myTaskSql.setDate(new Date());

                try {
                    myTaskSql.save();
                } catch (Exception e) {
                    Log.e("Erro ao salvar", e.getMessage());
                    onDestroy();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}