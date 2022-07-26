package com.rcoddev.compiti.db.local;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.rcoddev.compiti.db.TaskDataSource;
import com.rcoddev.compiti.model.Task;

import java.util.List;

public class TaskLocalDataSource implements TaskDataSource {

    private TaskDao taskDao;

    public TaskLocalDataSource(Context context) {
        taskDao = new TaskDao( context );
    }
}
