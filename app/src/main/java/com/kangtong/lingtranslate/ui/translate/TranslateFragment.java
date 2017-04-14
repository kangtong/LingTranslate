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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.kangtong.lingtranslate.R;
import com.kangtong.lingtranslate.model.YoudaoResult;
import com.kangtong.lingtranslate.service.APIService;
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
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.edit_translate) EditText editTranslate;
  @BindView(R.id.cardview_youdao) CardView cardviewYoudao;
  Unbinder unbinder;

  // TODO: Rename and change types of parameters
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
  // TODO: Rename and change types and number of parameters
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

  // TODO: Rename method, update argument and hook method into UI event
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
  }

  public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
  }

  private void Youdao(String text) {
    APIService.youdaoService()
        .getYoudao("nalingTranslate", "226950363", "data", "json", "1.1", text)
        .enqueue(
            new Callback<YoudaoResult>() {
              @Override
              public void onResponse(Call<YoudaoResult> call, Response<YoudaoResult> response) {
                Log.d("youdao", response.body().translation.get(0));
              }

              @Override public void onFailure(Call<YoudaoResult> call, Throwable t) {

              }
            });
  }
}
