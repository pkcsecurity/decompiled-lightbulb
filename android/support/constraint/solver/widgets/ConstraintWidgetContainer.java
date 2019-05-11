package android.support.constraint.solver.widgets;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.Guideline;
import android.support.constraint.solver.widgets.Optimizer;
import android.support.constraint.solver.widgets.Rectangle;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.constraint.solver.widgets.WidgetContainer;
import java.util.ArrayList;
import java.util.Arrays;

public class ConstraintWidgetContainer extends WidgetContainer {

   static boolean ALLOW_ROOT_GROUP;
   private static final int CHAIN_FIRST = 0;
   private static final int CHAIN_FIRST_VISIBLE = 2;
   private static final int CHAIN_LAST = 1;
   private static final int CHAIN_LAST_VISIBLE = 3;
   private static final boolean DEBUG = false;
   private static final boolean DEBUG_LAYOUT = false;
   private static final boolean DEBUG_OPTIMIZE = false;
   private static final int FLAG_CHAIN_DANGLING = 1;
   private static final int FLAG_CHAIN_OPTIMIZE = 0;
   private static final int FLAG_RECOMPUTE_BOUNDS = 2;
   private static final int MAX_ITERATIONS = 8;
   public static final int OPTIMIZATION_ALL = 2;
   public static final int OPTIMIZATION_BASIC = 4;
   public static final int OPTIMIZATION_CHAIN = 8;
   public static final int OPTIMIZATION_NONE = 1;
   private static final boolean USE_SNAPSHOT = true;
   private static final boolean USE_THREAD = false;
   private boolean[] flags = new boolean[3];
   protected LinearSystem mBackgroundSystem = null;
   private ConstraintWidget[] mChainEnds = new ConstraintWidget[4];
   private boolean mHeightMeasuredTooSmall = false;
   private ConstraintWidget[] mHorizontalChainsArray = new ConstraintWidget[4];
   private int mHorizontalChainsSize = 0;
   private ConstraintWidget[] mMatchConstraintsChainedWidgets = new ConstraintWidget[4];
   private int mOptimizationLevel = 2;
   int mPaddingBottom;
   int mPaddingLeft;
   int mPaddingRight;
   int mPaddingTop;
   private Snapshot mSnapshot;
   protected LinearSystem mSystem = new LinearSystem();
   private ConstraintWidget[] mVerticalChainsArray = new ConstraintWidget[4];
   private int mVerticalChainsSize = 0;
   private boolean mWidthMeasuredTooSmall = false;
   int mWrapHeight;
   int mWrapWidth;


   public ConstraintWidgetContainer() {}

   public ConstraintWidgetContainer(int var1, int var2) {
      super(var1, var2);
   }

   public ConstraintWidgetContainer(int var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4);
   }

   private void addHorizontalChain(ConstraintWidget var1) {
      for(int var2 = 0; var2 < this.mHorizontalChainsSize; ++var2) {
         if(this.mHorizontalChainsArray[var2] == var1) {
            return;
         }
      }

      if(this.mHorizontalChainsSize + 1 >= this.mHorizontalChainsArray.length) {
         this.mHorizontalChainsArray = (ConstraintWidget[])Arrays.copyOf(this.mHorizontalChainsArray, this.mHorizontalChainsArray.length * 2);
      }

      this.mHorizontalChainsArray[this.mHorizontalChainsSize] = var1;
      ++this.mHorizontalChainsSize;
   }

   private void addVerticalChain(ConstraintWidget var1) {
      for(int var2 = 0; var2 < this.mVerticalChainsSize; ++var2) {
         if(this.mVerticalChainsArray[var2] == var1) {
            return;
         }
      }

      if(this.mVerticalChainsSize + 1 >= this.mVerticalChainsArray.length) {
         this.mVerticalChainsArray = (ConstraintWidget[])Arrays.copyOf(this.mVerticalChainsArray, this.mVerticalChainsArray.length * 2);
      }

      this.mVerticalChainsArray[this.mVerticalChainsSize] = var1;
      ++this.mVerticalChainsSize;
   }

   private void applyHorizontalChain(LinearSystem var1) {
      LinearSystem var9 = var1;
      int var3 = 0;

      while(true) {
         ConstraintWidgetContainer var12 = this;
         if(var3 >= this.mHorizontalChainsSize) {
            return;
         }

         int var4;
         LinearSystem var34;
         label298: {
            ConstraintWidget var15 = this.mHorizontalChainsArray[var3];
            int var7 = this.countMatchConstraintsChainedWidgets(var9, this.mChainEnds, this.mHorizontalChainsArray[var3], 0, this.flags);
            ConstraintWidget var10 = this.mChainEnds[2];
            if(var10 != null) {
               ConstraintWidget var11;
               if(this.flags[1]) {
                  for(var4 = var15.getDrawX(); var10 != null; var10 = var11) {
                     var9.addEquality(var10.mLeft.mSolverVariable, var4);
                     var11 = var10.mHorizontalNextWidget;
                     var4 += var10.mLeft.getMargin() + var10.getWidth() + var10.mRight.getMargin();
                  }
               } else {
                  boolean var20;
                  if(var15.mHorizontalChainStyle == 0) {
                     var20 = true;
                  } else {
                     var20 = false;
                  }

                  boolean var6;
                  if(var15.mHorizontalChainStyle == 2) {
                     var6 = true;
                  } else {
                     var6 = false;
                  }

                  boolean var5;
                  if(this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                     var5 = true;
                  } else {
                     var5 = false;
                  }

                  if((this.mOptimizationLevel == 2 || this.mOptimizationLevel == 8) && this.flags[0] && var15.mHorizontalChainFixedPosition && !var6 && !var5 && var15.mHorizontalChainStyle == 0) {
                     Optimizer.applyDirectResolutionHorizontalChain(this, var9, var7, var15);
                  } else {
                     int var8;
                     ConstraintWidget var13;
                     ConstraintWidget var14;
                     int var21;
                     int var23;
                     SolverVariable var28;
                     if(var7 == 0 || var6) {
                        ConstraintWidget var29 = var10;
                        var13 = null;
                        var14 = var13;
                        var5 = false;
                        var34 = var9;
                        boolean var24 = var20;
                        ConstraintWidget var26 = var13;

                        while(true) {
                           ConstraintWidget var40 = var29;
                           ConstraintAnchor var35;
                           if(var29 == null) {
                              LinearSystem var38 = var34;
                              var34 = var34;
                              var4 = var3;
                              if(var6) {
                                 var35 = var10.mLeft;
                                 ConstraintAnchor var41 = var26.mRight;
                                 var21 = var35.getMargin();
                                 var23 = var41.getMargin();
                                 if(var15.mLeft.mTarget != null) {
                                    var28 = var15.mLeft.mTarget.mSolverVariable;
                                 } else {
                                    var28 = null;
                                 }

                                 SolverVariable var32;
                                 if(var26.mRight.mTarget != null) {
                                    var32 = var26.mRight.mTarget.mSolverVariable;
                                 } else {
                                    var32 = null;
                                 }

                                 var34 = var38;
                                 var4 = var3;
                                 if(var28 != null) {
                                    var34 = var38;
                                    var4 = var3;
                                    if(var32 != null) {
                                       var38.addLowerThan(var41.mSolverVariable, var32, -var23, 1);
                                       var38.addCentering(var35.mSolverVariable, var28, var21, var15.mHorizontalBiasPercent, var32, var41.mSolverVariable, var23, 4);
                                       var4 = var3;
                                       var34 = var38;
                                    }
                                 }
                              }
                              break label298;
                           }

                           ConstraintWidget var42 = var29.mHorizontalNextWidget;
                           if(var42 == null) {
                              var29 = this.mChainEnds[1];
                              var20 = true;
                           } else {
                              var20 = var5;
                              var29 = var26;
                           }

                           label261: {
                              if(var6) {
                                 ConstraintAnchor var27 = var40.mLeft;
                                 var8 = var27.getMargin();
                                 var21 = var8;
                                 if(var14 != null) {
                                    var21 = var8 + var14.mRight.getMargin();
                                 }

                                 byte var25;
                                 if(var10 != var40) {
                                    var25 = 3;
                                 } else {
                                    var25 = 1;
                                 }

                                 var34.addGreaterThan(var27.mSolverVariable, var27.mTarget.mSolverVariable, var21, var25);
                                 if(var40.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                    var35 = var40.mRight;
                                    if(var40.mMatchConstraintDefaultWidth == 1) {
                                       var21 = Math.max(var40.mMatchConstraintMinWidth, var40.getWidth());
                                       var34.addEquality(var35.mSolverVariable, var27.mSolverVariable, var21, 3);
                                    } else {
                                       var34.addGreaterThan(var27.mSolverVariable, var27.mTarget.mSolverVariable, var27.mMargin, 3);
                                       var34.addLowerThan(var35.mSolverVariable, var27.mSolverVariable, var40.mMatchConstraintMinWidth, 3);
                                    }
                                 }
                              } else if(!var24 && var20 && var14 != null) {
                                 if(var40.mRight.mTarget == null) {
                                    var34.addEquality(var40.mRight.mSolverVariable, var40.getDrawRight());
                                 } else {
                                    var21 = var40.mRight.getMargin();
                                    var34.addEquality(var40.mRight.mSolverVariable, var29.mRight.mTarget.mSolverVariable, -var21, 5);
                                 }
                              } else {
                                 if(var24 || var20 || var14 != null) {
                                    ConstraintAnchor var43 = var40.mLeft;
                                    ConstraintAnchor var19 = var40.mRight;
                                    var21 = var43.getMargin();
                                    var8 = var19.getMargin();
                                    var34.addGreaterThan(var43.mSolverVariable, var43.mTarget.mSolverVariable, var21, 1);
                                    var34.addLowerThan(var19.mSolverVariable, var19.mTarget.mSolverVariable, -var8, 1);
                                    SolverVariable var36;
                                    if(var43.mTarget != null) {
                                       var36 = var43.mTarget.mSolverVariable;
                                    } else {
                                       var36 = null;
                                    }

                                    if(var14 == null) {
                                       if(var15.mLeft.mTarget != null) {
                                          var36 = var15.mLeft.mTarget.mSolverVariable;
                                       } else {
                                          var36 = null;
                                       }
                                    }

                                    var26 = var42;
                                    if(var42 == null) {
                                       if(var29.mRight.mTarget != null) {
                                          var26 = var29.mRight.mTarget.mOwner;
                                       } else {
                                          var26 = null;
                                       }
                                    }

                                    if(var26 != null) {
                                       SolverVariable var37 = var26.mLeft.mSolverVariable;
                                       if(var20) {
                                          if(var29.mRight.mTarget != null) {
                                             var37 = var29.mRight.mTarget.mSolverVariable;
                                          } else {
                                             var37 = null;
                                          }
                                       }

                                       if(var36 != null && var37 != null) {
                                          var34.addCentering(var43.mSolverVariable, var36, var21, 0.5F, var37, var19.mSolverVariable, var8, 4);
                                       }
                                    }
                                    break label261;
                                 }

                                 if(var40.mLeft.mTarget == null) {
                                    var34.addEquality(var40.mLeft.mSolverVariable, var40.getDrawX());
                                 } else {
                                    var21 = var40.mLeft.getMargin();
                                    var34.addEquality(var40.mLeft.mSolverVariable, var15.mLeft.mTarget.mSolverVariable, var21, 5);
                                 }
                              }

                              var26 = var42;
                           }

                           if(var20) {
                              var26 = null;
                           }

                           LinearSystem var39 = var34;
                           var11 = var29;
                           var14 = var40;
                           var29 = var26;
                           var26 = var11;
                           var5 = var20;
                           var34 = var39;
                        }
                     }

                     var11 = null;

                     float var2;
                     for(var2 = 0.0F; var10 != null; var10 = var13) {
                        if(var10.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                           var21 = var10.mLeft.getMargin();
                           var4 = var21;
                           if(var11 != null) {
                              var4 = var21 + var11.mRight.getMargin();
                           }

                           byte var22;
                           if(var10.mLeft.mTarget.mOwner.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                              var22 = 2;
                           } else {
                              var22 = 3;
                           }

                           var9.addGreaterThan(var10.mLeft.mSolverVariable, var10.mLeft.mTarget.mSolverVariable, var4, var22);
                           var21 = var10.mRight.getMargin();
                           var4 = var21;
                           if(var10.mRight.mTarget.mOwner.mLeft.mTarget != null) {
                              var4 = var21;
                              if(var10.mRight.mTarget.mOwner.mLeft.mTarget.mOwner == var10) {
                                 var4 = var21 + var10.mRight.mTarget.mOwner.mLeft.getMargin();
                              }
                           }

                           if(var10.mRight.mTarget.mOwner.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                              var22 = 2;
                           } else {
                              var22 = 3;
                           }

                           var9.addLowerThan(var10.mRight.mSolverVariable, var10.mRight.mTarget.mSolverVariable, -var4, var22);
                        } else {
                           var2 += var10.mHorizontalWeight;
                           if(var10.mRight.mTarget != null) {
                              var21 = var10.mRight.getMargin();
                              var4 = var21;
                              if(var10 != var12.mChainEnds[3]) {
                                 var4 = var21 + var10.mRight.mTarget.mOwner.mLeft.getMargin();
                              }
                           } else {
                              var4 = 0;
                           }

                           var9.addGreaterThan(var10.mRight.mSolverVariable, var10.mLeft.mSolverVariable, 0, 1);
                           var9.addLowerThan(var10.mRight.mSolverVariable, var10.mRight.mTarget.mSolverVariable, -var4, 1);
                        }

                        var13 = var10.mHorizontalNextWidget;
                        var11 = var10;
                     }

                     if(var7 == 1) {
                        var11 = var12.mMatchConstraintsChainedWidgets[0];
                        var21 = var11.mLeft.getMargin();
                        var4 = var21;
                        if(var11.mLeft.mTarget != null) {
                           var4 = var21 + var11.mLeft.mTarget.getMargin();
                        }

                        var23 = var11.mRight.getMargin();
                        var21 = var23;
                        if(var11.mRight.mTarget != null) {
                           var21 = var23 + var11.mRight.mTarget.getMargin();
                        }

                        var28 = var15.mRight.mTarget.mSolverVariable;
                        if(var11 == var12.mChainEnds[3]) {
                           var28 = var12.mChainEnds[1].mRight.mTarget.mSolverVariable;
                        }

                        if(var11.mMatchConstraintDefaultWidth == 1) {
                           var9.addGreaterThan(var15.mLeft.mSolverVariable, var15.mLeft.mTarget.mSolverVariable, var4, 1);
                           var9.addLowerThan(var15.mRight.mSolverVariable, var28, -var21, 1);
                           var9.addEquality(var15.mRight.mSolverVariable, var15.mLeft.mSolverVariable, var15.getWidth(), 2);
                        } else {
                           var9.addEquality(var11.mLeft.mSolverVariable, var11.mLeft.mTarget.mSolverVariable, var4, 1);
                           var9.addEquality(var11.mRight.mSolverVariable, var28, -var21, 1);
                        }
                     } else {
                        var21 = 0;
                        var4 = var7;

                        while(true) {
                           var8 = var4 - 1;
                           if(var21 >= var8) {
                              break;
                           }

                           var13 = var12.mMatchConstraintsChainedWidgets[var21];
                           ConstraintWidget[] var30 = var12.mMatchConstraintsChainedWidgets;
                           var23 = var21 + 1;
                           var14 = var30[var23];
                           SolverVariable var16 = var13.mLeft.mSolverVariable;
                           SolverVariable var17 = var13.mRight.mSolverVariable;
                           SolverVariable var18 = var14.mLeft.mSolverVariable;
                           var28 = var14.mRight.mSolverVariable;
                           if(var14 == var12.mChainEnds[3]) {
                              var28 = var12.mChainEnds[1].mRight.mSolverVariable;
                           }

                           var7 = var13.mLeft.getMargin();
                           var21 = var7;
                           if(var13.mLeft.mTarget != null) {
                              var21 = var7;
                              if(var13.mLeft.mTarget.mOwner.mRight.mTarget != null) {
                                 var21 = var7;
                                 if(var13.mLeft.mTarget.mOwner.mRight.mTarget.mOwner == var13) {
                                    var21 = var7 + var13.mLeft.mTarget.mOwner.mRight.getMargin();
                                 }
                              }
                           }

                           var9.addGreaterThan(var16, var13.mLeft.mTarget.mSolverVariable, var21, 2);
                           var7 = var13.mRight.getMargin();
                           var21 = var7;
                           if(var13.mRight.mTarget != null) {
                              var21 = var7;
                              if(var13.mHorizontalNextWidget != null) {
                                 if(var13.mHorizontalNextWidget.mLeft.mTarget != null) {
                                    var21 = var13.mHorizontalNextWidget.mLeft.getMargin();
                                 } else {
                                    var21 = 0;
                                 }

                                 var21 += var7;
                              }
                           }

                           var9.addLowerThan(var17, var13.mRight.mTarget.mSolverVariable, -var21, 2);
                           if(var23 == var8) {
                              var7 = var14.mLeft.getMargin();
                              var21 = var7;
                              if(var14.mLeft.mTarget != null) {
                                 var21 = var7;
                                 if(var14.mLeft.mTarget.mOwner.mRight.mTarget != null) {
                                    var21 = var7;
                                    if(var14.mLeft.mTarget.mOwner.mRight.mTarget.mOwner == var14) {
                                       var21 = var7 + var14.mLeft.mTarget.mOwner.mRight.getMargin();
                                    }
                                 }
                              }

                              var9.addGreaterThan(var18, var14.mLeft.mTarget.mSolverVariable, var21, 2);
                              ConstraintAnchor var31 = var14.mRight;
                              if(var14 == var12.mChainEnds[3]) {
                                 var31 = var12.mChainEnds[1].mRight;
                              }

                              var7 = var31.getMargin();
                              var21 = var7;
                              if(var31.mTarget != null) {
                                 var21 = var7;
                                 if(var31.mTarget.mOwner.mLeft.mTarget != null) {
                                    var21 = var7;
                                    if(var31.mTarget.mOwner.mLeft.mTarget.mOwner == var14) {
                                       var21 = var7 + var31.mTarget.mOwner.mLeft.getMargin();
                                    }
                                 }
                              }

                              var9.addLowerThan(var28, var31.mTarget.mSolverVariable, -var21, 2);
                           }

                           if(var15.mMatchConstraintMaxWidth > 0) {
                              var9.addLowerThan(var17, var16, var15.mMatchConstraintMaxWidth, 2);
                           }

                           ArrayRow var33 = var1.createRow();
                           var33.createRowEqualDimension(var13.mHorizontalWeight, var2, var14.mHorizontalWeight, var16, var13.mLeft.getMargin(), var17, var13.mRight.getMargin(), var18, var14.mLeft.getMargin(), var28, var14.mRight.getMargin());
                           var9.addConstraint(var33);
                           var21 = var23;
                        }
                     }
                  }
               }
            }

            var34 = var9;
            var4 = var3;
         }

         var3 = var4 + 1;
         var9 = var34;
      }
   }

   private void applyVerticalChain(LinearSystem var1) {
      LinearSystem var9 = var1;
      int var3 = 0;

      while(true) {
         ConstraintWidgetContainer var12 = this;
         if(var3 >= this.mVerticalChainsSize) {
            return;
         }

         int var4;
         LinearSystem var34;
         label313: {
            ConstraintWidget var15 = this.mVerticalChainsArray[var3];
            int var7 = this.countMatchConstraintsChainedWidgets(var9, this.mChainEnds, this.mVerticalChainsArray[var3], 1, this.flags);
            ConstraintWidget var10 = this.mChainEnds[2];
            if(var10 != null) {
               ConstraintWidget var11;
               if(this.flags[1]) {
                  for(var4 = var15.getDrawY(); var10 != null; var10 = var11) {
                     var9.addEquality(var10.mTop.mSolverVariable, var4);
                     var11 = var10.mVerticalNextWidget;
                     var4 += var10.mTop.getMargin() + var10.getHeight() + var10.mBottom.getMargin();
                  }
               } else {
                  boolean var20;
                  if(var15.mVerticalChainStyle == 0) {
                     var20 = true;
                  } else {
                     var20 = false;
                  }

                  boolean var6;
                  if(var15.mVerticalChainStyle == 2) {
                     var6 = true;
                  } else {
                     var6 = false;
                  }

                  boolean var5;
                  if(this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                     var5 = true;
                  } else {
                     var5 = false;
                  }

                  if((this.mOptimizationLevel == 2 || this.mOptimizationLevel == 8) && this.flags[0] && var15.mVerticalChainFixedPosition && !var6 && !var5 && var15.mVerticalChainStyle == 0) {
                     Optimizer.applyDirectResolutionVerticalChain(this, var9, var7, var15);
                  } else {
                     int var8;
                     ConstraintWidget var13;
                     ConstraintWidget var14;
                     int var21;
                     int var23;
                     SolverVariable var28;
                     if(var7 == 0 || var6) {
                        ConstraintWidget var30 = var10;
                        var13 = null;
                        ConstraintWidget var41 = var13;
                        var5 = false;
                        var34 = var9;
                        boolean var24 = var20;
                        ConstraintWidget var26 = var13;

                        while(true) {
                           ConstraintWidget var39 = var30;
                           SolverVariable var27;
                           if(var30 == null) {
                              LinearSystem var37 = var34;
                              var34 = var34;
                              var4 = var3;
                              if(var6) {
                                 ConstraintAnchor var42 = var10.mTop;
                                 ConstraintAnchor var38 = var26.mBottom;
                                 var21 = var42.getMargin();
                                 var23 = var38.getMargin();
                                 if(var15.mTop.mTarget != null) {
                                    var28 = var15.mTop.mTarget.mSolverVariable;
                                 } else {
                                    var28 = null;
                                 }

                                 if(var26.mBottom.mTarget != null) {
                                    var27 = var26.mBottom.mTarget.mSolverVariable;
                                 } else {
                                    var27 = null;
                                 }

                                 var34 = var37;
                                 var4 = var3;
                                 if(var28 != null) {
                                    var34 = var37;
                                    var4 = var3;
                                    if(var27 != null) {
                                       var37.addLowerThan(var38.mSolverVariable, var27, -var23, 1);
                                       var37.addCentering(var42.mSolverVariable, var28, var21, var15.mVerticalBiasPercent, var27, var38.mSolverVariable, var23, 4);
                                       var4 = var3;
                                       var34 = var37;
                                    }
                                 }
                              }
                              break label313;
                           }

                           var14 = var30.mVerticalNextWidget;
                           if(var14 == null) {
                              var30 = this.mChainEnds[1];
                              var20 = true;
                           } else {
                              var20 = var5;
                              var30 = var26;
                           }

                           label275: {
                              SolverVariable var35;
                              ConstraintAnchor var43;
                              if(var6) {
                                 var43 = var39.mTop;
                                 var8 = var43.getMargin();
                                 var21 = var8;
                                 if(var41 != null) {
                                    var21 = var8 + var41.mBottom.getMargin();
                                 }

                                 byte var25;
                                 if(var10 != var39) {
                                    var25 = 3;
                                 } else {
                                    var25 = 1;
                                 }

                                 if(var43.mTarget != null) {
                                    var27 = var43.mSolverVariable;
                                    var35 = var43.mTarget.mSolverVariable;
                                 } else if(var39.mBaseline.mTarget != null) {
                                    var27 = var39.mBaseline.mSolverVariable;
                                    var35 = var39.mBaseline.mTarget.mSolverVariable;
                                    var21 -= var43.getMargin();
                                 } else {
                                    var27 = null;
                                    var35 = var27;
                                 }

                                 if(var27 != null && var35 != null) {
                                    var34.addGreaterThan(var27, var35, var21, var25);
                                 }

                                 if(var39.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                                    ConstraintAnchor var29 = var39.mBottom;
                                    if(var39.mMatchConstraintDefaultHeight == 1) {
                                       var21 = Math.max(var39.mMatchConstraintMinHeight, var39.getHeight());
                                       var34.addEquality(var29.mSolverVariable, var43.mSolverVariable, var21, 3);
                                    } else {
                                       var34.addGreaterThan(var43.mSolverVariable, var43.mTarget.mSolverVariable, var43.mMargin, 3);
                                       var34.addLowerThan(var29.mSolverVariable, var43.mSolverVariable, var39.mMatchConstraintMinHeight, 3);
                                    }
                                 }
                              } else if(!var24 && var20 && var41 != null) {
                                 if(var39.mBottom.mTarget == null) {
                                    var34.addEquality(var39.mBottom.mSolverVariable, var39.getDrawBottom());
                                 } else {
                                    var21 = var39.mBottom.getMargin();
                                    var34.addEquality(var39.mBottom.mSolverVariable, var30.mBottom.mTarget.mSolverVariable, -var21, 5);
                                 }
                              } else {
                                 if(var24 || var20 || var41 != null) {
                                    var43 = var39.mTop;
                                    ConstraintAnchor var19 = var39.mBottom;
                                    var21 = var43.getMargin();
                                    var8 = var19.getMargin();
                                    var34.addGreaterThan(var43.mSolverVariable, var43.mTarget.mSolverVariable, var21, 1);
                                    var34.addLowerThan(var19.mSolverVariable, var19.mTarget.mSolverVariable, -var8, 1);
                                    if(var43.mTarget != null) {
                                       var35 = var43.mTarget.mSolverVariable;
                                    } else {
                                       var35 = null;
                                    }

                                    if(var41 == null) {
                                       if(var15.mTop.mTarget != null) {
                                          var35 = var15.mTop.mTarget.mSolverVariable;
                                       } else {
                                          var35 = null;
                                       }
                                    }

                                    var26 = var14;
                                    if(var14 == null) {
                                       if(var30.mBottom.mTarget != null) {
                                          var26 = var30.mBottom.mTarget.mOwner;
                                       } else {
                                          var26 = null;
                                       }
                                    }

                                    if(var26 != null) {
                                       SolverVariable var36 = var26.mTop.mSolverVariable;
                                       if(var20) {
                                          if(var30.mBottom.mTarget != null) {
                                             var36 = var30.mBottom.mTarget.mSolverVariable;
                                          } else {
                                             var36 = null;
                                          }
                                       }

                                       if(var35 != null && var36 != null) {
                                          var34.addCentering(var43.mSolverVariable, var35, var21, 0.5F, var36, var19.mSolverVariable, var8, 4);
                                       }
                                    }
                                    break label275;
                                 }

                                 if(var39.mTop.mTarget == null) {
                                    var34.addEquality(var39.mTop.mSolverVariable, var39.getDrawY());
                                 } else {
                                    var21 = var39.mTop.getMargin();
                                    var34.addEquality(var39.mTop.mSolverVariable, var15.mTop.mTarget.mSolverVariable, var21, 5);
                                 }
                              }

                              var26 = var14;
                           }

                           if(var20) {
                              var26 = null;
                           }

                           LinearSystem var40 = var34;
                           var11 = var30;
                           var30 = var26;
                           var26 = var11;
                           var41 = var39;
                           var5 = var20;
                           var34 = var40;
                        }
                     }

                     var11 = null;

                     float var2;
                     for(var2 = 0.0F; var10 != null; var10 = var13) {
                        if(var10.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                           var21 = var10.mTop.getMargin();
                           var4 = var21;
                           if(var11 != null) {
                              var4 = var21 + var11.mBottom.getMargin();
                           }

                           byte var22;
                           if(var10.mTop.mTarget.mOwner.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                              var22 = 2;
                           } else {
                              var22 = 3;
                           }

                           var9.addGreaterThan(var10.mTop.mSolverVariable, var10.mTop.mTarget.mSolverVariable, var4, var22);
                           var21 = var10.mBottom.getMargin();
                           var4 = var21;
                           if(var10.mBottom.mTarget.mOwner.mTop.mTarget != null) {
                              var4 = var21;
                              if(var10.mBottom.mTarget.mOwner.mTop.mTarget.mOwner == var10) {
                                 var4 = var21 + var10.mBottom.mTarget.mOwner.mTop.getMargin();
                              }
                           }

                           if(var10.mBottom.mTarget.mOwner.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                              var22 = 2;
                           } else {
                              var22 = 3;
                           }

                           var9.addLowerThan(var10.mBottom.mSolverVariable, var10.mBottom.mTarget.mSolverVariable, -var4, var22);
                        } else {
                           var2 += var10.mVerticalWeight;
                           if(var10.mBottom.mTarget != null) {
                              var21 = var10.mBottom.getMargin();
                              var4 = var21;
                              if(var10 != var12.mChainEnds[3]) {
                                 var4 = var21 + var10.mBottom.mTarget.mOwner.mTop.getMargin();
                              }
                           } else {
                              var4 = 0;
                           }

                           var9.addGreaterThan(var10.mBottom.mSolverVariable, var10.mTop.mSolverVariable, 0, 1);
                           var9.addLowerThan(var10.mBottom.mSolverVariable, var10.mBottom.mTarget.mSolverVariable, -var4, 1);
                        }

                        var13 = var10.mVerticalNextWidget;
                        var11 = var10;
                     }

                     if(var7 == 1) {
                        var11 = var12.mMatchConstraintsChainedWidgets[0];
                        var21 = var11.mTop.getMargin();
                        var4 = var21;
                        if(var11.mTop.mTarget != null) {
                           var4 = var21 + var11.mTop.mTarget.getMargin();
                        }

                        var23 = var11.mBottom.getMargin();
                        var21 = var23;
                        if(var11.mBottom.mTarget != null) {
                           var21 = var23 + var11.mBottom.mTarget.getMargin();
                        }

                        var28 = var15.mBottom.mTarget.mSolverVariable;
                        if(var11 == var12.mChainEnds[3]) {
                           var28 = var12.mChainEnds[1].mBottom.mTarget.mSolverVariable;
                        }

                        if(var11.mMatchConstraintDefaultHeight == 1) {
                           var9.addGreaterThan(var15.mTop.mSolverVariable, var15.mTop.mTarget.mSolverVariable, var4, 1);
                           var9.addLowerThan(var15.mBottom.mSolverVariable, var28, -var21, 1);
                           var9.addEquality(var15.mBottom.mSolverVariable, var15.mTop.mSolverVariable, var15.getHeight(), 2);
                        } else {
                           var9.addEquality(var11.mTop.mSolverVariable, var11.mTop.mTarget.mSolverVariable, var4, 1);
                           var9.addEquality(var11.mBottom.mSolverVariable, var28, -var21, 1);
                        }
                     } else {
                        var21 = 0;
                        var4 = var7;

                        while(true) {
                           var8 = var4 - 1;
                           if(var21 >= var8) {
                              break;
                           }

                           var13 = var12.mMatchConstraintsChainedWidgets[var21];
                           ConstraintWidget[] var31 = var12.mMatchConstraintsChainedWidgets;
                           var23 = var21 + 1;
                           var14 = var31[var23];
                           SolverVariable var16 = var13.mTop.mSolverVariable;
                           SolverVariable var17 = var13.mBottom.mSolverVariable;
                           SolverVariable var18 = var14.mTop.mSolverVariable;
                           var28 = var14.mBottom.mSolverVariable;
                           if(var14 == var12.mChainEnds[3]) {
                              var28 = var12.mChainEnds[1].mBottom.mSolverVariable;
                           }

                           var7 = var13.mTop.getMargin();
                           var21 = var7;
                           if(var13.mTop.mTarget != null) {
                              var21 = var7;
                              if(var13.mTop.mTarget.mOwner.mBottom.mTarget != null) {
                                 var21 = var7;
                                 if(var13.mTop.mTarget.mOwner.mBottom.mTarget.mOwner == var13) {
                                    var21 = var7 + var13.mTop.mTarget.mOwner.mBottom.getMargin();
                                 }
                              }
                           }

                           var9.addGreaterThan(var16, var13.mTop.mTarget.mSolverVariable, var21, 2);
                           var7 = var13.mBottom.getMargin();
                           var21 = var7;
                           if(var13.mBottom.mTarget != null) {
                              var21 = var7;
                              if(var13.mVerticalNextWidget != null) {
                                 if(var13.mVerticalNextWidget.mTop.mTarget != null) {
                                    var21 = var13.mVerticalNextWidget.mTop.getMargin();
                                 } else {
                                    var21 = 0;
                                 }

                                 var21 += var7;
                              }
                           }

                           var9.addLowerThan(var17, var13.mBottom.mTarget.mSolverVariable, -var21, 2);
                           if(var23 == var8) {
                              var7 = var14.mTop.getMargin();
                              var21 = var7;
                              if(var14.mTop.mTarget != null) {
                                 var21 = var7;
                                 if(var14.mTop.mTarget.mOwner.mBottom.mTarget != null) {
                                    var21 = var7;
                                    if(var14.mTop.mTarget.mOwner.mBottom.mTarget.mOwner == var14) {
                                       var21 = var7 + var14.mTop.mTarget.mOwner.mBottom.getMargin();
                                    }
                                 }
                              }

                              var9.addGreaterThan(var18, var14.mTop.mTarget.mSolverVariable, var21, 2);
                              ConstraintAnchor var32 = var14.mBottom;
                              if(var14 == var12.mChainEnds[3]) {
                                 var32 = var12.mChainEnds[1].mBottom;
                              }

                              var7 = var32.getMargin();
                              var21 = var7;
                              if(var32.mTarget != null) {
                                 var21 = var7;
                                 if(var32.mTarget.mOwner.mTop.mTarget != null) {
                                    var21 = var7;
                                    if(var32.mTarget.mOwner.mTop.mTarget.mOwner == var14) {
                                       var21 = var7 + var32.mTarget.mOwner.mTop.getMargin();
                                    }
                                 }
                              }

                              var9.addLowerThan(var28, var32.mTarget.mSolverVariable, -var21, 2);
                           }

                           if(var15.mMatchConstraintMaxHeight > 0) {
                              var9.addLowerThan(var17, var16, var15.mMatchConstraintMaxHeight, 2);
                           }

                           ArrayRow var33 = var1.createRow();
                           var33.createRowEqualDimension(var13.mVerticalWeight, var2, var14.mVerticalWeight, var16, var13.mTop.getMargin(), var17, var13.mBottom.getMargin(), var18, var14.mTop.getMargin(), var28, var14.mBottom.getMargin());
                           var9.addConstraint(var33);
                           var21 = var23;
                        }
                     }
                  }
               }
            }

            var34 = var9;
            var4 = var3;
         }

         var3 = var4 + 1;
         var9 = var34;
      }
   }

   private int countMatchConstraintsChainedWidgets(LinearSystem var1, ConstraintWidget[] var2, ConstraintWidget var3, int var4, boolean[] var5) {
      var5[0] = true;
      var5[1] = false;
      var2[0] = null;
      var2[2] = null;
      var2[1] = null;
      var2[3] = null;
      int var6;
      boolean var7;
      boolean var8;
      ConstraintWidget var9;
      ConstraintWidget var10;
      ConstraintWidget var11;
      ConstraintWidget var12;
      ConstraintWidget var13;
      ConstraintWidget var14;
      if(var4 == 0) {
         if(var3.mLeft.mTarget != null && var3.mLeft.mTarget.mOwner != this) {
            var7 = false;
         } else {
            var7 = true;
         }

         var3.mHorizontalNextWidget = null;
         if(var3.getVisibility() != 8) {
            var11 = var3;
         } else {
            var11 = null;
         }

         var13 = null;
         var10 = var11;
         var4 = 0;
         var9 = var3;

         while(true) {
            var12 = var11;
            var14 = var10;
            var6 = var4;
            if(var9.mRight.mTarget == null) {
               break;
            }

            var9.mHorizontalNextWidget = null;
            if(var9.getVisibility() != 8) {
               var12 = var10;
               if(var10 == null) {
                  var12 = var9;
               }

               if(var11 != null && var11 != var9) {
                  var11.mHorizontalNextWidget = var9;
               }

               var11 = var9;
               var10 = var12;
            } else {
               var1.addEquality(var9.mLeft.mSolverVariable, var9.mLeft.mTarget.mSolverVariable, 0, 5);
               var1.addEquality(var9.mRight.mSolverVariable, var9.mLeft.mSolverVariable, 0, 5);
            }

            var6 = var4;
            if(var9.getVisibility() != 8) {
               var6 = var4;
               if(var9.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                  if(var9.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                     var5[0] = false;
                  }

                  var6 = var4;
                  if(var9.mDimensionRatio <= 0.0F) {
                     var5[0] = false;
                     var6 = var4 + 1;
                     if(var6 >= this.mMatchConstraintsChainedWidgets.length) {
                        this.mMatchConstraintsChainedWidgets = (ConstraintWidget[])Arrays.copyOf(this.mMatchConstraintsChainedWidgets, this.mMatchConstraintsChainedWidgets.length * 2);
                     }

                     this.mMatchConstraintsChainedWidgets[var4] = var9;
                  }
               }
            }

            if(var9.mRight.mTarget.mOwner.mLeft.mTarget == null) {
               var12 = var11;
               var14 = var10;
               break;
            }

            if(var9.mRight.mTarget.mOwner.mLeft.mTarget.mOwner != var9) {
               var12 = var11;
               var14 = var10;
               break;
            }

            if(var9.mRight.mTarget.mOwner == var9) {
               var12 = var11;
               var14 = var10;
               break;
            }

            var13 = var9.mRight.mTarget.mOwner;
            var9 = var13;
            var4 = var6;
         }

         var8 = var7;
         if(var9.mRight.mTarget != null) {
            var8 = var7;
            if(var9.mRight.mTarget.mOwner != this) {
               var8 = false;
            }
         }

         if(var3.mLeft.mTarget == null || var13.mRight.mTarget == null) {
            var5[1] = true;
         }

         var3.mHorizontalChainFixedPosition = var8;
         var13.mHorizontalNextWidget = null;
         var2[0] = var3;
         var2[2] = var14;
         var2[1] = var13;
         var2[3] = var12;
         return var6;
      } else {
         if(var3.mTop.mTarget != null && var3.mTop.mTarget.mOwner != this) {
            var7 = false;
         } else {
            var7 = true;
         }

         var3.mVerticalNextWidget = null;
         if(var3.getVisibility() != 8) {
            var10 = var3;
         } else {
            var10 = null;
         }

         var13 = null;
         var11 = var10;
         var4 = 0;
         var9 = var3;

         while(true) {
            var12 = var10;
            var14 = var11;
            var6 = var4;
            if(var9.mBottom.mTarget == null) {
               break;
            }

            var9.mVerticalNextWidget = null;
            if(var9.getVisibility() != 8) {
               var12 = var10;
               if(var10 == null) {
                  var12 = var9;
               }

               if(var11 != null && var11 != var9) {
                  var11.mVerticalNextWidget = var9;
               }

               var11 = var9;
               var10 = var12;
            } else {
               var1.addEquality(var9.mTop.mSolverVariable, var9.mTop.mTarget.mSolverVariable, 0, 5);
               var1.addEquality(var9.mBottom.mSolverVariable, var9.mTop.mSolverVariable, 0, 5);
            }

            if(var9.getVisibility() != 8 && var9.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
               if(var9.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                  var5[0] = false;
               }

               var6 = var4;
               if(var9.mDimensionRatio <= 0.0F) {
                  var5[0] = false;
                  var6 = var4 + 1;
                  if(var6 >= this.mMatchConstraintsChainedWidgets.length) {
                     this.mMatchConstraintsChainedWidgets = (ConstraintWidget[])Arrays.copyOf(this.mMatchConstraintsChainedWidgets, this.mMatchConstraintsChainedWidgets.length * 2);
                  }

                  this.mMatchConstraintsChainedWidgets[var4] = var9;
               }
            } else {
               var6 = var4;
            }

            if(var9.mBottom.mTarget.mOwner.mTop.mTarget == null || var9.mBottom.mTarget.mOwner.mTop.mTarget.mOwner != var9 || var9.mBottom.mTarget.mOwner == var9) {
               var12 = var10;
               var14 = var11;
               break;
            }

            var13 = var9.mBottom.mTarget.mOwner;
            var9 = var13;
            var4 = var6;
         }

         var8 = var7;
         if(var9.mBottom.mTarget != null) {
            var8 = var7;
            if(var9.mBottom.mTarget.mOwner != this) {
               var8 = false;
            }
         }

         if(var3.mTop.mTarget == null || var13.mBottom.mTarget == null) {
            var5[1] = true;
         }

         var3.mVerticalChainFixedPosition = var8;
         var13.mVerticalNextWidget = null;
         var2[0] = var3;
         var2[2] = var12;
         var2[1] = var13;
         var2[3] = var14;
         return var6;
      }
   }

   public static ConstraintWidgetContainer createContainer(ConstraintWidgetContainer var0, String var1, ArrayList<ConstraintWidget> var2, int var3) {
      Rectangle var6 = getBounds(var2);
      if(var6.width != 0 && var6.height != 0) {
         int var4;
         if(var3 > 0) {
            int var5 = Math.min(var6.x, var6.y);
            var4 = var3;
            if(var3 > var5) {
               var4 = var5;
            }

            var6.grow(var4, var4);
         }

         var0.setOrigin(var6.x, var6.y);
         var0.setDimension(var6.width, var6.height);
         var0.setDebugName(var1);
         var3 = 0;
         ConstraintWidget var8 = ((ConstraintWidget)var2.get(0)).getParent();

         for(var4 = var2.size(); var3 < var4; ++var3) {
            ConstraintWidget var7 = (ConstraintWidget)var2.get(var3);
            if(var7.getParent() == var8) {
               var0.add(var7);
               var7.setX(var7.getX() - var6.x);
               var7.setY(var7.getY() - var6.y);
            }
         }

         return var0;
      } else {
         return null;
      }
   }

   private boolean optimize(LinearSystem var1) {
      int var10 = this.mChildren.size();

      int var2;
      ConstraintWidget var11;
      for(var2 = 0; var2 < var10; ++var2) {
         var11 = (ConstraintWidget)this.mChildren.get(var2);
         var11.mHorizontalResolution = -1;
         var11.mVerticalResolution = -1;
         if(var11.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || var11.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            var11.mHorizontalResolution = 1;
            var11.mVerticalResolution = 1;
         }
      }

      boolean var4 = false;
      int var5 = 0;

      int var3;
      int var6;
      for(var6 = 0; !var4; var6 = var2) {
         int var7 = 0;
         var3 = 0;

         int var9;
         for(var2 = 0; var7 < var10; var2 = var9) {
            var11 = (ConstraintWidget)this.mChildren.get(var7);
            if(var11.mHorizontalResolution == -1) {
               if(this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                  var11.mHorizontalResolution = 1;
               } else {
                  Optimizer.checkHorizontalSimpleDependency(this, var1, var11);
               }
            }

            if(var11.mVerticalResolution == -1) {
               if(this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                  var11.mVerticalResolution = 1;
               } else {
                  Optimizer.checkVerticalSimpleDependency(this, var1, var11);
               }
            }

            int var8 = var3;
            if(var11.mVerticalResolution == -1) {
               var8 = var3 + 1;
            }

            var9 = var2;
            if(var11.mHorizontalResolution == -1) {
               var9 = var2 + 1;
            }

            ++var7;
            var3 = var8;
         }

         boolean var14;
         label114: {
            if(var3 != 0 || var2 != 0) {
               var14 = var4;
               if(var5 != var3) {
                  break label114;
               }

               var14 = var4;
               if(var6 != var2) {
                  break label114;
               }
            }

            var14 = true;
         }

         var4 = var14;
         var5 = var3;
      }

      var2 = 0;
      var5 = 0;

      for(var3 = 0; var2 < var10; var3 = var6) {
         ConstraintWidget var12;
         int var13;
         label70: {
            var12 = (ConstraintWidget)this.mChildren.get(var2);
            if(var12.mHorizontalResolution != 1) {
               var13 = var5;
               if(var12.mHorizontalResolution != -1) {
                  break label70;
               }
            }

            var13 = var5 + 1;
         }

         label65: {
            if(var12.mVerticalResolution != 1) {
               var6 = var3;
               if(var12.mVerticalResolution != -1) {
                  break label65;
               }
            }

            var6 = var3 + 1;
         }

         ++var2;
         var5 = var13;
      }

      if(var5 == 0 && var3 == 0) {
         return true;
      } else {
         return false;
      }
   }

   private void resetChains() {
      this.mHorizontalChainsSize = 0;
      this.mVerticalChainsSize = 0;
   }

   static int setGroup(ConstraintAnchor var0, int var1) {
      int var2 = var0.mGroup;
      if(var0.mOwner.getParent() == null) {
         return var1;
      } else if(var2 <= var1) {
         return var2;
      } else {
         var0.mGroup = var1;
         ConstraintAnchor var3 = var0.getOpposite();
         ConstraintAnchor var4 = var0.mTarget;
         var2 = var1;
         if(var3 != null) {
            var2 = setGroup(var3, var1);
         }

         var1 = var2;
         if(var4 != null) {
            var1 = setGroup(var4, var2);
         }

         var2 = var1;
         if(var3 != null) {
            var2 = setGroup(var3, var1);
         }

         var0.mGroup = var2;
         return var2;
      }
   }

   void addChain(ConstraintWidget var1, int var2) {
      if(var2 == 0) {
         while(var1.mLeft.mTarget != null && var1.mLeft.mTarget.mOwner.mRight.mTarget != null && var1.mLeft.mTarget.mOwner.mRight.mTarget == var1.mLeft && var1.mLeft.mTarget.mOwner != var1) {
            var1 = var1.mLeft.mTarget.mOwner;
         }

         this.addHorizontalChain(var1);
      } else {
         if(var2 == 1) {
            while(var1.mTop.mTarget != null && var1.mTop.mTarget.mOwner.mBottom.mTarget != null && var1.mTop.mTarget.mOwner.mBottom.mTarget == var1.mTop && var1.mTop.mTarget.mOwner != var1) {
               var1 = var1.mTop.mTarget.mOwner;
            }

            this.addVerticalChain(var1);
         }

      }
   }

   public boolean addChildrenToSolver(LinearSystem var1, int var2) {
      this.addToSolver(var1, var2);
      int var5 = this.mChildren.size();
      int var3 = this.mOptimizationLevel;
      int var4 = 0;
      boolean var9;
      if(var3 != 2 && this.mOptimizationLevel != 4) {
         var9 = true;
      } else {
         if(this.optimize(var1)) {
            return false;
         }

         var9 = false;
      }

      for(; var4 < var5; ++var4) {
         ConstraintWidget var6 = (ConstraintWidget)this.mChildren.get(var4);
         if(var6 instanceof ConstraintWidgetContainer) {
            ConstraintWidget.DimensionBehaviour var7 = var6.mHorizontalDimensionBehaviour;
            ConstraintWidget.DimensionBehaviour var8 = var6.mVerticalDimensionBehaviour;
            if(var7 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var6.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
            }

            if(var8 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var6.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
            }

            var6.addToSolver(var1, var2);
            if(var7 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var6.setHorizontalDimensionBehaviour(var7);
            }

            if(var8 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var6.setVerticalDimensionBehaviour(var8);
            }
         } else {
            if(var9) {
               Optimizer.checkMatchParent(this, var1, var6);
            }

            var6.addToSolver(var1, var2);
         }
      }

      if(this.mHorizontalChainsSize > 0) {
         this.applyHorizontalChain(var1);
      }

      if(this.mVerticalChainsSize > 0) {
         this.applyVerticalChain(var1);
      }

      return true;
   }

   public void findHorizontalWrapRecursive(ConstraintWidget var1, boolean[] var2) {
      ConstraintWidget.DimensionBehaviour var9 = var1.mHorizontalDimensionBehaviour;
      ConstraintWidget.DimensionBehaviour var10 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
      byte var5 = 0;
      int var3 = 0;
      if(var9 == var10 && var1.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var1.mDimensionRatio > 0.0F) {
         var2[0] = false;
      } else {
         int var4 = var1.getOptimizerWrapWidth();
         if(var1.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var1.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var1.mDimensionRatio > 0.0F) {
            var2[0] = false;
         } else {
            boolean var8 = true;
            var1.mHorizontalWrapVisited = true;
            int var6;
            int var13;
            if(var1 instanceof Guideline) {
               Guideline var12 = (Guideline)var1;
               if(var12.getOrientation() == 1) {
                  label135: {
                     if(var12.getRelativeBegin() != -1) {
                        var3 = var12.getRelativeBegin();
                     } else if(var12.getRelativeEnd() != -1) {
                        var4 = var12.getRelativeEnd();
                        var3 = var5;
                        break label135;
                     }

                     var4 = 0;
                  }
               } else {
                  var3 = var4;
               }
            } else if(!var1.mRight.isConnected() && !var1.mLeft.isConnected()) {
               var3 = var4 + var1.getX();
            } else {
               label162: {
                  if(var1.mRight.mTarget != null && var1.mLeft.mTarget != null && (var1.mRight.mTarget == var1.mLeft.mTarget || var1.mRight.mTarget.mOwner == var1.mLeft.mTarget.mOwner && var1.mRight.mTarget.mOwner != var1.mParent)) {
                     var2[0] = false;
                     return;
                  }

                  ConstraintAnchor var14 = var1.mRight.mTarget;
                  ConstraintWidget var16 = null;
                  ConstraintWidget var11;
                  ConstraintWidget var15;
                  if(var14 != null) {
                     var11 = var1.mRight.mTarget.mOwner;
                     var3 = var1.mRight.getMargin() + var4;
                     var15 = var11;
                     var13 = var3;
                     if(!var11.isRoot()) {
                        var15 = var11;
                        var13 = var3;
                        if(!var11.mHorizontalWrapVisited) {
                           this.findHorizontalWrapRecursive(var11, var2);
                           var15 = var11;
                           var13 = var3;
                        }
                     }
                  } else {
                     var13 = var4;
                     var15 = null;
                  }

                  var3 = var4;
                  if(var1.mLeft.mTarget != null) {
                     var11 = var1.mLeft.mTarget.mOwner;
                     var4 += var1.mLeft.getMargin();
                     var3 = var4;
                     var16 = var11;
                     if(!var11.isRoot()) {
                        var3 = var4;
                        var16 = var11;
                        if(!var11.mHorizontalWrapVisited) {
                           this.findHorizontalWrapRecursive(var11, var2);
                           var16 = var11;
                           var3 = var4;
                        }
                     }
                  }

                  var4 = var13;
                  boolean var7;
                  if(var1.mRight.mTarget != null) {
                     var4 = var13;
                     if(!var15.isRoot()) {
                        if(var1.mRight.mTarget.mType == ConstraintAnchor.Type.RIGHT) {
                           var6 = var13 + (var15.mDistToRight - var15.getOptimizerWrapWidth());
                        } else {
                           var6 = var13;
                           if(var1.mRight.mTarget.getType() == ConstraintAnchor.Type.LEFT) {
                              var6 = var13 + var15.mDistToRight;
                           }
                        }

                        if(!var15.mRightHasCentered && (var15.mLeft.mTarget == null || var15.mRight.mTarget == null || var15.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {
                           var7 = false;
                        } else {
                           var7 = true;
                        }

                        var1.mRightHasCentered = var7;
                        var4 = var6;
                        if(var1.mRightHasCentered) {
                           label116: {
                              if(var15.mLeft.mTarget != null) {
                                 var4 = var6;
                                 if(var15.mLeft.mTarget.mOwner == var1) {
                                    break label116;
                                 }
                              }

                              var4 = var6 + (var6 - var15.mDistToRight);
                           }
                        }
                     }
                  }

                  var6 = var3;
                  if(var1.mLeft.mTarget != null) {
                     var6 = var3;
                     if(!var16.isRoot()) {
                        if(var1.mLeft.mTarget.getType() == ConstraintAnchor.Type.LEFT) {
                           var13 = var3 + (var16.mDistToLeft - var16.getOptimizerWrapWidth());
                        } else {
                           var13 = var3;
                           if(var1.mLeft.mTarget.getType() == ConstraintAnchor.Type.RIGHT) {
                              var13 = var3 + var16.mDistToLeft;
                           }
                        }

                        var7 = var8;
                        if(!var16.mLeftHasCentered) {
                           if(var16.mLeft.mTarget != null && var16.mRight.mTarget != null && var16.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                              var7 = var8;
                           } else {
                              var7 = false;
                           }
                        }

                        var1.mLeftHasCentered = var7;
                        var6 = var13;
                        if(var1.mLeftHasCentered) {
                           label107: {
                              if(var16.mRight.mTarget != null) {
                                 var6 = var13;
                                 if(var16.mRight.mTarget.mOwner == var1) {
                                    break label107;
                                 }
                              }

                              var3 = var13 + (var13 - var16.mDistToLeft);
                              break label162;
                           }
                        }
                     }
                  }

                  var3 = var6;
               }
            }

            var6 = var3;
            var13 = var4;
            if(var1.getVisibility() == 8) {
               var6 = var3 - var1.mWidth;
               var13 = var4 - var1.mWidth;
            }

            var1.mDistToLeft = var6;
            var1.mDistToRight = var13;
         }
      }
   }

   public void findVerticalWrapRecursive(ConstraintWidget var1, boolean[] var2) {
      ConstraintWidget.DimensionBehaviour var10 = var1.mVerticalDimensionBehaviour;
      ConstraintWidget.DimensionBehaviour var11 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
      byte var4 = 0;
      int var3 = 0;
      if(var10 == var11 && var1.mHorizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var1.mDimensionRatio > 0.0F) {
         var2[0] = false;
      } else {
         int var5;
         int var7;
         int var14;
         label167: {
            var5 = var1.getOptimizerWrapHeight();
            boolean var9 = true;
            var1.mVerticalWrapVisited = true;
            if(var1 instanceof Guideline) {
               Guideline var13 = (Guideline)var1;
               if(var13.getOrientation() == 0) {
                  label147: {
                     if(var13.getRelativeBegin() != -1) {
                        var3 = var13.getRelativeBegin();
                     } else if(var13.getRelativeEnd() != -1) {
                        var5 = var13.getRelativeEnd();
                        var3 = var4;
                        break label147;
                     }

                     var5 = 0;
                  }
               } else {
                  var3 = var5;
               }
            } else {
               if(var1.mBaseline.mTarget != null || var1.mTop.mTarget != null || var1.mBottom.mTarget != null) {
                  if(var1.mBottom.mTarget != null && var1.mTop.mTarget != null && (var1.mBottom.mTarget == var1.mTop.mTarget || var1.mBottom.mTarget.mOwner == var1.mTop.mTarget.mOwner && var1.mBottom.mTarget.mOwner != var1.mParent)) {
                     var2[0] = false;
                     return;
                  }

                  int var6;
                  ConstraintWidget var15;
                  if(var1.mBaseline.isConnected()) {
                     var15 = var1.mBaseline.mTarget.getOwner();
                     if(!var15.mVerticalWrapVisited) {
                        this.findVerticalWrapRecursive(var15, var2);
                     }

                     var6 = Math.max(var15.mDistToTop - var15.mHeight + var5, var5);
                     var5 = Math.max(var15.mDistToBottom - var15.mHeight + var5, var5);
                     var14 = var5;
                     var3 = var6;
                     if(var1.getVisibility() == 8) {
                        var3 = var6 - var1.mHeight;
                        var14 = var5 - var1.mHeight;
                     }

                     var1.mDistToTop = var3;
                     var1.mDistToBottom = var14;
                     return;
                  }

                  boolean var8 = var1.mTop.isConnected();
                  ConstraintWidget var16 = null;
                  ConstraintWidget var12;
                  if(var8) {
                     var12 = var1.mTop.mTarget.getOwner();
                     var14 = var1.mTop.getMargin() + var5;
                     var15 = var12;
                     var3 = var14;
                     if(!var12.isRoot()) {
                        var15 = var12;
                        var3 = var14;
                        if(!var12.mVerticalWrapVisited) {
                           this.findVerticalWrapRecursive(var12, var2);
                           var15 = var12;
                           var3 = var14;
                        }
                     }
                  } else {
                     var3 = var5;
                     var15 = null;
                  }

                  var14 = var5;
                  if(var1.mBottom.isConnected()) {
                     var12 = var1.mBottom.mTarget.getOwner();
                     var5 += var1.mBottom.getMargin();
                     var14 = var5;
                     var16 = var12;
                     if(!var12.isRoot()) {
                        var14 = var5;
                        var16 = var12;
                        if(!var12.mVerticalWrapVisited) {
                           this.findVerticalWrapRecursive(var12, var2);
                           var16 = var12;
                           var14 = var5;
                        }
                     }
                  }

                  var5 = var3;
                  if(var1.mTop.mTarget != null) {
                     var5 = var3;
                     if(!var15.isRoot()) {
                        if(var1.mTop.mTarget.getType() == ConstraintAnchor.Type.TOP) {
                           var6 = var3 + (var15.mDistToTop - var15.getOptimizerWrapHeight());
                        } else {
                           var6 = var3;
                           if(var1.mTop.mTarget.getType() == ConstraintAnchor.Type.BOTTOM) {
                              var6 = var3 + var15.mDistToTop;
                           }
                        }

                        if(!var15.mTopHasCentered && (var15.mTop.mTarget == null || var15.mTop.mTarget.mOwner == var1 || var15.mBottom.mTarget == null || var15.mBottom.mTarget.mOwner == var1 || var15.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {
                           var8 = false;
                        } else {
                           var8 = true;
                        }

                        var1.mTopHasCentered = var8;
                        var5 = var6;
                        if(var1.mTopHasCentered) {
                           label124: {
                              if(var15.mBottom.mTarget != null) {
                                 var5 = var6;
                                 if(var15.mBottom.mTarget.mOwner == var1) {
                                    break label124;
                                 }
                              }

                              var5 = var6 + (var6 - var15.mDistToTop);
                           }
                        }
                     }
                  }

                  var3 = var14;
                  var7 = var5;
                  if(var1.mBottom.mTarget != null) {
                     var3 = var14;
                     var7 = var5;
                     if(!var16.isRoot()) {
                        if(var1.mBottom.mTarget.getType() == ConstraintAnchor.Type.BOTTOM) {
                           var6 = var14 + (var16.mDistToBottom - var16.getOptimizerWrapHeight());
                        } else {
                           var6 = var14;
                           if(var1.mBottom.mTarget.getType() == ConstraintAnchor.Type.TOP) {
                              var6 = var14 + var16.mDistToBottom;
                           }
                        }

                        var8 = var9;
                        if(!var16.mBottomHasCentered) {
                           if(var16.mTop.mTarget != null && var16.mTop.mTarget.mOwner != var1 && var16.mBottom.mTarget != null && var16.mBottom.mTarget.mOwner != var1 && var16.mVerticalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                              var8 = var9;
                           } else {
                              var8 = false;
                           }
                        }

                        var1.mBottomHasCentered = var8;
                        var3 = var6;
                        var7 = var5;
                        if(var1.mBottomHasCentered) {
                           if(var16.mTop.mTarget != null) {
                              var3 = var6;
                              var7 = var5;
                              if(var16.mTop.mTarget.mOwner == var1) {
                                 break label167;
                              }
                           }

                           var3 = var6 + (var6 - var16.mDistToBottom);
                           var7 = var5;
                        }
                     }
                  }
                  break label167;
               }

               var3 = var5 + var1.getY();
            }

            var7 = var3;
            var3 = var5;
         }

         var5 = var3;
         var14 = var7;
         if(var1.getVisibility() == 8) {
            var14 = var7 - var1.mHeight;
            var5 = var3 - var1.mHeight;
         }

         var1.mDistToTop = var14;
         var1.mDistToBottom = var5;
      }
   }

   public void findWrapSize(ArrayList<ConstraintWidget> var1, boolean[] var2) {
      int var12 = var1.size();
      var2[0] = true;
      int var6 = 0;
      int var11 = 0;
      int var10 = 0;
      int var7 = 0;
      int var8 = 0;
      int var9 = 0;

      int var3;
      int var5;
      for(var5 = 0; var6 < var12; ++var6) {
         ConstraintWidget var17 = (ConstraintWidget)var1.get(var6);
         if(!var17.isRoot()) {
            if(!var17.mHorizontalWrapVisited) {
               this.findHorizontalWrapRecursive(var17, var2);
            }

            if(!var17.mVerticalWrapVisited) {
               this.findVerticalWrapRecursive(var17, var2);
            }

            if(!var2[0]) {
               return;
            }

            var3 = var17.mDistToLeft;
            int var15 = var17.mDistToRight;
            int var16 = var17.getWidth();
            int var4 = var17.mDistToTop;
            int var13 = var17.mDistToBottom;
            int var14 = var17.getHeight();
            if(var17.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
               var3 = var17.getWidth() + var17.mLeft.mMargin + var17.mRight.mMargin;
            } else {
               var3 = var3 + var15 - var16;
            }

            if(var17.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
               var4 = var17.getHeight() + var17.mTop.mMargin + var17.mBottom.mMargin;
            } else {
               var4 = var4 + var13 - var14;
            }

            if(var17.getVisibility() == 8) {
               var3 = 0;
               var4 = 0;
            }

            var11 = Math.max(var11, var17.mDistToLeft);
            var10 = Math.max(var10, var17.mDistToRight);
            var9 = Math.max(var9, var17.mDistToBottom);
            var8 = Math.max(var8, var17.mDistToTop);
            var7 = Math.max(var7, var3);
            var5 = Math.max(var5, var4);
         }
      }

      var3 = Math.max(var11, var10);
      this.mWrapWidth = Math.max(this.mMinWidth, Math.max(var3, var7));
      var3 = Math.max(var8, var9);
      this.mWrapHeight = Math.max(this.mMinHeight, Math.max(var3, var5));

      for(var3 = 0; var3 < var12; ++var3) {
         ConstraintWidget var18 = (ConstraintWidget)var1.get(var3);
         var18.mHorizontalWrapVisited = false;
         var18.mVerticalWrapVisited = false;
         var18.mLeftHasCentered = false;
         var18.mRightHasCentered = false;
         var18.mTopHasCentered = false;
         var18.mBottomHasCentered = false;
      }

   }

   public ArrayList<Guideline> getHorizontalGuidelines() {
      ArrayList var3 = new ArrayList();
      int var2 = this.mChildren.size();

      for(int var1 = 0; var1 < var2; ++var1) {
         ConstraintWidget var4 = (ConstraintWidget)this.mChildren.get(var1);
         if(var4 instanceof Guideline) {
            Guideline var5 = (Guideline)var4;
            if(var5.getOrientation() == 0) {
               var3.add(var5);
            }
         }
      }

      return var3;
   }

   public LinearSystem getSystem() {
      return this.mSystem;
   }

   public String getType() {
      return "ConstraintLayout";
   }

   public ArrayList<Guideline> getVerticalGuidelines() {
      ArrayList var3 = new ArrayList();
      int var2 = this.mChildren.size();

      for(int var1 = 0; var1 < var2; ++var1) {
         ConstraintWidget var4 = (ConstraintWidget)this.mChildren.get(var1);
         if(var4 instanceof Guideline) {
            Guideline var5 = (Guideline)var4;
            if(var5.getOrientation() == 1) {
               var3.add(var5);
            }
         }
      }

      return var3;
   }

   public boolean handlesInternalConstraints() {
      return false;
   }

   public boolean isHeightMeasuredTooSmall() {
      return this.mHeightMeasuredTooSmall;
   }

   public boolean isWidthMeasuredTooSmall() {
      return this.mWidthMeasuredTooSmall;
   }

   public void layout() {
      int var5 = this.mX;
      int var6 = this.mY;
      int var7 = Math.max(0, this.getWidth());
      int var8 = Math.max(0, this.getHeight());
      this.mWidthMeasuredTooSmall = false;
      this.mHeightMeasuredTooSmall = false;
      if(this.mParent != null) {
         if(this.mSnapshot == null) {
            this.mSnapshot = new Snapshot(this);
         }

         this.mSnapshot.updateFrom(this);
         this.setX(this.mPaddingLeft);
         this.setY(this.mPaddingTop);
         this.resetAnchors();
         this.resetSolverVariables(this.mSystem.getCache());
      } else {
         this.mX = 0;
         this.mY = 0;
      }

      ConstraintWidget.DimensionBehaviour var17 = this.mVerticalDimensionBehaviour;
      ConstraintWidget.DimensionBehaviour var18 = this.mHorizontalDimensionBehaviour;
      boolean var10;
      boolean var11;
      if(this.mOptimizationLevel == 2 && (this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
         this.findWrapSize(this.mChildren, this.flags);
         var11 = this.flags[0];
         var10 = var11;
         if(var7 > 0) {
            var10 = var11;
            if(var8 > 0) {
               label192: {
                  if(this.mWrapWidth <= var7) {
                     var10 = var11;
                     if(this.mWrapHeight <= var8) {
                        break label192;
                     }
                  }

                  var10 = false;
               }
            }
         }

         var11 = var10;
         if(var10) {
            if(this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
               if(var7 > 0 && var7 < this.mWrapWidth) {
                  this.mWidthMeasuredTooSmall = true;
                  this.setWidth(var7);
               } else {
                  this.setWidth(Math.max(this.mMinWidth, this.mWrapWidth));
               }
            }

            var11 = var10;
            if(this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
               if(var8 > 0 && var8 < this.mWrapHeight) {
                  this.mHeightMeasuredTooSmall = true;
                  this.setHeight(var8);
                  var11 = var10;
               } else {
                  this.setHeight(Math.max(this.mMinHeight, this.mWrapHeight));
                  var11 = var10;
               }
            }
         }
      } else {
         var11 = false;
      }

      this.resetChains();
      int var9 = this.mChildren.size();

      int var1;
      ConstraintWidget var16;
      for(var1 = 0; var1 < var9; ++var1) {
         var16 = (ConstraintWidget)this.mChildren.get(var1);
         if(var16 instanceof WidgetContainer) {
            ((WidgetContainer)var16).layout();
         }
      }

      var10 = var11;
      var11 = true;

      int var2;
      int var4;
      for(var1 = 0; var11; var1 = var4) {
         var4 = var1 + 1;

         boolean var12;
         label165: {
            label164: {
               Exception var21;
               label209: {
                  try {
                     this.mSystem.reset();
                     var12 = this.addChildrenToSolver(this.mSystem, Integer.MAX_VALUE);
                  } catch (Exception var20) {
                     var21 = var20;
                     break label209;
                  }

                  var11 = var12;
                  if(!var12) {
                     break label165;
                  }

                  try {
                     this.mSystem.minimize();
                     break label164;
                  } catch (Exception var19) {
                     var21 = var19;
                     var11 = var12;
                  }
               }

               var21.printStackTrace();
               break label165;
            }

            var11 = var12;
         }

         if(var11) {
            this.updateChildrenFromSolver(this.mSystem, Integer.MAX_VALUE, this.flags);
         } else {
            this.updateFromSolver(this.mSystem, Integer.MAX_VALUE);

            for(var1 = 0; var1 < var9; ++var1) {
               var16 = (ConstraintWidget)this.mChildren.get(var1);
               if(var16.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var16.getWidth() < var16.getWrapWidth()) {
                  this.flags[2] = true;
                  break;
               }

               if(var16.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var16.getHeight() < var16.getWrapHeight()) {
                  this.flags[2] = true;
                  break;
               }
            }
         }

         boolean var13;
         if(var4 < 8 && this.flags[2]) {
            var2 = 0;
            int var3 = 0;

            for(var1 = 0; var2 < var9; ++var2) {
               var16 = (ConstraintWidget)this.mChildren.get(var2);
               var3 = Math.max(var3, var16.mX + var16.getWidth());
               var1 = Math.max(var1, var16.mY + var16.getHeight());
            }

            var2 = Math.max(this.mMinWidth, var3);
            var1 = Math.max(this.mMinHeight, var1);
            if(var18 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && this.getWidth() < var2) {
               this.setWidth(var2);
               this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
               var12 = true;
               var13 = true;
            } else {
               var12 = false;
               var13 = var10;
            }

            var11 = var12;
            var10 = var13;
            if(var17 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
               var11 = var12;
               var10 = var13;
               if(this.getHeight() < var1) {
                  this.setHeight(var1);
                  this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                  var11 = true;
                  var10 = true;
               }
            }
         } else {
            var11 = false;
         }

         var1 = Math.max(this.mMinWidth, this.getWidth());
         var12 = var11;
         var11 = var10;
         if(var1 > this.getWidth()) {
            this.setWidth(var1);
            this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
            var12 = true;
            var11 = true;
         }

         var1 = Math.max(this.mMinHeight, this.getHeight());
         var10 = var12;
         if(var1 > this.getHeight()) {
            this.setHeight(var1);
            this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
            var10 = true;
            var11 = true;
         }

         label121: {
            var12 = var10;
            boolean var15 = var11;
            if(!var11) {
               boolean var14 = var10;
               var13 = var11;
               if(this.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                  var14 = var10;
                  var13 = var11;
                  if(var7 > 0) {
                     var14 = var10;
                     var13 = var11;
                     if(this.getWidth() > var7) {
                        this.mWidthMeasuredTooSmall = true;
                        this.mHorizontalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                        this.setWidth(var7);
                        var14 = true;
                        var13 = true;
                     }
                  }
               }

               var12 = var14;
               var15 = var13;
               if(this.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                  var12 = var14;
                  var15 = var13;
                  if(var8 > 0) {
                     var12 = var14;
                     var15 = var13;
                     if(this.getHeight() > var8) {
                        this.mHeightMeasuredTooSmall = true;
                        this.mVerticalDimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
                        this.setHeight(var8);
                        var12 = true;
                        var10 = true;
                        break label121;
                     }
                  }
               }
            }

            var10 = var15;
         }

         var11 = var12;
      }

      if(this.mParent != null) {
         var1 = Math.max(this.mMinWidth, this.getWidth());
         var2 = Math.max(this.mMinHeight, this.getHeight());
         this.mSnapshot.applyTo(this);
         this.setWidth(var1 + this.mPaddingLeft + this.mPaddingRight);
         this.setHeight(var2 + this.mPaddingTop + this.mPaddingBottom);
      } else {
         this.mX = var5;
         this.mY = var6;
      }

      if(var10) {
         this.mHorizontalDimensionBehaviour = var18;
         this.mVerticalDimensionBehaviour = var17;
      }

      this.resetSolverVariables(this.mSystem.getCache());
      if(this == this.getRootConstraintContainer()) {
         this.updateDrawPosition();
      }

   }

   public int layoutFindGroups() {
      ConstraintAnchor.Type[] var8 = new ConstraintAnchor.Type[5];
      ConstraintAnchor.Type var7 = ConstraintAnchor.Type.LEFT;
      byte var5 = 0;
      var8[0] = var7;
      var8[1] = ConstraintAnchor.Type.RIGHT;
      var8[2] = ConstraintAnchor.Type.TOP;
      var8[3] = ConstraintAnchor.Type.BASELINE;
      var8[4] = ConstraintAnchor.Type.BOTTOM;
      int var6 = this.mChildren.size();
      int var3 = 0;

      int var1;
      int var2;
      ConstraintAnchor var9;
      ConstraintAnchor var14;
      for(var1 = 1; var3 < var6; var1 = var2) {
         ConstraintWidget var13 = (ConstraintWidget)this.mChildren.get(var3);
         var9 = var13.mLeft;
         if(var9.mTarget != null) {
            var2 = var1;
            if(setGroup(var9, var1) == var1) {
               var2 = var1 + 1;
            }
         } else {
            var9.mGroup = Integer.MAX_VALUE;
            var2 = var1;
         }

         var9 = var13.mTop;
         if(var9.mTarget != null) {
            var1 = var2;
            if(setGroup(var9, var2) == var2) {
               var1 = var2 + 1;
            }
         } else {
            var9.mGroup = Integer.MAX_VALUE;
            var1 = var2;
         }

         var9 = var13.mRight;
         if(var9.mTarget != null) {
            var2 = var1;
            if(setGroup(var9, var1) == var1) {
               var2 = var1 + 1;
            }
         } else {
            var9.mGroup = Integer.MAX_VALUE;
            var2 = var1;
         }

         var9 = var13.mBottom;
         if(var9.mTarget != null) {
            var1 = var2;
            if(setGroup(var9, var2) == var2) {
               var1 = var2 + 1;
            }
         } else {
            var9.mGroup = Integer.MAX_VALUE;
            var1 = var2;
         }

         var14 = var13.mBaseline;
         if(var14.mTarget != null) {
            var2 = var1;
            if(setGroup(var14, var1) == var1) {
               var2 = var1 + 1;
            }
         } else {
            var14.mGroup = Integer.MAX_VALUE;
            var2 = var1;
         }

         ++var3;
      }

      boolean var11 = true;

      int var4;
      while(var11) {
         var3 = 0;

         for(var11 = false; var3 < var6; ++var3) {
            ConstraintWidget var18 = (ConstraintWidget)this.mChildren.get(var3);

            for(var4 = 0; var4 < var8.length; ++var4) {
               ConstraintAnchor.Type var10 = var8[var4];
               var14 = null;
               switch(null.$SwitchMap$android$support$constraint$solver$widgets$ConstraintAnchor$Type[var10.ordinal()]) {
               case 1:
                  var14 = var18.mLeft;
                  break;
               case 2:
                  var14 = var18.mTop;
                  break;
               case 3:
                  var14 = var18.mRight;
                  break;
               case 4:
                  var14 = var18.mBottom;
                  break;
               case 5:
                  var14 = var18.mBaseline;
               }

               ConstraintAnchor var19 = var14.mTarget;
               if(var19 != null) {
                  boolean var12 = var11;
                  if(var19.mOwner.getParent() != null) {
                     var12 = var11;
                     if(var19.mGroup != var14.mGroup) {
                        if(var14.mGroup > var19.mGroup) {
                           var1 = var19.mGroup;
                        } else {
                           var1 = var14.mGroup;
                        }

                        var14.mGroup = var1;
                        var19.mGroup = var1;
                        var12 = true;
                     }
                  }

                  var19 = var19.getOpposite();
                  var11 = var12;
                  if(var19 != null) {
                     var11 = var12;
                     if(var19.mGroup != var14.mGroup) {
                        if(var14.mGroup > var19.mGroup) {
                           var1 = var19.mGroup;
                        } else {
                           var1 = var14.mGroup;
                        }

                        var14.mGroup = var1;
                        var19.mGroup = var1;
                        var11 = true;
                     }
                  }
               }
            }
         }
      }

      int[] var17 = new int[this.mChildren.size() * var8.length + 1];
      Arrays.fill(var17, -1);
      var1 = 0;

      for(var3 = var5; var3 < var6; var1 = var2) {
         ConstraintWidget var15 = (ConstraintWidget)this.mChildren.get(var3);
         var9 = var15.mLeft;
         var2 = var1;
         if(var9.mGroup != Integer.MAX_VALUE) {
            var4 = var9.mGroup;
            var2 = var1;
            if(var17[var4] == -1) {
               var17[var4] = var1;
               var2 = var1 + 1;
            }

            var9.mGroup = var17[var4];
         }

         var9 = var15.mTop;
         var1 = var2;
         if(var9.mGroup != Integer.MAX_VALUE) {
            var4 = var9.mGroup;
            var1 = var2;
            if(var17[var4] == -1) {
               var17[var4] = var2;
               var1 = var2 + 1;
            }

            var9.mGroup = var17[var4];
         }

         var9 = var15.mRight;
         var2 = var1;
         if(var9.mGroup != Integer.MAX_VALUE) {
            var4 = var9.mGroup;
            var2 = var1;
            if(var17[var4] == -1) {
               var17[var4] = var1;
               var2 = var1 + 1;
            }

            var9.mGroup = var17[var4];
         }

         var9 = var15.mBottom;
         var1 = var2;
         if(var9.mGroup != Integer.MAX_VALUE) {
            var4 = var9.mGroup;
            var1 = var2;
            if(var17[var4] == -1) {
               var17[var4] = var2;
               var1 = var2 + 1;
            }

            var9.mGroup = var17[var4];
         }

         ConstraintAnchor var16 = var15.mBaseline;
         var2 = var1;
         if(var16.mGroup != Integer.MAX_VALUE) {
            var4 = var16.mGroup;
            var2 = var1;
            if(var17[var4] == -1) {
               var17[var4] = var1;
               var2 = var1 + 1;
            }

            var16.mGroup = var17[var4];
         }

         ++var3;
      }

      return var1;
   }

   public int layoutFindGroupsSimple() {
      int var2 = this.mChildren.size();

      for(int var1 = 0; var1 < var2; ++var1) {
         ConstraintWidget var3 = (ConstraintWidget)this.mChildren.get(var1);
         var3.mLeft.mGroup = 0;
         var3.mRight.mGroup = 0;
         var3.mTop.mGroup = 1;
         var3.mBottom.mGroup = 1;
         var3.mBaseline.mGroup = 1;
      }

      return 2;
   }

   public void layoutWithGroup(int var1) {
      int var4 = this.mX;
      int var5 = this.mY;
      ConstraintWidget var7 = this.mParent;
      byte var3 = 0;
      if(var7 != null) {
         if(this.mSnapshot == null) {
            this.mSnapshot = new Snapshot(this);
         }

         this.mSnapshot.updateFrom(this);
         this.mX = 0;
         this.mY = 0;
         this.resetAnchors();
         this.resetSolverVariables(this.mSystem.getCache());
      } else {
         this.mX = 0;
         this.mY = 0;
      }

      int var6 = this.mChildren.size();

      int var2;
      for(var2 = 0; var2 < var6; ++var2) {
         var7 = (ConstraintWidget)this.mChildren.get(var2);
         if(var7 instanceof WidgetContainer) {
            ((WidgetContainer)var7).layout();
         }
      }

      this.mLeft.mGroup = 0;
      this.mRight.mGroup = 0;
      this.mTop.mGroup = 1;
      this.mBottom.mGroup = 1;
      this.mSystem.reset();

      for(var2 = var3; var2 < var1; ++var2) {
         try {
            this.addToSolver(this.mSystem, var2);
            this.mSystem.minimize();
            this.updateFromSolver(this.mSystem, var2);
         } catch (Exception var8) {
            var8.printStackTrace();
         }

         this.updateFromSolver(this.mSystem, -2);
      }

      if(this.mParent != null) {
         var1 = this.getWidth();
         var2 = this.getHeight();
         this.mSnapshot.applyTo(this);
         this.setWidth(var1);
         this.setHeight(var2);
      } else {
         this.mX = var4;
         this.mY = var5;
      }

      if(this == this.getRootConstraintContainer()) {
         this.updateDrawPosition();
      }

   }

   public void reset() {
      this.mSystem.reset();
      this.mPaddingLeft = 0;
      this.mPaddingRight = 0;
      this.mPaddingTop = 0;
      this.mPaddingBottom = 0;
      super.reset();
   }

   public void setOptimizationLevel(int var1) {
      this.mOptimizationLevel = var1;
   }

   public void setPadding(int var1, int var2, int var3, int var4) {
      this.mPaddingLeft = var1;
      this.mPaddingTop = var2;
      this.mPaddingRight = var3;
      this.mPaddingBottom = var4;
   }

   public void updateChildrenFromSolver(LinearSystem var1, int var2, boolean[] var3) {
      int var4 = 0;
      var3[2] = false;
      this.updateFromSolver(var1, var2);

      for(int var5 = this.mChildren.size(); var4 < var5; ++var4) {
         ConstraintWidget var6 = (ConstraintWidget)this.mChildren.get(var4);
         var6.updateFromSolver(var1, var2);
         if(var6.mHorizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var6.getWidth() < var6.getWrapWidth()) {
            var3[2] = true;
         }

         if(var6.mVerticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && var6.getHeight() < var6.getWrapHeight()) {
            var3[2] = true;
         }
      }

   }
}
