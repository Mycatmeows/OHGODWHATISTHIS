package com.placeholder.trainingph;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;

public class BTConnectionThread implements Runnable {

    private BluetoothServerSocket socket;
    private BluetoothDevice target;
    private boolean isConnected=false;
    private BluetoothAdapter adapter;


    public  BTConnectionThread(BluetoothAdapter adapter){
        this.adapter = adapter;
        BluetoothServerSocket sck = null;
        try{
            sck = adapter.listenUsingInsecureRfcommWithServiceRecord(adapter.getName(),Constants.APP_UUID);
        }
        catch (IOException|NullPointerException e){
            Utils.sendGenericErrorMessage("BTConnectionThread() : "+e.getMessage());
        }
        if(sck != null){
            socket=sck;
            this.run();
        }

    }

    @Override
    public void run() {
        isConnected = false;
        while(!isConnected){
          try{
              socket.accept();
          }
          catch (IOException e){
              Utils.sendGenericErrorMessage("BTConnectionThread.run() : "+e.getMessage());
          }
        }
        isConnected=true;
    }

    public BluetoothServerSocket getSocket(){
        return socket;
    }

    public boolean isConnected(){
        return isConnected;
    }

    public BluetoothDevice getDevice(){
        return target;
    }

    public boolean isBonded(){
        return target.getBondState()==BluetoothDevice.BOND_BONDED;
    }

}
