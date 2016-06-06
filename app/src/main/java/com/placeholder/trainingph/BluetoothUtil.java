package com.placeholder.trainingph;

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

import java.io.IOException;
import java.util.*;

/**
 * Created by joaop on 15/05/2016.
 */
public class BluetoothUtil  {

    private BluetoothAdapter _adapter;
    private BluetoothDevice _target;
    private BluetoothServerSocket _serverSocket;
    private BluetoothSocket _socket;
    private List<BluetoothDevice> _devices;
    private MainScreen _screen;
	private SimpleThread reader;
	private Map Reporter;

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

    public BluetoothUtil(MainScreen screen){
        _screen = screen;
        _adapter = BluetoothAdapter.getDefaultAdapter();
		Reporter = new HashMap<java.lang.Integer, java.lang.Integer>();
    }

    public boolean isConnected(){
        return _devices.get(0) != null;
    }

    public boolean connect(){
        if(_adapter!=null && !_adapter.isEnabled()) {
            Intent bluetoothON = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            _screen.startActivityForResult(bluetoothON, 0x01);
        }
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        _screen.registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

        /* TODO: Scan for previously connected devices
        Set<BluetoothDevice> pairedDevices = _adapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                _adapter.add(device.getName() + "\n" + device.getAddress());
            }
        } */


        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        _screen.startActivity(discoverableIntent);

        try{
            _serverSocket = _adapter.listenUsingRfcommWithServiceRecord("BTConn", UUID.fromString("e632f568-f7de-4dbe-b351-25f8b993e4ca"));
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

