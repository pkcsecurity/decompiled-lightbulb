package com.google.android.gms.common.server;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.zaa;

@KeepForSdk
@SafeParcelable.Class(
   creator = "FavaDiagnosticsEntityCreator"
)
public class FavaDiagnosticsEntity extends AbstractSafeParcelable implements ReflectedParcelable {

   @KeepForSdk
   public static final Creator<FavaDiagnosticsEntity> CREATOR = new zaa();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int zale;
   @SafeParcelable.Field(
      id = 2
   )
   private final String zapi;
   @SafeParcelable.Field(
      id = 3
   )
   private final int zapj;


   @SafeParcelable.Constructor
   public FavaDiagnosticsEntity(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) String var2, 
      @SafeParcelable.Param(
         id = 3
      ) int var3) {
      this.zale = var1;
      this.zapi = var2;
      this.zapj = var3;
   }

   @KeepForSdk
   public FavaDiagnosticsEntity(String var1, int var2) {
      this.zale = 1;
      this.zapi = var1;
      this.zapj = var2;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zale);
      SafeParcelWriter.writeString(var1, 2, this.zapi, false);
      SafeParcelWriter.writeInt(var1, 3, this.zapj);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
