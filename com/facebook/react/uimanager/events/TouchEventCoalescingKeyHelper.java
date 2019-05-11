package com.facebook.react.uimanager.events;

import android.util.SparseIntArray;

public class TouchEventCoalescingKeyHelper {

   private final SparseIntArray mDownTimeToCoalescingKey = new SparseIntArray();


   public void addCoalescingKey(long var1) {
      this.mDownTimeToCoalescingKey.put((int)var1, 0);
   }

   public short getCoalescingKey(long var1) {
      int var3 = this.mDownTimeToCoalescingKey.get((int)var1, -1);
      if(var3 == -1) {
         throw new RuntimeException("Tried to get non-existent cookie");
      } else {
         return (short)(var3 & '\uffff');
      }
   }

   public boolean hasCoalescingKey(long var1) {
      return this.mDownTimeToCoalescingKey.get((int)var1, -1) != -1;
   }

   public void incrementCoalescingKey(long var1) {
      SparseIntArray var5 = this.mDownTimeToCoalescingKey;
      int var3 = (int)var1;
      int var4 = var5.get(var3, -1);
      if(var4 == -1) {
         throw new RuntimeException("Tried to increment non-existent cookie");
      } else {
         this.mDownTimeToCoalescingKey.put(var3, var4 + 1);
      }
   }

   public void removeCoalescingKey(long var1) {
      this.mDownTimeToCoalescingKey.delete((int)var1);
   }
}
