package lam.minh.com.appmarketphone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etEmail, etPassword;
    Button btnLogin, btnForgotPass, btnSignUp;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);
        initView();
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
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
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (email.equals("")) {
                    Toast.makeText(this, "Hãy nhập email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equals("")) {
                    Toast.makeText(this, "Hãy nhập password", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Hiện progress dialog
                progressDialog.setMessage("Đang xử lý");
                progressDialog.show();
                //Hàm đăng nhập của Firebase
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) { //Đăng nhập thành công
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else { //Đăng nhập thất bại
                                    String error = task.getException().getMessage().trim();
                                    Log.d("error", error);
                                    switch (error) {
                                        case "The password is invalid or the user does not have a password.":
                                            Toast.makeText(LoginActivity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "The email address is badly formatted.":
                                            Toast.makeText(LoginActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "There is no user record corresponding to this identifier. The user may have been deleted.":
                                            Toast.makeText(LoginActivity.this, "Không tìm thấy email", Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                progressDialog.dismiss(); //Đóng progress dialog
                            }
                        });
                break;
            case R.id.btnSignUp:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
            case R.id.btnForgotPassword:
                startActivity(new Intent(LoginActivity.this, PasswordResetActivity.class));
                break;
        }
    }
}
