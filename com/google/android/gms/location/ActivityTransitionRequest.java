package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.ClientIdentity;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.ActivityTransition;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

@SafeParcelable.Class(
   creator = "ActivityTransitionRequestCreator"
)
@SafeParcelable.Reserved({1000})
public class ActivityTransitionRequest extends AbstractSafeParcelable {

   public static final Creator<ActivityTransitionRequest> CREATOR = new il();
   public static final Comparator<ActivityTransition> a = new ik();
   @SafeParcelable.Field(
      getter = "getActivityTransitions",
      id = 1
   )
   private final List<ActivityTransition> b;
   @Nullable
   @SafeParcelable.Field(
      getter = "getTag",
      id = 2
   )
   private final String c;
   @SafeParcelable.Field(
      getter = "getClients",
      id = 3
   )
   private final List<ClientIdentity> d;


   @SafeParcelable.Constructor
   public ActivityTransitionRequest(
      @SafeParcelable.Param(
         id = 1
      ) List<ActivityTransition> var1, @Nullable 
      @SafeParcelable.Param(
         id = 2
      ) String var2, @Nullable 
      @SafeParcelable.Param(
         id = 3
      ) List<ClientIdentity> var3) {
      Preconditions.checkNotNull(var1, "transitions can\'t be null");
      boolean var4;
      if(var1.size() > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "transitions can\'t be empty.");
      TreeSet var5 = new TreeSet(a);
      Iterator var6 = var1.iterator();

      while(var6.hasNext()) {
         ActivityTransition var7 = (ActivityTransition)var6.next();
         Preconditions.checkArgument(var5.add(var7), String.format("Found duplicated transition: %s.", new Object[]{var7}));
      }

      this.b = Collections.unmodifiableList(var1);
      this.c = var2;
      if(var3 == null) {
         var1 = Collections.emptyList();
      } else {
         var1 = Collections.unmodifiableList(var3);
      }

      this.d = var1;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else {
         if(var1 != null) {
            if(this.getClass() != var1.getClass()) {
               return false;
            }

            ActivityTransitionRequest var2 = (ActivityTransitionRequest)var1;
            if(Objects.equal(this.b, var2.b) && Objects.equal(this.c, var2.c) && Objects.equal(this.d, var2.d)) {
               return true;
            }
         }

         return false;
      }
   }

   public int hashCode() {
      int var3 = this.b.hashCode();
      String var4 = this.c;
      int var2 = 0;
      int var1;
      if(var4 != null) {
         var1 = this.c.hashCode();
      } else {
         var1 = 0;
      }

      if(this.d != null) {
         var2 = this.d.hashCode();
      }

      return (var3 * 31 + var1) * 31 + var2;
   }

   public String toString() {
      String var1 = String.valueOf(this.b);
      String var2 = this.c;
      String var3 = String.valueOf(this.d);
      StringBuilder var4 = new StringBuilder(String.valueOf(var1).length() + 61 + String.valueOf(var2).length() + String.valueOf(var3).length());
      var4.append("ActivityTransitionRequest [mTransitions=");
      var4.append(var1);
      var4.append(", mTag=\'");
      var4.append(var2);
      var4.append('\'');
      var4.append(", mClients=");
      var4.append(var3);
      var4.append(']');
      return var4.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 1, this.b, false);
      SafeParcelWriter.writeString(var1, 2, this.c, false);
      SafeParcelWriter.writeTypedList(var1, 3, this.d, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
