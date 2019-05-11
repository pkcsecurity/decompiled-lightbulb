package com.tuya.smart.litho.mist.api;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.tuya.smart.litho.mist.api.Config;
import com.tuya.smart.litho.mist.api.Env;
import com.tuya.smart.litho.mist.api.TemplateModel;
import com.tuya.smart.litho.mist.core.TemplateSystem;
import com.tuya.smart.litho.mist.flex.MistItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MistCore {

   private static Context application;
   protected static MistCore sInstance;
   private static final Object slock = new Object();
   Config mGlobalConfig;


   public static Context getApplication() {
      return application;
   }

   public static MistCore getInstance() {
      // $FF: Couldn't be decompiled
   }

   public boolean checkLocalTemplates(Context var1, Env var2, List<TemplateModel> var3) {
      return true;
   }

   public MistItem createMistItem(Context var1, TemplateModel var2, Env var3, Object var4) {
      return this.createMistItem(var1, var2.getName(), var2.getInfo(), var3, var4);
   }

   public MistItem createMistItem(Context var1, String var2, String var3, Env var4, Object var5) {
      return new MistItem(var1, var4, var2, var3, var5);
   }

   public View createView(Context var1, Env var2, TemplateModel var3, Object var4) {
      return this.inflateTemplateModel(var1, var2, var3, (ViewGroup)null, false);
   }

   public View createView(Context var1, Env var2, TemplateModel var3, Object var4, View var5) {
      return var5 != null?var5:this.inflateTemplateModel(var1, var2, var3, (ViewGroup)null, false);
   }

   public boolean downloadTemplate(Context var1, Env var2, List<TemplateModel> var3) {
      return TemplateSystem.syncDownloadTemplates(var1, var2, var3);
   }

   public boolean downloadTemplate(Env var1, TemplateModel var2) {
      ArrayList var3 = new ArrayList();
      var3.add(var2);
      return this.downloadTemplate(var1, (List)var3);
   }

   public boolean downloadTemplate(Env var1, List<TemplateModel> var2) {
      return this.downloadTemplate((Context)null, var1, var2);
   }

   public Config getConfig() {
      return this.mGlobalConfig;
   }

   public View inflateTemplateModel(Context var1, Env var2, TemplateModel var3, ViewGroup var4) {
      return this.inflateTemplateModel(var1, var2, var3, var4, false);
   }

   public View inflateTemplateModel(Context var1, Env var2, TemplateModel var3, ViewGroup var4, boolean var5) {
      if(!var3.isLoaded() && !this.checkLocalTemplates(var1, var2, Collections.singletonList(var3))) {
         if(this.isDebug()) {
            Toast.makeText(var1, "TemplateModel is not ready.", 1).show();
         }

         return null;
      } else {
         return null;
      }
   }

   public void init(Config var1, Context var2) {
      application = var2;
      if(this.mGlobalConfig == null) {
         this.mGlobalConfig = var1;
      }

   }

   public boolean isDebug() {
      return this.getConfig() == null || this.getConfig().isDebug();
   }
}
