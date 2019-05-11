package com.facebook.appevents.codeless;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.codeless.CodelessMatcher;
import com.facebook.appevents.codeless.internal.EventBinding;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import com.facebook.appevents.internal.AppEventUtility;
import java.lang.ref.WeakReference;

public class CodelessLoggingEventListener {

   private static final String TAG = CodelessLoggingEventListener.class.getCanonicalName();


   public static CodelessLoggingEventListener.AutoLoggingAccessibilityDelegate getAccessibilityDelegate(EventBinding var0, View var1, View var2) {
      return new CodelessLoggingEventListener.AutoLoggingAccessibilityDelegate(var0, var1, var2);
   }

   public static class AutoLoggingAccessibilityDelegate extends AccessibilityDelegate {

      private int accessibilityEventType;
      private AccessibilityDelegate existingDelegate;
      private WeakReference<View> hostView;
      private EventBinding mapping;
      private WeakReference<View> rootView;
      protected boolean supportButtonIndexing = false;
      private boolean supportCodelessLogging = false;


      public AutoLoggingAccessibilityDelegate() {}

      public AutoLoggingAccessibilityDelegate(EventBinding var1, View var2, View var3) {
         if(var1 != null && var2 != null) {
            if(var3 != null) {
               this.existingDelegate = ViewHierarchy.getExistingDelegate(var3);
               this.mapping = var1;
               this.hostView = new WeakReference(var3);
               this.rootView = new WeakReference(var2);
               EventBinding.ActionType var5 = var1.getType();
               switch(null.$SwitchMap$com$facebook$appevents$codeless$internal$EventBinding$ActionType[var1.getType().ordinal()]) {
               case 1:
                  this.accessibilityEventType = 1;
                  break;
               case 2:
                  this.accessibilityEventType = 4;
                  break;
               case 3:
                  this.accessibilityEventType = 16;
                  break;
               default:
                  StringBuilder var4 = new StringBuilder();
                  var4.append("Unsupported action type: ");
                  var4.append(var5.toString());
                  throw new FacebookException(var4.toString());
               }

               this.supportCodelessLogging = true;
            }
         }
      }

      private void logEvent() {
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

      public boolean getSupportButtonIndexing() {
         return this.supportButtonIndexing;
      }

      public boolean getSupportCodelessLogging() {
         return this.supportCodelessLogging;
      }

      public void sendAccessibilityEvent(View var1, int var2) {
         if(var2 == -1) {
            Log.e(CodelessLoggingEventListener.TAG, "Unsupported action type");
         }

         if(var2 == this.accessibilityEventType) {
            if(this.existingDelegate != null && !(this.existingDelegate instanceof CodelessLoggingEventListener.AutoLoggingAccessibilityDelegate)) {
               this.existingDelegate.sendAccessibilityEvent(var1, var2);
            }

            this.logEvent();
         }
      }
   }
}
