package com.facebook.react.animated;

import com.facebook.react.animated.ValueAnimatedNode;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import java.util.List;
import javax.annotation.Nullable;

class EventAnimationDriver implements RCTEventEmitter {

   private List<String> mEventPath;
   ValueAnimatedNode mValueNode;


   public EventAnimationDriver(List<String> var1, ValueAnimatedNode var2) {
      this.mEventPath = var1;
      this.mValueNode = var2;
   }

   public void receiveEvent(int var1, String var2, @Nullable WritableMap var3) {
      if(var3 == null) {
         throw new IllegalArgumentException("Native animated events must have event data.");
      } else {
         for(var1 = 0; var1 < this.mEventPath.size() - 1; ++var1) {
            var3 = ((ReadableMap)var3).getMap((String)this.mEventPath.get(var1));
         }

         this.mValueNode.mValue = ((ReadableMap)var3).getDouble((String)this.mEventPath.get(this.mEventPath.size() - 1));
      }
   }

   public void receiveTouches(String var1, WritableArray var2, WritableArray var3) {
      throw new RuntimeException("receiveTouches is not support by native animated events");
   }
}
