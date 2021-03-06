package com.example.td4;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.td4.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Balise[] tabBalise;
    private Balise baliseBatInfo, baliseBatMmi, baliseBatTc, baliseBatGb, baliseAdmin,baliseBU,baliseCROUS,baliseBatDroit,baliseRU;
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 50;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPrefs = getPreferences(MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


         //Initialise les diff??rents points d'int??r??t et les ajoutes a un tableau
        baliseBatDroit = new Balise(this, new LatLng(48.087086227818894, -0.7590331292590524), "En savoir plus: Droit");
        baliseBatInfo = new Balise(this, new LatLng(48.0860628, -0.7596008), "En savoir plus: Info");
        baliseBatMmi = new Balise(this, new LatLng(48.0863351, -0.7589999), "En savoir plus: MMI");
        baliseBatTc = new Balise(this, new LatLng(48.0861559, -0.7581953), "En savoir plus: TC");
        baliseBatGb = new Balise(this, new LatLng(48.0856041, -0.7580022), "En savoir plus: GB");
        baliseAdmin = new Balise(this, new LatLng(48.0859, -0.7580282), "En savoir plus: Administration");
        baliseBU= new Balise(this, new LatLng(48.08597435745002, -0.7587137729929605), "En savoir plus: Bibliotheque");
        baliseCROUS =new Balise(this, new LatLng(48.08541004957722, -0.7588990181534716), "En savoir plus: CROUS");
        baliseRU =new Balise(this, new LatLng(48.086656572448774, -0.7571051576155985), "En savoir plus: Restaurant Universitaire");


        tabBalise = new Balise[]{baliseBatInfo, baliseBatMmi, baliseBatTc, baliseBatGb, baliseAdmin,baliseBU,baliseCROUS,baliseBatDroit,baliseRU};


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Permet la cr??ation des markers sur la map a sa cr??ation.
     * Positionne ensuite la cam??ra sur les coordonn??es du t??l??phone
     * D??fini la possibilit?? de zoom minimal et maximal
     **/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /**Retire la possibilit?? ?? l'utilisateur de zoomer et naviguer manuellement sur la map  **/

        /*  mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        */
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        
        try {
            //Utilise la feuille de style style_json sur la map enlevant ainsi les marqueurs par d??fauts google
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Mise en place de la feuille de style ??chou??e.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "La feuille de style n'a pas ??t?? trouv??. Erreur: ", e);
        }




        //Permet d'afficher la demande de permission de g??olocalisation au d??mmarage de l'application
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission. ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                }
            }
            return;
        }

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            // Build the alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Services de localisation inactifs");
            builder.setMessage("Veuillez activer les services de localisation et le GPS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }


        mMap.setMyLocationEnabled(true);

        loadBaliseState();

        //lignes de devMode
        LatLng pointZoom= new LatLng(48.0859, -0.7580282);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pointZoom,20));


        /**Permet de positionner la map sur l'utilisateur**/

        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);




        mMap.setOnInfoWindowClickListener(this);
    }






    @Override
    public void onInfoWindowClick(Marker marker) {


        Log.i("Clique sur le marker", marker.getTitle());

        this.change(marker);

        //rediriger vers l'info du batiment choisi


    }





    //Permet de changer d'??cran pour passer a l'??cran des informations de batiment
    public void change(Marker marker)  {
        Balise baliseSelected = null;
        for(Balise bal : tabBalise){
            if(bal.getId().equals(marker.getId())){
                baliseSelected = bal;
                break;
            }
        }

        baliseSelected.validerBalise();

        switch (marker.getTitle()){
            case "En savoir plus: Restaurant Universitaire":
                Intent RU= new Intent(getApplicationContext(),RuActivity.class);
                startActivity(RU);
                break;
            case "En savoir plus: Info":
                Intent Info= new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(Info);
                break;
            case "En savoir plus: MMI":
                Intent MMI= new Intent(getApplicationContext(),MMIActivity.class);
                startActivity(MMI);
                break;
            case "En savoir plus: GB":
                Intent GB= new Intent(getApplicationContext(),GBActivity.class);
                startActivity(GB);
                break;
            case "En savoir plus: TC":
                Intent TC= new Intent(getApplicationContext(),TCActivity.class);
                startActivity(TC);
                break;
            case "En savoir plus: Administration":
                Intent Administration= new Intent(getApplicationContext(),AdministrationActivity.class);
                startActivity(Administration);
                break;
            case "En savoir plus: Bibliotheque":
                Intent BU= new Intent(getApplicationContext(),BUActivity.class);
                startActivity(BU);
                break;
            case "En savoir plus: Droit":
                Intent Droit= new Intent(getApplicationContext(),DroitActivity.class);
                startActivity(Droit);
                break;
            case "En savoir plus: CROUS":
                Intent Crous= new Intent(getApplicationContext(),CrousActivity.class);
                startActivity(Crous);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng myPosition = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(myPosition, 20);
        mMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {

                // Si la permission est accord??e d??marre l'application normalement
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
                //Sinon affiche un message d'erreur impliquant de relancer l'application
                else {
                    new AlertDialog.Builder(this)
                            .setTitle("Localisation refus??")
                            .setMessage("L'application a besoin de la localisation de l'appareil pour fonctionner correctement")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                private static final int SIGNAL_KILL = 0;

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Process.sendSignal(Process.myPid(), SIGNAL_KILL);
                                    Process.killProcess(Process.myPid());
                                    System.exit(1);
                                }
                            })
                            .create()
                            .show();
                }
                return;
            }
        }
    }

    private void saveBaliseState(){
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json;
        for(Balise balise : tabBalise){
            json = gson.toJson(balise.getValide());
            prefsEditor.putString(balise.getId(), json);
        }
        prefsEditor.commit();
    }

    private void loadBaliseState(){
        Gson gson = new Gson();
        String json;
        for (Balise balise : tabBalise) {
            json = mPrefs.getString(balise.getId(), "false");
            Log.v("load", json);
            String valide = gson.fromJson(json, String.class);
            balise.creerMarker(mMap, Boolean.parseBoolean(valide));
        }
    }

    protected void onStop () {
        Log.v("stop", "stop");
        super.onStop();
        saveBaliseState();
    }
}