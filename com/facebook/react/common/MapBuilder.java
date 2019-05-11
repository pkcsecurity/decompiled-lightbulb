package com.facebook.react.common;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder {

   public static <K extends Object, V extends Object> MapBuilder.Builder<K, V> builder() {
      return new MapBuilder.Builder(null);
   }

   public static <K extends Object, V extends Object> HashMap<K, V> newHashMap() {
      return new HashMap();
   }

   public static <K extends Object, V extends Object> Map<K, V> of() {
      return newHashMap();
   }

   public static <K extends Object, V extends Object> Map<K, V> of(K var0, V var1) {
      Map var2 = of();
      var2.put(var0, var1);
      return var2;
   }

   public static <K extends Object, V extends Object> Map<K, V> of(K var0, V var1, K var2, V var3) {
      Map var4 = of();
      var4.put(var0, var1);
      var4.put(var2, var3);
      return var4;
   }

   public static <K extends Object, V extends Object> Map<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      Map var6 = of();
      var6.put(var0, var1);
      var6.put(var2, var3);
      var6.put(var4, var5);
      return var6;
   }

   public static <K extends Object, V extends Object> Map<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      Map var8 = of();
      var8.put(var0, var1);
      var8.put(var2, var3);
      var8.put(var4, var5);
      var8.put(var6, var7);
      return var8;
   }

   public static <K extends Object, V extends Object> Map<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      Map var10 = of();
      var10.put(var0, var1);
      var10.put(var2, var3);
      var10.put(var4, var5);
      var10.put(var6, var7);
      var10.put(var8, var9);
      return var10;
   }

   public static <K extends Object, V extends Object> Map<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9, K var10, V var11) {
      Map var12 = of();
      var12.put(var0, var1);
      var12.put(var2, var3);
      var12.put(var4, var5);
      var12.put(var6, var7);
      var12.put(var8, var9);
      var12.put(var10, var11);
      return var12;
   }

   public static <K extends Object, V extends Object> Map<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9, K var10, V var11, K var12, V var13) {
      Map var14 = of();
      var14.put(var0, var1);
      var14.put(var2, var3);
      var14.put(var4, var5);
      var14.put(var6, var7);
      var14.put(var8, var9);
      var14.put(var10, var11);
      var14.put(var12, var13);
      return var14;
   }

   public static final class Builder<K extends Object, V extends Object> {

      private Map mMap;
      private boolean mUnderConstruction;


      private Builder() {
         this.mMap = MapBuilder.newHashMap();
         this.mUnderConstruction = true;
      }

      // $FF: synthetic method
      Builder(Object var1) {
         this();
      }

      public Map<K, V> build() {
         if(!this.mUnderConstruction) {
            throw new IllegalStateException("Underlying map has already been built");
         } else {
            this.mUnderConstruction = false;
            return this.mMap;
         }
      }

      public MapBuilder.Builder<K, V> put(K var1, V var2) {
         if(!this.mUnderConstruction) {
            throw new IllegalStateException("Underlying map has already been built");
         } else {
            this.mMap.put(var1, var2);
            return this;
         }
      }
   }
}
