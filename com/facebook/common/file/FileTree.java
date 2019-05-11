package com.facebook.common.file;

import com.facebook.common.file.FileTreeVisitor;
import java.io.File;

public class FileTree {

   public static boolean deleteContents(File var0) {
      File[] var5 = var0.listFiles();
      boolean var4 = true;
      boolean var3 = true;
      if(var5 != null) {
         int var2 = var5.length;
         int var1 = 0;

         while(true) {
            var4 = var3;
            if(var1 >= var2) {
               break;
            }

            var3 &= deleteRecursively(var5[var1]);
            ++var1;
         }
      }

      return var4;
   }

   public static boolean deleteRecursively(File var0) {
      if(var0.isDirectory()) {
         deleteContents(var0);
      }

      return var0.delete();
   }

   public static void walkFileTree(File var0, FileTreeVisitor var1) {
      var1.preVisitDirectory(var0);
      File[] var4 = var0.listFiles();
      if(var4 != null) {
         int var3 = var4.length;

         for(int var2 = 0; var2 < var3; ++var2) {
            File var5 = var4[var2];
            if(var5.isDirectory()) {
               walkFileTree(var5, var1);
            } else {
               var1.visitFile(var5);
            }
         }
      }

      var1.postVisitDirectory(var0);
   }
}
