package com.facebook.react.animation;

import android.util.SparseArray;
import com.facebook.react.animation.Animation;
import com.facebook.react.bridge.UiThreadUtil;

public class AnimationRegistry {

   private final SparseArray<Animation> mRegistry = new SparseArray();


   public Animation getAnimation(int var1) {
      UiThreadUtil.assertOnUiThread();
      return (Animation)this.mRegistry.get(var1);
   }

   public void registerAnimation(Animation var1) {
      UiThreadUtil.assertOnUiThread();
      this.mRegistry.put(var1.getAnimationID(), var1);
   }

   public Animation removeAnimation(int var1) {
      UiThreadUtil.assertOnUiThread();
      Animation var2 = (Animation)this.mRegistry.get(var1);
      if(var2 != null) {
         this.mRegistry.delete(var1);
      }

      return var2;
   }
}
