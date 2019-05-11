package android.support.design.transformation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.transformation.ExpandableBehavior;
import android.util.AttributeSet;
import android.view.View;

public abstract class ExpandableTransformationBehavior extends ExpandableBehavior {

   @Nullable
   private AnimatorSet currentAnimation;


   public ExpandableTransformationBehavior() {}

   public ExpandableTransformationBehavior(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   @NonNull
   protected abstract AnimatorSet onCreateExpandedStateChangeAnimation(View var1, View var2, boolean var3, boolean var4);

   @CallSuper
   protected boolean onExpandedStateChange(View var1, View var2, boolean var3, boolean var4) {
      boolean var5;
      if(this.currentAnimation != null) {
         var5 = true;
      } else {
         var5 = false;
      }

      if(var5) {
         this.currentAnimation.cancel();
      }

      this.currentAnimation = this.onCreateExpandedStateChangeAnimation(var1, var2, var3, var5);
      this.currentAnimation.addListener(new AnimatorListenerAdapter() {
         public void onAnimationEnd(Animator var1) {
            ExpandableTransformationBehavior.this.currentAnimation = null;
         }
      });
      this.currentAnimation.start();
      if(!var4) {
         this.currentAnimation.end();
      }

      return true;
   }
}
