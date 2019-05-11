package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.zab;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@KeepForSdk
@SafeParcelable.Class(
   creator = "ClientIdentityCreator"
)
@SafeParcelable.Reserved({1000})
public class ClientIdentity extends AbstractSafeParcelable {

   @KeepForSdk
   public static final Creator<ClientIdentity> CREATOR = new zab();
   @Nullable
   @SafeParcelable.Field(
      defaultValueUnchecked = "null",
      id = 2
   )
   private final String packageName;
   @SafeParcelable.Field(
      defaultValueUnchecked = "0",
      id = 1
   )
   private final int uid;


   @SafeParcelable.Constructor
   public ClientIdentity(
      @SafeParcelable.Param(
         id = 1
      ) int var1, @Nullable 
      @SafeParcelable.Param(
         id = 2
      ) String var2) {
      this.uid = var1;
      this.packageName = var2;
   }

   public boolean equals(Object var1) {
      if(var1 == this) {
         return true;
      } else if(var1 != null) {
         if(!(var1 instanceof ClientIdentity)) {
            return false;
         } else {
            ClientIdentity var2 = (ClientIdentity)var1;
            return var2.uid == this.uid && Objects.equal(var2.packageName, this.packageName);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.uid;
   }

   public String toString() {
      int var1 = this.uid;
      String var2 = this.packageName;
      StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 12);
      var3.append(var1);
      var3.append(":");
      var3.append(var2);
      return var3.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.uid);
      SafeParcelWriter.writeString(var1, 2, this.packageName, false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
