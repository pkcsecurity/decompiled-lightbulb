package com.google.android.gms.common.wrappers;

import android.content.Context;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.wrappers.PackageManagerWrapper;

@KeepForSdk
public class Wrappers {

   private static Wrappers zzhx = new Wrappers();
   private PackageManagerWrapper zzhw = null;


   @KeepForSdk
   public static PackageManagerWrapper packageManager(Context var0) {
      return zzhx.zzi(var0);
   }

   @VisibleForTesting
   private final PackageManagerWrapper zzi(Context var1) {
      synchronized(this){}

      PackageManagerWrapper var4;
      try {
         if(this.zzhw == null) {
            if(var1.getApplicationContext() != null) {
               var1 = var1.getApplicationContext();
            }

            this.zzhw = new PackageManagerWrapper(var1);
         }

         var4 = this.zzhw;
      } finally {
         ;
      }

      return var4;
   }
}
