package com.facebook.react.views.imagehelper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.Uri.Builder;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class ResourceDrawableIdHelper {

   private static final String LOCAL_RESOURCE_SCHEME = "res";
   private static volatile ResourceDrawableIdHelper sResourceDrawableIdHelper;
   private Map<String, Integer> mResourceDrawableIdMap = new HashMap();


   public static ResourceDrawableIdHelper getInstance() {
      // $FF: Couldn't be decompiled
   }

   public void clear() {
      synchronized(this){}

      try {
         this.mResourceDrawableIdMap.clear();
      } finally {
         ;
      }

   }

   @Nullable
   public Drawable getResourceDrawable(Context var1, @Nullable String var2) {
      int var3 = this.getResourceDrawableId(var1, var2);
      return var3 > 0?var1.getResources().getDrawable(var3):null;
   }

   public int getResourceDrawableId(Context param1, @Nullable String param2) {
      // $FF: Couldn't be decompiled
   }

   public Uri getResourceDrawableUri(Context var1, @Nullable String var2) {
      int var3 = this.getResourceDrawableId(var1, var2);
      return var3 > 0?(new Builder()).scheme("res").path(String.valueOf(var3)).build():Uri.EMPTY;
   }
}
