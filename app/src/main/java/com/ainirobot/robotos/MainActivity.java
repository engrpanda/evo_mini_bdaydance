package com.ainirobot.robotos;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.ainirobot.coreservice.client.RobotApi;
import com.ainirobot.coreservice.client.listener.CommandListener;
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


        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle up button click
                showToast("Up button clicked");
                RobotApi.getInstance().goForward(0, 0.2f, mMotionListener);

                // Delay for 2 seconds (2000 milliseconds) and then stop the robot
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RobotApi.getInstance().stopMove(0, mMotionListener);
                    }
                }, 500);
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle left button click
                showToast("Left button clicked");
                RobotApi.getInstance().turnLeft(0, 40, mMotionListener);

                // Delay for 2 seconds (2000 milliseconds) and then stop the robot
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RobotApi.getInstance().stopMove(0, mMotionListener);
                    }
                }, 600);
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle right button click
                showToast("Right button clicked");
                RobotApi.getInstance().turnRight(0, 40, mMotionListener);

                // Delay for 2 seconds (2000 milliseconds) and then stop the robot
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RobotApi.getInstance().stopMove(0, mMotionListener);
                    }
                }, 600);
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle down button click
                showToast("Down button clicked");
                RobotApi.getInstance().goBackward(0, 0.1f, mMotionListener);

                // Delay for 2 seconds (2000 milliseconds) and then stop the robot
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RobotApi.getInstance().stopMove(0, mMotionListener);
                    }
                }, 300);
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
