package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import java.util.ArrayList;

public final class zac implements Creator<StringToIntConverter> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var3 = SafeParcelReader.validateObjectHeader(var1);
      int var2 = 0;
      ArrayList var5 = null;

      while(var1.dataPosition() < var3) {
         int var4 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var4)) {
         case 1:
            var2 = SafeParcelReader.readInt(var1, var4);
            break;
         case 2:
            var5 = SafeParcelReader.createTypedList(var1, var4, StringToIntConverter.zaa.CREATOR);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var4);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var3);
      return new StringToIntConverter(var2, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new StringToIntConverter[var1];
   }
}
