package com.facebook.drawee.controller;

import android.graphics.drawable.Animatable;
import android.util.Log;
import com.facebook.drawee.controller.ControllerListener;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class ForwardingControllerListener<INFO extends Object> implements ControllerListener<INFO> {

   private static final String TAG = "FdingControllerListener";
   private final List<ControllerListener<? super INFO>> mListeners = new ArrayList(2);


   public static <INFO extends Object> ForwardingControllerListener<INFO> create() {
      return new ForwardingControllerListener();
   }

   public static <INFO extends Object> ForwardingControllerListener<INFO> of(ControllerListener<? super INFO> var0) {
      ForwardingControllerListener var1 = create();
      var1.addListener(var0);
      return var1;
   }

   public static <INFO extends Object> ForwardingControllerListener<INFO> of(ControllerListener<? super INFO> var0, ControllerListener<? super INFO> var1) {
      ForwardingControllerListener var2 = create();
      var2.addListener(var0);
      var2.addListener(var1);
      return var2;
   }

   private void onException(String var1, Throwable var2) {
      synchronized(this){}

      try {
         Log.e("FdingControllerListener", var1, var2);
      } finally {
         ;
      }

   }

   public void addListener(ControllerListener<? super INFO> var1) {
      synchronized(this){}

      try {
         this.mListeners.add(var1);
      } finally {
         ;
      }

   }

   public void clearListeners() {
      synchronized(this){}

      try {
         this.mListeners.clear();
      } finally {
         ;
      }

   }

   public void onFailure(String param1, Throwable param2) {
      // $FF: Couldn't be decompiled
   }

   public void onFinalImageSet(String param1, @Nullable INFO param2, @Nullable Animatable param3) {
      // $FF: Couldn't be decompiled
   }

   public void onIntermediateImageFailed(String var1, Throwable var2) {
      int var4 = this.mListeners.size();

      for(int var3 = 0; var3 < var4; ++var3) {
         try {
            ((ControllerListener)this.mListeners.get(var3)).onIntermediateImageFailed(var1, var2);
         } catch (Exception var6) {
            this.onException("InternalListener exception in onIntermediateImageFailed", var6);
         }
      }

   }

   public void onIntermediateImageSet(String var1, @Nullable INFO var2) {
      int var4 = this.mListeners.size();

      for(int var3 = 0; var3 < var4; ++var3) {
         try {
            ((ControllerListener)this.mListeners.get(var3)).onIntermediateImageSet(var1, var2);
         } catch (Exception var6) {
            this.onException("InternalListener exception in onIntermediateImageSet", var6);
         }
      }

   }

   public void onRelease(String param1) {
      // $FF: Couldn't be decompiled
   }

   public void onSubmit(String param1, Object param2) {
      // $FF: Couldn't be decompiled
   }

   public void removeListener(ControllerListener<? super INFO> var1) {
      synchronized(this){}

      try {
         this.mListeners.remove(var1);
      } finally {
         ;
      }

   }
}
