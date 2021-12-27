package com.example.td4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.Random;

public class TCGame extends AppCompatActivity {

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

    private ConstraintLayout endOfGameWindow;
    private Button endGameButton;
    private CheckBox[] codeCheck = new CheckBox[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.TCGame);

        rotationManageur = new TCRotationManageur(this);

        endOfGameWindow = findViewById(R.id.endOfGameWindow);
        endOfGameWindow.setVisibility(View.GONE);

        endGameButton = findViewById(R.id.endGameButton);

        endGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                    endGame();
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

    private void endGame() {
        endOfGameWindow.setVisibility(View.VISIBLE);
        //intent to switch back to the main scene
        //maybe show a you won screen
    }

}