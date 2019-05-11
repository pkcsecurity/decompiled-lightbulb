package com.facebook.imageformat;

import com.facebook.common.internal.Preconditions;
import java.io.UnsupportedEncodingException;

public class ImageFormatCheckerUtils {

   public static byte[] asciiBytes(String var0) {
      Preconditions.checkNotNull(var0);

      try {
         byte[] var2 = var0.getBytes("ASCII");
         return var2;
      } catch (UnsupportedEncodingException var1) {
         throw new RuntimeException("ASCII not found!", var1);
      }
   }

   public static boolean startsWithPattern(byte[] var0, byte[] var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      if(var1.length > var0.length) {
         return false;
      } else {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            if(var0[var2] != var1[var2]) {
               return false;
            }
         }

         return true;
      }
   }
}
