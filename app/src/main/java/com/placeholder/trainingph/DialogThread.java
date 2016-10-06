package com.placeholder.trainingph;

import android.bluetooth.BluetoothDevice;


public class DialogThread<E> implements Runnable{

    private BluetoothDevice target;
    private Triple<E> T;


    public DialogThread(BluetoothDevice device) {
        target = device;
        T = new Triple<E>(null, null);//Dummy
        T.consume();
    }

    @Override
    public void run() {

    }

    public Triple getAndUpdate(){
        Triple out = T;
        T.consume();
        if(T.getNext()!=null) T = T.getNext();
        return out;
    }

    public Triple getCurrent(){
        return T;
    }
}
