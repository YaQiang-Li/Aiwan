package com.aiwan.ace.aiwan.Imservice.event;

/**
 * Created by ACE on 2016/3/10.
 */
public class PriorityEvent {

    public Object object;
    public Event event;

    public enum  Event{
        MSG_RECEIVED_MESSAGE
    }
}
