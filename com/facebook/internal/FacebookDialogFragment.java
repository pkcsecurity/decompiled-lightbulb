package com.facebook.internal;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.FacebookWebFallbackDialog;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.WebDialog;

public class FacebookDialogFragment extends DialogFragment {

   public static final String TAG = "FacebookDialogFragment";
   private Dialog dialog;


   private void onCompleteWebDialog(Bundle var1, FacebookException var2) {
      FragmentActivity var4 = this.getActivity();
      Intent var5 = NativeProtocol.createProtocolResultIntent(var4.getIntent(), var1, var2);
      byte var3;
      if(var2 == null) {
         var3 = -1;
      } else {
         var3 = 0;
      }

      var4.setResult(var3, var5);
      var4.finish();
   }

   private void onCompleteWebFallbackDialog(Bundle var1) {
      FragmentActivity var3 = this.getActivity();
      Intent var4 = new Intent();
      Bundle var2 = var1;
      if(var1 == null) {
         var2 = new Bundle();
      }

      var4.putExtras(var2);
      var3.setResult(-1, var4);
      var3.finish();
   }

   public void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      if(this.dialog instanceof WebDialog && this.isResumed()) {
         ((WebDialog)this.dialog).resize();
      }

   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(this.dialog == null) {
         FragmentActivity var4 = this.getActivity();
         Bundle var3 = NativeProtocol.getMethodArgumentsFromIntent(var4.getIntent());
         String var2;
         Object var5;
         if(!var3.getBoolean("is_fallback", false)) {
            var2 = var3.getString("action");
            var3 = var3.getBundle("params");
            if(Utility.isNullOrEmpty(var2)) {
               Utility.logd("FacebookDialogFragment", "Cannot start a WebDialog with an empty/missing \'actionName\'");
               var4.finish();
               return;
            }

            var5 = (new WebDialog.Builder(var4, var2, var3)).setOnCompleteListener(new WebDialog.OnCompleteListener() {
               public void onComplete(Bundle var1, FacebookException var2) {
                  FacebookDialogFragment.this.onCompleteWebDialog(var1, var2);
               }
            }).build();
         } else {
            var2 = var3.getString("url");
            if(Utility.isNullOrEmpty(var2)) {
               Utility.logd("FacebookDialogFragment", "Cannot start a fallback WebDialog with an empty/missing \'url\'");
               var4.finish();
               return;
            }

            var5 = FacebookWebFallbackDialog.newInstance(var4, var2, String.format("fb%s://bridge/", new Object[]{FacebookSdk.getApplicationId()}));
            ((WebDialog)var5).setOnCompleteListener(new WebDialog.OnCompleteListener() {
               public void onComplete(Bundle var1, FacebookException var2) {
                  FacebookDialogFragment.this.onCompleteWebFallbackDialog(var1);
               }
            });
         }

         this.dialog = (Dialog)var5;
      }

   }

   @NonNull
   public Dialog onCreateDialog(Bundle var1) {
      if(this.dialog == null) {
         this.onCompleteWebDialog((Bundle)null, (FacebookException)null);
         this.setShowsDialog(false);
      }

      return this.dialog;
   }

   public void onDestroyView() {
      if(this.getDialog() != null && this.getRetainInstance()) {
         this.getDialog().setDismissMessage((Message)null);
      }

      super.onDestroyView();
   }

   public void onResume() {
      super.onResume();
      if(this.dialog instanceof WebDialog) {
         ((WebDialog)this.dialog).resize();
      }

   }

   public void setDialog(Dialog var1) {
      this.dialog = var1;
   }
}
