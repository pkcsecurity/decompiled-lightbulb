package com.facebook.litho.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DeviceInfoUtils {

   private static final FileFilter CPU_FILTER = new FileFilter() {
      public boolean accept(File var1) {
         String var3 = var1.getName();
         if(var3.startsWith("cpu")) {
            for(int var2 = 3; var2 < var3.length(); ++var2) {
               if(!Character.isDigit(var3.charAt(var2))) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }
   };
   public static final int DEVICEINFO_UNKNOWN = -1;
   public static final int NUM_CORES_NOT_SET = -2;
   private static int sNumCores;


   private static int getCoresFromCPUFileList() {
      return (new File("/sys/devices/system/cpu/")).listFiles(CPU_FILTER).length;
   }

   private static int getCoresFromFileInfo(String var0) {
      int var1;
      FileInputStream var25;
      label153: {
         label149: {
            label148: {
               try {
                  var25 = new FileInputStream(var0);
                  break label148;
               } catch (IOException var23) {
                  ;
               } finally {
                  ;
               }

               var25 = null;
               break label149;
            }

            try {
               try {
                  BufferedReader var26 = new BufferedReader(new InputStreamReader(var25));
                  String var3 = var26.readLine();
                  var26.close();
                  var1 = getCoresFromFileString(var3);
                  break label153;
               } catch (IOException var21) {
                  ;
               }
            } catch (Throwable var22) {
               FileInputStream var2 = var25;
               if(var25 != null) {
                  try {
                     var2.close();
                  } catch (IOException var19) {
                     ;
                  }
               }

               throw var22;
            }
         }

         if(var25 != null) {
            try {
               var25.close();
            } catch (IOException var18) {
               return -1;
            }
         }

         return -1;
      }

      if(var25 != null) {
         try {
            var25.close();
         } catch (IOException var20) {
            return var1;
         }
      }

      return var1;
   }

   static int getCoresFromFileString(String var0) {
      return var0 != null && var0.matches("0-[\\d]+$")?Integer.valueOf(var0.substring(2)).intValue() + 1:-1;
   }

   public static int getNumberOfCPUCores() {
      // $FF: Couldn't be decompiled
   }

   public static boolean hasMultipleCores() {
      int var0 = getNumberOfCPUCores();
      return var0 != -1 && var0 > 1;
   }
}
