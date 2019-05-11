package com.facebook.litho;

import android.support.annotation.Nullable;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.DebugComponent;
import com.facebook.litho.DebugLayoutNode;
import com.facebook.litho.LithoView;
import com.facebook.litho.config.ComponentsConfiguration;
import java.util.Iterator;

public class ComponentTreeDumpingHelper {

   @Nullable
   public static String dumpContextTree(@Nullable ComponentContext var0) {
      if(!ComponentsConfiguration.isDebugModeEnabled) {
         return "Dumping of the component tree is not support on non-internal builds";
      } else if(var0 == null) {
         return "ComponentContext is null";
      } else {
         DebugComponent var2 = DebugComponent.getRootInstance(var0.getComponentTree());
         if(var2 == null) {
            return null;
         } else {
            StringBuilder var1 = new StringBuilder();
            logComponent(var2, 0, var1);
            return var1.toString();
         }
      }
   }

   private static void logComponent(@Nullable DebugComponent var0, int var1, StringBuilder var2) {
      if(var0 != null) {
         var2.append(var0.getComponent().getSimpleName());
         var2.append('{');
         LithoView var4 = var0.getLithoView();
         DebugLayoutNode var5 = var0.getLayoutNode();
         String var7;
         if(var4 != null && var4.getVisibility() == 0) {
            var7 = "V";
         } else {
            var7 = "H";
         }

         var2.append(var7);
         if(var5 != null && var5.getClickHandler() != null) {
            var2.append(" [clickable]");
         }

         var2.append('}');
         Iterator var6 = var0.getChildComponents().iterator();

         while(var6.hasNext()) {
            DebugComponent var8 = (DebugComponent)var6.next();
            var2.append("\n");

            for(int var3 = 0; var3 <= var1; ++var3) {
               var2.append("  ");
            }

            logComponent(var8, var1 + 1, var2);
         }

      }
   }
}
