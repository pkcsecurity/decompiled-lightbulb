package com.facebook.litho.sections.widget;

import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Dimension;
import android.support.annotation.IdRes;
import android.support.annotation.Px;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Pools;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.ErrorEvent;
import com.facebook.litho.EventHandler;
import com.facebook.litho.EventTrigger;
import com.facebook.litho.EventTriggerTarget;
import com.facebook.litho.EventTriggersContainer;
import com.facebook.litho.HasEventDispatcher;
import com.facebook.litho.StateContainer;
import com.facebook.litho.StateValue;
import com.facebook.litho.TouchEvent;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.State;
import com.facebook.litho.config.ComponentsConfiguration;
import com.facebook.litho.sections.LoadEventsHandler;
import com.facebook.litho.sections.Section;
import com.facebook.litho.sections.SectionTree;
import com.facebook.litho.sections.widget.RecyclerCollectionComponentSpec;
import com.facebook.litho.sections.widget.RecyclerCollectionEventsController;
import com.facebook.litho.sections.widget.RecyclerConfiguration;
import com.facebook.litho.sections.widget.ScrollEvent;
import com.facebook.litho.widget.Binder;
import com.facebook.litho.widget.LithoRecylerView;
import com.facebook.litho.widget.PTRRefreshEvent;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public final class RecyclerCollectionComponent extends Component {

   static final Pools.SynchronizedPool<PTRRefreshEvent> sPTRRefreshEventPool = new Pools.SynchronizedPool(2);
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean asyncPropUpdates;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean asyncStateUpdates;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_SIZE
   )
   int bottomPadding;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean canMeasureRecycler;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean clipChildren;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean clipToPadding;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean disablePTR;
   @Comparable(
      type = 10
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   Component emptyComponent;
   @Comparable(
      type = 10
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   Component errorComponent;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   RecyclerCollectionEventsController eventsController;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_SIZE
   )
   int fadingEdgeLength;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean forceSyncStateUpdates;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean horizontalFadingEdgeEnabled;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean ignoreLoadingUpdates;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   RecyclerView.ItemAnimator itemAnimator;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   RecyclerView.ItemDecoration itemDecoration;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_SIZE
   )
   int leftPadding;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   LoadEventsHandler loadEventsHandler;
   @Comparable(
      type = 10
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   Component loadingComponent;
   @Comparable(
      type = 14
   )
   private RecyclerCollectionComponent.RecyclerCollectionComponentStateContainer mStateContainer;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean nestedScrollingEnabled;
   @Comparable(
      type = 5
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   List<RecyclerView.OnScrollListener> onScrollListeners;
   EventTrigger onScrollTrigger;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int overScrollMode;
   EventHandler pTRRefreshEventHandler;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   RecyclerConfiguration recyclerConfiguration;
   @Comparable(
      type = 12
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   EventHandler<TouchEvent> recyclerTouchEventHandler;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   @IdRes
   int recyclerViewId;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.COLOR
   )
   int refreshProgressBarColor;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_SIZE
   )
   int rightPadding;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   int scrollBarStyle;
   @Comparable(
      type = 15
   )
   @Prop(
      optional = false,
      resType = ResType.NONE
   )
   Section section;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   String sectionTreeTag;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean setRootAsync;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.DIMEN_SIZE
   )
   int topPadding;
   @Comparable(
      type = 13
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   LithoRecylerView.TouchInterceptor touchInterceptor;
   @Comparable(
      type = 3
   )
   @Prop(
      optional = true,
      resType = ResType.NONE
   )
   boolean verticalFadingEdgeEnabled;


   private RecyclerCollectionComponent() {
      super("RecyclerCollectionComponent");
      this.asyncPropUpdates = RecyclerCollectionComponentSpec.asyncPropUpdates;
      this.asyncStateUpdates = RecyclerCollectionComponentSpec.asyncStateUpdates;
      this.clipChildren = true;
      this.clipToPadding = true;
      this.itemAnimator = RecyclerCollectionComponentSpec.itemAnimator;
      this.nestedScrollingEnabled = true;
      this.overScrollMode = 0;
      this.recyclerConfiguration = RecyclerCollectionComponentSpec.recyclerConfiguration;
      this.recyclerViewId = -1;
      this.refreshProgressBarColor = -12425294;
      this.scrollBarStyle = 0;
      this.setRootAsync = RecyclerCollectionComponentSpec.setRootAsync;
      this.mStateContainer = new RecyclerCollectionComponent.RecyclerCollectionComponentStateContainer();
   }

   public static RecyclerCollectionComponent.Builder create(ComponentContext var0) {
      return create(var0, 0, 0);
   }

   public static RecyclerCollectionComponent.Builder create(ComponentContext var0, int var1, int var2) {
      RecyclerCollectionComponent.Builder var3 = new RecyclerCollectionComponent.Builder();
      var3.init(var0, var1, var2, new RecyclerCollectionComponent());
      return var3;
   }

   private RecyclerCollectionComponent.UpdateLoadingStateStateUpdate createUpdateLoadingStateStateUpdate(RecyclerCollectionComponentSpec.LoadingState var1) {
      return new RecyclerCollectionComponent.UpdateLoadingStateStateUpdate(var1);
   }

   static Boolean dispatchPTRRefreshEvent(EventHandler var0) {
      PTRRefreshEvent var2 = (PTRRefreshEvent)sPTRRefreshEventPool.acquire();
      PTRRefreshEvent var1 = var2;
      if(var2 == null) {
         var1 = new PTRRefreshEvent();
      }

      Boolean var3 = (Boolean)var0.mHasEventDispatcher.getEventDispatcher().dispatchOnEvent(var0, var1);
      sPTRRefreshEventPool.release(var1);
      return var3;
   }

   public static EventHandler getPTRRefreshEventHandler(ComponentContext var0) {
      return var0.getComponentScope() == null?null:((RecyclerCollectionComponent)var0.getComponentScope()).pTRRefreshEventHandler;
   }

   protected static void lazyUpdateHasSetSectionTreeRoot(ComponentContext var0, final boolean var1) {
      if(var0.getComponentScope() != null) {
         var0.updateStateLazy(new ComponentLifecycle.StateUpdate() {
            public void updateState(StateContainer var1x, Component var2) {
               RecyclerCollectionComponent var3 = (RecyclerCollectionComponent)var2;
               StateValue var4 = new StateValue();
               var4.set(Boolean.valueOf(var1));
               var3.mStateContainer.hasSetSectionTreeRoot = ((Boolean)var4.get()).booleanValue();
            }
         });
      }
   }

   public static EventHandler<PTRRefreshEvent> onRefresh(ComponentContext var0, SectionTree var1) {
      return newEventHandler(var0, -1873243140, new Object[]{var0, var1});
   }

   private boolean onRefresh(HasEventDispatcher var1, ComponentContext var2, SectionTree var3) {
      return RecyclerCollectionComponentSpec.onRefresh(var2, var3, ((RecyclerCollectionComponent)var1).ignoreLoadingUpdates);
   }

   static void onScroll(ComponentContext var0, int var1, boolean var2) {
      RecyclerCollectionComponent var3 = (RecyclerCollectionComponent)var0.getComponentScope();
      var3.onScroll((EventTriggerTarget)var3, var1, var2);
   }

   public static void onScroll(ComponentContext var0, String var1, int var2, boolean var3) {
      EventTrigger var4 = getEventTrigger(var0, -1505688212, var1);
      if(var4 != null) {
         ScrollEvent var5 = new ScrollEvent();
         var5.position = var2;
         var5.animate = var3;
         var4.dispatchOnTrigger(var5, new Object[0]);
      }
   }

   public static void onScroll(EventTrigger var0, int var1, boolean var2) {
      ScrollEvent var3 = new ScrollEvent();
      var3.position = var1;
      var3.animate = var2;
      var0.dispatchOnTrigger(var3, new Object[0]);
   }

   private void onScroll(EventTriggerTarget var1, int var2, boolean var3) {
      RecyclerCollectionComponent var4 = (RecyclerCollectionComponent)var1;
      RecyclerCollectionComponentSpec.onScroll(var4.getScopedContext(), var2, var3, var4.mStateContainer.sectionTree);
   }

   public static EventTrigger onScrollTrigger(ComponentContext var0, String var1) {
      return newEventTrigger(var0, var1, -1505688212);
   }

   protected static void updateLoadingState(ComponentContext var0, RecyclerCollectionComponentSpec.LoadingState var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateSync(((RecyclerCollectionComponent)var2).createUpdateLoadingStateStateUpdate(var1), "RecyclerCollectionComponent.updateLoadingState");
      }
   }

   protected static void updateLoadingStateAsync(ComponentContext var0, RecyclerCollectionComponentSpec.LoadingState var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateAsync(((RecyclerCollectionComponent)var2).createUpdateLoadingStateStateUpdate(var1), "RecyclerCollectionComponent.updateLoadingState");
      }
   }

   protected static void updateLoadingStateSync(ComponentContext var0, RecyclerCollectionComponentSpec.LoadingState var1) {
      Component var2 = var0.getComponentScope();
      if(var2 != null) {
         var0.updateStateSync(((RecyclerCollectionComponent)var2).createUpdateLoadingStateStateUpdate(var1), "RecyclerCollectionComponent.updateLoadingState");
      }
   }

   public Object acceptTriggerEvent(EventTrigger var1, Object var2, Object[] var3) {
      if(var1.mId != -1505688212) {
         return null;
      } else {
         ScrollEvent var4 = (ScrollEvent)var2;
         this.onScroll(var1.mTriggerTarget, var4.position, var4.animate);
         return null;
      }
   }

   protected void createInitialState(ComponentContext var1) {
      StateValue var7 = new StateValue();
      StateValue var2 = new StateValue();
      StateValue var3 = new StateValue();
      StateValue var4 = new StateValue();
      StateValue var5 = new StateValue();
      StateValue var6 = new StateValue();
      RecyclerCollectionComponentSpec.createInitialState(var1, this.section, this.recyclerConfiguration, this.eventsController, this.asyncPropUpdates, this.asyncStateUpdates, this.forceSyncStateUpdates, this.ignoreLoadingUpdates, this.sectionTreeTag, this.canMeasureRecycler, var7, var2, var3, var4, var5, var6);
      if(var7.get() != null) {
         this.mStateContainer.snapHelper = (SnapHelper)var7.get();
      }

      if(var2.get() != null) {
         this.mStateContainer.sectionTree = (SectionTree)var2.get();
      }

      if(var3.get() != null) {
         this.mStateContainer.recyclerCollectionLoadEventsHandler = (RecyclerCollectionComponentSpec.RecyclerCollectionLoadEventsHandler)var3.get();
      }

      if(var4.get() != null) {
         this.mStateContainer.binder = (Binder)var4.get();
      }

      if(var5.get() != null) {
         this.mStateContainer.loadingState = (RecyclerCollectionComponentSpec.LoadingState)var5.get();
      }

      if(var6.get() != null) {
         this.mStateContainer.internalEventsController = (RecyclerCollectionEventsController)var6.get();
      }

   }

   public Object dispatchOnEvent(EventHandler var1, Object var2) {
      int var3 = var1.id;
      if(var3 != -1873243140) {
         if(var3 != -1048037474) {
            return null;
         } else {
            dispatchErrorEvent((ComponentContext)var1.params[0], (ErrorEvent)var2);
            return null;
         }
      } else {
         PTRRefreshEvent var4 = (PTRRefreshEvent)var2;
         return Boolean.valueOf(this.onRefresh(var1.mHasEventDispatcher, (ComponentContext)var1.params[0], (SectionTree)var1.params[1]));
      }
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
            RecyclerCollectionComponent var2 = (RecyclerCollectionComponent)var1;
            if(this.getId() == var2.getId()) {
               return true;
            } else if(this.asyncPropUpdates != var2.asyncPropUpdates) {
               return false;
            } else if(this.asyncStateUpdates != var2.asyncStateUpdates) {
               return false;
            } else if(this.bottomPadding != var2.bottomPadding) {
               return false;
            } else if(this.canMeasureRecycler != var2.canMeasureRecycler) {
               return false;
            } else if(this.clipChildren != var2.clipChildren) {
               return false;
            } else if(this.clipToPadding != var2.clipToPadding) {
               return false;
            } else if(this.disablePTR != var2.disablePTR) {
               return false;
            } else {
               if(this.emptyComponent != null) {
                  if(!this.emptyComponent.isEquivalentTo(var2.emptyComponent)) {
                     return false;
                  }
               } else if(var2.emptyComponent != null) {
                  return false;
               }

               if(this.errorComponent != null) {
                  if(!this.errorComponent.isEquivalentTo(var2.errorComponent)) {
                     return false;
                  }
               } else if(var2.errorComponent != null) {
                  return false;
               }

               if(this.eventsController != null) {
                  if(!this.eventsController.equals(var2.eventsController)) {
                     return false;
                  }
               } else if(var2.eventsController != null) {
                  return false;
               }

               if(this.fadingEdgeLength != var2.fadingEdgeLength) {
                  return false;
               } else if(this.forceSyncStateUpdates != var2.forceSyncStateUpdates) {
                  return false;
               } else if(this.horizontalFadingEdgeEnabled != var2.horizontalFadingEdgeEnabled) {
                  return false;
               } else if(this.ignoreLoadingUpdates != var2.ignoreLoadingUpdates) {
                  return false;
               } else {
                  if(this.itemAnimator != null) {
                     if(!this.itemAnimator.equals(var2.itemAnimator)) {
                        return false;
                     }
                  } else if(var2.itemAnimator != null) {
                     return false;
                  }

                  if(this.itemDecoration != null) {
                     if(!this.itemDecoration.equals(var2.itemDecoration)) {
                        return false;
                     }
                  } else if(var2.itemDecoration != null) {
                     return false;
                  }

                  if(this.leftPadding != var2.leftPadding) {
                     return false;
                  } else {
                     if(this.loadEventsHandler != null) {
                        if(!this.loadEventsHandler.equals(var2.loadEventsHandler)) {
                           return false;
                        }
                     } else if(var2.loadEventsHandler != null) {
                        return false;
                     }

                     if(this.loadingComponent != null) {
                        if(!this.loadingComponent.isEquivalentTo(var2.loadingComponent)) {
                           return false;
                        }
                     } else if(var2.loadingComponent != null) {
                        return false;
                     }

                     if(this.nestedScrollingEnabled != var2.nestedScrollingEnabled) {
                        return false;
                     } else {
                        if(this.onScrollListeners != null) {
                           if(!this.onScrollListeners.equals(var2.onScrollListeners)) {
                              return false;
                           }
                        } else if(var2.onScrollListeners != null) {
                           return false;
                        }

                        if(this.overScrollMode != var2.overScrollMode) {
                           return false;
                        } else {
                           if(this.recyclerConfiguration != null) {
                              if(!this.recyclerConfiguration.equals(var2.recyclerConfiguration)) {
                                 return false;
                              }
                           } else if(var2.recyclerConfiguration != null) {
                              return false;
                           }

                           if(this.recyclerTouchEventHandler != null) {
                              if(!this.recyclerTouchEventHandler.isEquivalentTo(var2.recyclerTouchEventHandler)) {
                                 return false;
                              }
                           } else if(var2.recyclerTouchEventHandler != null) {
                              return false;
                           }

                           if(this.recyclerViewId != var2.recyclerViewId) {
                              return false;
                           } else if(this.refreshProgressBarColor != var2.refreshProgressBarColor) {
                              return false;
                           } else if(this.rightPadding != var2.rightPadding) {
                              return false;
                           } else if(this.scrollBarStyle != var2.scrollBarStyle) {
                              return false;
                           } else {
                              if(this.section != null) {
                                 if(!this.section.isEquivalentTo(var2.section)) {
                                    return false;
                                 }
                              } else if(var2.section != null) {
                                 return false;
                              }

                              if(this.sectionTreeTag != null) {
                                 if(!this.sectionTreeTag.equals(var2.sectionTreeTag)) {
                                    return false;
                                 }
                              } else if(var2.sectionTreeTag != null) {
                                 return false;
                              }

                              if(this.setRootAsync != var2.setRootAsync) {
                                 return false;
                              } else if(this.topPadding != var2.topPadding) {
                                 return false;
                              } else {
                                 if(this.touchInterceptor != null) {
                                    if(!this.touchInterceptor.equals(var2.touchInterceptor)) {
                                       return false;
                                    }
                                 } else if(var2.touchInterceptor != null) {
                                    return false;
                                 }

                                 if(this.verticalFadingEdgeEnabled != var2.verticalFadingEdgeEnabled) {
                                    return false;
                                 } else {
                                    if(this.mStateContainer.binder != null) {
                                       if(!this.mStateContainer.binder.equals(var2.mStateContainer.binder)) {
                                          return false;
                                       }
                                    } else if(var2.mStateContainer.binder != null) {
                                       return false;
                                    }

                                    if(this.mStateContainer.hasSetSectionTreeRoot != var2.mStateContainer.hasSetSectionTreeRoot) {
                                       return false;
                                    } else {
                                       if(this.mStateContainer.internalEventsController != null) {
                                          if(!this.mStateContainer.internalEventsController.equals(var2.mStateContainer.internalEventsController)) {
                                             return false;
                                          }
                                       } else if(var2.mStateContainer.internalEventsController != null) {
                                          return false;
                                       }

                                       if(this.mStateContainer.loadingState != null) {
                                          if(!this.mStateContainer.loadingState.equals(var2.mStateContainer.loadingState)) {
                                             return false;
                                          }
                                       } else if(var2.mStateContainer.loadingState != null) {
                                          return false;
                                       }

                                       if(this.mStateContainer.recyclerCollectionLoadEventsHandler != null) {
                                          if(!this.mStateContainer.recyclerCollectionLoadEventsHandler.equals(var2.mStateContainer.recyclerCollectionLoadEventsHandler)) {
                                             return false;
                                          }
                                       } else if(var2.mStateContainer.recyclerCollectionLoadEventsHandler != null) {
                                          return false;
                                       }

                                       if(this.mStateContainer.sectionTree != null) {
                                          if(!this.mStateContainer.sectionTree.equals(var2.mStateContainer.sectionTree)) {
                                             return false;
                                          }
                                       } else if(var2.mStateContainer.sectionTree != null) {
                                          return false;
                                       }

                                       if(this.mStateContainer.snapHelper != null) {
                                          if(!this.mStateContainer.snapHelper.equals(var2.mStateContainer.snapHelper)) {
                                             return false;
                                          }
                                       } else if(var2.mStateContainer.snapHelper != null) {
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
            }
         }
      } else {
         return false;
      }
   }

   public RecyclerCollectionComponent makeShallowCopy() {
      RecyclerCollectionComponent var3 = (RecyclerCollectionComponent)super.makeShallowCopy();
      Component var1 = var3.emptyComponent;
      Object var2 = null;
      if(var1 != null) {
         var1 = var3.emptyComponent.makeShallowCopy();
      } else {
         var1 = null;
      }

      var3.emptyComponent = var1;
      if(var3.errorComponent != null) {
         var1 = var3.errorComponent.makeShallowCopy();
      } else {
         var1 = null;
      }

      var3.errorComponent = var1;
      if(var3.loadingComponent != null) {
         var1 = var3.loadingComponent.makeShallowCopy();
      } else {
         var1 = null;
      }

      var3.loadingComponent = var1;
      Section var4 = (Section)var2;
      if(var3.section != null) {
         var4 = var3.section.makeShallowCopy();
      }

      var3.section = var4;
      var3.mStateContainer = new RecyclerCollectionComponent.RecyclerCollectionComponentStateContainer();
      return var3;
   }

   protected Component onCreateLayout(ComponentContext var1) {
      return RecyclerCollectionComponentSpec.onCreateLayout(var1, this.section, this.loadingComponent, this.emptyComponent, this.errorComponent, this.onScrollListeners, this.loadEventsHandler, this.clipToPadding, this.clipChildren, this.nestedScrollingEnabled, this.scrollBarStyle, this.itemDecoration, this.itemAnimator, this.recyclerViewId, this.overScrollMode, this.leftPadding, this.rightPadding, this.topPadding, this.bottomPadding, this.recyclerTouchEventHandler, this.horizontalFadingEdgeEnabled, this.verticalFadingEdgeEnabled, this.fadingEdgeLength, this.refreshProgressBarColor, this.touchInterceptor, this.setRootAsync, this.disablePTR, this.recyclerConfiguration, this.mStateContainer.hasSetSectionTreeRoot, this.mStateContainer.internalEventsController, this.mStateContainer.loadingState, this.mStateContainer.binder, this.mStateContainer.sectionTree, this.mStateContainer.recyclerCollectionLoadEventsHandler, this.mStateContainer.snapHelper);
   }

   public void recordEventTrigger(EventTriggersContainer var1) {
      if(this.onScrollTrigger != null) {
         this.onScrollTrigger.mTriggerTarget = this;
         var1.recordEventTrigger(this.onScrollTrigger);
      }

   }

   protected void transferState(ComponentContext var1, StateContainer var2) {
      RecyclerCollectionComponent.RecyclerCollectionComponentStateContainer var3 = (RecyclerCollectionComponent.RecyclerCollectionComponentStateContainer)var2;
      this.mStateContainer.binder = var3.binder;
      this.mStateContainer.hasSetSectionTreeRoot = var3.hasSetSectionTreeRoot;
      this.mStateContainer.internalEventsController = var3.internalEventsController;
      this.mStateContainer.loadingState = var3.loadingState;
      this.mStateContainer.recyclerCollectionLoadEventsHandler = var3.recyclerCollectionLoadEventsHandler;
      this.mStateContainer.sectionTree = var3.sectionTree;
      this.mStateContainer.snapHelper = var3.snapHelper;
   }

   public static class Builder extends Component.Builder<RecyclerCollectionComponent.Builder> {

      private final int REQUIRED_PROPS_COUNT = 1;
      private final String[] REQUIRED_PROPS_NAMES = new String[]{"section"};
      ComponentContext mContext;
      RecyclerCollectionComponent mRecyclerCollectionComponent;
      private final BitSet mRequired = new BitSet(1);


      private void init(ComponentContext var1, int var2, int var3, RecyclerCollectionComponent var4) {
         super.init(var1, var2, var3, var4);
         this.mRecyclerCollectionComponent = var4;
         this.mContext = var1;
         this.mRequired.clear();
      }

      private void onScrollTrigger(String var1) {
         EventTrigger var3 = this.mRecyclerCollectionComponent.onScrollTrigger;
         EventTrigger var2 = var3;
         if(var3 == null) {
            var2 = RecyclerCollectionComponent.onScrollTrigger(this.mContext, var1);
         }

         this.onScrollTrigger(var2);
      }

      public RecyclerCollectionComponent.Builder asyncPropUpdates(boolean var1) {
         this.mRecyclerCollectionComponent.asyncPropUpdates = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder asyncStateUpdates(boolean var1) {
         this.mRecyclerCollectionComponent.asyncStateUpdates = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder bottomPaddingAttr(@AttrRes int var1) {
         this.mRecyclerCollectionComponent.bottomPadding = this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public RecyclerCollectionComponent.Builder bottomPaddingAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mRecyclerCollectionComponent.bottomPadding = this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public RecyclerCollectionComponent.Builder bottomPaddingDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mRecyclerCollectionComponent.bottomPadding = this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public RecyclerCollectionComponent.Builder bottomPaddingPx(@Px int var1) {
         this.mRecyclerCollectionComponent.bottomPadding = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder bottomPaddingRes(@DimenRes int var1) {
         this.mRecyclerCollectionComponent.bottomPadding = this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public RecyclerCollectionComponent build() {
         checkArgs(1, this.mRequired, this.REQUIRED_PROPS_NAMES);
         RecyclerCollectionComponent var1 = this.mRecyclerCollectionComponent;
         this.release();
         return var1;
      }

      public RecyclerCollectionComponent.Builder canMeasureRecycler(boolean var1) {
         this.mRecyclerCollectionComponent.canMeasureRecycler = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder clipChildren(boolean var1) {
         this.mRecyclerCollectionComponent.clipChildren = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder clipToPadding(boolean var1) {
         this.mRecyclerCollectionComponent.clipToPadding = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder disablePTR(boolean var1) {
         this.mRecyclerCollectionComponent.disablePTR = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder emptyComponent(Component.Builder<?> var1) {
         RecyclerCollectionComponent var2 = this.mRecyclerCollectionComponent;
         Component var3;
         if(var1 == null) {
            var3 = null;
         } else {
            var3 = var1.build();
         }

         var2.emptyComponent = var3;
         return this;
      }

      public RecyclerCollectionComponent.Builder emptyComponent(Component var1) {
         RecyclerCollectionComponent var2 = this.mRecyclerCollectionComponent;
         if(var1 == null) {
            var1 = null;
         } else {
            var1 = var1.makeShallowCopy();
         }

         var2.emptyComponent = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder errorComponent(Component.Builder<?> var1) {
         RecyclerCollectionComponent var2 = this.mRecyclerCollectionComponent;
         Component var3;
         if(var1 == null) {
            var3 = null;
         } else {
            var3 = var1.build();
         }

         var2.errorComponent = var3;
         return this;
      }

      public RecyclerCollectionComponent.Builder errorComponent(Component var1) {
         RecyclerCollectionComponent var2 = this.mRecyclerCollectionComponent;
         if(var1 == null) {
            var1 = null;
         } else {
            var1 = var1.makeShallowCopy();
         }

         var2.errorComponent = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder eventsController(RecyclerCollectionEventsController var1) {
         this.mRecyclerCollectionComponent.eventsController = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder fadingEdgeLengthAttr(@AttrRes int var1) {
         this.mRecyclerCollectionComponent.fadingEdgeLength = this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public RecyclerCollectionComponent.Builder fadingEdgeLengthAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mRecyclerCollectionComponent.fadingEdgeLength = this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public RecyclerCollectionComponent.Builder fadingEdgeLengthDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mRecyclerCollectionComponent.fadingEdgeLength = this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public RecyclerCollectionComponent.Builder fadingEdgeLengthPx(@Px int var1) {
         this.mRecyclerCollectionComponent.fadingEdgeLength = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder fadingEdgeLengthRes(@DimenRes int var1) {
         this.mRecyclerCollectionComponent.fadingEdgeLength = this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public RecyclerCollectionComponent.Builder forceSyncStateUpdates(boolean var1) {
         this.mRecyclerCollectionComponent.forceSyncStateUpdates = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder getThis() {
         return this;
      }

      public RecyclerCollectionComponent.Builder horizontalFadingEdgeEnabled(boolean var1) {
         this.mRecyclerCollectionComponent.horizontalFadingEdgeEnabled = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder ignoreLoadingUpdates(boolean var1) {
         this.mRecyclerCollectionComponent.ignoreLoadingUpdates = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder itemAnimator(RecyclerView.ItemAnimator var1) {
         this.mRecyclerCollectionComponent.itemAnimator = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder itemDecoration(RecyclerView.ItemDecoration var1) {
         this.mRecyclerCollectionComponent.itemDecoration = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder key(String var1) {
         super.key(var1);
         this.onScrollTrigger(var1);
         return this;
      }

      public RecyclerCollectionComponent.Builder leftPaddingAttr(@AttrRes int var1) {
         this.mRecyclerCollectionComponent.leftPadding = this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public RecyclerCollectionComponent.Builder leftPaddingAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mRecyclerCollectionComponent.leftPadding = this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public RecyclerCollectionComponent.Builder leftPaddingDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mRecyclerCollectionComponent.leftPadding = this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public RecyclerCollectionComponent.Builder leftPaddingPx(@Px int var1) {
         this.mRecyclerCollectionComponent.leftPadding = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder leftPaddingRes(@DimenRes int var1) {
         this.mRecyclerCollectionComponent.leftPadding = this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public RecyclerCollectionComponent.Builder loadEventsHandler(LoadEventsHandler var1) {
         this.mRecyclerCollectionComponent.loadEventsHandler = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder loadingComponent(Component.Builder<?> var1) {
         RecyclerCollectionComponent var2 = this.mRecyclerCollectionComponent;
         Component var3;
         if(var1 == null) {
            var3 = null;
         } else {
            var3 = var1.build();
         }

         var2.loadingComponent = var3;
         return this;
      }

      public RecyclerCollectionComponent.Builder loadingComponent(Component var1) {
         RecyclerCollectionComponent var2 = this.mRecyclerCollectionComponent;
         if(var1 == null) {
            var1 = null;
         } else {
            var1 = var1.makeShallowCopy();
         }

         var2.loadingComponent = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder nestedScrollingEnabled(boolean var1) {
         this.mRecyclerCollectionComponent.nestedScrollingEnabled = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder onScrollListener(RecyclerView.OnScrollListener var1) {
         if(var1 == null) {
            return this;
         } else {
            if(this.mRecyclerCollectionComponent.onScrollListeners == null) {
               this.mRecyclerCollectionComponent.onScrollListeners = new ArrayList();
            }

            this.mRecyclerCollectionComponent.onScrollListeners.add(var1);
            return this;
         }
      }

      public RecyclerCollectionComponent.Builder onScrollListeners(List<RecyclerView.OnScrollListener> var1) {
         if(var1 == null) {
            return this;
         } else if(this.mRecyclerCollectionComponent.onScrollListeners != null && !this.mRecyclerCollectionComponent.onScrollListeners.isEmpty()) {
            this.mRecyclerCollectionComponent.onScrollListeners.addAll(var1);
            return this;
         } else {
            this.mRecyclerCollectionComponent.onScrollListeners = var1;
            return this;
         }
      }

      public RecyclerCollectionComponent.Builder onScrollTrigger(EventTrigger var1) {
         this.mRecyclerCollectionComponent.onScrollTrigger = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder overScrollMode(int var1) {
         this.mRecyclerCollectionComponent.overScrollMode = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder pTRRefreshEventHandler(EventHandler var1) {
         this.mRecyclerCollectionComponent.pTRRefreshEventHandler = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder recyclerConfiguration(RecyclerConfiguration var1) {
         this.mRecyclerCollectionComponent.recyclerConfiguration = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder recyclerTouchEventHandler(EventHandler<TouchEvent> var1) {
         this.mRecyclerCollectionComponent.recyclerTouchEventHandler = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder recyclerViewId(@IdRes int var1) {
         this.mRecyclerCollectionComponent.recyclerViewId = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder refreshProgressBarColor(@ColorInt int var1) {
         this.mRecyclerCollectionComponent.refreshProgressBarColor = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder refreshProgressBarColorAttr(@AttrRes int var1) {
         this.mRecyclerCollectionComponent.refreshProgressBarColor = this.mResourceResolver.resolveColorAttr(var1, 0);
         return this;
      }

      public RecyclerCollectionComponent.Builder refreshProgressBarColorAttr(@AttrRes int var1, @ColorRes int var2) {
         this.mRecyclerCollectionComponent.refreshProgressBarColor = this.mResourceResolver.resolveColorAttr(var1, var2);
         return this;
      }

      public RecyclerCollectionComponent.Builder refreshProgressBarColorRes(@ColorRes int var1) {
         this.mRecyclerCollectionComponent.refreshProgressBarColor = this.mResourceResolver.resolveColorRes(var1);
         return this;
      }

      protected void release() {
         super.release();
         this.mRecyclerCollectionComponent = null;
         this.mContext = null;
      }

      public RecyclerCollectionComponent.Builder rightPaddingAttr(@AttrRes int var1) {
         this.mRecyclerCollectionComponent.rightPadding = this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public RecyclerCollectionComponent.Builder rightPaddingAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mRecyclerCollectionComponent.rightPadding = this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public RecyclerCollectionComponent.Builder rightPaddingDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mRecyclerCollectionComponent.rightPadding = this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public RecyclerCollectionComponent.Builder rightPaddingPx(@Px int var1) {
         this.mRecyclerCollectionComponent.rightPadding = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder rightPaddingRes(@DimenRes int var1) {
         this.mRecyclerCollectionComponent.rightPadding = this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public RecyclerCollectionComponent.Builder scrollBarStyle(int var1) {
         this.mRecyclerCollectionComponent.scrollBarStyle = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder section(Section.Builder<?> var1) {
         RecyclerCollectionComponent var2 = this.mRecyclerCollectionComponent;
         Section var3;
         if(var1 == null) {
            var3 = null;
         } else {
            var3 = var1.build();
         }

         var2.section = var3;
         this.mRequired.set(0);
         return this;
      }

      public RecyclerCollectionComponent.Builder section(Section var1) {
         this.mRecyclerCollectionComponent.section = var1;
         this.mRequired.set(0);
         return this;
      }

      public RecyclerCollectionComponent.Builder sectionTreeTag(String var1) {
         this.mRecyclerCollectionComponent.sectionTreeTag = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder setRootAsync(boolean var1) {
         this.mRecyclerCollectionComponent.setRootAsync = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder topPaddingAttr(@AttrRes int var1) {
         this.mRecyclerCollectionComponent.topPadding = this.mResourceResolver.resolveDimenSizeAttr(var1, 0);
         return this;
      }

      public RecyclerCollectionComponent.Builder topPaddingAttr(@AttrRes int var1, @DimenRes int var2) {
         this.mRecyclerCollectionComponent.topPadding = this.mResourceResolver.resolveDimenSizeAttr(var1, var2);
         return this;
      }

      public RecyclerCollectionComponent.Builder topPaddingDip(
         @Dimension(
            unit = 0
         ) float var1) {
         this.mRecyclerCollectionComponent.topPadding = this.mResourceResolver.dipsToPixels(var1);
         return this;
      }

      public RecyclerCollectionComponent.Builder topPaddingPx(@Px int var1) {
         this.mRecyclerCollectionComponent.topPadding = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder topPaddingRes(@DimenRes int var1) {
         this.mRecyclerCollectionComponent.topPadding = this.mResourceResolver.resolveDimenSizeRes(var1);
         return this;
      }

      public RecyclerCollectionComponent.Builder touchInterceptor(LithoRecylerView.TouchInterceptor var1) {
         this.mRecyclerCollectionComponent.touchInterceptor = var1;
         return this;
      }

      public RecyclerCollectionComponent.Builder verticalFadingEdgeEnabled(boolean var1) {
         this.mRecyclerCollectionComponent.verticalFadingEdgeEnabled = var1;
         return this;
      }
   }

   static class UpdateLoadingStateStateUpdate implements ComponentLifecycle.StateUpdate {

      private RecyclerCollectionComponentSpec.LoadingState mCurrentLoadingState;


      UpdateLoadingStateStateUpdate(RecyclerCollectionComponentSpec.LoadingState var1) {
         this.mCurrentLoadingState = var1;
      }

      public void updateState(StateContainer var1, Component var2) {
         RecyclerCollectionComponent.RecyclerCollectionComponentStateContainer var4 = (RecyclerCollectionComponent.RecyclerCollectionComponentStateContainer)var1;
         RecyclerCollectionComponent var5 = (RecyclerCollectionComponent)var2;
         StateValue var3 = new StateValue();
         var3.set(var4.loadingState);
         RecyclerCollectionComponentSpec.updateLoadingState(var3, this.mCurrentLoadingState);
         var5.mStateContainer.loadingState = (RecyclerCollectionComponentSpec.LoadingState)var3.get();
      }
   }

   @VisibleForTesting(
      otherwise = 2
   )
   static class RecyclerCollectionComponentStateContainer implements StateContainer {

      @Comparable(
         type = 13
      )
      @State
      Binder<RecyclerView> binder;
      @Comparable(
         type = 3
      )
      @State
      boolean hasSetSectionTreeRoot;
      @Comparable(
         type = 13
      )
      @State
      RecyclerCollectionEventsController internalEventsController;
      @Comparable(
         type = 13
      )
      @State
      RecyclerCollectionComponentSpec.LoadingState loadingState;
      @Comparable(
         type = 13
      )
      @State
      RecyclerCollectionComponentSpec.RecyclerCollectionLoadEventsHandler recyclerCollectionLoadEventsHandler;
      @Comparable(
         type = 13
      )
      @State
      SectionTree sectionTree;
      @Comparable(
         type = 13
      )
      @State
      SnapHelper snapHelper;


   }
}
