package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MMIActivity extends present {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mmi);
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> change());
    }

}