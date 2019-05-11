package com.facebook.litho;

import android.content.Context;
import android.os.Build.VERSION;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentHost;
import com.facebook.litho.ComponentLifecycle;

class HostComponent extends Component {

   protected HostComponent() {
      super("HostComponent");
   }

   static Component create() {
      return new HostComponent();
   }

   public ComponentLifecycle.MountType getMountType() {
      return ComponentLifecycle.MountType.VIEW;
   }

   public boolean isEquivalentTo(Component var1) {
      return this == var1;
   }

   protected Object onCreateMountContent(Context var1) {
      return new ComponentHost(var1);
   }

   protected void onMount(ComponentContext var1, Object var2) {
      ComponentHost var3 = (ComponentHost)var2;
      if(VERSION.SDK_INT >= 11) {
         var3.setAlpha(1.0F);
      }

   }

   protected int poolSize() {
      return 45;
   }

   protected boolean shouldUpdate(Component var1, Component var2) {
      return true;
   }
}
