package com.facebook.common.file;

import com.facebook.common.internal.Preconditions;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtils {

   public static void mkdirs(File var0) throws FileUtils.CreateDirectoryException {
      if(var0.exists()) {
         if(var0.isDirectory()) {
            return;
         }

         if(!var0.delete()) {
            throw new FileUtils.CreateDirectoryException(var0.getAbsolutePath(), new FileUtils.FileDeleteException(var0.getAbsolutePath()));
         }
      }

      if(!var0.mkdirs() && !var0.isDirectory()) {
         throw new FileUtils.CreateDirectoryException(var0.getAbsolutePath());
      }
   }

   public static void rename(File var0, File var1) throws FileUtils.RenameException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      var1.delete();
      if(!var0.renameTo(var1)) {
         Object var2 = null;
         if(var1.exists()) {
            var2 = new FileUtils.FileDeleteException(var1.getAbsolutePath());
         } else if(!var0.getParentFile().exists()) {
            var2 = new FileUtils.ParentDirNotFoundException(var0.getAbsolutePath());
         } else if(!var0.exists()) {
            var2 = new FileNotFoundException(var0.getAbsolutePath());
         }

         StringBuilder var3 = new StringBuilder();
         var3.append("Unknown error renaming ");
         var3.append(var0.getAbsolutePath());
         var3.append(" to ");
         var3.append(var1.getAbsolutePath());
         throw new FileUtils.RenameException(var3.toString(), (Throwable)var2);
      }
   }

   public static class RenameException extends IOException {

      public RenameException(String var1) {
         super(var1);
      }

      public RenameException(String var1, Throwable var2) {
         super(var1);
         this.initCause(var2);
      }
   }

   public static class FileDeleteException extends IOException {

      public FileDeleteException(String var1) {
         super(var1);
      }
   }

   public static class CreateDirectoryException extends IOException {

      public CreateDirectoryException(String var1) {
         super(var1);
      }

      public CreateDirectoryException(String var1, Throwable var2) {
         super(var1);
         this.initCause(var2);
      }
   }

   public static class ParentDirNotFoundException extends FileNotFoundException {

      public ParentDirNotFoundException(String var1) {
         super(var1);
      }
   }
}
