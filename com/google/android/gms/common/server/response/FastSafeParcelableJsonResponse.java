package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Iterator;

@KeepForSdk
@ShowFirstParty
public abstract class FastSafeParcelableJsonResponse extends FastJsonResponse implements SafeParcelable {

   public final int describeContents() {
      return 0;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!this.getClass().isInstance(var1)) {
         return false;
      } else {
         FastJsonResponse var4 = (FastJsonResponse)var1;
         Iterator var2 = this.getFieldMappings().values().iterator();

         while(var2.hasNext()) {
            FastJsonResponse.Field var3 = (FastJsonResponse.Field)var2.next();
            if(this.isFieldSet(var3)) {
               if(!var4.isFieldSet(var3)) {
                  return false;
               }

               if(!this.getFieldValue(var3).equals(var4.getFieldValue(var3))) {
                  return false;
               }
            } else if(var4.isFieldSet(var3)) {
               return false;
            }
         }

         return true;
      }
   }

   @VisibleForTesting
   public Object getValueObject(String var1) {
      return null;
   }

   public int hashCode() {
      Iterator var2 = this.getFieldMappings().values().iterator();
      int var1 = 0;

      while(var2.hasNext()) {
         FastJsonResponse.Field var3 = (FastJsonResponse.Field)var2.next();
         if(this.isFieldSet(var3)) {
            var1 = var1 * 31 + this.getFieldValue(var3).hashCode();
         }
      }

      return var1;
   }

   @VisibleForTesting
   public boolean isPrimitiveFieldSet(String var1) {
      return false;
   }

   @KeepForSdk
   public byte[] toByteArray() {
      Parcel var1 = Parcel.obtain();
      this.writeToParcel(var1, 0);
      byte[] var2 = var1.marshall();
      var1.recycle();
      return var2;
   }
}
