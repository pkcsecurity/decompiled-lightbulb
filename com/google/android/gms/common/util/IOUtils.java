package com.google.android.gms.common.util;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.Nullable;

@KeepForSdk
@ShowFirstParty
public final class IOUtils {

   @KeepForSdk
   public static void closeQuietly(@Nullable ParcelFileDescriptor var0) {
      if(var0 != null) {
         try {
            var0.close();
         } catch (IOException var1) {
            ;
         }
      }
   }

   @KeepForSdk
   public static void closeQuietly(@Nullable Closeable var0) {
      if(var0 != null) {
         try {
            var0.close();
         } catch (IOException var1) {
            ;
         }
      }
   }

   @KeepForSdk
   public static long copyStream(InputStream var0, OutputStream var1) throws IOException {
      return zza(var0, var1, false);
   }

   @KeepForSdk
   public static long copyStream(InputStream param0, OutputStream param1, boolean param2, int param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   @KeepForSdk
   public static boolean isGzipByteBuffer(byte[] var0) {
      if(var0.length > 1) {
         byte var1 = var0[0];
         if(((var0[1] & 255) << 8 | var1 & 255) == '\u8b1f') {
            return true;
         }
      }

      return false;
   }

   @KeepForSdk
   public static byte[] readInputStreamFully(InputStream var0) throws IOException {
      return readInputStreamFully(var0, true);
   }

   @KeepForSdk
   public static byte[] readInputStreamFully(InputStream var0, boolean var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      zza(var0, var2, var1);
      return var2.toByteArray();
   }

   @KeepForSdk
   public static byte[] toByteArray(InputStream var0) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var2);
      byte[] var3 = new byte[4096];

      while(true) {
         int var1 = var0.read(var3);
         if(var1 == -1) {
            return var2.toByteArray();
         }

         var2.write(var3, 0, var1);
      }
   }

   private static long zza(InputStream var0, OutputStream var1, boolean var2) throws IOException {
      return copyStream(var0, var1, var2, 1024);
   }
}
