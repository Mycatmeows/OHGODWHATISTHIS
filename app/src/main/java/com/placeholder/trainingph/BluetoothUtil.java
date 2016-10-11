package com.placeholder.trainingph;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

public class BluetoothUtil{

    private BluetoothAdapter adapter;
    private BluetoothDevice target;
    private BluetoothSocket _socket;
    private BTConnectionThread connectionThread;
    public static String DEVICE_NAME = null;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           // if(intent.getAction().equals(BluetoothDevice.ACTION_FOUND)){
                target = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //adapter.cancelDiscovery();TODO: Measure impact on adapter
                Toast.makeText(context,"Started a connection",Toast.LENGTH_LONG).show();
                context.unregisterReceiver(this);
                connectionThread = new BTConnectionThread(adapter);
           // }
        }
    };

    public void TurnOn(Context ctx){
        if(!adapter.isEnabled()){
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            turnOn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(turnOn);
            DEVICE_NAME= adapter.getName();
            Toast.makeText(ctx, "Bluetooth enabled!", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(ctx,"Already on", Toast.LENGTH_SHORT).show();
    }

    public void off(Activity currentActivity){
       adapter.disable();
        Toast.makeText(currentActivity.getApplicationContext(), "Turning bluetooth off", Toast.LENGTH_SHORT).show();
    }

    public void makeVisible(Context ctx){
        Intent makeVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        makeVisible.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        ctx.startActivity(makeVisible);
    }

    public boolean startConnection(Context ctx){
        /*for (BluetoothDevice device : adapter.getBondedDevices()) {
            if (device.getName().equals(Constants.BT_ANTENNA_NAME)) {//valid connection
                target = device;
                connectionThread = new BTConnectionThread(target);
            }
        }*/
        adapter.startDiscovery();
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        ctx.registerReceiver(broadcastReceiver, intentFilter);

        //EXPERIMENTAL
        //
        return true;
    }

    public DialogThread<java.lang.Integer> sendStartCommand(){
        if(target !=null){
            return new DialogThread<>(target, _socket);
        }
        return null;
    }

    public BluetoothUtil() {
        adapter = BluetoothAdapter.getDefaultAdapter();
        if(adapter ==null) Utils.sendGenericErrorMessage("BluetoothUtil()");
    }

    public boolean isReady(){
        return !(_socket==null || target ==null);
    }


}

