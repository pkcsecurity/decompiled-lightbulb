import android.content.Context;
import android.os.Process;
import android.os.SystemClock;
import bif.1;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Map;

public final class bif implements UncaughtExceptionHandler {

   private static String a;
   private static bif c = new bif();
   private UncaughtExceptionHandler b;
   private Context d;
   private Map<String, String> e = new HashMap();


   // $FF: synthetic method
   static Context a(bif var0) {
      return var0.d;
   }

   public static bif a() {
      return c;
   }

   private boolean a(Throwable var1) {
      if(var1 == null) {
         return false;
      } else {
         try {
            this.b(this.d);
            (new 1(this, this.b(var1))).start();
            SystemClock.sleep(3000L);
         } catch (Exception var2) {
            var2.printStackTrace();
         }

         return true;
      }
   }

   private String b(Throwable param1) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public void a(Context var1) {
      this.d = var1;
      this.b = Thread.getDefaultUncaughtExceptionHandler();
      Thread.setDefaultUncaughtExceptionHandler(this);
   }

   public void b(Context param1) {
      // $FF: Couldn't be decompiled
   }

   public void uncaughtException(Thread var1, Throwable var2) {
      if(!this.a(var2) && this.b != null) {
         this.b.uncaughtException(var1, var2);
      } else {
         SystemClock.sleep(3000L);
         Process.killProcess(Process.myPid());
         System.exit(1);
      }
   }
}
