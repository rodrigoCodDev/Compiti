package com.rcoddev.compiti.db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rcoddev.compiti.model.Task;
import com.rcoddev.compiti.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskDao {
    private DatabaseReference dbReference;

    public TaskDao(String userId) {
        FirebaseDatabase instance = FirebaseDatabase.getInstance();

        try {
            instance.setPersistenceEnabled(true);
        } catch (Exception e) {

        }

        dbReference = instance.getReference().child("users").child(userId).child("tasks");
//        dbReference.keepSynced(true);
    }

    public DatabaseReference getDbReference() {
        return dbReference;
    }

    public void insertTask(@NonNull Task task) {
        dbReference.child(task.getName()).setValue(task);
    }

    public void deleteTask(@NonNull Task task) {
        dbReference.child(task.getName()).removeValue();
    }
}
