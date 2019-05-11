package com.google.android.gms.common.internal;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

@KeepForSdk
public abstract class DowngradeableSafeParcel extends AbstractSafeParcelable implements ReflectedParcelable {

   private static final Object zzdb = new Object();
   private static ClassLoader zzdc;
   private static Integer zzdd;
   private boolean zzde = false;


   @KeepForSdk
   protected static boolean canUnparcelSafely(String var0) {
      zzp();
      return true;
   }

   @KeepForSdk
   protected static Integer getUnparcelClientVersion() {
      // $FF: Couldn't be decompiled
   }

   private static ClassLoader zzp() {
      // $FF: Couldn't be decompiled
   }

   @KeepForSdk
   protected abstract boolean prepareForClientVersion(int var1);

   @KeepForSdk
   public void setShouldDowngrade(boolean var1) {
      this.zzde = var1;
   }

   @KeepForSdk
   protected boolean shouldDowngrade() {
      return this.zzde;
   }
}
