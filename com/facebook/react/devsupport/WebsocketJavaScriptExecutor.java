package com.facebook.react.devsupport;

import android.os.Handler;
import android.os.Looper;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.JavaJSExecutor;
import com.facebook.react.devsupport.JSDebuggerWebSocketClient;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

public class WebsocketJavaScriptExecutor implements JavaJSExecutor {

   private static final int CONNECT_RETRY_COUNT = 3;
   private static final long CONNECT_TIMEOUT_MS = 5000L;
   private final HashMap<String, String> mInjectedObjects = new HashMap();
   @Nullable
   private JSDebuggerWebSocketClient mWebSocketClient;


   private void connectInternal(String var1, final WebsocketJavaScriptExecutor.JSExecutorConnectCallback var2) {
      final JSDebuggerWebSocketClient var3 = new JSDebuggerWebSocketClient();
      final Handler var4 = new Handler(Looper.getMainLooper());
      var3.connect(var1, new JSDebuggerWebSocketClient.JSDebuggerCallback() {

         private boolean didSendResult = false;

         public void onFailure(Throwable var1) {
            var4.removeCallbacksAndMessages((Object)null);
            if(!this.didSendResult) {
               var2.onFailure(var1);
               this.didSendResult = true;
            }

         }
         public void onSuccess(@Nullable String var1) {
            var3.prepareJSRuntime(new JSDebuggerWebSocketClient.JSDebuggerCallback() {
               public void onFailure(Throwable var1) {
                  var4.removeCallbacksAndMessages((Object)null);
                  if(!<VAR_NAMELESS_ENCLOSURE>.didSendResult) {
                     var2.onFailure(var1);
                     <VAR_NAMELESS_ENCLOSURE>.didSendResult = true;
                  }

               }
               public void onSuccess(@Nullable String var1) {
                  var4.removeCallbacksAndMessages((Object)null);
                  WebsocketJavaScriptExecutor.this.mWebSocketClient = var3;
                  if(!<VAR_NAMELESS_ENCLOSURE>.didSendResult) {
                     var2.onSuccess();
                     <VAR_NAMELESS_ENCLOSURE>.didSendResult = true;
                  }

               }
            });
         }
      });
      var4.postDelayed(new Runnable() {
         public void run() {
            var3.closeQuietly();
            var2.onFailure(new WebsocketJavaScriptExecutor.WebsocketExecutorTimeoutException("Timeout while connecting to remote debugger"));
         }
      }, 5000L);
   }

   public void close() {
      if(this.mWebSocketClient != null) {
         this.mWebSocketClient.closeQuietly();
      }

   }

   public void connect(final String var1, final WebsocketJavaScriptExecutor.JSExecutorConnectCallback var2) {
      this.connectInternal(var1, new WebsocketJavaScriptExecutor.JSExecutorConnectCallback() {

         // $FF: synthetic field
         final AtomicInteger val$retryCount;

         {
            this.val$retryCount = var3;
         }
         public void onFailure(Throwable var1x) {
            if(this.val$retryCount.decrementAndGet() <= 0) {
               var2.onFailure(var1x);
            } else {
               WebsocketJavaScriptExecutor.this.connectInternal(var1, this);
            }
         }
         public void onSuccess() {
            var2.onSuccess();
         }
      });
   }

   @Nullable
   public String executeJSCall(String var1, String var2) throws JavaJSExecutor.ProxyExecutorException {
      WebsocketJavaScriptExecutor.JSExecutorCallbackFuture var3 = new WebsocketJavaScriptExecutor.JSExecutorCallbackFuture(null);
      ((JSDebuggerWebSocketClient)Assertions.assertNotNull(this.mWebSocketClient)).executeJSCall(var1, var2, var3);

      try {
         var1 = var3.get();
         return var1;
      } catch (Throwable var4) {
         throw new JavaJSExecutor.ProxyExecutorException(var4);
      }
   }

   public void loadApplicationScript(String var1) throws JavaJSExecutor.ProxyExecutorException {
      WebsocketJavaScriptExecutor.JSExecutorCallbackFuture var2 = new WebsocketJavaScriptExecutor.JSExecutorCallbackFuture(null);
      ((JSDebuggerWebSocketClient)Assertions.assertNotNull(this.mWebSocketClient)).loadApplicationScript(var1, this.mInjectedObjects, var2);

      try {
         var2.get();
      } catch (Throwable var3) {
         throw new JavaJSExecutor.ProxyExecutorException(var3);
      }
   }

   public void setGlobalVariable(String var1, String var2) {
      this.mInjectedObjects.put(var1, var2);
   }

   public static class WebsocketExecutorTimeoutException extends Exception {

      public WebsocketExecutorTimeoutException(String var1) {
         super(var1);
      }
   }

   static class JSExecutorCallbackFuture implements JSDebuggerWebSocketClient.JSDebuggerCallback {

      @Nullable
      private Throwable mCause;
      @Nullable
      private String mResponse;
      private final Semaphore mSemaphore;


      private JSExecutorCallbackFuture() {
         this.mSemaphore = new Semaphore(0);
      }

      // $FF: synthetic method
      JSExecutorCallbackFuture(Object var1) {
         this();
      }

      @Nullable
      public String get() throws Throwable {
         this.mSemaphore.acquire();
         if(this.mCause != null) {
            throw this.mCause;
         } else {
            return this.mResponse;
         }
      }

      public void onFailure(Throwable var1) {
         this.mCause = var1;
         this.mSemaphore.release();
      }

      public void onSuccess(@Nullable String var1) {
         this.mResponse = var1;
         this.mSemaphore.release();
      }
   }

   public interface JSExecutorConnectCallback {

      void onFailure(Throwable var1);

      void onSuccess();
   }
}
