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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Scroller;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

/**
 * *****************************************************************************
 * ***************
 * 
 * This class plays the intended video clicked from the MainScreen of Wi-Fi mode
 * 
 * *****************************************************************************
 * ***************
 * */
@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Videoview extends Activity {

	int dy = 0;
	int startTime_array[];
	String filename = "";
	String filelines_array[];
	String vdp = "";
	VideoView view;
	Context ct;
	StringBuilder sb;
	SpannableStringBuilder sbr;
	String line;
	private String xmlName;
	public static final int OPEN_BOOKMARK_ACTIVITY = 2;
	boolean stop = false;
	int index = 0;
	int flag = 0, flag1 = 0;
	SlidingDrawer slidingDrawer;
	ImageView b;
	SlidingDrawer slidingDrawer0;
	LinearLayout.LayoutParams vlp;

	MediaPlayer audio = new MediaPlayer();
	String stringXmltoxml;
	ArrayList<String> themeattributeval = new ArrayList<String>();
	ArrayList<String> slidename = new ArrayList<String>();
	ArrayList<String> starttime = new ArrayList<String>();
	ArrayList<String> coursename = new ArrayList<String>();
	ArrayList<String> speaker = new ArrayList<String>();
	List<Integer> myCoords = new ArrayList<Integer>();
	ArrayList<String> contact = new ArrayList<String>();
	ArrayList<String> extension = new ArrayList<String>();
	ExpandableListAdapter mAdapter;
	ArrayList<ArrayList<String>> seektime = new ArrayList<ArrayList<String>>();
	int length;
	private String xmlPath;
	EditText transcriptxt;

	ExpandableListView epView;
	View v, v1;

	ImageView im[];
	String audioPath = null;
	ArrayList<ArrayList<String>> children = new ArrayList<ArrayList<String>>();
	ArrayList<String> groups = new ArrayList<String>();

	@SuppressLint("SdCardPath")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newactivity);
		ct = this;
		ListView lv = (ListView) findViewById(R.id.ListView1);
		view = (VideoView) findViewById(R.id.videoView1);
		final TextView txtv = (TextView) findViewById(R.id.textView1);
		Intent i = getIntent();
		final ArrayList<String> files = i.getStringArrayListExtra("files");
		extension = i.getStringArrayListExtra("extension");
		int no = files.size();
		b = (ImageView) findViewById(R.id.b);
		Thumbnail tm[] = new Thumbnail[no];
		for (int y = 0; y < no; y++) {
			tm[y] = new Thumbnail(Splash.thumbnail_MINI.get(y), files.get(y));

			System.out.println(tm[y].title);
		}

		Listad ad = new Listad(this, R.layout.listlayout1, tm);
		lv = (ListView) findViewById(R.id.ListView1);
		lv.setAdapter(ad);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv = (TextView) view.findViewById(R.id.txt);
				String dg = files.get(position);
				tv.setText(dg);
				System.out.println("After set Text");

				String ss = Splash.videopath + dg + extension.get(position);
				Intent nextScreen = new Intent(ct, Videoview.class);

				nextScreen.putExtra("videofilepath", ss);

				nextScreen.putExtra("files", files);
				nextScreen.putExtra("extension", extension);
				ct.startActivity(nextScreen);
				((Activity) ct).finish();

			}
		});

		playvideo(0);

		view.start();
		// new Timer().execute();

		int len = vdp.length();
		String sub = vdp.substring(28, len);
		txtv.setText(sub);
		int index = sub.lastIndexOf(".");

		final LinearLayout l0 = (LinearLayout) findViewById(R.id.ll0);
		final LinearLayout l1 = (LinearLayout) findViewById(R.id.ll1);
		final LinearLayout l3 = (LinearLayout) findViewById(R.id.ll3);
		final LinearLayout l2 = (LinearLayout) findViewById(R.id.ll2);
		final LinearLayout cl = (LinearLayout) findViewById(R.id.contentLayout);
		final int orig_width = l1.getWidth();
		vlp = (android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
		System.out.println("previous width" + vlp.width);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				vlp.width + 800, LayoutParams.FILL_PARENT);
		layoutParams.setMargins(vlp.leftMargin - 175, 0, vlp.rightMargin - 175,
				0);
		l3.setLayoutParams(layoutParams);
		final int left = ((LinearLayout.LayoutParams) vlp).leftMargin;
		int right = ((LinearLayout.LayoutParams) vlp).rightMargin;
		vlp = (android.widget.LinearLayout.LayoutParams) l3.getLayoutParams();
		System.out.println("width" + vlp.width);
		System.out.println("Left margin" + left);
		System.out.println("Right margin" + right);

		v1 = getLayoutInflater().inflate(R.layout.hidden2, cl, false);
		transcriptxt = (EditText) v1.findViewById(R.id.editText1);
		v = getLayoutInflater().inflate(R.layout.hidden1, cl, false);
		epView = (ExpandableListView) v.findViewById(R.id.expandableListView1);
		xmlName = sub.substring(0, index);
		System.out.println("xmlname " + xmlName);
		String xmlPath = "http://" + Splash.ip_address + "xml/" + xmlName
				+ ".xml";

		view = (VideoView) findViewById(R.id.videoView1);
		// tree structure

		if (new File(xmlPath).exists()) {
			try {

				stringXmltoxml = convertXMLFileToString();
				System.out.println("dddddddddddddddddddddddd");
				// String stringXmlContent = getEventsFromAnXML(this);

				epView = (ExpandableListView) findViewById(R.id.expandableListView1);

				mAdapter = new MyExpandableListAdapter();
				System.out.println("gggggggggggggggggggggggggg");
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Toast.makeText(
					getApplicationContext(),
					"One Continuous  Topic so theme/slide navigation is not needed ",
					Toast.LENGTH_SHORT).show();
		}
		final ImageView slideButton = (ImageView) findViewById(R.id.slideButton);

		final ImageView slideButton0 = (ImageView) findViewById(R.id.slideButton0);
		slidingDrawer = (SlidingDrawer) findViewById(R.id.SlidingDrawer);
		slideButton.setBackgroundResource(R.drawable.images);
		slidingDrawer0 = (SlidingDrawer) findViewById(R.id.SlidingDrawer0);
		slideButton0.setBackgroundResource(R.drawable.images1);

		slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				slideButton.setBackgroundResource(R.drawable.images1);

				v = getLayoutInflater().inflate(R.layout.hidden1, cl, false);
				try {
					b.setTag(R.drawable.cloud);
					b.setImageResource(R.drawable.cloud);
					epView = (ExpandableListView) v
							.findViewById(R.id.expandableListView1);
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

				} catch (Exception ex) {
					Toast.makeText(getApplicationContext(),
							"XML file doesn't exist", Toast.LENGTH_SHORT)
							.show();
				}

				cl.addView(v, new LinearLayout.LayoutParams(
						LayoutParams.FILL_PARENT, 0, 0.8f));
				if (!slidingDrawer0.isOpened()) {
					vlp = (android.widget.LinearLayout.LayoutParams) l3
							.getLayoutParams();
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							vlp.width - 200, LayoutParams.FILL_PARENT);
					layoutParams.setMargins(vlp.leftMargin, 0, 0, 0);
					l3.setLayoutParams(layoutParams);
					vlp = (android.widget.LinearLayout.LayoutParams) l3
							.getLayoutParams();
					int left1 = ((LinearLayout.LayoutParams) vlp).leftMargin;
					int width1 = vlp.width;
					System.out.println("now width" + width1);
					System.out.println("now left" + left1);
				} else {
					vlp = (android.widget.LinearLayout.LayoutParams) l3
							.getLayoutParams();
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							vlp.width - 200, LayoutParams.FILL_PARENT);
					layoutParams.setMargins(vlp.leftMargin, 0, 0, 0);
					l3.setLayoutParams(layoutParams);

				}

			}
		});

		slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				slideButton.setBackgroundResource(R.drawable.images);
				cl.removeView(v);

				if (!slidingDrawer0.isOpened()) {
					vlp = (android.widget.LinearLayout.LayoutParams) l3
							.getLayoutParams();
					System.out.println("first" + vlp.width);
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							vlp.width + 200, LayoutParams.FILL_PARENT);
					layoutParams.setMargins(vlp.leftMargin, 0,
							vlp.rightMargin - 175, 0);
					l3.setLayoutParams(layoutParams);
					vlp = (android.widget.LinearLayout.LayoutParams) l3
							.getLayoutParams();
					System.out.println("second" + vlp.width);

				} else {
					vlp = (android.widget.LinearLayout.LayoutParams) l3
							.getLayoutParams();
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							vlp.width + 200, LayoutParams.FILL_PARENT);
					layoutParams.setMargins(vlp.leftMargin, 0,
							vlp.rightMargin - 175, 0);
					l3.setLayoutParams(layoutParams);
				}

			}
		});
		slidingDrawer0.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				slideButton0.setBackgroundResource(R.drawable.images);
				if (!slidingDrawer.isOpened()) {
					vlp = (android.widget.LinearLayout.LayoutParams) l3
							.getLayoutParams();
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							vlp.width - 200, LayoutParams.FILL_PARENT);
					layoutParams.setMargins(0, 0, vlp.rightMargin, 0);
					l3.setLayoutParams(layoutParams);
					vlp = (android.widget.LinearLayout.LayoutParams) l3
							.getLayoutParams();
					int left1 = ((LinearLayout.LayoutParams) vlp).leftMargin;
					int width1 = vlp.width;
					System.out.println("now width" + width1);
					System.out.println("now left" + left1);
				} else {
					vlp = (android.widget.LinearLayout.LayoutParams) l3
							.getLayoutParams();
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							vlp.width - 200, LayoutParams.FILL_PARENT);
					layoutParams.setMargins(0, 0, vlp.rightMargin, 0);
					l3.setLayoutParams(layoutParams);

				}

			}
		});

		slidingDrawer0.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				slideButton0.setBackgroundResource(R.drawable.images1);
				if (!slidingDrawer.isOpened()) {
					vlp = (android.widget.LinearLayout.LayoutParams) l3
							.getLayoutParams();
					System.out.println("first" + vlp.width);
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							vlp.width + 200, LayoutParams.FILL_PARENT);
					layoutParams.setMargins(vlp.leftMargin - 175, 0,
							vlp.rightMargin, 0);
					l3.setLayoutParams(layoutParams);
					vlp = (android.widget.LinearLayout.LayoutParams) l3
							.getLayoutParams();
					System.out.println("second" + vlp.width);

				} else {
					vlp = (android.widget.LinearLayout.LayoutParams) l3
							.getLayoutParams();
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							vlp.width + 200, LayoutParams.FILL_PARENT);
					layoutParams.setMargins(vlp.leftMargin - 175, 0,
							vlp.rightMargin, 0);
					l3.setLayoutParams(layoutParams);
				}

			}
		});

		b.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("on button click");
				if ((Integer) b.getTag() == R.drawable.cloud) {
					System.out.println("in view transcript");

					cl.removeView(v);

					v1 = getLayoutInflater().inflate(R.layout.hidden2, cl,
							false);
					try {
						b.setTag(R.drawable.cloud2);
						b.setImageResource(R.drawable.cloud2);
						transcriptxt = (EditText) v1
								.findViewById(R.id.editText1);
						transcriptxt.setText(sbr);
						transcriptxt.setMovementMethod(LinkMovementMethod
								.getInstance());

					} catch (Exception ex) {
						Toast.makeText(getApplicationContext(),
								"Transcript file doesn't exist",
								Toast.LENGTH_SHORT).show();
					}

					cl.addView(v1, new LinearLayout.LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT,
							0.8f));

				}

				else {
					if ((Integer) b.getTag() == R.drawable.cloud2)

					{
						cl.removeView(v1);
						// cl.removeView(b);
						v = getLayoutInflater().inflate(R.layout.hidden1, cl,
								false);
						try {
							epView = (ExpandableListView) v
									.findViewById(R.id.expandableListView1);
							epView.setAdapter(mAdapter);
							epView.expandGroup(0);
							epView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
								public boolean onGroupClick(
										ExpandableListView arg0, View arg1,
										int groupPosition, long arg3) {
									if (groupPosition == 5) {

									}
									return false;
								}
							});

							epView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
								public boolean onChildClick(
										ExpandableListView parent, View v,
										int groupPosition, int childPosition,
										long id) {
									int pos = convertInMili(seektime.get(
											groupPosition).get(childPosition));
									view.seekTo(pos);
									view.start();

									return false;
								}

							});

							b.setTag(R.drawable.cloud);
							b.setImageResource(R.drawable.cloud);
						} catch (Exception ex) {
							Toast.makeText(getApplicationContext(),
									"XML file doesn't exist",
									Toast.LENGTH_SHORT).show();
						}
						cl.addView(v, new LinearLayout.LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT, 0.8f));
						// cl.addView(b,new
						// LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT,0.2f));

					}
				}

			}

		});
		/** Inner Class: MyClickableSpan */
		class MyClickableSpan extends ClickableSpan { // clickable span

			public void onClick(View textView) {
				// do something

				Toast.makeText(getApplicationContext(), "Clicked",
						Toast.LENGTH_SHORT).show();
				playvideo(10 * 60 * 1000);
				// playvideo(startTime_array[loop]);
			}

			@Override
			public void updateDrawState(TextPaint ds) {
				ds.setColor(Color.BLACK);// set text color
				// ds.setUnderlineText(false); // set to false to remove
				// underline
			}
		}

		/** Class body ends */

		int lines = 0;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		stop = true;

		index = 0;
		switch (item.getItemId()) {

		case R.id.bookmark:
			Intent i1 = new Intent(Videoview.this, Bookmark.class);
			int duration = view.getDuration();
			int current = view.getCurrentPosition();
			i1.putExtra("duration", duration);
			i1.putExtra("current", current);
			startActivityForResult(i1, OPEN_BOOKMARK_ACTIVITY);
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

	// class timer
	class Timer extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			while (true) {
				if (view.getCurrentPosition() >= 300000) {
					System.out.println("inside if");
					view.pause();
					// Intent i = new Intent(NewActivity.this,Quiz.class);
					// startActivity(i);
					break;

				}
			}
			return null;
		}

	}

	// class expandable listview
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

			TextView textView = new TextView(Videoview.this);
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

			TextView textView = new TextView(Videoview.this);
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
		length = view.getCurrentPosition();
	}

	public void onResume() {
		super.onResume();
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
		// ArrayList<String> linkattribute = new ArrayList<String>();
		// ArrayList<String> linkattributeval = new ArrayList<String>();
		ArrayList<String> themeattribute = new ArrayList<String>();
		// ArrayList<String> linkurl = new ArrayList<String>();
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

						/*
						 * if ((test.equalsIgnoreCase("Link"))) {
						 * linkattribute.add(xpp.getAttributeName(i));
						 * linkattributeval.add(xpp.getAttributeValue(i)); }
						 */

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

				/*
				 * if ((test.equalsIgnoreCase("linkurl"))) {
				 * linkurl.add(xpp.getText()); }
				 */

				if ((test.equalsIgnoreCase("slidename"))) {
					String dg1 = xpp.getText();
					/*
					 * int j = dg.lastIndexOf('.'); String dg1 = dg.substring(0,
					 * j);
					 */
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
		// String xmlPath=MainActivity.main_path+"xml/"+xmlName+".xml";
		BufferedReader br = new BufferedReader(
				new FileReader(new File(xmlPath)));
		// System.out.println("Yay");
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

	void playvideo(int seek) {

		MediaController mediaController = new MediaController(this);

		mediaController.setAnchorView(view);

		view.setMediaController(mediaController);

		view.setMediaController(mediaController);

		vdp = getIntent().getStringExtra("videofilepath");

		view.setVideoPath(vdp);

		view.requestFocus();
		view.start();

		view.seekTo(seek);
		view.resume();

	}
}
