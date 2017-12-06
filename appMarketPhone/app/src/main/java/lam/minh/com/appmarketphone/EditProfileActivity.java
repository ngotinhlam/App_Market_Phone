package lam.minh.com.appmarketphone;


import android.app.Dialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import dialog.NotificationDialog;
import handle.Validate;
import object.Account;

public class EditProfileActivity extends AppCompatActivity {

    CircleImageView civAvatar;
    TextView tvName, tvPhone, tvEmail, tvAddress;
    ImageView ivEditName, ivEditPhone, ivEditAddress, ivBack;
    RelativeLayout rlChangePassword;
    Account user;
    DatabaseReference drUser;
    NotificationDialog notificationDialog;
    ProgressDialog progressDialog;
    int CHOOSE_IMAGE_AVATAR = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_screen);
        initView();
        notificationDialog = new NotificationDialog(this);
        notificationDialog.setCanceledOnTouchOutside(false);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.title_loading));

        //Lấy thông tin tài khoản đang đăng nhập
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(Account.class);
                tvName.setText(user.getUsername());
                tvPhone.setText(user.getPhone());
                tvEmail.setText(user.getEmail());
                tvAddress.setText(user.getAddress());
                if (!user.getAvatar().equals("")) {
                    Picasso.with(EditProfileActivity.this).load(user.getAvatar()).into(civAvatar);
                } else {
                    civAvatar.setImageResource(R.drawable.logo_no_avatar);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Lấy node user hiện tại
        drUser = FirebaseDatabase.getInstance().getReference("users/" + FirebaseAuth.getInstance().getUid());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        progressDialog.show();
        CropImage.ActivityResult result = CropImage.getActivityResult(intent);
        if (resultCode == RESULT_OK) {
            Uri avatarUri = result.getUri();
            if (requestCode == CHOOSE_IMAGE_AVATAR) {
                try {
                    //Lấy bitmap từ một URI
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), avatarUri);
                    StorageReference srAvatar = FirebaseStorage.getInstance().getReference("avatar/avatar_" + FirebaseAuth.getInstance().getUid() + ".jpg");
                    if (bitmap != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();

                        UploadTask uploadTask = srAvatar.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) { //Upload avatar thất bại
                                Log.d("error", exception.getMessage().toString());
                                progressDialog.dismiss();
                                notificationDialog.showMessage(getString(R.string.title_notification), getString(R.string.change_avatar_fail));
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { //Upload avatar thành công
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                String url = downloadUrl.toString();
                                user.setAvatar(url);
                                drUser.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        notificationDialog.showMessage(getString(R.string.title_notification), getString(R.string.change_url_avatar_success));
                                    }
                                });
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        notificationDialog.showMessage(getString(R.string.title_notification), getString(R.string.change_avatar_fail));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("error", e.getMessage());
                    progressDialog.dismiss();
                    notificationDialog.showMessage(getString(R.string.title_notification), getString(R.string.change_avatar_fail));
                }
            }
        }
    }

    public void initView() {
        civAvatar = (CircleImageView) findViewById(R.id.civAvatarEditProfile);
        tvName = (TextView) findViewById(R.id.tvDisplayNameEditProfile);
        tvPhone = (TextView) findViewById(R.id.tvPhoneNumberEditProfile);
        tvEmail = (TextView) findViewById(R.id.tvEmailEditProfile);
        tvAddress = (TextView) findViewById(R.id.tvAddressEditProfile);
        ivEditName = (ImageView) findViewById(R.id.ivEditDisplayNameEditProfile);
        ivEditPhone = (ImageView) findViewById(R.id.ivEditPhoneNumberEditProfile);
        ivEditAddress = (ImageView) findViewById(R.id.ivEditAddressEditProfile);
        ivBack = (ImageView) findViewById(R.id.ivBackEditProfile);
        rlChangePassword = (RelativeLayout) findViewById(R.id.rlChangePasswordEditProfile);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        civAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CropImage.activity(null).setAspectRatio(1, 1).setCropShape(CropImageView.CropShape.RECTANGLE).getIntent(EditProfileActivity.this);
                startActivityForResult(intent, CHOOSE_IMAGE_AVATAR);
            }
        });
        ivEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(EditProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_change_information);

                final EditText etInfo = (EditText) dialog.findViewById(R.id.etChangeInformation);
                Button btnAgree = (Button) dialog.findViewById(R.id.btnAgreeChangeInformation);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancelChangeInformation);

                etInfo.setText(user.getUsername());
                etInfo.setSelection(user.getUsername().length());

                btnAgree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.setUsername(etInfo.getText().toString().trim());
                        drUser.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialog.dismiss();
                                notificationDialog.showMessage(getString(R.string.title_notification), getString(R.string.change_display_name_succes));
                            }
                        });
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        ivEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(EditProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_change_information);

                final EditText etInfo = (EditText) dialog.findViewById(R.id.etChangeInformation);
                Button btnAgree = (Button) dialog.findViewById(R.id.btnAgreeChangeInformation);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancelChangeInformation);

                etInfo.setText(user.getPhone());
                etInfo.setSelection(user.getPhone().length());

                btnAgree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phoneNumber = etInfo.getText().toString();
                        if (Validate.isValidPhoneNumber(phoneNumber)) {
                            user.setPhone(phoneNumber);
                            drUser.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    dialog.dismiss();
                                    notificationDialog.showMessage(getString(R.string.title_notification), getString(R.string.change_phone_number_succes));
                                }
                            });
                        } else {
                            notificationDialog.showMessage(getString(R.string.title_notification), getString(R.string.phone_number_invalid));
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        ivEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(EditProfileActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_change_information);

                final EditText etInfo = (EditText) dialog.findViewById(R.id.etChangeInformation);
                Button btnAgree = (Button) dialog.findViewById(R.id.btnAgreeChangeInformation);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancelChangeInformation);

                etInfo.setText(user.getAddress());
                etInfo.setSelection(user.getAddress().length());

                btnAgree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.setAddress(etInfo.getText().toString().trim());
                        drUser.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialog.dismiss();
                                notificationDialog.showMessage(getString(R.string.title_notification), getString(R.string.change_address_succes));
                            }
                        });
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        rlChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this, ChangePasswordActivity.class));
            }
        });
    }
}
