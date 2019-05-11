package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.AccountAccessor;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zaa;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;

@SafeParcelable.Class(
   creator = "AuthAccountRequestCreator"
)
public class AuthAccountRequest extends AbstractSafeParcelable {

   public static final Creator<AuthAccountRequest> CREATOR = new zaa();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int zale;
   @Deprecated
   @SafeParcelable.Field(
      id = 2
   )
   private final IBinder zanw;
   @SafeParcelable.Field(
      id = 3
   )
   private final Scope[] zanx;
   @SafeParcelable.Field(
      id = 4
   )
   private Integer zany;
   @SafeParcelable.Field(
      id = 5
   )
   private Integer zanz;
   @SafeParcelable.Field(
      id = 6,
      type = "android.accounts.Account"
   )
   private Account zax;


   @SafeParcelable.Constructor
   AuthAccountRequest(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) IBinder var2, 
      @SafeParcelable.Param(
         id = 3
      ) Scope[] var3, 
      @SafeParcelable.Param(
         id = 4
      ) Integer var4, 
      @SafeParcelable.Param(
         id = 5
      ) Integer var5, 
      @SafeParcelable.Param(
         id = 6
      ) Account var6) {
      this.zale = var1;
      this.zanw = var2;
      this.zanx = var3;
      this.zany = var4;
      this.zanz = var5;
      this.zax = var6;
   }

   public AuthAccountRequest(Account var1, Set<Scope> var2) {
      this(3, (IBinder)null, (Scope[])var2.toArray(new Scope[var2.size()]), (Integer)null, (Integer)null, (Account)Preconditions.checkNotNull(var1));
   }

   @Deprecated
   public AuthAccountRequest(IAccountAccessor var1, Set<Scope> var2) {
      this(3, var1.asBinder(), (Scope[])var2.toArray(new Scope[var2.size()]), (Integer)null, (Integer)null, (Account)null);
   }

   public Account getAccount() {
      return this.zax != null?this.zax:(this.zanw != null?AccountAccessor.getAccountBinderSafe(IAccountAccessor.Stub.asInterface(this.zanw)):null);
   }

   @Nullable
   public Integer getOauthPolicy() {
      return this.zany;
   }

   @Nullable
   public Integer getPolicyAction() {
      return this.zanz;
   }

   public Set<Scope> getScopes() {
      return new HashSet(Arrays.asList(this.zanx));
   }

   public AuthAccountRequest setOauthPolicy(@Nullable Integer var1) {
      this.zany = var1;
      return this;
   }

   public AuthAccountRequest setPolicyAction(@Nullable Integer var1) {
      this.zanz = var1;
      return this;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zale);
      SafeParcelWriter.writeIBinder(var1, 2, this.zanw, false);
      SafeParcelWriter.writeTypedArray(var1, 3, this.zanx, var2, false);
      SafeParcelWriter.writeIntegerObject(var1, 4, this.zany, false);
      SafeParcelWriter.writeIntegerObject(var1, 5, this.zanz, false);
      SafeParcelWriter.writeParcelable(var1, 6, this.zax, var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
