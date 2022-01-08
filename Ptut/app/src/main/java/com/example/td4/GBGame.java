package com.example.td4;

import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class GBGame extends Present {

    ImageView[] ingredients;
    ConstraintLayout general;
    ImageView add;
    List<ImageView> proposition;
    Button valider, cacher;
    int[] recette;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbgame);

        recette = new int[]{(int)(Math.random() * 5), (int)(Math.random() * 5), (int)(Math.random() * 5)};
        proposition = new ArrayList<>();

        createDialog(recette);

        valider = findViewById(R.id.valider);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(proposition.size() == 3){
                    for(int i = 0; i < 4; i++){
                        if(ingredients[recette[i]].getId() != proposition.get(i).getId()){
                            //cperdu
                        }
                    }
                    //cgagné
                }
                //cperdu
            }
        });

        add = findViewById(R.id.add);
        add.setOnDragListener(new MyDragListener());

        general = findViewById(R.id.general);

        general.setBackgroundResource(R.drawable.background_gb);

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

    public void createDialog(int[] recette){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Voici la recette");

        String ingrédients = "Tu aura besoin ";

        for(int i = 0; i < 3; i++) {
            switch (recette[i]) {
                case 0:
                    ingrédients += "d'une aile, ";
                    break;
                case 1:
                    ingrédients += "de bave de crapaud, ";
                    break;
                case 2:
                    ingrédients += "d'une griffe de chat, ";
                    break;
                case 3:
                    ingrédients += "d'une corne de licorne, ";
                    break;
                case 4:
                    ingrédients += "d'une oreille d'humain, ";
                    break;
            }
        }

        builder.setMessage(ingrédients);
        builder.create().show();
    }

}
