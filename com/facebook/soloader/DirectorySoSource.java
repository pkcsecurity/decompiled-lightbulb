package com.facebook.soloader;

import com.facebook.soloader.MinElf;
import com.facebook.soloader.SoLoader;
import com.facebook.soloader.SoSource;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class DirectorySoSource extends SoSource {

   public static final int ON_LD_LIBRARY_PATH = 2;
   public static final int RESOLVE_DEPENDENCIES = 1;
   protected final int flags;
   protected final File soDirectory;


   public DirectorySoSource(File var1, int var2) {
      this.soDirectory = var1;
      this.flags = var2;
   }

   private static String[] getDependencies(File var0) throws IOException {
      try {
         String[] var3 = MinElf.extract_DT_NEEDED(var0);
         return var3;
      } finally {
         ;
      }
   }

   public void addToLdLibraryPath(Collection<String> var1) {
      var1.add(this.soDirectory.getAbsolutePath());
   }

   public int loadLibrary(String var1, int var2) throws IOException {
      return this.loadLibraryFrom(var1, var2, this.soDirectory);
   }

   protected int loadLibraryFrom(String var1, int var2, File var3) throws IOException {
      File var7 = new File(var3, var1);
      boolean var5 = var7.exists();
      int var4 = 0;
      if(!var5) {
         return 0;
      } else if((var2 & 1) != 0 && (this.flags & 2) != 0) {
         return 2;
      } else {
         if((this.flags & 1) != 0) {
            for(String[] var8 = getDependencies(var7); var4 < var8.length; ++var4) {
               String var6 = var8[var4];
               if(!var6.startsWith("/")) {
                  SoLoader.loadLibraryBySoName(var6, var2 | 1);
               }
            }
         }

         System.load(var7.getAbsolutePath());
         return 1;
      }
   }

   public File unpackLibrary(String var1) throws IOException {
      File var2 = new File(this.soDirectory, var1);
      return var2.exists()?var2:null;
   }
}
