package android.arch.lifecycle;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import java.lang.reflect.InvocationTargetException;

public class ViewModelProvider {

   private final ViewModelProvider.Factory a;
   private final o b;


   public ViewModelProvider(@NonNull o var1, @NonNull ViewModelProvider.Factory var2) {
      this.a = var2;
      this.b = var1;
   }

   @MainThread
   @NonNull
   public <T extends m> T a(@NonNull Class<T> var1) {
      String var2 = var1.getCanonicalName();
      if(var2 == null) {
         throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("android.arch.lifecycle.ViewModelProvider.DefaultKey:");
         var3.append(var2);
         return this.a(var3.toString(), var1);
      }
   }

   @MainThread
   @NonNull
   public <T extends m> T a(@NonNull String var1, @NonNull Class<T> var2) {
      m var3 = this.b.a(var1);
      if(var2.isInstance(var3)) {
         return var3;
      } else {
         m var4 = this.a.create(var2);
         this.b.a(var1, var4);
         return var4;
      }
   }

   public static class b implements ViewModelProvider.Factory {

      @NonNull
      public <T extends m> T create(@NonNull Class<T> var1) {
         StringBuilder var3;
         try {
            m var2 = (m)var1.newInstance();
            return var2;
         } catch (InstantiationException var4) {
            var3 = new StringBuilder();
            var3.append("Cannot create an instance of ");
            var3.append(var1);
            throw new RuntimeException(var3.toString(), var4);
         } catch (IllegalAccessException var5) {
            var3 = new StringBuilder();
            var3.append("Cannot create an instance of ");
            var3.append(var1);
            throw new RuntimeException(var3.toString(), var5);
         }
      }
   }

   public static class a extends ViewModelProvider.b {

      private static ViewModelProvider.a a;
      private Application b;


      public a(@NonNull Application var1) {
         this.b = var1;
      }

      @NonNull
      public static ViewModelProvider.a a(@NonNull Application var0) {
         if(a == null) {
            a = new ViewModelProvider.a(var0);
         }

         return a;
      }

      @NonNull
      public <T extends m> T create(@NonNull Class<T> var1) {
         if(AndroidViewModel.class.isAssignableFrom(var1)) {
            StringBuilder var3;
            try {
               m var2 = (m)var1.getConstructor(new Class[]{Application.class}).newInstance(new Object[]{this.b});
               return var2;
            } catch (NoSuchMethodException var4) {
               var3 = new StringBuilder();
               var3.append("Cannot create an instance of ");
               var3.append(var1);
               throw new RuntimeException(var3.toString(), var4);
            } catch (IllegalAccessException var5) {
               var3 = new StringBuilder();
               var3.append("Cannot create an instance of ");
               var3.append(var1);
               throw new RuntimeException(var3.toString(), var5);
            } catch (InstantiationException var6) {
               var3 = new StringBuilder();
               var3.append("Cannot create an instance of ");
               var3.append(var1);
               throw new RuntimeException(var3.toString(), var6);
            } catch (InvocationTargetException var7) {
               var3 = new StringBuilder();
               var3.append("Cannot create an instance of ");
               var3.append(var1);
               throw new RuntimeException(var3.toString(), var7);
            }
         } else {
            return super.create(var1);
         }
      }
   }

   public interface Factory {

      @NonNull
      <T extends m> T create(@NonNull Class<T> var1);
   }
}
