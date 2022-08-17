package com.rcoddev.compiti.model;

import com.orm.SugarRecord;
import com.rcoddev.compiti.util.DateUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Serializable {
    private String name;
    private String annotation;
    private Date date;

    public Task() {}

    public Task(String name, String annotation, Date date) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
