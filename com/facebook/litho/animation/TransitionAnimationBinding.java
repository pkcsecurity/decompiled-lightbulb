package com.facebook.litho.animation;

import android.support.annotation.VisibleForTesting;
import com.facebook.litho.animation.BaseAnimationBinding;
import com.facebook.litho.animation.Resolver;
import com.facebook.litho.dataflow.BindingListener;
import com.facebook.litho.dataflow.GraphBinding;
import com.facebook.litho.dataflow.ValueNode;

public abstract class TransitionAnimationBinding extends BaseAnimationBinding {

   private final GraphBinding mGraphBinding;


   public TransitionAnimationBinding() {
      this(GraphBinding.create());
   }

   @VisibleForTesting
   TransitionAnimationBinding(GraphBinding var1) {
      this.mGraphBinding = var1;
      this.mGraphBinding.setListener(new BindingListener() {
         public void onAllNodesFinished(GraphBinding var1) {
            TransitionAnimationBinding.this.onAllNodesFinished();
         }
      });
   }

   private void onAllNodesFinished() {
      this.notifyFinished();
      this.stop();
   }

   public void addBinding(ValueNode var1, ValueNode var2) {
      this.mGraphBinding.addBinding(var1, var2);
   }

   public void addBinding(ValueNode var1, ValueNode var2, String var3) {
      this.mGraphBinding.addBinding(var1, var2, var3);
   }

   public boolean isActive() {
      return this.mGraphBinding.isActive();
   }

   public void prepareToStartLater() {
      this.notifyScheduledToStartLater();
   }

   protected abstract void setupBinding(Resolver var1);

   public void start(Resolver var1) {
      if(!this.shouldStart()) {
         this.notifyCanceledBeforeStart();
      } else {
         this.notifyWillStart();
         this.setupBinding(var1);
         this.mGraphBinding.activate();
      }
   }

   public void stop() {
      if(this.isActive()) {
         this.mGraphBinding.deactivate();
      }
   }
}
