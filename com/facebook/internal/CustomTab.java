package com.facebook.internal;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import com.facebook.FacebookSdk;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;

public class CustomTab {

   private Uri uri;


   public CustomTab(String var1, Bundle var2) {
      Bundle var3 = var2;
      if(var2 == null) {
         var3 = new Bundle();
      }

      String var5 = ServerProtocol.getDialogAuthority();
      StringBuilder var4 = new StringBuilder();
      var4.append(FacebookSdk.getGraphApiVersion());
      var4.append("/");
      var4.append("dialog/");
      var4.append(var1);
      this.uri = Utility.buildUri(var5, var4.toString(), var3);
   }

   public void openCustomTab(Activity var1, String var2) {
      CustomTabsIntent var3 = (new CustomTabsIntent.Builder()).build();
      var3.intent.setPackage(var2);
      var3.intent.addFlags(1073741824);
      var3.launchUrl(var1, this.uri);
   }
}
