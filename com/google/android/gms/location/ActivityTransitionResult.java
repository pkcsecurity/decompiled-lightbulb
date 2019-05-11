package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.location.ActivityTransitionEvent;
import java.util.Collections;
import java.util.List;

@SafeParcelable.Class(
   creator = "ActivityTransitionResultCreator"
)
@SafeParcelable.Reserved({1000})
public class ActivityTransitionResult extends AbstractSafeParcelable {

   public static final Creator<ActivityTransitionResult> CREATOR = new im();
   @SafeParcelable.Field(
      getter = "getTransitionEvents",
      id = 1
   )
   private final List<ActivityTransitionEvent> a;


   @SafeParcelable.Constructor
   public ActivityTransitionResult(
      @SafeParcelable.Param(
         id = 1
      ) List<ActivityTransitionEvent> var1) {
      Preconditions.checkNotNull(var1, "transitionEvents list can\'t be null.");
      if(!var1.isEmpty()) {
         for(int var2 = 1; var2 < var1.size(); ++var2) {
            boolean var3;
            if(((ActivityTransitionEvent)var1.get(var2)).c() >= ((ActivityTransitionEvent)var1.get(var2 - 1)).c()) {
               var3 = true;
            } else {
               var3 = false;
            }

            Preconditions.checkArgument(var3);
         }
      }

      this.a = Collections.unmodifiableList(var1);
   }

   public List<ActivityTransitionEvent> a() {
      return this.a;
   }

   public boolean equals(Object var1) {
      return this == var1?true:(var1 != null && this.getClass() == var1.getClass()?this.a.equals(((ActivityTransitionResult)var1).a):false);
   }

   public int hashCode() {
      return this.a.hashCode();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeTypedList(var1, 1, this.a(), false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
