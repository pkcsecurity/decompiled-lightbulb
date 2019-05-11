package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzb implements Creator<Feature> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var3 = SafeParcelReader.validateObjectHeader(var1);
      String var7 = null;
      int var2 = 0;
      long var5 = -1L;

      while(var1.dataPosition() < var3) {
         int var4 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var4)) {
         case 1:
            var7 = SafeParcelReader.createString(var1, var4);
            break;
         case 2:
            var2 = SafeParcelReader.readInt(var1, var4);
            break;
         case 3:
            var5 = SafeParcelReader.readLong(var1, var4);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var4);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var3);
      return new Feature(var7, var2, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new Feature[var1];
   }
}
