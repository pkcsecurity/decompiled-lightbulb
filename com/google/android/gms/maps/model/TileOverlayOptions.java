package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.maps.zzaf;
import com.google.android.gms.internal.maps.zzag;
import com.google.android.gms.maps.model.TileProvider;

@SafeParcelable.Class(
   creator = "TileOverlayOptionsCreator"
)
@SafeParcelable.Reserved({1})
public final class TileOverlayOptions extends AbstractSafeParcelable {

   public static final Creator<TileOverlayOptions> CREATOR = new kc();
   @SafeParcelable.Field(
      getter = "getTileProviderDelegate",
      id = 2,
      type = "android.os.IBinder"
   )
   private zzaf a;
   private TileProvider b;
   @SafeParcelable.Field(
      getter = "isVisible",
      id = 3
   )
   private boolean c = true;
   @SafeParcelable.Field(
      getter = "getZIndex",
      id = 4
   )
   private float d;
   @SafeParcelable.Field(
      defaultValue = "true",
      getter = "getFadeIn",
      id = 5
   )
   private boolean e = true;
   @SafeParcelable.Field(
      getter = "getTransparency",
      id = 6
   )
   private float f = 0.0F;


   public TileOverlayOptions() {}

   @SafeParcelable.Constructor
   public TileOverlayOptions(
      @SafeParcelable.Param(
         id = 2
      ) IBinder var1, 
      @SafeParcelable.Param(
         id = 3
      ) boolean var2, 
      @SafeParcelable.Param(
         id = 4
      ) float var3, 
      @SafeParcelable.Param(
         id = 5
      ) boolean var4, 
      @SafeParcelable.Param(
         id = 6
      ) float var5) {
      this.a = zzag.a(var1);
      kb var6;
      if(this.a == null) {
         var6 = null;
      } else {
         var6 = new kb(this);
      }

      this.b = var6;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
   }

   // $FF: synthetic method
   public static zzaf a(TileOverlayOptions var0) {
      return var0.a;
   }

   public final float a() {
      return this.d;
   }

   public final boolean b() {
      return this.c;
   }

   public final boolean c() {
      return this.e;
   }

   public final float d() {
      return this.f;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeIBinder(var1, 2, this.a.asBinder(), false);
      SafeParcelWriter.writeBoolean(var1, 3, this.b());
      SafeParcelWriter.writeFloat(var1, 4, this.a());
      SafeParcelWriter.writeBoolean(var1, 5, this.c());
      SafeParcelWriter.writeFloat(var1, 6, this.d());
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
