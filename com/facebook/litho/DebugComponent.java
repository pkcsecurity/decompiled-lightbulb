package com.facebook.litho;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentHost;
import com.facebook.litho.ComponentTree;
import com.facebook.litho.DebugLayoutNode;
import com.facebook.litho.InternalNode;
import com.facebook.litho.LayoutState;
import com.facebook.litho.LithoView;
import com.facebook.litho.MountItem;
import com.facebook.litho.MountState;
import com.facebook.litho.StateContainer;
import com.facebook.litho.TextContent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public final class DebugComponent {

   private static final Map<String, DebugComponent.Overrider> sOverriders = new HashMap();
   private int mComponentIndex;
   private String mGlobalKey;
   private InternalNode mNode;


   static void applyOverrides(ComponentContext var0, Component var1) {
      String var3 = generateGlobalKey(var0, var1);
      DebugComponent.Overrider var2 = (DebugComponent.Overrider)sOverriders.get(var3);
      if(var2 != null) {
         var2.applyComponentOverrides(var3, var1);
         var2.applyStateOverrides(var3, var1.getStateContainer());
      }

   }

   static void applyOverrides(ComponentContext var0, InternalNode var1) {
      String var3 = generateGlobalKey(var0, (Component)var1.getComponents().get(0));
      DebugComponent.Overrider var2 = (DebugComponent.Overrider)sOverriders.get(var3);
      if(var2 != null) {
         var2.applyLayoutOverrides(var3, new DebugLayoutNode(var1));
      }

   }

   private static String generateGlobalKey(ComponentContext var0, Component var1) {
      ComponentTree var3 = var0.getComponentTree();
      String var4 = var1.getGlobalKey();
      StringBuilder var2 = new StringBuilder();
      var2.append(System.identityHashCode(var3));
      var2.append(var4);
      return var2.toString();
   }

   static DebugComponent getInstance(InternalNode var0, int var1) {
      synchronized(DebugComponent.class){}

      DebugComponent var2;
      try {
         var2 = new DebugComponent();
         var2.mGlobalKey = generateGlobalKey(var0.getContext(), (Component)var0.getComponents().get(var1));
         var2.mNode = var0;
         var2.mComponentIndex = var1;
         var0.registerDebugComponent(var2);
      } finally {
         ;
      }

      return var2;
   }

   @Nullable
   private Object getMountedContent() {
      if(!this.isLayoutNode()) {
         return null;
      } else {
         ComponentContext var3 = this.mNode.getContext();
         ComponentTree var6;
         if(var3 == null) {
            var6 = null;
         } else {
            var6 = var3.getComponentTree();
         }

         LithoView var7;
         if(var6 == null) {
            var7 = null;
         } else {
            var7 = var6.getLithoView();
         }

         MountState var8;
         if(var7 == null) {
            var8 = null;
         } else {
            var8 = var7.getMountState();
         }

         if(var8 != null) {
            int var1 = 0;

            for(int var2 = var8.getItemCount(); var1 < var2; ++var1) {
               MountItem var5 = var8.getItemAt(var1);
               Component var4;
               if(var5 == null) {
                  var4 = null;
               } else {
                  var4 = var5.getComponent();
               }

               if(var4 != null && var4 == this.mNode.getRootComponent()) {
                  return var5.getMountableContent();
               }
            }
         }

         return null;
      }
   }

   @Nullable
   public static DebugComponent getRootInstance(Component var0) {
      return getRootInstance(var0.getScopedContext().getComponentTree());
   }

   @Nullable
   public static DebugComponent getRootInstance(@Nullable ComponentTree var0) {
      LayoutState var1;
      if(var0 == null) {
         var1 = null;
      } else {
         var1 = var0.getMainThreadLayoutState();
      }

      InternalNode var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getLayoutRoot();
      }

      return var2 != null?getInstance(var2, Math.max(0, var2.getComponents().size() - 1)):null;
   }

   @Nullable
   public static DebugComponent getRootInstance(LithoView var0) {
      return getRootInstance(var0.getComponentTree());
   }

   private static int getXFromRoot(InternalNode var0) {
      return var0 == null?0:var0.getX() + getXFromRoot(parent(var0));
   }

   private static int getYFromRoot(InternalNode var0) {
      return var0 == null?0:var0.getY() + getYFromRoot(parent(var0));
   }

   private static InternalNode parent(InternalNode var0) {
      InternalNode var1 = var0.getParent();
      return var1 != null?var1:var0.getNestedTreeHolder();
   }

   public boolean canResolve() {
      return this.getComponent().canResolve();
   }

   public Rect getBounds() {
      int var1 = this.mNode.getX();
      int var2 = this.mNode.getY();
      return new Rect(var1, var2, this.mNode.getWidth() + var1, this.mNode.getHeight() + var2);
   }

   public Rect getBoundsInLithoView() {
      if(this.isRoot()) {
         return new Rect(0, 0, this.mNode.getWidth(), this.mNode.getHeight());
      } else {
         int var1 = getXFromRoot(this.mNode);
         int var2 = getYFromRoot(this.mNode);
         return new Rect(var1, var2, this.mNode.getWidth() + var1, this.mNode.getHeight() + var2);
      }
   }

   public List<DebugComponent> getChildComponents() {
      int var1;
      if(!this.isLayoutNode()) {
         var1 = this.mComponentIndex;
         return Arrays.asList(new DebugComponent[]{getInstance(this.mNode, var1 - 1)});
      } else {
         ArrayList var3 = new ArrayList();
         int var2 = this.mNode.getChildCount();

         InternalNode var4;
         for(var1 = 0; var1 < var2; ++var1) {
            var4 = this.mNode.getChildAt(var1);
            var3.add(getInstance(var4, Math.max(0, var4.getComponents().size() - 1)));
         }

         var4 = this.mNode.getNestedTree();
         if(var4 != null && var4.isInitialized()) {
            var2 = var4.getChildCount();

            for(var1 = 0; var1 < var2; ++var1) {
               InternalNode var5 = var4.getChildAt(var1);
               var3.add(getInstance(var5, Math.max(0, var5.getComponents().size() - 1)));
            }
         }

         return var3;
      }
   }

   public Component getComponent() {
      return (Component)this.mNode.getComponents().get(this.mComponentIndex);
   }

   @Nullable
   public ComponentHost getComponentHost() {
      LithoView var4 = this.getLithoView();
      Component var5 = this.getComponent();
      if(var4 == null) {
         return null;
      } else {
         int var1 = 0;

         for(int var2 = var4.getMountState().getItemCount(); var1 < var2; ++var1) {
            MountItem var6 = var4.getMountState().getItemAt(var1);
            Component var3;
            if(var6 == null) {
               var3 = null;
            } else {
               var3 = var6.getComponent();
            }

            if(var3 != null && var3.isEquivalentTo(var5)) {
               return var6.getHost();
            }
         }

         return null;
      }
   }

   public ComponentContext getContext() {
      return this.mNode.getContext();
   }

   public String getGlobalKey() {
      return this.mGlobalKey;
   }

   @Nullable
   public String getKey() {
      return ((Component)this.mNode.getComponents().get(this.mComponentIndex)).getKey();
   }

   @Nullable
   public DebugLayoutNode getLayoutNode() {
      return this.isLayoutNode()?new DebugLayoutNode(this.mNode):null;
   }

   @Nullable
   public LithoView getLithoView() {
      ComponentContext var1 = this.mNode.getContext();
      ComponentTree var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getComponentTree();
      }

      return var2 == null?null:var2.getLithoView();
   }

   @Nullable
   public Drawable getMountedDrawable() {
      Component var1 = this.mNode.getRootComponent();
      return var1 != null && Component.isMountDrawableSpec(var1)?(Drawable)this.getMountedContent():null;
   }

   @Nullable
   public View getMountedView() {
      Component var1 = this.mNode.getRootComponent();
      return var1 != null && Component.isMountViewSpec(var1)?(View)this.getMountedContent():null;
   }

   @Nullable
   public StateContainer getStateContainer() {
      return this.getComponent().getStateContainer();
   }

   @Nullable
   public String getTestKey() {
      return this.isLayoutNode()?this.mNode.getTestKey():null;
   }

   @Nullable
   public String getTextContent() {
      LithoView var3 = this.getLithoView();
      Component var4 = this.getComponent();
      if(var3 == null) {
         return null;
      } else {
         MountState var5 = var3.getMountState();
         StringBuilder var6 = new StringBuilder();
         int var1 = 0;

         for(int var2 = var5.getItemCount(); var1 < var2; ++var1) {
            MountItem var7 = var5.getItemAt(var1);
            Component var8;
            if(var7 == null) {
               var8 = null;
            } else {
               var8 = var7.getComponent();
            }

            if(var8 != null && var8.isEquivalentTo(var4)) {
               Object var9 = var7.getBaseContent();
               if(var9 instanceof TextContent) {
                  Iterator var10 = ((TextContent)var9).getTextItems().iterator();

                  while(var10.hasNext()) {
                     var6.append((CharSequence)var10.next());
                  }
               } else if(var9 instanceof TextView) {
                  var6.append(((TextView)var9).getText());
               }
            }
         }

         return var6.toString();
      }
   }

   public boolean isLayoutNode() {
      return this.mComponentIndex == 0;
   }

   public boolean isRoot() {
      return this.mComponentIndex == 0 && this.mNode.getParent() == null;
   }

   public void rerender() {
      LithoView var1 = this.getLithoView();
      if(var1 != null) {
         var1.forceRelayout();
      }

   }

   public void setOverrider(DebugComponent.Overrider var1) {
      sOverriders.put(this.mGlobalKey, var1);
   }

   public interface Overrider {

      void applyComponentOverrides(String var1, Component var2);

      void applyLayoutOverrides(String var1, DebugLayoutNode var2);

      void applyStateOverrides(String var1, StateContainer var2);
   }
}
