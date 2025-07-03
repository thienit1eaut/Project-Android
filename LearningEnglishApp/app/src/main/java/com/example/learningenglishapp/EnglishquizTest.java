package com.example.learningenglishapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
import com.example.learningenglishapp.R;

//class QuestionNare {
//    public String ID;
//    public String AnswerA, AnswerB, AnswerC, AnswerD, Answer;
//}

public class EnglishquizTest extends AppCompatActivity {
    TextView KetQua, CauHoi, ThoiGian;
    ImageView HinhAnh;
    RadioGroup RG;
    Button TraLoi, TroGiup, BoQua, KetThuc;
    RadioButton A, B, C, D;
    int pos = 0;
    int kq = 0;
    CountDownTimer Time;
    public ArrayList<QuestionNare> list = new ArrayList();
    public ArrayList<Question> PList = new ArrayList();

    public void countdown() {
        Time = new CountDownTimer(21000, 1000) {

            public void onTick(long millisUntilFinished) {
                ThoiGian.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                pos++;
                if (pos >= list.size()) {
                    Intent callerIntent = getIntent();
                    Bundle packageFromCaller = callerIntent.getBundleExtra("bundle");
                    String name = packageFromCaller.getString("name");
                    Intent intent = new Intent(EnglishquizTest.this, EnglishquizResult.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("kq", kq);
                    bundle.putInt("num", pos);
                    bundle.putString("name", name);
                    intent.putExtra("package", bundle);
                    startActivity(intent);
                    finish();
                } else Display(pos);
            }
        }.start();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.englishquiz_test);

        CauHoi = (TextView) findViewById(R.id.Question);
        KetQua = (TextView) findViewById(R.id.Result);
        RG = (RadioGroup) findViewById(R.id.RadioGroup);
        A = (RadioButton) findViewById(R.id.RdbA);
        B = (RadioButton) findViewById(R.id.RdbB);
        C = (RadioButton) findViewById(R.id.RdbC);
        D = (RadioButton) findViewById(R.id.RdbD);
        TraLoi = (Button) findViewById(R.id.Answer);
        HinhAnh = (ImageView) findViewById(R.id.QuestionPicture);
        TroGiup = (Button) findViewById(R.id.Help);
        BoQua = (Button) findViewById(R.id.Skip);
        ThoiGian = (TextView) findViewById(R.id.Time);
        KetThuc = (Button) findViewById(R.id.End);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);

        AddQuestionFromFileTXT();
        CreateQuestion();
        Display(pos);

        KetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnglishquizTest.this, Englishquiz.class);
                startActivity(intent);
                finish();
            }
        });

        TroGiup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                String temp = list.get(pos).Answer;
                switch (temp) {
                    case "A":
                        B.setVisibility(View.INVISIBLE);
                        D.setVisibility(View.INVISIBLE);
                        break;
                    case "B":
                        A.setVisibility(View.INVISIBLE);
                        C.setVisibility(View.INVISIBLE);
                        break;
                    case "C":
                        B.setVisibility(View.INVISIBLE);
                        D.setVisibility(View.INVISIBLE);
                        break;
                    case "D":
                        A.setVisibility(View.INVISIBLE);
                        C.setVisibility(View.INVISIBLE);
                        break;
                }
                TroGiup.setVisibility(View.INVISIBLE);
            }
        });

        BoQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time.cancel();
                mediaPlayer.start();
                kq = kq + 1;
                pos++;
                Display(pos);
                BoQua.setVisibility(View.INVISIBLE);
            }
        });

        TraLoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time.cancel();
                mediaPlayer.start();
                int idCheck = RG.getCheckedRadioButtonId();
                if (idCheck == -1) {
                    // Nếu không có lựa chọn, có thể thông báo cho người dùng hoặc bỏ qua
                    Toast.makeText(EnglishquizTest.this, "Vui lòng chọn một đáp án!!", Toast.LENGTH_SHORT).show();
                    return; // Dừng xử lý nếu không có lựa chọn
                }

                if (idCheck == R.id.RdbA) {
                    if (list.get(pos).Answer.compareTo("A") == 0) kq = kq + 1;
                } else if (idCheck == R.id.RdbB) {
                    if (list.get(pos).Answer.compareTo("B") == 0) kq = kq + 1;
                } else if (idCheck == R.id.RdbC) {
                    if (list.get(pos).Answer.compareTo("C") == 0) kq = kq + 1;
                } else if (idCheck == R.id.RdbD) {
                    if (list.get(pos).Answer.compareTo("D") == 0) kq = kq + 1;
                }
                /*
                switch (idCheck) {
                    case R.id.RdbA:
                        if (list.get(pos).Answer.compareTo("A") == 0) kq = kq + 1;
                        break;
                    case R.id.RdbB:
                        if (list.get(pos).Answer.compareTo("B") == 0) kq = kq + 1;
                        break;
                    case R.id.RdbC:
                        if (list.get(pos).Answer.compareTo("C") == 0) kq = kq + 1;
                        break;
                    case R.id.RdbD:
                        if (list.get(pos).Answer.compareTo("D") == 0) kq = kq + 1;
                        break;
                }
                */

                pos++;
                if (pos >= list.size()) {
                    Intent callerIntent = getIntent();
                    Bundle packageFromCaller = callerIntent.getBundleExtra("bundle");
                    String name = packageFromCaller.getString("name");
                    Intent intent1 = new Intent(EnglishquizTest.this, EnglishquizResult.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("kq", kq);
                    bundle.putInt("num", pos);
                    bundle.putString("name", name);
                    intent1.putExtra("package", bundle);
                    startActivity(intent1);
                    pos = 0;
                    kq = 0;
                    Display(pos);
                    finish();
                } else Display(pos);
            }
        });
    }

    void Display(int i) {
        countdown();
        int resID = getResources().getIdentifier(list.get(i).ID, "drawable", getPackageName());
        HinhAnh.setImageResource(resID);
        A.setText(list.get(i).AnswerA);
        B.setText(list.get(i).AnswerB);
        C.setText(list.get(i).AnswerC);
        D.setText(list.get(i).AnswerD);
        KetQua.setText("Câu đúng: " + kq);
        RG.clearCheck();
        A.setVisibility(View.VISIBLE);
        B.setVisibility(View.VISIBLE);
        C.setVisibility(View.VISIBLE);
        D.setVisibility(View.VISIBLE);
    }

    public void AddQuestionFromFileTXT() {
        // checkFileExitsAdd();

        try {
            String splitBy = ",";
            FileInputStream in = this.openFileInput("Question.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while (br != null) {
                String line = br.readLine();
                String[] value = line.split(splitBy);
                PList.add(new Question(value[1], Integer.parseInt(value[0])));
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Loi tao file question.txt" + e.getMessage());
        }
    }

    private void checkFileExitsAdd() {
        // Lấy đường dẫn đến thư mục files của ứng dụng
        FileOutputStream fos = null;
        try {
            // Mở file với quyền ghi (Context.MODE_PRIVATE sẽ tạo mới file nếu chưa tồn tại)
            fos = openFileOutput("Question.txt", Context.MODE_PRIVATE);

            // Ghi dữ liệu vào file
            String content = "1,Apple\n" +
                    "2,Book\n" +
                    "3,Clock\n" +
                    "4,Bicycle\n" +
                    "5,Coin\n" +
                    "6,Wardrobe\n" +
                    "7,Monitor\n" +
                    "8,Shoes\n" +
                    "9,Washing machine\n" +
                    "10,Toothbrush\n" +
                    "11,Razor\n" +
                    "12,Scissors\n" +
                    "13,Air conditioner\n" +
                    "14,Curtain\n" +
                    "15,Mirror\n" +
                    "16,Lightswitch\n" +
                    "17,Comb\n" +
                    "18,Eraser\n" +
                    "19,Ribbon\n" +
                    "20,Newspaper\n" +
                    "21,Straws\n" +
                    "22,Jigsaws\n" +
                    "23,Bandage\n" +
                    "24,Surgical mask\n" +
                    "25,Wheelchair\n" +
                    "26,Syringe\n" +
                    "27,Crutch\n" +
                    "28,Scales\n" +
                    "29,Chainsaw\n" +
                    "30,Drill\n" +
                    "31,Screwdriver\n" +
                    "32,Wrench\n" +
                    "33,Hammer\n" +
                    "34,Carpet\n" +
                    "35,Shelf\n" +
                    "36,Necklace\n" +
                    "37,Tweezers\n" +
                    "38,Dental floss\n" +
                    "39,Tissues\n" +
                    "40,Jewelry box\n" +
                    "41,Calculator\n" +
                    "42,Ballpoint\n" +
                    "43,Blackboard\n" +
                    "44,Remote\n" +
                    "45,Binder clip\n" +
                    "46,Coat stand\n" +
                    "47,Iron\n" +
                    "48,Blinds\n" +
                    "49,Sponge\n" +
                    "50,Vase";
            fos.write(content.getBytes());  // Ghi nội dung dưới dạng byte

        } catch (IOException e) {
            e.printStackTrace();  // Nếu có lỗi, in ra log
        } finally {
            try {
                if (fos != null) fos.close();  // Đảm bảo đóng file sau khi sử dụng
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void CreateQuestion() {
        Intent callerIntent = getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra("bundle");
        int number = packageFromCaller.getInt("number");

        for (int i = 0; i <= number - 1; i++) {
            QuestionNare Q = new QuestionNare();
            Random generator = new Random();
            Q.AnswerA = PList.get(generator.nextInt(50)).getName();
            do {
                Q.AnswerB = PList.get(generator.nextInt(50)).getName();
            } while (Q.AnswerA == Q.AnswerB);

            do {
                Q.AnswerC = PList.get(generator.nextInt(50)).getName();
            } while (Q.AnswerC == Q.AnswerB || Q.AnswerC == Q.AnswerA);

            do {
                Q.AnswerD = PList.get(generator.nextInt(50)).getName();
            } while (Q.AnswerD == Q.AnswerC || Q.AnswerD == Q.AnswerB || Q.AnswerD == Q.AnswerA);

            int value = generator.nextInt(4);
            int find = 0;

            switch (value) {
                case 0:
                    find = PList.indexOf(searchQuestion(Q.AnswerA));
                    Q.Answer = "A";
                    break;
                case 1:
                    find = PList.indexOf(searchQuestion(Q.AnswerB));
                    Q.Answer = "B";
                    break;
                case 2:
                    find = PList.indexOf(searchQuestion(Q.AnswerC));
                    Q.Answer = "C";
                    break;
                case 3:
                    find = PList.indexOf(searchQuestion(Q.AnswerD));
                    Q.Answer = "D";
                    break;
            }
            Q.ID = "a" + PList.get(find).getId();
            list.add(Q);
        }
    }

    public Question searchQuestion(String code) {
        for (Question in : PList) {
            if (in.getName().equalsIgnoreCase(code)) {
                return in;
            }
        }
        return null;
    }
}
