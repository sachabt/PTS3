package com.example.td4;

import android.os.Bundle;
import android.widget.ImageButton;

public class infoActivity extends present {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        final ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> change());
    }





}
