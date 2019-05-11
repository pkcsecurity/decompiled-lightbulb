package com.facebook.litho.widget;

import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.Px;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools;
import android.view.View;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.ErrorEvent;
import com.facebook.litho.EventHandler;
import com.facebook.litho.HasEventDispatcher;
import com.facebook.litho.StateContainer;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.State;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.ItemSelectedEvent;
import com.facebook.litho.widget.SpinnerSpec;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;

@RequiresApi(11)
public final class Spinner extends Component {

   static final Pools.SynchronizedPool<ItemSelectedEvent> sItemSelectedEventPool = new Pools.SynchronizedPool(2);
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.DRAWABLE
   )
   @Nullable
   Drawable caret;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.INT
   )
   int itemLayout = 17367050;
   EventHandler itemSelectedEventHandler;
   @Comparable(
      type = 14
   )
   private Spinner.SpinnerStateContainer mStateContainer = new Spinner.SpinnerStateContainer();
   @Comparable(
      type = 5
   )
   @Prop(
      optional = false,
      resType = ResType.NONE
   )
   List<String> options;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = false,
      resType = ResType.NONE
   )
   String selectedOption;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int selectedTextColor = -570425344;
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_TEXT
   )
   float selectedTextSize = -1.0F;


   private Spinner() {
      super("Spinner");
   }

   public static Spinner.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static Spinner.Builder create(ComponentContext var0, int var1, int var2) {
      Spinner.Builder var3 = new Spinner.Builder();
      var3.init(var0, var1, var2, new Spinner());
      return var3;
   }

   private Spinner.UpdateIsShowingDropDownStateUpdate createUpdateIsShowingDropDownStateUpdate(boolean var1) {
      return new Spinner.UpdateIsShowingDropDownStateUpdate(var1);
   }

   private Spinner.UpdateSelectionStateUpdate createUpdateSelectionStateUpdate(String var1) {
      return new Spinner.UpdateSelectionStateUpdate(var1);
   }

   static void dispatchItemSelectedEvent(EventHandler var0, String var1) {
      ItemSelectedEvent var3 = (ItemSelectedEvent)sItemSelectedEventPool.acquire();
      ItemSelectedEvent var2 = var3;
      if(var3 == null) {
         var2 = new ItemSelectedEvent();
      }

      var2.newSelection = var1;
      var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, var2);
      var2.newSelection = null;
      sItemSelectedEventPool.release(var2);
   }

   public static EventHandler getItemSelectedEventHandler(ComponentContext var0) {
      return var0.getComponentScope() == null?null:((Spinner)var0.getComponentScope()).itemSelectedEventHandler;
   }

   public static EventHandler<ClickEvent> onClick(ComponentContext var0) {
      return newEventHandler(var0, -1351902487, new Object[]{var0});
   }

   private void onClick(HasEventDispatcher var1, ComponentContext var2, View var3) {
      Spinner var4 = (Spinner)var1;
      SpinnerSpec.onClick(var2, var3, var4.options, var4.itemLayout);
   }

   protected static void updateIsShowingDropDown(ComponentContext var0, boolean var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateSync(((Spinner)var2).createUpdateIsShowingDropDownStateUpdate(var1), "Spinner.updateIsShowingDropDown");
      }
   }

   protected static void updateIsShowingDropDownAsync(ComponentContext var0, boolean var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateAsync(((Spinner)var2).createUpdateIsShowingDropDownStateUpdate(var1), "Spinner.updateIsShowingDropDown");
      }
   }

   protected static void updateIsShowingDropDownSync(ComponentContext var0, boolean var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateSync(((Spinner)var2).createUpdateIsShowingDropDownStateUpdate(var1), "Spinner.updateIsShowingDropDown");
      }
   }

   protected static void updateSelection(ComponentContext var0, String var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateSync(((Spinner)var2).createUpdateSelectionStateUpdate(var1), "Spinner.updateSelection");
      }
   }

   protected static void updateSelectionAsync(ComponentContext var0, String var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateAsync(((Spinner)var2).createUpdateSelectionStateUpdate(var1), "Spinner.updateSelection");
      }
   }

   protected static void updateSelectionSync(ComponentContext var0, String var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateSync(((Spinner)var2).createUpdateSelectionStateUpdate(var1), "Spinner.updateSelection");
      }
   }

   protected void createInitialState(ComponentContext var1) {
      StateValue var2 = new StateValue();
      SpinnerSpec.onCreateInitialState(var1, this.selectedOption, var2);
      this.mStateContainer.selection = (String)var2.get();
   }

   public Object dispatchOnEvent(EventHandler var1, Object var2) {
      int var3 = var1.id;
      if(var3 != -1351902487) {
         if(var3 != -1048037474) {
            return null;
         } else {
            dispatchErrorEvent((ComponentContext)var1.params[0], (ErrorEvent)var2);
            return null;
         }
      } else {
         ClickEvent var4 = (ClickEvent)var2;
         this.onClick(var1.mHasEventDispatcher, (ComponentContext)var1.params[0], var4.view);
         return null;
      }
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
            Spinner var2 = (Spinner)var1;
            if(this.getId() == var2.getId()) {
               return true;
            } else {
               if(this.caret != null) {
                  if(!this.caret.equals(var2.caret)) {
                     return false;
                  }
               } else if(var2.caret != null) {
                  return false;
               }

               if(this.itemLayout != var2.itemLayout) {
                  return false;
               } else {
                  if(this.options != null) {
                     if(!this.options.equals(var2.options)) {
                        return false;
                     }
                  } else if(var2.options != null) {
                     return false;
                  }

                  if(this.selectedOption != null) {
                     if(!this.selectedOption.equals(var2.selectedOption)) {
                        return false;
                     }
                  } else if(var2.selectedOption != null) {
                     return false;
                  }

                  if(this.selectedTextColor != var2.selectedTextColor) {
                     return false;
                  } else if(Float.compare(this.selectedTextSize, var2.selectedTextSize) != 0) {
                     return false;
                  } else if(this.mStateContainer.isShowingDropDown != var2.mStateContainer.isShowingDropDown) {
                     return false;
                  } else {
                     if(this.mStateContainer.selection != null) {
                        if(!this.mStateContainer.selection.equals(var2.mStateContainer.selection)) {
                           return false;
                        }
                     } else if(var2.mStateContainer.selection != null) {
                        return false;
                     }

                     return true;
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   public Spinner makeShallowCopy() {
      Spinner var1 = (Spinner)super.makeShallowCopy();
      var1.mStateContainer = new Spinner.SpinnerStateContainer();
      return var1;
   }

   protected Component onCreateLayout(ComponentContext var1) {
      return SpinnerSpec.onCreateLayout(var1, this.mStateContainer.selection, this.mStateContainer.isShowingDropDown, this.selectedTextSize, this.selectedTextColor, this.caret);
   }

   protected void transferState(ComponentContext var1, StateContainer var2) {
      Spinner.SpinnerStateContainer var3 = (Spinner.SpinnerStateContainer)var2;
      this.mStateContainer.isShowingDropDown = var3.isShowingDropDown;
      this.mStateContainer.selection = var3.selection;
   }

   static class UpdateIsShowingDropDownStateUpdate implements ComponentLifecycle.StateUpdate {

      private boolean mIsShowing;


      UpdateIsShowingDropDownStateUpdate(boolean var1) {
         this.mIsShowing = var1;
      }

      public void updateState(StateContainer var1, Component var2) {
         Spinner.SpinnerStateContainer var4 = (Spinner.SpinnerStateContainer)var1;
         Spinner var5 = (Spinner)var2;
         StateValue var3 = new StateValue();
         var3.set(Boolean.valueOf(var4.isShowingDropDown));
         SpinnerSpec.updateIsShowingDropDown(var3, this.mIsShowing);
         var5.mStateContainer.isShowingDropDown = ((Boolean)var3.get()).booleanValue();
      }
   }

   public static class Builder extends Component.Builder<Spinner.Builder> {

      private final int REQUIRED_PROPS_COUNT = 2;
      private final String[] REQUIRED_PROPS_NAMES = new String[]{"options", "selectedOption"};
      ComponentContext mContext;
      private final BitSet mRequired = new BitSet(2);
      Spinner mSpinner;


      private void init(ComponentContext var1, int var2, int var3, Spinner var4) {
         super.init(var1, var2, var3, var4);
         this.mSpinner = var4;
         this.mContext = var1;
         this.mRequired.clear();
      }

      public Spinner build() {
         checkArgs(2, this.mRequired, this.REQUIRED_PROPS_NAMES);
         Spinner var1 = this.mSpinner;
         this.release();
         return var1;
      }

      public Spinner.Builder caret(@Nullable Drawable var1) {
         this.mSpinner.caret = var1;
         return this;
      }

      public Spinner.Builder caretAttr(@AttrRes int var1) {
         this.mSpinner.caret = this.mResourceResolver.resolveDrawableAttr(var1, 0);
         return this;
      }

      public Spinner.Builder caretAttr(@AttrRes int var1, @DrawableRes int var2) {
         this.mSpinner.caret = this.mResourceResolver.resolveDrawableAttr(var1, var2);
         return this;
      }

      public Spinner.Builder caretRes(@DrawableRes int var1) {
         this.mSpinner.caret = this.mResourceResolver.resolveDrawableRes(var1);
         return this;
      }

      public Spinner.Builder getThis() {
         return this;
      }

      public Spinner.Builder itemLayout(int var1) {
         this.mSpinner.itemLayout = var1;
         return this;
      }

      public Spinner.Builder itemLayoutAttr(@AttrRes int var1) {
         this.mSpinner.itemLayout = this.mResourceResolver.resolveIntAttr(var1, 0);
         return this;
      }

      public Spinner.Builder itemLayoutAttr(@AttrRes int var1, @IntegerRes int var2) {
         this.mSpinner.itemLayout = this.mResourceResolver.resolveIntAttr(var1, var2);
         return this;
      }

      public Spinner.Builder itemLayoutRes(@IntegerRes int var1) {
         this.mSpinner.itemLayout = this.mResourceResolver.resolveIntRes(var1);
         return this;
      }

      public Spinner.Builder itemSelectedEventHandler(EventHandler var1) {
         this.mSpinner.itemSelectedEventHandler = var1;
         return this;
      }

      public Spinner.Builder options(List<String> var1) {
         this.mSpinner.options = var1;
         this.mRequired.set(0);
         return this;
      }

      protected void release() {
         super.release();
         this.mSpinner = null;
         this.mContext = null;
      }

      public Spinner.Builder selectedOption(String var1) {
         this.mSpinner.selectedOption = var1;
         this.mRequired.set(1);
         return this;
      }

      public Spinner.Builder selectedTextColor(@ColorInt int var1) {
         this.mSpinner.selectedTextColor = var1;
         return this;
      }

      public Spinner.Builder selectedTextColorAttr(@AttrRes int var1) {
         this.mSpinner.selectedTextColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public Spinner.Builder selectedTextColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mSpinner.selectedTextColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public Spinner.Builder selectedTextColorRes(@ColorRes int var1) {
         this.mSpinner.selectedTextColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public Spinner.Builder selectedTextSizeAttr(@AttrRes int var1) {
         this.mSpinner.selectedTextSize = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public Spinner.Builder selectedTextSizeAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mSpinner.selectedTextSize = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public Spinner.Builder selectedTextSizeDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mSpinner.selectedTextSize = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public Spinner.Builder selectedTextSizePx(@Px float var1) {
         this.mSpinner.selectedTextSize = var1;
         return this;
      }

      public Spinner.Builder selectedTextSizeRes(@DimenRes int var1) {
         this.mSpinner.selectedTextSize = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public Spinner.Builder selectedTextSizeSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mSpinner.selectedTextSize = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }
   }

   @VisibleForTesting(
      otherwise = 2
   )
   static class SpinnerStateContainer implements StateContainer {

      @Comparable(
         type = 3
      )
      @State
      boolean isShowingDropDown;
      @Comparable(
         type = 13
      )
      @State
      String selection;


   }

   static class UpdateSelectionStateUpdate implements ComponentLifecycle.StateUpdate {

      private String mNewSelection;


      UpdateSelectionStateUpdate(String var1) {
         this.mNewSelection = var1;
      }

      public void updateState(StateContainer var1, Component var2) {
         Spinner.SpinnerStateContainer var4 = (Spinner.SpinnerStateContainer)var1;
         Spinner var5 = (Spinner)var2;
         StateValue var3 = new StateValue();
         var3.set(var4.selection);
         SpinnerSpec.updateSelection(var3, this.mNewSelection);
         var5.mStateContainer.selection = (String)var3.get();
      }
   }
}
