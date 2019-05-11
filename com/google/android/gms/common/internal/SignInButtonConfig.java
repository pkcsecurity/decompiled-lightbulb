package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zao;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(
   creator = "SignInButtonConfigCreator"
)
public class SignInButtonConfig extends AbstractSafeParcelable {

   public static final Creator<SignInButtonConfig> CREATOR = new zao();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int zale;
   @Deprecated
   @SafeParcelable.Field(
      getter = "getScopes",
      id = 4
   )
   private final Scope[] zanx;
   @SafeParcelable.Field(
      getter = "getButtonSize",
      id = 2
   )
   private final int zapc;
   @SafeParcelable.Field(
      getter = "getColorScheme",
      id = 3
   )
   private final int zapd;


   @SafeParcelable.Constructor
   SignInButtonConfig(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) int var2, 
      @SafeParcelable.Param(
         id = 3
      ) int var3, 
      @SafeParcelable.Param(
         id = 4
      ) Scope[] var4) {
      this.zale = var1;
      this.zapc = var2;
      this.zapd = var3;
      this.zanx = var4;
   }

   public SignInButtonConfig(int var1, int var2, Scope[] var3) {
      this(1, var1, var2, (Scope[])null);
   }

   public int getButtonSize() {
      return this.zapc;
   }

   public int getColorScheme() {
      return this.zapd;
   }

   @Deprecated
   public Scope[] getScopes() {
      return this.zanx;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.zale);
      SafeParcelWriter.writeInt(var1, 2, this.getButtonSize());
      SafeParcelWriter.writeInt(var1, 3, this.getColorScheme());
      SafeParcelWriter.writeTypedArray(var1, 4, this.getScopes(), var2, false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
