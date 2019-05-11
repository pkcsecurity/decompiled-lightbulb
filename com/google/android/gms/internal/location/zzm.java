package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.ClientIdentity;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.location.zzj;
import java.util.Collections;
import java.util.List;

@SafeParcelable.Class(
   creator = "DeviceOrientationRequestInternalCreator"
)
public final class zzm extends AbstractSafeParcelable {

   public static final Creator<zzm> CREATOR = new hn();
   @VisibleForTesting
   public static final List<ClientIdentity> a = Collections.emptyList();
   public static final zzj b = new zzj();
   @SafeParcelable.Field(
      defaultValueUnchecked = "DeviceOrientationRequestInternal.DEFAULT_DEVICE_ORIENTATION_REQUEST",
      id = 1
   )
   private zzj c;
   @SafeParcelable.Field(
      defaultValueUnchecked = "DeviceOrientationRequestInternal.DEFAULT_CLIENTS",
      id = 2
   )
   private List<ClientIdentity> d;
   @Nullable
   @SafeParcelable.Field(
      defaultValueUnchecked = "null",
      id = 3
   )
   private String e;


   @SafeParcelable.Constructor
   public zzm(
      @SafeParcelable.Param(
         id = 1
      ) zzj var1, 
      @SafeParcelable.Param(
         id = 2
      ) List<ClientIdentity> var2, 
      @SafeParcelable.Param(
         id = 3
      ) String var3) {
      this.c = var1;
      this.d = var2;
      this.e = var3;
   }

   public final boolean equals(Object var1) {
      if(!(var1 instanceof zzm)) {
         return false;
      } else {
         zzm var2 = (zzm)var1;
         return Objects.equal(this.c, var2.c) && Objects.equal(this.d, var2.d) && Objects.equal(this.e, var2.e);
      }
   }

   public final int hashCode() {
      return this.c.hashCode();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.c, var2, false);
      SafeParcelWriter.writeTypedList(var1, 2, this.d, false);
      SafeParcelWriter.writeString(var1, 3, this.e, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
