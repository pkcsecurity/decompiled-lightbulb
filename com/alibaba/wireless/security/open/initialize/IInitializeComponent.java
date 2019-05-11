package com.alibaba.wireless.security.open.initialize;

import android.content.Context;
import com.alibaba.wireless.security.open.SecException;

public interface IInitializeComponent {

   int initialize(Context var1) throws SecException;

   void initializeAsync(Context var1);

   boolean isSoValid(Context var1) throws SecException;

   void loadLibraryAsync(Context var1) throws SecException;

   int loadLibrarySync(Context var1) throws SecException;

   void registerInitFinishListener(IInitializeComponent.IInitFinishListener var1) throws SecException;

   void unregisterInitFinishListener(IInitializeComponent.IInitFinishListener var1) throws SecException;

   public interface IInitFinishListener {

      void onError();

      void onSuccess();
   }
}
