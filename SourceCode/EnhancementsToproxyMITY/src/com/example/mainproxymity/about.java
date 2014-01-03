package com.example.mainproxymity;



import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

public class about extends Activity {
	WebView aboutus;
    @SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        
        aboutus = (WebView) findViewById(R.id.aboutwebView);
        aboutus.getSettings().setJavaScriptEnabled(true);
        aboutus.setBackgroundColor(Color.rgb(192,222,255));
        
        //helppage.loadUrl("file:///android_asset/help.html");
        
        if (new File("/mnt/extsd/Instructions/proxymity.html").exists())
    		
		{    
        	aboutus.loadUrl("file:\\mnt\\extsd\\Instructions\\proxymity.html");
		}
        
        else if(new File("/mnt/sdcard/Instructions/proxymity.html").exists())
        {
        	aboutus.loadUrl("file:\\mnt\\sdcard\\Instructions\\proxymity.html");
        }
        
        else if(new File ("/mnt/external_sd/Instructions/proxymity.html").exists())
        {
        	aboutus.loadUrl("file:\\mnt\\external_sd\\Instructions\\proxymity.html");
        }
        else
        {
        	aboutus.loadUrl("file:///android_asset/proxymity.html");
        	
        }    
        
    }

   

    
}
