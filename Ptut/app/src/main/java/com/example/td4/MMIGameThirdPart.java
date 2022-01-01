package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MMIGameThirdPart extends Game {

    private ImageView topImage;
    private ImageView middleImage;
    private ImageView bottomImage;

    private int topAlpha=25;
    private int middleAlpha=25;
    private int bottomAlpha=25;

    private int alphaIncrease = 20;

    private int lengthOfGame = 5000;
    private final Handler timer = new Handler();
    private Runnable timerRunnable;

    private TextView timerText;
    private TextView scoreDisplay;

    private int delayBetweenTimerUpdate = lengthOfGame/100;
    private int time = lengthOfGame-delayBetweenTimerUpdate;

    private ConstraintLayout endWindow;

    private Button continuer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mmi_game_third_part);

        topImage = findViewById(R.id.topImage);
        middleImage = findViewById(R.id.middleImage);
        bottomImage = findViewById(R.id.bottomImage);
        topImage.setImageAlpha(topAlpha);
        middleImage.setImageAlpha(middleAlpha);
        bottomImage.setImageAlpha(bottomAlpha);

        timerText = findViewById(R.id.timerThirdGame);

        scoreDisplay = findViewById(R.id.scoreDisplay);

        endWindow = findViewById(R.id.endWindow);
        endWindow.setVisibility(View.GONE);

        continuer = findViewById(R.id.continuer);

        topImage.setOnClickListener(v -> {
            topAlpha += alphaIncrease;
            if(topAlpha<255)
                topImage.setImageAlpha(topAlpha);
        });

        middleImage.setOnClickListener(v -> {
            middleAlpha += alphaIncrease;
            if(middleAlpha<255)
                middleImage.setImageAlpha(middleAlpha);
        });

        bottomImage.setOnClickListener(v -> {
            if(bottomAlpha<255)
                bottomImage.setImageAlpha(bottomAlpha);
                bottomAlpha += alphaIncrease;
        });

        timerRunnable = () -> {

            //FIXME problem with the score
            time -= delayBetweenTimerUpdate;
            timerText.setText("Il reste " + time/1000+" secondes");
            if(time<=0){
                Intent intent = getIntent();
                int score = intent.getIntExtra("score", 0);
                score += Math.round( (topAlpha+middleAlpha+bottomAlpha)/255);
                Log.v("score", String.valueOf(score));
                endWindow.setVisibility(View.VISIBLE);
                scoreDisplay.setText("Vous avez obtenu un score de "+score);
                //TODO ajouter une reaction en fonction du score
            }
            else
                timer.postDelayed(timerRunnable, delayBetweenTimerUpdate);
        };

        timer.postDelayed(timerRunnable, delayBetweenTimerUpdate);

        continuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create intent to redirect toward main activity
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
