package com.kangtong.lingtranslate.ui.translate;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.dd.CircularProgressButton;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.kangtong.lingtranslate.R;
import com.kangtong.lingtranslate.constant.Constant;
import com.kangtong.lingtranslate.model.YoudaoResult;
import com.kangtong.lingtranslate.service.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YoudaoFragment extends Fragment {

  @BindView(R.id.edit_youdao_translate) EditText editYoudaoTranslate;
  @BindView(R.id.btn_youdao_favorite) MaterialFavoriteButton btnYoudaoFavorite;
  @BindView(R.id.linear_tool) LinearLayout linearTool;
  @BindView(R.id.text_youdao) TextView textYoudao;
  @BindView(R.id.text_explains) TextView textExplains;
  @BindView(R.id.text_phonetic) TextView textPhonetic;
  @BindView(R.id.text_web) TextView textWeb;
  @BindView(R.id.list_youdao) ListView listYoudao;
  @BindView(R.id.btn_youdao) CircularProgressButton btnYoudao;
  Unbinder unbinder;

  public YoudaoFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_youdao, container, false);
    unbinder = ButterKnife.bind(this, view);
    btnYoudaoFavorite.setOnFavoriteChangeListener(
        new MaterialFavoriteButton.OnFavoriteChangeListener() {
          @Override
          public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
            if (favorite) {
              // TODO: 2017/4/16 收藏
            } else {
              // TODO: 2017/4/16 取消收藏
            }
          }
        });
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({R.id.btn_youdao, R.id.btn_youdao_copy}) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_youdao:
        if (!editYoudaoTranslate.getText().toString().isEmpty()) {
          Youdao(editYoudaoTranslate.getText().toString());
          btnYoudao.setIndeterminateProgressMode(true);
          btnYoudao.setProgress(50);
        }
        break;
      case R.id.btn_youdao_copy:
        ((ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE)).setText(
            editYoudaoTranslate.getText().toString());
        Toast.makeText(getContext(), "复制成功", Toast.LENGTH_LONG).show();
        break;
    }
  }

  private void Youdao(String text) {
    APIService.youdaoService()
        .getYoudao(Constant.YOUDAO_KEY_FROM, Constant.YOUDAO_API_KEY, "data", "json", "1.1", text)
        .enqueue(
            new Callback<YoudaoResult>() {
              @Override
              public void onResponse(Call<YoudaoResult> call, Response<YoudaoResult> response) {
                new Handler().postDelayed(new Runnable() {
                  @Override public void run() {
                    btnYoudao.setProgress(100);
                    new Handler().postDelayed(new Runnable() {
                      @Override public void run() {
                        btnYoudao.setProgress(0);
                      }
                    }, 1000);
                  }
                }, 1000);
                if (response.body().errorCode == 0) {
                  StringBuffer translation = new StringBuffer();
                  for (String translate :
                      response.body().translation) {
                    translation.append(translate + "\n");
                  }
                  textYoudao.setText(translation);
                  StringBuffer explain = new StringBuffer();
                  for (String exp : response.body().basic.explains) {
                    explain.append(exp + "\n");
                  }
                  textExplains.setText(explain);
                } else {
                }
              }

              @Override public void onFailure(Call<YoudaoResult> call, Throwable t) {
                btnYoudao.setProgress(-1);
              }
            });
  }
}
