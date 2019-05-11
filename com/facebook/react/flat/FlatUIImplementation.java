package com.facebook.react.flat;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.flat.DraweeRequestHelper;
import com.facebook.react.flat.FlatARTSurfaceViewManager;
import com.facebook.react.flat.FlatNativeViewHierarchyManager;
import com.facebook.react.flat.FlatRootShadowNode;
import com.facebook.react.flat.FlatShadowNode;
import com.facebook.react.flat.FlatUIViewOperationQueue;
import com.facebook.react.flat.MoveProxy;
import com.facebook.react.flat.NativeViewWrapper;
import com.facebook.react.flat.RCTImageView;
import com.facebook.react.flat.RCTImageViewManager;
import com.facebook.react.flat.RCTModalHostManager;
import com.facebook.react.flat.RCTRawTextManager;
import com.facebook.react.flat.RCTTextInlineImageManager;
import com.facebook.react.flat.RCTTextInputManager;
import com.facebook.react.flat.RCTTextManager;
import com.facebook.react.flat.RCTViewManager;
import com.facebook.react.flat.RCTViewPagerManager;
import com.facebook.react.flat.RCTVirtualTextManager;
import com.facebook.react.flat.StateBuilder;
import com.facebook.react.flat.TypefaceCache;
import com.facebook.react.modules.fresco.FrescoModule;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.UIImplementation;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.uimanager.ViewManagerRegistry;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.yoga.YogaDirection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class FlatUIImplementation extends UIImplementation {

   private static final Map<String, Class<? extends ViewManager>> flatManagerClassMap = new HashMap();
   private final boolean mMemoryImprovementEnabled;
   private final MoveProxy mMoveProxy = new MoveProxy();
   @Nullable
   private RCTImageViewManager mRCTImageViewManager;
   private final ReactApplicationContext mReactContext;
   private final StateBuilder mStateBuilder;


   static {
      flatManagerClassMap.put("RCTView", RCTViewManager.class);
      flatManagerClassMap.put("RCTText", RCTTextManager.class);
      flatManagerClassMap.put("RCTRawText", RCTRawTextManager.class);
      flatManagerClassMap.put("RCTVirtualText", RCTVirtualTextManager.class);
      flatManagerClassMap.put("RCTTextInlineImage", RCTTextInlineImageManager.class);
      flatManagerClassMap.put("RCTImageView", RCTImageViewManager.class);
      flatManagerClassMap.put("AndroidTextInput", RCTTextInputManager.class);
      flatManagerClassMap.put("AndroidViewPager", RCTViewPagerManager.class);
      flatManagerClassMap.put("ARTSurfaceView", FlatARTSurfaceViewManager.class);
      flatManagerClassMap.put("RCTModalHostView", RCTModalHostManager.class);
   }

   private FlatUIImplementation(ReactApplicationContext var1, @Nullable RCTImageViewManager var2, ViewManagerRegistry var3, FlatUIViewOperationQueue var4, EventDispatcher var5, boolean var6) {
      super(var1, var3, var4, var5);
      this.mReactContext = var1;
      this.mRCTImageViewManager = var2;
      this.mStateBuilder = new StateBuilder(var4);
      this.mMemoryImprovementEnabled = var6;
   }

   private static void addChildAt(ReactShadowNode var0, ReactShadowNode var1, int var2, int var3) {
      if(var2 <= var3) {
         StringBuilder var4 = new StringBuilder();
         var4.append("Invariant failure, needs sorting! ");
         var4.append(var2);
         var4.append(" <= ");
         var4.append(var3);
         throw new RuntimeException(var4.toString());
      } else {
         var0.addChildAt(var1, var2);
      }
   }

   private void addChildren(ReactShadowNode var1, @Nullable ReadableArray var2, @Nullable ReadableArray var3) {
      int var4 = this.mMoveProxy.size();
      int var6 = 0;
      int var5;
      int var7;
      if(var4 == 0) {
         var7 = Integer.MAX_VALUE;
         var5 = Integer.MAX_VALUE;
      } else {
         var5 = this.mMoveProxy.getMoveTo(0);
         var7 = 0;
      }

      int var8 = -1;
      int var9;
      if(var3 == null) {
         var6 = Integer.MAX_VALUE;
         var9 = 0;
         var4 = Integer.MAX_VALUE;
      } else {
         var9 = var3.size();
         var4 = var3.getInt(0);
      }

      while(true) {
         while(true) {
            int var10 = var4;
            if(var4 < var5) {
               addChildAt(var1, this.resolveShadowNode(var2.getInt(var6)), var4, var8);
               ++var6;
               if(var6 == var9) {
                  var4 = Integer.MAX_VALUE;
               } else {
                  var4 = var3.getInt(var6);
               }

               var8 = var10;
            } else {
               if(var5 >= var4) {
                  return;
               }

               addChildAt(var1, this.mMoveProxy.getChildMoveTo(var7), var5, var8);
               ++var7;
               if(var7 == this.mMoveProxy.size()) {
                  var4 = Integer.MAX_VALUE;
               } else {
                  var4 = this.mMoveProxy.getMoveTo(var7);
               }

               var8 = var5;
               var5 = var4;
               var4 = var10;
            }
         }
      }
   }

   private static Map<String, ViewManager> buildViewManagerMap(List<ViewManager> var0) {
      HashMap var1 = new HashMap();
      Iterator var7 = var0.iterator();

      while(var7.hasNext()) {
         ViewManager var2 = (ViewManager)var7.next();
         var1.put(var2.getName(), var2);
      }

      Iterator var9 = flatManagerClassMap.entrySet().iterator();

      while(var9.hasNext()) {
         Entry var4 = (Entry)var9.next();
         String var8 = (String)var4.getKey();
         ViewManager var3 = (ViewManager)var1.get(var8);
         if(var3 != null) {
            Class var11 = (Class)var4.getValue();
            if(var3.getClass() != var11) {
               StringBuilder var10;
               try {
                  var1.put(var8, var11.newInstance());
               } catch (IllegalAccessException var5) {
                  var10 = new StringBuilder();
                  var10.append("Unable to access flat class for ");
                  var10.append(var8);
                  throw new RuntimeException(var10.toString(), var5);
               } catch (InstantiationException var6) {
                  var10 = new StringBuilder();
                  var10.append("Unable to instantiate flat class for ");
                  var10.append(var8);
                  throw new RuntimeException(var10.toString(), var6);
               }
            }
         }
      }

      return var1;
   }

   public static FlatUIImplementation createInstance(ReactApplicationContext var0, List<ViewManager> var1, EventDispatcher var2, boolean var3, int var4) {
      Map var5 = buildViewManagerMap(var1);
      RCTImageViewManager var8 = (RCTImageViewManager)var5.get("RCTImageView");
      if(var8 != null) {
         Object var6 = var8.getCallerContext();
         if(var6 != null) {
            RCTImageView.setCallerContext(var6);
         }
      }

      DraweeRequestHelper.setResources(var0.getResources());
      TypefaceCache.setAssetManager(var0.getAssets());
      ViewManagerRegistry var7 = new ViewManagerRegistry(var5);
      return new FlatUIImplementation(var0, var8, var7, new FlatUIViewOperationQueue(var0, new FlatNativeViewHierarchyManager(var7), var4), var2, var3);
   }

   private void dropNativeViews(ReactShadowNode var1, ReactShadowNode var2) {
      int var3;
      if(var1 instanceof FlatShadowNode) {
         FlatShadowNode var5 = (FlatShadowNode)var1;
         if(var5.mountsToView() && var5.isBackingViewCreated()) {
            byte var7 = -1;

            while(true) {
               var3 = var7;
               if(var2 == null) {
                  break;
               }

               if(var2 instanceof FlatShadowNode) {
                  FlatShadowNode var6 = (FlatShadowNode)var2;
                  if(var6.mountsToView() && var6.isBackingViewCreated() && var6.getParent() != null) {
                     var3 = var6.getReactTag();
                     break;
                  }
               }

               var2 = var2.getParent();
            }

            this.mStateBuilder.dropView(var5, var3);
            return;
         }
      }

      var3 = 0;

      for(int var4 = var1.getChildCount(); var3 != var4; ++var3) {
         this.dropNativeViews(var1.getChildAt(var3), var1);
      }

   }

   private void ensureMountsToViewAndBackingViewIsCreated(int var1) {
      FlatShadowNode var2 = (FlatShadowNode)this.resolveShadowNode(var1);
      if(!var2.isBackingViewCreated()) {
         var2.forceMountToView();
         this.mStateBuilder.ensureBackingViewIsCreated(var2);
      }
   }

   private void measureHelper(int var1, boolean var2, Callback var3) {
      FlatShadowNode var12 = (FlatShadowNode)this.resolveShadowNode(var1);
      FlatShadowNode var11 = var12;
      if(var12.mountsToView()) {
         this.mStateBuilder.ensureBackingViewIsCreated(var12);
         if(var2) {
            super.measureInWindow(var1, var3);
         } else {
            super.measure(var1, var3);
         }
      } else {
         while(var11 != null && var11.isVirtual()) {
            var11 = (FlatShadowNode)var11.getParent();
         }

         if(var11 != null) {
            float var8 = var11.getLayoutWidth();
            float var9 = var11.getLayoutHeight();
            boolean var10 = var11.mountsToView();
            float var4 = 0.0F;
            float var5;
            if(var10) {
               var5 = var11.getLayoutX();
            } else {
               var5 = 0.0F;
            }

            var12 = var11;
            float var6 = var5;
            if(var10) {
               var4 = var11.getLayoutY();
               var6 = var5;
               var12 = var11;
            }

            float var7;
            while(!var12.mountsToView()) {
               var7 = var4;
               var5 = var6;
               if(!var12.isVirtual()) {
                  var5 = var6 + var12.getLayoutX();
                  var7 = var4 + var12.getLayoutY();
               }

               var12 = (FlatShadowNode)Assertions.assumeNotNull((FlatShadowNode)var12.getParent());
               var4 = var7;
               var6 = var5;
            }

            var5 = var12.getLayoutWidth();
            var7 = var12.getLayoutHeight();
            this.mStateBuilder.getOperationsQueue().enqueueMeasureVirtualView(var12.getReactTag(), var6 / var5, var4 / var7, var8 / var5, var9 / var7, var2, var3);
         }
      }
   }

   private void moveChild(ReactShadowNode var1, int var2) {
      this.mMoveProxy.setChildMoveFrom(var2, var1);
   }

   private void removeChild(ReactShadowNode var1, ReactShadowNode var2) {
      this.dropNativeViews(var1, var2);
      this.removeShadowNode(var1);
   }

   private static ReactShadowNode removeChildAt(ReactShadowNode var0, int var1, int var2) {
      if(var1 >= var2) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Invariant failure, needs sorting! ");
         var3.append(var1);
         var3.append(" >= ");
         var3.append(var2);
         throw new RuntimeException(var3.toString());
      } else {
         return var0.removeChildAt(var1);
      }
   }

   private void removeChildren(ReactShadowNode var1, @Nullable ReadableArray var2, @Nullable ReadableArray var3, @Nullable ReadableArray var4) {
      this.mMoveProxy.setup(var2, var3);
      int var10 = this.mMoveProxy.size() - 1;
      int var6;
      if(var10 == -1) {
         var6 = -1;
      } else {
         var6 = this.mMoveProxy.getMoveFrom(var10);
      }

      int var7 = 0;
      int var5;
      if(var4 == null) {
         var5 = 0;
      } else {
         var5 = var4.size();
      }

      int[] var11 = new int[var5];
      if(var5 > 0) {
         Assertions.assertNotNull(var4);

         while(var7 < var5) {
            var11[var7] = var4.getInt(var7);
            ++var7;
         }
      }

      Arrays.sort(var11);
      int var8 = Integer.MAX_VALUE;
      if(var4 == null) {
         var7 = -1;
         var5 = -1;
      } else {
         var7 = var11.length - 1;
         var5 = var11[var7];
      }

      while(true) {
         while(true) {
            int var9 = var5;
            if(var6 > var5) {
               this.moveChild(removeChildAt(var1, var6, var8), var10);
               --var10;
               if(var10 == -1) {
                  var5 = -1;
               } else {
                  var5 = this.mMoveProxy.getMoveFrom(var10);
               }

               var8 = var6;
               var6 = var5;
               var5 = var9;
            } else {
               if(var5 <= var6) {
                  return;
               }

               this.removeChild(removeChildAt(var1, var5, var8), var1);
               --var7;
               if(var7 == -1) {
                  var5 = -1;
               } else {
                  var5 = var11[var7];
               }

               var8 = var9;
            }
         }
      }
   }

   public void addAnimation(int var1, int var2, Callback var3) {
      this.ensureMountsToViewAndBackingViewIsCreated(var1);
      super.addAnimation(var1, var2, var3);
   }

   protected void applyUpdatesRecursive(ReactShadowNode var1, float var2, float var3) {
      this.mStateBuilder.applyUpdates((FlatRootShadowNode)var1);
   }

   protected ReactShadowNode createRootShadowNode() {
      if(this.mRCTImageViewManager != null) {
         this.mReactContext.getNativeModule(FrescoModule.class);
         DraweeRequestHelper.setDraweeControllerBuilder(this.mRCTImageViewManager.getDraweeControllerBuilder());
         this.mRCTImageViewManager = null;
      }

      FlatRootShadowNode var1 = new FlatRootShadowNode();
      if(I18nUtil.getInstance().isRTL(this.mReactContext)) {
         var1.setLayoutDirection(YogaDirection.RTL);
      }

      return var1;
   }

   protected ReactShadowNode createShadowNode(String var1) {
      ReactShadowNode var2 = super.createShadowNode(var1);
      return (ReactShadowNode)(!(var2 instanceof FlatShadowNode)?(var2.isVirtual()?var2:new NativeViewWrapper(this.resolveViewManager(var1))):var2);
   }

   public void dispatchViewManagerCommand(int var1, int var2, ReadableArray var3) {
      this.ensureMountsToViewAndBackingViewIsCreated(var1);
      this.mStateBuilder.enqueueViewManagerCommand(var1, var2, var3);
   }

   public void findSubviewIn(int var1, float var2, float var3, Callback var4) {
      this.ensureMountsToViewAndBackingViewIsCreated(var1);
      super.findSubviewIn(var1, var2, var3, var4);
   }

   protected void handleCreateView(ReactShadowNode var1, int var2, @Nullable ReactStylesDiffMap var3) {
      if(var1 instanceof FlatShadowNode) {
         FlatShadowNode var4 = (FlatShadowNode)var1;
         if(var3 != null) {
            var4.handleUpdateProperties(var3);
         }

         if(var4.mountsToView()) {
            this.mStateBuilder.enqueueCreateOrUpdateView(var4, var3);
            return;
         }
      } else {
         super.handleCreateView(var1, var2, var3);
      }

   }

   protected void handleUpdateView(ReactShadowNode var1, String var2, ReactStylesDiffMap var3) {
      if(var1 instanceof FlatShadowNode) {
         FlatShadowNode var4 = (FlatShadowNode)var1;
         var4.handleUpdateProperties(var3);
         if(var4.mountsToView()) {
            this.mStateBuilder.enqueueCreateOrUpdateView(var4, var3);
            return;
         }
      } else {
         super.handleUpdateView(var1, var2, var3);
      }

   }

   public void manageChildren(int var1, @Nullable ReadableArray var2, @Nullable ReadableArray var3, @Nullable ReadableArray var4, @Nullable ReadableArray var5, @Nullable ReadableArray var6) {
      ReactShadowNode var7 = this.resolveShadowNode(var1);
      this.removeChildren(var7, var2, var3, var6);
      this.addChildren(var7, var4, var5);
   }

   public void measure(int var1, Callback var2) {
      this.measureHelper(var1, false, var2);
   }

   public void measureInWindow(int var1, Callback var2) {
      this.measureHelper(var1, true, var2);
   }

   public void removeRootView(int var1) {
      if(this.mMemoryImprovementEnabled) {
         this.removeRootShadowNode(var1);
      }

      this.mStateBuilder.removeRootView(var1);
   }

   public void sendAccessibilityEvent(int var1, int var2) {
      this.ensureMountsToViewAndBackingViewIsCreated(var1);
      super.sendAccessibilityEvent(var1, var2);
   }

   public void setChildren(int var1, ReadableArray var2) {
      ReactShadowNode var3 = this.resolveShadowNode(var1);

      for(var1 = 0; var1 < var2.size(); ++var1) {
         addChildAt(var3, this.resolveShadowNode(var2.getInt(var1)), var1, var1 - 1);
      }

   }

   public void setJSResponder(int var1, boolean var2) {
      ReactShadowNode var4;
      for(var4 = this.resolveShadowNode(var1); var4.isVirtual(); var4 = var4.getParent()) {
         ;
      }

      int var3;
      for(var3 = var4.getReactTag(); var4 instanceof FlatShadowNode && !((FlatShadowNode)var4).mountsToView(); var4 = var4.getParent()) {
         ;
      }

      FlatUIViewOperationQueue var5 = this.mStateBuilder.getOperationsQueue();
      if(var4 != null) {
         var3 = var4.getReactTag();
      }

      var5.enqueueSetJSResponder(var3, var1, var2);
   }

   public void showPopupMenu(int var1, ReadableArray var2, Callback var3, Callback var4) {
      this.ensureMountsToViewAndBackingViewIsCreated(var1);
      super.showPopupMenu(var1, var2, var3, var4);
   }

   protected void updateViewHierarchy() {
      super.updateViewHierarchy();
      this.mStateBuilder.afterUpdateViewHierarchy(this.mEventDispatcher);
   }
}
