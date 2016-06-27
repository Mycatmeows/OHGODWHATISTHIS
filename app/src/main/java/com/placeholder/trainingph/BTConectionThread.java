package com.placeholder.trainingph;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by jmontez on 23-06-2016.
 */
public class BTConectionThread extends Thread {

    private final BluetoothServerSocket serverSocket;

    public  BTConectionThread(BluetoothAdapter bluetoothAdapter){
        BluetoothServerSocket tmp = null;
        try{
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("NAME", UUID.fromString(bluetoothAdapter.getName()));
        }
        catch(IOException e){

        }

    }

}
