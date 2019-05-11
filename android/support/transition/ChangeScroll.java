package android.support.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.Transition;
import android.support.transition.TransitionUtils;
import android.support.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class ChangeScroll extends Transition {

   private static final String[] PROPERTIES = new String[]{"android:changeScroll:x", "android:changeScroll:y"};
   private static final String PROPNAME_SCROLL_X = "android:changeScroll:x";
   private static final String PROPNAME_SCROLL_Y = "android:changeScroll:y";


   public ChangeScroll() {}

   public ChangeScroll(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   private void captureValues(TransitionValues var1) {
      var1.values.put("android:changeScroll:x", Integer.valueOf(var1.view.getScrollX()));
      var1.values.put("android:changeScroll:y", Integer.valueOf(var1.view.getScrollY()));
   }

   public void captureEndValues(@NonNull TransitionValues var1) {
      this.captureValues(var1);
   }

   public void captureStartValues(@NonNull TransitionValues var1) {
      this.captureValues(var1);
   }

   @Nullable
   public Animator createAnimator(@NonNull ViewGroup var1, @Nullable TransitionValues var2, @Nullable TransitionValues var3) {
      Object var8 = null;
      if(var2 != null) {
         if(var3 == null) {
            return null;
         } else {
            View var9 = var3.view;
            int var4 = ((Integer)var2.values.get("android:changeScroll:x")).intValue();
            int var5 = ((Integer)var3.values.get("android:changeScroll:x")).intValue();
            int var6 = ((Integer)var2.values.get("android:changeScroll:y")).intValue();
            int var7 = ((Integer)var3.values.get("android:changeScroll:y")).intValue();
            ObjectAnimator var10;
            if(var4 != var5) {
               var9.setScrollX(var4);
               var10 = ObjectAnimator.ofInt(var9, "scrollX", new int[]{var4, var5});
            } else {
               var10 = null;
            }

            ObjectAnimator var11 = (ObjectAnimator)var8;
            if(var6 != var7) {
               var9.setScrollY(var6);
               var11 = ObjectAnimator.ofInt(var9, "scrollY", new int[]{var6, var7});
            }

            return TransitionUtils.mergeAnimators(var10, var11);
         }
      } else {
         return null;
      }
   }

   @Nullable
   public String[] getTransitionProperties() {
      return PROPERTIES;
   }
}
