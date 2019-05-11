package com.alibaba.wireless.security.open.staticdatastore;

import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

public interface IStaticDataStoreComponent extends IComponent {

   String getAppKeyByIndex(int var1, String var2) throws SecException;

   String getExtraData(String var1, String var2) throws SecException;

   int getKeyType(String var1, String var2) throws SecException;
}
