package com.facebook.drawee.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.drawee.view.GenericDraweeView;
import javax.annotation.Nullable;

public class SimpleDraweeView extends GenericDraweeView {

   private static Supplier<? extends SimpleDraweeControllerBuilder> sDraweeControllerBuilderSupplier;
   private SimpleDraweeControllerBuilder mSimpleDraweeControllerBuilder;


   public SimpleDraweeView(Context var1) {
      super(var1);
      this.init(var1, (AttributeSet)null);
   }

   public SimpleDraweeView(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.init(var1, var2);
   }

   public SimpleDraweeView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.init(var1, var2);
   }

   @TargetApi(21)
   public SimpleDraweeView(Context var1, AttributeSet var2, int var3, int var4) {
      super(var1, var2, var3, var4);
      this.init(var1, var2);
   }

   public SimpleDraweeView(Context var1, GenericDraweeHierarchy var2) {
      super(var1, var2);
      this.init(var1, (AttributeSet)null);
   }

   private void init(Context param1, @Nullable AttributeSet param2) {
      // $FF: Couldn't be decompiled
   }

   public static void initialize(Supplier<? extends SimpleDraweeControllerBuilder> var0) {
      sDraweeControllerBuilderSupplier = var0;
   }

   public static void shutDown() {
      sDraweeControllerBuilderSupplier = null;
   }

   protected SimpleDraweeControllerBuilder getControllerBuilder() {
      return this.mSimpleDraweeControllerBuilder;
   }

   public void setActualImageResource(@DrawableRes int var1) {
      this.setActualImageResource(var1, (Object)null);
   }

   public void setActualImageResource(@DrawableRes int var1, @Nullable Object var2) {
      this.setImageURI(UriUtil.getUriForResourceId(var1), var2);
   }

   public void setImageResource(int var1) {
      super.setImageResource(var1);
   }

   public void setImageURI(Uri var1) {
      this.setImageURI(var1, (Object)null);
   }

   public void setImageURI(Uri var1, @Nullable Object var2) {
      this.setController(this.mSimpleDraweeControllerBuilder.setCallerContext(var2).setUri(var1).setOldController(this.getController()).build());
   }

   public void setImageURI(@Nullable String var1) {
      this.setImageURI(var1, (Object)null);
   }

   public void setImageURI(@Nullable String var1, @Nullable Object var2) {
      Uri var3;
      if(var1 != null) {
         var3 = Uri.parse(var1);
      } else {
         var3 = null;
      }

      this.setImageURI(var3, var2);
   }
}
