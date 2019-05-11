package com.facebook.litho.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.widget.ImageView.ScaleType;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.Diff;
import com.facebook.litho.DrawableMatrix;
import com.facebook.litho.MatrixDrawable;
import com.facebook.litho.Output;
import com.facebook.litho.Size;
import com.facebook.litho.SizeSpec;
import com.facebook.litho.annotations.MountSpec;
import com.facebook.litho.annotations.OnBind;
import com.facebook.litho.annotations.OnBoundsDefined;
import com.facebook.litho.annotations.OnCreateMountContent;
import com.facebook.litho.annotations.OnMeasure;
import com.facebook.litho.annotations.OnMount;
import com.facebook.litho.annotations.OnUnmount;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.ShouldUpdate;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.utils.MeasureUtils;
import com.facebook.litho.widget.TextureWarmer;

@MountSpec(
   isPureRender = true,
   poolSize = 30
)
class ImageSpec {

   private static final ScaleType[] SCALE_TYPE = ScaleType.values();


   @OnBind
   static void onBind(ComponentContext var0, MatrixDrawable var1, Integer var2, Integer var3) {
      var1.bind(var2.intValue(), var3.intValue());
   }

   @OnBoundsDefined
   static void onBoundsDefined(ComponentContext var0, ComponentLayout var1, 
      @Prop(
         resType = ResType.DRAWABLE
      ) Drawable var2, 
      @Prop(
         optional = true
      ) ScaleType var3, Output<DrawableMatrix> var4, Output<Integer> var5, Output<Integer> var6) {
      int var7 = var1.getPaddingLeft() + var1.getPaddingRight();
      int var8 = var1.getPaddingTop() + var1.getPaddingBottom();
      if(ScaleType.FIT_XY != var3 && var2 != null && var2.getIntrinsicWidth() > 0 && var2.getIntrinsicHeight() > 0) {
         var4.set(DrawableMatrix.create(var2, var3, var1.getWidth() - var7, var1.getHeight() - var8));
         var5.set(Integer.valueOf(var2.getIntrinsicWidth()));
         var6.set(Integer.valueOf(var2.getIntrinsicHeight()));
      } else {
         var4.set((Object)null);
         var5.set(Integer.valueOf(var1.getWidth() - var7));
         var6.set(Integer.valueOf(var1.getHeight() - var8));
      }
   }

   @OnCreateMountContent
   static MatrixDrawable onCreateMountContent(Context var0) {
      return new MatrixDrawable();
   }

   static void onLoadStyle(ComponentContext var0, Output<Drawable> var1, Output<ScaleType> var2) {
      TypedArray var6 = var0.obtainStyledAttributes(com.facebook.litho.R.Image, 0);
      int var4 = var6.getIndexCount();

      for(int var3 = 0; var3 < var4; ++var3) {
         int var5 = var6.getIndex(var3);
         if(var5 == com.facebook.litho.R.Image_android_src) {
            var1.set(var0.getAndroidContext().getResources().getDrawable(var6.getResourceId(var5, 0)));
         } else if(var5 == com.facebook.litho.R.Image_android_scaleType) {
            var2.set(SCALE_TYPE[var6.getInteger(var5, -1)]);
         }
      }

      var6.recycle();
   }

   @OnMeasure
   static void onMeasure(ComponentContext var0, ComponentLayout var1, int var2, int var3, Size var4, 
      @Prop(
         resType = ResType.DRAWABLE
      ) Drawable var5) {
      if(var5 != null && var5.getIntrinsicWidth() > 0 && var5.getIntrinsicHeight() > 0) {
         int var6 = var5.getIntrinsicHeight();
         int var7 = var5.getIntrinsicWidth();
         if(SizeSpec.getMode(var2) == 0 && SizeSpec.getMode(var3) == 0) {
            var4.width = var7;
            var4.height = var6;
         } else {
            MeasureUtils.measureWithAspectRatio(var2, var3, var7, var6, (float)var7 / (float)var6, var4);
            if(ComponentsConfiguration.prewarmImageTexture) {
               TextureWarmer.WarmDrawable var8 = new TextureWarmer.WarmDrawable(var5, var4.width, var4.height);
               TextureWarmer.getInstance().warmDrawable(var8);
            }

         }
      } else {
         var4.width = 0;
         var4.height = 0;
      }
   }

   @OnMount
   static void onMount(ComponentContext var0, MatrixDrawable var1, 
      @Prop(
         resType = ResType.DRAWABLE
      ) Drawable var2, DrawableMatrix var3) {
      var1.mount(var2, var3);
   }

   @OnUnmount
   static void onUnmount(ComponentContext var0, MatrixDrawable var1, 
      @Prop(
         resType = ResType.DRAWABLE
      ) Drawable var2) {
      var1.unmount();
   }

   @ShouldUpdate(
      onMount = true
   )
   static boolean shouldUpdate(
      @Prop(
         optional = true
      ) Diff<ScaleType> var0, 
      @Prop(
         resType = ResType.DRAWABLE
      ) Diff<Drawable> var1) {
      return var0.getNext() != var0.getPrevious() || var1.getNext() != var1.getPrevious();
   }
}
