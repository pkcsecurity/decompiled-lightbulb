package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.zza;

@KeepForSdk
@KeepName
public final class BinderWrapper implements Parcelable {

   public static final Creator<BinderWrapper> CREATOR = new zza();
   private IBinder zzcy;


   public BinderWrapper() {
      this.zzcy = null;
   }

   @KeepForSdk
   public BinderWrapper(IBinder var1) {
      this.zzcy = null;
      this.zzcy = var1;
   }

   private BinderWrapper(Parcel var1) {
      this.zzcy = null;
      this.zzcy = var1.readStrongBinder();
   }

   // $FF: synthetic method
   BinderWrapper(Parcel var1, zza var2) {
      this(var1);
   }

   public final int describeContents() {
      return 0;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      var1.writeStrongBinder(this.zzcy);
   }
}
