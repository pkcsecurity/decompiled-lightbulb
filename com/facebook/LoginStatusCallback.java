package com.facebook;

import com.facebook.AccessToken;

public interface LoginStatusCallback {

   void onCompleted(AccessToken var1);

   void onError(Exception var1);

   void onFailure();
}
