package android.support.customtabs;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.customtabs.CustomTabsSessionToken;
import android.support.customtabs.ICustomTabsCallback;
import android.support.customtabs.ICustomTabsService;
import android.widget.RemoteViews;
import java.util.List;

public final class CustomTabsSession {

   private static final String TAG = "CustomTabsSession";
   private final ICustomTabsCallback mCallback;
   private final ComponentName mComponentName;
   private final Object mLock = new Object();
   private final ICustomTabsService mService;


   CustomTabsSession(ICustomTabsService var1, ICustomTabsCallback var2, ComponentName var3) {
      this.mService = var1;
      this.mCallback = var2;
      this.mComponentName = var3;
   }

   @NonNull
   @VisibleForTesting
   public static CustomTabsSession createMockSessionForTesting(@NonNull ComponentName var0) {
      return new CustomTabsSession((ICustomTabsService)null, new CustomTabsSessionToken.MockCallback(), var0);
   }

   IBinder getBinder() {
      return this.mCallback.asBinder();
   }

   ComponentName getComponentName() {
      return this.mComponentName;
   }

   public boolean mayLaunchUrl(Uri var1, Bundle var2, List<Bundle> var3) {
      try {
         boolean var4 = this.mService.mayLaunchUrl(this.mCallback, var1, var2, var3);
         return var4;
      } catch (RemoteException var5) {
         return false;
      }
   }

   public int postMessage(String param1, Bundle param2) {
      // $FF: Couldn't be decompiled
   }

   public boolean requestPostMessageChannel(Uri var1) {
      try {
         boolean var2 = this.mService.requestPostMessageChannel(this.mCallback, var1);
         return var2;
      } catch (RemoteException var3) {
         return false;
      }
   }

   public boolean setActionButton(@NonNull Bitmap var1, @NonNull String var2) {
      Bundle var4 = new Bundle();
      var4.putParcelable("android.support.customtabs.customaction.ICON", var1);
      var4.putString("android.support.customtabs.customaction.DESCRIPTION", var2);
      Bundle var6 = new Bundle();
      var6.putBundle("android.support.customtabs.extra.ACTION_BUTTON_BUNDLE", var4);

      try {
         boolean var3 = this.mService.updateVisuals(this.mCallback, var6);
         return var3;
      } catch (RemoteException var5) {
         return false;
      }
   }

   public boolean setSecondaryToolbarViews(@Nullable RemoteViews var1, @Nullable int[] var2, @Nullable PendingIntent var3) {
      Bundle var5 = new Bundle();
      var5.putParcelable("android.support.customtabs.extra.EXTRA_REMOTEVIEWS", var1);
      var5.putIntArray("android.support.customtabs.extra.EXTRA_REMOTEVIEWS_VIEW_IDS", var2);
      var5.putParcelable("android.support.customtabs.extra.EXTRA_REMOTEVIEWS_PENDINGINTENT", var3);

      try {
         boolean var4 = this.mService.updateVisuals(this.mCallback, var5);
         return var4;
      } catch (RemoteException var6) {
         return false;
      }
   }

   @Deprecated
   public boolean setToolbarItem(int var1, @NonNull Bitmap var2, @NonNull String var3) {
      Bundle var5 = new Bundle();
      var5.putInt("android.support.customtabs.customaction.ID", var1);
      var5.putParcelable("android.support.customtabs.customaction.ICON", var2);
      var5.putString("android.support.customtabs.customaction.DESCRIPTION", var3);
      Bundle var7 = new Bundle();
      var7.putBundle("android.support.customtabs.extra.ACTION_BUTTON_BUNDLE", var5);

      try {
         boolean var4 = this.mService.updateVisuals(this.mCallback, var7);
         return var4;
      } catch (RemoteException var6) {
         return false;
      }
   }

   public boolean validateRelationship(int var1, @NonNull Uri var2, @Nullable Bundle var3) {
      if(var1 >= 1) {
         if(var1 > 2) {
            return false;
         } else {
            try {
               boolean var4 = this.mService.validateRelationship(this.mCallback, var1, var2, var3);
               return var4;
            } catch (RemoteException var5) {
               return false;
            }
         }
      } else {
         return false;
      }
   }
}
