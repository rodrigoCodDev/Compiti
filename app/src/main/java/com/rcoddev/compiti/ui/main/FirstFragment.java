package com.rcoddev.compiti.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.rcoddev.compiti.R;
import com.rcoddev.compiti.adapter.TaskAdapter;
import com.rcoddev.compiti.databinding.FragmentFirstBinding;
import com.rcoddev.compiti.db.TaskDao;
import com.rcoddev.compiti.model.Task;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private RecyclerView recyclerTaskList;
    private TaskAdapter taskAdapter;
    private FirebaseAuth user = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerTaskList = view.findViewById(R.id.recyclerTaskList);
        loadList();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        taskAdapter.reloadList();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void loadList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getContext() );
        recyclerTaskList.setLayoutManager( layoutManager );
        recyclerTaskList.setHasFixedSize(true);
        recyclerTaskList.addItemDecoration( new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        if (user.getCurrentUser() != null) {
            String userId = user.getCurrentUser().getUid();
            TaskDao taskDao = new TaskDao(userId);

            taskAdapter = new TaskAdapter(taskDao);
            recyclerTaskList.setAdapter( taskAdapter );
        }
    }
}