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
import com.facebook.litho.widget.CardClipDrawable;
import com.facebook.litho.widget.CardClipSpec;

public final class CardClip extends Component {

   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int clippingColor = -1;
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
   boolean disableClipBottomLeft;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean disableClipBottomRight;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean disableClipTopLeft;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean disableClipTopRight;


   private CardClip() {
      super("CardClip");
   }

   public static CardClip.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static CardClip.Builder create(ComponentContext var0, int var1, int var2) {
      CardClip.Builder var3 = new CardClip.Builder();
      var3.init(var0, var1, var2, new CardClip());
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
            CardClip var2 = (CardClip)var1;
            return this.getId() == var2.getId()?true:(this.clippingColor != var2.clippingColor?false:(Float.compare(this.cornerRadius, var2.cornerRadius) != 0?false:(this.disableClipBottomLeft != var2.disableClipBottomLeft?false:(this.disableClipBottomRight != var2.disableClipBottomRight?false:(this.disableClipTopLeft != var2.disableClipTopLeft?false:this.disableClipTopRight == var2.disableClipTopRight)))));
         }
      } else {
         return false;
      }
   }

   public boolean isPureRender() {
      return true;
   }

   protected Object onCreateMountContent(Context var1) {
      return CardClipSpec.onCreateMountContent(var1);
   }

   protected void onMount(ComponentContext var1, Object var2) {
      CardClipSpec.onMount(var1, (CardClipDrawable)var2, this.clippingColor, this.cornerRadius, this.disableClipTopLeft, this.disableClipTopRight, this.disableClipBottomLeft, this.disableClipBottomRight);
   }

   protected void onUnmount(ComponentContext var1, Object var2) {
      CardClipSpec.onUnmount(var1, (CardClipDrawable)var2);
   }

   protected int poolSize() {
      return 3;
   }

   public static class Builder extends Component.Builder<CardClip.Builder> {

      CardClip mCardClip;
      ComponentContext mContext;


      private void init(ComponentContext var1, int var2, int var3, CardClip var4) {
         super.init(var1, var2, var3, var4);
         this.mCardClip = var4;
         this.mContext = var1;
      }

      public CardClip build() {
         CardClip var1 = this.mCardClip;
         this.release();
         return var1;
      }

      public CardClip.Builder clippingColor(@ColorInt int var1) {
         this.mCardClip.clippingColor = var1;
         return this;
      }

      public CardClip.Builder clippingColorAttr(@AttrRes int var1) {
         this.mCardClip.clippingColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public CardClip.Builder clippingColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mCardClip.clippingColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public CardClip.Builder clippingColorRes(@ColorRes int var1) {
         this.mCardClip.clippingColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public CardClip.Builder cornerRadiusAttr(@AttrRes int var1) {
         this.mCardClip.cornerRadius = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public CardClip.Builder cornerRadiusAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mCardClip.cornerRadius = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public CardClip.Builder cornerRadiusDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mCardClip.cornerRadius = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public CardClip.Builder cornerRadiusPx(@Px float var1) {
         this.mCardClip.cornerRadius = var1;
         return this;
      }

      public CardClip.Builder cornerRadiusRes(@DimenRes int var1) {
         this.mCardClip.cornerRadius = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public CardClip.Builder cornerRadiusSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mCardClip.cornerRadius = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public CardClip.Builder disableClipBottomLeft(boolean var1) {
         this.mCardClip.disableClipBottomLeft = var1;
         return this;
      }

      public CardClip.Builder disableClipBottomRight(boolean var1) {
         this.mCardClip.disableClipBottomRight = var1;
         return this;
      }

      public CardClip.Builder disableClipTopLeft(boolean var1) {
         this.mCardClip.disableClipTopLeft = var1;
         return this;
      }

      public CardClip.Builder disableClipTopRight(boolean var1) {
         this.mCardClip.disableClipTopRight = var1;
         return this;
      }

      public CardClip.Builder getThis() {
         return this;
      }

      protected void release() {
         super.release();
         this.mCardClip = null;
         this.mContext = null;
      }
   }
}
