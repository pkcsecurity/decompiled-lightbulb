package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zze;
import com.google.android.gms.common.zzf;
import com.google.android.gms.common.zzh;
import com.google.android.gms.common.zzm;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.wrappers.Wrappers;
import javax.annotation.CheckReturnValue;

@CheckReturnValue
@KeepForSdk
@ShowFirstParty
public class GoogleSignatureVerifier {

   private static GoogleSignatureVerifier zzal;
   private final Context mContext;
   private volatile String zzam;


   private GoogleSignatureVerifier(Context var1) {
      this.mContext = var1.getApplicationContext();
   }

   @KeepForSdk
   public static GoogleSignatureVerifier getInstance(Context param0) {
      // $FF: Couldn't be decompiled
   }

   private static zze zza(PackageInfo var0, zze ... var1) {
      if(var0.signatures == null) {
         return null;
      } else if(var0.signatures.length != 1) {
         Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
         return null;
      } else {
         Signature[] var3 = var0.signatures;
         int var2 = 0;

         for(zzf var4 = new zzf(var3[0].toByteArray()); var2 < var1.length; ++var2) {
            if(var1[var2].equals(var4)) {
               return var1[var2];
            }
         }

         return null;
      }
   }

   private final zzm zza(PackageInfo var1) {
      boolean var2 = GooglePlayServicesUtilLight.honorsDebugCertificates(this.mContext);
      if(var1 == null) {
         return zzm.zzb("null pkg");
      } else if(var1.signatures.length != 1) {
         return zzm.zzb("single cert required");
      } else {
         zzf var3 = new zzf(var1.signatures[0].toByteArray());
         String var4 = var1.packageName;
         zzm var5 = zzc.zza(var4, var3, var2);
         return var5.zzac && var1.applicationInfo != null && (var1.applicationInfo.flags & 2) != 0 && (!var2 || zzc.zza(var4, var3, false).zzac)?zzm.zzb("debuggable release cert app rejected"):var5;
      }
   }

   private final zzm zza(String var1, int var2) {
      try {
         zzm var3 = this.zza(Wrappers.packageManager(this.mContext).zza(var1, 64, var2));
         return var3;
      } catch (NameNotFoundException var4) {
         var1 = String.valueOf(var1);
         if(var1.length() != 0) {
            var1 = "no pkg ".concat(var1);
         } else {
            var1 = new String("no pkg ");
         }

         return zzm.zzb(var1);
      }
   }

   public static boolean zza(PackageInfo var0, boolean var1) {
      if(var0 != null && var0.signatures != null) {
         zze var2;
         if(var1) {
            var2 = zza(var0, zzh.zzx);
         } else {
            var2 = zza(var0, new zze[]{zzh.zzx[0]});
         }

         if(var2 != null) {
            return true;
         }
      }

      return false;
   }

   private final zzm zzc(String var1) {
      if(var1 == null) {
         return zzm.zzb("null pkg");
      } else if(var1.equals(this.zzam)) {
         return zzm.zze();
      } else {
         PackageInfo var2;
         try {
            var2 = Wrappers.packageManager(this.mContext).getPackageInfo(var1, 64);
         } catch (NameNotFoundException var3) {
            var1 = String.valueOf(var1);
            if(var1.length() != 0) {
               var1 = "no pkg ".concat(var1);
            } else {
               var1 = new String("no pkg ");
            }

            return zzm.zzb(var1);
         }

         zzm var4 = this.zza(var2);
         if(var4.zzac) {
            this.zzam = var1;
         }

         return var4;
      }
   }

   @KeepForSdk
   public boolean isGooglePublicSignedPackage(PackageInfo var1) {
      if(var1 == null) {
         return false;
      } else if(zza(var1, false)) {
         return true;
      } else {
         if(zza(var1, true)) {
            if(GooglePlayServicesUtilLight.honorsDebugCertificates(this.mContext)) {
               return true;
            }

            Log.w("GoogleSignatureVerifier", "Test-keys aren\'t accepted on this build.");
         }

         return false;
      }
   }

   @KeepForSdk
   @ShowFirstParty
   public boolean isPackageGoogleSigned(String var1) {
      zzm var2 = this.zzc(var1);
      var2.zzf();
      return var2.zzac;
   }

   @KeepForSdk
   @ShowFirstParty
   public boolean isUidGoogleSigned(int var1) {
      String[] var6 = Wrappers.packageManager(this.mContext).getPackagesForUid(var1);
      zzm var4;
      if(var6 != null && var6.length != 0) {
         var4 = null;
         int var3 = var6.length;

         zzm var5;
         for(int var2 = 0; var2 < var3; var4 = var5) {
            var5 = this.zza(var6[var2], var1);
            var4 = var5;
            if(var5.zzac) {
               break;
            }

            ++var2;
         }
      } else {
         var4 = zzm.zzb("no pkgs");
      }

      var4.zzf();
      return var4.zzac;
   }
}
