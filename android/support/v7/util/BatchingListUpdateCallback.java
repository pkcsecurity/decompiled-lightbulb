package android.support.v7.util;

import android.support.annotation.NonNull;
import android.support.v7.util.ListUpdateCallback;

public class BatchingListUpdateCallback implements ListUpdateCallback {

   private static final int TYPE_ADD = 1;
   private static final int TYPE_CHANGE = 3;
   private static final int TYPE_NONE = 0;
   private static final int TYPE_REMOVE = 2;
   int mLastEventCount = -1;
   Object mLastEventPayload = null;
   int mLastEventPosition = -1;
   int mLastEventType = 0;
   final ListUpdateCallback mWrapped;


   public BatchingListUpdateCallback(@NonNull ListUpdateCallback var1) {
      this.mWrapped = var1;
   }

   public void dispatchLastEvent() {
      if(this.mLastEventType != 0) {
         switch(this.mLastEventType) {
         case 1:
            this.mWrapped.onInserted(this.mLastEventPosition, this.mLastEventCount);
            break;
         case 2:
            this.mWrapped.onRemoved(this.mLastEventPosition, this.mLastEventCount);
            break;
         case 3:
            this.mWrapped.onChanged(this.mLastEventPosition, this.mLastEventCount, this.mLastEventPayload);
         }

         this.mLastEventPayload = null;
         this.mLastEventType = 0;
      }
   }

   public void onChanged(int var1, int var2, Object var3) {
      if(this.mLastEventType == 3 && var1 <= this.mLastEventPosition + this.mLastEventCount) {
         int var4 = var1 + var2;
         if(var4 >= this.mLastEventPosition && this.mLastEventPayload == var3) {
            var2 = this.mLastEventPosition;
            int var5 = this.mLastEventCount;
            this.mLastEventPosition = Math.min(var1, this.mLastEventPosition);
            this.mLastEventCount = Math.max(var2 + var5, var4) - this.mLastEventPosition;
            return;
         }
      }

      this.dispatchLastEvent();
      this.mLastEventPosition = var1;
      this.mLastEventCount = var2;
      this.mLastEventPayload = var3;
      this.mLastEventType = 3;
   }

   public void onInserted(int var1, int var2) {
      if(this.mLastEventType == 1 && var1 >= this.mLastEventPosition && var1 <= this.mLastEventPosition + this.mLastEventCount) {
         this.mLastEventCount += var2;
         this.mLastEventPosition = Math.min(var1, this.mLastEventPosition);
      } else {
         this.dispatchLastEvent();
         this.mLastEventPosition = var1;
         this.mLastEventCount = var2;
         this.mLastEventType = 1;
      }
   }

   public void onMoved(int var1, int var2) {
      this.dispatchLastEvent();
      this.mWrapped.onMoved(var1, var2);
   }

   public void onRemoved(int var1, int var2) {
      if(this.mLastEventType == 2 && this.mLastEventPosition >= var1 && this.mLastEventPosition <= var1 + var2) {
         this.mLastEventCount += var2;
         this.mLastEventPosition = var1;
      } else {
         this.dispatchLastEvent();
         this.mLastEventPosition = var1;
         this.mLastEventCount = var2;
         this.mLastEventType = 2;
      }
   }
}
