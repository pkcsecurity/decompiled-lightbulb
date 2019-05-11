package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;

public class ThreadHandoffProducerQueue {

   private final Executor mExecutor;
   private boolean mQueueing = false;
   private final Deque<Runnable> mRunnableList;


   public ThreadHandoffProducerQueue(Executor var1) {
      this.mExecutor = (Executor)Preconditions.checkNotNull(var1);
      this.mRunnableList = new ArrayDeque();
   }

   private void execInQueue() {
      while(!this.mRunnableList.isEmpty()) {
         this.mExecutor.execute((Runnable)this.mRunnableList.pop());
      }

      this.mRunnableList.clear();
   }

   public void addToQueueOrExecute(Runnable var1) {
      synchronized(this){}

      try {
         if(this.mQueueing) {
            this.mRunnableList.add(var1);
         } else {
            this.mExecutor.execute(var1);
         }
      } finally {
         ;
      }

   }

   public boolean isQueueing() {
      synchronized(this){}

      boolean var1;
      try {
         var1 = this.mQueueing;
      } finally {
         ;
      }

      return var1;
   }

   public void remove(Runnable var1) {
      synchronized(this){}

      try {
         this.mRunnableList.remove(var1);
      } finally {
         ;
      }

   }

   public void startQueueing() {
      synchronized(this){}

      try {
         this.mQueueing = true;
      } finally {
         ;
      }

   }

   public void stopQueuing() {
      synchronized(this){}

      try {
         this.mQueueing = false;
         this.execInQueue();
      } finally {
         ;
      }

   }
}
