package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.zam;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "ResolveAccountRequestCreator"
)
public class ResolveAccountRequest extends AbstractSafeParcelable {

   public static final Creator<ResolveAccountRequest> CREATOR = new zam();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int zale;
   @SafeParcelable.Field(
      getter = "getSessionId",
      id = 3
   )
   private final int zaoz;
   @SafeParcelable.Field(
      getter = "getSignInAccountHint",
      id = 4
   )
   private final GoogleSignInAccount zapa;
   @SafeParcelable.Field(
      getter = "getAccount",
      id = 2
   )
   private final Account zax;


   @SafeParcelable.Constructor
   ResolveAccountRequest(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) Account var2, 
      @SafeParcelable.Param(
         id = 3
      ) int var3, 
      @SafeParcelable.Param(
         id = 4
      ) GoogleSignInAccount var4) {
      this.zale = var1;
      this.zax = var2;
      this.zaoz = var3;
      this.zapa = var4;
   }

   public ResolveAccountRequest(Account var1, int var2, GoogleSignInAccount var3) {
      this(2, var1, var2, var3);
   }

   public Account getAccount() {
      return this.zax;
   }

   public int getSessionId() {
      return this.zaoz;
   }

   @Nullable
   public GoogleSignInAccount getSignInAccountHint() {
      return this.zapa;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zale);
      SafeParcelWriter.writeParcelable(var1, 2, this.getAccount(), var2, false);
      SafeParcelWriter.writeInt(var1, 3, this.getSessionId());
      SafeParcelWriter.writeParcelable(var1, 4, this.getSignInAccountHint(), var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
