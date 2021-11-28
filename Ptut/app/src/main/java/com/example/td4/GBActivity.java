package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class GBActivity extends present {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gb);
        final Button button = findViewById(R.id.button1);
        button.setOnClickListener(v -> change());
    }

}
