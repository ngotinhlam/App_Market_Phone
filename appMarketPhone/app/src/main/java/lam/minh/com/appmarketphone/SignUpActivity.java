package lam.minh.com.appmarketphone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import handle.DatabaseFirebase;
import handle.Validate;
import object.Account;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    EditText etName, etPhone, etAddress, etEmail, etPassword, etRetypePassword;
    Button btnSignUp;
    CircleImageView civAvatar;
    DatabaseFirebase df;
    Bitmap bitmapAvatar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        initView();
        progressDialog = new ProgressDialog(this);
        df = new DatabaseFirebase();
    }

    public void initView() {
        ivBack = (ImageView) findViewById(R.id.ivBackSignUp);
        etName = (EditText) findViewById(R.id.etNameSignUp);
        etPhone = (EditText) findViewById(R.id.etPhoneSignUp);
        etAddress = (EditText) findViewById(R.id.etAddressSignUp);
        etEmail = (EditText) findViewById(R.id.etEmailSignUp);
        etPassword = (EditText) findViewById(R.id.etPasswordSignUp);
        etRetypePassword = (EditText) findViewById(R.id.etRetypePasswordSignUp);
        civAvatar = (CircleImageView) findViewById(R.id.civAvatarSignUp);
        civAvatar.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        btnSignUp = (Button) findViewById(R.id.btnComplete);
        ivBack.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        civAvatar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Nhấn back
            case R.id.ivBackSignUp:
                finish();
                break;
            //Nhấn hoàn tất đăng ký
            case R.id.btnComplete:
                String name = etName.getText().toString().trim();
                final String phone = etPhone.getText().toString().trim();
                final String address = etAddress.getText().toString().trim();
                final String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String retypepassword = etRetypePassword.getText().toString().trim();
                if (name.equals("") || phone.equals("") || address.equals("") || email.equals("") || password.equals("") || retypepassword.equals("")) {
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
                    //Hiện progress dialog
                    progressDialog.setMessage("Đang xử lý");
                    progressDialog.show();
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        Account account = new Account(user.getUid(), email, address, phone, "");
                                        df.addAccount(account, bitmapAvatar);
                                        Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                        clearInfo();
                                    } else {
                                        Log.d("error", task.getException().getMessage());
                                    }
                                    progressDialog.dismiss(); //Đóng progress dialog
                                }
                            });
                }
                break;
            //Nhấn chọn ảnh đại diện
            case R.id.civAvatarSignUp:
                CropImage.activity(null).setAspectRatio(1, 1).setCropShape(CropImageView.CropShape.OVAL).start(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri avatarUri = result.getUri();
                try {
                    //Lấy bitmap từ một URI
                    bitmapAvatar = MediaStore.Images.Media.getBitmap(getContentResolver(), avatarUri);
                    //Gán ảnh cho Circle Image View
                    civAvatar.setImageBitmap(bitmapAvatar);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.d("error", error.toString());
            }
        }
    }

    public void clearInfo() {
        etName.setText("");
        etPhone.setText("");
        etAddress.setText("");
        etEmail.setText("");
        etPassword.setText("");
        etRetypePassword.setText("");
        civAvatar.setImageResource(R.drawable.icon_user);
        bitmapAvatar = null;
    }

}
