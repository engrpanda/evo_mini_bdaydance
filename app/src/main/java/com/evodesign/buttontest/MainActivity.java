package com.evodesign.buttontest;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.MediaController;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.ainirobot.coreservice.client.RobotApi;
import com.ainirobot.coreservice.client.listener.CommandListener;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private Handler handler;
    private boolean isDancing = false; // Track if dancing is in progress

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize VideoView and MediaController
        videoView = findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

        // Set the video URI
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bdayvideo);
        videoView.setVideoURI(videoUri);

        videoView.setOnCompletionListener(mp -> stopDance()); // Stop dancing when video completes

        // Initialize Start button
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> startDance());

        // Initialize Stop button
        Button stopButton = findViewById(R.id.stop);
        stopButton.setOnClickListener(v -> stopDance());

        // Initialize Back button
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
    }

    private void startDance() {
        if (!isDancing) { // Ensure we're not already dancing
            isDancing = true; // Set dancing flag

            // Reset video to the start and play it from the beginning
            videoView.seekTo(0);
            videoView.start();

            // Start the robot dance routine
            startDanceRoutine();
        }
    }

    private void startDanceRoutine() {
        handler = new Handler();
        final int interval = 2000; // 2 seconds delay

        handler.post(new Runnable() {
            private boolean turnRight = true; // Track which direction the robot should move

            @Override
            public void run() {
                if (!isDancing) { // Stop dancing if not in progress
                    return;
                }

                if (turnRight) {
                    RobotApi.getInstance().turnRight(0, 40, mMotionListener);
                    Log.d("DanceRoutine", "Turning Right");
                } else {
                    RobotApi.getInstance().turnLeft(0, 40, mMotionListener);
                    Log.d("DanceRoutine", "Turning Left");
                }

                // Toggle direction for next iteration
                turnRight = !turnRight;

                // Schedule next movement after 2 seconds
                handler.postDelayed(this, interval);
            }
        });
    }

    // Listener to handle robot motion events
    private CommandListener mMotionListener = new CommandListener() {
        @Override
        public void onResult(int result, String message) {
            Log.d("MovementListener", "Result: " + result + ", Message: " + message);
            if ("succeed".equals(message)) {
                Log.d("DanceRoutine", "Movement succeeded");
            } else {
                Log.d("DanceRoutine", "Movement failed: " + result);
            }
        }
    };

    // Stop the dance and clean up
    private void stopDance() {
        showToast("Stop Dancing");
        isDancing = false; // Reset the dancing flag

        if (videoView.isPlaying()) {
            videoView.pause();  // Pause the video if it's playing
            videoView.seekTo(0); // Reset video to the start
        }

        stopMovement();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null; // Clear handler reference
        }
    }

    private void stopMovement() {
        RobotApi.getInstance().stopMove(0, mMotionListener);
    }

    // Show toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        stopDance(); // Ensure the dance stops when the activity is destroyed
        super.onDestroy();
    }
}
