package com.rcoddev.compiti.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rcoddev.compiti.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskDAO implements ITaskDAO {

    private SQLiteDatabase writer;
    private SQLiteDatabase reader;

    public TaskDAO(Context context) {
        DbHelper db = new DbHelper( context );
        writer = db.getWritableDatabase();
        reader = db.getReadableDatabase();
    }

    @Override
    public boolean create(Task task) {
        ContentValues cv = new ContentValues();
        cv.put("name", task.getName());
        cv.put("annotation", task.getAnnotation());
        cv.put("date", task.getFormattedDate());

        try {
            writer.insert(DbHelper.TASK_TABLE, null, cv);
            Log.i("INFO", "Sucesso ao salvar dado na tabela");

        } catch (Exception e) {
            Log.e("INFO", "Erro ao salvar dado na tabela: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Task task) {
        ContentValues cv = new ContentValues();
        cv.put("name", task.getName());
        cv.put("annotation", task.getAnnotation());
        cv.put("date", task.getFormattedDate());

        try {
            String[] args = {task.getId().toString()};
            writer.update(DbHelper.TASK_TABLE, cv, "id = ?", args);

            Log.i("INFO", "Sucesso ao atualizar dado na tabela");

        } catch (Exception e) {
            Log.e("INFO", "Erro ao atualizar dado na tabela: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Task task) {
        try {
            String[] args = {task.getId().toString()};
            writer.delete(DbHelper.TASK_TABLE, "id = ?", args);
            Log.i("INFO", "Sucesso ao excluir dado na tabela");

        } catch (Exception e) {
            Log.e("INFO", "Erro ao excluir dado na tabela: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Task> read() {
        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TASK_TABLE + " ;";
        Cursor c = reader.rawQuery(sql, null);

        while ( c.moveToNext() ){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int annotaionIndex = c.getColumnIndex("annotation");
            int dateIndex = c.getColumnIndex("date");

            Long id = c.getLong( idIndex );
            String name = c.getString( nameIndex );
            String annotation = c.getString( annotaionIndex );
            String date = c.getString( dateIndex );

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            try {
                Date formattedDate = sdf.parse(date);
                Task task = new Task(id, name, annotation, formattedDate);
                tasks.add(task);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return tasks;
    }
}
