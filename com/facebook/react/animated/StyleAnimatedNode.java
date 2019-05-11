package com.facebook.react.animated;

import com.facebook.react.animated.AnimatedNode;
import com.facebook.react.animated.NativeAnimatedNodesManager;
import com.facebook.react.animated.TransformAnimatedNode;
import com.facebook.react.animated.ValueAnimatedNode;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class StyleAnimatedNode extends AnimatedNode {

   private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
   private final Map<String, Integer> mPropMapping;


   StyleAnimatedNode(ReadableMap var1, NativeAnimatedNodesManager var2) {
      var1 = var1.getMap("style");
      ReadableMapKeySetIterator var4 = var1.keySetIterator();
      this.mPropMapping = new HashMap();

      while(var4.hasNextKey()) {
         String var5 = var4.nextKey();
         int var3 = var1.getInt(var5);
         this.mPropMapping.put(var5, Integer.valueOf(var3));
      }

      this.mNativeAnimatedNodesManager = var2;
   }

   public void collectViewUpdates(JavaOnlyMap var1) {
      Iterator var3 = this.mPropMapping.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         AnimatedNode var2 = this.mNativeAnimatedNodesManager.getNodeById(((Integer)var4.getValue()).intValue());
         if(var2 == null) {
            throw new IllegalArgumentException("Mapped style node does not exists");
         }

         if(var2 instanceof TransformAnimatedNode) {
            ((TransformAnimatedNode)var2).collectViewUpdates(var1);
         } else {
            if(!(var2 instanceof ValueAnimatedNode)) {
               StringBuilder var5 = new StringBuilder();
               var5.append("Unsupported type of node used in property node ");
               var5.append(var2.getClass());
               throw new IllegalArgumentException(var5.toString());
            }

            var1.putDouble((String)var4.getKey(), ((ValueAnimatedNode)var2).getValue());
         }
      }

   }
}
