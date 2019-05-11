package com.github.mikephil.charting.utils;

import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class FileUtils {

   private static final String LOG = "MPChart-FileUtils";


   public static List<BarEntry> loadBarEntriesFromAssets(AssetManager param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public static List<Entry> loadEntriesFromAssets(AssetManager param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   public static List<Entry> loadEntriesFromFile(String param0) {
      // $FF: Couldn't be decompiled
   }

   public static void saveToSdCard(List<Entry> var0, String var1) {
      File var7 = new File(Environment.getExternalStorageDirectory(), var1);
      if(!var7.exists()) {
         try {
            var7.createNewFile();
         } catch (IOException var4) {
            Log.e("MPChart-FileUtils", var4.toString());
         }
      }

      try {
         BufferedWriter var8 = new BufferedWriter(new FileWriter(var7, true));
         Iterator var6 = var0.iterator();

         while(var6.hasNext()) {
            Entry var2 = (Entry)var6.next();
            StringBuilder var3 = new StringBuilder();
            var3.append(var2.getY());
            var3.append("#");
            var3.append(var2.getX());
            var8.append(var3.toString());
            var8.newLine();
         }

         var8.close();
      } catch (IOException var5) {
         Log.e("MPChart-FileUtils", var5.toString());
      }
   }
}
