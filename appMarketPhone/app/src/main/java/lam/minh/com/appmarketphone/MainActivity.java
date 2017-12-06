package lam.minh.com.appmarketphone;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import handle.LocaleHelper;

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

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("www", "thay doi nn");
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "en"));
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.title_notification))
                .setMessage(getString(R.string.confirm_sign_out_dialog))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.agree_sign_out_dialog), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.disagree_sign_out_dialog), null)
                .show();
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

