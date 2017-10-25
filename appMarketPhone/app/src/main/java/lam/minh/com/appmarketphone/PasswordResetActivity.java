package lam.minh.com.appmarketphone;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ivBackPasswordReset;
    EditText etEmailPasswordReset;
    Button btnResetPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_screen);
        initView();
        mAuth = FirebaseAuth.getInstance();
    }

    public  void initView()
    {
        ivBackPasswordReset = (ImageView)findViewById(R.id.ivBackPasswordReset);
        etEmailPasswordReset = (EditText) findViewById(R.id.etEmailPasswordReset);
        btnResetPassword = (Button) findViewById(R.id.btnResetPassword);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBackPasswordReset:
                finish();
                break;
            case R.id.btnResetPassword:
                //Hàm reset password
                mAuth.sendPasswordResetEmail(etEmailPasswordReset.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                }
                            }
                        });
                break;
        }
    }
}
