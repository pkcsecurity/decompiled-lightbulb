package com.facebook.react.packagerconnection;

import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import com.facebook.react.packagerconnection.JSPackagerClient;
import com.facebook.react.packagerconnection.RequestHandler;
import com.facebook.react.packagerconnection.RequestOnlyHandler;
import com.facebook.react.packagerconnection.Responder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

public class FileIoHandler implements Runnable {

   private static final long FILE_TTL = 30000L;
   private static final String TAG = JSPackagerClient.class.getSimpleName();
   private final Handler mHandler = new Handler(Looper.getMainLooper());
   private int mNextHandle = 1;
   private final Map<Integer, FileIoHandler.TtlFileInputStream> mOpenFiles = new HashMap();
   private final Map<String, RequestHandler> mRequestHandlers = new HashMap();


   public FileIoHandler() {
      this.mRequestHandlers.put("fopen", new RequestOnlyHandler() {
         public void onRequest(@Nullable Object param1, Responder param2) {
            // $FF: Couldn't be decompiled
         }
      });
      this.mRequestHandlers.put("fclose", new RequestOnlyHandler() {
         public void onRequest(@Nullable Object param1, Responder param2) {
            // $FF: Couldn't be decompiled
         }
      });
      this.mRequestHandlers.put("fread", new RequestOnlyHandler() {
         public void onRequest(@Nullable Object param1, Responder param2) {
            // $FF: Couldn't be decompiled
         }
      });
   }

   // $FF: synthetic method
   static Map access$000(FileIoHandler var0) {
      return var0.mOpenFiles;
   }

   // $FF: synthetic method
   static int access$100(FileIoHandler var0, String var1) throws FileNotFoundException {
      return var0.addOpenFile(var1);
   }

   private int addOpenFile(String var1) throws FileNotFoundException {
      int var2 = this.mNextHandle;
      this.mNextHandle = var2 + 1;
      this.mOpenFiles.put(Integer.valueOf(var2), new FileIoHandler.TtlFileInputStream(var1));
      if(this.mOpenFiles.size() == 1) {
         this.mHandler.postDelayed(this, 30000L);
      }

      return var2;
   }

   public Map<String, RequestHandler> handlers() {
      return this.mRequestHandlers;
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }

   static class TtlFileInputStream {

      private final FileInputStream mStream;
      private long mTtl;


      public TtlFileInputStream(String var1) throws FileNotFoundException {
         this.mStream = new FileInputStream(var1);
         this.mTtl = System.currentTimeMillis() + 30000L;
      }

      private void extendTtl() {
         this.mTtl = System.currentTimeMillis() + 30000L;
      }

      public void close() throws IOException {
         this.mStream.close();
      }

      public boolean expiredTtl() {
         return System.currentTimeMillis() >= this.mTtl;
      }

      public String read(int var1) throws IOException {
         this.extendTtl();
         byte[] var2 = new byte[var1];
         return Base64.encodeToString(var2, 0, this.mStream.read(var2), 0);
      }
   }
}
