package com.facebook.imagepipeline.animated.factory;

import com.facebook.imagepipeline.animated.factory.AnimatedFactory;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.core.ExecutorSupplier;

public class AnimatedFactoryProvider {

   private static AnimatedFactory sImpl;
   private static boolean sImplLoaded;


   public static AnimatedFactory getAnimatedFactory(PlatformBitmapFactory var0, ExecutorSupplier var1) {
      if(!sImplLoaded) {
         try {
            sImpl = (AnimatedFactory)Class.forName("com.facebook.imagepipeline.animated.factory.AnimatedFactoryImplSupport").getConstructor(new Class[]{PlatformBitmapFactory.class, ExecutorSupplier.class}).newInstance(new Object[]{var0, var1});
         } catch (Throwable var4) {
            ;
         }

         if(sImpl != null) {
            sImplLoaded = true;
            return sImpl;
         }

         try {
            sImpl = (AnimatedFactory)Class.forName("com.facebook.imagepipeline.animated.factory.AnimatedFactoryImpl").getConstructor(new Class[]{PlatformBitmapFactory.class, ExecutorSupplier.class}).newInstance(new Object[]{var0, var1});
         } catch (Throwable var3) {
            ;
         }

         sImplLoaded = true;
      }

      return sImpl;
   }
}
