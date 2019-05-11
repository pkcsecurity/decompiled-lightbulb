package com.facebook.litho.sections.common;

import android.support.annotation.Nullable;
import com.facebook.litho.Component;
import com.facebook.litho.Diff;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.sections.ChangeSet;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.annotations.DiffSectionSpec;
import com.facebook.litho.sections.annotations.OnDiff;
import com.facebook.litho.utils.MapDiffUtils;
import com.facebook.litho.widget.ComponentRenderInfo;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@DiffSectionSpec
public class SingleComponentSectionSpec {

   private static ComponentRenderInfo.Builder addCustomAttributes(ComponentRenderInfo.Builder var0, @Nullable Map<String, Object> var1) {
      if(var1 == null) {
         return var0;
      } else {
         Iterator var3 = var1.entrySet().iterator();

         while(var3.hasNext()) {
            Entry var2 = (Entry)var3.next();
            var0.customAttribute((String)var2.getKey(), var2.getValue());
         }

         return var0;
      }
   }

   @OnDiff
   public static void onCreateChangeSet(SectionContext var0, ChangeSet var1, @Prop Diff<Component> var2, 
      @Prop(
         optional = true
      ) Diff<Boolean> var3, 
      @Prop(
         optional = true
      ) Diff<Integer> var4, 
      @Prop(
         optional = true
      ) Diff<Boolean> var5, 
      @Prop(
         optional = true
      ) Diff<Map<String, Object>> var6) {
      if(var2.getNext() == null) {
         var1.delete(0);
      } else {
         boolean var10;
         if(var3 != null && var3.getNext() != null) {
            var10 = ((Boolean)var3.getNext()).booleanValue();
         } else {
            var10 = false;
         }

         byte var9 = 1;
         int var7;
         if(var4 != null && var4.getNext() != null) {
            var7 = ((Integer)var4.getNext()).intValue();
         } else {
            var7 = 1;
         }

         boolean var11;
         if(var5 != null && var5.getNext() != null) {
            var11 = ((Boolean)var5.getNext()).booleanValue();
         } else {
            var11 = false;
         }

         if(var2.getPrevious() == null) {
            var1.insert(0, ((ComponentRenderInfo.Builder)((ComponentRenderInfo.Builder)((ComponentRenderInfo.Builder)addCustomAttributes(ComponentRenderInfo.create(), (Map)var6.getNext()).component((Component)var2.getNext()).isSticky(var10)).spanSize(var7)).isFullSpan(var11)).build(), var0.getTreePropsCopy());
         } else {
            boolean var12;
            if(var3 != null && var3.getPrevious() != null) {
               var12 = ((Boolean)var3.getPrevious()).booleanValue();
            } else {
               var12 = false;
            }

            int var8 = var9;
            if(var4 != null) {
               var8 = var9;
               if(var4.getPrevious() != null) {
                  var8 = ((Integer)var4.getPrevious()).intValue();
               }
            }

            boolean var13;
            if(var5 != null && var5.getPrevious() != null) {
               var13 = ((Boolean)var5.getPrevious()).booleanValue();
            } else {
               var13 = false;
            }

            boolean var14 = MapDiffUtils.areMapsEqual((Map)var6.getPrevious(), (Map)var6.getNext());
            if(var12 != var10 || var8 != var7 || var13 != var11 || !((Component)var2.getPrevious()).isEquivalentTo((Component)var2.getNext()) || !var14) {
               var1.update(0, ((ComponentRenderInfo.Builder)((ComponentRenderInfo.Builder)((ComponentRenderInfo.Builder)addCustomAttributes(ComponentRenderInfo.create(), (Map)var6.getNext()).component((Component)var2.getNext()).isSticky(var10)).spanSize(var7)).isFullSpan(var11)).build(), var0.getTreePropsCopy());
            }

         }
      }
   }
}
