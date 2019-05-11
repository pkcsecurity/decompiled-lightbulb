package com.facebook.react.bridge;

import android.content.Context;
import com.facebook.react.bridge.CatalystInstanceImpl;
import com.facebook.react.common.DebugServerException;

public abstract class JSBundleLoader {

   public static JSBundleLoader createAssetLoader(final Context var0, final String var1, final boolean var2) {
      return new JSBundleLoader() {
         public String loadScript(CatalystInstanceImpl var1x) {
            var1x.loadScriptFromAssets(var0.getAssets(), var1, var2);
            return var1;
         }
      };
   }

   public static JSBundleLoader createCachedBundleFromNetworkLoader(final String var0, final String var1) {
      return new JSBundleLoader() {
         public String loadScript(CatalystInstanceImpl var1x) {
            try {
               var1x.loadScriptFromFile(var1, var0, false);
               String var3 = var0;
               return var3;
            } catch (Exception var2) {
               throw DebugServerException.makeGeneric(var2.getMessage(), var2);
            }
         }
      };
   }

   public static JSBundleLoader createFileLoader(String var0) {
      return createFileLoader(var0, var0, false);
   }

   public static JSBundleLoader createFileLoader(final String var0, final String var1, final boolean var2) {
      return new JSBundleLoader() {
         public String loadScript(CatalystInstanceImpl var1x) {
            var1x.loadScriptFromFile(var0, var1, var2);
            return var0;
         }
      };
   }

   public static JSBundleLoader createRemoteDebuggerBundleLoader(final String var0, final String var1) {
      return new JSBundleLoader() {
         public String loadScript(CatalystInstanceImpl var1x) {
            var1x.setSourceURLs(var1, var0);
            return var1;
         }
      };
   }

   public abstract String loadScript(CatalystInstanceImpl var1);
}
