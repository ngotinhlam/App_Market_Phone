package lam.minh.com.appmarketphone;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    ViewPager viewPager;
    TabLayout tabLayout;
    FragmentAdapter fragmentAdapter;
    ArrayList<Fragment> listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView()
    {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        FragmentPostNews fragment1 = new FragmentPostNews();
        FragmentListProduct fragment2 = new FragmentListProduct();
        FragmentProfile fragment3 = new FragmentProfile();
        listFragment = new ArrayList<>();
        listFragment.add(fragment1);
        listFragment.add(fragment2);
        listFragment.add(fragment3);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), listFragment);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.logo_smartphone);
        tabLayout.getTabAt(1).setIcon(R.drawable.logo_search);
        tabLayout.getTabAt(2).setIcon(R.drawable.logo_user);
    }
}

