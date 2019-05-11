package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.AuthAccountRequest;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zaa implements Creator<AuthAccountRequest> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var3 = SafeParcelReader.validateObjectHeader(var1);
      IBinder var9 = null;
      Object var5 = var9;
      Object var6 = var9;
      Object var7 = var9;
      Object var8 = var9;
      int var2 = 0;

      while(var1.dataPosition() < var3) {
         int var4 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var4)) {
         case 1:
            var2 = SafeParcelReader.readInt(var1, var4);
            break;
         case 2:
            var9 = SafeParcelReader.readIBinder(var1, var4);
            break;
         case 3:
            var5 = (Scope[])SafeParcelReader.createTypedArray(var1, var4, Scope.CREATOR);
            break;
         case 4:
            var6 = SafeParcelReader.readIntegerObject(var1, var4);
            break;
         case 5:
            var7 = SafeParcelReader.readIntegerObject(var1, var4);
            break;
         case 6:
            var8 = (Account)SafeParcelReader.createParcelable(var1, var4, Account.CREATOR);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var4);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var3);
      return new AuthAccountRequest(var2, var9, (Scope[])var5, (Integer)var6, (Integer)var7, (Account)var8);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new AuthAccountRequest[var1];
   }
}
