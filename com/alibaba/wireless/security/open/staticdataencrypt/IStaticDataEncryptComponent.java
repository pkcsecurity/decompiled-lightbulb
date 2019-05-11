package com.alibaba.wireless.security.open.staticdataencrypt;

import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

public interface IStaticDataEncryptComponent extends IComponent {

   int ALGORITHM_MAX_NUMBER = 19;
   int OPEN_ENUM_CIPHER_AES128 = 16;
   int OPEN_ENUM_CIPHER_AES192 = 17;
   int OPEN_ENUM_CIPHER_AES256 = 18;
   int OPEN_ENUM_CIPHER_ARCFOUR = 3;


   byte[] staticBinarySafeDecrypt(int var1, String var2, byte[] var3, String var4) throws SecException;

   byte[] staticBinarySafeDecryptNoB64(int var1, String var2, byte[] var3, String var4) throws SecException;

   byte[] staticBinarySafeEncrypt(int var1, String var2, byte[] var3, String var4) throws SecException;

   byte[] staticBinarySafeEncryptNoB64(int var1, String var2, byte[] var3, String var4) throws SecException;

   String staticSafeDecrypt(int var1, String var2, String var3, String var4) throws SecException;

   String staticSafeEncrypt(int var1, String var2, String var3, String var4) throws SecException;
}
