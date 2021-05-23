package com.example.rusphoto.story;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.rusphoto.MainActivity;
import com.example.rusphoto.databinding.CreateUserBinding;
import com.example.rusphoto.fragments.StoryFragment;

public class CreateUser extends AppCompatActivity {
    CreateUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = CreateUserBinding.inflate(getLayoutInflater());

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production3")
                .allowMainThreadQueries()
                .build();
        binding.button.setOnClickListener(v -> {
                User user = new User(binding.firstName.getText().toString());
                db.userDao().insertAll(user);
                startActivity(new Intent(CreateUser.this, MainActivity.class));
            });

        setContentView(binding.getRoot());
    }
}
