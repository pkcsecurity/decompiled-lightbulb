package com.facebook.react.modules.blob;

import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.Nullable;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.websocket.WebSocketModule;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import okio.ByteString;

@ReactModule(
   name = "BlobModule"
)
public class BlobModule extends ReactContextBaseJavaModule {

   protected static final String NAME = "BlobModule";
   private final Map<String, byte[]> mBlobs = new HashMap();
   protected final WebSocketModule.ContentHandler mContentHandler = new WebSocketModule.ContentHandler() {
      public void onMessage(String var1, WritableMap var2) {
         var2.putString("data", var1);
      }
      public void onMessage(ByteString var1, WritableMap var2) {
         byte[] var4 = var1.toByteArray();
         WritableMap var3 = Arguments.createMap();
         var3.putString("blobId", BlobModule.this.store(var4));
         var3.putInt("offset", 0);
         var3.putInt("size", var4.length);
         var2.putMap("data", var3);
         var2.putString("type", "blob");
      }
   };


   public BlobModule(ReactApplicationContext var1) {
      super(var1);
   }

   private WebSocketModule getWebSocketModule() {
      return (WebSocketModule)this.getReactApplicationContext().getNativeModule(WebSocketModule.class);
   }

   @ReactMethod
   public void createFromParts(ReadableArray var1, String var2) {
      ArrayList var5 = new ArrayList(var1.size());
      int var3 = 0;

      int var4;
      for(var4 = 0; var3 < var1.size(); ++var3) {
         ReadableMap var6 = var1.getMap(var3);
         var4 += var6.getInt("size");
         var5.add(var3, var6);
      }

      ByteBuffer var7 = ByteBuffer.allocate(var4);
      Iterator var8 = var5.iterator();

      while(var8.hasNext()) {
         var7.put(this.resolve((ReadableMap)var8.next()));
      }

      this.store(var7.array(), var2);
   }

   @ReactMethod
   public void disableBlobSupport(int var1) {
      this.getWebSocketModule().setContentHandler(var1, (WebSocketModule.ContentHandler)null);
   }

   @ReactMethod
   public void enableBlobSupport(int var1) {
      this.getWebSocketModule().setContentHandler(var1, this.mContentHandler);
   }

   @Nullable
   public Map getConstants() {
      Resources var2 = this.getReactApplicationContext().getResources();
      int var1 = var2.getIdentifier("blob_provider_authority", "string", this.getReactApplicationContext().getPackageName());
      return var1 == 0?null:MapBuilder.of("BLOB_URI_SCHEME", "content", "BLOB_URI_HOST", var2.getString(var1));
   }

   public String getName() {
      return "BlobModule";
   }

   @ReactMethod
   public void release(String var1) {
      this.remove(var1);
   }

   public void remove(String var1) {
      this.mBlobs.remove(var1);
   }

   @Nullable
   public byte[] resolve(Uri var1) {
      String var4 = var1.getLastPathSegment();
      String var5 = var1.getQueryParameter("offset");
      int var2;
      if(var5 != null) {
         var2 = Integer.parseInt(var5, 10);
      } else {
         var2 = 0;
      }

      String var6 = var1.getQueryParameter("size");
      int var3;
      if(var6 != null) {
         var3 = Integer.parseInt(var6, 10);
      } else {
         var3 = -1;
      }

      return this.resolve(var4, var2, var3);
   }

   @Nullable
   public byte[] resolve(ReadableMap var1) {
      return this.resolve(var1.getString("blobId"), var1.getInt("offset"), var1.getInt("size"));
   }

   @Nullable
   public byte[] resolve(String var1, int var2, int var3) {
      byte[] var5 = (byte[])this.mBlobs.get(var1);
      if(var5 == null) {
         return null;
      } else {
         int var4 = var3;
         if(var3 == -1) {
            var4 = var5.length - var2;
         }

         byte[] var6 = var5;
         if(var2 > 0) {
            var6 = Arrays.copyOfRange(var5, var2, var4 + var2);
         }

         return var6;
      }
   }

   @ReactMethod
   public void sendBlob(ReadableMap var1, int var2) {
      byte[] var3 = this.resolve(var1.getString("blobId"), var1.getInt("offset"), var1.getInt("size"));
      if(var3 != null) {
         this.getWebSocketModule().sendBinary(ByteString.of(var3), var2);
      } else {
         this.getWebSocketModule().sendBinary((ByteString)null, var2);
      }
   }

   public String store(byte[] var1) {
      String var2 = UUID.randomUUID().toString();
      this.store(var1, var2);
      return var2;
   }

   public void store(byte[] var1, String var2) {
      this.mBlobs.put(var2, var1);
   }
}
