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

    private static BluetoothAdapter _adapter;
    private static BluetoothDevice _target;
    private BluetoothServerSocket _serverSocket;
    private BluetoothSocket _socket;
    private List<BluetoothDevice> _devices;
	private SimpleThread reader;
	private Map Reporter;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(BluetoothDevice.ACTION_FOUND)){
                _target = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                _adapter.cancelDiscovery();
                new BTConectionThread(_target);
            }
        }
    };

    public static void TurnOn(Context ctx){
        if(!_adapter.isEnabled()){
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            turnOn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(turnOn);
            Toast.makeText(ctx, "Bluetooth enable.", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(ctx,"Already on", Toast.LENGTH_SHORT).show();
    }

    public void off(Activity currentActivity){
       _adapter.disable();
        Toast.makeText(currentActivity.getApplicationContext(), "Turning bluetooth off", Toast.LENGTH_SHORT).show();
    }

    public void makeVisible(Context ctx){
        Intent makeVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        ctx.startActivity(makeVisible);
    }

    public boolean startConnection(Context ctx){
        _adapter.startDiscovery();
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        ctx.registerReceiver(broadcastReceiver, intentFilter);
        return true;
    }

    public void sendStartCommand(){
        if(_target!=null){
            DialogThreadManager DTM = new DialogThreadManager();
            DTM.addThread(new DialogThread(_target));

        }
    }





    public BluetoothUtil(){
        _adapter = BluetoothAdapter.getDefaultAdapter();
		Reporter = new HashMap<java.lang.Integer, java.lang.String>();
    }

    public boolean isConnected(){
        return _devices.get(0) != null;
    }

    public int read(){
        try{
			int _in = _socket.getInputStream().read();
			System.out.println("READ: "+_in);
            return _in;
        }
        catch(IOException e){
            System.out.println("Error reading from buffer");
        }
       return 0;
    }

    public void write(byte data){
		System.out.println("Wrote "+data);
        try{
            _socket.getOutputStream().write(data);
        }
        catch(IOException e){
          System.out.println("Error writing to buffer");
        }

    }
}

