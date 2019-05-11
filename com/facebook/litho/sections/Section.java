package com.facebook.litho.sections;

import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pair;
import com.facebook.litho.ComponentUtils;
import com.facebook.litho.Equivalence;
import com.facebook.litho.EventDispatcher;
import com.facebook.litho.EventHandler;
import com.facebook.litho.EventTriggersContainer;
import com.facebook.litho.HasEventDispatcher;
import com.facebook.litho.HasEventTrigger;
import com.facebook.litho.ResourceResolver;
import com.facebook.litho.StateContainer;
import com.facebook.litho.sections.Children;
import com.facebook.litho.sections.LoadingEvent;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.SectionLifecycle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

public abstract class Section extends SectionLifecycle implements Equivalence<Section>, HasEventDispatcher, HasEventTrigger, Cloneable {

   private static final AtomicInteger sIdGenerator = new AtomicInteger(0);
   EventHandler<LoadingEvent> loadingEventHandler;
   @Nullable
   private Map<String, Integer> mChildCounters;
   private List<Section> mChildren;
   private int mCount;
   private String mGlobalKey;
   private final int mId;
   private boolean mInvalidated;
   private String mKey;
   private Section mParent;
   private SectionContext mScopedContext;
   private final String mSimpleName;


   protected Section(String var1) {
      this.mId = sIdGenerator.getAndIncrement();
      this.mSimpleName = var1;
      this.mKey = this.getLogTag();
   }

   static Map<String, Pair<Section, Integer>> acquireChildrenMap(@Nullable Section var0) {
      HashMap var3 = new HashMap();
      if(var0 == null) {
         return var3;
      } else {
         List var4 = var0.getChildren();
         if(var4 == null) {
            StringBuilder var5 = new StringBuilder();
            var5.append("Children of current section ");
            var5.append(var0);
            var5.append(" is null!");
            throw new IllegalStateException(var5.toString());
         } else {
            int var1 = 0;

            for(int var2 = var4.size(); var1 < var2; ++var1) {
               var0 = (Section)var4.get(var1);
               var3.put(var0.getGlobalKey(), new Pair(var0, Integer.valueOf(var1)));
            }

            return var3;
         }
      }
   }

   private String generateUniqueGlobalKeyForChild(Section var1, String var2) {
      if(!this.mScopedContext.getKeyHandler().hasKey(var2)) {
         return var2;
      } else {
         String var4 = var1.getSimpleName();
         if(this.mChildCounters == null) {
            this.mChildCounters = new HashMap();
         }

         int var3;
         if(this.mChildCounters.containsKey(var4)) {
            var3 = ((Integer)this.mChildCounters.get(var4)).intValue();
         } else {
            var3 = 0;
         }

         this.mChildCounters.put(var4, Integer.valueOf(var3 + 1));
         StringBuilder var5 = new StringBuilder();
         var5.append(var2);
         var5.append(var3);
         return var5.toString();
      }
   }

   private static void invalidateInternal(Section var0) {
      var0.setInvalidated(true);
      if(var0.getParent() != null) {
         invalidateInternal(var0.getParent());
      }

   }

   static void releaseChildrenMap(Map<String, Pair<Section, Integer>> var0) {}

   void generateKeyAndSet(SectionContext var1, String var2) {
      Section var3 = var1.getSectionScope();
      if(var3 != null) {
         var2 = var3.generateUniqueGlobalKeyForChild(this, var2);
      }

      this.setGlobalKey(var2);
      var1.getKeyHandler().registerKey(var2);
   }

   @VisibleForTesting(
      otherwise = 3
   )
   public List<Section> getChildren() {
      return this.mChildren;
   }

   @VisibleForTesting(
      otherwise = 3
   )
   int getCount() {
      return this.mCount;
   }

   public EventDispatcher getEventDispatcher() {
      return this;
   }

   @VisibleForTesting(
      otherwise = 3
   )
   public String getGlobalKey() {
      return this.mGlobalKey;
   }

   int getId() {
      return this.mId;
   }

   String getKey() {
      return this.mKey;
   }

   Section getParent() {
      return this.mParent;
   }

   public SectionContext getScopedContext() {
      return this.mScopedContext;
   }

   public final String getSimpleName() {
      return this.mSimpleName;
   }

   @Nullable
   public StateContainer getStateContainer() {
      return null;
   }

   void invalidate() {
      invalidateInternal(this);
   }

   public boolean isEquivalentTo(Section var1) {
      return this == var1?true:(var1 != null && this.getClass() == var1.getClass()?ComponentUtils.hasEquivalentFields(this, var1):false);
   }

   boolean isInvalidated() {
      return this.mInvalidated;
   }

   public Section makeShallowCopy() {
      return this.makeShallowCopy(false);
   }

   public Section makeShallowCopy(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   public void recordEventTrigger(EventTriggersContainer var1) {}

   void release() {}

   @VisibleForTesting(
      otherwise = 3
   )
   public void setChildren(Children var1) {
      Object var2;
      if(var1 == null) {
         var2 = new ArrayList();
      } else {
         var2 = var1.getChildren();
      }

      this.mChildren = (List)var2;
   }

   @VisibleForTesting(
      otherwise = 3
   )
   public void setCount(int var1) {
      this.mCount = var1;
   }

   @VisibleForTesting(
      otherwise = 3
   )
   public void setGlobalKey(String var1) {
      this.mGlobalKey = var1;
   }

   void setInvalidated(boolean var1) {
      this.mInvalidated = var1;
   }

   @VisibleForTesting(
      otherwise = 3
   )
   public void setKey(String var1) {
      this.mKey = var1;
   }

   void setParent(Section var1) {
      this.mParent = var1;
   }

   @VisibleForTesting
   public void setScopedContext(SectionContext var1) {
      this.mScopedContext = var1;
   }

   public abstract static class Builder<T extends Section.Builder<T>> {

      protected ResourceResolver mResourceResolver;
      private Section mSection;


      protected static void checkArgs(int var0, BitSet var1, String[] var2) {
         if(var1 != null) {
            int var3 = 0;
            if(var1.nextClearBit(0) < var0) {
               ArrayList var4;
               for(var4 = new ArrayList(); var3 < var0; ++var3) {
                  if(!var1.get(var3)) {
                     var4.add(var2[var3]);
                  }
               }

               StringBuilder var5 = new StringBuilder();
               var5.append("The following props are not marked as optional and were not supplied: ");
               var5.append(Arrays.toString(var4.toArray()));
               throw new IllegalStateException(var5.toString());
            }
         }

      }

      public abstract Section build();

      public abstract T getThis();

      protected void init(SectionContext var1, Section var2) {
         this.mSection = var2;
         this.mResourceResolver = new ResourceResolver(var1);
      }

      public T key(String var1) {
         this.mSection.setKey(var1);
         return this.getThis();
      }

      protected T loadingEventHandler(EventHandler<LoadingEvent> var1) {
         this.mSection.loadingEventHandler = var1;
         return this.getThis();
      }

      protected void release() {
         this.mSection = null;
         this.mResourceResolver.release();
         this.mResourceResolver = null;
      }
   }
}
