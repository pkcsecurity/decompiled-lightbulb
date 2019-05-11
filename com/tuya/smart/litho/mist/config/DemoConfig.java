package com.tuya.smart.litho.mist.config;

import com.tuya.smart.litho.mist.api.Config;
import com.tuya.smart.litho.mist.config.DemoClientInfoProvider;
import com.tuya.smart.litho.mist.config.DemoEncryptProvider;
import com.tuya.smart.litho.mist.config.DemoMonitor;
import com.tuya.smart.litho.mist.config.DemoResProvider;

public class DemoConfig extends Config {

   public void create() {
      this.clientInfoProvider = new DemoClientInfoProvider();
      this.resProvider = new DemoResProvider();
      this.encryptProvider = new DemoEncryptProvider();
      this.monitor = new DemoMonitor();
      this.logger = null;
   }

   public boolean isDebug() {
      return true;
   }
}
