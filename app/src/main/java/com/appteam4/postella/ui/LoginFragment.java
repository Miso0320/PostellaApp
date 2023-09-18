package com.appteam4.postella.ui;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appteam4.postella.R;
import com.appteam4.postella.databinding.FragmentLoginBinding;
import com.appteam4.postella.datastore.AppKeyValueStore;
import com.appteam4.postella.dto.LoginResult;
import com.appteam4.postella.service.ServiceProvider;
import com.appteam4.postella.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private FragmentLoginBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater);
        navController = NavHostFragment.findNavController(this);

        initBtnLogin();

        return binding.getRoot();
    }

    private void initBtnLogin() {
        binding.btnLogin.setOnClickListener(v -> {
            String mid = binding.mid.getText().toString();
            String mpassword = binding.mpassword.getText().toString();

            // UserService 구현 객체 얻기
            UserService userService = ServiceProvider.getUserService(getContext());

            // login 요청 객체 얻기
            Call<LoginResult> call = userService.login(mid, mpassword);

            // API 서버로 요청을 보내고 비동기로 응답 받기
            call.enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                    // 성공적으로 응답이 왔을 때 실행
                    LoginResult loginResult = response.body();
                    if (loginResult.getResult().equals("success")) {
                        Log.i(TAG, "로그인 성공");

                        // 로그인 성공시 us_email us_password 공유 저장소에 저장
                        AppKeyValueStore.put(getContext(), "us_email", loginResult.getUs_email());
                        AppKeyValueStore.put(getContext(), "us_password", loginResult.getUs_password());

                        // 메인 화면 이동
                        navController.navigate(R.id.dest_main);
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                .setTitle("로그인 실패")
                                .setMessage(loginResult.getResult())
                                .setPositiveButton("확인", null)
                                .create();
                        alertDialog.show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResult> call, Throwable t) {
                    // 네트워크 통신이 안될 때 실행 내용
                }
            });
        });
    }
}