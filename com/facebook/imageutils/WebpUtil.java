package com.facebook.imageutils;

import android.util.Pair;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;

public class WebpUtil {

   private static final String VP8L_HEADER = "VP8L";
   private static final String VP8X_HEADER = "VP8X";
   private static final String VP8_HEADER = "VP8 ";


   private static boolean compare(byte[] var0, String var1) {
      if(var0.length != var1.length()) {
         return false;
      } else {
         for(int var2 = 0; var2 < var0.length; ++var2) {
            if(var1.charAt(var2) != var0[var2]) {
               return false;
            }
         }

         return true;
      }
   }

   public static int get2BytesAsInt(InputStream var0) throws IOException {
      byte var1 = (byte)var0.read();
      return (byte)var0.read() << 8 & '\uff00' | var1 & 255;
   }

   private static byte getByte(InputStream var0) throws IOException {
      return (byte)(var0.read() & 255);
   }

   private static String getHeader(byte[] var0) {
      StringBuilder var2 = new StringBuilder();

      for(int var1 = 0; var1 < var0.length; ++var1) {
         var2.append((char)var0[var1]);
      }

      return var2.toString();
   }

   private static int getInt(InputStream var0) throws IOException {
      byte var1 = (byte)var0.read();
      byte var2 = (byte)var0.read();
      byte var3 = (byte)var0.read();
      return (byte)var0.read() << 24 & -16777216 | var3 << 16 & 16711680 | var2 << 8 & '\uff00' | var1 & 255;
   }

   private static short getShort(InputStream var0) throws IOException {
      return (short)(var0.read() & 255);
   }

   @Nullable
   public static Pair<Integer, Integer> getSize(InputStream param0) {
      // $FF: Couldn't be decompiled
   }

   private static Pair<Integer, Integer> getVP8Dimension(InputStream var0) throws IOException {
      var0.skip(7L);
      short var1 = getShort(var0);
      short var2 = getShort(var0);
      short var3 = getShort(var0);
      return var1 == 157 && var2 == 1 && var3 == 42?new Pair(Integer.valueOf(get2BytesAsInt(var0)), Integer.valueOf(get2BytesAsInt(var0))):null;
   }

   private static Pair<Integer, Integer> getVP8LDimension(InputStream var0) throws IOException {
      getInt(var0);
      if(getByte(var0) != 47) {
         return null;
      } else {
         byte var1 = (byte)var0.read();
         int var2 = (byte)var0.read() & 255;
         byte var3 = (byte)var0.read();
         return new Pair(Integer.valueOf((var1 & 255 | (var2 & 63) << 8) + 1), Integer.valueOf((((byte)var0.read() & 255 & 15) << 10 | (var3 & 255) << 2 | (var2 & 192) >> 6) + 1));
      }
   }

   private static Pair<Integer, Integer> getVP8XDimension(InputStream var0) throws IOException {
      var0.skip(8L);
      return new Pair(Integer.valueOf(read3Bytes(var0) + 1), Integer.valueOf(read3Bytes(var0) + 1));
   }

   private static boolean isBitOne(byte var0, int var1) {
      return (var0 >> var1 % 8 & 1) == 1;
   }

   private static int read3Bytes(InputStream var0) throws IOException {
      byte var1 = getByte(var0);
      byte var2 = getByte(var0);
      return getByte(var0) << 16 & 16711680 | var2 << 8 & '\uff00' | var1 & 255;
   }
}
