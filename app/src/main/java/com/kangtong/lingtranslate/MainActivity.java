package com.kangtong.lingtranslate;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.kangtong.lingtranslate.model.db.WordDB;
import com.kangtong.lingtranslate.ui.notice.WordNoteFragment;
import com.kangtong.lingtranslate.ui.translate.MainFragment;
import com.kangtong.lingtranslate.ui.translate.TranslateFragment;
import com.kangtong.lingtranslate.util.DBUtils;
import com.kangtong.lingtranslate.util.DialogUtils;

public class MainActivity extends AppCompatActivity implements
    TranslateFragment.OnFragmentInteractionListener,
    WordNoteFragment.OnFragmentListener {

  private static final String KEY_Main = "Main";
  private static final String KEY_Note = "Note";

  private String currentPage;
  private MainFragment mainFragment;
  private WordNoteFragment wordNoteFragment;

  @BindView(R.id.frame_container) FrameLayout frameContainer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    // 默认加载
    mainFragment = new MainFragment();
    getSupportFragmentManager().beginTransaction()
        .add(R.id.frame_container, mainFragment, KEY_Main)
        .commit();
    currentPage = KEY_Main;
  }

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
        case R.id.navigation_home:
          if (currentPage.equals(KEY_Main)) {
            return true;
          }
          if (mainFragment == null) {
            mainFragment = new MainFragment();
          }
          if (mainFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                .hide(wordNoteFragment).show(mainFragment)
                .commit();
          } else {
            getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_container, mainFragment, KEY_Main)
                .commit();
          }
          currentPage = KEY_Main;
          return true;
        case R.id.navigation_dashboard:
          if (currentPage.equals(KEY_Note)) {
            return true;
          }
          if (wordNoteFragment == null) {
            wordNoteFragment = new WordNoteFragment();
          }
          if (wordNoteFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                .hide(mainFragment).show(wordNoteFragment)
                .commit();
          } else {
            getSupportFragmentManager().beginTransaction()
                .hide(mainFragment) // 这个一定是已经在加载了的
                .add(R.id.frame_container, wordNoteFragment, KEY_Note)
                .commit();
          }
          wordNoteFragment.onResume(); // 重新提取数据
          currentPage = KEY_Note;
          return true;
      }
      return false;
    }
  };

  @Override public void onFragmentInteraction(Uri uri) {

  }

  @Override public void onClickFragment(WordDB item) {
    Toast.makeText(MainActivity.this, "单击了" + item.toString(), Toast.LENGTH_SHORT).show();
    // TODO: 2017/4/16 dengqi：这个点击跳转详情页，看你自己要怎么处理吧~ 
  }

  @Override public void onLongClickFragment(final WordDB item) {
    DialogUtils.showCommonDialog(MainActivity.this, "提示", "确定要移除改单词吗？", new DialogUtils.CommonCallbackWithCancel() {
      @Override public void onPositive() {
        if (DBUtils.deleteFromNote(item.getId())) {
          Toast.makeText(MainActivity.this, "移除成功~", Toast.LENGTH_SHORT).show();
        }
        wordNoteFragment.onResume(); // 重新提取数据
      }

      @Override public void onNegative() {

      }
    });
  }
}
