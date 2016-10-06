package com.placeholder.trainingph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmontez on 06-10-2016.
 */
public class DialogThreadManager {

    private List<DialogThread> threads;
    private List<Message> messages;

    public DialogThreadManager(){
        threads=new ArrayList<DialogThread>();
    }

    public void addThread(DialogThread DT){
       threads.add(DT);
    }

    public void send(int value){
    }

    public void read() {
        for (DialogThread dt : threads) {
            messages.add(Message.fromIntValue((int) dt.getAndUpdate().getValue()));
        }
    }

    public Message peek(){
        return messages.get(0);
    }

    public Message poll(){
        return messages.remove(0);
    }

}
