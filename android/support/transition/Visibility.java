package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.transition.AnimatorUtils;
import android.support.transition.Styleable;
import android.support.transition.Transition;
import android.support.transition.TransitionUtils;
import android.support.transition.TransitionValues;
import android.support.transition.ViewGroupOverlayImpl;
import android.support.transition.ViewGroupUtils;
import android.support.transition.ViewUtils;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class Visibility extends Transition {

   public static final int MODE_IN = 1;
   public static final int MODE_OUT = 2;
   private static final String PROPNAME_PARENT = "android:visibility:parent";
   private static final String PROPNAME_SCREEN_LOCATION = "android:visibility:screenLocation";
   static final String PROPNAME_VISIBILITY = "android:visibility:visibility";
   private static final String[] sTransitionProperties = new String[]{"android:visibility:visibility", "android:visibility:parent"};
   private int mMode = 3;


   public Visibility() {}

   public Visibility(Context var1, AttributeSet var2) {
      super(var1, var2);
      TypedArray var4 = var1.obtainStyledAttributes(var2, Styleable.VISIBILITY_TRANSITION);
      int var3 = TypedArrayUtils.getNamedInt(var4, (XmlResourceParser)var2, "transitionVisibilityMode", 0, 0);
      var4.recycle();
      if(var3 != 0) {
         this.setMode(var3);
      }

   }

   private void captureValues(TransitionValues var1) {
      int var2 = var1.view.getVisibility();
      var1.values.put("android:visibility:visibility", Integer.valueOf(var2));
      var1.values.put("android:visibility:parent", var1.view.getParent());
      int[] var3 = new int[2];
      var1.view.getLocationOnScreen(var3);
      var1.values.put("android:visibility:screenLocation", var3);
   }

   private Visibility.VisibilityInfo getVisibilityChangeInfo(TransitionValues var1, TransitionValues var2) {
      Visibility.VisibilityInfo var3 = new Visibility.VisibilityInfo();
      var3.mVisibilityChange = false;
      var3.mFadeIn = false;
      if(var1 != null && var1.values.containsKey("android:visibility:visibility")) {
         var3.mStartVisibility = ((Integer)var1.values.get("android:visibility:visibility")).intValue();
         var3.mStartParent = (ViewGroup)var1.values.get("android:visibility:parent");
      } else {
         var3.mStartVisibility = -1;
         var3.mStartParent = null;
      }

      if(var2 != null && var2.values.containsKey("android:visibility:visibility")) {
         var3.mEndVisibility = ((Integer)var2.values.get("android:visibility:visibility")).intValue();
         var3.mEndParent = (ViewGroup)var2.values.get("android:visibility:parent");
      } else {
         var3.mEndVisibility = -1;
         var3.mEndParent = null;
      }

      if(var1 != null && var2 != null) {
         if(var3.mStartVisibility == var3.mEndVisibility && var3.mStartParent == var3.mEndParent) {
            return var3;
         }

         if(var3.mStartVisibility != var3.mEndVisibility) {
            if(var3.mStartVisibility == 0) {
               var3.mFadeIn = false;
               var3.mVisibilityChange = true;
               return var3;
            }

            if(var3.mEndVisibility == 0) {
               var3.mFadeIn = true;
               var3.mVisibilityChange = true;
               return var3;
            }
         } else {
            if(var3.mEndParent == null) {
               var3.mFadeIn = false;
               var3.mVisibilityChange = true;
               return var3;
            }

            if(var3.mStartParent == null) {
               var3.mFadeIn = true;
               var3.mVisibilityChange = true;
               return var3;
            }
         }
      } else {
         if(var1 == null && var3.mEndVisibility == 0) {
            var3.mFadeIn = true;
            var3.mVisibilityChange = true;
            return var3;
         }

         if(var2 == null && var3.mStartVisibility == 0) {
            var3.mFadeIn = false;
            var3.mVisibilityChange = true;
         }
      }

      return var3;
   }

   public void captureEndValues(@NonNull TransitionValues var1) {
      this.captureValues(var1);
   }

   public void captureStartValues(@NonNull TransitionValues var1) {
      this.captureValues(var1);
   }

   @Nullable
   public Animator createAnimator(@NonNull ViewGroup var1, @Nullable TransitionValues var2, @Nullable TransitionValues var3) {
      Visibility.VisibilityInfo var4 = this.getVisibilityChangeInfo(var2, var3);
      return var4.mVisibilityChange && (var4.mStartParent != null || var4.mEndParent != null)?(var4.mFadeIn?this.onAppear(var1, var2, var4.mStartVisibility, var3, var4.mEndVisibility):this.onDisappear(var1, var2, var4.mStartVisibility, var3, var4.mEndVisibility)):null;
   }

   public int getMode() {
      return this.mMode;
   }

   @Nullable
   public String[] getTransitionProperties() {
      return sTransitionProperties;
   }

   public boolean isTransitionRequired(TransitionValues var1, TransitionValues var2) {
      boolean var4 = false;
      if(var1 == null && var2 == null) {
         return false;
      } else if(var1 != null && var2 != null && var2.values.containsKey("android:visibility:visibility") != var1.values.containsKey("android:visibility:visibility")) {
         return false;
      } else {
         Visibility.VisibilityInfo var5 = this.getVisibilityChangeInfo(var1, var2);
         boolean var3 = var4;
         if(var5.mVisibilityChange) {
            if(var5.mStartVisibility != 0) {
               var3 = var4;
               if(var5.mEndVisibility != 0) {
                  return var3;
               }
            }

            var3 = true;
         }

         return var3;
      }
   }

   public boolean isVisible(TransitionValues var1) {
      boolean var4 = false;
      if(var1 == null) {
         return false;
      } else {
         int var2 = ((Integer)var1.values.get("android:visibility:visibility")).intValue();
         View var5 = (View)var1.values.get("android:visibility:parent");
         boolean var3 = var4;
         if(var2 == 0) {
            var3 = var4;
            if(var5 != null) {
               var3 = true;
            }
         }

         return var3;
      }
   }

   public Animator onAppear(ViewGroup var1, TransitionValues var2, int var3, TransitionValues var4, int var5) {
      if((this.mMode & 1) == 1) {
         if(var4 == null) {
            return null;
         } else {
            if(var2 == null) {
               View var6 = (View)var4.view.getParent();
               if(this.getVisibilityChangeInfo(this.getMatchedTransitionValues(var6, false), this.getTransitionValues(var6, false)).mVisibilityChange) {
                  return null;
               }
            }

            return this.onAppear(var1, var4.view, var2, var4);
         }
      } else {
         return null;
      }
   }

   public Animator onAppear(ViewGroup var1, View var2, TransitionValues var3, TransitionValues var4) {
      return null;
   }

   public Animator onDisappear(ViewGroup var1, TransitionValues var2, int var3, TransitionValues var4, int var5) {
      if((this.mMode & 2) != 2) {
         return null;
      } else {
         View var7;
         if(var2 != null) {
            var7 = var2.view;
         } else {
            var7 = null;
         }

         final View var6;
         if(var4 != null) {
            var6 = var4.view;
         } else {
            var6 = null;
         }

         label88: {
            label87: {
               label97: {
                  if(var6 != null && var6.getParent() != null) {
                     if(var5 == 4 || var7 == var6) {
                        Object var8 = null;
                        var7 = var6;
                        var6 = (View)var8;
                        break label88;
                     }

                     if(!this.mCanRemoveViews) {
                        var6 = TransitionUtils.copyViewImage(var1, var7, (View)var7.getParent());
                        break label97;
                     }
                  } else {
                     if(var6 != null) {
                        break label97;
                     }

                     if(var7 == null) {
                        break label87;
                     }

                     if(var7.getParent() != null) {
                        if(!(var7.getParent() instanceof View)) {
                           break label87;
                        }

                        var6 = (View)var7.getParent();
                        if(!this.getVisibilityChangeInfo(this.getTransitionValues(var6, true), this.getMatchedTransitionValues(var6, true)).mVisibilityChange) {
                           var6 = TransitionUtils.copyViewImage(var1, var7, var6);
                        } else {
                           if(var6.getParent() == null) {
                              var3 = var6.getId();
                              if(var3 != -1 && var1.findViewById(var3) != null && this.mCanRemoveViews) {
                                 var6 = var7;
                                 break label97;
                              }
                           }

                           var6 = null;
                        }
                        break label97;
                     }
                  }

                  var6 = var7;
               }

               var7 = null;
               break label88;
            }

            var6 = null;
            var7 = var6;
         }

         Animator var9;
         if(var6 != null && var2 != null) {
            int[] var11 = (int[])var2.values.get("android:visibility:screenLocation");
            var3 = var11[0];
            var5 = var11[1];
            var11 = new int[2];
            var1.getLocationOnScreen(var11);
            var6.offsetLeftAndRight(var3 - var11[0] - var6.getLeft());
            var6.offsetTopAndBottom(var5 - var11[1] - var6.getTop());
            final ViewGroupOverlayImpl var12 = ViewGroupUtils.getOverlay(var1);
            var12.add(var6);
            var9 = this.onDisappear(var1, var6, var2, var4);
            if(var9 == null) {
               var12.remove(var6);
               return var9;
            } else {
               var9.addListener(new AnimatorListenerAdapter() {
                  public void onAnimationEnd(Animator var1) {
                     var12.remove(var6);
                  }
               });
               return var9;
            }
         } else if(var7 != null) {
            var3 = var7.getVisibility();
            ViewUtils.setTransitionVisibility(var7, 0);
            var9 = this.onDisappear(var1, var7, var2, var4);
            if(var9 != null) {
               Visibility.DisappearListener var10 = new Visibility.DisappearListener(var7, var5, true);
               var9.addListener(var10);
               AnimatorUtils.addPauseListener(var9, var10);
               this.addListener(var10);
               return var9;
            } else {
               ViewUtils.setTransitionVisibility(var7, var3);
               return var9;
            }
         } else {
            return null;
         }
      }
   }

   public Animator onDisappear(ViewGroup var1, View var2, TransitionValues var3, TransitionValues var4) {
      return null;
   }

   public void setMode(int var1) {
      if((var1 & -4) != 0) {
         throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
      } else {
         this.mMode = var1;
      }
   }

   static class VisibilityInfo {

      ViewGroup mEndParent;
      int mEndVisibility;
      boolean mFadeIn;
      ViewGroup mStartParent;
      int mStartVisibility;
      boolean mVisibilityChange;


   }

   static class DisappearListener extends AnimatorListenerAdapter implements AnimatorUtils.AnimatorPauseListenerCompat, Transition.TransitionListener {

      boolean mCanceled = false;
      private final int mFinalVisibility;
      private boolean mLayoutSuppressed;
      private final ViewGroup mParent;
      private final boolean mSuppressLayout;
      private final View mView;


      DisappearListener(View var1, int var2, boolean var3) {
         this.mView = var1;
         this.mFinalVisibility = var2;
         this.mParent = (ViewGroup)var1.getParent();
         this.mSuppressLayout = var3;
         this.suppressLayout(true);
      }

      private void hideViewWhenNotCanceled() {
         if(!this.mCanceled) {
            ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
            if(this.mParent != null) {
               this.mParent.invalidate();
            }
         }

         this.suppressLayout(false);
      }

      private void suppressLayout(boolean var1) {
         if(this.mSuppressLayout && this.mLayoutSuppressed != var1 && this.mParent != null) {
            this.mLayoutSuppressed = var1;
            ViewGroupUtils.suppressLayout(this.mParent, var1);
         }

      }

      public void onAnimationCancel(Animator var1) {
         this.mCanceled = true;
      }

      public void onAnimationEnd(Animator var1) {
         this.hideViewWhenNotCanceled();
      }

      public void onAnimationPause(Animator var1) {
         if(!this.mCanceled) {
            ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
         }

      }

      public void onAnimationRepeat(Animator var1) {}

      public void onAnimationResume(Animator var1) {
         if(!this.mCanceled) {
            ViewUtils.setTransitionVisibility(this.mView, 0);
         }

      }

      public void onAnimationStart(Animator var1) {}

      public void onTransitionCancel(@NonNull Transition var1) {}

      public void onTransitionEnd(@NonNull Transition var1) {
         this.hideViewWhenNotCanceled();
         var1.removeListener(this);
      }

      public void onTransitionPause(@NonNull Transition var1) {
         this.suppressLayout(false);
      }

      public void onTransitionResume(@NonNull Transition var1) {
         this.suppressLayout(true);
      }

      public void onTransitionStart(@NonNull Transition var1) {}
   }

   @Retention(RetentionPolicy.SOURCE)
   @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
   public @interface Mode {
   }
}
