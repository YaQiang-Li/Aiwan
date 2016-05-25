package com.aiwan.ace.aiwan.Utils;

/**
 * Created by ACE on 2016/3/14.
 */
public class Security {
/*    public native byte[] DecryptMsg(String strMsg);
    public native byte[] EncryptMsg(String strMsg);

    public native byte[] EncryptPass(String strPass);
*/
    static{
        System.loadLibrary("security");
    }

    private static Security m_pInstance;

    public static Security getInstance() {
        synchronized (Security.class) {
            if (m_pInstance == null) {
                m_pInstance = new Security();
            }

            return m_pInstance;
        }
    }
}
