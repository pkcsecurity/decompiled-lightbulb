package com.google.android.gms.common.config;

import android.content.Context;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.config.zza;
import com.google.android.gms.common.config.zzb;
import com.google.android.gms.common.config.zzc;
import com.google.android.gms.common.config.zzd;
import com.google.android.gms.common.config.zze;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.HashSet;
import javax.annotation.concurrent.GuardedBy;

@KeepForSdk
public abstract class GservicesValue<T extends Object> {

   private static final Object sLock = new Object();
   private static GservicesValue.zza zzbl;
   private static int zzbm;
   private static Context zzbn;
   @GuardedBy
   private static HashSet<String> zzbo;
   protected final String mKey;
   protected final T zzbp;
   private T zzbq = null;


   protected GservicesValue(String var1, T var2) {
      this.mKey = var1;
      this.zzbp = var2;
   }

   @KeepForSdk
   public static boolean isInitialized() {
      // $FF: Couldn't be decompiled
   }

   @KeepForSdk
   public static GservicesValue<Float> value(String var0, Float var1) {
      return new zzd(var0, var1);
   }

   @KeepForSdk
   public static GservicesValue<Integer> value(String var0, Integer var1) {
      return new zzc(var0, var1);
   }

   @KeepForSdk
   public static GservicesValue<Long> value(String var0, Long var1) {
      return new zzb(var0, var1);
   }

   @KeepForSdk
   public static GservicesValue<String> value(String var0, String var1) {
      return new zze(var0, var1);
   }

   @KeepForSdk
   public static GservicesValue<Boolean> value(String var0, boolean var1) {
      return new zza(var0, Boolean.valueOf(var1));
   }

   private static boolean zzi() {
      // $FF: Couldn't be decompiled
   }

   @KeepForSdk
   public final T get() {
      // $FF: Couldn't be decompiled
   }

   @Deprecated
   @KeepForSdk
   public final T getBinderSafe() {
      return this.get();
   }

   @KeepForSdk
   @VisibleForTesting
   public void override(T param1) {
      // $FF: Couldn't be decompiled
   }

   @KeepForSdk
   @VisibleForTesting
   public void resetOverride() {
      this.zzbq = null;
   }

   protected abstract T zzd(String var1);

   interface zza {

      Long getLong(String var1, Long var2);

      String getString(String var1, String var2);

      Boolean zza(String var1, Boolean var2);

      Float zza(String var1, Float var2);

      Integer zza(String var1, Integer var2);
   }
}
