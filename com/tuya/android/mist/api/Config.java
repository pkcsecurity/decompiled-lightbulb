package com.tuya.android.mist.api;

import com.tuya.android.mist.api.Config.ClientInfoProvider;
import com.tuya.android.mist.api.Config.EncryptProvider;
import com.tuya.android.mist.api.Config.Logger;
import com.tuya.android.mist.api.Config.Monitor;
import com.tuya.android.mist.api.Config.ResProvider;
import com.tuya.android.mist.api.Config.ScriptProvider;

public abstract class Config {

   protected ClientInfoProvider clientInfoProvider;
   protected EncryptProvider encryptProvider;
   protected Logger logger;
   protected Monitor monitor;
   protected ResProvider resProvider;
   protected ScriptProvider scriptProvider;


   public ClientInfoProvider getClientInfoProvider() {
      return this.clientInfoProvider;
   }

   public EncryptProvider getEncryptProvider() {
      return this.encryptProvider;
   }

   public Logger getLogger() {
      return this.logger;
   }

   public Monitor getMonitor() {
      return this.monitor;
   }

   public ResProvider getResProvider() {
      return this.resProvider;
   }

   public ScriptProvider getScriptProvider() {
      return this.scriptProvider;
   }

   public abstract boolean isDebug();

   public void setScriptProvider(ScriptProvider var1) {
      this.scriptProvider = var1;
   }
}
