package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AtomicFile {

   private final File mBackupName;
   private final File mBaseName;


   public AtomicFile(@NonNull File var1) {
      this.mBaseName = var1;
      StringBuilder var2 = new StringBuilder();
      var2.append(var1.getPath());
      var2.append(".bak");
      this.mBackupName = new File(var2.toString());
   }

   private static boolean sync(@NonNull FileOutputStream var0) {
      try {
         var0.getFD().sync();
         return true;
      } catch (IOException var1) {
         return false;
      }
   }

   public void delete() {
      this.mBaseName.delete();
      this.mBackupName.delete();
   }

   public void failWrite(@Nullable FileOutputStream var1) {
      if(var1 != null) {
         sync(var1);

         try {
            var1.close();
            this.mBaseName.delete();
            this.mBackupName.renameTo(this.mBaseName);
            return;
         } catch (IOException var2) {
            Log.w("AtomicFile", "failWrite: Got exception:", var2);
         }
      }

   }

   public void finishWrite(@Nullable FileOutputStream var1) {
      if(var1 != null) {
         sync(var1);

         try {
            var1.close();
            this.mBackupName.delete();
            return;
         } catch (IOException var2) {
            Log.w("AtomicFile", "finishWrite: Got exception:", var2);
         }
      }

   }

   @NonNull
   public File getBaseFile() {
      return this.mBaseName;
   }

   @NonNull
   public FileInputStream openRead() throws FileNotFoundException {
      if(this.mBackupName.exists()) {
         this.mBaseName.delete();
         this.mBackupName.renameTo(this.mBaseName);
      }

      return new FileInputStream(this.mBaseName);
   }

   @NonNull
   public byte[] readFully() throws IOException {
      // $FF: Couldn't be decompiled
   }

   @NonNull
   public FileOutputStream startWrite() throws IOException {
      StringBuilder var1;
      if(this.mBaseName.exists()) {
         if(!this.mBackupName.exists()) {
            if(!this.mBaseName.renameTo(this.mBackupName)) {
               var1 = new StringBuilder();
               var1.append("Couldn\'t rename file ");
               var1.append(this.mBaseName);
               var1.append(" to backup file ");
               var1.append(this.mBackupName);
               Log.w("AtomicFile", var1.toString());
            }
         } else {
            this.mBaseName.delete();
         }
      }

      FileOutputStream var4;
      try {
         var4 = new FileOutputStream(this.mBaseName);
         return var4;
      } catch (FileNotFoundException var3) {
         if(!this.mBaseName.getParentFile().mkdirs()) {
            var1 = new StringBuilder();
            var1.append("Couldn\'t create directory ");
            var1.append(this.mBaseName);
            throw new IOException(var1.toString());
         } else {
            try {
               var4 = new FileOutputStream(this.mBaseName);
               return var4;
            } catch (FileNotFoundException var2) {
               var1 = new StringBuilder();
               var1.append("Couldn\'t create ");
               var1.append(this.mBaseName);
               throw new IOException(var1.toString());
            }
         }
      }
   }
}
