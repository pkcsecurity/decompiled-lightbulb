package android.support.v4.graphics;

import android.graphics.Typeface;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.graphics.TypefaceCompatApi26Impl;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(28)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class TypefaceCompatApi28Impl extends TypefaceCompatApi26Impl {

   private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
   private static final String DEFAULT_FAMILY = "sans-serif";
   private static final int RESOLVE_BY_FONT_TABLE = -1;
   private static final String TAG = "TypefaceCompatApi28Impl";


   protected Typeface createFromFamiliesWithDefault(Object var1) {
      try {
         Object var2 = Array.newInstance(this.mFontFamily, 1);
         Array.set(var2, 0, var1);
         Typeface var4 = (Typeface)this.mCreateFromFamiliesWithDefault.invoke((Object)null, new Object[]{var2, "sans-serif", Integer.valueOf(-1), Integer.valueOf(-1)});
         return var4;
      } catch (InvocationTargetException var3) {
         throw new RuntimeException(var3);
      }
   }

   protected Method obtainCreateFromFamiliesWithDefaultMethod(Class var1) throws NoSuchMethodException {
      Method var2 = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", new Class[]{Array.newInstance(var1, 1).getClass(), String.class, Integer.TYPE, Integer.TYPE});
      var2.setAccessible(true);
      return var2;
   }
}
