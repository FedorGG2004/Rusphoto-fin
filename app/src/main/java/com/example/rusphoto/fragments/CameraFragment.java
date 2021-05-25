package com.example.rusphoto.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rusphoto.BuildConfig;

import com.example.rusphoto.Speller;
import com.example.rusphoto.databinding.CameraFragmentBinding;
import com.example.rusphoto.tesseract.RequestPermissionsTool;

import com.example.rusphoto.tesseract.RequestPermissionsToolImpl;
import com.googlecode.tesseract.android.TessBaseAPI;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CameraFragment extends Fragment {
    CameraFragmentBinding binding;

    private static final String TAG = CameraFragment.class.getSimpleName();
    static final int PHOTO_REQUEST_CODE = 1;
    private TessBaseAPI tessBaseApi;
    TextView textView;
    Uri outputFileUri;

    String result = "empty";
    private RequestPermissionsTool requestTool;

    private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/TesseractSample/";
    private static final String TESSDATA = "tessdata";

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = CameraFragmentBinding.inflate(inflater, container, false);

        if (binding.actionBtn != null) {
            binding.actionBtn.setOnClickListener(v -> startCameraActivity());
        }

        binding.button3.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Speller.class);
            startActivity(intent);
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
        }
        return binding.getRoot();

    }
    private void startCameraActivity() {
        try {
            String IMGS_PATH = Environment.getExternalStorageDirectory().toString() + "/TesseractSample/imgs";
            System.out.println(IMGS_PATH);
            prepareDirectory(IMGS_PATH);

            String img_path = IMGS_PATH + "/ocr.jpg";

            outputFileUri = FileProvider.getUriForFile(getActivity(),
                    BuildConfig.APPLICATION_ID + ".provider", new File(img_path));
            System.out.println(outputFileUri);

            final Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            startActivityForResult(takePictureIntent, PHOTO_REQUEST_CODE);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK)){
            doOCR();
        }
        else{
            System.out.println("error_request");
        }
    }

    private void doOCR() {
        prepareTesseract();
        startOCR();
    }

    private void prepareDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.e(TAG, "ERROR: Creation of directory " + path + " failed, check does Android Manifest have permission to write to external storage.");
            }
        } else {
            Log.i(TAG, "Created directory " + path);
        }
    }


    private void prepareTesseract() {
        try {
            prepareDirectory(DATA_PATH + TESSDATA);
        } catch (Exception e) {
            e.printStackTrace();
        }

        copyTessDataFiles(TESSDATA);
    }

    private void copyTessDataFiles(String path) {
        try {
            String[] fileList = getResources().getAssets().list(path);

            for (String fileName : fileList) {

                String pathToDataFile = DATA_PATH + path + "/" + fileName;
                if (!(new File(pathToDataFile)).exists()) {

                    InputStream in = getResources().getAssets().open(path + "/" + fileName);

                    OutputStream out = new FileOutputStream(pathToDataFile);

                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to copy files to tessdata " + e.toString());
        }
    }


    private void startOCR() {

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap bitmap = BitmapFactory.decodeFile(DATA_PATH + "imgs" + "/ocr.jpg", options);
            result = extractText(bitmap);

            textView.setText(result);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }


    private String extractText(Bitmap bitmap) {
        try {
            tessBaseApi = new TessBaseAPI();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            if (tessBaseApi == null) {
                Log.e(TAG, "TessBaseAPI is null. TessFactory not returning tess object.");
            }
        }

        tessBaseApi.init(DATA_PATH, "rus");

        tessBaseApi.setImage(bitmap);
        String extractedText = "empty result";
        try {
            extractedText = tessBaseApi.getUTF8Text();
        } catch (Exception e) {
            Log.e(TAG, "Error in recognizing text.");
        }
        tessBaseApi.end();
        return extractedText;
    }


    private void requestPermissions() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestTool = new RequestPermissionsToolImpl();
        requestTool.requestPermissions(getActivity(), permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        boolean grantedAllPermissions = true;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                grantedAllPermissions = false;
            }
        }

        if (grantResults.length != permissions.length || (!grantedAllPermissions)) {

            requestTool.onPermissionDenied();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }
}