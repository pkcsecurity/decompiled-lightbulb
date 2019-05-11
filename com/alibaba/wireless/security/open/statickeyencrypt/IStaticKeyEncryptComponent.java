package com.alibaba.wireless.security.open.statickeyencrypt;

import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

public interface IStaticKeyEncryptComponent extends IComponent {

   int OPEN_ALGORITHM_MAX_NUMBER = 20;
   int OPEN_CIPHER_AES128 = 16;
   int OPEN_CIPHER_AES192 = 17;
   int OPEN_CIPHER_AES256 = 18;
   int OPEN_NO_SUCH_ITEM = 2;
   int OPEN_OVERRIDE_SUCCESS = 2;
   int OPEN_REMOVE_FAILED = 0;
   int OPEN_REMOVE_SUCCESS = 1;
   int OPEN_SAVE_FAILED = 0;
   int OPEN_SAVE_SUCCESS = 1;


   byte[] decrypt(int var1, String var2, byte[] var3) throws SecException;

   byte[] encrypt(int var1, String var2, byte[] var3) throws SecException;

   byte[] encryptSecretData(int var1, String var2, String var3) throws SecException;

   boolean isSecretExist(String var1) throws SecException;

   int removeSecret(String var1) throws SecException;

   int saveSecret(String var1, byte[] var2) throws SecException;
}
