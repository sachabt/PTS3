package com.example.td4;

import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

public abstract class Game extends FragmentActivity {

    public boolean state;
    public View verifPage;
    public Button annuler;
    public Button accepter;



    public void setState(boolean state){
        this.state=state;
    }

    @Override
    public void onBackPressed(){
        verifPage=findViewById(R.id.verifPage);
        annuler=findViewById(R.id.annuler);
        annuler.setOnClickListener(v -> verifPage.setVisibility(View.INVISIBLE));
        accepter=findViewById(R.id.accepter);
        accepter.setOnClickListener(v -> finish());
        verifPage.setVisibility(View.VISIBLE);
    }


}
