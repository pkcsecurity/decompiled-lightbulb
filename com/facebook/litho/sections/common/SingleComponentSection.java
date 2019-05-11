package com.facebook.litho.sections.common;

import com.facebook.litho.Component;
import com.facebook.litho.Diff;
import com.facebook.litho.EventHandler;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.sections.ChangeSet;
import com.facebook.litho.sections.LoadingEvent;
import com.facebook.litho.sections.Section;
import com.facebook.litho.sections.SectionContext;
import com.facebook.litho.sections.common.SingleComponentSectionSpec;
import java.util.BitSet;
import java.util.Map;

public final class SingleComponentSection extends Section {

   @Comparable(
      type = 10
   )
   @Prop(
      optional = false,
      resType = ResType.NONE
   )
   Component component;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   Map<String, Object> customAttributes;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   Boolean isFullSpan;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   Integer spanSize;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   Boolean sticky;


   private SingleComponentSection() {
      super("SingleComponentSection");
   }

   public static SingleComponentSection.Builder create(SectionContext var0) {
      SingleComponentSection.Builder var1 = new SingleComponentSection.Builder();
      var1.init(var0, new SingleComponentSection());
      return var1;
   }

   protected void generateChangeSet(SectionContext var1, ChangeSet var2, Section var3, Section var4) {
      SingleComponentSection var7 = (SingleComponentSection)var3;
      SingleComponentSection var6 = (SingleComponentSection)var4;
      Object var5 = null;
      Component var12;
      if(var7 == null) {
         var12 = null;
      } else {
         var12 = var7.component;
      }

      Component var13;
      if(var6 == null) {
         var13 = null;
      } else {
         var13 = var6.component;
      }

      Diff var8 = this.acquireDiff(var12, var13);
      Boolean var14;
      if(var7 == null) {
         var14 = null;
      } else {
         var14 = var7.sticky;
      }

      Boolean var15;
      if(var6 == null) {
         var15 = null;
      } else {
         var15 = var6.sticky;
      }

      Diff var9 = this.acquireDiff(var14, var15);
      Integer var16;
      if(var7 == null) {
         var16 = null;
      } else {
         var16 = var7.spanSize;
      }

      Integer var17;
      if(var6 == null) {
         var17 = null;
      } else {
         var17 = var6.spanSize;
      }

      Diff var10 = this.acquireDiff(var16, var17);
      if(var7 == null) {
         var14 = null;
      } else {
         var14 = var7.isFullSpan;
      }

      if(var6 == null) {
         var15 = null;
      } else {
         var15 = var6.isFullSpan;
      }

      Diff var11 = this.acquireDiff(var14, var15);
      Map var18;
      if(var7 == null) {
         var18 = null;
      } else {
         var18 = var7.customAttributes;
      }

      Map var19;
      if(var6 == null) {
         var19 = (Map)var5;
      } else {
         var19 = var6.customAttributes;
      }

      Diff var20 = this.acquireDiff(var18, var19);
      SingleComponentSectionSpec.onCreateChangeSet(var1, var2, var8, var9, var10, var11, var20);
      this.releaseDiff(var8);
      this.releaseDiff(var9);
      this.releaseDiff(var10);
      this.releaseDiff(var11);
      this.releaseDiff(var20);
   }

   protected boolean isDiffSectionSpec() {
      return true;
   }

   public boolean isEquivalentTo(Section var1) {
      if(ComponentsConfiguration.useNewIsEquivalentTo) {
         return super.isEquivalentTo(var1);
      } else if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            SingleComponentSection var2 = (SingleComponentSection)var1;
            if(this.component != null) {
               if(!this.component.isEquivalentTo(var2.component)) {
                  return false;
               }
            } else if(var2.component != null) {
               return false;
            }

            if(this.customAttributes != null) {
               if(!this.customAttributes.equals(var2.customAttributes)) {
                  return false;
               }
            } else if(var2.customAttributes != null) {
               return false;
            }

            if(this.isFullSpan != null) {
               if(!this.isFullSpan.equals(var2.isFullSpan)) {
                  return false;
               }
            } else if(var2.isFullSpan != null) {
               return false;
            }

            if(this.spanSize != null) {
               if(!this.spanSize.equals(var2.spanSize)) {
                  return false;
               }
            } else if(var2.spanSize != null) {
               return false;
            }

            if(this.sticky != null) {
               if(!this.sticky.equals(var2.sticky)) {
                  return false;
               }
            } else if(var2.sticky != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public SingleComponentSection makeShallowCopy(boolean var1) {
      SingleComponentSection var3 = (SingleComponentSection)super.makeShallowCopy(var1);
      Component var2;
      if(var3.component != null) {
         var2 = var3.component.makeShallowCopy();
      } else {
         var2 = null;
      }

      var3.component = var2;
      return var3;
   }

   public static class Builder extends Section.Builder<SingleComponentSection.Builder> {

      private final int REQUIRED_PROPS_COUNT = 1;
      private final String[] REQUIRED_PROPS_NAMES = new String[]{"component"};
      SectionContext mContext;
      private final BitSet mRequired = new BitSet(1);
      SingleComponentSection mSingleComponentSection;


      private void init(SectionContext var1, SingleComponentSection var2) {
         super.init(var1, var2);
         this.mSingleComponentSection = var2;
         this.mContext = var1;
         this.mRequired.clear();
      }

      public SingleComponentSection build() {
         checkArgs(1, this.mRequired, this.REQUIRED_PROPS_NAMES);
         SingleComponentSection var1 = this.mSingleComponentSection;
         this.release();
         return var1;
      }

      public SingleComponentSection.Builder component(Component.Builder<?> var1) {
         SingleComponentSection var2 = this.mSingleComponentSection;
         Component var3;
         if(var1 == null) {
            var3 = null;
         } else {
            var3 = var1.build();
         }

         var2.component = var3;
         this.mRequired.set(0);
         return this;
      }

      public SingleComponentSection.Builder component(Component var1) {
         SingleComponentSection var2 = this.mSingleComponentSection;
         if(var1 == null) {
            var1 = null;
         } else {
            var1 = var1.makeShallowCopy();
         }

         var2.component = var1;
         this.mRequired.set(0);
         return this;
      }

      public SingleComponentSection.Builder customAttributes(Map<String, Object> var1) {
         this.mSingleComponentSection.customAttributes = var1;
         return this;
      }

      public SingleComponentSection.Builder getThis() {
         return this;
      }

      public SingleComponentSection.Builder isFullSpan(Boolean var1) {
         this.mSingleComponentSection.isFullSpan = var1;
         return this;
      }

      public SingleComponentSection.Builder key(String var1) {
         return (SingleComponentSection.Builder)super.key(var1);
      }

      public SingleComponentSection.Builder loadingEventHandler(EventHandler<LoadingEvent> var1) {
         return (SingleComponentSection.Builder)super.loadingEventHandler(var1);
      }

      protected void release() {
         super.release();
         this.mSingleComponentSection = null;
         this.mContext = null;
      }

      public SingleComponentSection.Builder spanSize(Integer var1) {
         this.mSingleComponentSection.spanSize = var1;
         return this;
      }

      public SingleComponentSection.Builder sticky(Boolean var1) {
         this.mSingleComponentSection.sticky = var1;
         return this;
      }
   }
}
