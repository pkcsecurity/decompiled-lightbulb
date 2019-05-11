package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.AccountAccessor;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@KeepForSdk
@SafeParcelable.Class(
   creator = "GetServiceRequestCreator"
)
@SafeParcelable.Reserved({9})
public class GetServiceRequest extends AbstractSafeParcelable {

   public static final Creator<GetServiceRequest> CREATOR = new zzd();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int version;
   @SafeParcelable.Field(
      id = 2
   )
   private final int zzdf;
   @SafeParcelable.Field(
      id = 3
   )
   private int zzdg;
   @SafeParcelable.Field(
      id = 4
   )
   String zzdh;
   @SafeParcelable.Field(
      id = 5
   )
   IBinder zzdi;
   @SafeParcelable.Field(
      id = 6
   )
   Scope[] zzdj;
   @SafeParcelable.Field(
      id = 7
   )
   Bundle zzdk;
   @SafeParcelable.Field(
      id = 8
   )
   Account zzdl;
   @SafeParcelable.Field(
      id = 10
   )
   Feature[] zzdm;
   @SafeParcelable.Field(
      id = 11
   )
   Feature[] zzdn;
   @SafeParcelable.Field(
      id = 12
   )
   private boolean zzdo;


   public GetServiceRequest(int var1) {
      this.version = 4;
      this.zzdg = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
      this.zzdf = var1;
      this.zzdo = true;
   }

   @SafeParcelable.Constructor
   GetServiceRequest(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) int var2, 
      @SafeParcelable.Param(
         id = 3
      ) int var3, 
      @SafeParcelable.Param(
         id = 4
      ) String var4, 
      @SafeParcelable.Param(
         id = 5
      ) IBinder var5, 
      @SafeParcelable.Param(
         id = 6
      ) Scope[] var6, 
      @SafeParcelable.Param(
         id = 7
      ) Bundle var7, 
      @SafeParcelable.Param(
         id = 8
      ) Account var8, 
      @SafeParcelable.Param(
         id = 10
      ) Feature[] var9, 
      @SafeParcelable.Param(
         id = 11
      ) Feature[] var10, 
      @SafeParcelable.Param(
         id = 12
      ) boolean var11) {
      this.version = var1;
      this.zzdf = var2;
      this.zzdg = var3;
      if("com.google.android.gms".equals(var4)) {
         this.zzdh = "com.google.android.gms";
      } else {
         this.zzdh = var4;
      }

      if(var1 < 2) {
         Account var12 = null;
         if(var5 != null) {
            var12 = AccountAccessor.getAccountBinderSafe(IAccountAccessor.Stub.asInterface(var5));
         }

         this.zzdl = var12;
      } else {
         this.zzdi = var5;
         this.zzdl = var8;
      }

      this.zzdj = var6;
      this.zzdk = var7;
      this.zzdm = var9;
      this.zzdn = var10;
      this.zzdo = var11;
   }

   @KeepForSdk
   public Bundle getExtraArgs() {
      return this.zzdk;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.version);
      SafeParcelWriter.writeInt(var1, 2, this.zzdf);
      SafeParcelWriter.writeInt(var1, 3, this.zzdg);
      SafeParcelWriter.writeString(var1, 4, this.zzdh, false);
      SafeParcelWriter.writeIBinder(var1, 5, this.zzdi, false);
      SafeParcelWriter.writeTypedArray(var1, 6, this.zzdj, var2, false);
      SafeParcelWriter.writeBundle(var1, 7, this.zzdk, false);
      SafeParcelWriter.writeParcelable(var1, 8, this.zzdl, var2, false);
      SafeParcelWriter.writeTypedArray(var1, 10, this.zzdm, var2, false);
      SafeParcelWriter.writeTypedArray(var1, 11, this.zzdn, var2, false);
      SafeParcelWriter.writeBoolean(var1, 12, this.zzdo);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
