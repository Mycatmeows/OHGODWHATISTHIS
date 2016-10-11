package com.placeholder.trainingph;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;
import android.R;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends Activity {

    private BluetoothUtil bluetoothUtil;
    private boolean BT_ON=false;
    private ThreadManager threadManager = new ThreadManager<>();
    private HashMap<String, Integer> valueMap;


    public MainActivity() {
        bluetoothUtil = new BluetoothUtil();
    }

    public void initBluetooth(View v) throws BluetoothException{
        Constants.DEFAULT_CONTEXT = v.getContext();
        bluetoothUtil.TurnOn(v.getContext());
        bluetoothUtil.makeVisible(v.getContext());
        bluetoothUtil.startConnection(v.getContext());
        //((GridLayout)findViewById(R.id.grid).setVisibility(View.VISIBLE);
    }

    public void sendStartCommand(View v){
        threadManager.addThread(bluetoothUtil.sendStartCommand());
    }



    public boolean canStart(){
        return bluetoothUtil.isReady();
    }

    public boolean hasStarted(){
        return threadManager.hasStarted();
    }

}
