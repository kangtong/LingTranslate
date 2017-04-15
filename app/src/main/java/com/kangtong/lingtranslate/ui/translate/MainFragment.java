package com.kangtong.lingtranslate.ui.translate;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
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

  public MainFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_main, container, false);
    unbinder = ButterKnife.bind(this, view);
    TranslatePagerAdapter translatePagerAdapter = new TranslatePagerAdapter(getFragmentManager());
    viewPager.setAdapter(translatePagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private class TranslatePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> stringList;

    public TranslatePagerAdapter(FragmentManager fm) {
      super(fm);
      stringList = new ArrayList<>();
      stringList.add("全部");
      stringList.add("有道");
      stringList.add("百度");
      stringList.add("金山");
      fragmentList = new ArrayList<>();
      fragmentList.add(new TranslateFragment());
      fragmentList.add(new TranslateFragment());
      fragmentList.add(new TranslateFragment());
      fragmentList.add(new TranslateFragment());
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
