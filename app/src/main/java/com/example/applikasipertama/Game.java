package com.example.applikasipertama;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Game extends AppCompatActivity {

    private ImageView imageView;
    private ImageView locknodua;
    private ImageView locknotiga;
    private ImageView locknoempat;
    private ImageView locknolima;
    private ImageView locknoenam;
    private ImageView locknotuju;
    private ImageView locknodelapan;
    private ImageView locknosembilan;
    private ImageView revisi;

    private TextView poin;
    private TextView Difficult;
    int nilai;
    String difficulty;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        dbHelper = new DatabaseHelper(this);

        poin = findViewById(R.id.poin);
        Difficult = findViewById(R.id.difficult);

        // Ambil nilai dari database
        nilai = dbHelper.getNilai();
        difficulty = dbHelper.getDifficulty();
        poin.setText(String.valueOf(nilai));
        Difficult.setText(String.valueOf(difficulty));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.imageView);
        locknodua = findViewById(R.id.button2lock);
        locknotiga = findViewById(R.id.button3lock);
        locknoempat = findViewById(R.id.button4lock);
        locknolima = findViewById(R.id.button5lock);
        locknoenam = findViewById(R.id.button6lock);
        locknotuju = findViewById(R.id.button7lock);
        locknodelapan = findViewById(R.id.button8lock);
        locknosembilan = findViewById(R.id.button9lock);
        revisi = findViewById(R.id.button10lock);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game.this, MainActivity.class);
                intent.putExtra("nilai", nilai);
                setResult(RESULT_OK, intent);
                startActivity(intent);
                finish();
            }
        });

        setupButton(R.id.button1, Quiz.class, 0);
        setupButton(R.id.button2, Quiz2.class, 1);
        setupButton(R.id.button3, Quiz3.class, 2);
        setupButton(R.id.button4, Quiz4.class, 3);
        setupButton(R.id.button5, Quiz5.class, 4);
        setupButton(R.id.button6, Quiz6.class, 5);
        setupButton(R.id.button7, Quiz7.class, 6);
        setupButton(R.id.button8, Quiz8.class, 7);
        setupButton(R.id.button9, Quiz9.class, 8);
        setupButton(R.id.buttonboss, FinalQuiz.class, 9);
    }

    private void setupButton(int buttonId, Class<?> targetActivity, int minValue) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nilai >= minValue) {
                    Intent intent = new Intent(Game.this, targetActivity);
                    intent.putExtra("nilai", nilai);
                    intent.putExtra("difficulty", difficulty);
                    startActivityForResult(intent, minValue);
                } else {
                    Toast.makeText(Game.this, "Poin anda tidak cukup.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ambil nilai dari database setiap kali Activity dilanjutkan
        nilai = dbHelper.getNilai();
        poin.setText(String.valueOf(nilai));
        if (nilai >= 1) {
            locknodua.setVisibility(View.GONE);
        }

        // Sembunyikan locknotiga jika nilai >= 3
        if (nilai >= 2) {
            locknotiga.setVisibility(View.GONE);
        }

        // Sembunyikan locknoempat jika nilai >= 4
        if (nilai >= 3) {
            locknoempat.setVisibility(View.GONE);
        }

        // Sembunyikan locknolima jika nilai >= 5
        if (nilai >= 4) {
            locknolima.setVisibility(View.GONE);
        }

        // Sembunyikan locknoenam jika nilai >= 6
        if (nilai >= 5) {
            locknoenam.setVisibility(View.GONE);
        }

        // Sembunyikan locknotuju jika nilai >= 7
        if (nilai >= 6) {
            locknotuju.setVisibility(View.GONE);
        }

        // Sembunyikan locknodelapan jika nilai >= 8
        if (nilai >= 7) {
            locknodelapan.setVisibility(View.GONE);
        }

        // Sembunyikan locknosembilan jika nilai >= 9
        if (nilai >= 8) {
            locknosembilan.setVisibility(View.GONE);
        }

        if (nilai >= 9) {
            revisi.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Game.this, MainActivity.class);
        intent.putExtra("nilai", nilai);
        setResult(RESULT_OK, intent);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.updateNilai(1, nilai, difficulty); // Simpan nilai ke database sebelum keluar
    }
}
