package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.server.response.SafeParcelResponse;
import com.google.android.gms.common.server.response.zak;

public final class zap implements Creator<SafeParcelResponse> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var3 = SafeParcelReader.validateObjectHeader(var1);
      Parcel var6 = null;
      int var2 = 0;
      zak var5 = null;

      while(var1.dataPosition() < var3) {
         int var4 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var4)) {
         case 1:
            var2 = SafeParcelReader.readInt(var1, var4);
            break;
         case 2:
            var6 = SafeParcelReader.createParcel(var1, var4);
            break;
         case 3:
            var5 = (zak)SafeParcelReader.createParcelable(var1, var4, zak.CREATOR);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var4);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var3);
      return new SafeParcelResponse(var2, var6, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new SafeParcelResponse[var1];
   }
}
