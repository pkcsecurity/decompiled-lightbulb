package com.google.android.gms.common.util;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.ArraySet;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@KeepForSdk
public final class CollectionUtils {

   @KeepForSdk
   public static boolean isEmpty(@Nullable Collection<?> var0) {
      return var0 == null?true:var0.isEmpty();
   }

   @Deprecated
   @KeepForSdk
   public static <T extends Object> List<T> listOf() {
      return Collections.emptyList();
   }

   @Deprecated
   @KeepForSdk
   public static <T extends Object> List<T> listOf(T var0) {
      return Collections.singletonList(var0);
   }

   @Deprecated
   @KeepForSdk
   public static <T extends Object> List<T> listOf(T ... var0) {
      switch(var0.length) {
      case 0:
         return listOf();
      case 1:
         return listOf(var0[0]);
      default:
         return Collections.unmodifiableList(Arrays.asList(var0));
      }
   }

   @KeepForSdk
   public static <K extends Object, V extends Object> Map<K, V> mapOf(K var0, V var1, K var2, V var3, K var4, V var5) {
      Map var6 = zzb(3, false);
      var6.put(var0, var1);
      var6.put(var2, var3);
      var6.put(var4, var5);
      return Collections.unmodifiableMap(var6);
   }

   @KeepForSdk
   public static <K extends Object, V extends Object> Map<K, V> mapOf(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9, K var10, V var11) {
      Map var12 = zzb(6, false);
      var12.put(var0, var1);
      var12.put(var2, var3);
      var12.put(var4, var5);
      var12.put(var6, var7);
      var12.put(var8, var9);
      var12.put(var10, var11);
      return Collections.unmodifiableMap(var12);
   }

   @KeepForSdk
   public static <K extends Object, V extends Object> Map<K, V> mapOfKeyValueArrays(K[] var0, V[] var1) {
      int var2;
      int var3;
      if(var0.length != var1.length) {
         var2 = var0.length;
         var3 = var1.length;
         StringBuilder var5 = new StringBuilder(66);
         var5.append("Key and values array lengths not equal: ");
         var5.append(var2);
         var5.append(" != ");
         var5.append(var3);
         throw new IllegalArgumentException(var5.toString());
      } else {
         var3 = var0.length;
         var2 = 0;
         switch(var3) {
         case 0:
            return Collections.emptyMap();
         case 1:
            return Collections.singletonMap(var0[0], var1[0]);
         default:
            Map var4;
            for(var4 = zzb(var0.length, false); var2 < var0.length; ++var2) {
               var4.put(var0[var2], var1[var2]);
            }

            return Collections.unmodifiableMap(var4);
         }
      }
   }

   @KeepForSdk
   public static <T extends Object> Set<T> mutableSetOfWithSize(int var0) {
      return (Set)(var0 == 0?new ArraySet():zza(var0, true));
   }

   @Deprecated
   @KeepForSdk
   public static <T extends Object> Set<T> setOf(T var0, T var1, T var2) {
      Set var3 = zza(3, false);
      var3.add(var0);
      var3.add(var1);
      var3.add(var2);
      return Collections.unmodifiableSet(var3);
   }

   @Deprecated
   @KeepForSdk
   public static <T extends Object> Set<T> setOf(T ... var0) {
      Object var1;
      Object var5;
      switch(var0.length) {
      case 0:
         return Collections.emptySet();
      case 1:
         return Collections.singleton(var0[0]);
      case 2:
         var1 = var0[0];
         var5 = var0[1];
         Set var7 = zza(2, false);
         var7.add(var1);
         var7.add(var5);
         return Collections.unmodifiableSet(var7);
      case 3:
         return setOf(var0[0], var0[1], var0[2]);
      case 4:
         var1 = var0[0];
         Object var2 = var0[1];
         Object var3 = var0[2];
         var5 = var0[3];
         Set var4 = zza(4, false);
         var4.add(var1);
         var4.add(var2);
         var4.add(var3);
         var4.add(var5);
         return Collections.unmodifiableSet(var4);
      default:
         Set var6 = zza(var0.length, false);
         Collections.addAll(var6, var0);
         return Collections.unmodifiableSet(var6);
      }
   }

   private static <T extends Object> Set<T> zza(int var0, boolean var1) {
      float var2;
      if(var1) {
         var2 = 0.75F;
      } else {
         var2 = 1.0F;
      }

      short var3;
      if(var1) {
         var3 = 128;
      } else {
         var3 = 256;
      }

      return (Set)(var0 <= var3?new ArraySet(var0):new HashSet(var0, var2));
   }

   private static <K extends Object, V extends Object> Map<K, V> zzb(int var0, boolean var1) {
      return (Map)(var0 <= 256?new ArrayMap(var0):new HashMap(var0, 1.0F));
   }
}
