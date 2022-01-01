package com.example.td4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

public class MMIGameFirstPart extends Game {

    private Button firstButton;
    private Button secondButton;
    private Button thirdButton;

    private int lengthOfGame = 5000;
    private final Handler timer = new Handler();
    private Runnable timerRunnable;

    private TextView timerText;

    private int order = 0;
    private int delayBetweenTimerUpdate = lengthOfGame/100;
    private int time = lengthOfGame-delayBetweenTimerUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mmi_game_first_part);

        firstButton = findViewById(R.id.firstButton);
        secondButton = findViewById(R.id.secondButton);
        thirdButton = findViewById(R.id.thirdButton);

        timerText = findViewById(R.id.timerFirstGame);

        timerRunnable = () -> {
            time -= delayBetweenTimerUpdate;
            timerText.setText("Il reste " + time+" millisecondes");
            if(time<=0){
                Intent i = new Intent(this, MMIGameSecondPart.class);
                i.putExtra("score", order);
                startActivity(i);
            }
            else
                timer.postDelayed(timerRunnable, delayBetweenTimerUpdate);
        };

        timer.postDelayed(timerRunnable, delayBetweenTimerUpdate);





        firstButton.setOnClickListener(v -> {
            if(order==0){
                order++;
                firstButton.setBackgroundColor(Color.GREEN);
            }
        });

        secondButton.setOnClickListener(v -> {
            if(order==1){
                order++;
                secondButton.setBackgroundColor(Color.GREEN);
            }
        });

        thirdButton.setOnClickListener(v -> {
            if(order==2){
                order++;
                thirdButton.setBackgroundColor(Color.GREEN);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}