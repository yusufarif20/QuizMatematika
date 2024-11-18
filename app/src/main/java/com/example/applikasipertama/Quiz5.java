package com.example.applikasipertama;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Quiz5 extends AppCompatActivity {

    private TextView waktu;
    private TextView resultTextView;
    private TextView Question;
    private Button submitButton;
    private Button selanjutnya;
    private RadioGroup answersRadioGroup;
    private RadioButton answer1RadioButton;
    private RadioButton answer2RadioButton;
    private RadioButton answer3RadioButton;
    private RadioButton answer4RadioButton;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000;
    MediaPlayer audiobenar;
    MediaPlayer audiosalah;
    MediaPlayer gameover;

    int nilai;
    String difficulty;
    DatabaseHelper dbHelper;

    String Pilihanjawaban[] = new String[]{
            "11", "13", "30", "25",
            "246", "254", "1134", "186",
            "456", "255", "396", "351"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz5);

        dbHelper = new DatabaseHelper(this);

        waktu = findViewById(R.id.waktu);
        resultTextView = findViewById(R.id.resultTextView);
        Question = findViewById(R.id.Question);
        submitButton = findViewById(R.id.submitButton);
        selanjutnya = findViewById(R.id.selanjutnya);
        answersRadioGroup = findViewById(R.id.answersRadioGroup);
        answer1RadioButton = findViewById(R.id.answer1RadioButton);
        answer2RadioButton = findViewById(R.id.answer2RadioButton);
        answer3RadioButton = findViewById(R.id.answer3RadioButton);
        answer4RadioButton = findViewById(R.id.answer4RadioButton);
        audiobenar = MediaPlayer.create(this, R.raw.benar);
        audiosalah = MediaPlayer.create(this, R.raw.salah);
        gameover = MediaPlayer.create(this, R.raw.gameover);
        audiobenar.setVolume(1, 1);
        audiosalah.setVolume(1, 1);
        gameover.setVolume(1, 1);

        nilai = getIntent().getIntExtra("nilai", 0);
        difficulty = dbHelper.getDifficulty();

        if (difficulty.equals("Easy")) {
            Question.setText("6 x 5");
            answer1RadioButton.setText(Pilihanjawaban[0]);
            answer2RadioButton.setText(Pilihanjawaban[1]);
            answer3RadioButton.setText(Pilihanjawaban[2]);
            answer4RadioButton.setText(Pilihanjawaban[3]);
        } else if (difficulty.equals("Medium")) {
            Question.setText("234 + 4 x 3");
            answer1RadioButton.setText(Pilihanjawaban[4]);
            answer2RadioButton.setText(Pilihanjawaban[5]);
            answer3RadioButton.setText(Pilihanjawaban[6]);
            answer4RadioButton.setText(Pilihanjawaban[7]);
        } else if (difficulty.equals("Hard")) {
            Question.setText("85 x 9/3");
            answer1RadioButton.setText(Pilihanjawaban[8]);
            answer2RadioButton.setText(Pilihanjawaban[9]);
            answer3RadioButton.setText(Pilihanjawaban[10]);
            answer4RadioButton.setText(Pilihanjawaban[11]);
        }

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                waktu.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                gameover.start();
                waktu.setText("Waktu Habis!");
                resultTextView.setText("Ulangi level!");
            }
        }.start();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer1RadioButton.isChecked() || answer2RadioButton.isChecked() || answer3RadioButton.isChecked() || answer4RadioButton.isChecked()) {

                    RadioButton Pilihan_User = findViewById(answersRadioGroup.getCheckedRadioButtonId());
                    String Jawaban_User = Pilihan_User.getText().toString();

                    if (difficulty.equals("Easy")) {
                        if (Jawaban_User.equalsIgnoreCase("30")) {
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                            }
                            if (nilai < 5) {
                                nilai++;  // Tambah nilai setiap jawaban benar
                            }
                            Log.d("Quiz", "Nilai setelah jawaban benar: " + nilai);
                            resultTextView.setText("Mantap");
                            waktu.setText("Selesai");
                            audiobenar.start();
                            dbHelper.updateNilai(1, nilai, difficulty); // Simpan nilai ke database saat jawaban benar
                        } else {
                            audiosalah.start();
                            resultTextView.setText("Salah bro");
                        }
                    }
                    if (difficulty.equals("Medium")) {
                        if (Jawaban_User.equalsIgnoreCase("246")) {
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                            }
                            if (nilai < 5) {
                                nilai++;  // Tambah nilai setiap jawaban benar
                            }
                            Log.d("Quiz", "Nilai setelah jawaban benar: " + nilai);
                            resultTextView.setText("Mantap");
                            waktu.setText("Selesai");
                            audiobenar.start();
                            dbHelper.updateNilai(1, nilai, difficulty); // Simpan nilai ke database saat jawaban benar
                        } else {
                            audiosalah.start();
                            resultTextView.setText("Salah bro");
                        }
                    }
                    if (difficulty.equals("Hard")) {
                        if (Jawaban_User.equalsIgnoreCase("255")) {
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                            }
                            if (nilai < 5) {
                                nilai++;  // Tambah nilai setiap jawaban benar
                            }
                            Log.d("Quiz", "Nilai setelah jawaban benar: " + nilai);
                            resultTextView.setText("Mantap");
                            waktu.setText("Selesai");
                            audiobenar.start();
                            dbHelper.updateNilai(1, nilai, difficulty); // Simpan nilai ke database saat jawaban benar
                        } else {
                            audiosalah.start();
                            resultTextView.setText("Salah bro");
                        }
                    }
                }
            }
        });

        selanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton Pilihan_User = findViewById(answersRadioGroup.getCheckedRadioButtonId());
                String Jawaban_User = Pilihan_User.getText().toString();
                if(difficulty.equals("Easy")) {
                    if (Jawaban_User.equalsIgnoreCase("30") && nilai >= 5) {
                        Intent intent = new Intent(Quiz5.this, Quiz6.class);
                        intent.putExtra("nilai", nilai);
                        intent.putExtra("difficulty", difficulty);
                        startActivity(intent);
                    }
                    else {
                        resultTextView.setText(("Pilih & Submit jawaban yang benar dulu bro!"));
                        audiosalah.start();
                    }
                }
                if(difficulty.equals("Medium")) {
                    if (Jawaban_User.equalsIgnoreCase("246") && nilai >= 5) {
                        Intent intent = new Intent(Quiz5.this, Quiz6.class);
                        intent.putExtra("nilai", nilai);
                        intent.putExtra("difficulty", difficulty);
                        startActivity(intent);
                    }
                    else {
                        resultTextView.setText(("Pilih & Submit jawaban yang benar dulu bro!"));
                        audiosalah.start();
                    }
                }
                if(difficulty.equals("Hard")) {
                    if (Jawaban_User.equalsIgnoreCase("255") && nilai >= 5) {
                        Intent intent = new Intent(Quiz5.this, Quiz6.class);
                        intent.putExtra("nilai", nilai);
                        intent.putExtra("difficulty", difficulty);
                        startActivity(intent);
                    }
                    else {
                        resultTextView.setText(("Pilih & Submit jawaban yang benar dulu bro!"));
                        audiosalah.start();
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

    @Override
    public void onBackPressed() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        Intent intent = new Intent(Quiz5.this, Game.class);
        intent.putExtra("nilai", nilai); // Kirim nilai terbaru ke Game.java
        startActivity(intent);
        finish(); // Mengakhiri aktivitas ini untuk menghapusnya dari back stack

        super.onBackPressed();
    }
}
