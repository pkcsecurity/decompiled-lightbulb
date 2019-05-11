package com.google.android.gms.location;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.DetectedActivity;
import java.util.Iterator;
import java.util.List;

@SafeParcelable.Class(
   creator = "ActivityRecognitionResultCreator"
)
@SafeParcelable.Reserved({1000})
public class ActivityRecognitionResult extends AbstractSafeParcelable implements ReflectedParcelable {

   public static final Creator<ActivityRecognitionResult> CREATOR = new ih();
   @SafeParcelable.Field(
      id = 1
   )
   private List<DetectedActivity> a;
   @SafeParcelable.Field(
      id = 2
   )
   private long b;
   @SafeParcelable.Field(
      id = 3
   )
   private long c;
   @SafeParcelable.Field(
      id = 4
   )
   private int d;
   @SafeParcelable.Field(
      id = 5
   )
   private Bundle e;


   @SafeParcelable.Constructor
   public ActivityRecognitionResult(
      @SafeParcelable.Param(
         id = 1
      ) List<DetectedActivity> var1, 
      @SafeParcelable.Param(
         id = 2
      ) long var2, 
      @SafeParcelable.Param(
         id = 3
      ) long var4, 
      @SafeParcelable.Param(
         id = 4
      ) int var6, 
      @SafeParcelable.Param(
         id = 5
      ) Bundle var7) {
      boolean var9 = false;
      boolean var8;
      if(var1 != null && var1.size() > 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      Preconditions.checkArgument(var8, "Must have at least 1 detected activity");
      var8 = var9;
      if(var2 > 0L) {
         var8 = var9;
         if(var4 > 0L) {
            var8 = true;
         }
      }

      Preconditions.checkArgument(var8, "Must set times");
      this.a = var1;
      this.b = var2;
      this.c = var4;
      this.d = var6;
      this.e = var7;
   }

   private static boolean a(Bundle var0, Bundle var1) {
      if(var0 == null && var1 == null) {
         return true;
      } else if((var0 != null || var1 == null) && (var0 == null || var1 != null)) {
         if(var0.size() != var1.size()) {
            return false;
         } else {
            Iterator var2 = var0.keySet().iterator();

            while(var2.hasNext()) {
               String var3 = (String)var2.next();
               if(!var1.containsKey(var3)) {
                  return false;
               }

               if(var0.get(var3) == null) {
                  if(var1.get(var3) != null) {
                     return false;
                  }
               } else if(var0.get(var3) instanceof Bundle) {
                  if(!a(var0.getBundle(var3), var1.getBundle(var3))) {
                     return false;
                  }
               } else if(!var0.get(var3).equals(var1.get(var3))) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else {
         if(var1 != null) {
            if(this.getClass() != var1.getClass()) {
               return false;
            }

            ActivityRecognitionResult var2 = (ActivityRecognitionResult)var1;
            if(this.b == var2.b && this.c == var2.c && this.d == var2.d && Objects.equal(this.a, var2.a) && a(this.e, var2.e)) {
               return true;
            }
         }

         return false;
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{Long.valueOf(this.b), Long.valueOf(this.c), Integer.valueOf(this.d), this.a, this.e});
   }

   public String toString() {
      String var5 = String.valueOf(this.a);
      long var1 = this.b;
      long var3 = this.c;
      StringBuilder var6 = new StringBuilder(String.valueOf(var5).length() + 124);
      var6.append("ActivityRecognitionResult [probableActivities=");
      var6.append(var5);
      var6.append(", timeMillis=");
      var6.append(var1);
      var6.append(", elapsedRealtimeMillis=");
      var6.append(var3);
      var6.append("]");
      return var6.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 1, this.a, false);
      SafeParcelWriter.writeLong(var1, 2, this.b);
      SafeParcelWriter.writeLong(var1, 3, this.c);
      SafeParcelWriter.writeInt(var1, 4, this.d);
      SafeParcelWriter.writeBundle(var1, 5, this.e, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
