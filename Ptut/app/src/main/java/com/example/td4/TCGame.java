package com.example.td4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.Random;

public class TCGame extends Game {

    int codeLength = 4;
    int numberOfRightDirections = 0;
    Direction[] code;
    TCRotationManageur rotationManageur;
    private Direction lastDirection = Direction.NONE;
    private CheckBox code1;
    private CheckBox code2;
    private CheckBox code3;
    private CheckBox code4;

    private ImageView upArrow;
    private ImageView downArrow;
    private ImageView rightArrow;
    private ImageView leftArrow;

    //private ConstraintLayout endOfGameWindow;
    private Button endGameButton;
    private CheckBox[] codeCheck = new CheckBox[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tcgame);

        rotationManageur = new TCRotationManageur(this);

        /*endOfGameWindow = findViewById(R.id.endOfGameWindow);
        endOfGameWindow.setVisibility(View.GONE);

        endGameButton = findViewById(R.id.endGameButton);

        endGameButton.setOnClickListener(v -> finish());*/
        code = generateCode();

        for(Direction i : code){
            Log.v("code", String.valueOf(i));
        }

        codeCheck[0] = code1 = findViewById(R.id.code1);
        codeCheck[1] = code2 = findViewById(R.id.code2);
        codeCheck[2] = code3 = findViewById(R.id.code3);
        codeCheck[3] = code4 = findViewById(R.id.code4);
        upArrow = findViewById(R.id.upArrow);
        downArrow = findViewById(R.id.downArrow);
        rightArrow = findViewById(R.id.rightArrow);
        leftArrow = findViewById(R.id.leftArrow);

        
    }

    private Direction[] generateCode(){
        //génère 4 angles dans 4 directions  différentes
        Direction[] code = new Direction[codeLength];
        for(int i=0;i<codeLength;i++){
            code[i] = Direction.values()[(new Random().nextInt(4)) + 1];
        }

        return code;
    }

    /**
     *
     * @param direction
     * @return true if the player found everything
     */
    private boolean isRight(Direction direction){
        if(code[numberOfRightDirections] == direction){
            codeCheck[numberOfRightDirections].setChecked(true);
            ++numberOfRightDirections;

        }
        else{
            setCheckboxToFalse();
            numberOfRightDirections=0;
        }
        return numberOfRightDirections == codeLength;
    }

    private void setCheckboxToFalse() {
        for(CheckBox box : codeCheck){
            box.setChecked(false);
        }
    }

    public void move(Direction direction){
        Log.v("direction", "dir : "+String.valueOf(direction));

        if(direction != lastDirection){
            displayDirection(direction);
            lastDirection = direction;
            if(direction != Direction.NONE){
                if(isRight(direction))
                    createDialog(true);
            }
        }

    }

    public void displayDirection(Direction direction){
        clearDisplay();
        switch(direction){
            case RIGHT:
                rightArrow.setImageResource(R.drawable.arrowrightblack);
                break;
            case LEFT:
                leftArrow.setImageResource(R.drawable.arrowleftblack);
                break;
            case UP:
                upArrow.setImageResource(R.drawable.arrowupblack);
                break;
            case DOWN:
                downArrow.setImageResource(R.drawable.arrowdownblack);
        }

    }

    private void clearDisplay() {
        rightArrow.setImageResource(R.drawable.arrowrightwhite);
        leftArrow.setImageResource(R.drawable.arrowleftwhite);
        upArrow.setImageResource(R.drawable.arrowupwhite);
        downArrow.setImageResource(R.drawable.arrowdownwhite);
    }

    public void createDialog(boolean win){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vous avez gagné !");
        builder.setMessage("Vous avez trouvez le code, félicitations ! Vous pouvez maintenant récupérer le contenu du coffre.");

        if(!win){
            builder.setTitle("Vous avez perdu...");
            builder.setMessage("Le code n'a pas été trouvé...");
        }

        builder.setPositiveButton(getResources().getString(R.string.Menu), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), TCActivity.class);
                startActivity(intent);
            }
        });
        builder.create().show();
    }

}