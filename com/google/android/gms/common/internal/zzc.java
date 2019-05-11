package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzc implements Creator<zzb> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      Bundle var5 = null;
      Feature[] var4 = null;

      while(var1.dataPosition() < var2) {
         int var3 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var3)) {
         case 1:
            var5 = SafeParcelReader.createBundle(var1, var3);
            break;
         case 2:
            var4 = (Feature[])SafeParcelReader.createTypedArray(var1, var3, Feature.CREATOR);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var3);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzb(var5, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzb[var1];
   }
}
