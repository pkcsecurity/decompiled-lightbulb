package com.facebook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.facebook.AccessTokenManager;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LegacyTokenHelper;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class AccessToken implements Parcelable {

   public static final String ACCESS_TOKEN_KEY = "access_token";
   private static final String APPLICATION_ID_KEY = "application_id";
   public static final Creator<AccessToken> CREATOR = new Creator() {
      public AccessToken createFromParcel(Parcel var1) {
         return new AccessToken(var1);
      }
      public AccessToken[] newArray(int var1) {
         return new AccessToken[var1];
      }
   };
   private static final int CURRENT_JSON_FORMAT = 1;
   public static final String DATA_ACCESS_EXPIRATION_TIME = "data_access_expiration_time";
   private static final String DECLINED_PERMISSIONS_KEY = "declined_permissions";
   private static final AccessTokenSource DEFAULT_ACCESS_TOKEN_SOURCE = AccessTokenSource.FACEBOOK_APPLICATION_WEB;
   private static final Date DEFAULT_EXPIRATION_TIME = MAX_DATE;
   private static final Date DEFAULT_LAST_REFRESH_TIME = new Date();
   private static final String EXPIRES_AT_KEY = "expires_at";
   public static final String EXPIRES_IN_KEY = "expires_in";
   private static final String LAST_REFRESH_KEY = "last_refresh";
   private static final Date MAX_DATE = new Date(Long.MAX_VALUE);
   private static final String PERMISSIONS_KEY = "permissions";
   private static final String SOURCE_KEY = "source";
   private static final String TOKEN_KEY = "token";
   public static final String USER_ID_KEY = "user_id";
   private static final String VERSION_KEY = "version";
   private final String applicationId;
   private final Date dataAccessExpirationTime;
   private final Set<String> declinedPermissions;
   private final Date expires;
   private final Date lastRefresh;
   private final Set<String> permissions;
   private final AccessTokenSource source;
   private final String token;
   private final String userId;


   AccessToken(Parcel var1) {
      this.expires = new Date(var1.readLong());
      ArrayList var2 = new ArrayList();
      var1.readStringList(var2);
      this.permissions = Collections.unmodifiableSet(new HashSet(var2));
      var2.clear();
      var1.readStringList(var2);
      this.declinedPermissions = Collections.unmodifiableSet(new HashSet(var2));
      this.token = var1.readString();
      this.source = AccessTokenSource.valueOf(var1.readString());
      this.lastRefresh = new Date(var1.readLong());
      this.applicationId = var1.readString();
      this.userId = var1.readString();
      this.dataAccessExpirationTime = new Date(var1.readLong());
   }

   public AccessToken(String var1, String var2, String var3, @Nullable Collection<String> var4, @Nullable Collection<String> var5, @Nullable AccessTokenSource var6, @Nullable Date var7, @Nullable Date var8, @Nullable Date var9) {
      Validate.notNullOrEmpty(var1, "accessToken");
      Validate.notNullOrEmpty(var2, "applicationId");
      Validate.notNullOrEmpty(var3, "userId");
      if(var7 == null) {
         var7 = DEFAULT_EXPIRATION_TIME;
      }

      this.expires = var7;
      HashSet var10;
      if(var4 != null) {
         var10 = new HashSet(var4);
      } else {
         var10 = new HashSet();
      }

      this.permissions = Collections.unmodifiableSet(var10);
      if(var5 != null) {
         var10 = new HashSet(var5);
      } else {
         var10 = new HashSet();
      }

      this.declinedPermissions = Collections.unmodifiableSet(var10);
      this.token = var1;
      if(var6 == null) {
         var6 = DEFAULT_ACCESS_TOKEN_SOURCE;
      }

      this.source = var6;
      if(var8 == null) {
         var8 = DEFAULT_LAST_REFRESH_TIME;
      }

      this.lastRefresh = var8;
      this.applicationId = var2;
      this.userId = var3;
      if(var9 == null || var9.getTime() == 0L) {
         var9 = DEFAULT_EXPIRATION_TIME;
      }

      this.dataAccessExpirationTime = var9;
   }

   private void appendPermissions(StringBuilder var1) {
      var1.append(" permissions:");
      if(this.permissions == null) {
         var1.append("null");
      } else {
         var1.append("[");
         var1.append(TextUtils.join(", ", this.permissions));
         var1.append("]");
      }
   }

   static AccessToken createExpired(AccessToken var0) {
      return new AccessToken(var0.token, var0.applicationId, var0.getUserId(), var0.getPermissions(), var0.getDeclinedPermissions(), var0.source, new Date(), new Date(), var0.dataAccessExpirationTime);
   }

   private static AccessToken createFromBundle(List<String> var0, Bundle var1, AccessTokenSource var2, Date var3, String var4) {
      String var5 = var1.getString("access_token");
      var3 = Utility.getBundleLongAsDate(var1, "expires_in", var3);
      String var6 = var1.getString("user_id");
      Date var7 = Utility.getBundleLongAsDate(var1, "data_access_expiration_time", new Date(0L));
      return !Utility.isNullOrEmpty(var5) && var3 != null?new AccessToken(var5, var4, var6, var0, (Collection)null, var2, var3, new Date(), var7):null;
   }

   static AccessToken createFromJSONObject(JSONObject var0) throws JSONException {
      if(var0.getInt("version") > 1) {
         throw new FacebookException("Unknown AccessToken serialization format.");
      } else {
         String var1 = var0.getString("token");
         Date var2 = new Date(var0.getLong("expires_at"));
         JSONArray var3 = var0.getJSONArray("permissions");
         JSONArray var4 = var0.getJSONArray("declined_permissions");
         Date var5 = new Date(var0.getLong("last_refresh"));
         AccessTokenSource var6 = AccessTokenSource.valueOf(var0.getString("source"));
         String var7 = var0.getString("application_id");
         String var8 = var0.getString("user_id");
         Date var9 = new Date(var0.getLong("data_access_expiration_time"));
         return new AccessToken(var1, var7, var8, Utility.jsonArrayToStringList(var3), Utility.jsonArrayToStringList(var4), var6, var2, var5, var9);
      }
   }

   static AccessToken createFromLegacyCache(Bundle var0) {
      List var3 = getPermissionsFromBundle(var0, "com.facebook.TokenCachingStrategy.Permissions");
      List var4 = getPermissionsFromBundle(var0, "com.facebook.TokenCachingStrategy.DeclinedPermissions");
      String var2 = LegacyTokenHelper.getApplicationId(var0);
      String var1 = var2;
      if(Utility.isNullOrEmpty(var2)) {
         var1 = FacebookSdk.getApplicationId();
      }

      var2 = LegacyTokenHelper.getToken(var0);
      JSONObject var5 = Utility.awaitGetGraphMeRequestWithCache(var2);

      String var7;
      try {
         var7 = var5.getString("id");
      } catch (JSONException var6) {
         return null;
      }

      return new AccessToken(var2, var1, var7, var3, var4, LegacyTokenHelper.getSource(var0), LegacyTokenHelper.getDate(var0, "com.facebook.TokenCachingStrategy.ExpirationDate"), LegacyTokenHelper.getDate(var0, "com.facebook.TokenCachingStrategy.LastRefreshDate"), (Date)null);
   }

   public static void createFromNativeLinkingIntent(Intent var0, final String var1, final AccessToken.AccessTokenCreationCallback var2) {
      Validate.notNull(var0, "intent");
      if(var0.getExtras() == null) {
         var2.onError(new FacebookException("No extras found on intent"));
      } else {
         final Bundle var5 = new Bundle(var0.getExtras());
         String var3 = var5.getString("access_token");
         if(var3 != null && !var3.isEmpty()) {
            String var4 = var5.getString("user_id");
            if(var4 != null && !var4.isEmpty()) {
               var2.onSuccess(createFromBundle((List)null, var5, AccessTokenSource.FACEBOOK_APPLICATION_WEB, new Date(), var1));
            } else {
               Utility.getGraphMeRequestWithCacheAsync(var3, new Utility.GraphMeRequestWithCacheCallback() {
                  public void onFailure(FacebookException var1x) {
                     var2.onError(var1x);
                  }
                  public void onSuccess(JSONObject var1x) {
                     try {
                        String var3 = var1x.getString("id");
                        var5.putString("user_id", var3);
                        var2.onSuccess(AccessToken.createFromBundle((List)null, var5, AccessTokenSource.FACEBOOK_APPLICATION_WEB, new Date(), var1));
                     } catch (JSONException var2x) {
                        var2.onError(new FacebookException("Unable to generate access token due to missing user id"));
                     }
                  }
               });
            }
         } else {
            var2.onError(new FacebookException("No access token found on intent"));
         }
      }
   }

   @SuppressLint({"FieldGetter"})
   static AccessToken createFromRefresh(AccessToken var0, Bundle var1) {
      if(var0.source != AccessTokenSource.FACEBOOK_APPLICATION_WEB && var0.source != AccessTokenSource.FACEBOOK_APPLICATION_NATIVE && var0.source != AccessTokenSource.FACEBOOK_APPLICATION_SERVICE) {
         StringBuilder var5 = new StringBuilder();
         var5.append("Invalid token source: ");
         var5.append(var0.source);
         throw new FacebookException(var5.toString());
      } else {
         Date var2 = Utility.getBundleLongAsDate(var1, "expires_in", new Date(0L));
         String var3 = var1.getString("access_token");
         Date var4 = Utility.getBundleLongAsDate(var1, "data_access_expiration_time", new Date(0L));
         return Utility.isNullOrEmpty(var3)?null:new AccessToken(var3, var0.applicationId, var0.getUserId(), var0.getPermissions(), var0.getDeclinedPermissions(), var0.source, var2, new Date(), var4);
      }
   }

   static void expireCurrentAccessToken() {
      AccessToken var0 = AccessTokenManager.getInstance().getCurrentAccessToken();
      if(var0 != null) {
         setCurrentAccessToken(createExpired(var0));
      }

   }

   public static AccessToken getCurrentAccessToken() {
      return AccessTokenManager.getInstance().getCurrentAccessToken();
   }

   static List<String> getPermissionsFromBundle(Bundle var0, String var1) {
      ArrayList var2 = var0.getStringArrayList(var1);
      return var2 == null?Collections.emptyList():Collections.unmodifiableList(new ArrayList(var2));
   }

   public static boolean isCurrentAccessTokenActive() {
      AccessToken var0 = AccessTokenManager.getInstance().getCurrentAccessToken();
      return var0 != null && !var0.isExpired();
   }

   public static boolean isDataAccessActive() {
      AccessToken var0 = AccessTokenManager.getInstance().getCurrentAccessToken();
      return var0 != null && !var0.isDataAccessExpired();
   }

   public static void refreshCurrentAccessTokenAsync() {
      AccessTokenManager.getInstance().refreshCurrentAccessToken((AccessToken.AccessTokenRefreshCallback)null);
   }

   public static void refreshCurrentAccessTokenAsync(AccessToken.AccessTokenRefreshCallback var0) {
      AccessTokenManager.getInstance().refreshCurrentAccessToken(var0);
   }

   public static void setCurrentAccessToken(AccessToken var0) {
      AccessTokenManager.getInstance().setCurrentAccessToken(var0);
   }

   private String tokenToString() {
      return this.token == null?"null":(FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.INCLUDE_ACCESS_TOKENS)?this.token:"ACCESS_TOKEN_REMOVED");
   }

   public int describeContents() {
      return 0;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof AccessToken)) {
         return false;
      } else {
         AccessToken var2 = (AccessToken)var1;
         if(this.expires.equals(var2.expires) && this.permissions.equals(var2.permissions) && this.declinedPermissions.equals(var2.declinedPermissions) && this.token.equals(var2.token) && this.source == var2.source && this.lastRefresh.equals(var2.lastRefresh)) {
            if(this.applicationId == null) {
               if(var2.applicationId != null) {
                  return false;
               }
            } else if(!this.applicationId.equals(var2.applicationId)) {
               return false;
            }

            if(this.userId.equals(var2.userId) && this.dataAccessExpirationTime.equals(var2.dataAccessExpirationTime)) {
               return true;
            }
         }

         return false;
      }
   }

   public String getApplicationId() {
      return this.applicationId;
   }

   public Date getDataAccessExpirationTime() {
      return this.dataAccessExpirationTime;
   }

   public Set<String> getDeclinedPermissions() {
      return this.declinedPermissions;
   }

   public Date getExpires() {
      return this.expires;
   }

   public Date getLastRefresh() {
      return this.lastRefresh;
   }

   public Set<String> getPermissions() {
      return this.permissions;
   }

   public AccessTokenSource getSource() {
      return this.source;
   }

   public String getToken() {
      return this.token;
   }

   public String getUserId() {
      return this.userId;
   }

   public int hashCode() {
      int var2 = this.expires.hashCode();
      int var3 = this.permissions.hashCode();
      int var4 = this.declinedPermissions.hashCode();
      int var5 = this.token.hashCode();
      int var6 = this.source.hashCode();
      int var7 = this.lastRefresh.hashCode();
      int var1;
      if(this.applicationId == null) {
         var1 = 0;
      } else {
         var1 = this.applicationId.hashCode();
      }

      return ((((((((527 + var2) * 31 + var3) * 31 + var4) * 31 + var5) * 31 + var6) * 31 + var7) * 31 + var1) * 31 + this.userId.hashCode()) * 31 + this.dataAccessExpirationTime.hashCode();
   }

   public boolean isDataAccessExpired() {
      return (new Date()).after(this.dataAccessExpirationTime);
   }

   public boolean isExpired() {
      return (new Date()).after(this.expires);
   }

   JSONObject toJSONObject() throws JSONException {
      JSONObject var1 = new JSONObject();
      var1.put("version", 1);
      var1.put("token", this.token);
      var1.put("expires_at", this.expires.getTime());
      var1.put("permissions", new JSONArray(this.permissions));
      var1.put("declined_permissions", new JSONArray(this.declinedPermissions));
      var1.put("last_refresh", this.lastRefresh.getTime());
      var1.put("source", this.source.name());
      var1.put("application_id", this.applicationId);
      var1.put("user_id", this.userId);
      var1.put("data_access_expiration_time", this.dataAccessExpirationTime.getTime());
      return var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("{AccessToken");
      var1.append(" token:");
      var1.append(this.tokenToString());
      this.appendPermissions(var1);
      var1.append("}");
      return var1.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeLong(this.expires.getTime());
      var1.writeStringList(new ArrayList(this.permissions));
      var1.writeStringList(new ArrayList(this.declinedPermissions));
      var1.writeString(this.token);
      var1.writeString(this.source.name());
      var1.writeLong(this.lastRefresh.getTime());
      var1.writeString(this.applicationId);
      var1.writeString(this.userId);
      var1.writeLong(this.dataAccessExpirationTime.getTime());
   }

   public interface AccessTokenCreationCallback {

      void onError(FacebookException var1);

      void onSuccess(AccessToken var1);
   }

   public interface AccessTokenRefreshCallback {

      void OnTokenRefreshFailed(FacebookException var1);

      void OnTokenRefreshed(AccessToken var1);
   }
}
