package com.kangtong.lingtranslate.ui.translate;

import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.kangtong.lingtranslate.model.BaiduResult;
import com.kangtong.lingtranslate.model.IcibaEnglishResult;
import com.kangtong.lingtranslate.model.YoudaoResult;
import com.kangtong.lingtranslate.model.db.WordDB;
import com.kangtong.lingtranslate.service.APIService;
import com.kangtong.lingtranslate.util.DBUtils;
import com.kangtong.lingtranslate.util.MD5;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TranslateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TranslateFragment extends Fragment {
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.edit_translate) EditText editTranslate;
  Unbinder unbinder;
  @BindView(R.id.text_youdao_translate) TextView textYoudaoTranslate;
  @BindView(R.id.btn_translate) CircularProgressButton btnTranslate;
  @BindView(R.id.card_youdao) CardView cardYoudao;
  @BindView(R.id.btn_youdao_copy) ImageButton btnYoudaoCopy;
  @BindView(R.id.btn_youdao_favorite) MaterialFavoriteButton btnYoudaoFavorite;
  @BindView(R.id.btn_baidu_copy) ImageButton btnBaiduCopy;
  @BindView(R.id.btn_baidu_favorite) MaterialFavoriteButton btnBaiduFavorite;
  @BindView(R.id.text_baidu_translate) TextView textBaiduTranslate;
  @BindView(R.id.card_baidu) CardView cardBaidu;
  @BindView(R.id.btn_iciba_copy) ImageButton btnIcibaCopy;
  @BindView(R.id.btn_iciba_favorite) MaterialFavoriteButton btnIcibaFavorite;
  @BindView(R.id.text_iciba_translate) TextView textIcibaTranslate;
  @BindView(R.id.card_iciba) CardView cardIciba;
  //@BindView(R.id.text_baidu_translate) TextView textBaiduTranslate;
  private WordDB bean;

  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment TranslateFragment.
   */
  public static TranslateFragment newInstance(String param1, String param2) {
    TranslateFragment fragment = new TranslateFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  public TranslateFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_translate, container, false);
    unbinder = ButterKnife.bind(this, view);
    btnBaiduFavorite.setOnFavoriteChangeListener(
        new MaterialFavoriteButton.OnFavoriteChangeListener() {
          @Override
          public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
            if (favorite) {
              if (textBaiduTranslate.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "请先完成翻译~", Toast.LENGTH_SHORT).show();
                btnBaiduFavorite.setFavorite(false);
                return;
              }
              bean = new WordDB(
                  editTranslate.getText().toString(),

                  textBaiduTranslate.getText().toString(),

                  WordDB.KEY_BAIDU
              );
              if (DBUtils.insertIntoNote(bean)) {
                Toast.makeText(getContext(), "已添加至单词本~", Toast.LENGTH_SHORT).show();
              } else {
                Toast.makeText(getContext(), "出现未知错误,请重试( ╯□╰ )", Toast.LENGTH_SHORT).show();
                btnBaiduFavorite.setFavorite(false);
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
    btnIcibaFavorite.setOnFavoriteChangeListener(
        new MaterialFavoriteButton.OnFavoriteChangeListener() {
          @Override
          public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
            if (favorite) {
              if (textIcibaTranslate.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "请先完成翻译~", Toast.LENGTH_SHORT).show();
                btnBaiduFavorite.setFavorite(false);
                return;
              }
              bean = new WordDB(
                  editTranslate.getText().toString(),

                  textIcibaTranslate.getText().toString(),

                  WordDB.KEY_JINSHAN
              );
              if (DBUtils.insertIntoNote(bean)) {
                Toast.makeText(getContext(), "已添加至单词本~", Toast.LENGTH_SHORT).show();
              } else {
                Toast.makeText(getContext(), "出现未知错误,请重试( ╯□╰ )", Toast.LENGTH_SHORT).show();
                btnBaiduFavorite.setFavorite(false);
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
    btnYoudaoFavorite.setOnFavoriteChangeListener(
        new MaterialFavoriteButton.OnFavoriteChangeListener() {
          @Override
          public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
            if (favorite) {
              if (textYoudaoTranslate.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "请先完成翻译~", Toast.LENGTH_SHORT).show();
                btnBaiduFavorite.setFavorite(false);
                return;
              }
              bean = new WordDB(
                  editTranslate.getText().toString(),

                  textYoudaoTranslate.getText().toString(),

                  WordDB.KEY_YOUDAO
              );
              if (DBUtils.insertIntoNote(bean)) {
                Toast.makeText(getContext(), "已添加至单词本~", Toast.LENGTH_SHORT).show();
              } else {
                Toast.makeText(getContext(), "出现未知错误,请重试( ╯□╰ )", Toast.LENGTH_SHORT).show();
                btnBaiduFavorite.setFavorite(false);
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

  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({R.id.btn_translate, R.id.btn_youdao_copy, R.id.btn_baidu_copy, R.id.btn_iciba_copy})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_translate:
        Youdao(editTranslate.getText().toString());
        Iciba(editTranslate.getText().toString());
        Baidu(editTranslate.getText().toString());
        ((InputMethodManager) getContext().getSystemService(
            Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
            getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        break;
      case R.id.btn_youdao_copy:
        ((ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE)).setText(
            textYoudaoTranslate.getText());
        Toast.makeText(getContext(), "复制成功", Toast.LENGTH_LONG).show();
        break;
      case R.id.btn_baidu_copy:
        ((ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE)).setText(
            textBaiduTranslate.getText());
        Toast.makeText(getContext(), "复制成功", Toast.LENGTH_LONG).show();
        break;
      case R.id.btn_iciba_copy:
        ((ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE)).setText(
            textIcibaTranslate.getText());
        Toast.makeText(getContext(), "复制成功", Toast.LENGTH_LONG).show();
        break;
    }
  }

  public interface OnFragmentInteractionListener {
    void onFragmentInteraction(Uri uri);
  }

  private void Youdao(String text) {
    APIService.youdaoService()
        .getYoudao(Constant.YOUDAO_KEY_FROM, Constant.YOUDAO_API_KEY, "data", "json", "1.1", text)
        .enqueue(
            new Callback<YoudaoResult>() {
              @Override
              public void onResponse(Call<YoudaoResult> call, Response<YoudaoResult> response) {
                if (response.body().errorCode == 0) {
                  cardYoudao.setVisibility(View.VISIBLE);
                  StringBuffer translate = new StringBuffer();
                  for (String trans :
                      response.body().translation) {
                    translate.append(trans);
                    translate.append(" ");
                  }
                  textYoudaoTranslate.setText(translate);
                } else {
                  cardYoudao.setVisibility(View.GONE);
                }
              }

              @Override public void onFailure(Call<YoudaoResult> call, Throwable t) {
                cardYoudao.setVisibility(View.GONE);
              }
            });
  }

  private void Baidu(final String text) {
    final String salt = String.valueOf(System.currentTimeMillis());
    final String sign = MD5.md5(Constant.BAIDU_APPID + text + salt + Constant.BAIDU_SECURITY_KEY);
    APIService.baiduService()
        .getBaidu(text, "auto", "en", Constant.BAIDU_APPID, salt, sign)
        .enqueue(
            new Callback<BaiduResult>() {
              @Override
              public void onResponse(Call<BaiduResult> call, Response<BaiduResult> response) {
                if (text.equals(response.body().trans_result.get(0).dst)) {
                  APIService.baiduService()
                      .getBaidu(text, "auto", "zh", Constant.BAIDU_APPID, salt, sign)
                      .enqueue(
                          new Callback<BaiduResult>() {
                            @Override
                            public void onResponse(Call<BaiduResult> call,
                                Response<BaiduResult> response) {
                              cardBaidu.setVisibility(View.VISIBLE);
                              textBaiduTranslate.setText(response.body().trans_result.get(0).dst);
                            }
                            @Override public void onFailure(Call<BaiduResult> call, Throwable t) {
                            }
                          });
                } else {
                  cardBaidu.setVisibility(View.VISIBLE);
                  textBaiduTranslate.setText(response.body().trans_result.get(0).dst);
                }
              }

              @Override public void onFailure(Call<BaiduResult> call, Throwable t) {
                cardBaidu.setVisibility(View.GONE);
              }
            });
  }

  private void Iciba(final String text) {
    APIService.icibaService().getEnglishIciba(text, Constant.ICBC_KEY, "json").enqueue(
        new Callback<IcibaEnglishResult>() {
          @Override
          public void onResponse(Call<IcibaEnglishResult> call,
              Response<IcibaEnglishResult> response) {
            if (response.body().word_name != null) {
              cardIciba.setVisibility(View.VISIBLE);
              StringBuffer explains = new StringBuffer();
              if (response.body().symbols.get(0).parts.get(0).means.get(
                  0) instanceof String) {
                for (IcibaEnglishResult.SymbolsBean.PartsBean parts :
                    response.body().symbols.get(0).parts) {
                  explains.append(parts.part + " ");
                  for (Object s :
                      parts.means) {
                    explains.append(s + ";");
                  }
                  explains.append("\n");
                }
              } else {
                for (IcibaEnglishResult.SymbolsBean.PartsBean parts :
                    response.body().symbols.get(0).parts) {
                  for (Object s :
                      parts.means) {
                    explains.append(
                        ((LinkedTreeMap) s).get("word_mean").toString() + "\n");
                  }
                }
              }
              textIcibaTranslate.setText(explains);
            }
          }

          @Override public void onFailure(Call<IcibaEnglishResult> call, Throwable t) {
            cardIciba.setVisibility(View.GONE);
          }
        });
  }
}
