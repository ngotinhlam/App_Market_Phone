package adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;

import lam.minh.com.appmarketphone.R;
import object.Phone;

public class PhoneAdapter extends BaseAdapter {

    ArrayList<Phone> list, listCopy;
    LayoutInflater inflater;
    Context context;

    public PhoneAdapter(Activity activity, ArrayList<Phone> list) {
        context = activity;
        inflater = activity.getLayoutInflater();
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void addPhone(Phone phone) {
        list.add(phone);
        listCopy = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void editPhone(Phone phone) {
        for (int i = 0; i < list.size(); i++) {
            Phone p = list.get(i);
            if (p.getId().equals(phone.getId())) {
                list.set(i, phone);
                notifyDataSetChanged();
                return;
            }
        }
    }

    public void removePhone(Phone phone) {
        for (int i = 0; i < list.size(); i++) {
            Phone p = list.get(i);
            if (p.getId().equals(phone.getId())) {
                list.remove(i);
                notifyDataSetChanged();
                return;
            }
        }
    }

    class ViewHolder {
        ImageView ivImage;
        TextView tvTitle, tvPrice, tvDate;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        Phone phone = list.get(i);
        if (view == null) {
            view = inflater.inflate(R.layout.product_item, null);
            holder = new ViewHolder();
            holder.ivImage = (ImageView) view.findViewById(R.id.ivImageProductItem);
            holder.tvTitle = (TextView) view.findViewById(R.id.tvTitleProductItem);
            holder.tvPrice = (TextView) view.findViewById(R.id.tvPriceProductItem);
            holder.tvDate = (TextView) view.findViewById(R.id.tvDatePostProductItem);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        String url1 = phone.getUrlimage1();
        String url2 = phone.getUrlimage2();
        String url3 = phone.getUrlimage3();
        if (!url1.equals("")) {
            Picasso.with(context).load(url1).into(holder.ivImage);
        } else if (!url2.equals("")) {
            Picasso.with(context).load(url2).into(holder.ivImage);
        } else if (!url3.equals("")) {
            Picasso.with(context).load(url3).into(holder.ivImage);
        } else {
            holder.ivImage.setImageResource(R.drawable.no_image);
        }

        holder.tvTitle.setText(phone.getTitle());
        holder.tvPrice.setText(phone.getPrice() + " VNĐ");
        holder.tvDate.setText(phone.getDate());

        return view;
    }

    public void filterSearch(String str) {
        if (listCopy != null) {
            list.clear();
            for (int i = 0; i < listCopy.size(); i++) {
                Phone phone = listCopy.get(i);
                if (removeAccent(phone.getTitle()).indexOf(removeAccent(str)) != -1) {
                    list.add(phone);
                }
            }
            notifyDataSetChanged();
        }
    }

    //Hàm chuyển chuỗi có dấu thành không dấu và in thường tất cả
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
    }
}
