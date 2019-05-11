package com.facebook.appevents.codeless;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.codeless.CodelessMatcher;
import com.facebook.appevents.codeless.internal.EventBinding;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import com.facebook.appevents.internal.AppEventUtility;
import java.lang.ref.WeakReference;

public class RCTCodelessLoggingEventListener {

   private static final String TAG = RCTCodelessLoggingEventListener.class.getCanonicalName();


   public static RCTCodelessLoggingEventListener.AutoLoggingOnTouchListener getOnTouchListener(EventBinding var0, View var1, View var2) {
      return new RCTCodelessLoggingEventListener.AutoLoggingOnTouchListener(var0, var1, var2);
   }

   public static class AutoLoggingOnTouchListener implements OnTouchListener {

      @Nullable
      private OnTouchListener existingOnTouchListener;
      private WeakReference<View> hostView;
      private EventBinding mapping;
      private WeakReference<View> rootView;
      private boolean supportCodelessLogging = false;


      public AutoLoggingOnTouchListener(EventBinding var1, View var2, View var3) {
         if(var1 != null && var2 != null) {
            if(var3 != null) {
               this.existingOnTouchListener = ViewHierarchy.getExistingOnTouchListener(var3);
               this.mapping = var1;
               this.hostView = new WeakReference(var3);
               this.rootView = new WeakReference(var2);
               this.supportCodelessLogging = true;
            }
         }
      }

      private void logEvent() {
         if(this.mapping != null) {
            final String var1 = this.mapping.getEventName();
            final Bundle var2 = CodelessMatcher.getParameters(this.mapping, (View)this.rootView.get(), (View)this.hostView.get());
            if(var2.containsKey("_valueToSum")) {
               var2.putDouble("_valueToSum", AppEventUtility.normalizePrice(var2.getString("_valueToSum")));
            }

            var2.putString("_is_fb_codeless", "1");
            FacebookSdk.getExecutor().execute(new Runnable() {
               public void run() {
                  AppEventsLogger.newLogger(FacebookSdk.getApplicationContext()).logEvent(var1, var2);
               }
            });
         }
      }

      public boolean getSupportCodelessLogging() {
         return this.supportCodelessLogging;
      }

      public boolean onTouch(View var1, MotionEvent var2) {
         if(var2.getAction() == 1) {
            this.logEvent();
         }

         return this.existingOnTouchListener != null && this.existingOnTouchListener.onTouch(var1, var2);
      }
   }
}
