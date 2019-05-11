package com.github.mikephil.charting.animation;

import com.github.mikephil.charting.animation.EasingFunction;

public class Easing {

   public static EasingFunction getEasingFunctionFromOption(Easing.EasingOption var0) {
      switch(null.$SwitchMap$com$github$mikephil$charting$animation$Easing$EasingOption[var0.ordinal()]) {
      case 2:
         return Easing.EasingFunctions.EaseInQuad;
      case 3:
         return Easing.EasingFunctions.EaseOutQuad;
      case 4:
         return Easing.EasingFunctions.EaseInOutQuad;
      case 5:
         return Easing.EasingFunctions.EaseInCubic;
      case 6:
         return Easing.EasingFunctions.EaseOutCubic;
      case 7:
         return Easing.EasingFunctions.EaseInOutCubic;
      case 8:
         return Easing.EasingFunctions.EaseInQuart;
      case 9:
         return Easing.EasingFunctions.EaseOutQuart;
      case 10:
         return Easing.EasingFunctions.EaseInOutQuart;
      case 11:
         return Easing.EasingFunctions.EaseInSine;
      case 12:
         return Easing.EasingFunctions.EaseOutSine;
      case 13:
         return Easing.EasingFunctions.EaseInOutSine;
      case 14:
         return Easing.EasingFunctions.EaseInExpo;
      case 15:
         return Easing.EasingFunctions.EaseOutExpo;
      case 16:
         return Easing.EasingFunctions.EaseInOutExpo;
      case 17:
         return Easing.EasingFunctions.EaseInCirc;
      case 18:
         return Easing.EasingFunctions.EaseOutCirc;
      case 19:
         return Easing.EasingFunctions.EaseInOutCirc;
      case 20:
         return Easing.EasingFunctions.EaseInElastic;
      case 21:
         return Easing.EasingFunctions.EaseOutElastic;
      case 22:
         return Easing.EasingFunctions.EaseInOutElastic;
      case 23:
         return Easing.EasingFunctions.EaseInBack;
      case 24:
         return Easing.EasingFunctions.EaseOutBack;
      case 25:
         return Easing.EasingFunctions.EaseInOutBack;
      case 26:
         return Easing.EasingFunctions.EaseInBounce;
      case 27:
         return Easing.EasingFunctions.EaseOutBounce;
      case 28:
         return Easing.EasingFunctions.EaseInOutBounce;
      default:
         return Easing.EasingFunctions.Linear;
      }
   }

   static class EasingFunctions {

      public static final EasingFunction EaseInBack = new EasingFunction() {
         public float getInterpolation(float var1) {
            return var1 * var1 * (var1 * 2.70158F - 1.70158F);
         }
      };
      public static final EasingFunction EaseInBounce = new EasingFunction() {
         public float getInterpolation(float var1) {
            return 1.0F - Easing.EasingFunctions.EaseOutBounce.getInterpolation(1.0F - var1);
         }
      };
      public static final EasingFunction EaseInCirc = new EasingFunction() {
         public float getInterpolation(float var1) {
            return -((float)Math.sqrt((double)(1.0F - var1 * var1)) - 1.0F);
         }
      };
      public static final EasingFunction EaseInCubic = new EasingFunction() {
         public float getInterpolation(float var1) {
            return var1 * var1 * var1;
         }
      };
      public static final EasingFunction EaseInElastic = new EasingFunction() {
         public float getInterpolation(float var1) {
            if(var1 == 0.0F) {
               return 0.0F;
            } else if(var1 == 1.0F) {
               return 1.0F;
            } else {
               float var2 = (float)Math.asin(1.0D);
               --var1;
               return -((float)Math.pow(2.0D, (double)(10.0F * var1)) * (float)Math.sin((double)(var1 - 0.047746483F * var2) * 6.283185307179586D / (double)0.3F));
            }
         }
      };
      public static final EasingFunction EaseInExpo = new EasingFunction() {
         public float getInterpolation(float var1) {
            return var1 == 0.0F?0.0F:(float)Math.pow(2.0D, (double)((var1 - 1.0F) * 10.0F));
         }
      };
      public static final EasingFunction EaseInOutBack = new EasingFunction() {
         public float getInterpolation(float var1) {
            var1 /= 0.5F;
            if(var1 < 1.0F) {
               return var1 * var1 * (3.5949094F * var1 - 2.5949094F) * 0.5F;
            } else {
               var1 -= 2.0F;
               return (var1 * var1 * (3.5949094F * var1 + 2.5949094F) + 2.0F) * 0.5F;
            }
         }
      };
      public static final EasingFunction EaseInOutBounce = new EasingFunction() {
         public float getInterpolation(float var1) {
            return var1 < 0.5F?Easing.EasingFunctions.EaseInBounce.getInterpolation(var1 * 2.0F) * 0.5F:Easing.EasingFunctions.EaseOutBounce.getInterpolation(var1 * 2.0F - 1.0F) * 0.5F + 0.5F;
         }
      };
      public static final EasingFunction EaseInOutCirc = new EasingFunction() {
         public float getInterpolation(float var1) {
            var1 /= 0.5F;
            if(var1 < 1.0F) {
               return ((float)Math.sqrt((double)(1.0F - var1 * var1)) - 1.0F) * -0.5F;
            } else {
               var1 -= 2.0F;
               return ((float)Math.sqrt((double)(1.0F - var1 * var1)) + 1.0F) * 0.5F;
            }
         }
      };
      public static final EasingFunction EaseInOutCubic = new EasingFunction() {
         public float getInterpolation(float var1) {
            var1 /= 0.5F;
            if(var1 < 1.0F) {
               return 0.5F * var1 * var1 * var1;
            } else {
               var1 -= 2.0F;
               return (var1 * var1 * var1 + 2.0F) * 0.5F;
            }
         }
      };
      public static final EasingFunction EaseInOutElastic = new EasingFunction() {
         public float getInterpolation(float var1) {
            if(var1 == 0.0F) {
               return 0.0F;
            } else {
               float var2 = var1 / 0.5F;
               if(var2 == 2.0F) {
                  return 1.0F;
               } else {
                  var1 = 0.07161973F * (float)Math.asin(1.0D);
                  if(var2 < 1.0F) {
                     --var2;
                     return (float)Math.pow(2.0D, (double)(10.0F * var2)) * (float)Math.sin((double)(var2 * 1.0F - var1) * 6.283185307179586D / (double)0.45000002F) * -0.5F;
                  } else {
                     --var2;
                     return (float)Math.pow(2.0D, (double)(-10.0F * var2)) * (float)Math.sin((double)(var2 * 1.0F - var1) * 6.283185307179586D / (double)0.45000002F) * 0.5F + 1.0F;
                  }
               }
            }
         }
      };
      public static final EasingFunction EaseInOutExpo = new EasingFunction() {
         public float getInterpolation(float var1) {
            if(var1 == 0.0F) {
               return 0.0F;
            } else if(var1 == 1.0F) {
               return 1.0F;
            } else {
               var1 /= 0.5F;
               return var1 < 1.0F?(float)Math.pow(2.0D, (double)((var1 - 1.0F) * 10.0F)) * 0.5F:(-((float)Math.pow(2.0D, (double)((var1 - 1.0F) * -10.0F))) + 2.0F) * 0.5F;
            }
         }
      };
      public static final EasingFunction EaseInOutQuad = new EasingFunction() {
         public float getInterpolation(float var1) {
            var1 /= 0.5F;
            if(var1 < 1.0F) {
               return 0.5F * var1 * var1;
            } else {
               --var1;
               return (var1 * (var1 - 2.0F) - 1.0F) * -0.5F;
            }
         }
      };
      public static final EasingFunction EaseInOutQuart = new EasingFunction() {
         public float getInterpolation(float var1) {
            var1 /= 0.5F;
            if(var1 < 1.0F) {
               return 0.5F * var1 * var1 * var1 * var1;
            } else {
               var1 -= 2.0F;
               return (var1 * var1 * var1 * var1 - 2.0F) * -0.5F;
            }
         }
      };
      public static final EasingFunction EaseInOutSine = new EasingFunction() {
         public float getInterpolation(float var1) {
            return ((float)Math.cos((double)var1 * 3.141592653589793D) - 1.0F) * -0.5F;
         }
      };
      public static final EasingFunction EaseInQuad = new EasingFunction() {
         public float getInterpolation(float var1) {
            return var1 * var1;
         }
      };
      public static final EasingFunction EaseInQuart = new EasingFunction() {
         public float getInterpolation(float var1) {
            return var1 * var1 * var1 * var1;
         }
      };
      public static final EasingFunction EaseInSine = new EasingFunction() {
         public float getInterpolation(float var1) {
            return -((float)Math.cos((double)var1 * 1.5707963267948966D)) + 1.0F;
         }
      };
      public static final EasingFunction EaseOutBack = new EasingFunction() {
         public float getInterpolation(float var1) {
            --var1;
            return var1 * var1 * (var1 * 2.70158F + 1.70158F) + 1.0F;
         }
      };
      public static final EasingFunction EaseOutBounce = new EasingFunction() {
         public float getInterpolation(float var1) {
            if(var1 < 0.36363637F) {
               return 7.5625F * var1 * var1;
            } else if(var1 < 0.72727275F) {
               var1 -= 0.54545456F;
               return 7.5625F * var1 * var1 + 0.75F;
            } else if(var1 < 0.90909094F) {
               var1 -= 0.8181818F;
               return 7.5625F * var1 * var1 + 0.9375F;
            } else {
               var1 -= 0.95454544F;
               return 7.5625F * var1 * var1 + 0.984375F;
            }
         }
      };
      public static final EasingFunction EaseOutCirc = new EasingFunction() {
         public float getInterpolation(float var1) {
            --var1;
            return (float)Math.sqrt((double)(1.0F - var1 * var1));
         }
      };
      public static final EasingFunction EaseOutCubic = new EasingFunction() {
         public float getInterpolation(float var1) {
            --var1;
            return var1 * var1 * var1 + 1.0F;
         }
      };
      public static final EasingFunction EaseOutElastic = new EasingFunction() {
         public float getInterpolation(float var1) {
            if(var1 == 0.0F) {
               return 0.0F;
            } else if(var1 == 1.0F) {
               return 1.0F;
            } else {
               float var2 = (float)Math.asin(1.0D);
               return (float)Math.pow(2.0D, (double)(-10.0F * var1)) * (float)Math.sin((double)(var1 - 0.047746483F * var2) * 6.283185307179586D / (double)0.3F) + 1.0F;
            }
         }
      };
      public static final EasingFunction EaseOutExpo = new EasingFunction() {
         public float getInterpolation(float var1) {
            return var1 == 1.0F?1.0F:-((float)Math.pow(2.0D, (double)((var1 + 1.0F) * -10.0F)));
         }
      };
      public static final EasingFunction EaseOutQuad = new EasingFunction() {
         public float getInterpolation(float var1) {
            return -var1 * (var1 - 2.0F);
         }
      };
      public static final EasingFunction EaseOutQuart = new EasingFunction() {
         public float getInterpolation(float var1) {
            --var1;
            return -(var1 * var1 * var1 * var1 - 1.0F);
         }
      };
      public static final EasingFunction EaseOutSine = new EasingFunction() {
         public float getInterpolation(float var1) {
            return (float)Math.sin((double)var1 * 1.5707963267948966D);
         }
      };
      public static final EasingFunction Linear = new EasingFunction() {
         public float getInterpolation(float var1) {
            return var1;
         }
      };


   }

   public static enum EasingOption {

      // $FF: synthetic field
      private static final Easing.EasingOption[] $VALUES = new Easing.EasingOption[]{Linear, EaseInQuad, EaseOutQuad, EaseInOutQuad, EaseInCubic, EaseOutCubic, EaseInOutCubic, EaseInQuart, EaseOutQuart, EaseInOutQuart, EaseInSine, EaseOutSine, EaseInOutSine, EaseInExpo, EaseOutExpo, EaseInOutExpo, EaseInCirc, EaseOutCirc, EaseInOutCirc, EaseInElastic, EaseOutElastic, EaseInOutElastic, EaseInBack, EaseOutBack, EaseInOutBack, EaseInBounce, EaseOutBounce, EaseInOutBounce};
      EaseInBack("EaseInBack", 22),
      EaseInBounce("EaseInBounce", 25),
      EaseInCirc("EaseInCirc", 16),
      EaseInCubic("EaseInCubic", 4),
      EaseInElastic("EaseInElastic", 19),
      EaseInExpo("EaseInExpo", 13),
      EaseInOutBack("EaseInOutBack", 24),
      EaseInOutBounce("EaseInOutBounce", 27),
      EaseInOutCirc("EaseInOutCirc", 18),
      EaseInOutCubic("EaseInOutCubic", 6),
      EaseInOutElastic("EaseInOutElastic", 21),
      EaseInOutExpo("EaseInOutExpo", 15),
      EaseInOutQuad("EaseInOutQuad", 3),
      EaseInOutQuart("EaseInOutQuart", 9),
      EaseInOutSine("EaseInOutSine", 12),
      EaseInQuad("EaseInQuad", 1),
      EaseInQuart("EaseInQuart", 7),
      EaseInSine("EaseInSine", 10),
      EaseOutBack("EaseOutBack", 23),
      EaseOutBounce("EaseOutBounce", 26),
      EaseOutCirc("EaseOutCirc", 17),
      EaseOutCubic("EaseOutCubic", 5),
      EaseOutElastic("EaseOutElastic", 20),
      EaseOutExpo("EaseOutExpo", 14),
      EaseOutQuad("EaseOutQuad", 2),
      EaseOutQuart("EaseOutQuart", 8),
      EaseOutSine("EaseOutSine", 11),
      Linear("Linear", 0);


      private EasingOption(String var1, int var2) {}
   }
}
