package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.zza;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@KeepForSdk
@SafeParcelable.Class(
   creator = "ScopeCreator"
)
public final class Scope extends AbstractSafeParcelable implements ReflectedParcelable {

   public static final Creator<Scope> CREATOR = new zza();
   @SafeParcelable.Field(
      getter = "getScopeUri",
      id = 2
   )
   private final String zzap;
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int zzg;


   @SafeParcelable.Constructor
   Scope(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) String var2) {
      Preconditions.checkNotEmpty(var2, "scopeUri must not be null or empty");
      this.zzg = var1;
      this.zzap = var2;
   }

   public Scope(String var1) {
      this(1, var1);
   }

   public final boolean equals(Object var1) {
      return this == var1?true:(!(var1 instanceof Scope)?false:this.zzap.equals(((Scope)var1).zzap));
   }

   @KeepForSdk
   public final String getScopeUri() {
      return this.zzap;
   }

   public final int hashCode() {
      return this.zzap.hashCode();
   }

   public final String toString() {
      return this.zzap;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var2 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zzg);
      SafeParcelWriter.writeString(var1, 2, this.getScopeUri(), false);
      SafeParcelWriter.finishObjectHeader(var1, var2);
   }
}
