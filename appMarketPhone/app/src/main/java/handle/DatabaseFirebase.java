package handle;

import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import object.Account;

/**
 * Created by Admin on 10/19/2017.
 */

public class DatabaseFirebase {
    DatabaseReference root;

    public DatabaseFirebase() {
        root = FirebaseDatabase.getInstance().getReference();
    }

    public void addAccount(Account account) {
        DatabaseReference mUsers = root.child("users");
        mUsers.setValue(account);
    }
}
