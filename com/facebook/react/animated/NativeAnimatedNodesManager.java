package com.facebook.react.animated;

import android.util.SparseArray;
import com.facebook.common.logging.FLog;
import com.facebook.react.animated.AdditionAnimatedNode;
import com.facebook.react.animated.AnimatedNode;
import com.facebook.react.animated.AnimatedNodeValueListener;
import com.facebook.react.animated.AnimationDriver;
import com.facebook.react.animated.DecayAnimation;
import com.facebook.react.animated.DiffClampAnimatedNode;
import com.facebook.react.animated.DivisionAnimatedNode;
import com.facebook.react.animated.EventAnimationDriver;
import com.facebook.react.animated.FrameBasedAnimationDriver;
import com.facebook.react.animated.InterpolationAnimatedNode;
import com.facebook.react.animated.ModulusAnimatedNode;
import com.facebook.react.animated.MultiplicationAnimatedNode;
import com.facebook.react.animated.PropsAnimatedNode;
import com.facebook.react.animated.SpringAnimation;
import com.facebook.react.animated.StyleAnimatedNode;
import com.facebook.react.animated.TransformAnimatedNode;
import com.facebook.react.animated.ValueAnimatedNode;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.UIImplementation;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcherListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.annotation.Nullable;

class NativeAnimatedNodesManager implements EventDispatcherListener {

   private final SparseArray<AnimationDriver> mActiveAnimations = new SparseArray();
   private int mAnimatedGraphBFSColor = 0;
   private final SparseArray<AnimatedNode> mAnimatedNodes = new SparseArray();
   private final UIManagerModule.CustomEventNamesResolver mCustomEventNamesResolver;
   private final Map<String, List<EventAnimationDriver>> mEventDrivers = new HashMap();
   private final List<AnimatedNode> mRunUpdateNodeList = new LinkedList();
   private final UIImplementation mUIImplementation;
   private final SparseArray<AnimatedNode> mUpdatedNodes = new SparseArray();


   public NativeAnimatedNodesManager(UIManagerModule var1) {
      this.mUIImplementation = var1.getUIImplementation();
      var1.getEventDispatcher().addListener(this);
      this.mCustomEventNamesResolver = var1.getDirectEventNamesResolver();
   }

   private void handleEvent(Event var1) {
      if(!this.mEventDrivers.isEmpty()) {
         String var2 = this.mCustomEventNamesResolver.resolveCustomEventName(var1.getEventName());
         Map var3 = this.mEventDrivers;
         StringBuilder var4 = new StringBuilder();
         var4.append(var1.getViewTag());
         var4.append(var2);
         List var5 = (List)var3.get(var4.toString());
         if(var5 != null) {
            Iterator var6 = var5.iterator();

            while(var6.hasNext()) {
               EventAnimationDriver var7 = (EventAnimationDriver)var6.next();
               this.stopAnimationsForNode(var7.mValueNode);
               var1.dispatch(var7);
               this.mRunUpdateNodeList.add(var7.mValueNode);
            }

            this.updateNodes(this.mRunUpdateNodeList);
            this.mRunUpdateNodeList.clear();
         }
      }

   }

   private void stopAnimationsForNode(AnimatedNode var1) {
      int var3;
      for(int var2 = 0; var2 < this.mActiveAnimations.size(); var2 = var3 + 1) {
         AnimationDriver var4 = (AnimationDriver)this.mActiveAnimations.valueAt(var2);
         var3 = var2;
         if(var1.equals(var4.mAnimatedValue)) {
            WritableMap var5 = Arguments.createMap();
            var5.putBoolean("finished", false);
            var4.mEndCallback.invoke(new Object[]{var5});
            this.mActiveAnimations.removeAt(var2);
            var3 = var2 - 1;
         }
      }

   }

   private void updateNodes(List<AnimatedNode> var1) {
      ++this.mAnimatedGraphBFSColor;
      if(this.mAnimatedGraphBFSColor == 0) {
         ++this.mAnimatedGraphBFSColor;
      }

      ArrayDeque var6 = new ArrayDeque();
      Iterator var7 = var1.iterator();
      int var3 = 0;

      while(true) {
         int var2 = var3;
         AnimatedNode var8;
         if(!var7.hasNext()) {
            int var4;
            AnimatedNode var13;
            while(!var6.isEmpty()) {
               var13 = (AnimatedNode)var6.poll();
               if(var13.mChildren != null) {
                  for(var3 = 0; var3 < var13.mChildren.size(); var2 = var4) {
                     var8 = (AnimatedNode)var13.mChildren.get(var3);
                     ++var8.mActiveIncomingNodes;
                     var4 = var2;
                     if(var8.mBFSColor != this.mAnimatedGraphBFSColor) {
                        var8.mBFSColor = this.mAnimatedGraphBFSColor;
                        var4 = var2 + 1;
                        var6.add(var8);
                     }

                     ++var3;
                  }
               }
            }

            ++this.mAnimatedGraphBFSColor;
            if(this.mAnimatedGraphBFSColor == 0) {
               ++this.mAnimatedGraphBFSColor;
            }

            Iterator var10 = var1.iterator();
            var3 = 0;

            while(true) {
               var4 = var3;
               if(!var10.hasNext()) {
                  while(!var6.isEmpty()) {
                     AnimatedNode var11 = (AnimatedNode)var6.poll();
                     var11.update();
                     if(var11 instanceof PropsAnimatedNode) {
                        try {
                           ((PropsAnimatedNode)var11).updateView();
                        } catch (IllegalViewOperationException var9) {
                           FLog.e("ReactNative", "Native animation workaround, frame lost as result of race condition", (Throwable)var9);
                        }
                     }

                     if(var11 instanceof ValueAnimatedNode) {
                        ((ValueAnimatedNode)var11).onValueUpdate();
                     }

                     if(var11.mChildren != null) {
                        var3 = var4;

                        int var5;
                        for(var4 = 0; var4 < var11.mChildren.size(); var3 = var5) {
                           var13 = (AnimatedNode)var11.mChildren.get(var4);
                           --var13.mActiveIncomingNodes;
                           var5 = var3;
                           if(var13.mBFSColor != this.mAnimatedGraphBFSColor) {
                              var5 = var3;
                              if(var13.mActiveIncomingNodes == 0) {
                                 var13.mBFSColor = this.mAnimatedGraphBFSColor;
                                 var5 = var3 + 1;
                                 var6.add(var13);
                              }
                           }

                           ++var4;
                        }

                        var4 = var3;
                     }
                  }

                  if(var2 != var4) {
                     StringBuilder var12 = new StringBuilder();
                     var12.append("Looks like animated nodes graph has cycles, there are ");
                     var12.append(var2);
                     var12.append(" but toposort visited only ");
                     var12.append(var4);
                     throw new IllegalStateException(var12.toString());
                  } else {
                     return;
                  }
               }

               var13 = (AnimatedNode)var10.next();
               if(var13.mActiveIncomingNodes == 0 && var13.mBFSColor != this.mAnimatedGraphBFSColor) {
                  var13.mBFSColor = this.mAnimatedGraphBFSColor;
                  ++var3;
                  var6.add(var13);
               }
            }
         }

         var8 = (AnimatedNode)var7.next();
         if(var8.mBFSColor != this.mAnimatedGraphBFSColor) {
            var8.mBFSColor = this.mAnimatedGraphBFSColor;
            ++var3;
            var6.add(var8);
         }
      }
   }

   public void addAnimatedEventToView(int var1, String var2, ReadableMap var3) {
      int var4 = var3.getInt("animatedValueTag");
      AnimatedNode var5 = (AnimatedNode)this.mAnimatedNodes.get(var4);
      StringBuilder var7;
      if(var5 == null) {
         var7 = new StringBuilder();
         var7.append("Animated node with tag ");
         var7.append(var4);
         var7.append(" does not exists");
         throw new JSApplicationIllegalArgumentException(var7.toString());
      } else if(!(var5 instanceof ValueAnimatedNode)) {
         var7 = new StringBuilder();
         var7.append("Animated node connected to event should beof type ");
         var7.append(ValueAnimatedNode.class.getName());
         throw new JSApplicationIllegalArgumentException(var7.toString());
      } else {
         ReadableArray var8 = var3.getArray("nativeEventPath");
         ArrayList var6 = new ArrayList(var8.size());

         for(var4 = 0; var4 < var8.size(); ++var4) {
            var6.add(var8.getString(var4));
         }

         EventAnimationDriver var9 = new EventAnimationDriver(var6, (ValueAnimatedNode)var5);
         StringBuilder var10 = new StringBuilder();
         var10.append(var1);
         var10.append(var2);
         var2 = var10.toString();
         if(this.mEventDrivers.containsKey(var2)) {
            ((List)this.mEventDrivers.get(var2)).add(var9);
         } else {
            ArrayList var11 = new ArrayList(1);
            var11.add(var9);
            this.mEventDrivers.put(var2, var11);
         }
      }
   }

   public void connectAnimatedNodeToView(int var1, int var2) {
      AnimatedNode var3 = (AnimatedNode)this.mAnimatedNodes.get(var1);
      StringBuilder var4;
      if(var3 == null) {
         var4 = new StringBuilder();
         var4.append("Animated node with tag ");
         var4.append(var1);
         var4.append(" does not exists");
         throw new JSApplicationIllegalArgumentException(var4.toString());
      } else if(!(var3 instanceof PropsAnimatedNode)) {
         var4 = new StringBuilder();
         var4.append("Animated node connected to view should beof type ");
         var4.append(PropsAnimatedNode.class.getName());
         throw new JSApplicationIllegalArgumentException(var4.toString());
      } else {
         ((PropsAnimatedNode)var3).connectToView(var2);
         this.mUpdatedNodes.put(var1, var3);
      }
   }

   public void connectAnimatedNodes(int var1, int var2) {
      AnimatedNode var3 = (AnimatedNode)this.mAnimatedNodes.get(var1);
      StringBuilder var5;
      if(var3 == null) {
         var5 = new StringBuilder();
         var5.append("Animated node with tag ");
         var5.append(var1);
         var5.append(" does not exists");
         throw new JSApplicationIllegalArgumentException(var5.toString());
      } else {
         AnimatedNode var4 = (AnimatedNode)this.mAnimatedNodes.get(var2);
         if(var4 == null) {
            var5 = new StringBuilder();
            var5.append("Animated node with tag ");
            var5.append(var2);
            var5.append(" does not exists");
            throw new JSApplicationIllegalArgumentException(var5.toString());
         } else {
            var3.addChild(var4);
            this.mUpdatedNodes.put(var2, var4);
         }
      }
   }

   public void createAnimatedNode(int var1, ReadableMap var2) {
      StringBuilder var5;
      if(this.mAnimatedNodes.get(var1) != null) {
         var5 = new StringBuilder();
         var5.append("Animated node with tag ");
         var5.append(var1);
         var5.append(" already exists");
         throw new JSApplicationIllegalArgumentException(var5.toString());
      } else {
         String var3 = var2.getString("type");
         Object var4;
         if("style".equals(var3)) {
            var4 = new StyleAnimatedNode(var2, this);
         } else if("value".equals(var3)) {
            var4 = new ValueAnimatedNode(var2);
         } else if("props".equals(var3)) {
            var4 = new PropsAnimatedNode(var2, this, this.mUIImplementation);
         } else if("interpolation".equals(var3)) {
            var4 = new InterpolationAnimatedNode(var2);
         } else if("addition".equals(var3)) {
            var4 = new AdditionAnimatedNode(var2, this);
         } else if("division".equals(var3)) {
            var4 = new DivisionAnimatedNode(var2, this);
         } else if("multiplication".equals(var3)) {
            var4 = new MultiplicationAnimatedNode(var2, this);
         } else if("modulus".equals(var3)) {
            var4 = new ModulusAnimatedNode(var2, this);
         } else if("diffclamp".equals(var3)) {
            var4 = new DiffClampAnimatedNode(var2, this);
         } else {
            if(!"transform".equals(var3)) {
               var5 = new StringBuilder();
               var5.append("Unsupported node type: ");
               var5.append(var3);
               throw new JSApplicationIllegalArgumentException(var5.toString());
            }

            var4 = new TransformAnimatedNode(var2, this);
         }

         ((AnimatedNode)var4).mTag = var1;
         this.mAnimatedNodes.put(var1, var4);
         this.mUpdatedNodes.put(var1, var4);
      }
   }

   public void disconnectAnimatedNodeFromView(int var1, int var2) {
      AnimatedNode var3 = (AnimatedNode)this.mAnimatedNodes.get(var1);
      StringBuilder var4;
      if(var3 == null) {
         var4 = new StringBuilder();
         var4.append("Animated node with tag ");
         var4.append(var1);
         var4.append(" does not exists");
         throw new JSApplicationIllegalArgumentException(var4.toString());
      } else if(!(var3 instanceof PropsAnimatedNode)) {
         var4 = new StringBuilder();
         var4.append("Animated node connected to view should beof type ");
         var4.append(PropsAnimatedNode.class.getName());
         throw new JSApplicationIllegalArgumentException(var4.toString());
      } else {
         ((PropsAnimatedNode)var3).disconnectFromView(var2);
      }
   }

   public void disconnectAnimatedNodes(int var1, int var2) {
      AnimatedNode var3 = (AnimatedNode)this.mAnimatedNodes.get(var1);
      StringBuilder var5;
      if(var3 == null) {
         var5 = new StringBuilder();
         var5.append("Animated node with tag ");
         var5.append(var1);
         var5.append(" does not exists");
         throw new JSApplicationIllegalArgumentException(var5.toString());
      } else {
         AnimatedNode var4 = (AnimatedNode)this.mAnimatedNodes.get(var2);
         if(var4 == null) {
            var5 = new StringBuilder();
            var5.append("Animated node with tag ");
            var5.append(var2);
            var5.append(" does not exists");
            throw new JSApplicationIllegalArgumentException(var5.toString());
         } else {
            var3.removeChild(var4);
            this.mUpdatedNodes.put(var2, var4);
         }
      }
   }

   public void dropAnimatedNode(int var1) {
      this.mAnimatedNodes.remove(var1);
      this.mUpdatedNodes.remove(var1);
   }

   public void extractAnimatedNodeOffset(int var1) {
      AnimatedNode var2 = (AnimatedNode)this.mAnimatedNodes.get(var1);
      if(var2 != null && var2 instanceof ValueAnimatedNode) {
         ((ValueAnimatedNode)var2).extractOffset();
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Animated node with tag ");
         var3.append(var1);
         var3.append(" does not exists or is not a \'value\' node");
         throw new JSApplicationIllegalArgumentException(var3.toString());
      }
   }

   public void flattenAnimatedNodeOffset(int var1) {
      AnimatedNode var2 = (AnimatedNode)this.mAnimatedNodes.get(var1);
      if(var2 != null && var2 instanceof ValueAnimatedNode) {
         ((ValueAnimatedNode)var2).flattenOffset();
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Animated node with tag ");
         var3.append(var1);
         var3.append(" does not exists or is not a \'value\' node");
         throw new JSApplicationIllegalArgumentException(var3.toString());
      }
   }

   @Nullable
   AnimatedNode getNodeById(int var1) {
      return (AnimatedNode)this.mAnimatedNodes.get(var1);
   }

   public boolean hasActiveAnimations() {
      return this.mActiveAnimations.size() > 0 || this.mUpdatedNodes.size() > 0;
   }

   public void onEventDispatch(final Event var1) {
      if(UiThreadUtil.isOnUiThread()) {
         this.handleEvent(var1);
      } else {
         UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
               NativeAnimatedNodesManager.this.handleEvent(var1);
            }
         });
      }
   }

   public void removeAnimatedEventFromView(int var1, String var2, int var3) {
      StringBuilder var4 = new StringBuilder();
      var4.append(var1);
      var4.append(var2);
      String var7 = var4.toString();
      if(this.mEventDrivers.containsKey(var7)) {
         List var8 = (List)this.mEventDrivers.get(var7);
         if(var8.size() == 1) {
            Map var9 = this.mEventDrivers;
            StringBuilder var5 = new StringBuilder();
            var5.append(var1);
            var5.append(var2);
            var9.remove(var5.toString());
            return;
         }

         ListIterator var6 = var8.listIterator();

         while(var6.hasNext()) {
            if(((EventAnimationDriver)var6.next()).mValueNode.mTag == var3) {
               var6.remove();
               break;
            }
         }
      }

   }

   public void restoreDefaultValues(int var1, int var2) {
      AnimatedNode var3 = (AnimatedNode)this.mAnimatedNodes.get(var1);
      if(var3 != null) {
         if(!(var3 instanceof PropsAnimatedNode)) {
            StringBuilder var4 = new StringBuilder();
            var4.append("Animated node connected to view should beof type ");
            var4.append(PropsAnimatedNode.class.getName());
            throw new JSApplicationIllegalArgumentException(var4.toString());
         } else {
            ((PropsAnimatedNode)var3).restoreDefaultValues();
         }
      }
   }

   public void runUpdates(long var1) {
      UiThreadUtil.assertOnUiThread();

      int var3;
      for(var3 = 0; var3 < this.mUpdatedNodes.size(); ++var3) {
         AnimatedNode var5 = (AnimatedNode)this.mUpdatedNodes.valueAt(var3);
         this.mRunUpdateNodeList.add(var5);
      }

      this.mUpdatedNodes.clear();
      var3 = 0;

      boolean var4;
      AnimationDriver var7;
      for(var4 = false; var3 < this.mActiveAnimations.size(); ++var3) {
         var7 = (AnimationDriver)this.mActiveAnimations.valueAt(var3);
         var7.runAnimationStep(var1);
         ValueAnimatedNode var6 = var7.mAnimatedValue;
         this.mRunUpdateNodeList.add(var6);
         if(var7.mHasFinished) {
            var4 = true;
         }
      }

      this.updateNodes(this.mRunUpdateNodeList);
      this.mRunUpdateNodeList.clear();
      if(var4) {
         for(var3 = this.mActiveAnimations.size() - 1; var3 >= 0; --var3) {
            var7 = (AnimationDriver)this.mActiveAnimations.valueAt(var3);
            if(var7.mHasFinished) {
               WritableMap var8 = Arguments.createMap();
               var8.putBoolean("finished", true);
               var7.mEndCallback.invoke(new Object[]{var8});
               this.mActiveAnimations.removeAt(var3);
            }
         }
      }

   }

   public void setAnimatedNodeOffset(int var1, double var2) {
      AnimatedNode var4 = (AnimatedNode)this.mAnimatedNodes.get(var1);
      if(var4 != null && var4 instanceof ValueAnimatedNode) {
         ((ValueAnimatedNode)var4).mOffset = var2;
         this.mUpdatedNodes.put(var1, var4);
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("Animated node with tag ");
         var5.append(var1);
         var5.append(" does not exists or is not a \'value\' node");
         throw new JSApplicationIllegalArgumentException(var5.toString());
      }
   }

   public void setAnimatedNodeValue(int var1, double var2) {
      AnimatedNode var4 = (AnimatedNode)this.mAnimatedNodes.get(var1);
      if(var4 != null && var4 instanceof ValueAnimatedNode) {
         this.stopAnimationsForNode(var4);
         ((ValueAnimatedNode)var4).mValue = var2;
         this.mUpdatedNodes.put(var1, var4);
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("Animated node with tag ");
         var5.append(var1);
         var5.append(" does not exists or is not a \'value\' node");
         throw new JSApplicationIllegalArgumentException(var5.toString());
      }
   }

   public void startAnimatingNode(int var1, int var2, ReadableMap var3, Callback var4) {
      AnimatedNode var5 = (AnimatedNode)this.mAnimatedNodes.get(var2);
      StringBuilder var8;
      if(var5 == null) {
         var8 = new StringBuilder();
         var8.append("Animated node with tag ");
         var8.append(var2);
         var8.append(" does not exists");
         throw new JSApplicationIllegalArgumentException(var8.toString());
      } else if(!(var5 instanceof ValueAnimatedNode)) {
         var8 = new StringBuilder();
         var8.append("Animated node should be of type ");
         var8.append(ValueAnimatedNode.class.getName());
         throw new JSApplicationIllegalArgumentException(var8.toString());
      } else {
         String var6 = var3.getString("type");
         Object var7;
         if("frames".equals(var6)) {
            var7 = new FrameBasedAnimationDriver(var3);
         } else if("spring".equals(var6)) {
            var7 = new SpringAnimation(var3);
         } else {
            if(!"decay".equals(var6)) {
               var8 = new StringBuilder();
               var8.append("Unsupported animation type: ");
               var8.append(var6);
               throw new JSApplicationIllegalArgumentException(var8.toString());
            }

            var7 = new DecayAnimation(var3);
         }

         ((AnimationDriver)var7).mId = var1;
         ((AnimationDriver)var7).mEndCallback = var4;
         ((AnimationDriver)var7).mAnimatedValue = (ValueAnimatedNode)var5;
         this.mActiveAnimations.put(var1, var7);
      }
   }

   public void startListeningToAnimatedNodeValue(int var1, AnimatedNodeValueListener var2) {
      AnimatedNode var3 = (AnimatedNode)this.mAnimatedNodes.get(var1);
      if(var3 != null && var3 instanceof ValueAnimatedNode) {
         ((ValueAnimatedNode)var3).setValueListener(var2);
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("Animated node with tag ");
         var4.append(var1);
         var4.append(" does not exists or is not a \'value\' node");
         throw new JSApplicationIllegalArgumentException(var4.toString());
      }
   }

   public void stopAnimation(int var1) {
      for(int var2 = 0; var2 < this.mActiveAnimations.size(); ++var2) {
         AnimationDriver var3 = (AnimationDriver)this.mActiveAnimations.valueAt(var2);
         if(var3.mId == var1) {
            WritableMap var4 = Arguments.createMap();
            var4.putBoolean("finished", false);
            var3.mEndCallback.invoke(new Object[]{var4});
            this.mActiveAnimations.removeAt(var2);
            return;
         }
      }

   }

   public void stopListeningToAnimatedNodeValue(int var1) {
      AnimatedNode var2 = (AnimatedNode)this.mAnimatedNodes.get(var1);
      if(var2 != null && var2 instanceof ValueAnimatedNode) {
         ((ValueAnimatedNode)var2).setValueListener((AnimatedNodeValueListener)null);
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Animated node with tag ");
         var3.append(var1);
         var3.append(" does not exists or is not a \'value\' node");
         throw new JSApplicationIllegalArgumentException(var3.toString());
      }
   }
}
