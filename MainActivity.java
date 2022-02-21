package com.example.testactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private Integer counter = 0; /// создание приватной переменной counter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha); /// обращаемся к анимации alpha
        Button btnAlpha = (Button)findViewById(R.id.alpha); /// обращаемся к объекту Button
        btnAlpha.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) { /// создание метода(события) Клик, тоесть то что будет происходить при клике
                counter++; /// увеличение  кол-ва очков при нажатии
                view.startAnimation(animAlpha); /// проигрыш анимации мигания
                TextView counterView = (TextView)findViewById(R.id.txt_counter); /// обращаемся к объекту TextView
                counterView.setText(counter.toString()); /// присваивание значения
                if (counter >= 1000){ /// проверка на кол-во очков
                    TextView layout = findViewById(R.id.txt_counter);
                    ViewGroup.LayoutParams params = layout.getLayoutParams();
                    params.width = 300; /// задаём размер ширины
                    layout.setLayoutParams(params); /// присваивание значения для layout
                }
                if (counter >= 10000){  /// проверка на кол-во очков
                    TextView layout = findViewById(R.id.txt_counter); /// обращаемся к объекту TextView
                    ViewGroup.LayoutParams params = layout.getLayoutParams(); /// создаём метод обращения к параметрам размещения(layout)
                    params.width = 600; /// задаём размер ширины
                    layout.setLayoutParams(params); /// присваивание значения для layout
                }
                if (counter == 20) { /// проверка на кол-во очков
                    openActivity2(); /// вызов метода перехода в другой активити
                }
            }
        });

            ///Добавление видео и элементов управления
        VideoView videoView = findViewById(R.id.video_view);
        String videoPath ="android.resource://" + getPackageName() + "/" + R.raw.video; /// добавления пути расположения нашего видео
        Uri uri = Uri.parse(videoPath); /// создание uri метода
        videoView.setVideoURI(uri); /// использование uri метода для работы видео
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
    /// Создание метода перехода в другой  активити
    public void openActivity2() {
        Intent intent = new Intent(this, Activity2.class); /// создание Intent метода и указание конректного активити, которое мы хотим открыть
        startActivity(intent);
    }
}
