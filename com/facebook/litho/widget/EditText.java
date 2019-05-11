package com.facebook.litho.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.support.annotation.AttrRes;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.IntegerRes;
import android.support.annotation.Px;
import android.support.annotation.StringRes;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.Layout.Alignment;
import android.text.TextUtils.TruncateAt;
import android.view.KeyEvent;
import android.widget.TextView.OnEditorActionListener;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.EventHandler;
import com.facebook.litho.EventTrigger;
import com.facebook.litho.EventTriggerTarget;
import com.facebook.litho.EventTriggersContainer;
import com.facebook.litho.Output;
import com.facebook.litho.Size;
import com.facebook.litho.StateContainer;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.State;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.widget.ClearFocusEvent;
import com.facebook.litho.widget.EditTextSpec;
import com.facebook.litho.widget.EditTextStateUpdatePolicy;
import com.facebook.litho.widget.KeyUpEvent;
import com.facebook.litho.widget.RequestFocusEvent;
import com.facebook.litho.widget.SelectionChangedEvent;
import com.facebook.litho.widget.SetTextEvent;
import com.facebook.litho.widget.TextChangedEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class EditText extends Component {

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
   int cursorDrawableRes = -1;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean editable = true;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   OnEditorActionListener editorActionListener;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   TruncateAt ellipsize;
   @Comparable(
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_OFFSET
   )
   float extraSpacing;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int gravity = 8388627;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int highlightColor;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.STRING
   )
   CharSequence hint;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int hintColor = 0;
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
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.BOOL
   )
   boolean isSingleLine;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean isSingleLineWrap;
   EventHandler keyUpEventHandler;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int linkColor;
   @Comparable(
      type = 14
   )
   private EditText.EditTextStateContainer mStateContainer;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.INT
   )
   int maxLength;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.INT
   )
   int maxLines;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.INT
   )
   int minLines;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int rawInputType;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean requestFocus;
   EventTrigger requestFocusTrigger;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int selection;
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
      type = 0
   )
   @Prop(
      optional = true,
      resType = ResType.FLOAT
   )
   float spacingMultiplier;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   EditTextStateUpdatePolicy stateUpdatePolicy;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.STRING
   )
   CharSequence text;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   Alignment textAlignment;
   EventHandler textChangedEventHandler;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int textColor;
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
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int textStyle;
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
   ColorStateList tintColorStateList;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   Typeface typeface;


   private EditText() {
      super("EditText");
      this.hintColorStateList = EditTextSpec.hintColorStateList;
      this.imeOptions = 0;
      this.inputType = 131073;
      this.isSingleLineWrap = false;
      this.linkColor = 0;
      this.maxLength = Integer.MAX_VALUE;
      this.maxLines = Integer.MAX_VALUE;
      this.minLines = Integer.MIN_VALUE;
      this.rawInputType = 0;
      this.requestFocus = false;
      this.selection = -1;
      this.shadowColor = -7829368;
      this.spacingMultiplier = 1.0F;
      this.stateUpdatePolicy = EditTextSpec.stateUpdatePolicy;
      this.textAlignment = EditTextSpec.textAlignment;
      this.textColor = 0;
      this.textColorStateList = EditTextSpec.textColorStateList;
      this.textSize = 13;
      this.textStyle = EditTextSpec.textStyle;
      this.typeface = EditTextSpec.typeface;
      this.mStateContainer = new EditText.EditTextStateContainer();
   }

   static void clearFocus(ComponentContext var0) {
      EditText var1 = (EditText)var0.getComponentScope();
      var1.clearFocus((EventTriggerTarget)var1);
   }

   public static void clearFocus(ComponentContext var0, String var1) {
      EventTrigger var2 = getEventTrigger(var0, -1050780906, var1);
      if(var2 != null) {
         var2.dispatchOnTrigger(new ClearFocusEvent(), new Object[0]);
      }
   }

   public static void clearFocus(EventTrigger var0) {
      var0.dispatchOnTrigger(new ClearFocusEvent(), new Object[0]);
   }

   private void clearFocus(EventTriggerTarget var1) {
      EditText var2 = (EditText)var1;
      EditTextSpec.clearFocus(var2.getScopedContext(), var2.mStateContainer.mountedView);
   }

   public static EventTrigger clearFocusTrigger(ComponentContext var0, String var1) {
      return newEventTrigger(var0, var1, -1050780906);
   }

   public static EditText.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static EditText.Builder create(ComponentContext var0, int var1, int var2) {
      EditText.Builder var3 = new EditText.Builder();
      var3.init(var0, var1, var2, new EditText());
      return var3;
   }

   private EditText.UpdateInputStateUpdate createUpdateInputStateUpdate(String var1) {
      return new EditText.UpdateInputStateUpdate(var1);
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

   public static EventHandler getKeyUpEventHandler(ComponentContext var0) {
      return var0.getComponentScope() == null?null:((EditText)var0.getComponentScope()).keyUpEventHandler;
   }

   public static EventHandler getSelectionChangedEventHandler(ComponentContext var0) {
      return var0.getComponentScope() == null?null:((EditText)var0.getComponentScope()).selectionChangedEventHandler;
   }

   public static EventHandler getSetTextEventHandler(ComponentContext var0) {
      return var0.getComponentScope() == null?null:((EditText)var0.getComponentScope()).setTextEventHandler;
   }

   public static EventHandler getTextChangedEventHandler(ComponentContext var0) {
      return var0.getComponentScope() == null?null:((EditText)var0.getComponentScope()).textChangedEventHandler;
   }

   protected static void lazyUpdateInput(ComponentContext var0, final String var1) {
      if(var0.getComponentScope() != null) {
         var0.updateStateLazy(new ComponentLifecycle.StateUpdate() {
            public void updateState(StateContainer var1x, Component var2) {
               EditText var3 = (EditText)var2;
               StateValue var4 = new StateValue();
               var4.set(var1);
               var3.mStateContainer.input = (String)var4.get();
            }
         });
      }
   }

   static void requestFocus(ComponentContext var0) {
      EditText var1 = (EditText)var0.getComponentScope();
      var1.requestFocus((EventTriggerTarget)var1);
   }

   public static void requestFocus(ComponentContext var0, String var1) {
      EventTrigger var2 = getEventTrigger(var0, 1670729240, var1);
      if(var2 != null) {
         var2.dispatchOnTrigger(new RequestFocusEvent(), new Object[0]);
      }
   }

   public static void requestFocus(EventTrigger var0) {
      var0.dispatchOnTrigger(new RequestFocusEvent(), new Object[0]);
   }

   private void requestFocus(EventTriggerTarget var1) {
      EditText var2 = (EditText)var1;
      EditTextSpec.requestFocus(var2.getScopedContext(), var2.mStateContainer.mountedView);
   }

   public static EventTrigger requestFocusTrigger(ComponentContext var0, String var1) {
      return newEventTrigger(var0, var1, 1670729240);
   }

   static void setText(ComponentContext var0, String var1) {
      EditText var2 = (EditText)var0.getComponentScope();
      var2.setText((EventTriggerTarget)var2, var1);
   }

   public static void setText(ComponentContext var0, String var1, String var2) {
      EventTrigger var3 = getEventTrigger(var0, 638451776, var1);
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
      EditText var3 = (EditText)var1;
      EditTextSpec.setText(var3.getScopedContext(), var3.mStateContainer.mountedView, var2);
   }

   public static EventTrigger setTextTrigger(ComponentContext var0, String var1) {
      return newEventTrigger(var0, var1, 638451776);
   }

   protected static void updateInput(ComponentContext var0, String var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateSync(((EditText)var2).createUpdateInputStateUpdate(var1), "EditText.updateInput");
      }
   }

   protected static void updateInputAsync(ComponentContext var0, String var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateAsync(((EditText)var2).createUpdateInputStateUpdate(var1), "EditText.updateInput");
      }
   }

   protected static void updateInputSync(ComponentContext var0, String var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateSync(((EditText)var2).createUpdateInputStateUpdate(var1), "EditText.updateInput");
      }
   }

   public Object acceptTriggerEvent(EventTrigger var1, Object var2, Object[] var3) {
      int var4 = var1.mId;
      if(var4 != -1050780906) {
         if(var4 != 638451776) {
            if(var4 != 1670729240) {
               return null;
            } else {
               RequestFocusEvent var7 = (RequestFocusEvent)var2;
               this.requestFocus(var1.mTriggerTarget);
               return null;
            }
         } else {
            SetTextEvent var6 = (SetTextEvent)var2;
            this.setText(var1.mTriggerTarget, var6.text);
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
      EditTextSpec.onCreateInitialState(var1, var2, var3);
      this.mStateContainer.mountedView = (AtomicReference)var2.get();
      this.mStateContainer.configuredInitialText = (AtomicBoolean)var3.get();
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
            EditText var2 = (EditText)var1;
            if(this.getId() == var2.getId()) {
               return true;
            } else if(this.cursorDrawableRes != var2.cursorDrawableRes) {
               return false;
            } else if(this.editable != var2.editable) {
               return false;
            } else {
               if(this.editorActionListener != null) {
                  if(!this.editorActionListener.equals(var2.editorActionListener)) {
                     return false;
                  }
               } else if(var2.editorActionListener != null) {
                  return false;
               }

               if(this.ellipsize != null) {
                  if(!this.ellipsize.equals(var2.ellipsize)) {
                     return false;
                  }
               } else if(var2.ellipsize != null) {
                  return false;
               }

               if(Float.compare(this.extraSpacing, var2.extraSpacing) != 0) {
                  return false;
               } else if(this.gravity != var2.gravity) {
                  return false;
               } else if(this.highlightColor != var2.highlightColor) {
                  return false;
               } else {
                  if(this.hint != null) {
                     if(!this.hint.equals(var2.hint)) {
                        return false;
                     }
                  } else if(var2.hint != null) {
                     return false;
                  }

                  if(this.hintColor != var2.hintColor) {
                     return false;
                  } else {
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

                        if(this.inputFilters != null) {
                           if(!this.inputFilters.equals(var2.inputFilters)) {
                              return false;
                           }
                        } else if(var2.inputFilters != null) {
                           return false;
                        }

                        if(this.inputType != var2.inputType) {
                           return false;
                        } else if(this.isSingleLine != var2.isSingleLine) {
                           return false;
                        } else if(this.isSingleLineWrap != var2.isSingleLineWrap) {
                           return false;
                        } else if(this.linkColor != var2.linkColor) {
                           return false;
                        } else if(this.maxLength != var2.maxLength) {
                           return false;
                        } else if(this.maxLines != var2.maxLines) {
                           return false;
                        } else if(this.minLines != var2.minLines) {
                           return false;
                        } else if(this.rawInputType != var2.rawInputType) {
                           return false;
                        } else if(this.requestFocus != var2.requestFocus) {
                           return false;
                        } else if(this.selection != var2.selection) {
                           return false;
                        } else if(this.shadowColor != var2.shadowColor) {
                           return false;
                        } else if(Float.compare(this.shadowDx, var2.shadowDx) != 0) {
                           return false;
                        } else if(Float.compare(this.shadowDy, var2.shadowDy) != 0) {
                           return false;
                        } else if(Float.compare(this.shadowRadius, var2.shadowRadius) != 0) {
                           return false;
                        } else if(Float.compare(this.spacingMultiplier, var2.spacingMultiplier) != 0) {
                           return false;
                        } else {
                           if(this.stateUpdatePolicy != null) {
                              if(!this.stateUpdatePolicy.equals(var2.stateUpdatePolicy)) {
                                 return false;
                              }
                           } else if(var2.stateUpdatePolicy != null) {
                              return false;
                           }

                           if(this.text != null) {
                              if(!this.text.equals(var2.text)) {
                                 return false;
                              }
                           } else if(var2.text != null) {
                              return false;
                           }

                           if(this.textAlignment != null) {
                              if(!this.textAlignment.equals(var2.textAlignment)) {
                                 return false;
                              }
                           } else if(var2.textAlignment != null) {
                              return false;
                           }

                           if(this.textColor != var2.textColor) {
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
                              } else if(this.textStyle != var2.textStyle) {
                                 return false;
                              } else {
                                 if(this.textWatchers != null) {
                                    if(!this.textWatchers.equals(var2.textWatchers)) {
                                       return false;
                                    }
                                 } else if(var2.textWatchers != null) {
                                    return false;
                                 }

                                 if(this.tintColorStateList != null) {
                                    if(!this.tintColorStateList.equals(var2.tintColorStateList)) {
                                       return false;
                                    }
                                 } else if(var2.tintColorStateList != null) {
                                    return false;
                                 }

                                 if(this.typeface != null) {
                                    if(!this.typeface.equals(var2.typeface)) {
                                       return false;
                                    }
                                 } else if(var2.typeface != null) {
                                    return false;
                                 }

                                 if(this.mStateContainer.configuredInitialText != null) {
                                    if(!this.mStateContainer.configuredInitialText.equals(var2.mStateContainer.configuredInitialText)) {
                                       return false;
                                    }
                                 } else if(var2.mStateContainer.configuredInitialText != null) {
                                    return false;
                                 }

                                 if(this.mStateContainer.input != null) {
                                    if(!this.mStateContainer.input.equals(var2.mStateContainer.input)) {
                                       return false;
                                    }
                                 } else if(var2.mStateContainer.input != null) {
                                    return false;
                                 }

                                 if(this.mStateContainer.mountedView != null) {
                                    if(!this.mStateContainer.mountedView.equals(var2.mStateContainer.mountedView)) {
                                       return false;
                                    }
                                 } else if(var2.mStateContainer.mountedView != null) {
                                    return false;
                                 }

                                 return true;
                              }
                           }
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

   public EditText makeShallowCopy() {
      EditText var1 = (EditText)super.makeShallowCopy();
      var1.mStateContainer = new EditText.EditTextStateContainer();
      return var1;
   }

   protected void onBind(ComponentContext var1, Object var2) {
      EditTextSpec.onBind(var1, (EditTextSpec.EditTextWithEventHandlers)var2, this.stateUpdatePolicy, this.textWatchers);
   }

   protected Object onCreateMountContent(Context var1) {
      return EditTextSpec.onCreateMountContent(var1);
   }

   protected void onLoadStyle(ComponentContext var1) {
      Output var2 = this.acquireOutput();
      Output var3 = this.acquireOutput();
      Output var4 = this.acquireOutput();
      Output var5 = this.acquireOutput();
      Output var6 = this.acquireOutput();
      Output var7 = this.acquireOutput();
      Output var8 = this.acquireOutput();
      Output var9 = this.acquireOutput();
      Output var10 = this.acquireOutput();
      Output var11 = this.acquireOutput();
      Output var12 = this.acquireOutput();
      Output var13 = this.acquireOutput();
      Output var14 = this.acquireOutput();
      Output var15 = this.acquireOutput();
      Output var16 = this.acquireOutput();
      Output var17 = this.acquireOutput();
      Output var18 = this.acquireOutput();
      Output var19 = this.acquireOutput();
      Output var20 = this.acquireOutput();
      EditTextSpec.onLoadStyle(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20);
      if(var2.get() != null) {
         this.ellipsize = (TruncateAt)var2.get();
      }

      this.releaseOutput(var2);
      if(var3.get() != null) {
         this.spacingMultiplier = ((Float)var3.get()).floatValue();
      }

      this.releaseOutput(var3);
      if(var4.get() != null) {
         this.minLines = ((Integer)var4.get()).intValue();
      }

      this.releaseOutput(var4);
      if(var5.get() != null) {
         this.maxLines = ((Integer)var5.get()).intValue();
      }

      this.releaseOutput(var5);
      if(var6.get() != null) {
         this.isSingleLine = ((Boolean)var6.get()).booleanValue();
      }

      this.releaseOutput(var6);
      if(var7.get() != null) {
         this.text = (CharSequence)var7.get();
      }

      this.releaseOutput(var7);
      if(var8.get() != null) {
         this.textColorStateList = (ColorStateList)var8.get();
      }

      this.releaseOutput(var8);
      if(var9.get() != null) {
         this.linkColor = ((Integer)var9.get()).intValue();
      }

      this.releaseOutput(var9);
      if(var10.get() != null) {
         this.highlightColor = ((Integer)var10.get()).intValue();
      }

      this.releaseOutput(var10);
      if(var11.get() != null) {
         this.textSize = ((Integer)var11.get()).intValue();
      }

      this.releaseOutput(var11);
      if(var12.get() != null) {
         this.textAlignment = (Alignment)var12.get();
      }

      this.releaseOutput(var12);
      if(var13.get() != null) {
         this.textStyle = ((Integer)var13.get()).intValue();
      }

      this.releaseOutput(var13);
      if(var14.get() != null) {
         this.shadowRadius = ((Float)var14.get()).floatValue();
      }

      this.releaseOutput(var14);
      if(var15.get() != null) {
         this.shadowDx = ((Float)var15.get()).floatValue();
      }

      this.releaseOutput(var15);
      if(var16.get() != null) {
         this.shadowDy = ((Float)var16.get()).floatValue();
      }

      this.releaseOutput(var16);
      if(var17.get() != null) {
         this.shadowColor = ((Integer)var17.get()).intValue();
      }

      this.releaseOutput(var17);
      if(var18.get() != null) {
         this.gravity = ((Integer)var18.get()).intValue();
      }

      this.releaseOutput(var18);
      if(var19.get() != null) {
         this.inputType = ((Integer)var19.get()).intValue();
      }

      this.releaseOutput(var19);
      if(var20.get() != null) {
         this.imeOptions = ((Integer)var20.get()).intValue();
      }

      this.releaseOutput(var20);
   }

   protected void onMeasure(ComponentContext var1, ComponentLayout var2, int var3, int var4, Size var5) {
      EditTextSpec.onMeasure(var1, var2, var3, var4, var5, this.text, this.initialText, this.hint, this.ellipsize, this.minLines, this.maxLines, this.maxLength, this.shadowRadius, this.shadowDx, this.shadowDy, this.shadowColor, this.isSingleLine, this.textColor, this.textColorStateList, this.hintColor, this.hintColorStateList, this.linkColor, this.highlightColor, this.tintColorStateList, this.textSize, this.extraSpacing, this.spacingMultiplier, this.textStyle, this.typeface, this.textAlignment, this.gravity, this.editable, this.selection, this.inputType, this.rawInputType, this.imeOptions, this.editorActionListener, this.isSingleLineWrap, this.requestFocus, this.cursorDrawableRes, this.inputFilters, this.mStateContainer.input);
   }

   protected void onMount(ComponentContext var1, Object var2) {
      EditTextSpec.onMount(var1, (EditTextSpec.EditTextWithEventHandlers)var2, this.text, this.initialText, this.hint, this.ellipsize, this.minLines, this.maxLines, this.maxLength, this.shadowRadius, this.shadowDx, this.shadowDy, this.shadowColor, this.isSingleLine, this.textColor, this.textColorStateList, this.hintColor, this.hintColorStateList, this.linkColor, this.highlightColor, this.tintColorStateList, this.textSize, this.extraSpacing, this.spacingMultiplier, this.textStyle, this.typeface, this.textAlignment, this.gravity, this.editable, this.selection, this.inputType, this.rawInputType, this.imeOptions, this.editorActionListener, this.isSingleLineWrap, this.requestFocus, this.cursorDrawableRes, this.inputFilters, this.mStateContainer.mountedView, this.mStateContainer.configuredInitialText, this.mStateContainer.input);
   }

   protected void onUnbind(ComponentContext var1, Object var2) {
      EditTextSpec.onUnbind(var1, (EditTextSpec.EditTextWithEventHandlers)var2);
   }

   protected void onUnmount(ComponentContext var1, Object var2) {
      EditTextSpec.onUnmount(var1, (EditTextSpec.EditTextWithEventHandlers)var2, this.mStateContainer.mountedView);
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

   protected void transferState(ComponentContext var1, StateContainer var2) {
      EditText.EditTextStateContainer var3 = (EditText.EditTextStateContainer)var2;
      this.mStateContainer.configuredInitialText = var3.configuredInitialText;
      this.mStateContainer.input = var3.input;
      this.mStateContainer.mountedView = var3.mountedView;
   }

   static class UpdateInputStateUpdate implements ComponentLifecycle.StateUpdate {

      private String mNewInput;


      UpdateInputStateUpdate(String var1) {
         this.mNewInput = var1;
      }

      public void updateState(StateContainer var1, Component var2) {
         EditText.EditTextStateContainer var4 = (EditText.EditTextStateContainer)var1;
         EditText var5 = (EditText)var2;
         StateValue var3 = new StateValue();
         var3.set(var4.input);
         EditTextSpec.updateInput(var3, this.mNewInput);
         var5.mStateContainer.input = (String)var3.get();
      }
   }

   public static class Builder extends Component.Builder<EditText.Builder> {

      ComponentContext mContext;
      EditText mEditText;


      private void clearFocusTrigger(String var1) {
         EventTrigger var3 = this.mEditText.clearFocusTrigger;
         EventTrigger var2 = var3;
         if(var3 == null) {
            var2 = EditText.clearFocusTrigger(this.mContext, var1);
         }

         this.clearFocusTrigger(var2);
      }

      private void init(ComponentContext var1, int var2, int var3, EditText var4) {
         super.init(var1, var2, var3, var4);
         this.mEditText = var4;
         this.mContext = var1;
      }

      private void requestFocusTrigger(String var1) {
         EventTrigger var3 = this.mEditText.requestFocusTrigger;
         EventTrigger var2 = var3;
         if(var3 == null) {
            var2 = EditText.requestFocusTrigger(this.mContext, var1);
         }

         this.requestFocusTrigger(var2);
      }

      private void setTextTrigger(String var1) {
         EventTrigger var3 = this.mEditText.setTextTrigger;
         EventTrigger var2 = var3;
         if(var3 == null) {
            var2 = EditText.setTextTrigger(this.mContext, var1);
         }

         this.setTextTrigger(var2);
      }

      public EditText build() {
         EditText var1 = this.mEditText;
         this.release();
         return var1;
      }

      public EditText.Builder clearFocusTrigger(EventTrigger var1) {
         this.mEditText.clearFocusTrigger = var1;
         return this;
      }

      public EditText.Builder cursorDrawableRes(int var1) {
         this.mEditText.cursorDrawableRes = var1;
         return this;
      }

      public EditText.Builder editable(boolean var1) {
         this.mEditText.editable = var1;
         return this;
      }

      public EditText.Builder editorActionListener(OnEditorActionListener var1) {
         this.mEditText.editorActionListener = var1;
         return this;
      }

      public EditText.Builder ellipsize(TruncateAt var1) {
         this.mEditText.ellipsize = var1;
         return this;
      }

      public EditText.Builder extraSpacingAttr(@AttrRes int var1) {
         this.mEditText.extraSpacing = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public EditText.Builder extraSpacingAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mEditText.extraSpacing = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public EditText.Builder extraSpacingDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mEditText.extraSpacing = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public EditText.Builder extraSpacingPx(@Px float var1) {
         this.mEditText.extraSpacing = var1;
         return this;
      }

      public EditText.Builder extraSpacingRes(@DimenRes int var1) {
         this.mEditText.extraSpacing = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public EditText.Builder extraSpacingSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mEditText.extraSpacing = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public EditText.Builder getThis() {
         return this;
      }

      public EditText.Builder gravity(int var1) {
         this.mEditText.gravity = var1;
         return this;
      }

      public EditText.Builder highlightColor(@ColorInt int var1) {
         this.mEditText.highlightColor = var1;
         return this;
      }

      public EditText.Builder highlightColorAttr(@AttrRes int var1) {
         this.mEditText.highlightColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public EditText.Builder highlightColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mEditText.highlightColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public EditText.Builder highlightColorRes(@ColorRes int var1) {
         this.mEditText.highlightColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public EditText.Builder hint(CharSequence var1) {
         this.mEditText.hint = var1;
         return this;
      }

      public EditText.Builder hintAttr(@AttrRes int var1) {
         this.mEditText.hint = this.mResourceResolver.resolveStringAttr(var1, 0);
         return this;
      }

      public EditText.Builder hintAttr(@AttrRes int var1, @StringRes int var2) {
         this.mEditText.hint = this.mResourceResolver.resolveStringAttr(var1, var2);
         return this;
      }

      public EditText.Builder hintColor(@ColorInt int var1) {
         this.mEditText.hintColor = var1;
         return this;
      }

      public EditText.Builder hintColorAttr(@AttrRes int var1) {
         this.mEditText.hintColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public EditText.Builder hintColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mEditText.hintColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public EditText.Builder hintColorRes(@ColorRes int var1) {
         this.mEditText.hintColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public EditText.Builder hintColorStateList(ColorStateList var1) {
         this.mEditText.hintColorStateList = var1;
         return this;
      }

      public EditText.Builder hintRes(@StringRes int var1) {
         this.mEditText.hint = this.mResourceResolver.resolveStringRes(var1);
         return this;
      }

      public EditText.Builder hintRes(@StringRes int var1, Object ... var2) {
         this.mEditText.hint = this.mResourceResolver.resolveStringRes(var1, var2);
         return this;
      }

      public EditText.Builder imeOptions(int var1) {
         this.mEditText.imeOptions = var1;
         return this;
      }

      public EditText.Builder initialText(CharSequence var1) {
         this.mEditText.initialText = var1;
         return this;
      }

      public EditText.Builder initialTextAttr(@AttrRes int var1) {
         this.mEditText.initialText = this.mResourceResolver.resolveStringAttr(var1, 0);
         return this;
      }

      public EditText.Builder initialTextAttr(@AttrRes int var1, @StringRes int var2) {
         this.mEditText.initialText = this.mResourceResolver.resolveStringAttr(var1, var2);
         return this;
      }

      public EditText.Builder initialTextRes(@StringRes int var1) {
         this.mEditText.initialText = this.mResourceResolver.resolveStringRes(var1);
         return this;
      }

      public EditText.Builder initialTextRes(@StringRes int var1, Object ... var2) {
         this.mEditText.initialText = this.mResourceResolver.resolveStringRes(var1, var2);
         return this;
      }

      public EditText.Builder inputFilter(InputFilter var1) {
         if(var1 == null) {
            return this;
         } else {
            if(this.mEditText.inputFilters == null) {
               this.mEditText.inputFilters = new ArrayList();
            }

            this.mEditText.inputFilters.add(var1);
            return this;
         }
      }

      public EditText.Builder inputFilters(List<InputFilter> var1) {
         if(var1 == null) {
            return this;
         } else if(this.mEditText.inputFilters != null && !this.mEditText.inputFilters.isEmpty()) {
            this.mEditText.inputFilters.addAll(var1);
            return this;
         } else {
            this.mEditText.inputFilters = var1;
            return this;
         }
      }

      public EditText.Builder inputType(int var1) {
         this.mEditText.inputType = var1;
         return this;
      }

      public EditText.Builder isSingleLine(boolean var1) {
         this.mEditText.isSingleLine = var1;
         return this;
      }

      public EditText.Builder isSingleLineAttr(@AttrRes int var1) {
         this.mEditText.isSingleLine = this.mResourceResolver.resolveBoolAttr(var1, 0);
         return this;
      }

      public EditText.Builder isSingleLineAttr(@AttrRes int var1, @BoolRes int var2) {
         this.mEditText.isSingleLine = this.mResourceResolver.resolveBoolAttr(var1, var2);
         return this;
      }

      public EditText.Builder isSingleLineRes(@BoolRes int var1) {
         this.mEditText.isSingleLine = this.mResourceResolver.resolveBoolRes(var1);
         return this;
      }

      public EditText.Builder isSingleLineWrap(boolean var1) {
         this.mEditText.isSingleLineWrap = var1;
         return this;
      }

      public EditText.Builder key(String var1) {
         super.key(var1);
         this.requestFocusTrigger(var1);
         this.clearFocusTrigger(var1);
         this.setTextTrigger(var1);
         return this;
      }

      public EditText.Builder keyUpEventHandler(EventHandler var1) {
         this.mEditText.keyUpEventHandler = var1;
         return this;
      }

      public EditText.Builder linkColor(@ColorInt int var1) {
         this.mEditText.linkColor = var1;
         return this;
      }

      public EditText.Builder linkColorAttr(@AttrRes int var1) {
         this.mEditText.linkColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public EditText.Builder linkColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mEditText.linkColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public EditText.Builder linkColorRes(@ColorRes int var1) {
         this.mEditText.linkColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public EditText.Builder maxLength(int var1) {
         this.mEditText.maxLength = var1;
         return this;
      }

      public EditText.Builder maxLengthAttr(@AttrRes int var1) {
         this.mEditText.maxLength = this.mResourceResolver.resolveIntAttr(var1, 0);
         return this;
      }

      public EditText.Builder maxLengthAttr(@AttrRes int var1, @IntegerRes int var2) {
         this.mEditText.maxLength = this.mResourceResolver.resolveIntAttr(var1, var2);
         return this;
      }

      public EditText.Builder maxLengthRes(@IntegerRes int var1) {
         this.mEditText.maxLength = this.mResourceResolver.resolveIntRes(var1);
         return this;
      }

      public EditText.Builder maxLines(int var1) {
         this.mEditText.maxLines = var1;
         return this;
      }

      public EditText.Builder maxLinesAttr(@AttrRes int var1) {
         this.mEditText.maxLines = this.mResourceResolver.resolveIntAttr(var1, 0);
         return this;
      }

      public EditText.Builder maxLinesAttr(@AttrRes int var1, @IntegerRes int var2) {
         this.mEditText.maxLines = this.mResourceResolver.resolveIntAttr(var1, var2);
         return this;
      }

      public EditText.Builder maxLinesRes(@IntegerRes int var1) {
         this.mEditText.maxLines = this.mResourceResolver.resolveIntRes(var1);
         return this;
      }

      public EditText.Builder minLines(int var1) {
         this.mEditText.minLines = var1;
         return this;
      }

      public EditText.Builder minLinesAttr(@AttrRes int var1) {
         this.mEditText.minLines = this.mResourceResolver.resolveIntAttr(var1, 0);
         return this;
      }

      public EditText.Builder minLinesAttr(@AttrRes int var1, @IntegerRes int var2) {
         this.mEditText.minLines = this.mResourceResolver.resolveIntAttr(var1, var2);
         return this;
      }

      public EditText.Builder minLinesRes(@IntegerRes int var1) {
         this.mEditText.minLines = this.mResourceResolver.resolveIntRes(var1);
         return this;
      }

      public EditText.Builder rawInputType(int var1) {
         this.mEditText.rawInputType = var1;
         return this;
      }

      protected void release() {
         super.release();
         this.mEditText = null;
         this.mContext = null;
      }

      public EditText.Builder requestFocus(boolean var1) {
         this.mEditText.requestFocus = var1;
         return this;
      }

      public EditText.Builder requestFocusTrigger(EventTrigger var1) {
         this.mEditText.requestFocusTrigger = var1;
         return this;
      }

      public EditText.Builder selection(int var1) {
         this.mEditText.selection = var1;
         return this;
      }

      public EditText.Builder selectionChangedEventHandler(EventHandler var1) {
         this.mEditText.selectionChangedEventHandler = var1;
         return this;
      }

      public EditText.Builder setTextEventHandler(EventHandler var1) {
         this.mEditText.setTextEventHandler = var1;
         return this;
      }

      public EditText.Builder setTextTrigger(EventTrigger var1) {
         this.mEditText.setTextTrigger = var1;
         return this;
      }

      public EditText.Builder shadowColor(@ColorInt int var1) {
         this.mEditText.shadowColor = var1;
         return this;
      }

      public EditText.Builder shadowColorAttr(@AttrRes int var1) {
         this.mEditText.shadowColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public EditText.Builder shadowColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mEditText.shadowColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public EditText.Builder shadowColorRes(@ColorRes int var1) {
         this.mEditText.shadowColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public EditText.Builder shadowDxAttr(@AttrRes int var1) {
         this.mEditText.shadowDx = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public EditText.Builder shadowDxAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mEditText.shadowDx = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public EditText.Builder shadowDxDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mEditText.shadowDx = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public EditText.Builder shadowDxPx(@Px float var1) {
         this.mEditText.shadowDx = var1;
         return this;
      }

      public EditText.Builder shadowDxRes(@DimenRes int var1) {
         this.mEditText.shadowDx = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public EditText.Builder shadowDxSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mEditText.shadowDx = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public EditText.Builder shadowDyAttr(@AttrRes int var1) {
         this.mEditText.shadowDy = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public EditText.Builder shadowDyAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mEditText.shadowDy = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public EditText.Builder shadowDyDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mEditText.shadowDy = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public EditText.Builder shadowDyPx(@Px float var1) {
         this.mEditText.shadowDy = var1;
         return this;
      }

      public EditText.Builder shadowDyRes(@DimenRes int var1) {
         this.mEditText.shadowDy = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public EditText.Builder shadowDySp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mEditText.shadowDy = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public EditText.Builder shadowRadiusAttr(@AttrRes int var1) {
         this.mEditText.shadowRadius = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public EditText.Builder shadowRadiusAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mEditText.shadowRadius = (float)this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public EditText.Builder shadowRadiusDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mEditText.shadowRadius = (float)this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public EditText.Builder shadowRadiusPx(@Px float var1) {
         this.mEditText.shadowRadius = var1;
         return this;
      }

      public EditText.Builder shadowRadiusRes(@DimenRes int var1) {
         this.mEditText.shadowRadius = (float)this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public EditText.Builder shadowRadiusSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mEditText.shadowRadius = (float)this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public EditText.Builder spacingMultiplier(float var1) {
         this.mEditText.spacingMultiplier = var1;
         return this;
      }

      public EditText.Builder spacingMultiplierAttr(@AttrRes int var1) {
         this.mEditText.spacingMultiplier = this.mResourceResolver.resolveFloatAttr(var1, 0);
         return this;
      }

      public EditText.Builder spacingMultiplierAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mEditText.spacingMultiplier = this.mResourceResolver.resolveFloatAttr(var1, var2);
         return this;
      }

      public EditText.Builder spacingMultiplierRes(@DimenRes int var1) {
         this.mEditText.spacingMultiplier = this.mResourceResolver.resolveFloatRes(var1);
         return this;
      }

      public EditText.Builder stateUpdatePolicy(EditTextStateUpdatePolicy var1) {
         this.mEditText.stateUpdatePolicy = var1;
         return this;
      }

      public EditText.Builder text(CharSequence var1) {
         this.mEditText.text = var1;
         return this;
      }

      public EditText.Builder textAlignment(Alignment var1) {
         this.mEditText.textAlignment = var1;
         return this;
      }

      public EditText.Builder textAttr(@AttrRes int var1) {
         this.mEditText.text = this.mResourceResolver.resolveStringAttr(var1, 0);
         return this;
      }

      public EditText.Builder textAttr(@AttrRes int var1, @StringRes int var2) {
         this.mEditText.text = this.mResourceResolver.resolveStringAttr(var1, var2);
         return this;
      }

      public EditText.Builder textChangedEventHandler(EventHandler var1) {
         this.mEditText.textChangedEventHandler = var1;
         return this;
      }

      public EditText.Builder textColor(@ColorInt int var1) {
         this.mEditText.textColor = var1;
         return this;
      }

      public EditText.Builder textColorAttr(@AttrRes int var1) {
         this.mEditText.textColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public EditText.Builder textColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mEditText.textColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public EditText.Builder textColorRes(@ColorRes int var1) {
         this.mEditText.textColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      public EditText.Builder textColorStateList(ColorStateList var1) {
         this.mEditText.textColorStateList = var1;
         return this;
      }

      public EditText.Builder textRes(@StringRes int var1) {
         this.mEditText.text = this.mResourceResolver.resolveStringRes(var1);
         return this;
      }

      public EditText.Builder textRes(@StringRes int var1, Object ... var2) {
         this.mEditText.text = this.mResourceResolver.resolveStringRes(var1, var2);
         return this;
      }

      public EditText.Builder textSizeAttr(@AttrRes int var1) {
         this.mEditText.textSize = this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public EditText.Builder textSizeAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mEditText.textSize = this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public EditText.Builder textSizeDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mEditText.textSize = this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public EditText.Builder textSizePx(@Px int var1) {
         this.mEditText.textSize = var1;
         return this;
      }

      public EditText.Builder textSizeRes(@DimenRes int var1) {
         this.mEditText.textSize = this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public EditText.Builder textSizeSp(
         @Dimension(
            unit = 2
         ) float var1) {
         this.mEditText.textSize = this.mResourceResolver.sipsToPixels(var1);
         return this;
      }

      public EditText.Builder textStyle(int var1) {
         this.mEditText.textStyle = var1;
         return this;
      }

      public EditText.Builder textWatcher(TextWatcher var1) {
         if(var1 == null) {
            return this;
         } else {
            if(this.mEditText.textWatchers == null) {
               this.mEditText.textWatchers = new ArrayList();
            }

            this.mEditText.textWatchers.add(var1);
            return this;
         }
      }

      public EditText.Builder textWatchers(List<TextWatcher> var1) {
         if(var1 == null) {
            return this;
         } else if(this.mEditText.textWatchers != null && !this.mEditText.textWatchers.isEmpty()) {
            this.mEditText.textWatchers.addAll(var1);
            return this;
         } else {
            this.mEditText.textWatchers = var1;
            return this;
         }
      }

      public EditText.Builder tintColorStateList(ColorStateList var1) {
         this.mEditText.tintColorStateList = var1;
         return this;
      }

      public EditText.Builder typeface(Typeface var1) {
         this.mEditText.typeface = var1;
         return this;
      }
   }

   @VisibleForTesting(
      otherwise = 2
   )
   static class EditTextStateContainer implements StateContainer {

      @Comparable(
         type = 13
      )
      @State
      AtomicBoolean configuredInitialText;
      @Comparable(
         type = 13
      )
      @State
      String input;
      @Comparable(
         type = 13
      )
      @State
      AtomicReference<EditTextSpec.EditTextWithEventHandlers> mountedView;


   }
}
