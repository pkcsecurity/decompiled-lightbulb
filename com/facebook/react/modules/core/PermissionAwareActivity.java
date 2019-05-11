package com.facebook.react.modules.core;

import com.facebook.react.modules.core.PermissionListener;

public interface PermissionAwareActivity {

   int checkPermission(String var1, int var2, int var3);

   int checkSelfPermission(String var1);

   void requestPermissions(String[] var1, int var2, PermissionListener var3);

   boolean shouldShowRequestPermissionRationale(String var1);
}
