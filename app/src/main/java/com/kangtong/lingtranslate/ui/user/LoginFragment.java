package com.kangtong.lingtranslate.ui.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.kangtong.lingtranslate.MainActivity;
import com.kangtong.lingtranslate.R;
import com.kangtong.lingtranslate.model.UserLogin;
import com.kangtong.lingtranslate.service.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

  @BindView(R.id.login_progress) ProgressBar loginProgress;
  @BindView(R.id.username) AutoCompleteTextView username;
  @BindView(R.id.password) EditText password;
  @BindView(R.id.login_in_button) Button loginInButton;
  @BindView(R.id.sign_in_button) Button signInButton;
  @BindView(R.id.email_login_form) LinearLayout emailLoginForm;
  @BindView(R.id.login_form) ScrollView loginForm;
  Unbinder unbinder;

  public LoginFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_login, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({R.id.login_in_button, R.id.sign_in_button}) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.login_in_button:
        login();
        break;
      case R.id.sign_in_button:
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        getActivity().startActivity(intent);
        break;
    }
  }

  private void login() {
    APIService.loginService()
        .getLogin("1d9d9c0ffa39e", username.getText().toString(), password.getText().toString())
        .enqueue(
            new Callback<UserLogin>() {
              @Override public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if (response.body().retCode.equals("200")) {
                  Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
                  SharedPreferences login = getActivity().getSharedPreferences("login", 0);
                  SharedPreferences.Editor editor = login.edit();
                  editor.putString("name", username.getText().toString());
                  editor.putString("uuid", response.body().result.uid);
                  editor.apply();
                  ((MainActivity) getActivity()).loginSuccess(true);
                } else {
                  Toast.makeText(getActivity(), "登录失败，请核对用户信息", Toast.LENGTH_SHORT).show();
                }
              }

              @Override public void onFailure(Call<UserLogin> call, Throwable t) {
                Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
              }
            });
  }
}
