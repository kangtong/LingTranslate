package com.kangtong.lingtranslate.ui.translate;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.dd.CircularProgressButton;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.gson.internal.LinkedTreeMap;
import com.kangtong.lingtranslate.R;
import com.kangtong.lingtranslate.constant.Constant;
import com.kangtong.lingtranslate.model.IcibaEnglishResult;
import com.kangtong.lingtranslate.service.APIService;
import com.kangtong.lingtranslate.service.IcibaService;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IcibaFragment extends Fragment {
  @BindView(R.id.edit_iciba_translate) EditText editIcibaTranslate;
  @BindView(R.id.btn_iciba) CircularProgressButton btnIciba;
  @BindView(R.id.btn_iciba_favorite) MaterialFavoriteButton btnIcibaFavorite;
  @BindView(R.id.linear_tool) LinearLayout linearTool;
  @BindView(R.id.text_explains) TextView textExplains;
  @BindView(R.id.text_phonetic) TextView textPhonetic;
  @BindView(R.id.text_exchange) TextView textExchange;
  @BindView(R.id.linear_content) LinearLayout linearContent;
  Unbinder unbinder;
  boolean is_chinese;
  IcibaService icibaService;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_iciba, container, false);
    unbinder = ButterKnife.bind(this, view);
    icibaService = APIService.icibaService();
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({R.id.btn_iciba, R.id.btn_iciba_copy}) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_iciba:
        if (!editIcibaTranslate.getText().toString().isEmpty()) {
          Iciba(editIcibaTranslate.getText().toString());
          btnIciba.setIndeterminateProgressMode(true);
          btnIciba.setProgress(50);
        }

        break;
      case R.id.btn_iciba_copy:
        ((ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE)).setText(
            textExplains.getText());
        Toast.makeText(getContext(), "复制成功", Toast.LENGTH_LONG).show();
        break;
    }
  }

  private void Iciba(final String text) {

    APIService.icibaService().getEnglishIciba(text, Constant.ICBC_KEY, "json").enqueue(
        new Callback<IcibaEnglishResult>() {
          @Override
          public void onResponse(Call<IcibaEnglishResult> call,
              Response<IcibaEnglishResult> response) {
            new Handler().postDelayed(new Runnable() {
              @Override public void run() {
                btnIciba.setProgress(100);
                new Handler().postDelayed(new Runnable() {
                  @Override public void run() {
                    btnIciba.setProgress(0);
                  }
                }, 1000);
              }
            }, 1000);
            icibaEnglish(response.body());
          }

          @Override public void onFailure(Call<IcibaEnglishResult> call, Throwable t) {
            linearContent.setVisibility(View.GONE);
            btnIciba.setProgress(-1);
          }
        });
    ((InputMethodManager) getContext().getSystemService(
        Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
        getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
  }

  private void icibaEnglish(IcibaEnglishResult result) {
    linearContent.setVisibility(View.VISIBLE);
    StringBuffer explains = new StringBuffer();

    if (result.symbols.get(0).parts.get(0).means.get(0) instanceof String) {
      for (IcibaEnglishResult.SymbolsBean.PartsBean parts :
          result.symbols.get(0).parts) {
        explains.append(parts.part + " ");
        for (Object s :
            parts.means) {
          explains.append(s + ";");
        }
        explains.append("\n");
      }
    } else {
      for (IcibaEnglishResult.SymbolsBean.PartsBean parts :
          result.symbols.get(0).parts) {
        for (Object s :
            parts.means) {
          explains.append(((LinkedTreeMap) s).get("word_mean").toString() + "\n");
        }
      }
    }
    textExplains.setText(explains);
    if (result.symbols.get(0).word_symbol != null) {
      textPhonetic.setText(result.symbols.get(0).word_symbol);
    } else if (result.symbols.get(0).ph_en != null || !result.symbols.get(0).ph_en.isEmpty()) {
      textPhonetic.setText("英："
          + result.symbols.get(0).ph_en
          + "      "
          + "美："
          + result.symbols.get(0).ph_am);
    }
    if (result.is_CRI.equals("1")) {
      StringBuffer exchange = new StringBuffer();
      if (result.exchange.word_pl instanceof ArrayList) {
        exchange.append("\n复数：" + ((ArrayList<String>) result.exchange.word_pl).get(0));
      }
      if (result.exchange.word_third instanceof ArrayList) {
        exchange.append("\n第三人称：" + ((ArrayList<String>) result.exchange.word_third).get(0));
      }
      if (result.exchange.word_past instanceof ArrayList) {
        exchange.append("\n过去时：" + ((ArrayList<String>) result.exchange.word_past).get(0));
      }
      if (result.exchange.word_done instanceof ArrayList) {
        exchange.append("\n完成时：" + ((ArrayList<String>) result.exchange.word_done).get(0));
      }
      if (result.exchange.word_ing instanceof ArrayList) {
        exchange.append("\n进行时：" + ((ArrayList<String>) result.exchange.word_ing).get(0));
      }
      if (result.exchange.word_er instanceof ArrayList) {
        exchange.append("\n比较级：" + ((ArrayList<String>) result.exchange.word_er).get(0));
      }
      if (result.exchange.word_est instanceof ArrayList) {
        exchange.append("\n最高级：" + ((ArrayList<String>) result.exchange.word_est).get(0));
      }
      textExchange.setText(exchange);
    }
  }
}
