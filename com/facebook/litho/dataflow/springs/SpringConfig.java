package com.facebook.litho.dataflow.springs;


public class SpringConfig {

   public static final double DEFAULT_FRICTION = 22.0D;
   public static final double DEFAULT_TENSION = 230.2D;
   public static SpringConfig defaultConfig = new SpringConfig(230.2D, 22.0D);
   public static SpringConfig noOvershootConfig = new SpringConfig(338.8D, 34.0D);
   public double friction;
   public double tension;


   public SpringConfig(double var1, double var3) {
      this.tension = var1;
      this.friction = var3;
   }
}
