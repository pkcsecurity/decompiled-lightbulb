package android.support.design.transformation;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.design.expandable.ExpandableWidget;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import java.util.List;

public abstract class ExpandableBehavior extends CoordinatorLayout.Behavior<View> {

   private static final int STATE_COLLAPSED = 2;
   private static final int STATE_EXPANDED = 1;
   private static final int STATE_UNINITIALIZED = 0;
   private int currentState = 0;


   public ExpandableBehavior() {}

   public ExpandableBehavior(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   private boolean didStateChange(boolean var1) {
      boolean var3 = false;
      boolean var2 = false;
      if(!var1) {
         var1 = var3;
         if(this.currentState == 1) {
            var1 = true;
         }

         return var1;
      } else {
         if(this.currentState != 0) {
            var1 = var2;
            if(this.currentState != 2) {
               return var1;
            }
         }

         var1 = true;
         return var1;
      }
   }

   public static <T extends ExpandableBehavior> T from(View var0, Class<T> var1) {
      LayoutParams var2 = var0.getLayoutParams();
      if(!(var2 instanceof CoordinatorLayout.LayoutParams)) {
         throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
      } else {
         CoordinatorLayout.Behavior var3 = ((CoordinatorLayout.LayoutParams)var2).getBehavior();
         if(!(var3 instanceof ExpandableBehavior)) {
            throw new IllegalArgumentException("The view is not associated with ExpandableBehavior");
         } else {
            return (ExpandableBehavior)var1.cast(var3);
         }
      }
   }

   @Nullable
   protected ExpandableWidget findExpandableWidget(CoordinatorLayout var1, View var2) {
      List var5 = var1.getDependencies(var2);
      int var4 = var5.size();

      for(int var3 = 0; var3 < var4; ++var3) {
         View var6 = (View)var5.get(var3);
         if(this.layoutDependsOn(var1, var2, var6)) {
            return (ExpandableWidget)var6;
         }
      }

      return null;
   }

   public abstract boolean layoutDependsOn(CoordinatorLayout var1, View var2, View var3);

   @CallSuper
   public boolean onDependentViewChanged(CoordinatorLayout var1, View var2, View var3) {
      ExpandableWidget var5 = (ExpandableWidget)var3;
      if(this.didStateChange(var5.isExpanded())) {
         byte var4;
         if(var5.isExpanded()) {
            var4 = 1;
         } else {
            var4 = 2;
         }

         this.currentState = var4;
         return this.onExpandedStateChange((View)var5, var2, var5.isExpanded(), true);
      } else {
         return false;
      }
   }

   protected abstract boolean onExpandedStateChange(View var1, View var2, boolean var3, boolean var4);

   @CallSuper
   public boolean onLayoutChild(CoordinatorLayout var1, final View var2, final int var3) {
      if(!ViewCompat.isLaidOut(var2)) {
         final ExpandableWidget var4 = this.findExpandableWidget(var1, var2);
         if(var4 != null && this.didStateChange(var4.isExpanded())) {
            byte var5;
            if(var4.isExpanded()) {
               var5 = 1;
            } else {
               var5 = 2;
            }

            this.currentState = var5;
            var3 = this.currentState;
            var2.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
               public boolean onPreDraw() {
                  var2.getViewTreeObserver().removeOnPreDrawListener(this);
                  if(ExpandableBehavior.this.currentState == var3) {
                     ExpandableBehavior.this.onExpandedStateChange((View)var4, var2, var4.isExpanded(), false);
                  }

                  return false;
               }
            });
         }
      }

      return false;
   }
}
