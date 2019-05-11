package com.facebook;

import android.content.Intent;
import com.facebook.internal.CallbackManagerImpl;

public interface CallbackManager {

   boolean onActivityResult(int var1, int var2, Intent var3);

   public static class Factory {

      public static CallbackManager create() {
         return new CallbackManagerImpl();
      }
   }
}
