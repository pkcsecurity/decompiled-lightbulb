package com.google.android.gms.common;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

abstract class zze extends com.google.android.gms.common.internal.zzj {

   private int zzt;


   protected zze(byte[] var1) {
      boolean var2;
      if(var1.length == 25) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2);
      this.zzt = Arrays.hashCode(var1);
   }

   protected static byte[] zza(String var0) {
      try {
         byte[] var2 = var0.getBytes("ISO-8859-1");
         return var2;
      } catch (UnsupportedEncodingException var1) {
         throw new AssertionError(var1);
      }
   }

   public boolean equals(Object param1) {
      // $FF: Couldn't be decompiled
   }

   abstract byte[] getBytes();

   public int hashCode() {
      return this.zzt;
   }

   public final IObjectWrapper zzb() {
      return ObjectWrapper.a((Object)this.getBytes());
   }

   public final int zzc() {
      return this.hashCode();
   }
}
