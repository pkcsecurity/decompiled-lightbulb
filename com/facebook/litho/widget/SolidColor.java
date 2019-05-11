package com.facebook.litho.widget;

import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.SolidColorSpec;
import java.util.BitSet;

public final class SolidColor extends Component {

   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   float alpha = -1.0F;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = false,
      resType = ResType.COLOR
   )
   int color;


   private SolidColor() {
      super("SolidColor");
   }

   public static SolidColor.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static SolidColor.Builder create(ComponentContext var0, int var1, int var2) {
      SolidColor.Builder var3 = new SolidColor.Builder();
      var3.init(var0, var1, var2, new SolidColor());
      return var3;
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
            SolidColor var2 = (SolidColor)var1;
            return this.getId() == var2.getId()?true:(Float.compare(this.alpha, var2.alpha) != 0?false:this.color == var2.color);
         }
      } else {
         return false;
      }
   }

   protected Component onCreateLayout(ComponentContext var1) {
      return SolidColorSpec.onCreateLayout(var1, this.color, this.alpha);
   }

   public static class Builder extends Component.Builder<SolidColor.Builder> {

      private final int REQUIRED_PROPS_COUNT = 1;
      private final String[] REQUIRED_PROPS_NAMES = new String[]{"color"};
      ComponentContext mContext;
      private final BitSet mRequired = new BitSet(1);
      SolidColor mSolidColor;


      private void init(ComponentContext var1, int var2, int var3, SolidColor var4) {
         super.init(var1, var2, var3, var4);
         this.mSolidColor = var4;
         this.mContext = var1;
         this.mRequired.clear();
      }

      public SolidColor.Builder alpha(float var1) {
         this.mSolidColor.alpha = var1;
         return this;
      }

      public SolidColor build() {
         checkArgs(1, this.mRequired, this.REQUIRED_PROPS_NAMES);
         SolidColor var1 = this.mSolidColor;
         this.release();
         return var1;
      }

      public SolidColor.Builder color(@ColorInt int var1) {
         this.mSolidColor.color = var1;
         this.mRequired.set(0);
         return this;
      }

      public SolidColor.Builder colorAttr(@AttrRes int var1) {
         this.mSolidColor.color = this.mResourceResolver.resolveColorAttr(var1, 0);
         this.mRequired.set(0);
         return this;
      }

      public SolidColor.Builder colorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mSolidColor.color = this.mResourceResolver.resolveColorAttr(var1, var2);
         this.mRequired.set(0);
         return this;
      }

      public SolidColor.Builder colorRes(@ColorRes int var1) {
         this.mSolidColor.color = this.mResourceResolver.resolveColorRes(var1);
         this.mRequired.set(0);
         return this;
      }

      public SolidColor.Builder getThis() {
         return this;
      }

      protected void release() {
         super.release();
         this.mSolidColor = null;
         this.mContext = null;
      }
   }
}
