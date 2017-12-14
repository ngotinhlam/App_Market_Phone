package lam.minh.com.appmarketphone;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.lang.reflect.Array;
import java.util.ArrayList;

import adapter.PhoneAdapter;
import dialog.NotificationDialog;
import object.Phone;

public class MyNewsActivity extends AppCompatActivity {

    ImageView ivBack;
    ListView lvMyPhones;
    PhoneAdapter adapter;
    ArrayList<Phone> listMyPhones;
    String uid;
    NotificationDialog notificationDialog;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_news);
        initView();
        notificationDialog = new NotificationDialog(this);
        notificationDialog.setCanceledOnTouchOutside(false);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.title_loading));

        listMyPhones = new ArrayList<>();
        adapter = new PhoneAdapter(this, listMyPhones);
        lvMyPhones.setAdapter(adapter);
        uid = FirebaseAuth.getInstance().getUid();
        //Lấy danh sách tất cả phone đang bán
        FirebaseDatabase.getInstance().getReference("phones").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Phone phone = dataSnapshot.getValue(Phone.class);
                if (phone.getUserid().equals(uid)) { //Kiểm tra xem nếu là sản phẩm của mình đăng thì add vào list
                    adapter.addPhone(phone);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Phone phone = dataSnapshot.getValue(Phone.class);
                adapter.editPhone(phone);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Phone phone = dataSnapshot.getValue(Phone.class);
                adapter.removePhone(phone);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_my_news, menu);//tạo context menu gán contextmenu bên menu_my_new vào
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int listPosition = info.position;
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(MyNewsActivity.this, SaleDetailActivity.class);
                intent.putExtra("Phone", listMyPhones.get(listPosition));
                startActivity(intent);
                break;
            case R.id.item2:
                Intent intent1 = new Intent(MyNewsActivity.this, EditSaleActivity.class);
                intent1.putExtra("phone", listMyPhones.get(listPosition));
                startActivity(intent1);
                break;
            case R.id.item3:
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.title_notification))
                        .setMessage(getString(R.string.confirm_remove_sale_news_dialog))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.agree_sign_out_dialog), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                progressDialog.show();
                                final Phone phone = listMyPhones.get(listPosition);
                                FirebaseDatabase.getInstance().getReference("phones/" + phone.getId()).removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        if (databaseError == null) {
                                            //Xóa ảnh của sản phẩm
                                            FirebaseStorage.getInstance().getReference("images/" + phone.getId() + "_1.jpg").delete();
                                            FirebaseStorage.getInstance().getReference("images/" + phone.getId() + "_2.jpg").delete();
                                            FirebaseStorage.getInstance().getReference("images/" + phone.getId() + "_3.jpg").delete();
                                            notificationDialog.showMessage(getString(R.string.title_loading), getString(R.string.remove_sale_news_success));
                                        } else {
                                            notificationDialog.showMessage(getString(R.string.title_loading), getString(R.string.remove_sale_news_fail));
                                        }
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        })
                        .setNegativeButton(getString(R.string.disagree_sign_out_dialog), null)
                        .show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void initView() {
        ivBack = (ImageView) findViewById(R.id.ivBackMyNews);
        lvMyPhones = (ListView) findViewById(R.id.lvProductMyNews);
        registerForContextMenu(lvMyPhones); //Gán menu ngữ cảnh cho listview (long click vào sẽ hiện menu ngữ cảnh)
        lvMyPhones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MyNewsActivity.this, SaleDetailActivity.class);
                intent.putExtra("Phone", listMyPhones.get(i));
                startActivity(intent);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
