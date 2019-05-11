package com.alibaba.wireless.security.open.initialize;

import android.content.Context;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.initialize.IInitializeComponent;
import com.alibaba.wireless.security.open.initialize.b;

public class a implements IInitializeComponent {

   b a = new b();


   public int initialize(Context var1) throws SecException {
      return this.loadLibrarySync(var1);
   }

   public void initializeAsync(Context var1) {
      try {
         this.loadLibraryAsync(var1);
      } catch (SecException var2) {
         var2.printStackTrace();
      }
   }

   public boolean isSoValid(Context var1) throws SecException {
      return this.a.a(var1);
   }

   public void loadLibraryAsync(Context var1) throws SecException {
      this.a.b(var1, (String)null, true);
   }

   public int loadLibrarySync(Context var1) throws SecException {
      return this.a.a(var1, (String)null, true);
   }

   public void registerInitFinishListener(IInitializeComponent.IInitFinishListener var1) throws SecException {
      this.a.a(var1);
   }

   public void unregisterInitFinishListener(IInitializeComponent.IInitFinishListener var1) throws SecException {
      this.a.b(var1);
   }
}
