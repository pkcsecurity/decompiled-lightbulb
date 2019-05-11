package com.facebook.litho.widget;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.widget.ChangeSetCompleteCallback;
import com.facebook.litho.widget.RecyclerBinder;
import com.facebook.litho.widget.RecyclerBinderUpdateCallback;
import java.util.ArrayList;
import java.util.List;

public class RecyclerBinderOperationExecutor implements RecyclerBinderUpdateCallback.OperationExecutor {

   private final RecyclerBinder mRecyclerBinder;


   public RecyclerBinderOperationExecutor(RecyclerBinder var1) {
      this.mRecyclerBinder = var1;
   }

   public void executeOperations(ComponentContext var1, List<RecyclerBinderUpdateCallback.Operation> var2) {
      int var5 = var2.size();

      for(int var3 = 0; var3 < var5; ++var3) {
         RecyclerBinderUpdateCallback.Operation var8 = (RecyclerBinderUpdateCallback.Operation)var2.get(var3);
         List var9 = var8.getComponentContainers();
         ArrayList var7 = null;
         ArrayList var10 = var7;
         if(var9 != null) {
            var10 = var7;
            if(var9.size() > 1) {
               var7 = new ArrayList();
               int var6 = var9.size();
               int var4 = 0;

               while(true) {
                  var10 = var7;
                  if(var4 >= var6) {
                     break;
                  }

                  var7.add(((RecyclerBinderUpdateCallback.ComponentContainer)var9.get(var4)).getRenderInfo());
                  ++var4;
               }
            }
         }

         switch(var8.getType()) {
         case 0:
            if(var10 != null) {
               this.mRecyclerBinder.insertRangeAt(var8.getIndex(), var10);
            } else {
               this.mRecyclerBinder.insertItemAt(var8.getIndex(), ((RecyclerBinderUpdateCallback.ComponentContainer)var8.getComponentContainers().get(0)).getRenderInfo());
            }
            break;
         case 1:
            if(var10 != null) {
               this.mRecyclerBinder.updateRangeAt(var8.getIndex(), var10);
            } else {
               this.mRecyclerBinder.updateItemAt(var8.getIndex(), ((RecyclerBinderUpdateCallback.ComponentContainer)var8.getComponentContainers().get(0)).getRenderInfo());
            }
            break;
         case 2:
            this.mRecyclerBinder.removeRangeAt(var8.getIndex(), var8.getToIndex());
            break;
         case 3:
            this.mRecyclerBinder.moveItem(var8.getIndex(), var8.getToIndex());
         }
      }

      this.mRecyclerBinder.notifyChangeSetComplete(true, new ChangeSetCompleteCallback() {
         public void onDataBound() {}
         public void onDataRendered(boolean var1, long var2) {}
      });
   }
}
