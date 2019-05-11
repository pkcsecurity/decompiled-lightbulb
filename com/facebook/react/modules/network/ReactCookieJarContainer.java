package com.facebook.react.modules.network;

import com.facebook.react.modules.network.CookieJarContainer;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class ReactCookieJarContainer implements CookieJarContainer {

   @Nullable
   private CookieJar cookieJar = null;


   public List<Cookie> loadForRequest(HttpUrl var1) {
      return this.cookieJar != null?this.cookieJar.loadForRequest(var1):Collections.emptyList();
   }

   public void removeCookieJar() {
      this.cookieJar = null;
   }

   public void saveFromResponse(HttpUrl var1, List<Cookie> var2) {
      if(this.cookieJar != null) {
         this.cookieJar.saveFromResponse(var1, var2);
      }

   }

   public void setCookieJar(CookieJar var1) {
      this.cookieJar = var1;
   }
}
