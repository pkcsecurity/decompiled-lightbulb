package android.support.design.animation;

import android.support.design.R;
import android.util.Property;
import android.view.ViewGroup;

public class ChildrenAlphaProperty extends Property<ViewGroup, Float> {

   public static final Property<ViewGroup, Float> CHILDREN_ALPHA = new ChildrenAlphaProperty("childrenAlpha");


   private ChildrenAlphaProperty(String var1) {
      super(Float.class, var1);
   }

   public Float get(ViewGroup var1) {
      Float var2 = (Float)var1.getTag(R.id.mtrl_internal_children_alpha_tag);
      return var2 != null?var2:Float.valueOf(1.0F);
   }

   public void set(ViewGroup var1, Float var2) {
      float var3 = var2.floatValue();
      var1.setTag(R.id.mtrl_internal_children_alpha_tag, Float.valueOf(var3));
      int var5 = var1.getChildCount();

      for(int var4 = 0; var4 < var5; ++var4) {
         var1.getChildAt(var4).setAlpha(var3);
      }

   }
}
