package lam.minh.com.appmarketphone;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity{

    ImageView ivBackEditProfile;
    CircleImageView civAvatarEditProfile;
    TextView tvNameEditProfile, tvPhoneNumberEditProfile, tvEmailEditProfile, tvAddressEditProfile, tvPasswordProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);
        initView();

    }
    public void initView()
    {
        ivBackEditProfile = (ImageView) findViewById(R.id.ivBackEditProfile);
        civAvatarEditProfile = (CircleImageView)findViewById(R.id.civAvatarEditProfile);
        tvNameEditProfile = (TextView)findViewById(R.id.tvNameEditProfile);
        tvPhoneNumberEditProfile = (TextView)findViewById(R.id.tvPhoneNumberEditProfile);
        tvEmailEditProfile = (TextView)findViewById(R.id.tvEmailEditProfile);
        tvAddressEditProfile = (TextView)findViewById(R.id.tvAddressEditProfile);
        tvPasswordProfile = (TextView)findViewById(R.id.tvPasswordProfile);

    }
}
