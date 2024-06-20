package com.evodesign.buttontest;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ainirobot.coreservice.client.RobotApi;
import com.ainirobot.coreservice.client.listener.CommandListener;

public class MainActivity extends AppCompatActivity {

    private static int reqId = 0;
    private MediaPlayer mediaPlayer;
    private Handler handler;

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
        Button dance = findViewById(R.id.dance);
        Button stop = findViewById(R.id.stop);

        dance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle dance button click
                showToast("Start Dancing");

                // Initialize MediaPlayer
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.happybday);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // Stop the robot when the music is done
                        RobotApi.getInstance().stopMove(0, mMotionListener);
                        // Release the MediaPlayer
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                });

                // Start playing the music
                mediaPlayer.start();

                // Start the dance sequence
                startDance();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle stop button click
                showToast("Stop Dancing");
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                RobotApi.getInstance().stopMove(0, mMotionListener);
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                }
            }
        });

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
                        // Handle button press (start turning left)
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
                        // Handle button press (start turning right)
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
                        // Handle button press (start moving backward)
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
                // Handle success
            } else {
                // Handle failure
            }
        }
    };

    private void startDance() {
        handler = new Handler();
        final int[] movements = {0, 1, 2, 3, 4, 5}; // Sequence of movements
        final int interval = 500; // Interval between movements in milliseconds
        final int movementCount = movements.length;

        handler.post(new Runnable() {
            int currentMovement = 0;

            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    switch (movements[currentMovement % movementCount]) {
                        case 0:
                            RobotApi.getInstance().goForward(0, 0.2f, mMotionListener);
                            break;
                        case 1:
                            RobotApi.getInstance().goBackward(0, 0.2f, mMotionListener);
                            break;
                        case 2:
                            RobotApi.getInstance().turnLeft(0, 40, mMotionListener);
                            break;
                        case 3:
                            RobotApi.getInstance().turnRight(0, 40, mMotionListener);
                            break;
                        case 4:
                            RobotApi.getInstance().moveHead(reqId++, "relative", "relative", 0, -10, mMotionListener);
                            break;
                        case 5:
                            RobotApi.getInstance().moveHead(reqId++, "relative", "relative", 0, 10, mMotionListener);
                            break;
                    }

                    currentMovement++;
                    handler.postDelayed(this, interval);
                } else {
                    // Stop the robot after the music is done
                    RobotApi.getInstance().stopMove(0, mMotionListener);
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
