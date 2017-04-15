package com.kangtong.lingtranslate;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.kangtong.lingtranslate.ui.notice.ItemFragment;
import com.kangtong.lingtranslate.ui.notice.dummy.DummyContent;
import com.kangtong.lingtranslate.ui.translate.MainFragment;
import com.kangtong.lingtranslate.ui.translate.TranslateFragment;

public class MainActivity extends AppCompatActivity implements
    TranslateFragment.OnFragmentInteractionListener,
    ItemFragment.OnListFragmentInteractionListener {
  @BindView(R.id.frame_container) FrameLayout frameContainer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    getSupportFragmentManager().beginTransaction()
        .add(R.id.frame_container, new MainFragment())
        .commit();
  }

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
        case R.id.navigation_home:
          getSupportFragmentManager().beginTransaction()
              .replace(R.id.frame_container, new MainFragment())
              .commit();
          return true;
        case R.id.navigation_dashboard:
          getSupportFragmentManager().beginTransaction()
              .replace(R.id.frame_container, new ItemFragment())
              .commit();
          return true;
      }
      return false;
    }
  };

  @Override public void onFragmentInteraction(Uri uri) {

  }

  @Override public void onListFragmentInteraction(DummyContent.DummyItem item) {

  }
}
