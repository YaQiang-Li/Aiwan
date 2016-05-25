package com.aiwan.ace.aiwan.Protobuf.base;

import com.aiwan.ace.aiwan.Config.SysConstant;
import com.aiwan.ace.aiwan.Imservice.support.SequenceNumberMaker;
import com.aiwan.ace.aiwan.Utils.Logger;

/**
 * Created by ACE on 2016/3/28.
 */
public class DefaultHeader extends Header{
    private static final String Tag = "DefaultHeader" ;
    private Logger logger = Logger.getLogger(DefaultHeader.class);

    public DefaultHeader(int serviceId, int commandId) {
        setVersion((short) SysConstant.PROTOCOL_VERSION);
        setFlag((short) SysConstant.PROTOCOL_FLAG);
        setServiceId((short)serviceId);
        setCommandId((short)commandId);
        short seqNo = SequenceNumberMaker.getInstance().make();
        setSeqnum(seqNo);
        setReserved((short)SysConstant.PROTOCOL_RESERVED);

        logger.d(Tag, "packet#construct Default Header -> serviceId:%d, commandId:%d, seqNo:%d", serviceId, commandId, seqNo);
    }
}
