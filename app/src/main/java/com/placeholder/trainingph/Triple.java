package com.placeholder.trainingph;

import java.util.Objects;

/**
 * Created by joaop on 15/05/2016.
 */
 
public class Triple<E, E2>{
	
	private E Value;
	private boolean Consumed;
	private E2 Next;
	
	public Triple(){
		
	}
	
	public <E, E1, E2> Triple(E vE, E2 Origin){
			this.Value = vE;
			this.Consumed = false;
			this.Next = null;
	}
	
	public void append(Triple t1){
		if(t1.getNext()==null){
			t1.Next(this);
		}
		else{
			append(t1.getNext())
		}
	}
	
	public E getValue(){
		return value;
	}
	
	public E1 getIsConsumed(){		
		return Consumed;
	}
	
	public E2 getNext(){
		return Next;
	}
	
	public void consume(){
		Consumed = false;
		if(Next!=null){
			this.Value=this.Next.Value;
			this.Consumed = this.Next.Consumed;
			this.Next = this.Next.Next;
		}
	}
	
	private void setNext(Triple t){
		this.Next = t;
	}
	
}