package handle;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import lam.minh.com.appmarketphone.R;

public class SlideImagePagerAdapter extends PagerAdapter{

    ArrayList<String> lstUrlImage;
    LayoutInflater layoutInflater;
    Context context;

    public SlideImagePagerAdapter(Activity activity, ArrayList<String> url) {
        this.context = activity;
        this.lstUrlImage = url;
    }

    @Override
    public int getCount() {
        return lstUrlImage.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_image_sale_detail, container, false);
        ImageView ivItemImageSaleDetail;
        ivItemImageSaleDetail = (ImageView) view.findViewById(R.id.ivItemImageSaleDetail);
        if (getCount() == 0) {
            ivItemImageSaleDetail.setImageResource(R.drawable.logo_no_image);
        } else {
            Picasso.with(context).load(lstUrlImage.get(position)).resize(context.getResources().getInteger(R.integer.image_width_pixel), context.getResources().getInteger(R.integer.image_height_pixel)).centerCrop().into(ivItemImageSaleDetail);
        }
        ((ViewPager) container).addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);
    }

}
