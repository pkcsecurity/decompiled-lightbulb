package com.facebook.appevents.internal;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.facebook.FacebookSdk;

class SourceApplicationInfo {

   private static final String CALL_APPLICATION_PACKAGE_KEY = "com.facebook.appevents.SourceApplicationInfo.callingApplicationPackage";
   private static final String OPENED_BY_APP_LINK_KEY = "com.facebook.appevents.SourceApplicationInfo.openedByApplink";
   private static final String SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT = "_fbSourceApplicationHasBeenSet";
   private String callingApplicationPackage;
   private boolean openedByAppLink;


   private SourceApplicationInfo(String var1, boolean var2) {
      this.callingApplicationPackage = var1;
      this.openedByAppLink = var2;
   }

   // $FF: synthetic method
   SourceApplicationInfo(String var1, boolean var2, Object var3) {
      this(var1, var2);
   }

   public static void clearSavedSourceApplicationInfoFromDisk() {
      Editor var0 = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).edit();
      var0.remove("com.facebook.appevents.SourceApplicationInfo.callingApplicationPackage");
      var0.remove("com.facebook.appevents.SourceApplicationInfo.openedByApplink");
      var0.apply();
   }

   public static SourceApplicationInfo getStoredSourceApplicatioInfo() {
      SharedPreferences var0 = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext());
      return !var0.contains("com.facebook.appevents.SourceApplicationInfo.callingApplicationPackage")?null:new SourceApplicationInfo(var0.getString("com.facebook.appevents.SourceApplicationInfo.callingApplicationPackage", (String)null), var0.getBoolean("com.facebook.appevents.SourceApplicationInfo.openedByApplink", false));
   }

   public String getCallingApplicationPackage() {
      return this.callingApplicationPackage;
   }

   public boolean isOpenedByAppLink() {
      return this.openedByAppLink;
   }

   public String toString() {
      String var1 = "Unclassified";
      if(this.openedByAppLink) {
         var1 = "Applink";
      }

      if(this.callingApplicationPackage != null) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1);
         var2.append("(");
         var2.append(this.callingApplicationPackage);
         var2.append(")");
         return var2.toString();
      } else {
         return var1;
      }
   }

   public void writeSourceApplicationInfoToDisk() {
      Editor var1 = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).edit();
      var1.putString("com.facebook.appevents.SourceApplicationInfo.callingApplicationPackage", this.callingApplicationPackage);
      var1.putBoolean("com.facebook.appevents.SourceApplicationInfo.openedByApplink", this.openedByAppLink);
      var1.apply();
   }

   public static class Factory {

      public static SourceApplicationInfo create(Activity var0) {
         String var3 = "";
         ComponentName var4 = var0.getCallingActivity();
         if(var4 != null) {
            String var8 = var4.getPackageName();
            var3 = var8;
            if(var8.equals(var0.getPackageName())) {
               return null;
            }
         }

         Intent var9 = var0.getIntent();
         boolean var2 = false;
         String var6 = var3;
         boolean var1 = var2;
         if(var9 != null) {
            var6 = var3;
            var1 = var2;
            if(!var9.getBooleanExtra("_fbSourceApplicationHasBeenSet", false)) {
               var9.putExtra("_fbSourceApplicationHasBeenSet", true);
               Bundle var5 = t.a(var9);
               var6 = var3;
               var1 = var2;
               if(var5 != null) {
                  Bundle var7 = var5.getBundle("referer_app_link");
                  if(var7 != null) {
                     var3 = var7.getString("package");
                  }

                  var1 = true;
                  var6 = var3;
               }
            }
         }

         var9.putExtra("_fbSourceApplicationHasBeenSet", true);
         return new SourceApplicationInfo(var6, var1, null);
      }
   }
}
