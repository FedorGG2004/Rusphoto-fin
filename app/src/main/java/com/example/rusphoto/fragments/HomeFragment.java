package com.example.rusphoto.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;

import com.example.rusphoto.databinding.HomeFragmentBinding;



public class HomeFragment extends Fragment {
    HomeFragmentBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = HomeFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
