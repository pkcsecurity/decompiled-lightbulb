package com.alibaba.wireless.security.framework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import com.alibaba.wireless.security.framework.a;
import com.alibaba.wireless.security.framework.b;

public class SGBasePluginActivity extends Activity implements com.alibaba.wireless.security.framework.a.b {

   protected int mFrom = 0;
   protected b mPluginManager;
   protected com.alibaba.wireless.security.framework.a.c mPluginPackage;
   protected Activity mProxyActivity;
   protected Activity that;


   public void addContentView(View var1, LayoutParams var2) {
      if(this.mFrom == 0) {
         super.addContentView(var1, var2);
      } else {
         this.mProxyActivity.addContentView(var1, var2);
      }
   }

   public void attach(Activity var1, com.alibaba.wireless.security.framework.a.c var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append("attach: proxyActivity= ");
      var3.append(var1);
      Log.d("DLBasePluginActivity", var3.toString());
      this.mProxyActivity = var1;
      this.that = this.mProxyActivity;
      this.mPluginPackage = var2;
   }

   public int bindPluginService(a var1, ServiceConnection var2, int var3) {
      if(this.mFrom == 1 && var1.a() == null) {
         var1.a(this.mPluginPackage.a);
      }

      return this.mPluginManager.a(this.that, var1, var2, var3);
   }

   public View findViewById(int var1) {
      return this.mFrom == 0?super.findViewById(var1):this.mProxyActivity.findViewById(var1);
   }

   public void finish() {
      if(this.mFrom == 0) {
         super.finish();
      } else {
         this.mProxyActivity.finish();
      }
   }

   public Context getApplicationContext() {
      return this.mFrom == 0?super.getApplicationContext():this.mProxyActivity.getApplicationContext();
   }

   public ClassLoader getClassLoader() {
      return this.mFrom == 0?super.getClassLoader():this.mProxyActivity.getClassLoader();
   }

   public Intent getIntent() {
      return this.mFrom == 0?super.getIntent():this.mProxyActivity.getIntent();
   }

   public LayoutInflater getLayoutInflater() {
      return this.mFrom == 0?super.getLayoutInflater():this.mProxyActivity.getLayoutInflater();
   }

   public MenuInflater getMenuInflater() {
      return this.mFrom == 0?super.getMenuInflater():this.mProxyActivity.getMenuInflater();
   }

   public String getPackageName() {
      return this.mFrom == 0?super.getPackageName():this.mPluginPackage.a;
   }

   public Resources getResources() {
      return this.mFrom == 0?super.getResources():this.mProxyActivity.getResources();
   }

   public SharedPreferences getSharedPreferences(String var1, int var2) {
      return this.mFrom == 0?super.getSharedPreferences(var1, var2):this.mProxyActivity.getSharedPreferences(var1, var2);
   }

   public Object getSystemService(String var1) {
      return this.mFrom == 0?super.getSystemService(var1):this.mProxyActivity.getSystemService(var1);
   }

   public Window getWindow() {
      return this.mFrom == 0?super.getWindow():this.mProxyActivity.getWindow();
   }

   public WindowManager getWindowManager() {
      return this.mFrom == 0?super.getWindowManager():this.mProxyActivity.getWindowManager();
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      if(this.mFrom == 0) {
         super.onActivityResult(var1, var2, var3);
      }

   }

   public void onBackPressed() {
      if(this.mFrom == 0) {
         super.onBackPressed();
      }

   }

   public void onCreate(Bundle var1) {
      if(var1 != null) {
         this.mFrom = var1.getInt("extra.from", 0);
      }

      if(this.mFrom == 0) {
         super.onCreate(var1);
         this.mProxyActivity = this;
         this.that = this.mProxyActivity;
      }

      this.mPluginManager = b.a((Context)this.that);
      StringBuilder var2 = new StringBuilder();
      var2.append("onCreate: from= ");
      String var3;
      if(this.mFrom == 0) {
         var3 = "DLConstants.FROM_INTERNAL";
      } else {
         var3 = "FROM_EXTERNAL";
      }

      var2.append(var3);
      Log.d("DLBasePluginActivity", var2.toString());
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      return this.mFrom == 0?super.onCreateOptionsMenu(var1):true;
   }

   public void onDestroy() {
      if(this.mFrom == 0) {
         super.onDestroy();
      }

   }

   public boolean onKeyUp(int var1, KeyEvent var2) {
      return this.mFrom == 0?super.onKeyUp(var1, var2):false;
   }

   public void onNewIntent(Intent var1) {
      if(this.mFrom == 0) {
         super.onNewIntent(var1);
      }

   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      return this.mFrom == 0?this.onOptionsItemSelected(var1):false;
   }

   public void onPause() {
      if(this.mFrom == 0) {
         super.onPause();
      }

   }

   public void onRestart() {
      if(this.mFrom == 0) {
         super.onRestart();
      }

   }

   public void onRestoreInstanceState(Bundle var1) {
      if(this.mFrom == 0) {
         super.onRestoreInstanceState(var1);
      }

   }

   public void onResume() {
      if(this.mFrom == 0) {
         super.onResume();
      }

   }

   public void onSaveInstanceState(Bundle var1) {
      if(this.mFrom == 0) {
         super.onSaveInstanceState(var1);
      }

   }

   public void onStart() {
      if(this.mFrom == 0) {
         super.onStart();
      }

   }

   public void onStop() {
      if(this.mFrom == 0) {
         super.onStop();
      }

   }

   public boolean onTouchEvent(MotionEvent var1) {
      return this.mFrom == 0?super.onTouchEvent(var1):false;
   }

   public void onWindowAttributesChanged(android.view.WindowManager.LayoutParams var1) {
      if(this.mFrom == 0) {
         super.onWindowAttributesChanged(var1);
      }

   }

   public void onWindowFocusChanged(boolean var1) {
      if(this.mFrom == 0) {
         super.onWindowFocusChanged(var1);
      }

   }

   public void setContentView(int var1) {
      if(this.mFrom == 0) {
         super.setContentView(var1);
      } else {
         this.mProxyActivity.setContentView(var1);
      }
   }

   public void setContentView(View var1) {
      if(this.mFrom == 0) {
         super.setContentView(var1);
      } else {
         this.mProxyActivity.setContentView(var1);
      }
   }

   public void setContentView(View var1, LayoutParams var2) {
      if(this.mFrom == 0) {
         super.setContentView(var1, var2);
      } else {
         this.mProxyActivity.setContentView(var1, var2);
      }
   }

   public int startPluginActivity(a var1) {
      return this.startPluginActivityForResult(var1, -1);
   }

   public int startPluginActivityForResult(a var1, int var2) {
      if(this.mFrom == 1 && var1.a() == null) {
         var1.a(this.mPluginPackage.a);
      }

      return this.mPluginManager.a(this.that, var1, var2);
   }

   public int startPluginService(a var1) {
      if(this.mFrom == 1 && var1.a() == null) {
         var1.a(this.mPluginPackage.a);
      }

      return this.mPluginManager.a((Context)this.that, var1);
   }

   public int stopPluginService(a var1) {
      if(this.mFrom == 1 && var1.a() == null) {
         var1.a(this.mPluginPackage.a);
      }

      return this.mPluginManager.b((Context)this.that, var1);
   }

   int test() {
      return 1;
   }

   public int unBindPluginService(a var1, ServiceConnection var2) {
      if(this.mFrom == 1 && var1.a() == null) {
         var1.a(this.mPluginPackage.a);
      }

      return this.mPluginManager.a((Context)this.that, var1, var2);
   }
}
