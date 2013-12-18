package com.example.mainproxymity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.ClipData.Item;
import android.content.ClipDescription;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * ********************************************************************************************
 * 
 * This class implements drag listener for Drag n Drop feature used in 
 * Video playing screen: NewActivity for Sd-Card Mode
 * 
 * ********************************************************************************************
 * */

@SuppressLint("NewApi")
public class myDragEventListener implements View.OnDragListener{
Context context;
	    public myDragEventListener(Context context)
	       {
	    	   this.context=context;
	       }

	@Override
	public boolean onDrag(View v, DragEvent event) {
		// TODO Auto-generated method stub
		 System.out.println("mmmm");
		 
		// Defines a variable to store the action type for the incoming event
        final int action = event.getAction();
        
        // Handles each of the expected events
        switch(action) {

            case DragEvent.ACTION_DRAG_STARTED:

                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                	
                    // As an example of what your application might do,
                	
                	
                    // Invalidate the view to force a redraw in the new tint
                    v.invalidate();
                    System.out.println("falss");
                    // returns true to indicate that the View can accept the dragged data.
                    return(true);

                    } else {
                    	
                    // Returns false. During the current drag and drop operation, this View will
                    // not receive events again until ACTION_DRAG_ENDED is sent.
                    return(false);

                    }
             

            case DragEvent.ACTION_DRAG_ENTERED: {

                // Applies a green tint to the View. Return true; the return value is ignored.
            	  System.out.println("yyy");


                return(true);

             
            }
                case DragEvent.ACTION_DRAG_LOCATION:

                // Ignore the event
                	  System.out.println("loc");
                    return(true);

               

                case DragEvent.ACTION_DRAG_EXITED:

                	  System.out.println("iju");

                    return(true);

              

                case DragEvent.ACTION_DROP:
                	System.out.println("nooo");
                	String value = event.getClipDescription().getLabel().toString();
                     System.out.println("Hello"+value);
                    // NewActivity na=new NewActivity();
                     System.out.println("Hello");
                     //na.callActi(value);
                     String s="/mnt/sdcard/proxyMITY/video/" + value;
                     Intent nextScreen = new Intent(context, NewActivity.class);
						
 					nextScreen.putExtra("videopath",s);
 					
 					//nextScreen.putExtra("imgarray",thumbnail_MINI);
 					//nextScreen.putExtra("namearray",ls);
 					context.startActivity(nextScreen);
 					((Activity) context).finish();
           		 return(true);
                		
                case DragEvent.ACTION_DRAG_ENDED:

                   

                    // Invalidates the view to force a redraw
                    v.invalidate();

                    // Does a getResult(), and displays what happened.
                    if (event.getResult()) {
                       System.out.println("llooaaaa");
                    } else {
                    	System.out.println("llaa");

                    }

                    // returns true; the value is ignored.
                    return(true);


                // An unknown action type was received.
                default:
                    Log.e("DragDrop Example","Unknown action type received by OnDragListener.");

                break;
        };
		return false;
	}
	
}