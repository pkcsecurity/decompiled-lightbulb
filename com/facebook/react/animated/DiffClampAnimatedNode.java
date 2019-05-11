package com.facebook.react.animated;

import com.facebook.react.animated.AnimatedNode;
import com.facebook.react.animated.NativeAnimatedNodesManager;
import com.facebook.react.animated.ValueAnimatedNode;
import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReadableMap;

class DiffClampAnimatedNode extends ValueAnimatedNode {

   private final int mInputNodeTag;
   private double mLastValue;
   private final double mMax;
   private final double mMin;
   private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;


   public DiffClampAnimatedNode(ReadableMap var1, NativeAnimatedNodesManager var2) {
      this.mNativeAnimatedNodesManager = var2;
      this.mInputNodeTag = var1.getInt("input");
      this.mMin = var1.getDouble("min");
      this.mMax = var1.getDouble("max");
      this.mLastValue = 0.0D;
      this.mValue = 0.0D;
   }

   private double getInputNodeValue() {
      AnimatedNode var1 = this.mNativeAnimatedNodesManager.getNodeById(this.mInputNodeTag);
      if(var1 != null && var1 instanceof ValueAnimatedNode) {
         return ((ValueAnimatedNode)var1).getValue();
      } else {
         throw new JSApplicationCausedNativeException("Illegal node ID set as an input for Animated.DiffClamp node");
      }
   }

   public void update() {
      double var1 = this.getInputNodeValue();
      double var3 = this.mLastValue;
      this.mLastValue = var1;
      this.mValue = Math.min(Math.max(this.mValue + (var1 - var3), this.mMin), this.mMax);
   }
}
