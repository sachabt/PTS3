package com.example.td4;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InfoActivity extends Activity {

    Button validateButton;
    TextView scoreText;

    ImageView[][] dragImages;

    int score;

    List<InfoActivity.Dragable> dragables;
    List<InfoActivity.DragZone> dragZones;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infogame);

        score = 0;

        dragables = new ArrayList<>();
        dragZones = new ArrayList<>();

        dragImages  = new ImageView[7][4];

        dragImages[0][0] = findViewById(R.id.rouge1);
        dragImages[1][0] = findViewById(R.id.violet2);
        dragImages[2][0] = findViewById(R.id.rouge3);
        dragImages[3][0] = findViewById(R.id.rouge4);
        dragImages[4][0] = findViewById(R.id.rouge5);
        dragImages[5][0] = findViewById(R.id.rouge6);
        dragImages[6][0] = findViewById(R.id.rouge7);

        dragImages[0][1] = findViewById(R.id.bleu1);
        dragImages[1][1] = findViewById(R.id.bleu2);
        dragImages[2][1] = findViewById(R.id.bleu3);
        dragImages[3][1] = findViewById(R.id.bleu4);
        dragImages[4][1] = findViewById(R.id.bleu5);
        dragImages[5][1] = findViewById(R.id.bleu6);
        dragImages[6][1] = findViewById(R.id.bleu7);

        dragImages[0][2] = findViewById(R.id.jaune1);
        dragImages[1][2] = findViewById(R.id.jaune2);
        dragImages[2][2] = findViewById(R.id.jaune3);
        dragImages[3][2] = findViewById(R.id.jaune4);
        dragImages[4][2] = findViewById(R.id.jaune5);
        dragImages[5][2] = findViewById(R.id.jaune6);
        dragImages[6][2] = findViewById(R.id.jaune7);

        dragImages[0][3] = findViewById(R.id.violet1);
        dragImages[1][3] = findViewById(R.id.violet2);
        dragImages[2][3] = findViewById(R.id.violet3);
        dragImages[3][3] = findViewById(R.id.violet4);
        dragImages[4][3] = findViewById(R.id.violet5);
        dragImages[5][3] = findViewById(R.id.violet6);
        dragImages[6][3] = findViewById(R.id.violet7);

        dragables.add(new Dragable(findViewById(R.id.drag1), findViewById(R.id.dragTail1), 1));
        dragables.add(new Dragable(findViewById(R.id.drag2), findViewById(R.id.dragTail2), 2));
        dragables.add(new Dragable(findViewById(R.id.drag3), findViewById(R.id.dragTail3), 3));
        dragables.add(new Dragable(findViewById(R.id.drag4), findViewById(R.id.dragTail4), 4));

        generate();



        View.OnClickListener validateHandler = new View.OnClickListener() {
            public void onClick(View v) {
                if (verif()) {
                    score++;
                } else {
                    score--;
                }
                scoreText.setText("Score : " + score);
                generate();
                for (Dragable d : dragables) {
                    d.reset();
                }
            }
        };

        validateButton = (Button) this.findViewById(R.id.validateButton);
        validateButton.setOnClickListener(validateHandler);

        scoreText = (TextView) this.findViewById(R.id.scoreText);
        scoreText.setText("Score : " + score);


    }

    private class DragZone{
        ImageView zone;
        int id;

        public DragZone(ImageView zone, int id){
            this.zone = zone;
            this.id = id;

            zone.setOnDragListener(new MyDragListener());

            dragZones.add(this);
        }
    }

    private class Dragable{

        ImageView head, tail;
        ViewGroup basePosition, currentPosition;

        int id;

        public Dragable(ImageView head, ImageView tail, int id){
            this.head = head;
            this.tail = tail;
            this.id = id;

            basePosition = (ViewGroup) tail.getParent();
            currentPosition = basePosition;
            head.setOnTouchListener(new MyTouchListener());

            dragables.add(this);
        }

        public void reset(){
            ViewGroup currentPosition = (ViewGroup) head.getParent();
            currentPosition.removeView(head);
            basePosition.addView(head);
            head.setVisibility(View.VISIBLE);
        }

    }

    public boolean verif(){
        for (Dragable d : dragables) {
            for(DragZone z : dragZones) {
                if(d.head.getParent() == z.zone && d.id != z.id){
                    return false;
                }
            }
        }
        return true;
    }

    private void generate(){

        if(!dragZones.isEmpty()){
            for(Iterator<DragZone> iterator = dragZones.iterator(); iterator.hasNext(); ){
                iterator.next().zone.setVisibility(View.INVISIBLE);
                iterator.remove();
            }
        }

        ImageView dragZone;
        for(int i = 0; i < 4; i++){
            dragZone = dragImages[(int) (Math.random()*(6) + 1)][i];
            dragZone.setVisibility(View.VISIBLE);
            dragZones.add(new DragZone(dragZone, i));
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
}