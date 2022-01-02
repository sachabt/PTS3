package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class RuActivity extends Present {

    private ImageView backButtonA;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ru);

        backButtonA = findViewById(R.id.backButtonT);
        backButtonA.setOnClickListener(v -> change());

    }
}
