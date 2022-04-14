package com.rcoddev.compiti.model;

public class Task {
    private Long id;
    private String name;
    private String text;
    private String date;

    public Task() {}

    public Task(Long id, String name, String text, String date) {
        this.id = id;
        this.name = name;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
