package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.BaseDataSet;
import com.github.mikephil.charting.data.Entry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class DataSet<T extends Entry> extends BaseDataSet<T> {

   protected List<T> mValues = null;
   protected float mXMax = -3.4028235E38F;
   protected float mXMin = Float.MAX_VALUE;
   protected float mYMax = -3.4028235E38F;
   protected float mYMin = Float.MAX_VALUE;


   public DataSet(List<T> var1, String var2) {
      super(var2);
      this.mValues = var1;
      if(this.mValues == null) {
         this.mValues = new ArrayList();
      }

      this.calcMinMax();
   }

   public boolean addEntry(T var1) {
      if(var1 == null) {
         return false;
      } else {
         List var3 = this.getValues();
         Object var2 = var3;
         if(var3 == null) {
            var2 = new ArrayList();
         }

         this.calcMinMax(var1);
         return ((List)var2).add(var1);
      }
   }

   public void addEntryOrdered(T var1) {
      if(var1 != null) {
         if(this.mValues == null) {
            this.mValues = new ArrayList();
         }

         this.calcMinMax(var1);
         if(this.mValues.size() > 0 && ((Entry)this.mValues.get(this.mValues.size() - 1)).getX() > var1.getX()) {
            int var2 = this.getEntryIndex(var1.getX(), var1.getY(), DataSet.Rounding.UP);
            this.mValues.add(var2, var1);
         } else {
            this.mValues.add(var1);
         }
      }
   }

   public void calcMinMax() {
      if(this.mValues != null) {
         if(!this.mValues.isEmpty()) {
            this.mYMax = -3.4028235E38F;
            this.mYMin = Float.MAX_VALUE;
            this.mXMax = -3.4028235E38F;
            this.mXMin = Float.MAX_VALUE;
            Iterator var1 = this.mValues.iterator();

            while(var1.hasNext()) {
               this.calcMinMax((Entry)var1.next());
            }

         }
      }
   }

   protected void calcMinMax(T var1) {
      if(var1 != null) {
         this.calcMinMaxX(var1);
         this.calcMinMaxY(var1);
      }
   }

   protected void calcMinMaxX(T var1) {
      if(var1.getX() < this.mXMin) {
         this.mXMin = var1.getX();
      }

      if(var1.getX() > this.mXMax) {
         this.mXMax = var1.getX();
      }

   }

   public void calcMinMaxY(float var1, float var2) {
      if(this.mValues != null) {
         if(!this.mValues.isEmpty()) {
            this.mYMax = -3.4028235E38F;
            this.mYMin = Float.MAX_VALUE;
            int var3 = this.getEntryIndex(var1, Float.NaN, DataSet.Rounding.DOWN);

            for(int var4 = this.getEntryIndex(var2, Float.NaN, DataSet.Rounding.UP); var3 <= var4; ++var3) {
               this.calcMinMaxY((Entry)this.mValues.get(var3));
            }

         }
      }
   }

   protected void calcMinMaxY(T var1) {
      if(var1.getY() < this.mYMin) {
         this.mYMin = var1.getY();
      }

      if(var1.getY() > this.mYMax) {
         this.mYMax = var1.getY();
      }

   }

   public void clear() {
      this.mValues.clear();
      this.notifyDataSetChanged();
   }

   public abstract DataSet<T> copy();

   public List<T> getEntriesForXValue(float var1) {
      ArrayList var5 = new ArrayList();
      int var3 = this.mValues.size() - 1;
      int var2 = 0;

      while(var2 <= var3) {
         int var4 = (var3 + var2) / 2;
         Entry var6 = (Entry)this.mValues.get(var4);
         if(var1 == var6.getX()) {
            for(var2 = var4; var2 > 0 && ((Entry)this.mValues.get(var2 - 1)).getX() == var1; --var2) {
               ;
            }

            for(var3 = this.mValues.size(); var2 < var3; ++var2) {
               var6 = (Entry)this.mValues.get(var2);
               if(var6.getX() != var1) {
                  return var5;
               }

               var5.add(var6);
            }

            return var5;
         }

         if(var1 > var6.getX()) {
            var2 = var4 + 1;
         } else {
            var3 = var4 - 1;
         }
      }

      return var5;
   }

   public int getEntryCount() {
      return this.mValues.size();
   }

   public T getEntryForIndex(int var1) {
      return (Entry)this.mValues.get(var1);
   }

   public T getEntryForXValue(float var1, float var2) {
      return this.getEntryForXValue(var1, var2, DataSet.Rounding.CLOSEST);
   }

   public T getEntryForXValue(float var1, float var2, DataSet.Rounding var3) {
      int var4 = this.getEntryIndex(var1, var2, var3);
      return var4 > -1?(Entry)this.mValues.get(var4):null;
   }

   public int getEntryIndex(float var1, float var2, DataSet.Rounding var3) {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: fail exe a33 = a25\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:92)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.dfs(Cfg.java:255)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze0(BaseAnalyze.java:75)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.analyze(BaseAnalyze.java:69)\n\tat com.googlecode.dex2jar.ir.ts.Ir2JRegAssignTransformer.transform(Ir2JRegAssignTransformer.java:182)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:164)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\nCaused by: java.lang.NullPointerException\n\tat com.googlecode.dex2jar.ir.ts.an.SimpleLiveAnalyze.onUseLocal(SimpleLiveAnalyze.java:89)\n\tat com.googlecode.dex2jar.ir.ts.an.SimpleLiveAnalyze.onUseLocal(SimpleLiveAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.onUse(BaseAnalyze.java:166)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.onUse(BaseAnalyze.java:1)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.travel(Cfg.java:331)\n\tat com.googlecode.dex2jar.ir.ts.Cfg.travel(Cfg.java:387)\n\tat com.googlecode.dex2jar.ir.ts.an.BaseAnalyze.exec(BaseAnalyze.java:90)\n\t... 17 more\n");
   }

   public int getEntryIndex(Entry var1) {
      return this.mValues.indexOf(var1);
   }

   public List<T> getValues() {
      return this.mValues;
   }

   public float getXMax() {
      return this.mXMax;
   }

   public float getXMin() {
      return this.mXMin;
   }

   public float getYMax() {
      return this.mYMax;
   }

   public float getYMin() {
      return this.mYMin;
   }

   public boolean removeEntry(T var1) {
      if(var1 == null) {
         return false;
      } else if(this.mValues == null) {
         return false;
      } else {
         boolean var2 = this.mValues.remove(var1);
         if(var2) {
            this.calcMinMax();
         }

         return var2;
      }
   }

   public void setValues(List<T> var1) {
      this.mValues = var1;
      this.notifyDataSetChanged();
   }

   public String toSimpleString() {
      StringBuffer var2 = new StringBuffer();
      StringBuilder var3 = new StringBuilder();
      var3.append("DataSet, label: ");
      String var1;
      if(this.getLabel() == null) {
         var1 = "";
      } else {
         var1 = this.getLabel();
      }

      var3.append(var1);
      var3.append(", entries: ");
      var3.append(this.mValues.size());
      var3.append("\n");
      var2.append(var3.toString());
      return var2.toString();
   }

   public String toString() {
      StringBuffer var2 = new StringBuffer();
      var2.append(this.toSimpleString());

      for(int var1 = 0; var1 < this.mValues.size(); ++var1) {
         StringBuilder var3 = new StringBuilder();
         var3.append(((Entry)this.mValues.get(var1)).toString());
         var3.append(" ");
         var2.append(var3.toString());
      }

      return var2.toString();
   }

   public static enum Rounding {

      // $FF: synthetic field
      private static final DataSet.Rounding[] $VALUES = new DataSet.Rounding[]{UP, DOWN, CLOSEST};
      CLOSEST("CLOSEST", 2),
      DOWN("DOWN", 1),
      UP("UP", 0);


      private Rounding(String var1, int var2) {}
   }
}
