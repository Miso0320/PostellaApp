package com.appteam4.postella.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private FragmentLoginBinding binding;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        /*initBtnMain();
        initBtnLogin();*/

        return binding.getRoot();
    }

    /*private void initBtnMain() {
        binding.btnMain.setOnClickListener(v -> {
            navController.navigate(R.id.action_dest_login_to_dest_main);
        });
    }

    private void initBtnLogin() {
        binding.btnLogin.setOnClickListener(v -> {
            navController.popBackStack();
            //navController.popBackStack(R.id.dest_main,false);
        });
    }*/
}