package lam.minh.com.appmarketphone;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import dialog.NotificationDialog;
import handle.Validate;

public class PasswordResetActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etEmail;
    Button btnResetPassword;
    ImageView ivBack;
    FirebaseAuth mAuth;
    NotificationDialog notificationDialog;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset_screen);

        initView();

        mAuth = FirebaseAuth.getInstance();
        notificationDialog = new NotificationDialog(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang xử lý");
    }

    public  void initView()
    {
        etEmail = (EditText) findViewById(R.id.etEmailPasswordReset);
        btnResetPassword = (Button) findViewById(R.id.btnResetPassword);
        ivBack = (ImageView) findViewById(R.id.ivBackPasswordReset);
        btnResetPassword.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    public void clearInfo() {
        etEmail.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnResetPassword:
                //Hàm reset password
                final String email = etEmail.getText().toString().trim();
                if (email.equals("")) {
                    notificationDialog.showMessage("Thông báo", "Hãy nhập email mà bạn đã dùng để đăng ký tài khoản");
                    return;
                }
                if (!Validate.isValidEmail(email)) {
                    notificationDialog.showMessage("Thông báo", "Email không hợp lệ");
                    return;
                }
                progressDialog.show();
                mAuth.sendPasswordResetEmail(etEmail.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    notificationDialog.showMessage("Thông báo", "Xác nhận email thành công, vào email của bạn để đổi mật khẩu mới");
                                    clearInfo();
                                } else {
                                    String error = task.getException().getMessage();
                                    Log.d("error", error);
                                    if (error.equals("There is no user record corresponding to this identifier. The user may have been deleted.")) {
                                        notificationDialog.showMessage("Thông báo", "Không có tài khoản nào được đăng ký bởi email này");
                                    } else {
                                        notificationDialog.showMessage("Thông báo", "Xác nhận email thất bại, vui lòng thử lại sau");
                                    }
                                }
                                progressDialog.dismiss();
                            }
                        });
                break;
            case R.id.ivBackPasswordReset:
                finish();
                break;
        }
    }
}
