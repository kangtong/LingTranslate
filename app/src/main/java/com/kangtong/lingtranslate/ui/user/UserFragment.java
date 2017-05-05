package com.kangtong.lingtranslate.ui.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.kangtong.lingtranslate.MainActivity;
import com.kangtong.lingtranslate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

  @BindView(R.id.text_user) TextView textUser;
  @BindView(R.id.btn_quit) Button btnQuit;
  Unbinder unbinder;

  public UserFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_user, container, false);
    unbinder = ButterKnife.bind(this, view);
    SharedPreferences login = getActivity().getSharedPreferences("login", 0);
    textUser.setText(login.getString("name", "") + "，欢迎回来");
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick(R.id.btn_quit) public void onViewClicked() {
    SharedPreferences login = getActivity().getSharedPreferences("login", 0);
    SharedPreferences.Editor editor = login.edit();
    editor.clear();
    editor.apply();
    ((MainActivity) getActivity()).loginSuccess(false);
  }
}
