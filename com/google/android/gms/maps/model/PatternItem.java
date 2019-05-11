package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "PatternItemCreator"
)
@SafeParcelable.Reserved({1})
public class PatternItem extends AbstractSafeParcelable {

   public static final Creator<PatternItem> CREATOR = new jr();
   private static final String a = "PatternItem";
   @SafeParcelable.Field(
      getter = "getType",
      id = 2
   )
   private final int b;
   @Nullable
   @SafeParcelable.Field(
      getter = "getLength",
      id = 3
   )
   private final Float c;


   @SafeParcelable.Constructor
   public PatternItem(
      @SafeParcelable.Param(
         id = 2
      ) int var1, @Nullable 
      @SafeParcelable.Param(
         id = 3
      ) Float var2) {
      boolean var4 = true;
      boolean var3 = var4;
      if(var1 != 1) {
         if(var2 != null && var2.floatValue() >= 0.0F) {
            var3 = var4;
         } else {
            var3 = false;
         }
      }

      String var5 = String.valueOf(var2);
      StringBuilder var6 = new StringBuilder(String.valueOf(var5).length() + 45);
      var6.append("Invalid PatternItem: type=");
      var6.append(var1);
      var6.append(" length=");
      var6.append(var5);
      Preconditions.checkArgument(var3, var6.toString());
      this.b = var1;
      this.c = var2;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof PatternItem)) {
         return false;
      } else {
         PatternItem var2 = (PatternItem)var1;
         return this.b == var2.b && Objects.equal(this.c, var2.c);
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{Integer.valueOf(this.b), this.c});
   }

   public String toString() {
      int var1 = this.b;
      String var2 = String.valueOf(this.c);
      StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 39);
      var3.append("[PatternItem: type=");
      var3.append(var1);
      var3.append(" length=");
      var3.append(var2);
      var3.append("]");
      return var3.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 2, this.b);
      SafeParcelWriter.writeFloatObject(var1, 3, this.c, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
