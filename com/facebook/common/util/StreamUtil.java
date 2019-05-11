package com.facebook.common.util;

import com.facebook.common.internal.ByteStreams;
import com.facebook.common.internal.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {

   public static byte[] getBytesFromStream(InputStream var0) throws IOException {
      return getBytesFromStream(var0, var0.available());
   }

   public static byte[] getBytesFromStream(InputStream var0, final int var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream(var1) {
         public byte[] toByteArray() {
            return this.count == this.buf.length?this.buf:super.toByteArray();
         }
      };
      ByteStreams.copy(var0, var2);
      return var2.toByteArray();
   }

   public static long skip(InputStream var0, long var1) throws IOException {
      Preconditions.checkNotNull(var0);
      boolean var7;
      if(var1 >= 0L) {
         var7 = true;
      } else {
         var7 = false;
      }

      Preconditions.checkArgument(var7);
      long var3 = var1;

      while(var3 > 0L) {
         long var5 = var0.skip(var3);
         if(var5 > 0L) {
            var3 -= var5;
         } else {
            if(var0.read() == -1) {
               return var1 - var3;
            }

            --var3;
         }
      }

      return var1;
   }
}
