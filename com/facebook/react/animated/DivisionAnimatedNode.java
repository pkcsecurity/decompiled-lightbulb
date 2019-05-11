package com.facebook.react.animated;

import com.facebook.react.animated.AnimatedNode;
import com.facebook.react.animated.NativeAnimatedNodesManager;
import com.facebook.react.animated.ValueAnimatedNode;
import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

class DivisionAnimatedNode extends ValueAnimatedNode {

   private final int[] mInputNodes;
   private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;


   public DivisionAnimatedNode(ReadableMap var1, NativeAnimatedNodesManager var2) {
      this.mNativeAnimatedNodesManager = var2;
      ReadableArray var4 = var1.getArray("input");
      this.mInputNodes = new int[var4.size()];

      for(int var3 = 0; var3 < this.mInputNodes.length; ++var3) {
         this.mInputNodes[var3] = var4.getInt(var3);
      }

   }

   public void update() {
      for(int var3 = 0; var3 < this.mInputNodes.length; ++var3) {
         AnimatedNode var4 = this.mNativeAnimatedNodesManager.getNodeById(this.mInputNodes[var3]);
         if(var4 == null || !(var4 instanceof ValueAnimatedNode)) {
            throw new JSApplicationCausedNativeException("Illegal node ID set as an input for Animated.divide node");
         }

         double var1 = ((ValueAnimatedNode)var4).getValue();
         if(var3 == 0) {
            this.mValue = var1;
         } else {
            if(var1 == 0.0D) {
               throw new JSApplicationCausedNativeException("Detected a division by zero in Animated.divide node");
            }

            this.mValue /= var1;
         }
      }

   }
}
