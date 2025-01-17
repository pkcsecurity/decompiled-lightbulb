package android.support.constraint.solver;

import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.constraint.solver.Cache;
import android.support.constraint.solver.SolverVariable;

public class ArrayRow {

   private static final boolean DEBUG = false;
   float constantValue = 0.0F;
   boolean isSimpleDefinition = false;
   boolean used = false;
   SolverVariable variable = null;
   final ArrayLinkedVariables variables;


   public ArrayRow(Cache var1) {
      this.variables = new ArrayLinkedVariables(this, var1);
   }

   public ArrayRow addError(SolverVariable var1, SolverVariable var2) {
      this.variables.put(var1, 1.0F);
      this.variables.put(var2, -1.0F);
      return this;
   }

   ArrayRow addSingleError(SolverVariable var1, int var2) {
      this.variables.put(var1, (float)var2);
      return this;
   }

   ArrayRow createRowCentering(SolverVariable var1, SolverVariable var2, int var3, float var4, SolverVariable var5, SolverVariable var6, int var7) {
      if(var2 == var5) {
         this.variables.put(var1, 1.0F);
         this.variables.put(var6, 1.0F);
         this.variables.put(var2, -2.0F);
         return this;
      } else {
         if(var4 == 0.5F) {
            this.variables.put(var1, 1.0F);
            this.variables.put(var2, -1.0F);
            this.variables.put(var5, -1.0F);
            this.variables.put(var6, 1.0F);
            if(var3 > 0 || var7 > 0) {
               this.constantValue = (float)(-var3 + var7);
               return this;
            }
         } else {
            if(var4 <= 0.0F) {
               this.variables.put(var1, -1.0F);
               this.variables.put(var2, 1.0F);
               this.constantValue = (float)var3;
               return this;
            }

            if(var4 >= 1.0F) {
               this.variables.put(var5, -1.0F);
               this.variables.put(var6, 1.0F);
               this.constantValue = (float)var7;
               return this;
            }

            ArrayLinkedVariables var9 = this.variables;
            float var8 = 1.0F - var4;
            var9.put(var1, var8 * 1.0F);
            this.variables.put(var2, var8 * -1.0F);
            this.variables.put(var5, -1.0F * var4);
            this.variables.put(var6, 1.0F * var4);
            if(var3 > 0 || var7 > 0) {
               this.constantValue = (float)(-var3) * var8 + (float)var7 * var4;
            }
         }

         return this;
      }
   }

   ArrayRow createRowDefinition(SolverVariable var1, int var2) {
      this.variable = var1;
      float var3 = (float)var2;
      var1.computedValue = var3;
      this.constantValue = var3;
      this.isSimpleDefinition = true;
      return this;
   }

   ArrayRow createRowDimensionPercent(SolverVariable var1, SolverVariable var2, SolverVariable var3, float var4) {
      this.variables.put(var1, -1.0F);
      this.variables.put(var2, 1.0F - var4);
      this.variables.put(var3, var4);
      return this;
   }

   public ArrayRow createRowDimensionRatio(SolverVariable var1, SolverVariable var2, SolverVariable var3, SolverVariable var4, float var5) {
      this.variables.put(var1, -1.0F);
      this.variables.put(var2, 1.0F);
      this.variables.put(var3, var5);
      this.variables.put(var4, -var5);
      return this;
   }

   public ArrayRow createRowEqualDimension(float var1, float var2, float var3, SolverVariable var4, int var5, SolverVariable var6, int var7, SolverVariable var8, int var9, SolverVariable var10, int var11) {
      if(var2 != 0.0F && var1 != var3) {
         var1 = var1 / var2 / (var3 / var2);
         this.constantValue = (float)(-var5 - var7) + (float)var9 * var1 + (float)var11 * var1;
         this.variables.put(var4, 1.0F);
         this.variables.put(var6, -1.0F);
         this.variables.put(var10, var1);
         this.variables.put(var8, -var1);
         return this;
      } else {
         this.constantValue = (float)(-var5 - var7 + var9 + var11);
         this.variables.put(var4, 1.0F);
         this.variables.put(var6, -1.0F);
         this.variables.put(var10, 1.0F);
         this.variables.put(var8, -1.0F);
         return this;
      }
   }

   public ArrayRow createRowEquals(SolverVariable var1, int var2) {
      if(var2 < 0) {
         this.constantValue = (float)(var2 * -1);
         this.variables.put(var1, 1.0F);
         return this;
      } else {
         this.constantValue = (float)var2;
         this.variables.put(var1, -1.0F);
         return this;
      }
   }

   public ArrayRow createRowEquals(SolverVariable var1, SolverVariable var2, int var3) {
      boolean var4 = false;
      boolean var5 = false;
      if(var3 != 0) {
         var4 = var5;
         int var6 = var3;
         if(var3 < 0) {
            var6 = var3 * -1;
            var4 = true;
         }

         this.constantValue = (float)var6;
      }

      if(!var4) {
         this.variables.put(var1, -1.0F);
         this.variables.put(var2, 1.0F);
         return this;
      } else {
         this.variables.put(var1, 1.0F);
         this.variables.put(var2, -1.0F);
         return this;
      }
   }

   public ArrayRow createRowGreaterThan(SolverVariable var1, SolverVariable var2, SolverVariable var3, int var4) {
      boolean var5 = false;
      boolean var6 = false;
      if(var4 != 0) {
         var5 = var6;
         int var7 = var4;
         if(var4 < 0) {
            var7 = var4 * -1;
            var5 = true;
         }

         this.constantValue = (float)var7;
      }

      if(!var5) {
         this.variables.put(var1, -1.0F);
         this.variables.put(var2, 1.0F);
         this.variables.put(var3, 1.0F);
         return this;
      } else {
         this.variables.put(var1, 1.0F);
         this.variables.put(var2, -1.0F);
         this.variables.put(var3, -1.0F);
         return this;
      }
   }

   public ArrayRow createRowLowerThan(SolverVariable var1, SolverVariable var2, SolverVariable var3, int var4) {
      boolean var5 = false;
      boolean var6 = false;
      if(var4 != 0) {
         var5 = var6;
         int var7 = var4;
         if(var4 < 0) {
            var7 = var4 * -1;
            var5 = true;
         }

         this.constantValue = (float)var7;
      }

      if(!var5) {
         this.variables.put(var1, -1.0F);
         this.variables.put(var2, 1.0F);
         this.variables.put(var3, -1.0F);
         return this;
      } else {
         this.variables.put(var1, 1.0F);
         this.variables.put(var2, -1.0F);
         this.variables.put(var3, 1.0F);
         return this;
      }
   }

   void ensurePositiveConstant() {
      if(this.constantValue < 0.0F) {
         this.constantValue *= -1.0F;
         this.variables.invert();
      }

   }

   boolean hasAtLeastOnePositiveVariable() {
      return this.variables.hasAtLeastOnePositiveVariable();
   }

   boolean hasKeyVariable() {
      return this.variable != null && (this.variable.mType == SolverVariable.Type.UNRESTRICTED || this.constantValue >= 0.0F);
   }

   boolean hasVariable(SolverVariable var1) {
      return this.variables.containsKey(var1);
   }

   void pickRowVariable() {
      SolverVariable var1 = this.variables.pickPivotCandidate();
      if(var1 != null) {
         this.pivot(var1);
      }

      if(this.variables.currentSize == 0) {
         this.isSimpleDefinition = true;
      }

   }

   void pivot(SolverVariable var1) {
      if(this.variable != null) {
         this.variables.put(this.variable, -1.0F);
         this.variable = null;
      }

      float var2 = this.variables.remove(var1) * -1.0F;
      this.variable = var1;
      if(var2 != 1.0F) {
         this.constantValue /= var2;
         this.variables.divideByAmount(var2);
      }
   }

   public void reset() {
      this.variable = null;
      this.variables.clear();
      this.constantValue = 0.0F;
      this.isSimpleDefinition = false;
   }

   int sizeInBytes() {
      byte var1;
      if(this.variable != null) {
         var1 = 4;
      } else {
         var1 = 0;
      }

      return var1 + 4 + 4 + this.variables.sizeInBytes();
   }

   String toReadableString() {
      StringBuilder var6;
      String var9;
      if(this.variable == null) {
         var6 = new StringBuilder();
         var6.append("");
         var6.append("0");
         var9 = var6.toString();
      } else {
         var6 = new StringBuilder();
         var6.append("");
         var6.append(this.variable);
         var9 = var6.toString();
      }

      StringBuilder var7 = new StringBuilder();
      var7.append(var9);
      var7.append(" = ");
      var9 = var7.toString();
      float var1 = this.constantValue;
      int var4 = 0;
      boolean var3;
      if(var1 != 0.0F) {
         var7 = new StringBuilder();
         var7.append(var9);
         var7.append(this.constantValue);
         var9 = var7.toString();
         var3 = true;
      } else {
         var3 = false;
      }

      String var11;
      for(int var5 = this.variables.currentSize; var4 < var5; ++var4) {
         SolverVariable var10 = this.variables.getVariable(var4);
         if(var10 != null) {
            float var2 = this.variables.getVariableValue(var4);
            String var8 = var10.toString();
            if(!var3) {
               var11 = var9;
               var1 = var2;
               if(var2 < 0.0F) {
                  var7 = new StringBuilder();
                  var7.append(var9);
                  var7.append("- ");
                  var11 = var7.toString();
                  var1 = var2 * -1.0F;
               }
            } else if(var2 > 0.0F) {
               var7 = new StringBuilder();
               var7.append(var9);
               var7.append(" + ");
               var11 = var7.toString();
               var1 = var2;
            } else {
               var7 = new StringBuilder();
               var7.append(var9);
               var7.append(" - ");
               var11 = var7.toString();
               var1 = var2 * -1.0F;
            }

            if(var1 == 1.0F) {
               var6 = new StringBuilder();
               var6.append(var11);
               var6.append(var8);
               var9 = var6.toString();
            } else {
               var6 = new StringBuilder();
               var6.append(var11);
               var6.append(var1);
               var6.append(" ");
               var6.append(var8);
               var9 = var6.toString();
            }

            var3 = true;
         }
      }

      var11 = var9;
      if(!var3) {
         var7 = new StringBuilder();
         var7.append(var9);
         var7.append("0.0");
         var11 = var7.toString();
      }

      return var11;
   }

   public String toString() {
      return this.toReadableString();
   }

   void updateClientEquations() {
      this.variables.updateClientEquations(this);
   }

   boolean updateRowWithEquation(ArrayRow var1) {
      this.variables.updateFromRow(this, var1);
      return true;
   }
}
