package android.support.design.widget;

import android.animation.TimeInterpolator;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.design.animation.AnimationUtils;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.TintTypedArray;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class CollapsingTextHelper {

   private static final boolean DEBUG_DRAW = false;
   private static final Paint DEBUG_DRAW_PAINT;
   private static final boolean USE_SCALING_TEXTURE;
   private boolean boundsChanged;
   private final Rect collapsedBounds;
   private float collapsedDrawX;
   private float collapsedDrawY;
   private int collapsedShadowColor;
   private float collapsedShadowDx;
   private float collapsedShadowDy;
   private float collapsedShadowRadius;
   private ColorStateList collapsedTextColor;
   private int collapsedTextGravity = 16;
   private float collapsedTextSize = 15.0F;
   private Typeface collapsedTypeface;
   private final RectF currentBounds;
   private float currentDrawX;
   private float currentDrawY;
   private float currentTextSize;
   private Typeface currentTypeface;
   private boolean drawTitle;
   private final Rect expandedBounds;
   private float expandedDrawX;
   private float expandedDrawY;
   private float expandedFraction;
   private int expandedShadowColor;
   private float expandedShadowDx;
   private float expandedShadowDy;
   private float expandedShadowRadius;
   private ColorStateList expandedTextColor;
   private int expandedTextGravity = 16;
   private float expandedTextSize = 15.0F;
   private Bitmap expandedTitleTexture;
   private Typeface expandedTypeface;
   private boolean isRtl;
   private TimeInterpolator positionInterpolator;
   private float scale;
   private int[] state;
   private CharSequence text;
   private final TextPaint textPaint;
   private TimeInterpolator textSizeInterpolator;
   private CharSequence textToDraw;
   private float textureAscent;
   private float textureDescent;
   private Paint texturePaint;
   private final TextPaint tmpPaint;
   private boolean useTexture;
   private final View view;


   static {
      boolean var0;
      if(VERSION.SDK_INT < 18) {
         var0 = true;
      } else {
         var0 = false;
      }

      USE_SCALING_TEXTURE = var0;
      if(DEBUG_DRAW_PAINT != null) {
         DEBUG_DRAW_PAINT.setAntiAlias(true);
         DEBUG_DRAW_PAINT.setColor(-65281);
      }

   }

   public CollapsingTextHelper(View var1) {
      this.view = var1;
      this.textPaint = new TextPaint(129);
      this.tmpPaint = new TextPaint(this.textPaint);
      this.collapsedBounds = new Rect();
      this.expandedBounds = new Rect();
      this.currentBounds = new RectF();
   }

   private static int blendColors(int var0, int var1, float var2) {
      float var3 = 1.0F - var2;
      float var4 = (float)Color.alpha(var0);
      float var5 = (float)Color.alpha(var1);
      float var6 = (float)Color.red(var0);
      float var7 = (float)Color.red(var1);
      float var8 = (float)Color.green(var0);
      float var9 = (float)Color.green(var1);
      float var10 = (float)Color.blue(var0);
      float var11 = (float)Color.blue(var1);
      return Color.argb((int)(var4 * var3 + var5 * var2), (int)(var6 * var3 + var7 * var2), (int)(var8 * var3 + var9 * var2), (int)(var10 * var3 + var11 * var2));
   }

   private void calculateBaseOffsets() {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:820)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
   }

   private void calculateCurrentOffsets() {
      this.calculateOffsets(this.expandedFraction);
   }

   private boolean calculateIsRtl(CharSequence var1) {
      int var3 = ViewCompat.getLayoutDirection(this.view);
      boolean var2 = true;
      if(var3 != 1) {
         var2 = false;
      }

      TextDirectionHeuristicCompat var4;
      if(var2) {
         var4 = TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL;
      } else {
         var4 = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
      }

      return var4.isRtl(var1, 0, var1.length());
   }

   private void calculateOffsets(float var1) {
      this.interpolateBounds(var1);
      this.currentDrawX = lerp(this.expandedDrawX, this.collapsedDrawX, var1, this.positionInterpolator);
      this.currentDrawY = lerp(this.expandedDrawY, this.collapsedDrawY, var1, this.positionInterpolator);
      this.setInterpolatedTextSize(lerp(this.expandedTextSize, this.collapsedTextSize, var1, this.textSizeInterpolator));
      if(this.collapsedTextColor != this.expandedTextColor) {
         this.textPaint.setColor(blendColors(this.getCurrentExpandedTextColor(), this.getCurrentCollapsedTextColor(), var1));
      } else {
         this.textPaint.setColor(this.getCurrentCollapsedTextColor());
      }

      this.textPaint.setShadowLayer(lerp(this.expandedShadowRadius, this.collapsedShadowRadius, var1, (TimeInterpolator)null), lerp(this.expandedShadowDx, this.collapsedShadowDx, var1, (TimeInterpolator)null), lerp(this.expandedShadowDy, this.collapsedShadowDy, var1, (TimeInterpolator)null), blendColors(this.expandedShadowColor, this.collapsedShadowColor, var1));
      ViewCompat.postInvalidateOnAnimation(this.view);
   }

   private void calculateUsingTextSize(float var1) {
      if(this.text != null) {
         float var3 = (float)this.collapsedBounds.width();
         float var4 = (float)this.expandedBounds.width();
         boolean var8 = isClose(var1, this.collapsedTextSize);
         boolean var7 = true;
         float var2;
         boolean var5;
         if(var8) {
            var2 = this.collapsedTextSize;
            this.scale = 1.0F;
            if(this.currentTypeface != this.collapsedTypeface) {
               this.currentTypeface = this.collapsedTypeface;
               var5 = true;
            } else {
               var5 = false;
            }

            var1 = var3;
         } else {
            var2 = this.expandedTextSize;
            if(this.currentTypeface != this.expandedTypeface) {
               this.currentTypeface = this.expandedTypeface;
               var5 = true;
            } else {
               var5 = false;
            }

            if(isClose(var1, this.expandedTextSize)) {
               this.scale = 1.0F;
            } else {
               this.scale = var1 / this.expandedTextSize;
            }

            var1 = this.collapsedTextSize / this.expandedTextSize;
            if(var4 * var1 > var3) {
               var1 = Math.min(var3 / var1, var4);
            } else {
               var1 = var4;
            }
         }

         boolean var6 = var5;
         if(var1 > 0.0F) {
            if(this.currentTextSize == var2 && !this.boundsChanged && !var5) {
               var5 = false;
            } else {
               var5 = true;
            }

            this.currentTextSize = var2;
            this.boundsChanged = false;
            var6 = var5;
         }

         if(this.textToDraw == null || var6) {
            this.textPaint.setTextSize(this.currentTextSize);
            this.textPaint.setTypeface(this.currentTypeface);
            TextPaint var9 = this.textPaint;
            if(this.scale == 1.0F) {
               var7 = false;
            }

            var9.setLinearText(var7);
            CharSequence var10 = TextUtils.ellipsize(this.text, this.textPaint, var1, TruncateAt.END);
            if(!TextUtils.equals(var10, this.textToDraw)) {
               this.textToDraw = var10;
               this.isRtl = this.calculateIsRtl(this.textToDraw);
            }
         }

      }
   }

   private void clearTexture() {
      if(this.expandedTitleTexture != null) {
         this.expandedTitleTexture.recycle();
         this.expandedTitleTexture = null;
      }

   }

   private void ensureExpandedTexture() {
      if(this.expandedTitleTexture == null && !this.expandedBounds.isEmpty()) {
         if(!TextUtils.isEmpty(this.textToDraw)) {
            this.calculateOffsets(0.0F);
            this.textureAscent = this.textPaint.ascent();
            this.textureDescent = this.textPaint.descent();
            int var1 = Math.round(this.textPaint.measureText(this.textToDraw, 0, this.textToDraw.length()));
            int var2 = Math.round(this.textureDescent - this.textureAscent);
            if(var1 > 0) {
               if(var2 > 0) {
                  this.expandedTitleTexture = Bitmap.createBitmap(var1, var2, Config.ARGB_8888);
                  (new Canvas(this.expandedTitleTexture)).drawText(this.textToDraw, 0, this.textToDraw.length(), 0.0F, (float)var2 - this.textPaint.descent(), this.textPaint);
                  if(this.texturePaint == null) {
                     this.texturePaint = new Paint(3);
                  }

               }
            }
         }
      }
   }

   @ColorInt
   private int getCurrentExpandedTextColor() {
      return this.state != null?this.expandedTextColor.getColorForState(this.state, 0):this.expandedTextColor.getDefaultColor();
   }

   private void getTextPaintCollapsed(TextPaint var1) {
      var1.setTextSize(this.collapsedTextSize);
      var1.setTypeface(this.collapsedTypeface);
   }

   private void interpolateBounds(float var1) {
      this.currentBounds.left = lerp((float)this.expandedBounds.left, (float)this.collapsedBounds.left, var1, this.positionInterpolator);
      this.currentBounds.top = lerp(this.expandedDrawY, this.collapsedDrawY, var1, this.positionInterpolator);
      this.currentBounds.right = lerp((float)this.expandedBounds.right, (float)this.collapsedBounds.right, var1, this.positionInterpolator);
      this.currentBounds.bottom = lerp((float)this.expandedBounds.bottom, (float)this.collapsedBounds.bottom, var1, this.positionInterpolator);
   }

   private static boolean isClose(float var0, float var1) {
      return Math.abs(var0 - var1) < 0.001F;
   }

   private static float lerp(float var0, float var1, float var2, TimeInterpolator var3) {
      float var4 = var2;
      if(var3 != null) {
         var4 = var3.getInterpolation(var2);
      }

      return AnimationUtils.lerp(var0, var1, var4);
   }

   private Typeface readFontFamilyTypeface(int param1) {
      // $FF: Couldn't be decompiled
   }

   private static boolean rectEquals(Rect var0, int var1, int var2, int var3, int var4) {
      return var0.left == var1 && var0.top == var2 && var0.right == var3 && var0.bottom == var4;
   }

   private void setInterpolatedTextSize(float var1) {
      this.calculateUsingTextSize(var1);
      boolean var2;
      if(USE_SCALING_TEXTURE && this.scale != 1.0F) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.useTexture = var2;
      if(this.useTexture) {
         this.ensureExpandedTexture();
      }

      ViewCompat.postInvalidateOnAnimation(this.view);
   }

   public float calculateCollapsedTextWidth() {
      if(this.text == null) {
         return 0.0F;
      } else {
         this.getTextPaintCollapsed(this.tmpPaint);
         return this.tmpPaint.measureText(this.text, 0, this.text.length());
      }
   }

   public void draw(Canvas var1) {
      int var7 = var1.save();
      if(this.textToDraw != null && this.drawTitle) {
         float var5 = this.currentDrawX;
         float var4 = this.currentDrawY;
         boolean var6;
         if(this.useTexture && this.expandedTitleTexture != null) {
            var6 = true;
         } else {
            var6 = false;
         }

         float var2;
         float var3;
         if(var6) {
            var2 = this.textureAscent * this.scale;
            var3 = this.textureDescent;
            var3 = this.scale;
         } else {
            var2 = this.textPaint.ascent() * this.scale;
            this.textPaint.descent();
            var3 = this.scale;
         }

         var3 = var4;
         if(var6) {
            var3 = var4 + var2;
         }

         if(this.scale != 1.0F) {
            var1.scale(this.scale, this.scale, var5, var3);
         }

         if(var6) {
            var1.drawBitmap(this.expandedTitleTexture, var5, var3, this.texturePaint);
         } else {
            var1.drawText(this.textToDraw, 0, this.textToDraw.length(), var5, var3, this.textPaint);
         }
      }

      var1.restoreToCount(var7);
   }

   public void getCollapsedTextActualBounds(RectF var1) {
      boolean var3 = this.calculateIsRtl(this.text);
      float var2;
      if(!var3) {
         var2 = (float)this.collapsedBounds.left;
      } else {
         var2 = (float)this.collapsedBounds.right - this.calculateCollapsedTextWidth();
      }

      var1.left = var2;
      var1.top = (float)this.collapsedBounds.top;
      if(!var3) {
         var2 = var1.left + this.calculateCollapsedTextWidth();
      } else {
         var2 = (float)this.collapsedBounds.right;
      }

      var1.right = var2;
      var1.bottom = (float)this.collapsedBounds.top + this.getCollapsedTextHeight();
   }

   public ColorStateList getCollapsedTextColor() {
      return this.collapsedTextColor;
   }

   public int getCollapsedTextGravity() {
      return this.collapsedTextGravity;
   }

   public float getCollapsedTextHeight() {
      this.getTextPaintCollapsed(this.tmpPaint);
      return -this.tmpPaint.ascent();
   }

   public float getCollapsedTextSize() {
      return this.collapsedTextSize;
   }

   public Typeface getCollapsedTypeface() {
      return this.collapsedTypeface != null?this.collapsedTypeface:Typeface.DEFAULT;
   }

   @ColorInt
   @VisibleForTesting
   public int getCurrentCollapsedTextColor() {
      return this.state != null?this.collapsedTextColor.getColorForState(this.state, 0):this.collapsedTextColor.getDefaultColor();
   }

   public ColorStateList getExpandedTextColor() {
      return this.expandedTextColor;
   }

   public int getExpandedTextGravity() {
      return this.expandedTextGravity;
   }

   public float getExpandedTextSize() {
      return this.expandedTextSize;
   }

   public Typeface getExpandedTypeface() {
      return this.expandedTypeface != null?this.expandedTypeface:Typeface.DEFAULT;
   }

   public float getExpansionFraction() {
      return this.expandedFraction;
   }

   public CharSequence getText() {
      return this.text;
   }

   public final boolean isStateful() {
      return this.collapsedTextColor != null && this.collapsedTextColor.isStateful() || this.expandedTextColor != null && this.expandedTextColor.isStateful();
   }

   void onBoundsChanged() {
      boolean var1;
      if(this.collapsedBounds.width() > 0 && this.collapsedBounds.height() > 0 && this.expandedBounds.width() > 0 && this.expandedBounds.height() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      this.drawTitle = var1;
   }

   public void recalculate() {
      if(this.view.getHeight() > 0 && this.view.getWidth() > 0) {
         this.calculateBaseOffsets();
         this.calculateCurrentOffsets();
      }

   }

   public void setCollapsedBounds(int var1, int var2, int var3, int var4) {
      if(!rectEquals(this.collapsedBounds, var1, var2, var3, var4)) {
         this.collapsedBounds.set(var1, var2, var3, var4);
         this.boundsChanged = true;
         this.onBoundsChanged();
      }

   }

   public void setCollapsedTextAppearance(int var1) {
      TintTypedArray var2 = TintTypedArray.obtainStyledAttributes(this.view.getContext(), var1, R.styleable.TextAppearance);
      if(var2.hasValue(R.styleable.TextAppearance_android_textColor)) {
         this.collapsedTextColor = var2.getColorStateList(R.styleable.TextAppearance_android_textColor);
      }

      if(var2.hasValue(R.styleable.TextAppearance_android_textSize)) {
         this.collapsedTextSize = (float)var2.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, (int)this.collapsedTextSize);
      }

      this.collapsedShadowColor = var2.getInt(R.styleable.TextAppearance_android_shadowColor, 0);
      this.collapsedShadowDx = var2.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0F);
      this.collapsedShadowDy = var2.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0F);
      this.collapsedShadowRadius = var2.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0F);
      var2.recycle();
      if(VERSION.SDK_INT >= 16) {
         this.collapsedTypeface = this.readFontFamilyTypeface(var1);
      }

      this.recalculate();
   }

   public void setCollapsedTextColor(ColorStateList var1) {
      if(this.collapsedTextColor != var1) {
         this.collapsedTextColor = var1;
         this.recalculate();
      }

   }

   public void setCollapsedTextGravity(int var1) {
      if(this.collapsedTextGravity != var1) {
         this.collapsedTextGravity = var1;
         this.recalculate();
      }

   }

   public void setCollapsedTextSize(float var1) {
      if(this.collapsedTextSize != var1) {
         this.collapsedTextSize = var1;
         this.recalculate();
      }

   }

   public void setCollapsedTypeface(Typeface var1) {
      if(this.collapsedTypeface != var1) {
         this.collapsedTypeface = var1;
         this.recalculate();
      }

   }

   public void setExpandedBounds(int var1, int var2, int var3, int var4) {
      if(!rectEquals(this.expandedBounds, var1, var2, var3, var4)) {
         this.expandedBounds.set(var1, var2, var3, var4);
         this.boundsChanged = true;
         this.onBoundsChanged();
      }

   }

   public void setExpandedTextAppearance(int var1) {
      TintTypedArray var2 = TintTypedArray.obtainStyledAttributes(this.view.getContext(), var1, R.styleable.TextAppearance);
      if(var2.hasValue(R.styleable.TextAppearance_android_textColor)) {
         this.expandedTextColor = var2.getColorStateList(R.styleable.TextAppearance_android_textColor);
      }

      if(var2.hasValue(R.styleable.TextAppearance_android_textSize)) {
         this.expandedTextSize = (float)var2.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, (int)this.expandedTextSize);
      }

      this.expandedShadowColor = var2.getInt(R.styleable.TextAppearance_android_shadowColor, 0);
      this.expandedShadowDx = var2.getFloat(R.styleable.TextAppearance_android_shadowDx, 0.0F);
      this.expandedShadowDy = var2.getFloat(R.styleable.TextAppearance_android_shadowDy, 0.0F);
      this.expandedShadowRadius = var2.getFloat(R.styleable.TextAppearance_android_shadowRadius, 0.0F);
      var2.recycle();
      if(VERSION.SDK_INT >= 16) {
         this.expandedTypeface = this.readFontFamilyTypeface(var1);
      }

      this.recalculate();
   }

   public void setExpandedTextColor(ColorStateList var1) {
      if(this.expandedTextColor != var1) {
         this.expandedTextColor = var1;
         this.recalculate();
      }

   }

   public void setExpandedTextGravity(int var1) {
      if(this.expandedTextGravity != var1) {
         this.expandedTextGravity = var1;
         this.recalculate();
      }

   }

   public void setExpandedTextSize(float var1) {
      if(this.expandedTextSize != var1) {
         this.expandedTextSize = var1;
         this.recalculate();
      }

   }

   public void setExpandedTypeface(Typeface var1) {
      if(this.expandedTypeface != var1) {
         this.expandedTypeface = var1;
         this.recalculate();
      }

   }

   public void setExpansionFraction(float var1) {
      var1 = android.support.v4.math.MathUtils.clamp(var1, 0.0F, 1.0F);
      if(var1 != this.expandedFraction) {
         this.expandedFraction = var1;
         this.calculateCurrentOffsets();
      }

   }

   public void setPositionInterpolator(TimeInterpolator var1) {
      this.positionInterpolator = var1;
      this.recalculate();
   }

   public final boolean setState(int[] var1) {
      this.state = var1;
      if(this.isStateful()) {
         this.recalculate();
         return true;
      } else {
         return false;
      }
   }

   public void setText(CharSequence var1) {
      if(var1 == null || !var1.equals(this.text)) {
         this.text = var1;
         this.textToDraw = null;
         this.clearTexture();
         this.recalculate();
      }

   }

   public void setTextSizeInterpolator(TimeInterpolator var1) {
      this.textSizeInterpolator = var1;
      this.recalculate();
   }

   public void setTypefaces(Typeface var1) {
      this.expandedTypeface = var1;
      this.collapsedTypeface = var1;
      this.recalculate();
   }
}
