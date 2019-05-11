package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzs;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@Deprecated
@SafeParcelable.Class(
   creator = "ValidateAccountRequestCreator"
)
public final class zzr extends AbstractSafeParcelable {

   public static final Creator<zzr> CREATOR = new zzs();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int zzg;


   @SafeParcelable.Constructor
   zzr(
      @SafeParcelable.Param(
         id = 1
      ) int var1) {
      this.zzg = var1;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zzg);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
