package com.facebook.litho;

import com.facebook.litho.EventTriggerTarget;
import javax.annotation.Nullable;

public class EventTrigger<E extends Object> {

   public final int mId;
   public final String mKey;
   @Nullable
   public EventTriggerTarget mTriggerTarget;


   public EventTrigger(String var1, int var2, String var3) {
      this.mId = var2;
      StringBuilder var4 = new StringBuilder();
      var4.append(var1);
      var4.append(var2);
      var4.append(var3);
      this.mKey = var4.toString();
   }

   @Nullable
   public Object dispatchOnTrigger(E var1) {
      return this.dispatchOnTrigger(var1, new Object[0]);
   }

   @Nullable
   public Object dispatchOnTrigger(E var1, Object[] var2) {
      return this.mTriggerTarget == null?null:this.mTriggerTarget.acceptTriggerEvent(this, var1, var2);
   }
}
