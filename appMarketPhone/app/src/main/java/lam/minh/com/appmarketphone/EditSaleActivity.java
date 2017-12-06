package lam.minh.com.appmarketphone;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
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

public class EditSaleActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ivBack, ivImageProduct1, ivImageProduct2, ivImageProduct3;
    Button btnClearImage1, btnClearImage2, btnClearImage3;
    EditText etTitle, etPrice, etDescription;
    Button btnAgree;
    Phone phone;
    final int CHOOSE_IMAGE_1 = 111, CHOOSE_IMAGE_2 = 222, CHOOSE_IMAGE_3 = 333;
    Bitmap bitmap1 = null, bitmap2 = null, bitmap3 = null;
    String urlimage1 = "", urlimage2 = "", urlimage3 = "";
    Intent intent;
    public static NotificationDialog notificationDialog;
    public static ProgressDialog progressDialog;
    DatabaseFirebase df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sale_news);
        initView();
        notificationDialog = new NotificationDialog(this);
        notificationDialog.setCanceledOnTouchOutside(false);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.title_loading));
        df = new DatabaseFirebase(this);

        phone = (Phone) getIntent().getSerializableExtra("phone");

        //Gán dữ liệu
        etTitle.setText(phone.getTitle());
        etPrice.setText(phone.getPrice());
        etDescription.setText(phone.getDescription());
        if (!phone.getUrlimage1().equals("")) {
            Picasso.with(this).load(phone.getUrlimage1()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    ivImageProduct1.setImageBitmap(bitmap);
                    bitmap1 = bitmap;
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
            btnClearImage1.setVisibility(View.VISIBLE);
        }
        if (!phone.getUrlimage2().equals("")) {
            Picasso.with(this).load(phone.getUrlimage2()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    ivImageProduct2.setImageBitmap(bitmap);
                    bitmap2 = bitmap;
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
            btnClearImage2.setVisibility(View.VISIBLE);
        }
        if (!phone.getUrlimage3().equals("")) {
            Picasso.with(this).load(phone.getUrlimage3()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    ivImageProduct3.setImageBitmap(bitmap);
                    bitmap3 = bitmap;
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
            btnClearImage3.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            Uri uriImage = result.getUri();
            switch (requestCode) {
                case CHOOSE_IMAGE_1:
                    try {
                        //Lấy bitmap từ một URI
                        bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImage);
                        //Gán ảnh cho mage View
                        ivImageProduct1.setImageBitmap(bitmap1);
                        //Hiện nút xóa ảnh
                        btnClearImage1.setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case CHOOSE_IMAGE_2:
                    try {
                        //Lấy bitmap từ một URI
                        bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImage);
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
                        bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImage);
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

    public void initView() {
        ivBack = (ImageView) findViewById(R.id.ivBackEditSale);
        ivImageProduct1 = (ImageView) findViewById(R.id.ivImageProduct1EditSale);
        ivImageProduct2 = (ImageView) findViewById(R.id.ivImageProduct2EditSale);
        ivImageProduct3 = (ImageView) findViewById(R.id.ivImageProduct3EditSale);
        btnClearImage1 = (Button) findViewById(R.id.btnClearImage1EditSale);
        btnClearImage2 = (Button) findViewById(R.id.btnClearImage2EditSale);
        btnClearImage3 = (Button) findViewById(R.id.btnClearImage3EditSale);
        etTitle = (EditText) findViewById(R.id.etTitleProductEditSale);
        etPrice = (EditText) findViewById(R.id.etPriceProductEditSale);
        etDescription = (EditText) findViewById(R.id.etDescriptionProductEditSale);
        btnAgree = (Button) findViewById(R.id.btnAgreeEditSale);
        btnClearImage1.setVisibility(View.INVISIBLE);
        btnClearImage2.setVisibility(View.INVISIBLE);
        btnClearImage3.setVisibility(View.INVISIBLE);
        ivImageProduct1.setOnClickListener(this);
        ivImageProduct2.setOnClickListener(this);
        ivImageProduct3.setOnClickListener(this);
        btnClearImage1.setOnClickListener(this);
        btnClearImage2.setOnClickListener(this);
        btnClearImage3.setOnClickListener(this);
        btnAgree.setOnClickListener(this);
        ivBack.setOnClickListener(this);
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

    public void clearImage(ImageView imageView, Bitmap bitmap, String urlimage, Button clearimage) {
        imageView.setImageResource(R.drawable.logo_camera);
        imageView.setImageResource(R.drawable.logo_camera);
        bitmap = null;
        urlimage = "";
        clearimage.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBackEditSale:
                finish();
                break;
            case R.id.ivImageProduct1EditSale: //Chon hinh 1
                //Toast.makeText(context, "asdasd", Toast.LENGTH_SHORT).show();
                intent = CropImage.activity(null).setAspectRatio(1, 1).setCropShape(CropImageView.CropShape.RECTANGLE).getIntent(EditSaleActivity.this);
                startActivityForResult(intent, CHOOSE_IMAGE_1);
                break;
            case R.id.ivImageProduct2EditSale: //Chon hinh 2
                intent = CropImage.activity(null).setAspectRatio(1, 1).setCropShape(CropImageView.CropShape.RECTANGLE).getIntent(EditSaleActivity.this);
                startActivityForResult(intent, CHOOSE_IMAGE_2);
                break;
            case R.id.ivImageProduct3EditSale: //Chon hinh 3
                intent = CropImage.activity(null).setAspectRatio(1, 1).setCropShape(CropImageView.CropShape.RECTANGLE).getIntent(EditSaleActivity.this);
                startActivityForResult(intent, CHOOSE_IMAGE_3);
                break;
            case R.id.btnClearImage1EditSale:
                clearImage(ivImageProduct1, bitmap1, urlimage1, btnClearImage1);
                break;
            case R.id.btnClearImage2EditSale:
                clearImage(ivImageProduct2, bitmap2, urlimage2, btnClearImage2);
                break;
            case R.id.btnClearImage3EditSale:
                clearImage(ivImageProduct3, bitmap3, urlimage3, btnClearImage3);
                break;
            case R.id.btnAgreeEditSale:
                String title = etTitle.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                String price = etPrice.getText().toString().trim();
                //Kiểm tra
                if (title.equals("") || price.equals("") || description.equals("")) {
                    notificationDialog.showMessage(getString(R.string.title_notification), getString(R.string.empty_warning));
                    return;
                }
                progressDialog.show();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                phone.setTitle(title);
                phone.setPrice(price);
                phone.setDescription(description);
                phone.setUrlimage1(urlimage1);
                phone.setUrlimage2(urlimage2);
                phone.setUrlimage3(urlimage3);
                phone.setDate(dateFormat.format(date));
                df.editProduct(phone, bitmap1, bitmap2, bitmap3);
                break;
        }
    }
}
