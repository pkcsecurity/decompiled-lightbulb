package com.facebook.common.util;

import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecureHashUtil {

   static final byte[] HEX_CHAR_TABLE = new byte[]{(byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102};


   public static String convertToHex(byte[] var0) throws UnsupportedEncodingException {
      StringBuilder var4 = new StringBuilder(var0.length);
      int var2 = var0.length;

      for(int var1 = 0; var1 < var2; ++var1) {
         int var3 = var0[var1] & 255;
         var4.append((char)HEX_CHAR_TABLE[var3 >>> 4]);
         var4.append((char)HEX_CHAR_TABLE[var3 & 15]);
      }

      return var4.toString();
   }

   private static String makeHash(byte[] var0, String var1) {
      try {
         MessageDigest var5 = MessageDigest.getInstance(var1);
         var5.update(var0, 0, var0.length);
         String var4 = convertToHex(var5.digest());
         return var4;
      } catch (NoSuchAlgorithmException var2) {
         throw new RuntimeException(var2);
      } catch (UnsupportedEncodingException var3) {
         throw new RuntimeException(var3);
      }
   }

   public static String makeMD5Hash(String var0) {
      try {
         var0 = makeMD5Hash(var0.getBytes("utf-8"));
         return var0;
      } catch (UnsupportedEncodingException var1) {
         throw new RuntimeException(var1);
      }
   }

   public static String makeMD5Hash(byte[] var0) {
      return makeHash(var0, "MD5");
   }

   public static String makeSHA1Hash(String var0) {
      try {
         var0 = makeSHA1Hash(var0.getBytes("utf-8"));
         return var0;
      } catch (UnsupportedEncodingException var1) {
         throw new RuntimeException(var1);
      }
   }

   public static String makeSHA1Hash(byte[] var0) {
      return makeHash(var0, "SHA-1");
   }

   public static String makeSHA1HashBase64(byte[] var0) {
      try {
         MessageDigest var1 = MessageDigest.getInstance("SHA-1");
         var1.update(var0, 0, var0.length);
         String var3 = Base64.encodeToString(var1.digest(), 11);
         return var3;
      } catch (NoSuchAlgorithmException var2) {
         throw new RuntimeException(var2);
      }
   }

   public static String makeSHA256Hash(byte[] var0) {
      return makeHash(var0, "SHA-256");
   }
}
