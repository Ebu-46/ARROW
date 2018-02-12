package com.example.dell.test;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    private  int screenWidth;
    private  int screenHeight;

    public ImageView arrowUp;
    public ImageView watermelon;
    public ImageView boom;

    private float arrowUpX;
    private float arrowUpY;

    public float watermelonX;
    public float watermelonY;

    private float boomX;
    private float boomY;

    public int total=0;
    public int score=0;

    public RelativeLayout mainLayout;
    public RelativeLayout winnerLayout;

    private Handler handler=new Handler();
    private Timer timer = new Timer();

    boolean clicked;
    boolean gmOver=false;
    boolean check=false;

    public void gameLogic(View view){
        arrowUp = (ImageView) findViewById(R.id.arrowUp);
        clicked = true;


        WindowManager wm=getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size =new Point();
        disp.getSize(size);
        screenWidth=size.x;
        screenHeight=size.y;
        arrowUpX=screenWidth*.3f;
        arrowUp.setX(screenWidth*.3f);
        arrowUp.setY(-80.0f);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(clicked)changePos();
                    }

                });
            }
        },0,20);
    }

        public void changePos(){
            arrowUpY-=10;
            if(arrowUp.getY() + arrowUp.getHeight() < 0){
                if(check) score++;
                //arrowUpX=(float) Math.floor(Math.random()*(screenWidth-arrowUp.getWidth()));
                arrowUpY=screenHeight-2*arrowUp.getHeight();
                clicked=false;
                check=false;
            }

            if(Math.abs(arrowUpY-watermelonY)<60.0f && Math.abs(watermelonX-arrowUpX)<60.0f) {
                check=true;
                watermelon.setVisibility(View.INVISIBLE);
                boom.setVisibility(View.VISIBLE);
            }


            arrowUp.setX(arrowUpX);
            arrowUp.setY(arrowUpY);
        }


   


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        winnerLayout = (RelativeLayout) findViewById(R.id.winnerLayout);
        winnerLayout.setVisibility(View.INVISIBLE);

        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mainLayout.setVisibility(View.VISIBLE);

       /* arrowUp = (ImageView) findViewById(R.id.arrowUp);
        WindowManager wm=getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size =new Point();
        disp.getSize(size);
        screenWidth=size.x;
        screenHeight=size.y;
        arrowUp.setX(-80.0f);
        arrowUp.setY(-80.0f);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos();
                    }

                });
            }
        },0,20);
    }

    public void changePos(){
        arrowUpY-=10;
        if(arrowUp.getY() + arrowUp.getHeight() < 0){
            arrowUpX=(float) Math.floor(Math.random()*(screenWidth-arrowUp.getWidth()));
            arrowUpY=screenHeight+100.0f;
        }
        arrowUp.setX(arrowUpX);
        arrowUp.setY(arrowUpY);
    }*/


        watermelon = (ImageView) findViewById(R.id.watermelon);
        WindowManager wm=getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size =new Point();
        disp.getSize(size);
        screenWidth=size.x;
        screenHeight=size.y;
        watermelon.setX(-80.0f);
        watermelon.setY(10.0f);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       if(!gmOver) changePosW();
                    }

                });
            }
        },10,50);


        //boom
        boom = (ImageView) findViewById(R.id.boom);
        boom.setVisibility(View.INVISIBLE);
        boom.setX(-80.0f);
        boom.setY(10.0f);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(!gmOver)changePosB();
                    }

                });
            }
        },10,50);


    }

    public void changePosW(){
        watermelonX+=10;
        if(watermelon.getX() + watermelon.getWidth() > screenWidth){
            watermelon.setVisibility(View.VISIBLE);
            boom.setVisibility(View.INVISIBLE);
            total++;
            //arrowUpX=(float) Math.floor(Math.random()*(screenWidth-arrowUp.getWidth()));
            watermelonX=-100.0f;
            TextView scr = (TextView) findViewById(R.id.textView);
            TextView mis = (TextView) findViewById(R.id.textView2);

            scr.setText("Score : " + String.valueOf(score));
            mis.setText("Missed : " + String.valueOf(total-score));

            if(total-score>=10){
                gameOver();
            }
        }
        watermelon.setX(watermelonX);
        watermelon.setY(watermelonY);
    }

    //boom

    public void changePosB(){
        boomX+=10;
        if(boom.getX() + boom.getWidth() > screenWidth){
            //arrowUpX=(float) Math.floor(Math.random()*(screenWidth-arrowUp.getWidth()));
            boomX=-100.0f;
        }
        boom.setX(boomX);
        boom.setY(boomY);
    }

     public void playAgain(View view){

        winnerLayout.setVisibility(View.INVISIBLE);

        gmOver=false;
        total=0;
        score=0;

        watermelonX=-80.0f;
        watermelonY=10.0f;

        boomX=-80.0f;
        boomY=10.0f;

        watermelon.setX(-80.0f);
        watermelon.setY(10.0f);

        boom.setX(-80.0f);
        boom.setY(10.0f);

        arrowUp=(ImageView) findViewById(R.id.arrowUp);
        arrowUp.setVisibility(View.VISIBLE);

        watermelon=(ImageView) findViewById(R.id.watermelon);
        watermelon.setVisibility(View.VISIBLE);

        boom=(ImageView) findViewById(R.id.boom);
        boom.setVisibility(View.INVISIBLE);

        //mainLayout.setVisibility(View.VISIBLE);
        TextView scr = (TextView) findViewById(R.id.textView);
        scr.setText("Score : 0");
        scr.setVisibility(View.VISIBLE);

        TextView mis = (TextView) findViewById(R.id.textView2);
        mis.setText("Missed : 0");
        mis.setVisibility(View.VISIBLE);

    }



}
