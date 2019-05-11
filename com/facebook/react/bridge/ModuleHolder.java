package com.facebook.react.bridge;

import com.facebook.debug.holder.PrinterHolder;
import com.facebook.debug.tags.ReactDebugOverlayTags;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.module.model.ReactModuleInfo;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.inject.Provider;

@DoNotStrip
public class ModuleHolder {

   private static final AtomicInteger sInstanceKeyCounter = new AtomicInteger(1);
   private final boolean mCanOverrideExistingModule;
   private final boolean mHasConstants;
   @GuardedBy
   private boolean mInitializable;
   private final int mInstanceKey;
   @GuardedBy
   private boolean mIsCreating;
   @GuardedBy
   private boolean mIsInitializing;
   @Nullable
   @GuardedBy
   private NativeModule mModule;
   private final String mName;
   @Nullable
   private Provider<? extends NativeModule> mProvider;


   public ModuleHolder(NativeModule var1) {
      this.mInstanceKey = sInstanceKeyCounter.getAndIncrement();
      this.mName = var1.getName();
      this.mCanOverrideExistingModule = var1.canOverrideExistingModule();
      this.mHasConstants = true;
      this.mModule = var1;
      PrinterHolder.getPrinter().logMessage(ReactDebugOverlayTags.NATIVE_MODULE, "NativeModule init: %s", new Object[]{this.mName});
   }

   public ModuleHolder(ReactModuleInfo var1, Provider<? extends NativeModule> var2) {
      this.mInstanceKey = sInstanceKeyCounter.getAndIncrement();
      this.mName = var1.name();
      this.mCanOverrideExistingModule = var1.canOverrideExistingModule();
      this.mHasConstants = var1.hasConstants();
      this.mProvider = var2;
      if(var1.needsEagerInit()) {
         this.mModule = this.create();
      }

   }

   private NativeModule create() {
      // $FF: Couldn't be decompiled
   }

   private void doInitialize(NativeModule param1) {
      // $FF: Couldn't be decompiled
   }

   public void destroy() {
      synchronized(this){}

      try {
         if(this.mModule != null) {
            this.mModule.onCatalystInstanceDestroy();
         }
      } finally {
         ;
      }

   }

   public boolean getCanOverrideExistingModule() {
      return this.mCanOverrideExistingModule;
   }

   public boolean getHasConstants() {
      return this.mHasConstants;
   }

   @DoNotStrip
   public NativeModule getModule() {
      // $FF: Couldn't be decompiled
   }

   @DoNotStrip
   public String getName() {
      return this.mName;
   }

   boolean hasInstance() {
      synchronized(this){}
      boolean var4 = false;

      NativeModule var2;
      try {
         var4 = true;
         var2 = this.mModule;
         var4 = false;
      } finally {
         if(var4) {
            ;
         }
      }

      boolean var1;
      if(var2 != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   void markInitializable() {
      // $FF: Couldn't be decompiled
   }
}
