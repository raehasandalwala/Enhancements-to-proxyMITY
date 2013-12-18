package com.example.mainproxymity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


/**
 * ***************************************************************************************
 * 
 * This class displays the splash screen that lasts for 5 seconds alerting about the Quiz 
 * session that will start after 5 seconds
 * 
 * ***************************************************************************************
 * */
public class Quiz0 extends Activity {
   
   // importing the oncreate by right clicking and going to the "source" and then "over ride implement methods". Also made sure it was the
   // bundle one
   
   @Override
   protected void onCreate(Bundle TravisLoveBacon) {
      // TODO Auto-generated method stub
      super.onCreate(TravisLoveBacon);
      System.out.println("b4");
      setContentView(R.layout.quiz0);
      System.out.println("b4");
       Thread timer = new Thread(){
         public void run(){
            try{
               sleep(3000);
            } catch (InterruptedException e){
               e.printStackTrace();
            }finally{
               Intent openStartingPoint = new Intent(Quiz0.this,Quiz.class);
               startActivity(openStartingPoint);
               finish();
            }
         }
       };
       timer.start();
   
   }

}