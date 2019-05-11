package android.support.customtabs;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.BundleCompat;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;
import java.util.ArrayList;

public final class CustomTabsIntent {

   public static final String EXTRA_ACTION_BUTTON_BUNDLE = "android.support.customtabs.extra.ACTION_BUTTON_BUNDLE";
   public static final String EXTRA_CLOSE_BUTTON_ICON = "android.support.customtabs.extra.CLOSE_BUTTON_ICON";
   public static final String EXTRA_DEFAULT_SHARE_MENU_ITEM = "android.support.customtabs.extra.SHARE_MENU_ITEM";
   public static final String EXTRA_ENABLE_INSTANT_APPS = "android.support.customtabs.extra.EXTRA_ENABLE_INSTANT_APPS";
   public static final String EXTRA_ENABLE_URLBAR_HIDING = "android.support.customtabs.extra.ENABLE_URLBAR_HIDING";
   public static final String EXTRA_EXIT_ANIMATION_BUNDLE = "android.support.customtabs.extra.EXIT_ANIMATION_BUNDLE";
   public static final String EXTRA_MENU_ITEMS = "android.support.customtabs.extra.MENU_ITEMS";
   public static final String EXTRA_REMOTEVIEWS = "android.support.customtabs.extra.EXTRA_REMOTEVIEWS";
   public static final String EXTRA_REMOTEVIEWS_CLICKED_ID = "android.support.customtabs.extra.EXTRA_REMOTEVIEWS_CLICKED_ID";
   public static final String EXTRA_REMOTEVIEWS_PENDINGINTENT = "android.support.customtabs.extra.EXTRA_REMOTEVIEWS_PENDINGINTENT";
   public static final String EXTRA_REMOTEVIEWS_VIEW_IDS = "android.support.customtabs.extra.EXTRA_REMOTEVIEWS_VIEW_IDS";
   public static final String EXTRA_SECONDARY_TOOLBAR_COLOR = "android.support.customtabs.extra.SECONDARY_TOOLBAR_COLOR";
   public static final String EXTRA_SESSION = "android.support.customtabs.extra.SESSION";
   public static final String EXTRA_TINT_ACTION_BUTTON = "android.support.customtabs.extra.TINT_ACTION_BUTTON";
   public static final String EXTRA_TITLE_VISIBILITY_STATE = "android.support.customtabs.extra.TITLE_VISIBILITY";
   public static final String EXTRA_TOOLBAR_COLOR = "android.support.customtabs.extra.TOOLBAR_COLOR";
   public static final String EXTRA_TOOLBAR_ITEMS = "android.support.customtabs.extra.TOOLBAR_ITEMS";
   private static final String EXTRA_USER_OPT_OUT_FROM_CUSTOM_TABS = "android.support.customtabs.extra.user_opt_out";
   public static final String KEY_DESCRIPTION = "android.support.customtabs.customaction.DESCRIPTION";
   public static final String KEY_ICON = "android.support.customtabs.customaction.ICON";
   public static final String KEY_ID = "android.support.customtabs.customaction.ID";
   public static final String KEY_MENU_ITEM_TITLE = "android.support.customtabs.customaction.MENU_ITEM_TITLE";
   public static final String KEY_PENDING_INTENT = "android.support.customtabs.customaction.PENDING_INTENT";
   private static final int MAX_TOOLBAR_ITEMS = 5;
   public static final int NO_TITLE = 0;
   public static final int SHOW_PAGE_TITLE = 1;
   public static final int TOOLBAR_ACTION_BUTTON_ID = 0;
   @NonNull
   public final Intent intent;
   @Nullable
   public final Bundle startAnimationBundle;


   private CustomTabsIntent(Intent var1, Bundle var2) {
      this.intent = var1;
      this.startAnimationBundle = var2;
   }

   // $FF: synthetic method
   CustomTabsIntent(Intent var1, Bundle var2, Object var3) {
      this(var1, var2);
   }

   public static int getMaxToolbarItems() {
      return 5;
   }

   public static Intent setAlwaysUseBrowserUI(Intent var0) {
      Intent var1 = var0;
      if(var0 == null) {
         var1 = new Intent("android.intent.action.VIEW");
      }

      var1.addFlags(268435456);
      var1.putExtra("android.support.customtabs.extra.user_opt_out", true);
      return var1;
   }

   public static boolean shouldAlwaysUseBrowserUI(Intent var0) {
      boolean var2 = false;
      boolean var1 = var2;
      if(var0.getBooleanExtra("android.support.customtabs.extra.user_opt_out", false)) {
         var1 = var2;
         if((var0.getFlags() & 268435456) != 0) {
            var1 = true;
         }
      }

      return var1;
   }

   public void launchUrl(Context var1, Uri var2) {
      this.intent.setData(var2);
      ContextCompat.startActivity(var1, this.intent, this.startAnimationBundle);
   }

   public static final class Builder {

      private ArrayList<Bundle> mActionButtons;
      private boolean mInstantAppsEnabled;
      private final Intent mIntent;
      private ArrayList<Bundle> mMenuItems;
      private Bundle mStartAnimationBundle;


      public Builder() {
         this((CustomTabsSession)null);
      }

      public Builder(@Nullable CustomTabsSession var1) {
         this.mIntent = new Intent("android.intent.action.VIEW");
         Object var2 = null;
         this.mMenuItems = null;
         this.mStartAnimationBundle = null;
         this.mActionButtons = null;
         this.mInstantAppsEnabled = true;
         if(var1 != null) {
            this.mIntent.setPackage(var1.getComponentName().getPackageName());
         }

         Bundle var3 = new Bundle();
         IBinder var4;
         if(var1 == null) {
            var4 = (IBinder)var2;
         } else {
            var4 = var1.getBinder();
         }

         BundleCompat.putBinder(var3, "android.support.customtabs.extra.SESSION", var4);
         this.mIntent.putExtras(var3);
      }

      public CustomTabsIntent.Builder addDefaultShareMenuItem() {
         this.mIntent.putExtra("android.support.customtabs.extra.SHARE_MENU_ITEM", true);
         return this;
      }

      public CustomTabsIntent.Builder addMenuItem(@NonNull String var1, @NonNull PendingIntent var2) {
         if(this.mMenuItems == null) {
            this.mMenuItems = new ArrayList();
         }

         Bundle var3 = new Bundle();
         var3.putString("android.support.customtabs.customaction.MENU_ITEM_TITLE", var1);
         var3.putParcelable("android.support.customtabs.customaction.PENDING_INTENT", var2);
         this.mMenuItems.add(var3);
         return this;
      }

      @Deprecated
      public CustomTabsIntent.Builder addToolbarItem(int var1, @NonNull Bitmap var2, @NonNull String var3, PendingIntent var4) throws IllegalStateException {
         if(this.mActionButtons == null) {
            this.mActionButtons = new ArrayList();
         }

         if(this.mActionButtons.size() >= 5) {
            throw new IllegalStateException("Exceeded maximum toolbar item count of 5");
         } else {
            Bundle var5 = new Bundle();
            var5.putInt("android.support.customtabs.customaction.ID", var1);
            var5.putParcelable("android.support.customtabs.customaction.ICON", var2);
            var5.putString("android.support.customtabs.customaction.DESCRIPTION", var3);
            var5.putParcelable("android.support.customtabs.customaction.PENDING_INTENT", var4);
            this.mActionButtons.add(var5);
            return this;
         }
      }

      public CustomTabsIntent build() {
         if(this.mMenuItems != null) {
            this.mIntent.putParcelableArrayListExtra("android.support.customtabs.extra.MENU_ITEMS", this.mMenuItems);
         }

         if(this.mActionButtons != null) {
            this.mIntent.putParcelableArrayListExtra("android.support.customtabs.extra.TOOLBAR_ITEMS", this.mActionButtons);
         }

         this.mIntent.putExtra("android.support.customtabs.extra.EXTRA_ENABLE_INSTANT_APPS", this.mInstantAppsEnabled);
         return new CustomTabsIntent(this.mIntent, this.mStartAnimationBundle, null);
      }

      public CustomTabsIntent.Builder enableUrlBarHiding() {
         this.mIntent.putExtra("android.support.customtabs.extra.ENABLE_URLBAR_HIDING", true);
         return this;
      }

      public CustomTabsIntent.Builder setActionButton(@NonNull Bitmap var1, @NonNull String var2, @NonNull PendingIntent var3) {
         return this.setActionButton(var1, var2, var3, false);
      }

      public CustomTabsIntent.Builder setActionButton(@NonNull Bitmap var1, @NonNull String var2, @NonNull PendingIntent var3, boolean var4) {
         Bundle var5 = new Bundle();
         var5.putInt("android.support.customtabs.customaction.ID", 0);
         var5.putParcelable("android.support.customtabs.customaction.ICON", var1);
         var5.putString("android.support.customtabs.customaction.DESCRIPTION", var2);
         var5.putParcelable("android.support.customtabs.customaction.PENDING_INTENT", var3);
         this.mIntent.putExtra("android.support.customtabs.extra.ACTION_BUTTON_BUNDLE", var5);
         this.mIntent.putExtra("android.support.customtabs.extra.TINT_ACTION_BUTTON", var4);
         return this;
      }

      public CustomTabsIntent.Builder setCloseButtonIcon(@NonNull Bitmap var1) {
         this.mIntent.putExtra("android.support.customtabs.extra.CLOSE_BUTTON_ICON", var1);
         return this;
      }

      public CustomTabsIntent.Builder setExitAnimations(@NonNull Context var1, @AnimRes int var2, @AnimRes int var3) {
         Bundle var4 = ActivityOptionsCompat.makeCustomAnimation(var1, var2, var3).toBundle();
         this.mIntent.putExtra("android.support.customtabs.extra.EXIT_ANIMATION_BUNDLE", var4);
         return this;
      }

      public CustomTabsIntent.Builder setInstantAppsEnabled(boolean var1) {
         this.mInstantAppsEnabled = var1;
         return this;
      }

      public CustomTabsIntent.Builder setSecondaryToolbarColor(@ColorInt int var1) {
         this.mIntent.putExtra("android.support.customtabs.extra.SECONDARY_TOOLBAR_COLOR", var1);
         return this;
      }

      public CustomTabsIntent.Builder setSecondaryToolbarViews(@NonNull RemoteViews var1, @Nullable int[] var2, @Nullable PendingIntent var3) {
         this.mIntent.putExtra("android.support.customtabs.extra.EXTRA_REMOTEVIEWS", var1);
         this.mIntent.putExtra("android.support.customtabs.extra.EXTRA_REMOTEVIEWS_VIEW_IDS", var2);
         this.mIntent.putExtra("android.support.customtabs.extra.EXTRA_REMOTEVIEWS_PENDINGINTENT", var3);
         return this;
      }

      public CustomTabsIntent.Builder setShowTitle(boolean var1) {
         throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\n");
      }

      public CustomTabsIntent.Builder setStartAnimations(@NonNull Context var1, @AnimRes int var2, @AnimRes int var3) {
         this.mStartAnimationBundle = ActivityOptionsCompat.makeCustomAnimation(var1, var2, var3).toBundle();
         return this;
      }

      public CustomTabsIntent.Builder setToolbarColor(@ColorInt int var1) {
         this.mIntent.putExtra("android.support.customtabs.extra.TOOLBAR_COLOR", var1);
         return this;
      }
   }
}
