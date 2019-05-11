package com.facebook.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Looper;
import android.util.Log;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.FacebookSdkNotInitializedException;
import com.facebook.internal.Utility;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class Validate {

   private static final String CONTENT_PROVIDER_BASE = "com.facebook.app.FacebookContentProvider";
   private static final String CONTENT_PROVIDER_NOT_FOUND_REASON = "A ContentProvider for this app was not set up in the AndroidManifest.xml, please add %s as a provider to your AndroidManifest.xml file. See https://developers.facebook.com/docs/sharing/android for more info.";
   private static final String CUSTOM_TAB_REDIRECT_ACTIVITY_NOT_FOUND_REASON = "FacebookActivity is declared incorrectly in the AndroidManifest.xml, please add com.facebook.FacebookActivity to your AndroidManifest.xml file. See https://developers.facebook.com/docs/android/getting-started for more info.";
   private static final String FACEBOOK_ACTIVITY_NOT_FOUND_REASON = "FacebookActivity is not declared in the AndroidManifest.xml. If you are using the facebook-common module or dependent modules please add com.facebook.FacebookActivity to your AndroidManifest.xml file. See https://developers.facebook.com/docs/android/getting-started for more info.";
   private static final String NO_INTERNET_PERMISSION_REASON = "No internet permissions granted for the app, please add <uses-permission android:name=\"android.permission.INTERNET\" /> to your AndroidManifest.xml.";
   private static final String TAG = "com.facebook.internal.Validate";


   public static void checkCustomTabRedirectActivity(Context var0) {
      checkCustomTabRedirectActivity(var0, true);
   }

   public static void checkCustomTabRedirectActivity(Context var0, boolean var1) {
      if(!hasCustomTabRedirectActivity(var0)) {
         if(var1) {
            throw new IllegalStateException("FacebookActivity is declared incorrectly in the AndroidManifest.xml, please add com.facebook.FacebookActivity to your AndroidManifest.xml file. See https://developers.facebook.com/docs/android/getting-started for more info.");
         }

         Log.w(TAG, "FacebookActivity is declared incorrectly in the AndroidManifest.xml, please add com.facebook.FacebookActivity to your AndroidManifest.xml file. See https://developers.facebook.com/docs/android/getting-started for more info.");
      }

   }

   public static void containsNoNullOrEmpty(Collection<String> var0, String var1) {
      notNull(var0, var1);
      Iterator var3 = var0.iterator();

      String var2;
      StringBuilder var4;
      do {
         if(!var3.hasNext()) {
            return;
         }

         var2 = (String)var3.next();
         if(var2 == null) {
            var4 = new StringBuilder();
            var4.append("Container \'");
            var4.append(var1);
            var4.append("\' cannot contain null values");
            throw new NullPointerException(var4.toString());
         }
      } while(var2.length() != 0);

      var4 = new StringBuilder();
      var4.append("Container \'");
      var4.append(var1);
      var4.append("\' cannot contain empty values");
      throw new IllegalArgumentException(var4.toString());
   }

   public static <T extends Object> void containsNoNulls(Collection<T> var0, String var1) {
      notNull(var0, var1);
      Iterator var2 = var0.iterator();

      do {
         if(!var2.hasNext()) {
            return;
         }
      } while(var2.next() != null);

      StringBuilder var3 = new StringBuilder();
      var3.append("Container \'");
      var3.append(var1);
      var3.append("\' cannot contain null values");
      throw new NullPointerException(var3.toString());
   }

   public static String hasAppID() {
      String var0 = FacebookSdk.getApplicationId();
      if(var0 == null) {
         throw new IllegalStateException("No App ID found, please set the App ID.");
      } else {
         return var0;
      }
   }

   public static boolean hasBluetoothPermission(Context var0) {
      return hasPermission(var0, "android.permission.BLUETOOTH") && hasPermission(var0, "android.permission.BLUETOOTH_ADMIN");
   }

   public static boolean hasChangeWifiStatePermission(Context var0) {
      return hasPermission(var0, "android.permission.CHANGE_WIFI_STATE");
   }

   public static String hasClientToken() {
      String var0 = FacebookSdk.getClientToken();
      if(var0 == null) {
         throw new IllegalStateException("No Client Token found, please set the Client Token.");
      } else {
         return var0;
      }
   }

   public static void hasContentProvider(Context var0) {
      notNull(var0, "context");
      String var1 = hasAppID();
      PackageManager var3 = var0.getPackageManager();
      if(var3 != null) {
         StringBuilder var2 = new StringBuilder();
         var2.append("com.facebook.app.FacebookContentProvider");
         var2.append(var1);
         var1 = var2.toString();
         if(var3.resolveContentProvider(var1, 0) == null) {
            throw new IllegalStateException(String.format("A ContentProvider for this app was not set up in the AndroidManifest.xml, please add %s as a provider to your AndroidManifest.xml file. See https://developers.facebook.com/docs/sharing/android for more info.", new Object[]{var1}));
         }
      }

   }

   public static boolean hasCustomTabRedirectActivity(Context var0) {
      notNull(var0, "context");
      PackageManager var2 = var0.getPackageManager();
      List var5;
      if(var2 != null) {
         Intent var3 = new Intent();
         var3.setAction("android.intent.action.VIEW");
         var3.addCategory("android.intent.category.DEFAULT");
         var3.addCategory("android.intent.category.BROWSABLE");
         StringBuilder var4 = new StringBuilder();
         var4.append("fb");
         var4.append(FacebookSdk.getApplicationId());
         var4.append("://authorize");
         var3.setData(Uri.parse(var4.toString()));
         var5 = var2.queryIntentActivities(var3, 64);
      } else {
         var5 = null;
      }

      boolean var1 = false;
      if(var5 != null) {
         Iterator var6 = var5.iterator();

         for(var1 = false; var6.hasNext(); var1 = true) {
            ActivityInfo var7 = ((ResolveInfo)var6.next()).activityInfo;
            if(!var7.name.equals("com.facebook.CustomTabActivity") || !var7.packageName.equals(var0.getPackageName())) {
               return false;
            }
         }
      }

      return var1;
   }

   public static void hasFacebookActivity(Context var0) {
      hasFacebookActivity(var0, true);
   }

   public static void hasFacebookActivity(Context var0, boolean var1) {
      ActivityInfo var5;
      label26: {
         notNull(var0, "context");
         PackageManager var2 = var0.getPackageManager();
         if(var2 != null) {
            ComponentName var4 = new ComponentName(var0, "com.facebook.FacebookActivity");

            try {
               var5 = var2.getActivityInfo(var4, 1);
               break label26;
            } catch (NameNotFoundException var3) {
               ;
            }
         }

         var5 = null;
      }

      if(var5 == null) {
         if(var1) {
            throw new IllegalStateException("FacebookActivity is not declared in the AndroidManifest.xml. If you are using the facebook-common module or dependent modules please add com.facebook.FacebookActivity to your AndroidManifest.xml file. See https://developers.facebook.com/docs/android/getting-started for more info.");
         }

         Log.w(TAG, "FacebookActivity is not declared in the AndroidManifest.xml. If you are using the facebook-common module or dependent modules please add com.facebook.FacebookActivity to your AndroidManifest.xml file. See https://developers.facebook.com/docs/android/getting-started for more info.");
      }

   }

   public static void hasInternetPermissions(Context var0) {
      hasInternetPermissions(var0, true);
   }

   public static void hasInternetPermissions(Context var0, boolean var1) {
      notNull(var0, "context");
      if(var0.checkCallingOrSelfPermission("android.permission.INTERNET") == -1) {
         if(var1) {
            throw new IllegalStateException("No internet permissions granted for the app, please add <uses-permission android:name=\"android.permission.INTERNET\" /> to your AndroidManifest.xml.");
         }

         Log.w(TAG, "No internet permissions granted for the app, please add <uses-permission android:name=\"android.permission.INTERNET\" /> to your AndroidManifest.xml.");
      }

   }

   public static boolean hasLocationPermission(Context var0) {
      return hasPermission(var0, "android.permission.ACCESS_COARSE_LOCATION") || hasPermission(var0, "android.permission.ACCESS_FINE_LOCATION");
   }

   public static boolean hasPermission(Context var0, String var1) {
      return var0.checkCallingOrSelfPermission(var1) == 0;
   }

   public static boolean hasWiFiPermission(Context var0) {
      return hasPermission(var0, "android.permission.ACCESS_WIFI_STATE");
   }

   public static <T extends Object> void notEmpty(Collection<T> var0, String var1) {
      if(var0.isEmpty()) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Container \'");
         var2.append(var1);
         var2.append("\' cannot be empty");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public static <T extends Object> void notEmptyAndContainsNoNulls(Collection<T> var0, String var1) {
      containsNoNulls(var0, var1);
      notEmpty(var0, var1);
   }

   public static void notNull(Object var0, String var1) {
      if(var0 == null) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Argument \'");
         var2.append(var1);
         var2.append("\' cannot be null");
         throw new NullPointerException(var2.toString());
      }
   }

   public static void notNullOrEmpty(String var0, String var1) {
      if(Utility.isNullOrEmpty(var0)) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Argument \'");
         var2.append(var1);
         var2.append("\' cannot be null or empty");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public static void oneOf(Object var0, String var1, Object ... var2) {
      int var4 = var2.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Object var5 = var2[var3];
         if(var5 != null) {
            if(var5.equals(var0)) {
               return;
            }
         } else if(var0 == null) {
            return;
         }
      }

      StringBuilder var6 = new StringBuilder();
      var6.append("Argument \'");
      var6.append(var1);
      var6.append("\' was not one of the allowed values");
      throw new IllegalArgumentException(var6.toString());
   }

   public static void runningOnUiThread() {
      if(!Looper.getMainLooper().equals(Looper.myLooper())) {
         throw new FacebookException("This method should be called from the UI thread");
      }
   }

   public static void sdkInitialized() {
      if(!FacebookSdk.isInitialized()) {
         throw new FacebookSdkNotInitializedException("The SDK has not been initialized, make sure to call FacebookSdk.sdkInitialize() first.");
      }
   }
}
