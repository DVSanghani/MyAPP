package com.example.peacock.myapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by peacock on 8/4/17.
 */

public class SQLiteListBaseAdapter extends BaseAdapter {


    Context context;
    ArrayList<Contact> listuser;

    public SQLiteListBaseAdapter(Context context, ArrayList<Contact> listuser) {
        this.context = context;
        this.listuser = listuser;
    }

    @Override
    public int getCount() {
        return listuser.size();
    }

    @Override
    public Object getItem(int position) {
        return listuser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View child, ViewGroup parent) {
        Holder holder;

        byte[] bytes = listuser.get(position).get_photo();
        Bitmap obj = null;
        if (bytes != null) {

            obj = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        }

        //LayoutInflater is used to create a new View (or Layout) object from one of your xml layouts.
        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.list_item, null);

            holder = new Holder();

            //   holder.textviewid = (TextView) child.findViewById(R.id.textViewID);
            holder.textviewname = (TextView) child.findViewById(R.id.textViewNAME);
            holder.textViewdob = (TextView) child.findViewById(R.id.textViewDOB);
            holder.imageView = (ImageView) child.findViewById(R.id.List_photo);

            holder.textviewgender = (TextView) child.findViewById(R.id.textViewGENDER);
            holder.textviewcountry = (TextView) child.findViewById(R.id.textViewCOUNTRY);
            holder.textviewhobbies = (TextView) child.findViewById(R.id.textViewHOBBIES);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        // holder.textviewid.setText(listuser.get(position).getId());
        holder.textviewname.setText(listuser.get(position).getName());
        holder.textViewdob.setText(listuser.get(position).getDob());
        holder.textviewgender.setText(listuser.get(position).get_gender());
        holder.imageView.setImageBitmap(obj);
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.textviewcountry.setText(listuser.get(position).get_country());
        holder.textviewhobbies.setText(listuser.get(position).get_hobbies());

        return child;
    }

    public class Holder {
        TextView textviewid;
        TextView textviewname;
        TextView textViewdob;
        TextView textviewgender;
        TextView textviewcountry, textviewhobbies;
        ImageView imageView;
    }

}
