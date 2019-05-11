package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "SignInRequestCreator"
)
public final class zah extends AbstractSafeParcelable {

   public static final Creator<zah> CREATOR = new la();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int a;
   @SafeParcelable.Field(
      getter = "getResolveAccountRequest",
      id = 2
   )
   private final ResolveAccountRequest b;


   @SafeParcelable.Constructor
   public zah(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) ResolveAccountRequest var2) {
      this.a = var1;
      this.b = var2;
   }

   public zah(ResolveAccountRequest var1) {
      this(1, var1);
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.a);
      SafeParcelWriter.writeParcelable(var1, 2, this.b, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
