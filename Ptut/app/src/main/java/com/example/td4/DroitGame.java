package com.example.td4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.example.td4.databinding.ActivityMainBinding;
import com.example.td4.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class DroitGame extends Game {

    private ActivityMainBinding binding;
    private int nbAffaires=2;
    private InputStream affaire;
    private String Defense;
    private String Faits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        //Choisit une affaire aléatoirement pour le cadre du jeu
        Random random = new Random();
        int numAffaire;
        numAffaire = random.nextInt(nbAffaires)+1;
        switch (numAffaire){
            case 1:
                affaire=getResources().openRawResource(R.raw.defense1);
                Defense=text();
                affaire=getResources().openRawResource(R.raw.faits1);
                Faits=text();
                createAffaire("Croft","Lara",25,Defense,true,Faits,false);
                break;
            case 2:
                affaire=getResources().openRawResource(R.raw.defense2);
                Defense=text();
                affaire=getResources().openRawResource(R.raw.faits1);
                Faits=text();
                createAffaire("Oto","Fabrice",46,"Je ne suis pas ton pere",true,Faits,false);
                break;
            default:
                break;
        }

        //Permets la séparation des différentes view symbolisants chaque pages
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

    }
    public String text(){        //Transforme les textes pour les adapter en textView

        try {
            InputStreamReader isr = new InputStreamReader(affaire);
            BufferedReader reader = new BufferedReader(isr);

            String text = "";
            while(reader.ready())
            {
                text +=reader.readLine()+"\n";
            }
            return text;
        } catch (Exception e){
            return "";
        }
    }
    //Permet de créer une affaire ayant pour infos les paramètres associés

    /*N.B: Ajouter dans PersonnagePage une image correspondant
        au personnage dans le cas de la création d'une nouvelle affaire
    */
    public void createAffaire( String nom, String prenom, int age, String defense, Boolean casier, String faits, boolean resultat){

        SharedPreferences affaire = getSharedPreferences("affaire", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = affaire.edit();
        editor.putString("nom",nom);
        editor.putString("prenom",prenom);
        editor.putInt("age",age);
        editor.putString("defense",defense);
        editor.putBoolean("casier",casier);
        editor.putString("faits",faits);
        editor.putBoolean("resultat",resultat);
        editor.apply();
    }
}