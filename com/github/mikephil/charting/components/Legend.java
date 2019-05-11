package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import com.github.mikephil.charting.components.ComponentBase;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.List;

public class Legend extends ComponentBase {

   private List<Boolean> mCalculatedLabelBreakPoints;
   private List<FSize> mCalculatedLabelSizes;
   private List<FSize> mCalculatedLineSizes;
   private Legend.LegendDirection mDirection;
   private boolean mDrawInside;
   private LegendEntry[] mEntries;
   private LegendEntry[] mExtraEntries;
   private DashPathEffect mFormLineDashEffect;
   private float mFormLineWidth;
   private float mFormSize;
   private float mFormToTextSpace;
   private Legend.LegendHorizontalAlignment mHorizontalAlignment;
   private boolean mIsLegendCustom;
   private float mMaxSizePercent;
   public float mNeededHeight;
   public float mNeededWidth;
   private Legend.LegendOrientation mOrientation;
   private Legend.LegendForm mShape;
   private float mStackSpace;
   public float mTextHeightMax;
   public float mTextWidthMax;
   private Legend.LegendVerticalAlignment mVerticalAlignment;
   private boolean mWordWrapEnabled;
   private float mXEntrySpace;
   private float mYEntrySpace;


   public Legend() {
      this.mEntries = new LegendEntry[0];
      this.mIsLegendCustom = false;
      this.mHorizontalAlignment = Legend.LegendHorizontalAlignment.LEFT;
      this.mVerticalAlignment = Legend.LegendVerticalAlignment.BOTTOM;
      this.mOrientation = Legend.LegendOrientation.HORIZONTAL;
      this.mDrawInside = false;
      this.mDirection = Legend.LegendDirection.LEFT_TO_RIGHT;
      this.mShape = Legend.LegendForm.SQUARE;
      this.mFormSize = 8.0F;
      this.mFormLineWidth = 3.0F;
      this.mFormLineDashEffect = null;
      this.mXEntrySpace = 6.0F;
      this.mYEntrySpace = 0.0F;
      this.mFormToTextSpace = 5.0F;
      this.mStackSpace = 3.0F;
      this.mMaxSizePercent = 0.95F;
      this.mNeededWidth = 0.0F;
      this.mNeededHeight = 0.0F;
      this.mTextHeightMax = 0.0F;
      this.mTextWidthMax = 0.0F;
      this.mWordWrapEnabled = false;
      this.mCalculatedLabelSizes = new ArrayList(16);
      this.mCalculatedLabelBreakPoints = new ArrayList(16);
      this.mCalculatedLineSizes = new ArrayList(16);
      this.mTextSize = Utils.convertDpToPixel(10.0F);
      this.mXOffset = Utils.convertDpToPixel(5.0F);
      this.mYOffset = Utils.convertDpToPixel(3.0F);
   }

   @Deprecated
   public Legend(List<Integer> var1, List<String> var2) {
      this(Utils.convertIntegers(var1), Utils.convertStrings(var2));
   }

   @Deprecated
   public Legend(int[] var1, String[] var2) {
      this();
      if(var1 != null && var2 != null) {
         if(var1.length != var2.length) {
            throw new IllegalArgumentException("colors array and labels array need to be of same size");
         } else {
            ArrayList var4 = new ArrayList();

            for(int var3 = 0; var3 < Math.min(var1.length, var2.length); ++var3) {
               LegendEntry var5 = new LegendEntry();
               var5.formColor = var1[var3];
               var5.label = var2[var3];
               if(var5.formColor == 1122868) {
                  var5.form = Legend.LegendForm.NONE;
               } else if(var5.formColor == 1122867 || var5.formColor == 0) {
                  var5.form = Legend.LegendForm.EMPTY;
               }

               var4.add(var5);
            }

            this.mEntries = (LegendEntry[])var4.toArray(new LegendEntry[var4.size()]);
         }
      } else {
         throw new IllegalArgumentException("colors array or labels array is NULL");
      }
   }

   public Legend(LegendEntry[] var1) {
      this();
      if(var1 == null) {
         throw new IllegalArgumentException("entries array is NULL");
      } else {
         this.mEntries = var1;
      }
   }

   public void calculateDimensions(Paint var1, ViewPortHandler var2) {
      float var9 = Utils.convertDpToPixel(this.mFormSize);
      float var11 = Utils.convertDpToPixel(this.mStackSpace);
      float var12 = Utils.convertDpToPixel(this.mFormToTextSpace);
      float var5 = Utils.convertDpToPixel(this.mXEntrySpace);
      float var10 = Utils.convertDpToPixel(this.mYEntrySpace);
      boolean var20 = this.mWordWrapEnabled;
      LegendEntry[] var21 = this.mEntries;
      int var19 = var21.length;
      this.mTextWidthMax = this.getMaximumEntryWidth(var1);
      this.mTextHeightMax = this.getMaximumEntryHeight(var1);
      float var3;
      float var4;
      float var6;
      float var7;
      float var8;
      float var13;
      int var28;
      switch(null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[this.mOrientation.ordinal()]) {
      case 1:
         var13 = Utils.getLineHeight(var1);
         var28 = 0;
         var3 = 0.0F;
         var5 = 0.0F;
         boolean var26 = false;

         for(var4 = 0.0F; var28 < var19; var4 = var7) {
            LegendEntry var24 = var21[var28];
            boolean var27;
            if(var24.form != Legend.LegendForm.NONE) {
               var27 = true;
            } else {
               var27 = false;
            }

            if(Float.isNaN(var24.formSize)) {
               var6 = var9;
            } else {
               var6 = Utils.convertDpToPixel(var24.formSize);
            }

            String var25 = var24.label;
            if(!var26) {
               var4 = 0.0F;
            }

            var7 = var4;
            if(var27) {
               var7 = var4;
               if(var26) {
                  var7 = var4 + var11;
               }

               var7 += var6;
            }

            if(var25 != null) {
               if(var27 && !var26) {
                  var8 = var7 + var12;
                  var4 = var3;
                  var6 = var5;
                  var27 = var26;
               } else {
                  var4 = var3;
                  var6 = var5;
                  var27 = var26;
                  var8 = var7;
                  if(var26) {
                     var4 = Math.max(var3, var7);
                     var6 = var5 + var13 + var10;
                     var27 = false;
                     var8 = 0.0F;
                  }
               }

               var8 += (float)Utils.calcTextWidth(var1, var25);
               var3 = var4;
               var5 = var6;
               var26 = var27;
               var7 = var8;
               if(var28 < var19 - 1) {
                  var5 = var6 + var13 + var10;
                  var3 = var4;
                  var26 = var27;
                  var7 = var8;
               }
            } else {
               var4 = var7 + var6;
               var7 = var4;
               if(var28 < var19 - 1) {
                  var7 = var4 + var11;
               }

               var26 = true;
            }

            var3 = Math.max(var3, var7);
            ++var28;
         }

         this.mNeededWidth = var3;
         this.mNeededHeight = var5;
         break;
      case 2:
         var13 = Utils.getLineHeight(var1);
         var8 = Utils.getLineSpacing(var1) + var10;
         float var14 = var2.contentWidth();
         float var15 = this.mMaxSizePercent;
         this.mCalculatedLabelBreakPoints.clear();
         this.mCalculatedLabelSizes.clear();
         this.mCalculatedLineSizes.clear();
         int var16 = 0;
         int var17 = -1;
         var4 = 0.0F;
         var10 = 0.0F;
         var7 = 0.0F;
         LegendEntry[] var23 = var21;

         for(var6 = var9; var16 < var19; var10 = var3) {
            LegendEntry var29 = var23[var16];
            boolean var18;
            if(var29.form != Legend.LegendForm.NONE) {
               var18 = true;
            } else {
               var18 = false;
            }

            if(Float.isNaN(var29.formSize)) {
               var3 = var6;
            } else {
               var3 = Utils.convertDpToPixel(var29.formSize);
            }

            String var30 = var29.label;
            this.mCalculatedLabelBreakPoints.add(Boolean.valueOf(false));
            if(var17 == -1) {
               var9 = 0.0F;
            } else {
               var9 = var10 + var11;
            }

            List var22;
            if(var30 != null) {
               this.mCalculatedLabelSizes.add(Utils.calcTextSize(var1, var30));
               if(var18) {
                  var3 += var12;
               } else {
                  var3 = 0.0F;
               }

               var3 = var9 + var3 + ((FSize)this.mCalculatedLabelSizes.get(var16)).width;
            } else {
               var22 = this.mCalculatedLabelSizes;
               var22.add(FSize.getInstance(0.0F, 0.0F));
               if(!var18) {
                  var3 = 0.0F;
               }

               var9 += var3;
               var3 = var9;
               if(var17 == -1) {
                  var3 = var9;
                  var17 = var16;
               }
            }

            if(var30 != null || var16 == var19 - 1) {
               if(var7 == 0.0F) {
                  var9 = 0.0F;
               } else {
                  var9 = var5;
               }

               if(var20 && var7 != 0.0F && var14 * var15 - var7 < var9 + var3) {
                  this.mCalculatedLineSizes.add(FSize.getInstance(var7, var13));
                  var7 = Math.max(var4, var7);
                  var22 = this.mCalculatedLabelBreakPoints;
                  if(var17 > -1) {
                     var28 = var17;
                  } else {
                     var28 = var16;
                  }

                  var22.set(var28, Boolean.valueOf(true));
                  var4 = var3;
               } else {
                  var9 = var7 + var9 + var3;
                  var7 = var4;
                  var4 = var9;
               }

               if(var16 == var19 - 1) {
                  this.mCalculatedLineSizes.add(FSize.getInstance(var4, var13));
                  var9 = Math.max(var7, var4);
                  var7 = var4;
                  var4 = var9;
               } else {
                  var9 = var7;
                  var7 = var4;
                  var4 = var9;
               }
            }

            if(var30 != null) {
               var17 = -1;
            }

            ++var16;
         }

         this.mNeededWidth = var4;
         var3 = (float)this.mCalculatedLineSizes.size();
         if(this.mCalculatedLineSizes.size() == 0) {
            var16 = 0;
         } else {
            var16 = this.mCalculatedLineSizes.size() - 1;
         }

         this.mNeededHeight = var13 * var3 + var8 * (float)var16;
      }

      this.mNeededHeight += this.mYOffset;
      this.mNeededWidth += this.mXOffset;
   }

   public List<Boolean> getCalculatedLabelBreakPoints() {
      return this.mCalculatedLabelBreakPoints;
   }

   public List<FSize> getCalculatedLabelSizes() {
      return this.mCalculatedLabelSizes;
   }

   public List<FSize> getCalculatedLineSizes() {
      return this.mCalculatedLineSizes;
   }

   @Deprecated
   public int[] getColors() {
      int[] var3 = new int[this.mEntries.length];

      for(int var2 = 0; var2 < this.mEntries.length; ++var2) {
         int var1;
         if(this.mEntries[var2].form == Legend.LegendForm.NONE) {
            var1 = 1122868;
         } else if(this.mEntries[var2].form == Legend.LegendForm.EMPTY) {
            var1 = 1122867;
         } else {
            var1 = this.mEntries[var2].formColor;
         }

         var3[var2] = var1;
      }

      return var3;
   }

   public Legend.LegendDirection getDirection() {
      return this.mDirection;
   }

   public LegendEntry[] getEntries() {
      return this.mEntries;
   }

   @Deprecated
   public int[] getExtraColors() {
      int[] var3 = new int[this.mExtraEntries.length];

      for(int var2 = 0; var2 < this.mExtraEntries.length; ++var2) {
         int var1;
         if(this.mExtraEntries[var2].form == Legend.LegendForm.NONE) {
            var1 = 1122868;
         } else if(this.mExtraEntries[var2].form == Legend.LegendForm.EMPTY) {
            var1 = 1122867;
         } else {
            var1 = this.mExtraEntries[var2].formColor;
         }

         var3[var2] = var1;
      }

      return var3;
   }

   public LegendEntry[] getExtraEntries() {
      return this.mExtraEntries;
   }

   @Deprecated
   public String[] getExtraLabels() {
      String[] var2 = new String[this.mExtraEntries.length];

      for(int var1 = 0; var1 < this.mExtraEntries.length; ++var1) {
         var2[var1] = this.mExtraEntries[var1].label;
      }

      return var2;
   }

   public Legend.LegendForm getForm() {
      return this.mShape;
   }

   public DashPathEffect getFormLineDashEffect() {
      return this.mFormLineDashEffect;
   }

   public float getFormLineWidth() {
      return this.mFormLineWidth;
   }

   public float getFormSize() {
      return this.mFormSize;
   }

   public float getFormToTextSpace() {
      return this.mFormToTextSpace;
   }

   public Legend.LegendHorizontalAlignment getHorizontalAlignment() {
      return this.mHorizontalAlignment;
   }

   @Deprecated
   public String[] getLabels() {
      String[] var2 = new String[this.mEntries.length];

      for(int var1 = 0; var1 < this.mEntries.length; ++var1) {
         var2[var1] = this.mEntries[var1].label;
      }

      return var2;
   }

   public float getMaxSizePercent() {
      return this.mMaxSizePercent;
   }

   public float getMaximumEntryHeight(Paint var1) {
      LegendEntry[] var7 = this.mEntries;
      int var6 = var7.length;
      float var2 = 0.0F;

      float var3;
      for(int var5 = 0; var5 < var6; var2 = var3) {
         String var8 = var7[var5].label;
         if(var8 == null) {
            var3 = var2;
         } else {
            float var4 = (float)Utils.calcTextHeight(var1, var8);
            var3 = var2;
            if(var4 > var2) {
               var3 = var4;
            }
         }

         ++var5;
      }

      return var2;
   }

   public float getMaximumEntryWidth(Paint var1) {
      float var6 = Utils.convertDpToPixel(this.mFormToTextSpace);
      LegendEntry[] var9 = this.mEntries;
      int var8 = var9.length;
      float var2 = 0.0F;
      int var7 = 0;

      float var3;
      float var4;
      for(var3 = 0.0F; var7 < var8; var3 = var4) {
         LegendEntry var10 = var9[var7];
         if(Float.isNaN(var10.formSize)) {
            var4 = this.mFormSize;
         } else {
            var4 = var10.formSize;
         }

         float var5 = Utils.convertDpToPixel(var4);
         var4 = var3;
         if(var5 > var3) {
            var4 = var5;
         }

         String var11 = var10.label;
         if(var11 == null) {
            var3 = var2;
         } else {
            var5 = (float)Utils.calcTextWidth(var1, var11);
            var3 = var2;
            if(var5 > var2) {
               var3 = var5;
            }
         }

         ++var7;
         var2 = var3;
      }

      return var2 + var3 + var6;
   }

   public Legend.LegendOrientation getOrientation() {
      return this.mOrientation;
   }

   @Deprecated
   public Legend.LegendPosition getPosition() {
      return this.mOrientation == Legend.LegendOrientation.VERTICAL && this.mHorizontalAlignment == Legend.LegendHorizontalAlignment.CENTER && this.mVerticalAlignment == Legend.LegendVerticalAlignment.CENTER?Legend.LegendPosition.PIECHART_CENTER:(this.mOrientation == Legend.LegendOrientation.HORIZONTAL?(this.mVerticalAlignment == Legend.LegendVerticalAlignment.TOP?(this.mHorizontalAlignment == Legend.LegendHorizontalAlignment.LEFT?Legend.LegendPosition.ABOVE_CHART_LEFT:(this.mHorizontalAlignment == Legend.LegendHorizontalAlignment.RIGHT?Legend.LegendPosition.ABOVE_CHART_RIGHT:Legend.LegendPosition.ABOVE_CHART_CENTER)):(this.mHorizontalAlignment == Legend.LegendHorizontalAlignment.LEFT?Legend.LegendPosition.BELOW_CHART_LEFT:(this.mHorizontalAlignment == Legend.LegendHorizontalAlignment.RIGHT?Legend.LegendPosition.BELOW_CHART_RIGHT:Legend.LegendPosition.BELOW_CHART_CENTER))):(this.mHorizontalAlignment == Legend.LegendHorizontalAlignment.LEFT?(this.mVerticalAlignment == Legend.LegendVerticalAlignment.TOP && this.mDrawInside?Legend.LegendPosition.LEFT_OF_CHART_INSIDE:(this.mVerticalAlignment == Legend.LegendVerticalAlignment.CENTER?Legend.LegendPosition.LEFT_OF_CHART_CENTER:Legend.LegendPosition.LEFT_OF_CHART)):(this.mVerticalAlignment == Legend.LegendVerticalAlignment.TOP && this.mDrawInside?Legend.LegendPosition.RIGHT_OF_CHART_INSIDE:(this.mVerticalAlignment == Legend.LegendVerticalAlignment.CENTER?Legend.LegendPosition.RIGHT_OF_CHART_CENTER:Legend.LegendPosition.RIGHT_OF_CHART))));
   }

   public float getStackSpace() {
      return this.mStackSpace;
   }

   public Legend.LegendVerticalAlignment getVerticalAlignment() {
      return this.mVerticalAlignment;
   }

   public float getXEntrySpace() {
      return this.mXEntrySpace;
   }

   public float getYEntrySpace() {
      return this.mYEntrySpace;
   }

   public boolean isDrawInsideEnabled() {
      return this.mDrawInside;
   }

   public boolean isLegendCustom() {
      return this.mIsLegendCustom;
   }

   public boolean isWordWrapEnabled() {
      return this.mWordWrapEnabled;
   }

   public void resetCustom() {
      this.mIsLegendCustom = false;
   }

   public void setCustom(List<LegendEntry> var1) {
      this.mEntries = (LegendEntry[])var1.toArray(new LegendEntry[var1.size()]);
      this.mIsLegendCustom = true;
   }

   public void setCustom(LegendEntry[] var1) {
      this.mEntries = var1;
      this.mIsLegendCustom = true;
   }

   public void setDirection(Legend.LegendDirection var1) {
      this.mDirection = var1;
   }

   public void setDrawInside(boolean var1) {
      this.mDrawInside = var1;
   }

   public void setEntries(List<LegendEntry> var1) {
      this.mEntries = (LegendEntry[])var1.toArray(new LegendEntry[var1.size()]);
   }

   public void setExtra(List<LegendEntry> var1) {
      this.mExtraEntries = (LegendEntry[])var1.toArray(new LegendEntry[var1.size()]);
   }

   @Deprecated
   public void setExtra(List<Integer> var1, List<String> var2) {
      this.setExtra(Utils.convertIntegers(var1), Utils.convertStrings(var2));
   }

   public void setExtra(int[] var1, String[] var2) {
      ArrayList var4 = new ArrayList();

      for(int var3 = 0; var3 < Math.min(var1.length, var2.length); ++var3) {
         LegendEntry var5 = new LegendEntry();
         var5.formColor = var1[var3];
         var5.label = var2[var3];
         if(var5.formColor != 1122868 && var5.formColor != 0) {
            if(var5.formColor == 1122867) {
               var5.form = Legend.LegendForm.EMPTY;
            }
         } else {
            var5.form = Legend.LegendForm.NONE;
         }

         var4.add(var5);
      }

      this.mExtraEntries = (LegendEntry[])var4.toArray(new LegendEntry[var4.size()]);
   }

   public void setExtra(LegendEntry[] var1) {
      LegendEntry[] var2 = var1;
      if(var1 == null) {
         var2 = new LegendEntry[0];
      }

      this.mExtraEntries = var2;
   }

   public void setForm(Legend.LegendForm var1) {
      this.mShape = var1;
   }

   public void setFormLineDashEffect(DashPathEffect var1) {
      this.mFormLineDashEffect = var1;
   }

   public void setFormLineWidth(float var1) {
      this.mFormLineWidth = var1;
   }

   public void setFormSize(float var1) {
      this.mFormSize = var1;
   }

   public void setFormToTextSpace(float var1) {
      this.mFormToTextSpace = var1;
   }

   public void setHorizontalAlignment(Legend.LegendHorizontalAlignment var1) {
      this.mHorizontalAlignment = var1;
   }

   public void setMaxSizePercent(float var1) {
      this.mMaxSizePercent = var1;
   }

   public void setOrientation(Legend.LegendOrientation var1) {
      this.mOrientation = var1;
   }

   @Deprecated
   public void setPosition(Legend.LegendPosition var1) {
      Legend.LegendHorizontalAlignment var3;
      Legend.LegendVerticalAlignment var4;
      switch(null.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendPosition[var1.ordinal()]) {
      case 1:
      case 2:
      case 3:
         this.mHorizontalAlignment = Legend.LegendHorizontalAlignment.LEFT;
         if(var1 == Legend.LegendPosition.LEFT_OF_CHART_CENTER) {
            var4 = Legend.LegendVerticalAlignment.CENTER;
         } else {
            var4 = Legend.LegendVerticalAlignment.TOP;
         }

         this.mVerticalAlignment = var4;
         this.mOrientation = Legend.LegendOrientation.VERTICAL;
         break;
      case 4:
      case 5:
      case 6:
         this.mHorizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT;
         if(var1 == Legend.LegendPosition.RIGHT_OF_CHART_CENTER) {
            var4 = Legend.LegendVerticalAlignment.CENTER;
         } else {
            var4 = Legend.LegendVerticalAlignment.TOP;
         }

         this.mVerticalAlignment = var4;
         this.mOrientation = Legend.LegendOrientation.VERTICAL;
         break;
      case 7:
      case 8:
      case 9:
         if(var1 == Legend.LegendPosition.ABOVE_CHART_LEFT) {
            var3 = Legend.LegendHorizontalAlignment.LEFT;
         } else if(var1 == Legend.LegendPosition.ABOVE_CHART_RIGHT) {
            var3 = Legend.LegendHorizontalAlignment.RIGHT;
         } else {
            var3 = Legend.LegendHorizontalAlignment.CENTER;
         }

         this.mHorizontalAlignment = var3;
         this.mVerticalAlignment = Legend.LegendVerticalAlignment.TOP;
         this.mOrientation = Legend.LegendOrientation.HORIZONTAL;
         break;
      case 10:
      case 11:
      case 12:
         if(var1 == Legend.LegendPosition.BELOW_CHART_LEFT) {
            var3 = Legend.LegendHorizontalAlignment.LEFT;
         } else if(var1 == Legend.LegendPosition.BELOW_CHART_RIGHT) {
            var3 = Legend.LegendHorizontalAlignment.RIGHT;
         } else {
            var3 = Legend.LegendHorizontalAlignment.CENTER;
         }

         this.mHorizontalAlignment = var3;
         this.mVerticalAlignment = Legend.LegendVerticalAlignment.BOTTOM;
         this.mOrientation = Legend.LegendOrientation.HORIZONTAL;
         break;
      case 13:
         this.mHorizontalAlignment = Legend.LegendHorizontalAlignment.CENTER;
         this.mVerticalAlignment = Legend.LegendVerticalAlignment.CENTER;
         this.mOrientation = Legend.LegendOrientation.VERTICAL;
      }

      boolean var2;
      if(var1 != Legend.LegendPosition.LEFT_OF_CHART_INSIDE && var1 != Legend.LegendPosition.RIGHT_OF_CHART_INSIDE) {
         var2 = false;
      } else {
         var2 = true;
      }

      this.mDrawInside = var2;
   }

   public void setStackSpace(float var1) {
      this.mStackSpace = var1;
   }

   public void setVerticalAlignment(Legend.LegendVerticalAlignment var1) {
      this.mVerticalAlignment = var1;
   }

   public void setWordWrapEnabled(boolean var1) {
      this.mWordWrapEnabled = var1;
   }

   public void setXEntrySpace(float var1) {
      this.mXEntrySpace = var1;
   }

   public void setYEntrySpace(float var1) {
      this.mYEntrySpace = var1;
   }

   public static enum LegendOrientation {

      // $FF: synthetic field
      private static final Legend.LegendOrientation[] $VALUES = new Legend.LegendOrientation[]{HORIZONTAL, VERTICAL};
      HORIZONTAL("HORIZONTAL", 0),
      VERTICAL("VERTICAL", 1);


      private LegendOrientation(String var1, int var2) {}
   }

   @Deprecated
   public static enum LegendPosition {

      // $FF: synthetic field
      private static final Legend.LegendPosition[] $VALUES = new Legend.LegendPosition[]{RIGHT_OF_CHART, RIGHT_OF_CHART_CENTER, RIGHT_OF_CHART_INSIDE, LEFT_OF_CHART, LEFT_OF_CHART_CENTER, LEFT_OF_CHART_INSIDE, BELOW_CHART_LEFT, BELOW_CHART_RIGHT, BELOW_CHART_CENTER, ABOVE_CHART_LEFT, ABOVE_CHART_RIGHT, ABOVE_CHART_CENTER, PIECHART_CENTER};
      ABOVE_CHART_CENTER("ABOVE_CHART_CENTER", 11),
      ABOVE_CHART_LEFT("ABOVE_CHART_LEFT", 9),
      ABOVE_CHART_RIGHT("ABOVE_CHART_RIGHT", 10),
      BELOW_CHART_CENTER("BELOW_CHART_CENTER", 8),
      BELOW_CHART_LEFT("BELOW_CHART_LEFT", 6),
      BELOW_CHART_RIGHT("BELOW_CHART_RIGHT", 7),
      LEFT_OF_CHART("LEFT_OF_CHART", 3),
      LEFT_OF_CHART_CENTER("LEFT_OF_CHART_CENTER", 4),
      LEFT_OF_CHART_INSIDE("LEFT_OF_CHART_INSIDE", 5),
      PIECHART_CENTER("PIECHART_CENTER", 12),
      RIGHT_OF_CHART("RIGHT_OF_CHART", 0),
      RIGHT_OF_CHART_CENTER("RIGHT_OF_CHART_CENTER", 1),
      RIGHT_OF_CHART_INSIDE("RIGHT_OF_CHART_INSIDE", 2);


      private LegendPosition(String var1, int var2) {}
   }

   public static enum LegendVerticalAlignment {

      // $FF: synthetic field
      private static final Legend.LegendVerticalAlignment[] $VALUES = new Legend.LegendVerticalAlignment[]{TOP, CENTER, BOTTOM};
      BOTTOM("BOTTOM", 2),
      CENTER("CENTER", 1),
      TOP("TOP", 0);


      private LegendVerticalAlignment(String var1, int var2) {}
   }

   public static enum LegendForm {

      // $FF: synthetic field
      private static final Legend.LegendForm[] $VALUES = new Legend.LegendForm[]{NONE, EMPTY, DEFAULT, SQUARE, CIRCLE, LINE};
      CIRCLE("CIRCLE", 4),
      DEFAULT("DEFAULT", 2),
      EMPTY("EMPTY", 1),
      LINE("LINE", 5),
      NONE("NONE", 0),
      SQUARE("SQUARE", 3);


      private LegendForm(String var1, int var2) {}
   }

   public static enum LegendHorizontalAlignment {

      // $FF: synthetic field
      private static final Legend.LegendHorizontalAlignment[] $VALUES = new Legend.LegendHorizontalAlignment[]{LEFT, CENTER, RIGHT};
      CENTER("CENTER", 1),
      LEFT("LEFT", 0),
      RIGHT("RIGHT", 2);


      private LegendHorizontalAlignment(String var1, int var2) {}
   }

   public static enum LegendDirection {

      // $FF: synthetic field
      private static final Legend.LegendDirection[] $VALUES = new Legend.LegendDirection[]{LEFT_TO_RIGHT, RIGHT_TO_LEFT};
      LEFT_TO_RIGHT("LEFT_TO_RIGHT", 0),
      RIGHT_TO_LEFT("RIGHT_TO_LEFT", 1);


      private LegendDirection(String var1, int var2) {}
   }
}
