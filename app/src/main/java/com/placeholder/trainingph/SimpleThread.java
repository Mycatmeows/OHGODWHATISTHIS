package com.placeholder.trainingph;

import java.util.Objects;

/**
 * Created by joaop on 15/05/2016.
 */
public class SimpleThread extends Thread {

    private Object stopCondition;

    public static SimpleThread buildSimpleThread(){
        return new SimpleThread();
    }

    public void setStopCondition(Object obj){
        this.stopCondition = obj;
    }



}
