package com.example.learningenglishapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CorrectwordResult extends Activity {
    Button PlayAgain, TroVe;
    TextView Result, NgoiSao;
    ArrayList<NguoiChoiCorWord> list = new ArrayList<NguoiChoiCorWord>();
    int sodiem, socau;
    private PieChart PieChart;
    String name;
    int ngoisao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.correctword_result);
        Result = (TextView)findViewById(R.id.KetQua);
        PlayAgain = (Button)findViewById(R.id.PlayAgain);
        TroVe = (Button)findViewById(R.id.Back);
        NgoiSao = (TextView) findViewById(R.id.Star);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);
        final MediaPlayer music = MediaPlayer.create(this,R.raw.winning);
        music.start();
        Intent callerIntent=getIntent();
        Bundle packageFromCaller= callerIntent.getBundleExtra("package");
        name = packageFromCaller.getString("name");
        sodiem = packageFromCaller.getInt("KQ");
        socau = packageFromCaller.getInt("num");
        ngoisao = packageFromCaller.getInt("star");
        readFromFile();

        NgoiSao.setText("Số ngôi sao của bạn là: " + ngoisao);
        Result.setText("Số câu trả lời: " + sodiem + "/" + socau);
        PieChart = findViewById(R.id.PieChart);
        setupPieChart();
        loadPieChartData();
        kiemtra();

        TroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent = new Intent(CorrectwordResult.this, Correctword.class);
                startActivity(intent);
                finish();
            }
        });

        PlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Intent intent1 = new Intent(CorrectwordResult.this, Entername.class);
                startActivity(intent1);
                finish();
            }
        });
    }

    public void saveToFile(ArrayList<NguoiChoiCorWord> list)
    {
        try
        {
            FileOutputStream outputStream = this.openFileOutput("nguoichoi2.csv", Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(outputStream);
            for (NguoiChoiCorWord in:list)
                pw.println(in);
            pw.close();
            outputStream.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
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

    private void kiemtra() {
        NguoiChoiCorWord temp = searchnguoichoi(name);
        if(temp == null) {
            NguoiChoiCorWord a=new NguoiChoiCorWord(name,sodiem);
            list.add(a);
            saveToFile(list);
        }
        else{
            if(temp.getDiem()<sodiem){
                temp.setdiem(sodiem);
                saveToFile(list);
            }
        }
    }

    protected NguoiChoiCorWord searchnguoichoi(String code)
    {
        for (NguoiChoiCorWord in:list)
        {
            if (in.getName().equalsIgnoreCase(code))
            {
                return in;
            }
        }
        return null;
    }

    private void setupPieChart() {
        PieChart.setDrawHoleEnabled(true);
        PieChart.setUsePercentValues(true);
        PieChart.setEntryLabelTextSize(12);
        PieChart.setEntryLabelColor(Color.BLACK);
        PieChart.setCenterText("Tổng số câu");
        PieChart.setCenterTextSize(24);
        PieChart.getDescription().setEnabled(false);

        Legend l = PieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        float temp = (float) sodiem/socau;
        entries.add(new PieEntry(temp,"Số câu đúng"));
        entries.add(new PieEntry(1.0f-temp,"Số câu sai"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet2 = new PieDataSet(entries, "Chú thích");
        dataSet2.setColors(colors);

        PieData data2 = new PieData(dataSet2);
        data2.setDrawValues(true);
        data2.setValueFormatter(new PercentFormatter(PieChart));
        data2.setValueTextSize(12f);
        data2.setValueTextColor(Color.BLACK);

        PieChart.setData(data2);
        PieChart.invalidate();

        PieChart.animateY(1400, Easing.EaseInOutQuad);
    }
}