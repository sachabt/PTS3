package com.example.PTS3;

import android.os.Bundle;
import android.widget.Button;

public class TCActivity extends present {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tc);
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> change());
    }





}
