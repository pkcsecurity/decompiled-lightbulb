package com.facebook.react.devsupport.interfaces;

import javax.annotation.Nullable;

public interface DevBundleDownloadListener {

   void onFailure(Exception var1);

   void onProgress(@Nullable String var1, @Nullable Integer var2, @Nullable Integer var3);

   void onSuccess();
}
