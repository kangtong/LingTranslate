package com.kangtong.lingtranslate.ui.translate;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.kangtong.lingtranslate.R;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

  @BindView(R.id.tab_layout) TabLayout tabLayout;
  @BindView(R.id.view_pager) ViewPager viewPager;
  Unbinder unbinder;
  private int lastIndex = 0;

  public MainFragment() {
    // Required empty public constructor
  }

  @Override public void onResume() {
    super.onResume();
    viewPager.setCurrentItem(lastIndex, true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_main, container, false);
    unbinder = ButterKnife.bind(this, view);
    TranslatePagerAdapter translatePagerAdapter = new TranslatePagerAdapter(getFragmentManager());
    viewPager.setAdapter(translatePagerAdapter);
    viewPager.setOffscreenPageLimit(translatePagerAdapter.getCount());
    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override public void onPageSelected(int position) {
        lastIndex = position; // 监听滑动到哪一个界面了
      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });
    tabLayout.setupWithViewPager(viewPager);
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private class TranslatePagerAdapter extends FragmentPagerAdapter { // 这个adapter可以保存view，不销毁
    private List<Fragment> fragmentList;
    private List<String> stringList;

    public TranslatePagerAdapter(FragmentManager fm) {
      super(fm);
      stringList = new ArrayList<>();
      stringList.add("多结果对比");
      stringList.add("多语言&段落");
      stringList.add("单词详解");
      fragmentList = new ArrayList<>();
      fragmentList.add(new TranslateFragment());
      fragmentList.add(new BaiduFragment());
      fragmentList.add(new IcibaFragment());
    }

    @Override public Fragment getItem(int position) {
      return fragmentList.get(position);
    }

    @Override public int getCount() {
      return fragmentList.size();
    }

    @Override public CharSequence getPageTitle(int position) {
      return stringList.get(position);
    }
  }
}
