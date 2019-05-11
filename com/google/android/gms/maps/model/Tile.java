package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "TileCreator"
)
@SafeParcelable.Reserved({1})
public final class Tile extends AbstractSafeParcelable {

   public static final Creator<Tile> CREATOR = new ka();
   @SafeParcelable.Field(
      id = 2
   )
   public final int a;
   @SafeParcelable.Field(
      id = 3
   )
   public final int b;
   @SafeParcelable.Field(
      id = 4
   )
   public final byte[] c;


   @SafeParcelable.Constructor
   public Tile(
      @SafeParcelable.Param(
         id = 2
      ) int var1, 
      @SafeParcelable.Param(
         id = 3
      ) int var2, 
      @SafeParcelable.Param(
         id = 4
      ) byte[] var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 2, this.a);
      SafeParcelWriter.writeInt(var1, 3, this.b);
      SafeParcelWriter.writeByteArray(var1, 4, this.c, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
