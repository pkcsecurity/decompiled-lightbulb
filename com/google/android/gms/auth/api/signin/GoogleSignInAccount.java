package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SafeParcelable.Class(
   creator = "GoogleSignInAccountCreator"
)
public class GoogleSignInAccount extends AbstractSafeParcelable implements ReflectedParcelable {

   public static final Creator<GoogleSignInAccount> CREATOR = new fi();
   @VisibleForTesting
   private static Clock a = DefaultClock.getInstance();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int b;
   @SafeParcelable.Field(
      getter = "getId",
      id = 2
   )
   private String c;
   @SafeParcelable.Field(
      getter = "getIdToken",
      id = 3
   )
   private String d;
   @SafeParcelable.Field(
      getter = "getEmail",
      id = 4
   )
   private String e;
   @SafeParcelable.Field(
      getter = "getDisplayName",
      id = 5
   )
   private String f;
   @SafeParcelable.Field(
      getter = "getPhotoUrl",
      id = 6
   )
   private Uri g;
   @SafeParcelable.Field(
      getter = "getServerAuthCode",
      id = 7
   )
   private String h;
   @SafeParcelable.Field(
      getter = "getExpirationTimeSecs",
      id = 8
   )
   private long i;
   @SafeParcelable.Field(
      getter = "getObfuscatedIdentifier",
      id = 9
   )
   private String j;
   @SafeParcelable.Field(
      id = 10
   )
   private List<Scope> k;
   @SafeParcelable.Field(
      getter = "getGivenName",
      id = 11
   )
   private String l;
   @SafeParcelable.Field(
      getter = "getFamilyName",
      id = 12
   )
   private String m;
   private Set<Scope> n = new HashSet();


   @SafeParcelable.Constructor
   public GoogleSignInAccount(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) String var2, 
      @SafeParcelable.Param(
         id = 3
      ) String var3, 
      @SafeParcelable.Param(
         id = 4
      ) String var4, 
      @SafeParcelable.Param(
         id = 5
      ) String var5, 
      @SafeParcelable.Param(
         id = 6
      ) Uri var6, 
      @SafeParcelable.Param(
         id = 7
      ) String var7, 
      @SafeParcelable.Param(
         id = 8
      ) long var8, 
      @SafeParcelable.Param(
         id = 9
      ) String var10, 
      @SafeParcelable.Param(
         id = 10
      ) List<Scope> var11, 
      @SafeParcelable.Param(
         id = 11
      ) String var12, 
      @SafeParcelable.Param(
         id = 12
      ) String var13) {
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
      this.g = var6;
      this.h = var7;
      this.i = var8;
      this.j = var10;
      this.k = var11;
      this.l = var12;
      this.m = var13;
   }

   @Nullable
   public static GoogleSignInAccount a(@Nullable String var0) throws JSONException {
      if(TextUtils.isEmpty(var0)) {
         return null;
      } else {
         JSONObject var5 = new JSONObject(var0);
         var0 = var5.optString("photoUrl", (String)null);
         Uri var8;
         if(!TextUtils.isEmpty(var0)) {
            var8 = Uri.parse(var0);
         } else {
            var8 = null;
         }

         long var3 = Long.parseLong(var5.getString("expirationTime"));
         HashSet var6 = new HashSet();
         JSONArray var7 = var5.getJSONArray("grantedScopes");
         int var2 = var7.length();

         for(int var1 = 0; var1 < var2; ++var1) {
            var6.add(new Scope(var7.getString(var1)));
         }

         GoogleSignInAccount var9 = a(var5.optString("id"), var5.optString("tokenId", (String)null), var5.optString("email", (String)null), var5.optString("displayName", (String)null), var5.optString("givenName", (String)null), var5.optString("familyName", (String)null), var8, Long.valueOf(var3), var5.getString("obfuscatedIdentifier"), var6);
         var9.h = var5.optString("serverAuthCode", (String)null);
         return var9;
      }
   }

   private static GoogleSignInAccount a(@Nullable String var0, @Nullable String var1, @Nullable String var2, @Nullable String var3, @Nullable String var4, @Nullable String var5, @Nullable Uri var6, @Nullable Long var7, @NonNull String var8, @NonNull Set<Scope> var9) {
      if(var7 == null) {
         var7 = Long.valueOf(a.currentTimeMillis() / 1000L);
      }

      return new GoogleSignInAccount(3, var0, var1, var2, var3, var6, (String)null, var7.longValue(), Preconditions.checkNotEmpty(var8), new ArrayList((Collection)Preconditions.checkNotNull(var9)), var4, var5);
   }

   @Nullable
   public String a() {
      return this.c;
   }

   @Nullable
   public String b() {
      return this.d;
   }

   @Nullable
   public String c() {
      return this.e;
   }

   @Nullable
   public Account d() {
      return this.e == null?null:new Account(this.e, "com.google");
   }

   @Nullable
   public String e() {
      return this.f;
   }

   public boolean equals(Object var1) {
      if(var1 == this) {
         return true;
      } else if(!(var1 instanceof GoogleSignInAccount)) {
         return false;
      } else {
         GoogleSignInAccount var2 = (GoogleSignInAccount)var1;
         return var2.j.equals(this.j) && var2.j().equals(this.j());
      }
   }

   @Nullable
   public String f() {
      return this.l;
   }

   @Nullable
   public String g() {
      return this.m;
   }

   @Nullable
   public Uri h() {
      return this.g;
   }

   public int hashCode() {
      return (this.j.hashCode() + 527) * 31 + this.j().hashCode();
   }

   @Nullable
   public String i() {
      return this.h;
   }

   @NonNull
   @KeepForSdk
   public Set<Scope> j() {
      HashSet var1 = new HashSet(this.k);
      var1.addAll(this.n);
      return var1;
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.b);
      SafeParcelWriter.writeString(var1, 2, this.a(), false);
      SafeParcelWriter.writeString(var1, 3, this.b(), false);
      SafeParcelWriter.writeString(var1, 4, this.c(), false);
      SafeParcelWriter.writeString(var1, 5, this.e(), false);
      SafeParcelWriter.writeParcelable(var1, 6, this.h(), var2, false);
      SafeParcelWriter.writeString(var1, 7, this.i(), false);
      SafeParcelWriter.writeLong(var1, 8, this.i);
      SafeParcelWriter.writeString(var1, 9, this.j, false);
      SafeParcelWriter.writeTypedList(var1, 10, this.k, false);
      SafeParcelWriter.writeString(var1, 11, this.f(), false);
      SafeParcelWriter.writeString(var1, 12, this.g(), false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }
}
