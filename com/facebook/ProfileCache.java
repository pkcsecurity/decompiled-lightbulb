package com.facebook;

import android.content.SharedPreferences;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.internal.Validate;
import org.json.JSONException;
import org.json.JSONObject;

final class ProfileCache {

   static final String CACHED_PROFILE_KEY = "com.facebook.ProfileManager.CachedProfile";
   static final String SHARED_PREFERENCES_NAME = "com.facebook.AccessTokenManager.SharedPreferences";
   private final SharedPreferences sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences("com.facebook.AccessTokenManager.SharedPreferences", 0);


   void clear() {
      this.sharedPreferences.edit().remove("com.facebook.ProfileManager.CachedProfile").apply();
   }

   Profile load() {
      String var1 = this.sharedPreferences.getString("com.facebook.ProfileManager.CachedProfile", (String)null);
      if(var1 != null) {
         try {
            Profile var3 = new Profile(new JSONObject(var1));
            return var3;
         } catch (JSONException var2) {
            return null;
         }
      } else {
         return null;
      }
   }

   void save(Profile var1) {
      Validate.notNull(var1, "profile");
      JSONObject var2 = var1.toJSONObject();
      if(var2 != null) {
         this.sharedPreferences.edit().putString("com.facebook.ProfileManager.CachedProfile", var2.toString()).apply();
      }

   }
}
