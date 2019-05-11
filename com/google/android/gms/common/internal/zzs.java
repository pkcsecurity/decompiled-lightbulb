package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzs implements Creator<zzr> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var3 = SafeParcelReader.validateObjectHeader(var1);
      int var2 = 0;

      while(var1.dataPosition() < var3) {
         int var4 = SafeParcelReader.readHeader(var1);
         if(SafeParcelReader.getFieldId(var4) != 1) {
            SafeParcelReader.skipUnknownField(var1, var4);
         } else {
            var2 = SafeParcelReader.readInt(var1, var4);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var3);
      return new zzr(var2);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzr[var1];
   }
}
