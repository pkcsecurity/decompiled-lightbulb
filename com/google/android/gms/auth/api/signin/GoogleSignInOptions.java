package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SafeParcelable.Class(
   creator = "GoogleSignInOptionsCreator"
)
public class GoogleSignInOptions extends AbstractSafeParcelable implements Api.Optional, ReflectedParcelable {

   public static final Creator<GoogleSignInOptions> CREATOR = new fk();
   @VisibleForTesting
   public static final Scope a = new Scope("profile");
   @VisibleForTesting
   public static final Scope b = new Scope("email");
   @VisibleForTesting
   public static final Scope c = new Scope("openid");
   @VisibleForTesting
   public static final Scope d = new Scope("https://www.googleapis.com/auth/games_lite");
   @VisibleForTesting
   public static final Scope e = new Scope("https://www.googleapis.com/auth/games");
   public static final GoogleSignInOptions f = (new GoogleSignInOptions.a()).a().b().c();
   public static final GoogleSignInOptions g = (new GoogleSignInOptions.a()).a(d, new Scope[0]).c();
   private static Comparator<Scope> r = new fj();
   @SafeParcelable.VersionField(
      id = 1
   )
   private final int h;
   @SafeParcelable.Field(
      getter = "getScopes",
      id = 2
   )
   private final ArrayList<Scope> i;
   @SafeParcelable.Field(
      getter = "getAccount",
      id = 3
   )
   private Account j;
   @SafeParcelable.Field(
      getter = "isIdTokenRequested",
      id = 4
   )
   private boolean k;
   @SafeParcelable.Field(
      getter = "isServerAuthCodeRequested",
      id = 5
   )
   private final boolean l;
   @SafeParcelable.Field(
      getter = "isForceCodeForRefreshToken",
      id = 6
   )
   private final boolean m;
   @SafeParcelable.Field(
      getter = "getServerClientId",
      id = 7
   )
   private String n;
   @SafeParcelable.Field(
      getter = "getHostedDomain",
      id = 8
   )
   private String o;
   @SafeParcelable.Field(
      getter = "getExtensions",
      id = 9
   )
   private ArrayList<GoogleSignInOptionsExtensionParcelable> p;
   private Map<Integer, GoogleSignInOptionsExtensionParcelable> q;


   @SafeParcelable.Constructor
   public GoogleSignInOptions(
      @SafeParcelable.Param(
         id = 1
      ) int var1, 
      @SafeParcelable.Param(
         id = 2
      ) ArrayList<Scope> var2, 
      @SafeParcelable.Param(
         id = 3
      ) Account var3, 
      @SafeParcelable.Param(
         id = 4
      ) boolean var4, 
      @SafeParcelable.Param(
         id = 5
      ) boolean var5, 
      @SafeParcelable.Param(
         id = 6
      ) boolean var6, 
      @SafeParcelable.Param(
         id = 7
      ) String var7, 
      @SafeParcelable.Param(
         id = 8
      ) String var8, 
      @SafeParcelable.Param(
         id = 9
      ) ArrayList<GoogleSignInOptionsExtensionParcelable> var9) {
      this(var1, var2, var3, var4, var5, var6, var7, var8, a(var9));
   }

   private GoogleSignInOptions(int var1, ArrayList<Scope> var2, Account var3, boolean var4, boolean var5, boolean var6, String var7, String var8, Map<Integer, GoogleSignInOptionsExtensionParcelable> var9) {
      this.h = var1;
      this.i = var2;
      this.j = var3;
      this.k = var4;
      this.l = var5;
      this.m = var6;
      this.n = var7;
      this.o = var8;
      this.p = new ArrayList(var9.values());
      this.q = var9;
   }

   // $FF: synthetic method
   GoogleSignInOptions(int var1, ArrayList var2, Account var3, boolean var4, boolean var5, boolean var6, String var7, String var8, Map var9, fj var10) {
      this(3, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   private static Map<Integer, GoogleSignInOptionsExtensionParcelable> a(@Nullable List<GoogleSignInOptionsExtensionParcelable> var0) {
      HashMap var1 = new HashMap();
      if(var0 == null) {
         return var1;
      } else {
         Iterator var3 = var0.iterator();

         while(var3.hasNext()) {
            GoogleSignInOptionsExtensionParcelable var2 = (GoogleSignInOptionsExtensionParcelable)var3.next();
            var1.put(Integer.valueOf(var2.a()), var2);
         }

         return var1;
      }
   }

   @KeepForSdk
   public ArrayList<Scope> a() {
      return new ArrayList(this.i);
   }

   @KeepForSdk
   public Account b() {
      return this.j;
   }

   @KeepForSdk
   public boolean c() {
      return this.k;
   }

   @KeepForSdk
   public boolean d() {
      return this.l;
   }

   @KeepForSdk
   public boolean e() {
      return this.m;
   }

   public boolean equals(Object var1) {
      if(var1 == null) {
         return false;
      } else {
         boolean var2;
         boolean var3;
         try {
            GoogleSignInOptions var5 = (GoogleSignInOptions)var1;
            if(this.p.size() > 0) {
               return false;
            }

            if(var5.p.size() > 0) {
               return false;
            }

            if(this.i.size() != var5.a().size()) {
               return false;
            }

            if(!this.i.containsAll(var5.a())) {
               return false;
            }

            if(this.j == null) {
               if(var5.b() != null) {
                  return false;
               }
            } else if(!this.j.equals(var5.b())) {
               return false;
            }

            if(TextUtils.isEmpty(this.n)) {
               if(!TextUtils.isEmpty(var5.f())) {
                  return false;
               }
            } else if(!this.n.equals(var5.f())) {
               return false;
            }

            if(this.m != var5.e() || this.k != var5.c()) {
               return false;
            }

            var2 = this.l;
            var3 = var5.d();
         } catch (ClassCastException var4) {
            return false;
         }

         if(var2 == var3) {
            return true;
         } else {
            return false;
         }
      }
   }

   @KeepForSdk
   public String f() {
      return this.n;
   }

   @KeepForSdk
   public ArrayList<GoogleSignInOptionsExtensionParcelable> g() {
      return this.p;
   }

   public int hashCode() {
      ArrayList var3 = new ArrayList();
      ArrayList var4 = (ArrayList)this.i;
      int var2 = var4.size();
      int var1 = 0;

      while(var1 < var2) {
         Object var5 = var4.get(var1);
         ++var1;
         var3.add(((Scope)var5).getScopeUri());
      }

      Collections.sort(var3);
      return (new ff()).a(var3).a(this.j).a(this.n).a(this.m).a(this.k).a(this.l).a();
   }

   public void writeToParcel(Parcel var1, int var2) {
      int var3 = SafeParcelWriter.beginObjectHeader(var1);
      SafeParcelWriter.writeInt(var1, 1, this.h);
      SafeParcelWriter.writeTypedList(var1, 2, this.a(), false);
      SafeParcelWriter.writeParcelable(var1, 3, this.b(), var2, false);
      SafeParcelWriter.writeBoolean(var1, 4, this.c());
      SafeParcelWriter.writeBoolean(var1, 5, this.d());
      SafeParcelWriter.writeBoolean(var1, 6, this.e());
      SafeParcelWriter.writeString(var1, 7, this.f(), false);
      SafeParcelWriter.writeString(var1, 8, this.o, false);
      SafeParcelWriter.writeTypedList(var1, 9, this.g(), false);
      SafeParcelWriter.finishObjectHeader(var1, var3);
   }

   public static final class a {

      private Set<Scope> a = new HashSet();
      private boolean b;
      private boolean c;
      private boolean d;
      private String e;
      private Account f;
      private String g;
      private Map<Integer, GoogleSignInOptionsExtensionParcelable> h = new HashMap();


      public final GoogleSignInOptions.a a() {
         this.a.add(GoogleSignInOptions.c);
         return this;
      }

      public final GoogleSignInOptions.a a(Scope var1, Scope ... var2) {
         this.a.add(var1);
         this.a.addAll(Arrays.asList(var2));
         return this;
      }

      public final GoogleSignInOptions.a b() {
         this.a.add(GoogleSignInOptions.a);
         return this;
      }

      public final GoogleSignInOptions c() {
         if(this.a.contains(GoogleSignInOptions.e) && this.a.contains(GoogleSignInOptions.d)) {
            this.a.remove(GoogleSignInOptions.d);
         }

         if(this.d && (this.f == null || !this.a.isEmpty())) {
            this.a();
         }

         return new GoogleSignInOptions(3, new ArrayList(this.a), this.f, this.d, this.b, this.c, this.e, this.g, this.h, (fj)null);
      }
   }
}
