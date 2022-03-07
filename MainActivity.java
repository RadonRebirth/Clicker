package com.example.testactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    final String LOG_TAG = "myLogs";
    Integer counter = 0;
    /// метод перехода в другой активити
    public void openActivity2() {
        Intent intent = new Intent(this, Activity2.class); /// создание Intent метода и указание конректного активити, которое мы хотим открыть
        startActivity(intent);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        Button btn = (Button)findViewById(R.id.alpha);
        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                counter++;
                view.startAnimation(animAlpha);
                TextView counterView = findViewById(R.id.txt_counter);
                ViewGroup.LayoutParams params = counterView.getLayoutParams();
                counterView.setText(counter.toString());
                if (counter >= 1000){
                    params.width = 300;
                    counterView.setLayoutParams(params);
                }
                if (counter >= 10000){
                    params.width = 600;
                    counterView.setLayoutParams(params);
                }
                if (counter == 20) {
                    openActivity2();
                }
            }
        });
            ///Добавление видео и элементов управления
        VideoView videoView = findViewById(R.id.video_view);
        String videoPath ="android.resource://" + getPackageName() + "/" + R.raw.video; /// добавления пути расположения нашего видео
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start(); /// автозапуск видео
        videoView.setOnCompletionListener ( new MediaPlayer.OnCompletionListener() {

            @Override /// создание метода при завершении видео (onComplectetion)
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start(); /// автозапуск видео
            }
        });
        MediaController mediaController = new MediaController(this); /// создание метода элементов управления видео
        videoView.setMediaController(mediaController); /// установка метода для объекта videoView
        mediaController.setAnchorView(videoView);
    }
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey("Count")) {
            counter = savedInstanceState.getInt("Count");
        }
        Log.d(LOG_TAG, "onRestoreInstanceState");
    }
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Count", counter);
        Log.d(LOG_TAG, "onSaveInstateState");
    }
    public void resetUI() {
        ((TextView) findViewById(R.id.txt_counter)).setText(counter.toString());
        Log.d(LOG_TAG, "resetUI");
    }
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
        resetUI();
    }
}
