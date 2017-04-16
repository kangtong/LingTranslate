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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.kangtong.lingtranslate.model.BaiduResult;
import com.kangtong.lingtranslate.model.db.WordDB;
import com.kangtong.lingtranslate.service.APIService;
import com.kangtong.lingtranslate.util.DBUtils;
import com.kangtong.lingtranslate.util.MD5;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaiduFragment extends Fragment {

  @BindView(R.id.spinner_from) Spinner spinnerFrom;
  @BindView(R.id.spinner_to) Spinner spinnerTo;
  Unbinder unbinder;
  @BindView(R.id.edit_baidu_translate) EditText editBaiduTranslate;
  @BindView(R.id.ldbtn_baidu) CircularProgressButton ldbtnBaidu;
  @BindView(R.id.text_baidu) TextView textBaidu;
  @BindView(R.id.btn_baidu_favorite) MaterialFavoriteButton btnBaiduFavorite;
  @BindView(R.id.linear_tool) LinearLayout linearTool;

  private WordDB bean;

  public BaiduFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment

    View view = inflater.inflate(R.layout.fragment_baidu, container, false);
    unbinder = ButterKnife.bind(this, view);
    ArrayAdapter<CharSequence> adapterFrom =
        ArrayAdapter.createFromResource(getActivity(), R.array.baidu_from,
            android.R.layout.simple_spinner_item);
    adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerFrom.setAdapter(adapterFrom);
    ArrayAdapter<CharSequence> adapterTo =
        ArrayAdapter.createFromResource(getActivity(), R.array.baidu_translate,
            android.R.layout.simple_spinner_item);
    adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerTo.setAdapter(adapterTo);

    // 需要做一个检查
    editBaiduTranslate.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().isEmpty()) {
          linearTool.setVisibility(View.GONE);
          textBaidu.setVisibility(View.GONE);
        } else {
          btnBaiduFavorite.setFavorite(false);
          textBaidu.setText("");
        }
      }

      @Override public void afterTextChanged(Editable s) {

      }
    });

    btnBaiduFavorite.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
      @Override public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
        if (favorite) {
          if (textBaidu.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "请先完成翻译~", Toast.LENGTH_SHORT).show();
            btnBaiduFavorite.setFavorite(false);
            return;
          }
          bean = new WordDB(
              editBaiduTranslate.getText().toString(),
              spinnerFrom.getSelectedItem().toString(),
              textBaidu.getText().toString(),
              spinnerTo.getSelectedItem().toString(),
              WordDB.KEY_BAIDU);
          if (DBUtils.insertIntoNote(bean)) {
            Toast.makeText(getContext(), "已添加至单词本~", Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(getContext(), "出现未知错误,请重试( ╯□╰ )", Toast.LENGTH_SHORT).show();
            btnBaiduFavorite.setFavorite(false);
          }
        } else {
          // 取消收藏很简单，其实就是把最后一条数据删除掉
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

  private void Baidu(final String text) {
    final String salt = String.valueOf(System.currentTimeMillis());
    final String sign = MD5.md5(Constant.BAIDU_APPID + text + salt + Constant.BAIDU_SECURITY_KEY);
    APIService.baiduService()
        .getBaidu(text, splitSpinner(spinnerFrom.getSelectedItem().toString()),
            splitSpinner(spinnerTo.getSelectedItem().toString()), Constant.BAIDU_APPID, salt, sign)
        .enqueue(
            new Callback<BaiduResult>() {
              @Override
              public void onResponse(Call<BaiduResult> call, Response<BaiduResult> response) {
                new Handler().postDelayed(new Runnable() {
                  @Override public void run() {
                    ldbtnBaidu.setProgress(100);
                    new Handler().postDelayed(new Runnable() {
                      @Override public void run() {
                        ldbtnBaidu.setProgress(0);
                      }
                    }, 1000);
                  }
                }, 1000);
                textBaidu.setText(response.body().trans_result.get(0).dst);
                linearTool.setVisibility(View.VISIBLE);
                textBaidu.setVisibility(View.VISIBLE);
              }

              @Override public void onFailure(Call<BaiduResult> call, Throwable t) {
                ldbtnBaidu.setProgress(-1);
              }
            });
    ((InputMethodManager) getContext().getSystemService(
        Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
        getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
  }

  private String splitSpinner(String spinner) {
    String[] strings;
    strings = spinner.split("\\|");
    return strings[1];
  }

  @OnClick({R.id.ldbtn_baidu, R.id.btn_baidu_copy}) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.ldbtn_baidu:
        if (!editBaiduTranslate.getText().toString().isEmpty()) {
          Baidu(editBaiduTranslate.getText().toString());
          ldbtnBaidu.setIndeterminateProgressMode(true);
          ldbtnBaidu.setProgress(50);
        }
        break;
      case R.id.btn_baidu_copy:
        ((ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE)).setText(
            editBaiduTranslate.getText().toString());
        Toast.makeText(getContext(), "复制成功", Toast.LENGTH_LONG).show();
        break;
    }
  }
}
