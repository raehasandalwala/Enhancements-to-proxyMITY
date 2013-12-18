package com.example.mainproxymity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.R.color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager.OnDismissListener;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Scroller;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;


/**
 * ********************************************************************************************
 * 
 * This class plays the intended video clicked from the MainScreen(MainActivity).
 * Also, it contains 2 sliding drawer, one contains the list of thumbnails and other contains
 * XML contents and Transcripts as well
 * VideoView is featured with DoubleTap functionality which enables fullview and inlineview
 * on each double tap
 * Transcript text is clickable which directs to fullview of transcript and to that particular 
 * portion in video	
 * 
 * ********************************************************************************************
 * */

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class NewActivity extends Activity {
	
	int dy=0;				// Total no of Lines in SRT with text
	int startTime_array[];
	String filename="";
	String filelines_array[];
	String vdp="";
	int seekTo;
	
	VideoView view;
	ImageView b;
	Context ct;
	SpannableStringBuilder sbr;
	StringBuilder sb;
	String line;

	private boolean singleViewExpanded = false;  // Tracks the fullscreen or inline view

	private String xmlName;
	public static final int OPEN_BOOKMARK_ACTIVITY = 2;
	boolean stop = false;
	int index = 0;
	int flag=0,flag1=0,flag2=0;
	MediaPlayer audio = new MediaPlayer();
	String stringXmltoxml;
	LinearLayout.LayoutParams vlp;
	ArrayList<String> themeattributeval = new ArrayList<String>();
	ArrayList<String> slidename = new ArrayList<String>();
	ArrayList<String> starttime = new ArrayList<String>();
	ArrayList<String> coursename = new ArrayList<String>();
	ArrayList<String> speaker = new ArrayList<String>();
	List<Integer> myCoords = new ArrayList<Integer>();
	ArrayList<String> contact = new ArrayList<String>();
	ExpandableListAdapter mAdapter;
	AsyncTask<Void, Void, Void> async;
	ArrayList<ArrayList<String>> seektime = new ArrayList<ArrayList<String>>();
	int length;
	private String xmlPath;
							// In this block objects are declared, & defined later
	TextView txtv;
	ListView lv;
	SlidingDrawer slidingDrawer, slidingDrawer0; 
	LinearLayout parentLayout;
	
				// ##################################
	EditText transcriptxt;
	ExpandableListView epView;
	View v,v1;
	String audioPath=null;
	ArrayList<ArrayList<String>> children = new ArrayList<ArrayList<String>>();
	ArrayList<String> groups = new ArrayList<String>();
	static int timer;
	boolean viewStatus = false;
	@SuppressWarnings("deprecation")
	@SuppressLint("SdCardPath")
	
	//ListView lv=(ListView)findViewById(R.id.ListView1);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.newactivity);		// Activity thats is being inflated by this class
		ct=this;
		view = (VideoView)findViewById(R.id.videoView1);
		txtv=(TextView)findViewById(R.id.textView1);

		// Array of thumbnails to be displayed in the ListView
		ImageView []arr=MainActivity.thumbnail_MINI;
		String [] stringArray = MainActivity.ls;
		int no= stringArray.length;
		b = (ImageView) findViewById(R.id.b);
		 
		int h=arr.length;
		System.out.println(h);
		Thumbnail tm[]=new Thumbnail[no];
		for(int y=0;y<no;y++)
		{
		 tm[y]=new Thumbnail(arr[y],stringArray[y]);
		 
		 System.out.println(tm[y].title);
		}
		
		// Adding thumbnails to ListView
		Listad ad=new Listad(this,R.layout.listlayout,tm);
		lv=(ListView)findViewById(R.id.ListView1);
		lv.setAdapter(ad);
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(NewActivity.this,"Drag and Drop to view video",Toast.LENGTH_SHORT).show();			
			}
			
		});

		// Implements Drag n Drop on LongClick
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				Thumbnail t1=(Thumbnail)arg0.getItemAtPosition(pos);
				//System.out.println(t1.title);
				 ClipData.Item item = new ClipData.Item(t1.title);
				 
				 String tagString = item.getText().toString();
               //  System.out.println("i am"+tagString);
				ClipDescription str=new ClipDescription((CharSequence)t1.title,new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN});
				 ClipData dragData = new ClipData(str,item);
				System.out.println(dragData.toString());
				 View.DragShadowBuilder myShadow = new View.DragShadowBuilder(arg1);
				 
				 arg1.startDrag(dragData,  // the data to be dragged
	                        myShadow,  // the drag shadow builder
	                        arg1,      // no need to use local data
	                        0          // flags (not currently used, set to 0)
	            );
				  
				 arg1.setVisibility(View.VISIBLE);

				myDragEventListener mDragListen = new myDragEventListener(ct);

				// Sets the drag event listener for the View
				if(vdp.equals("/mnt/sdcard/proxyMITY/video/Introduction to DBMS.flv"))
				{
				async.cancel(true);
				}
				view.setOnDragListener(mDragListen);

				return false;
			}
			
		});
		vdp = getIntent().getStringExtra("videopath");
		seekTo = getIntent().getIntExtra("seek", 0);
	
		playvideo(seekTo);
	
	// Timer for this video as this video only contains Quiz	
	if(vdp.equals("/mnt/sdcard/proxyMITY/video/Introduction to DBMS.flv"))
	async = new Timer().execute();
		
		int len=vdp.length();
		String sub=vdp.substring(28,len);
		txtv.setText(sub);
		int index=sub.lastIndexOf(".");
		
			final LinearLayout l0=(LinearLayout)findViewById(R.id.ll0);
			parentLayout = l0;
	        final LinearLayout l1=(LinearLayout)findViewById(R.id.ll1);
	        final LinearLayout l3=(LinearLayout)findViewById(R.id.ll3);
	        final LinearLayout l2=(LinearLayout)findViewById(R.id.ll2);
	        final LinearLayout cl=(LinearLayout)findViewById(R.id.contentLayout);
	        final int orig_width=l1.getWidth();
	        vlp =(android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
	       System.out.println("previous width"+vlp.width);
	       LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(vlp.width+800,LayoutParams.FILL_PARENT);
      	   layoutParams.setMargins(vlp.leftMargin-175,0,vlp.rightMargin-175,0);
      	   l3.setLayoutParams(layoutParams);
	        final int left = ((LinearLayout.LayoutParams) vlp).leftMargin;
	        int right = ((LinearLayout.LayoutParams) vlp).rightMargin;
	        vlp =(android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
	        System.out.println("width"+vlp.width);
	        System.out.println("Left margin"+left);
	        System.out.println("Right margin"+right);
	        
	        v1 = getLayoutInflater().inflate(R.layout.hidden2,cl,false);
	       // Transcriptxt Editable area that contains whole text
	        transcriptxt = (EditText) v1.findViewById(R.id.editText1);
            v = getLayoutInflater().inflate(R.layout.hidden1,cl,false);
            epView = (ExpandableListView) v.findViewById(R.id.expandableListView1);
		   
		xmlName =sub.substring(0,index);
		String xmlPath=MainActivity.main_path+"xml/"+xmlName+".xml";
		// Displays XML content in tree structure
		if (new File(xmlPath).exists()) {
			try {

				stringXmltoxml = convertXMLFileToString();
				String stringXmlContent = getEventsFromAnXML(this);
				
				mAdapter = new MyExpandableListAdapter();
				
			
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				System.out.println("helooo"+e);
			e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
					Toast.makeText(getApplicationContext(),
					"One Continuous  Topic so theme/slide navigation is not needed ",
					Toast.LENGTH_SHORT).show();
		}
		
		// Sliding Button for Sliding Drawer: that contains list of video thumbnails
		final ImageView slideButton = (ImageView) findViewById(R.id.slideButton);
		// Sliding Button for Sliding Drawer: that contains XML contents & Transcripts  
		final ImageView slideButton0 = (ImageView) findViewById(R.id.slideButton0);
		
	    slidingDrawer = (SlidingDrawer) findViewById(R.id.SlidingDrawer);
	    slideButton.setBackgroundResource(R.drawable.images);
	    
	    slidingDrawer0 = (SlidingDrawer) findViewById(R.id.SlidingDrawer0);
	    slideButton0.setBackgroundResource(R.drawable.images1);
	    
	        slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
	            @Override
	            public void onDrawerOpened() {
	               slideButton.setBackgroundResource(R.drawable.images1);
	             
	               v = getLayoutInflater().inflate(R.layout.hidden1,cl,false);
	               try
					 {
	            	   b.setTag(R.drawable.cloud);
	            	   b.setImageResource(R.drawable.cloud);
	            	   System.out.println("when drawer opened" + (Integer)b.getTag() +R.drawable.cloud);
	            //	   b.setBackgroundColor(Color.WHITE);
	            	   
			             epView = (ExpandableListView) v.findViewById(R.id.expandableListView1);
			             epView.setAdapter(mAdapter);
							epView.expandGroup(0);
							epView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
								public boolean onGroupClick(ExpandableListView arg0,
										View arg1, int groupPosition, long arg3) {
									if (groupPosition == 5) {

									}
									return false;
								}
							});

							epView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
								public boolean onChildClick(ExpandableListView parent,
										View v, int groupPosition, int childPosition,
										long id) {
									int pos = convertInMili(seektime.get(groupPosition)
											.get(childPosition));
									view.seekTo(pos);
									view.start();
									
									return false;
								}

							});
		     			
					 }
					   catch(Exception ex)
				          {
				        	  Toast.makeText(getApplicationContext(), "XML file doesn't exist", Toast.LENGTH_SHORT).show();
				          }
	              
	             cl.addView(v,new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,0,0.8f));
	             if(!slidingDrawer0.isOpened())
	             {
	            	 vlp =(android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
	            	 LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(vlp.width-200,LayoutParams.FILL_PARENT);
	            	 layoutParams.setMargins(vlp.leftMargin,0,0,0);
	            	 l3.setLayoutParams(layoutParams);
	            	 vlp =(android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
	            	 int left1 = ((LinearLayout.LayoutParams) vlp).leftMargin;
	            	 int width1=vlp.width;
	            	 System.out.println("now width"+width1);
	            	 System.out.println("now left"+left1);
	             }
	             else
	             {
	            	 vlp =(android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
	            	 LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(vlp.width-200,LayoutParams.FILL_PARENT);
	            	 layoutParams.setMargins(vlp.leftMargin,0,0,0);
	            	 l3.setLayoutParams(layoutParams);


	             }
	           
	            
	 		   
	 		       
	            }
	        });
	 
	        slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
	            @Override
	            public void onDrawerClosed() {
	                slideButton.setBackgroundResource(R.drawable.images);
	                cl.removeView(v);
	                
	                if(!slidingDrawer0.isOpened())
		             {
	                	 vlp =(android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
	                	 System.out.println("first"+vlp.width);
		            	 LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(vlp.width+200,LayoutParams.FILL_PARENT);
		            	 layoutParams.setMargins(vlp.leftMargin,0,vlp.rightMargin-175,0);
		            	 l3.setLayoutParams(layoutParams);
		            	 vlp =(android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
		               	 System.out.println("second"+vlp.width);
		            	 
		             }
		             else
		             {
		            	 vlp =(android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
		            	 LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(vlp.width+200,LayoutParams.FILL_PARENT);
		            	 layoutParams.setMargins(vlp.leftMargin,0,vlp.rightMargin-175,0);
		            	 l3.setLayoutParams(layoutParams);
		             }
		           
	             
	            
	                
	            }
	        });
	        slidingDrawer0.setOnDrawerOpenListener(new OnDrawerOpenListener() {
	            @Override
	            public void onDrawerOpened() {
	                slideButton0.setBackgroundResource(R.drawable.images);
	                if(!slidingDrawer.isOpened())
		             {
	                	vlp =(android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
		            	 LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(vlp.width-200,LayoutParams.FILL_PARENT);
		            	 layoutParams.setMargins(0,0,vlp.rightMargin,0);
		            	 l3.setLayoutParams(layoutParams);
		            	 vlp =(android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
		            	 int left1 = ((LinearLayout.LayoutParams) vlp).leftMargin;
		            	 int width1=vlp.width;
		            	 System.out.println("now width"+width1);
		            	 System.out.println("now left"+left1);
		             }
		             else
		             {
		            	 vlp =(android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
		            	 LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(vlp.width-200,LayoutParams.FILL_PARENT);
		            	 layoutParams.setMargins(0,0,vlp.rightMargin,0);
		            	 l3.setLayoutParams(layoutParams);


		             }
	                
	            }
	        });
	 
	        slidingDrawer0.setOnDrawerCloseListener(new OnDrawerCloseListener() {
	            @Override
	            public void onDrawerClosed() {
	                slideButton0.setBackgroundResource(R.drawable.images1);
	                if(!slidingDrawer.isOpened())
		             {
	                	 vlp =(android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
	                	 System.out.println("first"+vlp.width);
		            	 LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(vlp.width+200,LayoutParams.FILL_PARENT);
		            	 layoutParams.setMargins(vlp.leftMargin-175,0,vlp.rightMargin,0);
		            	 l3.setLayoutParams(layoutParams);
		            	 vlp =(android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
		               	 System.out.println("second"+vlp.width);
		            	 
		             }
		             else
		             {
		            	 vlp =(android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
		            	 LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(vlp.width+200,LayoutParams.FILL_PARENT);
		            	 layoutParams.setMargins(vlp.leftMargin-175,0,vlp.rightMargin,0);
		            	 l3.setLayoutParams(layoutParams);
		             }
		           
	             
	               
	            }
	        });
	        
	        // Toggle Button that switches between: View Contents & View Transcripts
	        b.setOnClickListener(new OnClickListener()
	        {
	        	@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					System.out.println("on button click");
					if((Integer)b.getTag()==R.drawable.cloud)
					{System.out.println("in view transcript");
					
						cl.removeView(v);
						
						  v1 = getLayoutInflater().inflate(R.layout.hidden2,cl,false);
				          try{
				        	  b.setTag(R.drawable.cloud2);
				        	  b.setImageResource(R.drawable.cloud2);
						  transcriptxt = (EditText) v1.findViewById(R.id.editText1);
				            transcriptxt.setText(sbr);
			     			 transcriptxt.setMovementMethod(LinkMovementMethod.getInstance());
			     			
				          }
				          catch(Exception ex)
				          {
				        	  Toast.makeText(getApplicationContext(), "Transcript file doesn't exist", Toast.LENGTH_SHORT).show();
				          }
			     			 
			                cl.addView(v1,new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,0.8f));
			              
					}
					
					else
					{
					if((Integer)b.getTag()==R.drawable.cloud2)
					
					{
						cl.removeView(v1);
						//cl.removeView(b);
						  v = getLayoutInflater().inflate(R.layout.hidden1,cl,false);
						 try
						 {
				             epView = (ExpandableListView) v.findViewById(R.id.expandableListView1);
				             epView.setAdapter(mAdapter);
								epView.expandGroup(0);
								epView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
									public boolean onGroupClick(ExpandableListView arg0,
											View arg1, int groupPosition, long arg3) {
										if (groupPosition == 5) {

										}
										return false;
									}
								});

								epView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
									public boolean onChildClick(ExpandableListView parent,
											View v, int groupPosition, int childPosition,
											long id) {
										int pos = convertInMili(seektime.get(groupPosition)
												.get(childPosition));
										view.seekTo(pos);
										view.start();
										
										return false;
									}

								});
			     			
			     			 b.setTag(R.drawable.cloud);
			     			 b.setImageResource(R.drawable.cloud);
						 }
						   catch(Exception ex)
					          {
					        	  Toast.makeText(getApplicationContext(), "XML file doesn't exist", Toast.LENGTH_SHORT).show();
					          }
			     			  cl.addView(v,new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,0.8f));
			                //cl.addView(b,new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,0.2f));
			              
					}
					}
					
				}
	        	
	        });
		       
		/** Below specified block implements Transcript for the SRT of the video */
// ########## Implementation of Transcript ##############		 
	        	
	        	/** Inner Class: MyClickableSpan, makes each line of SRT clickable*/
				   class MyClickableSpan extends ClickableSpan
					{	 //clickable span
						
						public void onClick(View textView) 
						{
							//	do something

							//Toast.makeText(getApplicationContext(), "Clicked",
					       // Toast.LENGTH_SHORT).show();
							playvideo(10*60*1000);
							//playvideo(startTime_array[loop]);
						}
						
						@Override
						public void updateDrawState(TextPaint ds) 
						{
							ds.setColor(Color.BLACK);//set text color 
							//ds.setUnderlineText(false); // set to false to remove underline
						} 
					}
				    

				   /** Inner Class: InheritSpannableString, sets span for each line of SRT */
				   class InheritSpannableString extends SpannableString
					{
						int index;
						
						public InheritSpannableString(CharSequence source) {
							super(source);
							// TODO Auto-generated constructor stub
						}

					
						
						
					}
				   
		        String srtFile="";  // Stores the path of available SRT file for that particular video
		        // "filename" contains the path of the .text file of SRT that would generate Transcript
		        // at run-time
		        
		        final int lines;
		        int totallines=0, sequence=0;	// Totallines in SRT & no of sequences in SRT
				
				try
				 {
					
				/** Initially SRT's were available for only these two videos so Transcripts are 
				 * generated for the same	
				 */
					if(vdp.equals("/mnt/sdcard/proxyMITY/video/Differential Equations.flv"))				
					{	srtFile = "/mnt/sdcard/proxyMITY/text/differential equations .srt";
						filename = "/mnt/sdcard/proxyMITY/text/srttotext.txt";
					}	
					
					else if(vdp.equals("/mnt/sdcard/proxyMITY/video/Introduction to DBMS.flv"))
					{	srtFile = "/mnt/sdcard/proxyMITY/text/dbms.srt";
						filename = "/mnt/sdcard/proxyMITY/text/srttotext.txt";
					}	
						
					
					
				//		srtFile = "/mnt/sdcard/proxyMITY/text/dbms.srt";
						
						
				//	FileReader fr = new FileReader("/mnt/sdcard/proxyMITY/text/madmen.srt");
					FileReader fr = new FileReader(srtFile);
					
					FileWriter fwr = new FileWriter(filename, false);

					
					BufferedReader reader = new BufferedReader(fr);
					BufferedWriter writer = new BufferedWriter(fwr);
					
					
					sb = new StringBuilder(512);
					 
					int flag=0;
					while((line = reader.readLine()) != null)
				    {/**
				       *Writes line-by-line from SRT file to text file located at "filename"
				       **/
				    
						  flag++;
						  
						  writer.write(line, 0, line.length());
					      writer.newLine();
					      
					      if(flag==1) sequence++;
					      if(line.isEmpty()) flag=0;
					  			     
					      totallines++;
					}
					  System.out.println("TotalLines: " + totallines);
							//Close the stream:
					reader.close();
					writer.close();
					
				//	sb.toString();
				//	lines = totallines - ((sequence-1)*3+2);
				//	filelines_array = new String[lines];		// No. of lines containing Text: 1192
				//	startTime_array = new int [lines];
					
				 }
				catch(FileNotFoundException fnfe)
				{	// If SRT file doesn't exist
					fnfe.printStackTrace();
					Toast file_not_found_msg = Toast.makeText(getApplicationContext(), "Transcript file doest not exist", Toast.LENGTH_SHORT);
					file_not_found_msg.show();
					
				}
				catch (IOException e) 
				 {	// For error in IO operation, in case of read-write failure
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					Toast file_not_found_msg = Toast.makeText(getApplicationContext(), "Transcript file__doest not exist", Toast.LENGTH_SHORT);
					file_not_found_msg.show();
				 } 
				
				// No of lines that contain actual text in SRT are calculated here
				lines = totallines - ((sequence-1)*3+2);
				
				//Array storing each line as each element of an array
				filelines_array = new String[lines];		// No. of lines containing Text: 1192
				startTime_array = new int [lines];
				System.out.println("Lines: " + lines);
				
		//	   TextView transcriptxt = (TextView) findViewById(R.id.textView3);
				
			  
				try
				{
					// Reads the text file created from SRT and stores it as String
				String transcript_filetext = readLines(filename);
				}
				catch(ArrayIndexOutOfBoundsException boundExp)
				{
					// In case SRT files a'int available, Array could throw this exception
					boundExp.printStackTrace();
					Toast file_not_found_msg = Toast.makeText(getApplicationContext(), "Transcript & SRT file doest not exist", Toast.LENGTH_SHORT);
					file_not_found_msg.show();
				}
			   System.out.println("File Read");
				//transcriptxt.setText(sb);
			   int start, end=0;
			    
			   sbr = new SpannableStringBuilder("");
			   //SpannableString ss = new SpannableString(transcript_filetext);
			   try{
			   int loop=0;
			   System.out.println("Filelines_Array 1st index: " + filelines_array[0] + " and length: " + filelines_array[0].length());
			   while(loop<lines)
			   {
				   // Sets clickable span for each line of the Transcript text
			    	 
			    final InheritSpannableString ss = new InheritSpannableString(filelines_array[loop]+"\n");
			    	
			    	System.out.println("Array Created" + ss);
			    	ss.index = startTime_array[loop];
			    	
			    	start = 0;
			    	end = filelines_array[loop].length();
			    	System.out.println("Start: " + start + "End: " + end);
			    	
			    	// Calls FullTranscriptView to gain full view 
			    	ss.setSpan(new ClickableSpan(){
			    		// Override onClick()
			    		public void onClick(View textView) 
			    		{
			    			//	do something

			  //  			System.out.println("ss[loop].index = " + ss[loop].index);
			    			Toast.makeText(NewActivity.this, "Clicked" + ss.toString() + "\n" + ss.index ,
			    	        Toast.LENGTH_SHORT).show();
			    			
			    			Intent fullView = new Intent(NewActivity.this, FullTransciptView.class);
			    			
			    			fullView.putExtra("startingTime", startTime_array);
			    			fullView.putExtra("transcriptLines", filelines_array);
			    			fullView.putExtra("length", lines);
			    			fullView.putExtra("videopath", vdp);
			    			fullView.putExtra("seek", ss.index);
			    			
			    			startActivity(fullView);
			    			finish();
			    	//		Intent action = new Intent(Intent.ACTION_VIEW);
			    		//	playvideo(seekTo);
			    		//	playvideo(ss.index);
			    		//	playvideo(4*60*1000);//playvideo(10*60*1000);
			    		}}, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			    	loop++;
			    	
			    	System.out.println("Appending");
			    	
			    	sbr.append(ss);
			    	
			    	System.out.println(sbr);
			    }
  
     			  transcriptxt.setText(sbr);
     			 transcriptxt.setMovementMethod(LinkMovementMethod.getInstance());
			  }
			  catch(NullPointerException ne)
			  {
				  ne.printStackTrace();
			
			  }
				
			
// ###########			  Transcript Implemented ############
			   
			   // Implements Double Tap on VideoView: interval of 1.5 seconds
			   
			  view.setOnTouchListener(new View.OnTouchListener() {
					
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "vidView is Clicked", Toast.LENGTH_LONG).show();
						if(view.getCurrentPosition()-timer < 1500)
						{
							//playvideo(240000);
						//	Intent fullVidView = new Intent(NewActivity.this, FullVideoView.class);
						//	fullVidView.putExtra("videoPath", vdp);
						//	fullVidView.putExtra("seek", view.getCurrentPosition());
						//	startActivity(fullVidView);
						//	finish();
							if(!viewStatus)
							{
								slidingDrawer.close();
								slidingDrawer0.close();
								viewStatus = true;
							}	
							else
							{
								slidingDrawer.open();
								slidingDrawer0.open();
								viewStatus = false;
							}
							
						}
						timer = view.getCurrentPosition();
						return false;
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main,menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		stop = true;
		
		index = 0;
		switch (item.getItemId()) {

		case R.id.bookmark:
			Intent i1 = new Intent(NewActivity.this, Bookmark.class);
			int duration = view.getDuration();
			int current = view.getCurrentPosition();
			i1.putExtra("duration", duration);
			i1.putExtra("current", current);
			startActivityForResult(i1, OPEN_BOOKMARK_ACTIVITY);
			if(vdp.equals("/mnt/sdcard/proxyMITY/video/Introduction to DBMS.flv"))
			{
			async.cancel(true);
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}


	public int convertInMili(String string) {
		// TODO Auto-generated method stub
		String sub1 = string.substring(0, 2);
		String sub2 = string.substring(3, 5);
		String sub3 = string.substring(6);
		int a1 = Integer.parseInt(sub1);
		int a2 = Integer.parseInt(sub2);
		int a3 = Integer.parseInt(sub3);

		return (a1 * 3600 + a2 * 60 + a3 * 1) * 1000;
	}
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		System.out.println("vdp" +vdp);
		if(vdp.equals("/mnt/sdcard/proxyMITY/video/Introduction to DBMS.flv"))
		{
		async.cancel(true);
		System.out.println("position on back pressed " + view.getCurrentPosition());
		}
		
	}
	
	
 //class timer that runs in background for Quiz to pop-up after specified time interval
	class Timer extends AsyncTask<Void,Void,Void>
	{
		@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
//		if(vdp.equals("/mnt/sdcard/proxyMITY/video/Introduction to DBMS.flv"))
//	{	
		File f = new File("/mnt/sdcard/proxyMITY/text/SQL.txt");
		if(f.exists())
		{		
		while(true)
		{
			if(isCancelled())
				break;
			if(((view.getCurrentPosition())>=300000))
			{
				System.out.println("inside if");
				
				view.pause();
				
				Intent i = new Intent(NewActivity.this,Quiz0.class);
				startActivity(i);
				break;
				
			}
		}
		
		}
//	}	
		return null;
	}

		
	}
	
//class expandable listview
	public class MyExpandableListAdapter extends BaseExpandableListAdapter {
		public Object getChild(int groupPosition, int childPosition) {
			return children.get(groupPosition).get(childPosition);
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			int i = 0;
			try {
				i = children.get(groupPosition).size();

			} catch (Exception e) {
			}

			return i;
		}

		public TextView getchildGenericView() {
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);

			TextView textView = new TextView(NewActivity.this);
			textView.setLayoutParams(lp);

			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			textView.setTypeface(null, Typeface.NORMAL);
			textView.setTextSize(16);
			textView.setPadding(36, 10, 5, 5);
			return textView;
		}

		public TextView getgroupGenericView() {
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);

			TextView textView = new TextView(NewActivity.this);
			textView.setLayoutParams(lp);

			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			textView.setTypeface(null, Typeface.BOLD);
			textView.setTextSize(18);
			textView.setPadding(55, 10, 0, 0);
			return textView;
		}

		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			TextView textView = getchildGenericView();
			textView.setText(getChild(groupPosition, childPosition).toString());
			return textView;
		}

		public Object getGroup(int groupPosition) {
			return groups.get(groupPosition);
		}

		public int getGroupCount() {
			return groups.size();
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			TextView textView = getgroupGenericView();
			textView.setText(getGroup(groupPosition).toString());
			return textView;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		public boolean hasStableIds() {
			return true;
		}

	}
	public void onPause() {
		super.onPause();
		stop = true;
		index = 0;
	 length=view.getCurrentPosition();
	}
	
	public void onResume() {
		super.onResume();
		if(length!=0)
		view.seekTo(length);
		view.start();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	private String getEventsFromAnXML(Activity activity)
			throws XmlPullParserException, IOException {
		
		ArrayList<String> a = new ArrayList<String>();
		System.out.println("Yay");
	//	ArrayList<String> linkattribute = new ArrayList<String>();
		//ArrayList<String> linkattributeval = new ArrayList<String>();
		ArrayList<String> themeattribute = new ArrayList<String>();
		//ArrayList<String> linkurl = new ArrayList<String>();
		ArrayList<String> endtime = new ArrayList<String>();
		ArrayList<String> videoname = new ArrayList<String>();
		ArrayList<String> present = new ArrayList<String>();
		String test = null;
		
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	
		factory.setValidating(false);
		
		XmlPullParser xpp = factory.newPullParser();

		xpp.setInput(new StringReader(stringXmltoxml));
		
		xpp.nextToken();
		int eventType = xpp.getEventType();

		int attributecount = 0;
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_DOCUMENT) {
				a.add("--- Start XML ---");
			} else if (eventType == XmlPullParser.START_TAG) {
				a.add(xpp.getName());

				test = a.get(a.size() - 1);

				attributecount = xpp.getAttributeCount();
				if (attributecount != 0) {
					for (int i = 0; i < attributecount; i++) {
						a.add(xpp.getAttributeName(i));
						a.add(xpp.getAttributeValue(i));

						/*if ((test.equalsIgnoreCase("Link"))) {
							linkattribute.add(xpp.getAttributeName(i));
							linkattributeval.add(xpp.getAttributeValue(i));
						}*/

						if ((test.equalsIgnoreCase("Theme"))) {
							themeattribute.add(xpp.getAttributeName(i));
							themeattributeval.add(xpp.getAttributeValue(i));
						}

					}
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				a.add(xpp.getName());
			} else if (eventType == XmlPullParser.TEXT) {
				a.add(xpp.getText());

				/*if ((test.equalsIgnoreCase("linkurl"))) {
					linkurl.add(xpp.getText());
				}*/

				if ((test.equalsIgnoreCase("slidename"))) {
					String dg1 = xpp.getText();
					/*int j = dg.lastIndexOf('.');
					String dg1 = dg.substring(0, j);*/
					slidename.add(dg1);
				}

				if ((test.equalsIgnoreCase("starttime"))) {
					starttime.add(xpp.getText());
				}

				if ((test.equalsIgnoreCase("endtime"))) {
					endtime.add(xpp.getText());
				}

				if ((test.equalsIgnoreCase("videoname"))) {
					videoname.add(xpp.getText());
				}

				if ((test.equalsIgnoreCase("coursename"))) {
					coursename.add(xpp.getText());
				}

				if ((test.equalsIgnoreCase("presentation"))) {
					present.add(xpp.getText());
				}

				if ((test.equalsIgnoreCase("speaker"))) {
					speaker.add(xpp.getText());
				}

				if ((test.equalsIgnoreCase("contact"))) {
					contact.add(xpp.getText());
				}

			}

			eventType = xpp.next();
		}
		a.add("\n--- End XML ---");
		// System.out.println("content of array list" + a);
		
		System.out.println("content of array theme attribute" + themeattribute);
		System.out.println("content of array theme attribute val"
				+ themeattributeval);
		System.out.println("content of array  slidename" + slidename);
		System.out.println("content of array  starttime" + starttime);
		System.out.println("content of array endtime " + endtime);

		System.out.println("content of array videoname " + videoname);
		System.out.println("content of array coursename " + coursename);
		System.out.println("content of array presentation " + present);
		System.out.println("content of array speaker " + speaker);
		System.out.println("content of array contact " + contact);

	

		int num = UniqueValues();
		System.out.println(num);
		for (int j = 0, k = 0; j < themeattributeval.size(); j++) {
			if (!containsValue(groups, themeattributeval.get(j))) {
				groups.add(k++, themeattributeval.get(j));
			}
		}
		System.out.println(groups.size());
		for (int i = 0; i < groups.size(); i++) {
			System.out.println(groups.get(i));
		}
		int count = 1;
		for (int i = 0; i < themeattributeval.size() - 1; i++) {
			if (themeattributeval.get(i).equals(themeattributeval.get(i + 1))) {
				count++;
			} else {
				myCoords.add(count);
				count = 1;

			}
		}
		myCoords.add(count);
		int k = 0;
		for (int i = 0; i < groups.size(); i++) {
			ArrayList<String> row = new ArrayList<String>();
			ArrayList<String> row1 = new ArrayList<String>();
			for (int j = 0; j < myCoords.get(i); j++) {
				row.add(slidename.get(k));
				row1.add(starttime.get(k));
				k++;
			}

			children.add(row);
			seektime.add(row1);
		}
		System.out.println("children array " + children);
		return a.toString();

	}

	private int UniqueValues() {
		ArrayList<String> values = new ArrayList<String>();
		int count = 0;
		for (int j = 0; j < themeattributeval.size(); j++) {
			if (!containsValue(values, themeattributeval.get(j)))
				values.add(count++, themeattributeval.get(j));
		}
		return count;
	}

	private static boolean containsValue(ArrayList<String> array, String target) {
		for (int j = 0; j < array.size(); j++) {
			if (array.get(j) != null && array.get(j).equals(target))
				return true;
		}
		return false;
	}

	public String convertXMLFileToString() throws IOException {
		String xmlPath=MainActivity.main_path+"xml/"+xmlName+".xml";
		BufferedReader br = new BufferedReader(
				new FileReader(new File(xmlPath)));
	//	System.out.println("Yay");
		String line;
		StringBuilder sb = new StringBuilder();

		while ((line = br.readLine()) != null) {
			sb.append(line.trim());
		}
		br.close();

		return sb.toString();

	}
	
	public class StructureOfList {
		String txt;
		int i;
		int ti, tf, sleep;

		public StructureOfList(int timeStart, int DurationOfSleep, int timeEnd,
				int number, String text) {
			txt = text;
			i = number;
			ti = timeStart;
			tf = timeEnd;
			sleep = DurationOfSleep;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == OPEN_BOOKMARK_ACTIVITY) {
			if (resultCode == 3) {
				int pos = data.getIntExtra("time", requestCode);
				view.seekTo(pos);
				view.start();
			} else if (resultCode == RESULT_CANCELED) {
				Log.d("proxymity", "result cancel");
			}
		}
	}

	// This method reads every line of text file generated from SRT & makes a complete string
	// of text
	public String readLines(String filename)
	{
		int flag=0;
		int timeArrayIndex = 0;
		int array_index = 0;
		int time=0, startTime=0, endTime=0;
		
		String filetxt="";
		String start,end;
		String start_hrs, start_min, start_sec, start_ms;
		String end_hrs, end_min, end_sec, end_ms;
		
		
		String filelines;
		String filelines_trim = "";
		try 
		{
			dy=0;

			FileReader freader = new FileReader(filename);
			
			Scanner scan = new Scanner(freader);
				
			scan.useDelimiter("\n");
			
			
			
			while(scan.hasNext())				// Condition: !scan.hasNext(regex)
			{
				/**
				 * SRT format:
				 * Line 1: SRT sequence
				 * Line 2: Start Time(hh,mn,sc:ms) --> End Time(hh,mn,sc:ms)
				 * Line 3: Actual text
				 * Line 4: Empty Line
				 * */
				filelines = scan.next();
	
				flag++;
				
				try
				{
					if(filelines.isEmpty())
					{	
						flag = 0;
						
						filelines_trim = "";	// Srt sequence of lines
					//	no_of_lines+=3;
					//	no_of_seq++;
						continue;
						//break;
					}
				}
				catch(NullPointerException ne)
				{
					System.out.println("null throwed or String contains null");
				}
				
				// Flag==2 fetches the start and end time for that particular text
				if(flag==2)			// 2nd line of SRT file contains duration of display
				{
				//	duration = getTime(filelines);		// Retrieves time 
					start = filelines.substring(0,12);
					end = filelines.substring(17,29);
					
					start_hrs = start.substring(0, 2);
					start_min = start.substring(3, 5);
					start_sec = start.substring(6, 8);
					start_ms = start.substring(9, 12);
					
					end_hrs = end.substring(0, 2);
					end_min = end.substring(3, 5);
					end_sec = end.substring(6, 8);
					end_ms = end.substring(9, 12);
					
					
					startTime = (Integer.parseInt(start_hrs))*3600000 + (Integer.parseInt(start_min))*60000 + (Integer.parseInt(start_sec))*1000 + (Integer.parseInt(start_ms));
					endTime = (Integer.parseInt(end_hrs))*3600000 + (Integer.parseInt(end_min))*60000 + (Integer.parseInt(end_sec))*1000 + (Integer.parseInt(end_ms));
					
					
					startTime_array[timeArrayIndex] = startTime;
					timeArrayIndex++;
					
				//	duration = endTime - startTime;
				}
			
				// Flag==3 fetches text for that corresponding sequence
				if(flag>2)
				{
					filetxt = filetxt + filelines + "\n";
					filelines_trim = filelines_trim + filelines + "\n";
				//	no_of_lines++;
					dy++;
					filelines_array[array_index] = filelines;
					array_index++;
				}
					
				
			}
			
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	//	loop=no_of_lines;
	
		return filetxt;
	}
	
	// Plays video for corresponding video with pre-set seek time(0, if not)
	void playvideo(int seek)
	{
		Integer s = new Integer(seek);
	//	Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_LONG).show();

		MediaController mediaController = new MediaController(this);
		
		mediaController.setAnchorView(view);
				
		view.setMediaController(mediaController);
		
		view.setVideoPath(vdp); 
		System.out.println("Seek TIme" + seek);
		
		//Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_LONG).show();
		view.requestFocus();
		view.start();
		view.seekTo(seek);
		view.resume();	
	} 
}
