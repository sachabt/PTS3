package com.example.td4;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class TCRotationManageur implements SensorEventListener {

    public TCGame context;
    public SensorManager sensorManager;
    public Sensor rotationVectorSensor;
    public Direction direction = Direction.NONE;

    private boolean init=true;//FIXME ugly, but simple, way to do something
    private final int sensibility = 15;
    private double phoneStartPitch = 0;
    public TCRotationManageur(TCGame pContext){
        context = pContext;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }


    /**
     * retourne angle d'inclinaison du téléphone selon l'axe z
     * @return
     */
    public Direction getDirection(){
        return direction;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    /**
     * est appeler lorsque l'orientation change
     * crée une animation de rotation sur l'image view lockImage (RotateAnimation)
     * Si le degré correspond à un des numéros du code le téléphone vibre
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
    //FIXME only work if the phone is flat (like this - not like this |)
        if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
            //Get Rotation Vector Sensor Values
            double[] g = convertFloatsToDoubles(event.values.clone());

            //Normalise
            double norm = Math.sqrt(g[0] * g[0] + g[1] * g[1] + g[2] * g[2] + g[3] * g[3]);
            g[0] /= norm;
            g[1] /= norm;
            g[2] /= norm;
            g[3] /= norm;

            //Set values to commonly known quaternion letter representatives
            double x = g[0];
            double y = g[1];
            double z = g[2];
            double w = g[3];

            //Calculate Pitch in degrees (-180 to 180)
            double sinP = 2.0 * (w * x + y * z);
            double cosP = 1.0 - 2.0 * (x * x + y * y);
            double pitch = Math.atan2(sinP, cosP) * (180 / Math.PI);
            //Calculate Tilt in degrees (-90 to 90)
            double sinT = 2.0 * (w * y - z * x);
            double tilt;
            if (Math.abs(sinT) >= 1)
                tilt = Math.copySign(Math.PI / 2, sinT) * (180 / Math.PI);
            else
                tilt = Math.asin(sinT) * (180 / Math.PI);

            if(init){
                init = false;
                phoneStartPitch = pitch; //phoneStartPitch was equal to 0
            }

            Log.v("calcul", "pitch : "+ Double.toString(Math.abs(pitch-phoneStartPitch))+ ", tilt : "+ Double.toString(Math.abs(tilt)));
            //call the function depending on wich way the phone is the most tilted
            if((Math.abs(pitch-phoneStartPitch))<sensibility && Math.abs(tilt)<sensibility) {
                direction = Direction.NONE;
            } else if(Math.abs(pitch-phoneStartPitch)>Math.abs(tilt)) {
                direction = ( (pitch - phoneStartPitch )< 0 ? Direction.UP : Direction.DOWN);
            } else {
                direction = (tilt < 0 ? Direction.LEFT : Direction.RIGHT);
            }
            context.move(direction);
        }
    }

    private double[] convertFloatsToDoubles(float[] input)
    {
        if (input == null)
            return null;

        double[] output = new double[input.length];

        for (int i = 0; i < input.length; i++)
            output[i] = input[i];

        return output;
    }

}
