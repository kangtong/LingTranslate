package com.kangtong.lingtranslate.ui.translate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.kangtong.lingtranslate.R;
import com.kangtong.lingtranslate.constant.Constant;
import com.kangtong.lingtranslate.model.BaiduResult;
import com.kangtong.lingtranslate.model.IcibaChineseResult;
import com.kangtong.lingtranslate.model.IcibaEnglishResult;
import com.kangtong.lingtranslate.model.IcibaResult;
import com.kangtong.lingtranslate.model.YoudaoResult;
import com.kangtong.lingtranslate.service.APIService;
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
  @BindView(R.id.cardview_youdao) CardView cardviewYoudao;
  Unbinder unbinder;
  @BindView(R.id.text_youdao_translate) TextView textYoudaoTranslate;
  @BindView(R.id.text_youdao_us_phonetic) TextView textYoudaoUsPhonetic;
  @BindView(R.id.text_youdao_uk_phonetic) TextView textYoudaoUkPhonetic;
  @BindView(R.id.text_youdao_explains) TextView textYoudaoExplains;
  @BindView(R.id.text_baidu_translate) TextView textBaiduTranslate;
  @BindView(R.id.cardview_baidu) CardView cardviewBaidu;

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

  @OnClick(R.id.btn_translate) public void onViewClicked() {
    Youdao(editTranslate.getText().toString());
    Baidu(editTranslate.getText().toString());
    Iciba(editTranslate.getText().toString());
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
                  cardviewYoudao.setVisibility(View.VISIBLE);
                  StringBuffer translate = new StringBuffer();
                  for (String trans :
                      response.body().translation) {
                    translate.append(trans);
                    translate.append(" ");
                  }
                  textYoudaoTranslate.setText(translate);
                } else {
                  cardviewYoudao.setVisibility(View.GONE);
                }
              }

              @Override public void onFailure(Call<YoudaoResult> call, Throwable t) {
                cardviewYoudao.setVisibility(View.GONE);
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
                              cardviewBaidu.setVisibility(View.VISIBLE);
                              textBaiduTranslate.setText(response.body().trans_result.get(0).dst);
                            }

                            @Override public void onFailure(Call<BaiduResult> call, Throwable t) {

                            }
                          });
                } else {
                  cardviewBaidu.setVisibility(View.VISIBLE);
                  textBaiduTranslate.setText(response.body().trans_result.get(0).dst);
                }
              }

              @Override public void onFailure(Call<BaiduResult> call, Throwable t) {
                cardviewBaidu.setVisibility(View.GONE);
              }
            });
  }

  private void Iciba(final String text) {
    APIService.icibaService().getIciba(text, Constant.ICBC_KEY, "json").enqueue(
        new Callback<IcibaResult>() {
          @Override public void onResponse(Call<IcibaResult> call, Response<IcibaResult> response) {
            if (response.body().is_CRI == null) {
              APIService.icibaService().getChineseIciba(text, Constant.ICBC_KEY, "json").enqueue(
                  new Callback<IcibaChineseResult>() {
                    @Override
                    public void onResponse(Call<IcibaChineseResult> call,
                        Response<IcibaChineseResult> response) {
                      Log.d("iciba", "chinese" + response.body().word_name);
                    }

                    @Override public void onFailure(Call<IcibaChineseResult> call, Throwable t) {

                    }
                  });
            } else {
              APIService.icibaService().getEnglishIciba(text, Constant.ICBC_KEY, "json").enqueue(
                  new Callback<IcibaEnglishResult>() {
                    @Override
                    public void onResponse(Call<IcibaEnglishResult> call,
                        Response<IcibaEnglishResult> response) {
                      Log.d("iciba", "english" + response.body().word_name);
                    }

                    @Override public void onFailure(Call<IcibaEnglishResult> call, Throwable t) {

                    }
                  });
            }
          }

          @Override public void onFailure(Call<IcibaResult> call, Throwable t) {

          }
        });
  }
}
