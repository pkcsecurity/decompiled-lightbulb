package com.google.android.gms.location;

import android.os.Parcel;
import android.os.SystemClock;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "DeviceOrientationRequestCreator"
)
public final class zzj extends AbstractSafeParcelable {

   public static final Creator<zzj> CREATOR = new ip();
   @SafeParcelable.Field(
      defaultValueUnchecked = "DeviceOrientationRequest.DEFAULT_SHOULD_USE_MAG",
      id = 1
   )
   private boolean a;
   @SafeParcelable.Field(
      defaultValueUnchecked = "DeviceOrientationRequest.DEFAULT_MINIMUM_SAMPLING_PERIOD_MS",
      id = 2
   )
   private long b;
   @SafeParcelable.Field(
      defaultValueUnchecked = "DeviceOrientationRequest.DEFAULT_SMALLEST_ANGLE_CHANGE_RADIANS",
      id = 3
   )
   private float c;
   @SafeParcelable.Field(
      defaultValueUnchecked = "DeviceOrientationRequest.DEFAULT_EXPIRE_AT_MS",
      id = 4
   )
   private long d;
   @SafeParcelable.Field(
      defaultValueUnchecked = "DeviceOrientationRequest.DEFAULT_NUM_UPDATES",
      id = 5
   )
   private int e;


   public zzj() {
      this(true, 50L, 0.0F, Long.MAX_VALUE, Integer.MAX_VALUE);
   }

   @SafeParcelable.Constructor
   public zzj(
      @SafeParcelable.Param(
         id = 1
      ) boolean var1, 
      @SafeParcelable.Param(
         id = 2
      ) long var2, 
      @SafeParcelable.Param(
         id = 3
      ) float var4, 
      @SafeParcelable.Param(
         id = 4
      ) long var5, 
      @SafeParcelable.Param(
         id = 5
      ) int var7) {
      this.a = var1;
      this.b = var2;
      this.c = var4;
      this.d = var5;
      this.e = var7;
   }

   public final boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof zzj)) {
         return false;
      } else {
         zzj var2 = (zzj)var1;
         return this.a == var2.a && this.b == var2.b && Float.compare(this.c, var2.c) == 0 && this.d == var2.d && this.e == var2.e;
      }
   }

   public final int hashCode() {
      return Objects.hashCode(new Object[]{Boolean.valueOf(this.a), Long.valueOf(this.b), Float.valueOf(this.c), Long.valueOf(this.d), Integer.valueOf(this.e)});
   }

   public final String toString() {
      StringBuilder var5 = new StringBuilder();
      var5.append("DeviceOrientationRequest[mShouldUseMag=");
      var5.append(this.a);
      var5.append(" mMinimumSamplingPeriodMs=");
      var5.append(this.b);
      var5.append(" mSmallestAngleChangeRadians=");
      var5.append(this.c);
      if(this.d != Long.MAX_VALUE) {
         long var1 = this.d;
         long var3 = SystemClock.elapsedRealtime();
         var5.append(" expireIn=");
         var5.append(var1 - var3);
         var5.append("ms");
      }

      if(this.e != Integer.MAX_VALUE) {
         var5.append(" num=");
         var5.append(this.e);
      }

      var5.append(']');
      return var5.toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeBoolean(var1, 1, this.a);
      SafeParcelWriter.writeLong(var1, 2, this.b);
      SafeParcelWriter.writeFloat(var1, 3, this.c);
      SafeParcelWriter.writeLong(var1, 4, this.d);
      SafeParcelWriter.writeInt(var1, 5, this.e);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
