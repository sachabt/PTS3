package com.example.td4;

import com.example.td4.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class balise {

    private MapsActivity context;
    private LatLng coordonnees;
    private String titre;
    private boolean valide=false;
    private Marker marker;

    public  balise(MapsActivity pContext,LatLng pCoordonnees,String pTitre){
    this.context=pContext;
    this.coordonnees=pCoordonnees;
    this.titre=pTitre;
    }

    public void creerMarker(GoogleMap pMap){
        marker= pMap.addMarker(new MarkerOptions()
                .position(this.coordonnees)
                .title(this.titre));

        }


/*
    public void validerBalise(){
        this.valide=true;
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    } */
}
