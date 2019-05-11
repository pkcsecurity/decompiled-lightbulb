package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.text.TextUtils;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.art.ARTShapeShadowNode;
import javax.annotation.Nullable;

public class ARTTextShadowNode extends ARTShapeShadowNode {

   private static final int DEFAULT_FONT_SIZE = 12;
   private static final String PROP_FONT = "font";
   private static final String PROP_FONT_FAMILY = "fontFamily";
   private static final String PROP_FONT_SIZE = "fontSize";
   private static final String PROP_FONT_STYLE = "fontStyle";
   private static final String PROP_FONT_WEIGHT = "fontWeight";
   private static final String PROP_LINES = "lines";
   private static final int TEXT_ALIGNMENT_CENTER = 2;
   private static final int TEXT_ALIGNMENT_LEFT = 0;
   private static final int TEXT_ALIGNMENT_RIGHT = 1;
   @Nullable
   private ReadableMap mFrame;
   private int mTextAlignment = 0;


   private void applyTextPropertiesToPaint(Paint var1) {
      switch(this.mTextAlignment) {
      case 0:
         var1.setTextAlign(Align.LEFT);
         break;
      case 1:
         var1.setTextAlign(Align.RIGHT);
         break;
      case 2:
         var1.setTextAlign(Align.CENTER);
      }

      if(this.mFrame != null && this.mFrame.hasKey("font")) {
         ReadableMap var7 = this.mFrame.getMap("font");
         if(var7 != null) {
            float var2 = 12.0F;
            if(var7.hasKey("fontSize")) {
               var2 = (float)var7.getDouble("fontSize");
            }

            var1.setTextSize(var2 * this.mScale);
            boolean var6 = var7.hasKey("fontWeight");
            byte var5 = 0;
            boolean var3;
            if(var6 && "bold".equals(var7.getString("fontWeight"))) {
               var3 = true;
            } else {
               var3 = false;
            }

            boolean var4;
            if(var7.hasKey("fontStyle") && "italic".equals(var7.getString("fontStyle"))) {
               var4 = true;
            } else {
               var4 = false;
            }

            byte var8;
            if(var3 && var4) {
               var8 = 3;
            } else if(var3) {
               var8 = 1;
            } else {
               var8 = var5;
               if(var4) {
                  var8 = 2;
               }
            }

            var1.setTypeface(Typeface.create(var7.getString("fontFamily"), var8));
         }
      }

   }

   public void draw(Canvas var1, Paint var2, float var3) {
      if(this.mFrame != null) {
         var3 *= this.mOpacity;
         if(var3 > 0.01F) {
            if(this.mFrame.hasKey("lines")) {
               ReadableArray var5 = this.mFrame.getArray("lines");
               if(var5 != null) {
                  if(var5.size() != 0) {
                     this.saveAndSetupCanvas(var1);
                     String[] var6 = new String[var5.size()];

                     for(int var4 = 0; var4 < var6.length; ++var4) {
                        var6[var4] = var5.getString(var4);
                     }

                     String var7 = TextUtils.join("\n", var6);
                     if(this.setupStrokePaint(var2, var3)) {
                        this.applyTextPropertiesToPaint(var2);
                        if(this.mPath == null) {
                           var1.drawText(var7, 0.0F, -var2.ascent(), var2);
                        } else {
                           var1.drawTextOnPath(var7, this.mPath, 0.0F, 0.0F, var2);
                        }
                     }

                     if(this.setupFillPaint(var2, var3)) {
                        this.applyTextPropertiesToPaint(var2);
                        if(this.mPath == null) {
                           var1.drawText(var7, 0.0F, -var2.ascent(), var2);
                        } else {
                           var1.drawTextOnPath(var7, this.mPath, 0.0F, 0.0F, var2);
                        }
                     }

                     this.restoreCanvas(var1);
                     this.markUpdateSeen();
                  }
               }
            }
         }
      }
   }

   @ReactProp(
      defaultInt = 0,
      name = "alignment"
   )
   public void setAlignment(int var1) {
      this.mTextAlignment = var1;
   }

   @ReactProp(
      name = "frame"
   )
   public void setFrame(@Nullable ReadableMap var1) {
      this.mFrame = var1;
   }
}
