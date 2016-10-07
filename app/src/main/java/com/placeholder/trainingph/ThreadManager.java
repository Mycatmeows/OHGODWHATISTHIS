package com.placeholder.trainingph;

import android.os.Build;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThreadManager<E> {

    private List<E> threads;
    private List<Message> messages;
    private HashMap<String, Integer> valueMap;
    private boolean hasNewData = false;

    public ThreadManager(){
        threads=new ArrayList<E>();
    }

    public void addThread(E newThread){
        threads.add(newThread);
    }

    public void send(int value){
    }

    public void read() {
        for (int i=0; i!=threads.size();i++) {
            E currentThread= threads.get(i);
            if(currentThread instanceof BTConnectionThread){
                if(!((BTConnectionThread)currentThread).isBonded()){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        ((BTConnectionThread)currentThread).getDevice().createBond();
                    }
                    else System.out.println("PLACEHOLDER FOR API 19-");
                }
            }
            else{
                int value = (int)((DialogThread)currentThread).getCurrent().getValue();
                String deviceName = ((DialogThread)currentThread).getDevice().getName();
                messages.add(Message.fromIntValue(value,deviceName));
            }
        }
    }

    public void updateValue(String key, int value){
        valueMap.put(key,value);
        if(!hasNewData)hasNewData=true;
    }

    public Message peek(){
        return messages.get(0);
    }

    public Message poll(){
        return messages.remove(0);
    }

    public boolean hasStarted(){
        return messages.size()>0;
    }

    public boolean hasNewData(){
        return hasNewData;
    }

}
