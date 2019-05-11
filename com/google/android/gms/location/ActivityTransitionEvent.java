package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.DetectedActivity;

@SafeParcelable.Class(
   creator = "ActivityTransitionEventCreator"
)
@SafeParcelable.Reserved({1000})
public class ActivityTransitionEvent extends AbstractSafeParcelable {

   public static final Creator<ActivityTransitionEvent> CREATOR = new ij();
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
   @SafeParcelable.Field(
      getter = "getElapsedRealTimeNanos",
      id = 3
   )
   private final long c;


   @SafeParcelable.Constructor
   public ActivityTransitionEvent(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) int var2, 
      @SafeParcelable.Param(
         id = 3
      ) long var3) {
      DetectedActivity.a(var1);
      ActivityTransition.a(var2);
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public int a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }

   public long c() {
      return this.c;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof ActivityTransitionEvent)) {
         return false;
      } else {
         ActivityTransitionEvent var2 = (ActivityTransitionEvent)var1;
         return this.a == var2.a && this.b == var2.b && this.c == var2.c;
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{Integer.valueOf(this.a), Integer.valueOf(this.b), Long.valueOf(this.c)});
   }

   public String toString() {
      StringBuilder var4 = new StringBuilder();
      int var1 = this.a;
      StringBuilder var5 = new StringBuilder(24);
      var5.append("ActivityType ");
      var5.append(var1);
      var4.append(var5.toString());
      var4.append(" ");
      var1 = this.b;
      var5 = new StringBuilder(26);
      var5.append("TransitionType ");
      var5.append(var1);
      var4.append(var5.toString());
      var4.append(" ");
      long var2 = this.c;
      var5 = new StringBuilder(41);
      var5.append("ElapsedRealTimeNanos ");
      var5.append(var2);
      var4.append(var5.toString());
      return var4.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.a());
      SafeParcelWriter.writeInt(var1, 2, this.b());
      SafeParcelWriter.writeLong(var1, 3, this.c());
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
