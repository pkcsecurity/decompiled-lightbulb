package android.support.transition;

import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.transition.ViewUtilsApi19;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(21)
class ViewUtilsApi21 extends ViewUtilsApi19 {

   private static final String TAG = "ViewUtilsApi21";
   private static Method sSetAnimationMatrixMethod;
   private static boolean sSetAnimationMatrixMethodFetched;
   private static Method sTransformMatrixToGlobalMethod;
   private static boolean sTransformMatrixToGlobalMethodFetched;
   private static Method sTransformMatrixToLocalMethod;
   private static boolean sTransformMatrixToLocalMethodFetched;


   private void fetchSetAnimationMatrix() {
      if(!sSetAnimationMatrixMethodFetched) {
         try {
            sSetAnimationMatrixMethod = View.class.getDeclaredMethod("setAnimationMatrix", new Class[]{Matrix.class});
            sSetAnimationMatrixMethod.setAccessible(true);
         } catch (NoSuchMethodException var2) {
            Log.i("ViewUtilsApi21", "Failed to retrieve setAnimationMatrix method", var2);
         }

         sSetAnimationMatrixMethodFetched = true;
      }

   }

   private void fetchTransformMatrixToGlobalMethod() {
      if(!sTransformMatrixToGlobalMethodFetched) {
         try {
            sTransformMatrixToGlobalMethod = View.class.getDeclaredMethod("transformMatrixToGlobal", new Class[]{Matrix.class});
            sTransformMatrixToGlobalMethod.setAccessible(true);
         } catch (NoSuchMethodException var2) {
            Log.i("ViewUtilsApi21", "Failed to retrieve transformMatrixToGlobal method", var2);
         }

         sTransformMatrixToGlobalMethodFetched = true;
      }

   }

   private void fetchTransformMatrixToLocalMethod() {
      if(!sTransformMatrixToLocalMethodFetched) {
         try {
            sTransformMatrixToLocalMethod = View.class.getDeclaredMethod("transformMatrixToLocal", new Class[]{Matrix.class});
            sTransformMatrixToLocalMethod.setAccessible(true);
         } catch (NoSuchMethodException var2) {
            Log.i("ViewUtilsApi21", "Failed to retrieve transformMatrixToLocal method", var2);
         }

         sTransformMatrixToLocalMethodFetched = true;
      }

   }

   public void setAnimationMatrix(@NonNull View var1, Matrix var2) {
      this.fetchSetAnimationMatrix();
      if(sSetAnimationMatrixMethod != null) {
         try {
            sSetAnimationMatrixMethod.invoke(var1, new Object[]{var2});
         } catch (InvocationTargetException var3) {
            ;
         } catch (IllegalAccessException var4) {
            throw new RuntimeException(var4.getCause());
         }
      }
   }

   public void transformMatrixToGlobal(@NonNull View var1, @NonNull Matrix var2) {
      this.fetchTransformMatrixToGlobalMethod();
      if(sTransformMatrixToGlobalMethod != null) {
         try {
            sTransformMatrixToGlobalMethod.invoke(var1, new Object[]{var2});
         } catch (IllegalAccessException var3) {
            ;
         } catch (InvocationTargetException var4) {
            throw new RuntimeException(var4.getCause());
         }
      }
   }

   public void transformMatrixToLocal(@NonNull View var1, @NonNull Matrix var2) {
      this.fetchTransformMatrixToLocalMethod();
      if(sTransformMatrixToLocalMethod != null) {
         try {
            sTransformMatrixToLocalMethod.invoke(var1, new Object[]{var2});
         } catch (IllegalAccessException var3) {
            ;
         } catch (InvocationTargetException var4) {
            throw new RuntimeException(var4.getCause());
         }
      }
   }
}
