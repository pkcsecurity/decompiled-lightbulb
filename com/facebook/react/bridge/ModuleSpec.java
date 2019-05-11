package com.facebook.react.bridge;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import java.lang.reflect.Constructor;
import javax.annotation.Nullable;
import javax.inject.Provider;

public class ModuleSpec {

   private static final Class[] CONTEXT_SIGNATURE = new Class[]{ReactApplicationContext.class};
   private static final Class[] EMPTY_SIGNATURE = new Class[0];
   private final Provider<? extends NativeModule> mProvider;
   @Nullable
   private final Class<? extends NativeModule> mType;


   private ModuleSpec(@Nullable Class<? extends NativeModule> var1, Provider<? extends NativeModule> var2) {
      this.mType = var1;
      this.mProvider = var2;
   }

   public static ModuleSpec nativeModuleSpec(Class<? extends NativeModule> var0, Provider<? extends NativeModule> var1) {
      return new ModuleSpec(var0, var1);
   }

   public static ModuleSpec simple(final Class<? extends NativeModule> var0) {
      return new ModuleSpec(var0, new ModuleSpec.ConstructorProvider(var0, EMPTY_SIGNATURE) {
         public NativeModule get() {
            try {
               NativeModule var1 = (NativeModule)this.getConstructor(var0, ModuleSpec.EMPTY_SIGNATURE).newInstance(new Object[0]);
               return var1;
            } catch (Exception var3) {
               StringBuilder var2 = new StringBuilder();
               var2.append("ModuleSpec with class: ");
               var2.append(var0.getName());
               throw new RuntimeException(var2.toString(), var3);
            }
         }
      });
   }

   public static ModuleSpec simple(final Class<? extends NativeModule> var0, final ReactApplicationContext var1) {
      return new ModuleSpec(var0, new ModuleSpec.ConstructorProvider(var0, CONTEXT_SIGNATURE) {
         public NativeModule get() {
            try {
               NativeModule var1x = (NativeModule)this.getConstructor(var0, ModuleSpec.CONTEXT_SIGNATURE).newInstance(new Object[]{var1});
               return var1x;
            } catch (Exception var3) {
               StringBuilder var2 = new StringBuilder();
               var2.append("ModuleSpec with class: ");
               var2.append(var0.getName());
               throw new RuntimeException(var2.toString(), var3);
            }
         }
      });
   }

   public static ModuleSpec viewManagerSpec(Provider<? extends NativeModule> var0) {
      return new ModuleSpec((Class)null, var0);
   }

   public Provider<? extends NativeModule> getProvider() {
      return this.mProvider;
   }

   @Nullable
   public Class<? extends NativeModule> getType() {
      return this.mType;
   }

   abstract static class ConstructorProvider implements Provider<NativeModule> {

      @Nullable
      protected Constructor<? extends NativeModule> mConstructor;


      public ConstructorProvider(Class<? extends NativeModule> var1, Class[] var2) {}

      protected Constructor<? extends NativeModule> getConstructor(Class<? extends NativeModule> var1, Class[] var2) throws NoSuchMethodException {
         return this.mConstructor != null?this.mConstructor:var1.getConstructor(var2);
      }
   }
}
