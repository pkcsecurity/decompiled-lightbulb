package com.facebook.react.flat;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.flat.FlatTextShadowNode;
import com.facebook.react.flat.FontStylingSpan;
import com.facebook.react.flat.ShadowStyleSpan;
import com.facebook.react.flat.StateBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.annotations.ReactProp;
import javax.annotation.Nullable;

class RCTVirtualText extends FlatTextShadowNode {

   private static final String BOLD = "bold";
   private static final int DEFAULT_TEXT_SHADOW_COLOR = 1426063360;
   private static final String ITALIC = "italic";
   private static final String NORMAL = "normal";
   private static final String PROP_SHADOW_COLOR = "textShadowColor";
   private static final String PROP_SHADOW_OFFSET = "textShadowOffset";
   private static final String PROP_SHADOW_RADIUS = "textShadowRadius";
   private FontStylingSpan mFontStylingSpan;
   private ShadowStyleSpan mShadowStyleSpan;


   RCTVirtualText() {
      this.mFontStylingSpan = FontStylingSpan.INSTANCE;
      this.mShadowStyleSpan = ShadowStyleSpan.INSTANCE;
   }

   static int fontSizeFromSp(float var0) {
      return (int)Math.ceil((double)PixelUtil.toPixelFromSP(var0));
   }

   private final ShadowStyleSpan getShadowSpan() {
      if(this.mShadowStyleSpan.isFrozen()) {
         this.mShadowStyleSpan = this.mShadowStyleSpan.mutableCopy();
      }

      return this.mShadowStyleSpan;
   }

   private static int parseNumericFontWeight(String var0) {
      return var0.length() == 3 && var0.endsWith("00") && var0.charAt(0) <= 57 && var0.charAt(0) >= 49?(var0.charAt(0) - 48) * 100:-1;
   }

   public void addChildAt(ReactShadowNodeImpl var1, int var2) {
      super.addChildAt(var1, var2);
      this.notifyChanged(true);
   }

   protected int getDefaultFontSize() {
      return -1;
   }

   protected final int getFontSize() {
      return this.mFontStylingSpan.getFontSize();
   }

   protected final int getFontStyle() {
      int var1 = this.mFontStylingSpan.getFontStyle();
      return var1 >= 0?var1:0;
   }

   protected final FontStylingSpan getSpan() {
      if(this.mFontStylingSpan.isFrozen()) {
         this.mFontStylingSpan = this.mFontStylingSpan.mutableCopy();
      }

      return this.mFontStylingSpan;
   }

   final SpannableStringBuilder getText() {
      SpannableStringBuilder var1 = new SpannableStringBuilder();
      this.collectText(var1);
      this.applySpans(var1, this.isEditable());
      return var1;
   }

   protected void performApplySpans(SpannableStringBuilder var1, int var2, int var3, boolean var4) {
      this.mFontStylingSpan.freeze();
      byte var5;
      if(var4) {
         var5 = 33;
      } else if(var2 == 0) {
         var5 = 18;
      } else {
         var5 = 34;
      }

      var1.setSpan(this.mFontStylingSpan, var2, var3, var5);
      if(this.mShadowStyleSpan.getColor() != 0 && this.mShadowStyleSpan.getRadius() != 0.0F) {
         this.mShadowStyleSpan.freeze();
         var1.setSpan(this.mShadowStyleSpan, var2, var3, var5);
      }

      var2 = 0;

      for(var3 = this.getChildCount(); var2 < var3; ++var2) {
         ((FlatTextShadowNode)this.getChildAt(var2)).applySpans(var1, var4);
      }

   }

   protected void performCollectAttachDetachListeners(StateBuilder var1) {
      int var3 = this.getChildCount();

      for(int var2 = 0; var2 < var3; ++var2) {
         ((FlatTextShadowNode)this.getChildAt(var2)).performCollectAttachDetachListeners(var1);
      }

   }

   protected void performCollectText(SpannableStringBuilder var1) {
      int var3 = this.getChildCount();

      for(int var2 = 0; var2 < var3; ++var2) {
         ((FlatTextShadowNode)this.getChildAt(var2)).collectText(var1);
      }

   }

   public void setBackgroundColor(int var1) {
      if(this.isVirtual()) {
         if(this.mFontStylingSpan.getBackgroundColor() != var1) {
            this.getSpan().setBackgroundColor(var1);
            this.notifyChanged(false);
            return;
         }
      } else {
         super.setBackgroundColor(var1);
      }

   }

   @ReactProp(
      defaultDouble = Double.NaN,
      name = "color"
   )
   public void setColor(double var1) {
      if(this.mFontStylingSpan.getTextColor() != var1) {
         this.getSpan().setTextColor(var1);
         this.notifyChanged(false);
      }

   }

   @ReactProp(
      name = "fontFamily"
   )
   public void setFontFamily(@Nullable String var1) {
      if(!TextUtils.equals(this.mFontStylingSpan.getFontFamily(), var1)) {
         this.getSpan().setFontFamily(var1);
         this.notifyChanged(true);
      }

   }

   @ReactProp(
      defaultFloat = Float.NaN,
      name = "fontSize"
   )
   public void setFontSize(float var1) {
      int var2;
      if(Float.isNaN(var1)) {
         var2 = this.getDefaultFontSize();
      } else {
         var2 = fontSizeFromSp(var1);
      }

      if(this.mFontStylingSpan.getFontSize() != var2) {
         this.getSpan().setFontSize(var2);
         this.notifyChanged(true);
      }

   }

   @ReactProp(
      name = "fontStyle"
   )
   public void setFontStyle(@Nullable String var1) {
      byte var2;
      if(var1 == null) {
         var2 = -1;
      } else if("italic".equals(var1)) {
         var2 = 2;
      } else {
         if(!"normal".equals(var1)) {
            StringBuilder var3 = new StringBuilder();
            var3.append("invalid font style ");
            var3.append(var1);
            throw new RuntimeException(var3.toString());
         }

         var2 = 0;
      }

      if(this.mFontStylingSpan.getFontStyle() != var2) {
         this.getSpan().setFontStyle(var2);
         this.notifyChanged(true);
      }

   }

   @ReactProp(
      name = "fontWeight"
   )
   public void setFontWeight(@Nullable String var1) {
      byte var2 = 0;
      if(var1 == null) {
         var2 = -1;
      } else {
         label26: {
            if(!"bold".equals(var1)) {
               if("normal".equals(var1)) {
                  break label26;
               }

               int var3 = parseNumericFontWeight(var1);
               if(var3 == -1) {
                  StringBuilder var4 = new StringBuilder();
                  var4.append("invalid font weight ");
                  var4.append(var1);
                  throw new RuntimeException(var4.toString());
               }

               if(var3 < 500) {
                  break label26;
               }
            }

            var2 = 1;
         }
      }

      if(this.mFontStylingSpan.getFontWeight() != var2) {
         this.getSpan().setFontWeight(var2);
         this.notifyChanged(true);
      }

   }

   @ReactProp(
      name = "textDecorationLine"
   )
   public void setTextDecorationLine(@Nullable String var1) {
      boolean var5 = false;
      int var2 = 0;
      boolean var4;
      if(var1 != null) {
         String[] var8 = var1.split(" ");
         int var3 = var8.length;
         var5 = false;

         boolean var6;
         for(var4 = false; var2 < var3; var5 = var6) {
            String var7 = var8[var2];
            if("underline".equals(var7)) {
               var6 = true;
            } else {
               var6 = var5;
               if("line-through".equals(var7)) {
                  var4 = true;
                  var6 = var5;
               }
            }

            ++var2;
         }
      } else {
         var4 = false;
      }

      if(var5 != this.mFontStylingSpan.hasUnderline() || var4 != this.mFontStylingSpan.hasStrikeThrough()) {
         FontStylingSpan var9 = this.getSpan();
         var9.setHasUnderline(var5);
         var9.setHasStrikeThrough(var4);
         this.notifyChanged(true);
      }

   }

   @ReactProp(
      customType = "Color",
      defaultInt = 1426063360,
      name = "textShadowColor"
   )
   public void setTextShadowColor(int var1) {
      if(this.mShadowStyleSpan.getColor() != var1) {
         this.getShadowSpan().setColor(var1);
         this.notifyChanged(false);
      }

   }

   @ReactProp(
      name = "textShadowOffset"
   )
   public void setTextShadowOffset(@Nullable ReadableMap var1) {
      float var4 = 0.0F;
      float var3;
      if(var1 != null) {
         float var2;
         if(var1.hasKey("width")) {
            var2 = PixelUtil.toPixelFromDIP(var1.getDouble("width"));
         } else {
            var2 = 0.0F;
         }

         var3 = var2;
         if(var1.hasKey("height")) {
            var4 = PixelUtil.toPixelFromDIP(var1.getDouble("height"));
            var3 = var2;
         }
      } else {
         var3 = 0.0F;
      }

      if(!this.mShadowStyleSpan.offsetMatches(var3, var4)) {
         this.getShadowSpan().setOffset(var3, var4);
         this.notifyChanged(false);
      }

   }

   @ReactProp(
      name = "textShadowRadius"
   )
   public void setTextShadowRadius(float var1) {
      var1 = PixelUtil.toPixelFromDIP(var1);
      if(this.mShadowStyleSpan.getRadius() != var1) {
         this.getShadowSpan().setRadius(var1);
         this.notifyChanged(false);
      }

   }
}
