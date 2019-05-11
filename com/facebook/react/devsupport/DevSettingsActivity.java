package com.facebook.react.devsupport;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.facebook.react.R;

public class DevSettingsActivity extends PreferenceActivity {

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setTitle(R.string.catalyst_settings_title);
      this.addPreferencesFromResource(R.xml.preferences);
   }
}
