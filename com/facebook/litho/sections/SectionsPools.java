package com.facebook.litho.sections;

import com.facebook.litho.RecyclePool;
import com.facebook.litho.sections.SectionTree;

public class SectionsPools {

   private static final RecyclePool<SectionTree.StateUpdatesHolder> sStateUpdatesHolderPool = new RecyclePool("StateUpdatesHolder", 4, true);


   static SectionTree.StateUpdatesHolder acquireStateUpdatesHolder() {
      SectionTree.StateUpdatesHolder var1 = (SectionTree.StateUpdatesHolder)sStateUpdatesHolderPool.acquire();
      SectionTree.StateUpdatesHolder var0 = var1;
      if(var1 == null) {
         var0 = new SectionTree.StateUpdatesHolder();
      }

      return var0;
   }

   static void release(SectionTree.StateUpdatesHolder var0) {
      var0.release();
      sStateUpdatesHolderPool.release(var0);
   }
}
