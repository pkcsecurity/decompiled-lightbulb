package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.uimanager.layoutanimation.AbstractLayoutAnimation;
import com.facebook.react.uimanager.layoutanimation.HandleLayout;
import com.facebook.react.uimanager.layoutanimation.LayoutAnimationListener;
import com.facebook.react.uimanager.layoutanimation.LayoutAnimationType;
import com.facebook.react.uimanager.layoutanimation.LayoutCreateAnimation;
import com.facebook.react.uimanager.layoutanimation.LayoutDeleteAnimation;
import com.facebook.react.uimanager.layoutanimation.LayoutUpdateAnimation;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class LayoutAnimationController {

   private static final boolean ENABLED = true;
   private final AbstractLayoutAnimation mLayoutCreateAnimation = new LayoutCreateAnimation();
   private final AbstractLayoutAnimation mLayoutDeleteAnimation = new LayoutDeleteAnimation();
   private final AbstractLayoutAnimation mLayoutUpdateAnimation = new LayoutUpdateAnimation();
   private boolean mShouldAnimateLayout;


   private void disableUserInteractions(View var1) {
      int var2 = 0;
      var1.setClickable(false);
      if(var1 instanceof ViewGroup) {
         for(ViewGroup var3 = (ViewGroup)var1; var2 < var3.getChildCount(); ++var2) {
            this.disableUserInteractions(var3.getChildAt(var2));
         }
      }

   }

   public void applyLayoutUpdate(View var1, int var2, int var3, int var4, int var5) {
      UiThreadUtil.assertOnUiThread();
      AbstractLayoutAnimation var6;
      if(var1.getWidth() != 0 && var1.getHeight() != 0) {
         var6 = this.mLayoutUpdateAnimation;
      } else {
         var6 = this.mLayoutCreateAnimation;
      }

      Animation var7 = var6.createAnimation(var1, var2, var3, var4, var5);
      if(var7 == null || !(var7 instanceof HandleLayout)) {
         var1.layout(var2, var3, var4 + var2, var5 + var3);
      }

      if(var7 != null) {
         var1.startAnimation(var7);
      }

   }

   public void deleteView(View var1, final LayoutAnimationListener var2) {
      UiThreadUtil.assertOnUiThread();
      Animation var3 = this.mLayoutDeleteAnimation.createAnimation(var1, var1.getLeft(), var1.getTop(), var1.getWidth(), var1.getHeight());
      if(var3 != null) {
         this.disableUserInteractions(var1);
         var3.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation var1) {
               var2.onAnimationEnd();
            }
            public void onAnimationRepeat(Animation var1) {}
            public void onAnimationStart(Animation var1) {}
         });
         var1.startAnimation(var3);
      } else {
         var2.onAnimationEnd();
      }
   }

   public void initializeFromConfig(@Nullable ReadableMap var1) {
      if(var1 == null) {
         this.reset();
      } else {
         int var2 = 0;
         this.mShouldAnimateLayout = false;
         if(var1.hasKey("duration")) {
            var2 = var1.getInt("duration");
         }

         if(var1.hasKey(LayoutAnimationType.CREATE.toString())) {
            this.mLayoutCreateAnimation.initializeFromConfig(var1.getMap(LayoutAnimationType.CREATE.toString()), var2);
            this.mShouldAnimateLayout = true;
         }

         if(var1.hasKey(LayoutAnimationType.UPDATE.toString())) {
            this.mLayoutUpdateAnimation.initializeFromConfig(var1.getMap(LayoutAnimationType.UPDATE.toString()), var2);
            this.mShouldAnimateLayout = true;
         }

         if(var1.hasKey(LayoutAnimationType.DELETE.toString())) {
            this.mLayoutDeleteAnimation.initializeFromConfig(var1.getMap(LayoutAnimationType.DELETE.toString()), var2);
            this.mShouldAnimateLayout = true;
         }

      }
   }

   public void reset() {
      this.mLayoutCreateAnimation.reset();
      this.mLayoutUpdateAnimation.reset();
      this.mLayoutDeleteAnimation.reset();
      this.mShouldAnimateLayout = false;
   }

   public boolean shouldAnimateLayout(View var1) {
      return this.mShouldAnimateLayout && var1.getParent() != null;
   }
}
