package com.example.td4;

import android.content.Intent;

public abstract class Present extends MapsActivity{


    //Permet de changer d'ecran pour passer a l'ecran des informations de batiment
    public void change(){
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent map= new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(map);
    }
}
