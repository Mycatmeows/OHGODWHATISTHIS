package com.placeholder.trainingph;


public class ConsumerThread implements Runnable {

    private ThreadManager threadManager;

    public ConsumerThread(ThreadManager manager){
        this.threadManager = manager;
    }

    @Override
    public void run() {
        while(threadManager.peek()!=null) {
            Message current = threadManager.poll();
            if(current.getOrigin() == BluetoothUtil.DEVICE_NAME){//Intended for this device
                String Column = Utils.BluetoothMessageCodeToColumn(current.getCode());
                threadManager.updateValue(Column, current.getCode());
            }
        }
    }


}
