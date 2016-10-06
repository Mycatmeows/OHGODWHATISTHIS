package com.placeholder.trainingph;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    private BluetoothUtil bluetoothUtil;
    private boolean BT_ON=false;

    public MainActivity(){


    }

    public void initBluetooth(View v){
        bluetoothUtil = new BluetoothUtil();
        bluetoothUtil.TurnOn(v.getContext());
        bluetoothUtil.makeVisible(v.getContext());
        bluetoothUtil.startConnection(v.getContext());

    }

    public void initConnection(View v){
        bluetoothUtil.sendStartCommand();
    }

}
