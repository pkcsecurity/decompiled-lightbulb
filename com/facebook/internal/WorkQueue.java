package com.facebook.internal;

import com.facebook.FacebookSdk;
import java.util.concurrent.Executor;

public class WorkQueue {

   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   public static final int DEFAULT_MAX_CONCURRENT = 8;
   private final Executor executor;
   private final int maxConcurrent;
   private WorkQueue.WorkNode pendingJobs;
   private int runningCount;
   private WorkQueue.WorkNode runningJobs;
   private final Object workLock;


   public WorkQueue() {
      this(8);
   }

   public WorkQueue(int var1) {
      this(var1, FacebookSdk.getExecutor());
   }

   public WorkQueue(int var1, Executor var2) {
      this.workLock = new Object();
      this.runningJobs = null;
      this.runningCount = 0;
      this.maxConcurrent = var1;
      this.executor = var2;
   }

   // $FF: synthetic method
   static Object access$100(WorkQueue var0) {
      return var0.workLock;
   }

   // $FF: synthetic method
   static WorkQueue.WorkNode access$200(WorkQueue var0) {
      return var0.pendingJobs;
   }

   // $FF: synthetic method
   static WorkQueue.WorkNode access$202(WorkQueue var0, WorkQueue.WorkNode var1) {
      var0.pendingJobs = var1;
      return var1;
   }

   private void execute(final WorkQueue.WorkNode var1) {
      this.executor.execute(new Runnable() {
         public void run() {
            try {
               var1.getCallback().run();
            } finally {
               WorkQueue.this.finishItemAndStartNew(var1);
            }

         }
      });
   }

   private void finishItemAndStartNew(WorkQueue.WorkNode param1) {
      // $FF: Couldn't be decompiled
   }

   private void startItem() {
      this.finishItemAndStartNew((WorkQueue.WorkNode)null);
   }

   public WorkQueue.WorkItem addActiveWorkItem(Runnable var1) {
      return this.addActiveWorkItem(var1, true);
   }

   public WorkQueue.WorkItem addActiveWorkItem(Runnable param1, boolean param2) {
      // $FF: Couldn't be decompiled
   }

   public void validate() {
      // $FF: Couldn't be decompiled
   }

   class WorkNode implements WorkQueue.WorkItem {

      // $FF: synthetic field
      static final boolean $assertionsDisabled = false;
      private final Runnable callback;
      private boolean isRunning;
      private WorkQueue.WorkNode next;
      private WorkQueue.WorkNode prev;


      WorkNode(Runnable var2) {
         this.callback = var2;
      }

      WorkQueue.WorkNode addToList(WorkQueue.WorkNode var1, boolean var2) {
         if(var1 == null) {
            this.prev = this;
            this.next = this;
            var1 = this;
         } else {
            this.next = var1;
            this.prev = var1.prev;
            WorkQueue.WorkNode var3 = this.next;
            this.prev.next = this;
            var3.prev = this;
         }

         if(var2) {
            var1 = this;
         }

         return var1;
      }

      public boolean cancel() {
         // $FF: Couldn't be decompiled
      }

      Runnable getCallback() {
         return this.callback;
      }

      WorkQueue.WorkNode getNext() {
         return this.next;
      }

      public boolean isRunning() {
         return this.isRunning;
      }

      public void moveToFront() {
         // $FF: Couldn't be decompiled
      }

      WorkQueue.WorkNode removeFromList(WorkQueue.WorkNode var1) {
         WorkQueue.WorkNode var2 = var1;
         if(var1 == this) {
            if(this.next == this) {
               var2 = null;
            } else {
               var2 = this.next;
            }
         }

         this.next.prev = this.prev;
         this.prev.next = this.next;
         this.prev = null;
         this.next = null;
         return var2;
      }

      void setIsRunning(boolean var1) {
         this.isRunning = var1;
      }

      void verify(boolean var1) {}
   }

   public interface WorkItem {

      boolean cancel();

      boolean isRunning();

      void moveToFront();
   }
}
