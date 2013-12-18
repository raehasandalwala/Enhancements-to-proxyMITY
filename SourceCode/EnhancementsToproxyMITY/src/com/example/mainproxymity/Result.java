package com.example.mainproxymity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * ********************************************************************************************
 * 
 * This class displays the result of the Quiz Session along with the correct answer
 * 
 * ********************************************************************************************
 * */

public class Result extends Activity{

	TextView tv,tv1;
	LinearLayout l1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		l1=(LinearLayout)findViewById(R.id.ll);
		
		Intent mIntent = getIntent();
		 int no = mIntent.getIntExtra("no", 0);
		 String[] correct = getIntent().getStringArrayExtra("correct");
		 ArrayList<String> array_file = getIntent().getStringArrayListExtra("array_file");
		 for(int i=0;i<no;i++)
		 {
			 tv=new TextView(this);
			 tv.setTextSize(25);
			 tv.setGravity(Gravity.CENTER);
			 tv.setText(array_file.get(6*i));
			 tv1=new TextView(this);
			 tv1.setTextSize(25);
			 tv1.setGravity(Gravity.CENTER);
			 tv1.setText("Ans: "+correct[i]);
			 l1.addView(tv);
			 l1.addView(tv1);
		 }
	}

}
