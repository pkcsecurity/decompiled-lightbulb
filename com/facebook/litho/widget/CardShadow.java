package com.facebook.litho.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.Px;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.CardShadowDrawable;
import com.facebook.litho.widget.CardShadowSpec;

public final class CardShadow extends Component {

   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_OFFSET
   )
   float cornerRadius;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean hideBottomShadow;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean hideTopShadow;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int shadowEndColor;
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_SIZE
   )
   float shadowSize;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int shadowStartColor;


   private CardShadow() {
      super("CardShadow");
   }

   public static CardShadow.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static CardShadow.Builder create(ComponentContext var0, int var1, int var2) {
      CardShadow.Builder var3 = new CardShadow.Builder();
      var3.init(var0, var1, var2, new CardShadow());
      return var3;
   }

   protected boolean canPreallocate() {
      return false;
   }

   public ComponentLifecycle.MountType getMountType() {
      return ComponentLifecycle.MountType.DRAWABLE;
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
            CardShadow var2 = (CardShadow)var1;
            return this.getId() == var2.getId()?true:(Float.compare(this.cornerRadius, var2.cornerRadius) != 0?false:(this.hideBottomShadow != var2.hideBottomShadow?false:(this.hideTopShadow != var2.hideTopShadow?false:(this.shadowEndColor != var2.shadowEndColor?false:(Float.compare(this.shadowSize, var2.shadowSize) != 0?false:this.shadowStartColor == var2.shadowStartColor)))));
         }
      } else {
         return false;
      }
   }

   public boolean isPureRender() {
      return true;
   }

   protected Object onCreateMountContent(Context var1) {
      return CardShadowSpec.onCreateMountContent(var1);
   }

   protected void onMount(ComponentContext var1, Object var2) {
      CardShadowSpec.onMount(var1, (CardShadowDrawable)var2, this.shadowStartColor, this.shadowEndColor, this.cornerRadius, this.shadowSize, this.hideTopShadow, this.hideBottomShadow);
   }

   protected int poolSize() {
      return 3;
   }

   public static class Builder extends Component.Builder<CardShadow.Builder> {

      CardShadow mCardShadow;
      ComponentContext mContext;


      private void init(ComponentContext var1, int var2, int var3, CardShadow var4) {
         super.init(var1, var2, var3, var4);
         this.mCardShadow = var4;
         this.mContext = var1;
      }

      public CardShadow build() {
         CardShadow var1 = this.mCardShadow;
         this.release();
         return var1;
      }

      public CardShadow.Builder cornerRadiusAttr(@AttrRes int var1) {
         this.mCardShadow.cornerRadius = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public CardShadow.Builder cornerRadiusAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mCardShadow.cornerRadius = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public CardShadow.Builder cornerRadiusDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mCardShadow.cornerRadius = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public CardShadow.Builder cornerRadiusPx(@Px float var1) {
         this.mCardShadow.cornerRadius = var1;
         return this;
      }

      public CardShadow.Builder cornerRadiusRes(@DimenRes int var1) {
         this.mCardShadow.cornerRadius = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public CardShadow.Builder cornerRadiusSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mCardShadow.cornerRadius = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public CardShadow.Builder getThis() {
         return this;
      }

      public CardShadow.Builder hideBottomShadow(boolean var1) {
         this.mCardShadow.hideBottomShadow = var1;
         return this;
      }

      public CardShadow.Builder hideTopShadow(boolean var1) {
         this.mCardShadow.hideTopShadow = var1;
         return this;
      }

      protected void release() {
         super.release();
         this.mCardShadow = null;
         this.mContext = null;
      }

      public CardShadow.Builder shadowEndColor(@ColorInt int var1) {
         this.mCardShadow.shadowEndColor = var1;
         return this;
      }

      public CardShadow.Builder shadowEndColorAttr(@AttrRes int var1) {
         this.mCardShadow.shadowEndColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public CardShadow.Builder shadowEndColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mCardShadow.shadowEndColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public CardShadow.Builder shadowEndColorRes(@ColorRes int var1) {
         this.mCardShadow.shadowEndColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public CardShadow.Builder shadowSizeAttr(@AttrRes int var1) {
         this.mCardShadow.shadowSize = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public CardShadow.Builder shadowSizeAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mCardShadow.shadowSize = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public CardShadow.Builder shadowSizeDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mCardShadow.shadowSize = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public CardShadow.Builder shadowSizePx(@Px float var1) {
         this.mCardShadow.shadowSize = var1;
         return this;
      }

      public CardShadow.Builder shadowSizeRes(@DimenRes int var1) {
         this.mCardShadow.shadowSize = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public CardShadow.Builder shadowStartColor(@ColorInt int var1) {
         this.mCardShadow.shadowStartColor = var1;
         return this;
      }

      public CardShadow.Builder shadowStartColorAttr(@AttrRes int var1) {
         this.mCardShadow.shadowStartColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public CardShadow.Builder shadowStartColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mCardShadow.shadowStartColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public CardShadow.Builder shadowStartColorRes(@ColorRes int var1) {
         this.mCardShadow.shadowStartColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }
   }
}
