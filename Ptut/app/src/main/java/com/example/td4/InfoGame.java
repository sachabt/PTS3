package com.example.td4;

import android.app.Activity;
import android.app.AlertDialog;
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

public class InfoGame extends Activity {

    ImageView[] drag;
    LinearLayout[] drop;
    Button valider;
    Boolean win;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infogame);

        win = true;

        valider = findViewById(R.id.validerButton);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ImageView drag : drag){
                    if(!verif(drag)){
                       win = false;
                    }
                }
                createDialog(win);
            }
        });

        drag = new ImageView[4];
        drag[0] = findViewById(R.id.drag1);
        drag[1] = findViewById(R.id.drag2);
        drag[2] = findViewById(R.id.drag3);
        drag[3] = findViewById(R.id.drag4);

        for(ImageView drag : drag){
            drag.setOnTouchListener(new MyTouchListener());
        }

        drop = new LinearLayout[4];
        drop[0] = findViewById(R.id.rouge);
        drop[1] = findViewById(R.id.bleu);
        drop[2] = findViewById(R.id.jaune);
        drop[3] = findViewById(R.id.violet);

        for(LinearLayout drop : drop){
            drop.setOnDragListener(new MyDragListener());
        }

    }

    private boolean verif(ImageView drag){
        switch(drag.getId()){
            case R.id.drag1 :
                if(((LinearLayout)drag.getParent()).getId() == R.id.jaune1) return true;
            case R.id.drag2 :
                if(((LinearLayout)drag.getParent()).getId() == R.id.bleu1) return true;
            case R.id.drag3 :
                if(((LinearLayout)drag.getParent()).getId() == R.id.rouge1) return true;
            case R.id.drag4 :
                if(((LinearLayout)drag.getParent()).getId() == R.id.violet1) return true;
            default : return false;
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
                    v.setBackground(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v.getParent();
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackground(normalShape);
                default:
                    break;
            }
            return true;
        }
    }

    public void createDialog(boolean win){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vous avez gagné !");
        builder.setMessage("Vous savez reconnaître les couleurs, vous ferez un bon ingénieur");

        if(!win){
            builder.setTitle("Vous avez perdu...");
            builder.setMessage("et fait sauter le courant de tout le campus");
        }

        builder.setPositiveButton(getResources().getString(R.string.Menu), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), InfoActivity.class));
            }
        });
        builder.create().show();
    }
}