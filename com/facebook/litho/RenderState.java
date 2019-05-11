package com.facebook.litho;

import com.facebook.litho.Component;
import com.facebook.litho.ComponentLifecycle;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RenderState {

   private final Map<String, ComponentLifecycle.RenderData> mRenderData = new HashMap();
   private final Set<String> mSeenGlobalKeys = new HashSet();


   private void applyPreviousRenderData(Component var1) {
      if(!var1.needsPreviousRenderData()) {
         throw new RuntimeException("Trying to apply previous render data to component that doesn\'t support it");
      } else {
         String var2 = var1.getGlobalKey();
         var1.applyPreviousRenderData((ComponentLifecycle.RenderData)this.mRenderData.get(var2));
      }
   }

   private void recordRenderData(Component var1) {
      if(!var1.needsPreviousRenderData()) {
         throw new RuntimeException("Trying to record previous render data for component that doesn\'t support it");
      } else {
         String var2 = var1.getGlobalKey();
         if(this.mSeenGlobalKeys.contains(var2)) {
            StringBuilder var3 = new StringBuilder();
            var3.append("Cannot record previous render data for ");
            var3.append(var1.getSimpleName());
            var3.append(", found another Component with the same key: ");
            var3.append(var2);
            throw new RuntimeException(var3.toString());
         } else {
            this.mSeenGlobalKeys.add(var2);
            ComponentLifecycle.RenderData var4 = var1.recordRenderData((ComponentLifecycle.RenderData)this.mRenderData.get(var2));
            this.mRenderData.put(var2, var4);
         }
      }
   }

   void applyPreviousRenderData(List<Component> var1) {
      if(var1 != null) {
         int var2 = 0;

         for(int var3 = var1.size(); var2 < var3; ++var2) {
            this.applyPreviousRenderData((Component)var1.get(var2));
         }

      }
   }

   void recordRenderData(List<Component> var1) {
      if(var1 != null) {
         int var2 = 0;

         for(int var3 = var1.size(); var2 < var3; ++var2) {
            this.recordRenderData((Component)var1.get(var2));
         }

         this.mSeenGlobalKeys.clear();
      }
   }

   void reset() {
      this.mRenderData.clear();
      this.mSeenGlobalKeys.clear();
   }
}
