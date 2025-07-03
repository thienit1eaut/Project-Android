package com.example.learningenglishapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

public class Homepage extends AppCompatActivity{
    Button EnglishQuiz, CorrectWord, PersonalProfile, Thoat;
    private FirebaseAuth Authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        Authentication = (FirebaseAuth)FirebaseAuth.getInstance();
        EnglishQuiz = (Button) findViewById(R.id.EnglishQuiz);
        CorrectWord = (Button) findViewById(R.id.CorrectWord);
        PersonalProfile = (Button) findViewById(R.id.PersonalProfile);
        Thoat = (Button) findViewById(R.id.LogOut);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.buttonclick);

        EnglishQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent = new Intent(Homepage.this, Englishquiz.class);
                startActivity(intent);
            }
        });

        CorrectWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent1 = new Intent(Homepage.this, Correctword.class);
                startActivity(intent1);
            }
        });

        PersonalProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent1 = new Intent(Homepage.this, PersonalProfile.class);
                startActivity(intent1);
            }
        });

        Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                showLogoutDialog();
            }
        });
    }

    // Phương thức hiển thị Dialog xác nhận đăng xuất
    private void showLogoutDialog() {
        // Tạo AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đăng xuất người dùng khi nhấn "Có"
                        Authentication.signOut();

                        // Hiển thị thông báo đăng xuất thành công
                        Toast.makeText(Homepage.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

                        Intent intent2 = new Intent(Homepage.this, MainActivity.class);
                        startActivity(intent2);
                        finish();
                    }
                })
                .setNegativeButton("Không", null) // Nếu người dùng chọn "Không", không làm gì cả
                .show();
    }
}

