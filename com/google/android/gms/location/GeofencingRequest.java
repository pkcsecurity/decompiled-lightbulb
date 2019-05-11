package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.location.zzbh;
import java.util.List;

@SafeParcelable.Class(
   creator = "GeofencingRequestCreator"
)
@SafeParcelable.Reserved({1000})
public class GeofencingRequest extends AbstractSafeParcelable {

   public static final Creator<GeofencingRequest> CREATOR = new it();
   @SafeParcelable.Field(
      getter = "getParcelableGeofences",
      id = 1
   )
   private final List<zzbh> a;
   @SafeParcelable.Field(
      getter = "getInitialTrigger",
      id = 2
   )
   private final int b;
   @SafeParcelable.Field(
      defaultValue = "",
      getter = "getTag",
      id = 3
   )
   private final String c;


   @SafeParcelable.Constructor
   public GeofencingRequest(
      @SafeParcelable.Param(
         id = 1
      ) List<zzbh> var1, 
      @SafeParcelable.Param(
         id = 2
      ) int var2, 
      @SafeParcelable.Param(
         id = 3
      ) String var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public int a() {
      return this.b;
   }

   public String toString() {
      StringBuilder var3 = new StringBuilder();
      var3.append("GeofencingRequest[");
      var3.append("geofences=");
      var3.append(this.a);
      int var1 = this.b;
      StringBuilder var2 = new StringBuilder(30);
      var2.append(", initialTrigger=");
      var2.append(var1);
      var2.append(", ");
      var3.append(var2.toString());
      String var4 = String.valueOf(this.c);
      if(var4.length() != 0) {
         var4 = "tag=".concat(var4);
      } else {
         var4 = new String("tag=");
      }

      var3.append(var4);
      var3.append("]");
      return var3.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 1, this.a, false);
      SafeParcelWriter.writeInt(var1, 2, this.a());
      SafeParcelWriter.writeString(var1, 3, this.c, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
