package com.facebook.react.modules.image;

import android.net.Uri;
import android.util.SparseArray;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import javax.annotation.Nullable;

@ReactModule(
   name = "ImageLoader"
)
public class ImageLoaderModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

   private static final String ERROR_GET_SIZE_FAILURE = "E_GET_SIZE_FAILURE";
   private static final String ERROR_INVALID_URI = "E_INVALID_URI";
   private static final String ERROR_PREFETCH_FAILURE = "E_PREFETCH_FAILURE";
   private final Object mCallerContext;
   private final Object mEnqueuedRequestMonitor = new Object();
   private final SparseArray<DataSource<Void>> mEnqueuedRequests = new SparseArray();


   public ImageLoaderModule(ReactApplicationContext var1) {
      super(var1);
      this.mCallerContext = this;
   }

   public ImageLoaderModule(ReactApplicationContext var1, Object var2) {
      super(var1);
      this.mCallerContext = var2;
   }

   private void registerRequest(int param1, DataSource<Void> param2) {
      // $FF: Couldn't be decompiled
   }

   @Nullable
   private DataSource<Void> removeRequest(int param1) {
      // $FF: Couldn't be decompiled
   }

   @ReactMethod
   public void abortRequest(int var1) {
      DataSource var2 = this.removeRequest(var1);
      if(var2 != null) {
         var2.close();
      }

   }

   public String getName() {
      return "ImageLoader";
   }

   @ReactMethod
   public void getSize(String var1, final Promise var2) {
      if(var1 != null && !var1.isEmpty()) {
         ImageRequest var3 = ImageRequestBuilder.newBuilderWithSource(Uri.parse(var1)).build();
         Fresco.getImagePipeline().fetchDecodedImage(var3, this.mCallerContext).subscribe(new BaseDataSubscriber() {
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> var1) {
               var2.reject("E_GET_SIZE_FAILURE", var1.getFailureCause());
            }
            protected void onNewResultImpl(DataSource<CloseableReference<CloseableImage>> var1) {
               if(var1.isFinished()) {
                  CloseableReference var8 = (CloseableReference)var1.getResult();
                  if(var8 != null) {
                     try {
                        CloseableImage var2x = (CloseableImage)var8.get();
                        WritableMap var3 = Arguments.createMap();
                        var3.putInt("width", var2x.getWidth());
                        var3.putInt("height", var2x.getHeight());
                        var2.resolve(var3);
                     } catch (Exception var6) {
                        var2.reject("E_GET_SIZE_FAILURE", (Throwable)var6);
                     } finally {
                        CloseableReference.closeSafely(var8);
                     }

                  } else {
                     var2.reject("E_GET_SIZE_FAILURE");
                  }
               }
            }
         }, CallerThreadExecutor.getInstance());
      } else {
         var2.reject("E_INVALID_URI", "Cannot get the size of an image for an empty URI");
      }
   }

   public void onHostDestroy() {
      // $FF: Couldn't be decompiled
   }

   public void onHostPause() {}

   public void onHostResume() {}

   @ReactMethod
   public void prefetchImage(String var1, final int var2, final Promise var3) {
      if(var1 != null && !var1.isEmpty()) {
         ImageRequest var4 = ImageRequestBuilder.newBuilderWithSource(Uri.parse(var1)).build();
         DataSource var5 = Fresco.getImagePipeline().prefetchToDiskCache(var4, this.mCallerContext);
         BaseDataSubscriber var6 = new BaseDataSubscriber() {
            protected void onFailureImpl(DataSource<Void> var1) {
               try {
                  ImageLoaderModule.this.removeRequest(var2);
                  var3.reject("E_PREFETCH_FAILURE", var1.getFailureCause());
               } finally {
                  var1.close();
               }

            }
            protected void onNewResultImpl(DataSource<Void> var1) {
               if(var1.isFinished()) {
                  try {
                     ImageLoaderModule.this.removeRequest(var2);
                     var3.resolve(Boolean.valueOf(true));
                  } finally {
                     var1.close();
                  }

               }
            }
         };
         this.registerRequest(var2, var5);
         var5.subscribe(var6, CallerThreadExecutor.getInstance());
      } else {
         var3.reject("E_INVALID_URI", "Cannot prefetch an image for an empty URI");
      }
   }

   @ReactMethod
   public void queryCache(final ReadableArray var1, final Promise var2) {
      (new GuardedAsyncTask(this.getReactApplicationContext()) {
         protected void doInBackgroundGuarded(Void ... var1x) {
            WritableMap var6 = Arguments.createMap();
            ImagePipeline var3 = Fresco.getImagePipeline();

            for(int var2x = 0; var2x < var1.size(); ++var2x) {
               String var4 = var1.getString(var2x);
               Uri var5 = Uri.parse(var4);
               if(var3.isInBitmapMemoryCache(var5)) {
                  var6.putString(var4, "memory");
               } else if(var3.isInDiskCacheSync(var5)) {
                  var6.putString(var4, "disk");
               }
            }

            var2.resolve(var6);
         }
      }).executeOnExecutor(GuardedAsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
   }
}
