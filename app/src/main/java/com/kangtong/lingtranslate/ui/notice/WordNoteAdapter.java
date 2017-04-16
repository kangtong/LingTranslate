package com.kangtong.lingtranslate.ui.notice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kangtong.lingtranslate.R;
import com.kangtong.lingtranslate.model.db.WordDB;
import com.kangtong.lingtranslate.ui.notice.WordNoteFragment.OnFragmentListener;
import java.util.List;
import java.util.Locale;

public class WordNoteAdapter
    extends RecyclerView.Adapter<WordNoteAdapter.ViewHolder> {

  private final List<WordDB> mValues;
  private final OnFragmentListener mListener; // 单击事件

  public WordNoteAdapter(List<WordDB> items, OnFragmentListener listener) {
    mValues = items;
    mListener = listener;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_item, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.mItem = mValues.get(position);
    // UUID 太长了，默认显示 position 好了
    // holder.mIdView.setText(mValues.get(position).getId().toString());
    holder.mIdView.setText(String.format(Locale.CHINA, "%d", position + 1));
    holder.mContentView.setText(mValues.get(position).src + " -> " + mValues.get(position).getDst());
    // 单击事件
    holder.mView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (null != mListener) {
          mListener.onClickFragment(holder.mItem);
        }
      }
    });
    // 长按事件
    holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override public boolean onLongClick(View v) {
        if (null != mListener) {
          mListener.onLongClickFragment(holder.mItem);
        }
        return true;
      }
    });
  }

  @Override public int getItemCount() {
    return mValues.size();
  }

  public void addAll(List<WordDB> list) {
    mValues.clear();
    mValues.addAll(list);
    notifyDataSetChanged();
  }

  /* 这边不用解释了，目前只展示源，不显示语种和结果 */
  public class ViewHolder extends RecyclerView.ViewHolder {

    public final View mView;
    public final TextView mIdView;
    public final TextView mContentView;
    public WordDB mItem;

    public ViewHolder(View view) {
      super(view);
      mView = view;
      mIdView = (TextView) view.findViewById(R.id.id);
      mContentView = (TextView) view.findViewById(R.id.content);
    }
  }
}
