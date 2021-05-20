package com.example.rusphoto.story;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.rusphoto.databinding.CreateUserBinding;
import com.example.rusphoto.fragments.StoryFragment;

public class CreateUser extends AppCompatActivity {
    private static final String TAG = "CreateUser";
    CreateUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CreateUserBinding.inflate(getLayoutInflater());

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();
        binding.button.setOnClickListener(v -> {
                User user = new User(binding.firstName.getText().toString(), binding.lastName.getText().toString(), binding.email.getText().toString());
                db.userDao().insertAll(user);
                startActivity(new Intent(CreateUser.this, StoryFragment.class));
            });

        setContentView(binding.getRoot());
    }
}
