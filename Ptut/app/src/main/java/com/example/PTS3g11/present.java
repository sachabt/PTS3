package com.example.PTS3g11;

import android.content.Intent;

public abstract class present extends MapsActivity{

    //Permet de changer d'ecran pour passer a l'ecran des informations de batiment
    public void change(){
        Intent intent= new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);
        finish();
    }
}
