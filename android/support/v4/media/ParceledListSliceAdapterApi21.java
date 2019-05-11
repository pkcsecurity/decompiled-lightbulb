package android.support.v4.media;

import android.media.browse.MediaBrowser.MediaItem;
import android.support.annotation.RequiresApi;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RequiresApi(21)
class ParceledListSliceAdapterApi21 {

   private static Constructor sConstructor;


   static {
      try {
         sConstructor = Class.forName("android.content.pm.ParceledListSlice").getConstructor(new Class[]{List.class});
      } catch (NoSuchMethodException var1) {
         var1.printStackTrace();
      }
   }

   static Object newInstance(List<MediaItem> var0) {
      try {
         Object var2 = sConstructor.newInstance(new Object[]{var0});
         return var2;
      } catch (InvocationTargetException var1) {
         var1.printStackTrace();
         return null;
      }
   }
}
