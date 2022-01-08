package com.example.td4;

import android.os.Bundle;
import android.widget.ImageView;

public class CrousActivity extends Present {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crous);
        final ImageView button = findViewById(R.id.backButtonT);
        button.setOnClickListener(v -> change());
    }
}
