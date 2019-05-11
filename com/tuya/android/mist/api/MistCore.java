package com.tuya.android.mist.api;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.tuya.android.mist.api.Config;
import com.tuya.android.mist.api.Env;
import com.tuya.android.mist.api.TemplateModel;
import com.tuya.android.mist.api.TemplateStatus;
import com.tuya.android.mist.core.Actor;
import com.tuya.android.mist.core.MistLayoutInflater;
import com.tuya.android.mist.core.MistViewBinder;
import com.tuya.android.mist.core.TemplateModelImpl;
import com.tuya.android.mist.core.internal.TemplateDownloader;
import com.tuya.android.mist.core.internal.TemplateSystem;
import com.tuya.android.mist.flex.MistItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MistCore {

   public static final String ACTION_TEMPLATE_UPDATE = "com.tuya.mist.update";
   public static final String KEY_TEMPLATE_NAME = "template_name";
   protected static MistCore sInstance;
   private static final Object slock = new Object();
   Config mGlobalConfig;


   public static MistCore getInstance() {
      // $FF: Couldn't be decompiled
   }

   public void bindView(Env var1, TemplateModel var2, View var3, Object var4) {
      MistViewBinder.from().bind(var1, var2, var4, var3, (Actor)null);
   }

   public void bindView(TemplateModel var1, View var2, Object var3, Actor var4) {
      Env var5;
      if(var1.isLoaded()) {
         var5 = ((TemplateModelImpl)var1.getImplement()).getEnv();
      } else {
         MistViewBinder.from();
         var5 = MistViewBinder.obtainEnvFromActor(var4, var2.getContext().getPackageName());
      }

      MistViewBinder.from().bind(var5, var1, var3, var2, var4);
   }

   public boolean checkLocalTemplates(Context var1, Env var2, List<TemplateModel> var3) {
      return TemplateDownloader.checkLocalTemplates(var1, var2, var3, (Map)null) != TemplateStatus.FAIL;
   }

   public MistItem createMistItem(Context var1, TemplateModel var2, Env var3, Object var4) {
      return this.createMistItem(var1, var2.getName(), var2.getInfo(), var3, var4);
   }

   public MistItem createMistItem(Context var1, String var2, String var3, Env var4, Object var5) {
      return new MistItem(var1, var4, var2, var3, var5);
   }

   public View createView(Context var1, Env var2, TemplateModel var3, Object var4) {
      View var5 = this.inflateTemplateModel(var1, var2, var3, (ViewGroup)null, false);
      MistViewBinder.from().bind(var2, var3, var4, var5, new Actor(var3));
      return var5;
   }

   public View createView(Context var1, Env var2, TemplateModel var3, Object var4, View var5) {
      if(var5 == null) {
         var5 = this.inflateTemplateModel(var1, var2, var3, (ViewGroup)null, false);
      }

      MistViewBinder.from().bind(var2, var3, var4, var5, new Actor(var3));
      return var5;
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
         return MistLayoutInflater.from(var1).inflate(var3.getImplement(), var4, var5);
      }
   }

   public void init(Config var1) {
      if(this.mGlobalConfig == null) {
         this.mGlobalConfig = var1;
      }

   }

   public boolean isDebug() {
      return this.getConfig() == null || this.getConfig().isDebug();
   }
}
