package android.support.v4.view;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class AbsSavedState implements Parcelable {

   public static final Creator<AbsSavedState> CREATOR = new ClassLoaderCreator() {
      public AbsSavedState createFromParcel(Parcel var1) {
         return this.createFromParcel(var1, (ClassLoader)null);
      }
      public AbsSavedState createFromParcel(Parcel var1, ClassLoader var2) {
         if(var1.readParcelable(var2) != null) {
            throw new IllegalStateException("superState must be null");
         } else {
            return AbsSavedState.EMPTY_STATE;
         }
      }
      public AbsSavedState[] newArray(int var1) {
         return new AbsSavedState[var1];
      }
   };
   public static final AbsSavedState EMPTY_STATE = new AbsSavedState(null) {
   };
   private final Parcelable mSuperState;


   private AbsSavedState() {
      this.mSuperState = null;
   }

   protected AbsSavedState(@NonNull Parcel var1) {
      this(var1, (ClassLoader)null);
   }

   protected AbsSavedState(@NonNull Parcel var1, @Nullable ClassLoader var2) {
      Object var3 = var1.readParcelable(var2);
      if(var3 == null) {
         var3 = EMPTY_STATE;
      }

      this.mSuperState = (Parcelable)var3;
   }

   protected AbsSavedState(@NonNull Parcelable var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("superState must not be null");
      } else {
         if(var1 == EMPTY_STATE) {
            var1 = null;
         }

         this.mSuperState = var1;
      }
   }

   // $FF: synthetic method
   AbsSavedState(Object var1) {
      this();
   }

   public int describeContents() {
      return 0;
   }

   @Nullable
   public final Parcelable getSuperState() {
      return this.mSuperState;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeParcelable(this.mSuperState, var2);
   }
}
