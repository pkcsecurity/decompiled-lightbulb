package com.facebook.react.views.toolbar;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.MeasureSpec;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.drawee.view.MultiDraweeHolder;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.views.toolbar.DrawableWithIntrinsicSize;
import javax.annotation.Nullable;

public class ReactToolbar extends Toolbar {

   private static final String PROP_ACTION_ICON = "icon";
   private static final String PROP_ACTION_SHOW = "show";
   private static final String PROP_ACTION_SHOW_WITH_TEXT = "showWithText";
   private static final String PROP_ACTION_TITLE = "title";
   private static final String PROP_ICON_HEIGHT = "height";
   private static final String PROP_ICON_URI = "uri";
   private static final String PROP_ICON_WIDTH = "width";
   private final MultiDraweeHolder<GenericDraweeHierarchy> mActionsHolder = new MultiDraweeHolder();
   private final Runnable mLayoutRunnable = new Runnable() {
      public void run() {
         ReactToolbar.this.measure(MeasureSpec.makeMeasureSpec(ReactToolbar.this.getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(ReactToolbar.this.getHeight(), 1073741824));
         ReactToolbar.this.layout(ReactToolbar.this.getLeft(), ReactToolbar.this.getTop(), ReactToolbar.this.getRight(), ReactToolbar.this.getBottom());
      }
   };
   private ReactToolbar.IconControllerListener mLogoControllerListener;
   private final DraweeHolder mLogoHolder;
   private ReactToolbar.IconControllerListener mNavIconControllerListener;
   private final DraweeHolder mNavIconHolder;
   private ReactToolbar.IconControllerListener mOverflowIconControllerListener;
   private final DraweeHolder mOverflowIconHolder;


   public ReactToolbar(Context var1) {
      super(var1);
      this.mLogoHolder = DraweeHolder.create(this.createDraweeHierarchy(), var1);
      this.mNavIconHolder = DraweeHolder.create(this.createDraweeHierarchy(), var1);
      this.mOverflowIconHolder = DraweeHolder.create(this.createDraweeHierarchy(), var1);
      this.mLogoControllerListener = new ReactToolbar.IconControllerListener(this.mLogoHolder) {
         protected void setDrawable(Drawable var1) {
            ReactToolbar.this.setLogo(var1);
         }
      };
      this.mNavIconControllerListener = new ReactToolbar.IconControllerListener(this.mNavIconHolder) {
         protected void setDrawable(Drawable var1) {
            ReactToolbar.this.setNavigationIcon(var1);
         }
      };
      this.mOverflowIconControllerListener = new ReactToolbar.IconControllerListener(this.mOverflowIconHolder) {
         protected void setDrawable(Drawable var1) {
            ReactToolbar.this.setOverflowIcon(var1);
         }
      };
   }

   private void attachDraweeHolders() {
      this.mLogoHolder.onAttach();
      this.mNavIconHolder.onAttach();
      this.mOverflowIconHolder.onAttach();
      this.mActionsHolder.onAttach();
   }

   private GenericDraweeHierarchy createDraweeHierarchy() {
      return (new GenericDraweeHierarchyBuilder(this.getResources())).setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER).setFadeDuration(0).build();
   }

   private void detachDraweeHolders() {
      this.mLogoHolder.onDetach();
      this.mNavIconHolder.onDetach();
      this.mOverflowIconHolder.onDetach();
      this.mActionsHolder.onDetach();
   }

   private Drawable getDrawableByName(String var1) {
      return this.getDrawableResourceByName(var1) != 0?this.getResources().getDrawable(this.getDrawableResourceByName(var1)):null;
   }

   private int getDrawableResourceByName(String var1) {
      return this.getResources().getIdentifier(var1, "drawable", this.getContext().getPackageName());
   }

   private ReactToolbar.IconImageInfo getIconImageInfo(ReadableMap var1) {
      return var1.hasKey("width") && var1.hasKey("height")?new ReactToolbar.IconImageInfo(Math.round(PixelUtil.toPixelFromDIP((float)var1.getInt("width"))), Math.round(PixelUtil.toPixelFromDIP((float)var1.getInt("height")))):null;
   }

   private void setIconSource(ReadableMap var1, ReactToolbar.IconControllerListener var2, DraweeHolder var3) {
      String var4;
      if(var1 != null) {
         var4 = var1.getString("uri");
      } else {
         var4 = null;
      }

      if(var4 == null) {
         var2.setIconImageInfo((ReactToolbar.IconImageInfo)null);
         var2.setDrawable((Drawable)null);
      } else if(!var4.startsWith("http://") && !var4.startsWith("https://") && !var4.startsWith("file://")) {
         var2.setDrawable(this.getDrawableByName(var4));
      } else {
         var2.setIconImageInfo(this.getIconImageInfo(var1));
         var3.setController(((PipelineDraweeControllerBuilder)((PipelineDraweeControllerBuilder)Fresco.newDraweeControllerBuilder().setUri(Uri.parse(var4)).setControllerListener(var2)).setOldController(var3.getController())).build());
         var3.getTopLevelDrawable().setVisible(true, true);
      }
   }

   private void setMenuItemIcon(MenuItem var1, ReadableMap var2) {
      DraweeHolder var3 = DraweeHolder.create(this.createDraweeHierarchy(), this.getContext());
      ReactToolbar.ActionIconControllerListener var4 = new ReactToolbar.ActionIconControllerListener(var1, var3);
      var4.setIconImageInfo(this.getIconImageInfo(var2));
      this.setIconSource(var2, var4, var3);
      this.mActionsHolder.add(var3);
   }

   public void onAttachedToWindow() {
      super.onAttachedToWindow();
      this.attachDraweeHolders();
   }

   public void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      this.detachDraweeHolders();
   }

   public void onFinishTemporaryDetach() {
      super.onFinishTemporaryDetach();
      this.attachDraweeHolders();
   }

   public void onStartTemporaryDetach() {
      super.onStartTemporaryDetach();
      this.detachDraweeHolders();
   }

   public void requestLayout() {
      super.requestLayout();
      this.post(this.mLayoutRunnable);
   }

   void setActions(@Nullable ReadableArray var1) {
      Menu var5 = this.getMenu();
      var5.clear();
      this.mActionsHolder.clear();
      if(var1 != null) {
         for(int var3 = 0; var3 < var1.size(); ++var3) {
            ReadableMap var6 = var1.getMap(var3);
            MenuItem var7 = var5.add(0, 0, var3, var6.getString("title"));
            if(var6.hasKey("icon")) {
               this.setMenuItemIcon(var7, var6.getMap("icon"));
            }

            int var2;
            if(var6.hasKey("show")) {
               var2 = var6.getInt("show");
            } else {
               var2 = 0;
            }

            int var4 = var2;
            if(var6.hasKey("showWithText")) {
               var4 = var2;
               if(var6.getBoolean("showWithText")) {
                  var4 = var2 | 4;
               }
            }

            var7.setShowAsAction(var4);
         }
      }

   }

   void setLogoSource(@Nullable ReadableMap var1) {
      this.setIconSource(var1, this.mLogoControllerListener, this.mLogoHolder);
   }

   void setNavIconSource(@Nullable ReadableMap var1) {
      this.setIconSource(var1, this.mNavIconControllerListener, this.mNavIconHolder);
   }

   void setOverflowIconSource(@Nullable ReadableMap var1) {
      this.setIconSource(var1, this.mOverflowIconControllerListener, this.mOverflowIconHolder);
   }

   abstract class IconControllerListener extends BaseControllerListener<ImageInfo> {

      private final DraweeHolder mHolder;
      private ReactToolbar.IconImageInfo mIconImageInfo;


      public IconControllerListener(DraweeHolder var2) {
         this.mHolder = var2;
      }

      public void onFinalImageSet(String var1, @Nullable ImageInfo var2, @Nullable Animatable var3) {
         super.onFinalImageSet(var1, var2, var3);
         if(this.mIconImageInfo != null) {
            var2 = this.mIconImageInfo;
         }

         this.setDrawable(new DrawableWithIntrinsicSize(this.mHolder.getTopLevelDrawable(), (ImageInfo)var2));
      }

      protected abstract void setDrawable(Drawable var1);

      public void setIconImageInfo(ReactToolbar.IconImageInfo var1) {
         this.mIconImageInfo = var1;
      }
   }

   class ActionIconControllerListener extends ReactToolbar.IconControllerListener {

      private final MenuItem mItem;


      ActionIconControllerListener(MenuItem var2, DraweeHolder var3) {
         super(var3);
         this.mItem = var2;
      }

      protected void setDrawable(Drawable var1) {
         this.mItem.setIcon(var1);
         ReactToolbar.this.requestLayout();
      }
   }

   static class IconImageInfo implements ImageInfo {

      private int mHeight;
      private int mWidth;


      public IconImageInfo(int var1, int var2) {
         this.mWidth = var1;
         this.mHeight = var2;
      }

      public int getHeight() {
         return this.mHeight;
      }

      public QualityInfo getQualityInfo() {
         return null;
      }

      public int getWidth() {
         return this.mWidth;
      }
   }
}
