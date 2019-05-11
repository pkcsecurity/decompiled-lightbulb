package com.facebook.react.devsupport;

import android.os.Build.VERSION;

class WindowOverlayCompat {

   private static final int ANDROID_OREO = 26;
   private static final int TYPE_APPLICATION_OVERLAY = 2038;
   static final int TYPE_SYSTEM_ALERT;
   static final int TYPE_SYSTEM_OVERLAY;


   static {
      int var0 = VERSION.SDK_INT;
      short var1 = 2038;
      short var2;
      if(var0 < 26) {
         var2 = 2003;
      } else {
         var2 = 2038;
      }

      TYPE_SYSTEM_ALERT = var2;
      var2 = var1;
      if(VERSION.SDK_INT < 26) {
         var2 = 2006;
      }

      TYPE_SYSTEM_OVERLAY = var2;
   }

}
