package android.support.constraint.solver;

import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import java.util.ArrayList;

public class Goal {

   ArrayList<SolverVariable> variables = new ArrayList();


   private void initFromSystemErrors(LinearSystem var1) {
      this.variables.clear();

      for(int var2 = 1; var2 < var1.mNumColumns; ++var2) {
         SolverVariable var4 = var1.mCache.mIndexedVariables[var2];

         for(int var3 = 0; var3 < 6; ++var3) {
            var4.strengthVector[var3] = 0.0F;
         }

         var4.strengthVector[var4.strength] = 1.0F;
         if(var4.mType == SolverVariable.Type.ERROR) {
            this.variables.add(var4);
         }
      }

   }

   SolverVariable getPivotCandidate() {
      int var6 = this.variables.size();
      int var5 = 0;
      SolverVariable var8 = null;

      for(int var3 = 0; var5 < var6; ++var5) {
         SolverVariable var9 = (SolverVariable)this.variables.get(var5);

         for(int var2 = 5; var2 >= 0; --var2) {
            float var1 = var9.strengthVector[var2];
            SolverVariable var7 = var8;
            int var4 = var3;
            if(var8 == null) {
               var7 = var8;
               var4 = var3;
               if(var1 < 0.0F) {
                  var7 = var8;
                  var4 = var3;
                  if(var2 >= var3) {
                     var7 = var9;
                     var4 = var2;
                  }
               }
            }

            var8 = var7;
            var3 = var4;
            if(var1 > 0.0F) {
               var8 = var7;
               var3 = var4;
               if(var2 > var4) {
                  var8 = null;
                  var3 = var2;
               }
            }
         }
      }

      return var8;
   }

   public String toString() {
      String var3 = "Goal: ";
      int var2 = this.variables.size();

      for(int var1 = 0; var1 < var2; ++var1) {
         SolverVariable var4 = (SolverVariable)this.variables.get(var1);
         StringBuilder var5 = new StringBuilder();
         var5.append(var3);
         var5.append(var4.strengthsToString());
         var3 = var5.toString();
      }

      return var3;
   }

   void updateFromSystem(LinearSystem var1) {
      this.initFromSystemErrors(var1);
      int var6 = this.variables.size();

      for(int var3 = 0; var3 < var6; ++var3) {
         SolverVariable var8 = (SolverVariable)this.variables.get(var3);
         if(var8.definitionId != -1) {
            ArrayLinkedVariables var9 = var1.getRow(var8.definitionId).variables;
            int var7 = var9.currentSize;

            for(int var4 = 0; var4 < var7; ++var4) {
               SolverVariable var10 = var9.getVariable(var4);
               if(var10 != null) {
                  float var2 = var9.getVariableValue(var4);

                  for(int var5 = 0; var5 < 6; ++var5) {
                     float[] var11 = var10.strengthVector;
                     var11[var5] += var8.strengthVector[var5] * var2;
                  }

                  if(!this.variables.contains(var10)) {
                     this.variables.add(var10);
                  }
               }
            }

            var8.clearStrengths();
         }
      }

   }
}
