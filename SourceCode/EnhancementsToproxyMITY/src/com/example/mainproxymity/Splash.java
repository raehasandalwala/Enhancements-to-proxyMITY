package com.example.mainproxymity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ivy.util.url.ApacheURLLister;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images.Thumbnails;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * ********************************************************************************************
 * 
 * This class displays a splash screen while videos are being downloaded from the server in
 * Wi-Fi mode
 * 
 * ********************************************************************************************
 * */
public class Splash extends Activity{
	
	static URL url1;
	List<URL> list = new ArrayList<URL>();
	ArrayList<String> files = new ArrayList<String>();
	ArrayList<String> extension = new ArrayList<String>();
	static ArrayList<ImageView> thumbnail_MINI=new ArrayList<ImageView>();
	static ArrayList<ImageView> thumbnail_MINI1=new ArrayList<ImageView>();
	public static final int OPEN_SETTINGS_REQUEST = 1;
	static Activity ACTIVITY;
	static PendingIntent RESTART_INTENT;
	private SharedPreferences settings;
	static int flag=0;
	static String videopath;
    static String ip_address;
	static String ext;
	HttpURLConnection con;
	ApacheURLLister lister1;
	static String videourl;
	ProgressDialog pd;
	static String file_path;
	ProgressBar pb;
	 final Context context = this;
	 
	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.splash);
	        LinearLayout l = (LinearLayout) findViewById(R.id.ll1);
	        pb = (ProgressBar) findViewById(R.id.pb);
	        pb.setVisibility(View.VISIBLE);
	        TextView tv = new TextView(this);
	        tv.setText("Loading...");
	        tv.setGravity(Gravity.CENTER);
	        tv.setTextSize(30);
	        l.addView(tv);
	        ACTIVITY = this;
			
			RESTART_INTENT = PendingIntent.getActivity(this.getBaseContext(), 0,
					new Intent(getIntent()), getIntent().getFlags());
			PreferenceManager.setDefaultValues(this, R.xml.settings, false);
			settings = PreferenceManager.getDefaultSharedPreferences(this);
			//Editor editor=settings.edit();
			//editor.clear();
			//editor.commit();
			ip_address = settings.getString("cameraStreamURL", "<doesnt-exist>");
			//WifiManager wim= (WifiManager) getSystemService(WIFI_SERVICE);
			 // List<WifiConfiguration> l1 =  wim.getConfiguredNetworks(); 
			  //WifiConfiguration wc = l1.get(0);
			//  ip_address=Formatter.formatIpAddress(wim.getConnectionInfo().getIpAddress());
			System.out.println("this is "+ip_address);
			videopath = "http://" + ip_address + "/videos/";
			
			try {
				System.out.println(videopath);
				try {
					url1 = new URL(videopath);
				//	HttpURLConnection.setFollowRedirects(false);
				      // note : you may also need
				      //        HttpURLConnection.setInstanceFollowRedirects(false)
				      //con =
				       //  (HttpURLConnection) url1.openConnection();
				      //con.setRequestMethod("HEAD");
				    if(validate_ip(ip_address))
				    	{
				    	try
				    	{
				    	 lister1 = new ApacheURLLister();
				    	 
				    	 list = lister1.listFiles(url1);
				    	 System.out.println("up");
				    	 new Loading().execute();
				    	 System.out.println("down");
				    	}
				    	catch(Exception e)
				    	{
				    		 //System.out.println("sorry");
								//Toast.makeText(Splash.this,"Sorry,No videos folder found on this URL . Please check your IP settings",Toast.LENGTH_SHORT).show();
								 Intent i = new Intent(Splash.this,ListVideos.class);
								 i.putStringArrayListExtra("files",files);
								 i.putStringArrayListExtra("extension",extension);
								 flag=1;
								
								 startActivity(i);
				    	}
				    	 
				    	}
				    else
				    {
				    	
								
								System.out.println("Else part");
						
								
								 Intent i = new Intent(Splash.this,ListVideos.class);
								 i.putStringArrayListExtra("files",files);
								 i.putStringArrayListExtra("extension",extension);
								
								 startActivity(i);
								
								Splash.this.finish();
							}//else
		
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
				} 
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				System.out.println("after url");
					
			}catch(Exception e)
			{
				
			}
			
			
			}//onCreate
					 
					public boolean validate_ip(final String ip) {
						Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
						Matcher matcher = pattern.matcher(ip);
						return matcher.matches();
					}
					
					private  ArrayList<ImageView> createThumbNails(String fp,ArrayList<ImageView>thumbnail_MINI) {
						
						// TODO Auto-generated method stub
						
						Bitmap	bmThumbnail = ThumbnailUtils.createVideoThumbnail(fp, 
				       			Thumbnails.MINI_KIND);
						
						bmThumbnail=ThumbnailUtils.extractThumbnail(bmThumbnail,150,150);
						
						ImageView thumbnail=new ImageView(this);
						
						  thumbnail.setImageBitmap(bmThumbnail);
						thumbnail_MINI.add(thumbnail);
						 return thumbnail_MINI;
					} 
					@Override
					public boolean onCreateOptionsMenu(Menu menu) {
						MenuInflater inflater = getMenuInflater();
						inflater.inflate(R.menu.menu1, menu);
						return true;
					}

					@Override
					public boolean onOptionsItemSelected(MenuItem item) {
						// Handle item selection
						switch (item.getItemId()) {

						case R.id.wifi:
							Intent i = new Intent(Splash.this, SettingsActivity.class);
							startActivityForResult(i, Splash.OPEN_SETTINGS_REQUEST);
							return true;
						
						case R.id.help:
							Intent help = new Intent(Splash.this, Help.class);
							startActivityForResult(help, Splash.OPEN_SETTINGS_REQUEST);
							return true;
							
							
							
						default:
							return super.onOptionsItemSelected(item);
						}
					}
					@Override
					protected void onActivityResult(int requestCode, int resultCode, Intent data) {
						if (requestCode == Splash.OPEN_SETTINGS_REQUEST) {
							if (resultCode == RESULT_OK) {
							} else if (resultCode == RESULT_CANCELED) {
								AlarmManager mgr = (AlarmManager) Splash.ACTIVITY
										.getSystemService(Context.ALARM_SERVICE);
								mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
										Splash.RESTART_INTENT);
								System.exit(2);
								System.out.println("restarting app");

							}
						}
					}
					class Loading extends AsyncTask<Void,Void,Void>
					{

						@Override
						protected Void doInBackground(Void... arg0) {
							// TODO Auto-generated method stub
							
								System.out.println("in thread");
									
								//	System.out.println("thumbanil size"+ tm.length);
									for (int i = 0; i < list.size(); i++) {
										String dg = list.get(i).toString();
										System.out.println("I am dg"+ dg);
										if (dg.contains(".mp4") || dg.contains(".3gp")
												|| dg.contains(".MP4") || dg.contains(".3GP")||dg.contains(".flv")||dg.contains(".FLV")) {
											String dg1 = dg.substring(videopath.length(),
													dg.length());
											String dg2 = dg1.replaceAll("%20", " ");
											int j = dg2.lastIndexOf('.');
											ext = dg2.substring(j, dg2.length());
											System.out.println(ext);
											String dg3 = dg2.replaceAll(ext, "");
											extension.add(ext);
											files.add(dg3);
											file_path="http://"+ip_address + "/videos/"+dg3+ext;
											System.out.println("File_path"+file_path);
											thumbnail_MINI=createThumbNails(file_path,thumbnail_MINI);
											
										}
								
								 
					        
					

					        }//for
									
							
							
									System.out.println("file size" + files.size());
									// pb.setVisibility(View.GONE);
									 System.out.println("after gone");
									 if(files.size()==0)
									 {
									 System.out.println("sorry");
									Toast.makeText(Splash.this,"Sorry,No videos folder found on this URL",Toast.LENGTH_SHORT).show();
									 }
									 else
									 {
									 Intent i = new Intent(Splash.this,ListVideos.class);
									 i.putStringArrayListExtra("files",files);
									 i.putStringArrayListExtra("extension",extension);
									 startActivity(i);
									 Splash.this.finish();
									 }
								
							return null;
						}
						
					}

}