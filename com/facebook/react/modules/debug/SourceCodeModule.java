package com.facebook.react.modules.debug;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.module.annotations.ReactModule;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

@ReactModule(
   name = "SourceCode"
)
public class SourceCodeModule extends BaseJavaModule {

   public static final String NAME = "SourceCode";
   private final ReactContext mReactContext;


   public SourceCodeModule(ReactContext var1) {
      this.mReactContext = var1;
   }

   @Nullable
   public Map<String, Object> getConstants() {
      HashMap var1 = new HashMap();
      var1.put("scriptURL", (String)Assertions.assertNotNull(this.mReactContext.getCatalystInstance().getSourceURL(), "No source URL loaded, have you initialised the instance?"));
      return var1;
   }

   public String getName() {
      return "SourceCode";
   }
}
