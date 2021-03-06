package com.placeholder.trainingph;

import java.util.Objects;

/**
 * Created by joaop on 15/05/2016.
 */
 
public class Triple<E>{
	
	private E Value;
	private boolean Consumed;
	private Triple Next;
	
	public Triple(){
		
	}
	
	public Triple(E vE, Triple Origin){
			this.Value = vE;
			this.Consumed = false;
			this.Next = null;
	}
	
	public void append(Triple t1){
		if(t1.getNext()==null){
			t1.Next=this;
		}
		else{
			append(t1.getNext());
		}
	}
	
	public E getValue(){
		return Value;
	}
	
	public boolean getIsConsumed(){
		return Consumed;
	}
	
	public Triple getNext(){
		return Next;
	}
	
	public void consume(){
		Consumed = true;
		if(Next!=null){
			this.Value=this.Next.getValue();
			this.Consumed = this.Next.Consumed;
			this.Next = this.Next.Next;
		}
	}
	
	private void setNext(Triple t){
		this.Next = t;
	}
	
}