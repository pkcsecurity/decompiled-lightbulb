package android.support.v4.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.CoreComponentFactory;

@RequiresApi(28)
public class AppComponentFactory extends android.app.AppComponentFactory {

   public final Activity instantiateActivity(ClassLoader var1, String var2, Intent var3) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
      return (Activity)CoreComponentFactory.checkCompatWrapper(this.instantiateActivityCompat(var1, var2, var3));
   }

   @NonNull
   public Activity instantiateActivityCompat(@NonNull ClassLoader var1, @NonNull String var2, @Nullable Intent var3) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
      try {
         Activity var5 = (Activity)var1.loadClass(var2).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
         return var5;
      } catch (NoSuchMethodException var4) {
         throw new RuntimeException("Couldn\'t call constructor", var4);
      }
   }

   public final Application instantiateApplication(ClassLoader var1, String var2) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
      return (Application)CoreComponentFactory.checkCompatWrapper(this.instantiateApplicationCompat(var1, var2));
   }

   @NonNull
   public Application instantiateApplicationCompat(@NonNull ClassLoader var1, @NonNull String var2) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
      try {
         Application var4 = (Application)var1.loadClass(var2).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
         return var4;
      } catch (NoSuchMethodException var3) {
         throw new RuntimeException("Couldn\'t call constructor", var3);
      }
   }

   public final ContentProvider instantiateProvider(ClassLoader var1, String var2) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
      return (ContentProvider)CoreComponentFactory.checkCompatWrapper(this.instantiateProviderCompat(var1, var2));
   }

   @NonNull
   public ContentProvider instantiateProviderCompat(@NonNull ClassLoader var1, @NonNull String var2) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
      try {
         ContentProvider var4 = (ContentProvider)var1.loadClass(var2).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
         return var4;
      } catch (NoSuchMethodException var3) {
         throw new RuntimeException("Couldn\'t call constructor", var3);
      }
   }

   public final BroadcastReceiver instantiateReceiver(ClassLoader var1, String var2, Intent var3) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
      return (BroadcastReceiver)CoreComponentFactory.checkCompatWrapper(this.instantiateReceiverCompat(var1, var2, var3));
   }

   @NonNull
   public BroadcastReceiver instantiateReceiverCompat(@NonNull ClassLoader var1, @NonNull String var2, @Nullable Intent var3) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
      try {
         BroadcastReceiver var5 = (BroadcastReceiver)var1.loadClass(var2).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
         return var5;
      } catch (NoSuchMethodException var4) {
         throw new RuntimeException("Couldn\'t call constructor", var4);
      }
   }

   public final Service instantiateService(ClassLoader var1, String var2, Intent var3) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
      return (Service)CoreComponentFactory.checkCompatWrapper(this.instantiateServiceCompat(var1, var2, var3));
   }

   @NonNull
   public Service instantiateServiceCompat(@NonNull ClassLoader var1, @NonNull String var2, @Nullable Intent var3) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
      try {
         Service var5 = (Service)var1.loadClass(var2).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
         return var5;
      } catch (NoSuchMethodException var4) {
         throw new RuntimeException("Couldn\'t call constructor", var4);
      }
   }
}
