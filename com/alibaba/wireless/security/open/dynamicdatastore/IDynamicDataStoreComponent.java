package com.alibaba.wireless.security.open.dynamicdatastore;

import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

public interface IDynamicDataStoreComponent extends IComponent {

   boolean getBoolean(String var1) throws SecException;

   byte[] getByteArray(String var1) throws SecException;

   byte[] getByteArrayDDp(String var1) throws SecException;

   byte[] getByteArrayDDpEx(String var1, int var2) throws SecException;

   float getFloat(String var1) throws SecException;

   int getInt(String var1) throws SecException;

   long getLong(String var1) throws SecException;

   String getString(String var1) throws SecException;

   String getStringDDp(String var1) throws SecException;

   String getStringDDpEx(String var1, int var2) throws SecException;

   int putBoolean(String var1, boolean var2) throws SecException;

   int putByteArray(String var1, byte[] var2) throws SecException;

   int putByteArrayDDp(String var1, byte[] var2) throws SecException;

   boolean putByteArrayDDpEx(String var1, byte[] var2, int var3) throws SecException;

   int putFloat(String var1, float var2) throws SecException;

   int putInt(String var1, int var2) throws SecException;

   int putLong(String var1, long var2) throws SecException;

   int putString(String var1, String var2) throws SecException;

   int putStringDDp(String var1, String var2) throws SecException;

   boolean putStringDDpEx(String var1, String var2, int var3) throws SecException;

   void removeBoolean(String var1) throws SecException;

   void removeByteArray(String var1) throws SecException;

   void removeByteArrayDDp(String var1) throws SecException;

   void removeByteArrayDDpEx(String var1, int var2) throws SecException;

   void removeFloat(String var1) throws SecException;

   void removeInt(String var1) throws SecException;

   void removeLong(String var1) throws SecException;

   void removeString(String var1) throws SecException;

   void removeStringDDp(String var1) throws SecException;

   void removeStringDDpEx(String var1, int var2) throws SecException;
}
