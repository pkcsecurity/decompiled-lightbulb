package com.facebook.appevents.codeless;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.codeless.RCTCodelessLoggingEventListener;
import com.facebook.appevents.codeless.internal.EventBinding;
import com.facebook.appevents.codeless.internal.ParameterComponent;
import com.facebook.appevents.codeless.internal.PathComponent;
import com.facebook.appevents.codeless.internal.ViewHierarchy;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.InternalSettings;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CodelessMatcher {

   private static final String CURRENT_CLASS_NAME = ".";
   private static final String PARENT_CLASS_NAME = "..";
   private static final String TAG = CodelessMatcher.class.getCanonicalName();
   private Set<Activity> activitiesSet = new HashSet();
   private HashMap<String, String> delegateMap = new HashMap();
   private final Handler uiThreadHandler = new Handler(Looper.getMainLooper());
   private Set<CodelessMatcher.ViewMatcher> viewMatchers = new HashSet();


   // $FF: synthetic method
   static String access$100() {
      return TAG;
   }

   public static Bundle getParameters(EventBinding var0, View var1, View var2) {
      Bundle var4 = new Bundle();
      if(var0 == null) {
         return var4;
      } else {
         List var3 = var0.getViewParameters();
         if(var3 != null) {
            Iterator var5 = var3.iterator();

            while(var5.hasNext()) {
               ParameterComponent var6 = (ParameterComponent)var5.next();
               if(var6.value != null && var6.value.length() > 0) {
                  var4.putString(var6.name, var6.value);
               } else if(var6.path.size() > 0) {
                  if(var6.pathType.equals("relative")) {
                     var3 = CodelessMatcher.ViewMatcher.findViewByPath(var0, var2, var6.path, 0, -1, var2.getClass().getSimpleName());
                  } else {
                     var3 = CodelessMatcher.ViewMatcher.findViewByPath(var0, var1, var6.path, 0, -1, var1.getClass().getSimpleName());
                  }

                  Iterator var8 = var3.iterator();

                  while(var8.hasNext()) {
                     CodelessMatcher.MatchedView var7 = (CodelessMatcher.MatchedView)var8.next();
                     if(var7.getView() != null) {
                        String var9 = ViewHierarchy.getTextOfView(var7.getView());
                        if(var9.length() > 0) {
                           var4.putString(var6.name, var9);
                           break;
                        }
                     }
                  }
               }
            }
         }

         return var4;
      }
   }

   private void matchViews() {
      Iterator var1 = this.activitiesSet.iterator();

      while(var1.hasNext()) {
         Activity var3 = (Activity)var1.next();
         View var2 = var3.getWindow().getDecorView().getRootView();
         String var5 = var3.getClass().getSimpleName();
         CodelessMatcher.ViewMatcher var4 = new CodelessMatcher.ViewMatcher(var2, this.uiThreadHandler, this.delegateMap, var5);
         this.viewMatchers.add(var4);
      }

   }

   private void startTracking() {
      if(Thread.currentThread() == Looper.getMainLooper().getThread()) {
         this.matchViews();
      } else {
         this.uiThreadHandler.post(new Runnable() {
            public void run() {
               CodelessMatcher.this.matchViews();
            }
         });
      }
   }

   public void add(Activity var1) {
      if(!InternalSettings.isUnityApp()) {
         if(Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new FacebookException("Can\'t add activity to CodelessMatcher on non-UI thread");
         } else {
            this.activitiesSet.add(var1);
            this.delegateMap.clear();
            this.startTracking();
         }
      }
   }

   public void remove(Activity var1) {
      if(!InternalSettings.isUnityApp()) {
         if(Thread.currentThread() != Looper.getMainLooper().getThread()) {
            throw new FacebookException("Can\'t remove activity from CodelessMatcher on non-UI thread");
         } else {
            this.activitiesSet.remove(var1);
            this.viewMatchers.clear();
            this.delegateMap.clear();
         }
      }
   }

   public static class ViewMatcher implements OnGlobalLayoutListener, OnScrollChangedListener, Runnable {

      private final String activityName;
      private HashMap<String, String> delegateMap;
      @Nullable
      private List<EventBinding> eventBindings;
      private final Handler handler;
      private WeakReference<View> rootView;


      public ViewMatcher(View var1, Handler var2, HashMap<String, String> var3, String var4) {
         this.rootView = new WeakReference(var1);
         this.handler = var2;
         this.delegateMap = var3;
         this.activityName = var4;
         this.handler.postDelayed(this, 200L);
      }

      private void attachListener(CodelessMatcher.MatchedView param1, View param2, EventBinding param3) {
         // $FF: Couldn't be decompiled
      }

      private void attachRCTListener(CodelessMatcher.MatchedView var1, View var2, View var3, EventBinding var4) {
         if(var4 != null) {
            View var9 = var1.getView();
            if(var9 != null) {
               if(ViewHierarchy.isRCTButton(var9, var3)) {
                  String var10 = var1.getViewMapKey();
                  OnTouchListener var11 = ViewHierarchy.getExistingOnTouchListener(var9);
                  boolean var8 = false;
                  boolean var5;
                  if(var11 != null) {
                     var5 = true;
                  } else {
                     var5 = false;
                  }

                  boolean var6;
                  if(var5 && var11 instanceof RCTCodelessLoggingEventListener.AutoLoggingOnTouchListener) {
                     var6 = true;
                  } else {
                     var6 = false;
                  }

                  boolean var7 = var8;
                  if(var6) {
                     var7 = var8;
                     if(((RCTCodelessLoggingEventListener.AutoLoggingOnTouchListener)var11).getSupportCodelessLogging()) {
                        var7 = true;
                     }
                  }

                  if(!this.delegateMap.containsKey(var10) && (!var5 || !var6 || !var7)) {
                     var9.setOnTouchListener(RCTCodelessLoggingEventListener.getOnTouchListener(var4, var2, var9));
                     this.delegateMap.put(var10, var4.getEventName());
                  }

               }
            }
         }
      }

      public static List<CodelessMatcher.MatchedView> findViewByPath(EventBinding var0, View var1, List<PathComponent> var2, int var3, int var4, String var5) {
         StringBuilder var7 = new StringBuilder();
         var7.append(var5);
         var7.append(".");
         var7.append(String.valueOf(var4));
         var5 = var7.toString();
         ArrayList var11 = new ArrayList();
         if(var1 == null) {
            return var11;
         } else {
            int var6;
            List var9;
            if(var3 >= var2.size()) {
               var11.add(new CodelessMatcher.MatchedView(var1, var5));
            } else {
               PathComponent var8 = (PathComponent)var2.get(var3);
               if(var8.className.equals("..")) {
                  ViewParent var10 = var1.getParent();
                  if(var10 instanceof ViewGroup) {
                     var9 = findVisibleChildren((ViewGroup)var10);
                     var6 = var9.size();

                     for(var4 = 0; var4 < var6; ++var4) {
                        var11.addAll(findViewByPath(var0, (View)var9.get(var4), var2, var3 + 1, var4, var5));
                     }
                  }

                  return var11;
               }

               if(var8.className.equals(".")) {
                  var11.add(new CodelessMatcher.MatchedView(var1, var5));
                  return var11;
               }

               if(!isTheSameView(var1, var8, var4)) {
                  return var11;
               }

               if(var3 == var2.size() - 1) {
                  var11.add(new CodelessMatcher.MatchedView(var1, var5));
               }
            }

            if(var1 instanceof ViewGroup) {
               var9 = findVisibleChildren((ViewGroup)var1);
               var6 = var9.size();

               for(var4 = 0; var4 < var6; ++var4) {
                  var11.addAll(findViewByPath(var0, (View)var9.get(var4), var2, var3 + 1, var4, var5));
               }
            }

            return var11;
         }
      }

      private static List<View> findVisibleChildren(ViewGroup var0) {
         ArrayList var3 = new ArrayList();
         int var2 = var0.getChildCount();

         for(int var1 = 0; var1 < var2; ++var1) {
            View var4 = var0.getChildAt(var1);
            if(var4.getVisibility() == 0) {
               var3.add(var4);
            }
         }

         return var3;
      }

      private static boolean isTheSameView(View var0, PathComponent var1, int var2) {
         if(var1.index != -1 && var2 != var1.index) {
            return false;
         } else {
            String var7;
            if(!var0.getClass().getCanonicalName().equals(var1.className)) {
               if(!var1.className.matches(".*android\\..*")) {
                  return false;
               }

               String[] var3 = var1.className.split("\\.");
               if(var3.length <= 0) {
                  return false;
               }

               var7 = var3[var3.length - 1];
               if(!var0.getClass().getSimpleName().equals(var7)) {
                  return false;
               }
            }

            if((var1.matchBitmask & PathComponent.MatchBitmaskType.ID.getValue()) > 0 && var1.id != var0.getId()) {
               return false;
            } else if((var1.matchBitmask & PathComponent.MatchBitmaskType.TEXT.getValue()) > 0 && !var1.text.equals(ViewHierarchy.getTextOfView(var0))) {
               return false;
            } else {
               if((var1.matchBitmask & PathComponent.MatchBitmaskType.DESCRIPTION.getValue()) > 0) {
                  String var4 = var1.description;
                  if(var0.getContentDescription() == null) {
                     var7 = "";
                  } else {
                     var7 = String.valueOf(var0.getContentDescription());
                  }

                  if(!var4.equals(var7)) {
                     return false;
                  }
               }

               if((var1.matchBitmask & PathComponent.MatchBitmaskType.HINT.getValue()) > 0 && !var1.hint.equals(ViewHierarchy.getHintOfView(var0))) {
                  return false;
               } else {
                  if((var1.matchBitmask & PathComponent.MatchBitmaskType.TAG.getValue()) > 0) {
                     String var6 = var1.tag;
                     String var5;
                     if(var0.getTag() == null) {
                        var5 = "";
                     } else {
                        var5 = String.valueOf(var0.getTag());
                     }

                     if(!var6.equals(var5)) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         }
      }

      private void startMatch() {
         if(this.eventBindings != null && this.rootView.get() != null) {
            for(int var1 = 0; var1 < this.eventBindings.size(); ++var1) {
               this.findView((EventBinding)this.eventBindings.get(var1), (View)this.rootView.get());
            }
         }

      }

      public void findView(EventBinding var1, View var2) {
         if(var1 != null) {
            if(var2 != null) {
               if(TextUtils.isEmpty(var1.getActivityName()) || var1.getActivityName().equals(this.activityName)) {
                  List var3 = var1.getViewPath();
                  if(var3.size() <= 25) {
                     Iterator var4 = findViewByPath(var1, var2, var3, 0, -1, this.activityName).iterator();

                     while(var4.hasNext()) {
                        this.attachListener((CodelessMatcher.MatchedView)var4.next(), var2, var1);
                     }

                  }
               }
            }
         }
      }

      public void onGlobalLayout() {
         this.startMatch();
      }

      public void onScrollChanged() {
         this.startMatch();
      }

      public void run() {
         FetchedAppSettings var1 = FetchedAppSettingsManager.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId());
         if(var1 != null) {
            if(var1.getCodelessEventsEnabled()) {
               this.eventBindings = EventBinding.parseArray(var1.getEventBindings());
               if(this.eventBindings != null) {
                  View var2 = (View)this.rootView.get();
                  if(var2 == null) {
                     return;
                  }

                  ViewTreeObserver var3 = var2.getViewTreeObserver();
                  if(var3.isAlive()) {
                     var3.addOnGlobalLayoutListener(this);
                     var3.addOnScrollChangedListener(this);
                  }

                  this.startMatch();
               }

            }
         }
      }
   }

   public static class MatchedView {

      private WeakReference<View> view;
      private String viewMapKey;


      public MatchedView(View var1, String var2) {
         this.view = new WeakReference(var1);
         this.viewMapKey = var2;
      }

      @Nullable
      public View getView() {
         return this.view == null?null:(View)this.view.get();
      }

      public String getViewMapKey() {
         return this.viewMapKey;
      }
   }
}
