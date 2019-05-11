package com.google.android.gms.location;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Collections;
import java.util.List;

@SafeParcelable.Class(
   creator = "RemoveGeofencingRequestCreator"
)
@SafeParcelable.Reserved({1000})
public final class zzal extends AbstractSafeParcelable {

   public static final Creator<zzal> CREATOR = new ig();
   @SafeParcelable.Field(
      getter = "getGeofenceIds",
      id = 1
   )
   private final List<String> a;
   @SafeParcelable.Field(
      getter = "getPendingIntent",
      id = 2
   )
   private final PendingIntent b;
   @SafeParcelable.Field(
      defaultValue = "",
      getter = "getTag",
      id = 3
   )
   private final String c;


   @SafeParcelable.Constructor
   public zzal(@Nullable 
      @SafeParcelable.Param(
         id = 1
      ) List<String> var1, @Nullable 
      @SafeParcelable.Param(
         id = 2
      ) PendingIntent var2, 
      @SafeParcelable.Param(
         id = 3
      ) String var3) {
      if(var1 == null) {
         var1 = Collections.emptyList();
      } else {
         var1 = Collections.unmodifiableList(var1);
      }

      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeStringList(var1, 1, this.a, false);
      SafeParcelWriter.writeParcelable(var1, 2, this.b, var2, false);
      SafeParcelWriter.writeString(var1, 3, this.c, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
