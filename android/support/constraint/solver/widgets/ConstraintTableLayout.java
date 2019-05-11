package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;
import android.support.constraint.solver.widgets.Guideline;
import java.util.ArrayList;

public class ConstraintTableLayout extends ConstraintWidgetContainer {

   public static final int ALIGN_CENTER = 0;
   private static final int ALIGN_FULL = 3;
   public static final int ALIGN_LEFT = 1;
   public static final int ALIGN_RIGHT = 2;
   private ArrayList<Guideline> mHorizontalGuidelines = new ArrayList();
   private ArrayList<ConstraintTableLayout.HorizontalSlice> mHorizontalSlices = new ArrayList();
   private int mNumCols = 0;
   private int mNumRows = 0;
   private int mPadding = 8;
   private boolean mVerticalGrowth = true;
   private ArrayList<Guideline> mVerticalGuidelines = new ArrayList();
   private ArrayList<ConstraintTableLayout.VerticalSlice> mVerticalSlices = new ArrayList();
   private LinearSystem system = null;


   public ConstraintTableLayout() {}

   public ConstraintTableLayout(int var1, int var2) {
      super(var1, var2);
   }

   public ConstraintTableLayout(int var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4);
   }

   private void setChildrenConnections() {
      int var3 = this.mChildren.size();
      int var1 = 0;

      for(int var2 = 0; var1 < var3; ++var1) {
         ConstraintWidget var6 = (ConstraintWidget)this.mChildren.get(var1);
         var2 += var6.getContainerItemSkip();
         int var4 = this.mNumCols;
         int var5 = var2 / this.mNumCols;
         ConstraintTableLayout.HorizontalSlice var11 = (ConstraintTableLayout.HorizontalSlice)this.mHorizontalSlices.get(var5);
         ConstraintTableLayout.VerticalSlice var7 = (ConstraintTableLayout.VerticalSlice)this.mVerticalSlices.get(var2 % var4);
         ConstraintWidget var8 = var7.left;
         ConstraintWidget var9 = var7.right;
         ConstraintWidget var10 = var11.top;
         ConstraintWidget var12 = var11.bottom;
         var6.getAnchor(ConstraintAnchor.Type.LEFT).connect(var8.getAnchor(ConstraintAnchor.Type.LEFT), this.mPadding);
         if(var9 instanceof Guideline) {
            var6.getAnchor(ConstraintAnchor.Type.RIGHT).connect(var9.getAnchor(ConstraintAnchor.Type.LEFT), this.mPadding);
         } else {
            var6.getAnchor(ConstraintAnchor.Type.RIGHT).connect(var9.getAnchor(ConstraintAnchor.Type.RIGHT), this.mPadding);
         }

         switch(var7.alignment) {
         case 1:
            var6.getAnchor(ConstraintAnchor.Type.LEFT).setStrength(ConstraintAnchor.Strength.STRONG);
            var6.getAnchor(ConstraintAnchor.Type.RIGHT).setStrength(ConstraintAnchor.Strength.WEAK);
            break;
         case 2:
            var6.getAnchor(ConstraintAnchor.Type.LEFT).setStrength(ConstraintAnchor.Strength.WEAK);
            var6.getAnchor(ConstraintAnchor.Type.RIGHT).setStrength(ConstraintAnchor.Strength.STRONG);
            break;
         case 3:
            var6.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
         }

         var6.getAnchor(ConstraintAnchor.Type.TOP).connect(var10.getAnchor(ConstraintAnchor.Type.TOP), this.mPadding);
         if(var12 instanceof Guideline) {
            var6.getAnchor(ConstraintAnchor.Type.BOTTOM).connect(var12.getAnchor(ConstraintAnchor.Type.TOP), this.mPadding);
         } else {
            var6.getAnchor(ConstraintAnchor.Type.BOTTOM).connect(var12.getAnchor(ConstraintAnchor.Type.BOTTOM), this.mPadding);
         }

         ++var2;
      }

   }

   private void setHorizontalSlices() {
      this.mHorizontalSlices.clear();
      float var2 = 100.0F / (float)this.mNumRows;
      Object var4 = this;
      float var1 = var2;

      for(int var3 = 0; var3 < this.mNumRows; ++var3) {
         ConstraintTableLayout.HorizontalSlice var5 = new ConstraintTableLayout.HorizontalSlice();
         var5.top = (ConstraintWidget)var4;
         if(var3 < this.mNumRows - 1) {
            Guideline var6 = new Guideline();
            var6.setOrientation(0);
            var6.setParent(this);
            var6.setGuidePercent((int)var1);
            var1 += var2;
            var5.bottom = var6;
            this.mHorizontalGuidelines.add(var6);
         } else {
            var5.bottom = this;
         }

         var4 = var5.bottom;
         this.mHorizontalSlices.add(var5);
      }

      this.updateDebugSolverNames();
   }

   private void setVerticalSlices() {
      this.mVerticalSlices.clear();
      float var2 = 100.0F / (float)this.mNumCols;
      int var3 = 0;
      Object var4 = this;

      for(float var1 = var2; var3 < this.mNumCols; ++var3) {
         ConstraintTableLayout.VerticalSlice var5 = new ConstraintTableLayout.VerticalSlice();
         var5.left = (ConstraintWidget)var4;
         if(var3 < this.mNumCols - 1) {
            Guideline var6 = new Guideline();
            var6.setOrientation(1);
            var6.setParent(this);
            var6.setGuidePercent((int)var1);
            var1 += var2;
            var5.right = var6;
            this.mVerticalGuidelines.add(var6);
         } else {
            var5.right = this;
         }

         var4 = var5.right;
         this.mVerticalSlices.add(var5);
      }

      this.updateDebugSolverNames();
   }

   private void updateDebugSolverNames() {
      if(this.system != null) {
         int var3 = this.mVerticalGuidelines.size();
         byte var2 = 0;

         int var1;
         Guideline var4;
         LinearSystem var5;
         StringBuilder var6;
         for(var1 = 0; var1 < var3; ++var1) {
            var4 = (Guideline)this.mVerticalGuidelines.get(var1);
            var5 = this.system;
            var6 = new StringBuilder();
            var6.append(this.getDebugName());
            var6.append(".VG");
            var6.append(var1);
            var4.setDebugSolverName(var5, var6.toString());
         }

         var3 = this.mHorizontalGuidelines.size();

         for(var1 = var2; var1 < var3; ++var1) {
            var4 = (Guideline)this.mHorizontalGuidelines.get(var1);
            var5 = this.system;
            var6 = new StringBuilder();
            var6.append(this.getDebugName());
            var6.append(".HG");
            var6.append(var1);
            var4.setDebugSolverName(var5, var6.toString());
         }

      }
   }

   public void addToSolver(LinearSystem var1, int var2) {
      super.addToSolver(var1, var2);
      int var6 = this.mChildren.size();
      if(var6 != 0) {
         this.setTableDimensions();
         if(var1 == this.mSystem) {
            int var4 = this.mVerticalGuidelines.size();
            byte var5 = 0;
            int var3 = 0;

            label40:
            while(true) {
               boolean var8 = true;
               Guideline var9;
               if(var3 >= var4) {
                  int var7 = this.mHorizontalGuidelines.size();
                  var3 = 0;

                  while(true) {
                     var4 = var5;
                     if(var3 >= var7) {
                        while(var4 < var6) {
                           ((ConstraintWidget)this.mChildren.get(var4)).addToSolver(var1, var2);
                           ++var4;
                        }
                        break label40;
                     }

                     var9 = (Guideline)this.mHorizontalGuidelines.get(var3);
                     if(this.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                        var8 = true;
                     } else {
                        var8 = false;
                     }

                     var9.setPositionRelaxed(var8);
                     var9.addToSolver(var1, var2);
                     ++var3;
                  }
               }

               var9 = (Guideline)this.mVerticalGuidelines.get(var3);
               if(this.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                  var8 = false;
               }

               var9.setPositionRelaxed(var8);
               var9.addToSolver(var1, var2);
               ++var3;
            }
         }

      }
   }

   public void computeGuidelinesPercentPositions() {
      int var3 = this.mVerticalGuidelines.size();
      byte var2 = 0;

      int var1;
      for(var1 = 0; var1 < var3; ++var1) {
         ((Guideline)this.mVerticalGuidelines.get(var1)).inferRelativePercentPosition();
      }

      var3 = this.mHorizontalGuidelines.size();

      for(var1 = var2; var1 < var3; ++var1) {
         ((Guideline)this.mHorizontalGuidelines.get(var1)).inferRelativePercentPosition();
      }

   }

   public void cycleColumnAlignment(int var1) {
      ConstraintTableLayout.VerticalSlice var2 = (ConstraintTableLayout.VerticalSlice)this.mVerticalSlices.get(var1);
      switch(var2.alignment) {
      case 0:
         var2.alignment = 2;
         break;
      case 1:
         var2.alignment = 0;
         break;
      case 2:
         var2.alignment = 1;
      }

      this.setChildrenConnections();
   }

   public String getColumnAlignmentRepresentation(int var1) {
      ConstraintTableLayout.VerticalSlice var2 = (ConstraintTableLayout.VerticalSlice)this.mVerticalSlices.get(var1);
      return var2.alignment == 1?"L":(var2.alignment == 0?"C":(var2.alignment == 3?"F":(var2.alignment == 2?"R":"!")));
   }

   public String getColumnsAlignmentRepresentation() {
      int var2 = this.mVerticalSlices.size();
      String var4 = "";

      String var6;
      for(int var1 = 0; var1 < var2; var4 = var6) {
         ConstraintTableLayout.VerticalSlice var5 = (ConstraintTableLayout.VerticalSlice)this.mVerticalSlices.get(var1);
         StringBuilder var3;
         if(var5.alignment == 1) {
            var3 = new StringBuilder();
            var3.append(var4);
            var3.append("L");
            var6 = var3.toString();
         } else if(var5.alignment == 0) {
            var3 = new StringBuilder();
            var3.append(var4);
            var3.append("C");
            var6 = var3.toString();
         } else if(var5.alignment == 3) {
            var3 = new StringBuilder();
            var3.append(var4);
            var3.append("F");
            var6 = var3.toString();
         } else {
            var6 = var4;
            if(var5.alignment == 2) {
               var3 = new StringBuilder();
               var3.append(var4);
               var3.append("R");
               var6 = var3.toString();
            }
         }

         ++var1;
      }

      return var4;
   }

   public ArrayList<Guideline> getHorizontalGuidelines() {
      return this.mHorizontalGuidelines;
   }

   public int getNumCols() {
      return this.mNumCols;
   }

   public int getNumRows() {
      return this.mNumRows;
   }

   public int getPadding() {
      return this.mPadding;
   }

   public String getType() {
      return "ConstraintTableLayout";
   }

   public ArrayList<Guideline> getVerticalGuidelines() {
      return this.mVerticalGuidelines;
   }

   public boolean handlesInternalConstraints() {
      return true;
   }

   public boolean isVerticalGrowth() {
      return this.mVerticalGrowth;
   }

   public void setColumnAlignment(int var1, int var2) {
      if(var1 < this.mVerticalSlices.size()) {
         ((ConstraintTableLayout.VerticalSlice)this.mVerticalSlices.get(var1)).alignment = var2;
         this.setChildrenConnections();
      }

   }

   public void setColumnAlignment(String var1) {
      int var3 = var1.length();

      for(int var2 = 0; var2 < var3; ++var2) {
         char var4 = var1.charAt(var2);
         if(var4 == 76) {
            this.setColumnAlignment(var2, 1);
         } else if(var4 == 67) {
            this.setColumnAlignment(var2, 0);
         } else if(var4 == 70) {
            this.setColumnAlignment(var2, 3);
         } else if(var4 == 82) {
            this.setColumnAlignment(var2, 2);
         } else {
            this.setColumnAlignment(var2, 0);
         }
      }

   }

   public void setDebugSolverName(LinearSystem var1, String var2) {
      this.system = var1;
      super.setDebugSolverName(var1, var2);
      this.updateDebugSolverNames();
   }

   public void setNumCols(int var1) {
      if(this.mVerticalGrowth && this.mNumCols != var1) {
         this.mNumCols = var1;
         this.setVerticalSlices();
         this.setTableDimensions();
      }

   }

   public void setNumRows(int var1) {
      if(!this.mVerticalGrowth && this.mNumCols != var1) {
         this.mNumRows = var1;
         this.setHorizontalSlices();
         this.setTableDimensions();
      }

   }

   public void setPadding(int var1) {
      if(var1 > 1) {
         this.mPadding = var1;
      }

   }

   public void setTableDimensions() {
      int var3 = this.mChildren.size();
      int var1 = 0;

      int var2;
      for(var2 = 0; var1 < var3; ++var1) {
         var2 += ((ConstraintWidget)this.mChildren.get(var1)).getContainerItemSkip();
      }

      var3 += var2;
      if(this.mVerticalGrowth) {
         if(this.mNumCols == 0) {
            this.setNumCols(1);
         }

         var2 = var3 / this.mNumCols;
         var1 = var2;
         if(this.mNumCols * var2 < var3) {
            var1 = var2 + 1;
         }

         if(this.mNumRows == var1 && this.mVerticalGuidelines.size() == this.mNumCols - 1) {
            return;
         }

         this.mNumRows = var1;
         this.setHorizontalSlices();
      } else {
         if(this.mNumRows == 0) {
            this.setNumRows(1);
         }

         var2 = var3 / this.mNumRows;
         var1 = var2;
         if(this.mNumRows * var2 < var3) {
            var1 = var2 + 1;
         }

         if(this.mNumCols == var1 && this.mHorizontalGuidelines.size() == this.mNumRows - 1) {
            return;
         }

         this.mNumCols = var1;
         this.setVerticalSlices();
      }

      this.setChildrenConnections();
   }

   public void setVerticalGrowth(boolean var1) {
      this.mVerticalGrowth = var1;
   }

   public void updateFromSolver(LinearSystem var1, int var2) {
      super.updateFromSolver(var1, var2);
      if(var1 == this.mSystem) {
         int var5 = this.mVerticalGuidelines.size();
         byte var4 = 0;

         int var3;
         for(var3 = 0; var3 < var5; ++var3) {
            ((Guideline)this.mVerticalGuidelines.get(var3)).updateFromSolver(var1, var2);
         }

         var5 = this.mHorizontalGuidelines.size();

         for(var3 = var4; var3 < var5; ++var3) {
            ((Guideline)this.mHorizontalGuidelines.get(var3)).updateFromSolver(var1, var2);
         }
      }

   }

   class VerticalSlice {

      int alignment = 1;
      ConstraintWidget left;
      int padding;
      ConstraintWidget right;


   }

   class HorizontalSlice {

      ConstraintWidget bottom;
      int padding;
      ConstraintWidget top;


   }
}
