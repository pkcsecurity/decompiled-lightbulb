package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;

@KeepForSdk
public abstract class GmsClientSupervisor {

   private static final Object zzdp = new Object();
   private static GmsClientSupervisor zzdq;


   @KeepForSdk
   public static GmsClientSupervisor getInstance(Context param0) {
      // $FF: Couldn't be decompiled
   }

   @KeepForSdk
   public boolean bindService(ComponentName var1, ServiceConnection var2, String var3) {
      return this.zza(new GmsClientSupervisor.zza(var1, 129), var2, var3);
   }

   @KeepForSdk
   public boolean bindService(String var1, ServiceConnection var2, String var3) {
      return this.zza(new GmsClientSupervisor.zza(var1, 129), var2, var3);
   }

   @KeepForSdk
   public void unbindService(ComponentName var1, ServiceConnection var2, String var3) {
      this.zzb(new GmsClientSupervisor.zza(var1, 129), var2, var3);
   }

   @KeepForSdk
   public void unbindService(String var1, ServiceConnection var2, String var3) {
      this.zzb(new GmsClientSupervisor.zza(var1, 129), var2, var3);
   }

   public final void zza(String var1, String var2, int var3, ServiceConnection var4, String var5) {
      this.zzb(new GmsClientSupervisor.zza(var1, var2, var3), var4, var5);
   }

   protected abstract boolean zza(GmsClientSupervisor.zza var1, ServiceConnection var2, String var3);

   protected abstract void zzb(GmsClientSupervisor.zza var1, ServiceConnection var2, String var3);

   public static final class zza {

      private final ComponentName mComponentName;
      private final String zzdr;
      private final String zzds;
      private final int zzdt;


      public zza(ComponentName var1, int var2) {
         this.zzdr = null;
         this.zzds = null;
         this.mComponentName = (ComponentName)Preconditions.checkNotNull(var1);
         this.zzdt = 129;
      }

      public zza(String var1, int var2) {
         this.zzdr = Preconditions.checkNotEmpty(var1);
         this.zzds = "com.google.android.gms";
         this.mComponentName = null;
         this.zzdt = 129;
      }

      public zza(String var1, String var2, int var3) {
         this.zzdr = Preconditions.checkNotEmpty(var1);
         this.zzds = Preconditions.checkNotEmpty(var2);
         this.mComponentName = null;
         this.zzdt = var3;
      }

      public final boolean equals(Object var1) {
         if(this == var1) {
            return true;
         } else if(!(var1 instanceof GmsClientSupervisor.zza)) {
            return false;
         } else {
            GmsClientSupervisor.zza var2 = (GmsClientSupervisor.zza)var1;
            return Objects.equal(this.zzdr, var2.zzdr) && Objects.equal(this.zzds, var2.zzds) && Objects.equal(this.mComponentName, var2.mComponentName) && this.zzdt == var2.zzdt;
         }
      }

      public final ComponentName getComponentName() {
         return this.mComponentName;
      }

      public final String getPackage() {
         return this.zzds;
      }

      public final int hashCode() {
         return Objects.hashCode(new Object[]{this.zzdr, this.zzds, this.mComponentName, Integer.valueOf(this.zzdt)});
      }

      public final String toString() {
         return this.zzdr == null?this.mComponentName.flattenToString():this.zzdr;
      }

      public final Intent zzb(Context var1) {
         return this.zzdr != null?(new Intent(this.zzdr)).setPackage(this.zzds):(new Intent()).setComponent(this.mComponentName);
      }

      public final int zzq() {
         return this.zzdt;
      }
   }
}
