package android.support.transition;

import android.animation.PropertyValuesHolder;
import android.animation.TypeConverter;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build.VERSION;
import android.support.transition.PathProperty;
import android.util.Property;

class PropertyValuesHolderUtils {

   static PropertyValuesHolder ofPointF(Property<?, PointF> var0, Path var1) {
      return VERSION.SDK_INT >= 21?PropertyValuesHolder.ofObject(var0, (TypeConverter)null, var1):PropertyValuesHolder.ofFloat(new PathProperty(var0, var1), new float[]{0.0F, 1.0F});
   }
}
