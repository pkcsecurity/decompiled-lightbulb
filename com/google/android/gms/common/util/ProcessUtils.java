package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.annotation.Nullable;

@KeepForSdk
public class ProcessUtils {

   private static String zzhd;
   private static int zzhe;


   @Nullable
   @KeepForSdk
   public static String getMyProcessName() {
      if(zzhd == null) {
         if(zzhe == 0) {
            zzhe = Process.myPid();
         }

         zzhd = zzd(zzhe);
      }

      return zzhd;
   }

   @Nullable
   private static String zzd(int param0) {
      // $FF: Couldn't be decompiled
   }

   private static BufferedReader zzj(String var0) throws IOException {
      ThreadPolicy var1 = StrictMode.allowThreadDiskReads();

      BufferedReader var4;
      try {
         var4 = new BufferedReader(new FileReader(var0));
      } finally {
         StrictMode.setThreadPolicy(var1);
      }

      return var4;
   }
}
