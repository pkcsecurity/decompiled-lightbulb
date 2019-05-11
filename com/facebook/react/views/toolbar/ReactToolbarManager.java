package com.facebook.react.views.toolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.toolbar.ReactToolbar;
import com.facebook.react.views.toolbar.events.ToolbarClickEvent;
import java.util.Map;
import javax.annotation.Nullable;

public class ReactToolbarManager extends ViewGroupManager<ReactToolbar> {

   private static final String REACT_CLASS = "ToolbarAndroid";


   private static int[] getDefaultColors(Context param0) {
      // $FF: Couldn't be decompiled
   }

   private int[] getDefaultContentInsets(Context param1) {
      // $FF: Couldn't be decompiled
   }

   private static int getIdentifier(Context var0, String var1) {
      return var0.getResources().getIdentifier(var1, "attr", var0.getPackageName());
   }

   private static void recycleQuietly(@Nullable TypedArray var0) {
      if(var0 != null) {
         var0.recycle();
      }

   }

   protected void addEventEmitters(ThemedReactContext var1, final ReactToolbar var2) {
      final EventDispatcher var3 = ((UIManagerModule)var1.getNativeModule(UIManagerModule.class)).getEventDispatcher();
      var2.setNavigationOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            var3.dispatchEvent(new ToolbarClickEvent(var2.getId(), -1));
         }
      });
      var2.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
         public boolean onMenuItemClick(MenuItem var1) {
            var3.dispatchEvent(new ToolbarClickEvent(var2.getId(), var1.getOrder()));
            return true;
         }
      });
   }

   protected ReactToolbar createViewInstance(ThemedReactContext var1) {
      return new ReactToolbar(var1);
   }

   @Nullable
   public Map<String, Object> getExportedViewConstants() {
      return MapBuilder.of("ShowAsAction", MapBuilder.of("never", Integer.valueOf(0), "always", Integer.valueOf(2), "ifRoom", Integer.valueOf(1)));
   }

   public String getName() {
      return "ToolbarAndroid";
   }

   public boolean needsCustomLayoutForChildren() {
      return true;
   }

   @ReactProp(
      name = "nativeActions"
   )
   public void setActions(ReactToolbar var1, @Nullable ReadableArray var2) {
      var1.setActions(var2);
   }

   @ReactProp(
      defaultFloat = Float.NaN,
      name = "contentInsetEnd"
   )
   public void setContentInsetEnd(ReactToolbar var1, float var2) {
      int var3;
      if(Float.isNaN(var2)) {
         var3 = this.getDefaultContentInsets(var1.getContext())[1];
      } else {
         var3 = Math.round(PixelUtil.toPixelFromDIP(var2));
      }

      var1.setContentInsetsRelative(var1.getContentInsetStart(), var3);
   }

   @ReactProp(
      defaultFloat = Float.NaN,
      name = "contentInsetStart"
   )
   public void setContentInsetStart(ReactToolbar var1, float var2) {
      int var3;
      if(Float.isNaN(var2)) {
         var3 = this.getDefaultContentInsets(var1.getContext())[0];
      } else {
         var3 = Math.round(PixelUtil.toPixelFromDIP(var2));
      }

      var1.setContentInsetsRelative(var3, var1.getContentInsetEnd());
   }

   @ReactProp(
      name = "logo"
   )
   public void setLogo(ReactToolbar var1, @Nullable ReadableMap var2) {
      var1.setLogoSource(var2);
   }

   @ReactProp(
      name = "navIcon"
   )
   public void setNavIcon(ReactToolbar var1, @Nullable ReadableMap var2) {
      var1.setNavIconSource(var2);
   }

   @ReactProp(
      name = "overflowIcon"
   )
   public void setOverflowIcon(ReactToolbar var1, @Nullable ReadableMap var2) {
      var1.setOverflowIconSource(var2);
   }

   @ReactProp(
      name = "rtl"
   )
   public void setRtl(ReactToolbar var1, boolean var2) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
   }

   @ReactProp(
      name = "subtitle"
   )
   public void setSubtitle(ReactToolbar var1, @Nullable String var2) {
      var1.setSubtitle(var2);
   }

   @ReactProp(
      customType = "Color",
      name = "subtitleColor"
   )
   public void setSubtitleColor(ReactToolbar var1, @Nullable Integer var2) {
      int[] var3 = getDefaultColors(var1.getContext());
      if(var2 != null) {
         var1.setSubtitleTextColor(var2.intValue());
      } else {
         var1.setSubtitleTextColor(var3[1]);
      }
   }

   @ReactProp(
      name = "title"
   )
   public void setTitle(ReactToolbar var1, @Nullable String var2) {
      var1.setTitle(var2);
   }

   @ReactProp(
      customType = "Color",
      name = "titleColor"
   )
   public void setTitleColor(ReactToolbar var1, @Nullable Integer var2) {
      int[] var3 = getDefaultColors(var1.getContext());
      if(var2 != null) {
         var1.setTitleTextColor(var2.intValue());
      } else {
         var1.setTitleTextColor(var3[0]);
      }
   }
}
