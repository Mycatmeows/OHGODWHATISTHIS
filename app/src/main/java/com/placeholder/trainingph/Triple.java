package com.placeholder.trainingph;

import java.util.Iterator;
import java.util.Objects;

 
public class Triple<E> {
	
	private E Value;
	private boolean Consumed;
	private Triple<E> Next;
	
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
	}
	
	private void setNext(Triple t){
		this.Next = t;
	}

	public boolean hasNext(){
		return Next!=null;
	}


}