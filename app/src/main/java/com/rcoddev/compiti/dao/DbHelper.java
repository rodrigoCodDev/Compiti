package com.rcoddev.compiti.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    // Versão do App
    public static int VERSION = 1;
    public static String DB_NAME = "DB_TASKS";
    public static String TASK_TABLE = "tasks";

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TASK_TABLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL UNIQUE," +
                "annotation TEXT NOT NULL," +
                "date TEXT NOT NULL" +
                ");";

        try {
            db.execSQL( sql );
            Log.i("INFO DB", "Sucesso ao criar tabela");
        } catch (Exception e) {
            Log.e("INFO DB", "Erro ao criar a tabela: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TASK_TABLE + " ;";

        try {
            db.execSQL( sql );
            onCreate( db );
            Log.i("INFO DB", "Sucesso ao atualizar app");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao atualizar app" + e.getStackTrace());
        }
    }
}
