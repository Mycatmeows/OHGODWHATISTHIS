package com.placeholder.trainingph;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.io.IOException;
import java.util.UUID;

public class BTConectionThread implements Runnable {

    private BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;
    private BluetoothAdapter adapter;
    private BluetoothDevice target;
    private boolean isConnected=false;


    public  BTConectionThread(BluetoothDevice device){
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
}
