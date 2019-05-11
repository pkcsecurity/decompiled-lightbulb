package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.location.zzaj;
import com.google.android.gms.internal.location.zzal;
import com.google.android.gms.internal.location.zzbd;
import com.google.android.gms.location.zzu;
import com.google.android.gms.location.zzv;
import com.google.android.gms.location.zzx;
import com.google.android.gms.location.zzy;

@SafeParcelable.Class(
   creator = "LocationRequestUpdateDataCreator"
)
@SafeParcelable.Reserved({1000})
public final class zzbf extends AbstractSafeParcelable {

   public static final Creator<zzbf> CREATOR = new hf();
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationRequestUpdateData.OPERATION_ADD",
      id = 1
   )
   private int a;
   @SafeParcelable.Field(
      defaultValueUnchecked = "null",
      id = 2
   )
   private zzbd b;
   @SafeParcelable.Field(
      defaultValueUnchecked = "null",
      getter = "getLocationListenerBinder",
      id = 3,
      type = "android.os.IBinder"
   )
   private zzx c;
   @SafeParcelable.Field(
      defaultValueUnchecked = "null",
      id = 4
   )
   private PendingIntent d;
   @SafeParcelable.Field(
      defaultValueUnchecked = "null",
      getter = "getLocationCallbackBinder",
      id = 5,
      type = "android.os.IBinder"
   )
   private zzu e;
   @SafeParcelable.Field(
      defaultValueUnchecked = "null",
      getter = "getFusedLocationProviderCallbackBinder",
      id = 6,
      type = "android.os.IBinder"
   )
   private zzaj f;


   @SafeParcelable.Constructor
   public zzbf(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) zzbd var2, 
      @SafeParcelable.Param(
         id = 3
      ) IBinder var3, 
      @SafeParcelable.Param(
         id = 4
      ) PendingIntent var4, 
      @SafeParcelable.Param(
         id = 5
      ) IBinder var5, 
      @SafeParcelable.Param(
         id = 6
      ) IBinder var6) {
      this.a = var1;
      this.b = var2;
      Object var7 = null;
      zzx var8;
      if(var3 == null) {
         var8 = null;
      } else {
         var8 = zzy.a(var3);
      }

      this.c = var8;
      this.d = var4;
      zzu var9;
      if(var5 == null) {
         var9 = null;
      } else {
         var9 = zzv.a(var5);
      }

      this.e = var9;
      Object var10;
      if(var6 == null) {
         var10 = var7;
      } else if(var6 == null) {
         var10 = var7;
      } else {
         IInterface var11 = var6.queryLocalInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
         if(var11 instanceof zzaj) {
            var10 = (zzaj)var11;
         } else {
            var10 = new zzal(var6);
         }
      }

      this.f = (zzaj)var10;
   }

   public static zzbf a(zzu var0, @Nullable zzaj var1) {
      IBinder var2 = var0.asBinder();
      IBinder var3;
      if(var1 != null) {
         var3 = var1.asBinder();
      } else {
         var3 = null;
      }

      return new zzbf(2, (zzbd)null, (IBinder)null, (PendingIntent)null, var2, var3);
   }

   public static zzbf a(zzx var0, @Nullable zzaj var1) {
      IBinder var2 = var0.asBinder();
      IBinder var3;
      if(var1 != null) {
         var3 = var1.asBinder();
      } else {
         var3 = null;
      }

      return new zzbf(2, (zzbd)null, var2, (PendingIntent)null, (IBinder)null, var3);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.a);
      SafeParcelWriter.writeParcelable(var1, 2, this.b, var2, false);
      zzx var4 = this.c;
      Object var5 = null;
      IBinder var6;
      if(var4 == null) {
         var6 = null;
      } else {
         var6 = this.c.asBinder();
      }

      SafeParcelWriter.writeIBinder(var1, 3, var6, false);
      SafeParcelWriter.writeParcelable(var1, 4, this.d, var2, false);
      if(this.e == null) {
         var6 = null;
      } else {
         var6 = this.e.asBinder();
      }

      SafeParcelWriter.writeIBinder(var1, 5, var6, false);
      if(this.f == null) {
         var6 = (IBinder)var5;
      } else {
         var6 = this.f.asBinder();
      }

      SafeParcelWriter.writeIBinder(var1, 6, var6, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
