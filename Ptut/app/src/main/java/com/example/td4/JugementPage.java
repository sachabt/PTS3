package com.example.td4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.td4.databinding.ActivityMainBinding;

public class JugementPage extends Fragment {

    private ImageView coupable,nonCoupable;
    private View overPage;
    private TextView TextFin;
    private String choix;
    private Button confirmer;
    private Button continu;

    private ActivityMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView  =inflater.inflate(R.layout.fragment_main3,container,false);

        //Permet la récupération des données sauvegarder sur le SharedPreference "affaire"
        SharedPreferences pref = getActivity().getSharedPreferences("affaire", Context.MODE_PRIVATE);

        binding = ActivityMainBinding.inflate(getLayoutInflater());


        //Récupère la données sauvegarder correspondant au résultat "Coupable/Non Coupable"
        Boolean resultatS=pref.getBoolean("resultat",true);

        coupable=rootView.findViewById(R.id.coupable);

        //Lorsque le Bouton coupable est sélectionné ajoute un filtre rouge par dessus et change le choix pour "Coupable"
        coupable.setOnClickListener(v->{
            setChoix("Coupable");
            final int semiTransparentRed= Color.argb(155, 170, 51, 51);
            coupable.setColorFilter(semiTransparentRed, PorterDuff.Mode.SRC_ATOP);
            nonCoupable.setColorFilter(null);
        });


        nonCoupable=rootView.findViewById(R.id.nonCoupable);
        //Lorsque le Bouton coupable est sélectionné ajoute un filtre vert par dessus et change le choix pour "Non coupable"
        nonCoupable.setOnClickListener(v->{
            setChoix("Non coupable");
            final int semiTransparentGreen = Color.argb(155, 51, 170, 51);
            nonCoupable.setColorFilter(semiTransparentGreen, PorterDuff.Mode.SRC_ATOP);
            coupable.setColorFilter(null);
        });

        confirmer=rootView.findViewById(R.id.confirmer);

        // Lorsque le Bouton confirmer est actionné vérifie la réponse et le résultat attendu
        // si bon-> victoire
        // sinon-> défaite
        confirmer.setOnClickListener(v->{
            TextFin=rootView.findViewById(R.id.TextFin);
            overPage=rootView.findViewById(R.id.overPage);

            if (choix=="Coupable") {
                overPage.setVisibility(View.VISIBLE);
                if(resultatS==true){
                    TextFin.setText("Bravo tu as réussi l'accusé est bien le coupable");
                }
                else{
                    TextFin.setText("BOOOOOUUUHHH tu n'as pas réussi l'accusé n'etait pas le coupable");
                }
                confirmer.setClickable(false);
                coupable.setClickable(false);
                nonCoupable.setClickable(false);


            }
            else if(choix=="Non coupable"){
                overPage.setVisibility(View.VISIBLE);
                if (resultatS == true) {
                    TextFin.setText("BOOOOOUUUHHH tu n'as pas réussi l'accusé était le coupable");
                } else {
                    TextFin.setText("Bravo tu as réussi l'accusé n'était pas le coupable");
                }
                confirmer.setClickable(false);
                coupable.setClickable(false);
                nonCoupable.setClickable(false);

            }
            else{
                Toast toast = Toast.makeText(getContext(), "Veuillez choisir une réponse", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        continu=rootView.findViewById(R.id.continu);
        continu.setOnClickListener(v->{
            Intent back= new Intent(getActivity(),DroitGame.class);
            startActivity(back);});

        return rootView;
    }

    private void setChoix(String choix){
        this.choix=choix;
    }
}
