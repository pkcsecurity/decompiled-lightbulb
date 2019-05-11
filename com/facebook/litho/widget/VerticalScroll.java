package com.facebook.litho.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.Px;
import android.support.annotation.VisibleForTesting;
import android.support.v4.widget.NestedScrollView;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.ComponentTree;
import com.facebook.litho.Diff;
import com.facebook.litho.Output;
import com.facebook.litho.Size;
import com.facebook.litho.StateContainer;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.State;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.VerticalScrollSpec;
import java.util.BitSet;

public final class VerticalScroll extends Component {

   @Comparable(
      type = 10
   )
   @Prop(
      optional = false,
      resType = ResType.NONE
   )
   Component childComponent;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_SIZE
   )
   int fadingEdgeLength;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean fillViewport;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean incrementalMountEnabled;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   Integer initialScrollOffsetPixels;
   @Comparable(
      type = 14
   )
   private VerticalScroll.VerticalScrollStateContainer mStateContainer = new VerticalScroll.VerticalScrollStateContainer();
   Integer measuredHeight;
   Integer measuredWidth;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean nestedScrollingEnabled;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   NestedScrollView.OnScrollChangeListener onScrollChangeListener;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean scrollbarEnabled = true;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean scrollbarFadingEnabled = true;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean verticalFadingEdgeEnabled;


   private VerticalScroll() {
      super("VerticalScroll");
   }

   public static VerticalScroll.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static VerticalScroll.Builder create(ComponentContext var0, int var1, int var2) {
      VerticalScroll.Builder var3 = new VerticalScroll.Builder();
      var3.init(var0, var1, var2, new VerticalScroll());
      return var3;
   }

   public boolean callsShouldUpdateOnMount() {
      return true;
   }

   protected boolean canMeasure() {
      return true;
   }

   protected boolean canPreallocate() {
      return false;
   }

   protected void copyInterStageImpl(Component var1) {
      VerticalScroll var2 = (VerticalScroll)var1;
      this.measuredHeight = var2.measuredHeight;
      this.measuredWidth = var2.measuredWidth;
   }

   protected void createInitialState(ComponentContext var1) {
      StateValue var2 = new StateValue();
      StateValue var3 = new StateValue();
      VerticalScrollSpec.onCreateInitialState(var1, var2, var3, this.initialScrollOffsetPixels, this.incrementalMountEnabled, this.childComponent);
      this.mStateContainer.scrollPosition = (VerticalScrollSpec.ScrollPosition)var2.get();
      this.mStateContainer.childComponentTree = (ComponentTree)var3.get();
   }

   public ComponentLifecycle.MountType getMountType() {
      return ComponentLifecycle.MountType.VIEW;
   }

   protected StateContainer getStateContainer() {
      return this.mStateContainer;
   }

   public boolean hasChildLithoViews() {
      return true;
   }

   protected boolean hasState() {
      return true;
   }

   public boolean isEquivalentTo(Component var1) {
      if(ComponentsConfiguration.useNewIsEquivalentTo) {
         return super.isEquivalentTo(var1);
      } else if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            VerticalScroll var2 = (VerticalScroll)var1;
            if(this.getId() == var2.getId()) {
               return true;
            } else {
               if(this.childComponent != null) {
                  if(!this.childComponent.isEquivalentTo(var2.childComponent)) {
                     return false;
                  }
               } else if(var2.childComponent != null) {
                  return false;
               }

               if(this.fadingEdgeLength != var2.fadingEdgeLength) {
                  return false;
               } else if(this.fillViewport != var2.fillViewport) {
                  return false;
               } else if(this.incrementalMountEnabled != var2.incrementalMountEnabled) {
                  return false;
               } else {
                  if(this.initialScrollOffsetPixels != null) {
                     if(!this.initialScrollOffsetPixels.equals(var2.initialScrollOffsetPixels)) {
                        return false;
                     }
                  } else if(var2.initialScrollOffsetPixels != null) {
                     return false;
                  }

                  if(this.nestedScrollingEnabled != var2.nestedScrollingEnabled) {
                     return false;
                  } else {
                     if(this.onScrollChangeListener != null) {
                        if(!this.onScrollChangeListener.equals(var2.onScrollChangeListener)) {
                           return false;
                        }
                     } else if(var2.onScrollChangeListener != null) {
                        return false;
                     }

                     if(this.scrollbarEnabled != var2.scrollbarEnabled) {
                        return false;
                     } else if(this.scrollbarFadingEnabled != var2.scrollbarFadingEnabled) {
                        return false;
                     } else if(this.verticalFadingEdgeEnabled != var2.verticalFadingEdgeEnabled) {
                        return false;
                     } else {
                        if(this.mStateContainer.childComponentTree != null) {
                           if(!this.mStateContainer.childComponentTree.equals(var2.mStateContainer.childComponentTree)) {
                              return false;
                           }
                        } else if(var2.mStateContainer.childComponentTree != null) {
                           return false;
                        }

                        if(this.mStateContainer.scrollPosition != null) {
                           if(!this.mStateContainer.scrollPosition.equals(var2.mStateContainer.scrollPosition)) {
                              return false;
                           }
                        } else if(var2.mStateContainer.scrollPosition != null) {
                           return false;
                        }

                        return true;
                     }
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   public boolean isPureRender() {
      return true;
   }

   public VerticalScroll makeShallowCopy() {
      VerticalScroll var2 = (VerticalScroll)super.makeShallowCopy();
      Component var1;
      if(var2.childComponent != null) {
         var1 = var2.childComponent.makeShallowCopy();
      } else {
         var1 = null;
      }

      var2.childComponent = var1;
      var2.measuredHeight = null;
      var2.measuredWidth = null;
      var2.mStateContainer = new VerticalScroll.VerticalScrollStateContainer();
      return var2;
   }

   protected void onBoundsDefined(ComponentContext var1, ComponentLayout var2) {
      VerticalScrollSpec.onBoundsDefined(var1, var2, this.childComponent, this.fillViewport, this.mStateContainer.childComponentTree, this.measuredWidth, this.measuredHeight);
   }

   protected Object onCreateMountContent(Context var1) {
      return VerticalScrollSpec.onCreateMountContent(var1);
   }

   protected void onMeasure(ComponentContext var1, ComponentLayout var2, int var3, int var4, Size var5) {
      Output var6 = this.acquireOutput();
      Output var7 = this.acquireOutput();
      VerticalScrollSpec.onMeasure(var1, var2, var3, var4, var5, this.childComponent, this.fillViewport, this.mStateContainer.childComponentTree, var6, var7);
      this.measuredWidth = (Integer)var6.get();
      this.releaseOutput(var6);
      this.measuredHeight = (Integer)var7.get();
      this.releaseOutput(var7);
   }

   protected void onMount(ComponentContext var1, Object var2) {
      VerticalScrollSpec.onMount(var1, (VerticalScrollSpec.LithoScrollView)var2, this.scrollbarEnabled, this.scrollbarFadingEnabled, this.nestedScrollingEnabled, this.incrementalMountEnabled, this.verticalFadingEdgeEnabled, this.fadingEdgeLength, this.onScrollChangeListener, this.mStateContainer.childComponentTree, this.mStateContainer.scrollPosition);
   }

   protected void onUnmount(ComponentContext var1, Object var2) {
      VerticalScrollSpec.onUnmount(var1, (VerticalScrollSpec.LithoScrollView)var2);
   }

   protected int poolSize() {
      return 3;
   }

   protected boolean shouldUpdate(Component var1, Component var2) {
      VerticalScroll var6 = (VerticalScroll)var1;
      VerticalScroll var5 = (VerticalScroll)var2;
      Object var4 = null;
      if(var6 == null) {
         var1 = null;
      } else {
         var1 = var6.childComponent;
      }

      if(var5 == null) {
         var2 = null;
      } else {
         var2 = var5.childComponent;
      }

      Diff var7 = this.acquireDiff(var1, var2);
      Boolean var12;
      if(var6 == null) {
         var12 = null;
      } else {
         var12 = Boolean.valueOf(var6.scrollbarEnabled);
      }

      Boolean var13;
      if(var5 == null) {
         var13 = null;
      } else {
         var13 = Boolean.valueOf(var5.scrollbarEnabled);
      }

      Diff var8 = this.acquireDiff(var12, var13);
      if(var6 == null) {
         var12 = null;
      } else {
         var12 = Boolean.valueOf(var6.scrollbarFadingEnabled);
      }

      if(var5 == null) {
         var13 = null;
      } else {
         var13 = Boolean.valueOf(var5.scrollbarFadingEnabled);
      }

      Diff var9 = this.acquireDiff(var12, var13);
      if(var6 == null) {
         var12 = null;
      } else {
         var12 = Boolean.valueOf(var6.fillViewport);
      }

      if(var5 == null) {
         var13 = null;
      } else {
         var13 = Boolean.valueOf(var5.fillViewport);
      }

      Diff var10 = this.acquireDiff(var12, var13);
      if(var6 == null) {
         var12 = null;
      } else {
         var12 = Boolean.valueOf(var6.nestedScrollingEnabled);
      }

      if(var5 == null) {
         var13 = null;
      } else {
         var13 = Boolean.valueOf(var5.nestedScrollingEnabled);
      }

      Diff var11 = this.acquireDiff(var12, var13);
      if(var6 == null) {
         var12 = null;
      } else {
         var12 = Boolean.valueOf(var6.incrementalMountEnabled);
      }

      if(var5 == null) {
         var13 = (Boolean)var4;
      } else {
         var13 = Boolean.valueOf(var5.incrementalMountEnabled);
      }

      Diff var14 = this.acquireDiff(var12, var13);
      boolean var3 = VerticalScrollSpec.shouldUpdate(var7, var8, var9, var10, var11, var14);
      this.releaseDiff(var7);
      this.releaseDiff(var8);
      this.releaseDiff(var9);
      this.releaseDiff(var10);
      this.releaseDiff(var11);
      this.releaseDiff(var14);
      return var3;
   }

   protected void transferState(ComponentContext var1, StateContainer var2) {
      VerticalScroll.VerticalScrollStateContainer var3 = (VerticalScroll.VerticalScrollStateContainer)var2;
      this.mStateContainer.childComponentTree = var3.childComponentTree;
      this.mStateContainer.scrollPosition = var3.scrollPosition;
   }

   @VisibleForTesting(
      otherwise = 2
   )
   static class VerticalScrollStateContainer implements StateContainer {

      @Comparable(
         type = 13
      )
      @State
      ComponentTree childComponentTree;
      @Comparable(
         type = 13
      )
      @State
      VerticalScrollSpec.ScrollPosition scrollPosition;


   }

   public static class Builder extends Component.Builder<VerticalScroll.Builder> {

      private final int REQUIRED_PROPS_COUNT = 1;
      private final String[] REQUIRED_PROPS_NAMES = new String[]{"childComponent"};
      ComponentContext mContext;
      private final BitSet mRequired = new BitSet(1);
      VerticalScroll mVerticalScroll;


      private void init(ComponentContext var1, int var2, int var3, VerticalScroll var4) {
         super.init(var1, var2, var3, var4);
         this.mVerticalScroll = var4;
         this.mContext = var1;
         this.mRequired.clear();
      }

      public VerticalScroll build() {
         checkArgs(1, this.mRequired, this.REQUIRED_PROPS_NAMES);
         VerticalScroll var1 = this.mVerticalScroll;
         this.release();
         return var1;
      }

      public VerticalScroll.Builder childComponent(Component.Builder<?> var1) {
         VerticalScroll var2 = this.mVerticalScroll;
         Component var3;
         if(var1 == null) {
            var3 = null;
         } else {
            var3 = var1.build();
         }

         var2.childComponent = var3;
         this.mRequired.set(0);
         return this;
      }

      public VerticalScroll.Builder childComponent(Component var1) {
         VerticalScroll var2 = this.mVerticalScroll;
         if(var1 == null) {
            var1 = null;
         } else {
            var1 = var1.makeShallowCopy();
         }

         var2.childComponent = var1;
         this.mRequired.set(0);
         return this;
      }

      public VerticalScroll.Builder fadingEdgeLengthAttr(@AttrRes int var1) {
         this.mVerticalScroll.fadingEdgeLength = this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public VerticalScroll.Builder fadingEdgeLengthAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mVerticalScroll.fadingEdgeLength = this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public VerticalScroll.Builder fadingEdgeLengthDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mVerticalScroll.fadingEdgeLength = this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public VerticalScroll.Builder fadingEdgeLengthPx(@Px int var1) {
         this.mVerticalScroll.fadingEdgeLength = var1;
         return this;
      }

      public VerticalScroll.Builder fadingEdgeLengthRes(@DimenRes int var1) {
         this.mVerticalScroll.fadingEdgeLength = this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public VerticalScroll.Builder fillViewport(boolean var1) {
         this.mVerticalScroll.fillViewport = var1;
         return this;
      }

      public VerticalScroll.Builder getThis() {
         return this;
      }

      public VerticalScroll.Builder incrementalMountEnabled(boolean var1) {
         this.mVerticalScroll.incrementalMountEnabled = var1;
         return this;
      }

      public VerticalScroll.Builder initialScrollOffsetPixels(Integer var1) {
         this.mVerticalScroll.initialScrollOffsetPixels = var1;
         return this;
      }

      public VerticalScroll.Builder nestedScrollingEnabled(boolean var1) {
         this.mVerticalScroll.nestedScrollingEnabled = var1;
         return this;
      }

      public VerticalScroll.Builder onScrollChangeListener(NestedScrollView.OnScrollChangeListener var1) {
         this.mVerticalScroll.onScrollChangeListener = var1;
         return this;
      }

      protected void release() {
         super.release();
         this.mVerticalScroll = null;
         this.mContext = null;
      }

      public VerticalScroll.Builder scrollbarEnabled(boolean var1) {
         this.mVerticalScroll.scrollbarEnabled = var1;
         return this;
      }

      public VerticalScroll.Builder scrollbarFadingEnabled(boolean var1) {
         this.mVerticalScroll.scrollbarFadingEnabled = var1;
         return this;
      }

      public VerticalScroll.Builder verticalFadingEdgeEnabled(boolean var1) {
         this.mVerticalScroll.verticalFadingEdgeEnabled = var1;
         return this;
      }
   }
}
