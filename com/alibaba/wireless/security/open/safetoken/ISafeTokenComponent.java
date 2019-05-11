package com.alibaba.wireless.security.open.safetoken;

import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

public interface ISafeTokenComponent extends IComponent {

   byte[] decryptWithToken(String var1, byte[] var2, int var3) throws SecException;

   byte[] encryptWithToken(String var1, byte[] var2, int var3) throws SecException;

   byte[] getOtp(String var1, int var2, String[] var3, byte[][] var4) throws SecException;

   boolean isTokenExisted(String var1) throws SecException;

   void removeToken(String var1) throws SecException;

   boolean saveToken(String var1, String var2, String var3, int var4) throws SecException;

   String signWithToken(String var1, byte[] var2, int var3) throws SecException;
}
