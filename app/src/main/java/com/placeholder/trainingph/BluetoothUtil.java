package com.placeholder.trainingph;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.content.Intent;

import java.util.List;

/**
 * Created by joaop on 15/05/2016.
 */
public class BluetoothUtil  {

    private BluetoothAdapter _adapter;
    private BluetoothDevice _target;
    private BluetoothServerSocket _socket;
    private List<BluetoothDevice> _devices;

    public BluetoothUtil(){
        _adapter = BluetoothAdapter.getDefaultAdapter();
       // _socket = new BluetoothServerSocket();
    }

    public boolean isConnected(){
        return _devices.get(0) != null;
    }

    public boolean connect(){
        Intent bluetoothON = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

        _adapter.
        SimpleThread S = SimpleThread.buildSimpleThread();

        return true;
    }

    public void destroy(){
        _adapter.disable();
    }

    public int read(){
       return 0;
    }

    public void write(byte data){

    }

}

