package com.google.android.gms.internal.location;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.location.zzaj;
import com.google.android.gms.internal.location.zzal;
import com.google.android.gms.internal.location.zzm;
import com.google.android.gms.location.zzr;
import com.google.android.gms.location.zzs;

@SafeParcelable.Class(
   creator = "DeviceOrientationRequestUpdateDataCreator"
)
public final class zzo extends AbstractSafeParcelable {

   public static final Creator<zzo> CREATOR = new ho();
   @SafeParcelable.Field(
      defaultValueUnchecked = "DeviceOrientationRequestUpdateData.OPERATION_ADD",
      id = 1
   )
   private int a;
   @SafeParcelable.Field(
      defaultValueUnchecked = "null",
      id = 2
   )
   private zzm b;
   @SafeParcelable.Field(
      defaultValueUnchecked = "null",
      getter = "getDeviceOrientationListenerBinder",
      id = 3,
      type = "android.os.IBinder"
   )
   private zzr c;
   @SafeParcelable.Field(
      defaultValueUnchecked = "null",
      getter = "getFusedLocationProviderCallbackBinder",
      id = 4,
      type = "android.os.IBinder"
   )
   private zzaj d;


   @SafeParcelable.Constructor
   public zzo(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) zzm var2, 
      @SafeParcelable.Param(
         id = 3
      ) IBinder var3, 
      @SafeParcelable.Param(
         id = 4
      ) IBinder var4) {
      this.a = var1;
      this.b = var2;
      Object var5 = null;
      zzr var6;
      if(var3 == null) {
         var6 = null;
      } else {
         var6 = zzs.a(var3);
      }

      this.c = var6;
      Object var7;
      if(var4 == null) {
         var7 = var5;
      } else if(var4 == null) {
         var7 = var5;
      } else {
         IInterface var8 = var4.queryLocalInterface("com.google.android.gms.location.internal.IFusedLocationProviderCallback");
         if(var8 instanceof zzaj) {
            var7 = (zzaj)var8;
         } else {
            var7 = new zzal(var4);
         }
      }

      this.d = (zzaj)var7;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.a);
      SafeParcelWriter.writeParcelable(var1, 2, this.b, var2, false);
      zzr var4 = this.c;
      Object var5 = null;
      IBinder var6;
      if(var4 == null) {
         var6 = null;
      } else {
         var6 = this.c.asBinder();
      }

      SafeParcelWriter.writeIBinder(var1, 3, var6, false);
      if(this.d == null) {
         var6 = (IBinder)var5;
      } else {
         var6 = this.d.asBinder();
      }

      SafeParcelWriter.writeIBinder(var1, 4, var6, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
