package com.example.td4;

import android.util.Log;

import com.example.td4.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Balise {

    private MapsActivity context;
    private LatLng coordonnees;
    private String titre;
    private Marker marker;
    private boolean valide;

    private String id;
    public Balise(MapsActivity pContext, LatLng pCoordonnees, String pTitre){
        this.context=pContext;
        this.coordonnees=pCoordonnees;
        this.titre=pTitre;
    }

    public void creerMarker(GoogleMap pMap,boolean valide){
        marker= pMap.addMarker(new MarkerOptions()
                .position(this.coordonnees)
                .title(this.titre));
        if(valide){
            validerBalise();
        }
        id = marker.getId();
    }


    public void validerBalise(){
        Log.v("balise", "validation de la balise");
        this.valide=true;
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    }

    public boolean getValide(){
        return valide;
    }

    public String getId(){return id;}
}
