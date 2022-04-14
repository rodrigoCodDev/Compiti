package com.rcoddev.compiti.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rcoddev.compiti.R;
import com.rcoddev.compiti.adapter.TaskAdapter;
import com.rcoddev.compiti.databinding.FragmentFirstBinding;
import com.rcoddev.compiti.model.Task;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private RecyclerView recyclerTaskList;
    private TaskAdapter taskAdapter;

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
    public void onStart() {
        super.onStart();
        loadList();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadList();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadList() {
        // Test
        List<Task> list = new ArrayList<>();
        list.add(new Task((long) 1, "Task 1", "", "1/2/2022"));
        list.add(new Task((long) 2, "Task 2", "", "2/2/2022"));
        list.add(new Task((long) 3, "Task 3", "", "3/2/2022"));

        taskAdapter = new TaskAdapter(list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getContext() );
        recyclerTaskList.setLayoutManager( layoutManager );
        recyclerTaskList.setHasFixedSize(true);
        recyclerTaskList.addItemDecoration( new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        recyclerTaskList.setAdapter( taskAdapter );
    }
}