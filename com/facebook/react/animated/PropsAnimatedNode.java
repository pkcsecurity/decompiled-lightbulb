package com.facebook.react.animated;

import com.facebook.react.animated.AnimatedNode;
import com.facebook.react.animated.NativeAnimatedNodesManager;
import com.facebook.react.animated.StyleAnimatedNode;
import com.facebook.react.animated.ValueAnimatedNode;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.UIImplementation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class PropsAnimatedNode extends AnimatedNode {

   private int mConnectedViewTag = -1;
   private final ReactStylesDiffMap mDiffMap;
   private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
   private final JavaOnlyMap mPropMap;
   private final Map<String, Integer> mPropNodeMapping;
   private final UIImplementation mUIImplementation;


   PropsAnimatedNode(ReadableMap var1, NativeAnimatedNodesManager var2, UIImplementation var3) {
      var1 = var1.getMap("props");
      ReadableMapKeySetIterator var5 = var1.keySetIterator();
      this.mPropNodeMapping = new HashMap();

      while(var5.hasNextKey()) {
         String var6 = var5.nextKey();
         int var4 = var1.getInt(var6);
         this.mPropNodeMapping.put(var6, Integer.valueOf(var4));
      }

      this.mPropMap = new JavaOnlyMap();
      this.mDiffMap = new ReactStylesDiffMap(this.mPropMap);
      this.mNativeAnimatedNodesManager = var2;
      this.mUIImplementation = var3;
   }

   public void connectToView(int var1) {
      if(this.mConnectedViewTag != -1) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Animated node ");
         var2.append(this.mTag);
         var2.append(" is already attached to a view");
         throw new JSApplicationIllegalArgumentException(var2.toString());
      } else {
         this.mConnectedViewTag = var1;
      }
   }

   public void disconnectFromView(int var1) {
      if(this.mConnectedViewTag != var1) {
         throw new JSApplicationIllegalArgumentException("Attempting to disconnect view that has not been connected with the given animated node");
      } else {
         this.mConnectedViewTag = -1;
      }
   }

   public void restoreDefaultValues() {
      ReadableMapKeySetIterator var1 = this.mPropMap.keySetIterator();

      while(var1.hasNextKey()) {
         this.mPropMap.putNull(var1.nextKey());
      }

      this.mUIImplementation.synchronouslyUpdateViewOnUIThread(this.mConnectedViewTag, this.mDiffMap);
   }

   public final void updateView() {
      if(this.mConnectedViewTag != -1) {
         Iterator var2 = this.mPropNodeMapping.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            AnimatedNode var1 = this.mNativeAnimatedNodesManager.getNodeById(((Integer)var3.getValue()).intValue());
            if(var1 == null) {
               throw new IllegalArgumentException("Mapped property node does not exists");
            }

            if(var1 instanceof StyleAnimatedNode) {
               ((StyleAnimatedNode)var1).collectViewUpdates(this.mPropMap);
            } else {
               if(!(var1 instanceof ValueAnimatedNode)) {
                  StringBuilder var4 = new StringBuilder();
                  var4.append("Unsupported type of node used in property node ");
                  var4.append(var1.getClass());
                  throw new IllegalArgumentException(var4.toString());
               }

               this.mPropMap.putDouble((String)var3.getKey(), ((ValueAnimatedNode)var1).getValue());
            }
         }

         this.mUIImplementation.synchronouslyUpdateViewOnUIThread(this.mConnectedViewTag, this.mDiffMap);
      }
   }
}
