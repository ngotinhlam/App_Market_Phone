package lam.minh.com.appmarketphone;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;

public class MyNewsActivity extends AppCompatActivity{

    ImageView ivBackMyNews;
    ListView lvProductMyNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);
        initView();
    }

    public void initView()
    {
        ivBackMyNews = (ImageView)findViewById(R.id.ivBackMyNews);
        lvProductMyNews = (ListView)findViewById(R.id.lvProductMyNews);
    }
}
