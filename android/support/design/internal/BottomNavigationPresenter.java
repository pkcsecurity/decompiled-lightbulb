package android.support.design.internal;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.SubMenuBuilder;
import android.view.ViewGroup;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class BottomNavigationPresenter implements MenuPresenter {

   private int id;
   private MenuBuilder menu;
   private BottomNavigationMenuView menuView;
   private boolean updateSuspended = false;


   public boolean collapseItemActionView(MenuBuilder var1, MenuItemImpl var2) {
      return false;
   }

   public boolean expandItemActionView(MenuBuilder var1, MenuItemImpl var2) {
      return false;
   }

   public boolean flagActionItems() {
      return false;
   }

   public int getId() {
      return this.id;
   }

   public MenuView getMenuView(ViewGroup var1) {
      return this.menuView;
   }

   public void initForMenu(Context var1, MenuBuilder var2) {
      this.menu = var2;
      this.menuView.initialize(this.menu);
   }

   public void onCloseMenu(MenuBuilder var1, boolean var2) {}

   public void onRestoreInstanceState(Parcelable var1) {
      if(var1 instanceof BottomNavigationPresenter.SavedState) {
         this.menuView.tryRestoreSelectedItemId(((BottomNavigationPresenter.SavedState)var1).selectedItemId);
      }

   }

   public Parcelable onSaveInstanceState() {
      BottomNavigationPresenter.SavedState var1 = new BottomNavigationPresenter.SavedState();
      var1.selectedItemId = this.menuView.getSelectedItemId();
      return var1;
   }

   public boolean onSubMenuSelected(SubMenuBuilder var1) {
      return false;
   }

   public void setBottomNavigationMenuView(BottomNavigationMenuView var1) {
      this.menuView = var1;
   }

   public void setCallback(MenuPresenter.Callback var1) {}

   public void setId(int var1) {
      this.id = var1;
   }

   public void setUpdateSuspended(boolean var1) {
      this.updateSuspended = var1;
   }

   public void updateMenuView(boolean var1) {
      if(!this.updateSuspended) {
         if(var1) {
            this.menuView.buildMenuView();
         } else {
            this.menuView.updateMenuView();
         }
      }
   }

   static class SavedState implements Parcelable {

      public static final Creator<BottomNavigationPresenter.SavedState> CREATOR = new Creator() {
         public BottomNavigationPresenter.SavedState createFromParcel(Parcel var1) {
            return new BottomNavigationPresenter.SavedState(var1);
         }
         public BottomNavigationPresenter.SavedState[] newArray(int var1) {
            return new BottomNavigationPresenter.SavedState[var1];
         }
      };
      int selectedItemId;


      SavedState() {}

      SavedState(Parcel var1) {
         this.selectedItemId = var1.readInt();
      }

      public int describeContents() {
         return 0;
      }

      public void writeToParcel(@NonNull Parcel var1, int var2) {
         var1.writeInt(this.selectedItemId);
      }
   }
}
