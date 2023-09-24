package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ProdDetailBottomSheetFragment extends BottomSheetDialogFragment {
    public static final String TAG = "ProdDetailBottomSheetFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prod_detail_bottom_sheet, container, false);
    }
}