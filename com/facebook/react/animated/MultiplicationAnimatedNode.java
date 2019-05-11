package com.facebook.react.animated;

import com.facebook.react.animated.AnimatedNode;
import com.facebook.react.animated.NativeAnimatedNodesManager;
import com.facebook.react.animated.ValueAnimatedNode;
import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

class MultiplicationAnimatedNode extends ValueAnimatedNode {

   private final int[] mInputNodes;
   private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;


   public MultiplicationAnimatedNode(ReadableMap var1, NativeAnimatedNodesManager var2) {
      this.mNativeAnimatedNodesManager = var2;
      ReadableArray var4 = var1.getArray("input");
      this.mInputNodes = new int[var4.size()];

      for(int var3 = 0; var3 < this.mInputNodes.length; ++var3) {
         this.mInputNodes[var3] = var4.getInt(var3);
      }

   }

   public void update() {
      this.mValue = 1.0D;

      for(int var1 = 0; var1 < this.mInputNodes.length; ++var1) {
         AnimatedNode var2 = this.mNativeAnimatedNodesManager.getNodeById(this.mInputNodes[var1]);
         if(var2 == null || !(var2 instanceof ValueAnimatedNode)) {
            throw new JSApplicationCausedNativeException("Illegal node ID set as an input for Animated.multiply node");
         }

         this.mValue *= ((ValueAnimatedNode)var2).getValue();
      }

   }
}
