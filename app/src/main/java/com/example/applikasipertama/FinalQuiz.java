package com.example.applikasipertama;

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
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FinalQuiz extends AppCompatActivity {

    private TextView waktu;
    private TextView resultTextView;
    private TextView questionTextView;
    private TextView Question;
    private Button submitButton;
    private ImageView action_image;
    private RadioGroup answersRadioGroup;
    private RadioButton answer1RadioButton;
    private RadioButton answer2RadioButton;
    private RadioButton answer3RadioButton;
    private RadioButton answer4RadioButton;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 90000;
    MediaPlayer audiobenar;
    MediaPlayer audiosalah;

    int nomoreasy = 0;
    int nomormedium = 10;
    int nomorhard = 20;
    int benar, salah = 0;
    int nilai;

    String difficulty;
    DatabaseHelper dbHelper;

    String Soalangka[] = new String[]{
            "8+5=",
            "9−3=",
            "6×4=",
            "15÷3=",
            "7+6−4=",
            "12−5+8=",
            "3×5+4=",
            "18÷6+7=",
            "(4+9)×2=",
            "(10−7)×5=",

            "35+27=",
            "64−29=",
            "8×7=",
            "56÷8+13=",
            "(23+48)−(12+15)=",
            "(81−36)÷3=",
            "14×5+8×3=",
            "(72÷9)+(15×2)=",
            "(15+45)×2−30=",
            "(100−57)÷(8−2)=",

            "45×19=",
            "256÷8=",
            "289−49=",
            "(8+12)×(15−9)=",
            "(18×12)+(25×4)=",
            "(600÷15)−(320÷8)=",
            "(145+378)−(245÷5)=",
            "(55×8)−(72÷9)=",
            "480÷(12−8)+93=",
            "(234+567)÷9=",
    };

    String Soal[] = new String[]{
            "1. Berapakah hasil dari..",
            "2. Berapakah hasil dari..",
            "3. Berapakah hasil dari..",
            "4. Berapakah hasil dari..",
            "5. Berapakah hasil dari..",
            "6. Berapakah hasil dari..",
            "7. Berapakah hasil dari..",
            "8. Berapakah hasil dari..",
            "9. Berapakah hasil dari..",
            "10. Berapakah hasil dari..",
    };

    String Pilihanjawaban[] = new String[]{
            "13", "4", "8", "12",
            "3", "13", "6", "23",
            "34", "46", "27", "24",
            "3", "2", "5", "13",
            "29", "9", "4", "20",
            "15", "35", "48", "49",
            "47", "19", "26", "14",
            "10", "47", "8", "23",
            "27", "24", "37", "26",
            "15", "14", "25", "16",

            "62", "44", "78", "72",
            "36", "35", "63", "68",
            "69", "35", "78", "56",
            "30", "20", "50", "45",
            "29", "90", "44", "20",
            "15", "35", "48", "49",
            "49", "94", "23", "114",
            "23", "56", "38", "45",
            "90", "97", "68", "35",
            "8.4567", "7.1667", "7.8667", "9.9667",

            "13", "855", "8", "12",
            "32", "13", "6", "23",
            "34", "240", "27", "24",
            "120", "2", "5", "13",
            "29", "9", "316", "20",
            "15", "35", "48", "0",
            "47", "19", "474", "14",
            "432", "47", "8", "23",
            "27", "24", "213", "26",
            "15", "14", "25", "89"
    };

    String Jawaban[] = new String[]{
            "13",
            "6",
            "24",
            "5",
            "9",
            "15",
            "19",
            "10",
            "26",
            "15",

            "62",
            "35",
            "56",
            "20",
            "44",
            "15",
            "94",
            "38",
            "90",
            "7.1667",

            "855",
            "32",
            "240",
            "120",
            "316",
            "0",
            "474",
            "432",
            "213",
            "89",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_final_quiz);

        waktu = findViewById(R.id.waktu);
        resultTextView = findViewById(R.id.resultTextView);
        questionTextView = findViewById(R.id.questionTextView);
        submitButton = findViewById(R.id.submitButton);
        Question = findViewById(R.id.Question);
        answersRadioGroup = findViewById(R.id.answersRadioGroup);
        answer1RadioButton = findViewById(R.id.answer1RadioButton);
        answer2RadioButton = findViewById(R.id.answer2RadioButton);
        answer3RadioButton = findViewById(R.id.answer3RadioButton);
        answer4RadioButton = findViewById(R.id.answer4RadioButton);
        audiobenar = MediaPlayer.create(this, R.raw.benar);
        audiosalah = MediaPlayer.create(this, R.raw.salah);
        audiobenar.setVolume(1,1);
        audiosalah.setVolume(1,1);

        dbHelper = new DatabaseHelper(this);
        nilai = getIntent().getIntExtra("nilai", 0);
        difficulty = dbHelper.getDifficulty();

        answersRadioGroup.check(0);

        if (difficulty.equals("Easy")) {
            Question.setText(Soalangka[nomoreasy]);
            answer1RadioButton.setText(Pilihanjawaban[0]);
            answer2RadioButton.setText(Pilihanjawaban[1]);
            answer3RadioButton.setText(Pilihanjawaban[2]);
            answer4RadioButton.setText(Pilihanjawaban[3]);
        } else if (difficulty.equals("Medium")) {
            Question.setText(Soalangka[nomormedium]);
            answer1RadioButton.setText(Pilihanjawaban[(nomormedium*4) + 0]);
            answer2RadioButton.setText(Pilihanjawaban[(nomormedium*4) + 1]);
            answer3RadioButton.setText(Pilihanjawaban[(nomormedium*4) + 2]);
            answer4RadioButton.setText(Pilihanjawaban[(nomormedium*4) + 3]);
        } else if (difficulty.equals("Hard")) {
            Question.setText(Soalangka[nomorhard]);
            answer1RadioButton.setText(Pilihanjawaban[(nomorhard*4) + 0]);
            answer2RadioButton.setText(Pilihanjawaban[(nomorhard*4) + 1]);
            answer3RadioButton.setText(Pilihanjawaban[(nomorhard*4) + 2]);
            answer4RadioButton.setText(Pilihanjawaban[(nomorhard*4) + 3]);
        }

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                waktu.setText(millisUntilFinished / 1000 + "s");
            }
            @Override
            public void onFinish() {
                waktu.setText("Waktu Habis!!");
                nilai = benar * 10;
                Intent next = new Intent(getApplicationContext(), HasilKuis.class);
                next.putExtra("nilai", nilai);
                next.putExtra("benar", benar);
                next.putExtra("salah", salah);
                startActivity(next);
            }
        }.start();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer1RadioButton.isChecked() || answer2RadioButton.isChecked() || answer3RadioButton.isChecked() || answer4RadioButton.isChecked()) {

                    RadioButton Pilihan_User = findViewById(answersRadioGroup.getCheckedRadioButtonId());
                    String Jawaban_User = Pilihan_User.getText().toString();
                    answersRadioGroup.check(0);

                    if(difficulty.equals("Easy")) {
                        if (Jawaban_User.equalsIgnoreCase(Jawaban[nomoreasy])) {
                            benar++;
                            resultTextView.setText("Mantap");
                            audiobenar.start();
                        } else {
                            salah++;
                            resultTextView.setText("Salah bro");
                            audiosalah.start();
                        }
                        nomoreasy++;
                        if (nomoreasy < Soal.length) {
                            questionTextView.setText(Soal[nomoreasy]);
                            Question.setText(Soalangka[nomoreasy]);

                            answer1RadioButton.setText(Pilihanjawaban[(nomoreasy * 4) + 0]);
                            answer2RadioButton.setText(Pilihanjawaban[(nomoreasy * 4) + 1]);
                            answer3RadioButton.setText(Pilihanjawaban[(nomoreasy * 4) + 2]);
                            answer4RadioButton.setText(Pilihanjawaban[(nomoreasy * 4) + 3]);
                        } else {
                            nilai = benar * 10;
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                            }
                            Intent next = new Intent(getApplicationContext(), HasilKuis.class);
                            next.putExtra("nilai", nilai);
                            next.putExtra("benar", benar);
                            next.putExtra("salah", salah);
                            startActivity(next);
                        }
                    }
                    if(difficulty.equals("Medium")) {
                        if (Jawaban_User.equalsIgnoreCase(Jawaban[nomormedium])) {
                            benar++;
                            resultTextView.setText("Mantap");
                            audiobenar.start();
                        } else {
                            salah++;
                            resultTextView.setText("Salah bro");
                            audiosalah.start();
                        }
                        nomoreasy++;
                        nomormedium++;
                        if (nomoreasy < Soal.length) {
                            questionTextView.setText(Soal[nomormedium]);
                            Question.setText(Soalangka[nomormedium]);

                            answer1RadioButton.setText(Pilihanjawaban[(nomormedium * 4) + 0]);
                            answer2RadioButton.setText(Pilihanjawaban[(nomormedium * 4) + 1]);
                            answer3RadioButton.setText(Pilihanjawaban[(nomormedium * 4) + 2]);
                            answer4RadioButton.setText(Pilihanjawaban[(nomormedium * 4) + 3]);
                        } else {
                            nilai = benar * 10;
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                            }
                            Intent next = new Intent(getApplicationContext(), HasilKuis.class);
                            next.putExtra("nilai", nilai);
                            next.putExtra("benar", benar);
                            next.putExtra("salah", salah);
                            startActivity(next);
                        }
                    }
                    if(difficulty.equals("Hard")) {
                        if (Jawaban_User.equalsIgnoreCase(Jawaban[nomorhard])) {
                            benar++;
                            resultTextView.setText("Mantap");
                            audiobenar.start();
                        } else {
                            salah++;
                            resultTextView.setText("Salah bro");
                            audiosalah.start();
                        }
                        nomoreasy++;
                        nomorhard++;
                        if (nomoreasy < Soal.length) {
                            questionTextView.setText(Soal[nomorhard]);
                            Question.setText(Soalangka[nomorhard]);

                            answer1RadioButton.setText(Pilihanjawaban[(nomorhard * 4) + 0]);
                            answer2RadioButton.setText(Pilihanjawaban[(nomorhard * 4) + 1]);
                            answer3RadioButton.setText(Pilihanjawaban[(nomorhard * 4) + 2]);
                            answer4RadioButton.setText(Pilihanjawaban[(nomorhard * 4) + 3]);
                        } else {
                            nilai = benar * 10;
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                            }
                            Intent next = new Intent(getApplicationContext(), HasilKuis.class);
                            next.putExtra("nilai", nilai);
                            next.putExtra("benar", benar);
                            next.putExtra("salah", salah);
                            startActivity(next);
                        }
                    }
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}