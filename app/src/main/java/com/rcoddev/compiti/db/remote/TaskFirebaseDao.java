package com.rcoddev.compiti.db.remote;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TaskFirebaseDao {
    private DatabaseReference dbReference;

    public TaskFirebaseDao() {
        dbReference = FirebaseDatabase.getInstance().getReference();
    }
}
