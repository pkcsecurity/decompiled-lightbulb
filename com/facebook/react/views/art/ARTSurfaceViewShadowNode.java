package com.facebook.react.views.art;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView.SurfaceTextureListener;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.annotations.ReactProp;
import javax.annotation.Nullable;

public class ARTSurfaceViewShadowNode extends LayoutShadowNode implements SurfaceTextureListener {

   @Nullable
   private Integer mBackgroundColor;
   @Nullable
   private Surface mSurface;


   private void drawOutput() {
      // $FF: Couldn't be decompiled
   }

   private void markChildrenUpdatesSeen(ReactShadowNode var1) {
      for(int var2 = 0; var2 < var1.getChildCount(); ++var2) {
         ReactShadowNode var3 = var1.getChildAt(var2);
         var3.markUpdateSeen();
         this.markChildrenUpdatesSeen(var3);
      }

   }

   public boolean isVirtual() {
      return false;
   }

   public boolean isVirtualAnchor() {
      return true;
   }

   public void onCollectExtraUpdates(UIViewOperationQueue var1) {
      super.onCollectExtraUpdates(var1);
      this.drawOutput();
      var1.enqueueUpdateExtraData(this.getReactTag(), this);
   }

   public void onSurfaceTextureAvailable(SurfaceTexture var1, int var2, int var3) {
      this.mSurface = new Surface(var1);
      this.drawOutput();
   }

   public boolean onSurfaceTextureDestroyed(SurfaceTexture var1) {
      var1.release();
      this.mSurface = null;
      return true;
   }

   public void onSurfaceTextureSizeChanged(SurfaceTexture var1, int var2, int var3) {}

   public void onSurfaceTextureUpdated(SurfaceTexture var1) {}

   @ReactProp(
      customType = "Color",
      name = "backgroundColor"
   )
   public void setBackgroundColor(Integer var1) {
      this.mBackgroundColor = var1;
      this.markUpdated();
   }
}
