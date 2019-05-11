package com.tuya.smart.litho.mist.component;

import android.view.View;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.StateValue;
import com.facebook.litho.VisibleEvent;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateInitialState;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.OnEvent;
import com.facebook.litho.annotations.OnUpdateState;
import com.facebook.litho.annotations.Param;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.State;
import com.facebook.litho.widget.Text;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.litho.mist.component.MistText;

@LayoutSpec
public class MistTextSpec {

   @OnCreateInitialState
   static void createInitialState(ComponentContext var0, StateValue<Boolean> var1, @Prop boolean var2) {
      var1.set(Boolean.valueOf(var2));
   }

   @OnEvent(ClickEvent.class)
   static void onClick(ComponentContext var0, View var1, @Prop String var2, @State boolean var3) {
      StringBuilder var4 = new StringBuilder();
      var4.append("click:---");
      var4.append(var1);
      var4.append(":");
      var4.append(var2);
      L.i("HHHHHH", var4.toString());
      MistText.updateLikeButton(var0, var3 ^ true);
   }

   @OnCreateLayout
   static Component onCreateLayout(ComponentContext var0, @State boolean var1) {
      Text.Builder var3 = Text.create(var0);
      String var2;
      if(var1) {
         var2 = "like";
      } else {
         var2 = "disLike";
      }

      return ((Text.Builder)((Text.Builder)var3.text(var2).textSizeDip(16.0F).visibleHandler(MistText.onTitleVisible(var0))).clickHandler(MistText.onClick(var0))).build();
   }

   @OnEvent(VisibleEvent.class)
   static void onTitleVisible(ComponentContext var0) {
      L.d("VisibilityRanges", "The title entered the Visible Range");
   }

   @OnUpdateState
   static void updateLikeButton(StateValue<Boolean> var0, @Param boolean var1) {
      var0.set(Boolean.valueOf(var1));
   }
}
