package lam.minh.com.appmarketphone;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditSaleActivity extends AppCompatActivity {

    ImageView ivBackEditSale,ivImageProduct1EditSale, ivImageProduct2EditSale, ivImageProduct3EditSale;
    EditText etTitleProductEditSale, etPriceProductEditSale, etDescriptionProductEditSale;
    Button btnAgreeEditSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);
        initView();
    }

    public void initView()
    {
        ivBackEditSale =(ImageView)findViewById(R.id.ivBackEditSale);
        ivImageProduct1EditSale = (ImageView)findViewById(R.id.ivImageProduct1EditSale);
        ivImageProduct2EditSale = (ImageView)findViewById(R.id.ivImageProduct2EditSale);
        ivImageProduct3EditSale = (ImageView)findViewById(R.id.ivImageProduct3EditSale);
        etTitleProductEditSale = (EditText)findViewById(R.id.etTitleProductEditSale);
        etPriceProductEditSale = (EditText)findViewById(R.id.etPriceProductEditSale);
        etDescriptionProductEditSale = (EditText)findViewById(R.id.etDescriptionProductEditSale);
        btnAgreeEditSale = (Button)findViewById(R.id.btnAgreeEditSale);
    }
}
