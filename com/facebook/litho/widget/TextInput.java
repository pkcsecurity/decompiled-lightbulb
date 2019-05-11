package com.facebook.litho.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.annotation.Px;
import android.support.annotation.StringRes;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.Diff;
import com.facebook.litho.EventHandler;
import com.facebook.litho.EventTrigger;
import com.facebook.litho.EventTriggerTarget;
import com.facebook.litho.EventTriggersContainer;
import com.facebook.litho.Size;
import com.facebook.litho.StateContainer;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.State;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.ClearFocusEvent;
import com.facebook.litho.widget.EditorActionEvent;
import com.facebook.litho.widget.KeyUpEvent;
import com.facebook.litho.widget.RequestFocusEvent;
import com.facebook.litho.widget.SelectionChangedEvent;
import com.facebook.litho.widget.SetTextEvent;
import com.facebook.litho.widget.TextChangedEvent;
import com.facebook.litho.widget.TextInputSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class TextInput extends Component {

   static final Pools.SynchronizedPool<EditorActionEvent> sEditorActionEventPool = new Pools.SynchronizedPool(2);
   static final Pools.SynchronizedPool<KeyUpEvent> sKeyUpEventPool = new Pools.SynchronizedPool(2);
   static final Pools.SynchronizedPool<SelectionChangedEvent> sSelectionChangedEventPool = new Pools.SynchronizedPool(2);
   static final Pools.SynchronizedPool<SetTextEvent> sSetTextEventPool = new Pools.SynchronizedPool(2);
   static final Pools.SynchronizedPool<TextChangedEvent> sTextChangedEventPool = new Pools.SynchronizedPool(2);
   EventTrigger clearFocusTrigger;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean editable = true;
   EventHandler editorActionEventHandler;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int gravity = 8388627;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.STRING
   )
   CharSequence hint;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   ColorStateList hintColorStateList;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int imeOptions;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.STRING
   )
   CharSequence initialText;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.DRAWABLE
   )
   Drawable inputBackground;
   @Comparable(
      type = 5
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   List<InputFilter> inputFilters;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int inputType;
   EventHandler keyUpEventHandler;
   @Comparable(
      type = 14
   )
   private TextInput.TextInputStateContainer mStateContainer;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean multiline;
   EventTrigger requestFocusTrigger;
   EventHandler selectionChangedEventHandler;
   EventHandler setTextEventHandler;
   EventTrigger setTextTrigger;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int shadowColor;
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_OFFSET
   )
   float shadowDx;
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_OFFSET
   )
   float shadowDy;
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_OFFSET
   )
   float shadowRadius;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int textAlignment;
   EventHandler textChangedEventHandler;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   ColorStateList textColorStateList;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_TEXT
   )
   int textSize;
   @Comparable(
      type = 5
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   List<TextWatcher> textWatchers;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   Typeface typeface;


   private TextInput() {
      super("TextInput");
      this.hint = TextInputSpec.hint;
      this.hintColorStateList = TextInputSpec.hintColorStateList;
      this.imeOptions = 0;
      this.initialText = TextInputSpec.initialText;
      this.inputType = 1;
      this.multiline = false;
      this.shadowColor = -7829368;
      this.textAlignment = 1;
      this.textColorStateList = TextInputSpec.textColorStateList;
      this.textSize = 13;
      this.typeface = TextInputSpec.typeface;
      this.mStateContainer = new TextInput.TextInputStateContainer();
   }

   static void clearFocus(ComponentContext var0) {
      TextInput var1 = (TextInput)var0.getComponentScope();
      var1.clearFocus((EventTriggerTarget)var1);
   }

   public static void clearFocus(ComponentContext var0, String var1) {
      EventTrigger var2 = getEventTrigger(var0, -50354224, var1);
      if(var2 != null) {
         var2.dispatchOnTrigger(new ClearFocusEvent(), new Object[0]);
      }
   }

   public static void clearFocus(EventTrigger var0) {
      var0.dispatchOnTrigger(new ClearFocusEvent(), new Object[0]);
   }

   private void clearFocus(EventTriggerTarget var1) {
      TextInput var2 = (TextInput)var1;
      TextInputSpec.clearFocus(var2.getScopedContext(), var2.mStateContainer.mountedView);
   }

   public static EventTrigger clearFocusTrigger(ComponentContext var0, String var1) {
      return newEventTrigger(var0, var1, -50354224);
   }

   public static TextInput.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static TextInput.Builder create(ComponentContext var0, int var1, int var2) {
      TextInput.Builder var3 = new TextInput.Builder();
      var3.init(var0, var1, var2, new TextInput());
      return var3;
   }

   private TextInput.RemeasureForUpdatedTextStateUpdate createRemeasureForUpdatedTextStateUpdate() {
      return new TextInput.RemeasureForUpdatedTextStateUpdate();
   }

   static boolean dispatchEditorActionEvent(EventHandler var0, int var1, KeyEvent var2) {
      EditorActionEvent var5 = (EditorActionEvent)sEditorActionEventPool.acquire();
      EditorActionEvent var4 = var5;
      if(var5 == null) {
         var4 = new EditorActionEvent();
      }

      var4.actionId = var1;
      var4.event = var2;
      boolean var3 = ((Boolean)var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, var4)).booleanValue();
      var4.event = null;
      sEditorActionEventPool.release(var4);
      return var3;
   }

   static boolean dispatchKeyUpEvent(EventHandler var0, int var1, KeyEvent var2) {
      KeyUpEvent var5 = (KeyUpEvent)sKeyUpEventPool.acquire();
      KeyUpEvent var4 = var5;
      if(var5 == null) {
         var4 = new KeyUpEvent();
      }

      var4.keyCode = var1;
      var4.keyEvent = var2;
      boolean var3 = ((Boolean)var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, var4)).booleanValue();
      var4.keyEvent = null;
      sKeyUpEventPool.release(var4);
      return var3;
   }

   static void dispatchSelectionChangedEvent(EventHandler var0, int var1, int var2) {
      SelectionChangedEvent var4 = (SelectionChangedEvent)sSelectionChangedEventPool.acquire();
      SelectionChangedEvent var3 = var4;
      if(var4 == null) {
         var3 = new SelectionChangedEvent();
      }

      var3.start = var1;
      var3.end = var2;
      var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, var3);
      sSelectionChangedEventPool.release(var3);
   }

   static void dispatchSetTextEvent(EventHandler var0, String var1) {
      SetTextEvent var3 = (SetTextEvent)sSetTextEventPool.acquire();
      SetTextEvent var2 = var3;
      if(var3 == null) {
         var2 = new SetTextEvent();
      }

      var2.text = var1;
      var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, var2);
      var2.text = null;
      sSetTextEventPool.release(var2);
   }

   static void dispatchTextChangedEvent(EventHandler var0, android.widget.EditText var1, String var2) {
      TextChangedEvent var4 = (TextChangedEvent)sTextChangedEventPool.acquire();
      TextChangedEvent var3 = var4;
      if(var4 == null) {
         var3 = new TextChangedEvent();
      }

      var3.view = var1;
      var3.text = var2;
      var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, var3);
      var3.view = null;
      var3.text = null;
      sTextChangedEventPool.release(var3);
   }

   public static EventHandler getEditorActionEventHandler(ComponentContext var0) {
      return var0.getComponentScope() == null?null:((TextInput)var0.getComponentScope()).editorActionEventHandler;
   }

   public static EventHandler getKeyUpEventHandler(ComponentContext var0) {
      return var0.getComponentScope() == null?null:((TextInput)var0.getComponentScope()).keyUpEventHandler;
   }

   public static EventHandler getSelectionChangedEventHandler(ComponentContext var0) {
      return var0.getComponentScope() == null?null:((TextInput)var0.getComponentScope()).selectionChangedEventHandler;
   }

   public static EventHandler getSetTextEventHandler(ComponentContext var0) {
      return var0.getComponentScope() == null?null:((TextInput)var0.getComponentScope()).setTextEventHandler;
   }

   public static EventHandler getTextChangedEventHandler(ComponentContext var0) {
      return var0.getComponentScope() == null?null:((TextInput)var0.getComponentScope()).textChangedEventHandler;
   }

   protected static void remeasureForUpdatedText(ComponentContext var0) {
      Component var1 = var0.getComponentScope();
      if(var1 != null) {
         var0.updateStateSync(((TextInput)var1).createRemeasureForUpdatedTextStateUpdate(), "TextInput.remeasureForUpdatedText");
      }
   }

   protected static void remeasureForUpdatedTextAsync(ComponentContext var0) {
      Component var1 = var0.getComponentScope();
      if(var1 != null) {
         var0.updateStateAsync(((TextInput)var1).createRemeasureForUpdatedTextStateUpdate(), "TextInput.remeasureForUpdatedText");
      }
   }

   protected static void remeasureForUpdatedTextSync(ComponentContext var0) {
      Component var1 = var0.getComponentScope();
      if(var1 != null) {
         var0.updateStateSync(((TextInput)var1).createRemeasureForUpdatedTextStateUpdate(), "TextInput.remeasureForUpdatedText");
      }
   }

   static void requestFocus(ComponentContext var0) {
      TextInput var1 = (TextInput)var0.getComponentScope();
      var1.requestFocus((EventTriggerTarget)var1);
   }

   public static void requestFocus(ComponentContext var0, String var1) {
      EventTrigger var2 = getEventTrigger(var0, 1008096338, var1);
      if(var2 != null) {
         var2.dispatchOnTrigger(new RequestFocusEvent(), new Object[0]);
      }
   }

   public static void requestFocus(EventTrigger var0) {
      var0.dispatchOnTrigger(new RequestFocusEvent(), new Object[0]);
   }

   private void requestFocus(EventTriggerTarget var1) {
      TextInput var2 = (TextInput)var1;
      TextInputSpec.requestFocus(var2.getScopedContext(), var2.mStateContainer.mountedView);
   }

   public static EventTrigger requestFocusTrigger(ComponentContext var0, String var1) {
      return newEventTrigger(var0, var1, 1008096338);
   }

   static void setText(ComponentContext var0, String var1) {
      TextInput var2 = (TextInput)var0.getComponentScope();
      var2.setText((EventTriggerTarget)var2, var1);
   }

   public static void setText(ComponentContext var0, String var1, String var2) {
      EventTrigger var3 = getEventTrigger(var0, 2092727750, var1);
      if(var3 != null) {
         SetTextEvent var4 = new SetTextEvent();
         var4.text = var2;
         var3.dispatchOnTrigger(var4, new Object[0]);
      }
   }

   public static void setText(EventTrigger var0, String var1) {
      SetTextEvent var2 = new SetTextEvent();
      var2.text = var1;
      var0.dispatchOnTrigger(var2, new Object[0]);
   }

   private void setText(EventTriggerTarget var1, String var2) {
      TextInput var3 = (TextInput)var1;
      TextInputSpec.setText(var3.getScopedContext(), var3.mStateContainer.mountedView, var3.mStateContainer.savedText, var2);
   }

   public static EventTrigger setTextTrigger(ComponentContext var0, String var1) {
      return newEventTrigger(var0, var1, 2092727750);
   }

   public Object acceptTriggerEvent(EventTrigger var1, Object var2, Object[] var3) {
      int var4 = var1.mId;
      if(var4 != -50354224) {
         if(var4 != 1008096338) {
            if(var4 != 2092727750) {
               return null;
            } else {
               SetTextEvent var7 = (SetTextEvent)var2;
               this.setText(var1.mTriggerTarget, var7.text);
               return null;
            }
         } else {
            RequestFocusEvent var6 = (RequestFocusEvent)var2;
            this.requestFocus(var1.mTriggerTarget);
            return null;
         }
      } else {
         ClearFocusEvent var5 = (ClearFocusEvent)var2;
         this.clearFocus(var1.mTriggerTarget);
         return null;
      }
   }

   protected boolean canMeasure() {
      return true;
   }

   protected boolean canPreallocate() {
      return false;
   }

   protected void createInitialState(ComponentContext var1) {
      StateValue var2 = new StateValue();
      StateValue var3 = new StateValue();
      StateValue var4 = new StateValue();
      TextInputSpec.onCreateInitialState(var1, var2, var3, var4, this.initialText);
      this.mStateContainer.mountedView = (AtomicReference)var2.get();
      this.mStateContainer.savedText = (AtomicReference)var3.get();
      this.mStateContainer.measureSeqNumber = ((Integer)var4.get()).intValue();
   }

   public ComponentLifecycle.MountType getMountType() {
      return ComponentLifecycle.MountType.VIEW;
   }

   protected StateContainer getStateContainer() {
      return this.mStateContainer;
   }

   protected boolean hasState() {
      return true;
   }

   public boolean isEquivalentTo(Component var1) {
      if(ComponentsConfiguration.useNewIsEquivalentTo) {
         return super.isEquivalentTo(var1);
      } else if(this == var1) {
         return true;
      } else if(var1 != null) {
         if(this.getClass() != var1.getClass()) {
            return false;
         } else {
            TextInput var2 = (TextInput)var1;
            if(this.getId() == var2.getId()) {
               return true;
            } else if(this.editable != var2.editable) {
               return false;
            } else if(this.gravity != var2.gravity) {
               return false;
            } else {
               if(this.hint != null) {
                  if(!this.hint.equals(var2.hint)) {
                     return false;
                  }
               } else if(var2.hint != null) {
                  return false;
               }

               if(this.hintColorStateList != null) {
                  if(!this.hintColorStateList.equals(var2.hintColorStateList)) {
                     return false;
                  }
               } else if(var2.hintColorStateList != null) {
                  return false;
               }

               if(this.imeOptions != var2.imeOptions) {
                  return false;
               } else {
                  if(this.initialText != null) {
                     if(!this.initialText.equals(var2.initialText)) {
                        return false;
                     }
                  } else if(var2.initialText != null) {
                     return false;
                  }

                  if(this.inputBackground != null) {
                     if(!this.inputBackground.equals(var2.inputBackground)) {
                        return false;
                     }
                  } else if(var2.inputBackground != null) {
                     return false;
                  }

                  if(this.inputFilters != null) {
                     if(!this.inputFilters.equals(var2.inputFilters)) {
                        return false;
                     }
                  } else if(var2.inputFilters != null) {
                     return false;
                  }

                  if(this.inputType != var2.inputType) {
                     return false;
                  } else if(this.multiline != var2.multiline) {
                     return false;
                  } else if(this.shadowColor != var2.shadowColor) {
                     return false;
                  } else if(Float.compare(this.shadowDx, var2.shadowDx) != 0) {
                     return false;
                  } else if(Float.compare(this.shadowDy, var2.shadowDy) != 0) {
                     return false;
                  } else if(Float.compare(this.shadowRadius, var2.shadowRadius) != 0) {
                     return false;
                  } else if(this.textAlignment != var2.textAlignment) {
                     return false;
                  } else {
                     if(this.textColorStateList != null) {
                        if(!this.textColorStateList.equals(var2.textColorStateList)) {
                           return false;
                        }
                     } else if(var2.textColorStateList != null) {
                        return false;
                     }

                     if(this.textSize != var2.textSize) {
                        return false;
                     } else {
                        if(this.textWatchers != null) {
                           if(!this.textWatchers.equals(var2.textWatchers)) {
                              return false;
                           }
                        } else if(var2.textWatchers != null) {
                           return false;
                        }

                        if(this.typeface != null) {
                           if(!this.typeface.equals(var2.typeface)) {
                              return false;
                           }
                        } else if(var2.typeface != null) {
                           return false;
                        }

                        if(this.mStateContainer.measureSeqNumber != var2.mStateContainer.measureSeqNumber) {
                           return false;
                        } else {
                           if(this.mStateContainer.mountedView != null) {
                              if(!this.mStateContainer.mountedView.equals(var2.mStateContainer.mountedView)) {
                                 return false;
                              }
                           } else if(var2.mStateContainer.mountedView != null) {
                              return false;
                           }

                           if(this.mStateContainer.savedText != null) {
                              if(!this.mStateContainer.savedText.equals(var2.mStateContainer.savedText)) {
                                 return false;
                              }
                           } else if(var2.mStateContainer.savedText != null) {
                              return false;
                           }

                           return true;
                        }
                     }
                  }
               }
            }
         }
      } else {
         return false;
      }
   }

   public boolean isPureRender() {
      return true;
   }

   public TextInput makeShallowCopy() {
      TextInput var1 = (TextInput)super.makeShallowCopy();
      var1.mStateContainer = new TextInput.TextInputStateContainer();
      return var1;
   }

   protected Object onCreateMountContent(Context var1) {
      return TextInputSpec.onCreateMountContent(var1);
   }

   protected void onMeasure(ComponentContext var1, ComponentLayout var2, int var3, int var4, Size var5) {
      TextInputSpec.onMeasure(var1, var2, var3, var4, var5, this.hint, this.inputBackground, this.shadowRadius, this.shadowDx, this.shadowDy, this.shadowColor, this.textColorStateList, this.hintColorStateList, this.textSize, this.typeface, this.textAlignment, this.gravity, this.editable, this.inputType, this.imeOptions, this.inputFilters, this.multiline, this.mStateContainer.savedText, this.mStateContainer.measureSeqNumber);
   }

   protected void onMount(ComponentContext var1, Object var2) {
      TextInputSpec.onMount(var1, (TextInputSpec.EditTextWithEventHandlers)var2, this.hint, this.inputBackground, this.shadowRadius, this.shadowDx, this.shadowDy, this.shadowColor, this.textColorStateList, this.hintColorStateList, this.textSize, this.typeface, this.textAlignment, this.gravity, this.editable, this.inputType, this.imeOptions, this.inputFilters, this.multiline, this.textWatchers, this.mStateContainer.savedText, this.mStateContainer.mountedView);
   }

   protected void onUnmount(ComponentContext var1, Object var2) {
      TextInputSpec.onUnmount(var1, (TextInputSpec.EditTextWithEventHandlers)var2, this.mStateContainer.mountedView);
   }

   protected int poolSize() {
      return 3;
   }

   public void recordEventTrigger(EventTriggersContainer var1) {
      if(this.requestFocusTrigger != null) {
         this.requestFocusTrigger.mTriggerTarget = this;
         var1.recordEventTrigger(this.requestFocusTrigger);
      }

      if(this.clearFocusTrigger != null) {
         this.clearFocusTrigger.mTriggerTarget = this;
         var1.recordEventTrigger(this.clearFocusTrigger);
      }

      if(this.setTextTrigger != null) {
         this.setTextTrigger.mTriggerTarget = this;
         var1.recordEventTrigger(this.setTextTrigger);
      }

   }

   protected boolean shouldUpdate(Component var1, Component var2) {
      TextInput var10 = (TextInput)var1;
      TextInput var9 = (TextInput)var2;
      CharSequence var23;
      if(var10 == null) {
         var23 = null;
      } else {
         var23 = var10.initialText;
      }

      CharSequence var24;
      if(var9 == null) {
         var24 = null;
      } else {
         var24 = var9.initialText;
      }

      Diff var11 = this.acquireDiff(var23, var24);
      if(var10 == null) {
         var23 = null;
      } else {
         var23 = var10.hint;
      }

      if(var9 == null) {
         var24 = null;
      } else {
         var24 = var9.hint;
      }

      Diff var12 = this.acquireDiff(var23, var24);
      Drawable var25;
      if(var10 == null) {
         var25 = null;
      } else {
         var25 = var10.inputBackground;
      }

      Drawable var26;
      if(var9 == null) {
         var26 = null;
      } else {
         var26 = var9.inputBackground;
      }

      Diff var13 = this.acquireDiff(var25, var26);
      Float var27;
      if(var10 == null) {
         var27 = null;
      } else {
         var27 = Float.valueOf(var10.shadowRadius);
      }

      Float var28;
      if(var9 == null) {
         var28 = null;
      } else {
         var28 = Float.valueOf(var9.shadowRadius);
      }

      Diff var14 = this.acquireDiff(var27, var28);
      if(var10 == null) {
         var27 = null;
      } else {
         var27 = Float.valueOf(var10.shadowDx);
      }

      if(var9 == null) {
         var28 = null;
      } else {
         var28 = Float.valueOf(var9.shadowDx);
      }

      Diff var15 = this.acquireDiff(var27, var28);
      if(var10 == null) {
         var27 = null;
      } else {
         var27 = Float.valueOf(var10.shadowDy);
      }

      if(var9 == null) {
         var28 = null;
      } else {
         var28 = Float.valueOf(var9.shadowDy);
      }

      Diff var16 = this.acquireDiff(var27, var28);
      Integer var29;
      if(var10 == null) {
         var29 = null;
      } else {
         var29 = Integer.valueOf(var10.shadowColor);
      }

      Integer var30;
      if(var9 == null) {
         var30 = null;
      } else {
         var30 = Integer.valueOf(var9.shadowColor);
      }

      Diff var17 = this.acquireDiff(var29, var30);
      ColorStateList var31;
      if(var10 == null) {
         var31 = null;
      } else {
         var31 = var10.textColorStateList;
      }

      ColorStateList var32;
      if(var9 == null) {
         var32 = null;
      } else {
         var32 = var9.textColorStateList;
      }

      Diff var18 = this.acquireDiff(var31, var32);
      if(var10 == null) {
         var31 = null;
      } else {
         var31 = var10.hintColorStateList;
      }

      if(var9 == null) {
         var32 = null;
      } else {
         var32 = var9.hintColorStateList;
      }

      Diff var19 = this.acquireDiff(var31, var32);
      if(var10 == null) {
         var29 = null;
      } else {
         var29 = Integer.valueOf(var10.textSize);
      }

      if(var9 == null) {
         var30 = null;
      } else {
         var30 = Integer.valueOf(var9.textSize);
      }

      Diff var20 = this.acquireDiff(var29, var30);
      Typeface var33;
      if(var10 == null) {
         var33 = null;
      } else {
         var33 = var10.typeface;
      }

      Typeface var34;
      if(var9 == null) {
         var34 = null;
      } else {
         var34 = var9.typeface;
      }

      Diff var4 = this.acquireDiff(var33, var34);
      if(var10 == null) {
         var29 = null;
      } else {
         var29 = Integer.valueOf(var10.textAlignment);
      }

      if(var9 == null) {
         var30 = null;
      } else {
         var30 = Integer.valueOf(var9.textAlignment);
      }

      Diff var21 = this.acquireDiff(var29, var30);
      if(var10 == null) {
         var29 = null;
      } else {
         var29 = Integer.valueOf(var10.gravity);
      }

      if(var9 == null) {
         var30 = null;
      } else {
         var30 = Integer.valueOf(var9.gravity);
      }

      Diff var5 = this.acquireDiff(var29, var30);
      Boolean var35;
      if(var10 == null) {
         var35 = null;
      } else {
         var35 = Boolean.valueOf(var10.editable);
      }

      Boolean var36;
      if(var9 == null) {
         var36 = null;
      } else {
         var36 = Boolean.valueOf(var9.editable);
      }

      Diff var6 = this.acquireDiff(var35, var36);
      if(var10 == null) {
         var29 = null;
      } else {
         var29 = Integer.valueOf(var10.inputType);
      }

      if(var9 == null) {
         var30 = null;
      } else {
         var30 = Integer.valueOf(var9.inputType);
      }

      Diff var7 = this.acquireDiff(var29, var30);
      if(var10 == null) {
         var29 = null;
      } else {
         var29 = Integer.valueOf(var10.imeOptions);
      }

      if(var9 == null) {
         var30 = null;
      } else {
         var30 = Integer.valueOf(var9.imeOptions);
      }

      Diff var8 = this.acquireDiff(var29, var30);
      List var37;
      if(var10 == null) {
         var37 = null;
      } else {
         var37 = var10.inputFilters;
      }

      List var38;
      if(var9 == null) {
         var38 = null;
      } else {
         var38 = var9.inputFilters;
      }

      Diff var22 = this.acquireDiff(var37, var38);
      if(var10 == null) {
         var29 = null;
      } else {
         var29 = Integer.valueOf(var10.mStateContainer.measureSeqNumber);
      }

      if(var9 == null) {
         var30 = null;
      } else {
         var30 = Integer.valueOf(var9.mStateContainer.measureSeqNumber);
      }

      Diff var39 = this.acquireDiff(var29, var30);
      boolean var3 = TextInputSpec.shouldUpdate(var11, var12, var13, var14, var15, var16, var17, var18, var19, var20, var4, var21, var5, var6, var7, var8, var22, var39);
      this.releaseDiff(var11);
      this.releaseDiff(var12);
      this.releaseDiff(var13);
      this.releaseDiff(var14);
      this.releaseDiff(var15);
      this.releaseDiff(var16);
      this.releaseDiff(var17);
      this.releaseDiff(var18);
      this.releaseDiff(var19);
      this.releaseDiff(var20);
      this.releaseDiff(var4);
      this.releaseDiff(var21);
      this.releaseDiff(var5);
      this.releaseDiff(var6);
      this.releaseDiff(var7);
      this.releaseDiff(var8);
      this.releaseDiff(var22);
      this.releaseDiff(var39);
      return var3;
   }

   protected void transferState(ComponentContext var1, StateContainer var2) {
      TextInput.TextInputStateContainer var3 = (TextInput.TextInputStateContainer)var2;
      this.mStateContainer.measureSeqNumber = var3.measureSeqNumber;
      this.mStateContainer.mountedView = var3.mountedView;
      this.mStateContainer.savedText = var3.savedText;
   }

   public static class Builder extends Component.Builder<TextInput.Builder> {

      ComponentContext mContext;
      TextInput mTextInput;


      private void clearFocusTrigger(String var1) {
         EventTrigger var3 = this.mTextInput.clearFocusTrigger;
         EventTrigger var2 = var3;
         if(var3 == null) {
            var2 = TextInput.clearFocusTrigger(this.mContext, var1);
         }

         this.clearFocusTrigger(var2);
      }

      private void init(ComponentContext var1, int var2, int var3, TextInput var4) {
         super.init(var1, var2, var3, var4);
         this.mTextInput = var4;
         this.mContext = var1;
      }

      private void requestFocusTrigger(String var1) {
         EventTrigger var3 = this.mTextInput.requestFocusTrigger;
         EventTrigger var2 = var3;
         if(var3 == null) {
            var2 = TextInput.requestFocusTrigger(this.mContext, var1);
         }

         this.requestFocusTrigger(var2);
      }

      private void setTextTrigger(String var1) {
         EventTrigger var3 = this.mTextInput.setTextTrigger;
         EventTrigger var2 = var3;
         if(var3 == null) {
            var2 = TextInput.setTextTrigger(this.mContext, var1);
         }

         this.setTextTrigger(var2);
      }

      public TextInput build() {
         TextInput var1 = this.mTextInput;
         this.release();
         return var1;
      }

      public TextInput.Builder clearFocusTrigger(EventTrigger var1) {
         this.mTextInput.clearFocusTrigger = var1;
         return this;
      }

      public TextInput.Builder editable(boolean var1) {
         this.mTextInput.editable = var1;
         return this;
      }

      public TextInput.Builder editorActionEventHandler(EventHandler var1) {
         this.mTextInput.editorActionEventHandler = var1;
         return this;
      }

      public TextInput.Builder getThis() {
         return this;
      }

      public TextInput.Builder gravity(int var1) {
         this.mTextInput.gravity = var1;
         return this;
      }

      public TextInput.Builder hint(CharSequence var1) {
         this.mTextInput.hint = var1;
         return this;
      }

      public TextInput.Builder hintAttr(@AttrRes int var1) {
         this.mTextInput.hint = this.mResourceResolver.resolveStringAttr(var1, 0);
         return this;
      }

      public TextInput.Builder hintAttr(@AttrRes int var1, @StringRes int var2) {
         this.mTextInput.hint = this.mResourceResolver.resolveStringAttr(var1, var2);
         return this;
      }

      public TextInput.Builder hintColorStateList(ColorStateList var1) {
         this.mTextInput.hintColorStateList = var1;
         return this;
      }

      public TextInput.Builder hintRes(@StringRes int var1) {
         this.mTextInput.hint = this.mResourceResolver.resolveStringRes(var1);
         return this;
      }

      public TextInput.Builder hintRes(@StringRes int var1, Object ... var2) {
         this.mTextInput.hint = this.mResourceResolver.resolveStringRes(var1, var2);
         return this;
      }

      public TextInput.Builder imeOptions(int var1) {
         this.mTextInput.imeOptions = var1;
         return this;
      }

      public TextInput.Builder initialText(CharSequence var1) {
         this.mTextInput.initialText = var1;
         return this;
      }

      public TextInput.Builder initialTextAttr(@AttrRes int var1) {
         this.mTextInput.initialText = this.mResourceResolver.resolveStringAttr(var1, 0);
         return this;
      }

      public TextInput.Builder initialTextAttr(@AttrRes int var1, @StringRes int var2) {
         this.mTextInput.initialText = this.mResourceResolver.resolveStringAttr(var1, var2);
         return this;
      }

      public TextInput.Builder initialTextRes(@StringRes int var1) {
         this.mTextInput.initialText = this.mResourceResolver.resolveStringRes(var1);
         return this;
      }

      public TextInput.Builder initialTextRes(@StringRes int var1, Object ... var2) {
         this.mTextInput.initialText = this.mResourceResolver.resolveStringRes(var1, var2);
         return this;
      }

      public TextInput.Builder inputBackground(Drawable var1) {
         this.mTextInput.inputBackground = var1;
         return this;
      }

      public TextInput.Builder inputBackgroundAttr(@AttrRes int var1) {
         this.mTextInput.inputBackground = this.mResourceResolver.resolveDrawableAttr(var1, 0);
         return this;
      }

      public TextInput.Builder inputBackgroundAttr(@AttrRes int var1, @DrawableRes int var2) {
         this.mTextInput.inputBackground = this.mResourceResolver.resolveDrawableAttr(var1, var2);
         return this;
      }

      public TextInput.Builder inputBackgroundRes(@DrawableRes int var1) {
         this.mTextInput.inputBackground = this.mResourceResolver.resolveDrawableRes(var1);
         return this;
      }

      public TextInput.Builder inputFilter(InputFilter var1) {
         if(var1 == null) {
            return this;
         } else {
            if(this.mTextInput.inputFilters == null) {
               this.mTextInput.inputFilters = new ArrayList();
            }

            this.mTextInput.inputFilters.add(var1);
            return this;
         }
      }

      public TextInput.Builder inputFilters(List<InputFilter> var1) {
         if(var1 == null) {
            return this;
         } else if(this.mTextInput.inputFilters != null && !this.mTextInput.inputFilters.isEmpty()) {
            this.mTextInput.inputFilters.addAll(var1);
            return this;
         } else {
            this.mTextInput.inputFilters = var1;
            return this;
         }
      }

      public TextInput.Builder inputType(int var1) {
         this.mTextInput.inputType = var1;
         return this;
      }

      public TextInput.Builder key(String var1) {
         super.key(var1);
         this.requestFocusTrigger(var1);
         this.clearFocusTrigger(var1);
         this.setTextTrigger(var1);
         return this;
      }

      public TextInput.Builder keyUpEventHandler(EventHandler var1) {
         this.mTextInput.keyUpEventHandler = var1;
         return this;
      }

      public TextInput.Builder multiline(boolean var1) {
         this.mTextInput.multiline = var1;
         return this;
      }

      protected void release() {
         super.release();
         this.mTextInput = null;
         this.mContext = null;
      }

      public TextInput.Builder requestFocusTrigger(EventTrigger var1) {
         this.mTextInput.requestFocusTrigger = var1;
         return this;
      }

      public TextInput.Builder selectionChangedEventHandler(EventHandler var1) {
         this.mTextInput.selectionChangedEventHandler = var1;
         return this;
      }

      public TextInput.Builder setTextEventHandler(EventHandler var1) {
         this.mTextInput.setTextEventHandler = var1;
         return this;
      }

      public TextInput.Builder setTextTrigger(EventTrigger var1) {
         this.mTextInput.setTextTrigger = var1;
         return this;
      }

      public TextInput.Builder shadowColor(@ColorInt int var1) {
         this.mTextInput.shadowColor = var1;
         return this;
      }

      public TextInput.Builder shadowColorAttr(@AttrRes int var1) {
         this.mTextInput.shadowColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public TextInput.Builder shadowColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mTextInput.shadowColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public TextInput.Builder shadowColorRes(@ColorRes int var1) {
         this.mTextInput.shadowColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public TextInput.Builder shadowDxAttr(@AttrRes int var1) {
         this.mTextInput.shadowDx = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public TextInput.Builder shadowDxAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mTextInput.shadowDx = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public TextInput.Builder shadowDxDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mTextInput.shadowDx = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public TextInput.Builder shadowDxPx(@Px float var1) {
         this.mTextInput.shadowDx = var1;
         return this;
      }

      public TextInput.Builder shadowDxRes(@DimenRes int var1) {
         this.mTextInput.shadowDx = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public TextInput.Builder shadowDxSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mTextInput.shadowDx = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public TextInput.Builder shadowDyAttr(@AttrRes int var1) {
         this.mTextInput.shadowDy = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public TextInput.Builder shadowDyAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mTextInput.shadowDy = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public TextInput.Builder shadowDyDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mTextInput.shadowDy = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public TextInput.Builder shadowDyPx(@Px float var1) {
         this.mTextInput.shadowDy = var1;
         return this;
      }

      public TextInput.Builder shadowDyRes(@DimenRes int var1) {
         this.mTextInput.shadowDy = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public TextInput.Builder shadowDySp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mTextInput.shadowDy = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public TextInput.Builder shadowRadiusAttr(@AttrRes int var1) {
         this.mTextInput.shadowRadius = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public TextInput.Builder shadowRadiusAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mTextInput.shadowRadius = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public TextInput.Builder shadowRadiusDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mTextInput.shadowRadius = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public TextInput.Builder shadowRadiusPx(@Px float var1) {
         this.mTextInput.shadowRadius = var1;
         return this;
      }

      public TextInput.Builder shadowRadiusRes(@DimenRes int var1) {
         this.mTextInput.shadowRadius = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public TextInput.Builder shadowRadiusSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mTextInput.shadowRadius = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public TextInput.Builder textAlignment(int var1) {
         this.mTextInput.textAlignment = var1;
         return this;
      }

      public TextInput.Builder textChangedEventHandler(EventHandler var1) {
         this.mTextInput.textChangedEventHandler = var1;
         return this;
      }

      public TextInput.Builder textColorStateList(ColorStateList var1) {
         this.mTextInput.textColorStateList = var1;
         return this;
      }

      public TextInput.Builder textSizeAttr(@AttrRes int var1) {
         this.mTextInput.textSize = this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public TextInput.Builder textSizeAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mTextInput.textSize = this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public TextInput.Builder textSizeDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mTextInput.textSize = this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public TextInput.Builder textSizePx(@Px int var1) {
         this.mTextInput.textSize = var1;
         return this;
      }

      public TextInput.Builder textSizeRes(@DimenRes int var1) {
         this.mTextInput.textSize = this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public TextInput.Builder textSizeSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mTextInput.textSize = this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public TextInput.Builder textWatcher(TextWatcher var1) {
         if(var1 == null) {
            return this;
         } else {
            if(this.mTextInput.textWatchers == null) {
               this.mTextInput.textWatchers = new ArrayList();
            }

            this.mTextInput.textWatchers.add(var1);
            return this;
         }
      }

      public TextInput.Builder textWatchers(List<TextWatcher> var1) {
         if(var1 == null) {
            return this;
         } else if(this.mTextInput.textWatchers != null && !this.mTextInput.textWatchers.isEmpty()) {
            this.mTextInput.textWatchers.addAll(var1);
            return this;
         } else {
            this.mTextInput.textWatchers = var1;
            return this;
         }
      }

      public TextInput.Builder typeface(Typeface var1) {
         this.mTextInput.typeface = var1;
         return this;
      }
   }

   static class RemeasureForUpdatedTextStateUpdate implements ComponentLifecycle.StateUpdate {

      public void updateState(StateContainer var1, Component var2) {
         TextInput.TextInputStateContainer var4 = (TextInput.TextInputStateContainer)var1;
         TextInput var5 = (TextInput)var2;
         StateValue var3 = new StateValue();
         var3.set(Integer.valueOf(var4.measureSeqNumber));
         TextInputSpec.remeasureForUpdatedText(var3);
         var5.mStateContainer.measureSeqNumber = ((Integer)var3.get()).intValue();
      }
   }

   @VisibleForTesting(
      otherwise = 2
   )
   static class TextInputStateContainer implements StateContainer {

      @Comparable(
         type = 3
      )
      @State
      int measureSeqNumber;
      @Comparable(
         type = 13
      )
      @State
      AtomicReference<TextInputSpec.EditTextWithEventHandlers> mountedView;
      @Comparable(
         type = 13
      )
      @State
      AtomicReference<CharSequence> savedText;


   }
}
