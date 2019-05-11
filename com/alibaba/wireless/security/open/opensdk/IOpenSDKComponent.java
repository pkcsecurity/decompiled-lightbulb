package com.alibaba.wireless.security.open.opensdk;

import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

public interface IOpenSDKComponent extends IComponent {

   byte[] OPEN_BIZ_IID = new byte[]{(byte)0, (byte)2};
   byte[] OPEN_BIZ_TID = new byte[]{(byte)0, (byte)3};
   byte[] OPEN_BIZ_UID = new byte[]{(byte)0, (byte)1};


   Long analyzeOpenId(String var1, String var2, String var3, byte[] var4, String var5) throws SecException;
}
