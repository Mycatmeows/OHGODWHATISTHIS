package com.placeholder.trainingph;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;

public class DialogThread<E> implements Runnable{

    private BluetoothDevice target;
    private BluetoothSocket socket;
    private Triple<E> T;


    public DialogThread(BluetoothDevice device, BluetoothSocket connection) {
        target = device;
        socket = connection;
        T = new Triple<E>(null, null);//Dummy
        T.consume();
    }

    @Override
    public void run() {
        try{
            InputStream Input = socket.getInputStream();
            int val = Input.read();
            while(val!=-1){
                T.append(new Triple<Integer>(val,T));
                val=Input.read();
            }
        }
        catch (IOException e){
            Utils.sendGenericErrorMessage("DialogThread.run()");
        }

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

    public boolean hasNext(){
        return T.hasNext();
    }

    public BluetoothSocket getSocket(){
        return socket;
    }

    public BluetoothDevice getDevice(){
        return target;
    }


}
