package com.placeholder.trainingph;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.bluetooth.BluetoothSocket;
import android.widget.Toast;

import java.io.IOException;
import java.util.*;

public class BluetoothUtil{

    private BluetoothAdapter _adapter;
    private BluetoothDevice _target;
    private BluetoothSocket _socket;
    private BTConnectionThread connectionThread;
    public static String DEVICE_NAME = null;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(BluetoothDevice.ACTION_FOUND)){
                _target = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                _adapter.cancelDiscovery();
                connectionThread = new BTConnectionThread(_target);
            }
        }
    };

    public void TurnOn(Context ctx){
        if(!_adapter.isEnabled()){
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            turnOn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(turnOn);
            DEVICE_NAME=_adapter.getName();
            Toast.makeText(ctx, "Bluetooth enabled!", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(ctx,"Already on", Toast.LENGTH_SHORT).show();
    }

    public void off(Activity currentActivity){
       _adapter.disable();
        Toast.makeText(currentActivity.getApplicationContext(), "Turning bluetooth off", Toast.LENGTH_SHORT).show();
    }

    public void makeVisible(Context ctx){//NOT needed as we will connect to arduino as server
       // Intent makeVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
       // ctx.startActivity(makeVisible);
    }

    public boolean startConnection(Context ctx){
        for (BluetoothDevice device : _adapter.getBondedDevices()){
            if(device.getName().equals(Constants.BT_ANTENNA_NAME)){
                _target=device;
                connectionThread = new BTConnectionThread(_target);
            }
        }

        _adapter.startDiscovery();
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        ctx.registerReceiver(broadcastReceiver, intentFilter);
        return true;
    }

    public DialogThread<java.lang.Integer> sendStartCommand(){
        if(_target!=null){
            return new DialogThread<>(_target, _socket);
        }
        return null;
    }

    public BluetoothUtil() throws BluetoothException{
        _adapter = BluetoothAdapter.getDefaultAdapter();
        if(_adapter==null) throw new BluetoothException("YOUR DEVICE DOES NOT SEEM TO HAVE BLUETOOTH!");
    }

    public boolean isReady(){
        return !(_socket==null || _target ==null);
    }


}

