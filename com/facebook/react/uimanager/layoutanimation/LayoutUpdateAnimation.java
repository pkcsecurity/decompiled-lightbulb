package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.animation.Animation;
import com.facebook.react.uimanager.layoutanimation.AbstractLayoutAnimation;
import com.facebook.react.uimanager.layoutanimation.PositionAndSizeAnimation;
import javax.annotation.Nullable;

class LayoutUpdateAnimation extends AbstractLayoutAnimation {

   private static final boolean USE_TRANSLATE_ANIMATION = false;


   @Nullable
   Animation createAnimationImpl(View var1, int var2, int var3, int var4, int var5) {
      float var6 = var1.getX();
      float var7 = (float)var2;
      boolean var10 = true;
      boolean var8;
      if(var6 == var7 && var1.getY() == (float)var3) {
         var8 = false;
      } else {
         var8 = true;
      }

      boolean var9 = var10;
      if(var1.getWidth() == var4) {
         if(var1.getHeight() != var5) {
            var9 = var10;
         } else {
            var9 = false;
         }
      }

      return !var8 && !var9?null:new PositionAndSizeAnimation(var1, var2, var3, var4, var5);
   }

   boolean isValid() {
      return this.mDurationMs > 0;
   }
}
