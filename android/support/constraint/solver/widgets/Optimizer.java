package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;
import android.support.constraint.solver.widgets.Guideline;

public class Optimizer {

   static void applyDirectResolutionHorizontalChain(ConstraintWidgetContainer var0, LinearSystem var1, int var2, ConstraintWidget var3) {
      ConstraintWidget var14 = var3;
      ConstraintWidget var16 = null;
      int var12 = 0;
      int var11 = 0;
      float var5 = 0.0F;

      while(true) {
         boolean var13 = true;
         float var4;
         int var9;
         int var10;
         ConstraintWidget var15;
         if(var14 == null) {
            if(var16 != null) {
               if(var16.mRight.mTarget != null) {
                  var9 = var16.mRight.mTarget.mOwner.getX();
               } else {
                  var9 = 0;
               }

               var10 = var9;
               if(var16.mRight.mTarget != null) {
                  var10 = var9;
                  if(var16.mRight.mTarget.mOwner == var0) {
                     var10 = var0.getRight();
                  }
               }
            } else {
               var10 = 0;
            }

            float var8 = (float)(var10 - 0) - (float)var11;
            var4 = var8 / (float)(var12 + 1);
            float var6;
            if(var2 == 0) {
               var6 = var4;
            } else {
               var6 = var8 / (float)var2;
               var4 = 0.0F;
            }

            while(var3 != null) {
               if(var3.mLeft.mTarget != null) {
                  var9 = var3.mLeft.getMargin();
               } else {
                  var9 = 0;
               }

               if(var3.mRight.mTarget != null) {
                  var10 = var3.mRight.getMargin();
               } else {
                  var10 = 0;
               }

               float var7;
               if(var3.getVisibility() != 8) {
                  var7 = (float)var9;
                  var4 += var7;
                  var1.addEquality(var3.mLeft.mSolverVariable, (int)(var4 + 0.5F));
                  if(var3.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                     if(var5 == 0.0F) {
                        var4 += var6 - var7 - (float)var10;
                     } else {
                        var4 += var3.mHorizontalWeight * var8 / var5 - var7 - (float)var10;
                     }
                  } else {
                     var4 += (float)var3.getWidth();
                  }

                  var1.addEquality(var3.mRight.mSolverVariable, (int)(0.5F + var4));
                  var7 = var4;
                  if(var2 == 0) {
                     var7 = var4 + var6;
                  }

                  var4 = var7 + (float)var10;
               } else {
                  var7 = var6 / 2.0F;
                  SolverVariable var18 = var3.mLeft.mSolverVariable;
                  var9 = (int)(var4 - var7 + 0.5F);
                  var1.addEquality(var18, var9);
                  var1.addEquality(var3.mRight.mSolverVariable, var9);
               }

               if(var3.mRight.mTarget != null) {
                  var14 = var3.mRight.mTarget.mOwner;
               } else {
                  var14 = null;
               }

               var15 = var14;
               if(var14 != null) {
                  var15 = var14;
                  if(var14.mLeft.mTarget != null) {
                     var15 = var14;
                     if(var14.mLeft.mTarget.mOwner != var3) {
                        var15 = null;
                     }
                  }
               }

               if(var15 == var0) {
                  var3 = null;
               } else {
                  var3 = var15;
               }
            }

            return;
         }

         if(var14.getVisibility() != 8) {
            var13 = false;
         }

         var9 = var12;
         var10 = var11;
         var4 = var5;
         if(!var13) {
            ++var12;
            if(var14.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
               int var17 = var14.getWidth();
               if(var14.mLeft.mTarget != null) {
                  var9 = var14.mLeft.getMargin();
               } else {
                  var9 = 0;
               }

               if(var14.mRight.mTarget != null) {
                  var10 = var14.mRight.getMargin();
               } else {
                  var10 = 0;
               }

               var10 += var11 + var17 + var9;
               var9 = var12;
               var4 = var5;
            } else {
               var4 = var5 + var14.mHorizontalWeight;
               var10 = var11;
               var9 = var12;
            }
         }

         if(var14.mRight.mTarget != null) {
            var16 = var14.mRight.mTarget.mOwner;
         } else {
            var16 = null;
         }

         var15 = var16;
         if(var16 != null) {
            label111: {
               if(var16.mLeft.mTarget != null) {
                  var15 = var16;
                  if(var16.mLeft.mTarget == null) {
                     break label111;
                  }

                  var15 = var16;
                  if(var16.mLeft.mTarget.mOwner == var14) {
                     break label111;
                  }
               }

               var15 = null;
            }
         }

         var16 = var14;
         var14 = var15;
         var12 = var9;
         var11 = var10;
         var5 = var4;
      }
   }

   static void applyDirectResolutionVerticalChain(ConstraintWidgetContainer var0, LinearSystem var1, int var2, ConstraintWidget var3) {
      ConstraintWidget var14 = var3;
      ConstraintWidget var16 = null;
      int var12 = 0;
      int var11 = 0;
      float var5 = 0.0F;

      while(true) {
         boolean var13 = true;
         float var4;
         int var9;
         int var10;
         ConstraintWidget var15;
         if(var14 == null) {
            if(var16 != null) {
               if(var16.mBottom.mTarget != null) {
                  var9 = var16.mBottom.mTarget.mOwner.getX();
               } else {
                  var9 = 0;
               }

               var10 = var9;
               if(var16.mBottom.mTarget != null) {
                  var10 = var9;
                  if(var16.mBottom.mTarget.mOwner == var0) {
                     var10 = var0.getBottom();
                  }
               }
            } else {
               var10 = 0;
            }

            float var8 = (float)(var10 - 0) - (float)var11;
            var4 = var8 / (float)(var12 + 1);
            float var6;
            if(var2 == 0) {
               var6 = var4;
            } else {
               var6 = var8 / (float)var2;
               var4 = 0.0F;
            }

            while(var3 != null) {
               if(var3.mTop.mTarget != null) {
                  var9 = var3.mTop.getMargin();
               } else {
                  var9 = 0;
               }

               if(var3.mBottom.mTarget != null) {
                  var10 = var3.mBottom.getMargin();
               } else {
                  var10 = 0;
               }

               float var7;
               if(var3.getVisibility() != 8) {
                  var7 = (float)var9;
                  var4 += var7;
                  var1.addEquality(var3.mTop.mSolverVariable, (int)(var4 + 0.5F));
                  if(var3.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                     if(var5 == 0.0F) {
                        var4 += var6 - var7 - (float)var10;
                     } else {
                        var4 += var3.mVerticalWeight * var8 / var5 - var7 - (float)var10;
                     }
                  } else {
                     var4 += (float)var3.getHeight();
                  }

                  var1.addEquality(var3.mBottom.mSolverVariable, (int)(0.5F + var4));
                  var7 = var4;
                  if(var2 == 0) {
                     var7 = var4 + var6;
                  }

                  var4 = var7 + (float)var10;
               } else {
                  var7 = var6 / 2.0F;
                  SolverVariable var18 = var3.mTop.mSolverVariable;
                  var9 = (int)(var4 - var7 + 0.5F);
                  var1.addEquality(var18, var9);
                  var1.addEquality(var3.mBottom.mSolverVariable, var9);
               }

               if(var3.mBottom.mTarget != null) {
                  var14 = var3.mBottom.mTarget.mOwner;
               } else {
                  var14 = null;
               }

               var15 = var14;
               if(var14 != null) {
                  var15 = var14;
                  if(var14.mTop.mTarget != null) {
                     var15 = var14;
                     if(var14.mTop.mTarget.mOwner != var3) {
                        var15 = null;
                     }
                  }
               }

               if(var15 == var0) {
                  var3 = null;
               } else {
                  var3 = var15;
               }
            }

            return;
         }

         if(var14.getVisibility() != 8) {
            var13 = false;
         }

         var9 = var12;
         var10 = var11;
         var4 = var5;
         if(!var13) {
            ++var12;
            if(var14.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
               int var17 = var14.getHeight();
               if(var14.mTop.mTarget != null) {
                  var9 = var14.mTop.getMargin();
               } else {
                  var9 = 0;
               }

               if(var14.mBottom.mTarget != null) {
                  var10 = var14.mBottom.getMargin();
               } else {
                  var10 = 0;
               }

               var10 += var11 + var17 + var9;
               var9 = var12;
               var4 = var5;
            } else {
               var4 = var5 + var14.mVerticalWeight;
               var10 = var11;
               var9 = var12;
            }
         }

         if(var14.mBottom.mTarget != null) {
            var16 = var14.mBottom.mTarget.mOwner;
         } else {
            var16 = null;
         }

         var15 = var16;
         if(var16 != null) {
            label111: {
               if(var16.mTop.mTarget != null) {
                  var15 = var16;
                  if(var16.mTop.mTarget == null) {
                     break label111;
                  }

                  var15 = var16;
                  if(var16.mTop.mTarget.mOwner == var14) {
                     break label111;
                  }
               }

               var15 = null;
            }
         }

         var16 = var14;
         var14 = var15;
         var12 = var9;
         var11 = var10;
         var5 = var4;
      }
   }

   static void checkHorizontalSimpleDependency(ConstraintWidgetContainer var0, LinearSystem var1, ConstraintWidget var2) {
      if(var2.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
         var2.mHorizontalResolution = 1;
      } else {
         int var9;
         int var10;
         if(var0.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && var2.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            var2.mLeft.mSolverVariable = var1.createObjectVariable(var2.mLeft);
            var2.mRight.mSolverVariable = var1.createObjectVariable(var2.mRight);
            var9 = var2.mLeft.mMargin;
            var10 = var0.getWidth() - var2.mRight.mMargin;
            var1.addEquality(var2.mLeft.mSolverVariable, var9);
            var1.addEquality(var2.mRight.mSolverVariable, var10);
            var2.setHorizontalDimension(var9, var10);
            var2.mHorizontalResolution = 2;
         } else if(var2.mLeft.mTarget != null && var2.mRight.mTarget != null) {
            if(var2.mLeft.mTarget.mOwner == var0 && var2.mRight.mTarget.mOwner == var0) {
               var9 = var2.mLeft.getMargin();
               var10 = var2.mRight.getMargin();
               if(var0.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                  var10 = var0.getWidth() - var10;
               } else {
                  int var6 = var2.getWidth();
                  var9 += (int)((float)(var0.getWidth() - var9 - var10 - var6) * var2.mHorizontalBiasPercent + 0.5F);
                  var10 = var2.getWidth() + var9;
               }

               var2.mLeft.mSolverVariable = var1.createObjectVariable(var2.mLeft);
               var2.mRight.mSolverVariable = var1.createObjectVariable(var2.mRight);
               var1.addEquality(var2.mLeft.mSolverVariable, var9);
               var1.addEquality(var2.mRight.mSolverVariable, var10);
               var2.mHorizontalResolution = 2;
               var2.setHorizontalDimension(var9, var10);
            } else {
               var2.mHorizontalResolution = 1;
            }
         } else if(var2.mLeft.mTarget != null && var2.mLeft.mTarget.mOwner == var0) {
            var9 = var2.mLeft.getMargin();
            var10 = var2.getWidth() + var9;
            var2.mLeft.mSolverVariable = var1.createObjectVariable(var2.mLeft);
            var2.mRight.mSolverVariable = var1.createObjectVariable(var2.mRight);
            var1.addEquality(var2.mLeft.mSolverVariable, var9);
            var1.addEquality(var2.mRight.mSolverVariable, var10);
            var2.mHorizontalResolution = 2;
            var2.setHorizontalDimension(var9, var10);
         } else if(var2.mRight.mTarget != null && var2.mRight.mTarget.mOwner == var0) {
            var2.mLeft.mSolverVariable = var1.createObjectVariable(var2.mLeft);
            var2.mRight.mSolverVariable = var1.createObjectVariable(var2.mRight);
            var9 = var0.getWidth() - var2.mRight.getMargin();
            var10 = var9 - var2.getWidth();
            var1.addEquality(var2.mLeft.mSolverVariable, var10);
            var1.addEquality(var2.mRight.mSolverVariable, var9);
            var2.mHorizontalResolution = 2;
            var2.setHorizontalDimension(var10, var9);
         } else {
            SolverVariable var8;
            if(var2.mLeft.mTarget != null && var2.mLeft.mTarget.mOwner.mHorizontalResolution == 2) {
               var8 = var2.mLeft.mTarget.mSolverVariable;
               var2.mLeft.mSolverVariable = var1.createObjectVariable(var2.mLeft);
               var2.mRight.mSolverVariable = var1.createObjectVariable(var2.mRight);
               var9 = (int)(var8.computedValue + (float)var2.mLeft.getMargin() + 0.5F);
               var10 = var2.getWidth() + var9;
               var1.addEquality(var2.mLeft.mSolverVariable, var9);
               var1.addEquality(var2.mRight.mSolverVariable, var10);
               var2.mHorizontalResolution = 2;
               var2.setHorizontalDimension(var9, var10);
            } else if(var2.mRight.mTarget != null && var2.mRight.mTarget.mOwner.mHorizontalResolution == 2) {
               var8 = var2.mRight.mTarget.mSolverVariable;
               var2.mLeft.mSolverVariable = var1.createObjectVariable(var2.mLeft);
               var2.mRight.mSolverVariable = var1.createObjectVariable(var2.mRight);
               var9 = (int)(var8.computedValue - (float)var2.mRight.getMargin() + 0.5F);
               var10 = var9 - var2.getWidth();
               var1.addEquality(var2.mLeft.mSolverVariable, var10);
               var1.addEquality(var2.mRight.mSolverVariable, var9);
               var2.mHorizontalResolution = 2;
               var2.setHorizontalDimension(var10, var9);
            } else {
               boolean var4;
               if(var2.mLeft.mTarget != null) {
                  var4 = true;
               } else {
                  var4 = false;
               }

               boolean var5;
               if(var2.mRight.mTarget != null) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               if(!var4 && !var5) {
                  if(var2 instanceof Guideline) {
                     Guideline var7 = (Guideline)var2;
                     if(var7.getOrientation() == 1) {
                        var2.mLeft.mSolverVariable = var1.createObjectVariable(var2.mLeft);
                        var2.mRight.mSolverVariable = var1.createObjectVariable(var2.mRight);
                        float var3;
                        if(var7.getRelativeBegin() != -1) {
                           var3 = (float)var7.getRelativeBegin();
                        } else if(var7.getRelativeEnd() != -1) {
                           var3 = (float)(var0.getWidth() - var7.getRelativeEnd());
                        } else {
                           var3 = (float)var0.getWidth();
                           var3 = var7.getRelativePercent() * var3;
                        }

                        var9 = (int)(var3 + 0.5F);
                        var1.addEquality(var2.mLeft.mSolverVariable, var9);
                        var1.addEquality(var2.mRight.mSolverVariable, var9);
                        var2.mHorizontalResolution = 2;
                        var2.mVerticalResolution = 2;
                        var2.setHorizontalDimension(var9, var9);
                        var2.setVerticalDimension(0, var0.getHeight());
                        return;
                     }
                  } else {
                     var2.mLeft.mSolverVariable = var1.createObjectVariable(var2.mLeft);
                     var2.mRight.mSolverVariable = var1.createObjectVariable(var2.mRight);
                     var9 = var2.getX();
                     var10 = var2.getWidth();
                     var1.addEquality(var2.mLeft.mSolverVariable, var9);
                     var1.addEquality(var2.mRight.mSolverVariable, var10 + var9);
                     var2.mHorizontalResolution = 2;
                  }
               }

            }
         }
      }
   }

   static void checkMatchParent(ConstraintWidgetContainer var0, LinearSystem var1, ConstraintWidget var2) {
      int var3;
      int var4;
      if(var0.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && var2.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
         var2.mLeft.mSolverVariable = var1.createObjectVariable(var2.mLeft);
         var2.mRight.mSolverVariable = var1.createObjectVariable(var2.mRight);
         var3 = var2.mLeft.mMargin;
         var4 = var0.getWidth() - var2.mRight.mMargin;
         var1.addEquality(var2.mLeft.mSolverVariable, var3);
         var1.addEquality(var2.mRight.mSolverVariable, var4);
         var2.setHorizontalDimension(var3, var4);
         var2.mHorizontalResolution = 2;
      }

      if(var0.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && var2.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
         var2.mTop.mSolverVariable = var1.createObjectVariable(var2.mTop);
         var2.mBottom.mSolverVariable = var1.createObjectVariable(var2.mBottom);
         var3 = var2.mTop.mMargin;
         var4 = var0.getHeight() - var2.mBottom.mMargin;
         var1.addEquality(var2.mTop.mSolverVariable, var3);
         var1.addEquality(var2.mBottom.mSolverVariable, var4);
         if(var2.mBaselineDistance > 0 || var2.getVisibility() == 8) {
            var2.mBaseline.mSolverVariable = var1.createObjectVariable(var2.mBaseline);
            var1.addEquality(var2.mBaseline.mSolverVariable, var2.mBaselineDistance + var3);
         }

         var2.setVerticalDimension(var3, var4);
         var2.mVerticalResolution = 2;
      }

   }

   static void checkVerticalSimpleDependency(ConstraintWidgetContainer var0, LinearSystem var1, ConstraintWidget var2) {
      ConstraintWidget.DimensionBehaviour var8 = var2.mVerticalDimensionBehaviour;
      ConstraintWidget.DimensionBehaviour var9 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
      boolean var6 = true;
      if(var8 == var9) {
         var2.mVerticalResolution = 1;
      } else {
         int var11;
         int var12;
         if(var0.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && var2.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            var2.mTop.mSolverVariable = var1.createObjectVariable(var2.mTop);
            var2.mBottom.mSolverVariable = var1.createObjectVariable(var2.mBottom);
            var11 = var2.mTop.mMargin;
            var12 = var0.getHeight() - var2.mBottom.mMargin;
            var1.addEquality(var2.mTop.mSolverVariable, var11);
            var1.addEquality(var2.mBottom.mSolverVariable, var12);
            if(var2.mBaselineDistance > 0 || var2.getVisibility() == 8) {
               var2.mBaseline.mSolverVariable = var1.createObjectVariable(var2.mBaseline);
               var1.addEquality(var2.mBaseline.mSolverVariable, var2.mBaselineDistance + var11);
            }

            var2.setVerticalDimension(var11, var12);
            var2.mVerticalResolution = 2;
         } else if(var2.mTop.mTarget != null && var2.mBottom.mTarget != null) {
            if(var2.mTop.mTarget.mOwner == var0 && var2.mBottom.mTarget.mOwner == var0) {
               var11 = var2.mTop.getMargin();
               var12 = var2.mBottom.getMargin();
               if(var0.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                  var12 = var2.getHeight() + var11;
               } else {
                  int var13 = var2.getHeight();
                  int var7 = var0.getHeight();
                  var11 = (int)((float)var11 + (float)(var7 - var11 - var12 - var13) * var2.mVerticalBiasPercent + 0.5F);
                  var12 = var2.getHeight() + var11;
               }

               var2.mTop.mSolverVariable = var1.createObjectVariable(var2.mTop);
               var2.mBottom.mSolverVariable = var1.createObjectVariable(var2.mBottom);
               var1.addEquality(var2.mTop.mSolverVariable, var11);
               var1.addEquality(var2.mBottom.mSolverVariable, var12);
               if(var2.mBaselineDistance > 0 || var2.getVisibility() == 8) {
                  var2.mBaseline.mSolverVariable = var1.createObjectVariable(var2.mBaseline);
                  var1.addEquality(var2.mBaseline.mSolverVariable, var2.mBaselineDistance + var11);
               }

               var2.mVerticalResolution = 2;
               var2.setVerticalDimension(var11, var12);
            } else {
               var2.mVerticalResolution = 1;
            }
         } else if(var2.mTop.mTarget != null && var2.mTop.mTarget.mOwner == var0) {
            var11 = var2.mTop.getMargin();
            var12 = var2.getHeight() + var11;
            var2.mTop.mSolverVariable = var1.createObjectVariable(var2.mTop);
            var2.mBottom.mSolverVariable = var1.createObjectVariable(var2.mBottom);
            var1.addEquality(var2.mTop.mSolverVariable, var11);
            var1.addEquality(var2.mBottom.mSolverVariable, var12);
            if(var2.mBaselineDistance > 0 || var2.getVisibility() == 8) {
               var2.mBaseline.mSolverVariable = var1.createObjectVariable(var2.mBaseline);
               var1.addEquality(var2.mBaseline.mSolverVariable, var2.mBaselineDistance + var11);
            }

            var2.mVerticalResolution = 2;
            var2.setVerticalDimension(var11, var12);
         } else if(var2.mBottom.mTarget != null && var2.mBottom.mTarget.mOwner == var0) {
            var2.mTop.mSolverVariable = var1.createObjectVariable(var2.mTop);
            var2.mBottom.mSolverVariable = var1.createObjectVariable(var2.mBottom);
            var11 = var0.getHeight() - var2.mBottom.getMargin();
            var12 = var11 - var2.getHeight();
            var1.addEquality(var2.mTop.mSolverVariable, var12);
            var1.addEquality(var2.mBottom.mSolverVariable, var11);
            if(var2.mBaselineDistance > 0 || var2.getVisibility() == 8) {
               var2.mBaseline.mSolverVariable = var1.createObjectVariable(var2.mBaseline);
               var1.addEquality(var2.mBaseline.mSolverVariable, var2.mBaselineDistance + var12);
            }

            var2.mVerticalResolution = 2;
            var2.setVerticalDimension(var12, var11);
         } else {
            SolverVariable var10;
            if(var2.mTop.mTarget != null && var2.mTop.mTarget.mOwner.mVerticalResolution == 2) {
               var10 = var2.mTop.mTarget.mSolverVariable;
               var2.mTop.mSolverVariable = var1.createObjectVariable(var2.mTop);
               var2.mBottom.mSolverVariable = var1.createObjectVariable(var2.mBottom);
               var11 = (int)(var10.computedValue + (float)var2.mTop.getMargin() + 0.5F);
               var12 = var2.getHeight() + var11;
               var1.addEquality(var2.mTop.mSolverVariable, var11);
               var1.addEquality(var2.mBottom.mSolverVariable, var12);
               if(var2.mBaselineDistance > 0 || var2.getVisibility() == 8) {
                  var2.mBaseline.mSolverVariable = var1.createObjectVariable(var2.mBaseline);
                  var1.addEquality(var2.mBaseline.mSolverVariable, var2.mBaselineDistance + var11);
               }

               var2.mVerticalResolution = 2;
               var2.setVerticalDimension(var11, var12);
            } else if(var2.mBottom.mTarget != null && var2.mBottom.mTarget.mOwner.mVerticalResolution == 2) {
               var10 = var2.mBottom.mTarget.mSolverVariable;
               var2.mTop.mSolverVariable = var1.createObjectVariable(var2.mTop);
               var2.mBottom.mSolverVariable = var1.createObjectVariable(var2.mBottom);
               var11 = (int)(var10.computedValue - (float)var2.mBottom.getMargin() + 0.5F);
               var12 = var11 - var2.getHeight();
               var1.addEquality(var2.mTop.mSolverVariable, var12);
               var1.addEquality(var2.mBottom.mSolverVariable, var11);
               if(var2.mBaselineDistance > 0 || var2.getVisibility() == 8) {
                  var2.mBaseline.mSolverVariable = var1.createObjectVariable(var2.mBaseline);
                  var1.addEquality(var2.mBaseline.mSolverVariable, var2.mBaselineDistance + var12);
               }

               var2.mVerticalResolution = 2;
               var2.setVerticalDimension(var12, var11);
            } else if(var2.mBaseline.mTarget != null && var2.mBaseline.mTarget.mOwner.mVerticalResolution == 2) {
               var10 = var2.mBaseline.mTarget.mSolverVariable;
               var2.mTop.mSolverVariable = var1.createObjectVariable(var2.mTop);
               var2.mBottom.mSolverVariable = var1.createObjectVariable(var2.mBottom);
               var11 = (int)(var10.computedValue - (float)var2.mBaselineDistance + 0.5F);
               var12 = var2.getHeight() + var11;
               var1.addEquality(var2.mTop.mSolverVariable, var11);
               var1.addEquality(var2.mBottom.mSolverVariable, var12);
               var2.mBaseline.mSolverVariable = var1.createObjectVariable(var2.mBaseline);
               var1.addEquality(var2.mBaseline.mSolverVariable, var2.mBaselineDistance + var11);
               var2.mVerticalResolution = 2;
               var2.setVerticalDimension(var11, var12);
            } else {
               boolean var4;
               if(var2.mBaseline.mTarget != null) {
                  var4 = true;
               } else {
                  var4 = false;
               }

               boolean var5;
               if(var2.mTop.mTarget != null) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               if(var2.mBottom.mTarget == null) {
                  var6 = false;
               }

               if(!var4 && !var5 && !var6) {
                  if(var2 instanceof Guideline) {
                     Guideline var14 = (Guideline)var2;
                     if(var14.getOrientation() == 0) {
                        var2.mTop.mSolverVariable = var1.createObjectVariable(var2.mTop);
                        var2.mBottom.mSolverVariable = var1.createObjectVariable(var2.mBottom);
                        float var3;
                        if(var14.getRelativeBegin() != -1) {
                           var3 = (float)var14.getRelativeBegin();
                        } else if(var14.getRelativeEnd() != -1) {
                           var3 = (float)(var0.getHeight() - var14.getRelativeEnd());
                        } else {
                           var3 = (float)var0.getHeight();
                           var3 = var14.getRelativePercent() * var3;
                        }

                        var11 = (int)(var3 + 0.5F);
                        var1.addEquality(var2.mTop.mSolverVariable, var11);
                        var1.addEquality(var2.mBottom.mSolverVariable, var11);
                        var2.mVerticalResolution = 2;
                        var2.mHorizontalResolution = 2;
                        var2.setVerticalDimension(var11, var11);
                        var2.setHorizontalDimension(0, var0.getWidth());
                        return;
                     }
                  } else {
                     var2.mTop.mSolverVariable = var1.createObjectVariable(var2.mTop);
                     var2.mBottom.mSolverVariable = var1.createObjectVariable(var2.mBottom);
                     var11 = var2.getY();
                     var12 = var2.getHeight();
                     var1.addEquality(var2.mTop.mSolverVariable, var11);
                     var1.addEquality(var2.mBottom.mSolverVariable, var12 + var11);
                     if(var2.mBaselineDistance > 0 || var2.getVisibility() == 8) {
                        var2.mBaseline.mSolverVariable = var1.createObjectVariable(var2.mBaseline);
                        var1.addEquality(var2.mBaseline.mSolverVariable, var11 + var2.mBaselineDistance);
                     }

                     var2.mVerticalResolution = 2;
                  }
               }

            }
         }
      }
   }
}
