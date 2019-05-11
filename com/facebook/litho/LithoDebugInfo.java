package com.facebook.litho;

import com.facebook.litho.ComponentsPools;
import com.facebook.litho.PoolWithDebugInfo;
import java.util.ArrayList;
import java.util.List;

public final class LithoDebugInfo {

   public static List<PoolWithDebugInfo> getPools() {
      ArrayList var0 = new ArrayList();
      var0.addAll(ComponentsPools.getMountContentPools());
      var0.add(ComponentsPools.sLayoutStatePool);
      var0.add(ComponentsPools.sInternalNodePool);
      var0.add(ComponentsPools.sNodeInfoPool);
      var0.add(ComponentsPools.sViewNodeInfoPool);
      var0.add(ComponentsPools.sYogaNodePool);
      var0.add(ComponentsPools.sMountItemPool);
      var0.add(ComponentsPools.sLayoutOutputPool);
      var0.add(ComponentsPools.sVisibilityOutputPool);
      var0.add(ComponentsPools.sVisibilityItemPool);
      var0.add(ComponentsPools.sOutputPool);
      var0.add(ComponentsPools.sDiffNodePool);
      var0.add(ComponentsPools.sDiffPool);
      var0.add(ComponentsPools.sComponentTreeBuilderPool);
      var0.add(ComponentsPools.sStateHandlerPool);
      var0.add(ComponentsPools.sMountItemScrapArrayPool);
      var0.add(ComponentsPools.sRectFPool);
      var0.add(ComponentsPools.sRectPool);
      var0.add(ComponentsPools.sEdgesPool);
      var0.add(ComponentsPools.sDisplayListDrawablePool);
      var0.add(ComponentsPools.sArraySetPool);
      var0.add(ComponentsPools.sArrayDequePool);
      if(ComponentsPools.sTestOutputPool != null) {
         var0.add(ComponentsPools.sTestOutputPool);
      }

      if(ComponentsPools.sTestItemPool != null) {
         var0.add(ComponentsPools.sTestItemPool);
      }

      if(ComponentsPools.sBorderColorDrawablePool != null) {
         var0.add(ComponentsPools.sBorderColorDrawablePool);
      }

      return var0;
   }
}
