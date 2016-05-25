package com.aiwan.ace.aiwan.Imservice.event;

import com.aiwan.ace.aiwan.Imservice.entity.UnreadEntity;

/**
 * Created by ACE on 2016/3/28.
 */
public class UnreadEvent {
    public UnreadEntity entity;
    public Event event;

    public UnreadEvent(){}
    public UnreadEvent(Event e){
        this.event = e;
    }

    public enum Event {
        UNREAD_MSG_LIST_OK,
        UNREAD_MSG_RECEIVED,

        SESSION_READED_UNREAD_MSG
    }
}
