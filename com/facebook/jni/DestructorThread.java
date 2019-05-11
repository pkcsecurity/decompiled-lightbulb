package com.facebook.jni;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.atomic.AtomicReference;

public class DestructorThread {

   private static DestructorThread.DestructorList sDestructorList = new DestructorThread.DestructorList();
   private static DestructorThread.DestructorStack sDestructorStack = new DestructorThread.DestructorStack(null);
   private static ReferenceQueue sReferenceQueue = new ReferenceQueue();
   private static Thread sThread = new Thread("HybridData DestructorThread") {
      public void run() {
         while(true) {
            try {
               while(true) {
                  DestructorThread.Destructor var1 = (DestructorThread.Destructor)DestructorThread.sReferenceQueue.remove();
                  var1.destruct();
                  if(var1.previous == null) {
                     DestructorThread.sDestructorStack.transferAllToList();
                  }

                  DestructorThread.DestructorList.drop(var1);
               }
            } catch (InterruptedException var2) {
               ;
            }
         }
      }
   };


   static {
      sThread.start();
   }


   public abstract static class Destructor extends PhantomReference<Object> {

      private DestructorThread.Destructor next;
      private DestructorThread.Destructor previous;


      private Destructor() {
         super((Object)null, DestructorThread.sReferenceQueue);
      }

      // $FF: synthetic method
      Destructor(Object var1) {
         this();
      }

      Destructor(Object var1) {
         super(var1, DestructorThread.sReferenceQueue);
         DestructorThread.sDestructorStack.push(this);
      }

      abstract void destruct();
   }

   static class DestructorStack {

      private AtomicReference<DestructorThread.Destructor> mHead;


      private DestructorStack() {
         this.mHead = new AtomicReference();
      }

      // $FF: synthetic method
      DestructorStack(Object var1) {
         this();
      }

      public void push(DestructorThread.Destructor var1) {
         DestructorThread.Destructor var2;
         do {
            var2 = (DestructorThread.Destructor)this.mHead.get();
            var1.next = var2;
         } while(!this.mHead.compareAndSet(var2, var1));

      }

      public void transferAllToList() {
         DestructorThread.Destructor var2;
         for(DestructorThread.Destructor var1 = (DestructorThread.Destructor)this.mHead.getAndSet((Object)null); var1 != null; var1 = var2) {
            var2 = var1.next;
            DestructorThread.sDestructorList.enqueue(var1);
         }

      }
   }

   static class DestructorList {

      private DestructorThread.Destructor mHead = new DestructorThread.Terminus(null);


      public DestructorList() {
         this.mHead.next = new DestructorThread.Terminus(null);
         this.mHead.next.previous = this.mHead;
      }

      private static void drop(DestructorThread.Destructor var0) {
         var0.next.previous = var0.previous;
         var0.previous.next = var0.next;
      }

      public void enqueue(DestructorThread.Destructor var1) {
         var1.next = this.mHead.next;
         this.mHead.next = var1;
         var1.next.previous = var1;
         var1.previous = this.mHead;
      }
   }

   static class Terminus extends DestructorThread.Destructor {

      private Terminus() {
         super((<undefinedtype>)null);
      }

      // $FF: synthetic method
      Terminus(Object var1) {
         this();
      }

      void destruct() {
         throw new IllegalStateException("Cannot destroy Terminus Destructor.");
      }
   }
}
