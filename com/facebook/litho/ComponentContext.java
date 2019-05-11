package com.facebook.litho;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Looper;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.facebook.infer.annotation.ThreadConfined;
import com.facebook.litho.CommonPropsCopyable;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.ComponentManager;
import com.facebook.litho.ComponentTree;
import com.facebook.litho.ComponentsLogger;
import com.facebook.litho.ComponentsPools;
import com.facebook.litho.DebugComponent;
import com.facebook.litho.EventHandler;
import com.facebook.litho.EventTrigger;
import com.facebook.litho.InternalNode;
import com.facebook.litho.KeyHandler;
import com.facebook.litho.NoOpInternalNode;
import com.facebook.litho.R;
import com.facebook.litho.ResourceCache;
import com.facebook.litho.StateHandler;
import com.facebook.litho.TreeProps;
import com.facebook.litho.config.ComponentsConfiguration;

public class ComponentContext {

   static final InternalNode NULL_LAYOUT = new NoOpInternalNode();
   @ThreadConfined("ANY")
   private Component mComponentScope;
   @ThreadConfined("ANY")
   private ComponentTree mComponentTree;
   private final Context mContext;
   @AttrRes
   @ThreadConfined("ANY")
   private int mDefStyleAttr;
   @StyleRes
   @ThreadConfined("ANY")
   private int mDefStyleRes;
   @ThreadConfined("ANY")
   private int mHeightSpec;
   private final KeyHandler mKeyHandler;
   private final String mLogTag;
   private final ComponentsLogger mLogger;
   private String mNoStateUpdatesMethod;
   @ThreadConfined("ANY")
   private final ResourceCache mResourceCache;
   @Nullable
   private final StateHandler mStateHandler;
   @ThreadConfined("ANY")
   protected TreeProps mTreeProps;
   @ThreadConfined("ANY")
   private int mWidthSpec;


   public ComponentContext(Context var1) {
      this(var1, (String)null, (ComponentsLogger)null, (StateHandler)null, (KeyHandler)null, (TreeProps)null);
   }

   public ComponentContext(Context var1, StateHandler var2) {
      this(var1, var2, (KeyHandler)null);
   }

   public ComponentContext(Context var1, StateHandler var2, KeyHandler var3) {
      this(var1, (String)null, (ComponentsLogger)null, var2, var3, (TreeProps)null);
   }

   ComponentContext(Context var1, StateHandler var2, KeyHandler var3, @Nullable TreeProps var4) {
      this(var1, (String)null, (ComponentsLogger)null, var2, var3, var4);
   }

   public ComponentContext(Context var1, StateHandler var2, String var3, ComponentsLogger var4) {
      this(var1, var3, var4, var2, (KeyHandler)null, (TreeProps)null);
   }

   public ComponentContext(Context var1, String var2, ComponentsLogger var3) {
      this(var1, var2, var3, (StateHandler)null, (KeyHandler)null, (TreeProps)null);
   }

   private ComponentContext(Context var1, String var2, ComponentsLogger var3, StateHandler var4, KeyHandler var5, @Nullable TreeProps var6) {
      this.mDefStyleRes = 0;
      this.mDefStyleAttr = 0;
      this.mContext = var1;
      if(var3 != null && var2 == null) {
         throw new IllegalStateException("When a ComponentsLogger is set, a LogTag must be set");
      } else {
         this.mResourceCache = ResourceCache.getLatest(var1.getResources().getConfiguration());
         this.mTreeProps = var6;
         this.mLogger = var3;
         this.mLogTag = var2;
         this.mStateHandler = var4;
         this.mKeyHandler = var5;
      }
   }

   public ComponentContext(Context var1, String var2, ComponentsLogger var3, TreeProps var4) {
      this(var1, var2, var3, (StateHandler)null, (KeyHandler)null, var4);
   }

   public ComponentContext(ComponentContext var1) {
      this(var1, var1.mStateHandler, var1.mKeyHandler, var1.mTreeProps);
   }

   public ComponentContext(ComponentContext var1, StateHandler var2) {
      this(var1, var2, var1.mKeyHandler, var1.mTreeProps);
   }

   protected ComponentContext(ComponentContext var1, StateHandler var2, KeyHandler var3, TreeProps var4) {
      this.mDefStyleRes = 0;
      this.mDefStyleAttr = 0;
      this.mContext = var1.getAndroidContext();
      this.mResourceCache = var1.mResourceCache;
      this.mWidthSpec = var1.mWidthSpec;
      this.mHeightSpec = var1.mHeightSpec;
      this.mComponentScope = var1.mComponentScope;
      this.mComponentTree = var1.mComponentTree;
      this.mLogger = var1.mLogger;
      this.mLogTag = var1.mLogTag;
      if(var2 == null) {
         var2 = var1.mStateHandler;
      }

      this.mStateHandler = var2;
      if(var3 == null) {
         var3 = var1.mKeyHandler;
      }

      this.mKeyHandler = var3;
      if(var4 == null) {
         var4 = var1.mTreeProps;
      }

      this.mTreeProps = var4;
   }

   private void checkIfNoStateUpdatesMethod() {
      if(this.mNoStateUpdatesMethod != null) {
         StringBuilder var1 = new StringBuilder();
         var1.append("Updating the state of a component during ");
         var1.append(this.mNoStateUpdatesMethod);
         var1.append(" leads to unexpected behaviour, consider using lazy state updates.");
         throw new IllegalStateException(var1.toString());
      }
   }

   public static boolean isIncrementalMountEnabled(ComponentContext var0) {
      return var0.getComponentTree().isIncrementalMountEnabled();
   }

   @VisibleForTesting(
      otherwise = 3
   )
   public static ComponentContext withComponentScope(ComponentContext var0, Component var1) {
      ComponentContext var2 = var0.makeNewCopy();
      var2.mComponentScope = var1;
      var2.mComponentTree = var0.mComponentTree;
      if(!TextUtils.isEmpty(var1.getUuid())) {
         ComponentManager.getInstance().putComponent(var1.getUuid(), var1);
      }

      return var2;
   }

   static ComponentContext withComponentTree(ComponentContext var0, ComponentTree var1) {
      var0 = new ComponentContext(var0, ComponentsPools.acquireStateHandler());
      var0.mComponentTree = var1;
      return var0;
   }

   void applyStyle(InternalNode var1, @AttrRes int var2, @StyleRes int var3) {
      if(var2 != 0 || var3 != 0) {
         this.setDefStyle(var2, var3);
         TypedArray var4 = this.mContext.obtainStyledAttributes((AttributeSet)null, R.styleable.ComponentLayout, var2, var3);
         var1.applyAttributes(var4);
         var4.recycle();
         this.setDefStyle(0, 0);
      }

   }

   void enterNoStateUpdatesMethod(String var1) {
      this.mNoStateUpdatesMethod = var1;
   }

   void exitNoStateUpdatesMethod() {
      this.mNoStateUpdatesMethod = null;
   }

   public final Context getAndroidContext() {
      return this.mContext;
   }

   public final Context getApplicationContext() {
      return this.mContext.getApplicationContext();
   }

   public int getColor(@ColorRes int var1) {
      return this.mContext.getResources().getColor(var1);
   }

   public Component getComponentScope() {
      return this.mComponentScope;
   }

   ComponentTree getComponentTree() {
      return this.mComponentTree;
   }

   int getHeightSpec() {
      return this.mHeightSpec;
   }

   KeyHandler getKeyHandler() {
      return this.mKeyHandler;
   }

   public String getLogTag() {
      return this.mLogTag;
   }

   @Nullable
   public ComponentsLogger getLogger() {
      return this.mLogger;
   }

   public final Looper getMainLooper() {
      return this.mContext.getMainLooper();
   }

   public ResourceCache getResourceCache() {
      return this.mResourceCache;
   }

   public Resources getResources() {
      return this.mContext.getResources();
   }

   @Nullable
   public String getSplitLayoutTag() {
      return this.mComponentTree == null?null:this.mComponentTree.getSplitLayoutTag();
   }

   StateHandler getStateHandler() {
      return this.mStateHandler;
   }

   public String getString(@StringRes int var1) {
      return this.mContext.getResources().getString(var1);
   }

   public String getString(@StringRes int var1, Object ... var2) {
      return this.mContext.getResources().getString(var1, var2);
   }

   public CharSequence getText(@StringRes int var1) {
      return this.mContext.getResources().getText(var1);
   }

   @Nullable
   protected TreeProps getTreeProps() {
      return this.mTreeProps;
   }

   @Nullable
   public TreeProps getTreePropsCopy() {
      return TreeProps.copy(this.mTreeProps);
   }

   int getWidthSpec() {
      return this.mWidthSpec;
   }

   ComponentContext makeNewCopy() {
      return new ComponentContext(this);
   }

   EventHandler newEventHandler(int var1) {
      return new EventHandler(this.mComponentScope, var1);
   }

   public <E extends Object> EventHandler<E> newEventHandler(int var1, Object[] var2) {
      return new EventHandler(this.mComponentScope, var1, var2);
   }

   protected <E extends Object> EventTrigger<E> newEventTrigger(String var1, int var2) {
      String var3;
      if(this.mComponentScope == null) {
         var3 = "";
      } else {
         var3 = this.mComponentScope.getGlobalKey();
      }

      return new EventTrigger(var3, var2, var1);
   }

   InternalNode newLayoutBuilder(@AttrRes int var1, @StyleRes int var2) {
      InternalNode var3 = ComponentsPools.acquireInternalNode(this);
      this.applyStyle(var3, var1, var2);
      return var3;
   }

   InternalNode newLayoutBuilder(Component var1, @AttrRes int var2, @StyleRes int var3) {
      InternalNode var4 = var1.consumeLayoutCreatedInWillRender();
      if(var4 != null) {
         return var4;
      } else {
         var1 = var1.getThreadSafeInstance();
         var1.updateInternalChildState(this);
         if(ComponentsConfiguration.isDebugModeEnabled) {
            DebugComponent.applyOverrides(this, var1);
         }

         InternalNode var5 = var1.createLayout(var1.getScopedContext(), false);
         if(var5 != NULL_LAYOUT) {
            this.applyStyle(var5, var2, var3);
         }

         return var5;
      }
   }

   public TypedArray obtainStyledAttributes(int[] var1, @AttrRes int var2) {
      Context var3 = this.mContext;
      if(var2 == 0) {
         var2 = this.mDefStyleAttr;
      }

      return var3.obtainStyledAttributes((AttributeSet)null, var1, var2, this.mDefStyleRes);
   }

   InternalNode resolveLayout(Component var1) {
      InternalNode var2 = var1.consumeLayoutCreatedInWillRender();
      if(var2 != null) {
         return var2;
      } else {
         var1 = var1.getThreadSafeInstance();
         var1.updateInternalChildState(this, true);
         if(ComponentsConfiguration.isDebugModeEnabled) {
            DebugComponent.applyOverrides(this, var1);
         }

         var2 = (InternalNode)var1.resolve(var1.getScopedContext());
         if(var1.canResolve()) {
            CommonPropsCopyable var3 = var1.getCommonPropsCopyable();
            if(var3 != null) {
               var3.copyInto(var1.getScopedContext(), var2);
            }
         }

         return var2;
      }
   }

   void setDefStyle(@AttrRes int var1, @StyleRes int var2) {
      this.mDefStyleAttr = var1;
      this.mDefStyleRes = var2;
   }

   void setHeightSpec(int var1) {
      this.mHeightSpec = var1;
   }

   protected void setTreeProps(TreeProps var1) {
      this.mTreeProps = var1;
   }

   void setWidthSpec(int var1) {
      this.mWidthSpec = var1;
   }

   public void updateStateAsync(ComponentLifecycle.StateUpdate var1, String var2) {
      this.checkIfNoStateUpdatesMethod();
      if(this.mComponentTree != null) {
         this.mComponentTree.updateStateAsync(this.mComponentScope.getGlobalKey(), var1, var2);
      }
   }

   public void updateStateLazy(ComponentLifecycle.StateUpdate var1) {
      if(this.mComponentTree != null) {
         this.mComponentTree.updateStateLazy(this.mComponentScope.getGlobalKey(), var1);
      }
   }

   public void updateStateSync(ComponentLifecycle.StateUpdate var1, String var2) {
      this.checkIfNoStateUpdatesMethod();
      if(this.mComponentTree != null) {
         if(ComponentsConfiguration.updateStateAsync) {
            this.mComponentTree.updateStateAsync(this.mComponentScope.getGlobalKey(), var1, var2);
         } else {
            this.mComponentTree.updateStateSync(this.mComponentScope.getGlobalKey(), var1, var2);
         }
      }
   }

   public void updateStateWithTransition(ComponentLifecycle.StateUpdate var1, String var2) {
      this.updateStateAsync(var1, var2);
   }
}
