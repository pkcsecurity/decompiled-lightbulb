package com.alibaba.wireless.security.framework.b;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class b {

   private FileChannel a = null;
   private FileLock b = null;
   private RandomAccessFile c = null;
   private File d = null;
   private boolean e = true;


   public b(Context var1, String var2) {
      try {
         this.d = new File(var2);
         if(!this.d.exists()) {
            this.d.createNewFile();
            return;
         }
      } catch (Exception var4) {
         if(!this.d.exists()) {
            try {
               this.d.createNewFile();
               return;
            } catch (Exception var3) {
               if(!this.d.exists()) {
                  this.e = false;
               }
            }
         }
      }

   }

   public boolean a() {
      if(!this.e) {
         return true;
      } else {
         try {
            if(this.d != null) {
               this.c = new RandomAccessFile(this.d, "rw");
               this.a = this.c.getChannel();
               this.b = this.a.lock();
               return true;
            }
         } catch (Exception var2) {
            var2.printStackTrace();
         }

         return false;
      }
   }

   public boolean b() {
      boolean var1 = this.e;
      boolean var2 = true;
      if(!var1) {
         return true;
      } else {
         var1 = var2;

         label40: {
            try {
               if(this.b == null) {
                  break label40;
               }

               this.b.release();
               this.b = null;
            } catch (IOException var6) {
               var1 = false;
               break label40;
            }

            var1 = var2;
         }

         var2 = var1;

         label33: {
            try {
               if(this.a == null) {
                  break label33;
               }

               this.a.close();
               this.a = null;
            } catch (IOException var5) {
               var2 = false;
               break label33;
            }

            var2 = var1;
         }

         try {
            if(this.c != null) {
               this.c.close();
               this.c = null;
            }

            return var2;
         } catch (IOException var4) {
            return false;
         }
      }
   }
}
