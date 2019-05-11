package com.google.android.gms.common;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zza implements Creator<ConnectionResult> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var4 = SafeParcelReader.validateObjectHeader(var1);
      int var2 = 0;
      PendingIntent var7 = null;
      Object var6 = var7;
      int var3 = 0;

      while(var1.dataPosition() < var4) {
         int var5 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var5)) {
         case 1:
            var2 = SafeParcelReader.readInt(var1, var5);
            break;
         case 2:
            var3 = SafeParcelReader.readInt(var1, var5);
            break;
         case 3:
            var7 = (PendingIntent)SafeParcelReader.createParcelable(var1, var5, PendingIntent.CREATOR);
            break;
         case 4:
            var6 = SafeParcelReader.createString(var1, var5);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var5);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var4);
      return new ConnectionResult(var2, var3, var7, (String)var6);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new ConnectionResult[var1];
   }
}
