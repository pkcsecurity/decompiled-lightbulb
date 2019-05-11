package com.alibaba.wireless.security.open.dynamicdataencrypt;

import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

public interface IDynamicDataEncryptComponent extends IComponent {

   String dynamicDecrypt(String var1) throws SecException;

   byte[] dynamicDecryptByteArray(byte[] var1) throws SecException;

   byte[] dynamicDecryptByteArrayDDp(byte[] var1) throws SecException;

   String dynamicDecryptDDp(String var1) throws SecException;

   String dynamicEncrypt(String var1) throws SecException;

   byte[] dynamicEncryptByteArray(byte[] var1) throws SecException;

   byte[] dynamicEncryptByteArrayDDp(byte[] var1) throws SecException;

   String dynamicEncryptDDp(String var1) throws SecException;
}
