package com.kangtong.lingtranslate.ui.notice;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kangtong.lingtranslate.R;
import com.kangtong.lingtranslate.model.db.WordDB;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

/**
 * 单词本页面
 */
public class WordNoteFragment extends Fragment {

  private static final String ARG_COLUMN_COUNT = "column-count"; // 用以切换list or gird
  private int mColumnCount = 1;
  private OnListFragmentInteractionListener mListener;

  private WordNoteAdapter mAdapter; // 适配器
  private List<WordDB> mWordDBList = new ArrayList<>(); // 送数据库返回的

  public WordNoteFragment() {
  }

  @SuppressWarnings("unused") public static WordNoteFragment newInstance(int columnCount) {
    WordNoteFragment fragment = new WordNoteFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_COLUMN_COUNT, columnCount);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_item_list, container, false);

    // Set the adapter
    if (view instanceof RecyclerView) {
      Context context = view.getContext();
      RecyclerView recyclerView = (RecyclerView) view;
      if (mColumnCount <= 1) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
      } else {
        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
      }
      mAdapter = new WordNoteAdapter(mWordDBList, mListener);
      recyclerView.setAdapter(mAdapter);
    }
    return view;
  }

  @Override public void onResume() {
    super.onResume();
    // 从数据库中异步获取数据
    DataSupport.findAllAsync(WordDB.class).listen(new FindMultiCallback() {
      @Override public <T> void onFinish(List<T> t) {
        mWordDBList = (ArrayList<WordDB>) t;
        mAdapter.addAll(mWordDBList);
        Log.d("dengqi", "LitePal 获取到的数据：" + mWordDBList.toString());
      }
    });
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnListFragmentInteractionListener) {
      mListener = (OnListFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
    }
  }

  @Override public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  public interface OnListFragmentInteractionListener {
    void onListFragmentInteraction(WordDB item);
  }
}
