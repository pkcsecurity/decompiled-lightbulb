package com.facebook;

import android.content.SharedPreferences;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.LegacyTokenHelper;
import com.facebook.internal.Validate;
import org.json.JSONException;
import org.json.JSONObject;

class AccessTokenCache {

   static final String CACHED_ACCESS_TOKEN_KEY = "com.facebook.AccessTokenManager.CachedAccessToken";
   private final SharedPreferences sharedPreferences;
   private LegacyTokenHelper tokenCachingStrategy;
   private final AccessTokenCache.SharedPreferencesTokenCachingStrategyFactory tokenCachingStrategyFactory;


   public AccessTokenCache() {
      this(FacebookSdk.getApplicationContext().getSharedPreferences("com.facebook.AccessTokenManager.SharedPreferences", 0), new AccessTokenCache.SharedPreferencesTokenCachingStrategyFactory());
   }

   AccessTokenCache(SharedPreferences var1, AccessTokenCache.SharedPreferencesTokenCachingStrategyFactory var2) {
      this.sharedPreferences = var1;
      this.tokenCachingStrategyFactory = var2;
   }

   private AccessToken getCachedAccessToken() {
      String var1 = this.sharedPreferences.getString("com.facebook.AccessTokenManager.CachedAccessToken", (String)null);
      if(var1 != null) {
         try {
            AccessToken var3 = AccessToken.createFromJSONObject(new JSONObject(var1));
            return var3;
         } catch (JSONException var2) {
            return null;
         }
      } else {
         return null;
      }
   }

   private AccessToken getLegacyAccessToken() {
      Bundle var1 = this.getTokenCachingStrategy().load();
      return var1 != null && LegacyTokenHelper.hasTokenInformation(var1)?AccessToken.createFromLegacyCache(var1):null;
   }

   private LegacyTokenHelper getTokenCachingStrategy() {
      // $FF: Couldn't be decompiled
   }

   private boolean hasCachedAccessToken() {
      return this.sharedPreferences.contains("com.facebook.AccessTokenManager.CachedAccessToken");
   }

   private boolean shouldCheckLegacyToken() {
      return FacebookSdk.isLegacyTokenUpgradeSupported();
   }

   public void clear() {
      this.sharedPreferences.edit().remove("com.facebook.AccessTokenManager.CachedAccessToken").apply();
      if(this.shouldCheckLegacyToken()) {
         this.getTokenCachingStrategy().clear();
      }

   }

   public AccessToken load() {
      if(this.hasCachedAccessToken()) {
         return this.getCachedAccessToken();
      } else {
         AccessToken var1;
         if(this.shouldCheckLegacyToken()) {
            AccessToken var2 = this.getLegacyAccessToken();
            var1 = var2;
            if(var2 != null) {
               this.save(var2);
               this.getTokenCachingStrategy().clear();
               return var2;
            }
         } else {
            var1 = null;
         }

         return var1;
      }
   }

   public void save(AccessToken var1) {
      Validate.notNull(var1, "accessToken");

      try {
         JSONObject var3 = var1.toJSONObject();
         this.sharedPreferences.edit().putString("com.facebook.AccessTokenManager.CachedAccessToken", var3.toString()).apply();
      } catch (JSONException var2) {
         ;
      }
   }

   static class SharedPreferencesTokenCachingStrategyFactory {

      public LegacyTokenHelper create() {
         return new LegacyTokenHelper(FacebookSdk.getApplicationContext());
      }
   }
}
