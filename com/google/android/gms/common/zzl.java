package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.zzk;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzl implements Creator<zzk> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var2 = SafeParcelReader.validateObjectHeader(var1);
      String var6 = null;
      IBinder var5 = null;
      boolean var4 = false;

      while(var1.dataPosition() < var2) {
         int var3 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var3)) {
         case 1:
            var6 = SafeParcelReader.createString(var1, var3);
            break;
         case 2:
            var5 = SafeParcelReader.readIBinder(var1, var3);
            break;
         case 3:
            var4 = SafeParcelReader.readBoolean(var1, var3);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var3);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var2);
      return new zzk(var6, var5, var4);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new zzk[var1];
   }
}
