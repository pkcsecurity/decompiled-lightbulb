package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.location.Geofence;
import java.util.Locale;

@SafeParcelable.Class(
   creator = "ParcelableGeofenceCreator"
)
@SafeParcelable.Reserved({1000})
@VisibleForTesting
public final class zzbh extends AbstractSafeParcelable implements Geofence {

   public static final Creator<zzbh> CREATOR = new hg();
   @SafeParcelable.Field(
      getter = "getRequestId",
      id = 1
   )
   private final String a;
   @SafeParcelable.Field(
      getter = "getExpirationTime",
      id = 2
   )
   private final long b;
   @SafeParcelable.Field(
      getter = "getType",
      id = 3
   )
   private final short c;
   @SafeParcelable.Field(
      getter = "getLatitude",
      id = 4
   )
   private final double d;
   @SafeParcelable.Field(
      getter = "getLongitude",
      id = 5
   )
   private final double e;
   @SafeParcelable.Field(
      getter = "getRadius",
      id = 6
   )
   private final float f;
   @SafeParcelable.Field(
      getter = "getTransitionTypes",
      id = 7
   )
   private final int g;
   @SafeParcelable.Field(
      defaultValue = "0",
      getter = "getNotificationResponsiveness",
      id = 8
   )
   private final int h;
   @SafeParcelable.Field(
      defaultValue = "-1",
      getter = "getLoiteringDelay",
      id = 9
   )
   private final int i;


   @SafeParcelable.Constructor
   public zzbh(
      @SafeParcelable.Param(
         id = 1
      ) String var1, 
      @SafeParcelable.Param(
         id = 7
      ) int var2, 
      @SafeParcelable.Param(
         id = 3
      ) short var3, 
      @SafeParcelable.Param(
         id = 4
      ) double var4, 
      @SafeParcelable.Param(
         id = 5
      ) double var6, 
      @SafeParcelable.Param(
         id = 6
      ) float var8, 
      @SafeParcelable.Param(
         id = 2
      ) long var9, 
      @SafeParcelable.Param(
         id = 8
      ) int var11, 
      @SafeParcelable.Param(
         id = 9
      ) int var12) {
      if(var1 != null && var1.length() <= 100) {
         StringBuilder var14;
         if(var8 <= 0.0F) {
            var14 = new StringBuilder(31);
            var14.append("invalid radius: ");
            var14.append(var8);
            throw new IllegalArgumentException(var14.toString());
         } else if(var4 <= 90.0D && var4 >= -90.0D) {
            if(var6 <= 180.0D && var6 >= -180.0D) {
               int var13 = var2 & 7;
               if(var13 == 0) {
                  var14 = new StringBuilder(46);
                  var14.append("No supported transition specified: ");
                  var14.append(var2);
                  throw new IllegalArgumentException(var14.toString());
               } else {
                  this.c = var3;
                  this.a = var1;
                  this.d = var4;
                  this.e = var6;
                  this.f = var8;
                  this.b = var9;
                  this.g = var13;
                  this.h = var11;
                  this.i = var12;
               }
            } else {
               var14 = new StringBuilder(43);
               var14.append("invalid longitude: ");
               var14.append(var6);
               throw new IllegalArgumentException(var14.toString());
            }
         } else {
            var14 = new StringBuilder(42);
            var14.append("invalid latitude: ");
            var14.append(var4);
            throw new IllegalArgumentException(var14.toString());
         }
      } else {
         var1 = String.valueOf(var1);
         if(var1.length() != 0) {
            var1 = "requestId is null or too long: ".concat(var1);
         } else {
            var1 = new String("requestId is null or too long: ");
         }

         throw new IllegalArgumentException(var1);
      }
   }

   public final String a() {
      return this.a;
   }

   public final boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(var1 == null) {
         return false;
      } else if(!(var1 instanceof zzbh)) {
         return false;
      } else {
         zzbh var2 = (zzbh)var1;
         return this.f != var2.f?false:(this.d != var2.d?false:(this.e != var2.e?false:this.c == var2.c));
      }
   }

   public final int hashCode() {
      long var2 = Double.doubleToLongBits(this.d);
      int var1 = (int)(var2 ^ var2 >>> 32);
      var2 = Double.doubleToLongBits(this.e);
      return ((((var1 + 31) * 31 + (int)(var2 ^ var2 >>> 32)) * 31 + Float.floatToIntBits(this.f)) * 31 + this.c) * 31 + this.g;
   }

   public final String toString() {
      Locale var2 = Locale.US;
      String var1;
      if(this.c != 1) {
         var1 = null;
      } else {
         var1 = "CIRCLE";
      }

      return String.format(var2, "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]", new Object[]{var1, this.a.replaceAll("\\p{C}", "?"), Integer.valueOf(this.g), Double.valueOf(this.d), Double.valueOf(this.e), Float.valueOf(this.f), Integer.valueOf(this.h / 1000), Integer.valueOf(this.i), Long.valueOf(this.b)});
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 1, this.a(), false);
      SafeParcelWriter.writeLong(var1, 2, this.b);
      SafeParcelWriter.writeShort(var1, 3, this.c);
      SafeParcelWriter.writeDouble(var1, 4, this.d);
      SafeParcelWriter.writeDouble(var1, 5, this.e);
      SafeParcelWriter.writeFloat(var1, 6, this.f);
      SafeParcelWriter.writeInt(var1, 7, this.g);
      SafeParcelWriter.writeInt(var1, 8, this.h);
      SafeParcelWriter.writeInt(var1, 9, this.i);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
