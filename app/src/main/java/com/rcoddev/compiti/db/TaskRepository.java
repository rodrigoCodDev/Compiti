package com.rcoddev.compiti.db;

import android.content.Context;

public class TaskRepository {
    private Context context;
    private TaskDataSource taskLocalDataSource, taskRemoteDataSource;

    public TaskRepository(Context context, TaskDataSource taskLocalDataSource, TaskDataSource taskRemoteDataSource) {
        this.taskLocalDataSource = taskLocalDataSource;
        this.taskRemoteDataSource = taskRemoteDataSource;
    }
}
