package com.facebook.react.bridge;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import java.util.HashMap;

public interface ReadableMap {

   ReadableArray getArray(String var1);

   boolean getBoolean(String var1);

   double getDouble(String var1);

   Dynamic getDynamic(String var1);

   int getInt(String var1);

   ReadableMap getMap(String var1);

   String getString(String var1);

   ReadableType getType(String var1);

   boolean hasKey(String var1);

   boolean isNull(String var1);

   ReadableMapKeySetIterator keySetIterator();

   HashMap<String, Object> toHashMap();
}
