package com.example.rusphoto.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.rusphoto.databinding.StoryFragmentBinding;
import com.example.rusphoto.story.AppDatabase;

import com.example.rusphoto.story.CreateUser;
import com.example.rusphoto.story.User;
import com.example.rusphoto.story.UserAdapter;

import java.util.List;

public class StoryFragment extends Fragment {

    StoryFragmentBinding binding;
    UserAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = StoryFragmentBinding.inflate(inflater, container, false);

        AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();
        List<User> users = db.userDao().getAllUsers();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserAdapter(users);
        binding.recyclerView.setAdapter(adapter);

//        binding.button4.setOnClickListener(v -> {
//            db.userDao().deleteUser(text.get(0));
//            adapter.text.remove(0);
//            adapter.notifyDataSetChanged();
//
//        });

        binding.button.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateUser.class);
            startActivity(intent);
        });

        return binding.getRoot();

    }
}