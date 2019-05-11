package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.zzb;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@KeepForSdk
@SafeParcelable.Class(
   creator = "FeatureCreator"
)
public class Feature extends AbstractSafeParcelable {

   public static final Creator<Feature> CREATOR = new zzb();
   @SafeParcelable.Field(
      getter = "getName",
      id = 1
   )
   private final String name;
   @Deprecated
   @SafeParcelable.Field(
      getter = "getOldVersion",
      id = 2
   )
   private final int zzk;
   @SafeParcelable.Field(
      defaultValue = "-1",
      getter = "getVersion",
      id = 3
   )
   private final long zzl;


   @SafeParcelable.Constructor
   public Feature(
      @SafeParcelable.Param(
         id = 1
      ) String var1, 
      @SafeParcelable.Param(
         id = 2
      ) int var2, 
      @SafeParcelable.Param(
         id = 3
      ) long var3) {
      this.name = var1;
      this.zzk = var2;
      this.zzl = var3;
   }

   @KeepForSdk
   public Feature(String var1, long var2) {
      this.name = var1;
      this.zzl = var2;
      this.zzk = -1;
   }

   public boolean equals(@Nullable Object var1) {
      if(!(var1 instanceof Feature)) {
         return false;
      } else {
         Feature var2 = (Feature)var1;
         return (this.getName() != null && this.getName().equals(var2.getName()) || this.getName() == null && var2.getName() == null) && this.getVersion() == var2.getVersion();
      }
   }

   @KeepForSdk
   public String getName() {
      return this.name;
   }

   @KeepForSdk
   public long getVersion() {
      return this.zzl == -1L?(long)this.zzk:this.zzl;
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.getName(), Long.valueOf(this.getVersion())});
   }

   public String toString() {
      return Objects.toStringHelper(this).add("name", this.getName()).add("version", Long.valueOf(this.getVersion())).toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeString(var1, 1, this.getName(), false);
      SafeParcelWriter.writeInt(var1, 2, this.zzk);
      SafeParcelWriter.writeLong(var1, 3, this.getVersion());
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
