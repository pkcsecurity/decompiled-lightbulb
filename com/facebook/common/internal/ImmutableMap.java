package com.facebook.common.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ImmutableMap<K extends Object, V extends Object> extends HashMap<K, V> {

   private ImmutableMap(Map<? extends K, ? extends V> var1) {
      super(var1);
   }

   public static <K extends Object, V extends Object> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> var0) {
      return new ImmutableMap(var0);
   }

   public static <K extends Object, V extends Object> Map<K, V> of() {
      return Collections.unmodifiableMap(new HashMap());
   }

   public static <K extends Object, V extends Object> Map<K, V> of(K var0, V var1) {
      HashMap var2 = new HashMap();
      var2.put(var0, var1);
      return Collections.unmodifiableMap(var2);
   }

   public static <K extends Object, V extends Object> Map<K, V> of(K var0, V var1, K var2, V var3) {
      HashMap var4 = new HashMap();
      var4.put(var0, var1);
      var4.put(var2, var3);
      return Collections.unmodifiableMap(var4);
   }

   public static <K extends Object, V extends Object> Map<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      HashMap var6 = new HashMap();
      var6.put(var0, var1);
      var6.put(var2, var3);
      var6.put(var4, var5);
      return Collections.unmodifiableMap(var6);
   }

   public static <K extends Object, V extends Object> Map<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      HashMap var8 = new HashMap();
      var8.put(var0, var1);
      var8.put(var2, var3);
      var8.put(var4, var5);
      var8.put(var6, var7);
      return Collections.unmodifiableMap(var8);
   }

   public static <K extends Object, V extends Object> Map<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      HashMap var10 = new HashMap();
      var10.put(var0, var1);
      var10.put(var2, var3);
      var10.put(var4, var5);
      var10.put(var6, var7);
      var10.put(var8, var9);
      return Collections.unmodifiableMap(var10);
   }

   public static <K extends Object, V extends Object> Map<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9, K var10, V var11) {
      HashMap var12 = new HashMap();
      var12.put(var0, var1);
      var12.put(var2, var3);
      var12.put(var4, var5);
      var12.put(var6, var7);
      var12.put(var8, var9);
      var12.put(var10, var11);
      return Collections.unmodifiableMap(var12);
   }
}
