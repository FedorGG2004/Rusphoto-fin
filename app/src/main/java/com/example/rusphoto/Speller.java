package com.example.rusphoto;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.rusphoto.databinding.SpellerBinding;
import com.example.rusphoto.fragments.StoryFragment;
import com.example.rusphoto.story.AppDatabase;
import com.example.rusphoto.story.CreateUser;
import com.example.rusphoto.story.User;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class Speller extends AppCompatActivity {
    SpellerBinding binding;
    String URL = "http://speller.yandex.net/services/spellservice.json/checkText?text=";
    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SpellerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonret.setOnClickListener(v -> {
            Intent intent = new Intent(Speller.this, MainActivity.class);
            startActivity(intent);
        });
        if(isInternetAvailable(this)) {
            binding.buttontest.setOnClickListener(v -> {
                if (binding.textView1.getText().toString().isEmpty()) {
                    FancyToast.makeText(Speller.this, "input word", Toast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
                } else {
                    try {
                        temp = JsonUtils.getWordFromJson(URL, binding.textView1.getText().toString());
                        if (temp == null) {
                            FancyToast.makeText(Speller.this, "No results", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        } else {
                            binding.textView.setText(temp);

                        }
                    } catch (InterruptedException | ExecutionException | JSONException e) {
                        System.out.println(e);
                    }
                }
            });
        }
        else{
            FancyToast.makeText(Speller.this, "Error connection", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        }
        binding.buttonret.setOnClickListener(v -> {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production3")
                    .allowMainThreadQueries()
                    .build();
            User user = new User(temp);
            db.userDao().insertAll(user);
            Intent intent = new Intent(Speller.this, MainActivity.class);
            startActivity(intent);
        });
        setContentView(binding.getRoot());
    }

    public boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;

    }
}