package android.support.constraint.solver;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.Pools;
import android.support.constraint.solver.SolverVariable;

public class Cache {

   Pools.Pool<ArrayRow> arrayRowPool = new Pools.SimplePool(256);
   SolverVariable[] mIndexedVariables = new SolverVariable[32];
   Pools.Pool<SolverVariable> solverVariablePool = new Pools.SimplePool(256);


}
