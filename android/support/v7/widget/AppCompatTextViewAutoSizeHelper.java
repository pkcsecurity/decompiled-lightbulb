package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v7.appcompat.R;
import android.support.v7.widget.AppCompatEditText;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.Layout.Alignment;
import android.text.StaticLayout.Builder;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

class AppCompatTextViewAutoSizeHelper {

   private static final int DEFAULT_AUTO_SIZE_GRANULARITY_IN_PX = 1;
   private static final int DEFAULT_AUTO_SIZE_MAX_TEXT_SIZE_IN_SP = 112;
   private static final int DEFAULT_AUTO_SIZE_MIN_TEXT_SIZE_IN_SP = 12;
   private static final String TAG = "ACTVAutoSizeHelper";
   private static final RectF TEMP_RECTF = new RectF();
   static final float UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE = -1.0F;
   private static final int VERY_WIDE = 1048576;
   private static ConcurrentHashMap<String, Method> sTextViewMethodByNameCache = new ConcurrentHashMap();
   private float mAutoSizeMaxTextSizeInPx = -1.0F;
   private float mAutoSizeMinTextSizeInPx = -1.0F;
   private float mAutoSizeStepGranularityInPx = -1.0F;
   private int[] mAutoSizeTextSizesInPx = new int[0];
   private int mAutoSizeTextType = 0;
   private final Context mContext;
   private boolean mHasPresetAutoSizeValues = false;
   private boolean mNeedsAutoSizeText = false;
   private TextPaint mTempTextPaint;
   private final TextView mTextView;


   AppCompatTextViewAutoSizeHelper(TextView var1) {
      this.mTextView = var1;
      this.mContext = this.mTextView.getContext();
   }

   private int[] cleanupAutoSizePresetSizes(int[] var1) {
      int var4 = var1.length;
      if(var4 == 0) {
         return var1;
      } else {
         Arrays.sort(var1);
         ArrayList var6 = new ArrayList();
         byte var3 = 0;

         int var2;
         for(var2 = 0; var2 < var4; ++var2) {
            int var5 = var1[var2];
            if(var5 > 0 && Collections.binarySearch(var6, Integer.valueOf(var5)) < 0) {
               var6.add(Integer.valueOf(var5));
            }
         }

         if(var4 == var6.size()) {
            return var1;
         } else {
            var4 = var6.size();
            var1 = new int[var4];

            for(var2 = var3; var2 < var4; ++var2) {
               var1[var2] = ((Integer)var6.get(var2)).intValue();
            }

            return var1;
         }
      }
   }

   private void clearAutoSizeConfiguration() {
      this.mAutoSizeTextType = 0;
      this.mAutoSizeMinTextSizeInPx = -1.0F;
      this.mAutoSizeMaxTextSizeInPx = -1.0F;
      this.mAutoSizeStepGranularityInPx = -1.0F;
      this.mAutoSizeTextSizesInPx = new int[0];
      this.mNeedsAutoSizeText = false;
   }

   @RequiresApi(23)
   private StaticLayout createStaticLayoutForMeasuring(CharSequence var1, Alignment var2, int var3, int var4) {
      TextDirectionHeuristic var5 = (TextDirectionHeuristic)this.invokeAndReturnWithDefault(this.mTextView, "getTextDirectionHeuristic", TextDirectionHeuristics.FIRSTSTRONG_LTR);
      Builder var6 = Builder.obtain(var1, 0, var1.length(), this.mTempTextPaint, var3).setAlignment(var2).setLineSpacing(this.mTextView.getLineSpacingExtra(), this.mTextView.getLineSpacingMultiplier()).setIncludePad(this.mTextView.getIncludeFontPadding()).setBreakStrategy(this.mTextView.getBreakStrategy()).setHyphenationFrequency(this.mTextView.getHyphenationFrequency());
      var3 = var4;
      if(var4 == -1) {
         var3 = Integer.MAX_VALUE;
      }

      return var6.setMaxLines(var3).setTextDirection(var5).build();
   }

   private StaticLayout createStaticLayoutForMeasuringPre23(CharSequence var1, Alignment var2, int var3) {
      float var4;
      float var5;
      boolean var6;
      if(VERSION.SDK_INT >= 16) {
         var4 = this.mTextView.getLineSpacingMultiplier();
         var5 = this.mTextView.getLineSpacingExtra();
         var6 = this.mTextView.getIncludeFontPadding();
      } else {
         var4 = ((Float)this.invokeAndReturnWithDefault(this.mTextView, "getLineSpacingMultiplier", Float.valueOf(1.0F))).floatValue();
         var5 = ((Float)this.invokeAndReturnWithDefault(this.mTextView, "getLineSpacingExtra", Float.valueOf(0.0F))).floatValue();
         var6 = ((Boolean)this.invokeAndReturnWithDefault(this.mTextView, "getIncludeFontPadding", Boolean.valueOf(true))).booleanValue();
      }

      return new StaticLayout(var1, this.mTempTextPaint, var3, var2, var4, var5, var6);
   }

   private int findLargestTextSizeWhichFits(RectF var1) {
      int var2 = this.mAutoSizeTextSizesInPx.length;
      if(var2 == 0) {
         throw new IllegalStateException("No available text sizes to choose from.");
      } else {
         int var4 = var2 - 1;
         var2 = 1;
         int var3 = 0;

         while(var2 <= var4) {
            int var5 = (var2 + var4) / 2;
            if(this.suggestedSizeFitsInSpace(this.mAutoSizeTextSizesInPx[var5], var1)) {
               var3 = var2;
               var2 = var5 + 1;
            } else {
               var3 = var5 - 1;
               var4 = var3;
            }
         }

         return this.mAutoSizeTextSizesInPx[var3];
      }
   }

   @Nullable
   private Method getTextViewMethod(@NonNull String param1) {
      // $FF: Couldn't be decompiled
   }

   private <T extends Object> T invokeAndReturnWithDefault(@NonNull Object var1, @NonNull String var2, @NonNull T var3) {
      try {
         var1 = this.getTextViewMethod(var2).invoke(var1, new Object[0]);
         return var1;
      } catch (Exception var7) {
         StringBuilder var4 = new StringBuilder();
         var4.append("Failed to invoke TextView#");
         var4.append(var2);
         var4.append("() method");
         Log.w("ACTVAutoSizeHelper", var4.toString(), var7);
         return var3;
      } finally {
         ;
      }
   }

   private void setRawTextSize(float param1) {
      // $FF: Couldn't be decompiled
   }

   private boolean setupAutoSizeText() {
      boolean var4 = this.supportsAutoSizeText();
      int var3 = 0;
      if(var4 && this.mAutoSizeTextType == 1) {
         if(!this.mHasPresetAutoSizeValues || this.mAutoSizeTextSizesInPx.length == 0) {
            float var1 = (float)Math.round(this.mAutoSizeMinTextSizeInPx);

            int var2;
            for(var2 = 1; Math.round(this.mAutoSizeStepGranularityInPx + var1) <= Math.round(this.mAutoSizeMaxTextSizeInPx); var1 += this.mAutoSizeStepGranularityInPx) {
               ++var2;
            }

            int[] var5 = new int[var2];

            for(var1 = this.mAutoSizeMinTextSizeInPx; var3 < var2; ++var3) {
               var5[var3] = Math.round(var1);
               var1 += this.mAutoSizeStepGranularityInPx;
            }

            this.mAutoSizeTextSizesInPx = this.cleanupAutoSizePresetSizes(var5);
         }

         this.mNeedsAutoSizeText = true;
      } else {
         this.mNeedsAutoSizeText = false;
      }

      return this.mNeedsAutoSizeText;
   }

   private void setupAutoSizeUniformPresetSizes(TypedArray var1) {
      int var3 = var1.length();
      int[] var4 = new int[var3];
      if(var3 > 0) {
         for(int var2 = 0; var2 < var3; ++var2) {
            var4[var2] = var1.getDimensionPixelSize(var2, -1);
         }

         this.mAutoSizeTextSizesInPx = this.cleanupAutoSizePresetSizes(var4);
         this.setupAutoSizeUniformPresetSizesConfiguration();
      }

   }

   private boolean setupAutoSizeUniformPresetSizesConfiguration() {
      int var1 = this.mAutoSizeTextSizesInPx.length;
      boolean var2;
      if(var1 > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.mHasPresetAutoSizeValues = var2;
      if(this.mHasPresetAutoSizeValues) {
         this.mAutoSizeTextType = 1;
         this.mAutoSizeMinTextSizeInPx = (float)this.mAutoSizeTextSizesInPx[0];
         this.mAutoSizeMaxTextSizeInPx = (float)this.mAutoSizeTextSizesInPx[var1 - 1];
         this.mAutoSizeStepGranularityInPx = -1.0F;
      }

      return this.mHasPresetAutoSizeValues;
   }

   private boolean suggestedSizeFitsInSpace(int var1, RectF var2) {
      CharSequence var5 = this.mTextView.getText();
      TransformationMethod var6 = this.mTextView.getTransformationMethod();
      CharSequence var4 = var5;
      if(var6 != null) {
         CharSequence var9 = var6.getTransformation(var5, this.mTextView);
         var4 = var5;
         if(var9 != null) {
            var4 = var9;
         }
      }

      int var3;
      if(VERSION.SDK_INT >= 16) {
         var3 = this.mTextView.getMaxLines();
      } else {
         var3 = -1;
      }

      if(this.mTempTextPaint == null) {
         this.mTempTextPaint = new TextPaint();
      } else {
         this.mTempTextPaint.reset();
      }

      this.mTempTextPaint.set(this.mTextView.getPaint());
      this.mTempTextPaint.setTextSize((float)var1);
      Alignment var7 = (Alignment)this.invokeAndReturnWithDefault(this.mTextView, "getLayoutAlignment", Alignment.ALIGN_NORMAL);
      StaticLayout var8;
      if(VERSION.SDK_INT >= 23) {
         var8 = this.createStaticLayoutForMeasuring(var4, var7, Math.round(var2.right), var3);
      } else {
         var8 = this.createStaticLayoutForMeasuringPre23(var4, var7, Math.round(var2.right));
      }

      return var3 != -1 && (var8.getLineCount() > var3 || var8.getLineEnd(var8.getLineCount() - 1) != var4.length())?false:(float)var8.getHeight() <= var2.bottom;
   }

   private boolean supportsAutoSizeText() {
      return !(this.mTextView instanceof AppCompatEditText);
   }

   private void validateAndSetAutoSizeTextTypeUniformConfiguration(float var1, float var2, float var3) throws IllegalArgumentException {
      StringBuilder var4;
      if(var1 <= 0.0F) {
         var4 = new StringBuilder();
         var4.append("Minimum auto-size text size (");
         var4.append(var1);
         var4.append("px) is less or equal to (0px)");
         throw new IllegalArgumentException(var4.toString());
      } else if(var2 <= var1) {
         var4 = new StringBuilder();
         var4.append("Maximum auto-size text size (");
         var4.append(var2);
         var4.append("px) is less or equal to minimum auto-size ");
         var4.append("text size (");
         var4.append(var1);
         var4.append("px)");
         throw new IllegalArgumentException(var4.toString());
      } else if(var3 <= 0.0F) {
         var4 = new StringBuilder();
         var4.append("The auto-size step granularity (");
         var4.append(var3);
         var4.append("px) is less or equal to (0px)");
         throw new IllegalArgumentException(var4.toString());
      } else {
         this.mAutoSizeTextType = 1;
         this.mAutoSizeMinTextSizeInPx = var1;
         this.mAutoSizeMaxTextSizeInPx = var2;
         this.mAutoSizeStepGranularityInPx = var3;
         this.mHasPresetAutoSizeValues = false;
      }
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   void autoSizeText() {
      // $FF: Couldn't be decompiled
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   int getAutoSizeMaxTextSize() {
      return Math.round(this.mAutoSizeMaxTextSizeInPx);
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   int getAutoSizeMinTextSize() {
      return Math.round(this.mAutoSizeMinTextSizeInPx);
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   int getAutoSizeStepGranularity() {
      return Math.round(this.mAutoSizeStepGranularityInPx);
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   int[] getAutoSizeTextAvailableSizes() {
      return this.mAutoSizeTextSizesInPx;
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   int getAutoSizeTextType() {
      return this.mAutoSizeTextType;
   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   boolean isAutoSizeEnabled() {
      return this.supportsAutoSizeText() && this.mAutoSizeTextType != 0;
   }

   void loadFromAttributes(AttributeSet var1, int var2) {
      TypedArray var8 = this.mContext.obtainStyledAttributes(var1, R.styleable.AppCompatTextView, var2, 0);
      if(var8.hasValue(R.styleable.AppCompatTextView_autoSizeTextType)) {
         this.mAutoSizeTextType = var8.getInt(R.styleable.AppCompatTextView_autoSizeTextType, 0);
      }

      float var3;
      if(var8.hasValue(R.styleable.AppCompatTextView_autoSizeStepGranularity)) {
         var3 = var8.getDimension(R.styleable.AppCompatTextView_autoSizeStepGranularity, -1.0F);
      } else {
         var3 = -1.0F;
      }

      float var4;
      if(var8.hasValue(R.styleable.AppCompatTextView_autoSizeMinTextSize)) {
         var4 = var8.getDimension(R.styleable.AppCompatTextView_autoSizeMinTextSize, -1.0F);
      } else {
         var4 = -1.0F;
      }

      float var5;
      if(var8.hasValue(R.styleable.AppCompatTextView_autoSizeMaxTextSize)) {
         var5 = var8.getDimension(R.styleable.AppCompatTextView_autoSizeMaxTextSize, -1.0F);
      } else {
         var5 = -1.0F;
      }

      if(var8.hasValue(R.styleable.AppCompatTextView_autoSizePresetSizes)) {
         var2 = var8.getResourceId(R.styleable.AppCompatTextView_autoSizePresetSizes, 0);
         if(var2 > 0) {
            TypedArray var7 = var8.getResources().obtainTypedArray(var2);
            this.setupAutoSizeUniformPresetSizes(var7);
            var7.recycle();
         }
      }

      var8.recycle();
      if(this.supportsAutoSizeText()) {
         if(this.mAutoSizeTextType == 1) {
            if(!this.mHasPresetAutoSizeValues) {
               DisplayMetrics var9 = this.mContext.getResources().getDisplayMetrics();
               float var6 = var4;
               if(var4 == -1.0F) {
                  var6 = TypedValue.applyDimension(2, 12.0F, var9);
               }

               var4 = var5;
               if(var5 == -1.0F) {
                  var4 = TypedValue.applyDimension(2, 112.0F, var9);
               }

               var5 = var3;
               if(var3 == -1.0F) {
                  var5 = 1.0F;
               }

               this.validateAndSetAutoSizeTextTypeUniformConfiguration(var6, var4, var5);
            }

            this.setupAutoSizeText();
            return;
         }
      } else {
         this.mAutoSizeTextType = 0;
      }

   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   void setAutoSizeTextTypeUniformWithConfiguration(int var1, int var2, int var3, int var4) throws IllegalArgumentException {
      if(this.supportsAutoSizeText()) {
         DisplayMetrics var5 = this.mContext.getResources().getDisplayMetrics();
         this.validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(var4, (float)var1, var5), TypedValue.applyDimension(var4, (float)var2, var5), TypedValue.applyDimension(var4, (float)var3, var5));
         if(this.setupAutoSizeText()) {
            this.autoSizeText();
         }
      }

   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull int[] var1, int var2) throws IllegalArgumentException {
      if(this.supportsAutoSizeText()) {
         int var4 = var1.length;
         int var3 = 0;
         if(var4 > 0) {
            int[] var6 = new int[var4];
            int[] var5;
            if(var2 == 0) {
               var5 = Arrays.copyOf(var1, var4);
            } else {
               DisplayMetrics var7 = this.mContext.getResources().getDisplayMetrics();

               while(true) {
                  var5 = var6;
                  if(var3 >= var4) {
                     break;
                  }

                  var6[var3] = Math.round(TypedValue.applyDimension(var2, (float)var1[var3], var7));
                  ++var3;
               }
            }

            this.mAutoSizeTextSizesInPx = this.cleanupAutoSizePresetSizes(var5);
            if(!this.setupAutoSizeUniformPresetSizesConfiguration()) {
               StringBuilder var8 = new StringBuilder();
               var8.append("None of the preset sizes is valid: ");
               var8.append(Arrays.toString(var1));
               throw new IllegalArgumentException(var8.toString());
            }
         } else {
            this.mHasPresetAutoSizeValues = false;
         }

         if(this.setupAutoSizeText()) {
            this.autoSizeText();
         }
      }

   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   void setAutoSizeTextTypeWithDefaults(int var1) {
      if(this.supportsAutoSizeText()) {
         switch(var1) {
         case 0:
            this.clearAutoSizeConfiguration();
            break;
         case 1:
            DisplayMetrics var2 = this.mContext.getResources().getDisplayMetrics();
            this.validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(2, 12.0F, var2), TypedValue.applyDimension(2, 112.0F, var2), 1.0F);
            if(this.setupAutoSizeText()) {
               this.autoSizeText();
               return;
            }
            break;
         default:
            StringBuilder var3 = new StringBuilder();
            var3.append("Unknown auto-size text type: ");
            var3.append(var1);
            throw new IllegalArgumentException(var3.toString());
         }
      }

   }

   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   void setTextSizeInternal(int var1, float var2) {
      Resources var3;
      if(this.mContext == null) {
         var3 = Resources.getSystem();
      } else {
         var3 = this.mContext.getResources();
      }

      this.setRawTextSize(TypedValue.applyDimension(var1, var2, var3.getDisplayMetrics()));
   }
}
