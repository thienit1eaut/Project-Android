package com.example.learningenglishapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Englishquiz extends AppCompatActivity {
    Button Start, HighScore, Back, Tutorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.englishquiz);

        Start = (Button)findViewById(R.id.Start);
        HighScore = (Button)findViewById(R.id.HighScore);
        Back = (Button)findViewById(R.id.Back);
        Tutorial = (Button)findViewById(R.id.Tutorial);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.buttonclick);

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent = new Intent(Englishquiz.this, Playername.class);
                startActivity(intent);
            }
        });

        HighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent1 = new Intent(Englishquiz.this, EnglishQuizHighScore.class);
                startActivity(intent1);
            }
        });

        Tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent2 = new Intent(Englishquiz.this, HuongDanQuiz.class);
                startActivity(intent2);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent3 = new Intent(Englishquiz.this, Homepage.class);
                startActivity(intent3);
                finish();
            }
        });
    }
}

