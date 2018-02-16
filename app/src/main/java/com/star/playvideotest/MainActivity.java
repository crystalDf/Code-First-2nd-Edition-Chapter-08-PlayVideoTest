package com.star.playvideotest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;

    private VideoView mVideoView;

    private Button mPlay;
    private Button mPause;
    private Button mReplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVideoView = findViewById(R.id.video_view);

        mPlay = findViewById(R.id.play);
        mPause = findViewById(R.id.pause);
        mReplay = findViewById(R.id.replay);

        mPlay.setOnClickListener(v -> {

            if (!mVideoView.isPlaying()) {
                mVideoView.start();
            }
        });

        mPause.setOnClickListener(v -> {

            if (mVideoView.isPlaying()) {
                mVideoView.pause();
            }
        });

        mReplay.setOnClickListener(v -> {

            if (mVideoView.isPlaying()) {
                mVideoView.resume();
            }
        });

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else {
            initMediaPlayer();
        }
    }

    private void initMediaPlayer() {

        File file = new File(Environment.getExternalStorageDirectory(), "童话镇.mp4");

        mVideoView.setVideoPath(file.getPath());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case REQUEST_CODE:
                if ((grantResults.length > 0) &&
                        (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    initMediaPlayer();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mVideoView != null) {
            mVideoView.suspend();
        }
    }
}
