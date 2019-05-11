package com.google.android.gms.common.internal;

import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
public final class ServiceSpecificExtraArgs {


   @KeepForSdk
   public interface GamesExtraArgs {

      @KeepForSdk
      String DESIRED_LOCALE = "com.google.android.gms.games.key.desiredLocale";
      @KeepForSdk
      String GAME_PACKAGE_NAME = "com.google.android.gms.games.key.gamePackageName";
      @KeepForSdk
      String SIGNIN_OPTIONS = "com.google.android.gms.games.key.signInOptions";
      @KeepForSdk
      String WINDOW_TOKEN = "com.google.android.gms.games.key.popupWindowToken";

   }

   @KeepForSdk
   public interface PlusExtraArgs {

      @KeepForSdk
      String PLUS_AUTH_PACKAGE = "auth_package";

   }

   @KeepForSdk
   public interface CastExtraArgs {

      @KeepForSdk
      String LISTENER = "listener";

   }
}
