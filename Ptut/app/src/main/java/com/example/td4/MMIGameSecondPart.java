package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MMIGameSecondPart extends Game {

    private String text = "<body>\n    <p>Hello world.</p>\n</body>";

    private ImageView keyboard;
    private TextView codeText;
    private int clickCounter=0;

    private int lengthOfGame = 8000;
    private final Handler timer = new Handler();
    private Runnable timerRunnable;

    private TextView timerText;

    private int delayBetweenTimerUpdate = lengthOfGame/100;
    private int time = lengthOfGame-delayBetweenTimerUpdate;


    private int score; //get it from extra
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mmi_game_second_part);

        keyboard = findViewById(R.id.keyboard);
        codeText = findViewById(R.id.codeText);

        timerText  = findViewById(R.id.timerSecondGame);
        keyboard.setOnClickListener(v -> {
            if(clickCounter<text.length())
                clickCounter++;
                codeText.setText(text.substring(0, clickCounter));
        });

        timerRunnable = () -> {

            //FIXME problem with the score
            time -= delayBetweenTimerUpdate;
            timerText.setText("Il reste " + time+" millisecondes");
            if(time<=0){
                Intent intent = getIntent();
                score = intent.getIntExtra("score",0);
                score += Math.round( (clickCounter/text.length()) *3);
                Log.v("score", String.valueOf(score));
                Intent i = new Intent(this, MMIGameThirdPart.class);
                i.putExtra("score", score);
                startActivity(i);
            }
            else
                timer.postDelayed(timerRunnable, delayBetweenTimerUpdate);
        };

        timer.postDelayed(timerRunnable, delayBetweenTimerUpdate);

    }

    @Override
    public void onBackPressed() {
    }
}
