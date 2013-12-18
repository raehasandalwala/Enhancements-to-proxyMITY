package com.example.mainproxymity;

import java.io.Serializable;

import android.widget.ImageView;

public class Thumbnail implements Serializable{
	public ImageView icon;
	public String title;
	public Thumbnail(){
		super();
	}
public Thumbnail(ImageView icon,String title)
{
	super();
	if(icon==null)
	{
		
		icon.setImageResource(R.drawable.ic_launcher);
	}
	this.icon=icon;
	this.title=title;
	
}
}
