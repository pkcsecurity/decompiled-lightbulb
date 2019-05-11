package com.facebook.litho.sections;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools;
import com.facebook.litho.sections.Section;
import java.util.ArrayList;
import java.util.List;

public class Children {

   private static final Pools.Pool<Children.Builder> sBuildersPool = new Pools.SynchronizedPool(2);
   private List<Section> mSections = new ArrayList();


   public static Children.Builder create() {
      Children.Builder var1 = (Children.Builder)sBuildersPool.acquire();
      Children.Builder var0 = var1;
      if(var1 == null) {
         var0 = new Children.Builder(null);
      }

      var0.init(new Children());
      return var0;
   }

   @VisibleForTesting(
      otherwise = 3
   )
   public List<Section> getChildren() {
      return this.mSections;
   }

   public static class Builder {

      private Children mChildren;


      private Builder() {}

      // $FF: synthetic method
      Builder(Object var1) {
         this();
      }

      private void init(Children var1) {
         this.mChildren = var1;
      }

      private void verifyValidState() {
         if(this.mChildren == null) {
            throw new IllegalStateException(".build() call has been already made on this Builder.");
         }
      }

      public Children build() {
         this.verifyValidState();
         Children var1 = this.mChildren;
         this.mChildren = null;
         Children.sBuildersPool.release(this);
         return var1;
      }

      public Children.Builder child(@Nullable Section.Builder<?> var1) {
         this.verifyValidState();
         if(var1 != null) {
            this.mChildren.mSections.add(var1.build());
         }

         return this;
      }

      public Children.Builder child(@Nullable Section var1) {
         this.verifyValidState();
         if(var1 != null) {
            this.mChildren.mSections.add(var1.makeShallowCopy());
         }

         return this;
      }

      public Children.Builder child(@Nullable List<Section> var1) {
         this.verifyValidState();
         if(var1 != null) {
            if(var1.isEmpty()) {
               return this;
            } else {
               for(int var2 = 0; var2 < var1.size(); ++var2) {
                  Section var3 = (Section)var1.get(var2);
                  if(var3 != null) {
                     this.mChildren.mSections.add(var3.makeShallowCopy());
                  }
               }

               return this;
            }
         } else {
            return this;
         }
      }

      public Children.Builder children(@Nullable List<Section.Builder<?>> var1) {
         this.verifyValidState();
         if(var1 != null) {
            if(var1.isEmpty()) {
               return this;
            } else {
               for(int var2 = 0; var2 < var1.size(); ++var2) {
                  Section.Builder var3 = (Section.Builder)var1.get(var2);
                  if(var3 != null) {
                     this.mChildren.mSections.add(var3.build());
                  }
               }

               return this;
            }
         } else {
            return this;
         }
      }
   }
}
