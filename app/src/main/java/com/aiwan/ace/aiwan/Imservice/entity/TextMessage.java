package com.aiwan.ace.aiwan.Imservice.entity;

import com.aiwan.ace.aiwan.Config.DBConstant;
import com.aiwan.ace.aiwan.Config.MessageConstant;
import com.aiwan.ace.aiwan.Db.entity.MessageEntity;
import com.aiwan.ace.aiwan.Db.entity.PeerEntity;
import com.aiwan.ace.aiwan.Db.entity.UserEntity;
import com.aiwan.ace.aiwan.Imservice.support.SequenceNumberMaker;

import java.io.Serializable;

/**
 * Created by ACE on 2016/3/14.
 */
public class TextMessage extends MessageEntity implements Serializable {
    public TextMessage(){
        setMsgId(SequenceNumberMaker.getInstance().makelocalUniqueMsgId());
    }

    private TextMessage(MessageEntity entity){
        /**父类的id*/
        setId(entity.getId());
        setMsgId(entity.getMsgId());
        setFromId(entity.getFromId());
        setToId(entity.getToId());
        setSessionKey(entity.getSessionKey());
        setContent(entity.getContent());
        setMsgType(entity.getMsgType());
        setDisplayType(entity.getDisplayType());
        setStatus(entity.getStatus());
        setCreated(entity.getCreated());
        setUpdated(entity.getUpdated());
    }

    public static TextMessage parseFromNet(MessageEntity entity){
        TextMessage textMessage = new TextMessage(entity);
        textMessage.setStatus(MessageConstant.MSG_SUCCESS);
        textMessage.setDisplayType(DBConstant.SHOW_ORIGIN_TEXT_TYPE);
        return textMessage;
    }

    public static TextMessage parseFromDB(MessageEntity entity){
        if(entity.getDisplayType()!=DBConstant.SHOW_ORIGIN_TEXT_TYPE){
            throw new RuntimeException("#TextMessage# parseFromDB,not SHOW_ORIGIN_TEXT_TYPE");
        }
        TextMessage textMessage = new TextMessage(entity);
        return textMessage;
    }

    public static TextMessage buildForSend(String content, UserEntity fromUser, PeerEntity peerEntity){
        TextMessage textMessage = new TextMessage();
        int nowTime = (int) (System.currentTimeMillis() / 1000);
        textMessage.setFromId(fromUser.getPeerId());
        textMessage.setToId(peerEntity.getPeerId());
        textMessage.setUpdated(nowTime);
        textMessage.setCreated(nowTime);
        textMessage.setDisplayType(DBConstant.SHOW_ORIGIN_TEXT_TYPE);
        textMessage.setGIfEmo(true);
        int peerType = peerEntity.getType();
        int msgType = peerType == DBConstant.SESSION_TYPE_GROUP ? DBConstant.MSG_TYPE_GROUP_TEXT
                : DBConstant.MSG_TYPE_SINGLE_TEXT;
        textMessage.setMsgType(msgType);
        textMessage.setStatus(MessageConstant.MSG_SENDING);
        // 内容的设定
        textMessage.setContent(content);
        textMessage.buildSessionKey(true);
        return textMessage;
    }


    /**
     * Not-null value.
     * DB的时候需要
     */
    @Override
    public String getContent() {
        return getContent();
    }

    @Override
    public byte[] getSendContent() {
        return getContent().getBytes();
       /*  try {
            /** 加密
            String sendContent =new String(com.mogujie.tt.Security.getInstance().EncryptMsg(content));
            return sendContent.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;*/
    }
}
