package lam.minh.com.appmarketphone;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class PasswordResetActivity extends AppCompatActivity{

    ImageView ivBackPasswordReset;
    EditText etEmailPasswordReset;
    Button btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_screen);
        initView();
    }

    public  void initView()
    {
        ivBackPasswordReset = (ImageView)findViewById(R.id.ivBackPasswordReset);
        etEmailPasswordReset = (EditText) findViewById(R.id.etEmailPasswordReset);
        btnResetPassword = (Button) findViewById(R.id.btnResetPassword);
    }

}
