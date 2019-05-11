package android.support.transition;

import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.transition.GhostViewImpl;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(21)
class GhostViewApi21 implements GhostViewImpl {

   private static final String TAG = "GhostViewApi21";
   private static Method sAddGhostMethod;
   private static boolean sAddGhostMethodFetched;
   private static Class<?> sGhostViewClass;
   private static boolean sGhostViewClassFetched;
   private static Method sRemoveGhostMethod;
   private static boolean sRemoveGhostMethodFetched;
   private final View mGhostView;


   private GhostViewApi21(@NonNull View var1) {
      this.mGhostView = var1;
   }

   static GhostViewImpl addGhost(View var0, ViewGroup var1, Matrix var2) {
      fetchAddGhostMethod();
      if(sAddGhostMethod != null) {
         try {
            GhostViewApi21 var5 = new GhostViewApi21((View)sAddGhostMethod.invoke((Object)null, new Object[]{var0, var1, var2}));
            return var5;
         } catch (IllegalAccessException var3) {
            return null;
         } catch (InvocationTargetException var4) {
            throw new RuntimeException(var4.getCause());
         }
      } else {
         return null;
      }
   }

   private static void fetchAddGhostMethod() {
      if(!sAddGhostMethodFetched) {
         try {
            fetchGhostViewClass();
            sAddGhostMethod = sGhostViewClass.getDeclaredMethod("addGhost", new Class[]{View.class, ViewGroup.class, Matrix.class});
            sAddGhostMethod.setAccessible(true);
         } catch (NoSuchMethodException var1) {
            Log.i("GhostViewApi21", "Failed to retrieve addGhost method", var1);
         }

         sAddGhostMethodFetched = true;
      }

   }

   private static void fetchGhostViewClass() {
      if(!sGhostViewClassFetched) {
         try {
            sGhostViewClass = Class.forName("android.view.GhostView");
         } catch (ClassNotFoundException var1) {
            Log.i("GhostViewApi21", "Failed to retrieve GhostView class", var1);
         }

         sGhostViewClassFetched = true;
      }

   }

   private static void fetchRemoveGhostMethod() {
      if(!sRemoveGhostMethodFetched) {
         try {
            fetchGhostViewClass();
            sRemoveGhostMethod = sGhostViewClass.getDeclaredMethod("removeGhost", new Class[]{View.class});
            sRemoveGhostMethod.setAccessible(true);
         } catch (NoSuchMethodException var1) {
            Log.i("GhostViewApi21", "Failed to retrieve removeGhost method", var1);
         }

         sRemoveGhostMethodFetched = true;
      }

   }

   static void removeGhost(View var0) {
      fetchRemoveGhostMethod();
      if(sRemoveGhostMethod != null) {
         try {
            sRemoveGhostMethod.invoke((Object)null, new Object[]{var0});
         } catch (IllegalAccessException var1) {
            ;
         } catch (InvocationTargetException var2) {
            throw new RuntimeException(var2.getCause());
         }
      }
   }

   public void reserveEndViewTransition(ViewGroup var1, View var2) {}

   public void setVisibility(int var1) {
      this.mGhostView.setVisibility(var1);
   }
}
