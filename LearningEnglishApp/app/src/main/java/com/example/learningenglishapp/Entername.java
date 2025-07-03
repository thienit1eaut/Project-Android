package com.example.learningenglishapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class Entername extends Activity {
    EditText NhapTen;
    Button BatDau, TroVe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entername);

        NhapTen = (EditText)findViewById(R.id.UserName);
        BatDau= (Button)findViewById(R.id.Start);
        TroVe = (Button)findViewById(R.id.Back);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.buttonclick);

        BatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                String name;
                name = NhapTen.getText().toString().trim();
                Intent intent = new Intent(Entername.this, CorrectwordTest.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });

        TroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent1 = new Intent(Entername.this, Correctword.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}
