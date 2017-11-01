package handle;

import android.graphics.Bitmap;
import android.net.Uri;
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

import lam.minh.com.appmarketphone.FragmentPostNews;
import object.Account;
import object.Phone;

/**
 * Created by Admin on 10/19/2017.
 */

public class DatabaseFirebase {

    DatabaseReference rootDatabase; //nút gốc của database
    StorageReference rootStorage; //nút gốc của storage


    public DatabaseFirebase() {
        rootDatabase = FirebaseDatabase.getInstance().getReference();
        rootStorage = FirebaseStorage.getInstance().getReference();
    }

    public void addAccount(final Account account, Bitmap bitmap) {
        final String uid = account.getUid();
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

    public void addProduct(final Phone phone, Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3) {
        String phoneid = phone.getId();
        String urlimage1 = phone.getUrlimage1();
        String urlimage2 = phone.getUrlimage2();
        String urlimage3 = phone.getUrlimage3();
        final DatabaseReference drPhones = rootDatabase.child("phones").child(phoneid);
        StorageReference srImage1 = rootStorage.child("images").child(phoneid + "_1.jpg");
        StorageReference srImage2 = rootStorage.child("images").child(phoneid + "_2.jpg");
        StorageReference srImage3 = rootStorage.child("images").child(phoneid + "_3.jpg");

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
                    drPhones.setValue(phone);
                }
            });
        }

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
                    drPhones.setValue(phone);
                }
            });
        }

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
                }
            });
        }

        drPhones.setValue(phone).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FragmentPostNews.notificationDialog.showMessage("Thông báo", "Đăng sản phẩm thành công");
                FragmentPostNews.clearInfo();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("error", e.getMessage());
                FragmentPostNews.notificationDialog.showMessage("Thông báo", "Đăng sản phẩm thất bại, vui lòng thử lại sau");
            }
        });
    }
}
