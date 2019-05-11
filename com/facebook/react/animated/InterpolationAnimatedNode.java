package com.facebook.react.animated;

import com.facebook.react.animated.AnimatedNode;
import com.facebook.react.animated.ValueAnimatedNode;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import javax.annotation.Nullable;

class InterpolationAnimatedNode extends ValueAnimatedNode {

   public static final String EXTRAPOLATE_TYPE_CLAMP = "clamp";
   public static final String EXTRAPOLATE_TYPE_EXTEND = "extend";
   public static final String EXTRAPOLATE_TYPE_IDENTITY = "identity";
   private final String mExtrapolateLeft;
   private final String mExtrapolateRight;
   private final double[] mInputRange;
   private final double[] mOutputRange;
   @Nullable
   private ValueAnimatedNode mParent;


   public InterpolationAnimatedNode(ReadableMap var1) {
      this.mInputRange = fromDoubleArray(var1.getArray("inputRange"));
      this.mOutputRange = fromDoubleArray(var1.getArray("outputRange"));
      this.mExtrapolateLeft = var1.getString("extrapolateLeft");
      this.mExtrapolateRight = var1.getString("extrapolateRight");
   }

   private static int findRangeIndex(double var0, double[] var2) {
      int var3;
      for(var3 = 1; var3 < var2.length - 1 && var2[var3] < var0; ++var3) {
         ;
      }

      return var3 - 1;
   }

   private static double[] fromDoubleArray(ReadableArray var0) {
      double[] var2 = new double[var0.size()];

      for(int var1 = 0; var1 < var2.length; ++var1) {
         var2[var1] = var0.getDouble(var1);
      }

      return var2;
   }

   private static double interpolate(double var0, double var2, double var4, double var6, double var8, String var10, String var11) {
      byte var15 = 0;
      int var14;
      byte var18;
      if(var0 < var2) {
         label61: {
            var14 = var10.hashCode();
            if(var14 != -1289044198) {
               if(var14 != -135761730) {
                  if(var14 == 94742715 && var10.equals("clamp")) {
                     var18 = 1;
                     break label61;
                  }
               } else if(var10.equals("identity")) {
                  var18 = 0;
                  break label61;
               }
            } else if(var10.equals("extend")) {
               var18 = 2;
               break label61;
            }

            var18 = -1;
         }

         switch(var18) {
         case 0:
            return var0;
         case 1:
            var0 = var2;
         case 2:
            break;
         default:
            StringBuilder var17 = new StringBuilder();
            var17.append("Invalid extrapolation type ");
            var17.append(var10);
            var17.append("for left extrapolation");
            throw new JSApplicationIllegalArgumentException(var17.toString());
         }
      }

      double var12 = var0;
      if(var0 > var4) {
         label50: {
            var14 = var11.hashCode();
            if(var14 != -1289044198) {
               if(var14 != -135761730) {
                  if(var14 == 94742715 && var11.equals("clamp")) {
                     var18 = 1;
                     break label50;
                  }
               } else if(var11.equals("identity")) {
                  var18 = var15;
                  break label50;
               }
            } else if(var11.equals("extend")) {
               var18 = 2;
               break label50;
            }

            var18 = -1;
         }

         var12 = var0;
         switch(var18) {
         case 0:
            return var0;
         case 1:
            var12 = var4;
         case 2:
            break;
         default:
            StringBuilder var16 = new StringBuilder();
            var16.append("Invalid extrapolation type ");
            var16.append(var11);
            var16.append("for right extrapolation");
            throw new JSApplicationIllegalArgumentException(var16.toString());
         }
      }

      return var6 + (var8 - var6) * (var12 - var2) / (var4 - var2);
   }

   static double interpolate(double var0, double[] var2, double[] var3, String var4, String var5) {
      int var8 = findRangeIndex(var0, var2);
      double var6 = var2[var8];
      int var9 = var8 + 1;
      return interpolate(var0, var6, var2[var9], var3[var8], var3[var9], var4, var5);
   }

   public void onAttachedToNode(AnimatedNode var1) {
      if(this.mParent != null) {
         throw new IllegalStateException("Parent already attached");
      } else if(!(var1 instanceof ValueAnimatedNode)) {
         throw new IllegalArgumentException("Parent is of an invalid type");
      } else {
         this.mParent = (ValueAnimatedNode)var1;
      }
   }

   public void onDetachedFromNode(AnimatedNode var1) {
      if(var1 != this.mParent) {
         throw new IllegalArgumentException("Invalid parent node provided");
      } else {
         this.mParent = null;
      }
   }

   public void update() {
      if(this.mParent == null) {
         throw new IllegalStateException("Trying to update interpolation node that has not been attached to the parent");
      } else {
         this.mValue = interpolate(this.mParent.getValue(), this.mInputRange, this.mOutputRange, this.mExtrapolateLeft, this.mExtrapolateRight);
      }
   }
}
