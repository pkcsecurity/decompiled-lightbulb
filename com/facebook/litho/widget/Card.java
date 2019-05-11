package com.facebook.litho.widget;

import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.Px;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.CardSpec;
import java.util.BitSet;

public final class Card extends Component {

   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int cardBackgroundColor = -1;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int clippingColor = -1;
   @Comparable(
      type = 10
   )
   @Prop(
      optional = false,
      resType = ResType.NONE
   )
   Component content;
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_OFFSET
   )
   float cornerRadius = -1.0F;
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
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_OFFSET
   )
   float elevation = -1.0F;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_OFFSET
   )
   int shadowBottomOverride = -1;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int shadowEndColor = 50331648;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int shadowStartColor = 922746880;


   private Card() {
      super("Card");
   }

   public static Card.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static Card.Builder create(ComponentContext var0, int var1, int var2) {
      Card.Builder var3 = new Card.Builder();
      var3.init(var0, var1, var2, new Card());
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
            Card var2 = (Card)var1;
            if(this.getId() == var2.getId()) {
               return true;
            } else if(this.cardBackgroundColor != var2.cardBackgroundColor) {
               return false;
            } else if(this.clippingColor != var2.clippingColor) {
               return false;
            } else {
               if(this.content != null) {
                  if(!this.content.isEquivalentTo(var2.content)) {
                     return false;
                  }
               } else if(var2.content != null) {
                  return false;
               }

               return Float.compare(this.cornerRadius, var2.cornerRadius) != 0?false:(this.disableClipBottomLeft != var2.disableClipBottomLeft?false:(this.disableClipBottomRight != var2.disableClipBottomRight?false:(this.disableClipTopLeft != var2.disableClipTopLeft?false:(this.disableClipTopRight != var2.disableClipTopRight?false:(Float.compare(this.elevation, var2.elevation) != 0?false:(this.shadowBottomOverride != var2.shadowBottomOverride?false:(this.shadowEndColor != var2.shadowEndColor?false:this.shadowStartColor == var2.shadowStartColor)))))));
            }
         }
      } else {
         return false;
      }
   }

   public boolean isPureRender() {
      return true;
   }

   public Card makeShallowCopy() {
      Card var2 = (Card)super.makeShallowCopy();
      Component var1;
      if(var2.content != null) {
         var1 = var2.content.makeShallowCopy();
      } else {
         var1 = null;
      }

      var2.content = var1;
      return var2;
   }

   protected Component onCreateLayout(ComponentContext var1) {
      return CardSpec.onCreateLayout(var1, this.content, this.cardBackgroundColor, this.clippingColor, this.shadowStartColor, this.shadowEndColor, this.cornerRadius, this.elevation, this.shadowBottomOverride, this.disableClipTopLeft, this.disableClipTopRight, this.disableClipBottomLeft, this.disableClipBottomRight);
   }

   public static class Builder extends Component.Builder<Card.Builder> {

      private final int REQUIRED_PROPS_COUNT = 1;
      private final String[] REQUIRED_PROPS_NAMES = new String[]{"content"};
      Card mCard;
      ComponentContext mContext;
      private final BitSet mRequired = new BitSet(1);


      private void init(ComponentContext var1, int var2, int var3, Card var4) {
         super.init(var1, var2, var3, var4);
         this.mCard = var4;
         this.mContext = var1;
         this.mRequired.clear();
      }

      public Card build() {
         checkArgs(1, this.mRequired, this.REQUIRED_PROPS_NAMES);
         Card var1 = this.mCard;
         this.release();
         return var1;
      }

      public Card.Builder cardBackgroundColor(@ColorInt int var1) {
         this.mCard.cardBackgroundColor = var1;
         return this;
      }

      public Card.Builder cardBackgroundColorAttr(@AttrRes int var1) {
         this.mCard.cardBackgroundColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public Card.Builder cardBackgroundColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mCard.cardBackgroundColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public Card.Builder cardBackgroundColorRes(@ColorRes int var1) {
         this.mCard.cardBackgroundColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public Card.Builder clippingColor(@ColorInt int var1) {
         this.mCard.clippingColor = var1;
         return this;
      }

      public Card.Builder clippingColorAttr(@AttrRes int var1) {
         this.mCard.clippingColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public Card.Builder clippingColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mCard.clippingColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public Card.Builder clippingColorRes(@ColorRes int var1) {
         this.mCard.clippingColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public Card.Builder content(Component.Builder<?> var1) {
         Card var2 = this.mCard;
         Component var3;
         if(var1 == null) {
            var3 = null;
         } else {
            var3 = var1.build();
         }

         var2.content = var3;
         this.mRequired.set(0);
         return this;
      }

      public Card.Builder content(Component var1) {
         Card var2 = this.mCard;
         if(var1 == null) {
            var1 = null;
         } else {
            var1 = var1.makeShallowCopy();
         }

         var2.content = var1;
         this.mRequired.set(0);
         return this;
      }

      public Card.Builder cornerRadiusAttr(@AttrRes int var1) {
         this.mCard.cornerRadius = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public Card.Builder cornerRadiusAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mCard.cornerRadius = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public Card.Builder cornerRadiusDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mCard.cornerRadius = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public Card.Builder cornerRadiusPx(@Px float var1) {
         this.mCard.cornerRadius = var1;
         return this;
      }

      public Card.Builder cornerRadiusRes(@DimenRes int var1) {
         this.mCard.cornerRadius = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public Card.Builder cornerRadiusSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mCard.cornerRadius = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public Card.Builder disableClipBottomLeft(boolean var1) {
         this.mCard.disableClipBottomLeft = var1;
         return this;
      }

      public Card.Builder disableClipBottomRight(boolean var1) {
         this.mCard.disableClipBottomRight = var1;
         return this;
      }

      public Card.Builder disableClipTopLeft(boolean var1) {
         this.mCard.disableClipTopLeft = var1;
         return this;
      }

      public Card.Builder disableClipTopRight(boolean var1) {
         this.mCard.disableClipTopRight = var1;
         return this;
      }

      public Card.Builder elevationAttr(@AttrRes int var1) {
         this.mCard.elevation = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public Card.Builder elevationAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mCard.elevation = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public Card.Builder elevationDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mCard.elevation = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public Card.Builder elevationPx(@Px float var1) {
         this.mCard.elevation = var1;
         return this;
      }

      public Card.Builder elevationRes(@DimenRes int var1) {
         this.mCard.elevation = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public Card.Builder elevationSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mCard.elevation = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public Card.Builder getThis() {
         return this;
      }

      protected void release() {
         super.release();
         this.mCard = null;
         this.mContext = null;
      }

      public Card.Builder shadowBottomOverrideAttr(@AttrRes int var1) {
         this.mCard.shadowBottomOverride = this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public Card.Builder shadowBottomOverrideAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mCard.shadowBottomOverride = this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public Card.Builder shadowBottomOverrideDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mCard.shadowBottomOverride = this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public Card.Builder shadowBottomOverridePx(@Px int var1) {
         this.mCard.shadowBottomOverride = var1;
         return this;
      }

      public Card.Builder shadowBottomOverrideRes(@DimenRes int var1) {
         this.mCard.shadowBottomOverride = this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public Card.Builder shadowBottomOverrideSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mCard.shadowBottomOverride = this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public Card.Builder shadowEndColor(@ColorInt int var1) {
         this.mCard.shadowEndColor = var1;
         return this;
      }

      public Card.Builder shadowEndColorAttr(@AttrRes int var1) {
         this.mCard.shadowEndColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public Card.Builder shadowEndColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mCard.shadowEndColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public Card.Builder shadowEndColorRes(@ColorRes int var1) {
         this.mCard.shadowEndColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public Card.Builder shadowStartColor(@ColorInt int var1) {
         this.mCard.shadowStartColor = var1;
         return this;
      }

      public Card.Builder shadowStartColorAttr(@AttrRes int var1) {
         this.mCard.shadowStartColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public Card.Builder shadowStartColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mCard.shadowStartColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public Card.Builder shadowStartColorRes(@ColorRes int var1) {
         this.mCard.shadowStartColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }
   }
}
