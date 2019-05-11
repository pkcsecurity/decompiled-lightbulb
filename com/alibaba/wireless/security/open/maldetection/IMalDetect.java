package com.alibaba.wireless.security.open.maldetection;

import com.alibaba.wireless.security.open.IComponent;

public interface IMalDetect extends IComponent {

   void registerCallBack(IMalDetect.ICallBack var1);

   void unregisterCallBack(IMalDetect.ICallBack var1);

   public interface ICallBack {

      void onDetection(int var1, String var2, String var3);
   }
}
