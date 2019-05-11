package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zam implements Creator<ResolveAccountRequest> {

   // $FF: synthetic method
   public final Object createFromParcel(Parcel var1) {
      int var4 = SafeParcelReader.validateObjectHeader(var1);
      Account var7 = null;
      int var3 = 0;
      GoogleSignInAccount var6 = null;
      int var2 = 0;

      while(var1.dataPosition() < var4) {
         int var5 = SafeParcelReader.readHeader(var1);
         switch(SafeParcelReader.getFieldId(var5)) {
         case 1:
            var3 = SafeParcelReader.readInt(var1, var5);
            break;
         case 2:
            var7 = (Account)SafeParcelReader.createParcelable(var1, var5, Account.CREATOR);
            break;
         case 3:
            var2 = SafeParcelReader.readInt(var1, var5);
            break;
         case 4:
            var6 = (GoogleSignInAccount)SafeParcelReader.createParcelable(var1, var5, GoogleSignInAccount.CREATOR);
            break;
         default:
            SafeParcelReader.skipUnknownField(var1, var5);
         }
      }

      SafeParcelReader.ensureAtEnd(var1, var4);
      return new ResolveAccountRequest(var3, var7, var2, var6);
   }

   // $FF: synthetic method
   public final Object[] newArray(int var1) {
      return new ResolveAccountRequest[var1];
   }
}
