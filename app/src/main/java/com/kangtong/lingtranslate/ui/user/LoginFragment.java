package com.kangtong.lingtranslate.ui.user;

import android.content.Intent;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.kangtong.lingtranslate.R;

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
        break;
      case R.id.sign_in_button:
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        getActivity().startActivity(intent);
        break;
    }
  }
}
