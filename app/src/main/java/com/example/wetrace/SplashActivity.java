package com.example.wetrace;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    VideoView videoHolder;
    private FirebaseAuth fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        fa = FirebaseAuth.getInstance();
        try {
            videoHolder = findViewById(R.id.videoView);
            Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.covid);
            videoHolder.setVideoURI(video);
            videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    jump();
                }
            });
            videoHolder.start();
        } catch (
                Exception ex) {
            jump();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        jump();
        return true;
    }

    private void jump() {
        if (isFinishing())
            return;
        FirebaseUser currentUser = fa.getCurrentUser();
        if (currentUser == null) {
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        } else {
            Intent in = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(in);
            finish();
        }
    }
}