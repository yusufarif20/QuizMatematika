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

public class MiniGame extends AppCompatActivity {

    private ImageView base;
    private ImageView sprint;
    private ImageView connect;
    private ImageView puzzle;
    private ImageView cekscore;
    String minigamedifficulty = "minigamedifficulty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mini_game);

        base = findViewById(R.id.base);
        sprint = findViewById(R.id.sprint);
        connect = findViewById(R.id.connect);
        cekscore = findViewById(R.id.cekscore);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MiniGame.this, Dificulty.class);
                intent.putExtra("minigamedifficulty", minigamedifficulty);
                startActivity(intent);
            }
        });
    }
}