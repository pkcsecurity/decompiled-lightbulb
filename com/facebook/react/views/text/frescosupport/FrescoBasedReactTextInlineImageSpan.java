package com.facebook.react.views.text.frescosupport;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.TextView;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.fresco.ReactNetworkImageRequest;
import com.facebook.react.views.text.TextInlineImageSpan;
import javax.annotation.Nullable;

public class FrescoBasedReactTextInlineImageSpan extends TextInlineImageSpan {

   @Nullable
   private final Object mCallerContext;
   @Nullable
   private Drawable mDrawable;
   private final AbstractDraweeControllerBuilder mDraweeControllerBuilder;
   private final DraweeHolder<GenericDraweeHierarchy> mDraweeHolder;
   private ReadableMap mHeaders;
   private int mHeight;
   @Nullable
   private TextView mTextView;
   private Uri mUri;
   private int mWidth;


   public FrescoBasedReactTextInlineImageSpan(Resources var1, int var2, int var3, @Nullable Uri var4, ReadableMap var5, AbstractDraweeControllerBuilder var6, @Nullable Object var7) {
      this.mDraweeHolder = new DraweeHolder(GenericDraweeHierarchyBuilder.newInstance(var1).build());
      this.mDraweeControllerBuilder = var6;
      this.mCallerContext = var7;
      this.mHeight = var2;
      this.mWidth = var3;
      if(var4 == null) {
         var4 = Uri.EMPTY;
      }

      this.mUri = var4;
      this.mHeaders = var5;
   }

   public void draw(Canvas var1, CharSequence var2, int var3, int var4, float var5, int var6, int var7, int var8, Paint var9) {
      if(this.mDrawable == null) {
         ReactNetworkImageRequest var10 = ReactNetworkImageRequest.fromBuilderWithHeaders(ImageRequestBuilder.newBuilderWithSource(this.mUri), this.mHeaders);
         AbstractDraweeController var11 = this.mDraweeControllerBuilder.reset().setOldController(this.mDraweeHolder.getController()).setCallerContext(this.mCallerContext).setImageRequest(var10).build();
         this.mDraweeHolder.setController(var11);
         this.mDraweeControllerBuilder.reset();
         this.mDrawable = this.mDraweeHolder.getTopLevelDrawable();
         this.mDrawable.setBounds(0, 0, this.mWidth, this.mHeight);
         this.mDrawable.setCallback(this.mTextView);
      }

      var1.save();
      var1.translate(var5, (float)(var7 - this.mDrawable.getBounds().bottom));
      this.mDrawable.draw(var1);
      var1.restore();
   }

   @Nullable
   public Drawable getDrawable() {
      return this.mDrawable;
   }

   public int getHeight() {
      return this.mHeight;
   }

   public int getSize(Paint var1, CharSequence var2, int var3, int var4, FontMetricsInt var5) {
      if(var5 != null) {
         var5.ascent = -this.mHeight;
         var5.descent = 0;
         var5.top = var5.ascent;
         var5.bottom = 0;
      }

      return this.mWidth;
   }

   public int getWidth() {
      return this.mWidth;
   }

   public void onAttachedToWindow() {
      this.mDraweeHolder.onAttach();
   }

   public void onDetachedFromWindow() {
      this.mDraweeHolder.onDetach();
   }

   public void onFinishTemporaryDetach() {
      this.mDraweeHolder.onAttach();
   }

   public void onStartTemporaryDetach() {
      this.mDraweeHolder.onDetach();
   }

   public void setTextView(TextView var1) {
      this.mTextView = var1;
   }
}
