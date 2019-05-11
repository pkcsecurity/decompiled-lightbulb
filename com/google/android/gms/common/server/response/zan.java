package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.server.response.zak;
import com.google.android.gms.common.server.response.zal;
import java.util.ArrayList;

public final class zan implements Creator<zak> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var3 = SafeParcelReader.validateObjectHeader(var1);
      ArrayList var6 = null;
      int var2 = 0;
      String var5 = null;

      while(var1.dataPosition() < var3) {
         int var4 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var4)) {
         case 1:
            var2 = SafeParcelReader.readInt(var1, var4);
            break;
         case 2:
            var6 = SafeParcelReader.createTypedList(var1, var4, zal.CREATOR);
            break;
         case 3:
            var5 = SafeParcelReader.createString(var1, var4);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var4);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var3);
      return new zak(var2, var6, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zak[var1];
   }
}
