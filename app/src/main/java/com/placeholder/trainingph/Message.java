package com.placeholder.trainingph;

import java.util.Objects;

/**
 * Created by joaop on 15/05/2016.
 */
 
 public class Message(){
	
	private int Code;
	private int Value;
	private static final int valueMask = 0x0F;
	private static final int codeMask = 0xF0;
	
	public Message(int Value, int Code){
			this.Code = Code;
			this.Value = Value
	}
	
	public static Message fromIntValue(int val){
		return new Message(val&valueMask, val&codeMask);
	}
	
	public int getCode(){
		return Code;
	}
	
	public int getValue(){
		return Value;
	}
	
	
	
	
 }