package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.server.converter.StringToIntConverter;

public final class zad implements Creator<StringToIntConverter.zaa> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var4 = SafeParcelReader.validateObjectHeader(var1);
      int var3 = 0;
      String var6 = null;
      int var2 = 0;

      while(var1.dataPosition() < var4) {
         int var5 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var5)) {
         case 1:
            var3 = SafeParcelReader.readInt(var1, var5);
            break;
         case 2:
            var6 = SafeParcelReader.createString(var1, var5);
            break;
         case 3:
            var2 = SafeParcelReader.readInt(var1, var5);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var5);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var4);
      return new StringToIntConverter.zaa(var3, var6, var2);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new StringToIntConverter.zaa[var1];
   }
}
