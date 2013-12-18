package com.example.mainproxymity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;


/**
 * ********************************************************************************************
 * 
 * This class displays the Quiz, initially it contains Quiz for "Introduction to DBMS" lecture.
 * Quiz contains 2 question that are being read from SQL.txt file 
 * 
 * ********************************************************************************************
 * */

public class Quiz extends TabActivity implements OnTabChangeListener{

	int no;
	long pos;
	char ch;
	RandomAccessFile r;
	TextView tv;
	RadioGroup rg;
	RadioButton r1,r2,r3,r4;
	String[] ans,correct;
	int count = 0;
	TabHost host;
	Button button1;
	int index=0;
	ImageView img,img1;
	int flag1=0;
	String str;
	int[] flag;
	int rg_flag=0;
	LinearLayout lll;
	RadioButton[] radioButton;
	ArrayList<String> array_file=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz_de);
		lll = (LinearLayout)findViewById(R.id.lll);
		System.out.println("after cv");
		File f = new File("/mnt/sdcard/proxyMITY/text/SQL.txt");
		try {
			 r = new RandomAccessFile(f,"r");
			 System.out.println("after fileopen");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			no = Integer.parseInt(r.readLine());
			pos = r.getFilePointer();
			ans = new String[no];
			correct = new String[no];
			flag = new int[no];
			radioButton = new RadioButton[no];
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("no of questions" + no);
	    readFile();

		tv = (TextView) findViewById(R.id.textView1);
		rg = (RadioGroup) findViewById(R.id.radioGroup);
		r1 = (RadioButton) findViewById(R.id.rb1);
		r2 = (RadioButton) findViewById(R.id.rb2);
		r3 = (RadioButton) findViewById(R.id.rb3);
		r4 = (RadioButton) findViewById(R.id.rb4);
		button1 = (Button) findViewById(R.id.button1);
		host=(TabHost)findViewById(android.R.id.tabhost);
		

		button1.setText("Submit the Quiz");
		
		rg.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener(){

			

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				RadioButton checkedRadioButton = (RadioButton)rg.findViewById(arg1);
				if(flag1==0)
				{
				
			     int checkedIndex = rg.indexOfChild(checkedRadioButton);
				    String selection = (String) checkedRadioButton.getText();
				    radioButton[Integer.parseInt(getTabHost().getCurrentTabTag())-1]=checkedRadioButton;
				    System.out.println("rb text" + radioButton[Integer.parseInt(getTabHost().getCurrentTabTag())-1].getText() );
				    System.out.println("selection is"+selection);
				    System.out.println("correct answer"+correct[Integer.parseInt(getTabHost().getCurrentTabTag())-1]);
				    flag[Integer.parseInt(getTabHost().getCurrentTabTag())-1]=1;
				    rg_flag=1;
				    if(selection.equals(correct[Integer.parseInt(getTabHost().getCurrentTabTag())-1]))
				    {
				    	 lll.removeView(img);
				    	img=new ImageView(Quiz.this);
				    	img.setImageResource(R.drawable.imagestick);
			           
				    	lll.addView(img);
				    	
				    	
				    }
				    else
				    {
				    	 lll.removeView(img);
				    	System.out.println("in else");
				    	img=new ImageView(Quiz.this);
				    	img.setImageResource(R.drawable.imgthing);
				    	
				    	lll.addView(img);
				    	
				    }
				
			}
			}
			
		});
		
		for(int t=0;t<no;t++)
		{
		System.out.println("after button1");
		tv.setText(array_file.get(index++));
		r1.setText(array_file.get(index++));
		r2.setText(array_file.get(index++));
		r3.setText(array_file.get(index++));
		r4.setText(array_file.get(index++));
		correct[t]=array_file.get(index++);

		Integer i = new Integer(t+1);
	TabSpec spec=host.newTabSpec(i.toString());
	spec.setContent(R.id.lll);
	
	spec.setIndicator(i.toString());
	

	
		host.addTab(spec);
		host.setCurrentTab(1);
		host.setCurrentTab(0);
		host.setOnTabChangedListener(this);
		
		       // tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF0000")); //unselected
		}
		
		System.out.println("array list size " + array_file.size());
	
		for(int u=0;u<array_file.size();u++)
		{
			System.out.println(array_file.get(u));
		}

		
	}
	
	public void onBackPressed()
	{
		int index = rg.indexOfChild(findViewById(rg.getCheckedRadioButtonId()));
		if(index==-1)
		{
			
			//Toast.makeText(this,"Please select an option",Toast.LENGTH_SHORT).show();
		}
	} 
	public void readFile()
	{
	
        while(count!=no)
		{
        	String ques = new String();
        	try {
        		if(count==0)
        		{
        			ch=(char) r.read();
        			ch=(char) r.read();
        			ch=(char) r.read();
        		}
    			while((ch=(char) r.read())!='?')
    			{

    				System.out.println("count " + count);
    				ques+=ch;
    			}
    			ques+='?';
    			r.readLine();
    			
    			array_file.add(ques);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	for(int i=0;i<5;i++)
        	{
		       try {
		    	   System.out.println("in read File");
				array_file.add(r.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
        }
        	count++;
		}
	}
	
	
	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		System.out.println("arg0 is " + arg0);
		for(int n=1;n<=no;n++)
		{
			Integer t = new Integer(n);
			System.out.println("current tab tag is " + getTabHost().getCurrentTabTag());
			
			if(arg0.equals(t.toString()))
			{
				//getTabHost().setCurrentTab(n);
				System.out.println("tab changed" + t.toString());
				tv.setText(array_file.get(6*(n-1)+0));
				r1.setText(array_file.get(6*(n-1)+1));
				r2.setText(array_file.get(6*(n-1)+2));
				r3.setText(array_file.get(6*(n-1)+3));
				r4.setText(array_file.get(6*(n-1)+4));
				correct[n-1]=array_file.get(6*(n-1)+5);
				System.out.println("file ans"+correct[n-1]);
				
			}
			
		}
		
		if(rg_flag==1)
		{
			if( flag[Integer.parseInt(getTabHost().getCurrentTabTag())-1]==0)
			{
				lll.removeView(img);
				System.out.println("after remove view");
				flag1=1;
				r1.setChecked(false);
				r2.setChecked(false);
				r3.setChecked(false);
				r4.setChecked(false);
				flag1=0;
				
				
			}
			else
			{
				flag1=1;
				int j =Integer.parseInt(getTabHost().getCurrentTabTag());
				System.out.println("for second tab" + j);
				//flag1=0;
				
				 System.out.println("rb text" + radioButton[Integer.parseInt(getTabHost().getCurrentTabTag())-1].getText() );
			    lll.removeView(img);
			    if(correct[Integer.parseInt(getTabHost().getCurrentTabTag())-1].equals(radioButton[Integer.parseInt(getTabHost().getCurrentTabTag())-1].getText()))
			    	{img=new ImageView(Quiz.this);
		    	img.setImageResource(R.drawable.imagestick);
	           
		    	lll.addView(img);
		    
			    	}
			    else
			    {
			    	img=new ImageView(Quiz.this);
			    	img.setImageResource(R.drawable.imgthing);
		           
			    	lll.addView(img);
			    }
			    	
				
				 rg.clearCheck();
				rg.check(radioButton[Integer.parseInt(getTabHost().getCurrentTabTag())-1].getId());
				
				
				flag1=0;
			}
		}
		
		
	}
	
	
	public void buttonClick(View v)
	{
				    Intent it=new Intent(Quiz.this,Result.class);
				    it.putStringArrayListExtra("array_file",array_file);
				    it.putExtra("correct", correct);
				    it.putExtra("no", no);
				    startActivity(it);
				    finish();
		}
	}
	

