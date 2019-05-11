package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.SignInButtonConfig;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zao implements Creator<SignInButtonConfig> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var5 = SafeParcelReader.validateObjectHeader(var1);
      int var4 = 0;
      Scope[] var7 = null;
      int var3 = 0;
      int var2 = 0;

      while(var1.dataPosition() < var5) {
         int var6 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var6)) {
         case 1:
            var4 = SafeParcelReader.readInt(var1, var6);
            break;
         case 2:
            var3 = SafeParcelReader.readInt(var1, var6);
            break;
         case 3:
            var2 = SafeParcelReader.readInt(var1, var6);
            break;
         case 4:
            var7 = (Scope[])SafeParcelReader.createTypedArray(var1, var6, Scope.CREATOR);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var6);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var5);
      return new SignInButtonConfig(var4, var3, var2, var7);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new SignInButtonConfig[var1];
   }
}
