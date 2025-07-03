package com.example.learningenglishapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;

public class CorrectWordHighScore extends Activity {
    Button Back;
    ListView ListPlayer;

    ArrayList<NguoiChoiCorWord> list = new ArrayList<NguoiChoiCorWord>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.correctword_highscore);

        Back = (Button)findViewById(R.id.Back);
        ListPlayer = (ListView) findViewById(R.id.ListView);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.buttonclick);


        Print();
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent = new Intent(CorrectWordHighScore.this, Correctword.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void Print()
    {
        list.clear();
        readFromFile();
        sortDiem();
        ArrayList<String> temp = new ArrayList<String>();
        for (NguoiChoiCorWord in:list) {
            temp.add(in.toString1());
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,temp);
        ListPlayer.setAdapter(adapter2);
    }

    public void readFromFile() {
        try{
            String splitBy = ",";
            FileInputStream in = this.openFileInput("nguoichoi2.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while (br != null) {
                String line = br.readLine();
                String[] value = line.split(splitBy);
                list.add(new NguoiChoiCorWord(value[0], Integer.parseInt(value[1])));
            }
            br.close();
        }
        catch (Exception e){
            System.out.println(""+e.getMessage());
        }
    }

    public void sortDiem()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.sort(new Comparator<NguoiChoiCorWord>()
            {
                @Override
                public int compare(NguoiChoiCorWord s, NguoiChoiCorWord s1)
                {
                    if (s.getDiem()<s1.getDiem()) return 1;
                    if (s.getDiem() == s1.getDiem()) return 0;
                    return -1;
                }
            });
        }
    }
}
