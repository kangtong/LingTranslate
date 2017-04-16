package com.kangtong.lingtranslate;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.kangtong.lingtranslate.model.db.WordDB;
import com.kangtong.lingtranslate.ui.notice.WordNoteFragment;
import com.kangtong.lingtranslate.ui.translate.MainFragment;
import com.kangtong.lingtranslate.ui.translate.TranslateFragment;

public class MainActivity extends AppCompatActivity implements
    TranslateFragment.OnFragmentInteractionListener,
    WordNoteFragment.OnListFragmentInteractionListener {

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

  // TODO: 2017/4/16 dengqi: 这里最后修改一下，记录上一个 fragment 是哪一个？ 以便恢复，

  // TODO: 2017/4/16 dengqi: 结合上一个 todo，最好不要用replace，用 hide() + show()

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

  @Override public void onListFragmentInteraction(WordDB item) {
    Log.d("dengqi", "点击了" + item.toString());
  }
}
