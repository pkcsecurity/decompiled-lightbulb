package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.design.R;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.internal.NavigationMenuView;
import android.support.design.internal.ParcelableSparseArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class NavigationMenuPresenter implements MenuPresenter {

   private static final String STATE_ADAPTER = "android:menu:adapter";
   private static final String STATE_HEADER = "android:menu:header";
   private static final String STATE_HIERARCHY = "android:menu:list";
   NavigationMenuPresenter.NavigationMenuAdapter adapter;
   private MenuPresenter.Callback callback;
   LinearLayout headerLayout;
   ColorStateList iconTintList;
   private int id;
   Drawable itemBackground;
   int itemHorizontalPadding;
   int itemIconPadding;
   LayoutInflater layoutInflater;
   MenuBuilder menu;
   private NavigationMenuView menuView;
   final OnClickListener onClickListener = new OnClickListener() {
      public void onClick(View var1) {
         NavigationMenuItemView var3 = (NavigationMenuItemView)var1;
         NavigationMenuPresenter.this.setUpdateSuspended(true);
         MenuItemImpl var4 = var3.getItemData();
         boolean var2 = NavigationMenuPresenter.this.menu.performItemAction(var4, NavigationMenuPresenter.this, 0);
         if(var4 != null && var4.isCheckable() && var2) {
            NavigationMenuPresenter.this.adapter.setCheckedItem(var4);
         }

         NavigationMenuPresenter.this.setUpdateSuspended(false);
         NavigationMenuPresenter.this.updateMenuView(false);
      }
   };
   int paddingSeparator;
   private int paddingTopDefault;
   int textAppearance;
   boolean textAppearanceSet;
   ColorStateList textColor;


   public void addHeaderView(@NonNull View var1) {
      this.headerLayout.addView(var1);
      this.menuView.setPadding(0, 0, 0, this.menuView.getPaddingBottom());
   }

   public boolean collapseItemActionView(MenuBuilder var1, MenuItemImpl var2) {
      return false;
   }

   public void dispatchApplyWindowInsets(WindowInsetsCompat var1) {
      int var2 = var1.getSystemWindowInsetTop();
      if(this.paddingTopDefault != var2) {
         this.paddingTopDefault = var2;
         if(this.headerLayout.getChildCount() == 0) {
            this.menuView.setPadding(0, this.paddingTopDefault, 0, this.menuView.getPaddingBottom());
         }
      }

      ViewCompat.dispatchApplyWindowInsets(this.headerLayout, var1);
   }

   public boolean expandItemActionView(MenuBuilder var1, MenuItemImpl var2) {
      return false;
   }

   public boolean flagActionItems() {
      return false;
   }

   @Nullable
   public MenuItemImpl getCheckedItem() {
      return this.adapter.getCheckedItem();
   }

   public int getHeaderCount() {
      return this.headerLayout.getChildCount();
   }

   public View getHeaderView(int var1) {
      return this.headerLayout.getChildAt(var1);
   }

   public int getId() {
      return this.id;
   }

   @Nullable
   public Drawable getItemBackground() {
      return this.itemBackground;
   }

   public int getItemHorizontalPadding() {
      return this.itemHorizontalPadding;
   }

   public int getItemIconPadding() {
      return this.itemIconPadding;
   }

   @Nullable
   public ColorStateList getItemTextColor() {
      return this.textColor;
   }

   @Nullable
   public ColorStateList getItemTintList() {
      return this.iconTintList;
   }

   public MenuView getMenuView(ViewGroup var1) {
      if(this.menuView == null) {
         this.menuView = (NavigationMenuView)this.layoutInflater.inflate(R.layout.design_navigation_menu, var1, false);
         if(this.adapter == null) {
            this.adapter = new NavigationMenuPresenter.NavigationMenuAdapter();
         }

         this.headerLayout = (LinearLayout)this.layoutInflater.inflate(R.layout.design_navigation_item_header, this.menuView, false);
         this.menuView.setAdapter(this.adapter);
      }

      return this.menuView;
   }

   public View inflateHeaderView(@LayoutRes int var1) {
      View var2 = this.layoutInflater.inflate(var1, this.headerLayout, false);
      this.addHeaderView(var2);
      return var2;
   }

   public void initForMenu(Context var1, MenuBuilder var2) {
      this.layoutInflater = LayoutInflater.from(var1);
      this.menu = var2;
      this.paddingSeparator = var1.getResources().getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
   }

   public void onCloseMenu(MenuBuilder var1, boolean var2) {
      if(this.callback != null) {
         this.callback.onCloseMenu(var1, var2);
      }

   }

   public void onRestoreInstanceState(Parcelable var1) {
      if(var1 instanceof Bundle) {
         Bundle var3 = (Bundle)var1;
         SparseArray var2 = var3.getSparseParcelableArray("android:menu:list");
         if(var2 != null) {
            this.menuView.restoreHierarchyState(var2);
         }

         Bundle var5 = var3.getBundle("android:menu:adapter");
         if(var5 != null) {
            this.adapter.restoreInstanceState(var5);
         }

         SparseArray var4 = var3.getSparseParcelableArray("android:menu:header");
         if(var4 != null) {
            this.headerLayout.restoreHierarchyState(var4);
         }
      }

   }

   public Parcelable onSaveInstanceState() {
      Bundle var1 = new Bundle();
      SparseArray var2;
      if(this.menuView != null) {
         var2 = new SparseArray();
         this.menuView.saveHierarchyState(var2);
         var1.putSparseParcelableArray("android:menu:list", var2);
      }

      if(this.adapter != null) {
         var1.putBundle("android:menu:adapter", this.adapter.createInstanceState());
      }

      if(this.headerLayout != null) {
         var2 = new SparseArray();
         this.headerLayout.saveHierarchyState(var2);
         var1.putSparseParcelableArray("android:menu:header", var2);
      }

      return var1;
   }

   public boolean onSubMenuSelected(SubMenuBuilder var1) {
      return false;
   }

   public void removeHeaderView(@NonNull View var1) {
      this.headerLayout.removeView(var1);
      if(this.headerLayout.getChildCount() == 0) {
         this.menuView.setPadding(0, this.paddingTopDefault, 0, this.menuView.getPaddingBottom());
      }

   }

   public void setCallback(MenuPresenter.Callback var1) {
      this.callback = var1;
   }

   public void setCheckedItem(@NonNull MenuItemImpl var1) {
      this.adapter.setCheckedItem(var1);
   }

   public void setId(int var1) {
      this.id = var1;
   }

   public void setItemBackground(@Nullable Drawable var1) {
      this.itemBackground = var1;
      this.updateMenuView(false);
   }

   public void setItemHorizontalPadding(int var1) {
      this.itemHorizontalPadding = var1;
      this.updateMenuView(false);
   }

   public void setItemIconPadding(int var1) {
      this.itemIconPadding = var1;
      this.updateMenuView(false);
   }

   public void setItemIconTintList(@Nullable ColorStateList var1) {
      this.iconTintList = var1;
      this.updateMenuView(false);
   }

   public void setItemTextAppearance(@StyleRes int var1) {
      this.textAppearance = var1;
      this.textAppearanceSet = true;
      this.updateMenuView(false);
   }

   public void setItemTextColor(@Nullable ColorStateList var1) {
      this.textColor = var1;
      this.updateMenuView(false);
   }

   public void setUpdateSuspended(boolean var1) {
      if(this.adapter != null) {
         this.adapter.setUpdateSuspended(var1);
      }

   }

   public void updateMenuView(boolean var1) {
      if(this.adapter != null) {
         this.adapter.update();
      }

   }

   class NavigationMenuAdapter extends RecyclerView.Adapter<NavigationMenuPresenter.ViewHolder> {

      private static final String STATE_ACTION_VIEWS = "android:menu:action_views";
      private static final String STATE_CHECKED_ITEM = "android:menu:checked";
      private static final int VIEW_TYPE_HEADER = 3;
      private static final int VIEW_TYPE_NORMAL = 0;
      private static final int VIEW_TYPE_SEPARATOR = 2;
      private static final int VIEW_TYPE_SUBHEADER = 1;
      private MenuItemImpl checkedItem;
      private final ArrayList<NavigationMenuPresenter.NavigationMenuItem> items = new ArrayList();
      private boolean updateSuspended;


      NavigationMenuAdapter() {
         this.prepareMenuItems();
      }

      private void appendTransparentIconIfMissing(int var1, int var2) {
         while(var1 < var2) {
            ((NavigationMenuPresenter.NavigationMenuTextItem)this.items.get(var1)).needsEmptyIcon = true;
            ++var1;
         }

      }

      private void prepareMenuItems() {
         if(!this.updateSuspended) {
            this.updateSuspended = true;
            this.items.clear();
            this.items.add(new NavigationMenuPresenter.NavigationMenuHeaderItem());
            int var7 = NavigationMenuPresenter.this.menu.getVisibleItems().size();
            int var4 = 0;
            int var5 = -1;
            boolean var11 = false;

            int var3;
            for(int var1 = 0; var4 < var7; var1 = var3) {
               MenuItemImpl var12 = (MenuItemImpl)NavigationMenuPresenter.this.menu.getVisibleItems().get(var4);
               if(var12.isChecked()) {
                  this.setCheckedItem(var12);
               }

               if(var12.isCheckable()) {
                  var12.setExclusiveCheckable(false);
               }

               int var6;
               boolean var10;
               if(var12.hasSubMenu()) {
                  SubMenu var13 = var12.getSubMenu();
                  var6 = var5;
                  var10 = var11;
                  var3 = var1;
                  if(var13.hasVisibleItems()) {
                     if(var4 != 0) {
                        this.items.add(new NavigationMenuPresenter.NavigationMenuSeparatorItem(NavigationMenuPresenter.this.paddingSeparator, 0));
                     }

                     this.items.add(new NavigationMenuPresenter.NavigationMenuTextItem(var12));
                     int var8 = this.items.size();
                     int var9 = var13.size();
                     var6 = 0;

                     boolean var2;
                     boolean var16;
                     for(var2 = false; var6 < var9; var2 = var16) {
                        MenuItemImpl var14 = (MenuItemImpl)var13.getItem(var6);
                        var16 = var2;
                        if(var14.isVisible()) {
                           var16 = var2;
                           if(!var2) {
                              var16 = var2;
                              if(var14.getIcon() != null) {
                                 var16 = true;
                              }
                           }

                           if(var14.isCheckable()) {
                              var14.setExclusiveCheckable(false);
                           }

                           if(var12.isChecked()) {
                              this.setCheckedItem(var12);
                           }

                           this.items.add(new NavigationMenuPresenter.NavigationMenuTextItem(var14));
                        }

                        ++var6;
                     }

                     var6 = var5;
                     var10 = var11;
                     var3 = var1;
                     if(var2) {
                        this.appendTransparentIconIfMissing(var8, this.items.size());
                        var6 = var5;
                        var10 = var11;
                        var3 = var1;
                     }
                  }
               } else {
                  var6 = var12.getGroupId();
                  int var15;
                  if(var6 != var5) {
                     var1 = this.items.size();
                     if(var12.getIcon() != null) {
                        var10 = true;
                     } else {
                        var10 = false;
                     }

                     var15 = var1;
                     if(var4 != 0) {
                        var15 = var1 + 1;
                        this.items.add(new NavigationMenuPresenter.NavigationMenuSeparatorItem(NavigationMenuPresenter.this.paddingSeparator, NavigationMenuPresenter.this.paddingSeparator));
                     }
                  } else {
                     var10 = var11;
                     var15 = var1;
                     if(!var11) {
                        var10 = var11;
                        var15 = var1;
                        if(var12.getIcon() != null) {
                           this.appendTransparentIconIfMissing(var1, this.items.size());
                           var10 = true;
                           var15 = var1;
                        }
                     }
                  }

                  NavigationMenuPresenter.NavigationMenuTextItem var17 = new NavigationMenuPresenter.NavigationMenuTextItem(var12);
                  var17.needsEmptyIcon = var10;
                  this.items.add(var17);
                  var3 = var15;
               }

               ++var4;
               var5 = var6;
               var11 = var10;
            }

            this.updateSuspended = false;
         }
      }

      public Bundle createInstanceState() {
         Bundle var4 = new Bundle();
         if(this.checkedItem != null) {
            var4.putInt("android:menu:checked", this.checkedItem.getItemId());
         }

         SparseArray var5 = new SparseArray();
         int var1 = 0;

         for(int var2 = this.items.size(); var1 < var2; ++var1) {
            NavigationMenuPresenter.NavigationMenuItem var3 = (NavigationMenuPresenter.NavigationMenuItem)this.items.get(var1);
            if(var3 instanceof NavigationMenuPresenter.NavigationMenuTextItem) {
               MenuItemImpl var6 = ((NavigationMenuPresenter.NavigationMenuTextItem)var3).getMenuItem();
               View var8;
               if(var6 != null) {
                  var8 = var6.getActionView();
               } else {
                  var8 = null;
               }

               if(var8 != null) {
                  ParcelableSparseArray var7 = new ParcelableSparseArray();
                  var8.saveHierarchyState(var7);
                  var5.put(var6.getItemId(), var7);
               }
            }
         }

         var4.putSparseParcelableArray("android:menu:action_views", var5);
         return var4;
      }

      public MenuItemImpl getCheckedItem() {
         return this.checkedItem;
      }

      public int getItemCount() {
         return this.items.size();
      }

      public long getItemId(int var1) {
         return (long)var1;
      }

      public int getItemViewType(int var1) {
         NavigationMenuPresenter.NavigationMenuItem var2 = (NavigationMenuPresenter.NavigationMenuItem)this.items.get(var1);
         if(var2 instanceof NavigationMenuPresenter.NavigationMenuSeparatorItem) {
            return 2;
         } else if(var2 instanceof NavigationMenuPresenter.NavigationMenuHeaderItem) {
            return 3;
         } else if(var2 instanceof NavigationMenuPresenter.NavigationMenuTextItem) {
            return ((NavigationMenuPresenter.NavigationMenuTextItem)var2).getMenuItem().hasSubMenu()?1:0;
         } else {
            throw new RuntimeException("Unknown item type.");
         }
      }

      public void onBindViewHolder(NavigationMenuPresenter.ViewHolder var1, int var2) {
         switch(this.getItemViewType(var2)) {
         case 0:
            NavigationMenuItemView var6 = (NavigationMenuItemView)var1.itemView;
            var6.setIconTintList(NavigationMenuPresenter.this.iconTintList);
            if(NavigationMenuPresenter.this.textAppearanceSet) {
               var6.setTextAppearance(NavigationMenuPresenter.this.textAppearance);
            }

            if(NavigationMenuPresenter.this.textColor != null) {
               var6.setTextColor(NavigationMenuPresenter.this.textColor);
            }

            Drawable var4;
            if(NavigationMenuPresenter.this.itemBackground != null) {
               var4 = NavigationMenuPresenter.this.itemBackground.getConstantState().newDrawable();
            } else {
               var4 = null;
            }

            ViewCompat.setBackground(var6, var4);
            NavigationMenuPresenter.NavigationMenuTextItem var5 = (NavigationMenuPresenter.NavigationMenuTextItem)this.items.get(var2);
            var6.setNeedsEmptyIcon(var5.needsEmptyIcon);
            var6.setHorizontalPadding(NavigationMenuPresenter.this.itemHorizontalPadding);
            var6.setIconPadding(NavigationMenuPresenter.this.itemIconPadding);
            var6.initialize(var5.getMenuItem(), 0);
            return;
         case 1:
            ((TextView)var1.itemView).setText(((NavigationMenuPresenter.NavigationMenuTextItem)this.items.get(var2)).getMenuItem().getTitle());
            return;
         case 2:
            NavigationMenuPresenter.NavigationMenuSeparatorItem var3 = (NavigationMenuPresenter.NavigationMenuSeparatorItem)this.items.get(var2);
            var1.itemView.setPadding(0, var3.getPaddingTop(), 0, var3.getPaddingBottom());
            return;
         default:
         }
      }

      public NavigationMenuPresenter.ViewHolder onCreateViewHolder(ViewGroup var1, int var2) {
         switch(var2) {
         case 0:
            return new NavigationMenuPresenter.NormalViewHolder(NavigationMenuPresenter.this.layoutInflater, var1, NavigationMenuPresenter.this.onClickListener);
         case 1:
            return new NavigationMenuPresenter.SubheaderViewHolder(NavigationMenuPresenter.this.layoutInflater, var1);
         case 2:
            return new NavigationMenuPresenter.SeparatorViewHolder(NavigationMenuPresenter.this.layoutInflater, var1);
         case 3:
            return new NavigationMenuPresenter.HeaderViewHolder(NavigationMenuPresenter.this.headerLayout);
         default:
            return null;
         }
      }

      public void onViewRecycled(NavigationMenuPresenter.ViewHolder var1) {
         if(var1 instanceof NavigationMenuPresenter.NormalViewHolder) {
            ((NavigationMenuItemView)var1.itemView).recycle();
         }

      }

      public void restoreInstanceState(Bundle var1) {
         byte var3 = 0;
         int var4 = var1.getInt("android:menu:checked", 0);
         int var2;
         NavigationMenuPresenter.NavigationMenuItem var6;
         if(var4 != 0) {
            this.updateSuspended = true;
            int var5 = this.items.size();

            for(var2 = 0; var2 < var5; ++var2) {
               var6 = (NavigationMenuPresenter.NavigationMenuItem)this.items.get(var2);
               if(var6 instanceof NavigationMenuPresenter.NavigationMenuTextItem) {
                  MenuItemImpl var9 = ((NavigationMenuPresenter.NavigationMenuTextItem)var6).getMenuItem();
                  if(var9 != null && var9.getItemId() == var4) {
                     this.setCheckedItem(var9);
                     break;
                  }
               }
            }

            this.updateSuspended = false;
            this.prepareMenuItems();
         }

         SparseArray var8 = var1.getSparseParcelableArray("android:menu:action_views");
         if(var8 != null) {
            var4 = this.items.size();

            for(var2 = var3; var2 < var4; ++var2) {
               var6 = (NavigationMenuPresenter.NavigationMenuItem)this.items.get(var2);
               if(var6 instanceof NavigationMenuPresenter.NavigationMenuTextItem) {
                  MenuItemImpl var7 = ((NavigationMenuPresenter.NavigationMenuTextItem)var6).getMenuItem();
                  if(var7 != null) {
                     View var10 = var7.getActionView();
                     if(var10 != null) {
                        ParcelableSparseArray var11 = (ParcelableSparseArray)var8.get(var7.getItemId());
                        if(var11 != null) {
                           var10.restoreHierarchyState(var11);
                        }
                     }
                  }
               }
            }
         }

      }

      public void setCheckedItem(MenuItemImpl var1) {
         if(this.checkedItem != var1) {
            if(var1.isCheckable()) {
               if(this.checkedItem != null) {
                  this.checkedItem.setChecked(false);
               }

               this.checkedItem = var1;
               var1.setChecked(true);
            }
         }
      }

      public void setUpdateSuspended(boolean var1) {
         this.updateSuspended = var1;
      }

      public void update() {
         this.prepareMenuItems();
         this.notifyDataSetChanged();
      }
   }

   static class NavigationMenuSeparatorItem implements NavigationMenuPresenter.NavigationMenuItem {

      private final int paddingBottom;
      private final int paddingTop;


      public NavigationMenuSeparatorItem(int var1, int var2) {
         this.paddingTop = var1;
         this.paddingBottom = var2;
      }

      public int getPaddingBottom() {
         return this.paddingBottom;
      }

      public int getPaddingTop() {
         return this.paddingTop;
      }
   }

   static class SubheaderViewHolder extends NavigationMenuPresenter.ViewHolder {

      public SubheaderViewHolder(LayoutInflater var1, ViewGroup var2) {
         super(var1.inflate(R.layout.design_navigation_item_subheader, var2, false));
      }
   }

   abstract static class ViewHolder extends RecyclerView.ViewHolder {

      public ViewHolder(View var1) {
         super(var1);
      }
   }

   interface NavigationMenuItem {
   }

   static class SeparatorViewHolder extends NavigationMenuPresenter.ViewHolder {

      public SeparatorViewHolder(LayoutInflater var1, ViewGroup var2) {
         super(var1.inflate(R.layout.design_navigation_item_separator, var2, false));
      }
   }

   static class NormalViewHolder extends NavigationMenuPresenter.ViewHolder {

      public NormalViewHolder(LayoutInflater var1, ViewGroup var2, OnClickListener var3) {
         super(var1.inflate(R.layout.design_navigation_item, var2, false));
         this.itemView.setOnClickListener(var3);
      }
   }

   static class HeaderViewHolder extends NavigationMenuPresenter.ViewHolder {

      public HeaderViewHolder(View var1) {
         super(var1);
      }
   }

   static class NavigationMenuHeaderItem implements NavigationMenuPresenter.NavigationMenuItem {

   }

   static class NavigationMenuTextItem implements NavigationMenuPresenter.NavigationMenuItem {

      private final MenuItemImpl menuItem;
      boolean needsEmptyIcon;


      NavigationMenuTextItem(MenuItemImpl var1) {
         this.menuItem = var1;
      }

      public MenuItemImpl getMenuItem() {
         return this.menuItem;
      }
   }
}
