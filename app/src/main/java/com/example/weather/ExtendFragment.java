package com.example.weather;
// Created By Callum Macleod Murdoch - S1828149
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;


public class ExtendFragment extends Fragment {
    @Override
    //Inflates the extend fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.extend_frag, container, false);
    }
}


