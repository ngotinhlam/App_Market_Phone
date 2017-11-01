package dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

public class NotificationDialog extends AlertDialog{

    AlertDialog.Builder builder;

    public NotificationDialog(@NonNull Context context) {
        super(context);
        builder = new Builder(context);
    }

    public void showMessage(String title, String content) {
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setCancelable(true);
        builder.setNegativeButton("Đóng", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss(); //Đóng dialog
            }
        });
        builder.create().show(); //Hiện dialog
    }
}
