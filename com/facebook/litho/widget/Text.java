package com.facebook.litho.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.support.annotation.AttrRes;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.annotation.StringRes;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.util.Pools;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.TextUtils.TruncateAt;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.EventHandler;
import com.facebook.litho.Output;
import com.facebook.litho.Size;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.ClickableSpanListener;
import com.facebook.litho.widget.CustomEllipsisTruncationEvent;
import com.facebook.litho.widget.TextDrawable;
import com.facebook.litho.widget.TextOffsetOnTouchEvent;
import com.facebook.litho.widget.TextSpec;
import com.facebook.litho.widget.VerticalGravity;
import java.util.BitSet;

public final class Text extends Component {

   static final Pools.SynchronizedPool<CustomEllipsisTruncationEvent> sCustomEllipsisTruncationEventPool = new Pools.SynchronizedPool(2);
   static final Pools.SynchronizedPool<TextOffsetOnTouchEvent> sTextOffsetOnTouchEventPool = new Pools.SynchronizedPool(2);
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.BOOL
   )
   boolean accessibleClickableSpans;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int breakStrategy = 0;
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_TEXT
   )
   float clickableSpanExpandedOffset;
   ClickableSpan[] clickableSpans;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean clipToBounds = true;
   @Comparable(
      type = 11
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   EventHandler customEllipsisHandler;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.STRING
   )
   CharSequence customEllipsisText;
   EventHandler customEllipsisTruncationEventHandler;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   TruncateAt ellipsize;
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_OFFSET
   )
   float extraSpacing;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean glyphWarming = false;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int highlightColor;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int highlightEndOffset = -1;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int highlightStartOffset = -1;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int hyphenationFrequency = 0;
   ImageSpan[] imageSpans;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.BOOL
   )
   boolean isSingleLine;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int justificationMode = 0;
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.FLOAT
   )
   float letterSpacing;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int linkColor = 0;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.INT
   )
   int maxEms = -1;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.INT
   )
   int maxLines = Integer.MAX_VALUE;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_SIZE
   )
   int maxTextWidth = Integer.MAX_VALUE;
   Layout measureLayout;
   Integer measuredHeight;
   Integer measuredWidth;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.INT
   )
   int minEms = -1;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.INT
   )
   int minLines = Integer.MIN_VALUE;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_SIZE
   )
   int minTextWidth = 0;
   CharSequence processedText;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int shadowColor = -7829368;
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_OFFSET
   )
   float shadowDx;
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_OFFSET
   )
   float shadowDy;
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_OFFSET
   )
   float shadowRadius;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.BOOL
   )
   boolean shouldIncludeFontPadding = true;
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.FLOAT
   )
   float spacingMultiplier = 1.0F;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   ClickableSpanListener spanListener;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = false,
      resType = ResType.STRING
   )
   @Nullable
   CharSequence text;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   Alignment textAlignment;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int textColor;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   ColorStateList textColorStateList;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   TextDirectionHeuristicCompat textDirection;
   Layout textLayout;
   Float textLayoutTranslationY;
   EventHandler textOffsetOnTouchEventHandler;
   @Comparable(
      type = 11
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   EventHandler textOffsetOnTouchHandler;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_TEXT
   )
   int textSize;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int textStyle;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   @Nullable
   Typeface typeface;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   VerticalGravity verticalGravity;


   private Text() {
      super("Text");
      this.textAlignment = TextSpec.textAlignment;
      this.textColor = 0;
      this.textColorStateList = TextSpec.textColorStateList;
      this.textSize = 13;
      this.textStyle = TextSpec.textStyle;
      this.typeface = TextSpec.typeface;
      this.verticalGravity = TextSpec.verticalGravity;
   }

   public static Text.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static Text.Builder create(ComponentContext var0, int var1, int var2) {
      Text.Builder var3 = new Text.Builder();
      var3.init(var0, var1, var2, new Text());
      return var3;
   }

   static void dispatchCustomEllipsisTruncationEvent(EventHandler var0) {
      CustomEllipsisTruncationEvent var2 = (CustomEllipsisTruncationEvent)sCustomEllipsisTruncationEventPool.acquire();
      CustomEllipsisTruncationEvent var1 = var2;
      if(var2 == null) {
         var1 = new CustomEllipsisTruncationEvent();
      }

      var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, var1);
      sCustomEllipsisTruncationEventPool.release(var1);
   }

   static void dispatchTextOffsetOnTouchEvent(EventHandler var0, CharSequence var1, int var2) {
      TextOffsetOnTouchEvent var4 = (TextOffsetOnTouchEvent)sTextOffsetOnTouchEventPool.acquire();
      TextOffsetOnTouchEvent var3 = var4;
      if(var4 == null) {
         var3 = new TextOffsetOnTouchEvent();
      }

      var3.text = var1;
      var3.textOffset = var2;
      var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, var3);
      var3.text = null;
      sTextOffsetOnTouchEventPool.release(var3);
   }

   public static EventHandler getCustomEllipsisTruncationEventHandler(ComponentContext var0) {
      return var0.getComponentScope() == null?null:((Text)var0.getComponentScope()).customEllipsisTruncationEventHandler;
   }

   public static EventHandler getTextOffsetOnTouchEventHandler(ComponentContext var0) {
      return var0.getComponentScope() == null?null:((Text)var0.getComponentScope()).textOffsetOnTouchEventHandler;
   }

   protected boolean canMeasure() {
      return true;
   }

   protected boolean canPreallocate() {
      return false;
   }

   protected void copyInterStageImpl(Component var1) {
      Text var2 = (Text)var1;
      this.clickableSpans = var2.clickableSpans;
      this.imageSpans = var2.imageSpans;
      this.measureLayout = var2.measureLayout;
      this.measuredHeight = var2.measuredHeight;
      this.measuredWidth = var2.measuredWidth;
      this.processedText = var2.processedText;
      this.textLayout = var2.textLayout;
      this.textLayoutTranslationY = var2.textLayoutTranslationY;
   }

   protected int getExtraAccessibilityNodeAt(int var1, int var2) {
      return TextSpec.getExtraAccessibilityNodeAt(var1, var2, this.text, this.textLayout, (ClickableSpan[])this.clickableSpans);
   }

   protected int getExtraAccessibilityNodesCount() {
      return TextSpec.getExtraAccessibilityNodesCount(this.accessibleClickableSpans, (ClickableSpan[])this.clickableSpans);
   }

   public ComponentLifecycle.MountType getMountType() {
      return ComponentLifecycle.MountType.DRAWABLE;
   }

   public boolean implementsAccessibility() {
      return true;
   }

   public boolean implementsExtraAccessibilityNodes() {
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
            Text var2 = (Text)var1;
            if(this.getId() == var2.getId()) {
               return true;
            } else if(this.accessibleClickableSpans != var2.accessibleClickableSpans) {
               return false;
            } else if(this.breakStrategy != var2.breakStrategy) {
               return false;
            } else if(Float.compare(this.clickableSpanExpandedOffset, var2.clickableSpanExpandedOffset) != 0) {
               return false;
            } else if(this.clipToBounds != var2.clipToBounds) {
               return false;
            } else {
               if(this.customEllipsisHandler != null) {
                  if(!this.customEllipsisHandler.isEquivalentTo(var2.customEllipsisHandler)) {
                     return false;
                  }
               } else if(var2.customEllipsisHandler != null) {
                  return false;
               }

               if(this.customEllipsisText != null) {
                  if(!this.customEllipsisText.equals(var2.customEllipsisText)) {
                     return false;
                  }
               } else if(var2.customEllipsisText != null) {
                  return false;
               }

               if(this.ellipsize != null) {
                  if(!this.ellipsize.equals(var2.ellipsize)) {
                     return false;
                  }
               } else if(var2.ellipsize != null) {
                  return false;
               }

               if(Float.compare(this.extraSpacing, var2.extraSpacing) != 0) {
                  return false;
               } else if(this.glyphWarming != var2.glyphWarming) {
                  return false;
               } else if(this.highlightColor != var2.highlightColor) {
                  return false;
               } else if(this.highlightEndOffset != var2.highlightEndOffset) {
                  return false;
               } else if(this.highlightStartOffset != var2.highlightStartOffset) {
                  return false;
               } else if(this.hyphenationFrequency != var2.hyphenationFrequency) {
                  return false;
               } else if(this.isSingleLine != var2.isSingleLine) {
                  return false;
               } else if(this.justificationMode != var2.justificationMode) {
                  return false;
               } else if(Float.compare(this.letterSpacing, var2.letterSpacing) != 0) {
                  return false;
               } else if(this.linkColor != var2.linkColor) {
                  return false;
               } else if(this.maxEms != var2.maxEms) {
                  return false;
               } else if(this.maxLines != var2.maxLines) {
                  return false;
               } else if(this.maxTextWidth != var2.maxTextWidth) {
                  return false;
               } else if(this.minEms != var2.minEms) {
                  return false;
               } else if(this.minLines != var2.minLines) {
                  return false;
               } else if(this.minTextWidth != var2.minTextWidth) {
                  return false;
               } else if(this.shadowColor != var2.shadowColor) {
                  return false;
               } else if(Float.compare(this.shadowDx, var2.shadowDx) != 0) {
                  return false;
               } else if(Float.compare(this.shadowDy, var2.shadowDy) != 0) {
                  return false;
               } else if(Float.compare(this.shadowRadius, var2.shadowRadius) != 0) {
                  return false;
               } else if(this.shouldIncludeFontPadding != var2.shouldIncludeFontPadding) {
                  return false;
               } else if(Float.compare(this.spacingMultiplier, var2.spacingMultiplier) != 0) {
                  return false;
               } else {
                  if(this.spanListener != null) {
                     if(!this.spanListener.equals(var2.spanListener)) {
                        return false;
                     }
                  } else if(var2.spanListener != null) {
                     return false;
                  }

                  if(this.text != null) {
                     if(!this.text.equals(var2.text)) {
                        return false;
                     }
                  } else if(var2.text != null) {
                     return false;
                  }

                  if(this.textAlignment != null) {
                     if(!this.textAlignment.equals(var2.textAlignment)) {
                        return false;
                     }
                  } else if(var2.textAlignment != null) {
                     return false;
                  }

                  if(this.textColor != var2.textColor) {
                     return false;
                  } else {
                     if(this.textColorStateList != null) {
                        if(!this.textColorStateList.equals(var2.textColorStateList)) {
                           return false;
                        }
                     } else if(var2.textColorStateList != null) {
                        return false;
                     }

                     if(this.textDirection != null) {
                        if(!this.textDirection.equals(var2.textDirection)) {
                           return false;
                        }
                     } else if(var2.textDirection != null) {
                        return false;
                     }

                     if(this.textOffsetOnTouchHandler != null) {
                        if(!this.textOffsetOnTouchHandler.isEquivalentTo(var2.textOffsetOnTouchHandler)) {
                           return false;
                        }
                     } else if(var2.textOffsetOnTouchHandler != null) {
                        return false;
                     }

                     if(this.textSize != var2.textSize) {
                        return false;
                     } else if(this.textStyle != var2.textStyle) {
                        return false;
                     } else {
                        if(this.typeface != null) {
                           if(!this.typeface.equals(var2.typeface)) {
                              return false;
                           }
                        } else if(var2.typeface != null) {
                           return false;
                        }

                        if(this.verticalGravity != null) {
                           if(!this.verticalGravity.equals(var2.verticalGravity)) {
                              return false;
                           }
                        } else if(var2.verticalGravity != null) {
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

   protected boolean isMountSizeDependent() {
      return true;
   }

   public boolean isPureRender() {
      return true;
   }

   public Text makeShallowCopy() {
      Text var1 = (Text)super.makeShallowCopy();
      var1.clickableSpans = null;
      var1.imageSpans = null;
      var1.measureLayout = null;
      var1.measuredHeight = null;
      var1.measuredWidth = null;
      var1.processedText = null;
      var1.textLayout = null;
      var1.textLayoutTranslationY = null;
      return var1;
   }

   protected void onBoundsDefined(ComponentContext var1, ComponentLayout var2) {
      Output var3 = this.acquireOutput();
      Output var4 = this.acquireOutput();
      Output var5 = this.acquireOutput();
      Output var6 = this.acquireOutput();
      Output var7 = this.acquireOutput();
      TextSpec.onBoundsDefined(var1, var2, this.text, this.ellipsize, this.shouldIncludeFontPadding, this.maxLines, this.minEms, this.maxEms, this.minTextWidth, this.maxTextWidth, this.shadowRadius, this.shadowDx, this.shadowDy, this.shadowColor, this.isSingleLine, this.textColor, this.textColorStateList, this.linkColor, this.textSize, this.extraSpacing, this.spacingMultiplier, this.letterSpacing, this.verticalGravity, this.textStyle, this.typeface, this.textAlignment, this.breakStrategy, this.hyphenationFrequency, this.glyphWarming, this.textDirection, this.customEllipsisText, this.customEllipsisHandler, this.measureLayout, this.measuredWidth, this.measuredHeight, var3, var4, var5, var6, var7);
      this.processedText = (CharSequence)var3.get();
      this.releaseOutput(var3);
      this.textLayout = (Layout)var4.get();
      this.releaseOutput(var4);
      this.textLayoutTranslationY = (Float)var5.get();
      this.releaseOutput(var5);
      this.clickableSpans = (ClickableSpan[])var6.get();
      this.releaseOutput(var6);
      this.imageSpans = (ImageSpan[])var7.get();
      this.releaseOutput(var7);
   }

   protected Object onCreateMountContent(Context var1) {
      return TextSpec.onCreateMountContent(var1);
   }

   protected void onLoadStyle(ComponentContext var1) {
      Output var2 = this.acquireOutput();
      Output var3 = this.acquireOutput();
      Output var4 = this.acquireOutput();
      Output var5 = this.acquireOutput();
      Output var6 = this.acquireOutput();
      Output var7 = this.acquireOutput();
      Output var8 = this.acquireOutput();
      Output var9 = this.acquireOutput();
      Output var10 = this.acquireOutput();
      Output var11 = this.acquireOutput();
      Output var12 = this.acquireOutput();
      Output var13 = this.acquireOutput();
      Output var14 = this.acquireOutput();
      Output var15 = this.acquireOutput();
      Output var16 = this.acquireOutput();
      Output var17 = this.acquireOutput();
      Output var18 = this.acquireOutput();
      Output var19 = this.acquireOutput();
      Output var20 = this.acquireOutput();
      Output var21 = this.acquireOutput();
      Output var22 = this.acquireOutput();
      Output var23 = this.acquireOutput();
      Output var24 = this.acquireOutput();
      Output var25 = this.acquireOutput();
      Output var26 = this.acquireOutput();
      Output var27 = this.acquireOutput();
      Output var28 = this.acquireOutput();
      TextSpec.onLoadStyle(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20, var21, var22, var23, var24, var25, var26, var27, var28);
      if(var2.get() != null) {
         this.ellipsize = (TruncateAt)var2.get();
      }

      this.releaseOutput(var2);
      if(var3.get() != null) {
         this.extraSpacing = ((Float)var3.get()).floatValue();
      }

      this.releaseOutput(var3);
      if(var4.get() != null) {
         this.shouldIncludeFontPadding = ((Boolean)var4.get()).booleanValue();
      }

      this.releaseOutput(var4);
      if(var5.get() != null) {
         this.spacingMultiplier = ((Float)var5.get()).floatValue();
      }

      this.releaseOutput(var5);
      if(var6.get() != null) {
         this.minLines = ((Integer)var6.get()).intValue();
      }

      this.releaseOutput(var6);
      if(var7.get() != null) {
         this.maxLines = ((Integer)var7.get()).intValue();
      }

      this.releaseOutput(var7);
      if(var8.get() != null) {
         this.minEms = ((Integer)var8.get()).intValue();
      }

      this.releaseOutput(var8);
      if(var9.get() != null) {
         this.maxEms = ((Integer)var9.get()).intValue();
      }

      this.releaseOutput(var9);
      if(var10.get() != null) {
         this.minTextWidth = ((Integer)var10.get()).intValue();
      }

      this.releaseOutput(var10);
      if(var11.get() != null) {
         this.maxTextWidth = ((Integer)var11.get()).intValue();
      }

      this.releaseOutput(var11);
      if(var12.get() != null) {
         this.isSingleLine = ((Boolean)var12.get()).booleanValue();
      }

      this.releaseOutput(var12);
      if(var13.get() != null) {
         this.text = (CharSequence)var13.get();
      }

      this.releaseOutput(var13);
      if(var14.get() != null) {
         this.textColorStateList = (ColorStateList)var14.get();
      }

      this.releaseOutput(var14);
      if(var15.get() != null) {
         this.linkColor = ((Integer)var15.get()).intValue();
      }

      this.releaseOutput(var15);
      if(var16.get() != null) {
         this.highlightColor = ((Integer)var16.get()).intValue();
      }

      this.releaseOutput(var16);
      if(var17.get() != null) {
         this.textSize = ((Integer)var17.get()).intValue();
      }

      this.releaseOutput(var17);
      if(var18.get() != null) {
         this.textAlignment = (Alignment)var18.get();
      }

      this.releaseOutput(var18);
      if(var19.get() != null) {
         this.breakStrategy = ((Integer)var19.get()).intValue();
      }

      this.releaseOutput(var19);
      if(var20.get() != null) {
         this.hyphenationFrequency = ((Integer)var20.get()).intValue();
      }

      this.releaseOutput(var20);
      if(var21.get() != null) {
         this.justificationMode = ((Integer)var21.get()).intValue();
      }

      this.releaseOutput(var21);
      if(var22.get() != null) {
         this.textStyle = ((Integer)var22.get()).intValue();
      }

      this.releaseOutput(var22);
      if(var23.get() != null) {
         this.shadowRadius = ((Float)var23.get()).floatValue();
      }

      this.releaseOutput(var23);
      if(var24.get() != null) {
         this.shadowDx = ((Float)var24.get()).floatValue();
      }

      this.releaseOutput(var24);
      if(var25.get() != null) {
         this.shadowDy = ((Float)var25.get()).floatValue();
      }

      this.releaseOutput(var25);
      if(var26.get() != null) {
         this.shadowColor = ((Integer)var26.get()).intValue();
      }

      this.releaseOutput(var26);
      if(var27.get() != null) {
         this.verticalGravity = (VerticalGravity)var27.get();
      }

      this.releaseOutput(var27);
      if(var28.get() != null) {
         this.typeface = (Typeface)var28.get();
      }

      this.releaseOutput(var28);
   }

   protected void onMeasure(ComponentContext var1, ComponentLayout var2, int var3, int var4, Size var5) {
      Output var6 = this.acquireOutput();
      Output var7 = this.acquireOutput();
      Output var8 = this.acquireOutput();
      TextSpec.onMeasure(var1, var2, var3, var4, var5, this.text, this.ellipsize, this.shouldIncludeFontPadding, this.minLines, this.maxLines, this.minEms, this.maxEms, this.minTextWidth, this.maxTextWidth, this.shadowRadius, this.shadowDx, this.shadowDy, this.shadowColor, this.isSingleLine, this.textColor, this.textColorStateList, this.linkColor, this.textSize, this.extraSpacing, this.spacingMultiplier, this.letterSpacing, this.textStyle, this.typeface, this.textAlignment, this.breakStrategy, this.hyphenationFrequency, this.justificationMode, this.glyphWarming, this.textDirection, var6, var7, var8);
      this.measureLayout = (Layout)var6.get();
      this.releaseOutput(var6);
      this.measuredWidth = (Integer)var7.get();
      this.releaseOutput(var7);
      this.measuredHeight = (Integer)var8.get();
      this.releaseOutput(var8);
   }

   protected void onMount(ComponentContext var1, Object var2) {
      TextSpec.onMount(var1, (TextDrawable)var2, this.textColor, this.highlightColor, this.textColorStateList, this.textOffsetOnTouchHandler, this.highlightStartOffset, this.highlightEndOffset, this.clickableSpanExpandedOffset, this.clipToBounds, this.spanListener, this.processedText, this.textLayout, this.textLayoutTranslationY, (ClickableSpan[])this.clickableSpans, (ImageSpan[])this.imageSpans);
   }

   protected void onPopulateAccessibilityNode(View var1, AccessibilityNodeInfoCompat var2) {
      TextSpec.onPopulateAccessibilityNode(var1, var2, this.text, this.isSingleLine);
   }

   protected void onPopulateExtraAccessibilityNode(AccessibilityNodeInfoCompat var1, int var2, int var3, int var4) {
      TextSpec.onPopulateExtraAccessibilityNode(var1, var2, var3, var4, this.text, this.textLayout, (ClickableSpan[])this.clickableSpans);
   }

   protected void onUnmount(ComponentContext var1, Object var2) {
      TextSpec.onUnmount(var1, (TextDrawable)var2, this.text);
   }

   protected int poolSize() {
      return 30;
   }

   public boolean shouldUseDisplayList() {
      return true;
   }

   public static class Builder extends Component.Builder<Text.Builder> {

      private final int REQUIRED_PROPS_COUNT = 1;
      private final String[] REQUIRED_PROPS_NAMES = new String[]{"text"};
      ComponentContext mContext;
      private final BitSet mRequired = new BitSet(1);
      Text mText;


      private void init(ComponentContext var1, int var2, int var3, Text var4) {
         super.init(var1, var2, var3, var4);
         this.mText = var4;
         this.mContext = var1;
         this.mRequired.clear();
      }

      public Text.Builder accessibleClickableSpans(boolean var1) {
         this.mText.accessibleClickableSpans = var1;
         return this;
      }

      public Text.Builder accessibleClickableSpansAttr(@AttrRes int var1) {
         this.mText.accessibleClickableSpans = this.mResourceResolver.resolveBoolAttr(var1, 0);
         return this;
      }

      public Text.Builder accessibleClickableSpansAttr(@AttrRes int var1, @BoolRes int var2) {
         this.mText.accessibleClickableSpans = this.mResourceResolver.resolveBoolAttr(var1, var2);
         return this;
      }

      public Text.Builder accessibleClickableSpansRes(@BoolRes int var1) {
         this.mText.accessibleClickableSpans = this.mResourceResolver.resolveBoolRes(var1);
         return this;
      }

      public Text.Builder breakStrategy(int var1) {
         this.mText.breakStrategy = var1;
         return this;
      }

      public Text build() {
         checkArgs(1, this.mRequired, this.REQUIRED_PROPS_NAMES);
         Text var1 = this.mText;
         this.release();
         return var1;
      }

      public Text.Builder clickableSpanExpandedOffsetAttr(@AttrRes int var1) {
         this.mText.clickableSpanExpandedOffset = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public Text.Builder clickableSpanExpandedOffsetAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mText.clickableSpanExpandedOffset = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public Text.Builder clickableSpanExpandedOffsetDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mText.clickableSpanExpandedOffset = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public Text.Builder clickableSpanExpandedOffsetPx(@Px float var1) {
         this.mText.clickableSpanExpandedOffset = var1;
         return this;
      }

      public Text.Builder clickableSpanExpandedOffsetRes(@DimenRes int var1) {
         this.mText.clickableSpanExpandedOffset = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public Text.Builder clickableSpanExpandedOffsetSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mText.clickableSpanExpandedOffset = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public Text.Builder clipToBounds(boolean var1) {
         this.mText.clipToBounds = var1;
         return this;
      }

      public Text.Builder customEllipsisHandler(EventHandler var1) {
         this.mText.customEllipsisHandler = var1;
         return this;
      }

      public Text.Builder customEllipsisText(CharSequence var1) {
         this.mText.customEllipsisText = var1;
         return this;
      }

      public Text.Builder customEllipsisTextAttr(@AttrRes int var1) {
         this.mText.customEllipsisText = this.mResourceResolver.resolveStringAttr(var1, 0);
         return this;
      }

      public Text.Builder customEllipsisTextAttr(@AttrRes int var1, @StringRes int var2) {
         this.mText.customEllipsisText = this.mResourceResolver.resolveStringAttr(var1, var2);
         return this;
      }

      public Text.Builder customEllipsisTextRes(@StringRes int var1) {
         this.mText.customEllipsisText = this.mResourceResolver.resolveStringRes(var1);
         return this;
      }

      public Text.Builder customEllipsisTextRes(@StringRes int var1, Object ... var2) {
         this.mText.customEllipsisText = this.mResourceResolver.resolveStringRes(var1, var2);
         return this;
      }

      public Text.Builder customEllipsisTruncationEventHandler(EventHandler var1) {
         this.mText.customEllipsisTruncationEventHandler = var1;
         return this;
      }

      public Text.Builder ellipsize(TruncateAt var1) {
         this.mText.ellipsize = var1;
         return this;
      }

      public Text.Builder extraSpacingAttr(@AttrRes int var1) {
         this.mText.extraSpacing = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public Text.Builder extraSpacingAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mText.extraSpacing = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public Text.Builder extraSpacingDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mText.extraSpacing = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public Text.Builder extraSpacingPx(@Px float var1) {
         this.mText.extraSpacing = var1;
         return this;
      }

      public Text.Builder extraSpacingRes(@DimenRes int var1) {
         this.mText.extraSpacing = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public Text.Builder extraSpacingSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mText.extraSpacing = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public Text.Builder getThis() {
         return this;
      }

      public Text.Builder glyphWarming(boolean var1) {
         this.mText.glyphWarming = var1;
         return this;
      }

      public Text.Builder highlightColor(@ColorInt int var1) {
         this.mText.highlightColor = var1;
         return this;
      }

      public Text.Builder highlightColorAttr(@AttrRes int var1) {
         this.mText.highlightColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public Text.Builder highlightColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mText.highlightColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public Text.Builder highlightColorRes(@ColorRes int var1) {
         this.mText.highlightColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public Text.Builder highlightEndOffset(int var1) {
         this.mText.highlightEndOffset = var1;
         return this;
      }

      public Text.Builder highlightStartOffset(int var1) {
         this.mText.highlightStartOffset = var1;
         return this;
      }

      public Text.Builder hyphenationFrequency(int var1) {
         this.mText.hyphenationFrequency = var1;
         return this;
      }

      public Text.Builder isSingleLine(boolean var1) {
         this.mText.isSingleLine = var1;
         return this;
      }

      public Text.Builder isSingleLineAttr(@AttrRes int var1) {
         this.mText.isSingleLine = this.mResourceResolver.resolveBoolAttr(var1, 0);
         return this;
      }

      public Text.Builder isSingleLineAttr(@AttrRes int var1, @BoolRes int var2) {
         this.mText.isSingleLine = this.mResourceResolver.resolveBoolAttr(var1, var2);
         return this;
      }

      public Text.Builder isSingleLineRes(@BoolRes int var1) {
         this.mText.isSingleLine = this.mResourceResolver.resolveBoolRes(var1);
         return this;
      }

      public Text.Builder justificationMode(int var1) {
         this.mText.justificationMode = var1;
         return this;
      }

      public Text.Builder letterSpacing(float var1) {
         this.mText.letterSpacing = var1;
         return this;
      }

      public Text.Builder letterSpacingAttr(@AttrRes int var1) {
         this.mText.letterSpacing = this.mResourceResolver.resolveFloatAttr(var1, 0);
         return this;
      }

      public Text.Builder letterSpacingAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mText.letterSpacing = this.mResourceResolver.resolveFloatAttr(var1, var2);
         return this;
      }

      public Text.Builder letterSpacingRes(@DimenRes int var1) {
         this.mText.letterSpacing = this.mResourceResolver.resolveFloatRes(var1);
         return this;
      }

      public Text.Builder linkColor(@ColorInt int var1) {
         this.mText.linkColor = var1;
         return this;
      }

      public Text.Builder linkColorAttr(@AttrRes int var1) {
         this.mText.linkColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public Text.Builder linkColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mText.linkColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public Text.Builder linkColorRes(@ColorRes int var1) {
         this.mText.linkColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public Text.Builder maxEms(int var1) {
         this.mText.maxEms = var1;
         return this;
      }

      public Text.Builder maxEmsAttr(@AttrRes int var1) {
         this.mText.maxEms = this.mResourceResolver.resolveIntAttr(var1, 0);
         return this;
      }

      public Text.Builder maxEmsAttr(@AttrRes int var1, @IntegerRes int var2) {
         this.mText.maxEms = this.mResourceResolver.resolveIntAttr(var1, var2);
         return this;
      }

      public Text.Builder maxEmsRes(@IntegerRes int var1) {
         this.mText.maxEms = this.mResourceResolver.resolveIntRes(var1);
         return this;
      }

      public Text.Builder maxLines(int var1) {
         this.mText.maxLines = var1;
         return this;
      }

      public Text.Builder maxLinesAttr(@AttrRes int var1) {
         this.mText.maxLines = this.mResourceResolver.resolveIntAttr(var1, 0);
         return this;
      }

      public Text.Builder maxLinesAttr(@AttrRes int var1, @IntegerRes int var2) {
         this.mText.maxLines = this.mResourceResolver.resolveIntAttr(var1, var2);
         return this;
      }

      public Text.Builder maxLinesRes(@IntegerRes int var1) {
         this.mText.maxLines = this.mResourceResolver.resolveIntRes(var1);
         return this;
      }

      public Text.Builder maxTextWidthAttr(@AttrRes int var1) {
         this.mText.maxTextWidth = this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public Text.Builder maxTextWidthAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mText.maxTextWidth = this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public Text.Builder maxTextWidthDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mText.maxTextWidth = this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public Text.Builder maxTextWidthPx(@Px int var1) {
         this.mText.maxTextWidth = var1;
         return this;
      }

      public Text.Builder maxTextWidthRes(@DimenRes int var1) {
         this.mText.maxTextWidth = this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public Text.Builder minEms(int var1) {
         this.mText.minEms = var1;
         return this;
      }

      public Text.Builder minEmsAttr(@AttrRes int var1) {
         this.mText.minEms = this.mResourceResolver.resolveIntAttr(var1, 0);
         return this;
      }

      public Text.Builder minEmsAttr(@AttrRes int var1, @IntegerRes int var2) {
         this.mText.minEms = this.mResourceResolver.resolveIntAttr(var1, var2);
         return this;
      }

      public Text.Builder minEmsRes(@IntegerRes int var1) {
         this.mText.minEms = this.mResourceResolver.resolveIntRes(var1);
         return this;
      }

      public Text.Builder minLines(int var1) {
         this.mText.minLines = var1;
         return this;
      }

      public Text.Builder minLinesAttr(@AttrRes int var1) {
         this.mText.minLines = this.mResourceResolver.resolveIntAttr(var1, 0);
         return this;
      }

      public Text.Builder minLinesAttr(@AttrRes int var1, @IntegerRes int var2) {
         this.mText.minLines = this.mResourceResolver.resolveIntAttr(var1, var2);
         return this;
      }

      public Text.Builder minLinesRes(@IntegerRes int var1) {
         this.mText.minLines = this.mResourceResolver.resolveIntRes(var1);
         return this;
      }

      public Text.Builder minTextWidthAttr(@AttrRes int var1) {
         this.mText.minTextWidth = this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public Text.Builder minTextWidthAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mText.minTextWidth = this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public Text.Builder minTextWidthDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mText.minTextWidth = this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public Text.Builder minTextWidthPx(@Px int var1) {
         this.mText.minTextWidth = var1;
         return this;
      }

      public Text.Builder minTextWidthRes(@DimenRes int var1) {
         this.mText.minTextWidth = this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      protected void release() {
         super.release();
         this.mText = null;
         this.mContext = null;
      }

      public Text.Builder shadowColor(@ColorInt int var1) {
         this.mText.shadowColor = var1;
         return this;
      }

      public Text.Builder shadowColorAttr(@AttrRes int var1) {
         this.mText.shadowColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public Text.Builder shadowColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mText.shadowColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public Text.Builder shadowColorRes(@ColorRes int var1) {
         this.mText.shadowColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public Text.Builder shadowDxAttr(@AttrRes int var1) {
         this.mText.shadowDx = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public Text.Builder shadowDxAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mText.shadowDx = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public Text.Builder shadowDxDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mText.shadowDx = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public Text.Builder shadowDxPx(@Px float var1) {
         this.mText.shadowDx = var1;
         return this;
      }

      public Text.Builder shadowDxRes(@DimenRes int var1) {
         this.mText.shadowDx = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public Text.Builder shadowDxSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mText.shadowDx = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public Text.Builder shadowDyAttr(@AttrRes int var1) {
         this.mText.shadowDy = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public Text.Builder shadowDyAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mText.shadowDy = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public Text.Builder shadowDyDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mText.shadowDy = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public Text.Builder shadowDyPx(@Px float var1) {
         this.mText.shadowDy = var1;
         return this;
      }

      public Text.Builder shadowDyRes(@DimenRes int var1) {
         this.mText.shadowDy = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public Text.Builder shadowDySp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mText.shadowDy = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public Text.Builder shadowRadiusAttr(@AttrRes int var1) {
         this.mText.shadowRadius = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public Text.Builder shadowRadiusAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mText.shadowRadius = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public Text.Builder shadowRadiusDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mText.shadowRadius = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public Text.Builder shadowRadiusPx(@Px float var1) {
         this.mText.shadowRadius = var1;
         return this;
      }

      public Text.Builder shadowRadiusRes(@DimenRes int var1) {
         this.mText.shadowRadius = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public Text.Builder shadowRadiusSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mText.shadowRadius = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public Text.Builder shouldIncludeFontPadding(boolean var1) {
         this.mText.shouldIncludeFontPadding = var1;
         return this;
      }

      public Text.Builder shouldIncludeFontPaddingAttr(@AttrRes int var1) {
         this.mText.shouldIncludeFontPadding = this.mResourceResolver.resolveBoolAttr(var1, 0);
         return this;
      }

      public Text.Builder shouldIncludeFontPaddingAttr(@AttrRes int var1, @BoolRes int var2) {
         this.mText.shouldIncludeFontPadding = this.mResourceResolver.resolveBoolAttr(var1, var2);
         return this;
      }

      public Text.Builder shouldIncludeFontPaddingRes(@BoolRes int var1) {
         this.mText.shouldIncludeFontPadding = this.mResourceResolver.resolveBoolRes(var1);
         return this;
      }

      public Text.Builder spacingMultiplier(float var1) {
         this.mText.spacingMultiplier = var1;
         return this;
      }

      public Text.Builder spacingMultiplierAttr(@AttrRes int var1) {
         this.mText.spacingMultiplier = this.mResourceResolver.resolveFloatAttr(var1, 0);
         return this;
      }

      public Text.Builder spacingMultiplierAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mText.spacingMultiplier = this.mResourceResolver.resolveFloatAttr(var1, var2);
         return this;
      }

      public Text.Builder spacingMultiplierRes(@DimenRes int var1) {
         this.mText.spacingMultiplier = this.mResourceResolver.resolveFloatRes(var1);
         return this;
      }

      public Text.Builder spanListener(ClickableSpanListener var1) {
         this.mText.spanListener = var1;
         return this;
      }

      public Text.Builder text(@Nullable CharSequence var1) {
         this.mText.text = var1;
         this.mRequired.set(0);
         return this;
      }

      public Text.Builder textAlignment(Alignment var1) {
         this.mText.textAlignment = var1;
         return this;
      }

      public Text.Builder textAttr(@AttrRes int var1) {
         this.mText.text = this.mResourceResolver.resolveStringAttr(var1, 0);
         this.mRequired.set(0);
         return this;
      }

      public Text.Builder textAttr(@AttrRes int var1, @StringRes int var2) {
         this.mText.text = this.mResourceResolver.resolveStringAttr(var1, var2);
         this.mRequired.set(0);
         return this;
      }

      public Text.Builder textColor(@ColorInt int var1) {
         this.mText.textColor = var1;
         return this;
      }

      public Text.Builder textColorAttr(@AttrRes int var1) {
         this.mText.textColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public Text.Builder textColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mText.textColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public Text.Builder textColorRes(@ColorRes int var1) {
         this.mText.textColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public Text.Builder textColorStateList(ColorStateList var1) {
         this.mText.textColorStateList = var1;
         return this;
      }

      public Text.Builder textDirection(TextDirectionHeuristicCompat var1) {
         this.mText.textDirection = var1;
         return this;
      }

      public Text.Builder textOffsetOnTouchEventHandler(EventHandler var1) {
         this.mText.textOffsetOnTouchEventHandler = var1;
         return this;
      }

      public Text.Builder textOffsetOnTouchHandler(EventHandler var1) {
         this.mText.textOffsetOnTouchHandler = var1;
         return this;
      }

      public Text.Builder textRes(@StringRes int var1) {
         this.mText.text = this.mResourceResolver.resolveStringRes(var1);
         this.mRequired.set(0);
         return this;
      }

      public Text.Builder textRes(@StringRes int var1, Object ... var2) {
         this.mText.text = this.mResourceResolver.resolveStringRes(var1, var2);
         this.mRequired.set(0);
         return this;
      }

      public Text.Builder textSizeAttr(@AttrRes int var1) {
         this.mText.textSize = this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public Text.Builder textSizeAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mText.textSize = this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public Text.Builder textSizeDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mText.textSize = this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public Text.Builder textSizePx(@Px int var1) {
         this.mText.textSize = var1;
         return this;
      }

      public Text.Builder textSizeRes(@DimenRes int var1) {
         this.mText.textSize = this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public Text.Builder textSizeSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mText.textSize = this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public Text.Builder textStyle(int var1) {
         this.mText.textStyle = var1;
         return this;
      }

      public Text.Builder typeface(@Nullable Typeface var1) {
         this.mText.typeface = var1;
         return this;
      }

      public Text.Builder verticalGravity(VerticalGravity var1) {
         this.mText.verticalGravity = var1;
         return this;
      }
   }
}
