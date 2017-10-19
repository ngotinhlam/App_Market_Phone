package lam.minh.com.appmarketphone;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class SaleDetailActivity extends AppCompatActivity{

    ImageView ivBackSaleDetail, ivMenuSaleDetail, ivFollowSaleDetail;
    ViewPager vpImageSaleDetail;
    TextView tvTitleProductSaleDetail, tvPriceProductSaleDetail, tvDisplayNameSaleDetail, tvDatePostProductSaleDetail, tvDescribeSaleDetail, tvAddressProductSaleDetail;
    CircleImageView civAvatarSaleDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_screen);
        initView();
    }

    public void initView()
    {
        ivBackSaleDetail = (ImageView)findViewById(R.id.ivBackSaleDetail);
        ivFollowSaleDetail = (ImageView)findViewById(R.id.ivFollowSaleDetail);
        ivMenuSaleDetail = (ImageView)findViewById(R.id.ivMenuSaleDetail);
        vpImageSaleDetail = (ViewPager)findViewById(R.id.vpImageSaleDetail);
        civAvatarSaleDetail = (CircleImageView) findViewById(R.id.civAvatarEditProfile);
        tvTitleProductSaleDetail = (TextView)findViewById(R.id.tvTitleProductSaleDetail);
        tvPriceProductSaleDetail = (TextView)findViewById(R.id.tvPriceProductSaleDetail);
        tvDisplayNameSaleDetail = (TextView)findViewById(R.id.tvDisplayNameSaleDetail);
        tvDatePostProductSaleDetail = (TextView)findViewById(R.id.tvDatePostProductItem);
        tvDescribeSaleDetail = (TextView)findViewById(R.id.tvDescribeSaleDetail);
        tvAddressProductSaleDetail = (TextView)findViewById(R.id.tvAddressProductSaleDetail);

    }
}
