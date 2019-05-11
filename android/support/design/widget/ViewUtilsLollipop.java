package android.support.design.widget;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.RequiresApi;
import android.support.design.R;
import android.support.design.internal.ThemeEnforcement;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

@RequiresApi(21)
class ViewUtilsLollipop {

   private static final int[] STATE_LIST_ANIM_ATTRS = new int[]{16843848};


   static void setBoundsViewOutlineProvider(View var0) {
      var0.setOutlineProvider(ViewOutlineProvider.BOUNDS);
   }

   static void setDefaultAppBarLayoutStateListAnimator(View var0, float var1) {
      int var2 = var0.getResources().getInteger(R.integer.app_bar_elevation_anim_duration);
      android.animation.StateListAnimator var7 = new android.animation.StateListAnimator();
      int var3 = R.attr.state_liftable;
      int var4 = -R.attr.state_lifted;
      ObjectAnimator var8 = ObjectAnimator.ofFloat(var0, "elevation", new float[]{0.0F});
      long var5 = (long)var2;
      var8 = var8.setDuration(var5);
      var7.addState(new int[]{16842766, var3, var4}, var8);
      var8 = ObjectAnimator.ofFloat(var0, "elevation", new float[]{var1}).setDuration(var5);
      var7.addState(new int[]{16842766}, var8);
      var8 = ObjectAnimator.ofFloat(var0, "elevation", new float[]{0.0F}).setDuration(0L);
      var7.addState(new int[0], var8);
      var0.setStateListAnimator(var7);
   }

   static void setStateListAnimatorFromAttrs(View var0, AttributeSet var1, int var2, int var3) {
      Context var4 = var0.getContext();
      TypedArray var7 = ThemeEnforcement.obtainStyledAttributes(var4, var1, STATE_LIST_ANIM_ATTRS, var2, var3, new int[0]);

      try {
         if(var7.hasValue(0)) {
            var0.setStateListAnimator(AnimatorInflater.loadStateListAnimator(var4, var7.getResourceId(0, 0)));
         }
      } finally {
         var7.recycle();
      }

   }
}
