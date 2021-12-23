package com.example.PTS3;

import androidx.fragment.app.FragmentActivity;

public abstract class game extends FragmentActivity {

    public boolean state;

    public void setState(boolean state){
        this.state=state;
    }

}
