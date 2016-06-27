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

/**
 * Created by joaop on 15/05/2016.
 */
public class BluetoothUtil{

    private BluetoothAdapter _adapter;
    private BluetoothDevice _target;
    private BluetoothServerSocket _serverSocket;
    private BluetoothSocket _socket;
    private List<BluetoothDevice> _devices;
	private SimpleThread reader;
	private Map Reporter;

    public void on(Activity currentActivity){
        if(!_adapter.isEnabled()){
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            currentActivity.startActivityForResult(turnOn,0x01);
            Toast.makeText(currentActivity.getApplicationContext(), "Bluetooth enabled.", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(currentActivity.getApplicationContext(),"Already on", Toast.LENGTH_SHORT).show();
    }

    public void off(Activity currentActivity){
       _adapter.disable();
        Toast.makeText(currentActivity.getApplicationContext(), "Turning bluetooth off", Toast.LENGTH_SHORT).show();
    }

    public void makeVisible(Activity currentActivity){
        Intent makeVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        makeVisible.putExtra()
        currentActivity.startActivityForResult(makeVisible, 0x01);
    }

    public void startServer(){
        try{
            _serverSocket = _adapter.listenUsingRfcommWithServiceRecord("BTConn", UUID.fromString(_adapter.getName()));
            _socket = _serverSocket.accept();
            reader = new SimpleThread(Reporter, _socket);
            reader.ready();
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }



    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                //_adapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    };
    // Register the BroadcastReceiver

    public BluetoothUtil(){
        _adapter = BluetoothAdapter.getDefaultAdapter();
		Reporter = new HashMap<java.lang.Integer, java.lang.Integer>();
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

