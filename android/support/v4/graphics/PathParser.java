package android.support.v4.graphics;

import android.graphics.Path;
import android.support.annotation.RestrictTo;
import android.util.Log;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class PathParser {

   private static final String LOGTAG = "PathParser";


   private static void addNode(ArrayList<PathParser.PathDataNode> var0, char var1, float[] var2) {
      var0.add(new PathParser.PathDataNode(var1, var2));
   }

   public static boolean canMorph(PathParser.PathDataNode[] var0, PathParser.PathDataNode[] var1) {
      if(var0 != null) {
         if(var1 == null) {
            return false;
         } else if(var0.length != var1.length) {
            return false;
         } else {
            for(int var2 = 0; var2 < var0.length; ++var2) {
               if(var0[var2].mType != var1[var2].mType) {
                  return false;
               }

               if(var0[var2].mParams.length != var1[var2].mParams.length) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   static float[] copyOfRange(float[] var0, int var1, int var2) {
      if(var1 > var2) {
         throw new IllegalArgumentException();
      } else {
         int var3 = var0.length;
         if(var1 >= 0 && var1 <= var3) {
            var2 -= var1;
            var3 = Math.min(var2, var3 - var1);
            float[] var4 = new float[var2];
            System.arraycopy(var0, var1, var4, 0, var3);
            return var4;
         } else {
            throw new ArrayIndexOutOfBoundsException();
         }
      }
   }

   public static PathParser.PathDataNode[] createNodesFromPathData(String var0) {
      if(var0 == null) {
         return null;
      } else {
         ArrayList var3 = new ArrayList();
         int var2 = 1;

         int var1;
         for(var1 = 0; var2 < var0.length(); var1 = var2++) {
            var2 = nextStart(var0, var2);
            String var4 = var0.substring(var1, var2).trim();
            if(var4.length() > 0) {
               float[] var5 = getFloats(var4);
               addNode(var3, var4.charAt(0), var5);
            }
         }

         if(var2 - var1 == 1 && var1 < var0.length()) {
            addNode(var3, var0.charAt(var1), new float[0]);
         }

         return (PathParser.PathDataNode[])var3.toArray(new PathParser.PathDataNode[var3.size()]);
      }
   }

   public static Path createPathFromPathData(String var0) {
      Path var1 = new Path();
      PathParser.PathDataNode[] var2 = createNodesFromPathData(var0);
      if(var2 != null) {
         try {
            PathParser.PathDataNode.nodesToPath(var2, var1);
            return var1;
         } catch (RuntimeException var3) {
            StringBuilder var4 = new StringBuilder();
            var4.append("Error in parsing ");
            var4.append(var0);
            throw new RuntimeException(var4.toString(), var3);
         }
      } else {
         return null;
      }
   }

   public static PathParser.PathDataNode[] deepCopyNodes(PathParser.PathDataNode[] var0) {
      if(var0 == null) {
         return null;
      } else {
         PathParser.PathDataNode[] var2 = new PathParser.PathDataNode[var0.length];

         for(int var1 = 0; var1 < var0.length; ++var1) {
            var2[var1] = new PathParser.PathDataNode(var0[var1]);
         }

         return var2;
      }
   }

   private static void extract(String var0, int var1, PathParser.ExtractFloatResult var2) {
      var2.mEndWithNegOrDot = false;
      int var4 = var1;
      boolean var3 = false;
      boolean var6 = false;

      for(boolean var5 = false; var4 < var0.length(); ++var4) {
         label42: {
            char var7 = var0.charAt(var4);
            if(var7 != 32) {
               if(var7 == 69 || var7 == 101) {
                  var3 = true;
                  break label42;
               }

               switch(var7) {
               case 44:
                  break;
               case 45:
                  if(var4 != var1 && !var3) {
                     var2.mEndWithNegOrDot = true;
                     break;
                  }
               case 46:
                  if(!var6) {
                     var3 = false;
                     var6 = true;
                     break label42;
                  }

                  var2.mEndWithNegOrDot = true;
                  break;
               default:
                  var3 = false;
                  break label42;
               }
            }

            var3 = false;
            var5 = true;
         }

         if(var5) {
            break;
         }
      }

      var2.mEndPosition = var4;
   }

   private static float[] getFloats(String param0) {
      // $FF: Couldn't be decompiled
   }

   private static int nextStart(String var0, int var1) {
      while(var1 < var0.length()) {
         char var2 = var0.charAt(var1);
         if(((var2 - 65) * (var2 - 90) <= 0 || (var2 - 97) * (var2 - 122) <= 0) && var2 != 101 && var2 != 69) {
            return var1;
         }

         ++var1;
      }

      return var1;
   }

   public static void updateNodes(PathParser.PathDataNode[] var0, PathParser.PathDataNode[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         var0[var2].mType = var1[var2].mType;

         for(int var3 = 0; var3 < var1[var2].mParams.length; ++var3) {
            var0[var2].mParams[var3] = var1[var2].mParams[var3];
         }
      }

   }

   static class ExtractFloatResult {

      int mEndPosition;
      boolean mEndWithNegOrDot;


   }

   public static class PathDataNode {

      @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
      public float[] mParams;
      @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
      public char mType;


      PathDataNode(char var1, float[] var2) {
         this.mType = var1;
         this.mParams = var2;
      }

      PathDataNode(PathParser.PathDataNode var1) {
         this.mType = var1.mType;
         this.mParams = PathParser.copyOfRange(var1.mParams, 0, var1.mParams.length);
      }

      private static void addCommand(Path var0, float[] var1, char var2, char var3, float[] var4) {
         float var5;
         float var6;
         float var7;
         float var8;
         float var9;
         float var10;
         float var11;
         float var12;
         float var13;
         byte var15;
         label164: {
            var11 = var1[0];
            var12 = var1[1];
            var13 = var1[2];
            float var14 = var1[3];
            var10 = var1[4];
            var9 = var1[5];
            var5 = var11;
            var6 = var12;
            var7 = var13;
            var8 = var14;
            switch(var3) {
            case 65:
            case 97:
               var15 = 7;
               var8 = var14;
               var7 = var13;
               var6 = var12;
               var5 = var11;
               break label164;
            case 67:
            case 99:
               var15 = 6;
               var5 = var11;
               var6 = var12;
               var7 = var13;
               var8 = var14;
               break label164;
            case 72:
            case 86:
            case 104:
            case 118:
               var15 = 1;
               var5 = var11;
               var6 = var12;
               var7 = var13;
               var8 = var14;
               break label164;
            case 76:
            case 77:
            case 84:
            case 108:
            case 109:
            case 116:
               break;
            case 81:
            case 83:
            case 113:
            case 115:
               var15 = 4;
               var5 = var11;
               var6 = var12;
               var7 = var13;
               var8 = var14;
               break label164;
            case 90:
            case 122:
               var0.close();
               var0.moveTo(var10, var9);
               var5 = var10;
               var7 = var10;
               var6 = var9;
               var8 = var9;
               break;
            default:
               var8 = var14;
               var7 = var13;
               var6 = var12;
               var5 = var11;
            }

            var15 = 2;
         }

         var11 = var6;
         byte var17 = 0;
         char var16 = var2;
         int var22 = var17;
         var6 = var5;

         for(var5 = var11; var22 < var4.length; var16 = var3) {
            label148: {
               label147: {
                  var12 = 0.0F;
                  var11 = 0.0F;
                  int var18;
                  int var19;
                  boolean var20;
                  boolean var21;
                  int var23;
                  int var24;
                  switch(var3) {
                  case 65:
                     var24 = var22 + 5;
                     var7 = var4[var24];
                     var18 = var22 + 6;
                     var8 = var4[var18];
                     var11 = var4[var22 + 0];
                     var12 = var4[var22 + 1];
                     var13 = var4[var22 + 2];
                     if(var4[var22 + 3] != 0.0F) {
                        var20 = true;
                     } else {
                        var20 = false;
                     }

                     if(var4[var22 + 4] != 0.0F) {
                        var21 = true;
                     } else {
                        var21 = false;
                     }

                     drawArc(var0, var6, var5, var7, var8, var11, var12, var13, var20, var21);
                     var6 = var4[var24];
                     var5 = var4[var18];
                     break label147;
                  case 67:
                     var5 = var4[var22 + 0];
                     var6 = var4[var22 + 1];
                     var24 = var22 + 2;
                     var7 = var4[var24];
                     var18 = var22 + 3;
                     var8 = var4[var18];
                     var19 = var22 + 4;
                     var11 = var4[var19];
                     var23 = var22 + 5;
                     var0.cubicTo(var5, var6, var7, var8, var11, var4[var23]);
                     var6 = var4[var19];
                     var5 = var4[var23];
                     var7 = var4[var24];
                     var8 = var4[var18];
                     break label148;
                  case 72:
                     var23 = var22 + 0;
                     var0.lineTo(var4[var23], var5);
                     var6 = var4[var23];
                     break label148;
                  case 76:
                     var24 = var22 + 0;
                     var5 = var4[var24];
                     var23 = var22 + 1;
                     var0.lineTo(var5, var4[var23]);
                     var6 = var4[var24];
                     var5 = var4[var23];
                     break label148;
                  case 77:
                     var24 = var22 + 0;
                     var6 = var4[var24];
                     var18 = var22 + 1;
                     var5 = var4[var18];
                     if(var22 > 0) {
                        var0.lineTo(var4[var24], var4[var18]);
                     } else {
                        var0.moveTo(var4[var24], var4[var18]);
                        var9 = var5;
                        var10 = var6;
                     }
                     break label148;
                  case 81:
                     var24 = var22 + 0;
                     var5 = var4[var24];
                     var18 = var22 + 1;
                     var6 = var4[var18];
                     var19 = var22 + 2;
                     var7 = var4[var19];
                     var23 = var22 + 3;
                     var0.quadTo(var5, var6, var7, var4[var23]);
                     var8 = var4[var24];
                     var7 = var4[var18];
                     var6 = var4[var19];
                     var5 = var4[var23];
                     break;
                  case 83:
                     if(var16 != 99 && var16 != 115 && var16 != 67 && var16 != 83) {
                        var7 = var5;
                        var5 = var6;
                        var6 = var7;
                     } else {
                        var5 = var5 * 2.0F - var8;
                        var7 = var6 * 2.0F - var7;
                        var6 = var5;
                        var5 = var7;
                     }

                     var23 = var22 + 0;
                     var7 = var4[var23];
                     var18 = var22 + 1;
                     var8 = var4[var18];
                     var19 = var22 + 2;
                     var11 = var4[var19];
                     var24 = var22 + 3;
                     var0.cubicTo(var5, var6, var7, var8, var11, var4[var24]);
                     var8 = var4[var23];
                     var7 = var4[var18];
                     var6 = var4[var19];
                     var5 = var4[var24];
                     break;
                  case 84:
                     label105: {
                        if(var16 != 113 && var16 != 116 && var16 != 81) {
                           var6 = var6;
                           var5 = var5;
                           if(var16 != 84) {
                              break label105;
                           }
                        }

                        var5 = var5 * 2.0F - var8;
                        var6 = var6 * 2.0F - var7;
                     }

                     var23 = var22 + 0;
                     var7 = var4[var23];
                     var24 = var22 + 1;
                     var0.quadTo(var6, var5, var7, var4[var24]);
                     var11 = var4[var23];
                     var12 = var4[var24];
                     var7 = var6;
                     var8 = var5;
                     var5 = var12;
                     var6 = var11;
                     break label148;
                  case 86:
                     var23 = var22 + 0;
                     var0.lineTo(var6, var4[var23]);
                     var5 = var4[var23];
                     break label148;
                  case 97:
                     var23 = var22 + 5;
                     var7 = var4[var23];
                     var24 = var22 + 6;
                     var8 = var4[var24];
                     var11 = var4[var22 + 0];
                     var12 = var4[var22 + 1];
                     var13 = var4[var22 + 2];
                     if(var4[var22 + 3] != 0.0F) {
                        var20 = true;
                     } else {
                        var20 = false;
                     }

                     if(var4[var22 + 4] != 0.0F) {
                        var21 = true;
                     } else {
                        var21 = false;
                     }

                     drawArc(var0, var6, var5, var7 + var6, var8 + var5, var11, var12, var13, var20, var21);
                     var6 += var4[var23];
                     var5 += var4[var24];
                     break label147;
                  case 99:
                     var7 = var4[var22 + 0];
                     var8 = var4[var22 + 1];
                     var23 = var22 + 2;
                     var11 = var4[var23];
                     var24 = var22 + 3;
                     var12 = var4[var24];
                     var18 = var22 + 4;
                     var13 = var4[var18];
                     var19 = var22 + 5;
                     var0.rCubicTo(var7, var8, var11, var12, var13, var4[var19]);
                     var7 = var4[var23] + var6;
                     var8 = var4[var24] + var5;
                     var6 += var4[var18];
                     var5 += var4[var19];
                     break label148;
                  case 104:
                     var23 = var22 + 0;
                     var0.rLineTo(var4[var23], 0.0F);
                     var6 += var4[var23];
                     break label148;
                  case 108:
                     var23 = var22 + 0;
                     var11 = var4[var23];
                     var24 = var22 + 1;
                     var0.rLineTo(var11, var4[var24]);
                     var6 += var4[var23];
                     var5 += var4[var24];
                     break label148;
                  case 109:
                     var23 = var22 + 0;
                     var6 += var4[var23];
                     var24 = var22 + 1;
                     var5 += var4[var24];
                     if(var22 > 0) {
                        var0.rLineTo(var4[var23], var4[var24]);
                     } else {
                        var0.rMoveTo(var4[var23], var4[var24]);
                        var9 = var5;
                        var10 = var6;
                     }
                     break label148;
                  case 113:
                     var23 = var22 + 0;
                     var7 = var4[var23];
                     var24 = var22 + 1;
                     var8 = var4[var24];
                     var18 = var22 + 2;
                     var11 = var4[var18];
                     var19 = var22 + 3;
                     var0.rQuadTo(var7, var8, var11, var4[var19]);
                     var7 = var4[var23] + var6;
                     var8 = var4[var24] + var5;
                     var6 += var4[var18];
                     var5 += var4[var19];
                     break label148;
                  case 115:
                     if(var16 != 99 && var16 != 115 && var16 != 67 && var16 != 83) {
                        var8 = 0.0F;
                        var7 = var12;
                     } else {
                        var8 = var5 - var8;
                        var7 = var6 - var7;
                     }

                     var23 = var22 + 0;
                     var11 = var4[var23];
                     var24 = var22 + 1;
                     var12 = var4[var24];
                     var18 = var22 + 2;
                     var13 = var4[var18];
                     var19 = var22 + 3;
                     var0.rCubicTo(var7, var8, var11, var12, var13, var4[var19]);
                     var7 = var4[var23] + var6;
                     var8 = var4[var24] + var5;
                     var6 += var4[var18];
                     var5 += var4[var19];
                     break label148;
                  case 116:
                     if(var16 != 113 && var16 != 116 && var16 != 81 && var16 != 84) {
                        var8 = 0.0F;
                        var7 = var11;
                     } else {
                        var7 = var6 - var7;
                        var8 = var5 - var8;
                     }

                     var23 = var22 + 0;
                     var11 = var4[var23];
                     var24 = var22 + 1;
                     var0.rQuadTo(var7, var8, var11, var4[var24]);
                     var11 = var6 + var4[var23];
                     var12 = var5 + var4[var24];
                     var8 += var5;
                     var7 += var6;
                     var5 = var12;
                     var6 = var11;
                     break label148;
                  case 118:
                     var23 = var22 + 0;
                     var0.rLineTo(0.0F, var4[var23]);
                     var5 += var4[var23];
                  default:
                     break label148;
                  }

                  var11 = var7;
                  var7 = var8;
                  var8 = var11;
                  break label148;
               }

               var8 = var5;
               var7 = var6;
            }

            var22 += var15;
         }

         var1[0] = var6;
         var1[1] = var5;
         var1[2] = var7;
         var1[3] = var8;
         var1[4] = var10;
         var1[5] = var9;
      }

      private static void arcToBezier(Path var0, double var1, double var3, double var5, double var7, double var9, double var11, double var13, double var15, double var17) {
         int var43 = (int)Math.ceil(Math.abs(var17 * 4.0D / 3.141592653589793D));
         double var27 = Math.cos(var13);
         double var31 = Math.sin(var13);
         double var19 = Math.cos(var15);
         double var21 = Math.sin(var15);
         var13 = -var5;
         double var37 = var13 * var27;
         double var39 = var7 * var31;
         var13 *= var31;
         double var29 = var7 * var27;
         double var33 = var17 / (double)var43;
         int var44 = 0;
         double var23 = var21 * var13 + var19 * var29;
         var19 = var37 * var21 - var39 * var19;
         var21 = var11;
         var17 = var9;
         double var25 = var15;
         var7 = var31;
         var9 = var27;
         var15 = var13;
         var13 = var33;

         for(var11 = var29; var44 < var43; var25 = var27) {
            var27 = var25 + var13;
            var33 = Math.sin(var27);
            double var41 = Math.cos(var27);
            var31 = var1 + var5 * var9 * var41 - var39 * var33;
            double var35 = var3 + var5 * var7 * var41 + var11 * var33;
            var29 = var37 * var33 - var39 * var41;
            var33 = var33 * var15 + var41 * var11;
            var25 = var27 - var25;
            var41 = Math.tan(var25 / 2.0D);
            var25 = Math.sin(var25) * (Math.sqrt(var41 * 3.0D * var41 + 4.0D) - 1.0D) / 3.0D;
            var0.rLineTo(0.0F, 0.0F);
            var0.cubicTo((float)(var17 + var19 * var25), (float)(var21 + var23 * var25), (float)(var31 - var25 * var29), (float)(var35 - var25 * var33), (float)var31, (float)var35);
            ++var44;
            var21 = var35;
            var17 = var31;
            var23 = var33;
            var19 = var29;
         }

      }

      private static void drawArc(Path var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, boolean var8, boolean var9) {
         double var18 = Math.toRadians((double)var7);
         double var20 = Math.cos(var18);
         double var22 = Math.sin(var18);
         double var24 = (double)var1;
         double var26 = (double)var2;
         double var28 = (double)var5;
         double var10 = (var24 * var20 + var26 * var22) / var28;
         double var12 = (double)(-var1);
         double var30 = (double)var6;
         double var16 = (var12 * var22 + var26 * var20) / var30;
         double var14 = (double)var3;
         var12 = (double)var4;
         double var32 = (var14 * var20 + var12 * var22) / var28;
         double var34 = ((double)(-var3) * var22 + var12 * var20) / var30;
         double var38 = var10 - var32;
         double var36 = var16 - var34;
         var14 = (var10 + var32) / 2.0D;
         var12 = (var16 + var34) / 2.0D;
         double var40 = var38 * var38 + var36 * var36;
         if(var40 == 0.0D) {
            Log.w("PathParser", " Points are coincident");
         } else {
            double var42 = 1.0D / var40 - 0.25D;
            if(var42 < 0.0D) {
               StringBuilder var45 = new StringBuilder();
               var45.append("Points are too far apart ");
               var45.append(var40);
               Log.w("PathParser", var45.toString());
               float var44 = (float)(Math.sqrt(var40) / 1.99999D);
               drawArc(var0, var1, var2, var3, var4, var5 * var44, var6 * var44, var7, var8, var9);
            } else {
               var40 = Math.sqrt(var42);
               var38 *= var40;
               var36 = var40 * var36;
               if(var8 == var9) {
                  var14 -= var36;
                  var12 += var38;
               } else {
                  var14 += var36;
                  var12 -= var38;
               }

               var36 = Math.atan2(var16 - var12, var10 - var14);
               var16 = Math.atan2(var34 - var12, var32 - var14) - var36;
               if(var16 >= 0.0D) {
                  var8 = true;
               } else {
                  var8 = false;
               }

               var10 = var16;
               if(var9 != var8) {
                  if(var16 > 0.0D) {
                     var10 = var16 - 6.283185307179586D;
                  } else {
                     var10 = var16 + 6.283185307179586D;
                  }
               }

               var14 *= var28;
               var12 *= var30;
               arcToBezier(var0, var14 * var20 - var12 * var22, var14 * var22 + var12 * var20, var28, var30, var24, var26, var18, var36, var10);
            }
         }
      }

      public static void nodesToPath(PathParser.PathDataNode[] var0, Path var1) {
         float[] var4 = new float[6];
         char var2 = 109;

         for(int var3 = 0; var3 < var0.length; ++var3) {
            addCommand(var1, var4, var2, var0[var3].mType, var0[var3].mParams);
            var2 = var0[var3].mType;
         }

      }

      public void interpolatePathDataNode(PathParser.PathDataNode var1, PathParser.PathDataNode var2, float var3) {
         for(int var4 = 0; var4 < var1.mParams.length; ++var4) {
            this.mParams[var4] = var1.mParams[var4] * (1.0F - var3) + var2.mParams[var4] * var3;
         }

      }
   }
}
