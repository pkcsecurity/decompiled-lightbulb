package com.facebook.drawee.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchyInflater;
import com.facebook.drawee.view.DraweeView;
import javax.annotation.Nullable;

public class GenericDraweeView extends DraweeView<GenericDraweeHierarchy> {

   public GenericDraweeView(Context var1) {
      super(var1);
      this.inflateHierarchy(var1, (AttributeSet)null);
   }

   public GenericDraweeView(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.inflateHierarchy(var1, var2);
   }

   public GenericDraweeView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.inflateHierarchy(var1, var2);
   }

   @TargetApi(21)
   public GenericDraweeView(Context var1, AttributeSet var2, int var3, int var4) {
      super(var1, var2, var3, var4);
      this.inflateHierarchy(var1, var2);
   }

   public GenericDraweeView(Context var1, GenericDraweeHierarchy var2) {
      super(var1);
      this.setHierarchy(var2);
   }

   protected void inflateHierarchy(Context var1, @Nullable AttributeSet var2) {
      GenericDraweeHierarchyBuilder var3 = GenericDraweeHierarchyInflater.inflateBuilder(var1, var2);
      this.setAspectRatio(var3.getDesiredAspectRatio());
      this.setHierarchy(var3.build());
   }
}
