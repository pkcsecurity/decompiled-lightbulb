package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.zze;
import com.google.android.gms.common.zzf;
import com.google.android.gms.common.zzl;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import javax.annotation.Nullable;

@SafeParcelable.Class(
   creator = "GoogleCertificatesQueryCreator"
)
public final class zzk extends AbstractSafeParcelable {

   public static final Creator<zzk> CREATOR = new zzl();
   @SafeParcelable.Field(
      getter = "getAllowTestKeys",
      id = 3
   )
   private final boolean zzaa;
   @SafeParcelable.Field(
      getter = "getCallingPackage",
      id = 1
   )
   private final String zzy;
   @Nullable
   @SafeParcelable.Field(
      getter = "getCallingCertificateBinder",
      id = 2,
      type = "android.os.IBinder"
   )
   private final zze zzz;


   @SafeParcelable.Constructor
   zzk(
      @SafeParcelable.Param(
         id = 1
      ) String var1, @Nullable 
      @SafeParcelable.Param(
         id = 2
      ) IBinder var2, 
      @SafeParcelable.Param(
         id = 3
      ) boolean var3) {
      this.zzy = var1;
      this.zzz = zza(var2);
      this.zzaa = var3;
   }

   zzk(String var1, @Nullable zze var2, boolean var3) {
      this.zzy = var1;
      this.zzz = var2;
      this.zzaa = var3;
   }

   @Nullable
   private static zze zza(@Nullable IBinder var0) {
      if(var0 == null) {
         return null;
      } else {
         IObjectWrapper var2;
         try {
            var2 = com.google.android.gms.common.internal.zzj.zzb(var0).zzb();
         } catch (RemoteException var1) {
            Log.e("GoogleCertificatesQuery", "Could not unwrap certificate", var1);
            return null;
         }

         byte[] var3;
         if(var2 == null) {
            var3 = null;
         } else {
            var3 = (byte[])ObjectWrapper.a(var2);
         }

         if(var3 != null) {
            return new zzf(var3);
         } else {
            Log.e("GoogleCertificatesQuery", "Could not unwrap certificate");
            return null;
         }
      }
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 1, this.zzy, false);
      IBinder var3;
      if(this.zzz == null) {
         Log.w("GoogleCertificatesQuery", "certificate binder is null");
         var3 = null;
      } else {
         var3 = this.zzz.asBinder();
      }

      SafeParcelWriter.writeIBinder(var1, 2, var3, false);
      SafeParcelWriter.writeBoolean(var1, 3, this.zzaa);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
