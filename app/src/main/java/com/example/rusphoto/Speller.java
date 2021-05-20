package com.example.rusphoto;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rusphoto.databinding.SpellerBinding;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class Speller extends AppCompatActivity{
    SpellerBinding binding;
    String URL = "http://speller.yandex.net/services/spellservice.json/checkText?text=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SpellerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonret.setOnClickListener(v -> {
            Intent intent = new Intent(Speller.this, MainActivity.class);
            startActivity(intent);
        });
        binding.buttontest.setOnClickListener(v -> {
            if (binding.textView1.getText().toString().isEmpty()) {
                FancyToast.makeText(Speller.this, "input word", Toast.LENGTH_SHORT, FancyToast.CONFUSING, false).show();
            } else {
                try {
                    String temp = JsonUtils.getWordFromJson(URL, binding.textView1.getText().toString());
                    if (temp == null) {
                        FancyToast.makeText(Speller.this, "No results", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    } else
                        binding.textView.setText(temp);
                } catch (InterruptedException | ExecutionException | JSONException e) {
                    System.out.println(e);
                }
            }
        });
        setContentView(binding.getRoot());
    }


}