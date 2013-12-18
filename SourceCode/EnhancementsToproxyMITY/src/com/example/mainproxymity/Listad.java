package com.example.mainproxymity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ********************************************************************************************
 * 
 * This class creates the list adapter(ListAd) for the thumbnails of ListView
 * 
 * ********************************************************************************************
 * */

public class Listad extends ArrayAdapter<Thumbnail>{
	
	Context context;
	int layoutResID;
	Thumbnail data[]=null;
	
	public Listad(Context context, int layoutResID,Thumbnail [] data){
		super(context, layoutResID,data);
		this.layoutResID = layoutResID;
        this.context = context;
        this.data = data;
		// TODO Auto-generated constructor stub
	}
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
     ListHolder holder = null;
     System.out.println("llloo");
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResID, parent, false);
           
            holder = new ListHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.img);
           
            holder.txtTitle = (TextView)row.findViewById(R.id.txt);
           
            row.setTag(holder);
        }
        else
        {
            holder = (ListHolder)row.getTag();
        }
       
        Thumbnail t = data[position];
        holder.txtTitle.setText(t.title);
       
        Bitmap bitmap=((BitmapDrawable)(t.icon).getDrawable()).getBitmap();
       holder.imgIcon.setImageBitmap(bitmap);
       //System.out.println(t.title);
        return row;
    }
   
    static class ListHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}
