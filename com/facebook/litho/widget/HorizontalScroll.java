package com.facebook.litho.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.BoolRes;
import android.support.annotation.VisibleForTesting;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.Output;
import com.facebook.litho.Size;
import com.facebook.litho.StateContainer;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.State;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.HorizontalScrollEventsController;
import com.facebook.litho.widget.HorizontalScrollSpec;
import com.facebook.yoga.YogaDirection;
import java.util.BitSet;

public final class HorizontalScroll extends Component {

   Integer componentHeight;
   Integer componentWidth;
   @Comparable(
      type = 10
   )
   @Prop(
      optional = false,
      resType = ResType.NONE
   )
   Component contentProps;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   HorizontalScrollEventsController eventsController;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   Integer initialScrollPosition;
   YogaDirection layoutDirection;
   @Comparable(
      type = 14
   )
   private HorizontalScroll.HorizontalScrollStateContainer mStateContainer = new HorizontalScroll.HorizontalScrollStateContainer();
   Integer measuredComponentHeight;
   Integer measuredComponentWidth;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.BOOL
   )
   boolean scrollbarEnabled = true;


   private HorizontalScroll() {
      super("HorizontalScroll");
   }

   public static HorizontalScroll.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static HorizontalScroll.Builder create(ComponentContext var0, int var1, int var2) {
      HorizontalScroll.Builder var3 = new HorizontalScroll.Builder();
      var3.init(var0, var1, var2, new HorizontalScroll());
      return var3;
   }

   protected boolean canMeasure() {
      return true;
   }

   protected boolean canPreallocate() {
      return false;
   }

   protected void copyInterStageImpl(Component var1) {
      HorizontalScroll var2 = (HorizontalScroll)var1;
      this.componentHeight = var2.componentHeight;
      this.componentWidth = var2.componentWidth;
      this.layoutDirection = var2.layoutDirection;
      this.measuredComponentHeight = var2.measuredComponentHeight;
      this.measuredComponentWidth = var2.measuredComponentWidth;
   }

   protected void createInitialState(ComponentContext var1) {
      StateValue var2 = new StateValue();
      HorizontalScrollSpec.onCreateInitialState(var1, var2, this.initialScrollPosition);
      this.mStateContainer.lastScrollPosition = (HorizontalScrollSpec.ScrollPosition)var2.get();
   }

   public ComponentLifecycle.MountType getMountType() {
      return ComponentLifecycle.MountType.VIEW;
   }

   protected StateContainer getStateContainer() {
      return this.mStateContainer;
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
            HorizontalScroll var2 = (HorizontalScroll)var1;
            if(this.getId() == var2.getId()) {
               return true;
            } else {
               if(this.contentProps != null) {
                  if(!this.contentProps.isEquivalentTo(var2.contentProps)) {
                     return false;
                  }
               } else if(var2.contentProps != null) {
                  return false;
               }

               if(this.eventsController != null) {
                  if(!this.eventsController.equals(var2.eventsController)) {
                     return false;
                  }
               } else if(var2.eventsController != null) {
                  return false;
               }

               if(this.initialScrollPosition != null) {
                  if(!this.initialScrollPosition.equals(var2.initialScrollPosition)) {
                     return false;
                  }
               } else if(var2.initialScrollPosition != null) {
                  return false;
               }

               if(this.scrollbarEnabled != var2.scrollbarEnabled) {
                  return false;
               } else {
                  if(this.mStateContainer.lastScrollPosition != null) {
                     if(!this.mStateContainer.lastScrollPosition.equals(var2.mStateContainer.lastScrollPosition)) {
                        return false;
                     }
                  } else if(var2.mStateContainer.lastScrollPosition != null) {
                     return false;
                  }

                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   protected boolean isMountSizeDependent() {
      return true;
   }

   public HorizontalScroll makeShallowCopy() {
      HorizontalScroll var2 = (HorizontalScroll)super.makeShallowCopy();
      Component var1;
      if(var2.contentProps != null) {
         var1 = var2.contentProps.makeShallowCopy();
      } else {
         var1 = null;
      }

      var2.contentProps = var1;
      var2.componentHeight = null;
      var2.componentWidth = null;
      var2.layoutDirection = null;
      var2.measuredComponentHeight = null;
      var2.measuredComponentWidth = null;
      var2.mStateContainer = new HorizontalScroll.HorizontalScrollStateContainer();
      return var2;
   }

   protected void onBoundsDefined(ComponentContext var1, ComponentLayout var2) {
      Output var3 = this.acquireOutput();
      Output var4 = this.acquireOutput();
      Output var5 = this.acquireOutput();
      HorizontalScrollSpec.onBoundsDefined(var1, var2, this.contentProps, this.measuredComponentWidth, this.measuredComponentHeight, var3, var4, var5);
      this.componentWidth = (Integer)var3.get();
      this.releaseOutput(var3);
      this.componentHeight = (Integer)var4.get();
      this.releaseOutput(var4);
      this.layoutDirection = (YogaDirection)var5.get();
      this.releaseOutput(var5);
   }

   protected Object onCreateMountContent(Context var1) {
      return HorizontalScrollSpec.onCreateMountContent(var1);
   }

   protected void onLoadStyle(ComponentContext var1) {
      Output var2 = this.acquireOutput();
      HorizontalScrollSpec.onLoadStyle(var1, var2);
      if(var2.get() != null) {
         this.scrollbarEnabled = ((Boolean)var2.get()).booleanValue();
      }

      this.releaseOutput(var2);
   }

   protected void onMeasure(ComponentContext var1, ComponentLayout var2, int var3, int var4, Size var5) {
      Output var6 = this.acquireOutput();
      Output var7 = this.acquireOutput();
      HorizontalScrollSpec.onMeasure(var1, var2, var3, var4, var5, this.contentProps, var6, var7);
      this.measuredComponentWidth = (Integer)var6.get();
      this.releaseOutput(var6);
      this.measuredComponentHeight = (Integer)var7.get();
      this.releaseOutput(var7);
   }

   protected void onMount(ComponentContext var1, Object var2) {
      HorizontalScrollSpec.onMount(var1, (HorizontalScrollSpec.HorizontalScrollLithoView)var2, this.contentProps, this.scrollbarEnabled, this.eventsController, this.mStateContainer.lastScrollPosition, this.componentWidth.intValue(), this.componentHeight.intValue(), this.layoutDirection);
   }

   protected void onUnmount(ComponentContext var1, Object var2) {
      HorizontalScrollSpec.onUnmount(var1, (HorizontalScrollSpec.HorizontalScrollLithoView)var2, this.eventsController);
   }

   protected int poolSize() {
      return 3;
   }

   protected void transferState(ComponentContext var1, StateContainer var2) {
      HorizontalScroll.HorizontalScrollStateContainer var3 = (HorizontalScroll.HorizontalScrollStateContainer)var2;
      this.mStateContainer.lastScrollPosition = var3.lastScrollPosition;
   }

   @VisibleForTesting(
      otherwise = 2
   )
   static class HorizontalScrollStateContainer implements StateContainer {

      @Comparable(
         type = 13
      )
      @State
      HorizontalScrollSpec.ScrollPosition lastScrollPosition;


   }

   public static class Builder extends Component.Builder<HorizontalScroll.Builder> {

      private final int REQUIRED_PROPS_COUNT = 1;
      private final String[] REQUIRED_PROPS_NAMES = new String[]{"contentProps"};
      ComponentContext mContext;
      HorizontalScroll mHorizontalScroll;
      private final BitSet mRequired = new BitSet(1);


      private void init(ComponentContext var1, int var2, int var3, HorizontalScroll var4) {
         super.init(var1, var2, var3, var4);
         this.mHorizontalScroll = var4;
         this.mContext = var1;
         this.mRequired.clear();
      }

      public HorizontalScroll build() {
         checkArgs(1, this.mRequired, this.REQUIRED_PROPS_NAMES);
         HorizontalScroll var1 = this.mHorizontalScroll;
         this.release();
         return var1;
      }

      public HorizontalScroll.Builder contentProps(Component.Builder<?> var1) {
         HorizontalScroll var2 = this.mHorizontalScroll;
         Component var3;
         if(var1 == null) {
            var3 = null;
         } else {
            var3 = var1.build();
         }

         var2.contentProps = var3;
         this.mRequired.set(0);
         return this;
      }

      public HorizontalScroll.Builder contentProps(Component var1) {
         HorizontalScroll var2 = this.mHorizontalScroll;
         if(var1 == null) {
            var1 = null;
         } else {
            var1 = var1.makeShallowCopy();
         }

         var2.contentProps = var1;
         this.mRequired.set(0);
         return this;
      }

      public HorizontalScroll.Builder eventsController(HorizontalScrollEventsController var1) {
         this.mHorizontalScroll.eventsController = var1;
         return this;
      }

      public HorizontalScroll.Builder getThis() {
         return this;
      }

      public HorizontalScroll.Builder initialScrollPosition(Integer var1) {
         this.mHorizontalScroll.initialScrollPosition = var1;
         return this;
      }

      protected void release() {
         super.release();
         this.mHorizontalScroll = null;
         this.mContext = null;
      }

      public HorizontalScroll.Builder scrollbarEnabled(boolean var1) {
         this.mHorizontalScroll.scrollbarEnabled = var1;
         return this;
      }

      public HorizontalScroll.Builder scrollbarEnabledAttr(@AttrRes int var1) {
         this.mHorizontalScroll.scrollbarEnabled = this.mResourceResolver.resolveBoolAttr(var1, 0);
         return this;
      }

      public HorizontalScroll.Builder scrollbarEnabledAttr(@AttrRes int var1, @BoolRes int var2) {
         this.mHorizontalScroll.scrollbarEnabled = this.mResourceResolver.resolveBoolAttr(var1, var2);
         return this;
      }

      public HorizontalScroll.Builder scrollbarEnabledRes(@BoolRes int var1) {
         this.mHorizontalScroll.scrollbarEnabled = this.mResourceResolver.resolveBoolRes(var1);
         return this;
      }
   }
}
