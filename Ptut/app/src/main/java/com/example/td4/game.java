package com.example.td4;

import androidx.fragment.app.FragmentActivity;

public abstract class game extends FragmentActivity {

    public boolean state;

    public void setState(boolean state){
        this.state=state;
    }

}
