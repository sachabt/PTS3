package com.example.td4;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

public class BUGame extends FragmentActivity {

    private ImageButton backButton;
    private Animator currentAnimator;
    private int shortAnimationDuration;
    private Button mrBacker;
    private Button jack;
    private Button john;
    private Button james;
    private Button inspecteur;
    private Button player;
    private LinearLayout layoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bu_game);

        backButton = findViewById(R.id.backButton);
        mrBacker = findViewById(R.id.mrBacker);
        jack = findViewById(R.id.jack);
        john = findViewById(R.id.john);
        james = findViewById(R.id.james);
        inspecteur = findViewById(R.id.inspecteur);
        player = findViewById(R.id.player);
        layoutButton = findViewById(R.id.layoutButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BUActivity.class);
                startActivity(intent);
            }
        });

        final View livre1View = findViewById(R.id.livre1);
        livre1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(livre1View, R.drawable.livre_zoom);
            }
        });

        /*final View livre2View = findViewById(R.id.livre2);
        livre2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(livre2View, R.drawable.livre2);
            }
        });*/

        final View parcheminView = findViewById(R.id.enigme);
        parcheminView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(parcheminView, R.drawable.parchemin_ecrit);
                layoutButton.setVisibility(View.VISIBLE);
            }
        });

        mrBacker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(false);
            }
        });

        jack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(true);
            }
        });

        john.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(false);
            }
        });

        james.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(false);
            }
        });

        inspecteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(false);
            }
        });

        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(false);
            }
        });

        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
    }

    private void zoomImageFromThumb(final View thumbView, int imageResId) {

        if (currentAnimator != null) {
            currentAnimator.cancel();
        }


        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.expanded_image);
        expandedImageView.setImageResource(imageResId);


        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();


        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);


        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }


        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);


        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);


        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(shortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                currentAnimator = null;
            }
        });
        set.start();
        currentAnimator = set;


        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentAnimator != null) {
                    currentAnimator.cancel();
                }


                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(shortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutButton = findViewById(R.id.layoutButton);
                        layoutButton.setVisibility(View.GONE);
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        layoutButton = findViewById(R.id.layoutButton);
                        layoutButton.setVisibility(View.GONE);
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }
                });
                set.start();
                currentAnimator = set;
            }
        });
    }

    public void createDialog(boolean win){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vous avez gagné !");
        builder.setMessage("Le coupable est bien Jack !");

        if(!win){
            builder.setTitle("Vous avez perdu...");
            builder.setMessage("Le coupable était Jack");
        }

        builder.setPositiveButton(getResources().getString(R.string.Menu), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), BUActivity.class);
                startActivity(intent);
            }
        });
        builder.create().show();
    }
}

