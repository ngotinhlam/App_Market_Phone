package lam.minh.com.appmarketphone;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);
        initView();
    }

    public void initView()
    {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }
}
