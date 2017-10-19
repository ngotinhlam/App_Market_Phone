package lam.minh.com.appmarketphone;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText etNewPasswordChangePassword, etRetypePasswordChangePassword;
    Button btnAgreeChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_screen);
        initView();

    }
    public void initView()
    {
        etNewPasswordChangePassword = (EditText)findViewById(R.id.etNewPasswordChangePassword);
        etRetypePasswordChangePassword = (EditText)findViewById(R.id.etRetypePasswordChangePassword);
        btnAgreeChangePassword = (Button)findViewById(R.id.btnAgreeChangeInformation);
    }
}
