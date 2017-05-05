package com.kangtong.lingtranslate.ui.translate;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.kangtong.lingtranslate.model.YoudaoResult;
import com.kangtong.lingtranslate.model.db.WordDB;
import com.kangtong.lingtranslate.service.APIService;
import com.kangtong.lingtranslate.service.IcibaService;
import com.kangtong.lingtranslate.util.DBUtils;
import com.kangtong.lingtranslate.util.Player;
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
  @BindView(R.id.text_web) TextView textWeb;
  @BindView(R.id.text_youdao) TextView textYoudao;
  Unbinder unbinder;
  boolean is_chinese;
  IcibaService icibaService;
  @BindView(R.id.btn_iciba_phonetic) ImageButton btnIcibaPhonetic;
  @BindView(R.id.btn_iciba_uk_phonetic) ImageButton btnIcibaUkPhonetic;
  @BindView(R.id.btn_iciba_us_phonetic) ImageButton btnIcibaUsPhonetic;
  String phonetic;
  String ukPhonetic;
  String usPhonetic;
  @BindView(R.id.text_us_phonetic) TextView textUsPhonetic;
  private Player player;
  private WordDB bean;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_iciba, container, false);
    unbinder = ButterKnife.bind(this, view);
    player = new Player();
    icibaService = APIService.icibaService();
    editIcibaTranslate.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().isEmpty()) {
          linearContent.setVisibility(View.GONE);
        } else {
          btnIcibaFavorite.setFavorite(false);
          textExplains.setText("");
        }
      }

      @Override public void afterTextChanged(Editable editable) {

      }
    });
    btnIcibaFavorite.setOnFavoriteChangeListener(
        new MaterialFavoriteButton.OnFavoriteChangeListener() {
          @Override
          public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
            if (favorite) {
              if (textExplains.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "请先完成翻译", Toast.LENGTH_SHORT).show();
                btnIcibaFavorite.setFavorite(false);
              }
              bean = new WordDB(
                  editIcibaTranslate.getText().toString(),

                  textExplains.getText().toString(),

                  WordDB.KEY_JINSHAN
              );
              if (DBUtils.insertIntoNote(bean)) {
                Toast.makeText(getContext(), "已添加至单词本", Toast.LENGTH_SHORT).show();
              } else {
                Toast.makeText(getContext(), "出现未知错误,请重试( ╯□╰ )", Toast.LENGTH_SHORT).show();
                btnIcibaFavorite.setFavorite(false);
              }
            } else {
              if (DBUtils.deleteFromNote(bean.getId())) {
                Toast.makeText(getContext(), "已从单词本成功移除~", Toast.LENGTH_SHORT).show();
              } else {
                Toast.makeText(getContext(), "出现未知错误,请重试( ╯□╰ )", Toast.LENGTH_SHORT).show();
              }
            }
          }
        });
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({R.id.btn_iciba, R.id.btn_iciba_copy, R.id.btn_iciba_phonetic,
      R.id.btn_iciba_uk_phonetic, R.id.btn_iciba_us_phonetic})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_iciba:
        if (!editIcibaTranslate.getText().toString().isEmpty()) {
          Iciba(editIcibaTranslate.getText().toString());
          Youdao(editIcibaTranslate.getText().toString());
          btnIciba.setIndeterminateProgressMode(true);
          btnIciba.setProgress(50);
        }

        break;
      case R.id.btn_iciba_copy:
        ((ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE)).setText(
            textExplains.getText());
        Toast.makeText(getContext(), "复制成功", Toast.LENGTH_LONG).show();
        break;
      case R.id.btn_iciba_phonetic:
        player.playUrl(phonetic);
        break;
      case R.id.btn_iciba_uk_phonetic:
        player.playUrl(ukPhonetic);
        break;
      case R.id.btn_iciba_us_phonetic:
        player.playUrl(usPhonetic);
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

    if (result.word_name != null) {
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
      textExplains.setText(textExplains.getText() + "\n" + explains);
      if (result.symbols.get(0).word_symbol != null) {
        textPhonetic.setText(result.symbols.get(0).word_symbol);
      } else if (result.symbols.get(0).ph_en != null || !result.symbols.get(0).ph_en.isEmpty()) {
        textPhonetic.setText("英："
            + result.symbols.get(0).ph_en);
        textUsPhonetic.setText("美：" + result.symbols.get(0).ph_am);
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
      if (!result.symbols.get(0).ph_am_mp3.isEmpty()) {
        btnIcibaUsPhonetic.setVisibility(View.VISIBLE);
        usPhonetic = result.symbols.get(0).ph_am_mp3;
      } else {
        btnIcibaUsPhonetic.setVisibility(View.GONE);
      }
      if (!result.symbols.get(0).ph_en_mp3.isEmpty()) {
        btnIcibaUkPhonetic.setVisibility(View.VISIBLE);
        ukPhonetic = result.symbols.get(0).ph_en_mp3;
      } else {
        btnIcibaUkPhonetic.setVisibility(View.GONE);
      }
      if (!result.symbols.get(0).ph_tts_mp3.isEmpty()) {
        btnIcibaPhonetic.setVisibility(View.VISIBLE);
        phonetic = result.symbols.get(0).ph_tts_mp3;
      } else {
        btnIcibaPhonetic.setVisibility(View.GONE);
      }
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
                    btnIciba.setProgress(100);
                    new Handler().postDelayed(new Runnable() {
                      @Override public void run() {
                        btnIciba.setProgress(0);
                      }
                    }, 1000);
                  }
                }, 1000);
                linearContent.setVisibility(View.VISIBLE);
                if (response.body().errorCode == 0) {
                  StringBuffer translation = new StringBuffer();
                  for (String translate :
                      response.body().translation) {
                    translation.append(translate);
                  }
                  textYoudao.setText(translation);
                  if (response.body().basic != null) {
                    StringBuffer explain = new StringBuffer();
                    for (String exp : response.body().basic.explains) {
                      explain.append(exp + "\n");
                    }
                    textExplains.setText(textExplains.getText().toString() + "\n" + explain);
                  }

                  if (response.body().web != null) {
                    StringBuffer web = new StringBuffer();
                    for (YoudaoResult.WebBean webBean : response.body().web) {
                      web.append(webBean.key + ":\n");
                      for (String w : webBean.value) {
                        web.append(w + ";");
                      }
                      web.append("\n");
                    }
                    textWeb.setText(web);
                  }
                }
              }

              @Override public void onFailure(Call<YoudaoResult> call, Throwable t) {
                btnIciba.setProgress(-1);
              }
            });
    ((InputMethodManager) getContext().getSystemService(
        Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
        getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
  }
}
