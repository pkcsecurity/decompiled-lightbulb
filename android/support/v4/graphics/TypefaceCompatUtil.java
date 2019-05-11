package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class TypefaceCompatUtil {

   private static final String CACHE_FILE_PREFIX = ".font";
   private static final String TAG = "TypefaceCompatUtil";


   public static void closeQuietly(Closeable var0) {
      if(var0 != null) {
         try {
            var0.close();
         } catch (IOException var1) {
            return;
         }
      }

   }

   @Nullable
   @RequiresApi(19)
   public static ByteBuffer copyToDirectBuffer(Context param0, Resources param1, int param2) {
      // $FF: Couldn't be decompiled
   }

   public static boolean copyToFile(File var0, Resources var1, int var2) {
      // $FF: Couldn't be decompiled
   }

   public static boolean copyToFile(File param0, InputStream param1) {
      // $FF: Couldn't be decompiled
   }

   @Nullable
   public static File getTempFile(Context var0) {
      StringBuilder var3 = new StringBuilder();
      var3.append(".font");
      var3.append(Process.myPid());
      var3.append("-");
      var3.append(Process.myTid());
      var3.append("-");
      String var7 = var3.toString();

      for(int var1 = 0; var1 < 100; ++var1) {
         File var4 = var0.getCacheDir();
         StringBuilder var5 = new StringBuilder();
         var5.append(var7);
         var5.append(var1);
         var4 = new File(var4, var5.toString());

         boolean var2;
         try {
            var2 = var4.createNewFile();
         } catch (IOException var6) {
            continue;
         }

         if(var2) {
            return var4;
         }
      }

      return null;
   }

   @Nullable
   @RequiresApi(19)
   public static ByteBuffer mmap(Context param0, CancellationSignal param1, Uri param2) {
      // $FF: Couldn't be decompiled
   }

   @Nullable
   @RequiresApi(19)
   private static ByteBuffer mmap(File param0) {
      // $FF: Couldn't be decompiled
   }
}
