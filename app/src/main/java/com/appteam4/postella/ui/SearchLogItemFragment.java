package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentSearchBinding;
import com.appteam4.postella.databinding.SearchLogItemBinding;
import com.appteam4.postella.datastore.AppKeyValueStore;

public class SearchLogItemFragment extends Fragment {
    private static final String TAG = "SearchLogItemFragment";
    private SearchLogItemBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = SearchLogItemBinding.inflate(inflater);
        binding.btnSearchLog.setOnClickListener(v->{
            Log.i(TAG, "onCreateView: 실행");
            AppKeyValueStore.removeRecentSearchKeyword(requireContext(), (String) binding.btnSearchLog.getText());
        });
        return binding.getRoot();
    }

}