import com.tuya.android.mist.api.Config;
import com.tuya.smart.mistbase.config.DemoClientInfoProvider;
import com.tuya.smart.mistbase.config.DemoEncryptProvider;
import com.tuya.smart.mistbase.config.DemoResProvider;

public class avv extends Config {

   public void a() {
      this.clientInfoProvider = new DemoClientInfoProvider();
      this.resProvider = new DemoResProvider();
      this.encryptProvider = new DemoEncryptProvider();
      this.monitor = new avw();
      this.logger = null;
   }

   public boolean isDebug() {
      return true;
   }
}
