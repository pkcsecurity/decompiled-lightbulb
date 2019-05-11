package com.facebook.react.animated;

import com.facebook.react.animated.AnimatedNode;
import com.facebook.react.animated.NativeAnimatedNodesManager;
import com.facebook.react.animated.ValueAnimatedNode;
import com.facebook.react.bridge.JavaOnlyArray;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class TransformAnimatedNode extends AnimatedNode {

   private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
   private final List<TransformAnimatedNode.TransformConfig> mTransformConfigs;


   TransformAnimatedNode(ReadableMap var1, NativeAnimatedNodesManager var2) {
      ReadableArray var7 = var1.getArray("transforms");
      this.mTransformConfigs = new ArrayList(var7.size());

      for(int var3 = 0; var3 < var7.size(); ++var3) {
         ReadableMap var4 = var7.getMap(var3);
         String var5 = var4.getString("property");
         if(var4.getString("type").equals("animated")) {
            TransformAnimatedNode.AnimatedTransformConfig var6 = new TransformAnimatedNode.AnimatedTransformConfig(null);
            var6.mProperty = var5;
            var6.mNodeTag = var4.getInt("nodeTag");
            this.mTransformConfigs.add(var6);
         } else {
            TransformAnimatedNode.StaticTransformConfig var8 = new TransformAnimatedNode.StaticTransformConfig(null);
            var8.mProperty = var5;
            var8.mValue = var4.getDouble("value");
            this.mTransformConfigs.add(var8);
         }
      }

      this.mNativeAnimatedNodesManager = var2;
   }

   public void collectViewUpdates(JavaOnlyMap var1) {
      ArrayList var6 = new ArrayList(this.mTransformConfigs.size());

      double var2;
      TransformAnimatedNode.TransformConfig var8;
      for(Iterator var7 = this.mTransformConfigs.iterator(); var7.hasNext(); var6.add(JavaOnlyMap.of(new Object[]{var8.mProperty, Double.valueOf(var2)}))) {
         var8 = (TransformAnimatedNode.TransformConfig)var7.next();
         if(var8 instanceof TransformAnimatedNode.AnimatedTransformConfig) {
            int var4 = ((TransformAnimatedNode.AnimatedTransformConfig)var8).mNodeTag;
            AnimatedNode var5 = this.mNativeAnimatedNodesManager.getNodeById(var4);
            if(var5 == null) {
               throw new IllegalArgumentException("Mapped style node does not exists");
            }

            if(!(var5 instanceof ValueAnimatedNode)) {
               StringBuilder var9 = new StringBuilder();
               var9.append("Unsupported type of node used as a transform child node ");
               var9.append(var5.getClass());
               throw new IllegalArgumentException(var9.toString());
            }

            var2 = ((ValueAnimatedNode)var5).getValue();
         } else {
            var2 = ((TransformAnimatedNode.StaticTransformConfig)var8).mValue;
         }
      }

      var1.putArray("transform", JavaOnlyArray.from(var6));
   }

   class TransformConfig {

      public String mProperty;


      private TransformConfig() {}

      // $FF: synthetic method
      TransformConfig(Object var2) {
         this();
      }
   }

   class StaticTransformConfig extends TransformAnimatedNode.TransformConfig {

      public double mValue;


      private StaticTransformConfig() {
         super(null);
      }

      // $FF: synthetic method
      StaticTransformConfig(Object var2) {
         this();
      }
   }

   class AnimatedTransformConfig extends TransformAnimatedNode.TransformConfig {

      public int mNodeTag;


      private AnimatedTransformConfig() {
         super(null);
      }

      // $FF: synthetic method
      AnimatedTransformConfig(Object var2) {
         this();
      }
   }
}
