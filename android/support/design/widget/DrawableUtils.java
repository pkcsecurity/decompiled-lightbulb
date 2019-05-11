package android.support.design.widget;

import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.support.annotation.RestrictTo;
import android.util.Log;
import java.lang.reflect.Method;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class DrawableUtils {

   private static final String LOG_TAG = "DrawableUtils";
   private static Method setConstantStateMethod;
   private static boolean setConstantStateMethodFetched;


   public static boolean setContainerConstantState(DrawableContainer var0, ConstantState var1) {
      return setContainerConstantStateV9(var0, var1);
   }

   private static boolean setContainerConstantStateV9(DrawableContainer var0, ConstantState var1) {
      if(!setConstantStateMethodFetched) {
         try {
            setConstantStateMethod = DrawableContainer.class.getDeclaredMethod("setConstantState", new Class[]{DrawableContainerState.class});
            setConstantStateMethod.setAccessible(true);
         } catch (NoSuchMethodException var3) {
            Log.e("DrawableUtils", "Could not fetch setConstantState(). Oh well.");
         }

         setConstantStateMethodFetched = true;
      }

      if(setConstantStateMethod != null) {
         try {
            setConstantStateMethod.invoke(var0, new Object[]{var1});
            return true;
         } catch (Exception var4) {
            Log.e("DrawableUtils", "Could not invoke setConstantState(). Oh well.");
         }
      }

      return false;
   }
}
