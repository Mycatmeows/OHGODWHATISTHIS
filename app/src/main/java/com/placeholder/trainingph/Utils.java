package com.placeholder.trainingph;


import android.widget.Toast;

import java.io.Console;
import java.util.Timer;
import java.util.TimerTask;


public class Utils {


    public synchronized void interruptMeAfter(Thread T, final int interval){
        Timer time = new Timer();
        final long startTime = System.currentTimeMillis();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                //T.interrupt();
            }
        },interval);
    }

    public static void sendGenericErrorMessage(String prefix){
        Toast.makeText(Constants.DEFAULT_CONTEXT,"Something bad happened @ "+prefix, Toast.LENGTH_LONG).show();
    }

    public static String BluetoothMessageCodeToColumn(int code){
        return "";
    }




}
