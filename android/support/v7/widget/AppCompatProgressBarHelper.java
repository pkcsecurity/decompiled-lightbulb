package android.support.v7.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.graphics.drawable.WrappedDrawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.widget.ProgressBar;

class AppCompatProgressBarHelper {

   private static final int[] TINT_ATTRS = new int[]{16843067, 16843068};
   private Bitmap mSampleTile;
   private final ProgressBar mView;


   AppCompatProgressBarHelper(ProgressBar var1) {
      this.mView = var1;
   }

   private Shape getDrawableShape() {
      return new RoundRectShape(new float[]{5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F}, (RectF)null, (float[])null);
   }

   private Drawable tileify(Drawable var1, boolean var2) {
      Drawable var8;
      if(var1 instanceof WrappedDrawable) {
         WrappedDrawable var7 = (WrappedDrawable)var1;
         var8 = var7.getWrappedDrawable();
         if(var8 != null) {
            var7.setWrappedDrawable(this.tileify(var8, var2));
            return var1;
         }
      } else {
         if(var1 instanceof LayerDrawable) {
            LayerDrawable var10 = (LayerDrawable)var1;
            int var5 = var10.getNumberOfLayers();
            Drawable[] var12 = new Drawable[var5];
            byte var4 = 0;

            int var3;
            for(var3 = 0; var3 < var5; ++var3) {
               int var6 = var10.getId(var3);
               var8 = var10.getDrawable(var3);
               if(var6 != 16908301 && var6 != 16908303) {
                  var2 = false;
               } else {
                  var2 = true;
               }

               var12[var3] = this.tileify(var8, var2);
            }

            LayerDrawable var13 = new LayerDrawable(var12);

            for(var3 = var4; var3 < var5; ++var3) {
               var13.setId(var3, var10.getId(var3));
            }

            return var13;
         }

         if(var1 instanceof BitmapDrawable) {
            BitmapDrawable var9 = (BitmapDrawable)var1;
            Bitmap var14 = var9.getBitmap();
            if(this.mSampleTile == null) {
               this.mSampleTile = var14;
            }

            ShapeDrawable var11 = new ShapeDrawable(this.getDrawableShape());
            BitmapShader var15 = new BitmapShader(var14, TileMode.REPEAT, TileMode.CLAMP);
            var11.getPaint().setShader(var15);
            var11.getPaint().setColorFilter(var9.getPaint().getColorFilter());
            if(var2) {
               return new ClipDrawable(var11, 3, 1);
            }

            return var11;
         }
      }

      return var1;
   }

   private Drawable tileifyIndeterminate(Drawable var1) {
      Object var4 = var1;
      if(var1 instanceof AnimationDrawable) {
         AnimationDrawable var6 = (AnimationDrawable)var1;
         int var3 = var6.getNumberOfFrames();
         var4 = new AnimationDrawable();
         ((AnimationDrawable)var4).setOneShot(var6.isOneShot());

         for(int var2 = 0; var2 < var3; ++var2) {
            Drawable var5 = this.tileify(var6.getFrame(var2), true);
            var5.setLevel(10000);
            ((AnimationDrawable)var4).addFrame(var5, var6.getDuration(var2));
         }

         ((AnimationDrawable)var4).setLevel(10000);
      }

      return (Drawable)var4;
   }

   Bitmap getSampleTime() {
      return this.mSampleTile;
   }

   void loadFromAttributes(AttributeSet var1, int var2) {
      TintTypedArray var4 = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), var1, TINT_ATTRS, var2, 0);
      Drawable var3 = var4.getDrawableIfKnown(0);
      if(var3 != null) {
         this.mView.setIndeterminateDrawable(this.tileifyIndeterminate(var3));
      }

      var3 = var4.getDrawableIfKnown(1);
      if(var3 != null) {
         this.mView.setProgressDrawable(this.tileify(var3, false));
      }

      var4.recycle();
   }
}
