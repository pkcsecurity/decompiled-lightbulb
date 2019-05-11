package com.facebook.react.uimanager;

import com.facebook.react.common.ClearableSynchronizedPool;
import com.facebook.yoga.YogaNode;

public class YogaNodePool {

   private static final Object sInitLock = new Object();
   private static ClearableSynchronizedPool<YogaNode> sPool;


   public static ClearableSynchronizedPool<YogaNode> get() {
      // $FF: Couldn't be decompiled
   }
}
