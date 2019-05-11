package com.facebook.litho;

import com.facebook.litho.EventHandler;
import com.facebook.litho.HasEventDispatcher;
import javax.annotation.Nullable;

public class DelegatingEventHandler<E extends Object> extends EventHandler<E> {

   private final EventHandler<E> mEventHandler1;
   private final EventHandler<E> mEventHandler2;


   protected DelegatingEventHandler(EventHandler<E> var1, EventHandler<E> var2) {
      super((HasEventDispatcher)null, -1);
      this.mEventHandler1 = var1;
      this.mEventHandler2 = var2;
   }

   public void dispatchEvent(E var1) {
      this.mEventHandler1.dispatchEvent(var1);
      this.mEventHandler2.dispatchEvent(var1);
   }

   public boolean isEquivalentTo(@Nullable EventHandler var1) {
      if(this == var1) {
         return true;
      } else if(var1 == null) {
         return false;
      } else if(var1.getClass() != this.getClass()) {
         return false;
      } else {
         DelegatingEventHandler var2 = (DelegatingEventHandler)var1;
         return this.mEventHandler1.isEquivalentTo(var2.mEventHandler1) && this.mEventHandler2.isEquivalentTo(var2.mEventHandler2);
      }
   }
}
