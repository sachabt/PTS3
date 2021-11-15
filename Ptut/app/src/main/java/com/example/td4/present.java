package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public abstract class present extends MapsActivity{

    //Permet de changer d'ecran pour passer a l'ecran des informations de batiment
    public void change(){
        Intent intent= new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);
        finish();
    }
}
