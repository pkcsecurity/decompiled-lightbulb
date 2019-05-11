package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.zzae;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SafeParcelable.Class(
   creator = "LocationSettingsRequestCreator"
)
@SafeParcelable.Reserved({1000})
public final class LocationSettingsRequest extends AbstractSafeParcelable {

   public static final Creator<LocationSettingsRequest> CREATOR = new ic();
   @SafeParcelable.Field(
      getter = "getLocationRequests",
      id = 1
   )
   private final List<LocationRequest> a;
   @SafeParcelable.Field(
      defaultValue = "false",
      getter = "alwaysShow",
      id = 2
   )
   private final boolean b;
   @SafeParcelable.Field(
      getter = "needBle",
      id = 3
   )
   private final boolean c;
   @SafeParcelable.Field(
      getter = "getConfiguration",
      id = 5
   )
   private zzae d;


   @SafeParcelable.Constructor
   public LocationSettingsRequest(
      @SafeParcelable.Param(
         id = 1
      ) List<LocationRequest> var1, 
      @SafeParcelable.Param(
         id = 2
      ) boolean var2, 
      @SafeParcelable.Param(
         id = 3
      ) boolean var3, 
      @SafeParcelable.Param(
         id = 5
      ) zzae var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 1, Collections.unmodifiableList(this.a), false);
      SafeParcelWriter.writeBoolean(var1, 2, this.b);
      SafeParcelWriter.writeBoolean(var1, 3, this.c);
      SafeParcelWriter.writeParcelable(var1, 5, this.d, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public static final class a {

      private final ArrayList<LocationRequest> a = new ArrayList();
      private boolean b = false;
      private boolean c = false;
      private zzae d = null;


      public final LocationSettingsRequest.a a(@NonNull LocationRequest var1) {
         if(var1 != null) {
            this.a.add(var1);
         }

         return this;
      }

      public final LocationSettingsRequest a() {
         return new LocationSettingsRequest(this.a, this.b, this.c, (zzae)null);
      }
   }
}
