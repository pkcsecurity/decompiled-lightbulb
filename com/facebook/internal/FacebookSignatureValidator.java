package com.facebook.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import com.facebook.internal.Utility;
import java.util.HashSet;

public class FacebookSignatureValidator {

   private static final String FBF_HASH = "2438bce1ddb7bd026d5ff89f598b3b5e5bb824b3";
   private static final String FBI_HASH = "a4b7452e2ed8f5f191058ca7bbfd26b0d3214bfc";
   private static final String FBL2_HASH = "df6b721c8b4d3b6eb44c861d4415007e5a35fc95";
   private static final String FBL_HASH = "5e8f16062ea3cd2c4a0d547876baa6f38cabf625";
   private static final String FBR2_HASH = "cc2751449a350f668590264ed76692694a80308a";
   private static final String FBR_HASH = "8a3c4b262d721acd49a4bf97d5213199c86fa2b9";
   private static final String MSR_HASH = "9b8f518b086098de3d77736f9458a3d2f6f95a37";
   private static final HashSet<String> validAppSignatureHashes = buildAppSignatureHashes();


   private static HashSet<String> buildAppSignatureHashes() {
      HashSet var0 = new HashSet();
      var0.add("8a3c4b262d721acd49a4bf97d5213199c86fa2b9");
      var0.add("cc2751449a350f668590264ed76692694a80308a");
      var0.add("a4b7452e2ed8f5f191058ca7bbfd26b0d3214bfc");
      var0.add("5e8f16062ea3cd2c4a0d547876baa6f38cabf625");
      var0.add("df6b721c8b4d3b6eb44c861d4415007e5a35fc95");
      var0.add("9b8f518b086098de3d77736f9458a3d2f6f95a37");
      var0.add("2438bce1ddb7bd026d5ff89f598b3b5e5bb824b3");
      return var0;
   }

   public static boolean validateSignature(Context var0, String var1) {
      String var4 = Build.BRAND;
      int var2 = var0.getApplicationInfo().flags;
      if(var4.startsWith("generic") && (var2 & 2) != 0) {
         return true;
      } else {
         PackageInfo var6;
         try {
            var6 = var0.getPackageManager().getPackageInfo(var1, 64);
         } catch (NameNotFoundException var5) {
            return false;
         }

         if(var6.signatures != null) {
            if(var6.signatures.length <= 0) {
               return false;
            } else {
               Signature[] var7 = var6.signatures;
               int var3 = var7.length;

               for(var2 = 0; var2 < var3; ++var2) {
                  var1 = Utility.sha1hash(var7[var2].toByteArray());
                  if(!validAppSignatureHashes.contains(var1)) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }
}
