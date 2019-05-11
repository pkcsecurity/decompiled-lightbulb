package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.text.DecimalFormat;

public class DefaultValueFormatter implements IValueFormatter {

   protected int mDecimalDigits;
   protected DecimalFormat mFormat;


   public DefaultValueFormatter(int var1) {
      this.setup(var1);
   }

   public int getDecimalDigits() {
      return this.mDecimalDigits;
   }

   public String getFormattedValue(float var1, Entry var2, int var3, ViewPortHandler var4) {
      return this.mFormat.format((double)var1);
   }

   public void setup(int var1) {
      this.mDecimalDigits = var1;
      StringBuffer var3 = new StringBuffer();

      for(int var2 = 0; var2 < var1; ++var2) {
         if(var2 == 0) {
            var3.append(".");
         }

         var3.append("0");
      }

      StringBuilder var4 = new StringBuilder();
      var4.append("###,###,###,##0");
      var4.append(var3.toString());
      this.mFormat = new DecimalFormat(var4.toString());
   }
}
