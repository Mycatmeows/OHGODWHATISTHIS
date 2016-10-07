package com.placeholder.trainingph;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;

public class BTConnectionThread implements Runnable {

    private BluetoothSocket socket;
    private BluetoothDevice target;
    private boolean isConnected=false;


    public  BTConnectionThread(BluetoothDevice device){
        target = device;
        this.run();
    }

    @Override
    public void run() {
        BluetoothSocket sck = null;
        isConnected = false;
        while(!isConnected){
            try{
                sck = target.createInsecureRfcommSocketToServiceRecord(Constants.APP_UUID);
                sck.connect();
                isConnected=true;
            }
            catch (IOException e){
                System.out.println("SOMETHING BAD HAPPENED D:");
            }
            catch(NullPointerException e){
                System.out.println("SOMETHING BAD HAPPENED D:");
            }
        }
        socket=sck;
    }

    public BluetoothSocket getSocket(){
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
