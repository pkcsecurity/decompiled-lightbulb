package com.google.android.gms.signin.internal;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "SignInResponseCreator"
)
public final class zaj extends AbstractSafeParcelable {

   public static final Creator<zaj> CREATOR = new lb();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int a;
   @SafeParcelable.Field(
      getter = "getConnectionResult",
      id = 2
   )
   private final ConnectionResult b;
   @SafeParcelable.Field(
      getter = "getResolveAccountResponse",
      id = 3
   )
   private final ResolveAccountResponse c;


   public zaj(int var1) {
      this(new ConnectionResult(8, (PendingIntent)null), (ResolveAccountResponse)null);
   }

   @SafeParcelable.Constructor
   public zaj(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) ConnectionResult var2, 
      @SafeParcelable.Param(
         id = 3
      ) ResolveAccountResponse var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   private zaj(ConnectionResult var1, ResolveAccountResponse var2) {
      this(1, var1, (ResolveAccountResponse)null);
   }

   public final ConnectionResult a() {
      return this.b;
   }

   public final ResolveAccountResponse b() {
      return this.c;
   }

   public final void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.a);
      SafeParcelWriter.writeParcelable(var1, 2, this.b, var2, false);
      SafeParcelWriter.writeParcelable(var1, 3, this.c, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
