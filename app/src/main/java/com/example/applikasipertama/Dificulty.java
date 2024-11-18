package com.example.applikasipertama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Dificulty extends AppCompatActivity {

    private ImageView easyButton;
    private ImageView mediumButton;
    private ImageView hardButton;
    DatabaseHelper dbHelper;
    String minigamedifficulty;
    String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dificulty);

        easyButton = findViewById(R.id.easy);
        mediumButton = findViewById(R.id.medium);
        hardButton = findViewById(R.id.hard);
        dbHelper = new DatabaseHelper(this);
        difficulty = getIntent().getStringExtra("difficulty");
        minigamedifficulty = getIntent().getStringExtra("minigamedifficulty");

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                difficulty="Easy";
                dbHelper.updateNilai(1, dbHelper.getNilai(), difficulty);

                // Periksa nilai minigamedifficulty dan arahkan pengguna ke aktivitas yang sesuai
                if (minigamedifficulty.equalsIgnoreCase("minigame")) {
                    Intent intent = new Intent(Dificulty.this, MiniGame.class);
                    intent.putExtra("difficulty", difficulty);
                    startActivity(intent);
                } else if (minigamedifficulty.equalsIgnoreCase("main")) {
                    Intent intent = new Intent(Dificulty.this, Game.class);
                    startActivity(intent);
                }
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                difficulty="Medium";
                dbHelper.updateNilai(1, dbHelper.getNilai(), difficulty);

                // Periksa nilai minigamedifficulty dan arahkan pengguna ke aktivitas yang sesuai
                if (minigamedifficulty.equalsIgnoreCase("minigame")) {
                    Intent intent = new Intent(Dificulty.this, MiniGame.class);
                    intent.putExtra("difficulty", difficulty);
                    startActivity(intent);
                } else if (minigamedifficulty.equalsIgnoreCase("main")) {
                    Intent intent = new Intent(Dificulty.this, Game.class);
                    startActivity(intent);
                }
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Simpan Difficulty di database
                difficulty="Hard";
                dbHelper.updateNilai(1, dbHelper.getNilai(), difficulty);

                // Periksa nilai minigamedifficulty dan arahkan pengguna ke aktivitas yang sesuai
                if (minigamedifficulty.equalsIgnoreCase("minigame")) {
                    Intent intent = new Intent(Dificulty.this, MiniGame.class);
                    intent.putExtra("difficulty", difficulty);
                    startActivity(intent);
                } else if (minigamedifficulty.equalsIgnoreCase("main")) {
                    Intent intent = new Intent(Dificulty.this, Game.class);
                    startActivity(intent);
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
