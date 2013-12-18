package com.example.mainproxymity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.utils.URIUtils;
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
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images.Thumbnails;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


/**
 * ********************************************************************************************
 * 
 * This activity is used in Wi-Fi mode. It displays the list of videos that are present in server
 * after downloading from the server.
 * Also, it provides searching facility for the videos that are in the list by entering the text
 * that closely matches to name of the video file, searching is done using Regular Expressions
 * 
 * ********************************************************************************************
 * */

public class ListVideos extends Activity {
	/** Called when the activity is first created. */

	EditText edittext;
	
	ArrayList<ImageView> thumbnail_MINI1=new ArrayList<ImageView>();
	
	ListView lv;
	Thumbnail tm[],tm1[];
	private ProgressDialog mProgressDialog, progressBar;
	private ArrayList<String> arraylist = new ArrayList<String>();
	private ArrayList<Integer> index = new ArrayList<Integer>();
	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	Listad ad;
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listvideos);
		edittext = (EditText) findViewById(R.id.EditText01);
		Intent i = getIntent();
		final ArrayList<String> files = i.getStringArrayListExtra("files");
		final ArrayList<String> extension = i.getStringArrayListExtra("extension");
		
		if(!validate_ip(Splash.ip_address) )
		{
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						ListVideos.this);
				System.out.println("Else part");
				alertDialogBuilder.setTitle("Alert");
				alertDialogBuilder
						.setMessage(
								"InValid IP Address, Go To Menu-->Wifi-Settings")
						.setIcon(R.drawable.proxy)
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// ListVideoes.this.finish();
									}
								});
				AlertDialog alertDialog = alertDialogBuilder.create();
				 alertDialog.show();
				
				
				
			} 
		if(Splash.flag==1)
		{
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						ListVideos.this);
				System.out.println("Else part");
				alertDialogBuilder.setTitle("Alert");
				alertDialogBuilder
						.setMessage(
								"No folder named videos on this IP. Check your address.")
						.setIcon(R.drawable.proxy)
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// ListVideoes.this.finish();
									}
								});
				AlertDialog alertDialog = alertDialogBuilder.create();
				Splash.flag=0;
				 alertDialog.show();
		}
		
					tm=new Thumbnail[files.size()];
					for(int y=0;y<files.size();y++)
					{
					 tm[y]=new Thumbnail(Splash.thumbnail_MINI.get(y),files.get(y));
					 
					 System.out.println(tm[y].title);
					}
				
		
       
		ad=new Listad(this,R.layout.listlayout2,tm);
		System.out.println("mad");
	 lv=(ListView)findViewById(R.id.listView1);
		lv.setAdapter(ad);


		edittext.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				arraylist.clear();
				index.clear();
				System.out.println("in edit text" + files.size());
				for (int i = 0; i < files.size(); i++) {
					// String dg = edittext.getText().toString();
					
					if ((files.get(i).toString().toLowerCase())
							.contains(edittext.getText().toString()
									.toLowerCase())) {
						System.out.println("in for");
						arraylist.add(files.get(i));
						index.add(i);
					}
				}
				
				tm1=new Thumbnail[index.size()];
				System.out.println("Index"+index.size());
				for(int y=0;y<index.size();y++)
				{
					tm1[y]=new Thumbnail(Splash.thumbnail_MINI.get(index.get(y)),arraylist.get(y));
				}

				
				Listad ad=new Listad(ListVideos.this,R.layout.listlayout2,tm1);
				System.out.println("mad");
			 lv=(ListView)findViewById(R.id.listView1);
				lv.setAdapter(ad);
				

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			public void afterTextChanged(Editable s) {
			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv = (TextView) view.findViewById(R.id.txt);
				//String dg = tv.getText().toString();
				String dg=files.get(position);
				
				String xmlname = dg;
				
				String ss = Splash.videopath + dg + extension.get(position);
				System.out.println("This is what I am viewing"+ss);
				Intent intent = new Intent(ListVideos.this,Videoview.class);
				intent.putExtra("xmlname", xmlname);
				intent.putStringArrayListExtra("files",files);
				intent.putStringArrayListExtra("extension",extension);
				intent.putExtra("ipaddress", Splash.ip_address);
				intent.putExtra("videofilepath", ss);
				startActivity(intent);

			}
		});
		
		
lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				TextView tv = (TextView) findViewById(R.id.txt);
				String videoname = tv.getText().toString();
			//	String xmlname = videoname;
				Splash.videourl = Splash.videopath + videoname + extension.get(position);
				
				
				 final AlertDialog.Builder builder = new AlertDialog.Builder(ListVideos.this);
                 builder.setMessage("Do you want to download this lecture video?")
                         .setCancelable(false)
                         .setPositiveButton("Yes",
                                 new DialogInterface.OnClickListener() {
                                     public void onClick(DialogInterface dialog, int id) {
                                    	 startDownload();
                                    	  mProgressDialog = new ProgressDialog(ListVideos.this);
                                          mProgressDialog.setMessage("Downloading file..");
                                          mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                          mProgressDialog.setCancelable(false);
                                          mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",new DialogInterface.OnClickListener() {
                                            
                                              public void onClick(DialogInterface dialog, int which) {
                                                  final AlertDialog.Builder builder = new AlertDialog.Builder(ListVideos.this);
                                                  builder.setMessage("Are you sure you want cancel downloading?")
                                                          .setCancelable(false)
                                                          .setPositiveButton("Yes",
                                                                  new DialogInterface.OnClickListener() {
                                                                      public void onClick(DialogInterface dialog, int id) {
                                                                          dialog.dismiss();
                                                                         
                                                                       /*   String[] command = {"rm /mnt/sdcard/apl.tar.gz"};
                                                                          RunAsRoot(command);  */
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
                                       
                                     
                                 })
                         .setNegativeButton("No", new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id) {
                                 //mProgressDialog.show();
                            	 dialog.dismiss();
                             }
                         });
                 AlertDialog alert = builder.create();
                 alert.show();
				return true;
				
			}
		});

		}
	
	int p=0;
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

	private void startDownload() {
    	if(isInternetOn()) {
            // INTERNET IS AVAILABLE, DO STUFF..
                Toast.makeText(ListVideos.this, "Connected to network", Toast.LENGTH_SHORT).show();
            }else{
            // NO INTERNET AVAILABLE, DO STUFF..
                Toast.makeText(ListVideos.this, "Network disconnected", Toast.LENGTH_SHORT).show();
                //rebootFlag = 1;
                AlertDialog.Builder builder = new AlertDialog.Builder(ListVideos.this);
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
    	 * link for downloading data
    	 **/
    	String url = Splash.videourl;
    	new DownloadFileAsync().execute(url);
    }  
	
	
	
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
		                
		                if ( !(new File("mnt/sdcard/proxyMITY downloads")).exists())
		                {
		                	 new File("mnt/sdcard/proxyMITY downloads").mkdir();
		                }

		                InputStream input = new BufferedInputStream(url.openStream());
		                OutputStream output = new FileOutputStream(
		                        "/mnt/sdcard/proxyMITY downloads");

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
		        	    
		        		    
		             	
		    }
		      
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
			Intent i = new Intent(ListVideos.this, SettingsActivity.class);
			startActivityForResult(i, Splash.OPEN_SETTINGS_REQUEST);
			return true;
		
		case R.id.help:
			Intent help = new Intent(ListVideos.this, Help.class);
			startActivityForResult(help, Splash.OPEN_SETTINGS_REQUEST);
			return true;
			
			
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public static boolean exists(String URLName) {
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(URLName)
					.openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	 
	public boolean validate_ip(final String ip) {
		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
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
}

