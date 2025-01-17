package com.google.android.gms.dynamic;

import android.os.IBinder;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.lang.reflect.Field;

@KeepForSdk
public final class ObjectWrapper<T extends Object> extends IObjectWrapper.Stub {

   private final T a;


   private ObjectWrapper(T var1) {
      this.a = var1;
   }

   @KeepForSdk
   public static <T extends Object> IObjectWrapper a(T var0) {
      return new ObjectWrapper(var0);
   }

   @KeepForSdk
   public static <T extends Object> T a(IObjectWrapper var0) {
      if(var0 instanceof ObjectWrapper) {
         return ((ObjectWrapper)var0).a;
      } else {
         IBinder var6 = var0.asBinder();
         Field[] var7 = var6.getClass().getDeclaredFields();
         int var4 = var7.length;
         int var1 = 0;
         Field var10 = null;

         int var2;
         int var3;
         for(var2 = 0; var1 < var4; var2 = var3) {
            Field var5 = var7[var1];
            var3 = var2;
            if(!var5.isSynthetic()) {
               var3 = var2 + 1;
               var10 = var5;
            }

            ++var1;
         }

         if(var2 == 1) {
            if(!var10.isAccessible()) {
               var10.setAccessible(true);

               try {
                  Object var12 = var10.get(var6);
                  return var12;
               } catch (NullPointerException var8) {
                  throw new IllegalArgumentException("Binder object is null.", var8);
               } catch (IllegalAccessException var9) {
                  throw new IllegalArgumentException("Could not access the field in remoteBinder.", var9);
               }
            } else {
               throw new IllegalArgumentException("IObjectWrapper declared field not private!");
            }
         } else {
            var1 = var7.length;
            StringBuilder var11 = new StringBuilder(64);
            var11.append("Unexpected number of IObjectWrapper declared fields: ");
            var11.append(var1);
            throw new IllegalArgumentException(var11.toString());
         }
      }
   }
}
