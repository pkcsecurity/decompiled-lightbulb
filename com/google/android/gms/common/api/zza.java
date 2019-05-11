package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zza implements Creator<Scope> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var3 = SafeParcelReader.validateObjectHeader(var1);
      int var2 = 0;
      String var5 = null;

      while(var1.dataPosition() < var3) {
         int var4 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var4)) {
         case 1:
            var2 = SafeParcelReader.readInt(var1, var4);
            break;
         case 2:
            var5 = SafeParcelReader.createString(var1, var4);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var4);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var3);
      return new Scope(var2, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new Scope[var1];
   }
}
