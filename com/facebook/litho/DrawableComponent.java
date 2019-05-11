package com.facebook.litho;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.MatrixDrawable;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.reference.Reference;

class DrawableComponent<T extends Drawable> extends Component {

   Reference<T> mDrawable;
   int mDrawableHeight;
   int mDrawableWidth;


   private DrawableComponent(Reference var1) {
      super("DrawableComponent");
      this.mDrawable = var1;
   }

   public static DrawableComponent create(Reference<? extends Drawable> var0) {
      return new DrawableComponent(var0);
   }

   private Reference<T> getDrawable() {
      return this.mDrawable;
   }

   private int getDrawableHeight() {
      return this.mDrawableHeight;
   }

   private int getDrawableWidth() {
      return this.mDrawableWidth;
   }

   private void setDrawableHeight(int var1) {
      this.mDrawableHeight = var1;
   }

   private void setDrawableWidth(int var1) {
      this.mDrawableWidth = var1;
   }

   public ComponentLifecycle.MountType getMountType() {
      return ComponentLifecycle.MountType.DRAWABLE;
   }

   public boolean isEquivalentTo(Component var1) {
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         DrawableComponent var2 = (DrawableComponent)var1;
         return this.mDrawable.equals(var2.mDrawable);
      } else {
         return false;
      }
   }

   protected boolean isPureRender() {
      return true;
   }

   protected void onBind(ComponentContext var1, Object var2) {
      ((MatrixDrawable)var2).bind(this.getDrawableWidth(), this.getDrawableHeight());
   }

   protected void onBoundsDefined(ComponentContext var1, ComponentLayout var2) {
      this.setDrawableWidth(var2.getWidth());
      this.setDrawableHeight(var2.getHeight());
   }

   protected Object onCreateMountContent(Context var1) {
      return new MatrixDrawable();
   }

   protected void onMount(ComponentContext var1, Object var2) {
      ((MatrixDrawable)var2).mount((Drawable)Reference.acquire(var1.getAndroidContext(), this.getDrawable()));
   }

   protected void onUnmount(ComponentContext var1, Object var2) {
      MatrixDrawable var4 = (MatrixDrawable)var2;
      Drawable var3 = var4.getMountedDrawable();
      if(!ComponentsConfiguration.unmountThenReleaseDrawableCmp) {
         Reference.release(var1.getAndroidContext(), var3, this.getDrawable());
      }

      var4.unmount();
      if(ComponentsConfiguration.unmountThenReleaseDrawableCmp) {
         Reference.release(var1.getAndroidContext(), var3, this.getDrawable());
      }

   }

   protected boolean shouldUpdate(Component var1, Component var2) {
      return Reference.shouldUpdate(((DrawableComponent)var1).getDrawable(), ((DrawableComponent)var2).getDrawable());
   }
}
