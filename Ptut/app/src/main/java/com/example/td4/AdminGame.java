package com.example.td4;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AdminGame extends Game {


    private Button valider;
    private Button startButton;

    private CheckBox condition;
    private boolean win=true;
    private long mTimeLeftInMillis = 15500 ;
    private CountDownTimer mCountDownTimer;

    private ScrollView gamePage;
    private View startPage;
    private View timePage;
    private View winPage;

    private InputStream input;
    private TextView timer;
    private TextView firstPartText;
    private TextView secondPartText;
    private TextView thirdPartText;
    private TextView fourthPartText;
    private Button continu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admingame);

        //Initialisation de tous les éléments de la page
        valider = findViewById(R.id.valider);
        condition = findViewById(R.id.condition);

        startPage=findViewById(R.id.startPage);
        timePage=findViewById(R.id.timePage);
        winPage = findViewById(R.id.winPage);
        gamePage = findViewById(R.id.gamePage);

        timer = findViewById(R.id.Time);

        startButton=findViewById(R.id.StartButton);

        firstPartText = findViewById(R.id.firstPartText);
        secondPartText = findViewById(R.id.secondPartText);
        thirdPartText = findViewById(R.id.thirdPartText);
        fourthPartText = findViewById(R.id.fourthPartText);


        //Permet d'insérer les textes dans les textview correspondant
        input=getResources().openRawResource(R.raw.firstpart);
        text(firstPartText);
        input=getResources().openRawResource(R.raw.secondpart);
        text(secondPartText);
        input=getResources().openRawResource(R.raw.thirdpart);
        text(thirdPartText);
        input=getResources().openRawResource(R.raw.fourthpart);
        text(fourthPartText);

        //Affiche les différentes view lorque le bouton pour accéder au cgu est activé
        startButton.setOnClickListener(arg0 -> {
            startPage.setVisibility(View.INVISIBLE);
            gamePage.setVisibility(View.VISIBLE);
            timePage.setVisibility(View.VISIBLE);

            /**
             * Permet la mise en place d'un timer qui décompte à partir de mTimeLeftInMillis jusqu'à 0 puis se réinitialise
             * tout en ramenant l'utilisateur en haut de la page.
             * **/
            mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeftInMillis = millisUntilFinished;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                    gamePage.fullScroll(ScrollView.FOCUS_UP);

                    mTimeLeftInMillis = 30000;

                    mCountDownTimer.start();
                }
            }.start();
        });




        //Fonction appelé lorsque l'utilisateur appuie sur le bouton valider les cgu
        valider.setOnClickListener(arg0 -> {
           if(condition.isChecked()){   // Si les conditions on été coché affiche le message de victoire et stop le timer
            setState(win);
            winPage.setVisibility(View.VISIBLE);
            mCountDownTimer.cancel();
           }
           else{                        // Sinon affiche un message
               Toast erreurMsg = Toast.makeText(
                                 getApplicationContext(), "Vous devez d'abord accepter les conditions d'utilisation", Toast.LENGTH_SHORT);
               erreurMsg.show();
           }
        });

        continu=findViewById(R.id.continu);
        continu.setOnClickListener(v -> finish());  //Lorsque le bouton continuer est activé met fin a l'activité
    }


    //Permet de mettre a jour le timer
    private void updateCountDownText() {
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        timer.setText(String.valueOf(seconds));
    }

    public void text(TextView partText){        //Transforme les textes pour les adapter en textView

        try {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);

            String textPart1;
            while(reader.ready())
            {
                textPart1 = partText.getText()+ reader.readLine()+"\n";
                partText.setText(textPart1);
            }
        } catch (Exception e){
            return;
        }
    }



}