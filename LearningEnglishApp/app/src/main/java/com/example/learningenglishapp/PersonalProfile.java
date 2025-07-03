package com.example.learningenglishapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PersonalProfile extends AppCompatActivity {
    Button Back;
    ListView ListPlayer;
    private FirebaseAuth Authentication;
    ArrayList<UserFile> list = new ArrayList<UserFile>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_profile);

        Back = (Button) findViewById(R.id.Back);
        ListPlayer = (ListView) findViewById(R.id.ListView);
        Authentication = (FirebaseAuth)FirebaseAuth.getInstance();
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);

        Print();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent = new Intent(PersonalProfile.this, Homepage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void Print() {
        list.clear();
        FirebaseUser currentUser = Authentication.getCurrentUser();
        if (currentUser != null) {
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            if(name.equals("")) {
                int atIndex = email.indexOf("@");
                if (atIndex != -1) {
                    name = email.substring(0, atIndex);
                }
            }

            list.add(new UserFile(name, email));
        }

        ArrayList<String> temp = new ArrayList<String>();
        for (UserFile in : list) {
            temp.add(in.toString());
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, temp);
        ListPlayer.setAdapter(adapter1);
    }

}
