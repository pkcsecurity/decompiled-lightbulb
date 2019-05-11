package com.facebook.common.internal;

import com.facebook.common.internal.ByteStreams;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Files {

   static byte[] readFile(InputStream var0, long var1) throws IOException {
      if(var1 > 2147483647L) {
         StringBuilder var3 = new StringBuilder();
         var3.append("file is too large to fit in a byte array: ");
         var3.append(var1);
         var3.append(" bytes");
         throw new OutOfMemoryError(var3.toString());
      } else {
         return var1 == 0L?ByteStreams.toByteArray(var0):ByteStreams.toByteArray(var0, (int)var1);
      }
   }

   public static byte[] toByteArray(File param0) throws IOException {
      // $FF: Couldn't be decompiled
   }
}
