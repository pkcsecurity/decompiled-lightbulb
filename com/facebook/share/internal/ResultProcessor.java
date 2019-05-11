package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.AppCall;

public abstract class ResultProcessor {

   private FacebookCallback appCallback;


   public ResultProcessor(FacebookCallback var1) {
      this.appCallback = var1;
   }

   public void onCancel(AppCall var1) {
      if(this.appCallback != null) {
         this.appCallback.onCancel();
      }

   }

   public void onError(AppCall var1, FacebookException var2) {
      if(this.appCallback != null) {
         this.appCallback.onError(var2);
      }

   }

   public abstract void onSuccess(AppCall var1, Bundle var2);
}
