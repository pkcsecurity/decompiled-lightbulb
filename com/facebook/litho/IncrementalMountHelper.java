package com.facebook.litho;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.ViewParent;
import com.facebook.litho.ComponentTree;
import com.facebook.litho.LithoView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

class IncrementalMountHelper {

   private final ComponentTree mComponentTree;
   private List<IncrementalMountHelper.ViewPagerListener> mViewPagerListeners;


   IncrementalMountHelper(ComponentTree var1) {
      this.mComponentTree = var1;
      this.mViewPagerListeners = new ArrayList(2);
   }

   void onAttach(LithoView var1) {
      if(this.mComponentTree.isIncrementalMountEnabled()) {
         for(ViewParent var6 = var1.getParent(); var6 != null; var6 = var6.getParent()) {
            if(var6 instanceof ViewPager) {
               final ViewPager var2 = (ViewPager)var6;
               final IncrementalMountHelper.ViewPagerListener var3 = new IncrementalMountHelper.ViewPagerListener(this.mComponentTree, var2, null);

               try {
                  var2.addOnPageChangeListener(var3);
               } catch (ConcurrentModificationException var5) {
                  ViewCompat.postOnAnimation(var2, new Runnable() {
                     public void run() {
                        var2.addOnPageChangeListener(var3);
                     }
                  });
               }

               this.mViewPagerListeners.add(var3);
            }
         }

      }
   }

   void onDetach(LithoView var1) {
      int var3 = this.mViewPagerListeners.size();

      for(int var2 = 0; var2 < var3; ++var2) {
         ((IncrementalMountHelper.ViewPagerListener)this.mViewPagerListeners.get(var2)).release();
      }

      this.mViewPagerListeners.clear();
   }

   static class ViewPagerListener extends ViewPager.SimpleOnPageChangeListener {

      private final WeakReference<ComponentTree> mComponentTree;
      private final WeakReference<ViewPager> mViewPager;


      private ViewPagerListener(ComponentTree var1, ViewPager var2) {
         this.mComponentTree = new WeakReference(var1);
         this.mViewPager = new WeakReference(var2);
      }

      // $FF: synthetic method
      ViewPagerListener(ComponentTree var1, ViewPager var2, Object var3) {
         this(var1, var2);
      }

      private void release() {
         final ViewPager var1 = (ViewPager)this.mViewPager.get();
         if(var1 != null) {
            ViewCompat.postOnAnimation(var1, new Runnable() {
               public void run() {
                  var1.removeOnPageChangeListener(ViewPagerListener.this);
               }
            });
         }

      }

      public void onPageScrolled(int var1, float var2, int var3) {
         ComponentTree var4 = (ComponentTree)this.mComponentTree.get();
         if(var4 != null) {
            var4.incrementalMountComponent();
         }

      }
   }
}
