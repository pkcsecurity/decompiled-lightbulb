package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.layoutanimation.AbstractLayoutAnimation;
import com.facebook.react.uimanager.layoutanimation.OpacityAnimation;

abstract class BaseLayoutAnimation extends AbstractLayoutAnimation {

   Animation createAnimationImpl(View var1, int var2, int var3, int var4, int var5) {
      if(this.mAnimatedProperty != null) {
         var2 = null.$SwitchMap$com$facebook$react$uimanager$layoutanimation$AnimatedPropertyType[this.mAnimatedProperty.ordinal()];
         float var7 = 0.0F;
         float var6;
         switch(var2) {
         case 1:
            if(this.isReverse()) {
               var6 = var1.getAlpha();
            } else {
               var6 = 0.0F;
            }

            if(!this.isReverse()) {
               var7 = var1.getAlpha();
            }

            return new OpacityAnimation(var1, var6, var7);
         case 2:
            if(this.isReverse()) {
               var6 = 1.0F;
            } else {
               var6 = 0.0F;
            }

            if(this.isReverse()) {
               var7 = 0.0F;
            } else {
               var7 = 1.0F;
            }

            return new ScaleAnimation(var6, var7, var6, var7, 1, 0.5F, 1, 0.5F);
         default:
            StringBuilder var8 = new StringBuilder();
            var8.append("Missing animation for property : ");
            var8.append(this.mAnimatedProperty);
            throw new IllegalViewOperationException(var8.toString());
         }
      } else {
         throw new IllegalViewOperationException("Missing animated property from animation config");
      }
   }

   abstract boolean isReverse();

   boolean isValid() {
      return this.mDurationMs > 0 && this.mAnimatedProperty != null;
   }
}
