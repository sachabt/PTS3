package com.example.td4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MMIActivity extends Present {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mmi);
        ImageView backButton = this.findViewById(R.id.backButtonT);
        backButton.setOnClickListener(v -> change());
    }

}
