package lam.minh.com.appmarketphone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import handle.LocaleHelper;
import object.Account;


public class FragmentProfile extends Fragment implements View.OnClickListener{

    RelativeLayout rlProfile;
    LinearLayout llMyNews, llChangeLanguage;
    public static CircleImageView civAvatar;
    public static TextView tvName;
    Button btnSignOut;
    Account user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView(view);

        //Lấy thông tin tài khoản đang đăng nhập
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(Account.class);
                tvName.setText(user.getUsername());
                if (!user.getAvatar().equals("")) {
                    Picasso.with(getContext()).load(user.getAvatar()).into(civAvatar);
                } else {
                    civAvatar.setImageResource(R.drawable.logo_no_avatar);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    public void initView(View view) {
        rlProfile = (RelativeLayout) view.findViewById(R.id.rlProfile);
        llMyNews = (LinearLayout) view.findViewById(R.id.llMyNews);
        llChangeLanguage = (LinearLayout) view.findViewById(R.id.llChangeLaguage);
        civAvatar = (CircleImageView) view.findViewById(R.id.circleViewAvatar);
        tvName = (TextView) view.findViewById(R.id.tvProfileName);
        btnSignOut = (Button) view.findViewById(R.id.btnSignOut);
        rlProfile.setOnClickListener(this);
        llMyNews.setOnClickListener(this);
        llChangeLanguage.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlProfile:
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
                break;
            case R.id.llMyNews:
                startActivity(new Intent(getActivity(), MyNewsActivity.class));
                break;
            case R.id.llChangeLaguage:
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_choose_language);

                Button btnVI = (Button) dialog.findViewById(R.id.btnVI);
                Button btnENG = (Button) dialog.findViewById(R.id.btnENG);

                btnVI.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocaleHelper.setLocale(getActivity(), "vi");
                        dialog.dismiss();
                        getActivity().recreate();
                    }
                });
                btnENG.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocaleHelper.setLocale(getActivity(), "en");
                        dialog.dismiss();
                        getActivity().recreate();
                    }
                });

                dialog.show();
                break;
            case R.id.btnSignOut:
                new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.title_notification))
                        .setMessage(getString(R.string.confirm_sign_out_dialog))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.agree_sign_out_dialog), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirebaseAuth.getInstance().signOut();
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton(getString(R.string.disagree_sign_out_dialog), null)
                        .show();
                break;
        }
    }
}
