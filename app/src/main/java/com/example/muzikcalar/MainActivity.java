package com.example.muzikcalar;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton ibStart, ibPause, ibNext, ibMusicList, ibPrev;
    TextView tvMusicTitle;
    MediaPlayer mediaPlayer;
    ArrayList<String> musicList;
    int currentMusic = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ibPrev = findViewById(R.id.ibPrev);
        ibStart = findViewById(R.id.ibStart);
        ibPause = findViewById(R.id.ibPause);
        ibNext = findViewById(R.id.ibNext);
        ibMusicList = findViewById(R.id.ibMusicList);
        tvMusicTitle = findViewById(R.id.tvMusicTitle);
        musicList = new ArrayList<>();
        getList();
        mediaPlayer = MediaPlayer.create(this, R.raw.a_windows_remix);
        tvMusicTitle.setText(musicList.get(0));

        ibStart.setOnClickListener(this);
        ibPause.setOnClickListener(this);
        ibNext.setOnClickListener(this);
        ibMusicList.setOnClickListener(this);
        ibPrev.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibStart: {
                mediaPlayer.start();
                Log.d("MuzikCalar", "onClick: Start");
                break;
            }
            case R.id.ibPrev: {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                if (currentMusic==0){
                    currentMusic=musicList.size()-1;
                }
                else if (currentMusic <= musicList.size() - 1) {
                    currentMusic -= 1;
                } else {
                    currentMusic = 0;
                }
                String uri = "android.resource://" + getPackageName() + "/raw/" + musicList.get(currentMusic);
                mediaPlayer = MediaPlayer.create(this, Uri.parse(uri));
                tvMusicTitle.setText(musicList.get(currentMusic));
                mediaPlayer.start();
                Log.d("MuzikCalar", "onClick: Next " + currentMusic);
                break;
            }
            case R.id.ibNext: {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                if (currentMusic < musicList.size() - 1) {
                    currentMusic += 1;
                } else {
                    currentMusic = 0;
                }
                String uri = "android.resource://" + getPackageName() + "/raw/" + musicList.get(currentMusic);
                mediaPlayer = MediaPlayer.create(this, Uri.parse(uri));
                tvMusicTitle.setText(musicList.get(currentMusic));
                mediaPlayer.start();
                Log.d("MuzikCalar", "onClick: Next " + currentMusic);
                break;
            }
            case R.id.ibPause: {
                mediaPlayer.pause();
                Log.d("MuzikCalar", "onClick: Pause");
                break;
            }
            case R.id.ibMusicList: {
                // TODO muzik listesi ozelligini ekle
                Toast.makeText(this, "Bu özellik yakında eklenicek", Toast.LENGTH_SHORT).show();
                Log.d("MuzikCalar", "onClick: Music List");
                break;
            }
        }
    }

    public void getList() {
        Field[] fields = R.raw.class.getFields();
        for (int i = 0; i < fields.length; i++) {
            musicList.add(fields[i].getName());
        }
    }
}