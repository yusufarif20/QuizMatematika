package com.example.applikasipertama;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int GAME_REQUEST_CODE = 1;

    MediaPlayer audio;
    Button buttonquit;
    Button newgame;
    Button tutorial;
    Button minigame;
    private TextView poin;
    boolean isPlaying = false;
    private boolean isBackPressedConfirmed = false;
    int nilai;
    String difficulty;  // Tambahkan variabel untuk menyimpan difficulty
    String minigamedifficulty;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        audio = MediaPlayer.create(this, R.raw.bgm);
        audio.setLooping(true);
        audio.setVolume(1, 1);
        poin = findViewById(R.id.poin);
        newgame = findViewById(R.id.newgame);
        tutorial = findViewById(R.id.tutorial);
        minigame = findViewById(R.id.minigame);

        nilai = dbHelper.getNilai(); // Ambil nilai dari database
        difficulty = dbHelper.getDifficulty(); // Ambil difficulty dari database
        minigamedifficulty = "minigamedifficulty";
        Log.d("Game", "Difficulty saat ini: " + difficulty);

        poin.setText(String.valueOf(nilai));

        if (!isPlaying) {
            audio.start();
            isPlaying = true;
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nilai == 0) {
                    Intent intent = new Intent(MainActivity.this, Dificulty.class);
                    intent.putExtra("nilai", nilai);
                    intent.putExtra("difficulty", difficulty); // Kirim difficulty ke Game activity
                    intent.putExtra("minigamedifficulty", "main");
                    startActivity(intent);
                }
                else if (nilai > 0){
                    Intent intent = new Intent(MainActivity.this, Game.class);
                    intent.putExtra("nilai", nilai);
                    intent.putExtra("difficulty", difficulty); // Kirim difficulty ke Game activity
                    startActivityForResult(intent, GAME_REQUEST_CODE);
                }
            }
        });

        minigame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nilai >= 9){
                    Intent intent = new Intent(MainActivity.this, MiniGame.class);
                    intent.putExtra("minigamedifficulty", "minigame");
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, "Kumpulkan 9 poin untuk membuka Mini Game", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Tutorial.class);
                startActivity(intent);
            }
        });

        newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Reset Game");
                builder.setMessage("Apakah Anda yakin ingin Mengulang game? progres anda akan hilang!!");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        nilai = 0; // Set nilai ke 0
                        difficulty = "null"; // Reset difficulty ke default atau sesuai keinginan
                        dbHelper.updateNilai(1, nilai, difficulty); // Simpan nilai dan difficulty ke database
                        poin.setText(String.valueOf(nilai)); // Perbarui tampilan poin
                        newgame.setVisibility(View.GONE);
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        buttonquit = findViewById(R.id.buttonquit);
        buttonquit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audio != null) {
                    audio.stop();
                    audio.release();
                }
                dbHelper.updateNilai(1, nilai, difficulty); // Simpan nilai dan difficulty ke database sebelum keluar
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GAME_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                nilai = data.getIntExtra("nilai", 0);
                difficulty = data.getStringExtra("difficulty");
                poin.setText(String.valueOf(nilai));
                dbHelper.updateNilai(1, nilai, difficulty); // Simpan nilai dan difficulty ke database setelah kembali dari Game.java
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (audio != null && !audio.isPlaying()) {
            audio.start();
        }
        nilai = dbHelper.getNilai();
        difficulty = dbHelper.getDifficulty(); // Ambil difficulty dari database
        poin.setText(String.valueOf(nilai));
        if (nilai == 0) {
            newgame.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (audio != null && audio.isPlaying()) {
            audio.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (audio != null) {
            audio.release();
            audio = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (isBackPressedConfirmed) {
            super.onBackPressed();
        } else {
            showExitConfirmationDialog();
        }
    }

    private void showExitConfirmationDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Keluar Game");
        builder.setMessage("Apakah Anda yakin ingin keluar?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                isBackPressedConfirmed = true;
                if (audio != null) {
                    audio.stop();
                    audio.release();
                }
                dbHelper.updateNilai(1, nilai, difficulty); // Simpan nilai dan difficulty ke database sebelum keluar
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
