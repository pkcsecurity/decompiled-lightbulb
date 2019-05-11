package com.google.android.gms.internal.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.ClientIdentity;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.LocationRequest;
import java.util.Collections;
import java.util.List;

@SafeParcelable.Class(
   creator = "LocationRequestInternalCreator"
)
@SafeParcelable.Reserved({1000, 2, 3, 4})
public final class zzbd extends AbstractSafeParcelable {

   public static final Creator<zzbd> CREATOR = new he();
   public static final List<ClientIdentity> a = Collections.emptyList();
   @SafeParcelable.Field(
      defaultValueUnchecked = "null",
      id = 1
   )
   private LocationRequest b;
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationRequestInternal.DEFAULT_CLIENTS",
      id = 5
   )
   private List<ClientIdentity> c;
   @Nullable
   @SafeParcelable.Field(
      defaultValueUnchecked = "null",
      id = 6
   )
   private String d;
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationRequestInternal.DEFAULT_HIDE_FROM_APP_OPS",
      id = 7
   )
   private boolean e;
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationRequestInternal.DEFAULT_FORCE_COARSE_LOCATION",
      id = 8
   )
   private boolean f;
   @SafeParcelable.Field(
      defaultValueUnchecked = "LocationRequestInternal.DEFAULT_EXEMPT_FROM_THROTTLE",
      id = 9
   )
   private boolean g;
   @Nullable
   @SafeParcelable.Field(
      defaultValueUnchecked = "null",
      id = 10
   )
   private String h;
   private boolean i = true;


   @SafeParcelable.Constructor
   public zzbd(
      @SafeParcelable.Param(
         id = 1
      ) LocationRequest var1, 
      @SafeParcelable.Param(
         id = 5
      ) List<ClientIdentity> var2, @Nullable 
      @SafeParcelable.Param(
         id = 6
      ) String var3, 
      @SafeParcelable.Param(
         id = 7
      ) boolean var4, 
      @SafeParcelable.Param(
         id = 8
      ) boolean var5, 
      @SafeParcelable.Param(
         id = 9
      ) boolean var6, 
      @SafeParcelable.Param(
         id = 10
      ) String var7) {
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
      this.g = var6;
      this.h = var7;
   }

   @Deprecated
   public static zzbd a(LocationRequest var0) {
      return new zzbd(var0, a, (String)null, false, false, false, (String)null);
   }

   public final boolean equals(Object var1) {
      if(!(var1 instanceof zzbd)) {
         return false;
      } else {
         zzbd var2 = (zzbd)var1;
         return Objects.equal(this.b, var2.b) && Objects.equal(this.c, var2.c) && Objects.equal(this.d, var2.d) && this.e == var2.e && this.f == var2.f && this.g == var2.g && Objects.equal(this.h, var2.h);
      }
   }

   public final int hashCode() {
      return this.b.hashCode();
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.b);
      if(this.d != null) {
         var1.append(" tag=");
         var1.append(this.d);
      }

      if(this.h != null) {
         var1.append(" moduleId=");
         var1.append(this.h);
      }

      var1.append(" hideAppOps=");
      var1.append(this.e);
      var1.append(" clients=");
      var1.append(this.c);
      var1.append(" forceCoarseLocation=");
      var1.append(this.f);
      if(this.g) {
         var1.append(" exemptFromBackgroundThrottle");
      }

      return var1.toString();
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeParcelable(var1, 1, this.b, var2, false);
      SafeParcelWriter.writeTypedList(var1, 5, this.c, false);
      SafeParcelWriter.writeString(var1, 6, this.d, false);
      SafeParcelWriter.writeBoolean(var1, 7, this.e);
      SafeParcelWriter.writeBoolean(var1, 8, this.f);
      SafeParcelWriter.writeBoolean(var1, 9, this.g);
      SafeParcelWriter.writeString(var1, 10, this.h, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
