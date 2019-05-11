package com.google.android.gms.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Process;
import android.os.WorkSource;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.Strings;
import com.google.android.gms.common.wrappers.Wrappers;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@KeepForSdk
public class WorkSourceUtil {

   private static final int zzhh = Process.myUid();
   private static final Method zzhi = zzw();
   private static final Method zzhj = zzx();
   private static final Method zzhk = zzy();
   private static final Method zzhl = zzz();
   private static final Method zzhm = zzaa();
   private static final Method zzhn = zzab();
   private static final Method zzho = zzac();


   @Nullable
   @KeepForSdk
   public static WorkSource fromPackage(Context var0, @Nullable String var1) {
      if(var0 != null && var0.getPackageManager() != null) {
         if(var1 == null) {
            return null;
         } else {
            String var3;
            ApplicationInfo var4;
            try {
               var4 = Wrappers.packageManager(var0).getApplicationInfo(var1, 0);
            } catch (NameNotFoundException var2) {
               var3 = String.valueOf(var1);
               if(var3.length() != 0) {
                  var3 = "Could not find package: ".concat(var3);
               } else {
                  var3 = new String("Could not find package: ");
               }

               Log.e("WorkSourceUtil", var3);
               return null;
            }

            if(var4 == null) {
               var3 = String.valueOf(var1);
               if(var3.length() != 0) {
                  var3 = "Could not get applicationInfo from package: ".concat(var3);
               } else {
                  var3 = new String("Could not get applicationInfo from package: ");
               }

               Log.e("WorkSourceUtil", var3);
               return null;
            } else {
               return zza(var4.uid, var1);
            }
         }
      } else {
         return null;
      }
   }

   @KeepForSdk
   public static WorkSource fromPackageAndModuleExperimentalPi(Context var0, String var1, String var2) {
      if(var0 != null && var0.getPackageManager() != null && var2 != null && var1 != null) {
         int var3 = zzd(var0, var1);
         if(var3 < 0) {
            return null;
         } else {
            WorkSource var6 = new WorkSource();
            if(zzhn != null && zzho != null) {
               try {
                  Object var4 = zzhn.invoke(var6, new Object[0]);
                  if(var3 != zzhh) {
                     zzho.invoke(var4, new Object[]{Integer.valueOf(var3), var1});
                  }

                  zzho.invoke(var4, new Object[]{Integer.valueOf(zzhh), var2});
                  return var6;
               } catch (Exception var5) {
                  Log.w("WorkSourceUtil", "Unable to assign chained blame through WorkSource", var5);
                  return var6;
               }
            } else {
               zza(var6, var3, var1);
               return var6;
            }
         }
      } else {
         Log.w("WorkSourceUtil", "Unexpected null arguments");
         return null;
      }
   }

   @KeepForSdk
   public static List<String> getNames(@Nullable WorkSource var0) {
      int var2 = 0;
      int var1;
      if(var0 == null) {
         var1 = 0;
      } else {
         var1 = zza(var0);
      }

      if(var1 == 0) {
         return Collections.emptyList();
      } else {
         ArrayList var3;
         for(var3 = new ArrayList(); var2 < var1; ++var2) {
            String var4 = zza(var0, var2);
            if(!Strings.isEmptyOrWhitespace(var4)) {
               var3.add(var4);
            }
         }

         return var3;
      }
   }

   @KeepForSdk
   public static boolean hasWorkSourcePermission(Context var0) {
      return var0 == null?false:(var0.getPackageManager() == null?false:Wrappers.packageManager(var0).checkPermission("android.permission.UPDATE_DEVICE_STATS", var0.getPackageName()) == 0);
   }

   private static int zza(WorkSource var0) {
      if(zzhk != null) {
         try {
            int var1 = ((Integer)zzhk.invoke(var0, new Object[0])).intValue();
            return var1;
         } catch (Exception var2) {
            Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", var2);
         }
      }

      return 0;
   }

   private static WorkSource zza(int var0, String var1) {
      WorkSource var2 = new WorkSource();
      zza(var2, var0, var1);
      return var2;
   }

   @Nullable
   private static String zza(WorkSource var0, int var1) {
      if(zzhm != null) {
         try {
            String var3 = (String)zzhm.invoke(var0, new Object[]{Integer.valueOf(var1)});
            return var3;
         } catch (Exception var2) {
            Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", var2);
         }
      }

      return null;
   }

   private static void zza(WorkSource var0, int var1, @Nullable String var2) {
      if(zzhj != null) {
         String var3 = var2;
         if(var2 == null) {
            var3 = "";
         }

         try {
            zzhj.invoke(var0, new Object[]{Integer.valueOf(var1), var3});
         } catch (Exception var4) {
            Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", var4);
         }
      } else {
         if(zzhi != null) {
            try {
               zzhi.invoke(var0, new Object[]{Integer.valueOf(var1)});
               return;
            } catch (Exception var5) {
               Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", var5);
            }
         }

      }
   }

   private static Method zzaa() {
      if(PlatformVersion.isAtLeastJellyBeanMR2()) {
         try {
            Method var0 = WorkSource.class.getMethod("getName", new Class[]{Integer.TYPE});
            return var0;
         } catch (Exception var1) {
            ;
         }
      }

      return null;
   }

   private static final Method zzab() {
      if(PlatformVersion.isAtLeastP()) {
         try {
            Method var0 = WorkSource.class.getMethod("createWorkChain", new Class[0]);
            return var0;
         } catch (Exception var1) {
            Log.w("WorkSourceUtil", "Missing WorkChain API createWorkChain", var1);
         }
      }

      return null;
   }

   @SuppressLint({"PrivateApi"})
   private static final Method zzac() {
      if(PlatformVersion.isAtLeastP()) {
         try {
            Method var0 = Class.forName("android.os.WorkSource$WorkChain").getMethod("addNode", new Class[]{Integer.TYPE, String.class});
            return var0;
         } catch (Exception var1) {
            Log.w("WorkSourceUtil", "Missing WorkChain class", var1);
         }
      }

      return null;
   }

   private static int zzd(Context var0, String var1) {
      String var3;
      ApplicationInfo var4;
      try {
         var4 = Wrappers.packageManager(var0).getApplicationInfo(var1, 0);
      } catch (NameNotFoundException var2) {
         var3 = String.valueOf(var1);
         if(var3.length() != 0) {
            var3 = "Could not find package: ".concat(var3);
         } else {
            var3 = new String("Could not find package: ");
         }

         Log.e("WorkSourceUtil", var3);
         return -1;
      }

      if(var4 == null) {
         var3 = String.valueOf(var1);
         if(var3.length() != 0) {
            var3 = "Could not get applicationInfo from package: ".concat(var3);
         } else {
            var3 = new String("Could not get applicationInfo from package: ");
         }

         Log.e("WorkSourceUtil", var3);
         return -1;
      } else {
         return var4.uid;
      }
   }

   private static Method zzw() {
      try {
         Method var0 = WorkSource.class.getMethod("add", new Class[]{Integer.TYPE});
         return var0;
      } catch (Exception var1) {
         return null;
      }
   }

   private static Method zzx() {
      if(PlatformVersion.isAtLeastJellyBeanMR2()) {
         try {
            Method var0 = WorkSource.class.getMethod("add", new Class[]{Integer.TYPE, String.class});
            return var0;
         } catch (Exception var1) {
            ;
         }
      }

      return null;
   }

   private static Method zzy() {
      try {
         Method var0 = WorkSource.class.getMethod("size", new Class[0]);
         return var0;
      } catch (Exception var1) {
         return null;
      }
   }

   private static Method zzz() {
      try {
         Method var0 = WorkSource.class.getMethod("get", new Class[]{Integer.TYPE});
         return var0;
      } catch (Exception var1) {
         return null;
      }
   }
}
