import android.content.Context;
import com.alibaba.fastjson.JSONReader;
import com.tuya.smart.mistbase.bean.MistConfigBean;
import com.tuya.smart.mistbase.bean.PageBean;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

public class avu {

   private static avu a;
   private MistConfigBean b;


   public static avu a() {
      if(a == null) {
         a = new avu();
      }

      return a;
   }

   private PageBean a(String var1) {
      if(this.b()) {
         Iterator var2 = this.b.getHome().getSubPages().iterator();

         while(var2.hasNext()) {
            PageBean var3 = (PageBean)var2.next();
            if(var3.getName().equals(var1)) {
               return var3;
            }
         }
      }

      return null;
   }

   public void a(Context var1, String var2) {
      try {
         InputStream var4 = var1.getAssets().open(var2);
         this.b = (MistConfigBean)(new JSONReader(new InputStreamReader(var4))).readObject(MistConfigBean.class);
         var4.close();
      } catch (IOException var3) {
         throw new RuntimeException(var3);
      }
   }

   public boolean b() {
      return this.b != null;
   }

   public MistConfigBean c() {
      return this.b;
   }

   public PageBean d() {
      return this.a("personal_center");
   }
}
