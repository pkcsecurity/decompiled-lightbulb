package com.facebook.debug.holder;

import com.facebook.debug.holder.NoopPrinter;
import com.facebook.debug.holder.Printer;

public class PrinterHolder {

   private static Printer sPrinter = NoopPrinter.INSTANCE;


   public static Printer getPrinter() {
      return sPrinter;
   }

   public static void setPrinter(Printer var0) {
      if(var0 == null) {
         sPrinter = NoopPrinter.INSTANCE;
      } else {
         sPrinter = var0;
      }
   }
}
