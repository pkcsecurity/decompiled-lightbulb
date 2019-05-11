package com.google.android.gms.common.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.zan;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "ResolveAccountResponseCreator"
)
public class ResolveAccountResponse extends AbstractSafeParcelable {

   public static final Creator<ResolveAccountResponse> CREATOR = new zan();
   @SafeParcelable.Field(
      getter = "getConnectionResult",
      id = 3
   )
   private ConnectionResult zadh;
   @SafeParcelable.Field(
      getter = "getSaveDefaultAccount",
      id = 4
   )
   private boolean zagf;
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int zale;
   @SafeParcelable.Field(
      id = 2
   )
   private IBinder zanw;
   @SafeParcelable.Field(
      getter = "isFromCrossClientAuth",
      id = 5
   )
   private boolean zapb;


   public ResolveAccountResponse(int var1) {
      this(new ConnectionResult(var1, (PendingIntent)null));
   }

   @SafeParcelable.Constructor
   ResolveAccountResponse(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) IBinder var2, 
      @SafeParcelable.Param(
         id = 3
      ) ConnectionResult var3, 
      @SafeParcelable.Param(
         id = 4
      ) boolean var4, 
      @SafeParcelable.Param(
         id = 5
      ) boolean var5) {
      this.zale = var1;
      this.zanw = var2;
      this.zadh = var3;
      this.zagf = var4;
      this.zapb = var5;
   }

   public ResolveAccountResponse(ConnectionResult var1) {
      this(1, (IBinder)null, var1, false, false);
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof ResolveAccountResponse)) {
         return false;
      } else {
         ResolveAccountResponse var2 = (ResolveAccountResponse)var1;
         return this.zadh.equals(var2.zadh) && this.getAccountAccessor().equals(var2.getAccountAccessor());
      }
   }

   public IAccountAccessor getAccountAccessor() {
      return IAccountAccessor.Stub.asInterface(this.zanw);
   }

   public ConnectionResult getConnectionResult() {
      return this.zadh;
   }

   public boolean getSaveDefaultAccount() {
      return this.zagf;
   }

   public boolean isFromCrossClientAuth() {
      return this.zapb;
   }

   public ResolveAccountResponse setAccountAccessor(IAccountAccessor var1) {
      IBinder var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.asBinder();
      }

      this.zanw = var2;
      return this;
   }

   public ResolveAccountResponse setIsFromCrossClientAuth(boolean var1) {
      this.zapb = var1;
      return this;
   }

   public ResolveAccountResponse setSaveDefaultAccount(boolean var1) {
      this.zagf = var1;
      return this;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zale);
      SafeParcelWriter.writeIBinder(var1, 2, this.zanw, false);
      SafeParcelWriter.writeParcelable(var1, 3, this.getConnectionResult(), var2, false);
      SafeParcelWriter.writeBoolean(var1, 4, this.getSaveDefaultAccount());
      SafeParcelWriter.writeBoolean(var1, 5, this.isFromCrossClientAuth());
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
