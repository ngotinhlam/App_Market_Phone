package lam.minh.com.appmarketphone;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import handle.Authentication;
import handle.SlideImagePagerAdapter;
import me.relex.circleindicator.CircleIndicator;
import object.Account;
import object.Phone;

public class SaleDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBackSaleDetail, ivMenuSaleDetail, ivFollowSaleDetail;
    TextView tvTitleProductSaleDetail, tvPriceProductSaleDetail, tvDisplayNameSaleDetail, tvDatePostProductSaleDetail, tvDescribeSaleDetail, tvAddressProductSaleDetail;
    CircleImageView civAvatarSaleDetail;
    LinearLayout llCall, llSms;
    ViewPager vpImageSaleDetail;
    CircleIndicator ciImageSaleDetail;
    SlideImagePagerAdapter slideImagePagerAdapter;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);
        initView();

        Phone phone = (Phone) getIntent().getSerializableExtra("Phone");
        tvTitleProductSaleDetail.setText(phone.getTitle());
        tvDescribeSaleDetail.setText(phone.getDescription());
        tvPriceProductSaleDetail.setText(phone.getPrice() + " VNĐ");
        tvDatePostProductSaleDetail.setText(phone.getDate());
        tvAddressProductSaleDetail.setText(Authentication.account.getAddress());

        //Phần đưa ảnh vào ViewPager
        ArrayList<String> lstUrlImg = new ArrayList<>();
        String url1 = phone.getUrlimage1(), url2 = phone.getUrlimage2(), url3 = phone.getUrlimage3();
        if (!url1.equals("")) {
            lstUrlImg.add(url1);
        }
        if (!url2.equals("")) {
            lstUrlImg.add(url2);
        }
        if (!url3.equals("")) {
            lstUrlImg.add(url3);
        }
        slideImagePagerAdapter = new SlideImagePagerAdapter(this, lstUrlImg);
        vpImageSaleDetail.setAdapter(slideImagePagerAdapter);
        ciImageSaleDetail.setViewPager(vpImageSaleDetail);

        DatabaseReference drUser = FirebaseDatabase.getInstance().getReference("users").child(phone.getUserid());
        drUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                account = dataSnapshot.getValue(Account.class);
                tvDisplayNameSaleDetail.setText(account.getUsername());
                Picasso.with(SaleDetailActivity.this).load(account.getAvatar()).into(civAvatarSaleDetail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void initView() {
        ivBackSaleDetail = (ImageView) findViewById(R.id.ivBackSaleDetail);
        ivFollowSaleDetail = (ImageView) findViewById(R.id.ivFollowSaleDetail);
        ivMenuSaleDetail = (ImageView) findViewById(R.id.ivMenuSaleDetail);
        vpImageSaleDetail = (ViewPager) findViewById(R.id.vpImageSaleDetail);
        llCall = (LinearLayout) findViewById(R.id.llCallSaleDetail);
        llSms = (LinearLayout) findViewById(R.id.llSmsSaleDetail);
        ciImageSaleDetail = (CircleIndicator) findViewById(R.id.ciImageSaleDetail);
        civAvatarSaleDetail = (CircleImageView) findViewById(R.id.civAvatarSaleDetail);
        tvTitleProductSaleDetail = (TextView) findViewById(R.id.tvTitleProductSaleDetail);
        tvPriceProductSaleDetail = (TextView) findViewById(R.id.tvPriceProductSaleDetail);
        tvDisplayNameSaleDetail = (TextView) findViewById(R.id.tvDisplayNameSaleDetail);
        tvDatePostProductSaleDetail = (TextView) findViewById(R.id.tvDatePostProductSaleDetail);
        tvDescribeSaleDetail = (TextView) findViewById(R.id.tvDescribeSaleDetail);
        tvAddressProductSaleDetail = (TextView) findViewById(R.id.tvAddressProductSaleDetail);
        ivBackSaleDetail.setOnClickListener(this);
        llCall.setOnClickListener(this);
        llSms.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBackSaleDetail:
                finish();
                break;
            case R.id.llCallSaleDetail:
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                phoneIntent.setData(Uri.parse("tel:" + account.getPhone()));
                startActivity(phoneIntent);
                break;
            case R.id.llSmsSaleDetail:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + Uri.encode(account.getPhone())));
                startActivity(intent);
                break;
        }
    }
}
