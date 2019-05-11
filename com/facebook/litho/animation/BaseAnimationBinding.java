package com.facebook.litho.animation;

import com.facebook.litho.animation.AnimationBinding;
import com.facebook.litho.animation.AnimationBindingListener;
import java.util.concurrent.CopyOnWriteArrayList;

abstract class BaseAnimationBinding implements AnimationBinding {

   private CopyOnWriteArrayList<AnimationBindingListener> mListeners = new CopyOnWriteArrayList();


   public final void addListener(AnimationBindingListener var1) {
      this.mListeners.add(var1);
   }

   final void notifyCanceledBeforeStart() {
      for(int var1 = this.mListeners.size() - 1; var1 >= 0; --var1) {
         ((AnimationBindingListener)this.mListeners.get(var1)).onCanceledBeforeStart(this);
      }

   }

   final void notifyFinished() {
      for(int var1 = this.mListeners.size() - 1; var1 >= 0; --var1) {
         ((AnimationBindingListener)this.mListeners.get(var1)).onFinish(this);
      }

   }

   final void notifyScheduledToStartLater() {
      for(int var1 = this.mListeners.size() - 1; var1 >= 0; --var1) {
         ((AnimationBindingListener)this.mListeners.get(var1)).onScheduledToStartLater(this);
      }

   }

   final void notifyWillStart() {
      for(int var1 = this.mListeners.size() - 1; var1 >= 0; --var1) {
         ((AnimationBindingListener)this.mListeners.get(var1)).onWillStart(this);
      }

   }

   public final void removeListener(AnimationBindingListener var1) {
      this.mListeners.remove(var1);
   }

   final boolean shouldStart() {
      for(int var1 = this.mListeners.size() - 1; var1 >= 0; --var1) {
         if(!((AnimationBindingListener)this.mListeners.get(var1)).shouldStart(this)) {
            return false;
         }
      }

      return true;
   }
}
