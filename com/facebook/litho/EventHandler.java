package com.facebook.litho;

import com.facebook.litho.HasEventDispatcher;
import javax.annotation.Nullable;

public class EventHandler<E extends Object> {

   public final int id;
   public HasEventDispatcher mHasEventDispatcher;
   @Nullable
   public final Object[] params;


   protected EventHandler(HasEventDispatcher var1, int var2) {
      this(var1, var2, (Object[])null);
   }

   public EventHandler(HasEventDispatcher var1, int var2, @Nullable Object[] var3) {
      this.mHasEventDispatcher = var1;
      this.id = var2;
      this.params = var3;
   }

   public void dispatchEvent(E var1) {
      this.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(this, var1);
   }

   public boolean isEquivalentTo(@Nullable EventHandler var1) {
      if(this == var1) {
         return true;
      } else if(var1 == null) {
         return false;
      } else if(var1.getClass() != this.getClass()) {
         return false;
      } else if(this.id != var1.id) {
         return false;
      } else if(this.params == var1.params) {
         return true;
      } else if(this.params == null) {
         return false;
      } else if(var1.params == null) {
         return false;
      } else if(this.params.length != var1.params.length) {
         return false;
      } else {
         int var2 = 1;

         while(true) {
            if(var2 >= this.params.length) {
               return true;
            }

            Object var3 = this.params[var2];
            Object var4 = var1.params[var2];
            if(var3 == null) {
               if(var4 != null) {
                  break;
               }
            } else if(!var3.equals(var4)) {
               break;
            }

            ++var2;
         }

         return false;
      }
   }
}
