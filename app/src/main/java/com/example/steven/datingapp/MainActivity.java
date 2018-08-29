package com.example.steven.datingapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    LinearLayout.LayoutParams params;
    LinearLayout mainLayout;
    Button but;
    boolean click = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());



        popUp = new PopupWindow(this);
        layout = new LinearLayout(this);
        mainLayout = new LinearLayout(this);
        tv = new TextView(this);


        GradientDrawable popUpBG=new GradientDrawable();
        popUpBG.setCornerRadius(30);
        popUpBG.setColor(0x600000FF);


        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);

        layout.setOrientation(LinearLayout.VERTICAL);
        tv.setText("Pineapple on Pizza is ____\n" +
                "a) Delicious\n" +
                "b) An abomination\n" +
                "c) Never tried it\n" +
                "d) Meh");
        layout.addView(tv, params);
        //80 is alpha value, make semi transparent
        //other six digits to the right are RGB
        popUp.setBackgroundDrawable(popUpBG);
        popUp.setContentView(layout);
        //mainLayout.addView(but, params);
        setContentView(mainLayout);

        setRepeatingQuestions();

    }

    private void setRepeatingQuestions() {

        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                           displayQuestions();
                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 2*1000);  // interval of x seconds

    }

    public void displayQuestions(){
        if (click) {
            popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
            popUp.update(50, 50, 300, ViewGroup.LayoutParams.WRAP_CONTENT);
            click = false;
        } else {
            popUp.dismiss();
            click = true;
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
