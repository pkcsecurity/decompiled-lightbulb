package com.facebook.react.bridge;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.DynamicFromArray;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaOnlyArray implements ReadableArray, WritableArray {

   private final List mBackingList;


   public JavaOnlyArray() {
      this.mBackingList = new ArrayList();
   }

   private JavaOnlyArray(List var1) {
      this.mBackingList = new ArrayList(var1);
   }

   private JavaOnlyArray(Object ... var1) {
      this.mBackingList = Arrays.asList(var1);
   }

   public static JavaOnlyArray from(List var0) {
      return new JavaOnlyArray(var0);
   }

   public static JavaOnlyArray of(Object ... var0) {
      return new JavaOnlyArray(var0);
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            JavaOnlyArray var2 = (JavaOnlyArray)var1;
            if(this.mBackingList != null) {
               if(!this.mBackingList.equals(var2.mBackingList)) {
                  return false;
               }
            } else if(var2.mBackingList != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public JavaOnlyArray getArray(int var1) {
      return (JavaOnlyArray)this.mBackingList.get(var1);
   }

   public boolean getBoolean(int var1) {
      return ((Boolean)this.mBackingList.get(var1)).booleanValue();
   }

   public double getDouble(int var1) {
      return ((Double)this.mBackingList.get(var1)).doubleValue();
   }

   public Dynamic getDynamic(int var1) {
      return DynamicFromArray.create(this, var1);
   }

   public int getInt(int var1) {
      return ((Integer)this.mBackingList.get(var1)).intValue();
   }

   public JavaOnlyMap getMap(int var1) {
      return (JavaOnlyMap)this.mBackingList.get(var1);
   }

   public String getString(int var1) {
      return (String)this.mBackingList.get(var1);
   }

   public ReadableType getType(int var1) {
      Object var2 = this.mBackingList.get(var1);
      return var2 == null?ReadableType.Null:(var2 instanceof Boolean?ReadableType.Boolean:(!(var2 instanceof Double) && !(var2 instanceof Float) && !(var2 instanceof Integer)?(var2 instanceof String?ReadableType.String:(var2 instanceof ReadableArray?ReadableType.Array:(var2 instanceof ReadableMap?ReadableType.Map:null))):ReadableType.Number));
   }

   public int hashCode() {
      return this.mBackingList != null?this.mBackingList.hashCode():0;
   }

   public boolean isNull(int var1) {
      return this.mBackingList.get(var1) == null;
   }

   public void pushArray(WritableArray var1) {
      this.mBackingList.add(var1);
   }

   public void pushBoolean(boolean var1) {
      this.mBackingList.add(Boolean.valueOf(var1));
   }

   public void pushDouble(double var1) {
      this.mBackingList.add(Double.valueOf(var1));
   }

   public void pushInt(int var1) {
      this.mBackingList.add(Integer.valueOf(var1));
   }

   public void pushMap(WritableMap var1) {
      this.mBackingList.add(var1);
   }

   public void pushNull() {
      this.mBackingList.add((Object)null);
   }

   public void pushString(String var1) {
      this.mBackingList.add(var1);
   }

   public int size() {
      return this.mBackingList.size();
   }

   public ArrayList<Object> toArrayList() {
      return new ArrayList(this.mBackingList);
   }

   public String toString() {
      return this.mBackingList.toString();
   }
}
