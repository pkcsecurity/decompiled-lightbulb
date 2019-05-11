package com.google.android.gms.common;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;

public final class AccountPicker {

   public static Intent newChooseAccountIntent(Account var0, ArrayList<Account> var1, String[] var2, boolean var3, String var4, String var5, String[] var6, Bundle var7) {
      Intent var8 = new Intent();
      Preconditions.checkArgument(true, "We only support hostedDomain filter for account chip styled account picker");
      var8.setAction("com.google.android.gms.common.account.CHOOSE_ACCOUNT");
      var8.setPackage("com.google.android.gms");
      var8.putExtra("allowableAccounts", var1);
      var8.putExtra("allowableAccountTypes", var2);
      var8.putExtra("addAccountOptions", var7);
      var8.putExtra("selectedAccount", var0);
      var8.putExtra("alwaysPromptForAccount", var3);
      var8.putExtra("descriptionTextOverride", var4);
      var8.putExtra("authTokenType", var5);
      var8.putExtra("addAccountRequiredFeatures", var6);
      var8.putExtra("setGmsCoreAccount", false);
      var8.putExtra("overrideTheme", 0);
      var8.putExtra("overrideCustomTheme", 0);
      var8.putExtra("hostedDomainFilter", (String)null);
      return var8;
   }
}
