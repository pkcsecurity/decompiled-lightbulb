package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Comparator;

@SafeParcelable.Class(
   creator = "DetectedActivityCreator"
)
@SafeParcelable.Reserved({1000})
public class DetectedActivity extends AbstractSafeParcelable {

   public static final Creator<DetectedActivity> CREATOR = new io();
   private static final Comparator<DetectedActivity> a = new in();
   private static final int[] b = new int[]{9, 10};
   private static final int[] c = new int[]{0, 1, 2, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 16, 17, 18, 19};
   private static final int[] d = new int[]{0, 1, 2, 3, 7, 8, 16, 17};
   @SafeParcelable.Field(
      id = 1
   )
   private int e;
   @SafeParcelable.Field(
      id = 2
   )
   private int f;


   @SafeParcelable.Constructor
   public DetectedActivity(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) int var2) {
      this.e = var1;
      this.f = var2;
   }

   public static void a(int var0) {
      int[] var4 = d;
      int var3 = var4.length;
      int var1 = 0;

      boolean var2;
      for(var2 = false; var1 < var3; ++var1) {
         if(var4[var1] == var0) {
            var2 = true;
         }
      }

      if(!var2) {
         StringBuilder var5 = new StringBuilder(81);
         var5.append(var0);
         var5.append(" is not a valid DetectedActivity supported by Activity Transition API.");
         Log.w("DetectedActivity", var5.toString());
      }

   }

   public int a() {
      int var1 = this.e;
      return var1 <= 19 && var1 >= 0?var1:4;
   }

   public int b() {
      return this.f;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else {
         if(var1 != null) {
            if(this.getClass() != var1.getClass()) {
               return false;
            }

            DetectedActivity var2 = (DetectedActivity)var1;
            if(this.e == var2.e && this.f == var2.f) {
               return true;
            }
         }

         return false;
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{Integer.valueOf(this.e), Integer.valueOf(this.f)});
   }

   public String toString() {
      int var1 = this.a();
      String var2;
      switch(var1) {
      case 0:
         var2 = "IN_VEHICLE";
         break;
      case 1:
         var2 = "ON_BICYCLE";
         break;
      case 2:
         var2 = "ON_FOOT";
         break;
      case 3:
         var2 = "STILL";
         break;
      case 4:
         var2 = "UNKNOWN";
         break;
      case 5:
         var2 = "TILTING";
         break;
      default:
         switch(var1) {
         case 7:
            var2 = "WALKING";
            break;
         case 8:
            var2 = "RUNNING";
            break;
         default:
            switch(var1) {
            case 16:
               var2 = "IN_ROAD_VEHICLE";
               break;
            case 17:
               var2 = "IN_RAIL_VEHICLE";
               break;
            case 18:
               var2 = "IN_TWO_WHEELER_VEHICLE";
               break;
            case 19:
               var2 = "IN_FOUR_WHEELER_VEHICLE";
               break;
            default:
               var2 = Integer.toString(var1);
            }
         }
      }

      var1 = this.f;
      StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 48);
      var3.append("DetectedActivity [type=");
      var3.append(var2);
      var3.append(", confidence=");
      var3.append(var1);
      var3.append("]");
      return var3.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.e);
      SafeParcelWriter.writeInt(var1, 2, this.f);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
