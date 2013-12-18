package com.example.mainproxymity;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


/**
 * ********************************************************************************************
 * 
 * This class displays full view of clickable transcript text along with the small video view
 * at top-left corner so as to tap back to NewActivity(main video playing screen)
 * 
 * ********************************************************************************************
 * */

public class FullTransciptView  extends Activity{
	
	VideoView tempVidView;
	String tempVidPath="";
	int seekPos;
	MediaPlayer audio;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fulltranscriptview);	// Activity thats is being inflated by this class
		TextView transcriptView = (TextView) findViewById(R.id.transcriptTextView);
		transcriptView.setBackgroundColor(Color.rgb(25,25,112));
		transcriptView.setTextSize(20.0f);
		
		int loop=0;
		
		// Data passed from Previous Activity i.e NewActivity
		seekPos = getIntent().getIntExtra("seek", 0);
		
		int len = getIntent().getIntExtra("length", 1);
		int startTime[] = getIntent().getIntArrayExtra("startingTime");
		String filelines[] = getIntent().getStringArrayExtra("transcriptLines");
		final String vidPath = getIntent().getStringExtra("videopath");
		
		final VideoView playVideo = (VideoView) findViewById(R.id.videoView1);
		
		tempVidPath = vidPath;
		tempVidView = playVideo;
		
		playvideo(seekPos);
		
		playVideo.setOnTouchListener(new View.OnTouchListener() {	
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Intent inlineView = new Intent(FullTransciptView.this, NewActivity.class);
    			inlineView.putExtra("videopath", vidPath);
    			inlineView.putExtra("seek", playVideo.getCurrentPosition());
    		//	Toast.makeText(getApplicationContext(), ss.toString() + "\n" + ss.index, Toast.LENGTH_LONG).show();
    			
    			startActivity(inlineView);
    			finish();
				return false;
			}
		});
		
		// Sets clickable span for each text line
		SpannableStringBuilder sbr = new SpannableStringBuilder("");
		  try{
			    while(loop<len)
			    {
			    	int start = 0;
			    	int end = filelines[loop].length(); 
			    	final InheritSpannableString ss = new InheritSpannableString(filelines[loop]+"\n");
			    	
			    	System.out.println("Array Created");
			    	ss.index = startTime[loop];
			    	
			    	start = 0;
			    	end = filelines[loop].length();
			    	System.out.println("Start: " + start + "End: " + end);
			    	ss.setSpan(new ClickableSpan(){
			    		// Override onClick()
			    		public void onClick(View textView) 
			    		{
			    			playvideo(ss.index);
			    			
			    		}			
			    		public void updateDrawState(TextPaint ds) 
						{
							ds.setColor(Color.WHITE);//set text color
							
							//ds.setUnderlineText(false); // set to false to remove underline
						} }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			    	loop++;
			    	sbr.append(ss);
			    }
			    
			    transcriptView.setText(sbr);
			   
			    transcriptView.setMovementMethod(LinkMovementMethod.getInstance());
			  
		  }    
			  catch(NullPointerException ne)
			  {
				  ne.printStackTrace();
			 
			  }
	
	}
	
	// Plays Video
	public void playvideo(int seek)
	{
		
		MediaController mediaController = new MediaController(this);
		
		mediaController.setAnchorView(tempVidView);

		tempVidView.setMediaController(mediaController);

		tempVidView.setVideoPath(tempVidPath); 
		System.out.println("Seek TIme" + seekPos);
		
		//Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_LONG).show();
		tempVidView.requestFocus();
		tempVidView.start();
		tempVidView.seekTo(seek);
		tempVidView.resume();
	}
	
	public void finish()
	{
		super.finish();
	//	audio.stop();
	}
	
	// Inherited class that enables Spannable feature with little OOD
	 class InheritSpannableString extends SpannableString
	{
			int index;
			
			public InheritSpannableString(CharSequence source) 
			{
				super(source);
				// TODO Auto-generated constructor stub
			}	
	}

}
