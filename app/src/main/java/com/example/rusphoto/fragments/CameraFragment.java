package com.example.rusphoto.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rusphoto.R;
import com.example.rusphoto.Speller;
import com.example.rusphoto.databinding.CameraFragmentBinding;
import com.example.rusphoto.tesseract.RequestPermissionsTool;
import com.example.rusphoto.tesseract.Tesseract;
import com.googlecode.tesseract.android.TessBaseAPI;

public class CameraFragment extends Fragment {
    CameraFragmentBinding binding;

//    private static final String TAG = Tesseract.class.getSimpleName();
//    static final int PHOTO_REQUEST_CODE = 1;
//    private TessBaseAPI tessBaseApi;
//    TextView textView;
//    Uri outputFileUri;
//    private static final String lang = "rus";
//    String result = "empty";
//    private RequestPermissionsTool requestTool; //for API >=23 only
//
//    private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/TesseractSample/";
//    private static final String TESSDATA = "tessdata";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = CameraFragmentBinding.inflate(inflater, container, false);
//
//        Button captureImg = (Button) findViewById(R.id.action_btn);
//        if (captureImg != null) {
//            captureImg.setOnClickListener(v -> startCameraActivity());
//        }
//
//        binding.button3.setOnClickListener(v -> {
//            Intent intent = new Intent(Tesseract.this, Speller.class);
//            startActivity(intent);
//        });
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions();
//        }
          return binding.getRoot();
    }
}