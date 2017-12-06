package lam.minh.com.appmarketphone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import handle.Authentication;


public class FragmentProfile extends Fragment implements View.OnClickListener{

    RelativeLayout rlProfile;
    CircleImageView civAvatar;
    TextView tvName;
    Button btnSignOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView(view);

        if (!Authentication.account.getAvatar().equals("")) {
            Picasso.with(getContext()).load(Authentication.account.getAvatar()).into(civAvatar);
        }
        tvName.setText(Authentication.account.getUsername());

        return view;
    }

    public void initView(View view) {
        rlProfile = (RelativeLayout) view.findViewById(R.id.rlProfile);
        civAvatar = (CircleImageView) view.findViewById(R.id.circleViewAvatar);
        tvName = (TextView) view.findViewById(R.id.tvProfileName);
        btnSignOut = (Button) view.findViewById(R.id.btnSignOut);
        rlProfile.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlProfile:
                Toast.makeText(getActivity(), "Thong tin", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnSignOut:
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                break;
        }
    }
}
