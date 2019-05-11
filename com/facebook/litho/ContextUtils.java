package com.facebook.litho;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;

public class ContextUtils {

   @Nullable
   private static Activity findActivityInContext(Context var0) {
      return var0 instanceof Activity?(Activity)var0:(var0 instanceof ContextWrapper?findActivityInContext(((ContextWrapper)var0).getBaseContext()):null);
   }

   static Context getRootContext(Context var0) {
      while(var0 instanceof ContextWrapper && !(var0 instanceof Activity) && !(var0 instanceof Application) && !(var0 instanceof Service)) {
         var0 = ((ContextWrapper)var0).getBaseContext();
      }

      return var0;
   }

   @Nullable
   static Activity getValidActivityForContext(Context var0) {
      Activity var1 = findActivityInContext(var0);
      return var1 != null && !var1.isFinishing() && !isActivityDestroyed(var1)?var1:null;
   }

   private static boolean isActivityDestroyed(Activity var0) {
      return VERSION.SDK_INT >= 17?var0.isDestroyed():true;
   }
}
