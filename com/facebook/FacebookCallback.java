package com.facebook;

import com.facebook.FacebookException;

public interface FacebookCallback<RESULT extends Object> {

   void onCancel();

   void onError(FacebookException var1);

   void onSuccess(RESULT var1);
}
