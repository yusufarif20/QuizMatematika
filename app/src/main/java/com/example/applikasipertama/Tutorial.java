package com.example.applikasipertama;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Tutorial extends AppCompatActivity {

    private TextView waktu;
    private TextView resultTextView;
    private TextView questionTextView;
    private ImageView image;
    private Button submitButton;
    private Button selanjutnya;
    private Button mengertiButton;
    private RadioGroup answersRadioGroup;
    private RadioButton answer1RadioButton;
    private RadioButton answer2RadioButton;
    private RadioButton answer3RadioButton;
    private RadioButton answer4RadioButton;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 60000;
    int nilai = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tutorial);

        // Inisialisasi view
        waktu = findViewById(R.id.waktu);
        image = findViewById(R.id.image);
        resultTextView = findViewById(R.id.resultTextView);
        questionTextView = findViewById(R.id.questionTextView);
        submitButton = findViewById(R.id.submitButton);
        selanjutnya = findViewById(R.id.selanjutnya);
        answersRadioGroup = findViewById(R.id.answersRadioGroup);
        answer1RadioButton = findViewById(R.id.answer1RadioButton);
        answer2RadioButton = findViewById(R.id.answer2RadioButton);
        answer3RadioButton = findViewById(R.id.answer3RadioButton);
        answer4RadioButton = findViewById(R.id.answer4RadioButton);
        mengertiButton = findViewById(R.id.okepaham);

        // Mulai dengan menyembunyikan semua elemen kecuali questionTextView dan mengertiButton
        waktu.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
        resultTextView.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);
        selanjutnya.setVisibility(View.GONE);
        answersRadioGroup.setVisibility(View.GONE);
        answer1RadioButton.setVisibility(View.GONE);
        answer2RadioButton.setVisibility(View.GONE);
        answer3RadioButton.setVisibility(View.GONE);
        answer4RadioButton.setVisibility(View.GONE);

        // Tombol Mengerti di-klik untuk menaikkan nilai dan memperbarui tampilan
        mengertiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nilai++;
                updateUI();

                // Ketika nilai >= 5, kembali ke MainActivity
                if (nilai >= 6) {
                    Intent intent = new Intent(Tutorial.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Menutup Tutorial Activity
                }
            }
        });

        // Menyetel padding insets agar sesuai dengan layar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Method untuk memperbarui UI berdasarkan nilai
    private void updateUI() {
        if (nilai >= 1) {
            waktu.setVisibility(View.VISIBLE);
            questionTextView.setText("Ini adalah waktu diberikan untuk menjawab soal, selesaikan soal dengan benar atau poin anda akan berkurang.");
        }
        if (nilai >= 2) {
            image.setVisibility(View.VISIBLE);
            questionTextView.setText("Ini adalah soal quiznya, perhatikan baik-baik.");
        }
        if (nilai >= 3) {
            answersRadioGroup.setVisibility(View.VISIBLE);
            answer1RadioButton.setVisibility(View.VISIBLE);
            answer2RadioButton.setVisibility(View.VISIBLE);
            answer3RadioButton.setVisibility(View.VISIBLE);
            answer4RadioButton.setVisibility(View.VISIBLE);
            questionTextView.setText("Pilihlah salah satu jawaban yang anda anggap benar.");
        }
        if (nilai >= 4) {
            submitButton.setVisibility(View.VISIBLE);
            resultTextView.setVisibility(View.VISIBLE);
            submitButton.setText("Klik tombol ini jika sudah yakin.");
            resultTextView.setText("Jika jawaban anda salah anda harus memilih kembali jawaban yang benar, tidak ada pengurangan poin.");
        }
        if (nilai >= 5) {
            selanjutnya.setVisibility(View.VISIBLE);
            submitButton.setVisibility(View.GONE);
            selanjutnya.setText("Klik ini untuk ke level selanjutnya.");
            resultTextView.setText("klik tombol default back di kanan bawah untuk kembali ke main menu");
            mengertiButton.setText("Kembali ke Main Menu");
        }
    }
}
