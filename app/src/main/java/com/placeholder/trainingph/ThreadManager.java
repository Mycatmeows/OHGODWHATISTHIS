package com.placeholder.trainingph;

import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.util.SparseArray;

import java.io.IOException;
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

    public void send(int value, BluetoothSocket socket){
        try{
            socket.getOutputStream().write(value);
        }
        catch (IOException e){
            Utils.sendGenericErrorMessage("ThreadManger.send()");
        }

    }

    public void read() {
        for (int i=0; i!=threads.size();i++) {
            E currentThread= threads.get(i);
            if(currentThread instanceof BTConnectionThread) {
                if (!((BTConnectionThread) currentThread).isBonded()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        ((BTConnectionThread) currentThread).getDevice().createBond();
                    } else System.out.println("PLACEHOLDER FOR API 19-");
                }
            }
            if(currentThread instanceof DialogThread){
                DialogThread castedThread = ((DialogThread) currentThread);
                while (castedThread.hasNext()) {
                    Triple T = castedThread.getAndUpdate();
                    messages.add(Message.fromIntValue((int)T.getValue(), castedThread.getDevice().getName()));
                }
            }
        }

    }

    public void write(){
        for (Message message : messages){
            String col = Utils.BluetoothMessageCodeToColumn(message.getCode());
            int val = message.getValue();
            String origin = message.getOrigin();
            //TODO: use the DAOs to write to DB 'ere
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
