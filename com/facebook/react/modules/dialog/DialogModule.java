package com.facebook.react.modules.dialog;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.dialog.AlertFragment;
import com.facebook.react.modules.dialog.SupportAlertFragment;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(
   name = "DialogManagerAndroid"
)
public class DialogModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

   static final String ACTION_BUTTON_CLICKED = "buttonClicked";
   static final String ACTION_DISMISSED = "dismissed";
   static final Map<String, Object> CONSTANTS = MapBuilder.of("buttonClicked", "buttonClicked", "dismissed", "dismissed", "buttonPositive", Integer.valueOf(-1), "buttonNegative", Integer.valueOf(-2), "buttonNeutral", Integer.valueOf(-3));
   static final String FRAGMENT_TAG = "com.facebook.catalyst.react.dialog.DialogModule";
   static final String KEY_BUTTON_NEGATIVE = "buttonNegative";
   static final String KEY_BUTTON_NEUTRAL = "buttonNeutral";
   static final String KEY_BUTTON_POSITIVE = "buttonPositive";
   static final String KEY_CANCELABLE = "cancelable";
   static final String KEY_ITEMS = "items";
   static final String KEY_MESSAGE = "message";
   static final String KEY_TITLE = "title";
   static final String NAME = "DialogManagerAndroid";
   private boolean mIsInForeground;


   public DialogModule(ReactApplicationContext var1) {
      super(var1);
   }

   @Nullable
   private DialogModule.FragmentManagerHelper getFragmentManagerHelper() {
      Activity var1 = this.getCurrentActivity();
      return var1 == null?null:(var1 instanceof FragmentActivity?new DialogModule.FragmentManagerHelper(((FragmentActivity)var1).getSupportFragmentManager()):new DialogModule.FragmentManagerHelper(var1.getFragmentManager()));
   }

   public Map<String, Object> getConstants() {
      return CONSTANTS;
   }

   public String getName() {
      return "DialogManagerAndroid";
   }

   public void initialize() {
      this.getReactApplicationContext().addLifecycleEventListener(this);
   }

   public void onHostDestroy() {}

   public void onHostPause() {
      this.mIsInForeground = false;
   }

   public void onHostResume() {
      this.mIsInForeground = true;
      DialogModule.FragmentManagerHelper var1 = this.getFragmentManagerHelper();
      if(var1 != null) {
         var1.showPendingAlert();
      } else {
         FLog.w(DialogModule.class, "onHostResume called but no FragmentManager found");
      }
   }

   @ReactMethod
   public void showAlert(ReadableMap var1, Callback var2, Callback var3) {
      DialogModule.FragmentManagerHelper var5 = this.getFragmentManagerHelper();
      int var4 = 0;
      if(var5 == null) {
         var2.invoke(new Object[]{"Tried to show an alert while not attached to an Activity"});
      } else {
         Bundle var8 = new Bundle();
         if(var1.hasKey("title")) {
            var8.putString("title", var1.getString("title"));
         }

         if(var1.hasKey("message")) {
            var8.putString("message", var1.getString("message"));
         }

         if(var1.hasKey("buttonPositive")) {
            var8.putString("button_positive", var1.getString("buttonPositive"));
         }

         if(var1.hasKey("buttonNegative")) {
            var8.putString("button_negative", var1.getString("buttonNegative"));
         }

         if(var1.hasKey("buttonNeutral")) {
            var8.putString("button_neutral", var1.getString("buttonNeutral"));
         }

         if(var1.hasKey("items")) {
            ReadableArray var6 = var1.getArray("items");

            CharSequence[] var7;
            for(var7 = new CharSequence[var6.size()]; var4 < var6.size(); ++var4) {
               var7[var4] = var6.getString(var4);
            }

            var8.putCharSequenceArray("items", var7);
         }

         if(var1.hasKey("cancelable")) {
            var8.putBoolean("cancelable", var1.getBoolean("cancelable"));
         }

         var5.showNewAlert(this.mIsInForeground, var8, var3);
      }
   }

   class FragmentManagerHelper {

      @Nullable
      private final FragmentManager mFragmentManager;
      @Nullable
      private Object mFragmentToShow;
      @Nullable
      private final android.support.v4.app.FragmentManager mSupportFragmentManager;


      public FragmentManagerHelper(FragmentManager var2) {
         this.mFragmentManager = var2;
         this.mSupportFragmentManager = null;
      }

      public FragmentManagerHelper(android.support.v4.app.FragmentManager var2) {
         this.mFragmentManager = null;
         this.mSupportFragmentManager = var2;
      }

      private void dismissExisting() {
         if(this.isUsingSupportLibrary()) {
            SupportAlertFragment var1 = (SupportAlertFragment)this.mSupportFragmentManager.findFragmentByTag("com.facebook.catalyst.react.dialog.DialogModule");
            if(var1 != null) {
               var1.dismiss();
               return;
            }
         } else {
            AlertFragment var2 = (AlertFragment)this.mFragmentManager.findFragmentByTag("com.facebook.catalyst.react.dialog.DialogModule");
            if(var2 != null) {
               var2.dismiss();
            }
         }

      }

      private boolean isUsingSupportLibrary() {
         return this.mSupportFragmentManager != null;
      }

      public void showNewAlert(boolean var1, Bundle var2, Callback var3) {
         this.dismissExisting();
         DialogModule.AlertFragmentListener var4;
         if(var3 != null) {
            var4 = DialogModule.this.new AlertFragmentListener(var3);
         } else {
            var4 = null;
         }

         if(this.isUsingSupportLibrary()) {
            SupportAlertFragment var6 = new SupportAlertFragment(var4, var2);
            if(var1) {
               if(var2.containsKey("cancelable")) {
                  var6.setCancelable(var2.getBoolean("cancelable"));
               }

               var6.show(this.mSupportFragmentManager, "com.facebook.catalyst.react.dialog.DialogModule");
            } else {
               this.mFragmentToShow = var6;
            }
         } else {
            AlertFragment var5 = new AlertFragment(var4, var2);
            if(var1) {
               if(var2.containsKey("cancelable")) {
                  var5.setCancelable(var2.getBoolean("cancelable"));
               }

               var5.show(this.mFragmentManager, "com.facebook.catalyst.react.dialog.DialogModule");
            } else {
               this.mFragmentToShow = var5;
            }
         }
      }

      public void showPendingAlert() {
         if(this.mFragmentToShow != null) {
            if(this.isUsingSupportLibrary()) {
               ((SupportAlertFragment)this.mFragmentToShow).show(this.mSupportFragmentManager, "com.facebook.catalyst.react.dialog.DialogModule");
            } else {
               ((AlertFragment)this.mFragmentToShow).show(this.mFragmentManager, "com.facebook.catalyst.react.dialog.DialogModule");
            }

            this.mFragmentToShow = null;
         }
      }
   }

   class AlertFragmentListener implements OnClickListener, OnDismissListener {

      private final Callback mCallback;
      private boolean mCallbackConsumed = false;


      public AlertFragmentListener(Callback var2) {
         this.mCallback = var2;
      }

      public void onClick(DialogInterface var1, int var2) {
         if(!this.mCallbackConsumed && DialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
            this.mCallback.invoke(new Object[]{"buttonClicked", Integer.valueOf(var2)});
            this.mCallbackConsumed = true;
         }

      }

      public void onDismiss(DialogInterface var1) {
         if(!this.mCallbackConsumed && DialogModule.this.getReactApplicationContext().hasActiveCatalystInstance()) {
            this.mCallback.invoke(new Object[]{"dismissed"});
            this.mCallbackConsumed = true;
         }

      }
   }
}
