package com.google.android.gms.location;

import android.os.Parcel;
import android.os.SystemClock;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.VisibleForTesting;

@SafeParcelable.Class(
   creator = "LocationRequestCreator"
)
@SafeParcelable.Reserved({1000})
public final class LocationRequest extends AbstractSafeParcelable implements ReflectedParcelable {

   public static final Creator<LocationRequest> CREATOR = new hy();
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationRequest.DEFAULT_PRIORITY",
      id = 1
   )
   private int a;
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationRequest.DEFAULT_INTERVAL",
      id = 2
   )
   private long b;
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationRequest.DEFAULT_FASTEST_INTERVAL",
      id = 3
   )
   private long c;
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationRequest.DEFAULT_EXPLICIT_FASTEST_INTERVAL",
      id = 4
   )
   private boolean d;
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationRequest.DEFAULT_EXPIRE_AT",
      id = 5
   )
   private long e;
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationRequest.DEFAULT_NUM_UPDATES",
      id = 6
   )
   private int f;
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationRequest.DEFAULT_SMALLEST_DISPLACEMENT",
      id = 7
   )
   private float g;
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationRequest.DEFAULT_MAX_WAIT_TIME",
      id = 8
   )
   private long h;


   public LocationRequest() {
      this.a = 102;
      this.b = 3600000L;
      this.c = 600000L;
      this.d = false;
      this.e = Long.MAX_VALUE;
      this.f = Integer.MAX_VALUE;
      this.g = 0.0F;
      this.h = 0L;
   }

   @SafeParcelable.Constructor
   public LocationRequest(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) long var2, 
      @SafeParcelable.Param(
         id = 3
      ) long var4, 
      @SafeParcelable.Param(
         id = 4
      ) boolean var6, 
      @SafeParcelable.Param(
         id = 5
      ) long var7, 
      @SafeParcelable.Param(
         id = 6
      ) int var9, 
      @SafeParcelable.Param(
         id = 7
      ) float var10, 
      @SafeParcelable.Param(
         id = 8
      ) long var11) {
      this.a = var1;
      this.b = var2;
      this.c = var4;
      this.d = var6;
      this.e = var7;
      this.f = var9;
      this.g = var10;
      this.h = var11;
   }

   private static void c(long var0) {
      if(var0 < 0L) {
         StringBuilder var2 = new StringBuilder(38);
         var2.append("invalid interval: ");
         var2.append(var0);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public final long a() {
      long var3 = this.h;
      long var1 = var3;
      if(var3 < this.b) {
         var1 = this.b;
      }

      return var1;
   }

   @VisibleForTesting
   public final LocationRequest a(int var1) {
      switch(var1) {
      case 100:
      case 102:
      case 104:
      case 105:
         this.a = var1;
         return this;
      case 101:
      case 103:
      default:
         StringBuilder var2 = new StringBuilder(28);
         var2.append("invalid quality: ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public final LocationRequest a(long var1) {
      c(var1);
      this.b = var1;
      if(!this.d) {
         this.c = (long)((double)this.b / 6.0D);
      }

      return this;
   }

   public final LocationRequest b(long var1) {
      c(var1);
      this.d = true;
      this.c = var1;
      return this;
   }

   public final boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof LocationRequest)) {
         return false;
      } else {
         LocationRequest var2 = (LocationRequest)var1;
         return this.a == var2.a && this.b == var2.b && this.c == var2.c && this.d == var2.d && this.e == var2.e && this.f == var2.f && this.g == var2.g && this.a() == var2.a();
      }
   }

   public final int hashCode() {
      return Objects.hashCode(new Object[]{Integer.valueOf(this.a), Long.valueOf(this.b), Float.valueOf(this.g), Long.valueOf(this.h)});
   }

   public final String toString() {
      StringBuilder var6 = new StringBuilder();
      var6.append("Request[");
      String var5;
      switch(this.a) {
      case 100:
         var5 = "PRIORITY_HIGH_ACCURACY";
         break;
      case 101:
      case 103:
      default:
         var5 = "???";
         break;
      case 102:
         var5 = "PRIORITY_BALANCED_POWER_ACCURACY";
         break;
      case 104:
         var5 = "PRIORITY_LOW_POWER";
         break;
      case 105:
         var5 = "PRIORITY_NO_POWER";
      }

      var6.append(var5);
      if(this.a != 105) {
         var6.append(" requested=");
         var6.append(this.b);
         var6.append("ms");
      }

      var6.append(" fastest=");
      var6.append(this.c);
      var6.append("ms");
      if(this.h > this.b) {
         var6.append(" maxWait=");
         var6.append(this.h);
         var6.append("ms");
      }

      if(this.g > 0.0F) {
         var6.append(" smallestDisplacement=");
         var6.append(this.g);
         var6.append("m");
      }

      if(this.e != Long.MAX_VALUE) {
         long var1 = this.e;
         long var3 = SystemClock.elapsedRealtime();
         var6.append(" expireIn=");
         var6.append(var1 - var3);
         var6.append("ms");
      }

      if(this.f != Integer.MAX_VALUE) {
         var6.append(" num=");
         var6.append(this.f);
      }

      var6.append(']');
      return var6.toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.a);
      SafeParcelWriter.writeLong(var1, 2, this.b);
      SafeParcelWriter.writeLong(var1, 3, this.c);
      SafeParcelWriter.writeBoolean(var1, 4, this.d);
      SafeParcelWriter.writeLong(var1, 5, this.e);
      SafeParcelWriter.writeInt(var1, 6, this.f);
      SafeParcelWriter.writeFloat(var1, 7, this.g);
      SafeParcelWriter.writeLong(var1, 8, this.h);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
