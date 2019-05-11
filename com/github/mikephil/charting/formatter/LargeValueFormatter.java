package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.text.DecimalFormat;

public class LargeValueFormatter implements IAxisValueFormatter, IValueFormatter {

   private static final int MAX_LENGTH = 5;
   private static String[] SUFFIX = new String[]{"", "k", "m", "b", "t"};
   private DecimalFormat mFormat;
   private String mText;


   public LargeValueFormatter() {
      this.mText = "";
      this.mFormat = new DecimalFormat("###E00");
   }

   public LargeValueFormatter(String var1) {
      this();
      this.mText = var1;
   }

   private String makePretty(double var1) {
      String var5 = this.mFormat.format(var1);
      int var3 = Character.getNumericValue(var5.charAt(var5.length() - 1));
      int var4 = Character.getNumericValue(var5.charAt(var5.length() - 2));
      StringBuilder var6 = new StringBuilder();
      var6.append(var4);
      var6.append("");
      var6.append(var3);
      var3 = Integer.valueOf(var6.toString()).intValue();

      for(var5 = var5.replaceAll("E[0-9][0-9]", SUFFIX[var3 / 3]); var5.length() > 5 || var5.matches("[0-9]+\\.[a-z]"); var5 = var6.toString()) {
         var6 = new StringBuilder();
         var6.append(var5.substring(0, var5.length() - 2));
         var6.append(var5.substring(var5.length() - 1));
      }

      return var5;
   }

   public int getDecimalDigits() {
      return 0;
   }

   public String getFormattedValue(float var1, AxisBase var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append(this.makePretty((double)var1));
      var3.append(this.mText);
      return var3.toString();
   }

   public String getFormattedValue(float var1, Entry var2, int var3, ViewPortHandler var4) {
      StringBuilder var5 = new StringBuilder();
      var5.append(this.makePretty((double)var1));
      var5.append(this.mText);
      return var5.toString();
   }

   public void setAppendix(String var1) {
      this.mText = var1;
   }

   public void setSuffix(String[] var1) {
      SUFFIX = var1;
   }
}
