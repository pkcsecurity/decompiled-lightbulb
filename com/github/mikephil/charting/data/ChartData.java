package com.github.mikephil.charting.data;

import android.graphics.Typeface;
import android.util.Log;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ChartData<T extends Object & IDataSet<? extends Entry>> {

   protected List<T> mDataSets;
   protected float mLeftAxisMax = -3.4028235E38F;
   protected float mLeftAxisMin = Float.MAX_VALUE;
   protected float mRightAxisMax = -3.4028235E38F;
   protected float mRightAxisMin = Float.MAX_VALUE;
   protected float mXMax = -3.4028235E38F;
   protected float mXMin = Float.MAX_VALUE;
   protected float mYMax = -3.4028235E38F;
   protected float mYMin = Float.MAX_VALUE;


   public ChartData() {
      this.mDataSets = new ArrayList();
   }

   public ChartData(List<T> var1) {
      this.mDataSets = var1;
      this.notifyDataChanged();
   }

   public ChartData(T ... var1) {
      this.mDataSets = this.arrayToList(var1);
      this.notifyDataChanged();
   }

   private List<T> arrayToList(T[] var1) {
      ArrayList var4 = new ArrayList();
      int var3 = var1.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         var4.add(var1[var2]);
      }

      return var4;
   }

   public void addDataSet(T var1) {
      if(var1 != null) {
         this.calcMinMax(var1);
         this.mDataSets.add(var1);
      }
   }

   public void addEntry(Entry var1, int var2) {
      if(this.mDataSets.size() > var2 && var2 >= 0) {
         IDataSet var3 = (IDataSet)this.mDataSets.get(var2);
         if(var3.addEntry(var1)) {
            this.calcMinMax(var1, var3.getAxisDependency());
         }
      } else {
         Log.e("addEntry", "Cannot add Entry because dataSetIndex too high or too low.");
      }
   }

   protected void calcMinMax() {
      if(this.mDataSets != null) {
         this.mYMax = -3.4028235E38F;
         this.mYMin = Float.MAX_VALUE;
         this.mXMax = -3.4028235E38F;
         this.mXMin = Float.MAX_VALUE;
         Iterator var1 = this.mDataSets.iterator();

         while(var1.hasNext()) {
            this.calcMinMax((IDataSet)var1.next());
         }

         this.mLeftAxisMax = -3.4028235E38F;
         this.mLeftAxisMin = Float.MAX_VALUE;
         this.mRightAxisMax = -3.4028235E38F;
         this.mRightAxisMin = Float.MAX_VALUE;
         IDataSet var3 = this.getFirstLeft(this.mDataSets);
         IDataSet var2;
         if(var3 != null) {
            this.mLeftAxisMax = var3.getYMax();
            this.mLeftAxisMin = var3.getYMin();
            var1 = this.mDataSets.iterator();

            while(var1.hasNext()) {
               var2 = (IDataSet)var1.next();
               if(var2.getAxisDependency() == YAxis.AxisDependency.LEFT) {
                  if(var2.getYMin() < this.mLeftAxisMin) {
                     this.mLeftAxisMin = var2.getYMin();
                  }

                  if(var2.getYMax() > this.mLeftAxisMax) {
                     this.mLeftAxisMax = var2.getYMax();
                  }
               }
            }
         }

         var3 = this.getFirstRight(this.mDataSets);
         if(var3 != null) {
            this.mRightAxisMax = var3.getYMax();
            this.mRightAxisMin = var3.getYMin();
            var1 = this.mDataSets.iterator();

            while(var1.hasNext()) {
               var2 = (IDataSet)var1.next();
               if(var2.getAxisDependency() == YAxis.AxisDependency.RIGHT) {
                  if(var2.getYMin() < this.mRightAxisMin) {
                     this.mRightAxisMin = var2.getYMin();
                  }

                  if(var2.getYMax() > this.mRightAxisMax) {
                     this.mRightAxisMax = var2.getYMax();
                  }
               }
            }
         }

      }
   }

   protected void calcMinMax(Entry var1, YAxis.AxisDependency var2) {
      if(this.mYMax < var1.getY()) {
         this.mYMax = var1.getY();
      }

      if(this.mYMin > var1.getY()) {
         this.mYMin = var1.getY();
      }

      if(this.mXMax < var1.getX()) {
         this.mXMax = var1.getX();
      }

      if(this.mXMin > var1.getX()) {
         this.mXMin = var1.getX();
      }

      if(var2 == YAxis.AxisDependency.LEFT) {
         if(this.mLeftAxisMax < var1.getY()) {
            this.mLeftAxisMax = var1.getY();
         }

         if(this.mLeftAxisMin > var1.getY()) {
            this.mLeftAxisMin = var1.getY();
            return;
         }
      } else {
         if(this.mRightAxisMax < var1.getY()) {
            this.mRightAxisMax = var1.getY();
         }

         if(this.mRightAxisMin > var1.getY()) {
            this.mRightAxisMin = var1.getY();
         }
      }

   }

   protected void calcMinMax(T var1) {
      if(this.mYMax < var1.getYMax()) {
         this.mYMax = var1.getYMax();
      }

      if(this.mYMin > var1.getYMin()) {
         this.mYMin = var1.getYMin();
      }

      if(this.mXMax < var1.getXMax()) {
         this.mXMax = var1.getXMax();
      }

      if(this.mXMin > var1.getXMin()) {
         this.mXMin = var1.getXMin();
      }

      if(var1.getAxisDependency() == YAxis.AxisDependency.LEFT) {
         if(this.mLeftAxisMax < var1.getYMax()) {
            this.mLeftAxisMax = var1.getYMax();
         }

         if(this.mLeftAxisMin > var1.getYMin()) {
            this.mLeftAxisMin = var1.getYMin();
            return;
         }
      } else {
         if(this.mRightAxisMax < var1.getYMax()) {
            this.mRightAxisMax = var1.getYMax();
         }

         if(this.mRightAxisMin > var1.getYMin()) {
            this.mRightAxisMin = var1.getYMin();
         }
      }

   }

   public void calcMinMaxY(float var1, float var2) {
      Iterator var3 = this.mDataSets.iterator();

      while(var3.hasNext()) {
         ((IDataSet)var3.next()).calcMinMaxY(var1, var2);
      }

      this.calcMinMax();
   }

   public void clearValues() {
      if(this.mDataSets != null) {
         this.mDataSets.clear();
      }

      this.notifyDataChanged();
   }

   public boolean contains(T var1) {
      Iterator var2 = this.mDataSets.iterator();

      do {
         if(!var2.hasNext()) {
            return false;
         }
      } while(!((IDataSet)var2.next()).equals(var1));

      return true;
   }

   public int[] getColors() {
      if(this.mDataSets == null) {
         return null;
      } else {
         byte var3 = 0;
         int var1 = 0;

         int var2;
         for(var2 = 0; var1 < this.mDataSets.size(); ++var1) {
            var2 += ((IDataSet)this.mDataSets.get(var1)).getColors().size();
         }

         int[] var4 = new int[var2];
         var2 = 0;

         for(var1 = var3; var1 < this.mDataSets.size(); ++var1) {
            for(Iterator var5 = ((IDataSet)this.mDataSets.get(var1)).getColors().iterator(); var5.hasNext(); ++var2) {
               var4[var2] = ((Integer)var5.next()).intValue();
            }
         }

         return var4;
      }
   }

   public T getDataSetByIndex(int var1) {
      return this.mDataSets != null && var1 >= 0 && var1 < this.mDataSets.size()?(IDataSet)this.mDataSets.get(var1):null;
   }

   public T getDataSetByLabel(String var1, boolean var2) {
      int var3 = this.getDataSetIndexByLabel(this.mDataSets, var1, var2);
      return var3 >= 0 && var3 < this.mDataSets.size()?(IDataSet)this.mDataSets.get(var3):null;
   }

   public int getDataSetCount() {
      return this.mDataSets == null?0:this.mDataSets.size();
   }

   public T getDataSetForEntry(Entry var1) {
      if(var1 == null) {
         return null;
      } else {
         for(int var2 = 0; var2 < this.mDataSets.size(); ++var2) {
            IDataSet var4 = (IDataSet)this.mDataSets.get(var2);

            for(int var3 = 0; var3 < var4.getEntryCount(); ++var3) {
               if(var1.equalTo(var4.getEntryForXValue(var1.getX(), var1.getY()))) {
                  return var4;
               }
            }
         }

         return null;
      }
   }

   protected int getDataSetIndexByLabel(List<T> var1, String var2, boolean var3) {
      int var4 = 0;
      byte var5 = 0;
      if(var3) {
         for(var4 = var5; var4 < var1.size(); ++var4) {
            if(var2.equalsIgnoreCase(((IDataSet)var1.get(var4)).getLabel())) {
               return var4;
            }
         }
      } else {
         while(var4 < var1.size()) {
            if(var2.equals(((IDataSet)var1.get(var4)).getLabel())) {
               return var4;
            }

            ++var4;
         }
      }

      return -1;
   }

   public String[] getDataSetLabels() {
      String[] var2 = new String[this.mDataSets.size()];

      for(int var1 = 0; var1 < this.mDataSets.size(); ++var1) {
         var2[var1] = ((IDataSet)this.mDataSets.get(var1)).getLabel();
      }

      return var2;
   }

   public List<T> getDataSets() {
      return this.mDataSets;
   }

   public int getEntryCount() {
      Iterator var2 = this.mDataSets.iterator();

      int var1;
      for(var1 = 0; var2.hasNext(); var1 += ((IDataSet)var2.next()).getEntryCount()) {
         ;
      }

      return var1;
   }

   public Entry getEntryForHighlight(Highlight var1) {
      return var1.getDataSetIndex() >= this.mDataSets.size()?null:((IDataSet)this.mDataSets.get(var1.getDataSetIndex())).getEntryForXValue(var1.getX(), var1.getY());
   }

   protected T getFirstLeft(List<T> var1) {
      Iterator var3 = var1.iterator();

      IDataSet var2;
      do {
         if(!var3.hasNext()) {
            return null;
         }

         var2 = (IDataSet)var3.next();
      } while(var2.getAxisDependency() != YAxis.AxisDependency.LEFT);

      return var2;
   }

   public T getFirstRight(List<T> var1) {
      Iterator var3 = var1.iterator();

      IDataSet var2;
      do {
         if(!var3.hasNext()) {
            return null;
         }

         var2 = (IDataSet)var3.next();
      } while(var2.getAxisDependency() != YAxis.AxisDependency.RIGHT);

      return var2;
   }

   public int getIndexOfDataSet(T var1) {
      return this.mDataSets.indexOf(var1);
   }

   public T getMaxEntryCountSet() {
      if(this.mDataSets != null && !this.mDataSets.isEmpty()) {
         IDataSet var1 = (IDataSet)this.mDataSets.get(0);
         Iterator var3 = this.mDataSets.iterator();

         while(var3.hasNext()) {
            IDataSet var2 = (IDataSet)var3.next();
            if(var2.getEntryCount() > var1.getEntryCount()) {
               var1 = var2;
            }
         }

         return var1;
      } else {
         return null;
      }
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

   public float getYMax(YAxis.AxisDependency var1) {
      return var1 == YAxis.AxisDependency.LEFT?(this.mLeftAxisMax == -3.4028235E38F?this.mRightAxisMax:this.mLeftAxisMax):(this.mRightAxisMax == -3.4028235E38F?this.mLeftAxisMax:this.mRightAxisMax);
   }

   public float getYMin() {
      return this.mYMin;
   }

   public float getYMin(YAxis.AxisDependency var1) {
      return var1 == YAxis.AxisDependency.LEFT?(this.mLeftAxisMin == Float.MAX_VALUE?this.mRightAxisMin:this.mLeftAxisMin):(this.mRightAxisMin == Float.MAX_VALUE?this.mLeftAxisMin:this.mRightAxisMin);
   }

   public boolean isHighlightEnabled() {
      Iterator var1 = this.mDataSets.iterator();

      do {
         if(!var1.hasNext()) {
            return true;
         }
      } while(((IDataSet)var1.next()).isHighlightEnabled());

      return false;
   }

   public void notifyDataChanged() {
      this.calcMinMax();
   }

   public boolean removeDataSet(int var1) {
      return var1 < this.mDataSets.size() && var1 >= 0?this.removeDataSet((IDataSet)this.mDataSets.get(var1)):false;
   }

   public boolean removeDataSet(T var1) {
      if(var1 == null) {
         return false;
      } else {
         boolean var2 = this.mDataSets.remove(var1);
         if(var2) {
            this.calcMinMax();
         }

         return var2;
      }
   }

   public boolean removeEntry(float var1, int var2) {
      if(var2 >= this.mDataSets.size()) {
         return false;
      } else {
         Entry var3 = ((IDataSet)this.mDataSets.get(var2)).getEntryForXValue(var1, Float.NaN);
         return var3 == null?false:this.removeEntry(var3, var2);
      }
   }

   public boolean removeEntry(Entry var1, int var2) {
      if(var1 != null) {
         if(var2 >= this.mDataSets.size()) {
            return false;
         } else {
            IDataSet var4 = (IDataSet)this.mDataSets.get(var2);
            if(var4 != null) {
               boolean var3 = var4.removeEntry(var1);
               if(var3) {
                  this.calcMinMax();
               }

               return var3;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   public void setDrawValues(boolean var1) {
      Iterator var2 = this.mDataSets.iterator();

      while(var2.hasNext()) {
         ((IDataSet)var2.next()).setDrawValues(var1);
      }

   }

   public void setHighlightEnabled(boolean var1) {
      Iterator var2 = this.mDataSets.iterator();

      while(var2.hasNext()) {
         ((IDataSet)var2.next()).setHighlightEnabled(var1);
      }

   }

   public void setValueFormatter(IValueFormatter var1) {
      if(var1 != null) {
         Iterator var2 = this.mDataSets.iterator();

         while(var2.hasNext()) {
            ((IDataSet)var2.next()).setValueFormatter(var1);
         }

      }
   }

   public void setValueTextColor(int var1) {
      Iterator var2 = this.mDataSets.iterator();

      while(var2.hasNext()) {
         ((IDataSet)var2.next()).setValueTextColor(var1);
      }

   }

   public void setValueTextColors(List<Integer> var1) {
      Iterator var2 = this.mDataSets.iterator();

      while(var2.hasNext()) {
         ((IDataSet)var2.next()).setValueTextColors(var1);
      }

   }

   public void setValueTextSize(float var1) {
      Iterator var2 = this.mDataSets.iterator();

      while(var2.hasNext()) {
         ((IDataSet)var2.next()).setValueTextSize(var1);
      }

   }

   public void setValueTypeface(Typeface var1) {
      Iterator var2 = this.mDataSets.iterator();

      while(var2.hasNext()) {
         ((IDataSet)var2.next()).setValueTypeface(var1);
      }

   }
}
