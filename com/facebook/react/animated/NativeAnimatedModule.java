package com.facebook.react.animated;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.animated.AnimatedNodeValueListener;
import com.facebook.react.animated.NativeAnimatedNodesManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.annotations.VisibleForTesting;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.ReactChoreographer;
import com.facebook.react.uimanager.GuardedFrameCallback;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.UIManagerModuleListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.Nullable;

@ReactModule(
   name = "NativeAnimatedModule"
)
public class NativeAnimatedModule extends ReactContextBaseJavaModule implements LifecycleEventListener, UIManagerModuleListener {

   protected static final String NAME = "NativeAnimatedModule";
   private final GuardedFrameCallback mAnimatedFrameCallback;
   @Nullable
   private NativeAnimatedNodesManager mNodesManager;
   private ArrayList<NativeAnimatedModule.UIThreadOperation> mOperations = new ArrayList();
   private ArrayList<NativeAnimatedModule.UIThreadOperation> mPreOperations = new ArrayList();
   private final ReactChoreographer mReactChoreographer = ReactChoreographer.getInstance();


   public NativeAnimatedModule(final ReactApplicationContext var1) {
      super(var1);
      this.mAnimatedFrameCallback = new GuardedFrameCallback(var1) {
         protected void doFrameGuarded(long var1) {
            NativeAnimatedNodesManager var3 = NativeAnimatedModule.this.getNodesManager();
            if(var3.hasActiveAnimations()) {
               var3.runUpdates(var1);
            }

            ((ReactChoreographer)Assertions.assertNotNull(NativeAnimatedModule.this.mReactChoreographer)).postFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, NativeAnimatedModule.this.mAnimatedFrameCallback);
         }
      };
   }

   private void clearFrameCallback() {
      ((ReactChoreographer)Assertions.assertNotNull(this.mReactChoreographer)).removeFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, this.mAnimatedFrameCallback);
   }

   private void enqueueFrameCallback() {
      ((ReactChoreographer)Assertions.assertNotNull(this.mReactChoreographer)).postFrameCallback(ReactChoreographer.CallbackType.NATIVE_ANIMATED_MODULE, this.mAnimatedFrameCallback);
   }

   private NativeAnimatedNodesManager getNodesManager() {
      if(this.mNodesManager == null) {
         this.mNodesManager = new NativeAnimatedNodesManager((UIManagerModule)this.getReactApplicationContext().getNativeModule(UIManagerModule.class));
      }

      return this.mNodesManager;
   }

   @ReactMethod
   public void addAnimatedEventToView(final int var1, final String var2, final ReadableMap var3) {
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.addAnimatedEventToView(var1, var2, var3);
         }
      });
   }

   @ReactMethod
   public void connectAnimatedNodeToView(final int var1, final int var2) {
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.connectAnimatedNodeToView(var1, var2);
         }
      });
   }

   @ReactMethod
   public void connectAnimatedNodes(final int var1, final int var2) {
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.connectAnimatedNodes(var1, var2);
         }
      });
   }

   @ReactMethod
   public void createAnimatedNode(final int var1, final ReadableMap var2) {
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.createAnimatedNode(var1, var2);
         }
      });
   }

   @ReactMethod
   public void disconnectAnimatedNodeFromView(final int var1, final int var2) {
      this.mPreOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.restoreDefaultValues(var1, var2);
         }
      });
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.disconnectAnimatedNodeFromView(var1, var2);
         }
      });
   }

   @ReactMethod
   public void disconnectAnimatedNodes(final int var1, final int var2) {
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.disconnectAnimatedNodes(var1, var2);
         }
      });
   }

   @ReactMethod
   public void dropAnimatedNode(final int var1) {
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.dropAnimatedNode(var1);
         }
      });
   }

   @ReactMethod
   public void extractAnimatedNodeOffset(final int var1) {
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.extractAnimatedNodeOffset(var1);
         }
      });
   }

   @ReactMethod
   public void flattenAnimatedNodeOffset(final int var1) {
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.flattenAnimatedNodeOffset(var1);
         }
      });
   }

   public String getName() {
      return "NativeAnimatedModule";
   }

   public void initialize() {
      ReactApplicationContext var1 = this.getReactApplicationContext();
      UIManagerModule var2 = (UIManagerModule)var1.getNativeModule(UIManagerModule.class);
      var1.addLifecycleEventListener(this);
      var2.addUIManagerListener(this);
   }

   public void onHostDestroy() {}

   public void onHostPause() {
      this.clearFrameCallback();
   }

   public void onHostResume() {
      this.enqueueFrameCallback();
   }

   @ReactMethod
   public void removeAnimatedEventFromView(final int var1, final String var2, final int var3) {
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.removeAnimatedEventFromView(var1, var2, var3);
         }
      });
   }

   @ReactMethod
   public void setAnimatedNodeOffset(final int var1, final double var2) {
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.setAnimatedNodeOffset(var1, var2);
         }
      });
   }

   @ReactMethod
   public void setAnimatedNodeValue(final int var1, final double var2) {
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.setAnimatedNodeValue(var1, var2);
         }
      });
   }

   @VisibleForTesting
   public void setNodesManager(NativeAnimatedNodesManager var1) {
      this.mNodesManager = var1;
   }

   @ReactMethod
   public void startAnimatingNode(final int var1, final int var2, final ReadableMap var3, final Callback var4) {
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.startAnimatingNode(var1, var2, var3, var4);
         }
      });
   }

   @ReactMethod
   public void startListeningToAnimatedNodeValue(final int var1) {
      final AnimatedNodeValueListener var2 = new AnimatedNodeValueListener() {
         public void onValueUpdate(double var1x) {
            WritableMap var3 = Arguments.createMap();
            var3.putInt("tag", var1);
            var3.putDouble("value", var1x);
            ((DeviceEventManagerModule.RCTDeviceEventEmitter)NativeAnimatedModule.this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("onAnimatedValueUpdate", var3);
         }
      };
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.startListeningToAnimatedNodeValue(var1, var2);
         }
      });
   }

   @ReactMethod
   public void stopAnimation(final int var1) {
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.stopAnimation(var1);
         }
      });
   }

   @ReactMethod
   public void stopListeningToAnimatedNodeValue(final int var1) {
      this.mOperations.add(new NativeAnimatedModule.UIThreadOperation() {
         public void execute(NativeAnimatedNodesManager var1x) {
            var1x.stopListeningToAnimatedNodeValue(var1);
         }
      });
   }

   public void willDispatchViewUpdates(UIManagerModule var1) {
      if(!this.mOperations.isEmpty() || !this.mPreOperations.isEmpty()) {
         final ArrayList var2 = this.mPreOperations;
         final ArrayList var3 = this.mOperations;
         this.mPreOperations = new ArrayList();
         this.mOperations = new ArrayList();
         var1.prependUIBlock(new UIBlock() {
            public void execute(NativeViewHierarchyManager var1) {
               NativeAnimatedNodesManager var3 = NativeAnimatedModule.this.getNodesManager();
               Iterator var2x = var2.iterator();

               while(var2x.hasNext()) {
                  ((NativeAnimatedModule.UIThreadOperation)var2x.next()).execute(var3);
               }

            }
         });
         var1.addUIBlock(new UIBlock() {
            public void execute(NativeViewHierarchyManager var1) {
               NativeAnimatedNodesManager var3x = NativeAnimatedModule.this.getNodesManager();
               Iterator var2 = var3.iterator();

               while(var2.hasNext()) {
                  ((NativeAnimatedModule.UIThreadOperation)var2.next()).execute(var3x);
               }

            }
         });
      }
   }

   interface UIThreadOperation {

      void execute(NativeAnimatedNodesManager var1);
   }
}
