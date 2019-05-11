package com.facebook.react.flat;

import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.Layout.Alignment;
import android.text.TextUtils.TruncateAt;
import com.facebook.fbui.textlayoutbuilder.TextLayoutBuilder;
import com.facebook.fbui.textlayoutbuilder.glyphwarmer.GlyphWarmerImpl;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.flat.DrawTextLayout;
import com.facebook.react.flat.NodeRegion;
import com.facebook.react.flat.RCTVirtualText;
import com.facebook.react.flat.StateBuilder;
import com.facebook.react.flat.TextNodeRegion;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNode;
import javax.annotation.Nullable;

final class RCTText extends RCTVirtualText implements YogaMeasureFunction {

   private static final int ALIGNMENT_LEFT = 3;
   private static final int ALIGNMENT_RIGHT = 4;
   private static final TextLayoutBuilder sTextLayoutBuilder = (new TextLayoutBuilder()).setShouldCacheLayout(false).setShouldWarmText(true).setGlyphWarmer(new GlyphWarmerImpl());
   private int mAlignment = 0;
   @Nullable
   private DrawTextLayout mDrawCommand;
   private boolean mIncludeFontPadding = true;
   private int mNumberOfLines = Integer.MAX_VALUE;
   private float mSpacingAdd = 0.0F;
   private float mSpacingMult = 1.0F;
   @Nullable
   private CharSequence mText;


   public RCTText() {
      this.setMeasureFunction(this);
      this.getSpan().setFontSize(this.getDefaultFontSize());
   }

   private static Layout createTextLayout(int var0, YogaMeasureMode var1, TruncateAt var2, boolean var3, int var4, boolean var5, CharSequence var6, int var7, float var8, float var9, int var10, Alignment var11) {
      byte var12;
      switch(null.$SwitchMap$com$facebook$yoga$YogaMeasureMode[var1.ordinal()]) {
      case 1:
         var12 = 0;
         break;
      case 2:
         var12 = 1;
         break;
      case 3:
         var12 = 2;
         break;
      default:
         StringBuilder var14 = new StringBuilder();
         var14.append("Unexpected size mode: ");
         var14.append(var1);
         throw new IllegalStateException(var14.toString());
      }

      sTextLayoutBuilder.setEllipsize(var2).setMaxLines(var4).setSingleLine(var5).setText(var6).setTextSize(var7).setWidth(var0, var12);
      sTextLayoutBuilder.setTextStyle(var10);
      sTextLayoutBuilder.setTextDirection(TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR);
      sTextLayoutBuilder.setIncludeFontPadding(var3);
      sTextLayoutBuilder.setTextSpacingExtra(var8);
      sTextLayoutBuilder.setTextSpacingMultiplier(var9);
      sTextLayoutBuilder.setAlignment(var11);
      Layout var13 = sTextLayoutBuilder.build();
      sTextLayoutBuilder.setText((CharSequence)null);
      return var13;
   }

   protected void collectState(StateBuilder var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      super.collectState(var1, var2, var3, var4, var5, var6, var7, var8, var9);
      if(this.mText == null) {
         if(var5 - var3 > 0.0F && var4 - var2 > 0.0F) {
            SpannableStringBuilder var14 = this.getText();
            if(!TextUtils.isEmpty(var14)) {
               this.mText = var14;
            }
         }

         if(this.mText == null) {
            return;
         }
      }

      boolean var16;
      if(this.mDrawCommand == null) {
         int var10 = (int)Math.ceil((double)(var4 - var2));
         YogaMeasureMode var17 = YogaMeasureMode.EXACTLY;
         TruncateAt var15 = TruncateAt.END;
         boolean var13 = this.mIncludeFontPadding;
         int var11 = this.mNumberOfLines;
         boolean var12;
         if(this.mNumberOfLines == 1) {
            var12 = true;
         } else {
            var12 = false;
         }

         this.mDrawCommand = new DrawTextLayout(createTextLayout(var10, var17, var15, var13, var11, var12, this.mText, this.getFontSize(), this.mSpacingAdd, this.mSpacingMult, this.getFontStyle(), this.getAlignment()));
         var16 = true;
      } else {
         var16 = false;
      }

      var2 += this.getPadding(0);
      var3 += this.getPadding(1);
      var4 = this.mDrawCommand.getLayoutWidth();
      var5 = this.mDrawCommand.getLayoutHeight();
      this.mDrawCommand = (DrawTextLayout)this.mDrawCommand.updateBoundsAndFreeze(var2, var3, var2 + var4, var3 + var5, var6, var7, var8, var9);
      var1.addDrawCommand(this.mDrawCommand);
      if(var16) {
         NodeRegion var18 = this.getNodeRegion();
         if(var18 instanceof TextNodeRegion) {
            ((TextNodeRegion)var18).setLayout(this.mDrawCommand.getLayout());
         }
      }

      this.performCollectAttachDetachListeners(var1);
   }

   boolean doesDraw() {
      return true;
   }

   public Alignment getAlignment() {
      boolean var1;
      if(this.getLayoutDirection() == YogaDirection.RTL) {
         var1 = true;
      } else {
         var1 = false;
      }

      int var3 = this.mAlignment;
      byte var2 = 4;
      if(var3 != 3) {
         if(var3 != 5) {
            return var3 != 17?Alignment.ALIGN_NORMAL:Alignment.ALIGN_CENTER;
         } else {
            if(var1) {
               var2 = 3;
            }

            return Alignment.values()[var2];
         }
      } else {
         if(!var1) {
            var2 = 3;
         }

         return Alignment.values()[var2];
      }
   }

   protected int getDefaultFontSize() {
      return fontSizeFromSp(14.0F);
   }

   public boolean isVirtual() {
      return false;
   }

   public boolean isVirtualAnchor() {
      return true;
   }

   public long measure(YogaNode var1, float var2, YogaMeasureMode var3, float var4, YogaMeasureMode var5) {
      SpannableStringBuilder var10 = this.getText();
      if(TextUtils.isEmpty(var10)) {
         this.mText = null;
         return YogaMeasureOutput.make(0, 0);
      } else {
         this.mText = var10;
         int var6 = (int)Math.ceil((double)var2);
         TruncateAt var12 = TruncateAt.END;
         boolean var9 = this.mIncludeFontPadding;
         int var7 = this.mNumberOfLines;
         boolean var8;
         if(this.mNumberOfLines == 1) {
            var8 = true;
         } else {
            var8 = false;
         }

         Layout var11 = createTextLayout(var6, var3, var12, var9, var7, var8, var10, this.getFontSize(), this.mSpacingAdd, this.mSpacingMult, this.getFontStyle(), this.getAlignment());
         if(this.mDrawCommand != null && !this.mDrawCommand.isFrozen()) {
            this.mDrawCommand.setLayout(var11);
         } else {
            this.mDrawCommand = new DrawTextLayout(var11);
         }

         return YogaMeasureOutput.make(this.mDrawCommand.getLayoutWidth(), this.mDrawCommand.getLayoutHeight());
      }
   }

   protected void notifyChanged(boolean var1) {
      this.dirty();
   }

   @ReactProp(
      defaultBoolean = true,
      name = "includeFontPadding"
   )
   public void setIncludeFontPadding(boolean var1) {
      this.mIncludeFontPadding = var1;
   }

   @ReactProp(
      defaultDouble = Double.NaN,
      name = "lineHeight"
   )
   public void setLineHeight(double var1) {
      if(Double.isNaN(var1)) {
         this.mSpacingMult = 1.0F;
         this.mSpacingAdd = 0.0F;
      } else {
         this.mSpacingMult = 0.0F;
         this.mSpacingAdd = PixelUtil.toPixelFromSP((float)var1);
      }

      this.notifyChanged(true);
   }

   @ReactProp(
      defaultInt = Integer.MAX_VALUE,
      name = "numberOfLines"
   )
   public void setNumberOfLines(int var1) {
      this.mNumberOfLines = var1;
      this.notifyChanged(true);
   }

   @ReactProp(
      name = "textAlign"
   )
   public void setTextAlign(@Nullable String var1) {
      if(var1 != null && !"auto".equals(var1)) {
         if("left".equals(var1)) {
            this.mAlignment = 3;
         } else if("right".equals(var1)) {
            this.mAlignment = 5;
         } else {
            if(!"center".equals(var1)) {
               StringBuilder var2 = new StringBuilder();
               var2.append("Invalid textAlign: ");
               var2.append(var1);
               throw new JSApplicationIllegalArgumentException(var2.toString());
            }

            this.mAlignment = 17;
         }
      } else {
         this.mAlignment = 0;
      }

      this.notifyChanged(false);
   }

   void updateNodeRegion(float var1, float var2, float var3, float var4, boolean var5) {
      NodeRegion var7 = this.getNodeRegion();
      if(this.mDrawCommand == null) {
         if(!var7.matches(var1, var2, var3, var4, var5)) {
            this.setNodeRegion(new TextNodeRegion(var1, var2, var3, var4, this.getReactTag(), var5, (Layout)null));
         }

      } else {
         Layout var6 = null;
         if(var7 instanceof TextNodeRegion) {
            var6 = ((TextNodeRegion)var7).getLayout();
         }

         Layout var8 = this.mDrawCommand.getLayout();
         if(!var7.matches(var1, var2, var3, var4, var5) || var6 != var8) {
            this.setNodeRegion(new TextNodeRegion(var1, var2, var3, var4, this.getReactTag(), var5, var8));
         }

      }
   }
}
