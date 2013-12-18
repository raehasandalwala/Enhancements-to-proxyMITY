package com.example.mainproxymity;





import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.URL;
//import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
//import android.content.Context;
//import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
//import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
import android.os.Bundle;
//import android.os.Environment;
import android.provider.MediaStore.Images.Thumbnails;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
//import android.view.ViewManager;
//import android.view.WindowManager;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
import android.widget.ImageView;
import android.widget.Button;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
//import android.widget.Toast;
import android.widget.Toast;



/**
 * ********************************************************************************************
 * 
 * This class displays the thumbnails of all the videos in the specified folder that is either
 * present in ext-sd, sdcard or in external_sd.
 * Also, the thumnails are displayed in the Table-Layout(4 columns & rows are added dynamically
 * to accomodate as many videos as there are in the specified folder)
 * 
 * ********************************************************************************************
 * */
@SuppressLint("SdCardPath")
public class MainActivity extends Activity {
	// Initializations and declarations of objects and variables
	 public static ImageView[] thumbnail_MINI;
public static String ls[];
	static String folder_path,main_path;
	  List<String> folder;  
		TableLayout t1;
		 TableRow tr;
		 TextView txt,tv;
		 View v;
		 ImageView img;
		 final Context context = this;
			private Cursor videocursor;
			 AlertDialog help_dialog;
			    private ProgressDialog mProgressDialog, progressBar;
	  ///newwwwww
	  int i = 0;										///newwwwww
	ContentResolver cr;
	 File checkTar;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//System.out.println("out if");
		setContentView(R.layout.activity_main);		// Activity thats is being inflated by this class
				
		try
		{
			// folder to be searched to create video's thumbnails
			File list=new File("/mnt/extsd/proxyMITY/video/");
			folder=getListOfFiles("/mnt/sdcard/proxyMITY/video/");
			
			if (list.exists()||new File("/mnt/sdcard/proxyMITY/video/").exists()||new File ("/mnt/external_sd/proxyMITY/video/").exists())
				
			{
				// First it searches if extsd card is present
				if (list.exists())
				{	folder = getListOfFiles("/mnt/extsd/proxyMITY/video/");
				 System.out.println("folder"+folder);
				 folder_path="/mnt/extsd/proxyMITY/video/";
				 main_path="/mnt/extsd/proxyMITY/";
				}
				
				// Else it searches for internal sdcard
				else if(new File("/mnt/sdcard/proxyMITY/video/").exists())
				{
						
					 folder = getListOfFiles("/mnt/sdcard/proxyMITY/video/");
					 System.out.println("folder"+folder);
					 folder_path="/mnt/sdcard/proxyMITY/video/";
					 main_path="/mnt/sdcard/proxyMITY/";
				}
				//Else it searches for external_sd 
				else if(new File ("/mnt/external_sd/proxyMITY/video/").exists())
				{
					
					 folder = getListOfFiles("/mnt/external_sd/proxyMITY/video/");
					 System.out.println("folder"+folder);
					 folder_path="/mnt/external_sd/proxyMITY/video/";
					 main_path="/mnt/external_sd/proxyMITY/";
				}
				
		 // Creates Table Layout that conatins all the thumbnails of video		
		 t1=(TableLayout)findViewById(R.id.tableLayout1);
		 // String Array containg the path & ofcourse name of each video 
		 ls=new String[folder.size()];
	
		 // Extracts the size of the folder containing videos
		 int k=folder.size();
		
		 // ImageView array that contains thumbnails of all the videos
		 thumbnail_MINI=new ImageView[k];
		 
		 // Extracts the video path from the list of files
		 ls = folder.toArray(ls);
		   for(String s : ls)
		        System.out.println(s);
			String file_path;
		    
		// Loop to create thumbnail for each & every video	
		for(int j=0;j<ls.length;j++)
		 {
		 file_path=folder_path+ls[j];
		 v =  getLayoutInflater().inflate(R.layout.hidden,t1,false);
		 tv = (TextView) v.findViewById(R.id.text);
		 img = (ImageView) v.findViewById(R.id.image);
		 		
		 			// calls createThumbNails: that actually creates thumbnails
		 thumbnail_MINI = createThumbNails(file_path,thumbnail_MINI);
		
		 final String flp = file_path;
		 final String flp2 = ls[j];
		 
		// Calls NewActivity: Video Playing Screen, on Click of any video thumbnails
		img.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stubs
					Intent nextScreen = new Intent(MainActivity.this, NewActivity.class);
					nextScreen.putExtra("videopath",flp);
					startActivity(nextScreen);
				}
			}); 
		
		 } //end of for
		  //System.out.println(p);

 			
		  
		  } //end of if
		}
		
		
    	// If no such folder exists, it prompts for downloading the videos to user specified location
		catch(NullPointerException nex)
		{
		// download
    	// extract
    	// reboot
    	Toast.makeText(context, "start downloading", Toast.LENGTH_SHORT).show();
    	LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.download_source,null);

        // Building DatepPcker dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(
                MainActivity.this);        	        	
        builder.setView(layout);
        builder.setTitle("Notice");
        builder.setCancelable(false);
        Button btnNO = (Button) layout.findViewById(R.id.btnNo);
        
       
        btnNO.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		
        		AlertDialog.Builder builder = new AlertDialog.Builder(context);
            	builder.setIcon(R.drawable.proxy);
            	builder.setTitle("proxyMITY videos are not present in the tablet!!!");
            	builder.setMessage(	"Store the lecture videos at any one of the"+"\n"
            	+"following locations"+"\n"+"\n"+"1. mnt/sdcard/proxyMITY"
            			+"\n"+"2. mnt/extsd/proxyMITY")
            	
            	       .setCancelable(false)
            	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            	           public void onClick(DialogInterface dialog, int id) {
            	        	
            	        	  MainActivity.this.finish();
            	        	
            	           }
            	       });
            	AlertDialog alert = builder.create();   
            	alert.show();
            }	
        });	
      
        Button btnyes = (Button) layout.findViewById(R.id.btnyes);
        btnyes.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startDownload();
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setMessage("Downloading file..");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",new DialogInterface.OnClickListener() {
                  
                    public void onClick(DialogInterface dialog, int which) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Are you sure you want cancel downloading?")
                                .setCancelable(false)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                                help_dialog.dismiss();
                                            
                                                finish();
                                                android.os.Process.killProcess(android.os.Process.myPid());
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        mProgressDialog.show();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                      
                    }
                });
                mProgressDialog.show();
            }
        });
        
        help_dialog = builder.create();
        help_dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        // customizing the width and location of the dialog on screen
        lp.copyFrom(help_dialog.getWindow().getAttributes());
        lp.width = 500;
        help_dialog.getWindow().setAttributes(lp);
	}
		
		
	}//end of method

	int count=0,p=0;
	
		/** This method Creates Thumbnails */
		@SuppressWarnings("deprecation")
		private  ImageView[] createThumbNails(String fp,ImageView[] thumbnail_MINI) {
		int flag=0;
		// TODO Auto-generated method stub
		
		 Bitmap	bmThumbnail = ThumbnailUtils.createVideoThumbnail(fp, 
				       			Thumbnails.MINI_KIND);
		 bmThumbnail=ThumbnailUtils.extractThumbnail(bmThumbnail,150,150);
		 thumbnail_MINI[p]=new ImageView(this);
	
		 thumbnail_MINI[p].setPadding(20, 20, 20, 20);
		 thumbnail_MINI[p].setImageBitmap(bmThumbnail);
		 img.setImageBitmap(bmThumbnail);
		 img.setPadding(30, 40, 30, 10);
		 tv.setPadding(30, 20, 30, 10);
		 tv.setTextColor(Color.parseColor("#FFFFFF"));
		 tv.setGravity(Gravity.CENTER);
		 
		// int width = img.getWidth();
		// tv.setWidth(width);
		 tv.setText(ls[p]);
		if(count%4==0 || count==0)		// Column modulo 4, Rows are added dynamically depending upon the numbers of video
			
		 {
		flag=1;
        tr = new TableRow(this);
        
        //System.out.println("Hiiii");
        tr.addView(v,new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        count++;  
		 }
		 else 
		 {
		tr.addView(v,new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		count++;
		 }
		 if(flag==1)
		 {
		t1.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		 }
		 p++;
		 return thumbnail_MINI;
	} 
		
		// Fetches list of files from the folders
		private List<String> getListOfFiles(String path) {
			// TODO Auto-generated method stub
			System.out.println("list order");
			File files = new File(path);
			System.out.println("list order2222");
			List<String> list = new ArrayList<String>();
			 for (File f : files.listFiles()) {
		          
			
						list.add(f.getName());
					i++;
					
				
		                // make something with the name
		        }
			System.out.println("list order"+list);
		
			return list;
		}
		
		 // Displayed while videos are being downloaded, in case they aint there
		 private void spinner() {
		    	// will start spinner first and then extraction
		    	
		    	// start spinner to show extraction progress
		    	progressBar = new ProgressDialog(context);
		        progressBar.setCancelable(false);
		        progressBar.setMessage("Extracting files, please wait...");
		        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		        progressBar.show();
		        String zipFile = Environment.getExternalStorageDirectory() + "/proxyMITY.zip"; 
		        String unzipLocation = Environment.getExternalStorageDirectory()+"/"; 
		        new File("mnt/sdcard/proxyMITY").mkdir();
		        new File("mnt/sdcard/proxyMITY/video").mkdir();
		        new File("mnt/sdcard/proxyMITY/xml").mkdir();
		        
		        Decompress d = new Decompress(zipFile, unzipLocation); 
		        d.unzip(); 
		      //  Toast.makeText(context, "unzipped", Toast.LENGTH_SHORT).show();
		        
		       
		    }
		 
		 // Downloads the video from the input URL
		 private void startDownload() {
	    	if(isInternetOn()) {
	            // INTERNET IS AVAILABLE, DO STUFF..
	                Toast.makeText(context, "Connected to network", Toast.LENGTH_SHORT).show();
	            }else{
	            // NO INTERNET AVAILABLE, DO STUFF..
	                Toast.makeText(context, "Network disconnected", Toast.LENGTH_SHORT).show();
	                //rebootFlag = 1;
	                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
	                builder.setMessage("No Connection Found, please check your network setting!")
	                        .setCancelable(false)
	                        .setPositiveButton("OK",
	                                new DialogInterface.OnClickListener() {
	                                    public void onClick(DialogInterface dialog, int id) {
	                                        finish();
	                                        android.os.Process
	                                                .killProcess(android.os.Process.myPid());
	                                    }
	                                });
	                AlertDialog alert = builder.create();
	                alert.show();
	              
	            }  
	    	/**
	    	 * global github link for downloading demo videos
	    	 **/
	    	String url = "http://www.it.iitb.ac.in/AakashApps/repo/proxyMITY.zip";
	    	new DownloadFileAsync().execute(url);
	    }    
		 
		 // Checks the status of connection
		 private final boolean isInternetOn() {
		    	// check internet connection via wifi   
		    	 	ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		    	 	//NetworkInfo mwifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		    	 	//mwifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		            if( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
		            connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
		            connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
		            connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED ) {
		            	//Toast.makeText(this, connectionType + ” connected”, Toast.LENGTH_SHORT).show();
		            	return true;
		            } 
		            else if( connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||  
		            		connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  ) {
		            		//System.out.println(“Not Connected”);
		            		return false;
		            	}
		            	return false;
		    }
		 
		 class DownloadFileAsync extends AsyncTask<String, String, String> {
		    	/**
		    	 * download zip from URL and write in '/mnt/sdcard'
		    	 **/
		        @Override        	
		        public void onPreExecute() {
		            super.onPreExecute();
		        }

		        public String doInBackground(String... aurl) {
		            int count;

		            try {
		                URL url = new URL(aurl[0]);
		                URLConnection conexion = url.openConnection();
		                conexion.connect();

		                int lenghtOfFile = conexion.getContentLength();

		                InputStream input = new BufferedInputStream(url.openStream());
		                OutputStream output = new FileOutputStream(
		                        "/mnt/sdcard/proxyMITY.zip");

		                byte data[] = new byte[1024];

		                long total = 0;

		                while ((count = input.read(data)) != -1) {
		                    total += count;
		                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
		                    output.write(data, 0, count);
		                }
		                output.flush();
		                output.close();
		                input.close();
		            } catch (Exception e) {
		            }
		            return null;

		        }

		        public void onProgressUpdate(String... progress) {
		            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		        }
		        
		        public void onPostExecute(String unused) {
		        	mProgressDialog.dismiss();
		        	help_dialog.dismiss();
		        	if (checkTar.exists()){
		        		spinner();
		        		
		        	}
		        	  new Thread() {
		        		    public void run() {
		        		        try{
		        		            // just doing some long operation
		        		            Thread.sleep(10000);
		        		         } catch (Exception e) {  }
		        		           // handle the exception somehow, or do nothing
		        		         

		        		         // run code on the UI thread
		        		        runOnUiThread(new Runnable() {

		        		            public void run() {
		        		                progressBar.dismiss();
		        		                Intent intent = getIntent();
		       	        		     finish();
		       	        		     startActivity(intent);
		        		            }
		        		        });
		        		    }
		        		     }.start();
		        		     
		        		    
		       // 	progressBar.dismiss();
		        	
		    }
		        //delete internal files during un-installation 
		        public boolean deleteFile (String name){
		            name = "aakash.sh";
		            name = "help_flag";
		            name = "copyFilesFlag.txt";
		            return false;
		           
		        }
		}
	}