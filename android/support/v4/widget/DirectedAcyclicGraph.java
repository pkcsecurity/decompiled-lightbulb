package android.support.v4.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.util.Pools;
import android.support.v4.util.SimpleArrayMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public final class DirectedAcyclicGraph<T extends Object> {

   private final SimpleArrayMap<T, ArrayList<T>> mGraph = new SimpleArrayMap();
   private final Pools.Pool<ArrayList<T>> mListPool = new Pools.SimplePool(10);
   private final ArrayList<T> mSortResult = new ArrayList();
   private final HashSet<T> mSortTmpMarked = new HashSet();


   private void dfs(T var1, ArrayList<T> var2, HashSet<T> var3) {
      if(!var2.contains(var1)) {
         if(var3.contains(var1)) {
            throw new RuntimeException("This graph contains cyclic dependencies");
         } else {
            var3.add(var1);
            ArrayList var6 = (ArrayList)this.mGraph.get(var1);
            if(var6 != null) {
               int var4 = 0;

               for(int var5 = var6.size(); var4 < var5; ++var4) {
                  this.dfs(var6.get(var4), var2, var3);
               }
            }

            var3.remove(var1);
            var2.add(var1);
         }
      }
   }

   @NonNull
   private ArrayList<T> getEmptyList() {
      ArrayList var2 = (ArrayList)this.mListPool.acquire();
      ArrayList var1 = var2;
      if(var2 == null) {
         var1 = new ArrayList();
      }

      return var1;
   }

   private void poolList(@NonNull ArrayList<T> var1) {
      var1.clear();
      this.mListPool.release(var1);
   }

   public void addEdge(@NonNull T var1, @NonNull T var2) {
      if(this.mGraph.containsKey(var1) && this.mGraph.containsKey(var2)) {
         ArrayList var4 = (ArrayList)this.mGraph.get(var1);
         ArrayList var3 = var4;
         if(var4 == null) {
            var3 = this.getEmptyList();
            this.mGraph.put(var1, var3);
         }

         var3.add(var2);
      } else {
         throw new IllegalArgumentException("All nodes must be present in the graph before being added as an edge");
      }
   }

   public void addNode(@NonNull T var1) {
      if(!this.mGraph.containsKey(var1)) {
         this.mGraph.put(var1, (Object)null);
      }

   }

   public void clear() {
      int var2 = this.mGraph.size();

      for(int var1 = 0; var1 < var2; ++var1) {
         ArrayList var3 = (ArrayList)this.mGraph.valueAt(var1);
         if(var3 != null) {
            this.poolList(var3);
         }
      }

      this.mGraph.clear();
   }

   public boolean contains(@NonNull T var1) {
      return this.mGraph.containsKey(var1);
   }

   @Nullable
   public List getIncomingEdges(@NonNull T var1) {
      return (List)this.mGraph.get(var1);
   }

   @Nullable
   public List<T> getOutgoingEdges(@NonNull T var1) {
      int var3 = this.mGraph.size();
      ArrayList var4 = null;

      ArrayList var5;
      for(int var2 = 0; var2 < var3; var4 = var5) {
         ArrayList var6 = (ArrayList)this.mGraph.valueAt(var2);
         var5 = var4;
         if(var6 != null) {
            var5 = var4;
            if(var6.contains(var1)) {
               var5 = var4;
               if(var4 == null) {
                  var5 = new ArrayList();
               }

               var5.add(this.mGraph.keyAt(var2));
            }
         }

         ++var2;
      }

      return var4;
   }

   @NonNull
   public ArrayList<T> getSortedList() {
      this.mSortResult.clear();
      this.mSortTmpMarked.clear();
      int var2 = this.mGraph.size();

      for(int var1 = 0; var1 < var2; ++var1) {
         this.dfs(this.mGraph.keyAt(var1), this.mSortResult, this.mSortTmpMarked);
      }

      return this.mSortResult;
   }

   public boolean hasOutgoingEdges(@NonNull T var1) {
      int var3 = this.mGraph.size();

      for(int var2 = 0; var2 < var3; ++var2) {
         ArrayList var4 = (ArrayList)this.mGraph.valueAt(var2);
         if(var4 != null && var4.contains(var1)) {
            return true;
         }
      }

      return false;
   }

   int size() {
      return this.mGraph.size();
   }
}
