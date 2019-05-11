package com.facebook.react.bridge;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;

public interface WritableMap extends ReadableMap {

   void merge(ReadableMap var1);

   void putArray(String var1, WritableArray var2);

   void putBoolean(String var1, boolean var2);

   void putDouble(String var1, double var2);

   void putInt(String var1, int var2);

   void putMap(String var1, WritableMap var2);

   void putNull(String var1);

   void putString(String var1, String var2);
}
