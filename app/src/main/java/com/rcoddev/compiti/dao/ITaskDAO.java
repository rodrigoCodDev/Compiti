package com.rcoddev.compiti.dao;

import com.rcoddev.compiti.model.Task;

import java.util.List;

public interface ITaskDAO {
    public boolean create(Task task);
    public boolean update(Task task);
    public boolean delete(Task task);
    public List<Task> read();
}
