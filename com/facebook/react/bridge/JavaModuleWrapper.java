package com.facebook.react.bridge;

import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.JSInstance;
import com.facebook.react.bridge.JavaMethodWrapper;
import com.facebook.react.bridge.ModuleHolder;
import com.facebook.react.bridge.NativeMap;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReactModuleWithSpec;
import com.facebook.react.bridge.ReadableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.systrace.SystraceMessage;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

@DoNotStrip
public class JavaModuleWrapper {

   private final ArrayList<JavaModuleWrapper.MethodDescriptor> mDescs;
   private final JSInstance mJSInstance;
   private final ArrayList<NativeModule.NativeMethod> mMethods;
   private final Class<? extends NativeModule> mModuleClass;
   private final ModuleHolder mModuleHolder;


   public JavaModuleWrapper(JSInstance var1, Class<? extends NativeModule> var2, ModuleHolder var3) {
      this.mJSInstance = var1;
      this.mModuleHolder = var3;
      this.mModuleClass = var2;
      this.mMethods = new ArrayList();
      this.mDescs = new ArrayList();
   }

   @DoNotStrip
   private void findMethods() {
      com.facebook.systrace.Systrace.beginSection(0L, "findMethods");
      HashSet var5 = new HashSet();
      Class var3 = this.mModuleClass;
      Class var4 = this.mModuleClass.getSuperclass();
      if(ReactModuleWithSpec.class.isAssignableFrom(var4)) {
         var3 = var4;
      }

      Method[] var10 = var3.getDeclaredMethods();
      int var2 = var10.length;

      for(int var1 = 0; var1 < var2; ++var1) {
         Method var6 = var10[var1];
         ReactMethod var8 = (ReactMethod)var6.getAnnotation(ReactMethod.class);
         if(var8 != null) {
            String var9 = var6.getName();
            if(var5.contains(var9)) {
               StringBuilder var11 = new StringBuilder();
               var11.append("Java Module ");
               var11.append(this.getName());
               var11.append(" method name already registered: ");
               var11.append(var9);
               throw new IllegalArgumentException(var11.toString());
            }

            JavaModuleWrapper.MethodDescriptor var7 = new JavaModuleWrapper.MethodDescriptor();
            JavaMethodWrapper var12 = new JavaMethodWrapper(this, var6, var8.isBlockingSynchronousMethod());
            var7.name = var9;
            var7.type = var12.getType();
            if(var7.type == "sync") {
               var7.signature = var12.getSignature();
               var7.method = var6;
            }

            this.mMethods.add(var12);
            this.mDescs.add(var7);
         }
      }

      com.facebook.systrace.Systrace.endSection(0L);
   }

   @Nullable
   @DoNotStrip
   public NativeMap getConstants() {
      if(!this.mModuleHolder.getHasConstants()) {
         return null;
      } else {
         String var1 = this.getName();
         SystraceMessage.beginSection(0L, "JavaModuleWrapper.getConstants").arg("moduleName", var1).flush();
         ReactMarker.logMarker(ReactMarkerConstants.GET_CONSTANTS_START, var1);
         BaseJavaModule var2 = this.getModule();
         com.facebook.systrace.Systrace.beginSection(0L, "module.getConstants");
         Map var6 = var2.getConstants();
         com.facebook.systrace.Systrace.endSection(0L);
         com.facebook.systrace.Systrace.beginSection(0L, "create WritableNativeMap");
         ReactMarker.logMarker(ReactMarkerConstants.CONVERT_CONSTANTS_START, var1);

         WritableNativeMap var5;
         try {
            var5 = Arguments.makeNativeMap(var6);
         } finally {
            ReactMarker.logMarker(ReactMarkerConstants.CONVERT_CONSTANTS_END);
            com.facebook.systrace.Systrace.endSection(0L);
            ReactMarker.logMarker(ReactMarkerConstants.GET_CONSTANTS_END);
            SystraceMessage.endSection(0L).flush();
         }

         return var5;
      }
   }

   @DoNotStrip
   public List<JavaModuleWrapper.MethodDescriptor> getMethodDescriptors() {
      if(this.mDescs.isEmpty()) {
         this.findMethods();
      }

      return this.mDescs;
   }

   @DoNotStrip
   public BaseJavaModule getModule() {
      return (BaseJavaModule)this.mModuleHolder.getModule();
   }

   @DoNotStrip
   public String getName() {
      return this.mModuleHolder.getName();
   }

   @DoNotStrip
   public void invoke(int var1, ReadableNativeArray var2) {
      if(this.mMethods != null) {
         if(var1 < this.mMethods.size()) {
            ((NativeModule.NativeMethod)this.mMethods.get(var1)).invoke(this.mJSInstance, var2);
         }
      }
   }

   @DoNotStrip
   public class MethodDescriptor {

      @DoNotStrip
      Method method;
      @DoNotStrip
      String name;
      @DoNotStrip
      String signature;
      @DoNotStrip
      String type;


   }
}
