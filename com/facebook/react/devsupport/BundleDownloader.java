package com.facebook.react.devsupport;

import android.util.Log;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.common.DebugServerException;
import com.facebook.react.devsupport.MultipartStreamReader;
import com.facebook.react.devsupport.interfaces.DevBundleDownloadListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Request.Builder;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import org.json.JSONException;
import org.json.JSONObject;

public class BundleDownloader {

   private static final int FILES_CHANGED_COUNT_NOT_BUILT_BY_BUNDLER = -2;
   private static final String TAG = "BundleDownloader";
   private final OkHttpClient mClient;
   @Nullable
   private Call mDownloadBundleFromURLCall;


   public BundleDownloader(OkHttpClient var1) {
      this.mClient = var1;
   }

   private static void populateBundleInfo(String var0, Headers var1, BundleDownloader.BundleInfo var2) {
      var2.mUrl = var0;
      var0 = var1.get("X-Metro-Files-Changed-Count");
      if(var0 != null) {
         try {
            var2.mFilesChangedCount = Integer.parseInt(var0);
            return;
         } catch (NumberFormatException var3) {
            var2.mFilesChangedCount = -2;
         }
      }

   }

   private static void processBundleResult(String param0, int param1, Headers param2, BufferedSource param3, File param4, BundleDownloader.BundleInfo param5, DevBundleDownloadListener param6) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void cancelDownloadBundleFromURL() {
      if(this.mDownloadBundleFromURLCall != null) {
         this.mDownloadBundleFromURLCall.cancel();
         this.mDownloadBundleFromURLCall = null;
      }

   }

   public void downloadBundleFromURL(final DevBundleDownloadListener var1, final File var2, String var3, @Nullable final BundleDownloader.BundleInfo var4) {
      Request var5 = (new Builder()).url(var3).build();
      this.mDownloadBundleFromURLCall = (Call)Assertions.assertNotNull(this.mClient.newCall(var5));
      this.mDownloadBundleFromURLCall.enqueue(new Callback() {
         public void onFailure(Call var1x, IOException var2x) {
            if(BundleDownloader.this.mDownloadBundleFromURLCall != null && !BundleDownloader.this.mDownloadBundleFromURLCall.isCanceled()) {
               BundleDownloader.this.mDownloadBundleFromURLCall = null;
               DevBundleDownloadListener var3 = var1;
               StringBuilder var4x = new StringBuilder();
               var4x.append("URL: ");
               var4x.append(var1x.request().url().toString());
               var3.onFailure(DebugServerException.makeGeneric("Could not connect to development server.", var4x.toString(), var2x));
            } else {
               BundleDownloader.this.mDownloadBundleFromURLCall = null;
            }
         }
         public void onResponse(Call var1x, final Response var2x) throws IOException {
            if(BundleDownloader.this.mDownloadBundleFromURLCall != null && !BundleDownloader.this.mDownloadBundleFromURLCall.isCanceled()) {
               BundleDownloader.this.mDownloadBundleFromURLCall = null;
               final String var3 = var2x.request().url().toString();
               String var4x = var2x.header("content-type");
               Matcher var6 = Pattern.compile("multipart/mixed;.*boundary=\"([^\"]+)\"").matcher(var4x);
               if(var6.find()) {
                  var4x = var6.group(1);
                  if(!(new MultipartStreamReader(var2x.body().source(), var4x)).readAllParts(new MultipartStreamReader.ChunkCallback() {
                     public void execute(Map<String, String> param1, Buffer param2, boolean param3) throws IOException {
                        // $FF: Couldn't be decompiled
                     }
                  })) {
                     DevBundleDownloadListener var5 = var1;
                     StringBuilder var7 = new StringBuilder();
                     var7.append("Error while reading multipart response.\n\nResponse code: ");
                     var7.append(var2x.code());
                     var7.append("\n\nURL: ");
                     var7.append(var1x.request().url().toString());
                     var7.append("\n\n");
                     var5.onFailure(new DebugServerException(var7.toString()));
                     return;
                  }
               } else {
                  BundleDownloader.processBundleResult(var3, var2x.code(), var2x.headers(), Okio.buffer(var2x.body().source()), var2, var4, var1);
               }

            } else {
               BundleDownloader.this.mDownloadBundleFromURLCall = null;
            }
         }
      });
   }

   public static class BundleInfo {

      private int mFilesChangedCount;
      @Nullable
      private String mUrl;


      @Nullable
      public static BundleDownloader.BundleInfo fromJSONString(String var0) {
         if(var0 == null) {
            return null;
         } else {
            BundleDownloader.BundleInfo var1 = new BundleDownloader.BundleInfo();

            try {
               JSONObject var3 = new JSONObject(var0);
               var1.mUrl = var3.getString("url");
               var1.mFilesChangedCount = var3.getInt("filesChangedCount");
               return var1;
            } catch (JSONException var2) {
               Log.e("BundleDownloader", "Invalid bundle info: ", var2);
               return null;
            }
         }
      }

      public int getFilesChangedCount() {
         return this.mFilesChangedCount;
      }

      public String getUrl() {
         return this.mUrl != null?this.mUrl:"unknown";
      }

      @Nullable
      public String toJSONString() {
         JSONObject var1 = new JSONObject();

         try {
            var1.put("url", this.mUrl);
            var1.put("filesChangedCount", this.mFilesChangedCount);
         } catch (JSONException var2) {
            Log.e("BundleDownloader", "Can\'t serialize bundle info: ", var2);
            return null;
         }

         return var1.toString();
      }
   }
}
