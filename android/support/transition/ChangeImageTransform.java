package android.support.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.transition.ImageViewUtils;
import android.support.transition.MatrixUtils;
import android.support.transition.Transition;
import android.support.transition.TransitionUtils;
import android.support.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.Map;

public class ChangeImageTransform extends Transition {

   private static final Property<ImageView, Matrix> ANIMATED_TRANSFORM_PROPERTY = new Property(Matrix.class, "animatedTransform") {
      public Matrix get(ImageView var1) {
         return null;
      }
      public void set(ImageView var1, Matrix var2) {
         ImageViewUtils.animateTransform(var1, var2);
      }
   };
   private static final TypeEvaluator<Matrix> NULL_MATRIX_EVALUATOR = new TypeEvaluator() {
      public Matrix evaluate(float var1, Matrix var2, Matrix var3) {
         return null;
      }
   };
   private static final String PROPNAME_BOUNDS = "android:changeImageTransform:bounds";
   private static final String PROPNAME_MATRIX = "android:changeImageTransform:matrix";
   private static final String[] sTransitionProperties = new String[]{"android:changeImageTransform:matrix", "android:changeImageTransform:bounds"};


   public ChangeImageTransform() {}

   public ChangeImageTransform(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   private void captureValues(TransitionValues var1) {
      View var2 = var1.view;
      if(var2 instanceof ImageView) {
         if(var2.getVisibility() == 0) {
            ImageView var3 = (ImageView)var2;
            if(var3.getDrawable() != null) {
               Map var4 = var1.values;
               var4.put("android:changeImageTransform:bounds", new Rect(var2.getLeft(), var2.getTop(), var2.getRight(), var2.getBottom()));
               var4.put("android:changeImageTransform:matrix", copyImageMatrix(var3));
            }
         }
      }
   }

   private static Matrix centerCropMatrix(ImageView var0) {
      Drawable var8 = var0.getDrawable();
      int var6 = var8.getIntrinsicWidth();
      float var1 = (float)var0.getWidth();
      float var2 = (float)var6;
      float var5 = var1 / var2;
      var6 = var8.getIntrinsicHeight();
      float var3 = (float)var0.getHeight();
      float var4 = (float)var6;
      var5 = Math.max(var5, var3 / var4);
      var6 = Math.round((var1 - var2 * var5) / 2.0F);
      int var7 = Math.round((var3 - var4 * var5) / 2.0F);
      Matrix var9 = new Matrix();
      var9.postScale(var5, var5);
      var9.postTranslate((float)var6, (float)var7);
      return var9;
   }

   private static Matrix copyImageMatrix(ImageView var0) {
      switch(null.$SwitchMap$android$widget$ImageView$ScaleType[var0.getScaleType().ordinal()]) {
      case 1:
         return fitXYMatrix(var0);
      case 2:
         return centerCropMatrix(var0);
      default:
         return new Matrix(var0.getImageMatrix());
      }
   }

   private ObjectAnimator createMatrixAnimator(ImageView var1, Matrix var2, Matrix var3) {
      return ObjectAnimator.ofObject(var1, ANIMATED_TRANSFORM_PROPERTY, new TransitionUtils.MatrixEvaluator(), new Matrix[]{var2, var3});
   }

   private ObjectAnimator createNullAnimator(ImageView var1) {
      return ObjectAnimator.ofObject(var1, ANIMATED_TRANSFORM_PROPERTY, NULL_MATRIX_EVALUATOR, new Matrix[]{null, null});
   }

   private static Matrix fitXYMatrix(ImageView var0) {
      Drawable var1 = var0.getDrawable();
      Matrix var2 = new Matrix();
      var2.postScale((float)var0.getWidth() / (float)var1.getIntrinsicWidth(), (float)var0.getHeight() / (float)var1.getIntrinsicHeight());
      return var2;
   }

   public void captureEndValues(@NonNull TransitionValues var1) {
      this.captureValues(var1);
   }

   public void captureStartValues(@NonNull TransitionValues var1) {
      this.captureValues(var1);
   }

   public Animator createAnimator(@NonNull ViewGroup var1, TransitionValues var2, TransitionValues var3) {
      if(var2 != null) {
         if(var3 == null) {
            return null;
         } else {
            Rect var8 = (Rect)var2.values.get("android:changeImageTransform:bounds");
            Rect var7 = (Rect)var3.values.get("android:changeImageTransform:bounds");
            if(var8 != null) {
               if(var7 == null) {
                  return null;
               } else {
                  Matrix var11 = (Matrix)var2.values.get("android:changeImageTransform:matrix");
                  Matrix var6 = (Matrix)var3.values.get("android:changeImageTransform:matrix");
                  boolean var4;
                  if((var11 != null || var6 != null) && (var11 == null || !var11.equals(var6))) {
                     var4 = false;
                  } else {
                     var4 = true;
                  }

                  if(var8.equals(var7) && var4) {
                     return null;
                  } else {
                     ImageView var13 = (ImageView)var3.view;
                     Drawable var9 = var13.getDrawable();
                     int var14 = var9.getIntrinsicWidth();
                     int var5 = var9.getIntrinsicHeight();
                     ImageViewUtils.startAnimateTransform(var13);
                     ObjectAnimator var10;
                     if(var14 != 0 && var5 != 0) {
                        Matrix var12 = var11;
                        if(var11 == null) {
                           var12 = MatrixUtils.IDENTITY_MATRIX;
                        }

                        var11 = var6;
                        if(var6 == null) {
                           var11 = MatrixUtils.IDENTITY_MATRIX;
                        }

                        ANIMATED_TRANSFORM_PROPERTY.set(var13, var12);
                        var10 = this.createMatrixAnimator(var13, var12, var11);
                     } else {
                        var10 = this.createNullAnimator(var13);
                     }

                     ImageViewUtils.reserveEndAnimateTransform(var13, var10);
                     return var10;
                  }
               }
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   public String[] getTransitionProperties() {
      return sTransitionProperties;
   }
}
