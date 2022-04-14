package com.rcoddev.compiti.model;

public class Task {
    private Long id;
    private String name;
    private String annotation;
    private String date;

    public Task() {}

    public Task(Long id, String name, String annotation, String date) {
        this.id = id;
        this.name = name;
        this.annotation = annotation;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
