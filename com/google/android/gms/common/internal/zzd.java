package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.GetServiceRequest;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzd implements Creator<GetServiceRequest> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var5 = SafeParcelReader.validateObjectHeader(var1);
      String var14 = null;
      Object var8 = var14;
      Object var9 = var14;
      Object var10 = var14;
      Object var11 = var14;
      Object var12 = var14;
      Object var13 = var14;
      int var4 = 0;
      int var3 = 0;
      int var2 = 0;
      boolean var7 = false;

      while(var1.dataPosition() < var5) {
         int var6 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var6)) {
         case 1:
            var4 = SafeParcelReader.readInt(var1, var6);
            break;
         case 2:
            var3 = SafeParcelReader.readInt(var1, var6);
            break;
         case 3:
            var2 = SafeParcelReader.readInt(var1, var6);
            break;
         case 4:
            var14 = SafeParcelReader.createString(var1, var6);
            break;
         case 5:
            var8 = SafeParcelReader.readIBinder(var1, var6);
            break;
         case 6:
            var9 = (Scope[])SafeParcelReader.createTypedArray(var1, var6, Scope.CREATOR);
            break;
         case 7:
            var10 = SafeParcelReader.createBundle(var1, var6);
            break;
         case 8:
            var11 = (Account)SafeParcelReader.createParcelable(var1, var6, Account.CREATOR);
            break;
         case 9:
         default:
            SafeParcelReader.skipUnknownField(var1, var6);
            break;
         case 10:
            var12 = (Feature[])SafeParcelReader.createTypedArray(var1, var6, Feature.CREATOR);
            break;
         case 11:
            var13 = (Feature[])SafeParcelReader.createTypedArray(var1, var6, Feature.CREATOR);
            break;
         case 12:
            var7 = SafeParcelReader.readBoolean(var1, var6);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var5);
      return new GetServiceRequest(var4, var3, var2, var14, (IBinder)var8, (Scope[])var9, (Bundle)var10, (Account)var11, (Feature[])var12, (Feature[])var13, var7);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new GetServiceRequest[var1];
   }
}
