package com.facebook.appevents.codeless.internal;

import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.AccessibilityDelegate;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import com.facebook.internal.Utility;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewHierarchy {

   private static final int ADAPTER_VIEW_ITEM_BITMASK = 9;
   private static final int BUTTON_BITMASK = 2;
   private static final int CHECKBOX_BITMASK = 15;
   private static final String CHILDREN_VIEW_KEY = "childviews";
   private static final String CLASS_NAME_KEY = "classname";
   private static final String CLASS_RCTROOTVIEW = "com.facebook.react.ReactRootView";
   private static final String CLASS_RCTTEXTVIEW = "com.facebook.react.views.view.ReactTextView";
   private static final String CLASS_RCTVIEWGROUP = "com.facebook.react.views.view.ReactViewGroup";
   private static final String CLASS_TOUCHTARGETHELPER = "com.facebook.react.uimanager.TouchTargetHelper";
   private static final String CLASS_TYPE_BITMASK_KEY = "classtypebitmask";
   private static final int CLICKABLE_VIEW_BITMASK = 5;
   private static final String DESC_KEY = "description";
   private static final String DIMENSION_HEIGHT_KEY = "height";
   private static final String DIMENSION_KEY = "dimension";
   private static final String DIMENSION_LEFT_KEY = "left";
   private static final String DIMENSION_SCROLL_X_KEY = "scrollx";
   private static final String DIMENSION_SCROLL_Y_KEY = "scrolly";
   private static final String DIMENSION_TOP_KEY = "top";
   private static final String DIMENSION_VISIBILITY_KEY = "visibility";
   private static final String DIMENSION_WIDTH_KEY = "width";
   private static final String GET_ACCESSIBILITY_METHOD = "getAccessibilityDelegate";
   private static final String HINT_KEY = "hint";
   private static final String ICON_BITMAP = "icon_image";
   private static final int ICON_MAX_EDGE_LENGTH = 44;
   private static final String ID_KEY = "id";
   private static final int IMAGEVIEW_BITMASK = 1;
   private static final int INPUT_BITMASK = 11;
   private static final int LABEL_BITMASK = 10;
   private static final String METHOD_FIND_TOUCHTARGET_VIEW = "findTouchTargetView";
   private static final int PICKER_BITMASK = 12;
   private static final int RADIO_GROUP_BITMASK = 14;
   private static final int RATINGBAR_BITMASK = 16;
   private static WeakReference<View> RCTRootViewReference = new WeakReference((Object)null);
   private static final int REACT_NATIVE_BUTTON_BITMASK = 6;
   private static final int SWITCH_BITMASK = 13;
   private static final String TAG = ViewHierarchy.class.getCanonicalName();
   private static final String TAG_KEY = "tag";
   private static final int TEXTVIEW_BITMASK = 0;
   private static final String TEXT_IS_BOLD = "is_bold";
   private static final String TEXT_IS_ITALIC = "is_italic";
   private static final String TEXT_KEY = "text";
   private static final String TEXT_SIZE = "font_size";
   private static final String TEXT_STYLE = "text_style";
   @Nullable
   private static Method methodFindTouchTargetView;


   @Nullable
   public static View findRCTRootView(View var0) {
      while(true) {
         if(var0 != null) {
            if(isRCTRootView(var0)) {
               return var0;
            }

            ViewParent var1 = var0.getParent();
            if(var1 != null && var1 instanceof View) {
               var0 = (View)var1;
               continue;
            }
         }

         return null;
      }
   }

   public static List<View> getChildrenOfView(View var0) {
      ArrayList var3 = new ArrayList();
      if(var0 != null && var0 instanceof ViewGroup) {
         ViewGroup var4 = (ViewGroup)var0;
         int var2 = var4.getChildCount();

         for(int var1 = 0; var1 < var2; ++var1) {
            var3.add(var4.getChildAt(var1));
         }
      }

      return var3;
   }

   private static int getClassTypeBitmask(View var0) {
      byte var2;
      if(var0 instanceof ImageView) {
         var2 = 2;
      } else {
         var2 = 0;
      }

      int var1 = var2;
      if(isClickableView(var0)) {
         var1 = var2 | 32;
      }

      int var4 = var1;
      if(isAdapterViewItem(var0)) {
         var4 = var1 | 512;
      }

      int var3;
      if(var0 instanceof TextView) {
         var4 = var4 | 1024 | 1;
         var1 = var4;
         if(var0 instanceof Button) {
            var4 |= 4;
            if(var0 instanceof Switch) {
               var1 = var4 | 8192;
            } else {
               var1 = var4;
               if(var0 instanceof CheckBox) {
                  var1 = var4 | '\u8000';
               }
            }
         }

         var3 = var1;
         if(var0 instanceof EditText) {
            return var1 | 2048;
         }
      } else if(!(var0 instanceof Spinner) && !(var0 instanceof DatePicker)) {
         if(var0 instanceof RatingBar) {
            return var4 | 65536;
         }

         if(var0 instanceof RadioGroup) {
            return var4 | 16384;
         }

         var3 = var4;
         if(var0 instanceof ViewGroup) {
            var3 = var4;
            if(isRCTButton(var0, (View)RCTRootViewReference.get())) {
               return var4 | 64;
            }
         }
      } else {
         var3 = var4 | 4096;
      }

      return var3;
   }

   public static JSONObject getDictionaryOfView(View param0) {
      // $FF: Couldn't be decompiled
   }

   private static JSONObject getDimensionOfView(View var0) {
      JSONObject var1 = new JSONObject();

      try {
         var1.put("top", var0.getTop());
         var1.put("left", var0.getLeft());
         var1.put("width", var0.getWidth());
         var1.put("height", var0.getHeight());
         var1.put("scrollx", var0.getScrollX());
         var1.put("scrolly", var0.getScrollY());
         var1.put("visibility", var0.getVisibility());
         return var1;
      } catch (JSONException var2) {
         Log.e(TAG, "Failed to create JSONObject for dimension.", var2);
         return var1;
      }
   }

   @Nullable
   public static AccessibilityDelegate getExistingDelegate(View var0) {
      try {
         AccessibilityDelegate var6 = (AccessibilityDelegate)var0.getClass().getMethod("getAccessibilityDelegate", new Class[0]).invoke(var0, new Object[0]);
         return var6;
      } catch (NoSuchMethodException var1) {
         return null;
      } catch (NullPointerException var2) {
         return null;
      } catch (SecurityException var3) {
         return null;
      } catch (IllegalAccessException var4) {
         return null;
      } catch (InvocationTargetException var5) {
         return null;
      }
   }

   @Nullable
   public static OnTouchListener getExistingOnTouchListener(View param0) {
      // $FF: Couldn't be decompiled
   }

   public static String getHintOfView(View var0) {
      CharSequence var1;
      if(var0 instanceof TextView) {
         var1 = ((TextView)var0).getHint();
      } else if(var0 instanceof EditText) {
         var1 = ((EditText)var0).getHint();
      } else {
         var1 = null;
      }

      return var1 == null?"":var1.toString();
   }

   @Nullable
   public static ViewGroup getParentOfView(View var0) {
      if(var0 == null) {
         return null;
      } else {
         ViewParent var1 = var0.getParent();
         return var1 != null && var1 instanceof ViewGroup?(ViewGroup)var1:null;
      }
   }

   public static String getTextOfView(View var0) {
      boolean var4 = var0 instanceof TextView;
      Object var6 = null;
      Object var5;
      Object var7;
      if(var4) {
         var5 = ((TextView)var0).getText();
         if(!(var0 instanceof Switch)) {
            return var5 == null?"":var5.toString();
         }

         if(((Switch)var0).isChecked()) {
            var7 = "1";
         } else {
            var7 = "0";
         }
      } else {
         if(var0 instanceof Spinner) {
            var7 = ((Spinner)var0).getSelectedItem();
            var5 = var6;
            if(var7 != null) {
               var5 = var7.toString();
            }

            return var5 == null?"":var5.toString();
         }

         var4 = var0 instanceof DatePicker;
         int var1 = 0;
         if(var4) {
            DatePicker var10 = (DatePicker)var0;
            var5 = String.format("%04d-%02d-%02d", new Object[]{Integer.valueOf(var10.getYear()), Integer.valueOf(var10.getMonth()), Integer.valueOf(var10.getDayOfMonth())});
            return var5 == null?"":var5.toString();
         }

         if(var0 instanceof TimePicker) {
            TimePicker var9 = (TimePicker)var0;
            var5 = String.format("%02d:%02d", new Object[]{Integer.valueOf(var9.getCurrentHour().intValue()), Integer.valueOf(var9.getCurrentMinute().intValue())});
            return var5 == null?"":var5.toString();
         }

         if(!(var0 instanceof RadioGroup)) {
            var5 = var6;
            if(var0 instanceof RatingBar) {
               var5 = String.valueOf(((RatingBar)var0).getRating());
            }

            return var5 == null?"":var5.toString();
         }

         RadioGroup var8 = (RadioGroup)var0;
         int var2 = var8.getCheckedRadioButtonId();
         int var3 = var8.getChildCount();

         while(true) {
            var5 = var6;
            if(var1 >= var3) {
               return var5 == null?"":var5.toString();
            }

            View var11 = var8.getChildAt(var1);
            if(var11.getId() == var2 && var11 instanceof RadioButton) {
               var7 = ((RadioButton)var11).getText();
               break;
            }

            ++var1;
         }
      }

      var5 = var7;
      return var5 == null?"":var5.toString();
   }

   @Nullable
   public static View getTouchReactView(float[] param0, @Nullable View param1) {
      // $FF: Couldn't be decompiled
   }

   private static float[] getViewLocationOnScreen(View var0) {
      int[] var1 = new int[2];
      var0.getLocationOnScreen(var1);
      return new float[]{(float)var1[0], (float)var1[1]};
   }

   private static void initTouchTargetHelperMethods() {
      if(methodFindTouchTargetView == null) {
         try {
            methodFindTouchTargetView = Class.forName("com.facebook.react.uimanager.TouchTargetHelper").getDeclaredMethod("findTouchTargetView", new Class[]{float[].class, ViewGroup.class});
            methodFindTouchTargetView.setAccessible(true);
         } catch (ClassNotFoundException var1) {
            Utility.logd(TAG, (Exception)var1);
         } catch (NoSuchMethodException var2) {
            Utility.logd(TAG, (Exception)var2);
         }
      }
   }

   private static boolean isAdapterViewItem(View var0) {
      ViewParent var1 = var0.getParent();
      return var1 != null && (var1 instanceof AdapterView || var1 instanceof NestedScrollingChild);
   }

   public static boolean isClickableView(View param0) {
      // $FF: Couldn't be decompiled
   }

   public static boolean isRCTButton(View var0, @Nullable View var1) {
      boolean var2 = var0.getClass().getName().equals("com.facebook.react.views.view.ReactViewGroup");
      boolean var3 = false;
      if(var2) {
         var1 = getTouchReactView(getViewLocationOnScreen(var0), var1);
         var2 = var3;
         if(var1 != null) {
            var2 = var3;
            if(var1.getId() == var0.getId()) {
               var2 = true;
            }
         }

         return var2;
      } else {
         return false;
      }
   }

   public static boolean isRCTRootView(View var0) {
      return var0.getClass().getName().equals("com.facebook.react.ReactRootView");
   }

   public static boolean isRCTTextView(View var0) {
      return var0.getClass().getName().equals("com.facebook.react.views.view.ReactTextView");
   }

   public static boolean isRCTViewGroup(View var0) {
      return var0.getClass().getName().equals("com.facebook.react.views.view.ReactViewGroup");
   }

   public static JSONObject setAppearanceOfView(View param0, JSONObject param1, float param2) {
      // $FF: Couldn't be decompiled
   }

   public static JSONObject setBasicInfoOfView(View param0, JSONObject param1) {
      // $FF: Couldn't be decompiled
   }
}
