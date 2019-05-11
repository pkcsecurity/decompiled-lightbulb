package android.support.transition;

import android.annotation.SuppressLint;
import android.support.annotation.RequiresApi;
import android.support.transition.ViewUtilsApi21;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(22)
class ViewUtilsApi22 extends ViewUtilsApi21 {

   private static final String TAG = "ViewUtilsApi22";
   private static Method sSetLeftTopRightBottomMethod;
   private static boolean sSetLeftTopRightBottomMethodFetched;


   @SuppressLint({"PrivateApi"})
   private void fetchSetLeftTopRightBottomMethod() {
      if(!sSetLeftTopRightBottomMethodFetched) {
         try {
            sSetLeftTopRightBottomMethod = View.class.getDeclaredMethod("setLeftTopRightBottom", new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE});
            sSetLeftTopRightBottomMethod.setAccessible(true);
         } catch (NoSuchMethodException var2) {
            Log.i("ViewUtilsApi22", "Failed to retrieve setLeftTopRightBottom method", var2);
         }

         sSetLeftTopRightBottomMethodFetched = true;
      }

   }

   public void setLeftTopRightBottom(View var1, int var2, int var3, int var4, int var5) {
      this.fetchSetLeftTopRightBottomMethod();
      if(sSetLeftTopRightBottomMethod != null) {
         try {
            sSetLeftTopRightBottomMethod.invoke(var1, new Object[]{Integer.valueOf(var2), Integer.valueOf(var3), Integer.valueOf(var4), Integer.valueOf(var5)});
         } catch (IllegalAccessException var6) {
            ;
         } catch (InvocationTargetException var7) {
            throw new RuntimeException(var7.getCause());
         }
      }
   }
}
