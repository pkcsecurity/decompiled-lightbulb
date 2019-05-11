package com.facebook.react.bridge;

import com.facebook.common.logging.FLog;
import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.ReactBridge;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@DoNotStrip
public class Inspector {

   private final HybridData mHybridData;


   static {
      ReactBridge.staticInit();
   }

   private Inspector(HybridData var1) {
      this.mHybridData = var1;
   }

   public static Inspector.LocalConnection connect(int var0, Inspector.RemoteConnection var1) {
      try {
         Inspector.LocalConnection var3 = instance().connectNative(var0, var1);
         return var3;
      } catch (UnsatisfiedLinkError var2) {
         FLog.e("ReactNative", "Inspector doesn\'t work in open source yet", (Throwable)var2);
         throw new RuntimeException(var2);
      }
   }

   private native Inspector.LocalConnection connectNative(int var1, Inspector.RemoteConnection var2);

   public static List<Inspector.Page> getPages() {
      try {
         List var0 = Arrays.asList(instance().getPagesNative());
         return var0;
      } catch (UnsatisfiedLinkError var1) {
         FLog.e("ReactNative", "Inspector doesn\'t work in open source yet", (Throwable)var1);
         return Collections.emptyList();
      }
   }

   private native Inspector.Page[] getPagesNative();

   private static native Inspector instance();

   @DoNotStrip
   public interface RemoteConnection {

      void onDisconnect();

      void onMessage(String var1);
   }

   @DoNotStrip
   public static class Page {

      private final int mId;
      private final String mTitle;


      @DoNotStrip
      private Page(int var1, String var2) {
         this.mId = var1;
         this.mTitle = var2;
      }

      public int getId() {
         return this.mId;
      }

      public String getTitle() {
         return this.mTitle;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Page{mId=");
         var1.append(this.mId);
         var1.append(", mTitle=\'");
         var1.append(this.mTitle);
         var1.append('\'');
         var1.append('}');
         return var1.toString();
      }
   }

   @DoNotStrip
   public static class LocalConnection {

      private final HybridData mHybridData;


      private LocalConnection(HybridData var1) {
         this.mHybridData = var1;
      }

      public native void disconnect();

      public native void sendMessage(String var1);
   }
}
