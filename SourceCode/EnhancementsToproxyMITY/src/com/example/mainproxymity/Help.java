package com.example.mainproxymity;



import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


/**
 * ********************************************************************************************
 * 
 * This class displays the overall functionalities of the application and gives brief description
 * on how to operate the application conviniently
 * 
 * ********************************************************************************************
 * */

public class Help extends Activity {
	WebView helppage;
    @SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        
        helppage = (WebView) findViewById(R.id.helpwebView);
        helppage.getSettings().setJavaScriptEnabled(true);
        //helppage.loadUrl("file:///android_asset/help.html");
       
        // Searches for the "proxyMITY_wifi_help.html" file which contains the description
        if (new File("/mnt/extsd/Instructions/proxyMITY_wifi_help.html").exists())
    		
		{    
        	helppage.loadUrl("file:\\mnt\\extsd\\Instructions\\proxyMITY_wifi_help.html");
		}
        
        else if(new File("/mnt/sdcard/Instructions/proxyMITY_wifi_help.html").exists())
        {
        	  helppage.loadUrl("file:\\mnt\\sdcard\\Instructions\\proxyMITY_wifi_help.html");
        }
        
        else if(new File ("/mnt/external_sd/Instructions/proxyMITY_wifi_help.html").exists())
        {
      	  helppage.loadUrl("file:\\mnt\\external_sd\\Instructions\\proxyMITY_wifi_help.html");
        }
        else
        {
        	helppage.loadUrl("file:///android_asset/proxyMITY_wifi_help.html");	
        }    
        
    }

   

    
}