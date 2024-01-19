package com.evodesign.buttontest;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.ainirobot.coreservice.client.RobotApi;
import com.ainirobot.coreservice.client.listener.CommandListener;
import com.ainirobot.robotos.R;

import android.os.Handler;
public class MainActivity extends AppCompatActivity {

    private static int reqId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button upButton = findViewById(R.id.upButton);
        Button leftButton = findViewById(R.id.leftButton);
        Button rightButton = findViewById(R.id.rightButton);
        Button downButton = findViewById(R.id.downButton);
        Button buttonA = findViewById(R.id.buttonA);
        Button buttonB = findViewById(R.id.buttonB);


        upButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Handle button press (start moving forward)
                        showToast("forward");
                        RobotApi.getInstance().goForward(0, 0.4f, mMotionListener);
                        return true; // Consume the event to prevent multiple ACTION_DOWN events

                    case MotionEvent.ACTION_UP:
                        // Handle button release (stop moving)
                        showToast("stop");
                        RobotApi.getInstance().stopMove(0, mMotionListener);
                        return true;

                    default:
                        return false;
                }
            }
        });


        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Handle button press (start moving forward)
                        showToast("turn left");
                        RobotApi.getInstance().turnLeft(0, 40, mMotionListener);
                        return true; // Consume the event to prevent multiple ACTION_DOWN events

                    case MotionEvent.ACTION_UP:
                        // Handle button release (stop moving)
                        showToast("stop");
                        RobotApi.getInstance().stopMove(0, mMotionListener);
                        return true;

                    default:
                        return false;
                }
            }
        });

        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Handle button press (start moving forward)
                        showToast("turn right");
                        RobotApi.getInstance().turnRight(0, 40, mMotionListener);
                        return true; // Consume the event to prevent multiple ACTION_DOWN events

                    case MotionEvent.ACTION_UP:
                        // Handle button release (stop moving)
                        showToast("stop");
                        RobotApi.getInstance().stopMove(0, mMotionListener);
                        return true;

                    default:
                        return false;
                }
            }
        });


        downButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Handle button press (start moving forward)
                        showToast("backward");
                        RobotApi.getInstance().goBackward(0, 0.1f, mMotionListener);
                        return true; // Consume the event to prevent multiple ACTION_DOWN events

                    case MotionEvent.ACTION_UP:
                        // Handle button release (stop moving)
                        showToast("stop");
                        RobotApi.getInstance().stopMove(0, mMotionListener);
                        return true;

                    default:
                        return false;
                }
            }
        });

        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle A button click
                showToast("A button clicked");
                RobotApi.getInstance().moveHead(reqId++, "relative", "relative", 0, -10, mMotionListener);
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle B button click
                showToast("B button clicked");
                RobotApi.getInstance().moveHead(reqId++, "relative", "relative", 0, 10, mMotionListener);
            }
        });
    }


    private CommandListener mMotionListener = new CommandListener() {
        @Override
        public void onResult(int result, String message) {
            if ("succeed".equals(message)) {
            } else {
            }
        }
    };

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
