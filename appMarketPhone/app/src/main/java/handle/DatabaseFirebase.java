package handle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import lam.minh.com.appmarketphone.EditSaleActivity;
import lam.minh.com.appmarketphone.FragmentPostNews;
import lam.minh.com.appmarketphone.R;
import object.Account;
import object.Phone;

public class DatabaseFirebase {

    DatabaseReference rootDatabase; //nút gốc của database
    StorageReference rootStorage; //nút gốc của storage
    Activity activity;

    public DatabaseFirebase(Activity activity) {
        this.activity = activity;
        rootDatabase = FirebaseDatabase.getInstance().getReference();
        rootStorage = FirebaseStorage.getInstance().getReference();
    }

    public void addAccount(final Account account, Bitmap bitmap) {
        final String uid = account.getUserid();
        final DatabaseReference mUsers = rootDatabase.child("users").child(uid);
        StorageReference mAvatars = rootStorage.child("avatar").child("avatar_" + uid + ".jpg");

        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = mAvatars.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) { //Upload avatar thất bại
                    Log.d("error", exception.getMessage().toString());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { //Upload avatar thành công
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    String url = downloadUrl.toString();
                    account.setAvatar(url);
                    mUsers.setValue(account);
                }
            });
        } else {
            mUsers.setValue(account);
        }
    }

    public void addProduct(final Phone phone, Bitmap bitmap1, final Bitmap bitmap2, final Bitmap bitmap3) {
        String phoneid = phone.getId();

        final DatabaseReference drPhones = rootDatabase.child("phones").child(phoneid);
        StorageReference srImage1 = rootStorage.child("images").child(phoneid + "_1.jpg");
        final StorageReference srImage2 = rootStorage.child("images").child(phoneid + "_2.jpg");
        final StorageReference srImage3 = rootStorage.child("images").child(phoneid + "_3.jpg");

        if (bitmap1 != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = srImage1.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) { //Upload avatar thất bại
                    Log.d("error", exception.getMessage().toString());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { //Upload avatar thành công
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    String url = downloadUrl.toString();
                    phone.setUrlimage1(url);
                    if (bitmap2 != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();

                        UploadTask uploadTask = srImage2.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) { //Upload avatar thất bại
                                Log.d("error", exception.getMessage().toString());
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { //Upload avatar thành công
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                String url = downloadUrl.toString();
                                phone.setUrlimage2(url);
                                if (bitmap3 != null) {
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                    byte[] data = baos.toByteArray();

                                    UploadTask uploadTask = srImage3.putBytes(data);
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) { //Upload avatar thất bại
                                            Log.d("error", exception.getMessage().toString());
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { //Upload avatar thành công
                                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                            String url = downloadUrl.toString();
                                            phone.setUrlimage3(url);
                                            drPhones.setValue(phone);
                                            drPhones.setValue(phone).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    FragmentPostNews.notificationDialog.showMessage("Thông báo", "Đăng sản phẩm thành công");
                                                    FragmentPostNews.clearInfo();
                                                    FragmentPostNews.progressDialog.dismiss();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("error", e.getMessage());
                                                    FragmentPostNews.notificationDialog.showMessage("Thông báo", "Đăng sản phẩm thất bại, vui lòng thử lại sau");
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    public void editProduct(final Phone phone, Bitmap bitmap1, final Bitmap bitmap2, final Bitmap bitmap3) {
        String phoneid = phone.getId();
        //Xóa hết ảnh cũ
        rootStorage.child("images").child(phoneid + "_1.jpg").delete();
        rootStorage.child("images").child(phoneid + "_2.jpg").delete();
        rootStorage.child("images").child(phoneid + "_3.jpg").delete();
        //Cập nhật lại dữ liệu mới
        final DatabaseReference drPhones = rootDatabase.child("phones").child(phoneid);
        StorageReference srImage1 = rootStorage.child("images").child(phoneid + "_1.jpg");
        final StorageReference srImage2 = rootStorage.child("images").child(phoneid + "_2.jpg");
        final StorageReference srImage3 = rootStorage.child("images").child(phoneid + "_3.jpg");

        if (bitmap1 != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = srImage1.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) { //Upload ảnh thất bại
                    Log.d("error", exception.getMessage().toString());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { //Upload ảnh thành công
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    String url = downloadUrl.toString();
                    phone.setUrlimage1(url);
                    if (bitmap2 != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();

                        UploadTask uploadTask = srImage2.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) { //Upload avatar thất bại
                                Log.d("error", exception.getMessage().toString());
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { //Upload avatar thành công
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                String url = downloadUrl.toString();
                                phone.setUrlimage2(url);
                                if (bitmap3 != null) {
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                    byte[] data = baos.toByteArray();

                                    UploadTask uploadTask = srImage3.putBytes(data);
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) { //Upload avatar thất bại
                                            Log.d("error", exception.getMessage().toString());
                                        }
                                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) { //Upload avatar thành công
                                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                            String url = downloadUrl.toString();
                                            phone.setUrlimage3(url);
                                            drPhones.setValue(phone).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    EditSaleActivity.notificationDialog.showMessage(activity.getString(R.string.title_notification), activity.getString(R.string.edit_sale_news_succes));
                                                    EditSaleActivity.progressDialog.dismiss();
                                                    activity.finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("error", e.getMessage());
                                                    EditSaleActivity.notificationDialog.showMessage(activity.getString(R.string.title_notification), activity.getString(R.string.edit_sale_news_fail));
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }

    }
}
