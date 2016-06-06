package com.placeholder.trainingph;

import java.util.Objects;

/**
 * Created by joaop on 15/05/2016.
 */
public class SimpleThread extends Thread {

    private Object stopCondition;
	int _in;
	private List writeTarget;
	private Socket src;
	private boolean run=false;
	
	Triple<int, boolean, Object> Q;
	
	public SimpleThread(List wrTarget, Socket source){
		Q = new Triple<int, boolean, Object>();
		this.writeTarget = wrTarget;
		this.src = source;
	}
	
	public void ready(){
		this.start();		
	}
	
	public void setIn(int _in){
		Q.append(new Triple<int, boolean, Object>(_in, Q);
	}
	
	@Override
	public void run(){
			if(!Q.isConsumed()){
			_in = Q.getValue();
			Q.Consume();
			Message M = Message.fromIntValue(_in);
			writeTarget.add(M.getValue, M.getCode)
			}
		}
		
	}



}
