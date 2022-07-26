package com.rcoddev.compiti.db;

import android.content.Context;

import com.rcoddev.compiti.db.TaskDataSource;
import com.rcoddev.compiti.model.Task;

import java.util.List;

public class TaskRepository {
    private Context context;
    private TaskDataSource taskLocalDataSource, taskRemoteDataSource;

    public TaskRepository(Context context, TaskDataSource taskLocalDataSource, TaskDataSource taskRemoteDataSource) {
        this.taskLocalDataSource = taskLocalDataSource;
        this.taskRemoteDataSource = taskRemoteDataSource;
    }
}
