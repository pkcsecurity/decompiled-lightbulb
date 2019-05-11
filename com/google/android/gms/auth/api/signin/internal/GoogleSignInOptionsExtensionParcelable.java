package com.google.android.gms.auth.api.signin.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "GoogleSignInOptionsExtensionCreator"
)
public class GoogleSignInOptionsExtensionParcelable extends AbstractSafeParcelable {

   public static final Creator<GoogleSignInOptionsExtensionParcelable> CREATOR = new fh();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int a;
   @SafeParcelable.Field(
      getter = "getType",
      id = 2
   )
   private int b;
   @SafeParcelable.Field(
      getter = "getBundle",
      id = 3
   )
   private Bundle c;


   @SafeParcelable.Constructor
   public GoogleSignInOptionsExtensionParcelable(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) int var2, 
      @SafeParcelable.Param(
         id = 3
      ) Bundle var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   @KeepForSdk
   public int a() {
      return this.b;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.a);
      SafeParcelWriter.writeInt(var1, 2, this.a());
      SafeParcelWriter.writeBundle(var1, 3, this.c, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
