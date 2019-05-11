package com.tuya.smart.litho.mist.component;

import android.view.View;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ErrorEvent;
import com.facebook.litho.EventHandler;
import com.facebook.litho.HasEventDispatcher;
import com.facebook.litho.StateContainer;
import com.facebook.litho.StateValue;
import com.facebook.litho.VisibleEvent;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.config.ComponentsConfiguration;
import com.tuya.smart.litho.mist.component.MistTextSpec;
import com.tuya.smart.litho.mist.component.MistText.Builder;
import com.tuya.smart.litho.mist.component.MistText.MistTextStateContainer;
import com.tuya.smart.litho.mist.component.MistText.UpdateLikeButtonStateUpdate;

public final class MistText extends Component {

   @Comparable(
      type = 3
   )
   @Prop(
      optional = false,
      resType = ResType.NONE
   )
   boolean initLiked;
   @Comparable(
      type = 14
   )
   private MistTextStateContainer mStateContainer = new MistTextStateContainer();
   @Comparable(
      type = 13
   )
   @Prop(
      optional = false,
      resType = ResType.NONE
   )
   String someProp;


   private MistText() {
      super("MistText");
   }

   // $FF: synthetic method
   static MistTextStateContainer access$100(MistText var0) {
      return var0.mStateContainer;
   }

   public static Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static Builder create(ComponentContext var0, int var1, int var2) {
      Builder var3 = new Builder();
      Builder.access$000(var3, var0, var1, var2, new MistText());
      return var3;
   }

   private UpdateLikeButtonStateUpdate createUpdateLikeButtonStateUpdate(boolean var1) {
      return new UpdateLikeButtonStateUpdate(var1);
   }

   public static EventHandler<ClickEvent> onClick(ComponentContext var0) {
      return newEventHandler(var0, -1351902487, new Object[]{var0});
   }

   private void onClick(HasEventDispatcher var1, ComponentContext var2, View var3) {
      MistText var4 = (MistText)var1;
      MistTextSpec.onClick(var2, var3, var4.someProp, var4.mStateContainer.isLiked);
   }

   public static EventHandler<VisibleEvent> onTitleVisible(ComponentContext var0) {
      return newEventHandler(var0, -1131012487, new Object[]{var0});
   }

   private void onTitleVisible(HasEventDispatcher var1, ComponentContext var2) {
      MistText var3 = (MistText)var1;
      MistTextSpec.onTitleVisible(var2);
   }

   protected static void updateLikeButton(ComponentContext var0, boolean var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateSync(((MistText)var2).createUpdateLikeButtonStateUpdate(var1), "MistText.updateLikeButton");
      }
   }

   protected static void updateLikeButtonAsync(ComponentContext var0, boolean var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateAsync(((MistText)var2).createUpdateLikeButtonStateUpdate(var1), "MistText.updateLikeButton");
      }
   }

   protected static void updateLikeButtonSync(ComponentContext var0, boolean var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateSync(((MistText)var2).createUpdateLikeButtonStateUpdate(var1), "MistText.updateLikeButton");
      }
   }

   protected void createInitialState(ComponentContext var1) {
      StateValue var2 = new StateValue();
      MistTextSpec.createInitialState(var1, var2, this.initLiked);
      if(var2.get() != null) {
         this.mStateContainer.isLiked = ((Boolean)var2.get()).booleanValue();
      }

   }

   public Object dispatchOnEvent(EventHandler var1, Object var2) {
      int var3 = var1.id;
      if(var3 != -1351902487) {
         if(var3 != -1131012487) {
            if(var3 != -1048037474) {
               return null;
            } else {
               dispatchErrorEvent((ComponentContext)var1.params[0], (ErrorEvent)var2);
               return null;
            }
         } else {
            VisibleEvent var5 = (VisibleEvent)var2;
            this.onTitleVisible(var1.mHasEventDispatcher, (ComponentContext)var1.params[0]);
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
            MistText var2 = (MistText)var1;
            if(this.getId() == var2.getId()) {
               return true;
            } else if(this.initLiked != var2.initLiked) {
               return false;
            } else {
               if(this.someProp != null) {
                  if(!this.someProp.equals(var2.someProp)) {
                     return false;
                  }
               } else if(var2.someProp != null) {
                  return false;
               }

               return this.mStateContainer.isLiked == var2.mStateContainer.isLiked;
            }
         }
      } else {
         return false;
      }
   }

   public MistText makeShallowCopy() {
      MistText var1 = (MistText)super.makeShallowCopy();
      var1.mStateContainer = new MistTextStateContainer();
      return var1;
   }

   protected Component onCreateLayout(ComponentContext var1) {
      return MistTextSpec.onCreateLayout(var1, this.mStateContainer.isLiked);
   }

   protected void transferState(ComponentContext var1, StateContainer var2) {
      MistTextStateContainer var3 = (MistTextStateContainer)var2;
      this.mStateContainer.isLiked = var3.isLiked;
   }
}
