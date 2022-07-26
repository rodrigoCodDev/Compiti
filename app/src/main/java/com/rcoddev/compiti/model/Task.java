package com.rcoddev.compiti.model;

import com.orm.SugarRecord;
import com.rcoddev.compiti.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private String name;
    private String annotation;
    private String date;

    public Task() {}

    public Task(TaskSql taskSql) {
        this.name = taskSql.getName();
        this.annotation = taskSql.getAnnotation();
        this.date = DateUtils.formatDate(taskSql.getDate());
    }

    public Task(String name, String annotation, String date) {
        this.name = name;
        this.annotation = annotation;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
