package com.google.android.gms.common;

import com.google.android.gms.common.zze;
import java.lang.ref.WeakReference;

abstract class zzg extends zze {

   private static final WeakReference<byte[]> zzw = new WeakReference((Object)null);
   private WeakReference<byte[]> zzv;


   zzg(byte[] var1) {
      super(var1);
      this.zzv = zzw;
   }

   final byte[] getBytes() {
      // $FF: Couldn't be decompiled
   }

   protected abstract byte[] zzd();
}
