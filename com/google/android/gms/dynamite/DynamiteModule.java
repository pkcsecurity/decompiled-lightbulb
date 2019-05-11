package com.google.android.gms.dynamite;

import android.content.Context;
import android.database.Cursor;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.zzi;
import com.google.android.gms.dynamite.zzk;
import java.lang.reflect.Field;
import javax.annotation.concurrent.GuardedBy;

@KeepForSdk
public final class DynamiteModule {

   @KeepForSdk
   public static final DynamiteModule.VersionPolicy a = new fu();
   @KeepForSdk
   public static final DynamiteModule.VersionPolicy b = new fw();
   @KeepForSdk
   public static final DynamiteModule.VersionPolicy c = new fx();
   @KeepForSdk
   public static final DynamiteModule.VersionPolicy d = new fy();
   @GuardedBy
   private static Boolean e;
   @GuardedBy
   private static zzi f;
   @GuardedBy
   private static zzk g;
   @GuardedBy
   private static String h;
   @GuardedBy
   private static int i;
   private static final ThreadLocal<DynamiteModule.b> j = new ThreadLocal();
   private static final DynamiteModule.zza k = new ft();
   private static final DynamiteModule.VersionPolicy l = new fv();
   private static final DynamiteModule.VersionPolicy m = new fz();
   private final Context n;


   private DynamiteModule(Context var1) {
      this.n = (Context)Preconditions.checkNotNull(var1);
   }

   @KeepForSdk
   public static int a(Context var0, String var1) {
      String var6;
      try {
         ClassLoader var8 = var0.getApplicationContext().getClassLoader();
         StringBuilder var3 = new StringBuilder(String.valueOf(var1).length() + 61);
         var3.append("com.google.android.gms.dynamite.descriptors.");
         var3.append(var1);
         var3.append(".ModuleDescriptor");
         Class var10 = var8.loadClass(var3.toString());
         Field var9 = var10.getDeclaredField("MODULE_ID");
         Field var11 = var10.getDeclaredField("MODULE_VERSION");
         if(!var9.get((Object)null).equals(var1)) {
            var6 = String.valueOf(var9.get((Object)null));
            var3 = new StringBuilder(String.valueOf(var6).length() + 51 + String.valueOf(var1).length());
            var3.append("Module descriptor id \'");
            var3.append(var6);
            var3.append("\' didn\'t match expected id \'");
            var3.append(var1);
            var3.append("\'");
            Log.e("DynamiteModule", var3.toString());
            return 0;
         } else {
            int var2 = var11.getInt((Object)null);
            return var2;
         }
      } catch (ClassNotFoundException var4) {
         StringBuilder var7 = new StringBuilder(String.valueOf(var1).length() + 45);
         var7.append("Local module descriptor class for ");
         var7.append(var1);
         var7.append(" not found.");
         Log.w("DynamiteModule", var7.toString());
         return 0;
      } catch (Exception var5) {
         var6 = String.valueOf(var5.getMessage());
         if(var6.length() != 0) {
            var6 = "Failed to load module descriptor class: ".concat(var6);
         } else {
            var6 = new String("Failed to load module descriptor class: ");
         }

         Log.e("DynamiteModule", var6);
         return 0;
      }
   }

   public static int a(Context param0, String param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   private static Context a(Context var0, String var1, int var2, Cursor var3, zzk var4) {
      try {
         ObjectWrapper.a((Object)null);
         IObjectWrapper var7;
         if(b().booleanValue()) {
            Log.v("DynamiteModule", "Dynamite loader version >= 2, using loadModule2NoCrashUtils");
            var7 = var4.b(ObjectWrapper.a((Object)var0), var1, var2, ObjectWrapper.a((Object)var3));
         } else {
            Log.w("DynamiteModule", "Dynamite loader version < 2, falling back to loadModule2");
            var7 = var4.a(ObjectWrapper.a((Object)var0), var1, var2, ObjectWrapper.a((Object)var3));
         }

         var0 = (Context)ObjectWrapper.a(var7);
         return var0;
      } catch (Exception var5) {
         String var6 = String.valueOf(var5.toString());
         if(var6.length() != 0) {
            var6 = "Failed to load DynamiteLoader: ".concat(var6);
         } else {
            var6 = new String("Failed to load DynamiteLoader: ");
         }

         Log.e("DynamiteModule", var6);
         return null;
      }
   }

   @KeepForSdk
   public static DynamiteModule a(Context param0, DynamiteModule.VersionPolicy param1, String param2) throws DynamiteModule.a {
      // $FF: Couldn't be decompiled
   }

   private static DynamiteModule a(Context param0, String param1, int param2) throws DynamiteModule.a {
      // $FF: Couldn't be decompiled
   }

   private static zzi a(Context param0) {
      // $FF: Couldn't be decompiled
   }

   @GuardedBy
   private static void a(ClassLoader param0) throws DynamiteModule.a {
      // $FF: Couldn't be decompiled
   }

   private static int b(Context var0, String var1, boolean var2) {
      zzi var4 = a(var0);
      if(var4 == null) {
         return 0;
      } else {
         try {
            if(var4.a() >= 2) {
               return var4.b(ObjectWrapper.a((Object)var0), var1, var2);
            } else {
               Log.w("DynamiteModule", "IDynamite loader version < 2, falling back to getModuleVersion2");
               int var3 = var4.a(ObjectWrapper.a((Object)var0), var1, var2);
               return var3;
            }
         } catch (RemoteException var5) {
            String var6 = String.valueOf(var5.getMessage());
            if(var6.length() != 0) {
               var6 = "Failed to retrieve remote module version: ".concat(var6);
            } else {
               var6 = new String("Failed to retrieve remote module version: ");
            }

            Log.w("DynamiteModule", var6);
            return 0;
         }
      }
   }

   private static DynamiteModule b(Context var0, String var1) {
      var1 = String.valueOf(var1);
      if(var1.length() != 0) {
         var1 = "Selected local version of ".concat(var1);
      } else {
         var1 = new String("Selected local version of ");
      }

      Log.i("DynamiteModule", var1);
      return new DynamiteModule(var0.getApplicationContext());
   }

   private static DynamiteModule b(Context var0, String var1, int var2) throws DynamiteModule.a {
      StringBuilder var3 = new StringBuilder(String.valueOf(var1).length() + 51);
      var3.append("Selected remote version of ");
      var3.append(var1);
      var3.append(", version >= ");
      var3.append(var2);
      Log.i("DynamiteModule", var3.toString());
      zzi var6 = a(var0);
      if(var6 == null) {
         throw new DynamiteModule.a("Failed to create IDynamiteLoader.", (ft)null);
      } else {
         IObjectWrapper var5;
         try {
            if(var6.a() >= 2) {
               var5 = var6.b(ObjectWrapper.a((Object)var0), var1, var2);
            } else {
               Log.w("DynamiteModule", "Dynamite loader version < 2, falling back to createModuleContext");
               var5 = var6.a(ObjectWrapper.a((Object)var0), var1, var2);
            }
         } catch (RemoteException var4) {
            throw new DynamiteModule.a("Failed to load remote module.", var4, (ft)null);
         }

         if(ObjectWrapper.a(var5) == null) {
            throw new DynamiteModule.a("Failed to load remote module.", (ft)null);
         } else {
            return new DynamiteModule((Context)ObjectWrapper.a(var5));
         }
      }
   }

   private static Boolean b() {
      // $FF: Couldn't be decompiled
   }

   private static int c(Context param0, String param1, boolean param2) throws DynamiteModule.a {
      // $FF: Couldn't be decompiled
   }

   private static DynamiteModule c(Context param0, String param1, int param2) throws DynamiteModule.a {
      // $FF: Couldn't be decompiled
   }

   @KeepForSdk
   public final Context a() {
      return this.n;
   }

   @KeepForSdk
   public final IBinder a(String var1) throws DynamiteModule.a {
      try {
         IBinder var2 = (IBinder)this.n.getClassLoader().loadClass(var1).newInstance();
         return var2;
      } catch (IllegalAccessException var3) {
         var1 = String.valueOf(var1);
         if(var1.length() != 0) {
            var1 = "Failed to instantiate module class: ".concat(var1);
         } else {
            var1 = new String("Failed to instantiate module class: ");
         }

         throw new DynamiteModule.a(var1, var3, (ft)null);
      }
   }

   @DynamiteApi
   public static class DynamiteLoaderClassLoader {

      @GuardedBy
      public static ClassLoader sClassLoader;


   }

   public static final class a {

      public int a = 0;
      public int b = 0;
      public int c = 0;


   }

   @KeepForSdk
   public static class a extends Exception {

      private a(String var1) {
         super(var1);
      }

      // $FF: synthetic method
      a(String var1, ft var2) {
         this(var1);
      }

      private a(String var1, Throwable var2) {
         super(var1, var2);
      }

      // $FF: synthetic method
      a(String var1, Throwable var2, ft var3) {
         this(var1, var2);
      }
   }

   static final class b {

      public Cursor a;


      private b() {}

      // $FF: synthetic method
      b(ft var1) {
         this();
      }
   }

   static final class c implements DynamiteModule.zza {

      private final int a;
      private final int b;


      public c(int var1, int var2) {
         this.a = var1;
         this.b = 0;
      }

      public final int a(Context var1, String var2) {
         return this.a;
      }

      public final int a(Context var1, String var2, boolean var3) {
         return 0;
      }
   }

   public interface zza {

      int a(Context var1, String var2);

      int a(Context var1, String var2, boolean var3) throws DynamiteModule.a;
   }

   public interface VersionPolicy {

      DynamiteModule.a a(Context var1, String var2, DynamiteModule.zza var3) throws DynamiteModule.a;
   }
}
