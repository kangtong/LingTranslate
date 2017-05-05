package com.kangtong.lingtranslate.ui.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import com.kangtong.lingtranslate.MainActivity;
import com.kangtong.lingtranslate.R;
import com.kangtong.lingtranslate.model.WordBmob;
import com.kangtong.lingtranslate.model.db.WordDB;
import com.kangtong.lingtranslate.util.DBUtils;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

  @BindView(R.id.text_user) TextView textUser;
  @BindView(R.id.btn_quit) Button btnQuit;
  Unbinder unbinder;
  private String username;

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
    username = login.getString("name", "");
    textUser.setText(username + "，欢迎回来");
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({R.id.btn_download, R.id.btn_upload, R.id.btn_quit})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_download:
        download();
        break;
      case R.id.btn_upload:
        update();
        break;
      case R.id.btn_quit:
        SharedPreferences login = getActivity().getSharedPreferences("login", 0);
        SharedPreferences.Editor editor = login.edit();
        editor.clear();
        editor.apply();
        ((MainActivity) getActivity()).loginSuccess(false);
        break;
    }
  }

  private void update() {
    BmobQuery<WordBmob> query = new BmobQuery<WordBmob>();
    query.addWhereEqualTo("uuid", username);
    query.setLimit(50);
    query.findObjects(new FindListener<WordBmob>() {
      @Override public void done(List<WordBmob> list, BmobException e) {
        if (e == null) {
          Toast.makeText(getActivity(), "获取数据成功，开始删除", Toast.LENGTH_SHORT).show();
          List<BmobObject> objects = new ArrayList<BmobObject>();
          for (WordBmob b : list) {
            WordBmob o = new WordBmob();
            o.setObjectId(b.getObjectId());
            objects.add(o);
          }
          new BmobBatch().deleteBatch(objects).doBatch(new QueryListListener<BatchResult>() {
            @Override public void done(List<BatchResult> list, BmobException e) {
              if (e == null) {
                Toast.makeText(getActivity(), "删除旧数据成功，开始上传新数据", Toast.LENGTH_SHORT).show();
                DataSupport.findAllAsync(WordDB.class).listen(new FindMultiCallback() {
                  @Override public <T> void onFinish(List<T> t) {
                    List<WordDB> mWordDBList = (ArrayList<WordDB>) t;
                    List<BmobObject> words = new ArrayList<BmobObject>();
                    for (WordDB worddb : mWordDBList) {
                      WordBmob wordBmob = new WordBmob();
                      wordBmob.setUuid(username);
                      wordBmob.setId(worddb.getId());
                      wordBmob.setSrc(worddb.getSrc());
                      wordBmob.setDst(worddb.getDst());
                      wordBmob.setType(worddb.getType());
                      words.add(wordBmob);
                    }
                    new BmobBatch().insertBatch(words).doBatch(
                        new QueryListListener<BatchResult>() {
                          @Override public void done(List<BatchResult> list, BmobException e) {
                            if (e == null) {
                              Toast.makeText(getActivity(), "批量添加成功，单词本上传完毕", Toast.LENGTH_SHORT)
                                  .show();
                            } else {
                              Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                            }
                          }
                        });
                  }
                });
              } else {
                Toast.makeText(getActivity(), "删除旧数据失败，请查看网络连接", Toast.LENGTH_SHORT).show();
              }
            }
          });
        } else {
          Toast.makeText(getActivity(), "获取数据失败，请查看网络连接", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private void download() {
    BmobQuery<WordBmob> query = new BmobQuery<WordBmob>();
    query.addWhereEqualTo("uuid", username);
    query.setLimit(50);
    query.findObjects(new FindListener<WordBmob>() {
      @Override public void done(List<WordBmob> list, BmobException e) {
        if (e == null) {
          DBUtils.deleteAll();
          for (WordBmob word :
              list) {
            DBUtils.insertIntoNote(new WordDB(word.getSrc(), word.getDst(), word.getType()));
          }
          Toast.makeText(getActivity(), "数据下载成功", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(getActivity(), "数据下载失败，请检查网络连接", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }
}
