package com.example.rusphoto;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class myAsyncTask extends AsyncTask<String, Void, String>{
    OkHttpClient client = new OkHttpClient();

    @Override
    protected String doInBackground(String...strings) {
        Request request = new Request.Builder()
            .url(strings[0]+strings[1])
            .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
