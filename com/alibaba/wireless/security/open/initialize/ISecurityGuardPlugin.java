package com.alibaba.wireless.security.open.initialize;

import android.content.Context;
import android.content.pm.PackageInfo;
import com.alibaba.wireless.security.open.IRouterComponent;
import com.alibaba.wireless.security.open.SecException;
import java.util.HashMap;

public interface ISecurityGuardPlugin {

   String METADATA_ACTIVITIES = "activities";
   String METADATA_DEPENDENCIES = "dependencies";
   String METADATA_HASHES = "hashes";
   String METADATA_PLUGINID = "pluginid";


   Context getContext();

   <T extends Object> T getInterface(Class<T> var1);

   HashMap<Class, Object> getInterfaceToImplMaps();

   String getMetaData(String var1);

   PackageInfo getPackageInfo();

   String getPluginName();

   IRouterComponent getRouter();

   String getVersion();

   void init(Context var1, Object ... var2) throws SecException;

   IRouterComponent onPluginLoaded(Context var1, IRouterComponent var2, PackageInfo var3, String var4) throws SecException;

   void setContext(Context var1);

   void setPackageInfo(PackageInfo var1);

   void setRouter(IRouterComponent var1);
}
