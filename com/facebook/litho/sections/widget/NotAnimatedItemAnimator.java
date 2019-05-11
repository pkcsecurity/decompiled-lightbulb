package com.facebook.litho.sections.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

public class NotAnimatedItemAnimator extends SimpleItemAnimator {

   public NotAnimatedItemAnimator() {
      this.setSupportsChangeAnimations(false);
   }

   public boolean animateAdd(RecyclerView.ViewHolder var1) {
      this.dispatchAddStarting(var1);
      this.dispatchAddFinished(var1);
      return true;
   }

   public boolean animateChange(RecyclerView.ViewHolder var1, RecyclerView.ViewHolder var2, int var3, int var4, int var5, int var6) {
      if(var1 != var2) {
         this.dispatchChangeStarting(var1, true);
         this.dispatchChangeFinished(var1, true);
      }

      this.dispatchChangeStarting(var2, false);
      this.dispatchChangeFinished(var2, false);
      return true;
   }

   public boolean animateMove(RecyclerView.ViewHolder var1, int var2, int var3, int var4, int var5) {
      this.dispatchMoveStarting(var1);
      this.dispatchMoveFinished(var1);
      return true;
   }

   public boolean animateRemove(RecyclerView.ViewHolder var1) {
      this.dispatchRemoveStarting(var1);
      this.dispatchRemoveFinished(var1);
      return true;
   }

   public void endAnimation(RecyclerView.ViewHolder var1) {}

   public void endAnimations() {}

   public boolean isRunning() {
      return false;
   }

   public void runPendingAnimations() {}
}
