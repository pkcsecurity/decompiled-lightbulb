package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.IObjectWrapper;

@SafeParcelable.Class(
   creator = "CapCreator"
)
@SafeParcelable.Reserved({1})
public class Cap extends AbstractSafeParcelable {

   public static final Creator<Cap> CREATOR = new jk();
   private static final String a = "Cap";
   @SafeParcelable.Field(
      getter = "getType",
      id = 2
   )
   private final int b;
   @Nullable
   @SafeParcelable.Field(
      getter = "getWrappedBitmapDescriptorImplBinder",
      id = 3,
      type = "android.os.IBinder"
   )
   private final jc c;
   @Nullable
   @SafeParcelable.Field(
      getter = "getBitmapRefWidth",
      id = 4
   )
   private final Float d;


   protected Cap(int var1) {
      this(var1, (jc)null, (Float)null);
   }

   @SafeParcelable.Constructor
   public Cap(
      @SafeParcelable.Param(
         id = 2
      ) int var1, @Nullable 
      @SafeParcelable.Param(
         id = 3
      ) IBinder var2, @Nullable 
      @SafeParcelable.Param(
         id = 4
      ) Float var3) {
      jc var4;
      if(var2 == null) {
         var4 = null;
      } else {
         var4 = new jc(IObjectWrapper.Stub.a(var2));
      }

      this(var1, var4, var3);
   }

   private Cap(int var1, @Nullable jc var2, @Nullable Float var3) {
      boolean var4;
      if(var3 != null && var3.floatValue() > 0.0F) {
         var4 = true;
      } else {
         var4 = false;
      }

      boolean var5;
      if(var1 == 3 && (var2 == null || !var4)) {
         var5 = false;
      } else {
         var5 = true;
      }

      Preconditions.checkArgument(var5, String.format("Invalid Cap: type=%s bitmapDescriptor=%s bitmapRefWidth=%s", new Object[]{Integer.valueOf(var1), var2, var3}));
      this.b = var1;
      this.c = var2;
      this.d = var3;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof Cap)) {
         return false;
      } else {
         Cap var2 = (Cap)var1;
         return this.b == var2.b && Objects.equal(this.c, var2.c) && Objects.equal(this.d, var2.d);
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{Integer.valueOf(this.b), this.c, this.d});
   }

   public String toString() {
      int var1 = this.b;
      StringBuilder var2 = new StringBuilder(23);
      var2.append("[Cap: type=");
      var2.append(var1);
      var2.append("]");
      return var2.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 2, this.b);
      IBinder var3;
      if(this.c == null) {
         var3 = null;
      } else {
         var3 = this.c.a().asBinder();
      }

      SafeParcelWriter.writeIBinder(var1, 3, var3, false);
      SafeParcelWriter.writeFloatObject(var1, 4, this.d, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
