package com.facebook.litho.config;

import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import com.facebook.litho.boost.LithoAffinityBoosterFactory;
import com.facebook.litho.config.LayoutThreadPoolConfiguration;
import com.facebook.yoga.YogaLogger;

public class ComponentsConfiguration {

   public static final boolean ARE_TRANSITIONS_SUPPORTED;
   public static final boolean CAN_CHECK_GLOBAL_ANIMATOR_SETTINGS;
   public static final int DEFAULT_BACKGROUND_THREAD_PRIORITY = 5;
   public static final boolean IS_INTERNAL_BUILD = false;
   public static final boolean NEEDS_THEME_SYNCHRONIZATION;
   public static final boolean USE_INCREMENTAL_MOUNT_HELPER = true;
   public static YogaLogger YOGA_LOGGER;
   @Nullable
   public static LithoAffinityBoosterFactory affinityBoosterFactory;
   public static boolean areDefaultComparableDrawablesAlwaysEquivalent;
   public static int arrayBatchAllocationRuntimeSize;
   public static int arrayBatchAllocatorStartupSize;
   public static boolean assignTransitionKeysToAllOutputs;
   public static boolean asyncInitRange;
   public static boolean boostAffinityLayoutStateFuture;
   public static boolean debugHighlightInteractiveBounds;
   public static boolean debugHighlightMountBounds;
   public static int defaultChangeSetThreadPriority;
   public static boolean disablePools;
   public static boolean displayListWrappingEnabled;
   public static boolean doNotForceWrappingInViewForAnimation;
   public static boolean enableComparableDrawable;
   public static boolean enableLithoViewDebugOverlay;
   public static boolean enableOnErrorHandling;
   public static boolean enableSkipYogaPropExperiment;
   public static boolean enableThreadTracingStacktrace;
   public static boolean enableViewInfoDiffingForMountStateUpdates;
   public static boolean forceEnableTransitionsForInstrumentationTests;
   public static boolean hostHasOverlappingRendering;
   public static boolean incrementalMountWhenNotVisible;
   public static boolean inheritPriorityFromUiThread;
   public static boolean insertPostAsyncLayout;
   public static boolean isDebugModeEnabled;
   public static boolean isDrawableReferenceNonSynchronized;
   public static boolean isEndToEndTestRun;
   public static boolean lazilyInitializeLayoutStateOutputIdCalculator;
   public static boolean prewarmImageTexture;
   public static boolean setRootAsyncRecyclerCollectionComponent;
   public static boolean shouldUpdateMountSpecOnly;
   public static LayoutThreadPoolConfiguration threadPoolConfiguration;
   public static LayoutThreadPoolConfiguration threadPoolForBackgroundThreadsConfig;
   public static boolean unmountAllWhenComponentTreeSetToNull;
   public static boolean unmountThenReleaseDrawableCmp;
   public static boolean unsetThenReleaseDrawableBackground;
   public static boolean updateStateAsync;
   public static boolean useBatchArrayAllocator;
   public static boolean useDisplayListForAllDrawables;
   public static boolean useGlobalKeys;
   public static boolean useNewIsEquivalentTo;
   public static boolean usePlaceholderComponent;
   public static boolean useSharedLayoutStateFuture;
   public static boolean useStateHandlers;
   public static boolean variableArrayBatchAllocatorEnabled;


   static {
      int var0 = VERSION.SDK_INT;
      boolean var2 = false;
      boolean var1;
      if(var0 >= 14) {
         var1 = true;
      } else {
         var1 = false;
      }

      ARE_TRANSITIONS_SUPPORTED = var1;
      if(VERSION.SDK_INT >= 17) {
         var1 = true;
      } else {
         var1 = false;
      }

      CAN_CHECK_GLOBAL_ANIMATOR_SETTINGS = var1;
      if(VERSION.SDK_INT <= 22) {
         var1 = true;
      } else {
         var1 = false;
      }

      NEEDS_THEME_SYNCHRONIZATION = var1;
      var1 = var2;
      if(System.getProperty("IS_TESTING") != null) {
         var1 = true;
      }

      isEndToEndTestRun = var1;
   }

}
