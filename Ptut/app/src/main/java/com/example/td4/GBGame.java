package com.example.td4;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class GBGame extends Present {

    ImageView[] ingredients;
    ConstraintLayout general;
    ImageView add;
    List<ImageView> proposition;
    Button valider, cacher;
    int[] recette;
    Boolean win;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbgame);

        win = true;

        recette = new int[]{-1, -1, -1};
        int random = -1;
        for(int i = 0; i < 3; i++){
            random = (int)(Math.random() * 5);
            if(recette[0] == random || recette[1] == random || recette[2] == random){
                i--;
            }else{
                recette[i] = random;
            }
        }

        proposition = new ArrayList<>();

        valider = findViewById(R.id.validerButton);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(proposition.size() == 3){
                    for(int i = 0; i < 3; i++) {
                        if (ingredients[recette[i]].getId() != proposition.get(i).getId()) {
                            win = false;
                        }
                    }
                }else{win = false;}
                popupFin(win);
            }
        });

        add = findViewById(R.id.add);
        add.setOnDragListener(new MyDragListener());

        general = findViewById(R.id.general);

        general.setBackgroundResource(R.drawable.background_gb);

        popupRecette();

        ingredients = new ImageView[5];
        ingredients[0] = findViewById(R.id.aile);
        ingredients[1] = findViewById(R.id.bave);
        ingredients[2] = findViewById(R.id.griffe);
        ingredients[3] = findViewById(R.id.corne);
        ingredients[4] = findViewById(R.id.oreille);

        for(ImageView v : ingredients){
            v.setOnTouchListener(new MyTouchListener());
        }



    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {
        Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackground(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackground(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    LinearLayout container = (LinearLayout) v.getParent();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    container.addView(view);
                    //view.setVisibility(View.VISIBLE);

                    proposition.add((ImageView) view);

                    switch(view.getId()){
                        case R.id.aile : general.setBackgroundResource(R.drawable.background_gb1);break;
                        case R.id.griffe : general.setBackgroundResource(R.drawable.background_gb2);break;
                        case R.id.bave : general.setBackgroundResource(R.drawable.background_gb3);break;
                        case R.id.corne : general.setBackgroundResource(R.drawable.background_gb4);break;
                        case R.id.oreille : general.setBackgroundResource(R.drawable.background_gb5);break;
                        default:break;
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    public void popupFin(boolean win){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vous avez gagné !");
        builder.setMessage("Quelle puissante potion ! Pas pour n'importe quel aventurier");

        if(!win){
            builder.setTitle("Vous avez perdu...");
            builder.setMessage("Je ne boirais pas ça si j'étais vous..");
        }

        builder.setPositiveButton(getResources().getString(R.string.Menu), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), GBActivity.class));
            }
        });
        builder.create().show();
    }

    public void popupRecette(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String recipe = "Mélanger dans le chaudron ";

        for(int i : recette){
            switch(i){
                case 0 : recipe += "une aile de chauve souris"; break;
                case 1 : recipe += "de la bave de crapaud"; break;
                case 2 : recipe += "une serre de corbeau"; break;
                case 3 : recipe += "une corne de licorne"; break;
                case 4 : recipe += "une oreille humaine"; break;
            }
            recipe += ", ";
        }

        recipe += "et servez la préparation. Attention a l'ordre !";

        builder.setTitle("Recette : ");
        builder.setMessage(recipe);

        builder.create().show();
    }



}
