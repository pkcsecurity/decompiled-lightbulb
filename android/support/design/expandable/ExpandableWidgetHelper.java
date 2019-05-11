package android.support.design.expandable;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.expandable.ExpandableWidget;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewParent;

public final class ExpandableWidgetHelper {

   private boolean expanded = false;
   @IdRes
   private int expandedComponentIdHint = 0;
   private final View widget;


   public ExpandableWidgetHelper(ExpandableWidget var1) {
      this.widget = (View)var1;
   }

   private void dispatchExpandedStateChanged() {
      ViewParent var1 = this.widget.getParent();
      if(var1 instanceof CoordinatorLayout) {
         ((CoordinatorLayout)var1).dispatchDependentViewsChanged(this.widget);
      }

   }

   @IdRes
   public int getExpandedComponentIdHint() {
      return this.expandedComponentIdHint;
   }

   public boolean isExpanded() {
      return this.expanded;
   }

   public void onRestoreInstanceState(Bundle var1) {
      this.expanded = var1.getBoolean("expanded", false);
      this.expandedComponentIdHint = var1.getInt("expandedComponentIdHint", 0);
      if(this.expanded) {
         this.dispatchExpandedStateChanged();
      }

   }

   public Bundle onSaveInstanceState() {
      Bundle var1 = new Bundle();
      var1.putBoolean("expanded", this.expanded);
      var1.putInt("expandedComponentIdHint", this.expandedComponentIdHint);
      return var1;
   }

   public boolean setExpanded(boolean var1) {
      if(this.expanded != var1) {
         this.expanded = var1;
         this.dispatchExpandedStateChanged();
         return true;
      } else {
         return false;
      }
   }

   public void setExpandedComponentIdHint(@IdRes int var1) {
      this.expandedComponentIdHint = var1;
   }
}
