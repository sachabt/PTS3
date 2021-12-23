package com.example.PTS3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AdministrationActivity extends present{

  //  private ImageButton backButton;
    private Button play;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administration);
       // backButton = findViewById(R.id.backButton);
        //backButton.setOnClickListener(v -> change());
        play=findViewById(R.id.play);
        play.setOnClickListener(v -> {
            Intent adminGame=new Intent(getApplicationContext(),AdminGame.class);
            startActivity(adminGame);
        });
    }
}
