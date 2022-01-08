package com.example.td4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FaitsPage extends Fragment {

    private TextView test;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView  =inflater.inflate(R.layout.fragment_main2,container,false);

        //Permet la récupération des données sauvegarder sur le SharedPreference "affaire"
        SharedPreferences pref = getActivity().getSharedPreferences("affaire", Context.MODE_PRIVATE);

        //Permet l'affichage des faits du crime
        String faitsS=pref.getString("faits","No name defined");
        test= rootView.findViewById(R.id.test);
        test.setText("Faits: "+faitsS);


        return rootView;
    }




}
