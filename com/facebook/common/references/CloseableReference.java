package com.facebook.common.references;

import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.common.references.SharedReference;
import java.io.Closeable;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public abstract class CloseableReference<T extends Object> implements Closeable, Cloneable {

   private static final ResourceReleaser<Closeable> DEFAULT_CLOSEABLE_RELEASER = new ResourceReleaser() {
      public void release(Closeable var1) {
         try {
            Closeables.close(var1, true);
         } catch (IOException var2) {
            ;
         }
      }
   };
   private static Class<CloseableReference> TAG = CloseableReference.class;
   private static volatile boolean sUseFinalizers;


   // $FF: synthetic method
   static Class access$300() {
      return TAG;
   }

   @Nullable
   public static <T extends Object> CloseableReference<T> cloneOrNull(@Nullable CloseableReference<T> var0) {
      return var0 != null?var0.cloneOrNull():null;
   }

   public static <T extends Object> List<CloseableReference<T>> cloneOrNull(Collection<CloseableReference<T>> var0) {
      if(var0 == null) {
         return null;
      } else {
         ArrayList var1 = new ArrayList(var0.size());
         Iterator var2 = var0.iterator();

         while(var2.hasNext()) {
            var1.add(cloneOrNull((CloseableReference)var2.next()));
         }

         return var1;
      }
   }

   public static void closeSafely(@Nullable CloseableReference<?> var0) {
      if(var0 != null) {
         var0.close();
      }

   }

   public static void closeSafely(@Nullable Iterable<? extends CloseableReference<?>> var0) {
      if(var0 != null) {
         Iterator var1 = var0.iterator();

         while(var1.hasNext()) {
            closeSafely((CloseableReference)var1.next());
         }
      }

   }

   public static boolean isValid(@Nullable CloseableReference<?> var0) {
      return var0 != null && var0.isValid();
   }

   private static <T extends Object> CloseableReference<T> makeCloseableReference(@Nullable T var0, ResourceReleaser<T> var1) {
      return (CloseableReference)(sUseFinalizers?new CloseableReference.CloseableReferenceWithFinalizer(var0, var1, null):new CloseableReference.CloseableReferenceWithoutFinalizer(var0, var1, null));
   }

   @Nullable
   public static <T extends Object & Closeable> CloseableReference<T> of(@Nullable T var0) {
      return var0 == null?null:makeCloseableReference(var0, DEFAULT_CLOSEABLE_RELEASER);
   }

   @Nullable
   public static <T extends Object> CloseableReference<T> of(@Nullable T var0, ResourceReleaser<T> var1) {
      return var0 == null?null:makeCloseableReference(var0, var1);
   }

   public static void setUseFinalizers(boolean var0) {
      sUseFinalizers = var0;
   }

   public abstract CloseableReference<T> clone();

   public abstract CloseableReference<T> cloneOrNull();

   public abstract void close();

   public abstract T get();

   @VisibleForTesting
   public abstract SharedReference<T> getUnderlyingReferenceTestOnly();

   public abstract int getValueHash();

   public abstract boolean isValid();

   static class CloseableReferenceWithFinalizer<T extends Object> extends CloseableReference<T> {

      @GuardedBy
      private boolean mIsClosed;
      private final SharedReference<T> mSharedReference;


      private CloseableReferenceWithFinalizer(SharedReference<T> var1) {
         this.mIsClosed = false;
         this.mSharedReference = (SharedReference)Preconditions.checkNotNull(var1);
         var1.addReference();
      }

      private CloseableReferenceWithFinalizer(T var1, ResourceReleaser<T> var2) {
         this.mIsClosed = false;
         this.mSharedReference = new SharedReference(var1, var2);
      }

      // $FF: synthetic method
      CloseableReferenceWithFinalizer(Object var1, ResourceReleaser var2, Object var3) {
         this(var1, var2);
      }

      public CloseableReference<T> clone() {
         synchronized(this){}

         CloseableReference.CloseableReferenceWithFinalizer var1;
         try {
            Preconditions.checkState(this.isValid());
            var1 = new CloseableReference.CloseableReferenceWithFinalizer(this.mSharedReference);
         } finally {
            ;
         }

         return var1;
      }

      public CloseableReference<T> cloneOrNull() {
         synchronized(this){}

         try {
            if(this.isValid()) {
               CloseableReference var1 = this.clone();
               return var1;
            }
         } finally {
            ;
         }

         return null;
      }

      public void close() {
         // $FF: Couldn't be decompiled
      }

      protected void finalize() throws Throwable {
         // $FF: Couldn't be decompiled
      }

      public T get() {
         synchronized(this){}

         Object var1;
         try {
            Preconditions.checkState(this.mIsClosed ^ true);
            var1 = this.mSharedReference.get();
         } finally {
            ;
         }

         return var1;
      }

      public SharedReference<T> getUnderlyingReferenceTestOnly() {
         synchronized(this){}

         SharedReference var1;
         try {
            var1 = this.mSharedReference;
         } finally {
            ;
         }

         return var1;
      }

      public int getValueHash() {
         return this.isValid()?System.identityHashCode(this.mSharedReference.get()):0;
      }

      public boolean isValid() {
         synchronized(this){}

         boolean var1;
         try {
            var1 = this.mIsClosed;
         } finally {
            ;
         }

         return var1 ^ true;
      }
   }

   static class CloseableReferenceWithoutFinalizer<T extends Object> extends CloseableReference<T> {

      private static final ReferenceQueue<CloseableReference> REF_QUEUE = new ReferenceQueue();
      private final CloseableReference.Destructor mDestructor;
      private final SharedReference<T> mSharedReference;


      static {
         (new Thread(new Runnable() {
            public void run() {
               while(true) {
                  try {
                     while(true) {
                        ((CloseableReference.Destructor)CloseableReference.CloseableReferenceWithoutFinalizer.REF_QUEUE.remove()).destroy(false);
                     }
                  } catch (InterruptedException var2) {
                     ;
                  }
               }
            }
         }, "CloseableReferenceDestructorThread")).start();
      }

      private CloseableReferenceWithoutFinalizer(SharedReference<T> var1) {
         this.mSharedReference = (SharedReference)Preconditions.checkNotNull(var1);
         var1.addReference();
         this.mDestructor = new CloseableReference.Destructor(this, REF_QUEUE);
      }

      private CloseableReferenceWithoutFinalizer(T var1, ResourceReleaser<T> var2) {
         this.mSharedReference = new SharedReference(var1, var2);
         this.mDestructor = new CloseableReference.Destructor(this, REF_QUEUE);
      }

      // $FF: synthetic method
      CloseableReferenceWithoutFinalizer(Object var1, ResourceReleaser var2, Object var3) {
         this(var1, var2);
      }

      // $FF: synthetic method
      static SharedReference access$200(CloseableReference.CloseableReferenceWithoutFinalizer var0) {
         return var0.mSharedReference;
      }

      public CloseableReference<T> clone() {
         // $FF: Couldn't be decompiled
      }

      public CloseableReference<T> cloneOrNull() {
         // $FF: Couldn't be decompiled
      }

      public void close() {
         this.mDestructor.destroy(true);
      }

      public T get() {
         // $FF: Couldn't be decompiled
      }

      public SharedReference<T> getUnderlyingReferenceTestOnly() {
         return this.mSharedReference;
      }

      public int getValueHash() {
         // $FF: Couldn't be decompiled
      }

      public boolean isValid() {
         return this.mDestructor.isDestroyed() ^ true;
      }
   }

   static class Destructor extends PhantomReference<CloseableReference> {

      @GuardedBy
      private static CloseableReference.Destructor sHead;
      @GuardedBy
      private boolean destroyed;
      private final SharedReference mSharedReference;
      @GuardedBy
      private CloseableReference.Destructor next;
      @GuardedBy
      private CloseableReference.Destructor previous;


      public Destructor(CloseableReference.CloseableReferenceWithoutFinalizer param1, ReferenceQueue<? super CloseableReference> param2) {
         // $FF: Couldn't be decompiled
      }

      public void destroy(boolean param1) {
         // $FF: Couldn't be decompiled
      }

      public boolean isDestroyed() {
         synchronized(this){}

         boolean var1;
         try {
            var1 = this.destroyed;
         } finally {
            ;
         }

         return var1;
      }
   }
}
