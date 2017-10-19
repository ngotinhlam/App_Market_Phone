package lam.minh.com.appmarketphone;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import handle.DatabaseFirebase;
import handle.Validate;
import object.Account;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ivBack;
    EditText etName, etPhone, etAddress, etEmail, etPassword, etRetypePassword;
    Button btnSignUp;
    DatabaseFirebase df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        initView();

    }

    public void initView() {
        ivBack = (ImageView) findViewById(R.id.ivBackSignUp);
        etName = (EditText) findViewById(R.id.etNameSignUp);
        etPhone = (EditText) findViewById(R.id.etPhoneSignUp);
        etAddress = (EditText) findViewById(R.id.etAddressSignUp);
        etEmail = (EditText) findViewById(R.id.etEmailSignUp);
        etPassword = (EditText) findViewById(R.id.etPasswordSignUp);
        etRetypePassword = (EditText) findViewById(R.id.etRetypePasswordSignUp);
        btnSignUp = (Button) findViewById(R.id.btnComplete);
        ivBack.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBackSignUp:
                finish();
                break;
            case R.id.btnComplete:
                String name = etName.getText().toString().trim();
                final String phone = etPhone.getText().toString().trim();
                final String address = etAddress.getText().toString().trim();
                final String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String retypepassword = etRetypePassword.getText().toString().trim();
                if (name.equals("")||phone.equals("")||address.equals("")||email.equals("")||password.equals("")||retypepassword.equals("")) {
                    Toast.makeText(this, "Phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (!Validate.isValidPhoneNumber(phone)) {
                        Toast.makeText(this, "Số đt không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!Validate.isValidEmail(email)) {
                        Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!Validate.isValidPassword(password)) {
                        Toast.makeText(this, "Mật khẩu phải từ 6 kí tự trở lên", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!password.equals(retypepassword)) {
                        Toast.makeText(this, "Nhập lại mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                        Account account = new Account(user.getUid(), email, address, phone, "");
                                        df.addAccount(account);
                                    } else {
                                        Log.d("error", task.getException().getMessage());
                                    }
                                }
                            });
                }
                break;
        }
    }
}
