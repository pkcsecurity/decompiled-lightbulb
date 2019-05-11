package com.facebook.react.bridge;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.CatalystInstanceImpl;
import com.facebook.react.bridge.JSBundleLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public final class FallbackJSBundleLoader extends JSBundleLoader {

   static final String RECOVERABLE = "facebook::react::Recoverable";
   static final String TAG = "FallbackJSBundleLoader";
   private Stack<JSBundleLoader> mLoaders = new Stack();
   private final ArrayList<Exception> mRecoveredErrors = new ArrayList();


   public FallbackJSBundleLoader(List<JSBundleLoader> var1) {
      ListIterator var2 = var1.listIterator(var1.size());

      while(var2.hasPrevious()) {
         this.mLoaders.push(var2.previous());
      }

   }

   private JSBundleLoader getDelegateLoader() {
      if(!this.mLoaders.empty()) {
         return (JSBundleLoader)this.mLoaders.peek();
      } else {
         RuntimeException var3 = new RuntimeException("No fallback options available");
         Iterator var4 = this.mRecoveredErrors.iterator();
         Object var1 = var3;

         while(var4.hasNext()) {
            ((Throwable)var1).initCause((Exception)var4.next());
            Object var2 = var1;

            while(true) {
               var1 = var2;
               if(((Throwable)var2).getCause() == null) {
                  break;
               }

               var2 = ((Throwable)var2).getCause();
            }
         }

         throw var3;
      }
   }

   public String loadScript(CatalystInstanceImpl var1) {
      while(true) {
         try {
            String var2 = this.getDelegateLoader().loadScript(var1);
            return var2;
         } catch (Exception var3) {
            if(!var3.getMessage().startsWith("facebook::react::Recoverable")) {
               throw var3;
            }

            this.mLoaders.pop();
            this.mRecoveredErrors.add(var3);
            FLog.wtf("FallbackJSBundleLoader", "Falling back from recoverable error", (Throwable)var3);
         }
      }
   }
}
