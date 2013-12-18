package com.example.mainproxymity;

import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * ***************************************************************************************
 * 
 * This class displays the mode(Sd-Card & Wi-Fi) of the application.
 * Also, this activity contains Help & About Us button 
 * 
 * ***************************************************************************************
 * */

public class Main extends Activity {

	ImageView help, aboutus;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity1);		// Activity thats is being inflated by this class
		
		// SD-CARD Button, that enables Sd-Card mode of the app
		ImageView b=(ImageView)findViewById(R.id.button1);
		
		// Wi-Fi Button, that enables Wi-Fi mode of the app
		ImageView b1=(ImageView)findViewById(R.id.button2);
		
		// Help Button, that gives user the functionality of the application & way to operate
		help = (ImageView) findViewById(R.id.help);
		
		// About Us Button, that gives user the functionality of the application & way to operate
		aboutus = (ImageView) findViewById(R.id.aboutus);
		
		// Click Listener for Sd-Card mode
		b.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Main.this,MainActivity.class);
				startActivity(i);
				}
			
			});
		
		// Click Listener for Wi-Fi mode
		b1.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Main.this,Splash.class);
				startActivity(i);
				}
				
			});
		
		// Click Listener for Help button
		help.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
			//	help.layout(600, help.getTop()+2, help.getRight(), help.getBottom());
			//	help.layout(600, help.getTop()-2, help.getRight(), help.getBottom());
				startActivity(new Intent(Main.this, Help.class));
			}
		});
		
		// Click Listener for About Us button
		aboutus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
			//	help.layout(600, help.getTop()+2, help.getRight(), help.getBottom());
			//	help.layout(600, help.getTop()-2, help.getRight(), help.getBottom());
				startActivity(new Intent(Main.this, about.class));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}