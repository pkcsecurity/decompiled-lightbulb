package com.facebook.react.animated;

import com.facebook.react.animated.AnimatedNode;
import com.facebook.react.animated.NativeAnimatedNodesManager;
import com.facebook.react.animated.ValueAnimatedNode;
import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReadableMap;

class ModulusAnimatedNode extends ValueAnimatedNode {

   private final int mInputNode;
   private final int mModulus;
   private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;


   public ModulusAnimatedNode(ReadableMap var1, NativeAnimatedNodesManager var2) {
      this.mNativeAnimatedNodesManager = var2;
      this.mInputNode = var1.getInt("input");
      this.mModulus = var1.getInt("modulus");
   }

   public void update() {
      AnimatedNode var1 = this.mNativeAnimatedNodesManager.getNodeById(this.mInputNode);
      if(var1 != null && var1 instanceof ValueAnimatedNode) {
         this.mValue = ((ValueAnimatedNode)var1).getValue() % (double)this.mModulus;
      } else {
         throw new JSApplicationCausedNativeException("Illegal node ID set as an input for Animated.modulus node");
      }
   }
}
