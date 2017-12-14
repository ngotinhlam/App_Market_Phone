package handle;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.Telephony;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.logging.Handler;

import lam.minh.com.appmarketphone.MainActivity;
import lam.minh.com.appmarketphone.R;
import lam.minh.com.appmarketphone.SaleDetailActivity;
import object.Account;
import object.Phone;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public int IDNotification = 0;
    DatabaseReference drUser;

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        String phoneId = "";

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            phoneId = remoteMessage.getNotification().getBody();
        }

        sendNotification(phoneId);
    }

    private void sendNotification(final String phoneId) {
        DatabaseReference drPhone = FirebaseDatabase.getInstance().getReference("phones/" + phoneId);
        drPhone.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Phone phone = dataSnapshot.getValue(Phone.class);

                Intent intent = new Intent(MyFirebaseMessagingService.this, SaleDetailActivity.class);
                intent.putExtra("Phone", phone);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(MyFirebaseMessagingService.this)
                        .setAutoCancel(true)   //Automatically delete the notification
                        .setSmallIcon(R.mipmap.ic_launcher) //Notification icon
                        .setContentIntent(pendingIntent)
                        .setContentTitle(phone.getTitle())
                        .setContentText(phone.getPrice() + " VNĐ")
                        .setSound(defaultSoundUri);


                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(++IDNotification, notificationBuilder.build());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
