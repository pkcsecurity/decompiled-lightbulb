package android.support.v7.view.menu;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public abstract class BaseMenuPresenter implements MenuPresenter {

   private MenuPresenter.Callback mCallback;
   protected Context mContext;
   private int mId;
   protected LayoutInflater mInflater;
   private int mItemLayoutRes;
   protected MenuBuilder mMenu;
   private int mMenuLayoutRes;
   protected MenuView mMenuView;
   protected Context mSystemContext;
   protected LayoutInflater mSystemInflater;


   public BaseMenuPresenter(Context var1, int var2, int var3) {
      this.mSystemContext = var1;
      this.mSystemInflater = LayoutInflater.from(var1);
      this.mMenuLayoutRes = var2;
      this.mItemLayoutRes = var3;
   }

   protected void addItemView(View var1, int var2) {
      ViewGroup var3 = (ViewGroup)var1.getParent();
      if(var3 != null) {
         var3.removeView(var1);
      }

      ((ViewGroup)this.mMenuView).addView(var1, var2);
   }

   public abstract void bindItemView(MenuItemImpl var1, MenuView.ItemView var2);

   public boolean collapseItemActionView(MenuBuilder var1, MenuItemImpl var2) {
      return false;
   }

   public MenuView.ItemView createItemView(ViewGroup var1) {
      return (MenuView.ItemView)this.mSystemInflater.inflate(this.mItemLayoutRes, var1, false);
   }

   public boolean expandItemActionView(MenuBuilder var1, MenuItemImpl var2) {
      return false;
   }

   public boolean filterLeftoverView(ViewGroup var1, int var2) {
      var1.removeViewAt(var2);
      return true;
   }

   public boolean flagActionItems() {
      return false;
   }

   public MenuPresenter.Callback getCallback() {
      return this.mCallback;
   }

   public int getId() {
      return this.mId;
   }

   public View getItemView(MenuItemImpl var1, View var2, ViewGroup var3) {
      MenuView.ItemView var4;
      if(var2 instanceof MenuView.ItemView) {
         var4 = (MenuView.ItemView)var2;
      } else {
         var4 = this.createItemView(var3);
      }

      this.bindItemView(var1, var4);
      return (View)var4;
   }

   public MenuView getMenuView(ViewGroup var1) {
      if(this.mMenuView == null) {
         this.mMenuView = (MenuView)this.mSystemInflater.inflate(this.mMenuLayoutRes, var1, false);
         this.mMenuView.initialize(this.mMenu);
         this.updateMenuView(true);
      }

      return this.mMenuView;
   }

   public void initForMenu(Context var1, MenuBuilder var2) {
      this.mContext = var1;
      this.mInflater = LayoutInflater.from(this.mContext);
      this.mMenu = var2;
   }

   public void onCloseMenu(MenuBuilder var1, boolean var2) {
      if(this.mCallback != null) {
         this.mCallback.onCloseMenu(var1, var2);
      }

   }

   public boolean onSubMenuSelected(SubMenuBuilder var1) {
      return this.mCallback != null?this.mCallback.onOpenSubMenu(var1):false;
   }

   public void setCallback(MenuPresenter.Callback var1) {
      this.mCallback = var1;
   }

   public void setId(int var1) {
      this.mId = var1;
   }

   public boolean shouldIncludeItem(int var1, MenuItemImpl var2) {
      return true;
   }

   public void updateMenuView(boolean var1) {
      ViewGroup var7 = (ViewGroup)this.mMenuView;
      if(var7 != null) {
         MenuBuilder var6 = this.mMenu;
         int var2 = 0;
         if(var6 != null) {
            this.mMenu.flagActionItems();
            ArrayList var8 = this.mMenu.getVisibleItems();
            int var5 = var8.size();
            int var3 = 0;

            int var4;
            for(var2 = 0; var3 < var5; var2 = var4) {
               MenuItemImpl var9 = (MenuItemImpl)var8.get(var3);
               var4 = var2;
               if(this.shouldIncludeItem(var2, var9)) {
                  View var10 = var7.getChildAt(var2);
                  MenuItemImpl var12;
                  if(var10 instanceof MenuView.ItemView) {
                     var12 = ((MenuView.ItemView)var10).getItemData();
                  } else {
                     var12 = null;
                  }

                  View var11 = this.getItemView(var9, var10, var7);
                  if(var9 != var12) {
                     var11.setPressed(false);
                     var11.jumpDrawablesToCurrentState();
                  }

                  if(var11 != var10) {
                     this.addItemView(var11, var2);
                  }

                  var4 = var2 + 1;
               }

               ++var3;
            }
         }

         while(var2 < var7.getChildCount()) {
            if(!this.filterLeftoverView(var7, var2)) {
               ++var2;
            }
         }

      }
   }
}
