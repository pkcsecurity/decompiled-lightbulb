package com.alibaba.wireless.security.open.umid;

import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.umid.IUMIDInitListener;
import com.alibaba.wireless.security.open.umid.IUMIDInitListenerEx;

public interface IUMIDComponent extends IComponent {

   int ENVIRONMENT_DAILY = 2;
   int ENVIRONMENT_ONLINE = 0;
   int ENVIRONMENT_PRE = 1;


   @Deprecated
   String getSecurityToken() throws SecException;

   String getSecurityToken(int var1) throws SecException;

   void initUMID() throws SecException;

   void initUMID(int var1, IUMIDInitListenerEx var2) throws SecException;

   @Deprecated
   void initUMID(String var1, int var2, String var3, IUMIDInitListenerEx var4) throws SecException;

   int initUMIDSync(int var1) throws SecException;

   @Deprecated
   void registerInitListener(IUMIDInitListener var1) throws SecException;

   void setEnvironment(int var1) throws SecException;

   void setOnlineHost(String var1) throws SecException;
}
