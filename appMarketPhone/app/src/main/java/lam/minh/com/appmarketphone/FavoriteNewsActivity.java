package lam.minh.com.appmarketphone;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;

public class FavoriteNewsActivity extends AppCompatActivity{

    ImageView ivBackFavoriteNews;
    ListView lvProductFavoriteNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);
        initView();
    }

    public  void initView()
    {
        ivBackFavoriteNews = (ImageView) findViewById(R.id.ivBackFavoriteNews);
        lvProductFavoriteNews = (ListView)findViewById(R.id.lvProductFavoriteNews);
    }
}
