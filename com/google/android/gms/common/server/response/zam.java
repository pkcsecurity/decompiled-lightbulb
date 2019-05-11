package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.zaj;

@ShowFirstParty
@SafeParcelable.Class(
   creator = "FieldMapPairCreator"
)
public final class zam extends AbstractSafeParcelable {

   public static final Creator<zam> CREATOR = new zaj();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int versionCode;
   @SafeParcelable.Field(
      id = 2
   )
   final String zaqy;
   @SafeParcelable.Field(
      id = 3
   )
   final FastJsonResponse.Field<?, ?> zaqz;


   @SafeParcelable.Constructor
   zam(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) String var2, 
      @SafeParcelable.Param(
         id = 3
      ) FastJsonResponse.Field<?, ?> var3) {
      this.versionCode = var1;
      this.zaqy = var2;
      this.zaqz = var3;
   }

   zam(String var1, FastJsonResponse.Field<?, ?> var2) {
      this.versionCode = 1;
      this.zaqy = var1;
      this.zaqz = var2;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.versionCode);
      SafeParcelWriter.writeString(var1, 2, this.zaqy, false);
      SafeParcelWriter.writeParcelable(var1, 3, this.zaqz, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
