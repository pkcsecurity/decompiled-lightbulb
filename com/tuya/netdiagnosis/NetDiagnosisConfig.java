package com.tuya.netdiagnosis;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Keep;
import com.tuya.netdiagnosis.R;
import com.tuya.netdiagnosis.model.DomainPort;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata
@Keep
public final class NetDiagnosisConfig {

   public static final NetDiagnosisConfig INSTANCE = new NetDiagnosisConfig();
   @NotNull
   private static String email;
   private static boolean hideDomain;
   private static boolean lightStatusBar;
   private static boolean showAlias;
   private static boolean showLogDetail;
   private static boolean supportSendWechat;
   @ColorInt
   private static int systemBarColor = Color.parseColor("#303030");
   private static boolean titleCenter;
   @ColorRes
   private static int toolbarThemeBackgroundColor;
   @ColorRes
   private static int toolbarThemeTextColor = R.color.net_diagnosis_text_title;
   @NotNull
   private static DomainPort[] urls = new DomainPort[0];


   @JvmStatic
   // $FF: synthetic method
   public static void email$annotations() {}

   @NotNull
   public static final String getEmail() {
      return email;
   }

   public static final boolean getHideDomain() {
      return hideDomain;
   }

   public static final boolean getLightStatusBar() {
      return lightStatusBar;
   }

   public static final boolean getShowAlias() {
      return showAlias;
   }

   public static final boolean getShowLogDetail() {
      return showLogDetail;
   }

   public static final boolean getSupportSendWechat() {
      return supportSendWechat;
   }

   public static final int getSystemBarColor() {
      return systemBarColor;
   }

   public static final boolean getTitleCenter() {
      return titleCenter;
   }

   public static final int getToolbarThemeBackgroundColor() {
      return toolbarThemeBackgroundColor;
   }

   public static final int getToolbarThemeTextColor() {
      return toolbarThemeTextColor;
   }

   @NotNull
   public static final DomainPort[] getUrls() {
      return urls;
   }

   @JvmStatic
   // $FF: synthetic method
   public static void hideDomain$annotations() {}

   @JvmStatic
   // $FF: synthetic method
   public static void lightStatusBar$annotations() {}

   public static final void setEmail(@NotNull String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "<set-?>");
      email = var0;
   }

   public static final void setHideDomain(boolean var0) {
      hideDomain = var0;
   }

   public static final void setLightStatusBar(boolean var0) {
      lightStatusBar = var0;
   }

   public static final void setShowAlias(boolean var0) {
      showAlias = var0;
   }

   public static final void setShowLogDetail(boolean var0) {
      showLogDetail = var0;
   }

   public static final void setSupportSendWechat(boolean var0) {
      supportSendWechat = var0;
   }

   public static final void setSystemBarColor(int var0) {
      systemBarColor = var0;
   }

   public static final void setTitleCenter(boolean var0) {
      titleCenter = var0;
   }

   public static final void setToolbarThemeBackgroundColor(int var0) {
      toolbarThemeBackgroundColor = var0;
   }

   public static final void setToolbarThemeTextColor(int var0) {
      toolbarThemeTextColor = var0;
   }

   public static final void setUrls(@NotNull DomainPort[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "<set-?>");
      urls = var0;
   }

   @JvmStatic
   // $FF: synthetic method
   public static void showAlias$annotations() {}

   @JvmStatic
   // $FF: synthetic method
   public static void showLogDetail$annotations() {}

   @JvmStatic
   // $FF: synthetic method
   public static void supportSendWechat$annotations() {}

   @JvmStatic
   // $FF: synthetic method
   public static void systemBarColor$annotations() {}

   @JvmStatic
   // $FF: synthetic method
   public static void titleCenter$annotations() {}

   @JvmStatic
   // $FF: synthetic method
   public static void toolbarThemeBackgroundColor$annotations() {}

   @JvmStatic
   // $FF: synthetic method
   public static void toolbarThemeTextColor$annotations() {}

   @JvmStatic
   // $FF: synthetic method
   public static void urls$annotations() {}
}
