package lam.minh.com.appmarketphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import adapter.PhoneAdapter;
import object.Phone;


public class FragmentListProduct extends Fragment {

    SearchView svProduct;
    ListView lvProduct;
    ArrayList<Phone> listPhones;
    PhoneAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_product, container, false);

        initView(view);

        getListPhones();

        return view;
    }

    public void initView(View view) {
        svProduct = (SearchView) view.findViewById(R.id.svProduct);
        lvProduct = (ListView) view.findViewById(R.id.lvProduct);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), SaleDetailActivity.class);
                intent.putExtra("Phone", listPhones.get(i));
                startActivity(intent);
            }
        });

        svProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filterSearch(s);
                return false;
            }
        });
    }

    public void getListPhones() {
        listPhones = new ArrayList<>();
        adapter = new PhoneAdapter(getActivity(), listPhones);
        lvProduct.setAdapter(adapter);

        DatabaseReference drPhones = FirebaseDatabase.getInstance().getReference("phones");
        drPhones.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Phone phone = dataSnapshot.getValue(Phone.class);
                adapter.addPhone(phone);
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
}
