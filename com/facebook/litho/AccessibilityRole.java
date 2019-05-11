package com.facebook.litho;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AccessibilityRole {

   public static final String ACTION_BAR_TAB = "android.app.ActionBar$Tab";
   public static final String BUTTON = "android.widget.Button";
   public static final String CHECKED_TEXT_VIEW = "android.widget.CheckedTextView";
   public static final String CHECK_BOX = "android.widget.CompoundButton";
   public static final String DATE_PICKER = "android.widget.DatePicker";
   public static final String DATE_PICKER_DIALOG = "android.app.DatePickerDialog";
   public static final String DRAWER_LAYOUT = "android.support.v4.widget.DrawerLayout";
   public static final String DROP_DOWN_LIST = "android.widget.Spinner";
   public static final String EDIT_TEXT = "android.widget.EditText";
   public static final String GRID = "android.widget.GridView";
   public static final String HORIZONTAL_SCROLL_VIEW = "android.widget.HorizontalScrollView";
   public static final String ICON_MENU = "com.android.internal.view.menu.IconMenuView";
   public static final String IMAGE = "android.widget.ImageView";
   public static final String IMAGE_BUTTON = "android.widget.ImageView";
   public static final String KEYBOARD_KEY = "android.inputmethodservice.Keyboard$Key";
   public static final String LIST = "android.widget.AbsListView";
   public static final String NUMBER_PICKER = "android.widget.NumberPicker";
   public static final String PAGER = "android.support.v4.view.ViewPager";
   public static final String PROGRESS_BAR = "android.widget.ProgressBar";
   public static final String RADIO_BUTTON = "android.widget.RadioButton";
   public static final String SCROLL_VIEW = "android.widget.ScrollView";
   public static final String SEEK_CONTROL = "android.widget.SeekBar";
   public static final String SLIDING_DRAWER = "android.widget.SlidingDrawer";
   public static final String SWITCH = "android.widget.Switch";
   public static final String TAB_BAR = "android.widget.TabWidget";
   public static final String TIME_PICKER = "android.widget.TimePicker";
   public static final String TIME_PICKER_DIALOG = "android.app.TimePickerDialog";
   public static final String TOAST = "android.widget.Toast$TN";
   public static final String TOGGLE_BUTTON = "android.widget.ToggleButton";
   public static final String VIEW_GROUP = "android.view.ViewGroup";
   public static final String WEB_VIEW = "android.webkit.WebView";



   @Retention(RetentionPolicy.SOURCE)
   public @interface AccessibilityRoleType {
   }
}
