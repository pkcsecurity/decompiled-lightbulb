package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "LocationSettingsStatesCreator"
)
@SafeParcelable.Reserved({1000})
public final class LocationSettingsStates extends AbstractSafeParcelable {

   public static final Creator<LocationSettingsStates> CREATOR = new ie();
   @SafeParcelable.Field(
      getter = "isGpsUsable",
      id = 1
   )
   private final boolean a;
   @SafeParcelable.Field(
      getter = "isNetworkLocationUsable",
      id = 2
   )
   private final boolean b;
   @SafeParcelable.Field(
      getter = "isBleUsable",
      id = 3
   )
   private final boolean c;
   @SafeParcelable.Field(
      getter = "isGpsPresent",
      id = 4
   )
   private final boolean d;
   @SafeParcelable.Field(
      getter = "isNetworkLocationPresent",
      id = 5
   )
   private final boolean e;
   @SafeParcelable.Field(
      getter = "isBlePresent",
      id = 6
   )
   private final boolean f;


   @SafeParcelable.Constructor
   public LocationSettingsStates(
      @SafeParcelable.Param(
         id = 1
      ) boolean var1, 
      @SafeParcelable.Param(
         id = 2
      ) boolean var2, 
      @SafeParcelable.Param(
         id = 3
      ) boolean var3, 
      @SafeParcelable.Param(
         id = 4
      ) boolean var4, 
      @SafeParcelable.Param(
         id = 5
      ) boolean var5, 
      @SafeParcelable.Param(
         id = 6
      ) boolean var6) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
      this.f = var6;
   }

   public final boolean a() {
      return this.a;
   }

   public final boolean b() {
      return this.d;
   }

   public final boolean c() {
      return this.b;
   }

   public final boolean d() {
      return this.e;
   }

   public final boolean e() {
      return this.c;
   }

   public final boolean f() {
      return this.f;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeBoolean(var1, 1, this.a());
      SafeParcelWriter.writeBoolean(var1, 2, this.c());
      SafeParcelWriter.writeBoolean(var1, 3, this.e());
      SafeParcelWriter.writeBoolean(var1, 4, this.b());
      SafeParcelWriter.writeBoolean(var1, 5, this.d());
      SafeParcelWriter.writeBoolean(var1, 6, this.f());
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
