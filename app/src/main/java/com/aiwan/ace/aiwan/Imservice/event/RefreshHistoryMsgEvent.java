package com.aiwan.ace.aiwan.Imservice.event;

import com.aiwan.ace.aiwan.Db.entity.MessageEntity;

import java.util.List;

/**
 * Created by ACE on 2016/4/5.
 */
public class RefreshHistoryMsgEvent {
    public int pullTimes;
    public int lastMsgId;
    public int count;
    public List<MessageEntity> listMsg;
    public int peerId;
    public int peerType;
    public String sessionKey;

    public RefreshHistoryMsgEvent(){}
}
