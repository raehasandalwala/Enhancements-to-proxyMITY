package com.example.mainproxymity;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * ********************************************************************************************
 * 
 * This class is deprecated and is no longer used in the application.
 * Nevertheless, it used to display the video in full view on double tap from the previous 
 * activity i.e. NewActivity and same to get back to previous activity after fetching video path and the seek time from that activity
 * 
 * ********************************************************************************************
 * */


public class FullVideoView extends Activity{
	
	VideoView vidView;
	String vidPath;
	static int timer;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.fullvideoview);
        
         vidPath = getIntent().getStringExtra("videoPath");
        int seekPos = getIntent().getIntExtra("seek", 0);
        
        vidView = (VideoView) findViewById(R.id.videoView);
        
        
        // Double Tap Implementation
        vidView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "vidView is Clicked", Toast.LENGTH_LONG).show();
				if(vidView.getCurrentPosition()-timer < 1500)
				{
					//playvideo(240000);
					Intent fullVidView = new Intent(FullVideoView.this, NewActivity.class);
					fullVidView.putExtra("videopath", vidPath);
					fullVidView.putExtra("seek", vidView.getCurrentPosition());
					startActivity(fullVidView);
					finish();
					
					
				}
				timer = vidView.getCurrentPosition();
				return false;
			}
		});
        
        playvideo(seekPos);
        
	}
	
	// Plays video
	void playvideo(int seek)
    {
    	//Toast.makeText(getApplicationContext(), clickStatus, Toast.LENGTH_LONG).show();
    	
    	MediaController mediaController = new MediaController(this);
		
		mediaController.setAnchorView(vidView);
		
		vidView.setMediaController(mediaController);
	
		//String vdp = getIntent().getStringExtra("videopath");
		
		vidView.setVideoPath(vidPath); 
		System.out.println("Seek TIme" + seek);
		
		//Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_LONG).show();
		vidView.requestFocus();
		vidView.start();
		vidView.seekTo(seek);
		vidView.resume();
    }
    

}
