package lam.minh.com.appmarketphone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etEmail, etPassword;
    Button btnLogin, btnForgotPass, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);
        initView();
    }

    public void initView() {
        etEmail = (EditText) findViewById(R.id.etEmailSignIn);
        etPassword = (EditText) findViewById(R.id.etPasswordSignIn);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnForgotPass = (Button) findViewById(R.id.btnForgotPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                break;
            case R.id.btnSignUp:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
            case R.id.btnForgotPassword:
                break;
        }
    }
}
