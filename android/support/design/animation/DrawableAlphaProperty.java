package android.support.design.animation;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Property;
import java.util.WeakHashMap;

public class DrawableAlphaProperty extends Property<Drawable, Integer> {

   public static final Property<Drawable, Integer> DRAWABLE_ALPHA_COMPAT = new DrawableAlphaProperty();
   private final WeakHashMap<Drawable, Integer> alphaCache = new WeakHashMap();


   private DrawableAlphaProperty() {
      super(Integer.class, "drawableAlphaCompat");
   }

   public Integer get(Drawable var1) {
      return VERSION.SDK_INT >= 19?Integer.valueOf(var1.getAlpha()):(this.alphaCache.containsKey(var1)?(Integer)this.alphaCache.get(var1):Integer.valueOf(255));
   }

   public void set(Drawable var1, Integer var2) {
      if(VERSION.SDK_INT < 19) {
         this.alphaCache.put(var1, var2);
      }

      var1.setAlpha(var2.intValue());
   }
}
