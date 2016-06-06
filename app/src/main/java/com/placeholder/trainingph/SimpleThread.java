package com.placeholder.trainingph;

import android.bluetooth.BluetoothSocket;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by joaop on 15/05/2016.
 */
public class SimpleThread extends Thread {

    private Object stopCondition;
	private int _in;
	private Map writeTarget;
	private BluetoothSocket src;
	private boolean run=false;
	
	private Triple<java.lang.Integer> Q;
	
	SimpleThread(Map wrTarget, BluetoothSocket source){
		Q = new Triple<Integer>();
		this.writeTarget = wrTarget;
		this.src = source;
	}
	
	void ready(){
		this.start();		
	}
	
	public void setIn(int _in){
		Q.append(new Triple<java.lang.Integer>(_in, Q));
	}
	
	@Override
	public void run(){
			if(!Q.getIsConsumed()){
			_in = Q.getValue();
			Q.consume();
			Message M = Message.fromIntValue(_in);
			writeTarget.put(M.getValue(), M.getCode());
			}
		}
		
	}



}
