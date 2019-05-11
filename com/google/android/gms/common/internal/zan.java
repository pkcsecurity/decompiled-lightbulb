package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zan implements Creator<ResolveAccountResponse> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var3 = SafeParcelReader.validateObjectHeader(var1);
      IBinder var8 = null;
      Object var7 = var8;
      int var2 = 0;
      boolean var6 = false;
      boolean var5 = false;

      while(var1.dataPosition() < var3) {
         int var4 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var4)) {
         case 1:
            var2 = SafeParcelReader.readInt(var1, var4);
            break;
         case 2:
            var8 = SafeParcelReader.readIBinder(var1, var4);
            break;
         case 3:
            var7 = (ConnectionResult)SafeParcelReader.createParcelable(var1, var4, ConnectionResult.CREATOR);
            break;
         case 4:
            var6 = SafeParcelReader.readBoolean(var1, var4);
            break;
         case 5:
            var5 = SafeParcelReader.readBoolean(var1, var4);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var4);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var3);
      return new ResolveAccountResponse(var2, var8, (ConnectionResult)var7, var6, var5);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new ResolveAccountResponse[var1];
   }
}
