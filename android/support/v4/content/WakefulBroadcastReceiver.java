package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager.WakeLock;
import android.util.SparseArray;

@Deprecated
public abstract class WakefulBroadcastReceiver extends BroadcastReceiver {

   private static final String EXTRA_WAKE_LOCK_ID = "android.support.content.wakelockid";
   private static int mNextId;
   private static final SparseArray<WakeLock> sActiveWakeLocks = new SparseArray();


   public static boolean completeWakefulIntent(Intent param0) {
      // $FF: Couldn't be decompiled
   }

   public static ComponentName startWakefulService(Context param0, Intent param1) {
      // $FF: Couldn't be decompiled
   }
}
