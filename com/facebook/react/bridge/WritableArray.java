package com.facebook.react.bridge;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;

public interface WritableArray extends ReadableArray {

   void pushArray(WritableArray var1);

   void pushBoolean(boolean var1);

   void pushDouble(double var1);

   void pushInt(int var1);

   void pushMap(WritableMap var1);

   void pushNull();

   void pushString(String var1);
}
