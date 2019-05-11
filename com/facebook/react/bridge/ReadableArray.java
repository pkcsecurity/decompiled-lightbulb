package com.facebook.react.bridge;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import java.util.ArrayList;

public interface ReadableArray {

   ReadableArray getArray(int var1);

   boolean getBoolean(int var1);

   double getDouble(int var1);

   Dynamic getDynamic(int var1);

   int getInt(int var1);

   ReadableMap getMap(int var1);

   String getString(int var1);

   ReadableType getType(int var1);

   boolean isNull(int var1);

   int size();

   ArrayList<Object> toArrayList();
}
