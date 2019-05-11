package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SafeParcelable.Class(
   creator = "ActivityTransitionCreator"
)
@SafeParcelable.Reserved({1000})
public class ActivityTransition extends AbstractSafeParcelable {

   public static final Creator<ActivityTransition> CREATOR = new ii();
   @SafeParcelable.Field(
      getter = "getActivityType",
      id = 1
   )
   private final int a;
   @SafeParcelable.Field(
      getter = "getTransitionType",
      id = 2
   )
   private final int b;


   @SafeParcelable.Constructor
   public ActivityTransition(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) int var2) {
      this.a = var1;
      this.b = var2;
   }

   public static void a(int var0) {
      boolean var1 = true;
      if(var0 < 0 || var0 > 1) {
         var1 = false;
      }

      StringBuilder var2 = new StringBuilder(41);
      var2.append("Transition type ");
      var2.append(var0);
      var2.append(" is not valid.");
      Preconditions.checkArgument(var1, var2.toString());
   }

   public int a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof ActivityTransition)) {
         return false;
      } else {
         ActivityTransition var2 = (ActivityTransition)var1;
         return this.a == var2.a && this.b == var2.b;
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{Integer.valueOf(this.a), Integer.valueOf(this.b)});
   }

   public String toString() {
      int var1 = this.a;
      int var2 = this.b;
      StringBuilder var3 = new StringBuilder(75);
      var3.append("ActivityTransition [mActivityType=");
      var3.append(var1);
      var3.append(", mTransitionType=");
      var3.append(var2);
      var3.append(']');
      return var3.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.a());
      SafeParcelWriter.writeInt(var1, 2, this.b());
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface SupportedActivityTransition {
   }
}
