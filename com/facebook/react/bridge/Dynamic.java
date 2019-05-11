package com.facebook.react.bridge;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

public interface Dynamic {

   ReadableArray asArray();

   boolean asBoolean();

   double asDouble();

   int asInt();

   ReadableMap asMap();

   String asString();

   ReadableType getType();

   boolean isNull();

   void recycle();
}
