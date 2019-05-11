package com.facebook.imagepipeline.listener;

import com.facebook.common.logging.FLog;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public class ForwardingRequestListener implements RequestListener {

   private static final String TAG = "ForwardingRequestListener";
   private final List<RequestListener> mRequestListeners;


   public ForwardingRequestListener(Set<RequestListener> var1) {
      this.mRequestListeners = new ArrayList(var1.size());
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         RequestListener var2 = (RequestListener)var3.next();
         if(var2 != null) {
            this.mRequestListeners.add(var2);
         }
      }

   }

   public ForwardingRequestListener(RequestListener ... var1) {
      this.mRequestListeners = new ArrayList(var1.length);
      int var3 = var1.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         RequestListener var4 = var1[var2];
         if(var4 != null) {
            this.mRequestListeners.add(var4);
         }
      }

   }

   private void onException(String var1, Throwable var2) {
      FLog.e("ForwardingRequestListener", var1, var2);
   }

   public void onProducerEvent(String var1, String var2, String var3) {
      int var5 = this.mRequestListeners.size();

      for(int var4 = 0; var4 < var5; ++var4) {
         RequestListener var6 = (RequestListener)this.mRequestListeners.get(var4);

         try {
            var6.onProducerEvent(var1, var2, var3);
         } catch (Exception var7) {
            this.onException("InternalListener exception in onIntermediateChunkStart", var7);
         }
      }

   }

   public void onProducerFinishWithCancellation(String var1, String var2, @Nullable Map<String, String> var3) {
      int var5 = this.mRequestListeners.size();

      for(int var4 = 0; var4 < var5; ++var4) {
         RequestListener var6 = (RequestListener)this.mRequestListeners.get(var4);

         try {
            var6.onProducerFinishWithCancellation(var1, var2, var3);
         } catch (Exception var7) {
            this.onException("InternalListener exception in onProducerFinishWithCancellation", var7);
         }
      }

   }

   public void onProducerFinishWithFailure(String var1, String var2, Throwable var3, @Nullable Map<String, String> var4) {
      int var6 = this.mRequestListeners.size();

      for(int var5 = 0; var5 < var6; ++var5) {
         RequestListener var7 = (RequestListener)this.mRequestListeners.get(var5);

         try {
            var7.onProducerFinishWithFailure(var1, var2, var3, var4);
         } catch (Exception var8) {
            this.onException("InternalListener exception in onProducerFinishWithFailure", var8);
         }
      }

   }

   public void onProducerFinishWithSuccess(String var1, String var2, @Nullable Map<String, String> var3) {
      int var5 = this.mRequestListeners.size();

      for(int var4 = 0; var4 < var5; ++var4) {
         RequestListener var6 = (RequestListener)this.mRequestListeners.get(var4);

         try {
            var6.onProducerFinishWithSuccess(var1, var2, var3);
         } catch (Exception var7) {
            this.onException("InternalListener exception in onProducerFinishWithSuccess", var7);
         }
      }

   }

   public void onProducerStart(String var1, String var2) {
      int var4 = this.mRequestListeners.size();

      for(int var3 = 0; var3 < var4; ++var3) {
         RequestListener var5 = (RequestListener)this.mRequestListeners.get(var3);

         try {
            var5.onProducerStart(var1, var2);
         } catch (Exception var6) {
            this.onException("InternalListener exception in onProducerStart", var6);
         }
      }

   }

   public void onRequestCancellation(String var1) {
      int var3 = this.mRequestListeners.size();

      for(int var2 = 0; var2 < var3; ++var2) {
         RequestListener var4 = (RequestListener)this.mRequestListeners.get(var2);

         try {
            var4.onRequestCancellation(var1);
         } catch (Exception var5) {
            this.onException("InternalListener exception in onRequestCancellation", var5);
         }
      }

   }

   public void onRequestFailure(ImageRequest var1, String var2, Throwable var3, boolean var4) {
      int var6 = this.mRequestListeners.size();

      for(int var5 = 0; var5 < var6; ++var5) {
         RequestListener var7 = (RequestListener)this.mRequestListeners.get(var5);

         try {
            var7.onRequestFailure(var1, var2, var3, var4);
         } catch (Exception var8) {
            this.onException("InternalListener exception in onRequestFailure", var8);
         }
      }

   }

   public void onRequestStart(ImageRequest var1, Object var2, String var3, boolean var4) {
      int var6 = this.mRequestListeners.size();

      for(int var5 = 0; var5 < var6; ++var5) {
         RequestListener var7 = (RequestListener)this.mRequestListeners.get(var5);

         try {
            var7.onRequestStart(var1, var2, var3, var4);
         } catch (Exception var8) {
            this.onException("InternalListener exception in onRequestStart", var8);
         }
      }

   }

   public void onRequestSuccess(ImageRequest var1, String var2, boolean var3) {
      int var5 = this.mRequestListeners.size();

      for(int var4 = 0; var4 < var5; ++var4) {
         RequestListener var6 = (RequestListener)this.mRequestListeners.get(var4);

         try {
            var6.onRequestSuccess(var1, var2, var3);
         } catch (Exception var7) {
            this.onException("InternalListener exception in onRequestSuccess", var7);
         }
      }

   }

   public void onUltimateProducerReached(String var1, String var2, boolean var3) {
      int var5 = this.mRequestListeners.size();

      for(int var4 = 0; var4 < var5; ++var4) {
         RequestListener var6 = (RequestListener)this.mRequestListeners.get(var4);

         try {
            var6.onUltimateProducerReached(var1, var2, var3);
         } catch (Exception var7) {
            this.onException("InternalListener exception in onProducerFinishWithSuccess", var7);
         }
      }

   }

   public boolean requiresExtraMap(String var1) {
      int var3 = this.mRequestListeners.size();

      for(int var2 = 0; var2 < var3; ++var2) {
         if(((RequestListener)this.mRequestListeners.get(var2)).requiresExtraMap(var1)) {
            return true;
         }
      }

      return false;
   }
}
