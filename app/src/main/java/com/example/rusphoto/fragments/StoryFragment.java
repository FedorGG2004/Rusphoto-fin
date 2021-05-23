package com.example.rusphoto.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.rusphoto.databinding.StoryFragmentBinding;
import com.example.rusphoto.story.AppDatabase;

import com.example.rusphoto.story.CreateUser;
import com.example.rusphoto.story.User;
import com.example.rusphoto.story.UserAdapter;
import com.google.common.collect.Lists;

import java.util.List;

public class StoryFragment extends Fragment {

    StoryFragmentBinding binding;
    UserAdapter adapter;
    AppDatabase db;
    List<User> users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = StoryFragmentBinding.inflate(inflater, container, false);

        db = Room.databaseBuilder(getActivity(), AppDatabase.class, "production3")
                .allowMainThreadQueries()
                .build();
        users = db.userDao().getAllUsers();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        users = Lists.reverse(users);
        adapter = new UserAdapter(users, onDeleteTextListener);
        binding.recyclerView.setAdapter(adapter);


        binding.button.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreateUser.class);
            startActivity(intent);
        });

        return binding.getRoot();
    }

    UserAdapter.OnDeleteTextListener onDeleteTextListener = new UserAdapter.OnDeleteTextListener() {
        @Override
        public void onDelete(int position) {
            db.userDao().deleteUser(users.get(position));
            adapter.users.remove(position);
            adapter.notifyDataSetChanged();
        }
    };
}