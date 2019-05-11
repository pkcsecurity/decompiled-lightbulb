package com.tuya.smart.android.network;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.android.common.utils.TyCommonUtil;
import com.tuya.smart.android.network.IApiUrlProvider;
import com.tuya.smart.android.network.Business.OnNeedLoginListener;
import com.tuya.smart.android.network.util.TLSSocketFactory;
import com.tuya.smart.common.oo00o0o0o;
import com.tuya.smart.common.oo00o0ooo;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

public class TuyaSmartNetWork {

   public static final String CHANNEL_OEM = "oem";
   public static final String CHANNEL_SDK = "sdk";
   public static final String DOMAIN_AY = "AY";
   public static final String DOMAIN_AZ = "AZ";
   public static final String DOMAIN_EU = "EU";
   public static final String DOMAIN_IN = "IN";
   public static final String DOMAIN_SH = "SH";
   private static final String DOMAIN_Undefined = "UE";
   private static final String TAG = "TuyaSmartNetWork";
   public static String USER_AGENT;
   public static IApiUrlProvider mApiUrlProvider;
   public static Context mAppContext;
   public static boolean mAppDebug;
   public static String mAppId;
   public static String mAppRNVersion;
   public static String mAppSecret;
   public static String mAppVersion;
   public static String mChannel;
   public static boolean mNTY;
   private static OnNeedLoginListener mNeedLoginListener;
   public static boolean mSdk;
   public static String mSdkVersion;
   public static String mTtid;
   private static volatile OkHttpClient sOkHttpClient;


   public static void cancelALlNetwork() {
      getOkHttpClient().dispatcher().cancelAll();
   }

   public static Context getAppContext() {
      return mAppContext.getApplicationContext();
   }

   public static String getDefaultRegionByPhoneCode(String var0) {
      if(TextUtils.equals(var0, "86")) {
         return "AY";
      } else {
         if(var0 != null) {
            StringBuilder var1 = new StringBuilder();
            var1.append(",");
            var1.append(var0);
            var1.append(",");
            if(",61,591,1,56,57,593,594,502,62,81,82,60,52,95,64,595,51,63,1787,39,597,66,598,58,84,54,55,852,853,886,505,".contains(var1.toString())) {
               return "AZ";
            }
         }

         return "EU";
      }
   }

   public static String getGwApiUrl() {
      return mApiUrlProvider.getGwApiUrl();
   }

   public static String[] getGwMqttUrl() {
      return mApiUrlProvider.getGwMqttUrl();
   }

   public static String[] getMediaMqttUrl() {
      return mApiUrlProvider.getMediaMqttUrl();
   }

   public static String[] getMqttUrl() {
      return mApiUrlProvider.getMqttUrl();
   }

   public static OkHttpClient getOkHttpClient() {
      // $FF: Couldn't be decompiled
   }

   public static String getRegion() {
      return mApiUrlProvider.getRegion();
   }

   public static Map<String, String> getRequestHeaders() {
      HashMap var0 = new HashMap();
      var0.put("User-Agent", USER_AGENT);
      return var0;
   }

   public static String getServiceHostUrl() {
      return mApiUrlProvider.getApiUrl();
   }

   public static String getServiceHostUrl(String var0) {
      return mApiUrlProvider.getApiUrlByCountryCode(var0);
   }

   public static String getTtid() {
      return mTtid;
   }

   public static void initialize(Context var0, String var1, String var2, String var3, String var4, String var5, IApiUrlProvider var6) {
      initialize(var0, var1, var2, var3, "sdk", var4, var5, var6);
   }

   public static void initialize(Context var0, String var1, String var2, String var3, String var4, String var5, String var6, IApiUrlProvider var7) {
      mAppContext = var0;
      mAppId = var1;
      mAppSecret = var2;
      mTtid = var3;
      if(TextUtils.isEmpty(mAppVersion)) {
         var1 = TyCommonUtil.getAppVersionName(var0);
         if(!TextUtils.isEmpty(var1)) {
            mAppVersion = var1;
         }
      } else {
         mAppVersion = var5;
      }

      if(TextUtils.isEmpty(mAppVersion)) {
         mAppVersion = mSdkVersion;
      }

      if(var7 != null) {
         mApiUrlProvider = var7;
      }

      if(!TextUtils.isEmpty(var6)) {
         mAppRNVersion = var6;
      }

      if(TextUtils.isEmpty(var4)) {
         mChannel = "sdk";
      } else {
         mChannel = var4;
      }

      StringBuilder var8 = new StringBuilder();
      var8.append(USER_AGENT);
      var8.append("/");
      var8.append(mAppVersion);
      var8.append("/SDK/");
      var8.append(mSdkVersion);
      USER_AGENT = var8.toString();
      setDebugMode(mAppDebug);
      oo00o0o0o.O000000o(var0);
   }

   public static void needLogin() {
      if(mNeedLoginListener != null) {
         mNeedLoginListener.onNeedLogin(mAppContext);
      }

   }

   private static OkHttpClient newOKHttpClient() {
      Builder var0 = new Builder();
      var0.readTimeout(10L, TimeUnit.SECONDS);
      var0.connectTimeout(10L, TimeUnit.SECONDS);
      var0.writeTimeout(10L, TimeUnit.SECONDS);
      var0.addInterceptor(new oo00o0ooo());
      if(VERSION.SDK_INT <= 19) {
         try {
            X509TrustManager var1 = systemDefaultTrustManager();
            var0.sslSocketFactory(new TLSSocketFactory(systemDefaultSslSocketFactory(var1)), var1);
            L.d("TuyaSmartNetWork", "enable support protocols");
         } catch (Throwable var3) {
            StringBuilder var2 = new StringBuilder();
            var2.append("enable support protocols error");
            var2.append(var3.getMessage());
            L.e("TuyaSmartNetWork", var2.toString());
         }
      }

      return var0.build();
   }

   public static void setDebugMode(boolean var0) {
      mAppDebug = var0;
   }

   public static void setOnNeedLoginListener(OnNeedLoginListener var0) {
      mNeedLoginListener = var0;
   }

   public static void switchServer(String var0) {
      mApiUrlProvider.setRegion(var0);
   }

   private static SSLSocketFactory systemDefaultSslSocketFactory(X509TrustManager var0) {
      try {
         SSLContext var1 = SSLContext.getInstance("TLS");
         var1.init((KeyManager[])null, new TrustManager[]{var0}, (SecureRandom)null);
         SSLSocketFactory var3 = var1.getSocketFactory();
         return var3;
      } catch (GeneralSecurityException var2) {
         L.e("TuyaSmartNetWork", "The system has no SSLSocketFactory");
         throw new AssertionError();
      }
   }

   private static X509TrustManager systemDefaultTrustManager() {
      try {
         TrustManagerFactory var0 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
         var0.init((KeyStore)null);
         TrustManager[] var3 = var0.getTrustManagers();
         if(var3.length == 1 && var3[0] instanceof X509TrustManager) {
            return (X509TrustManager)var3[0];
         } else {
            StringBuilder var1 = new StringBuilder();
            var1.append("Unexpected default trust managers:");
            var1.append(Arrays.toString(var3));
            throw new IllegalStateException(var1.toString());
         }
      } catch (GeneralSecurityException var2) {
         L.e("TuyaSmartNetWork", "The system has no X509TrustManager");
         throw new AssertionError();
      }
   }
}
