package com.facebook.common.internal;

import com.facebook.common.internal.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public final class ByteStreams {

   private static final int BUF_SIZE = 4096;


   public static long copy(InputStream var0, OutputStream var1) throws IOException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      byte[] var5 = new byte[4096];
      long var3 = 0L;

      while(true) {
         int var2 = var0.read(var5);
         if(var2 == -1) {
            return var3;
         }

         var1.write(var5, 0, var2);
         var3 += (long)var2;
      }
   }

   public static int read(InputStream var0, byte[] var1, int var2, int var3) throws IOException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      if(var3 < 0) {
         throw new IndexOutOfBoundsException("len is negative");
      } else {
         int var4;
         int var5;
         for(var4 = 0; var4 < var3; var4 += var5) {
            var5 = var0.read(var1, var2 + var4, var3 - var4);
            if(var5 == -1) {
               return var4;
            }
         }

         return var4;
      }
   }

   public static void readFully(InputStream var0, byte[] var1, int var2, int var3) throws IOException {
      var2 = read(var0, var1, var2, var3);
      if(var2 != var3) {
         StringBuilder var4 = new StringBuilder();
         var4.append("reached end of stream after reading ");
         var4.append(var2);
         var4.append(" bytes; ");
         var4.append(var3);
         var4.append(" bytes expected");
         throw new EOFException(var4.toString());
      }
   }

   public static byte[] toByteArray(InputStream var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      copy(var0, var1);
      return var1.toByteArray();
   }

   public static byte[] toByteArray(InputStream var0, int var1) throws IOException {
      byte[] var5 = new byte[var1];

      int var4;
      for(int var2 = var1; var2 > 0; var2 -= var4) {
         int var3 = var1 - var2;
         var4 = var0.read(var5, var3, var2);
         if(var4 == -1) {
            return Arrays.copyOf(var5, var3);
         }
      }

      var1 = var0.read();
      if(var1 == -1) {
         return var5;
      } else {
         ByteStreams.FastByteArrayOutputStream var6 = new ByteStreams.FastByteArrayOutputStream(null);
         var6.write(var1);
         copy(var0, var6);
         byte[] var7 = new byte[var5.length + var6.size()];
         System.arraycopy(var5, 0, var7, 0, var5.length);
         var6.writeTo(var7, var5.length);
         return var7;
      }
   }

   static final class FastByteArrayOutputStream extends ByteArrayOutputStream {

      private FastByteArrayOutputStream() {}

      // $FF: synthetic method
      FastByteArrayOutputStream(Object var1) {
         this();
      }

      void writeTo(byte[] var1, int var2) {
         System.arraycopy(this.buf, 0, var1, var2, this.count);
      }
   }
}
