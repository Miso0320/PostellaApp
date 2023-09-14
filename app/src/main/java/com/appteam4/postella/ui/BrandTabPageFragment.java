package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;

public class BrandTabPageFragment extends Fragment {

    private static final String TAG = "brandTabPageFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_brand_tab_page, container, false);
    }
}