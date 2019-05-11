package com.facebook.react.flat;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView.SurfaceTextureListener;
import com.facebook.react.flat.AndroidView;
import com.facebook.react.flat.FlatShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.yoga.YogaUnit;
import com.facebook.yoga.YogaValue;
import javax.annotation.Nullable;

class FlatARTSurfaceViewShadowNode extends FlatShadowNode implements SurfaceTextureListener, AndroidView {

   private boolean mPaddingChanged = false;
   @Nullable
   private Surface mSurface;


   FlatARTSurfaceViewShadowNode() {
      this.forceMountToView();
      this.forceMountChildrenToView();
   }

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

   public boolean isPaddingChanged() {
      return this.mPaddingChanged;
   }

   public boolean isVirtual() {
      return false;
   }

   public boolean isVirtualAnchor() {
      return true;
   }

   public boolean needsCustomLayoutForChildren() {
      return false;
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

   public void resetPaddingChanged() {
      this.mPaddingChanged = false;
   }

   public void setPadding(int var1, float var2) {
      YogaValue var3 = this.getStylePadding(var1);
      if(var3.unit != YogaUnit.POINT || var3.value != var2) {
         super.setPadding(var1, var2);
         this.mPaddingChanged = true;
         this.markUpdated();
      }

   }

   public void setPaddingPercent(int var1, float var2) {
      YogaValue var3 = this.getStylePadding(var1);
      if(var3.unit != YogaUnit.PERCENT || var3.value != var2) {
         super.setPadding(var1, var2);
         this.mPaddingChanged = true;
         this.markUpdated();
      }

   }
}
