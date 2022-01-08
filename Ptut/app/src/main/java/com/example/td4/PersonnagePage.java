package com.example.td4;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class PersonnagePage extends Fragment {

    private TextView nom,prenom,defense,age,casier;
    private ImageView visageAffiche;
    private String Casier;
    private Bitmap visage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView  =inflater.inflate(R.layout.fragment_main,container,false);

        //Permet la récupération des données sauvegarder sur le SharedPreference "affaire"
        SharedPreferences pref = getActivity().getSharedPreferences("affaire", Context.MODE_PRIVATE);

        //Choix du visage affiché par rapport au nom choisi lors de la création d'affaire
        String prenomS=pref.getString("prenom","No name defined");
        switch (prenomS) {
            case "Lara":
                visage = ((BitmapDrawable) getResources().getDrawable(R.drawable.lara)).getBitmap();
                break;
            case "Fabrice":
                visage = ((BitmapDrawable) getResources().getDrawable(R.drawable.fabrice)).getBitmap();
                break;
            default:
                 visage = ((BitmapDrawable) getResources().getDrawable(R.drawable.error)).getBitmap();
        }


        //Affiche sur la page personnage le visage correspondant
        visageAffiche=rootView.findViewById(R.id.visage);
        visageAffiche.setImageBitmap(visage);


        //Affiche les informations liés au suspect sur la page personnage
        String nomS=pref.getString("nom","No name defined");
        nom= rootView.findViewById(R.id.nom);
        nom.setText("Nom: "+nomS);

        prenom= rootView.findViewById(R.id.prenom);
        prenom.setText("Prenom: "+prenomS);

        int ageS=pref.getInt("age",0);
        age= rootView.findViewById(R.id.age);
        age.setText("Age: "+ageS);

        boolean casierS=pref.getBoolean("casier",true);
        casier= rootView.findViewById(R.id.casier);
        if (casierS==true){
            Casier="Existant";
        }
        else{
            Casier="Inexistant";
        }
        casier.setText("Casier: "+Casier);

        String defenseS=pref.getString("defense","No name defined");
        defense= rootView.findViewById(R.id.defense);
        defense.setText("Defense:\n"+defenseS);

        return rootView;
    }
}
