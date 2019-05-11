package com.google.android.gms.signin.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "AuthAccountResultCreator"
)
public final class zaa extends AbstractSafeParcelable implements Result {

   public static final Creator<zaa> CREATOR = new kz();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int a;
   @SafeParcelable.Field(
      getter = "getConnectionResultCode",
      id = 2
   )
   private int b;
   @SafeParcelable.Field(
      getter = "getRawAuthResolutionIntent",
      id = 3
   )
   private Intent c;


   public zaa() {
      this(0, (Intent)null);
   }

   @SafeParcelable.Constructor
   public zaa(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) int var2, 
      @SafeParcelable.Param(
         id = 3
      ) Intent var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   private zaa(int var1, Intent var2) {
      this(2, 0, (Intent)null);
   }

   public final Status getStatus() {
      return this.b == 0?Status.RESULT_SUCCESS:Status.RESULT_CANCELED;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.a);
      SafeParcelWriter.writeInt(var1, 2, this.b);
      SafeParcelWriter.writeParcelable(var1, 3, this.c, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
