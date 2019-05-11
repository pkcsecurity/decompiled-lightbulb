package com.facebook.litho.sections.widget;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import com.facebook.litho.ComponentTree;
import com.facebook.litho.EventHandler;
import com.facebook.litho.Size;
import com.facebook.litho.sections.SectionTree;
import com.facebook.litho.sections.config.SectionsConfiguration;
import com.facebook.litho.widget.Binder;
import com.facebook.litho.widget.ChangeSetCompleteCallback;
import com.facebook.litho.widget.ReMeasureEvent;
import com.facebook.litho.widget.RecyclerBinder;
import com.facebook.litho.widget.RenderInfo;
import com.facebook.litho.widget.SmoothScrollAlignmentType;
import com.facebook.litho.widget.ViewportInfo;
import java.util.List;

public class SectionBinderTarget implements SectionTree.Target, Binder<RecyclerView> {

   private final RecyclerBinder mRecyclerBinder;
   private final boolean mUseBackgroundChangeSets;


   public SectionBinderTarget(RecyclerBinder var1) {
      this(var1, SectionsConfiguration.useBackgroundChangeSets);
   }

   SectionBinderTarget(RecyclerBinder var1, boolean var2) {
      this.mRecyclerBinder = var1;
      this.mUseBackgroundChangeSets = var2;
   }

   public static SectionBinderTarget createWithBackgroundChangeSets(RecyclerBinder var0) {
      return new SectionBinderTarget(var0, true);
   }

   public void bind(RecyclerView var1) {
      this.mRecyclerBinder.bind(var1);
   }

   public boolean canMeasure() {
      return this.mRecyclerBinder.canMeasure();
   }

   public void clear() {
      if(this.mUseBackgroundChangeSets) {
         this.mRecyclerBinder.clearAsync();
      } else {
         this.mRecyclerBinder.removeRangeAt(0, this.mRecyclerBinder.getItemCount());
      }
   }

   public void delete(int var1) {
      if(this.mUseBackgroundChangeSets) {
         this.mRecyclerBinder.removeItemAtAsync(var1);
      } else {
         this.mRecyclerBinder.removeItemAt(var1);
      }
   }

   public void deleteRange(int var1, int var2) {
      if(this.mUseBackgroundChangeSets) {
         this.mRecyclerBinder.removeRangeAtAsync(var1, var2);
      } else {
         this.mRecyclerBinder.removeRangeAt(var1, var2);
      }
   }

   public ComponentTree getComponentAt(int var1) {
      return this.mRecyclerBinder.getComponentAt(var1);
   }

   public void insert(int var1, RenderInfo var2) {
      if(this.mUseBackgroundChangeSets) {
         this.mRecyclerBinder.insertItemAtAsync(var1, var2);
      } else {
         this.mRecyclerBinder.insertItemAt(var1, var2);
      }
   }

   public void insertRange(int var1, int var2, List<RenderInfo> var3) {
      if(this.mUseBackgroundChangeSets) {
         this.mRecyclerBinder.insertRangeAtAsync(var1, var3);
      } else {
         this.mRecyclerBinder.insertRangeAt(var1, var3);
      }
   }

   public boolean isWrapContent() {
      return this.mRecyclerBinder.isWrapContent();
   }

   public void measure(Size var1, int var2, int var3, EventHandler<ReMeasureEvent> var4) {
      this.mRecyclerBinder.measure(var1, var2, var3, var4);
   }

   public void mount(RecyclerView var1) {
      this.mRecyclerBinder.mount(var1);
   }

   public void move(int var1, int var2) {
      if(this.mUseBackgroundChangeSets) {
         this.mRecyclerBinder.moveItemAsync(var1, var2);
      } else {
         this.mRecyclerBinder.moveItem(var1, var2);
      }
   }

   public void notifyChangeSetComplete(boolean var1, ChangeSetCompleteCallback var2) {
      if(this.mUseBackgroundChangeSets) {
         this.mRecyclerBinder.notifyChangeSetCompleteAsync(var1, var2);
      } else {
         this.mRecyclerBinder.notifyChangeSetComplete(var1, var2);
      }
   }

   public void requestFocus(int var1) {
      this.mRecyclerBinder.scrollToPosition(var1);
   }

   public void requestFocusWithOffset(int var1, int var2) {
      this.mRecyclerBinder.scrollToPositionWithOffset(var1, var2);
   }

   public void requestSmoothFocus(int var1, int var2, SmoothScrollAlignmentType var3) {
      this.mRecyclerBinder.scrollSmoothToPosition(var1, var2, var3);
   }

   public void setCanMeasure(boolean var1) {
      this.mRecyclerBinder.setCanMeasure(var1);
   }

   public void setSize(int var1, int var2) {
      this.mRecyclerBinder.setSize(var1, var2);
   }

   public void setViewportChangedListener(@Nullable ViewportInfo.ViewportChanged var1) {
      this.mRecyclerBinder.setViewportChangedListener(var1);
   }

   public boolean supportsBackgroundChangeSets() {
      return this.mUseBackgroundChangeSets;
   }

   public void unbind(RecyclerView var1) {
      this.mRecyclerBinder.unbind(var1);
   }

   public void unmount(RecyclerView var1) {
      this.mRecyclerBinder.unmount(var1);
   }

   public void update(int var1, RenderInfo var2) {
      if(this.mUseBackgroundChangeSets) {
         this.mRecyclerBinder.updateItemAtAsync(var1, var2);
      } else {
         this.mRecyclerBinder.updateItemAt(var1, var2);
      }
   }

   public void updateRange(int var1, int var2, List<RenderInfo> var3) {
      if(this.mUseBackgroundChangeSets) {
         this.mRecyclerBinder.updateRangeAtAsync(var1, var3);
      } else {
         this.mRecyclerBinder.updateRangeAt(var1, var3);
      }
   }
}
