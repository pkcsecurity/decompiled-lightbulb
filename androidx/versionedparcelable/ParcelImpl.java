package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.RestrictTo;
import androidx.versionedparcelable.VersionedParcelable;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public class ParcelImpl implements Parcelable {

   public static final Creator<ParcelImpl> CREATOR = new Creator() {
      public ParcelImpl a(Parcel var1) {
         return new ParcelImpl(var1);
      }
      public ParcelImpl[] a(int var1) {
         return new ParcelImpl[var1];
      }
      // $FF: synthetic method
      public Object createFromParcel(Parcel var1) {
         return this.a(var1);
      }
      // $FF: synthetic method
      public Object[] newArray(int var1) {
         return this.a(var1);
      }
   };
   private final VersionedParcelable a;


   protected ParcelImpl(Parcel var1) {
      this.a = (new r(var1)).h();
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel var1, int var2) {
      (new r(var1)).a(this.a);
   }
}
