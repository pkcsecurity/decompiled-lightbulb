package android.support.transition;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.Property;

class PathProperty<T extends Object> extends Property<T, Float> {

   private float mCurrentFraction;
   private final float mPathLength;
   private final PathMeasure mPathMeasure;
   private final PointF mPointF = new PointF();
   private final float[] mPosition = new float[2];
   private final Property<T, PointF> mProperty;


   PathProperty(Property<T, PointF> var1, Path var2) {
      super(Float.class, var1.getName());
      this.mProperty = var1;
      this.mPathMeasure = new PathMeasure(var2, false);
      this.mPathLength = this.mPathMeasure.getLength();
   }

   public Float get(T var1) {
      return Float.valueOf(this.mCurrentFraction);
   }

   public void set(T var1, Float var2) {
      this.mCurrentFraction = var2.floatValue();
      this.mPathMeasure.getPosTan(this.mPathLength * var2.floatValue(), this.mPosition, (float[])null);
      this.mPointF.x = this.mPosition[0];
      this.mPointF.y = this.mPosition[1];
      this.mProperty.set(var1, this.mPointF);
   }
}
