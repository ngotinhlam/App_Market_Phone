package lam.minh.com.appmarketphone;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dialog.NotificationDialog;
import handle.DatabaseFirebase;
import object.Phone;

import static android.app.Activity.RESULT_OK;


public class FragmentPostNews extends Fragment implements View.OnClickListener {

    public View rootView = null;
    public static ImageView ivImageProduct1, ivImageProduct2, ivImageProduct3;
    public static EditText etTitle, etPrice, etDescription;
    public static Button btnClearImage1, btnClearImage2, btnClearImage3, btnPost;
    Context context;
    final int CHOOSE_IMAGE_1 = 111, CHOOSE_IMAGE_2 = 222, CHOOSE_IMAGE_3 = 333;
    public static Bitmap bitmap1 = null, bitmap2 = null, bitmap3 = null;
    public static String urlimage1 = "", urlimage2 = "", urlimage3 = "";
    Intent intent;
    DatabaseFirebase df;
    public static NotificationDialog notificationDialog;
    public static ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       if (rootView == null) {
           rootView = inflater.inflate(R.layout.fragment_post_news, container, false);
           initView(rootView);
           df = new DatabaseFirebase(getActivity());
           notificationDialog = new NotificationDialog(getContext());
           progressDialog = new ProgressDialog(getContext());
           progressDialog.setCanceledOnTouchOutside(false);
       }

        return rootView;
    }

    public void initView(View view) {
        ivImageProduct1 = (ImageView) view.findViewById(R.id.ivImageProduct1PostSale);
        ivImageProduct2 = (ImageView) view.findViewById(R.id.ivImageProduct2PostSale);
        ivImageProduct3 = (ImageView) view.findViewById(R.id.ivImageProduct3PostSale);
        etTitle = (EditText) view.findViewById(R.id.etTitleProductPostSale);
        etPrice = (EditText) view.findViewById(R.id.etPriceProductPostSale);
        etDescription = (EditText) view.findViewById(R.id.etDescriptionProductPostSale);
        btnClearImage1 = (Button) view.findViewById(R.id.btnClearImage1);
        btnClearImage2 = (Button) view.findViewById(R.id.btnClearImage2);
        btnClearImage3 = (Button) view.findViewById(R.id.btnClearImage3);
        btnPost = (Button) view.findViewById(R.id.btnAgreePostSale);
        //Gán sự kiện click
        ivImageProduct1.setOnClickListener(this);
        ivImageProduct2.setOnClickListener(this);
        ivImageProduct3.setOnClickListener(this);
        btnClearImage1.setOnClickListener(this);
        btnClearImage2.setOnClickListener(this);
        btnClearImage3.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        btnClearImage1.setVisibility(View.INVISIBLE);
        btnClearImage2.setVisibility(View.INVISIBLE);
        btnClearImage3.setVisibility(View.INVISIBLE);
        etPrice.setText("0");
        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                etPrice.removeTextChangedListener(this);
                try {
                    String originalString = s.toString();
                    if (originalString.length() > 25) {
                        return;
                    }
                    if (originalString.equals("")) {
                        originalString = "0";
                    }
                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    etPrice.setText(formattedString);
                    etPrice.setSelection(etPrice.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                etPrice.addTextChangedListener(this);
            }
        });
    }

    public void clearImage(ImageView imageView, String urlimage, Button clearimage) {
        imageView.setImageResource(R.drawable.logo_camera);
        urlimage = "";
        clearimage.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            Uri avatarUri = result.getUri();
            switch (requestCode) {
                case CHOOSE_IMAGE_1:
                    try {
                        //Lấy bitmap từ một URI
                        bitmap1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), avatarUri);
                        //lấy ảnh từ profile chuyển thành bitmap
                        //Gán ảnh cho mage View
                        ivImageProduct1.setImageBitmap(bitmap1);
                        //set bitmap lên ô ảnh 1
                        //Hiện nút xóa ảnh
                        btnClearImage1.setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case CHOOSE_IMAGE_2:
                    try {
                        //Lấy bitmap từ một URI
                        bitmap2 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), avatarUri);
                        //Gán ảnh cho Circle Image View
                        ivImageProduct2.setImageBitmap(bitmap2);
                        //Hiện nút xóa ảnh
                        btnClearImage2.setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case CHOOSE_IMAGE_3:
                    try {
                        //Lấy bitmap từ một URI
                        bitmap3 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), avatarUri);
                        //Gán ảnh cho Circle Image View
                        ivImageProduct3.setImageBitmap(bitmap3);
                        //Hiện nút xóa ảnh
                        btnClearImage3.setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();
            Log.d("error", error.toString());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivImageProduct1PostSale: //Chon hinh 1
                //Toast.makeText(context, "asdasd", Toast.LENGTH_SHORT).show();
                intent = CropImage.activity(null).setAspectRatio(1, 1).setCropShape(CropImageView.CropShape.RECTANGLE).getIntent(getContext());
                startActivityForResult(intent, CHOOSE_IMAGE_1);
                break;
            case R.id.ivImageProduct2PostSale: //Chon hinh 2
                intent = CropImage.activity(null).setAspectRatio(1, 1).setCropShape(CropImageView.CropShape.RECTANGLE).getIntent(getContext());
                startActivityForResult(intent, CHOOSE_IMAGE_2);
                break;
            case R.id.ivImageProduct3PostSale: //Chon hinh 3
                intent = CropImage.activity(null).setAspectRatio(1, 1).setCropShape(CropImageView.CropShape.RECTANGLE).getIntent(getContext());
                startActivityForResult(intent, CHOOSE_IMAGE_3);
                break;
            case R.id.btnClearImage1:
                bitmap1 = null;
                clearImage(ivImageProduct1, urlimage1, btnClearImage1);
                break;
            case R.id.btnClearImage2:
                bitmap2 = null;
                clearImage(ivImageProduct2, urlimage2, btnClearImage2);
                break;
            case R.id.btnClearImage3:
                bitmap3 = null;
                clearImage(ivImageProduct3, urlimage3, btnClearImage3);
                break;
            case R.id.btnAgreePostSale:
                Calendar calendar = Calendar.getInstance();
                String id = "PHONE" + calendar.getTimeInMillis();//với số là giờ phút giây được đổi thằng mili
                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String title = etTitle.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                String price = etPrice.getText().toString().trim();
                //Kiểm tra
                if (title.equals("") || price.equals("") || description.equals("")) {
                    notificationDialog.showMessage("Thông báo", "Phải điền đủ thông tin");
                    return;
                }
                if (bitmap1 == null || bitmap2 == null || bitmap3 == null) {
                    notificationDialog.showMessage("Thông báo", getString(R.string.not_enough_photos));
                    return;
                }
                progressDialog.setMessage("Đang xử lý");
                progressDialog.show();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                Phone phone = new Phone(id, userid, title, price, description, "", "", "", dateFormat.format(date));
                df.addProduct(phone, bitmap1, bitmap2, bitmap3);
                break;
        }
    }

    public static void clearInfo() {
        ivImageProduct1.setImageResource(R.drawable.logo_camera);
        ivImageProduct2.setImageResource(R.drawable.logo_camera);
        ivImageProduct3.setImageResource(R.drawable.logo_camera);
        btnClearImage1.setVisibility(View.INVISIBLE);
        btnClearImage2.setVisibility(View.INVISIBLE);
        btnClearImage3.setVisibility(View.INVISIBLE);
        etTitle.setText("");
        etPrice.setText("0");
        etDescription.setText("");
        bitmap1 = bitmap2 = bitmap3 = null;
        urlimage1 = urlimage2 = urlimage3 = null;
    }
}
