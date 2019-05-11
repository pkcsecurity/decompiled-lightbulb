package com.facebook.react.devsupport;

import android.os.Handler;
import android.view.View;
import android.widget.EditText;

public class DoubleTapReloadRecognizer {

   private static final long DOUBLE_TAP_DELAY = 200L;
   private boolean mDoRefresh = false;


   public boolean didDoubleTapR(int var1, View var2) {
      if(var1 == 46 && !(var2 instanceof EditText)) {
         if(this.mDoRefresh) {
            this.mDoRefresh = false;
            return true;
         }

         this.mDoRefresh = true;
         (new Handler()).postDelayed(new Runnable() {
            public void run() {
               DoubleTapReloadRecognizer.this.mDoRefresh = false;
            }
         }, 200L);
      }

      return false;
   }
}
