package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MMIActivity extends Present {
    private Button play;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mmi);
        ImageView backButton = this.findViewById(R.id.backButtonT);
        backButton.setOnClickListener(v -> change());
        play=this.findViewById(R.id.startT);
        play.setOnClickListener(v -> {
            Intent mmiGame=new Intent(getApplicationContext(),MMIGameFirstPart.class);
            startActivity(mmiGame);
        });
    }

}
