package com.facebook.soloader;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;

public final class FileLocker implements Closeable {

   private final FileLock mLock;
   private final FileOutputStream mLockFileOutputStream;


   private FileLocker(File var1) throws IOException {
      this.mLockFileOutputStream = new FileOutputStream(var1);
      boolean var3 = false;

      FileLock var5;
      try {
         var3 = true;
         var5 = this.mLockFileOutputStream.getChannel().lock();
         var3 = false;
      } finally {
         if(var3) {
            this.mLockFileOutputStream.close();
         }
      }

      if(var5 == null) {
         this.mLockFileOutputStream.close();
      }

      this.mLock = var5;
   }

   public static FileLocker lock(File var0) throws IOException {
      return new FileLocker(var0);
   }

   public void close() throws IOException {
      try {
         this.mLock.release();
      } finally {
         this.mLockFileOutputStream.close();
      }

   }
}
