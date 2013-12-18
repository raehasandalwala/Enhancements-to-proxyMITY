package com.example.mainproxymity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * ***************************************************************************************
 * 
 * This class displays the splash screen that lasts for 5 seconds containing the complete
 * functionality of the application, displayed before actual start of the application
 * 
 * ***************************************************************************************
 * */

public class Main0 extends Activity {
   
   // importing the oncreate by right clicking and going to the "source" and then "over ride implement methods". Also made sure it was the
   // bundle one
   
   @Override
   protected void onCreate(Bundle TravisLoveBacon) {
      // TODO Auto-generated method stub
      super.onCreate(TravisLoveBacon);
      setContentView(R.layout.main0);	// Activity thats is being inflated by this class
       Thread timer = new Thread(){
         public void run(){
            try{
               sleep(3000);
            } catch (InterruptedException e){
               e.printStackTrace();
            }finally{
               Intent openStartingPoint = new Intent(Main0.this,Main.class);
               startActivity(openStartingPoint);
            }
         }
       };
       timer.start();
   
   }

}