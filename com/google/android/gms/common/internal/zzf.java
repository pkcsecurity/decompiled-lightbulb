package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import com.google.android.gms.common.internal.GmsClientSupervisor;
import com.google.android.gms.common.internal.zze;
import java.util.HashSet;
import java.util.Set;

final class zzf implements ServiceConnection {

   private ComponentName mComponentName;
   private int mState;
   private IBinder zzcy;
   private final Set<ServiceConnection> zzdz;
   private boolean zzea;
   private final GmsClientSupervisor.zza zzeb;
   // $FF: synthetic field
   private final zze zzec;


   public zzf(zze var1, GmsClientSupervisor.zza var2) {
      this.zzec = var1;
      this.zzeb = var2;
      this.zzdz = new HashSet();
      this.mState = 2;
   }

   public final IBinder getBinder() {
      return this.zzcy;
   }

   public final ComponentName getComponentName() {
      return this.mComponentName;
   }

   public final int getState() {
      return this.mState;
   }

   public final boolean isBound() {
      return this.zzea;
   }

   public final void onServiceConnected(ComponentName param1, IBinder param2) {
      // $FF: Couldn't be decompiled
   }

   public final void onServiceDisconnected(ComponentName param1) {
      // $FF: Couldn't be decompiled
   }

   public final void zza(ServiceConnection var1, String var2) {
      zze.zzd(this.zzec);
      zze.zzc(this.zzec);
      this.zzeb.zzb(zze.zzc(this.zzec));
      this.zzdz.add(var1);
   }

   public final boolean zza(ServiceConnection var1) {
      return this.zzdz.contains(var1);
   }

   public final void zzb(ServiceConnection var1, String var2) {
      zze.zzd(this.zzec);
      zze.zzc(this.zzec);
      this.zzdz.remove(var1);
   }

   public final void zze(String var1) {
      this.mState = 3;
      this.zzea = zze.zzd(this.zzec).zza(zze.zzc(this.zzec), var1, this.zzeb.zzb(zze.zzc(this.zzec)), this, this.zzeb.zzq());
      if(this.zzea) {
         Message var3 = zze.zzb(this.zzec).obtainMessage(1, this.zzeb);
         zze.zzb(this.zzec).sendMessageDelayed(var3, zze.zze(this.zzec));
      } else {
         this.mState = 2;

         try {
            zze.zzd(this.zzec).unbindService(zze.zzc(this.zzec), this);
         } catch (IllegalArgumentException var2) {
            ;
         }
      }
   }

   public final void zzf(String var1) {
      zze.zzb(this.zzec).removeMessages(1, this.zzeb);
      zze.zzd(this.zzec).unbindService(zze.zzc(this.zzec), this);
      this.zzea = false;
      this.mState = 2;
   }

   public final boolean zzr() {
      return this.zzdz.isEmpty();
   }
}
