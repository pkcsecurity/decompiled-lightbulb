package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "ConnectionInfoCreator"
)
public final class zzb extends AbstractSafeParcelable {

   public static final Creator<zzb> CREATOR = new zzc();
   @SafeParcelable.Field(
      id = 1
   )
   Bundle zzcz;
   @SafeParcelable.Field(
      id = 2
   )
   Feature[] zzda;


   public zzb() {}

   @SafeParcelable.Constructor
   zzb(
      @SafeParcelable.Param(
         id = 1
      ) Bundle var1, 
      @SafeParcelable.Param(
         id = 2
      ) Feature[] var2) {
      this.zzcz = var1;
      this.zzda = var2;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeBundle(var1, 1, this.zzcz, false);
      SafeParcelWriter.writeTypedArray(var1, 2, this.zzda, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
