package android.support.v4.media;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.media.MediaSessionManager;
import android.support.v4.media.MediaSessionManagerImplBase;

@RequiresApi(21)
class MediaSessionManagerImplApi21 extends MediaSessionManagerImplBase {

   MediaSessionManagerImplApi21(Context var1) {
      super(var1);
      this.mContext = var1;
   }

   private boolean hasMediaControlPermission(@NonNull MediaSessionManager.RemoteUserInfoImpl var1) {
      return this.getContext().checkPermission("android.permission.MEDIA_CONTENT_CONTROL", var1.getPid(), var1.getUid()) == 0;
   }

   public boolean isTrustedForMediaControl(@NonNull MediaSessionManager.RemoteUserInfoImpl var1) {
      return this.hasMediaControlPermission(var1) || super.isTrustedForMediaControl(var1);
   }
}
