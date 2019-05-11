package com.facebook.react.shell;

import android.preference.PreferenceManager;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.react.LazyReactPackage;
import com.facebook.react.animated.NativeAnimatedModule;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.flat.FlatARTSurfaceViewManager;
import com.facebook.react.flat.RCTImageViewManager;
import com.facebook.react.flat.RCTModalHostManager;
import com.facebook.react.flat.RCTRawTextManager;
import com.facebook.react.flat.RCTTextInlineImageManager;
import com.facebook.react.flat.RCTTextInputManager;
import com.facebook.react.flat.RCTTextManager;
import com.facebook.react.flat.RCTViewManager;
import com.facebook.react.flat.RCTViewPagerManager;
import com.facebook.react.flat.RCTVirtualTextManager;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.modules.accessibilityinfo.AccessibilityInfoModule;
import com.facebook.react.modules.appstate.AppStateModule;
import com.facebook.react.modules.blob.BlobModule;
import com.facebook.react.modules.camera.CameraRollManager;
import com.facebook.react.modules.camera.ImageEditingManager;
import com.facebook.react.modules.camera.ImageStoreManager;
import com.facebook.react.modules.clipboard.ClipboardModule;
import com.facebook.react.modules.datepicker.DatePickerDialogModule;
import com.facebook.react.modules.dialog.DialogModule;
import com.facebook.react.modules.fresco.FrescoModule;
import com.facebook.react.modules.i18nmanager.I18nManagerModule;
import com.facebook.react.modules.image.ImageLoaderModule;
import com.facebook.react.modules.intent.IntentModule;
import com.facebook.react.modules.location.LocationModule;
import com.facebook.react.modules.netinfo.NetInfoModule;
import com.facebook.react.modules.network.NetworkingModule;
import com.facebook.react.modules.permissions.PermissionsModule;
import com.facebook.react.modules.share.ShareModule;
import com.facebook.react.modules.statusbar.StatusBarModule;
import com.facebook.react.modules.storage.AsyncStorageModule;
import com.facebook.react.modules.timepicker.TimePickerDialogModule;
import com.facebook.react.modules.toast.ToastModule;
import com.facebook.react.modules.vibration.VibrationModule;
import com.facebook.react.modules.websocket.WebSocketModule;
import com.facebook.react.shell.MainPackageConfig;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.views.art.ARTRenderableViewManager;
import com.facebook.react.views.art.ARTSurfaceViewManager;
import com.facebook.react.views.checkbox.ReactCheckBoxManager;
import com.facebook.react.views.drawer.ReactDrawerLayoutManager;
import com.facebook.react.views.image.ReactImageManager;
import com.facebook.react.views.modal.ReactModalHostManager;
import com.facebook.react.views.picker.ReactDialogPickerManager;
import com.facebook.react.views.picker.ReactDropdownPickerManager;
import com.facebook.react.views.progressbar.ReactProgressBarViewManager;
import com.facebook.react.views.scroll.ReactHorizontalScrollContainerViewManager;
import com.facebook.react.views.scroll.ReactHorizontalScrollViewManager;
import com.facebook.react.views.scroll.ReactScrollViewManager;
import com.facebook.react.views.slider.ReactSliderManager;
import com.facebook.react.views.swiperefresh.SwipeRefreshLayoutManager;
import com.facebook.react.views.switchview.ReactSwitchManager;
import com.facebook.react.views.text.ReactRawTextManager;
import com.facebook.react.views.text.ReactTextViewManager;
import com.facebook.react.views.text.ReactVirtualTextViewManager;
import com.facebook.react.views.text.frescosupport.FrescoBasedReactTextInlineImageViewManager;
import com.facebook.react.views.textinput.ReactTextInputManager;
import com.facebook.react.views.toolbar.ReactToolbarManager;
import com.facebook.react.views.view.ReactViewManager;
import com.facebook.react.views.viewpager.ReactViewPagerManager;
import com.facebook.react.views.webview.ReactWebViewManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Provider;

public class MainReactPackage extends LazyReactPackage {

   private MainPackageConfig mConfig;


   public MainReactPackage() {}

   public MainReactPackage(MainPackageConfig var1) {
      this.mConfig = var1;
   }

   public List<ViewManager> createViewManagers(ReactApplicationContext var1) {
      ArrayList var2 = new ArrayList();
      var2.add(ARTRenderableViewManager.createARTGroupViewManager());
      var2.add(ARTRenderableViewManager.createARTShapeViewManager());
      var2.add(ARTRenderableViewManager.createARTTextViewManager());
      var2.add(new ReactCheckBoxManager());
      var2.add(new ReactDialogPickerManager());
      var2.add(new ReactDrawerLayoutManager());
      var2.add(new ReactDropdownPickerManager());
      var2.add(new ReactHorizontalScrollViewManager());
      var2.add(new ReactHorizontalScrollContainerViewManager());
      var2.add(new ReactProgressBarViewManager());
      var2.add(new ReactScrollViewManager());
      var2.add(new ReactSliderManager());
      var2.add(new ReactSwitchManager());
      var2.add(new ReactToolbarManager());
      var2.add(new ReactWebViewManager());
      var2.add(new SwipeRefreshLayoutManager());
      if(PreferenceManager.getDefaultSharedPreferences(var1).getBoolean("flat_uiimplementation", false)) {
         var2.add(new FlatARTSurfaceViewManager());
         var2.add(new RCTTextInlineImageManager());
         var2.add(new RCTImageViewManager());
         var2.add(new RCTModalHostManager());
         var2.add(new RCTRawTextManager());
         var2.add(new RCTTextInputManager());
         var2.add(new RCTTextManager());
         var2.add(new RCTViewManager());
         var2.add(new RCTViewPagerManager());
         var2.add(new RCTVirtualTextManager());
         return var2;
      } else {
         var2.add(new ARTSurfaceViewManager());
         var2.add(new FrescoBasedReactTextInlineImageViewManager());
         var2.add(new ReactImageManager());
         var2.add(new ReactModalHostManager());
         var2.add(new ReactRawTextManager());
         var2.add(new ReactTextInputManager());
         var2.add(new ReactTextViewManager());
         var2.add(new ReactViewManager());
         var2.add(new ReactViewPagerManager());
         var2.add(new ReactVirtualTextViewManager());
         return var2;
      }
   }

   public List<ModuleSpec> getNativeModules(final ReactApplicationContext var1) {
      return Arrays.asList(new ModuleSpec[]{ModuleSpec.nativeModuleSpec(AccessibilityInfoModule.class, new Provider() {
         public NativeModule get() {
            return new AccessibilityInfoModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(AppStateModule.class, new Provider() {
         public NativeModule get() {
            return new AppStateModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(BlobModule.class, new Provider() {
         public NativeModule get() {
            return new BlobModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(AsyncStorageModule.class, new Provider() {
         public NativeModule get() {
            return new AsyncStorageModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(CameraRollManager.class, new Provider() {
         public NativeModule get() {
            return new CameraRollManager(var1);
         }
      }), ModuleSpec.nativeModuleSpec(ClipboardModule.class, new Provider() {
         public NativeModule get() {
            return new ClipboardModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(DatePickerDialogModule.class, new Provider() {
         public NativeModule get() {
            return new DatePickerDialogModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(DialogModule.class, new Provider() {
         public NativeModule get() {
            return new DialogModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(FrescoModule.class, new Provider() {
         public NativeModule get() {
            ReactApplicationContext var2 = var1;
            ImagePipelineConfig var1x;
            if(MainReactPackage.this.mConfig != null) {
               var1x = MainReactPackage.this.mConfig.getFrescoConfig();
            } else {
               var1x = null;
            }

            return new FrescoModule(var2, true, var1x);
         }
      }), ModuleSpec.nativeModuleSpec(I18nManagerModule.class, new Provider() {
         public NativeModule get() {
            return new I18nManagerModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(ImageEditingManager.class, new Provider() {
         public NativeModule get() {
            return new ImageEditingManager(var1);
         }
      }), ModuleSpec.nativeModuleSpec(ImageLoaderModule.class, new Provider() {
         public NativeModule get() {
            return new ImageLoaderModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(ImageStoreManager.class, new Provider() {
         public NativeModule get() {
            return new ImageStoreManager(var1);
         }
      }), ModuleSpec.nativeModuleSpec(IntentModule.class, new Provider() {
         public NativeModule get() {
            return new IntentModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(LocationModule.class, new Provider() {
         public NativeModule get() {
            return new LocationModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(NativeAnimatedModule.class, new Provider() {
         public NativeModule get() {
            return new NativeAnimatedModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(NetworkingModule.class, new Provider() {
         public NativeModule get() {
            return new NetworkingModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(NetInfoModule.class, new Provider() {
         public NativeModule get() {
            return new NetInfoModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(PermissionsModule.class, new Provider() {
         public NativeModule get() {
            return new PermissionsModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(ShareModule.class, new Provider() {
         public NativeModule get() {
            return new ShareModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(StatusBarModule.class, new Provider() {
         public NativeModule get() {
            return new StatusBarModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(TimePickerDialogModule.class, new Provider() {
         public NativeModule get() {
            return new TimePickerDialogModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(ToastModule.class, new Provider() {
         public NativeModule get() {
            return new ToastModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(VibrationModule.class, new Provider() {
         public NativeModule get() {
            return new VibrationModule(var1);
         }
      }), ModuleSpec.nativeModuleSpec(WebSocketModule.class, new Provider() {
         public NativeModule get() {
            return new WebSocketModule(var1);
         }
      })});
   }

   public ReactModuleInfoProvider getReactModuleInfoProvider() {
      return LazyReactPackage.getReactModuleInfoProviderViaReflection(this);
   }
}
