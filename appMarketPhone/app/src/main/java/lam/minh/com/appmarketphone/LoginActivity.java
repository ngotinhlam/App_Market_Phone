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

import dialog.NotificationDialog;
import handle.Authentication;
import object.Account;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etEmail, etPassword;
    Button btnLogin, btnForgotPass, btnSignUp;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    NotificationDialog notificationDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);

        initView();

        mAuth = FirebaseAuth.getInstance();
        //Kiểm tra xem có tài khoản đang nhập hay không
        if (mAuth.getCurrentUser() != null) { //Có tài khoản đăng nhập
            DatabaseReference drUser = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid());
            drUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Account account = dataSnapshot.getValue(Account.class);
                    Authentication.account = account;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        progressDialog = new ProgressDialog(this);
        notificationDialog = new NotificationDialog(this);
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
                    notificationDialog.showMessage("Thông báo", "Hãy nhập email");
                    return;
                }
                if (password.equals("")) {
                    notificationDialog.showMessage("Thông báo", "Hãy nhập mật khẩu");
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
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else { //Đăng nhập thất bại
                                    String error = task.getException().getMessage().trim();
                                    Log.d("error", error);
                                    switch (error) {
                                        case "The password is invalid or the user does not have a password.":
                                            notificationDialog.showMessage("Thông báo", "Mật khẩu không đúng");
                                            break;
                                        case "The email address is badly formatted.":
                                            notificationDialog.showMessage("Thông báo", "Email không hợp lệ");
                                            break;
                                        case "There is no user record corresponding to this identifier. The user may have been deleted.":
                                            notificationDialog.showMessage("Thông báo", "Không tìm thấy email");
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
