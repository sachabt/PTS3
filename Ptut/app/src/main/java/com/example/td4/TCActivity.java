package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class TCActivity extends Present {

    private Button play;
    private ImageView backButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        backButton = findViewById(R.id.backButtonT);
        backButton.setOnClickListener(v -> change());
        play=findViewById(R.id.startT);
        play.setOnClickListener(v -> {
            Intent tcGame=new Intent(getApplicationContext(),AdminGame.class);
            startActivity(tcGame);
        });




}
}
