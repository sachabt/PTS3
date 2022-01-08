package com.example.td4;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

public class GBGame extends Activity {

    ImageView[] ingredients;
    ConstraintLayout general;
    ImageView add;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbgame);

        add = findViewById(R.id.add);
        add.setOnDragListener(new MyDragListener());

        general = findViewById(R.id.general);

        ingredients = new ImageView[5];
        ingredients[0] = findViewById(R.id.aile);
        ingredients[1] = findViewById(R.id.bave);
        ingredients[2] = findViewById(R.id.grife);
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
                    for(int i = 0; i < 5; i++){
                        if(ingredients[i] == view){
                            switch(i){
                                case 0 : general.setBackgroundResource(R.drawable.background_gb1);
                                case 1 : general.setBackgroundResource(R.drawable.background_gb2);
                                case 2 : general.setBackgroundResource(R.drawable.background_gb3);
                                case 3 : general.setBackgroundResource(R.drawable.background_gb4);
                                case 4 : general.setBackgroundResource(R.drawable.background_gb5);
                                default : general.setBackgroundResource(R.drawable.background_gb);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    }
}
