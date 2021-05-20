package com.example.rusphoto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

class JsonUtils {

    static String getWordFromJson(String URL, String input) throws InterruptedException, ExecutionException, JSONException {
            String output = getGSONFromMyAsyncTask(URL, input);
        if(output.equals("[]"))
            return input;
        else {
            JSONArray myValue = new JSONArray(output);
            JSONObject obj = myValue.getJSONObject(0);
            JSONArray ty = obj.getJSONArray("s");
            String word = ty.getString(0);
            return word;
        }
    }
    private static String getGSONFromMyAsyncTask(String URL, String input) throws ExecutionException, InterruptedException, JSONException {
        myAsyncTask asyncTask = new myAsyncTask();
        asyncTask.execute(URL, input);
        return asyncTask.get();
    }
}

