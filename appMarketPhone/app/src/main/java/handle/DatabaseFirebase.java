package handle;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import object.Account;

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
}
