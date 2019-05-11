package com.alibaba.wireless.security.open;

import android.content.Context;
import com.alibaba.wireless.security.framework.b;
import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.a;
import com.alibaba.wireless.security.open.atlasencrypt.IAtlasEncryptComponent;
import com.alibaba.wireless.security.open.datacollection.IDataCollectionComponent;
import com.alibaba.wireless.security.open.dynamicdataencrypt.IDynamicDataEncryptComponent;
import com.alibaba.wireless.security.open.dynamicdatastore.IDynamicDataStoreComponent;
import com.alibaba.wireless.security.open.initialize.IInitializeComponent;
import com.alibaba.wireless.security.open.initialize.d;
import com.alibaba.wireless.security.open.maldetection.IMalDetect;
import com.alibaba.wireless.security.open.nocaptcha.INoCaptchaComponent;
import com.alibaba.wireless.security.open.opensdk.IOpenSDKComponent;
import com.alibaba.wireless.security.open.pkgvaliditycheck.IPkgValidityCheckComponent;
import com.alibaba.wireless.security.open.safetoken.ISafeTokenComponent;
import com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent;
import com.alibaba.wireless.security.open.simulatordetect.ISimulatorDetectComponent;
import com.alibaba.wireless.security.open.staticdataencrypt.IStaticDataEncryptComponent;
import com.alibaba.wireless.security.open.staticdatastore.IStaticDataStoreComponent;
import com.alibaba.wireless.security.open.statickeyencrypt.IStaticKeyEncryptComponent;
import com.alibaba.wireless.security.open.umid.IUMIDComponent;
import java.util.Map;

public class SecurityGuardManager {

   private static volatile SecurityGuardManager b;
   private static volatile IInitializeComponent c;
   private static final Object d = new Object();
   private b a;
   private final Map<Integer, Class> e = new a(this);


   private SecurityGuardManager(Context var1) {
      this.a = b.a(var1);
   }

   public static IInitializeComponent getInitializer() {
      // $FF: Couldn't be decompiled
   }

   public static SecurityGuardManager getInstance(Context param0) throws SecException {
      // $FF: Couldn't be decompiled
   }

   IComponent a(int var1) {
      try {
         IComponent var2 = (IComponent)this.getInterface((Class)this.e.get(Integer.valueOf(var1)));
         return var2;
      } catch (SecException var3) {
         var3.printStackTrace();
         return null;
      }
   }

   public IAtlasEncryptComponent getAtlasEncryptComp() {
      IAtlasEncryptComponent var1 = (IAtlasEncryptComponent)this.a(13);
      if(var1 == null) {
         (new SecException(1098)).printStackTrace();
      }

      return var1;
   }

   public IDataCollectionComponent getDataCollectionComp() {
      return (IDataCollectionComponent)this.a(5);
   }

   public IDynamicDataEncryptComponent getDynamicDataEncryptComp() {
      return (IDynamicDataEncryptComponent)this.a(7);
   }

   public IDynamicDataStoreComponent getDynamicDataStoreComp() {
      return (IDynamicDataStoreComponent)this.a(2);
   }

   public <T extends Object> T getInterface(Class<T> var1) throws SecException {
      return this.a.a(var1);
   }

   public IMalDetect getMalDetectionComp() {
      IMalDetect var1 = (IMalDetect)this.a(14);
      if(var1 == null) {
         (new SecException(1398)).printStackTrace();
      }

      return var1;
   }

   public INoCaptchaComponent getNoCaptchaComp() {
      INoCaptchaComponent var1 = (INoCaptchaComponent)this.a(15);
      if(var1 == null) {
         (new SecException(1299)).printStackTrace();
      }

      return var1;
   }

   public IOpenSDKComponent getOpenSDKComp() {
      return (IOpenSDKComponent)this.a(10);
   }

   public IPkgValidityCheckComponent getPackageValidityCheckComp() {
      return (IPkgValidityCheckComponent)this.a(12);
   }

   public String getSDKVerison() {
      return d.a();
   }

   public ISafeTokenComponent getSafeTokenComp() {
      ISafeTokenComponent var1 = (ISafeTokenComponent)this.a(16);
      if(var1 == null) {
         (new SecException(1699)).printStackTrace();
      }

      return var1;
   }

   public ISecureSignatureComponent getSecureSignatureComp() {
      return (ISecureSignatureComponent)this.a(1);
   }

   public ISimulatorDetectComponent getSimulatorDetectComp() {
      ISimulatorDetectComponent var1 = (ISimulatorDetectComponent)this.a(8);
      if(var1 == null) {
         (new SecException(1598)).printStackTrace();
      }

      return var1;
   }

   public IStaticDataEncryptComponent getStaticDataEncryptComp() {
      return (IStaticDataEncryptComponent)this.a(6);
   }

   public IStaticDataStoreComponent getStaticDataStoreComp() {
      return (IStaticDataStoreComponent)this.a(3);
   }

   public IStaticKeyEncryptComponent getStaticKeyEncryptComp() {
      return (IStaticKeyEncryptComponent)this.a(9);
   }

   public IUMIDComponent getUMIDComp() {
      return (IUMIDComponent)this.a(11);
   }

   public Boolean isOpen() {
      return Boolean.valueOf(true);
   }
}
