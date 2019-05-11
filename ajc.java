import android.content.Context;
import android.os.Bundle;
import com.tuya.smart.api.service.SchemeService;

public class ajc {

   public static String a() {
      return ((SchemeService)aje.a().a(SchemeService.class.getName())).getAppScheme();
   }

   public static void a(ajb var0) {
      ((SchemeService)aje.a().a(SchemeService.class.getName())).execute(var0);
   }

   public static void a(Context var0, String var1) {
      a(var0, var1, (Bundle)null, -1);
   }

   public static void a(Context var0, String var1, Bundle var2) {
      a(var0, var1, var2, -1);
   }

   public static void a(Context var0, String var1, Bundle var2, int var3) {
      ((SchemeService)aje.a().a(SchemeService.class.getName())).execute(var0, var1, var2, var3);
   }

   public static void a(String var0) {
      ((SchemeService)aje.a().a(SchemeService.class.getName())).setScheme(var0);
   }

   public static void a(String var0, Bundle var1) {
      SchemeService var3 = (SchemeService)aje.a().a(SchemeService.class.getName());
      Bundle var2 = var1;
      if(var1 == null) {
         var2 = new Bundle();
      }

      var3.sendEvent(var0, var2);
   }

   public static ajb b(Context var0, String var1) {
      return b(var0, var1, (Bundle)null);
   }

   public static ajb b(Context var0, String var1, Bundle var2) {
      return b(var0, var1, var2, -1);
   }

   public static ajb b(Context var0, String var1, Bundle var2, int var3) {
      ajb var4 = new ajb(var0, var1);
      var4.a(var2);
      var4.a(var3);
      return var4;
   }

   public static boolean b(String var0) {
      return ((SchemeService)aje.a().a(SchemeService.class.getName())).isSchemeSupport(var0);
   }
}
