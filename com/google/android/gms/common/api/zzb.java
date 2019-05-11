package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzb implements Creator<Status> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var4 = SafeParcelReader.validateObjectHeader(var1);
      int var2 = 0;
      String var8 = null;
      Object var7 = var8;
      int var3 = 0;

      while(var1.dataPosition() < var4) {
         int var5 = SafeParcelReader.readHeader(var1);
         int var6 = SafeParcelReader.getFieldId(var5);
         if(var6 != 1000) {
            switch(var6) {
            case 1:
               var3 = SafeParcelReader.readInt(var1, var5);
               break;
            case 2:
               var8 = SafeParcelReader.createString(var1, var5);
               break;
            case 3:
               var7 = (PendingIntent)SafeParcelReader.createParcelable(var1, var5, PendingIntent.CREATOR);
               break;
            default:
               SafeParcelReader.skipUnknownField(var1, var5);
            }
         } else {
            var2 = SafeParcelReader.readInt(var1, var5);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var4);
      return new Status(var2, var3, var8, (PendingIntent)var7);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new Status[var1];
   }
}
