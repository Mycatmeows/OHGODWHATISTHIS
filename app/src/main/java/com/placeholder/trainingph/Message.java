package com.placeholder.trainingph;

import android.webkit.WebStorage;

import java.util.Objects;

/**
 * Created by joaop on 15/05/2016.
 */
 
 public class Message{
	
	private int Code;
	private int Value;
    private String Origin;
	private static final int valueMask = 0x0FFF;
	private static final int codeMask = 0xF000;

	public Message(int Value, int Code, String Origin){
        this.Code = Code;
		this.Value = Value;
        this.Origin=Origin;
	}
	
	public static Message fromIntValue(int val, String Origin){
		return new Message(val&valueMask, val&codeMask, Origin);
	}
	
	public int getCode(){
		return Code;
	}
	
	public int getValue(){
		return Value;
	}

    public String getOrigin(){
        return Origin;
    }
	
	
	
	
 }