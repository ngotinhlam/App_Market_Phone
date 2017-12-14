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
import com.google.firebase.auth.FirebaseUser;

import dialog.NotificationDialog;
import handle.Validate;

public class ChangePasswordActivity extends AppCompatActivity {

    ImageView ivBack;
    EditText etNewPassword, etRetypePassword;
    Button btnAgreeChangePassword;
    FirebaseUser user;
    NotificationDialog notificationDialog;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_screen);
        initView();
        notificationDialog = new NotificationDialog(this);
        notificationDialog.setCanceledOnTouchOutside(false);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.title_loading));

        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    public void initView() {
        ivBack = (ImageView) findViewById(R.id.ivBackChangePassword);
        etNewPassword = (EditText) findViewById(R.id.etNewPasswordChangePassword);
        etRetypePassword = (EditText) findViewById(R.id.etRetypePasswordChangePassword);
        btnAgreeChangePassword = (Button) findViewById(R.id.btnAgreeChangePassword);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAgreeChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = etNewPassword.getText().toString().trim();
                String retypePassword = etRetypePassword.getText().toString().trim();
                if (password.equals("") || retypePassword.equals("")) {
                    notificationDialog.showMessage(getString(R.string.title_notification), getString(R.string.empty_warning));
                    return;
                }
                if (!Validate.isValidPassword(password)) {
                    notificationDialog.showMessage(getString(R.string.title_notification), getString(R.string.password_not_enough_characters));
                    return;
                }
                if (!password.equals(retypePassword)) {
                    notificationDialog.showMessage(getString(R.string.title_notification), getString(R.string.retype_the_wrong_password));
                    return;
                }
                progressDialog.show();
                user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            notificationDialog.showMessage(getString(R.string.title_notification), getString(R.string.change_password_success));
                            etNewPassword.setText("");
                            etRetypePassword.setText("");
                        } else {
                            String error = task.getException().getMessage();
                            Log.d("error", task.getException().getMessage());
                            if (error.equals("This operation is sensitive and requires recent authentication. Log in again before retrying this request.")) {
                                notificationDialog.showMessage(getString(R.string.title_notification), getString(R.string.log_back_to_change_password));
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }
}
