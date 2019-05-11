import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

public abstract class ajd implements ComponentCallbacks2 {

   protected Context mBase;


   public void attachBaseContext(Context var1) {
      if(this.mBase != null) {
         throw new IllegalStateException("Base context already set");
      } else {
         this.mBase = var1;
      }
   }

   public void onConfigurationChanged(Configuration var1) {}

   public void onCreate() {}

   public void onDestroy() {}

   public void onLowMemory() {}

   public void onTrimMemory(int var1) {}
}
