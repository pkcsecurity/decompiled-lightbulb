package com.facebook;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;

public interface FacebookDialog<CONTENT extends Object, RESULT extends Object> {

   boolean canShow(CONTENT var1);

   void registerCallback(CallbackManager var1, FacebookCallback<RESULT> var2);

   void registerCallback(CallbackManager var1, FacebookCallback<RESULT> var2, int var3);

   void show(CONTENT var1);
}
