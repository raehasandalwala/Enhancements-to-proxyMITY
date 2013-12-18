package com.example.mainproxymity;



import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

public class about extends Activity {
	WebView helppage;
    @SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        
        helppage = (WebView) findViewById(R.id.aboutwebView);
        helppage.getSettings().setJavaScriptEnabled(true);
        
        
        //helppage.loadUrl("file:///android_asset/help.html");
        
        if (new File("/mnt/extsd/Instructions/proxymity.html").exists())
    		
		{    
        	helppage.loadUrl("file:\\mnt\\extsd\\Instructions\\proxymity.html");
		}
        
        else if(new File("/mnt/sdcard/Instructions/proxymity.html").exists())
        {
        	  helppage.loadUrl("file:\\mnt\\sdcard\\Instructions\\proxymity.html");
        }
        
        else if(new File ("/mnt/external_sd/Instructions/proxymity.html").exists())
        {
      	  helppage.loadUrl("file:\\mnt\\external_sd\\Instructions\\proxymity.html");
        }
        else
        {
        	helppage.loadUrl("file:///android_asset/proxymity.html");
        	
        }    
        
    }

   

    
}
